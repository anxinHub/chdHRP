package com.chd.hrp.cost.serviceImpl.report;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.report.CostSuperReportDesignMapper;
import com.chd.hrp.cost.dao.report.CostSuperReportManagerMapper;
import com.chd.hrp.cost.entity.report.CostRepDefineDict;
import com.chd.hrp.cost.entity.report.CostRepDefineEle;
import com.chd.hrp.cost.entity.report.CostRepInstance;
import com.chd.hrp.cost.service.report.CostSuperReportManagerService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 超级报表管理类
 * @author ADMINISTRATOR
 *
 */
@Service("costSuperReportManagerService")
public class CostSuperReportManagerServiceImpl implements CostSuperReportManagerService{

	private static Logger logger = Logger.getLogger(CostSuperReportManagerServiceImpl.class);
	
	@Resource(name = "costSuperReportManagerMapper")
	private final CostSuperReportManagerMapper costSuperReportManagerMapper = null;
	
	@Resource(name = "costSuperReportDesignMapper")
	private final CostSuperReportDesignMapper costSuperReportDesignMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	private SAXReader reader = new SAXReader();
//	private Connection conn=null;
	private Document doc=null;
//	private PreparedStatement pstmt = null;
	

	//报表管理页面，查询报表的所有实例
	@Override
	public String querySuperReportInstanceList(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
//		SysPage sysPage = new SysPage();
//		sysPage = (SysPage) map.get("sysPage");
//		
//		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<CostRepInstance> list = costSuperReportManagerMapper.querySuperReportInstanceList(map);
		return ChdJson.toJson(list);
	}

	//报表管理页面，删除报表实例
	@Override
	public String deleteBatchSuperReportInstance(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		String[] param=map.get("paramVo").toString().split(",");
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		Map<String, String> paramMap=null;
 		for(String s :param){
 			paramMap=new HashMap<String, String>();
 			paramMap.put("acc_year", s.split("@")[0]);
 			paramMap.put("acc_month", s.split("@")[1]);
 			
 			if(MyConfig.getSysPara("042").equals("1") && map.get("mod_code")!=null && map.get("mod_code").toString().equals("01") &&
 					paramMap.get("acc_year")!=null && !map.get("acc_year").toString().equals("0000") && !paramMap.get("acc_year").toString().equals("") && 
 					paramMap.get("acc_month")!=null && !paramMap.get("acc_month").toString().equals("")){
 				//财务系统
 				String accYear=paramMap.get("acc_year").toString();
 				String accMonth=paramMap.get("acc_month").toString();//01-13月份、14第一季度、15第二季度、16第三季度、17第四季度、18上半年、19下半、20全年
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
 			
 			
			list.add(paramMap);
		}
 		
 		try{
 			costSuperReportManagerMapper.deleteBatchSuperReportInstance(map.get("group_id").toString(), map.get("hos_id").toString(), map.get("copy_code").toString(), map.get("report_code").toString(), map.get("mod_code").toString(), list);
 		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
	
		}
 		
		return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
	}

	//报表字典页面，查询所有字典
	@Override
	public String querySuperReportDictList(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) map.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = costSuperReportManagerMapper.querySuperReportDictList(map, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	//报表字典页面，保存报表字典
	@Override
	public String saveSuperReportDict(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		//报表字典页面，验证字典SQL
		String dictSql=map.get("dict_sql").toString();
		dictSql=dictSql.toLowerCase();
		if(dictSql.indexOf(" id")==-1 || dictSql.indexOf(" text")==-1){
			return "{\"error\":\"查询SQL必须包含【id】和【text】两列！\",\"state\":\"false\"}";
		}
		
		
		dictSql=dictSql.replace("#{group_id}", "0");
		dictSql=dictSql.replace("#{hos_id}", "0");
		dictSql=dictSql.replace("#{copy_code}", "''");
		dictSql=dictSql.replace("#{acc_year}", "''");
		dictSql=dictSql.replace("#{user_id}", "0");
		dictSql=dictSql.replace("#{user_code}", "''");
		
		
		String dictCheckSql="";
		if(map.get("dict_check_sql")!=null && !map.get("dict_check_sql").toString().equals("")){
			dictCheckSql=map.get("dict_check_sql").toString();
			dictCheckSql=dictCheckSql.replace("#{key}","");
			if(dictSql.lastIndexOf("order by")!=-1){
				dictSql=dictSql.substring(0, dictSql.indexOf("order by"))+" and ("+dictCheckSql+") "+dictSql.substring(dictSql.indexOf("order by"),dictSql.length());
				
			}else{
				dictSql=dictSql+" and("+dictCheckSql+")";
			}
		}
		
		try{
			costSuperReportManagerMapper.querySuperReportDictSqlValidate(dictSql);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("查询SQL不能正确执行！");
		}
		
		
		try{
			
			if(map.get("operation").toString().equals("insert")){
				//添加
				map.put("field_table","REP_DEFINE_DICT");
				map.put("field_key1", "dict_code");
				map.put("field_value1", map.get("dict_code"));
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count >0) {
					return "{\"error\":\"字典编码：[" + map.get("dict_code").toString() + "]重复.\"}";
				}
				
				map.put("field_key1", "dict_name");
				map.put("field_value1", map.get("dict_name"));
				count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count >0) {
					return "{\"error\":\"字典名称：[" + map.get("dict_name").toString() + "]重复.\"}";
				}
				
				map.remove("field_key1");
				map.remove("field_value1");
				
				//取最大排序号
				map.put("field_sort", "sort_code");
				count=sysFunUtilMapper.querySysMaxSort(map);
				map.put("sort_code", count);
				map.remove("field_table");
				map.remove("field_sort");
				
	 			costSuperReportManagerMapper.insertSuperReportDict(map);
	 			
			}else{
				//修改
				map.put("field_table","REP_DEFINE_DICT");
				map.put("field_id", "dict_code");
				map.put("field_id_value", map.get("dict_code"));
				map.put("field_key1", "dict_name");
				map.put("field_value1", map.get("dict_name"));
				int count = sysFunUtilMapper.existsSysCodeNameByUpdate(map);
				if (count >0) {
					return "{\"error\":\"字典名称：[" + map.get("dict_name").toString() + "]重复.\"}";
				}
				
	 			costSuperReportManagerMapper.updateSuperReport(map);
	 			
			}
			
			
			
 		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return "{\"msg\":\"保存成功！\",\"state\":\"true\"}";
	}

	//报表字典页面，删除报表字典
	@Override
	public String deleteBatchSuperReportDict(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String[] param=map.get("paramVo").toString().split(",");
		List<String> list=new ArrayList<String>();
 		for(String s :param){
			list.add(s);
		}
 		
		try{
			List<String> listUse=costSuperReportManagerMapper.querySuperReportDictByUse(map.get("group_id").toString(), map.get("hos_id").toString(), map.get("copy_code").toString(),list);
			String msg="";
			if(listUse!=null && listUse.size()>0){
				for(String s:listUse){
					msg=msg+s+"，";
				}
			}
			if(msg!=null && !msg.equals("")){
				return "{\"error\":\"删除失败，以下字典："+msg+"已被使用！\",\"state\":\"false\"}";
			}
			
 			costSuperReportManagerMapper.deleteBatchSuperReportDict(map.get("group_id").toString(), map.get("hos_id").toString(), map.get("copy_code").toString(),list);
 		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
	}

	//报表字典页面，根据字典编码查询报表字典
	@Override
	public String querySuperReportDictByCode(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb=new StringBuffer();
		CostRepDefineDict dict=costSuperReportManagerMapper.querySuperReportDictByCode(map);
		if(dict!=null){
			sb.append("{");
			sb.append("\"sort_code\":\""+dict.getSort_code()+"\",");
			sb.append("\"note\":\""+dict.getNote()+"\",");
			sb.append("\"dict_sql\":\""+dict.getDict_sql()+"\",");
			sb.append("\"dict_check_sql\":\""+dict.getDict_check_sql()+"\"");
			sb.append("}");
		}else{
			sb.append("{}");
		}
		return sb.toString();
	}

	//报表元素页面，查询报表元素
	@Override
	public String querySuperReportEleManager(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");
		
		List<CostRepDefineEle> list = new ArrayList<CostRepDefineEle>();
		list=costSuperReportDesignMapper.querySuperReportEleByMod(map);
		
		//拼装级次
		if (list!=null && list.size()>0) {
			int row = 0;
			
			//系统模块（一级）
			Map<String,Object> modMap=new HashMap<String,Object>();
			for (CostRepDefineEle rep : list) {
				
				if(modMap.size()==0 || modMap.get(rep.getMod_code())==null){
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					modMap.put(rep.getMod_code(), "m_"+row);
					sb.append("id:\"m_"+row+"\",");
					sb.append("pId:\"top\",");
					sb.append("name:\"" + rep.getMod_name()+ "\",");
					sb.append("title:\"" + rep.getMod_name() + "\",");
					sb.append("open:false");
					sb.append("}");
				}
				
			}
			
			//元素分组（二级）
			row = 0;
			Map<String,Object> eleGroupMap=new HashMap<String,Object>();
			for (CostRepDefineEle rep : list) {
				
				if(eleGroupMap.size()==0 || eleGroupMap.get(rep.getMod_code()+rep.getEle_group())==null){
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					eleGroupMap.put(rep.getMod_code()+rep.getEle_group(), "t_"+row);
					sb.append("id:\"t_"+row+"\",");
					sb.append("pId:\""+modMap.get(rep.getMod_code())+"\",");
					sb.append("name:\"" + rep.getEle_group()+ "\",");
					sb.append("title:\"" + rep.getEle_group() + "\",");
					sb.append("open:false");
					sb.append("}");
				}
				
			}
			
			//元素（三级）
			for (CostRepDefineEle rep : list) {
				sb.append(",{");
				sb.append("id:\"" + rep.getEle_code() + "\",");
				sb.append("pId:\"" + eleGroupMap.get(rep.getMod_code()+rep.getEle_group()) + "\",");
				sb.append("name:\"" + rep.getEle_name() + "\",");
				sb.append("ele_group:\"" + rep.getEle_group() + "\",");
				sb.append("mod_code:\"" + rep.getMod_code() + "\",");
				sb.append("ele_type:\"" + rep.getEle_type() + "\",");
				sb.append("sort_code:\"" + rep.getSort_code() + "\",");
				sb.append("note:\"" + (rep.getNote()==null?"":rep.getNote()) + "\",");
				sb.append("is_sys:\"" + rep.getIs_sys() + "\",");
				sb.append("formula_type:\"2\",");//公式类型1非计算元素、2计算元素
				sb.append("title:\"" + rep.getEle_code() + "\",");
				sb.append("is_stop:\"" + rep.getIs_stop() + "\"");
				sb.append("}");
			}
		}
		
		
		sb.append("]}");
		return sb.toString();
	}	
	
	//报表元素页面，保存报表元素、报表参数
	@Override
	public String saveSuperReportEle(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try{
			
			if(map.get("ele_sql")==null || map.get("ele_sql").toString().equals("")){
				return "{\"error\":\"元素取数SQL不能为空.\"}";
			}
			
//			String eleSql=map.get("ele_sql").toString().trim();
//			if(map.get("ele_type").toString().equals("1") || map.get("ele_type").toString().equals("2") || map.get("ele_type").toString().equals("3")){
//				if(eleSql.toLowerCase().indexOf("create or replace")!=-1){
//					return "{\"error\":\"元素SQL不能包含create or replace关键字.\"}";
//				}
//				//函数、存储过程、视图不存表REP_DEFINE_ELE，ele_sql字段长度太小，从oracle数据库里面直接取
//				map.remove("ele_sql");
//			}
			
			String eleCode=map.get("ele_code").toString().toUpperCase();
			if(map.get("ele_type").toString().equals("1") && map.get("ele_sql").toString().toUpperCase().indexOf("CREATE OR REPLACE FUNCTION "+eleCode)==-1){
				
				//函数：判断元素SQL创建对象是否与元素编码一致
				return "{\"error\":\"元素取值SQL没有包含：【create or replace function "+eleCode+"】关键字！\"}";
				
			}else if(map.get("ele_type").toString().equals("2") && map.get("ele_sql").toString().toUpperCase().indexOf("CREATE OR REPLACE PROCEDURE "+eleCode)==-1){
				//存储过程：判断元素SQL创建对象是否与元素编码一致
				return "{\"error\":\"元素取值SQL没有包含：【create or replace procedure "+eleCode+"】关键字！\"}";
				
			}else if(map.get("ele_type").toString().equals("3") && map.get("ele_sql").toString().toUpperCase().indexOf("CREATE OR REPLACE VIEW "+eleCode)==-1){
				//视图：判断元素SQL创建对象是否与元素编码一致
				return "{\"error\":\"元素取值SQL没有包含：【create or replace view "+eleCode+"】关键字！\"}";
				
			}
			
			map.put("ele_code", eleCode);
			if(map.get("operation").toString().equals("insert")){
				
				//添加操作
				map.put("is_sys",0);
				map.put("field_table","rep_define_ele");
				map.put("field_key1", "ele_code");
				map.put("field_value1",eleCode);
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count >0) {
					return "{\"error\":\"元素编码：[" + eleCode + "]重复.\"}";
				}
				
				map.put("field_key1", "ele_name");
				map.put("field_value1", map.get("ele_name"));
				count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count >0) {
					return "{\"error\":\"元素名称：[" + map.get("ele_name").toString() + "]重复.\"}";
				}
				
				map.remove("field_key1");
				map.remove("field_value1");
				
				//取最大排序号
				map.put("field_sort", "sort_code");
				count=sysFunUtilMapper.querySysMaxSort(map);
				map.put("sort_code", count);
				map.remove("field_table");
				map.remove("field_sort");
				
				//添加报表元素
				costSuperReportManagerMapper.insertSuperReportEle(map);
				
			}else{
				
				//修改操作
				map.put("field_table","rep_define_ele");
				map.put("field_id", "ele_code");
				map.put("field_id_value", eleCode);
				map.put("field_key1", "ele_name");
				map.put("field_value1", map.get("ele_name"));
				int count = sysFunUtilMapper.existsSysCodeNameByUpdate(map);
				if (count >0) {
					return "{\"error\":\"元素名称：[" + map.get("ele_name").toString() + "]重复.\"}";
				}
				
				//修改报表元素
				costSuperReportManagerMapper.updateSuperReportEle(map);
				
				//删除报表元素参数
				costSuperReportManagerMapper.deleteSuperReportElePara(map);
				
			}
			
			//添加报表元素参数
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			Map<String,Object> paraMap=null;
			if(map.get("grid")!=null && !map.get("grid").toString().equals("")){
				int sortCode=0;
				JSONArray parajson=JSONObject.parseArray(map.get("grid").toString());
				for(int i=0;i<parajson.size();i++){
					paraMap=new HashMap<String,Object>();
					sortCode+=10;
					JSONObject para = JSONObject.parseObject(parajson.getString(i));
					paraMap.put("group_id", SessionManager.getGroupId());
					paraMap.put("hos_id", SessionManager.getHosId());
					paraMap.put("copy_code", SessionManager.getCopyCode());
					paraMap.put("ele_code",eleCode);
					paraMap.put("para_code", para.getString("para_code"));
					paraMap.put("para_name", para.getString("para_name"));
					paraMap.put("para_type", para.getInteger("para_type"));
					paraMap.put("dict_code", para.getString("dict_code")==null?"":para.getString("dict_code"));
					paraMap.put("para_json", para.getString("para_json"));
					paraMap.put("sort_code",sortCode);
					paraMap.put("is_stop", para.getInteger("is_stop"));
					list.add(paraMap);
				}
			}
			
			if(list!=null && list.size()>0){
				costSuperReportManagerMapper.insertSuperReportElePara(list);
			}
			
			//创建函数\存储过程\视图
			if(map.get("ele_type").toString().equals("1") || map.get("ele_type").toString().equals("2") || map.get("ele_type").toString().equals("3")){
				sysFunUtilMapper.execDDLSql(map.get("ele_sql").toString());
			}
			
//			String ddlSql=null;
//			if(map.get("ele_type").toString().equals("1")){
//				//函数
//				ddlSql="create or replace function "+eleCode+eleSql;
//				
//			}else if(map.get("ele_type").toString().equals("2")){
//				//存储过程
//				ddlSql=eleSql;
//				//去掉最后一个；号
//				if(ddlSql.substring(ddlSql.length()-1,ddlSql.length()).equals(";")){
//					ddlSql=ddlSql.substring(0,ddlSql.length()-1);
//				}
//				
//				//ddlSql=ddlSql.replaceAll("(?i)create or replace", "");//替换不区分大小写
//				//ddlSql=ddlSql.replaceAll("(?i)procedure", "");
//				//ddlSql=ddlSql.replaceAll("(?i)"+eleCode, "");
//				ddlSql="create or replace procedure "+eleCode+ddlSql+" "+eleCode+";";
//				
//			}else if(map.get("ele_type").toString().equals("3")){
//				//视图
//				ddlSql="create or replace view "+eleCode+" as "+eleSql;
//				
//			}
//			
//			if(ddlSql!=null && !ddlSql.equals("")){
//				sysFunUtilMapper.execDDLSql(ddlSql);
//			}
		
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return "{\"msg\":\"保存成功！\",\"state\":\"true\"}";
	}

	//报表元素页面，删除报表元素、报表参数
	@Override
	public String deleteSuperReportEle(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try{
			
			//删除报表元素参数
			costSuperReportManagerMapper.deleteSuperReportElePara(map);
			
			//删除报表元素
			costSuperReportManagerMapper.deleteSuperReportEle(map);
			
			
			if(map.get("ele_type").toString().equals("1") || map.get("ele_type").toString().equals("2") || map.get("ele_type").toString().equals("3")){
				//根据报表元素删除存储过程函数视图
				costSuperReportManagerMapper.deleteSuperVouchByProcFunView(map);
			}
			
		
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
	}
	
	//查询存储过程、函数(user_source)、视图(user_views)
	@Override
	public String querySuperReportSourceAndViews(Map<String, Object> map)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String eleSql=costSuperReportManagerMapper.querySuperReportEleSql(map.get("group_id").toString(),map.get("hos_id").toString(),map.get("copy_code").toString(),map.get("ele_code").toString());
//		if(map.get("ele_type").toString().equals("1") || map.get("ele_type").toString().equals("2")){
//			//函数、存储过程
//			eleSql="select text from user_source where name = '"+map.get("ele_code").toString().toUpperCase()+"' order by line ";
//			
//		}else if(map.get("ele_type").toString().equals("3")){
//			//视图
//			eleSql="select text from user_views where view_name = '"+map.get("ele_code").toString().toUpperCase()+"' ";
//			
//		}else if(map.get("ele_type").toString().equals("4")){
//			//自定义元素
//			eleSql="select ele_sql from REP_DEFINE_ELE where group_id = "+map.get("group_id").toString()+" and hos_id = "+map.get("hos_id").toString()+" and copy_code = '"+map.get("copy_code").toString()+"' and ele_code = '"+map.get("ele_code").toString()+"' ";
//			
//		}
//		String reStr="";
//		if(eleSql!=null){
//			List<String> str=costSuperReportManagerMapper.querySuperReportSourceAndViews(eleSql);
//			if(str!=null && str.size()>0){
//				for(String s:str){
//					sb.append(s);
//				}
//				reStr=sb.toString();
//				
//				if((map.get("ele_type").toString().equals("1") || map.get("ele_type").toString().equals("2"))){
//					//函数\存储过程去掉create or replace　名称
//					reStr=reStr.substring(reStr.indexOf("("),reStr.length());
//					//掉最后一个end后面的字符
//					reStr=reStr.substring(0,reStr.toLowerCase().lastIndexOf("end")+3);
//				}
//				
//			}
//		}
//		
		return eleSql;
	}

	@Override
	public String initSuperReportProc(Map<String, Object> map) throws DataAccessException {
		try{
			String url = LoadSystemInfo.getProjectUrl();
			
			String path = "WEB-INF\\classes\\oracle\\02fun";
			
			if(map.get("path") != null && !"".equals(map.get("path"))){
				path = map.get("path").toString();
			}
			
			File file=new File(url + path);//内置数据所在文件 
			map.put("is_init", 0);
			//conn=ConnectionFactory.getSystemConnection();//获取数据源
			readFile(file,map);
			int isInit= Integer.parseInt(map.get("is_init").toString());
			if(isInit>0){
				return "{\"msg\":\"加载成功，重新刷新！\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"02fun目录没有找到内置SQL，不需要加载！\",\"state\":\"false\"}";
			}
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
//		finally{
//			ConnectionFactory.closeAllConn(conn,"system",pstmt,null);
//		}
		
	}

	private void readFile(File file,Map<String, Object> map) throws SQLException, DocumentException{
		if(file==null){
			return;
		}
		if (file.isDirectory()) {
			
			 File[] flist=file.listFiles();
			 for (File f : flist) {
				 readFile(f,map);
			 }
            
		} else {
			String fileName=file.getName();
			 if (fileName.substring(fileName.lastIndexOf(".") + 1).equalsIgnoreCase("xml")) {
				readFileContent(file,map);
			}
		}
	}
	
	private void readFileContent(File file,Map<String, Object> map) throws SQLException, DocumentException{
		
		try {
			doc = reader.read(file);
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			if (list != null && list.size() > 0) {
				for (Element e : list) {
					if (!e.getName().equals("sql")) {
						continue;
					}

					if (e.attributeValue("id") == null || e.attributeValue("id").equals("")) {
						continue;
					}

					if (e.attributeValue("type") == null || e.attributeValue("type").equals("")) {
						continue;
					}

					if (e.getText() == null || e.getText().equals("")) {
						continue;
					}

//					byte[] ele_sql = e.getText().getBytes("UTF-8");//获取存储过程SQL内容

					String ele_code = e.attributeValue("id").toUpperCase();;//获取元素编码并转换大写
					if(ele_code.equalsIgnoreCase(map.get("ele_code").toString())){
						map.put("ele_sql", e.getText());
						
						map.put("ele_code", ele_code);
						
						int isInit = 0;
						if(map.get("is_ele") != null && "0".equals(map.get("is_ele").toString())){
							isInit=costSuperReportManagerMapper.updateSuperReportDsSql(map);//更新数据集表中数据
						}else{
							isInit=costSuperReportManagerMapper.updateSuperReportEleSql(map);//更新报表元素表中数据
						}
						
						//非自定义sql需加载到数据库中
						if(map.get("ele_type") == null || !"4".equals(map.get("ele_type").toString())){
							costSuperReportManagerMapper.querySuperReportDictSqlValidate(e.getText());
						}
						
						map.put("is_init", isInit);
						break;
					}

					//pstmt = conn.prepareStatement(e.getText());
					//pstmt.execute();//向数据库中加载存储过程

				}
			} 
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
