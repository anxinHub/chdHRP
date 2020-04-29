package com.chd.hrp.hpm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiCustomReportMapper;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.service.report.AphiCustomReportService;
import com.github.pagehelper.PageInfo;

@Service("aphiCustomReportService")
public class AphiCustomReportServiceImpl implements AphiCustomReportService {
	
	private static final Logger logger = Logger.getLogger(AphiCustomReportServiceImpl.class);
	
	@Resource(name = "aphiCustomReportMapper")
	private final AphiCustomReportMapper aphiCustomReportMapper = null;
	
	
	/*注:此查询共用
	 自定义指标页面 查询表头
	职能科室考核明细表  查询表头
	财务其它考核明细表 查询表头*/
	@Override
	public String queryHpmCustomReportHead(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer deptSql = new StringBuffer();
		StringBuffer targetSql = new StringBuffer();

		
		if(entityMap.get("dept") != null && !"".equals(entityMap.get("dept"))){
			
			deptSql.append("AND adtd.dept_id|| ',' || adtd.dept_no in (");
			String [] dept_id_nos = entityMap.get("dept").toString().split("@");
			for(String dept_id_no:dept_id_nos){
				
				deptSql.append("'" + dept_id_no + "',");

				
			}
			deptSql = deptSql.deleteCharAt(deptSql.length() - 1).append(")");
			entityMap.put("deptSql", deptSql.toString());
		}
		
		if(entityMap.get("target") != null && !"".equals(entityMap.get("target"))){
			
			targetSql.append("AND adtd.target_code in (");
			String [] targetCodes = entityMap.get("target").toString().split("@");
			for(String targetCode:targetCodes){
				
				targetSql.append("'" + targetCode + "',");
				
			}
			targetSql = targetSql.deleteCharAt(targetSql.length() - 1).append(")");
			entityMap.put("targetSql", targetSql.toString());
		}
		
		List<AphiDeptTargetData> dtdList = aphiCustomReportMapper.queryAphiCustomReportHead(entityMap);

		StringBuffer columns= new StringBuffer();

		Integer size = dtdList.size();

		columns.append("[");

		columns.append("{display: \'科室编码\', name: \'dept_code\', align: \'left\',width:120,frozen:true},");

		columns.append("{display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180,frozen:true},");

		for (int i = 0; i < size; i++) {

			AphiDeptTargetData dtd = dtdList.get(i);
			int length = String.valueOf(dtd.getTarget_name()).length();//获取列名称长度
			int minWidth = 90;//初始化列宽度
			
			if(length / 5 != 0){
				minWidth += length / 5 * 40;//计算列宽度
			}
			
			columns.append("{display : \'"+ dtd.getTarget_name()+ "\',name : \'"+ dtd.getTarget_code().toLowerCase()+"\',align : \'right\',formatter:\'###,##0.00\',minWidth:"+minWidth+",totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}},"
							+ "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata." + dtd.getTarget_code().toLowerCase()
							+ " ==null ? 0 : rowdata." + dtd.getTarget_code().toLowerCase() + ",2,1);" + "}" + "},");

		}

		columns.append("{ display: \'合计\', name: \'bonus_money\', align: \'right\',formatter:\'###,##0.00\',width:160,totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}},"
						+ "render: function (rowdata , rowindex , value){"
						+ "return formatNumber(rowdata.bonus_money ==null ? 0 : rowdata.bonus_money,2,1);"
						+ "}" + "}");
		
		//columns = columns.deleteCharAt(columns.length() - 1);
		
		columns.append("]");
		
		return columns.toString();
	}
	
	/*注:此查询共用
	 自定义指标页面 主查询
	职能科室考核明细表  主查询
	财务其它考核明细表 主查询*/
	@Override
	public String queryHpmCustomReport(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer deptSql = new StringBuffer();
		StringBuffer targetSql = new StringBuffer();
		
		//取科室查询条件
		if(entityMap.get("dept") != null && !"".equals(entityMap.get("dept"))){
			
			deptSql.append("AND adtd.dept_id|| ',' || adtd.dept_no in (");
			String [] dept_id_nos = entityMap.get("dept").toString().split("@");
			for(String dept_id_no:dept_id_nos){
				
				deptSql.append("'" + dept_id_no + "',");

				
			}
			deptSql = deptSql.deleteCharAt(deptSql.length() - 1).append(")");
			entityMap.put("deptSql", deptSql.toString());
		}
		
		
		//取指标查询条件
		if(entityMap.get("target") != null && !"".equals(entityMap.get("target"))){
			
			targetSql.append("AND adtd.target_code in (");
			String [] targetCodes = entityMap.get("target").toString().split("@");
			for(String targetCode:targetCodes){
				
				targetSql.append("'" + targetCode + "',");
				
			}
			targetSql = targetSql.deleteCharAt(targetSql.length() - 1).append(")");
			entityMap.put("targetSql", targetSql.toString());
		}

		List<AphiDeptTargetData> dtdList = aphiCustomReportMapper.queryAphiCustomReportHead(entityMap);
		if(dtdList.size() == 0 ){
			
			return "{\"error\":\"未找到指标  \",\"state\":\"false\"}";
		}


		StringBuffer sql_case = new StringBuffer();
			
		StringBuffer money_count = new StringBuffer();

		for (int i = 0; i < dtdList.size(); i++) {

			AphiDeptTargetData target = (AphiDeptTargetData) dtdList.get(i);

			sql_case.append(""
					+ "sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
					+ "' then adtd.target_value end),0)) as  "+target.getTarget_code()+ ",");
				
				money_count.append("sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
						+ "' then adtd.target_value end),0)) +");

			}
			
			if(money_count.length() > 0){
				sql_case.append(money_count.deleteCharAt(money_count.length()-1) + " as bonus_money,");
			}
			
			entityMap.put("sql_case", sql_case);

		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiCustomReportMapper.queryAphiCustomReport(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = aphiCustomReportMapper.queryAphiCustomReport(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	/**
	 * 自定义指标表打印
	 * */
	@Override
	public List<Map<String,Object>> queryHpmCustomReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		StringBuffer deptSql = new StringBuffer();
		StringBuffer targetSql = new StringBuffer();
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		//取科室查询条件
		if(entityMap.get("dept") != null && !"".equals(entityMap.get("dept"))){
			
			deptSql.append("AND adtd.dept_id|| ',' || adtd.dept_no in (");
			String [] dept_id_nos = entityMap.get("dept").toString().split("@");
			for(String dept_id_no:dept_id_nos){
				
				deptSql.append("'" + dept_id_no + "',");

				
			}
			deptSql = deptSql.deleteCharAt(deptSql.length() - 1).append(")");
			entityMap.put("deptSql", deptSql.toString());
		}
		
		
		//取指标查询条件
		if(entityMap.get("target") != null && !"".equals(entityMap.get("target"))){
			
			targetSql.append("AND adtd.target_code in (");
			String [] targetCodes = entityMap.get("target").toString().split("@");
			for(String targetCode:targetCodes){
				
				targetSql.append("'" + targetCode + "',");
				
			}
			targetSql = targetSql.deleteCharAt(targetSql.length() - 1).append(")");
			entityMap.put("targetSql", targetSql.toString());
		}

		List<AphiDeptTargetData> dtdList = aphiCustomReportMapper.queryAphiCustomReportHead(entityMap);
		
		StringBuffer sql_case = new StringBuffer();
		StringBuffer money_count = new StringBuffer();

		for (int i = 0; i < dtdList.size(); i++) {

			AphiDeptTargetData target = (AphiDeptTargetData) dtdList.get(i);

			sql_case.append(""
					+ "sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
					+ "' then adtd.target_value end),0)) as  "+target.getTarget_code()+ ",");
				
				money_count.append("sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
						+ "' then adtd.target_value end),0)) +");

		}
			
		if(money_count.length() > 0){
			sql_case.append(money_count.deleteCharAt(money_count.length()-1) + " as bonus_money,");
		}
			
		entityMap.put("sql_case", sql_case);

		List<Map<String,Object>> list = aphiCustomReportMapper.queryAphiCustomReportPrint(entityMap);

		return list;

	}
	
	/**
	 * 职能科室考核明细表 打印
	 * */
	@Override
	public List<Map<String, Object>> queryHpmDeptTargetCkeckReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (!"".equals(entityMap.get("year_month")) && entityMap.get("year_month") != null) {

			String acct_year = entityMap.get("year_month").toString().substring(0, 4);
			String acct_month = entityMap.get("year_month").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
		
		if("ZN".equals(entityMap.get("select_para"))){
			
			entityMap.put("where_sql", " and adtd.target_code like 'ZN%' "
					+ "and adtd.target_code <> 'ZN01000' ");
		}
		
		if (!SessionManager.existsUserPerm("isShowZeroHpmDeptTargetCkeckReport")) {
			entityMap.put("is_containsZero", "0");
		} else {
			entityMap.put("is_containsZero", "1");
		}
		
		
		List<AphiDeptTargetData> dtdList = aphiCustomReportMapper.queryAphiCustomReportHead(entityMap);
		
		StringBuffer sql_case = new StringBuffer();
			
		StringBuffer money_count = new StringBuffer();

		for (int i = 0; i < dtdList.size(); i++) {

			AphiDeptTargetData target = (AphiDeptTargetData) dtdList.get(i);

			sql_case.append(""
					+ "sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
					+ "' then adtd.target_value end),0)) as  "+target.getTarget_code()+ ",");
				
				money_count.append("sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
						+ "' then adtd.target_value end),0)) +");

		}
			
		if(money_count.length() > 0){
			sql_case.append(money_count.deleteCharAt(money_count.length()-1) + " as bonus_money,");
		}
			
		entityMap.put("sql_case", sql_case);

		

		List<Map<String,Object>> list = aphiCustomReportMapper.queryAphiCustomReportPrint(entityMap);

		return list;

	}
	

	/**
	 * 财务其它考核明细表 打印
	 * */
	@Override
	public List<Map<String, Object>> queryHpmOtherTargetCkeckReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (!"".equals(entityMap.get("year_month")) && entityMap.get("year_month") != null) {
			
			String acct_year = entityMap.get("year_month").toString().substring(0, 4);
			String acct_month = entityMap.get("year_month").toString().substring(4);
			
			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
			
		if("QT".equals(entityMap.get("select_para"))){
				
			entityMap.put("where_sql", " and adtd.target_code like 'QT%' "
				+ "and adtd.target_code <> 'QT01000' ");
		}
		
		if (!SessionManager.existsUserPerm("isShowZeroHpmOtherTargetCkeckReport")) {
			entityMap.put("is_containsZero", "0");
		} else {
			entityMap.put("is_containsZero", "1");
		}
		
		List<AphiDeptTargetData> dtdList = aphiCustomReportMapper.queryAphiCustomReportHead(entityMap);
		
		StringBuffer sql_case = new StringBuffer();
			
		StringBuffer money_count = new StringBuffer();

		for (int i = 0; i < dtdList.size(); i++) {

			AphiDeptTargetData target = (AphiDeptTargetData) dtdList.get(i);

			sql_case.append(""
					+ "sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
					+ "' then adtd.target_value end),0)) as  "+target.getTarget_code()+ ",");
				
				money_count.append("sum(nvl((case when adtd.target_code = '" + target.getTarget_code() 
						+ "' then adtd.target_value end),0)) +");

		}
			
		if(money_count.length() > 0){
			sql_case.append(money_count.deleteCharAt(money_count.length()-1) + " as bonus_money,");
		}
			
		entityMap.put("sql_case", sql_case);

		

		List<Map<String,Object>> list = aphiCustomReportMapper.queryAphiCustomReportPrint(entityMap);

		return list;
	}
	
	/**
	 * @Description 
	 * <BR>查询指标
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*//*
	@Override
	public String queryCustomTargetTree(Map<String, Object> entityMap) throws DataAccessException {
		
		List<AphiTarget> targetList = aphiTargetMapper.queryPrmTarget(entityMap);
		
		StringBuilder json = new StringBuilder();
		
		if (targetList.size() ==0 && targetList.size() ==0) {

			json.append("{Rows:[]}");

			return json.toString();
			
		}
		
		try {

			json.append("{Rows:[");
			
			for (AphiTarget target : targetList) {
				
				json.append("{");
				json.append("\"pId\":\"-1\"," + "\"id\":\"" + target.getTarget_code()+ "\"," + "\"name\":" + "\"" + target.getTarget_name()+ "\"");
				json.append("},");
			}
			
			json.setCharAt(json.length() - 1, ']');
			
			json.append("}");
			
		}catch (Exception e) {
			
			json.append("{Rows:[]}");

			return json.toString();
			
		}
		
		return json.toString();
		
	}
	
	*//**
	 * @Description 
	 * <BR>查询科室
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*//*
	@Override
	public String queryCustomDeptTree(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<AphiDept> deptList = aphiDeptMapper.queryPrmDept(entityMap);
		
		StringBuilder json = new StringBuilder();
		
		if (deptList.size() ==0 && deptList.size() ==0) {

			json.append("{Rows:[]}");

			return json.toString();
			
		}
		
		try {

			json.append("{Rows:[");
			
			for (AphiDept dept : deptList) {
				
				json.append("{");
				json.append("\"pId\":\"-1\"," + "\"id\":\"" + dept.getDept_code()+ "\"," + "\"name\":" + "\"" + dept.getDept_name()+ "\"");
				json.append("},");
			}
			
			json.setCharAt(json.length() - 1, ']');
			
			json.append("}");
			
		}catch (Exception e) {
			
			json.append("{Rows:[]}");

			return json.toString();
			
		}
		
		return json.toString();
	}

	@Override
	public String queryHpmCustomReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if(entityMap.get("column_name") == null || "".equals(entityMap.get("column_name"))){
			return "{\"error\":\"指标编码为空  \"}";
		}
		
		if(entityMap.get("deptCodes") == null || "".equals(entityMap.get("deptCodes"))){
			return "{\"error\":\"科室不能为空  \"}";
		}
		
		StringBuilder sql_where = new StringBuilder();//存储科室编码作为查询条件
		String[] deptCodes = String.valueOf(entityMap.get("deptCodes")).split(",");
		
		if(deptCodes.length == 0){
			entityMap.put("sql_where", "'" + entityMap.get("deptCodes") + "'");
		}else{
			for(String dept_code:deptCodes){
				sql_where.append("'" + dept_code + "',");
			}
			sql_where.deleteCharAt(sql_where.length()-1);
			entityMap.put("sql_where", sql_where);
		}
		
		
		StringBuilder sql_column = new StringBuilder();//存储指标动态查询列
		String[] column_names = String.valueOf(entityMap.get("column_name")).split(",");
		
		if(column_names.length == 0){
			//SUM(ISNULL((case when adtd.target_code = 'a011' then adtd.target_value end),0)) as targetCodea001
			sql_column.append("SUM(ISNULL((case when adtd.target_code='" + entityMap.get("column_name") + "' then adtd.target_value end),0)) as targetCode_" + entityMap.get("column_name"));
			entityMap.put("sql_column", sql_column);
		}else{
			for(int x = 0 ; x < column_names.length ; x++ ){
				//SUM(ISNULL((case when adtd.target_code = 'a011' then adtd.target_value end),0)) as targetCodea001,
				sql_column.append("SUM(ISNULL((case when adtd.target_code='" + column_names[x]+ "' then adtd.target_value end),0)) as targetCode_" + column_names[x] + ",");
			}
			
			sql_column = sql_column.deleteCharAt(sql_column.length() - 1);
			entityMap.put("sql_column", sql_column);
		}
		
		List<Map<String,Object>> resultList = aphiCustomReportMapper.queryHpmCustomReport(entityMap);
		//将结果集拼装成JSON对象
		StringBuilder json = new StringBuilder();
		json.append("{Rows:[");
		
		if(resultList != null && resultList.size() > 0){
			for (int i = 0; i < resultList.size(); i++) {
				Map<String, Object> map =resultList.get(i);
				json.append("{");
				
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					if (!"rownum".equals(entry.getKey())) {
						
						json.append("\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",");
					}
				}
				json.append("},");
			}
			
			json.setCharAt(json.length() - 1, ']');
			json.append(",Total:" + resultList.size() + "}");
			
		}else {
			json.append("],Total:0}");
		}
		
		return json.toString();
	}*/

}
