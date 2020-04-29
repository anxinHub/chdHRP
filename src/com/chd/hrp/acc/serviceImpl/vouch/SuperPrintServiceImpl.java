package com.chd.hrp.acc.serviceImpl.vouch;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberToCN;
import com.chd.hrp.acc.dao.vouch.SuperPrintMapper;
import com.chd.hrp.acc.entity.superVouch.SysPrintPara;
import com.chd.hrp.acc.entity.superVouch.SysPrintTemplate;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.ctc.wstx.util.DataUtil;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 凭证打印ServiceImpl 
 * @Author: Perry
 * @Version: 1.0
*/

@Service("superPrintService")
public class SuperPrintServiceImpl implements SuperPrintService{

	private final DecimalFormat myformat = new DecimalFormat("##,##0.00");
	private final Pattern patStrNum = Pattern.compile("[A-Z]+[0-9\\.]+");
	private final Pattern patNum = Pattern.compile("[^0-9]");
	private final Pattern patternAllNum = Pattern.compile("[0-9]*"); 
	private static Logger logger = Logger.getLogger(SuperPrintServiceImpl.class);
	@Resource(name = "superPrintMapper")
	private final SuperPrintMapper superPrintMapper = null;
	
	//查询系统参数，打印是否按用户权限控制
	@Override
	public String querySuperPrintAccPara(Map<String, Object> map)throws DataAccessException{
		//系统参数：打印模板参数：0统一打印、1按用户打印、2按仓库打印、3按供应商打印...
		String paraValue=superPrintMapper.querySuperPrintAccPara(map);
		return paraValue==null?"":paraValue;
	}
	
	//下拉框-取科目全称
	@Override
	public String querySubjNameAllDict(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return JSON.toJSONString(superPrintMapper.querySubjNameAllDict(mapVo));
	}

	//保存打印模板
	@Override
	public String saveSuperPrintTemplate(Map<String, Object> mapVo)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			

			//mapVo.put("content", mapVo.get("content").toString().getBytes("GBK"));
			int state=superPrintMapper.updateSuperPrintTemplate(mapVo);
			if(state==0){
				mapVo.put("print_count", 0);
				superPrintMapper.insertSuperPrintTemplate(mapVo);
			}
			
			//保存打印参数
			if(mapVo.get("para_value")!=null){
				List<Map<String, Object>> liMap=new ArrayList<Map<String, Object>>();
				Map<String, Object> mP=null;
				String[] p=mapVo.get("para_value").toString().split("#");
				if(p.length>0 && !p.toString().equals("")){
					for(String s:p){
						if(s.split("@").length<2){
							break;
						}
						mP=new HashMap<String, Object>();
						mP.put("mod_code",mapVo.get("mod_code"));
						mP.put("template_code",mapVo.get("template_code"));
						mP.put("group_id",mapVo.get("group_id"));
						mP.put("hos_id",mapVo.get("hos_id"));
						mP.put("copy_code",mapVo.get("copy_code"));
						mP.put("use_id",mapVo.get("use_id"));
						mP.put("para_code", s.split("@")[0]);
						mP.put("para_value", s.split("@")[1].replace(" ", ""));
						liMap.add(mP);
					}
					if(liMap!=null && liMap.size()>0){
						superPrintMapper.updateSuperPrintPara(liMap);
					}
				}
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
	
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
	}


	
	//查询打印模板
	@Override
	public SysPrintTemplate querySuperPrintTemplateByCode(Map<String, Object> mapVo)throws DataAccessException {
		// TODO Auto-generated method stub
		return superPrintMapper.querySuperPrintTemplateByCode(mapVo);
	}
	
	
	//打印模板设置页面，查询打印模板参数
	@Override
	public String querySuperPrintParaByListJosn(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			//初始化打印参数
			superPrintMapper.copySuperPrintParaByCode(map);
			
			return ChdJson.toJson(superPrintMapper.querySuperPrintParaByCode(map));
		} catch (Exception e) {
			// TODO: handle exception
			throw new SysException(e.getMessage());
		}
		
	}
	
	//业务调用页面，根据系统参数（打印是否按用户设置模板），查询打印模板参数
	@Override
	public String querySuperPrintParaByCode(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			return ChdJson.toJson(superPrintMapper.querySuperPrintParaByCode(map));
		} catch (Exception e) {
			// TODO: handle exception
			throw new SysException(e.getMessage());
		}
		
	}
	
	//查询打印模板参数，返回Map，解析模板用
	@Override
	public Map<String,String> querySuperPrintParaByList(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<SysPrintPara> paraList=new ArrayList<SysPrintPara>();
		paraList=superPrintMapper.querySuperPrintParaByCode(map);
		Map<String,String> paraMap=new HashMap<String,String>();
		int index=0;
		int maxRowIndex=0;//最大行号
		if(paraList!=null && paraList.size()>0){
			for(SysPrintPara para:paraList){
				if(index==0){
					//第一个打印参数的值唯一，可以是单据号和ID，用来更新打印次数
					paraMap.put("999999", para.getPara_code().toLowerCase());
				}
				index++;
				
				int flag=para.getFlag();
				
				if(flag==5){
					//主表和金额大写标识
					paraMap.put(para.getPara_code().toLowerCase(), para.getPara_value() == null ? null : para.getPara_value()+","+para.getFlag());
				}else{
					paraMap.put(para.getPara_code().toLowerCase(), para.getPara_value());
				}
				
				//取最大行号
				if(para.getPara_value()!=null && (flag==1 || flag==5 || flag==8 || flag==10)){
					if(para.getPara_value().indexOf(",")!=-1){
						int row=Integer.parseInt(para.getPara_value().split(",")[0]);
						if(row>maxRowIndex){
							maxRowIndex=row;
						}
					}else{
						 
					   //判断为数字
					   Matcher isNum = patternAllNum.matcher(para.getPara_value());
					   if(isNum.matches()){
						   int row=Integer.parseInt(para.getPara_value());
						   if(row>maxRowIndex){
								maxRowIndex=row;
						   }
					   } 
							
					}
				}
				
			}
			paraMap.put("maxRowIndex", String.valueOf(maxRowIndex));
		}
		
		return paraMap;
	}
	
	//查询打印模板参数，返回Map，PageOffice使用
	@Override
	public Map<String,String> querySuperPrintParaByFlag(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		List<SysPrintPara> paraList=new ArrayList<SysPrintPara>();
		paraList=superPrintMapper.querySuperPrintParaByCode(map);
		Map<String,String> paraMap=new HashMap<String,String>();
		int index=0;
		int maxRowIndex=0;//最大行号
		if(paraList!=null && paraList.size()>0){
			
			for(SysPrintPara para:paraList){
				if(index==0){
					//第一个打印参数的值唯一，可以是单据号和ID，用来更新打印次数
					paraMap.put("999999", para.getPara_code().toLowerCase());//特殊字段
				}
				index++;
				
				int flag=para.getFlag();
				
				if(para.getPara_value()!=null && !para.getPara_value().equals("")){
					if(flag==1 || flag==2 || flag==5){
						paraMap.put(para.getPara_code().toLowerCase(), para.getPara_value()+"@"+flag);
					}else{
						paraMap.put(para.getPara_code().toLowerCase(), para.getPara_value());
					}
				}
				
				//取最大行号
				if(para.getPara_value()!=null && (flag==1 || flag==5 || flag==8 || flag==10)){
					if(para.getPara_value().indexOf(",")!=-1){
						int row=Integer.parseInt(para.getPara_value().split(",")[0]);
						if(row>maxRowIndex){
							maxRowIndex=row;
						}
					}else{
						 
					   //判断为数字
					   Matcher isNum = patternAllNum.matcher(para.getPara_value());
					   if(isNum.matches()){
						   int row=Integer.parseInt(para.getPara_value());
						   if(row>maxRowIndex){
								maxRowIndex=row;
						   }
					   } 
							
					}
				}
				
			}
			paraMap.put("maxRowIndex", String.valueOf(maxRowIndex));//特殊字段
		}
		
		return paraMap;
	}
	
	//查询打印模板内容(打印模板设置页面)
	@Override
	public String querySuperPrintContentByCode(Map<String, Object> mapVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		String content=null;
		
		try {
			//查询用户模板
			content=superPrintMapper.querySuperPrintContentByCode(mapVo);
			
		} catch (Exception e) {
				// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		return content==null?"{}":content;
	}
	
	/*
	 * 此方法停用
	 * 根据打印参数编码查询参数值,直接从paraList里面检索(non-Javadoc)
	 * @see com.chd.hrp.acc.service.vouch.SuperPrintService#getSuperPrintParaValue(java.util.List, java.lang.String)
	 */
	@Override
	public String getSuperPrintParaValue(List<SysPrintPara> paraList,String paraCode)
			throws DataAccessException {
		// TODO Auto-generated method stub
		if(paraList!=null && paraList.size()>0){
			for(SysPrintPara m :paraList){
				if(m.getPara_code().equalsIgnoreCase(paraCode)){
					return m.getPara_value();
				}
			}
		}
//		else{
//			throw new SysException("没有定义打印参数！");
//		}
		return null;
	}

	
	@Override
	/**此方法停用
	 * printMap:根据打印模板填充主表明细表数据，物理分页，每页返回开始行到结束的数据
	 * mainMap:主表数据
	 * listMap：明细表数据
	 */
	public String querySuperPrintfillPage(Map<String, Object> printMap,List<SysPrintPara> paraList,Map<String,Object> mainMap,List<Map<String, Object>> liDetail)
			throws DataAccessException {
		// TODO Auto-generated method stub
		PageInfo pageInfo = new PageInfo(liDetail);
		long total=pageInfo.getTotal();//总记录条数
		long pages=pageInfo.getPages();//总页数， (total  +  pageSize  - 1) / pageSize;
		//pageInfo.getPageNum()当前页
		
		//查询打印模板内容
		String contentTemp=querySuperPrintContentByCode(printMap);
		if(contentTemp==null || contentTemp.equals("") || contentTemp.equals("<p><br></p>")){
			throw new SysException("没有定义打印模板！");
		}
		
		//明细起始行
		int detailBegin=Integer.parseInt(getSuperPrintParaValue(paraList,"004"));
		//明细结束行
		int detailEnd=Integer.parseInt(getSuperPrintParaValue(paraList,"005"));
		//是否套打
		if(getSuperPrintParaValue(paraList,"003").equals("是")){
			contentTemp=contentTemp.replaceAll("border=\"1\"+", "border=\"0\"");
		}
		
		//解析html
		Document doc = Jsoup.parse(contentTemp);
		
		/****************************************查找主表元素*************************************/
		Elements mainSpans = doc.select("span[dir$=.main]"); //带有dir属性的span元素
		if(mainSpans!=null && mainSpans.size()>0){
			for(Element span :mainSpans){
				
				//填充总页码
				if(span.attr("dir").replace(".main", "").equals("001")){
					span.after("<span tdata='pageNO'>###</span>/<span tdata='pageCount'>###</span>");
					span.remove();
				}
				
				//填充主表信息
				span.removeAttr("style");
				span.text("");
				if(mainMap!=null && mainMap.size()>0){
					
					for (Map.Entry<String, Object> entry : mainMap.entrySet()) {
						if(entry.getKey().toString().equalsIgnoreCase(span.attr("dir").replace(".main", ""))){
							 span.text(entry.getValue().toString());
						 }
					 }
				}
				
				
			}
		}
		
		//取合计大写的span
		Elements capSpans = doc.select("span[dir$=.capital]"); //带有dir属性的span元素
		if(capSpans!=null && capSpans.size()>0){
			for(Element span :capSpans){	
				//清空合计大写的span
				span.removeAttr("style");
				span.text("");
				
				//取合计数据
				if(mainMap!=null && mainMap.size()>0){
					if(mainMap.get(span.attr("dir").replace(".capital", "").toUpperCase())!=null){
						String heji=mainMap.get(span.attr("dir").replace(".capital", "").toUpperCase()).toString().replace(",", "");
						String capStr=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(heji)));
						span.text(capStr);
					}
				}else{
					throw new SysException("打印参数的编码【"+span.attr("dir").replace(".capital", "").toUpperCase()+"】与sql返回的列名不一致，或者返回NULL！");
				}
					
			}
		}
		
		
		/*******************************************查找明细表元素*************************************/
		//Element detailTds = doc.select("td:has(span[dir$=.detail])").first(); //带有dir属性的table元素
		Elements detailSpans = doc.select("span[dir$=.detail]");
		
		if(liDetail!=null && liDetail.size()>0 && detailSpans!=null && detailSpans.size()>0){
			
			//获取当前元素所在的table
			Element detailTable=getParentTable(detailSpans.get(0).parent(),"table");
			//取每列的打印参数编码
			List<String> paraCodeLi=new ArrayList<String>();
			Elements tdParas = detailTable.select("span[dir$=.detail]"); //带有dir属性的span元素
			if(tdParas!=null && tdParas.size()>0){
				for(Element span :tdParas){
					paraCodeLi.add(span.attr("dir").replace(".detail", ""));
				}
			}
		
			int detailIndex=0;
			for(Map<String, Object> detail:liDetail){
								
				
				//从开始行开始填充，大于结束行时break;
				if(detailBegin+detailIndex-1>detailEnd){
					break;
				}
				if(detailBegin+detailIndex-1>=detailTable.select("tr").size()){
					throw new SysException("打印模板明细表格的起始行定义错误！");
				}
				
				Element tr=detailTable.select("tr").get(detailBegin+detailIndex-1);//下标从0开始
					
				//开始填充明细数据
				Elements tds=tr.select("td");
				if(tds==null || tds.size()!=paraCodeLi.size()){
					throw new SysException("打印模板明细表格的每个td都必须指定打印参数！");
				}
				for(int i=0;i<tds.size();i++){
					
					Element td =tds.get(i);
					
					//复制明细开始行的TD样式
					td.html(detailTable.select("tr").get(detailBegin-1).select("td").get(i).html());
					//先清空数据
					if(td.select("span").last()!=null){
						td.select("span").last().text("");
					}else if(td.select("p").last()!=null){
						td.select("p").last().text("");
					}else{
						td.html("<span></span>");
					}
					
					if(detail.get(paraCodeLi.get(i).toUpperCase())!=null){
						String paraVal=detail.get(paraCodeLi.get(i).toUpperCase()).toString();			
						if(td.select("span").last()!=null){
							td.select("span").last().removeAttr("style");
							td.select("span").last().html("&nbsp;"+paraVal);
							if(!td.select("span").last().hasAttr("dir")){
								td.select("span").last().attr("dir", paraCodeLi.get(i)+".detail");
							}
						}else if(td.select("p").last()!=null){
							td.select("p").last().html("<span dir=\""+paraCodeLi.get(i)+".detail\">&nbsp;"+paraVal+"</span>");
						}else{
							td.html("<span dir=\""+paraCodeLi.get(i)+".detail\">&nbsp;"+paraVal+"</span>");
						} 
									 
					}else{
						//throw new SysException("打印参数的编码【"+paraCodeLi.get(i).toUpperCase()+"】与sql返回的列名不一致，或者返回NULL！");
					}
					
				}
				detailIndex++;
				
			}
		
			//小计
			Elements totalSpans = detailTable.select("span[dir$=.total]"); //带有dir属性的span元素
			if(totalSpans!=null && totalSpans.size()>0){
				for(Element span :totalSpans){					
					//填充小计数据
					String totalTd=span.attr("dir").replace("_total.total", "");
					Double totalMoney=0.00; 
					Elements totalEle = detailTable.select("span[dir="+totalTd+".detail]"); //带有dir属性的span元素
					if(totalEle!=null && totalEle.size()>0){
						for(Element s :totalEle){
							String ms=s.html().replaceAll(",+", "").replaceAll("&nbsp;+", "");
							if(!ms.equals("")){
								totalMoney+=Double.parseDouble(ms);
							}
						}
					}	
					span.removeAttr("style");
					span.html("&nbsp;"+myformat.format(totalMoney));
				}
			}
				
		}else{
			//没有明细数据，清空明细、小计、合计所有span的数据
			Elements allSpans = doc.select("span[dir~=(?i)\\.(detail|total|capital?g)]"); //带有dir属性的span元素
			if(allSpans!=null && allSpans.size()>0){
				for(Element span :allSpans){
					span.removeAttr("style");
					span.text("");
				}
			}
		}
		
		
		
		StringBuffer inputStr=new StringBuffer();
		inputStr.append("<input name=\"total\" type=\"hidden\" value=\""+total+"\"/>");
		inputStr.append("<input name=\"pages\" type=\"hidden\" value=\""+pages+"\"/>");
		inputStr.append("<input name=\"p_006\" type=\"hidden\" value=\""+getSuperPrintParaValue(paraList,"006")+"\"/>");//纸张大小mm(宽*高)
		inputStr.append("<input name=\"p_007\" type=\"hidden\" value=\""+getSuperPrintParaValue(paraList,"007")+"\"/>");//纸张属性
		inputStr.append("<input name=\"p_008\" type=\"hidden\" value=\""+getSuperPrintParaValue(paraList,"008")+"\"/>");//纸张方向
		inputStr.append("<input name=\"p_009\" type=\"hidden\" value=\""+getSuperPrintParaValue(paraList,"009")+"\"/>");//上边距
		inputStr.append("<input name=\"p_010\" type=\"hidden\" value=\""+getSuperPrintParaValue(paraList,"010")+"\"/>");//左边距
		
		contentTemp=doc.html()+inputStr.toString();

		return contentTemp;
	}


	//根据元素返回父table节点
	private Element getParentTable(Element e,String estr){
		if(e==null)return null;
		if(estr.split("@").length==1){
			if(e.tagName().equalsIgnoreCase(estr)){
				return e;
			}else{
				return getParentTable(e.parent(),estr);
			}
		}else{
			if(e.tagName().equalsIgnoreCase(estr.split("@")[0]) || e.tagName().equalsIgnoreCase(estr.split("@")[1])){
				return e;
			}else{
				return getParentTable(e.parent(),estr);
			}
		}
		
	}
	
	/*
	 * 此方法停用
	 * 主从表模板打印-明细表批量打印
	 * 填充所有明细数据，根据开始行结束行强制分页
	 */
	public String querySuperPrintfillBatch(Map<String, Object> mainMap,List<Map<String, Object>> liDetail)throws DataAccessException {
		//查询打印模板内容
		Map<String, Object> printMap=new HashMap<String, Object>();
		printMap.put("group_id", SessionManager.getGroupId());
		printMap.put("hos_id", SessionManager.getHosId());
		printMap.put("copy_code", SessionManager.getCopyCode());
		printMap.put("use_id", mainMap.get("use_id"));
		printMap.put("template_code",mainMap.get("print_template_code"));
		
		String contentTemp=null;
		try{
			contentTemp=querySuperPrintContentByCode(printMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		if(contentTemp==null || contentTemp.equals("") || contentTemp.equals("<p><br></p>")){
			throw new SysException("没有定义打印模板！");
		}
		
		//查询打印参数
		Map<String,String> paraMap=new HashMap<String,String>();
		try{
			paraMap=querySuperPrintParaByList(printMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		//明细起始行
		String beginStr=paraMap.get("004");
		int detailBegin=Integer.parseInt(beginStr.equals("")?"0":beginStr);
		//明细结束行
		String endStr=paraMap.get("005");
		int detailEnd=Integer.parseInt(endStr.equals("")?"0":endStr);
		
		//是否套打
		boolean p003=false;
		
		if(mainMap.get("print_p_003")!=null && mainMap.get("print_p_003").equals("true")){
			//通过前台按钮控制，套打
			p003=true;
		}else if(mainMap.get("print_p_003")!=null && mainMap.get("print_p_003").equals("false")){
			//通过前台按钮控制，模板打印
			p003=false;
		}else if(paraMap.get("003").equals("是")){
			//通过打印参数控制是否套打
			p003=true;
		}
		
		if(p003){
			contentTemp=contentTemp.replaceAll("border=\"1\"+", "border=\"0\"");
		}
		
		
		//解析html
		Document doc = Jsoup.parse(contentTemp);
		
		if(p003){
			//套打删除所有没有需要填充td的html
			Elements delTds=doc.select("td"); //带有dir属性的span元素
			if(delTds!=null && delTds.size()>0){
				for(Element td :delTds){
					if(td.select("td:has(span[dir~=(?i)\\.(main|detail|total|capital|capital?g)])").size()==0){//没有要填充的元素
						td.html("");
					}
				}
			}
			
			//套打删除所有没有需要填充td的html
			Elements delThs=doc.select("th"); //带有dir属性的span元素
			if(delThs!=null && delThs.size()>0){
				for(Element td :delThs){
					if(td.select("th:has(span[dir~=(?i)\\.(main|detail|total|capital|capital?g)])").size()==0){//没有要填充的元素
						td.html("");
					}
				}
			}
		}
		
		StringBuffer sbContentTemp=new StringBuffer();
		
		/****************************************查找主表元素*************************************/
		Elements mainSpans = doc.select("span[dir$=.main]"); //带有dir属性的span元素
		if(mainSpans!=null && mainSpans.size()>0){
			for(Element span :mainSpans){
				
				//填充系统页码
				if(span.attr("dir").replace(".main", "").equals("001")){
					span.after("<span tdata='pageNO'>###</span>/<span tdata='pageCount'>###</span>");
					span.remove();
				}
				
				//填充主表信息
				span.removeAttr("style");
				span.text("");
				if(mainMap!=null && mainMap.size()>0){
					
					for (Map.Entry<String, Object> entry : mainMap.entrySet()) {
						if(entry.getKey().toString().equalsIgnoreCase(span.attr("dir").replace(".main", ""))){
							 span.text(entry.getValue().toString());
						 }
					 }
				}
				
				
			}
		}	
		
		//取合计大写的span
		Elements capSpans = doc.select("span[dir$=.capital]"); //带有dir属性的span元素
		if(capSpans!=null && capSpans.size()>0){
			for(Element span :capSpans){	
				//清空合计大写的span
				span.removeAttr("style");
				span.text("");
				
				//取合计数据
				if(mainMap!=null && mainMap.size()>0){
					if(mainMap.get(span.attr("dir").replace(".capital", "").toUpperCase())!=null){
						String heji=mainMap.get(span.attr("dir").replace(".capital", "").toUpperCase()).toString().replace(",", "");
						String capStr=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(heji)));
						span.text(capStr);
					}
				}else{
					throw new SysException("打印参数的编码【"+span.attr("dir").replace(".capital", "").toUpperCase()+"】与sql返回的列名不一致，或者返回NULL！");
				}
					
			}
		}
	
		//Element detailTds = doc.select("td:has(span[dir$=.detail])").first(); //带有dir属性的table元素
		Elements detailSpans = doc.select("span[dir$=.detail]");
		//根据明细集合填充数据
		int pagdIndex=0;
		if(liDetail!=null && liDetail.size()>0 && detailSpans!=null && detailSpans.size()>0){
			
			//计算总页数
			int totalPageNum =(liDetail.size()  +  detailEnd-detailBegin) / (detailEnd-detailBegin+1);  
			//获取当前元素所在的table
			Element detailTable=getParentTable(detailSpans.get(0).parent(),"table");
			//取每列的打印参数编码
			List<String> paraCodeLi=new ArrayList<String>();
			Elements tdParas = detailTable.select("span[dir$=.detail]"); //带有dir属性的span元素
			if(tdParas!=null && tdParas.size()>0){
				for(Element span :tdParas){
					paraCodeLi.add(span.attr("dir").replace(".detail", ""));
				}
			}
		
			int detailIndex=0;
			int totalIndex=0;
			for(Map<String, Object> detail:liDetail){
				totalIndex++;
				//从开始行开始填充，大于结束行时break;
				if(detailBegin+detailIndex>detailEnd){
					detailIndex=0;
					//清空明细表格span的数据，重新开始填充
					Elements dataDetailSpans = doc.select("span[dir$=.detail]");
					for(Element span :dataDetailSpans){
						span.text("");
					}
					
				}
				if(detailBegin+detailIndex>=detailTable.select("tr").size()){
					throw new SysException("打印模板明细表格的起始行定义错误！");
				}
				
				
				/*******************************************开始填充明细数据*************************************/
				Element tr=detailTable.select("tr").get(detailBegin+detailIndex-1);//下标从0开始
					
				Elements tds=tr.select("td");
				if(tds==null || tds.size()!=paraCodeLi.size()){
					throw new SysException("打印模板明细表格的每个td都必须指定打印参数！");
				}
				for(int i=0;i<tds.size();i++){
					
					Element td =tds.get(i);
					
					//复制明细开始行的TD样式
					td.html(detailTable.select("tr").get(detailBegin-1).select("td").get(i).html());
					//先清空数据
					if(td.select("span").last()!=null){
						td.select("span").last().text("");
					}else if(td.select("p").last()!=null){
						td.select("p").last().text("");
					}else{
						td.html("<span></span>");
					}
					
					if(detail.get(paraCodeLi.get(i).toUpperCase())!=null){
						String paraVal=detail.get(paraCodeLi.get(i).toUpperCase()).toString();			
						if(td.select("span").last()!=null){
							td.select("span").last().removeAttr("style");
							td.select("span").last().text(paraVal);
							if(!td.select("span").last().hasAttr("dir")){
								td.select("span").last().attr("dir", paraCodeLi.get(i)+".detail");
							}
						}else if(td.select("p").last()!=null){
							td.select("p").last().html("<span dir=\""+paraCodeLi.get(i)+".detail\">"+paraVal+"</span>");
						}else{
							td.html("<span dir=\""+paraCodeLi.get(i)+".detail\">"+paraVal+"</span>");
						} 
									 
					}else{
						//throw new SysException("打印参数的编码【"+paraCodeLi.get(i).toUpperCase()+"】与sql返回的列名不一致，或者返回NULL！");
					}
					
				}
				detailIndex++;
				
				//最后一行或者最后一条记录
				if(detailEnd-detailBegin+1==detailIndex || totalIndex==liDetail.size()){
					pagdIndex++;
					//填充单据页码
					Element totalPageSpan=doc.select("span[dir=002.main]").first(); //带有dir属性的span元素
					totalPageSpan.removeAttr("style");
					totalPageSpan.text(pagdIndex+"/"+totalPageNum);
					
					//计算小计
					Elements totalSpans = doc.select("span[dir$=.total]"); //带有dir属性的span元素
					if(totalSpans!=null && totalSpans.size()>0){
						for(Element span :totalSpans){					
							//填充小计数据
							String totalTd=span.attr("dir").replace("_total.total", "");
							Double totalMoney=0.00; 
							Elements totalEle = detailTable.select("span[dir="+totalTd+".detail]"); //带有dir属性的span元素
							if(totalEle!=null && totalEle.size()>0){
								for(Element s :totalEle){
									String ms=s.html().replaceAll(",+", "").replace("&nbsp;+", "").replace(" +", "");
									if(!ms.equals("")){
										totalMoney+=Double.parseDouble(ms);
									}
								}
							}
						
							span.removeAttr("style");
							span.text(myformat.format(totalMoney));
						}
					}
					
					//处理分页符
					sbContentTemp.append("<div name=\"page-break\">");
					sbContentTemp.append(doc.select("body").html());
					sbContentTemp.append("</div>");
					//sbContentTemp.append("<p style=\"page-break-after:always\">&nbsp;</p>");
					//sbContentTemp.append("<p style=\"page-break-before:always\">&nbsp;</p>");
					
				}
			}
				
		}else{
			//没有明细数据，清空明细、小计、合计所有span的数据
			Elements allSpans = doc.select("span[dir~=(?i)\\.(main|detail|total|capital|capital?g)]"); //带有dir属性的span元素
			if(allSpans!=null && allSpans.size()>0){
				for(Element span :allSpans){
					span.removeAttr("style");
					span.text("");
				}
			}
		}
		
		return sbContentTemp.toString();
	}

	
	/**
	 *spreadJS打印（主从表单张打印）
	 *根据map、list合并成一个可打印的json串
	 * selMap查询打印参数，打印模板的条件
	 * mainMap主表数据
	 * detailList明细表数据
	 */
	public String getMapListByPrintTemplateJson(Map<String,Object> selMap,Map<String,Object> mainMap,List<Map<String,Object>> detailList)throws DataAccessException{
		
		if(selMap==null || selMap.get("template_code")==null || selMap.get("template_code").equals("")){
			return "{error:\"没有打印模板编码参数[template_code]！\"}";
		}
		
		if(selMap==null || selMap.get("use_id")==null || selMap.get("use_id").equals("")){
			selMap.put("use_id", 0);//统一打印
		}
		
		//查询打印模板参数
		Map<String,String> paraMap=new HashMap<String,String>();
		try{
			paraMap=querySuperPrintParaByList(selMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		if(null==paraMap || paraMap.size()==0){
			return "{error:\"没有维护打印参数！\"}";
		}
		
		//查询打印模板内容
		SysPrintTemplate printTemplate=null;
		try{
			printTemplate=superPrintMapper.querySuperPrintTemplateByCode(selMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		if(printTemplate==null || printTemplate.getContent().equals("")){
			return "{error:\"没有维护打印模板！\"}";
		}
		String content=printTemplate.getContent();
		
		//明细开始行
		String beginStr=paraMap.get("004");
		//明细结束行
		String endStr=paraMap.get("005");
		
		JSONObject json=JSONObject.parseObject(content);
		
		//模板每页打印次数
		String pageCount="1";
		if(paraMap.get("014")!=null && !paraMap.get("014").equals("")){
			pageCount=paraMap.get("014");
		}
		json.put("pageCount", pageCount);
		
		JSONObject jsonSheet=JSONObject.parseObject(json.getString("sheets"));
		if(jsonSheet==null || jsonSheet.size()==0){
			return content;
		}
		
		JSONObject sheetObject=null;
		int tempRowCount=0;//模板总行数
		int tempColumnCount=0;//模板总列数
		int pageIdex=1;//当前页
		int pageNum=1;
		int printCount=0;//打印次数
		
		//循环遍历sheet
		for (String sheet : jsonSheet.keySet()) {  
			sheetObject=jsonSheet.getJSONObject(sheet);
			sheetObject.remove("selections");//清除焦点区域
			tempRowCount=sheetObject.getIntValue("rowCount");
			tempColumnCount=sheetObject.getIntValue("columnCount");
			break;//打印模板只取第一个sheet
		}
		if(sheetObject==null){
			return content;
		}
		
		JSONObject data=sheetObject.getJSONObject("data");
		if(data==null){
			return content;
		}
		
		JSONObject dataTable=data.getJSONObject("dataTable");
		if(dataTable==null){
			dataTable=JSONObject.parseObject("{ \"0\": { \"0\": { \"value\": \"\", \"style\": {} } } }");
			data.put("dataTable",dataTable);
		}
		
		//是否套打
		boolean isSetPrint=false;
		if(paraMap.get("003")!=null && paraMap.get("003").toString().equals("是")){
			isSetPrint=true;
		}
		
		if(selMap.get("isSetPrint")!=null){
			if(selMap.get("isSetPrint").toString().equals("true")){
				isSetPrint=true;
			}else if(selMap.get("isSetPrint").toString().equals("false")){
				isSetPrint=false;
			}
		}
		
		//套打，删除单元格的内容，去掉边框线
		if(isSetPrint){
			
			//根据批注决定哪些单元格的内容在套打的时候可以正常显示
			JSONArray comments= sheetObject.getJSONArray("comments");
			Map<String,String> comMap=new HashMap<String,String>();
			if(comments!=null && comments.size()>0){
				for(int i=0;i<comments.size();i++){
					JSONObject comRowObject=JSONObject.parseObject(comments.getString(i));
					comMap.put(comRowObject.get("rowIndex")+"-"+comRowObject.get("colIndex"),"");
				}
			}
			
			//遍历行
			for (String row : dataTable.keySet()) {  
				JSONObject rowObject=dataTable.getJSONObject(row);
				
				//遍历列
				for (String cell : rowObject.keySet()) {
					JSONObject cellObject=rowObject.getJSONObject(cell);
					//没有批注，所有单元格的内容为空
					if(comMap.get(row+"-"+cell)==null){
						cellObject.put("value", "");
					}
					
					try{
						JSONObject styleObject=cellObject.getJSONObject("style");
						if(styleObject!=null){
							styleObject.remove("borderLeft");
							styleObject.remove("borderTop");
							styleObject.remove("borderRight");
							styleObject.remove("borderBottom");
						}
					}catch(Exception e){
						logger.error(row+","+cell+"："+cellObject.toString());
					}
				}
			}
		}
		
		
		try{
			
			/**
			 * 主表数据填充
			 */
			if(mainMap!=null && mainMap.size()>0){
				
				//处理系统参数
				mainMap.put("001", "&总页数&");
				mainMap.put("002", "&当前页数&");
				
				//打印次数，根据第一列更新打印次数
				if(paraMap.get("011")!=null && !paraMap.get("011").equals("")){
					for(Map.Entry<String, Object> entry:mainMap.entrySet()){ 
						if(entry.getKey().toLowerCase().equalsIgnoreCase(paraMap.get("999999"))){
							if(entry.getValue()!=null && !entry.getValue().toString().equals("")){
								selMap.put("print_business_no", entry.getValue().toString());
							}
						}
					}
					if(selMap.get("print_business_no")!=null){
						printCount=superPrintMapper.querySuperPrintCount(selMap);
						printCount=printCount+1;
						selMap.put("print_count", printCount);
						mainMap.put("011",printCount);
						
						//删除打印次数
						superPrintMapper.deleteSuperPrintCount(selMap);
							
						//添加打印次数
						superPrintMapper.insertSuperPrintCount(selMap);
						
					}
					
				}
								
				for(Map.Entry<String, Object> entry:mainMap.entrySet()){ 
					
					if(entry.getKey()!=null && !entry.getKey().equals("")){
						
						String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
						
						if(paraVal!=null && !paraVal.equals("")){
							
							//主表数据填充
							int rowIndex=Integer.parseInt(paraVal.split(",")[0])-1;//下标从0开始
							int cellIndex=Integer.parseInt(paraVal.split(",")[1])-1;//下标从0开始
							JSONObject rowObject=dataTable.getJSONObject(String.valueOf(rowIndex));//横坐标
							
							Object mainVal=entry.getValue()==null?"":entry.getValue();
							
							//金额转大写
							if(paraVal.split(",").length==3 && Integer.parseInt(paraVal.split(",")[2])==5){
								mainVal=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(mainVal.toString())));
							}
							
							if(rowObject==null){
								//没有行对象，插入列数据
								rowObject=JSONObject.parseObject("{ \""+cellIndex+"\": { \"value\": \""+mainVal+"\", \"style\": {} } }");
								dataTable.put(String.valueOf(rowIndex), rowObject);
								continue;
							}
							
							JSONObject cellObject=rowObject.getJSONObject(String.valueOf(cellIndex));//纵坐标
							if(cellObject!=null){
								//格式化日期、时间格式
								Object objStyle=cellObject.get("style");
								if(objStyle!=null && objStyle instanceof JSONObject){									
									String formatter=JSONObject.parseObject(objStyle.toString()).getString("formatter");
									if(formatter!=null && (formatter.equals("yyyy-MM-dd") || formatter.equals("yyyy-MM-dd HH:mm:ss"))){
										mainVal=DateUtil.dateFormat(mainVal, formatter);
									}
								}
								
								cellObject.put("value", mainVal);//填充值
							}else{
								//没有列对象，插入数据
								rowObject.put(String.valueOf(cellIndex),JSONObject.parseObject("{ \"value\": \""+mainVal+"\", \"style\": {}}"));
							}
							
						}
					}
				}
			}
			
			/**
			 * 明细表数据填充
			 */
			if(beginStr!=null && !beginStr.equals("") && endStr!=null && !endStr.equals("") && detailList!=null && detailList.size()>0){
				
				int detailBegin=Integer.parseInt(beginStr)-1;//模板开始行，下标从0开始
				int targetBegin=detailBegin;//目标开始行
				int detailEnd=Integer.parseInt(endStr)-1;//模板结束行，下标从0开始
				int targetEnd=detailEnd;//目标开始
				int pageSize=detailEnd+1-detailBegin;//明细数据页大小
				pageNum =(detailList.size()  +  pageSize-1) / pageSize; //计算总页数， (total  +  pageSize  - 1) / pageSize;
				JSONObject tableTemp=null;
				JSONArray rowsArray=null;
				JSONArray columnArray=null;
				JSONArray spanArray=null;
				int spanCount=0;
				
				/*
				 * 开始保存数据和格式
				 *如果大于1页，保存模板内容和格式，用于复制 
				 */
				if(pageNum>1){
					
					tableTemp=JSONObject.parseObject(dataTable.toJSONString());//模板新的空白行和主表数据"dataTable"
					
					rowsArray=sheetObject.getJSONArray("rows");//模板行的格式以及强制分页，例如高度size				
					if(rowsArray!=null && rowsArray.size()>0){
						
						//如果最后一行没有编辑，数组不会有数据，默认加一组null
						while(rowsArray.size()<tempRowCount){
							rowsArray.add(null);
						}
						
					}
					
					columnArray=sheetObject.getJSONArray("columns");//模板列的格式以及强制分页，例如宽度size
					if(columnArray!=null && columnArray.size()>0){
						
						//如果最后一列没有编辑，数组不会有数据，默认加一组null
						while(columnArray.size()<tempColumnCount){
							columnArray.add(null);
						}
						
					}
					
					spanArray=sheetObject.getJSONArray("spans");//模板单元格的合并格式因为复制表格，需要按页数修改行号，例如"row": 10, "rowCount": 1, "col": 3, "colCount": 3
					if(spanArray!=null && spanArray.size()>0){
						spanCount=spanArray.size();//记录大小，方便复制表格的时候，修改行数
						
					}
				}
				
				for(Map<String,Object> detailMap :detailList){
					
					if(targetBegin>targetEnd){
						targetBegin=tempRowCount*pageIdex+detailBegin;//新的页面目标开始行定位
						targetEnd=tempRowCount*pageIdex+detailEnd;//新的页面目标结束行定位
						
						/**
						 * 开始复制数据
						 */
						//（数据:dataTable）复制模板新的空白行和主表数据，到目标行
						if(dataTable!=null){
							for (String row : tableTemp.keySet()) {
								int newRow=Integer.parseInt(row)+tempRowCount*pageIdex;
								String rowStr=tableTemp.getString(row);
								rowStr=rowStr.replace("&当前页数&", String.valueOf(pageIdex+1));
								
								if(rowStr.indexOf("formula")!=1){
									//替换公式里面的行号
									rowStr=replaceFormula(rowStr,tempRowCount*pageIdex);
								}
								dataTable.put(String.valueOf(newRow), JSONObject.parseObject(rowStr));
							}
						}
						
						
						//（行:rows）复制模板新的行格式以及强制分页
						if(rowsArray!=null && rowsArray.size()>0){
							
							for(int i=0;i<tempRowCount;i++){
								JSONObject rowObject=JSONObject.parseObject(rowsArray.getString(i));
								
								if(i==0){
									if(rowObject==null){
										rowsArray.add(JSONObject.parseObject("{\"pageBreak\":true}"));
									}else{
										rowObject.put("pageBreak", true);
										rowsArray.add(rowObject);
									}
									
								}else{
									rowsArray.add(rowObject);
								}
								
							}
						}
						
						//（列:columns）复制模板新的列格式以及强制分页
						if(columnArray!=null && columnArray.size()>0){
							
							for(int i=0;i<tempColumnCount;i++){
								columnArray.add(JSONObject.parseObject(columnArray.getString(i)));
							}
						}
						
						//（单元格:rowspan、colspan）复制模板新的单元格的合并格式
						if(spanArray!=null && spanArray.size()>0){
							
							for(int i=0;i<spanCount;i++){
								JSONObject rowObj=JSONObject.parseObject(spanArray.getString(i));
								if(rowObj!=null){
									int rowIndex=rowObj.getInteger("row")+tempRowCount*pageIdex;
									rowObj.put("row", rowIndex);
								}
								spanArray.add(rowObj);
							}
						}
						
						pageIdex++;//页数
					}
					
					JSONObject rowObject=dataTable.getJSONObject(String.valueOf(targetBegin));//横坐标
					if(rowObject==null){
						//没有行对象，插入列数据
						rowObject=JSONObject.parseObject("{ \"0\": { \"value\": \"\", \"style\": {} } }");
						dataTable.put(String.valueOf(targetBegin), rowObject);
					}
					
					if(detailMap!=null && detailMap.size()>0){
						for(Map.Entry<String, Object> entry:detailMap.entrySet()){ 
							
							if(entry.getKey()!=null && !entry.getKey().equals("")){
								String paraVal=paraMap.get(entry.getKey().toLowerCase());
								if(paraVal!=null && !paraVal.equals("")){
									
									int cellIndex=Integer.parseInt(paraVal)-1;//下标从0开始
									JSONObject cellObject=rowObject.getJSONObject(String.valueOf(cellIndex));//纵坐标
									Object detailVal=entry.getValue()==null?"":entry.getValue();
									
									if(cellObject!=null){
										//格式化日期、时间格式
										Object objStyle=cellObject.get("style");
										if(objStyle!=null && objStyle instanceof JSONObject){									
											String formatter=JSONObject.parseObject(objStyle.toString()).getString("formatter");
											if(formatter!=null && (formatter.equals("yyyy-MM-dd") || formatter.equals("yyyy-MM-dd HH:mm:ss"))){
												detailVal=DateUtil.dateFormat(detailVal, formatter);
											}
										}
										cellObject.put("value", detailVal);//填充值
									}else{
										//没有列对象，插入数据
										rowObject.put(String.valueOf(cellIndex),JSONObject.parseObject("{ \"value\": \""+detailVal+"\", \"style\": {}}"));
									}
								}
							}
						}
					}
					targetBegin++;//目标开始行
				}
				
				/**
				 * 清除空行
				 */
				if(paraMap.get("013")!=null && paraMap.get("013").equals("是") && pageNum*pageSize>detailList.size()){
					JSONArray rowAll =sheetObject.getJSONArray("rows");
					int empNumber=pageNum*pageSize-detailList.size();//空行数
					
					if(rowAll!=null && rowAll.size()>0){
						int rowCount=rowAll.size();
						int empEnd=detailEnd+pageNum*tempRowCount-tempRowCount;//明细结束行+总页数*模板总行数-模板总行数
						if(empEnd>rowCount){
							//如果最后一行没有编辑，数组不会有数据，以数组大小为准，从最后行开始替换
							empEnd=rowCount-1;
						}
						
						//防止数组越界
						if(empEnd<=rowAll.size()){
							for(int i=0;i<empNumber;i++){
								
								//从最后行开始设置
								JSONObject rowObject=JSONObject.parseObject(rowAll.getString(empEnd-i));
								if(rowObject==null){
									rowAll.add(JSONObject.parseObject("{\"visible\":false}"));
								}else{
									rowObject.put("visible", false);
									rowAll.set(empEnd-i, rowObject);
								}
							}
						}
					}
				}
				
			}	
			
			if(pageIdex>1){
				sheetObject.put("rowCount", pageIdex*tempRowCount);//修改总行数
			}
			
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		
		
		json.put("sheets", jsonSheet);
		String jsonStr=json.toJSONString();
		jsonStr=jsonStr.replace("&当前页数&", "1");
		jsonStr=jsonStr.replace("&总页数&", String.valueOf(pageNum));
		
		return jsonStr;
	}
	
	//替换单元格里面公式的行号，"formula": "SUM(H6:H10)"
	private String replaceFormula(String rowStr,int tempRowCount)throws Exception{
		
		if(rowStr==null || rowStr.equals("")){
			return rowStr;
		}
		
		JSONObject rowJson=JSONObject.parseObject(rowStr);
		Matcher m=null;
		Matcher mNum=null;
		
		for (String cell : rowJson.keySet()) {
			
			JSONObject cellJson=JSONObject.parseObject(rowJson.getString(cell));
			if(cellJson!=null && cellJson.getString("formula")!=null && !cellJson.getString("formula").equals("")){
				
				String formula=cellJson.getString("formula");
				m = patStrNum.matcher(formula);
				while(m.find()){
					String rowCell=m.group();//字母和数字组合B13
					
					mNum=patNum.matcher(rowCell);
					String rowNum = mNum.replaceAll("");//提取数字
					String rowZm=rowCell.replace(rowNum, "");//提取字母
					
					formula=formula.replace(rowCell, rowZm+String.valueOf(Integer.parseInt(rowNum)+tempRowCount));
					//String oldRow=m.group();
					//formula=formula.replace(oldRow, String.valueOf(Integer.parseInt(oldRow)+tempRowCount));
				}
				
				cellJson.put("formula", formula);
			}
			rowJson.put(cell, cellJson);
		}
		
		return rowJson.toJSONString();
	}
	
	/**
	 * 此方法停用，数据量大，返回字符串内存溢出
	 * spreadJS打印（主从表批量打印）
	 * 根据list合并成一个可打印的json串
	 * selMap查询打印参数，打印模板的条件
	 * mainList主表数据
	 * detailList明细表数据
	 */
	public String getBatchListByPrintTemplateJson2(Map<String,Object> selMap,List<Map<String,Object>> mainList,List<Map<String,Object>> detailList)throws DataAccessException{
		
		if(selMap==null || selMap.get("template_code")==null || selMap.get("template_code").equals("")){
			return "{error:\"没有打印模板编码参数[template_code]！\"}";
		}
		
		if(selMap==null || selMap.get("use_id")==null || selMap.get("use_id").equals("")){
			selMap.put("use_id", 0);//统一打印
		}
		
		/*
		 * 主从表组装成一个List<Map<String,Object>>
		 * map格式，当key=detail时为明细数据，value=List<Map<String,Object>>
		 */
		if(mainList!=null && mainList.size()>0){
			
			if(detailList!=null && detailList.size()>0){
				
				List<Map<String,Object>> detailTemp=null;
				for(Map<String,Object> main:mainList){
					
					if(main.get("ID")!=null){
						
						detailTemp=new ArrayList<Map<String,Object>>();
						for(Map<String,Object> detail:detailList){
							//主表ID与明细表ID一致
							if(main.get("ID").toString().equalsIgnoreCase(detail.get("ID").toString())){
								detailTemp.add(detail);
							}
						}
						main.put("DETAIL", detailTemp);
					}
					
				}
			}
			
		}else{
			return "{error:\"没有主表数据！\"}";
		}
		
		//查询打印模板参数
		Map<String,String> paraMap=new HashMap<String,String>();
		try{
			paraMap=querySuperPrintParaByList(selMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		if(null==paraMap || paraMap.size()==0){
			return "{error:\"没有维护打印参数！\"}";
		}
		
		//查询打印模板内容
		SysPrintTemplate printTemplate=null;
		try{
			printTemplate=superPrintMapper.querySuperPrintTemplateByCode(selMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		if(printTemplate==null || printTemplate.getContent().equals("")){
			return "{error:\"没有维护打印模板！\"}";
		}
		String content=printTemplate.getContent();
		
		JSONObject json=JSONObject.parseObject(content);
		
		//模板每页打印次数
		String pageCount="1";
		if(paraMap.get("014")!=null && !paraMap.get("014").equals("")){
			pageCount=paraMap.get("014");
		}
		json.put("pageCount", pageCount);
		
		JSONObject jsonSheet=JSONObject.parseObject(json.getString("sheets"));
		if(jsonSheet==null || jsonSheet.size()==0){
			return content;
		}
		
		JSONObject sheetObject=null;
		int tempRowCount=0;//模板总行数
		int tempColumnCount=0;//模板总列数
		//循环遍历sheet
		for (String sheet : jsonSheet.keySet()) {  
			sheetObject=jsonSheet.getJSONObject(sheet);
			sheetObject.remove("selections");//清除焦点区域
			tempRowCount=sheetObject.getIntValue("rowCount");
			tempColumnCount=sheetObject.getIntValue("columnCount");
			break;//打印模板只取第一个sheet
		}
		if(sheetObject==null){
			return content;
		}
		
		JSONObject data=sheetObject.getJSONObject("data");
		if(data==null){
			return content;
		}
		
		JSONObject dataTable=data.getJSONObject("dataTable");
		if(dataTable==null){
			dataTable=JSONObject.parseObject("{ \"0\": { \"0\": { \"value\": \"\", \"style\": {} } } }");
			data.put("dataTable",dataTable);
		}
		
		//是否套打
		boolean isSetPrint=false;
		if(paraMap.get("003")!=null && paraMap.get("003").toString().equals("是")){
			isSetPrint=true;
		}
				
		if(selMap.get("isSetPrint")!=null){
			if(selMap.get("isSetPrint").toString().equals("true")){
				isSetPrint=true;
			}else if(selMap.get("isSetPrint").toString().equals("false")){
				isSetPrint=false;
			}
		}
		
		//套打，删除单元格的内容，去掉边框线
		if(isSetPrint){
			
			//根据批注决定哪些单元格的内容在套打的时候可以正常显示
			JSONArray comments= sheetObject.getJSONArray("comments");
			Map<String,String> comMap=new HashMap<String,String>();
			if(comments!=null && comments.size()>0){
				for(int i=0;i<comments.size();i++){
					JSONObject comRowObject=JSONObject.parseObject(comments.getString(i));
					comMap.put(comRowObject.get("rowIndex")+"-"+comRowObject.get("colIndex"),"");
				}
			}
			
			//遍历行
			for (String row : dataTable.keySet()) {  
				JSONObject rowObject=dataTable.getJSONObject(row);
				
				//遍历列
				for (String cell : rowObject.keySet()) {
					JSONObject cellObject=rowObject.getJSONObject(cell);
					//没有批注，所有单元格的内容为空
					if(comMap.get(row+"-"+cell)==null){
						cellObject.put("value", "");
					}
					
					try{
						JSONObject styleObject=cellObject.getJSONObject("style");
						if(styleObject!=null){
							styleObject.remove("borderLeft");
							styleObject.remove("borderTop");
							styleObject.remove("borderRight");
							styleObject.remove("borderBottom");
						}
					}catch(Exception e){
						logger.error(row+","+cell+"："+cellObject.toString());
					}
				}
			}
		}
		
		
		JSONArray rowsArray=null;
		JSONArray columnArray=null;
		JSONArray spanArray=null;
		int spanCount=0;
		rowsArray=sheetObject.getJSONArray("rows");//模板行的格式以及强制分页，例如高度size				
		if(rowsArray!=null && rowsArray.size()>0){
			
			//如果最后一行没有编辑，数组不会有数据，默认加一组null
			while(rowsArray.size()<tempRowCount){
				rowsArray.add(null);
			}
			
		}
		
		columnArray=sheetObject.getJSONArray("columns");//模板列的格式以及强制分页，例如宽度size
		if(columnArray!=null && columnArray.size()>0){
			
			//如果最后一列没有编辑，数组不会有数据，默认加一组null
			while(columnArray.size()<tempColumnCount){
				columnArray.add(null);
			}
			
		}
		
		spanArray=sheetObject.getJSONArray("spans");//模板单元格的合并格式因为复制表格，需要按页数修改行号，例如"row": 10, "rowCount": 1, "col": 3, "colCount": 3
		if(spanArray!=null && spanArray.size()>0){
			spanCount=spanArray.size();//记录大小，方便复制表格的时候，修改行数
			
		}
	
		try{
			int pageIndex=1;//页下标
			JSONObject tableNull=null;//保存模板内容，用于复制
			for(Map<String,Object> main:mainList){
				
				//处理系统参数
				main.put("001", "&总页数&");
				main.put("002", "&当前页数&");
				main.put("011", printTemplate.getPrint_count()+1);//打印次数
				
				if(pageIndex==1){
					tableNull=JSONObject.parseObject(dataTable.toJSONString());//模板新的空白行"dataTable"
				}else{
					//复制新的表格
					copySpreadTable(tableNull,dataTable,rowsArray,columnArray,spanArray,pageIndex,0,tempRowCount,tempColumnCount,spanCount);
				}
				
				//打印次数
				if(paraMap.get("011")!=null && !paraMap.get("011").equals("") && main!=null && main.size()>0){
						
					//主表数据填充
					for(Map.Entry<String, Object> entry:main.entrySet()){ 
						if(entry.getKey().toLowerCase().equalsIgnoreCase(paraMap.get("999999"))){
							if(entry.getValue()!=null && !entry.getValue().toString().equals("")){
								selMap.put("print_business_no", entry.getValue().toString());
							}
						}
							
					}
					if(selMap.get("print_business_no")!=null){
						int printCount=0;
						printCount=superPrintMapper.querySuperPrintCount(selMap);
						printCount=printCount+1;
						selMap.put("print_count", printCount);
						main.put("011",printCount);
								
						//删除打印次数
						superPrintMapper.deleteSuperPrintCount(selMap);
									
						//添加打印次数
						superPrintMapper.insertSuperPrintCount(selMap);
						selMap.remove("print_business_no");	
					}
					
				}
				
				//填充主从表数据
				pageIndex=spreadJsDatafill(main,paraMap,tableNull,sheetObject,dataTable,rowsArray,columnArray,spanArray,pageIndex,tempRowCount,tempColumnCount,spanCount);
			}
			
			sheetObject.put("rowCount", (pageIndex-1)*tempRowCount);//修改总行数
			
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		json.put("sheets", jsonSheet);
		String jsonStr=json.toJSONString();
		jsonStr=jsonStr.replace("&当前页数&", "1");
		//logger.debug(jsonStr);
		return jsonStr;
	}
	
	
	//按单据批量填充数据
	public int spreadJsDatafill(Map<String,Object> mainMap,Map<String,String> paraMap,JSONObject tableNull,JSONObject sheetObject,JSONObject dataTable,JSONArray rowsArray,JSONArray columnArray,JSONArray spanArray,int pageIndex,int tempRowCount,int tempColumnCount,int spanCount)
				throws Exception{
		
		//明细开始行
		String beginStr=paraMap.get("004");
		//明细结束行
		String endStr=paraMap.get("005");
		
		int pageNum=1;
		
		try{
			
			/**
			 * 主表数据填充
			 */
			if(mainMap!=null && mainMap.size()>0){	
				
				//主表数据填充
				for(Map.Entry<String, Object> entry:mainMap.entrySet()){ 
					
					if(entry.getKey()!=null && !entry.getKey().equals("")){
						
						String paraVal=paraMap.get(entry.getKey().toLowerCase());//取参数值
						
						if(paraVal!=null && !paraVal.equals("")){
						
							//主表数据填充
							int rowIndex=Integer.parseInt(paraVal.split(",")[0])-1;//下标从0开始
							
							if(pageIndex>1){
								//如果不是第一页，需要重新计算行号
								rowIndex=rowIndex+(pageIndex-1)*tempRowCount;
							}
							int cellIndex=Integer.parseInt(paraVal.split(",")[1])-1;//下标从0开始
							JSONObject rowObject=dataTable.getJSONObject(String.valueOf(rowIndex));//横坐标
							
							Object mainVal=entry.getValue()==null?"":entry.getValue();
							
							//金额转大写
							if(paraVal.split(",").length==3 && Integer.parseInt(paraVal.split(",")[2])==5){
								mainVal=NumberToCN.number2CNMontrayUnit(BigDecimal.valueOf(Double.parseDouble(mainVal.toString())));
							}
							
							if(rowObject==null){
								//没有行对象，插入列数据
								rowObject=JSONObject.parseObject("{ \""+cellIndex+"\": { \"value\": \""+mainVal+"\", \"style\": {} } }");
								dataTable.put(String.valueOf(rowIndex), rowObject);
								continue;
							}
							
							JSONObject cellObject=rowObject.getJSONObject(String.valueOf(cellIndex));//纵坐标
							if(cellObject!=null){
								//格式化日期、时间格式
								Object objStyle=cellObject.get("style");
								if(objStyle!=null && objStyle instanceof JSONObject){									
									String formatter=JSONObject.parseObject(objStyle.toString()).getString("formatter");
									if(formatter!=null && (formatter.equals("yyyy-MM-dd") || formatter.equals("yyyy-MM-dd HH:mm:ss"))){
										mainVal=DateUtil.dateFormat(mainVal, formatter);
									}
								}
								
								cellObject.put("value", mainVal);//填充值
							}else{
								//没有列对象，插入数据
								rowObject.put(String.valueOf(cellIndex),JSONObject.parseObject("{ \"value\": \""+mainVal+"\", \"style\": {}}"));
							}
							
						}
					}
				}
			}
			
			/**
			 * 明细表数据填充
			 */
			List<Map<String,Object>> detailList=(List<Map<String, Object>>) mainMap.get("DETAIL");
			if(beginStr!=null && !beginStr.equals("") && endStr!=null && !endStr.equals("") && detailList!=null && detailList.size()>0){
				
				int detailBegin=Integer.parseInt(beginStr)-1;//模板开始行，下标从0开始
				int detailEnd=Integer.parseInt(endStr)-1;//模板结束行，下标从0开始
				
				int targetBegin=detailBegin;//目标开始行
				int targetEnd=detailEnd;//目标开始
				if(pageIndex>1){
					targetBegin=targetBegin+(pageIndex-1)*tempRowCount;
					targetEnd=targetEnd+(pageIndex-1)*tempRowCount;
				}
				
				int detailPage=1;
				int pageSize=detailEnd+1-detailBegin;//明细数据页大小
				pageNum =(detailList.size()  +  pageSize-1) / pageSize; //计算总页数， (total  +  pageSize  - 1) / pageSize;
				
				/*
				 * 开始保存数据和格式
				 * 明细数据如果大于1页，保存带主表数据的模板，用于明细分页的时候复制 
				 */
				String tableEmpty="{}";//复制表格的时候，第一页的明细数据会写进去，所以转成string
				if(pageNum>1){
					JSONObject tableEmptyObj=JSONObject.parseObject(tableEmpty);
					for(int i=pageIndex*tempRowCount-tempRowCount;i<pageIndex*tempRowCount;i++){
						if(dataTable.getJSONObject(String.valueOf(i))!=null){
							tableEmptyObj.put(String.valueOf(i), dataTable.getJSONObject(String.valueOf(i)));
						}
					}
					tableEmpty=tableEmptyObj.toJSONString();
					tableEmptyObj=null;
				}
				
				
				for(Map<String,Object> detailMap :detailList){
					
					if(targetBegin>targetEnd){
						
						//修改单据的总页数
						if(paraMap.get("001")!=null && !paraMap.get("001").equals("")){
							updateSpreadJsPageNum(paraMap,dataTable,pageNum,pageIndex,tempRowCount);
						}
						
						pageIndex++;//总页数
						detailPage++;//单据页数
						
						targetBegin=tempRowCount*(pageIndex-1)+detailBegin;//新的页面目标开始行定位
						targetEnd=tempRowCount*(pageIndex-1)+detailEnd;//新的页面目标结束行定位
						
						copySpreadTable(JSONObject.parseObject(tableEmpty),dataTable,rowsArray,columnArray,spanArray,pageIndex,detailPage,tempRowCount,tempColumnCount,spanCount);
						
					}
					
					JSONObject rowObject=dataTable.getJSONObject(String.valueOf(targetBegin));//横坐标
					if(rowObject==null){
						//没有行对象，插入列数据
						rowObject=JSONObject.parseObject("{ \"0\": { \"value\": \"\", \"style\": {} } }");
						dataTable.put(String.valueOf(targetBegin), rowObject);
					}
					
					if(detailMap!=null && detailMap.size()>0){
						for(Map.Entry<String, Object> entry:detailMap.entrySet()){ 
							
							if(entry.getKey()!=null && !entry.getKey().equals("")){
								String paraVal=paraMap.get(entry.getKey().toLowerCase());
								if(paraVal!=null && !paraVal.equals("")){
									
									int cellIndex=Integer.parseInt(paraVal)-1;//下标从0开始
									JSONObject cellObject=rowObject.getJSONObject(String.valueOf(cellIndex));//纵坐标
									Object detailVal=entry.getValue()==null?"":entry.getValue();
									
									if(cellObject!=null){
										//格式化日期、时间格式
										Object objStyle=cellObject.get("style");
										if(objStyle!=null && objStyle instanceof JSONObject){									
											String formatter=JSONObject.parseObject(objStyle.toString()).getString("formatter");
											if(formatter!=null && (formatter.equals("yyyy-MM-dd") || formatter.equals("yyyy-MM-dd HH:mm:ss"))){
												detailVal=DateUtil.dateFormat(detailVal, formatter);
											}
										}
										
										cellObject.put("value", detailVal);//填充值
									}else{
										//没有列对象，插入数据
										rowObject.put(String.valueOf(cellIndex),JSONObject.parseObject("{ \"value\": \""+detailVal+"\", \"style\": {}}"));
									}
								}
							}
						}
					}
					
					targetBegin++;//目标开始行
				}
				
				/**
				 * 清除空行
				 */
				if(paraMap.get("013")!=null && paraMap.get("013").equals("是") && pageNum*pageSize>detailList.size()){
					JSONArray rowAll =sheetObject.getJSONArray("rows");
					int empNumber=pageNum*pageSize-detailList.size();//空行数
					
					if(rowAll!=null && rowAll.size()>0){
						int rowCount=rowAll.size();
						int empEnd=detailEnd+pageIndex*tempRowCount-tempRowCount;//明细结束行+总页数*模板总行数-模板总行数
						if(empEnd>rowCount){
							//如果最后一行没有编辑，数组不会有数据，以数组大小为准，从最后行开始替换
							empEnd=rowCount-1;
						}
						
						//防止数组越界
						if(empEnd<=rowAll.size()){
							for(int i=0;i<empNumber;i++){
								
								//从最后行开始设置
								JSONObject rowObject=JSONObject.parseObject(rowAll.getString(empEnd-i));
								if(rowObject==null){
									rowAll.add(JSONObject.parseObject("{\"visible\":false}"));
								}else{
									rowObject.put("visible", false);
									rowAll.set(empEnd-i, rowObject);
								}
							}
						}
					}
				}
				
			}
			
			//修改单据的总页数
			if(paraMap.get("001")!=null && !paraMap.get("001").equals("")){
				updateSpreadJsPageNum(paraMap,dataTable,pageNum,pageIndex,tempRowCount);
			}
			
			
			pageIndex++;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return pageIndex;
	}
	
	private void copySpreadTable(JSONObject tableNull,JSONObject dataTable,JSONArray rowsArray,JSONArray columnArray,JSONArray spanArray,int pageIndex,int detailPage,int tempRowCount,int tempColumnCount,int spanCount) throws Exception{

		//主表复制，传0，用总页大小
		if(detailPage==0)detailPage=pageIndex;
		/**
		 * 开始复制数据
		 */
		//（数据:dataTable）复制模板新的空白行和主表数据，到目标行
		if(dataTable!=null){
			for (String row : tableNull.keySet()) {
				int newRow=Integer.parseInt(row)+tempRowCount*(detailPage-1);
				String rowStr=tableNull.getString(row);
				rowStr=rowStr.replace("&当前页数&", String.valueOf(detailPage));
				
				if(rowStr.indexOf("formula")!=1){
					//替换公式里面的行号
					rowStr=replaceFormula(rowStr,tempRowCount*(detailPage-1));
				}
				dataTable.put(String.valueOf(newRow), JSONObject.parseObject(rowStr));
			}
		}
		
		
		//（行:rows）复制模板新的行格式以及强制分页
		if(rowsArray!=null && rowsArray.size()>0){
			
			for(int i=0;i<tempRowCount;i++){
				JSONObject rowObject=JSONObject.parseObject(rowsArray.getString(i));
				
				if(i==0){
					if(rowObject==null){
						rowsArray.add(JSONObject.parseObject("{\"pageBreak\":true}"));
					}else{
						rowObject.put("pageBreak", true);
						rowsArray.add(rowObject);
					}
					
				}else{
					if(rowObject!=null)rowObject.remove("visible");//复制时不隐藏，清除空行时使用
					rowsArray.add(rowObject);
				}
				
			}
		}
		
		//（列:columns）复制模板新的列格式以及强制分页
		if(columnArray!=null && columnArray.size()>0){
			
			for(int i=0;i<tempColumnCount;i++){
				columnArray.add(JSONObject.parseObject(columnArray.getString(i)));
			}
		}
		
		//（单元格:rowspan、colspan）复制模板新的单元格的合并格式
		if(spanArray!=null && spanArray.size()>0){
			
			for(int i=0;i<spanCount;i++){
				JSONObject rowObj=JSONObject.parseObject(spanArray.getString(i));
				if(rowObj!=null){
					int rowIndex=rowObj.getInteger("row")+tempRowCount*(pageIndex-1);
					rowObj.put("row", rowIndex);
				}
				spanArray.add(rowObj);
			}
		}
		
	}
	
	//修改单据的总页数
	private void updateSpreadJsPageNum(Map<String,String> paraMap,JSONObject dataTable,int pageNum,int pageIndex,int tempRowCount){
		
		int rowIndex=Integer.parseInt(paraMap.get("001").split(",")[0])-1;
		if(pageIndex>1){
			//如果不是第一页，需要重新计算行号
			rowIndex=rowIndex+(pageIndex-1)*tempRowCount;
		}
		JSONObject rowObject=dataTable.getJSONObject(String.valueOf(rowIndex));//横坐标
		JSONObject cellObject=rowObject.getJSONObject(String.valueOf(Integer.parseInt(paraMap.get("001").split(",")[1])-1));//纵坐标
		cellObject.put("value", pageNum);
	}


	
	/**
	 * spreadJS打印（主从表批量打印）
	 * 根据list合并成一个主从表的json串
	 * selMap查询打印参数，打印模板的条件
	 * mainList主表数据
	 * detailList明细表数据
	 */
	public String getBatchListByPrintTemplateJson(Map<String,Object> selMap,List<Map<String,Object>> mainList,List<Map<String,Object>> detailList)throws DataAccessException{
		
		if(selMap==null || selMap.get("template_code")==null || selMap.get("template_code").equals("")){
			return "{error:\"没有打印模板编码参数[template_code]！\"}";
		}
		
		if(selMap==null || selMap.get("use_id")==null || selMap.get("use_id").equals("")){
			selMap.put("use_id", 0);//统一打印
		}
		
		//查询打印模板参数
		Map<String,String> paraMap=new HashMap<String,String>();
		try{
			paraMap=querySuperPrintParaByList(selMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		if(null==paraMap || paraMap.size()==0){
			return "{error:\"没有维护打印参数！\"}";
		}
		
		//明细开始行
		String beginStr=paraMap.get("004");
		//明细结束行
		String endStr=paraMap.get("005");
		//打印次数
		String para011=paraMap.get("011");
		String para011Key=paraMap.get("999999").toUpperCase();//取打印次数业务表字段
		
		int pageSize=0;
		if(beginStr!=null && !beginStr.equals("") && endStr!=null && !endStr.equals("") && detailList!=null && detailList.size()>0){
			pageSize=Integer.parseInt(endStr)-Integer.parseInt(beginStr)+1;//明细数据页大小
		}
		
		int page=0;//模板页数
		
		StringBuilder viewJson=new StringBuilder();
		//{"Rows":[{key:value,detail:[{key,value}]}]}
		viewJson.append("{\"Rows\":[");
				
		if(mainList!=null && mainList.size()>0){
			
			//查询打印次数
			List<Map<String, Object>> printCountList=new ArrayList<Map<String, Object>>();
			if(para011!=null && !para011.equals("")){
				String print_business_no="";
				for(Map<String, Object> mainMap : mainList){ 
					if(mainMap.get(para011Key)!=null && !mainMap.get(para011Key).equals("")){
						print_business_no=print_business_no+"'"+mainMap.get(para011Key)+"',";
					}
				}
				if(!print_business_no.equals("")){
					print_business_no=print_business_no.substring(0,print_business_no.length()-1);
					selMap.put("print_business_no", print_business_no);
					printCountList=superPrintMapper.querySuperPrintCountBatch(selMap);
				}
				
			}
					
			//主表数据填充
			for (Map<String, Object> mainMap : mainList) {
				
				page++;				
				//拼装主表信息
				viewJson.append("{");
				for(Map.Entry<String, Object> entry1:mainMap.entrySet()){
					viewJson.append("\""+entry1.getKey().toLowerCase()+"\":\""+entry1.getValue()+"\",");
				}
				
				//打印次数
				if(para011!=null && !para011.equals("")){
					if(mainMap.get(para011Key)!=null && !mainMap.get(para011Key).equals("")){
						int printCount=getPrintCount(mainMap.get(para011Key).toString(),printCountList);
						viewJson.append("\"011\":"+printCount+",");
					}
				}
				
				viewJson.append("\"detail\":[");
				if(pageSize>0){
					
					int detailIndex=0;
					//拼装明细表信息
					for (Map<String, Object> detailMap : detailList) {
						//主表ID与明细表ID一致
						
						if(mainMap.get("ID").toString().equalsIgnoreCase(detailMap.get("ID").toString())){
							
							if(detailIndex>pageSize-1){
								//超过明细表格，模板页数+1
								detailIndex=0;
								page++;
							}
							viewJson.append("{");
							for(Map.Entry<String, Object> entry2:detailMap.entrySet()){
								viewJson.append("\""+entry2.getKey().toLowerCase()+"\":\""+entry2.getValue()+"\",");
							}
							viewJson.setCharAt(viewJson.length() - 1, '}'); //替换最后一个逗号
							viewJson.append(",");
							detailIndex++;
						}
						
					}
					viewJson.setCharAt(viewJson.length() - 1, ' '); //替换最后一个逗号
				}
				viewJson.append("]},");
			}
			viewJson.setCharAt(viewJson.length() - 1, ' '); //替换最后一个逗号
		}
		
		//取打印参数
		viewJson.append("],\"para\":{");
		for (Map.Entry<String, String> entry:paraMap.entrySet()){
			
			//是否套打
			if(entry.getKey().equals("003")){
				String para003=entry.getValue();
				//根据页面传参判断
				if(selMap.get("isSetPrint")!=null){
					if(selMap.get("isSetPrint").toString().equals("true")){
						para003="是";
					}else if(selMap.get("isSetPrint").toString().equals("false")){
						para003="否";
					}
				}
				viewJson.append("\"003\":\""+para003+"\",");
			}else{
				viewJson.append("\""+entry.getKey()+"\":\""+entry.getValue()+"\",");
			}
			
			
		}
		viewJson.setCharAt(viewJson.length() - 1, ' '); //替换最后一个逗号
		
		viewJson.append("},\"page\":"+page+"}");
		// logger.debug(viewJson);
		return viewJson.toString();
	}
	
	//根据key获取打印次数
	private int getPrintCount(String print_business_no,List<Map<String, Object>> printCountList){
		
		int printCount=1;
		if(printCountList!=null && printCountList.size()>0){
			for(Map<String, Object> map:printCountList){
				if(map.get("business_no")!=null && map.get("business_no").toString().equalsIgnoreCase(print_business_no)){
					printCount=Integer.parseInt(map.get("print_count").toString())+1;
					break;
				}
			}
		}
		return printCount;
	}
	
	//保存打印次数
	public String savePrintCount(List<Map<String,Object>> list){
		
		try{
			superPrintMapper.savePrintCount(list);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return "{\"state\":\"true\"}";
	}
}
