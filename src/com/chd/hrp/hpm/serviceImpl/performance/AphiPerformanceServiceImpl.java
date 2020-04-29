package com.chd.hrp.hpm.serviceImpl.performance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.dao.AphiPerformanceMapper;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.performance.AphiPerformanceService;
import com.github.pagehelper.PageInfo;
@Service("aphiPerformanceService")
public class AphiPerformanceServiceImpl implements AphiPerformanceService{
	
	private static Logger logger = Logger.getLogger(AphiPerformanceServiceImpl.class);  

	
	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;
	
	@Resource(name = "aphiPerformanceMapper")
	private final AphiPerformanceMapper aphiPerformanceMapper = null;
	@Override
	public String queryHpmEmpBonusDataForReportGrid(Map<String, Object> entityMap) {
		entityMap.put("is_stip", "0");entityMap.put("is_avg", "1");Integer is_two_audit = 0 ;
		
		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);
		
		if(itemList.size() == 0){return "{\"warn\":\"请维护参与核算的奖金项目\"}";}

		StringBuffer columns = new StringBuffer();

		columns.append("{Rows:[");
		
		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:80},");
		
		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:160},");
		
		columns.append("{ display: \'职工编码\', name: \'emp_code\', align: \'left\',width:80},");

		columns.append("{ display: \'职工姓名\', name: \'emp_name\', align: \'left\',width:120},");


		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = itemList.get(i);
			
			columns.append("{display : \'" + item.getItem_name() + "\',name : \'item_code_" + item.getItem_code().toLowerCase()+ "\',align : \'right\',width:160,editor:{type:\'float\'},"
					+ "render : function(rowdata, rowindex,value,col){"
						+ "var col = arguments[arguments.length - 1];"
						+ "if (rowdata.emp_id == null || rowdata.emp_id == '') {"
						+ "rowdata.notEidtColNames.push(col.columnname); return '';}"
						+ "if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}"
						+ "return formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],2,1);}"
					+ "},");	
		}

		
		columns.append("{ display: \'合计\', name: \'sum_money\', align: \'right\',width:160,"
				+ "render: function (rowdata , rowindex , value){" 
					+ "return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,2,1);}"
				+ "}");
		
		
		columns.append("]}");

		return columns.toString();
	}
	@Override
	public String queryHpmPerformance(Map<String, Object> entityMap) {
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_month", year_month.substring(4, 6));
		entityMap.put("acct_year", year_month.substring(0, 4));
		
		entityMap.put("is_avg", "1");
		
		 String emp_ids = aphiPerformanceMapper.queryEmp(entityMap);
		 
		 entityMap.put("emp_ids", emp_ids);
		 
		 List<Map<String,Object>> deptList = aphiPerformanceMapper.queryDeptList(entityMap);
		 
		 ArrayList<Map<String, Object>> deptList1 = new ArrayList<Map<String,Object>>();
		 Map<String, Object> entityMap1=new HashMap<String,Object>();
		 
		 for( Map<String, Object> dept_ids : deptList){
			 entityMap1.put("dept_ids", dept_ids.get("DEPT_ID"));
			 entityMap1.put("dept_nos", dept_ids.get("DEPT_NO"));
			 deptList1.add(entityMap1);
		 }
		 
		 if( "".equals(entityMap.get("dept_id"))){
			 entityMap.put("deptList", deptList1);
		 }
		
		
		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);
		
		if(itemList.size() == 0){return "{\"warn\":\"请维护参与核算的奖金项目\"}";}
		
		StringBuffer sql = new StringBuffer();
		StringBuffer sql_sum = new StringBuffer();
		StringBuffer sql_columns = new StringBuffer();
		StringBuffer sql_null = new StringBuffer();
		
		if(itemList.size() > 0 ){
			
			for(int x = 0 ; x < itemList.size() ; x++){
				
				AphiItem aebd = itemList.get(x);
				
				sql.append("sum(case when aebd.item_code='" + aebd.getItem_code() +"' then aebd.bonus_money end) as item_code_" + aebd.getItem_code()+ ",");
				sql_sum.append("nvl((case when aebd.item_code = '" + aebd.getItem_code() + "' then aebd.bonus_money end),0)+");
				sql_columns.append("NULL AS item_code_" + aebd.getItem_code()+ ",");
				sql_null.append(" aebd.bonus_money as item_code_"+ aebd.getItem_code()+",");
			}
			
		}
		
		entityMap.put("sql_columns", String.valueOf(sql_columns));
		
		if(sql.length() > 0){ 
			
			String sumSql = "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,";
			entityMap.put("sql", sql.toString()+sumSql);
			String nullSql = sql_sum.substring(0, sql_sum.length() - 1).toString()+"as sum_money,";
			entityMap.put("nullsql", sql_null.toString()+nullSql); 
			entityMap.put("is_group", "1");
			
		}

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage"); 

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiPerformanceMapper.queryHpmPerformance(entityMap);

			return ChdJson.toJson(ChdJson.toJsonLower(list));

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = aphiPerformanceMapper.queryHpmPerformance(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(ChdJson.toListLower(list),page.getTotal());
		}
	}

}
