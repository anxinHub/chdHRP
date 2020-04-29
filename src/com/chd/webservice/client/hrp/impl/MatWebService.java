package com.chd.webservice.client.hrp.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.jdbc.ConfigInit;
import com.chd.hrp.webservice.dao.hrp.MatMapper;
import com.chd.webservice.client.hrp.proxy.MatProxy;


public class MatWebService{

	private static Logger logger = Logger.getLogger(MatWebService.class);

	public MatWebService(){
		
	}
	
	//院端供应商产品审核推送到HRP云平台
	public String pushSupProSpecAudit(String data){
		
		String reJson=null;
		if(data==null || data.equals("")){
			reJson="{\"state\":\"false\",\"msg\":\"参数为空\"}";
			return reJson;
		}
		
		try{
			String url=ConfigInit.getConfigProperties("qzj_url");
			if(url==null || url.equals("")){
				reJson="{\"state\":\"false\",\"msg\":\"前置机url为空\"}";
				return reJson;
			}
			//调用WebService
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(MatProxy.class);
			factory.setAddress("http://"+url+"/HRP_QZ/webservice/matService?wsdl");
			MatProxy proxy = (MatProxy) factory.create();
			//返回JSON格式
			reJson = proxy.pushSupProSpecAudit(data);
		}catch(Exception e){
			reJson="{\"state\":\"false\",\"msg\":\"前置机通信异常\"}";
			logger.error(e.getMessage(), e);
		}
		return reJson;
	}
	
	
	//院端发送订单推送HRP云平台
	public String pushSupOrder(List<Map<String, Object>> list,String url){
		
		String reJson=null;
		if(list==null || list.size()==0){
			reJson="{\"state\":\"false\",\"msg\":\"请选择要发送的订单\"}";
			return reJson;
		}
		
		try{
			
//			String url=ConfigInit.getConfigProperties("qzj_url");
//			if(url==null || url.equals("")){
//				reJson="{\"state\":\"false\",\"msg\":\"前置机url为空\"}";
//				return reJson;
//			}
			
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatMapper matMapper = (MatMapper)context.getBean("matMapper");
			
			Map<String,Object> param=new HashMap<String,Object>();
			StringBuilder orderId=new StringBuilder();
			for(Map<String,Object> map:list){
				param.put("group_id", map.get("group_id"));
				param.put("hos_id", map.get("hos_id"));
				param.put("copy_code", map.get("copy_code"));
				orderId.append(map.get("order_id").toString()+",");
			}
			orderId.setCharAt(orderId.length()-1, ' ');
			param.put("order_id", orderId.toString());
			List<Map<String, Object>> mainList=matMapper.queryMatOrderMainByOrderId(param);
			List<Map<String, Object>> detailList=matMapper.queryMatOrderDetailByOrderId(param);
			StringBuilder data=new StringBuilder();
			if(mainList!=null && mainList.size()>0 && detailList!=null && detailList.size()>0){
				
				StringBuilder noInv=new StringBuilder();
				String chosId=null;
				StringBuilder rows=new StringBuilder();
				rows.append("[");
				for(Map<String, Object> map:mainList){
					rows.append("{");
					int i=0;
					for(Map.Entry<String, Object> entry:map.entrySet()){
						Object obj=entry.getValue();
						if(i==0){
							rows.append("\""+entry.getKey()+"\":\""+(obj==null?"":obj)+"\"");
						}else{
							rows.append(",\""+entry.getKey()+"\":\""+(obj==null?"":obj)+"\"");
						}
						i++;
						
					}
					
					rows.append(",\"detail\":[");
					i=0;
					for(Map<String, Object> dmap:detailList){
						if(dmap.get("order_id").toString().equals(map.get("order_id").toString())){
							
							if(dmap.get("chos_id")!=null && !dmap.get("chos_id").toString().equals("")){
								if(i==0){
									rows.append("{");
								}else{
									rows.append(",{");
								}
								
								chosId=dmap.get("chos_id").toString();
								for(Map.Entry<String, Object> dentry:dmap.entrySet()){
									Object obj=dentry.getValue();
									rows.append("\""+dentry.getKey()+"\":\""+(obj==null?"":obj)+"\",");
								}
								rows.setCharAt(rows.length()-1, '}');
								i++;
							}else{
								String invCode=dmap.get("inv_code").toString();
								if(noInv!=null && noInv.toString().indexOf(invCode)==-1){
									noInv.append(dmap.get("inv_code").toString()+" "+dmap.get("inv_name").toString()+"，");
								}
								
							}
						}
					}
					rows.append("]},");
				}
				
				if(noInv!=null && noInv.length()>0){
					reJson="{\"state\":\"true\",\"msg\":\""+noInv.toString()+"没有与供应商产品对应，请先审核供应商的产品并且绑定材料\",\"is_open\":true}";
					return reJson; 
				}
				
				
				rows.setCharAt(rows.length()-1, ']');
				data.append("{\"chos_id\":\""+chosId+"\",\"Rows\":"+rows.toString()+"}");
				
			}else{
				data.append("{\"state\":\"true\",\"msg\":\"没有数据\"}");
			}
			
			//调用WebService
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(MatProxy.class);
			factory.setAddress("http://"+url+"/HRP_QZ/webservice/matService?wsdl");
			MatProxy proxy = (MatProxy) factory.create();
			
			//返回JSON格式
			reJson = proxy.pushMathOrder(data.toString());
		}catch(Exception e){
			reJson="{\"state\":\"false\",\"msg\":\"前置机通信异常\"}";
			logger.error(e.getMessage(), e);
		}
		return reJson;
	}
	
	
	
	//院端签收推送供应商配送单到HRP云平台
	public String pushSupPsdState(Map<String,Object> map){
		
		String reJson=null;
		if(map==null || map.size()==0){
			reJson="{\"state\":\"false\",\"msg\":\"参数为空\"}";
			return reJson;
		}
		
		if(map.get("delivery_id")==null || map.get("delivery_id").toString().equals("")){
			reJson="{\"state\":\"false\",\"msg\":\"delivery_id参数为空\"}";
			return reJson;
		}
		
		try{
			String url=ConfigInit.getConfigProperties("qzj_url");
			if(url==null || url.equals("")){
				reJson="{\"state\":\"false\",\"msg\":\"前置机url为空\"}";
				return reJson;
			}
			
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatMapper matMapper = (MatMapper)context.getBean("matMapper");
			String chosId=matMapper.queryCHosId(map);
			if(chosId==null || chosId.equals("")){
				reJson="{\"state\":\"false\",\"msg\":\"chos_id为空\"}";
				return reJson;
			}
			map.put("chos_id", chosId);
			String data=JSON.toJSONString(map);
			
			//调用WebService
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(MatProxy.class);
			factory.setAddress("http://"+url+"/HRP_QZ/webservice/matService?wsdl");
			MatProxy proxy = (MatProxy) factory.create();
			//返回JSON格式
			reJson = proxy.pushSupPsdState(data);
		}catch(Exception e){
			reJson="{\"state\":\"false\",\"msg\":\"前置机通信异常\"}";
			logger.error(e.getMessage(), e);
		}
		return reJson;
	}
	
}
