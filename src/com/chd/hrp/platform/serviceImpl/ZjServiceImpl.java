package com.chd.hrp.platform.serviceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
 

import javax.annotation.Resource;

import oracle.net.aso.a;

import org.activiti.engine.impl.json.JsonObjectConverter;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.tool.HttpClientTool;
import com.chd.base.tool.ServerUrl;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.order.MatOrderMainMapper;
import com.chd.hrp.mat.dao.order.audit.MatOrderAuditMapper;
import com.chd.hrp.mat.dao.storage.back.MatStorageBackMapper;
import com.chd.hrp.mat.dao.storage.in.MatStorageInMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.platform.dao.ZjMapper;
import com.chd.hrp.sup.dao.SupDeliveryMainMapper;
import com.chd.hrp.sys.serviceImpl.CopyServiceImpl;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.And;
import com.chd.hrp.mat.dao.matpay.MatPayMainMapper;

@Service("zjService")
public class ZjServiceImpl{
	
	private static Logger logger = Logger.getLogger(ZjServiceImpl.class);             
	   
	@Resource(name="zjMapper")    
	private final ZjMapper zjMapper=null;
	@Resource(name="matOrderMainMapper")
	private final MatOrderMainMapper matOrderMainMapper=null;
	@Resource(name="matOrderAuditMapper")
	private final MatOrderAuditMapper matOrderAuditMapper=null;
	@Resource(name = "matPayMainMapper")
	private final MatPayMainMapper matPayMainMapper = null;
	@Resource(name="matCommonMapper")
	private final MatCommonMapper matCommonMapper=null;
	@Resource(name="matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper=null;
	@Resource(name="matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper=null;
	@Resource(name="supDeliveryMainMapper")
	private final SupDeliveryMainMapper supDeliveryMainMapper=null;
	@Resource(name="matStorageInMapper")
	private final MatStorageInMapper matStorageInMapper=null;
	@Resource(name="matCommonService")
	private final MatCommonService matCommonService=null;
	
	


public String getProcureCatalog(Map<String,Object> entityMap) throws Exception{
		
		//获取平台访问路径
		ServerUrl su=new ServerUrl();
		String url=su.getProperty("chdsup.ip", "chdsup.H006");
		//拼接平台参数map
		/**
		 * {
		 *    "currentPageNumber":"当前页",   		  //不填,默认1
		 *    "departmentID":"医疗机构采购部门编号",       //不填,默认省平台部门列表的第一个部门编号
		 *    "beginTime":"开始时间",                   //格式为 yyyy-MM-dd HH:mm:ss,不填的话,默认当前日期前二十天至当前日期
		 *	  "endTime":"结束时间",                     //起始时间和终止时间相差时间不能超过20天
		 * }
		 */
		
		Map<String, Object> requestProcureCatalogMap=new HashMap<String, Object>();
		
		//通过前台获取  终止时间 ，计算起始时间  = 终止时间 -10 
		requestProcureCatalogMap.put("endTime", entityMap.get("endDate"));

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime(sf.parse(entityMap.get("endDate").toString()) );
	    
	    calendar.add(Calendar.DATE,-10);
		   
	    requestProcureCatalogMap.put("beginTime",sf.format(calendar.getTime()) ); 
		
	    //执行方法,获取返回值
  		String responseProcureCatalogStr = HttpClientTool.doPost(url, JSON.toJSONString(requestProcureCatalogMap));
  		
  		//执行第一遍 为了获取总页数   totalPageCount
  		Map<String, Object> responseProcureCatalogMap = JSONObject.parseObject(responseProcureCatalogStr,Map.class);
	    
  		int currentPage  = 1;
	    
	    int totalPage = Integer.parseInt(responseProcureCatalogMap.get("totalPageCount").toString());
	    
	    //定义总的list  将参数（当前页数） 传入  分别得到当前页数的list
	    List<Map> totalList =new ArrayList<Map>();
	     
	    for (int i = 1; i<=totalPage;i++){
	    	
	    	requestProcureCatalogMap.put("currentPageNumber", currentPage ); 
	    	
	    	//传入当前页数   获取返回值
	    	String responseProcureCatalogStr1 = HttpClientTool.doPost(url, JSON.toJSONString(requestProcureCatalogMap));
	  		//获取当前页数 得到的list 值
	  		Map<String, Object> responseProcureCatalogMap1 = JSONObject.parseObject(responseProcureCatalogStr1,Map.class);
	  		
	    	if (responseProcureCatalogMap1.get("returnCode")!=null && "1".equals(responseProcureCatalogMap1.get("returnCode").toString())) {
				List<Map> procureCatalogList=new ArrayList<Map>();
				if (responseProcureCatalogMap1.get("successList")!=null && StringUtils.isNotBlank(responseProcureCatalogMap1.get("successList").toString())) {
					procureCatalogList=JSONArray.parseArray(responseProcureCatalogMap1.get("successList").toString(), Map.class);
					//将每页得到list  放入总的list
					totalList.addAll(procureCatalogList);
				}
			} 
	    	currentPage = i+1;
	    	
	    }

	    if(totalList.size()>0){
	    	 zjMapper.deleteProcureCatalog(totalList);
	    	 zjMapper.insertProcureCatalog(totalList);
			 return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	    }else{
	    	return "{\"error\":\"操作失败.\",\"state\":\"true\"}";
	    }
	    
	    /**
	     * 之前没有传入当前页数时的方法
	     */
		//执行方法,获取返回值
//		String responseProcureCatalogStr = HttpClientTool.doPost(url, JSON.toJSONString(requestProcureCatalogMap));
//		
//		Map<String, Object> responseProcureCatalogMap=JSONObject.parseObject(responseProcureCatalogStr,Map.class);
//		
//		if (responseProcureCatalogMap.get("returnCode")!=null && "1".equals(responseProcureCatalogMap.get("returnCode").toString())) {
//			List<Map> procureCatalogList=new ArrayList<Map>();
//			if (responseProcureCatalogMap.get("successList")!=null && StringUtils.isNotBlank(responseProcureCatalogMap.get("successList").toString())) {
//				procureCatalogList=JSONArray.parseArray(responseProcureCatalogMap.get("successList").toString(), Map.class);
//				 zjMapper.insertProcureCatalog(procureCatalogList);
//				 return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
//			}
//		}else if(responseProcureCatalogMap.get("returnCode")!=null && "2".equals(responseProcureCatalogMap.get("returnCode").toString())) {
//			requestProcureCatalogMap.put("flag", 1);
//			return getProcureCatalog(requestProcureCatalogMap);
//		}
//		return "{\"error\":\"操作失败.\",\"state\":\"true\"}";
		
		
	}
	
	
	


	




	/**
	 * 将hrp订单发送省平台
	 * 暂时只支持单张订单的发送
	 * liusiqi (2018-06-06)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String sendOrder(Map<String, Object> mapVo){
		try {
			StringBuffer errMsg=new StringBuffer();//错误信息
			Map<String,String> sendOrderMap=new HashMap<String, String>();//省平台订单数据
			//获取hrp订单主表信息
			Map<String, Object> matOrderMainMap = matOrderMainMapper.queryByCode(mapVo);
			if (matOrderMainMap.get("is_notice")==null || "0".equals(String.valueOf(matOrderMainMap.get("is_notice")))) {
				//通过订单id获取订单明细数据 (从zj_inv_map,zj_ps_map,zj_hospital_procurecata_log中获取订单省平台信息,用来组装省平台)
				List<Map<String, String>> platformOrderDetailList=zjMapper.getPlatformOrderDetailsByHrpOrderId(mapVo);
				
			
//			不再单独判断供应商,而是随材料的默认供应商走
//			//默认一个订单所有明细材料都是同一个配货商,如果没有配送商,此订单无法发送省平台
//			if (StringUtils.isBlank(platformOrderDetailList.get(0).get("companyIdPs"))) {
//				errMsg.append("供应商:  ");
//				errMsg.append(platformOrderDetailList.get(0).get("sup_code"));
//				errMsg.append(platformOrderDetailList.get(0).get("sup_name"));
//				errMsg.append(",");
//			}
				//如果有一个材料goodsID值为空,则说明hrp内材料未与省平台材料进行关联,则提示材料未关联,此订单不予发送
				
				for (Map<String, String> map : platformOrderDetailList) {
					//移出没用字段,否则发送省平台报错
					map.remove("inv_code");
					map.remove("inv_name");
					map.remove("sup_code");
					map.remove("sup_name");
					if (StringUtils.isBlank(map.get("goodsID"))) {
						errMsg.append("物资材料:  ");
						errMsg.append(map.get("inv_code")).append(map.get("inv_name")).append(",");
					}
					
				}
				
				//当条数是10的倍数时不加1 平台每次最多提交10条数据
				int size = platformOrderDetailList.size() % 10>0?platformOrderDetailList.size() % 10 +1:platformOrderDetailList.size() % 10 ;
				
				logger.info("有多少条数据"+size+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				
				int index = 0;
		        for (int i = 0; i < size; i++) {   
		            List<Map<String,String>> temp = new ArrayList<Map<String,String>>();
		            for (int j = 0; j < 10; j++) {
		            	if (platformOrderDetailList.size() > index) {
		                    temp.add(platformOrderDetailList.get(index));
		                    index++;
		                }else{
		                	break;
		                }
		            }
		            
		            logger.info("第"+i+"次发送数据"+temp+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
		           // System.out.println(temp.toString());
		       
				if (StringUtils.isNotBlank(errMsg.toString())) {
					return "{\"state\":\"false\",\"errMsg\":\""+errMsg.toString().substring(0,errMsg.toString().length()-1)+"未做省平台对应关系,此订单无法发送\"}";
				}else{
					//组装发送数据
					sendOrderMap.put("hospitalOrderId", matOrderMainMap.get("order_id").toString());
					sendOrderMap.put("distributeAddress", matOrderMainMap.get("arr_address")==null?"":matOrderMainMap.get("arr_address").toString());
					sendOrderMap.put("remarks", "由院内系统订单"+matOrderMainMap.get("order_code").toString()+"生成");
					sendOrderMap.put("list",JSON.toJSONString(temp));
					//发送订单
					ServerUrl su=new ServerUrl();
					String	url = su.getProperty("chdsup.ip", "chdsup.H001");//参看 HRP系统的 serverUrl.properties属性文件
					
					logger.info("第"+i+"增加数据开始"+sendOrderMap+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
					String sendOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(sendOrderMap));	
					Map sendOrderResultMap = JSONObject.parseObject(sendOrderResultStr, Map.class);
					if ("1".equals(String.valueOf(sendOrderResultMap.get("returnCode")))) {
						logger.info("第"+i+"增加数据成功"+sendOrderResultMap.get("returnCode")+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
						//添加成功
						String order_id = String.valueOf(matOrderMainMap.get("order_id"));//hrp订单id
						String platform_order_id=String.valueOf(sendOrderResultMap.get("orderId"));//省平台订单id
						List<Map<String, Object>> orderRelaList=new ArrayList<Map<String, Object>>();
						List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(sendOrderResultMap.get("successList")), List.class);
						for (Map<String, Object> map : successList) {
							map.put("group_id", mapVo.get("group_id"));
							map.put("hos_id", mapVo.get("hos_id"));
							map.put("copy_code", mapVo.get("copy_code"));
							map.put("order_id", order_id);
							map.put("platform_order_id", platform_order_id);
							orderRelaList.add(map);
						}
						//如果订单明细添加成功,将订单明细信息保存至数据库zj_order_map对应关系表中
						logger.info("第"+i+"次保存zj_order_map数据"+orderRelaList+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
						zjMapper.insertOrderRela(orderRelaList);
						//更新hrp订单is_notice状态
						List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
						mapVo.put("is_notice","2");//省平台发送过的订单 is_notice值为2,已经获取省平台配送信息的订单is_notice值为3
						list.add(mapVo);
						logger.info("第"+i+"次更新状态"+list+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
						matOrderAuditMapper.sendOutOrderMain(list);
						//提交订单
						url = su.getProperty("chdsup.ip", "chdsup.H003");
						Map<String, String> submitOrderMap=new HashMap<String, String>();
						/*
						 * {
						 *	"orderId":"省平台订单编号",
						 *	"departmentID":"医疗机构采购部门编号"//不填默认省平台H002接口部门列表第一个部门
						 *	}
						 */
						submitOrderMap.put("orderId",platform_order_id);
						logger.info("第"+i+"提交数据开始"+submitOrderMap+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
						String submitOrderReturnStr = HttpClientTool.doPost(url, JSON.toJSONString(submitOrderMap));
						Map submitOrderReturnMap = JSONObject.parseObject(submitOrderReturnStr, Map.class);
						if ("1".equals(String.valueOf(submitOrderReturnMap.get("returnCode")))) {
							logger.info("第"+i+"提交数据成功"+submitOrderReturnMap.get("returnCode")+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"); 
							return "{\"state\":\"true\",\"pageUrl\":\"\"}";
						}else{
							return "{\"state\":\"false\",\"errMsg\":\"订单省平台提交失败"+String.valueOf(submitOrderReturnMap.get("returnMsg"))+"\"}";
							
						}
					}else{
						//note 值为 orderMain 说明主表添加失败,值为orderDetail 说明明细表添加失败
						if("orderMain".equals(String.valueOf(sendOrderResultMap.get("note")))){
							return "{\"state\":\"false\",\"errMsg\":\"主表添加失败"+String.valueOf(sendOrderResultMap.get("returnMsg"))+"\"}";
						}else{
							return "{\"state\":\"false\",\"errMsg\":\"明细表添加失败"+String.valueOf(sendOrderResultMap.get("returnMsg"))+"\"}";
						}
					}
				}
		        }
		        return "{\"state\":\"true\",\"pageUrl\":\"\"}";
			}else{
				return "{\"state\":\"false\",\"pageUrl\":\"\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"state\":\"false\",\"errMsg\":\"发送失败 数据库异常 请联系管理员! 方法 auditOrderMain\"}";
		}
			
	}
	
	/**
	 * 获取订单明细信息
	 * @param mapVo
	 * @return
	 * @throws Exception 
	 */
	public String getOrderDetail(Map<String, Object> mapVo) throws Exception{
		/*
		 * {
		 *	"accessToken":"令牌",
		 *	"currentPageNumber":"当前页码",
		 *	"departmentID":"医疗机构采购部门编号",
		 *	"list":[
		 *	"省平台订单明细编号 1",
		 *	"省平台订单明细编号 2",
		 *	"省平台订单明细编号 3",
		 *	"省平台订单明细编号 4",
		 *	"省平台订单明细编号 5"
		 *	]
		 * }
		 */
		//获取订单明细数据
		ServerUrl su=new ServerUrl();
		String url = su.getProperty("chdsup.ip", "chdsup.H002");//参看 HRP系统的 serverUrl.properties属性文件
		List<String> orderDetailIDList= zjMapper.getPlatformOrderDetailId(mapVo);
		Map<String, String> getOrderMap=new HashMap<String, String>();
		getOrderMap.put("list", JSON.toJSONString(orderDetailIDList));
	    String getOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(getOrderMap));	
		return getOrderResultStr;
	}
	
	/**
	 * 获取订单明细信息(撤销)
	 * @param mapVo
	 * @return
	 * @throws Exception 
	 */
	public String getOrderDetailRevoke(Map<String, Object> mapVo) throws Exception{
		/*
		 * {
		 *	"accessToken":"令牌",
		 *	"currentPageNumber":"当前页码",
		 *	"departmentID":"医疗机构采购部门编号",
		 *	"list":[
		 *	"省平台订单明细编号 1",
		 *	"省平台订单明细编号 2",
		 *	"省平台订单明细编号 3",
		 *	"省平台订单明细编号 4",
		 *	"省平台订单明细编号 5"
		 *	]
		 * }
		 */
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();//把从省平台返回的数据只要符合状态为 08的单子都保存到这个容器里
		Map<String, Object> revokeOrderMap=new HashMap<String, Object>();//省平台返回的状态明细
		
			//获取订单明细数据
			ServerUrl su=new ServerUrl();
			String url = su.getProperty("chdsup.ip", "chdsup.H002");//参看 HRP系统的 serverUrl.properties属性文件
			List<String> orderDetailIDList= zjMapper.getPlatformOrderDetailId(mapVo);//获取明细id,在省平台发送之后会在这个表里保存数据,从这个表里获取明细ID
			Map<String, String> getOrderMap=new HashMap<String, String>();
			getOrderMap.put("currentPageNumber", "1");//后期需要通过计算来获取页码,如计算省平台一共能传过来多少条数据,在看看咱们系统有多少明细数据,他们相除算出页码
			//还少一个采购部门,现在先不用填,不填默认第一个
			getOrderMap.put("list", JSON.toJSONString(orderDetailIDList));
		    String getOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(getOrderMap));	
		    Map revokeOrderReturnMap = JSONObject.parseObject(getOrderResultStr, Map.class);
		    
		    if ("1".equals(String.valueOf(revokeOrderReturnMap.get("returnCode")))) {//返回的结果是1.往下走,不是直接提示报错
		    	List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(revokeOrderReturnMap.get("successList")), List.class);
		    	//把省平台已经撤销的数据订单id抓去出来,后期可能会把明细单id也抓取出来.现在只抓取从省平台返回的订单id,
		    	for (Map<String, Object> map : successList) {
					if("9".equals(map.get("orderDetailState"))){
						revokeOrderMap.put("platform_order_id", map.get("orderId").toString());
						revokeOrderMap.put("group_id", mapVo.get("group_id"));
						revokeOrderMap.put("hos_id", mapVo.get("hos_id"));
						revokeOrderMap.put("copy_code", mapVo.get("copy_code"));
						//revokeOrderMap.put("platform_detail_id", map.get("orderDetailID").toString());
					}else{
						return "{\"error\":\"该订单,不能撤销操作\",\"state\":\"false\"}";
					}
					list.add(revokeOrderMap);
				}
		    	matOrderAuditMapper.updateState(list);//改变订单状态规定9 是已经撤销
		    	
		    	return "{\"msg\":\"撤销成功.\",\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"errMsg\":\"订单省平台撤销失败"+String.valueOf(revokeOrderReturnMap.get("returnMsg"))+"\"}";
			}
		
	}
	
	

	/** 
	 * 省平台材料对应关系    查询
	*/
	public String queryMatInvRela(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){	
			List<Map<String, String>> list = zjMapper.queryMatInvRela(entityMap);
			return ChdJson.toJson(list);	
		}else{	
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());	
			List<Map<String, String>> list =  zjMapper.queryMatInvRela(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());			
		}		
		 
	}
	
	/**
	 * 省平台材料对应关系    添加
 	 */
	public String addMatInvRela(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			List<Map<String, Object>> invRela=zjMapper.queryExistsInvRelaByGoodsId(entityMap);
			if (invRela.size()==0) {
				List<Map<String, Object>> invList = zjMapper.queryInvCode(entityMap);  
				String inv_id = "";
				for(Map<String, Object> ids : invList){
					inv_id = ids.get("inv_id").toString();
				}
				
				//通过goodsid查询  材料关系表中所需的省平台的数据
				Map<String,Object> hospitalSpt = zjMapper.queryMatInvRelaSptByGoodsId(entityMap);
				entityMap.put("inv_id", inv_id) ;
				entityMap.put("hospitalid", hospitalSpt.get("hospitalid")) ;
				entityMap.put("departmentid", hospitalSpt.get("departmentid")) ;
				entityMap.put("sortname", hospitalSpt.get("sortname")) ;
				entityMap.put("productnamefirst", hospitalSpt.get("productnamefirst")) ;
				entityMap.put("productnamesecond", hospitalSpt.get("productnamesecond")) ;
				entityMap.put("goodsname", hospitalSpt.get("goodsname")) ;
				if (hospitalSpt.get("outlookc")==null) {
					entityMap.put("outlookc", "") ;
				}else{
					entityMap.put("outlookc", hospitalSpt.get("outlookc")) ;
				}
				if (hospitalSpt.get("goodstype")==null) {
					entityMap.put("goodstype", "") ;
				}else{
					entityMap.put("goodstype", hospitalSpt.get("goodstype")) ;
				}
				entityMap.put("unit", hospitalSpt.get("unit")) ;
				entityMap.put("regcodename", hospitalSpt.get("regcodename")) ;
				entityMap.put("brand", hospitalSpt.get("brand")) ;
				entityMap.put("source", hospitalSpt.get("source")) ;
				entityMap.put("purchaseprice", hospitalSpt.get("purchaseprice")) ;
				entityMap.put("addtime", hospitalSpt.get("addtime")) ;
				entityMap.put("lastupdatetime", hospitalSpt.get("lastupdatetime")) ;
				entityMap.put("companyidps", hospitalSpt.get("companyidps")) ;
				entityMap.put("companynameps", hospitalSpt.get("companynameps")) ;
				System.out.println("++"+entityMap);
				int state = zjMapper.addMatInvRela(entityMap);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else {
				return "{\"msg\":\"该hrp材料已存在相应省平台材料,无法继续添加\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! \"}";

		}
		
	}
	
	/**
	 * 省平台材料对应关系   更新
	 */
	public String updateMatInvRela(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			List<Map<String, Object>> invRela=zjMapper.queryExistsInvRelaByGoodsId(entityMap);
			if (invRela.size()==0) {
				//通过goodsid查询  材料关系表中所需的省平台的数据
				Map<String,Object> hospitalSpt = zjMapper.queryMatInvRelaSptByGoodsId(entityMap);
				
				entityMap.put("hospitalid", hospitalSpt.get("hospitalid")) ;
				entityMap.put("departmentid", hospitalSpt.get("departmentid")) ;
				entityMap.put("sortname", hospitalSpt.get("sortname")) ;
				entityMap.put("productnamefirst", hospitalSpt.get("productnamefirst")) ;
				entityMap.put("productnamesecond", hospitalSpt.get("productnamesecond")) ;
				entityMap.put("goodsname", hospitalSpt.get("goodsname")) ;
				entityMap.put("outlookc", hospitalSpt.get("outlookc")) ;
				entityMap.put("goodstype", hospitalSpt.get("goodstype")) ;
				entityMap.put("unit", hospitalSpt.get("unit")) ;
				entityMap.put("regcodename", hospitalSpt.get("regcodename")) ;
				entityMap.put("brand", hospitalSpt.get("brand")) ;
				entityMap.put("source", hospitalSpt.get("source")) ;
				entityMap.put("purchaseprice", hospitalSpt.get("purchaseprice")) ;
				entityMap.put("addtime", hospitalSpt.get("addtime")) ;
				entityMap.put("lastupdatetime", hospitalSpt.get("lastupdatetime")) ;
				entityMap.put("companyidps", hospitalSpt.get("companyidps")) ;
				entityMap.put("companynameps", hospitalSpt.get("companynameps")) ;
				
				int state = zjMapper.updateMatInvRela(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"该hrp材料已存在相应省平台材料,无法继续添加\",\"state\":\"false\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatLocationType\"}";

		}	
		
	}
	
	/**
	 * 省平台材料对应关系    删除
	 */
    public String deleteBatchMatInvRela(List<Map<String, Object>> entityList) throws DataAccessException {
    	
    try {
			
			int state = zjMapper.deleteBatchMatInvRela(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatLocationType\"}";

		}	 
    
  }
    
    /**
	 * 省平台材料对应关系    通过编码查询
	 */
    
    public Map<String,Object> queryMatInvRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return zjMapper.queryMatInvRelaByCode(entityMap);
	}
    
    /**
	 * 省平台材料对应关系    HRP材料 下拉框查询数据
	 */
	public String queryMatInvHrpBySelect(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBounds;
		}
		return JSON.toJSONString(zjMapper.queryMatInvHrpBySelect(mapVo, rowBounds));
	}

	/**
	 * 省平台材料对应关系    省平台 材料 下拉框查询数据
	 */
	public String queryMatInvSptBySelect(Map<String, Object> mapVo) {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}else{
			 rowBounds = rowBounds;
		}
		return JSON.toJSONString(zjMapper.queryMatInvSptBySelect(mapVo, rowBounds));
	}


	public String getDelivery(Map<String, Object> mapVo) throws Exception {
		//获取订单对应的省平台订单明细
		List<String> platformOrderDetailIdList = zjMapper.getPlatformOrderDetailId(mapVo);
		/*
		 * 参数
		 * { 
		 *  "list":"订单明细编号，多个编号以逗号分隔",
		 *  "departmentID":"医疗机构采购部门编号",//现阶段不需要填,默认已医院部门列表第一个
		 * }
		 */
		//访问参数
		Map<String, Object> getDeliveryMap=new HashMap<String, Object>();
		getDeliveryMap.put("list", JSON.toJSONString(platformOrderDetailIdList));
		
		if (mapVo.get("flag")!=null) {
			getDeliveryMap.put("flag", mapVo.get("flag"));
		}
		//获取平台访问路径
		ServerUrl su=new ServerUrl();
		String url=su.getProperty("chdsup.ip", "chdsup.H004");
		String returnDeliveryStr = HttpClientTool.doPost(url, JSON.toJSONString(getDeliveryMap));
		Map<String, Object> returnDeliveryMap=JSONObject.parseObject(returnDeliveryStr,Map.class);
		//若果returnCode返回值为2时说明是token过期,应该重新获取
		if ("2".equals(String.valueOf(returnDeliveryMap.get("returnCode")))) {
			mapVo.put("flag", "1");
			return getDelivery(mapVo);
		}else if ("1".equals(String.valueOf(returnDeliveryMap.get("returnCode")))) {
			List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(returnDeliveryMap.get("successList")),List.class);
			//配送明细获取成功,将获取信息更新到订单对应关系表相应字段中
			if (successList.size()>0) {
				zjMapper.updateDeliveryMsg(successList);
				//更新hrp订单的is_notice状态为3 代表着该订单已经获取过配送单了,无法重新获取,只能查看订单的内容
				//更新hrp订单is_notice状态
				List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
				mapVo.put("is_notice","3");//省平台发送过的订单 is_notice值为2,已经获取省平台配送信息的订单is_notice值为3
				list.add(mapVo);
				matOrderAuditMapper.sendOutOrderMain(list);
				return "{\"state\":\"true\"}";
			}else{
				return "{\"state\":\"false\",\"errMsg\":\"暂无配送数据\"}";
			}
		}else{
			return "{\"state\":\"false\",\"errMsg\":\""+String.valueOf(returnDeliveryMap.get("returnMsg"))+"\"}";
		}
		
	}
	/**
	 * 查询已经获取过省平台配送信息的从数据库查询相应数据
	 * @param mapVo
	 * @return
	 */
	public String queryPlatformDelivery(Map<String, Object> mapVo) {
		//查没有配送数量-入库数量不等于0的说明没有入库,或者入库不完全
		//List<Map<String,Object>> inOrders = zjMapper.queryInOrder(mapVo);
		//if(inOrders.size() >0){ 
			//mapVo.put("list", inOrders);
		//}
		List<Map<String, Object>> list = zjMapper.queryPlatformDeliveryDis(mapVo);
		return ChdJson.toJson(list);
	}
	/**
	 * 材料入库 包括hrp入库和省平台入库
	 * 
	 * liusiqi 18-06-12
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	public String createInByPlatfromOrder(Map<String, Object> mapVo)  throws Exception, IOException { 
		 
		JSONArray json = JSONArray.parseArray((String)mapVo.get("page_details"));
		Iterator it1 = json.iterator();
		//List<String> orderDetailIDList = new ArrayList<String>();
		List<Map<String,Object>> orderDetailIDList1 = new ArrayList<Map<String,Object>>();
		while (it1.hasNext()) {
			Map<String,Object> detailMap = new HashMap<String,Object>();
			JSONObject jsonObj = JSONObject.parseObject(it1.next().toString());
			String order_id  = jsonObj.get("order_id").toString();
			String platform_detail_id  = jsonObj.get("platform_detail_id").toString();
			String order_detail_id  = jsonObj.get("order_detail_id").toString();
			String distribution_id  = jsonObj.get("distribution_id").toString();
			//orderDetailIDList.add(platform_detail_id);
			detailMap.put("order_id", order_id);
			detailMap.put("order_detail_id", order_detail_id);
			detailMap.put("platform_detail_id", platform_detail_id);
			detailMap.put("distribution_id", distribution_id);
			orderDetailIDList1.add(detailMap);//为下面查询出详细明细用
		}
		/*//获取配送信息保存到关系表里
		ServerUrl su=new ServerUrl();
		String url = su.getProperty("chdsup.ip", "chdsup.H004");//参看 HRP系统的 serverUrl.properties属性文件
		//List<String> orderDetailIDList= zjMapper.getPlatformOrderDetailId(mapVo);
		Map<String, String> getOrderMap=new HashMap<String, String>();
		getOrderMap.put("currentPageNumber", "1");//后期需要通过计算来获取页码,如计算省平台一共能传过来多少条数据,在看看咱们系统有多少明细数据,他们相除算出页码
		//还少一个采购部门,现在先不用填,不填默认第一个
		getOrderMap.put("list", JSON.toJSONString(orderDetailIDList));
		String getOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(getOrderMap));
		Map orderReturnMap = JSONObject.parseObject(getOrderResultStr, Map.class);
		if ("1".equals(String.valueOf(orderReturnMap.get("returnCode")))) {
			List<Map<String, Object>> successList = JSONObject.parseObject(String.valueOf(orderReturnMap.get("successList")), List.class);
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : successList) {
				Map<String,Object> orderMap = new HashMap<String,Object>();
				//if("8".equals(map.get("orderDetailState"))){
					//获取配送订单
					//url = su.getProperty("chdsup.ip", "chdsup.H004");
					//Map<String, String> submitOrderMap=new HashMap<String, String>();
					//String submitOrderReturnStr = HttpClientTool.doPost(url, JSON.toJSONString(submitOrderMap));
					//Map submitOrderReturnMap = JSONObject.parseObject(submitOrderReturnStr, Map.class);
					//if ("1".equals(String.valueOf(submitOrderReturnMap.get("returnCode")))) {
						//List<Map<String, Object>> successList1 = JSONObject.parseObject(String.valueOf(submitOrderReturnMap.get("successList")), List.class);
						  
						  String orderDetailID = map.get("orderDetailID").toString();//订单明细编码
						  String distributionSerialID = map.get("distributionSerialID").toString();//配送明细编码
						  String invoiceID = map.get("invoiceID").toString();//发票号
						  String batchRecordID = map.get("batchRecordID").toString();//批号
						  String distributeCount = map.get("distributeCount").toString();//[配送数量
						  String distributeTime = map.get("distributeTime").toString();//[配送时间
						  String warehouseCount = map.get("warehouseCount").toString();//入库数量
						  String warehouseTime = map.get("warehouseTime").toString();//入库时间
						  
						  orderMap.put("orderDetailID", orderDetailID);
						  orderMap.put("distributionSerialID", distributionSerialID);
						  orderMap.put("invoiceID", invoiceID);
						  orderMap.put("batchRecordID", batchRecordID);
						  orderMap.put("distributeCount", distributeCount);
						  orderMap.put("distributeTime", distributeTime);
						  orderMap.put("warehouseCount", warehouseCount);
						  orderMap.put("warehouseTime", warehouseTime);
						  list.add(orderMap);
					//}else{
						//return "{\"state\":\"false\",\"errMsg\":\"订单省平台提交失败"+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
					//}
				//}else{
				//	return "{\"msg\":\"该订单,没有收到货物\",\"state\":\"false\"}";
				//}
			}
			zjMapper.updateDeliveryMsg(list);
		}else{
			//note 值为 orderMain 说明主表添加失败,值为orderDetail 说明明细表添加失败
			if("orderMain".equals(String.valueOf(orderReturnMap.get("note")))){
				return "{\"state\":\"false\",\"errMsg\":\"主表添加失败"+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
			}else{
				return "{\"state\":\"false\",\"errMsg\":\"明细表添加失败"+String.valueOf(orderReturnMap.get("returnMsg"))+"\"}";
			}
		}*/
		
		
	/***************************************************************************************************************************/
		
		
		
		//查询订单明细,其中amount为省平台入库数量,单价为省平台材料单间,金额为俩值相乘
		mapVo.put("list", orderDetailIDList1);
		List<Map<String, Object>> orderDetailList = zjMapper.queryOrderDetailForIn(mapVo);
		//从页面获取入库数量,因为要从数据库获取材料的其他信息,所以,只替换入库数量,和入库金额,其余信息还是从数据库中查询获取
		JSONArray page_details = JSONObject.parseArray(String.valueOf(mapVo.get("page_details")));
		Iterator<Object> detailIt = page_details.iterator();
		while (detailIt.hasNext()) {
			Map<String, String> page_detail = (Map<String, String>) detailIt.next(); 
			for (Map<String, Object> map : orderDetailList) {
				if (String.valueOf(map.get("distributionserialid")).equals(page_detail.get("distributionserialid"))) {
					map.put("amount", page_detail.get("warehousecount"));
					map.put("order_amount", page_detail.get("warehousecount"));
				}
			}
			
		}
		System.out.println("++++orderDetailList"+orderDetailList); 
		//拼装hrp入库明细数据
		mapVo.put("detailData",JSON.toJSONString(orderDetailList));
		//拼装省平台入库明细信息
		List<Map<String, Object>> platformInDetailList=new ArrayList<Map<String,Object>>();
		Set<String> bill_no_set=new HashSet<String>();
		for (Map<String, Object> map : orderDetailList) {
			Map<String, Object> platformInDetailMap=new HashMap<String, Object>();
			platformInDetailMap.put("distributionSerialID", map.get("distributionserialid"));
			platformInDetailMap.put("invoiceid", map.get("invoiceid"));
			platformInDetailMap.put("batchRecordID", map.get("batch_no"));
			platformInDetailMap.put("warehouseCount", map.get("amount"));
			bill_no_set.add(String.valueOf(map.get("batchrecord_id")));
			platformInDetailList.add(platformInDetailMap);
		}
		//处理hrp入库主表bill_no 发票号
		Iterator<String> it = bill_no_set.iterator();
		String bill_no="";
		while(it.hasNext()){
			bill_no+=it.next()+"|";
		}
		if (bill_no.length()>0) {
			mapVo.put("bill_no",bill_no.substring(0,bill_no.length()-1));
		}
		
		//hrp生成入库单
		String hrpInMsg = addInByOrder(mapVo);	 
		if (hrpInMsg.contains("error")) {
			return hrpInMsg;
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		/*//省平台入库
		Map<String, Object> sendMatInMap=new HashMap<String, Object>();
		sendMatInMap.put("list", JSON.toJSONString(platformInDetailList));
		String platformMatInMsg = platformMatIn(sendMatInMap);
		if (platformMatInMsg.contains("false")) {
			return platformMatInMsg;
		}else{
			 return hrpInMsg;
		}*/
	}


	/**
	 * 省平台生成入库
	 * @param sendMatInMap
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	private String platformMatIn(Map<String, Object> sendMatInMap)
			throws Exception, IOException {
		ServerUrl su=new ServerUrl();
		String url=su.getProperty("chdsup.ip", "chdsup.H005");
		String returnMatInStr = HttpClientTool.doPost(url,JSON.toJSONString(sendMatInMap) );
		Map returnMatInMap = JSONObject.parseObject(returnMatInStr, Map.class);
		if ("1".equals(String.valueOf(returnMatInMap.get("returnCode")))) {
		    return "{\"state\":\"true\"}";
		}else if("2".equals(String.valueOf(returnMatInMap.get("returnCode")))){
			sendMatInMap.put("flag", "1");
			return queryPlatformDelivery(sendMatInMap);
		}else{
			return "{\"state\":\"false\",\"errMsg\":\"省平台入库失败"+String.valueOf(returnMatInMap.get("returnMsg"))+"\"}";
		}
	}
	
 
	/**
	 * 获取退货明细
	 * @param mapVo
	 * @return
	 * @throws Exception 
	 */
	public String getMatBackDetail(Map<String, Object> mapVo) throws Exception{
		
		/*
		 * {
		 *	"accessToken":"令牌",
		 *	"currentPageNumber":"当前页码",
		 *	"departmentID":"医疗机构采购部门编号",
		 *  "beginTime":"开始时间,非必填(包含该时间点)",  //格式为 yyyy-MM-dd HH:mm:ss
		 *	"endTime":"结束时间,非必填(不包含该时间点)",  //起始时间和终止时间相差时间不能超过10天
		 *	"list":[
		 *	"配送明细编号【distributionSerialID】 1",
		 *	"配送明细编号【distributionSerialID】 2",
		 *	"配送明细编号【distributionSerialID】 3", 
		 *	]
		 * }
		 */
		//获取订单明细数据
		ServerUrl su=new ServerUrl();
		String url = su.getProperty("chdsup.ip", "chdsup.H009");//参看 HRP系统的 serverUrl.properties属性文件
		 
		//获取 zi_order_map中所有的配送单明细
		List<String> distributionList= zjMapper.getPlatformDistributionByAll(mapVo);
		 
		if(distributionList.size()<=0){
			return "{\"error\":\"没有配送单明细编号进行插入！\"}";
		}
			
		Map<String, String> getDisMap=new HashMap<String, String>();
		
		getDisMap.put("list", JSON.toJSONString(distributionList));
		
		int currentPage  = 1;
	     
		 while (true) {
	              
			 getDisMap.put("currentPageNumber",  String.valueOf(currentPage) );  
			 
	            String getDisResultStr = HttpClientTool.doPost(url, JSON.toJSONString(getDisMap));	
	            
	            Map<String, Object> responseDisMap = JSONObject.parseObject(getDisResultStr,Map.class);
  
	            if (responseDisMap.get("returnCode")!=null && "1".equals(responseDisMap.get("returnCode").toString())) {
	            	
	            	List<Map> totalList = new ArrayList<Map>();
	             
	            	if (responseDisMap.get("successList")!=null && StringUtils.isNotBlank(responseDisMap.get("successList").toString())) {
	            		totalList=JSONArray.parseArray(responseDisMap.get("successList").toString(), Map.class);
	            		if(totalList.size() == 0){
	            			return "{\"error\":\"获取失败，没有返回结果集.\",\"state\":\"true\"}";
	            		}
	            		System.out.println("+++totalList"+totalList);
	            		zjMapper.insertMatBackResult(totalList);
	            		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	            	} 
	            } 
	            
	            currentPage ++; 
	            if(currentPage == Integer.parseInt(responseDisMap.get("totalPageCount").toString())){
	            	break;
	            }
	        }
		 
		 return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		
	}
	
 
	public String  sendMatBack(Map<String, Object> mapVo)throws Exception{	
		//拼接参数
		/*
		 * {
		 *	"accessToken":"令牌",
		 *	"departmentID":"医疗机构采购部门编号",
		 *	"list":[
		 *	"hospitalReturnId":"医院退货明细主键(医院系统退货明细主键)1",
		 *	"distributionSerialID":"配送明细编号",
         *  "returnCount":"退货数量",
         *  "returnReason":"退货原因",
         *  "returnCustomInfo":"自定义退货信息"
		 *	]
		 * }
		 */
		//获取订单明细数据
		try {
			
			StringBuffer errMsg=new StringBuffer();//错误信息
			
            List<Map<String,Object>> sendBackMapList = new ArrayList<Map<String,Object>>();//拼接LIST
            
			Map<String,Object> sendBackMap=new HashMap<String, Object>();//省平台订单数据
			
			//获取退货单明细数据
   			List<Map<String,Object>> matBackDetailMap= zjMapper.queryMatStorageBackDetailById(mapVo);
   			
   			for (Map<String, Object> map : matBackDetailMap) {
   				
				if (null == map.get("goodsid")) {
					
					errMsg.append("物资材料:  ");
					
					errMsg.append(map.get("inv_code")).append(map.get("inv_name")).append(",");
					
					break;
				}
				
			}
   			
   			if (StringUtils.isNotBlank(errMsg.toString())) {
   				
				return "{\"state\":\"false\",\"errMsg\":\""+errMsg.toString().substring(0,errMsg.toString().length()-1)+"未做省平台对应关系,此订单无法发送\"}";
			
   			}else {
				
   				for (Map<String,Object> DetailMap :matBackDetailMap ) {
   					
   					Map<String,Object> map=new HashMap<String, Object>();

   					map.put("hospitalReturnId", DetailMap.get("in_detail_id").toString());
   					
   					map.put("distributionSerialID", DetailMap.get("distributionserialid").toString());
   					
   					map.put("returnCount", DetailMap.get("amount").toString());

   					map.put("returnReason", DetailMap.get("note"));
   					
   					map.put("returnCustomInfo", "测试");
   					
   					sendBackMapList.add(map);
   				}

   	   			sendBackMap.put("departmentID", "");
   	   			
   	   			sendBackMap.put("list",JSON.toJSONString( sendBackMapList));
   	   			
   	   			ServerUrl su=new ServerUrl();
   	   			
   	   			String url=su.getProperty("chdsup.ip", "chdsup.H008");
   	   			
   	   			System.out.println("sendBackMap = " + sendBackMap);
   	   		    //执行方法
   	   			String sendMatBackResultStr = HttpClientTool.doPost(url, JSON.toJSONString(sendBackMap));	
   	   			//获取返回值
   			    @SuppressWarnings("unchecked")
   				Map<String, Object> sendMatBackResultMap = JSONObject.parseObject(sendMatBackResultStr, Map.class);
   			    System.out.println(sendMatBackResultMap);
   			    if ("1".equals(String.valueOf(sendMatBackResultMap.get("returnCode")))) {   
   			    	
   			    	
   			    	
   			    	
   			    	
   			    	
   			    	
   					return "{\"msg\":\""+sendMatBackResultMap.get("returnMsg")+".\",\"state\":\"true\"}";
   					
   				}else{
   					
   					return "{\"error\":\""+sendMatBackResultMap.get("returnMsg")+".\",\"errMsg\":\"false\"}";
   					
   				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			return "{\"state\":\"false\",\"errMsg\":\"发送失败 数据库异常 请联系管理员! 方法 auditOrderMain\"}";
			
		}
		
	}

	
	/**
	 * 将hrp付款发送省平台
	 * 暂时只支持单张付款的发送
	 * liusiqi (2018-06-06)
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String sendpay(Map<String, Object> mapVo){
		try { 
			StringBuffer errMsg=new StringBuffer();//错误信息
			//获取hrp付款主表信息
			List<Map<String, Object>> matPayMainList = zjMapper.queryMatPayMainByCodeP(mapVo);
			if (matPayMainList.size()>0) {
				Map<String,String> sendPayMap=new HashMap<String, String>();//省平台订单数据
				Map<String, Object> matPayMainMap = matPayMainList.get(0);
				//通过订单id获取付款明细数据 (从zj_inv_map,zj_ps_map中获取订单省平台信息,用来组装省平台)
				List<Map<String, Object>> platformPayDetailList=zjMapper.queryMatPaydetailByCodeP(mapVo);
				
				//组装发送数据"hospitalOrderId ":" orderRemark ""companyId ":
				sendPayMap.put("hospitalOrderId", matPayMainMap.get("pay_id").toString());
				sendPayMap.put("companyId", matPayMainMap.get("companyId").toString());
				sendPayMap.put("orderRemark", "由院内系统付款单"+matPayMainMap.get("pay_bill_no").toString()+"生成");
				sendPayMap.put("list",JSON.toJSONString(platformPayDetailList));
				//发送订单
				ServerUrl su=new ServerUrl();
				String	url = su.getProperty("chdsup.ip", "chdsup.H007");//参看 HRP系统的 serverUrl.properties属性文件
			    String sendOrderResultStr = HttpClientTool.doPost(url, JSON.toJSONString(sendPayMap));	
				Map sendOrderResultMap = JSONObject.parseObject(sendOrderResultStr, Map.class);
				
				if ("1".equals(String.valueOf(sendOrderResultMap.get("returnCode")))) {
				    return "{\"state\":\"true\",\"pageUrl\":\"\"}";
				}
				else
				{
					//note 值为 orderMain 说明主表添加失败,值为orderDetail 说明明细表添加失败
					if("orderMain".equals(String.valueOf(sendOrderResultMap.get("note")))){
						return "{\"state\":\"false\",\"errMsg\":\"主表添加失败"+String.valueOf(sendOrderResultMap.get("returnMsg"))+"\"}";
					}else if ("orderDetail".equals(String.valueOf(sendOrderResultMap.get("note")))){
						return "{\"state\":\"false\",\"errMsg\":\"明细表添加失败"+String.valueOf(sendOrderResultMap.get("returnMsg"))+"\"}";
					}else{
						return "{\"state\":\"false\",\"errMsg\":\"提交失败"+String.valueOf(sendOrderResultMap.get("returnMsg"))+"\"}";
					}
				}
					
			}else{
				return "{\"state\":\"false\",\"errMsg\":\"省平台非唯一供应商\"}";
			}
	 		
			
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"state\":\"false\",\"errMsg\":\"发送失败 数据库异常 请联系管理员!\"}";
		}	
	}
	

	/**
	 * 
	 * hrp导入订单生成入库单
	 * 存入主表,明细表,省平台配送明细和入库明细对应关系表
	 * liusiqi 18-06-12
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Transactional(rollbackFor=Exception.class)
	public String addInByOrder(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				return "{\"error\":\"制单日期不能为空\",\"state\":\"false\"}";
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("in_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
				entityMap.put("bill_date",entityMap.get("in_date"));
			}
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成常备材料入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(entityMap);
				if(in_no.indexOf("error") > 0){
					return in_no;
				}
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
			//制单人
			entityMap.put("maker", SessionManager.getUserId());
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				double money_sum = 0;//记录明细总金额

				/*用于查询个体码----begin-----*/
				Map<String,Object> barCodeMap = new HashMap<String,Object>();
				barCodeMap.put("group_id", entityMap.get("group_id"));
				barCodeMap.put("hos_id", entityMap.get("hos_id"));
				barCodeMap.put("type_code", 1);
				String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
				//如果不存在则插入
				if(bar_code == null || "".equals(bar_code)){
					bar_code = "000000000000";
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.insertMatNoOther(barCodeMap);
				}
				String init_bar_code = bar_code;
				/*用于查询个体码----end-------*/
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
				System.out.println("++++"+json);
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						String cert_code=supDeliveryMainMapper.queryCertCode(jsonObj);
						detailMap.put("cert_code", cert_code);
						//取出明细表ID（自增序列）
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						detailMap.put("distributionserialid",  jsonObj.get("distribution_id"));//配送单id
						//detailMap.put("amount_money",  jsonObj.get("amount_money"));//金额
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString())));//金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no", jsonObj.get("disinfect_no"));//灭菌批号
						}
						
						//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no",  jsonObj.get("batch_no"));//批号
						}else{
							detailMap.put("batch_no", "-");//默认批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
							if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
								detailMap.put("batch_sn",  1);//批次
							}else{
								detailMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
							}
							invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailMap.get("batch_sn"))));
						}else{
							detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
							invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
						}
						/**********************自动生成批次-------end---------*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price",  "0");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_price")))){
							detailMap.put("allot_price",  jsonObj.get("allot_price"));//调拨价
						}else{
							detailMap.put("allot_price",  "0");//调拨价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_money")))){
							detailMap.put("allot_money",  jsonObj.get("allot_money"));//调拨金额
						}else{
							detailMap.put("allot_money",  "0");//调拨金额
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_code")))){
							detailMap.put("pack_code",  jsonObj.get("pack_code"));//包装单位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num_exchange")))){
							detailMap.put("num_exchange",  jsonObj.get("num_exchange"));//转换量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num")))){
							detailMap.put("num",  jsonObj.get("num"));//包装数量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_price")))){
							detailMap.put("pack_price",  jsonObj.get("pack_price"));//包装单价
						}else{
							detailMap.put("pack_price",  "0");//包装单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("is_per_bar")))){
							detailMap.put("is_per_bar",  jsonObj.get("is_per_bar"));//是否个体码
						}else{
							detailMap.put("is_per_bar", "0");//是否个体码(默认否)
						}
						//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
						if("0".equals(detailMap.get("is_per_bar").toString()) && ("0".equals(String.valueOf(jsonObj.get("is_highvalue"))) || "0".equals(String.valueOf(MyConfig.getSysPara("04010"))))){
							//System.out.println(jsonObj.get("inv_name").toString()+"不生成个体码");
							if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
								detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
							}else{
								detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
							}
							//该条明细数据添加到List中
							detailList.add(detailMap);
						}else{
							//根据一码一物规则自动拆分数量并生成个体码
							for(int i = 1; i <= Integer.parseInt(detailMap.get("amount").toString()); i++){  
								Map<String, Object> barMap = new HashMap<String, Object>();
								barMap.putAll(detailMap);
								//System.out.println(jsonObj.get("inv_name").toString()+"生成个体码");
								//System.out.println(bar_code);
								bar_code = matCommonService.getNextBar_code(bar_code);
								if(i > 1){
									barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
								}
								//拆分数量和金额
								barMap.put("amount",  1);//数量
								if(detailMap.get("num_exchange") != null){
									barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailMap.get("num_exchange").toString()));//包装件数
								}
								if(detailMap.get("num") != null){
									barMap.put("pack_price",  Float.parseFloat(detailMap.get("num").toString())*Float.parseFloat(detailMap.get("price").toString()));//包装件数
								}
								barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("price").toString()), money_para)));
								//barMap.put("amount_money",  Float.parseFloat(detailMap.get("amount_money").toString())/Float.parseFloat(detailMap.get("amount").toString()));//金额
								barMap.put("bar_code",  bar_code);//个体码
								//该条明细数据添加到List中
								detailList.add(barMap);
							}
						}
						/*订单关系-------begin--*/
						
							Map<String,Object> orderRelaMap = new HashMap<String,Object>();
							orderRelaMap.put("group_id", entityMap.get("group_id"));
							orderRelaMap.put("hos_id", entityMap.get("hos_id"));
							orderRelaMap.put("copy_code", entityMap.get("copy_code"));
							orderRelaMap.put("in_id", entityMap.get("in_id"));
							orderRelaMap.put("in_no", entityMap.get("in_no"));
							orderRelaMap.put("in_detail_id", detailMap.get("in_detail_id"));
							orderRelaMap.put("order_id", jsonObj.get("order_id"));
							orderRelaMap.put("order_detail_id", jsonObj.get("order_detail_id"));
							orderRelaMap.put("in_amount", Float.parseFloat(jsonObj.get("amount").toString()));
							if(jsonObj.containsKey("already_amount")){
								if(Float.parseFloat(jsonObj.get("already_amount").toString())>0){
									orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString())+Float.parseFloat(jsonObj.get("already_amount").toString()));
								}else{
									orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString()));
								}
							}else{
								orderRelaMap.put("order_amount", Float.parseFloat(jsonObj.get("order_amount").toString()));
							}
							
							
							orderRelaList.add(orderRelaMap);
							
						
						/*订单关系-------end--*/
					}
				}
				if(detailList.size() > 0){ 
					//更新个体码
					if(!init_bar_code.equals(bar_code)){
						barCodeMap.put("max_no", bar_code);
						matNoOtherMapper.updateMatNoOther(barCodeMap);
					}
					//新增入库明细和省平台配送明细对应关系表
					zjMapper.insertInDeliveryRela(detailList);
					//新增入库主表数据
					matInCommonMapper.addMatInMain(entityMap);
					//新增入库明细数据
					matInCommonMapper.addMatInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					matInCommonMapper.insertMatInResource(entityMap);
					//新增入库单订单关系表
					if(orderRelaList.size() > 0){
						matStorageInMapper.insertOrderRela(orderRelaList);
					}
					
				}else{
					return "{\"error\":\"订单材料未配送\",\"state\":\"false\"}";
				}
			}else{
				return "{\"error\":\"添加失败 改订单已生成过入库单,无剩余材料可生成\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatStorageIn\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("in_id").toString()+","
			+"\"}";
	}
	
	
	/**
	  * 批量修改页面查询HRP材料
	  * @param page
	  * @return
	  */
	 public String queryInvCode(Map<String, Object> entityMap) {
	  SysPage sysPage = new SysPage();

	  sysPage = (SysPage) entityMap.get("sysPage");

	  if (sysPage.getTotal() == -1) {

	   List<Map<String,Object>> list = zjMapper.queryInvCode(entityMap);

	   return ChdJson.toJson(list);

	  } else {

	   RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

	   List<Map<String,Object>> list = zjMapper.queryInvCode(entityMap, rowBounds);

	   PageInfo page = new PageInfo(list);

	   return ChdJson.toJson(list, page.getTotal());

	  }
	 }
	 
	 /**
	  * 批量修改页面查询省平台材料
	  * @param page
	  * @return
	  */
	 public String queryGoodsid(Map<String, Object> entityMap) {
	  SysPage sysPage = new SysPage();

	  sysPage = (SysPage) entityMap.get("sysPage");

	  if (sysPage.getTotal() == -1) {

	   List<Map<String,Object>> list = zjMapper.queryGoodsid(entityMap);

	   return ChdJson.toJson(list);

	  } else {

	   RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

	   List<Map<String,Object>> list = zjMapper.queryGoodsid(entityMap, rowBounds);

	   PageInfo page = new PageInfo(list);

	   return ChdJson.toJson(list, page.getTotal());

	  }
	 }
	 
	 public String addBatchInvRelaMaping(List<Map<String, Object>> dataAddedBatch) {
		  try {
		   
		   List<Map<String, Object>> invRela=zjMapper.queryExistsInvRelaByGoodsIdBatch(dataAddedBatch);  
		   if (invRela.size()==0) {
		    
		  
		   
		    for (int i = 0; i < dataAddedBatch.size(); i++) {
		    	
		    	  Map<String, Object> entityMap =new HashMap<String, Object>();
			    
			      entityMap.put("group_id", SessionManager.getGroupId());
			      entityMap.put("hos_id", SessionManager.getHosId());
			      entityMap.put("copy_code", SessionManager.getCopyCode());
			      entityMap.put("goodsid", dataAddedBatch.get(i).get("goodsid")) ;
			     
			     //通过goodsid查询  材料关系表中所需的省平台的数据
				  Map<String,Object> hospitalSpt = zjMapper.queryMatInvRelaSptByGoodsId(entityMap);  
			     entityMap.put("inv_id",  dataAddedBatch.get(i).get("inv_id"));
			     entityMap.put("hospitalid", hospitalSpt.get("hospitalid")) ;
			     entityMap.put("departmentid", hospitalSpt.get("departmentid")) ;
			     entityMap.put("sortname", hospitalSpt.get("sortname")) ;
			     entityMap.put("productnamefirst", hospitalSpt.get("productnamefirst")) ;
			     entityMap.put("productnamesecond", hospitalSpt.get("productnamesecond")) ;
			   
			     entityMap.put("goodsname", hospitalSpt.get("goodsname")) ;
			     if (hospitalSpt.get("outlookc")==null) {
			      entityMap.put("outlookc", "") ;
			     }else{
			      entityMap.put("outlookc", hospitalSpt.get("outlookc")) ;
			     }
			     if (hospitalSpt.get("goodstype")==null) {
			      entityMap.put("goodstype", "") ;
			     }else{
			      entityMap.put("goodstype", hospitalSpt.get("goodstype")) ;
			     }
			     entityMap.put("unit", hospitalSpt.get("unit")) ;
			     entityMap.put("regcodename", hospitalSpt.get("regcodename")) ;
			     entityMap.put("brand", hospitalSpt.get("brand")) ;
			     entityMap.put("source", hospitalSpt.get("source")) ;
			     entityMap.put("purchaseprice", hospitalSpt.get("purchaseprice")) ;
			     entityMap.put("addtime", hospitalSpt.get("addtime")) ;
			     entityMap.put("lastupdatetime", hospitalSpt.get("lastupdatetime")) ;
			     entityMap.put("companyidps", hospitalSpt.get("companyidps")) ;
			     entityMap.put("companynameps", hospitalSpt.get("companynameps")) ;
			     
			     int state = zjMapper.addMatInvRela(entityMap);
			     
		   
		    	
		    }
		     return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		    
		   }else {
		    return "{\"msg\":\"该hrp材料已存在相应省平台材料,无法继续添加\",\"state\":\"false\"}";
		   }
		  }
		  catch (Exception e) {
		   logger.error(e.getMessage(), e);
		   return "{\"error\":\"添加失败 数据库异常 请联系管理员! \"}";

		  }
		 }
 
}
