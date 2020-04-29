package com.chd.hrp.cost.serviceImpl.report;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.cost.dao.report.CostSuperReportDataSetMapper;
import com.chd.hrp.cost.dao.report.CostSuperReportDesignMapper;
import com.chd.hrp.cost.dao.report.CostSuperReportEngineMapper;
import com.chd.hrp.cost.dao.report.CostSuperReportManagerMapper;
import com.chd.hrp.cost.entity.report.CostRepDefine;
import com.chd.hrp.cost.entity.report.CostRepDefinePara;
import com.chd.hrp.cost.entity.report.CostRepInstance;
import com.chd.hrp.cost.service.report.CostSuperReportEngineService;

/**
 * 超级报表引擎类
 * @author ADMINISTRATOR
 *
 */
@Service("costSuperReportEngineService")
public class CostSuperReportEngineServiceImpl implements CostSuperReportEngineService{

	private static Logger logger = Logger.getLogger(CostSuperReportEngineServiceImpl.class);
	
	@Resource(name = "costSuperReportEngineMapper")
	private final CostSuperReportEngineMapper costSuperReportEngineMapper = null;
	
	@Resource(name = "costSuperReportDesignMapper")
	private final CostSuperReportDesignMapper costSuperReportDesignMapper = null;
	
	@Resource(name = "costSuperReportManagerMapper")
	private final CostSuperReportManagerMapper costSuperReportManagerMapper = null;
	@Resource(name = "costSuperReportDataSetMapper")
	private final CostSuperReportDataSetMapper costSuperReportDataSetMapper = null;
	
	private final Pattern patNum = Pattern.compile("^[0-9]+(.[0-9]+)?$");
	
	//根据系统编码查询报表，根据报表要求按数据权限过滤
	@Override
	public String queryAccSuperReportByPerm(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");
		List<CostRepDefine> list = new ArrayList<CostRepDefine>();
		list=costSuperReportEngineMapper.queryAccSuperReportByPerm(map);
		
		if(map.get("mod_code") != null && "01".equals(map.get("mod_code"))){
			//拼装顶级（新旧制度）,报表编码输入2会导致id重复，前面加特殊字符
			sb.append("{id:\"~!@#2\",pId:\"top\",name:\"政府会计制度（新）\",title:\"政府会计制度（新）\",open:false},");
			sb.append("{id:\"~!@#1\",pId:\"top\",name:\"医院财务制度（旧）\",title:\"医院财务制度（旧）\",open:false},");
		}
		
		//拼装级次
		if (list!=null && list.size()>0) {
			Map<String,Object> pidMap=new HashMap<String,Object>();
			int row = 0;
			for (CostRepDefine rep : list) {
				
				if(pidMap.size()==0 || pidMap.get(rep.getReport_attr() + rep.getReport_group())==null){
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					pidMap.put(rep.getReport_attr() + rep.getReport_group(), "t_"+row);
					sb.append("id:\"t_"+row+"\",");
					if(rep.getMod_code() != null && "01".equals(rep.getMod_code())){
						sb.append("pId:\"~!@#"+rep.getReport_attr()+"\",");
					}else{
						sb.append("pId:\"top\",");
					}
					sb.append("title:\"" + rep.getReport_group() + "\",");
					sb.append("name:\"" + rep.getReport_group()+ "\",");
					sb.append("open:false");
					sb.append("}");
				}
				
			}
			
			for (CostRepDefine rep : list) {
				sb.append(",{");
				sb.append("id:\"" + rep.getReport_code() + "\",");
				sb.append("pId:\"" + pidMap.get(rep.getReport_attr() + rep.getReport_group()) + "\",");
				sb.append("name:\"" + rep.getReport_name() + "\",");
				sb.append("report_type:\"" + rep.getReport_type() + "\",");
				sb.append("title:\"" + rep.getReport_code() + "\",");
				sb.append("is_write:\"" + rep.getIs_write() + "\",");
				sb.append("report_group:\"" + rep.getReport_group() + "\"");
				sb.append("}");
			}
		}
		
		
		sb.append("]}");
		return sb.toString();
	}

	//根据报表编码查询报表实例数据（查询操作、点击报表操作都走此方法）
	@Override
	public String querySuperReportInstance(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		String content="{}";
		try {
			
			if(map.get("operation").toString().equals("instance")){
				//查询报表实例数据
				CostRepInstance  repInstance=costSuperReportEngineMapper.querySuperReportInstance(map);
				if(null!=repInstance && repInstance.getContent()!=null && repInstance.getContent().length()>0){
					//content=new String(repInstance.getContent(),"GBK");
					content=repInstance.getContent();
				}else{
					map.put("operation","define");
					//return "{\"state\":\"no instance\"}";
				}
				
			}
			
			if(map.get("operation").toString().equals("define")){
				//查询报表定义模板
				CostRepDefine repDefine=costSuperReportDesignMapper.querySuperReportContentByCode(map);
				if(null!=repDefine && repDefine.getContent()!=null && repDefine.getContent().length()>0){
					//content=new String(repDefine.getContent(),"GBK");
					content=repDefine.getContent();
				}
				
			}
		
		} catch (Exception e) {
				// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
		}
		
		return content;
	}

	//根据报表模板生成报表实例数据
	@Override
	public String saveSuperReportInstance(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		if(MyConfig.getSysPara("042").equals("1") && map.get("mod_code")!=null && map.get("mod_code").toString().equals("01") &&
				map.get("acc_year")!=null && !map.get("acc_year").toString().equals("0000") && !map.get("acc_year").toString().equals("") && 
				map.get("acc_month")!=null && !map.get("acc_month").toString().equals("")){
			//财务系统
			String accYear=map.get("acc_year").toString();
			String accMonth=map.get("acc_month").toString();//01-13月份、14第一季度、15第二季度、16第三季度、17第四季度、18上半年、19下半、20全年
			if(accMonth.equals("12") || accMonth.equals("17") || accMonth.equals("19") || accMonth.equals("20")){
				if(Integer.parseInt(accYear+"12")<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
					return "{\"error\":\"会计期间：【" + accYear + "12月】已结账.\"}";
				}
			}else if(accMonth.equals("06") || accMonth.equals("15") || accMonth.equals("18")){
				if(Integer.parseInt(accYear+"06")<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
					return "{\"error\":\"会计期间：【" + accYear + "06月】已结账.\"}";
				}
			}else if(accMonth.equals("09") || accMonth.equals("16")){
				if(Integer.parseInt(accYear+"09")<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
					return "{\"error\":\"会计期间：【" + accYear + "09月】已结账.\"}";
				}
			}else if(accMonth.equals("03") || accMonth.equals("14")){
				if(Integer.parseInt(accYear+"03")<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
					return "{\"error\":\"会计期间：【" + accYear + "03月】已结账.\"}";
				}
			}else{
				if(Integer.parseInt(accYear+accMonth)<Integer.parseInt(MyConfig.getAccYearMonth().getCurYearMonthAcc())){
					return "{\"error\":\"会计期间：【" + accYear + accMonth+"月】已结账.\"}";
				}
			}
		}
		
			
		//查询报表定义表
		CostRepDefine repDefine=costSuperReportDesignMapper.querySuperReportByCode(map);		
		
		String content="{}";
		if(null==repDefine || repDefine.getContent()==null ||repDefine.getContent().length()==0){
			return content;
		}
		
		try {
			
			//content=new String(repDefine.getContent(),"GBK");
			content=repDefine.getContent();
			JSONObject json=JSONObject.parseObject(content);
			JSONObject jsonSheet=JSONObject.parseObject(json.getString("sheets"));
			
			if(jsonSheet==null || jsonSheet.size()==0){
				return content;
			}
			 
			//循环遍历sheet
			for (String sheet : jsonSheet.keySet()) {  
				JSONObject sheetObject=jsonSheet.getJSONObject(sheet);
				superReportInstanceParsing(map,sheetObject);
				superReportInstanceParsing2(map,sheetObject);
			}
			json.put("sheets", jsonSheet);
			content=json.toJSONString();
			
			//删除报表实例数据
			costSuperReportEngineMapper.deleteSuperReportInstance(map);
			
			//添加报表实例数据，暂时不保存，等页面计算完之后再保存，为了取报表单元格的数据
			map.put("content", "");
			map.put("report_name", repDefine.getReport_name());
			map.put("report_type", repDefine.getReport_type());
			map.put("user_id", SessionManager.getUserId());
			map.put("create_date",DateUtil.getCurrenDate());
			costSuperReportEngineMapper.insertSuperReportInstance(map);			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
		
		return content;
	}
	
	//解析json对象,填充数据 
	private void superReportInstanceParsing(Map<String, Object> map,JSONObject sheetObject)throws Exception{
		
		
//		/*****************遍历标记,解析函数、存储过程******************************/
//		JSONArray commentArray=JSONObject.parseArray(sheetObject.getString("comments"));
//		Map<String,Object> cellData=new HashMap<String,Object>();
//	
//		if(commentArray!=null && commentArray.size()>0){
//			
//			for(int i=0;i<commentArray.size();i++){
//				JSONObject comment = JSONObject.parseObject(commentArray.getString(i));
//				String comText = comment.getString("text");
//				
//				//没有执行对象跳过(${代表函数，#{代表存储过程)
//				if(comText==null || comText.equals("") || (comText.indexOf("${")==-1 && comText.indexOf("#{")==-1)){
//					continue;
//				}
//				
//				//替换系统参数的值
//				comText=comText.replace("true","1").replace("false","0");
//				comText=comText.replace("本年度",map.get("acc_year").toString());
//				comText=comText.replace("本期间",map.get("acc_month").toString());
//						
//				//存储单元格执行完函数或存储过程的值
//				String calStr=calculateReportEle(comText);
//				if(calStr.indexOf("+")!=-1 || calStr.indexOf("-")!=-1 || calStr.indexOf("*")!=-1 || calStr.indexOf("/")!=-1){
//					//计算运算公式
//					ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript"); 
//					cellData.put("R"+comment.getString("rowIndex")+"C"+comment.getString("colIndex"), jse.eval(calStr));
//				}else{
//					cellData.put("R"+comment.getString("rowIndex")+"C"+comment.getString("colIndex"), calStr);
//				}
//				
//			}
//		}
	
		
		
		/***********************遍历cell单元格，填充值**************************************/
		JSONObject data=sheetObject.getJSONObject("data");
		if(data==null){
			return;
		}
		JSONObject dataTable=data.getJSONObject("dataTable");
		if(dataTable!=null && dataTable.size()>0){
			
			//遍历行
			for (String row : dataTable.keySet()) {  
				JSONObject rowObject=dataTable.getJSONObject(row);
				
				//遍历列
				for (String cell : rowObject.keySet()) {
					JSONObject cellObject=rowObject.getJSONObject(cell);
					String cellFormula=cellObject.getString("formula");
					//String cellText=cellObject.getString("value");
					
					if(cellFormula!=null && !cellFormula.equals("")){
					//if(cellText!=null && cellText.indexOf("${")!=-1){
						cellObject.put("value", "");
						cellFormula=cellFormula.trim();
						if(cellFormula.indexOf("RES(")!=-1){
							try{
								//替换单元格内的系统元素
								cellFormula=replaceCellSystemEle(cellFormula,map);
								cellObject.put("formula", cellFormula);
								continue;
							}catch(Exception e){
								logger.error(e.getMessage(), e);
								throw new SysException((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1)+"单元格计算错误，请检查该单元格的公式！");
								//cellObject.put("value", "公式错误");
							}	
						}
						
						
						//替换系统参数的值
						cellFormula=cellFormula.replace("true","1").replace("false","0");
						cellFormula=cellFormula.replace("本集团",SessionManager.getGroupId());
						cellFormula=cellFormula.replace("本医院",SessionManager.getHosId());
						cellFormula=cellFormula.replace("本账套",SessionManager.getCopyCode());
						cellFormula=replaceYearMonth(map,cellFormula);//替换年度、期间
						
						try{
							
							//函数和存储过程计算完后，替换单元格
							String oldCellFormula=cellFormula;
							cellFormula=calculateReportEle(cellFormula);
							
							try{
								if(cellFormula.indexOf("+")!=-1 || cellFormula.indexOf("-")!=-1 || cellFormula.indexOf("*")!=-1 || cellFormula.indexOf("/")!=-1){
									//计算运算公式
									ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
									cellObject.put("value", jse.eval(cellFormula));
									cellObject.put("formula", "");
								}else{
									//cellObject.put("value",cellFormula);
									  cellObject.put("formula", cellFormula);
									
								}
							}catch(Exception e){
								//cellObject.put("value", cellFormula+"，该字符串不能参与计算");
								logger.error("公式："+oldCellFormula+"，执行后："+cellFormula+"，该字符串不能参与计算");
								cellObject.put("formula", cellFormula);
							}	
							
							
						}catch(Exception e){
							logger.error(e.getMessage(), e);
							throw new SysException((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1)+"单元格计算错误，请检查该单元格的公式！");
							//cellObject.put("value", "公式错误");
						}	
							
					}
						
				}
			  
			}
			//清除焦点区域
			sheetObject.remove("selections");

		}
	}
	private Map<String,Object> getDataSetNameList(JSONObject sheetObject){
		Map<String,Object> sfMap=new HashMap<String,Object>();
		
		 Map<Integer, String> bodyfMap = new TreeMap<Integer, String>(
			new Comparator<Integer>() {
			public int compare(Integer obj1, Integer obj2) {
				return obj1.compareTo(obj2);
			}
		});
		
		List<Integer> bodyList=new ArrayList<Integer>();
		JSONObject data=sheetObject.getJSONObject("data");   
		JSONObject dataTable=data.getJSONObject("dataTable");
		JSONArray commontsArray=sheetObject.getJSONArray("comments");
		if(commontsArray==null||commontsArray.size()<1)
			return null;
		JSONObject rowObject=null;
		JSONObject colObject=null;
		JSONObject tmpJson=null;
		String value=null;
		for(int i=0;i<commontsArray.size();i++){
			tmpJson=commontsArray.getJSONObject(i);
			
			if(tmpJson.getString("text") != null&& tmpJson.getString("text").equals("{page.body}")&&!bodyList.contains(tmpJson.getInteger("rowIndex"))){
			
				bodyList.add(tmpJson.getInteger("rowIndex"));
				rowObject=dataTable.getJSONObject(tmpJson.getString("rowIndex"));
				for (String cell : rowObject.keySet()) {
					colObject=rowObject.getJSONObject(cell); 
					value=colObject.getString("value");
					if(value!=null&&value.startsWith("{")&&value.indexOf("[")!=-1&&value.indexOf(".")!=-1){
						bodyfMap.put(tmpJson.getInteger("rowIndex"),value.substring(value.indexOf("[")+1,value.indexOf(".")));
						break;
					}
				}
			}
			 
		}
		sfMap.put("body", bodyfMap);
		return sfMap;
	}
	private void setDsValue(List<Map<String,Object>> lmap,int startRow,JSONObject sheetObject){
		JSONObject data=sheetObject.getJSONObject("data");
		if(data==null){
			return;
		}
		JSONObject dataTable=data.getJSONObject("dataTable");
		Object dvalue=null;
		Map<String,Object> dataMap=null;
		if(dataTable!=null && dataTable.size()>0){
			for (int i=0;i<lmap.size();i++) {  
					JSONObject rowObject=dataTable.getJSONObject((startRow+i)+"");
					dataMap=lmap.get(i);
					//获取数据集名称及对应数据源
					for (String cell : rowObject.keySet()) {
						JSONObject cellObject=rowObject.getJSONObject(cell);
						
						String cellValue=cellObject.getString("value");
						if(cellValue!=null&&cellValue.indexOf("[")!=-1&&cellValue.indexOf("]")!=-1){
							cellValue=cellValue.substring(cellValue.indexOf("[")+1,cellValue.indexOf("]"));
						}else{
							continue;
						}
						if(cellValue==null||cellValue.split("\\.").length!=2)
							continue;
						dvalue=dataMap.get(cellValue.split("\\.")[1].toUpperCase());
						if(dvalue==null)
							dvalue="";
						cellObject.put("value",dvalue.toString());
						
						
					}
					//执行数据集，获取数据集结果列表进行填充cellvalue
				}
				
			}
		logger.debug(JSONObject.toJSONString(data));
	}
	public static char backchar(int backnum) {
        char strChar = (char) backnum;
        return strChar;
    }
	private void superReportInstanceParsing2(Map<String, Object> map,JSONObject sheetObject)throws Exception{
		 
		//获取数据集名称
		 
		Map<String,Object> sfMap=getDataSetNameList(sheetObject);
		if(sfMap==null||sfMap.get("body")==null)
			return;
		Map<Integer,String> bodyfMap=(Map<Integer,String>)sfMap.get("body");
		int preAddRow=0;
		int preAddRow1=0;
		List<Map<String,Object>> dataList=null;
		Map<String,String> dsmap=new HashMap<String,String>();
		if(bodyfMap!=null){
			for(Map.Entry<Integer, String> entry : bodyfMap.entrySet()){
				dataList=getDataSet(entry.getValue());//需判断数据集类型，目前支持sql与函数
				dsmap.put(entry.getValue(), (entry.getKey()+preAddRow1)+"_"+dataList.size()+"_"+entry.getKey());
				preAddRow1+=dataList.size()-1;	
			}
			parseFormula(sheetObject,dsmap);
		
			for(Map.Entry<Integer, String> entry : bodyfMap.entrySet()){
				dataList=getDataSet(entry.getValue());//需判断数据集类型，目前支持sql与函数
				//if(dataList!=null&&dataList.size()>0){
					addSheetRow(sheetObject,entry.getKey()+preAddRow,dataList);
					setDsValue(dataList,preAddRow+entry.getKey(),sheetObject); 
					preAddRow+=dataList.size()-1;	
				//}
				
			}
		}
		sheetObject.put("comments",null);
		sheetObject.remove("selections");
		
	}
	public String ClobToString(Clob clob) throws SQLException, IOException {
		 
		String reString =null;
		if(clob==null)
			return reString;
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		sb.append(s);
		s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}
	public String getCellName(JSONObject sheetJson,String dsCell){
		JSONObject tmp=sheetJson.getJSONObject("data");
		tmp=tmp.getJSONObject("dataTable");
		Set<String> set=tmp.keySet();
		String r=null;
		for(String row:set){
			JSONObject rowObject=tmp.getJSONObject(row);
			for (String cell : rowObject.keySet()) {
				JSONObject cellObject=rowObject.getJSONObject(cell);	
				String value=cellObject.getString("value");
				if(value!=null&&value.indexOf(dsCell)!=-1){
					r=(Integer.parseInt(row)+1)+","+(Integer.parseInt(cell)+1);
					break;
				}
			}
			if(r!=null){
				break;
			}
		}
		return r;
	}
	public void parseFormula(JSONObject sheetJson,Map<String,String> dsmap){
		JSONObject tmp=sheetJson.getJSONObject("data");
		tmp=tmp.getJSONObject("dataTable");
		Set<String> set=tmp.keySet();
		for(String row:set){//替换sum公式
			JSONObject rowObject=tmp.getJSONObject((row)+"");
			for (String cell : rowObject.keySet()) {
				JSONObject cellObject=rowObject.getJSONObject(cell);								
				String formula=cellObject.getString("formula");		
				if(formula!=null&&formula.startsWith("SUMDS")){//求和公式翻译	
					formula=formula.substring(6,formula.length()-1);
					String[] fs=formula.split(":");
					String newformula="";
					for(int i=0;i<fs.length;i++){
						String c=getCellName(sheetJson,fs[i]);
						String[] cs=c.split(",");
						if(cs!=null&&cs.length==2){
							String dsCode=fs[i].substring(0,fs[i].indexOf("."));
							char colum=(char)(Integer.parseInt(cs[1])+64);
							String[] dsData=dsmap.get(dsCode).split("_");
							newformula+=colum+""+(Integer.parseInt(dsData[0])+1)+":"+colum+(Integer.parseInt(dsData[0])+(Integer.parseInt(dsData[1])==0?1:Integer.parseInt(dsData[1])))+":";
						}
					}
					if(newformula.endsWith(":"))
						newformula=newformula.substring(0,newformula.length()-1);
					cellObject.put("formula", "sum("+newformula+")");
				}else if(formula!=null&&isTransferFormula(formula)){
					String newf=getNewFormula(formula,dsmap);
					logger.debug("原函数="+formula+" 新函数="+newf);
					cellObject.put("formula", newf); 
				}
			}
		}
	}
	public String getNewFormula(String formula,Map<String,String> dsmap){
		String regex="[A-Z]\\d+";
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(formula);	
		List<String> list=new ArrayList<String>();
		int start=0;
		while(m.find()){
			if(m.start()==0){
				list.add("");
			}else{
				list.add(formula.substring(start,m.start()));
			}
			list.add(m.group(0));
			start=m.end();		
		}
		if(start!=formula.length()){
			list.add(formula.substring(start));
		}
		String newS=null;
		for(int i=0;i<list.size();i++){
			if(i%2==1){
				newS=cacuRows(list.get(i),dsmap);
				list.set(i, newS);
			}
		}
		String nf="";
		for(String a:list){
			nf+=a;
		}
		return nf;
//		int newRow=0;
//		int oldRow=0;
//		String tmp=null;
//		String[] s=formula.split("\\+");
//		
//		
//		for(int i=0;i<s.length;i++){
//			nf+=s[i].substring(0,1); //nf=L			
//			oldRow=Integer.parseInt(s[i].substring(1));//oldRow=12
//			newRow=oldRow;
//			for(Map.Entry<String, String> entry : dsmap.entrySet()){
//				tmp=entry.getValue();
//				if(oldRow>Integer.parseInt(tmp.split("_")[2])){
//					newRow+=Integer.parseInt(tmp.split("_")[1])-1;
//				}
//			}
//			nf+=newRow;
//			if(i<s.length-1)
//				nf+="+";
//		}
		
	}
	public String cacuRows(String s,Map<String,String> dsmap){
		String tmp=null;
		int oldRow=Integer.parseInt(s.substring(1));//oldRow=12
		int newRow=oldRow;
		for(Map.Entry<String, String> entry : dsmap.entrySet()){
			tmp=entry.getValue();
			if(oldRow>Integer.parseInt(tmp.split("_")[2])){
				newRow+=Integer.parseInt(tmp.split("_")[1])-1;
			}
		}
		return s.substring(0,1)+newRow;
	}
	public static void main(String[] args){
		String str="L13+L15";
		isTransferFormula(str);
	}
	public static boolean isTransferFormula(String fname){
		String regex="[A-Z]\\d+";
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(fname);	
		boolean b=false;
		while(m.find()){
			b=true;
			break;
		} 
		return b;
	}
	public void addSheetRow(JSONObject sheetJson,int startRow,List<Map<String,Object>> dataList){
		int rowCount1=dataList.size();
		
		JSONArray rows=sheetJson.getJSONArray("rows");
		//0.rowCount
		Integer rc=sheetJson.getInteger("rowCount");
		sheetJson.put("rowCount", rc+rowCount1);
		//1.activeRow标签变更，设置有效行数
		sheetJson.put("activeRow", sheetJson.getIntValue("activeRow")+rowCount1-1);
		//2.rows标签设置行高，如果有数据集则添加没有则删除空行
		JSONArray cpRows=JSONObject.parseArray(rows.toJSONString());
		if(rowCount1>0){
			for(int s=1;s<dataList.size();s++){
				rows.add(s+startRow,JSONObject.parseObject(cpRows.getString(startRow)));
			}
		}else{
			rows.getJSONObject(startRow).put("visible", false);
		}
		//1.获取数据集格式列，2.变换行索引进行转化新的json，3进行复制到结果集大小
		JSONArray spans=sheetJson.getJSONArray("spans");
		//tmp1先替换现有row标签需要变更的key值
		JSONObject tmp1=null;
		JSONObject newTmp1=null;
		if(rowCount1>0){
			
			for(int i=0;i<spans.size();i++){
				tmp1=spans.getJSONObject(i);
				if(tmp1.getIntValue("row")>startRow){
					tmp1.replace("row", tmp1.getIntValue("row")+rowCount1-1);
				}
			}
			//将tmp1按数据集大小重新生成新的span元素对象，然后赋值给spans，
			JSONArray tmpSpans=JSONArray.parseArray(spans.toJSONString());
			for(int i=0;i<tmpSpans.size();i++){
				tmp1=tmpSpans.getJSONObject(i);
				if(tmp1.getIntValue("row")==startRow){
					newTmp1=JSONObject.parseObject(tmp1.toJSONString());
					for(int j=1;j<=rowCount1;j++){//加入新json
						newTmp1.replace("row", j+startRow);
						spans.add(JSONObject.parseObject(newTmp1.toJSONString()));
					}
				}
				tmp1=null;
			}
		}else{
//			for(int i=0;i<spans.size();i++){
//				tmp1=spans.getJSONObject(i);
//				if(tmp1.getIntValue("row")==startRow){
//					spans.remove(i);
//					break;
//				}
//			}
		}
		JSONObject tmp=sheetJson.getJSONObject("data");
		JSONObject newTmp=JSONObject.parseObject(tmp.toJSONString());
		tmp=tmp.getJSONObject("dataTable");
		JSONObject newDtmp=newTmp.getJSONObject("dataTable");
		
		Set<String> set=newDtmp.keySet();
		
		if(rowCount1>0){	
			for(String row:set){//扩展表格
				if(Integer.parseInt(row)>startRow){
					tmp.put((Integer.parseInt(row)+rowCount1-1)+"", JSONObject.parse(newDtmp.getJSONObject(row).toJSONString()));
				}
			}
			//logger.debug("rows1="+tmp.toJSONString());
			for(int i=1;i<rowCount1;i++){//给扩展的新行填充数据
				tmp.put((i+startRow)+"", JSONObject.parse(tmp.getJSONObject(""+startRow).toJSONString()));
			}
		}else{
//			logger.debug(tmp.getString(startRow+""));
//			tmp.remove(startRow+"");
//			for(String row:set){//扩展表格
//				if(Integer.parseInt(row)>startRow){
//					tmp.put((Integer.parseInt(row)-1)+"", JSONObject.parse(newDtmp.getJSONObject(row).toJSONString()));
//				}
//			}
		}
		logger.debug("rows2="+tmp.toJSONString());
		logger.debug("sheetJson="+sheetJson.toJSONString());
	}
	private String setSQLParameter(Map<String,Object> map,String sql){
		List<Map<String,Object>> tl=costSuperReportDataSetMapper.querySuperReportDSparaValue(map);
		String pname="";
		String pvalue="";
		String pjson=null;
		JSONObject json=null; 
		for(Map<String,Object> m:tl){
			if(m.get("PARA_NAME")==null){
				System.out.println(m.get("PARA_NAME"));
				throw new SysException("数据集参数未配置，请设置参数后在操作");
			}
			pname=m.get("PARA_NAME").toString();
			if(m.get("PARA_VALUE")==null){
				System.out.println(m.get("PARA_VALUE"));
				throw new SysException("数据集参数错误："+pname+" 未设置参数值");
			}else
				pvalue=m.get("PARA_VALUE").toString();
			if(pvalue.equals("本集团"))
				pvalue=SessionManager.getGroupId();
			else if(pvalue.equals("本医院"))
				pvalue=SessionManager.getHosId();
			else if(pvalue.equals("本账套"))
				pvalue=SessionManager.getCopyCode();
			else if(pvalue.equals("本年度"))
				pvalue=SessionManager.getAcctYear();
			pjson=m.get("PARA_JSON").toString();
			json=JSONObject.parseObject(pjson);
			if(json.getInteger("data_type")==1)
				pvalue="'"+pvalue+"'";
			sql=sql.replace("@"+pname, pvalue);
		}
		return sql;
	}
	private List<Map<String,Object>> getDataSet(String dsCode){
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("ds_code",dsCode);
		
		List<Map<String,Object>> strListData=null;
		//如果是sql或函数的执行方式不同
		List<Map<String,Object>> l=costSuperReportDataSetMapper.queryDsSql(mapVo);
		Map<String,Object> rs=null;
		if(l!=null&&l.size()==1){
			rs=l.get(0);
			if(rs.get("DS_TYPE").toString().equals("4")){
				Clob clobSql=(Clob)rs.get("SQLCONTENT");
				String sql=null;
				try {
					sql = ClobToString(clobSql);
					if(sql==null)
						return strListData;
						sql=sql.replace(";", "");
						sql=setSQLParameter(mapVo,sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				strListData=costSuperReportDataSetMapper.querySql(sql);
				logger.debug("执行函数："+"select "+sql+" fun from dual");
			}else if(rs.get("DS_TYPE").toString().equals("1")){//存储过程处理方式
				String execSql=dsCode+"('1','2')";
				strListData=costSuperReportDataSetMapper.querySql("select * from table("+execSql+")");
				logger.debug("执行函数："+"select "+execSql+" fun from dual");
			}
		}
		return strListData;
	}
	/*
	 * 递归计算单元格的公式
	 * 公式格式：REP(\"RF_ACC_QMYE\",\"2\",\"1\",\"001\",\"本年度\",\"本期间\",\"1001\",\"RMB\",\"false\")+REP(\"RF_ACC_NCYE\",\"2\",\"1\",\"001\",\"本年度\",\"本期间\",\"1001\",\"RMB\",\"false\")
	 */
	private String calculateReportEle(String formula)throws Exception{
		
		if(formula.indexOf("REP(")==-1){
			return formula;
		}
			
		String tempStr=formula.substring(formula.indexOf("REP(")+4,formula.length());//从REP(后面开始截取
		String eleType=tempStr.substring(1,tempStr.indexOf("_"));//元素类型，RF:函数，RP:存储过程，RV:视图，RZ：自定义SQL
		String eleName="";
		String elePara="";
		
		//元素有参数，通过逗号分隔
		if(tempStr.indexOf(",")!=-1){
			eleName=tempStr.substring(1,tempStr.indexOf(",")-1);//执行对象，REP("RF_ACC_QMYE","参数1")
			elePara=tempStr.substring(tempStr.indexOf(",")+1,tempStr.indexOf(")"));//执行对象的参数，\"2\",\"1\",\"001\",\"本年度\",\"本期间\",\"1001\",\"RMB\",\"false\")
		}else{
			//没有参数通过最后一个双引号分隔
			eleName=tempStr.substring(1,tempStr.lastIndexOf("\""));//执行对象，REP("RF_ACC_QMYE","参数1")
		}
		
		
		if(eleType.equalsIgnoreCase("RF")){
			
			//执行函数
			String execSql=eleName+"("+elePara.replace("\"", "'")+")";
			List<String> strListData=costSuperReportEngineMapper.querySuperReportInstanceByFun("select "+execSql+" fun from dual");
			logger.debug("执行函数："+"select "+execSql+" fun from dual");
			String resData="";
			if(strListData!=null && strListData.size()>0){
				
				if(strListData.get(0).matches("\\.\\d+$")){
					resData="0"+strListData.get(0);
				}else{
					resData=strListData.get(0);
				}	
				
				if(!isNumeric(resData)){
					resData="\""+resData+"\"";
				}	
			}
			
			if(elePara!=null && !elePara.equals("")){
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\","+elePara+")",resData));//替换值
			}else{
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\")",resData));//替换值
			}
			
			
		}else if(eleType.equalsIgnoreCase("RP")){
			
			//执行存储过程
			String execSql=eleName+"("+elePara.replace("\"", "'")+",#{reVal,mode=OUT,jdbcType=VARCHAR})";
			Map<String,Object> procData=new HashMap<String, Object>();			
			//{call ${proc_sql}}--》{call RP_ACC_QMYE('2','1','001','本年度','本期间','1001','RMB','false',#{reVal,mode=OUT,jdbcType=VARCHAR})}
			procData.put("proc_sql", execSql);
			procData.put("reVal", "");
			costSuperReportEngineMapper.querySuperReportInstanceByProc(procData);
			logger.debug("执行存储过程："+execSql);
			
			String resData="";
			if(procData.get("reVal")!=null){
				resData=procData.get("reVal").toString();
				if(!isNumeric(resData)){
					resData="\""+resData+"\"";
				}
			}
			
			if(elePara!=null && !elePara.equals("")){
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\","+elePara+")",resData));
			}else{
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\")",resData));
			}
			
			
		}else if(eleType.equalsIgnoreCase("RV")){
			
			//执行视图
			String execViewSql="select * from (select * from "+eleName+") v";//拼装视图查询SQL
			
			if(elePara!=null && !elePara.equals("")){
				String[] sqlWhereArray=elePara.replace("\"", "'").split(",");
				
				//根据元素查询参数
				Map<String,Object> viewMap=new HashMap<String,Object>();
				viewMap.put("group_id", SessionManager.getGroupId());
				viewMap.put("hosid", SessionManager.getHosId());
				viewMap.put("copy_code", SessionManager.getCopyCode());
				viewMap.put("ele_code", eleName);
				//根据元素名称查询参数
				List<CostRepDefinePara> paraList=costSuperReportDesignMapper.querySuperReportParaByEle(viewMap);
				
				if(paraList!=null && paraList.size()>0){
					if(sqlWhereArray.length!=paraList.size()){
						throw new SysException(eleName+"，视图的参数个数不一致！");
					}
					//拼装查询条件
					for(int i=0;i<paraList.size();i++){
						if(i==0){
							execViewSql=execViewSql+" where v."+paraList.get(i).getPara_code()+"='"+sqlWhereArray[i]+"'";
						}else{
							execViewSql=execViewSql+" and v."+paraList.get(i).getPara_code()+"='"+sqlWhereArray[i]+"'";
						}
						
					}
				}
				
			}
				
			List<String> strListData=costSuperReportEngineMapper.querySuperReportInstanceByFun(execViewSql);
			logger.debug("执行视图："+execViewSql);
			String resData="";
			if(strListData!=null && strListData.size()>0){
				resData=strListData.get(0);
				if(!isNumeric(resData)){
					resData="\""+resData+"\"";
				}
			}
			
			if(elePara!=null && !elePara.equals("")){
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\","+elePara+")",resData));//替换值
			}else{
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\")",resData));//替换值
			}
			
			
			
		}else if(eleType.equalsIgnoreCase("RZ")){
			
			//执行自定义SQL
			if(eleName!=null && !eleName.equals("")){
				//String execDeinfeSql="";
				//String eleQuerySql="select ele_sql from REP_DEFINE_ELE where group_id = "+SessionManager.getGroupId()+" and hos_id = "+SessionManager.getHosId()+" and copy_code = '"+SessionManager.getCopyCode()+"' and ele_code = '"+eleCode+"' ";
				String execDeinfeSql=costSuperReportManagerMapper.querySuperReportEleSql(SessionManager.getGroupId(),SessionManager.getHosId(),SessionManager.getCopyCode(),eleName);

				if(elePara!=null && !elePara.equals("")){
					String[] sqlWhere=elePara.replace("\"", "'").split(",");
					for(int i=0;i<sqlWhere.length;i++){
						execDeinfeSql=execDeinfeSql.replaceAll("(?i)@para"+(i+1), sqlWhere[i]);//替换参数
					}
				}
				
				List<String> strListData=costSuperReportEngineMapper.querySuperReportInstanceByFun(execDeinfeSql);
				logger.debug("执行自定义SQL："+execDeinfeSql);
				String resData="";
				if(strListData!=null && strListData.size()>0){
					resData=strListData.get(0);
					if(!isNumeric(resData)){
						resData="\""+resData+"\"";
					}
				}
				
				
				if(elePara!=null && !elePara.equals("")){
					formula=calculateReportEle(formula.replace("REP(\""+eleName+"\","+elePara+")",resData));//替换值
				}else{
					formula=calculateReportEle(formula.replace("REP(\""+eleName+"\")",resData));//替换值
				}
				
			}
		
		}else if(eleType.equalsIgnoreCase("RS")){
			
			//取报表期间数
			if(eleName.equalsIgnoreCase("RS_CELL_DATA")){
				if(elePara==null || elePara.equals("")){
					logger.error("取报表期间数，参数错误："+elePara);
					throw new SysException("取报表期间数，参数错误："+elePara);
				}
				
				String[] eleParaA=elePara.replace("\"", "").split(",");
				Map<String,Object> rjMap=new HashMap<String,Object>();
				rjMap.put("mod_code", eleParaA[0]);
				rjMap.put("group_id", eleParaA[1]);
				rjMap.put("hos_id", eleParaA[2]);
				rjMap.put("copy_code", eleParaA[3]);
				rjMap.put("acc_year", eleParaA[4]);
				rjMap.put("acc_month", eleParaA[5]);
				rjMap.put("report_code", eleParaA[6]);
				rjMap.put("cell_text", eleParaA[7].trim());//=Sheet1!A#1或者A#1
				
				String resData="0";
				if(eleParaA.length==9 && eleParaA[8].equals("1")){
					//是否本年累计
					int month=Integer.parseInt(eleParaA[5]);
					BigDecimal sum = new BigDecimal(0.00);
					if(month<=13){//月报
						for(int i=1;i<=month;i++){
							if(i<10){
								rjMap.put("acc_month", "0"+i);
							}else{
								rjMap.put("acc_month", i);
							}
							resData=getReportContent(rjMap);
							//不是数字
							if(!NumberUtil.isNumeric(resData)){
								break;
							}
							sum=sum.add(new BigDecimal(resData));
						}
					}else if(month>=14 && month<=17){
						//季报 { text: '第一季度', id: '14' },{ text: '第二季度', id: '15' },{ text: '第三季度', id: '16' },{ text: '第四季度', id: '17' }
						for(int i=14;i<=month;i++){
							rjMap.put("acc_month", i);
							resData=getReportContent(rjMap);
							//不是数字
							if(!NumberUtil.isNumeric(resData)){
								break;
							}
							sum=sum.add(new BigDecimal(resData));
						}
					}else if(month>=18 && month<=19){
						//半年报{ text: '上半年', id: '18' },{ text: '下半年', id: '19' }
						for(int i=18;i<=month;i++){
							rjMap.put("acc_month", i);
							resData=getReportContent(rjMap);
							//不是数字
							if(!NumberUtil.isNumeric(resData)){
								break;
							}
							sum=sum.add(new BigDecimal(resData));
						}
					}
					if(!sum.equals(BigDecimal.ZERO)){
						resData=sum.toString();
					}
				}else{
					resData=getReportContent(rjMap);
				}
				
				//不是数字
				if(!NumberUtil.isNumeric(resData)){
					resData="\""+resData+"\"";
				}
				formula=calculateReportEle(formula.replace("REP(\""+eleName+"\","+elePara+")",resData));//替换值
			}
		}
			
		return formula;
	}
	
	//保存计算完后的报表内容，为了取报表单元格的数据
	@Override
	public String saveSuperReportContent(Map<String, Object> map)throws DataAccessException {
		
		try {
			costSuperReportEngineMapper.saveSuperReportContent(map);
			return "{\"state\":\"true\"}";
		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
	}
	
	//替换单元格内的系统元素
	private String replaceCellSystemEle(String cellText,Map<String,Object> map){
		
		if(cellText.indexOf("RES(\"group_code\")")!=-1){
			cellText=cellText.replace("RES(\"group_code\")", "\""+SessionManager.getGroupCode()+"\"");
		}
		
		if(cellText.indexOf("RES(\"group_name\")")!=-1){
			cellText=cellText.replace("RES(\"group_name\")", "\""+SessionManager.getGroupName()+"\"");
		}
		
		if(cellText.indexOf("RES(\"group_simple\")")!=-1){
			cellText=cellText.replace("RES(\"group_simple\")", "\""+SessionManager.getGroupSimple()+"\"");
		}
		
		if(cellText.indexOf("RES(\"hos_code\")")!=-1){
			cellText=cellText.replace("RES(\"hos_code\")", "\""+SessionManager.getHosCode()+"\"");
		}
		
		if(cellText.indexOf("RES(\"hos_name\")")!=-1){
			cellText=cellText.replace("RES(\"hos_name\")", "\""+SessionManager.getHosName()+"\"");
		}
		
		if(cellText.indexOf("RES(\"hos_simple\")")!=-1){
			cellText=cellText.replace("RES(\"hos_simple\")", "\""+SessionManager.getHosSimple()+"\"");
		}
		
		if(cellText.indexOf("RES(\"copy_code\")")!=-1){
			cellText=cellText.replace("RES(\"copy_code\")", "\""+SessionManager.getCopyCode()+"\"");
		}
		
		if(cellText.indexOf("RES(\"copy_name\")")!=-1){
			cellText=cellText.replace("RES(\"copy_name\")", "\""+SessionManager.getCopyName()+"\"");
		}
		
		if(cellText.indexOf("RES(\"user_code\")")!=-1){
			cellText=cellText.replace("RES(\"user_code\")", "\""+SessionManager.getUserCode()+"\"");
		}
		
		if(cellText.indexOf("RES(\"user_name\")")!=-1){
			cellText=cellText.replace("RES(\"user_name\")", "\""+SessionManager.getUserName()+"\"");
		}
		
		if(cellText.indexOf("RES(\"emp_code\")")!=-1){
			cellText=cellText.replace("RES(\"emp_code\")", "\""+SessionManager.getEmpCode()+"\"");
		}
		
		if(cellText.indexOf("RES(\"emp_name\")")!=-1){
			cellText=cellText.replace("RES(\"emp_name\")}", "\""+SessionManager.getEmpName()+"\"");
		}
		
		String currDateTime=DateUtil.dateToString(new Date());//2016-11-25 11:21:25
		if(cellText.indexOf("RES(\"sys_year\")")!=-1){
			cellText=cellText.replace("RES(\"sys_year\")","\""+currDateTime.substring(0, 4)+"\"");
		}
		
		if(cellText.indexOf("RES(\"sys_month\")")!=-1){
			cellText=cellText.replace("RES(\"sys_month\")","\""+currDateTime.substring(5, 7)+"\"");
		}
		
		if(cellText.indexOf("RES(\"sys_date\")")!=-1){
			cellText=cellText.replace("RES(\"sys_date\")","\""+currDateTime.substring(0, 10)+"\"");
		}
		
		if(cellText.indexOf("RES(\"sys_time\")")!=-1){
			cellText=cellText.replace("RES(\"sys_time\")","\""+currDateTime+"\"");
		}
		
		if(cellText.indexOf("RES(\"report_year\")")!=-1){
			
			String reportPperiod="";
			if(map.get("acc_year")!=null && !map.get("acc_year").toString().equals("")){
				reportPperiod=reportPperiod+map.get("acc_year");
			}
			cellText=cellText.replace("RES(\"report_year\")","\""+reportPperiod+"\"");
		}
		
		if(cellText.indexOf("RES(\"report_period\")")!=-1){
			
			String reportPperiod="";
			if(map.get("acc_month")!=null && !map.get("acc_month").toString().equals("")){
				//14第一季度、15第二季度、16第三季度、17第四季度、18上半年、19下半年、20全年
				if(Integer.parseInt(map.get("acc_month").toString())<14)reportPperiod=reportPperiod+map.get("acc_month").toString();
				if(map.get("acc_month").toString().equals("14"))reportPperiod=reportPperiod+"第一季度";
				if(map.get("acc_month").toString().equals("15"))reportPperiod=reportPperiod+"第二季度";
				if(map.get("acc_month").toString().equals("16"))reportPperiod=reportPperiod+"第三季度";
				if(map.get("acc_month").toString().equals("17"))reportPperiod=reportPperiod+"第四季度";
				if(map.get("acc_month").toString().equals("18"))reportPperiod=reportPperiod+"上半年";
				if(map.get("acc_month").toString().equals("19"))reportPperiod=reportPperiod+"下半年";
				
			}
			cellText=cellText.replace("RES(\"report_period\")","\""+reportPperiod+"\"");
		}

		return cellText;
	}
	
	//计算上年、上两年，上期、上两期
	private String replaceYearMonth(Map<String, Object> map,String cellFormula){
		String accYear=map.get("acc_year").toString();
		String accMonth=map.get("acc_month").toString();
		
		//月报
		if(Integer.parseInt(accMonth)<14){
			
			if(cellFormula.indexOf("本期间")!=-1){
				cellFormula=cellFormula.replace("本期间",accMonth);
				
			} 
			if(cellFormula.indexOf("上期")!=-1){
				int accMonthInt=Integer.parseInt(accMonth)-1;
				
				if(accMonthInt==0){
					//当前月=1月
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","12");
				}else if(accMonthInt>0 && accMonthInt<10){
					//当前月=2月-10月
					cellFormula=cellFormula.replace("上期","0"+accMonthInt);
				}else{
					//当前月=11月-12月
					cellFormula=cellFormula.replace("上期",String.valueOf(accMonthInt));
				}
				
			} 
		    if(cellFormula.indexOf("上两期")!=-1){
				int accMonthInt=Integer.parseInt(accMonth)-2;
				
				if(accMonthInt<0){
					//当前月=1月
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上两期","11");
				}else if(accMonthInt==0){
					//当前月=2月
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上两期","12");
				}else if(accMonthInt>0 && accMonthInt<10){
					//当前月=3月-11月
					cellFormula=cellFormula.replace("上两期","0"+accMonthInt);
				}else{
					//当前月=12月
					cellFormula=cellFormula.replace("上两期",String.valueOf(accMonthInt));
				}
				
			}
		}else{
			/*
			    { text: '第一季度', id: '14' },
				{ text: '第二季度', id: '15' },
				{ text: '第三季度', id: '16' },
				{ text: '第四季度', id: '17' }
				{ text: '上半年', id: '18' },
				{ text: '下半年', id: '19' }
				{ text: '年报', id: '20' }
			 */
			if(Integer.parseInt(accMonth)==14){
				//季报，第一季度
				if(cellFormula.indexOf("上期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","17");
				}else if(cellFormula.indexOf("上两期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","16");
				}else{
					cellFormula=cellFormula.replace("本期间",accMonth);
				}
				
			}else if(Integer.parseInt(accMonth)==15){
				//季报，第二季度
				if(cellFormula.indexOf("上期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("本年度",accYear);
					cellFormula=cellFormula.replace("上期","14");
				}else if(cellFormula.indexOf("上两期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上两期","17");
				}else{
					cellFormula=cellFormula.replace("本期间",accMonth);
				}
				
			}else if(Integer.parseInt(accMonth)==16){
				//季报，第三季度
				if(cellFormula.indexOf("上期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","15");
				}else if(cellFormula.indexOf("上两期")!=-1){
					cellFormula=cellFormula.replace("上两期","14");
				}else{
					cellFormula=cellFormula.replace("本期间",accMonth);
				}
				
			}else if(Integer.parseInt(accMonth)==17){
				//季报，第四季度
				if(cellFormula.indexOf("上期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","16");
				}else if(cellFormula.indexOf("上两期")!=-1){
					cellFormula=cellFormula.replace("上两期","15");
				}else{
					cellFormula=cellFormula.replace("本期间",accMonth);
				}
				
			}else if(Integer.parseInt(accMonth)==18){
				//半年报，上半年
				if(cellFormula.indexOf("上期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","19");
				}else if(cellFormula.indexOf("上两期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-4));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("上两期","18");
				}else{
					cellFormula=cellFormula.replace("本期间",accMonth);
				}
				
			}else if(Integer.parseInt(accMonth)==19){
				//半年报，下半年
				if(cellFormula.indexOf("上期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上期","18");
				}else if(cellFormula.indexOf("上两期")!=-1){
					cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-3));
					cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-2));
					cellFormula=cellFormula.replace("本年度",String.valueOf(Integer.parseInt(accYear)-1));
					cellFormula=cellFormula.replace("上两期","19");
				}else{
					cellFormula=cellFormula.replace("本期间",accMonth);
				}
				
			}else{
				//年报
				cellFormula=cellFormula.replace("本期间",accMonth);
				cellFormula=cellFormula.replace("上期",accMonth);
				cellFormula=cellFormula.replace("上两期",accMonth);
			}
			
		}
		
		if(cellFormula.indexOf("本年度")!=-1){
			cellFormula=cellFormula.replace("本年度",accYear);
			
		} 
			
	    if(cellFormula.indexOf("上年")!=-1){
			cellFormula=cellFormula.replace("上年",String.valueOf(Integer.parseInt(accYear)-1));
			
		} 
	    
		if(cellFormula.indexOf("上两年")!=-1){
			cellFormula=cellFormula.replace("上两年",String.valueOf(Integer.parseInt(accYear)-2));
			
		}
		
		return cellFormula;
	}
	
	//取报表期间数1，参数顺序：mod_code,group_id,hos_id,copy_code,acc_year,acc_month,report_code,cell
	private String getReportContent(Map<String, Object> map)throws Exception{
		
		String celltext=map.get("cell_text").toString();
		
		String sheetName="";
		int rowIndex=0;
		int cellIndex=0;
		try{
			
			if(celltext.indexOf("!")!=-1){
				sheetName=celltext.substring(0, celltext.indexOf("!"));
				celltext=celltext.substring(celltext.indexOf("!")+1, celltext.length());//=Sheet1!A#1或者A#1
			}
			
			cellIndex=StringTool.excelColStrToNum(celltext.split("#")[0])-1;
			rowIndex=Integer.parseInt(celltext.split("#")[1])-1;
		}catch(Exception e){
			logger.error("取报表期间数，单元格错误。应该为：【sheet名称!行号#列号】或者【行号#列号】。");
			throw new SysException("取报表期间数，单元格错误。应该为：【sheet名称!行号#列号】或者【行号#列号】。");
		}
		
		//取实例报表
		String reVal="0";
		CostRepInstance repInstance=costSuperReportEngineMapper.querySuperReportInstance(map);		
		
		
		if(null==repInstance || repInstance.getContent()==null ||repInstance.getContent().length()==0){
			return reVal;
		}
		
		
		try {
			
			//content=new String(repDefine.getContent(),"GBK");
			String content=repInstance.getContent();
			JSONObject json=JSONObject.parseObject(content);
			JSONObject jsonSheet=JSONObject.parseObject(json.getString("sheets"));
			
			if(jsonSheet==null || jsonSheet.size()==0){
				return reVal;
			}
			
			//循环遍历sheet
			for (String sheet : jsonSheet.keySet()) {  
				
				if(sheetName.equals("") || sheet.equalsIgnoreCase(sheetName)){
					//匹配sheet名称
					JSONObject sheetObject=jsonSheet.getJSONObject(sheet);
					
					JSONObject data=sheetObject.getJSONObject("data");
					if(data==null){
						return reVal;
					}
					
					JSONObject dataTable=data.getJSONObject("dataTable");
					if(dataTable==null){
						return reVal;
					}
						
					JSONObject rowObject=dataTable.getJSONObject(String.valueOf(rowIndex));
					if(rowObject==null){
						return reVal;
					}
					
					JSONObject cellObject=rowObject.getJSONObject(String.valueOf(cellIndex));
					if(cellObject==null){
						return reVal;
					}
					
					if(cellObject.getString("value")!=null && !cellObject.getString("value").equals("")){
						reVal=cellObject.getString("value");
					}else{
						reVal="0";
					}
					break;
				}
				
			}
			

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
		
		return reVal;
	}
	

	//检查是否为数字（包含小数、负数）
	private boolean isNumeric(String str){
		if(str==null || str.equals("")){
			return false;
		}
		
		str=str.replace(",", "");
		if(str.startsWith("-")){
			str = str.substring(1);
		}
		  
        Matcher isNum = patNum.matcher(str);
        if(isNum.matches() ){
              return true;
        }
        
        return false;
		
	}
}
