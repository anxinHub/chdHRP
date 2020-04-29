package com.chd.hrp.hpm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiEmpBonusCalculationMapper;
import com.chd.hrp.hpm.dao.AphiEmpItemMapper;
import com.chd.hrp.hpm.entity.AphiEmpItem;
import com.chd.hrp.hpm.service.report.AphiEmpBonusCalculationService;
import com.github.pagehelper.PageInfo;

@Service("aphiEmpBonusCalculationService")
public class AphiEmpBonusCalculationServiceImpl implements AphiEmpBonusCalculationService {

	private static Logger logger = Logger.getLogger(AphiEmpBonusCalculationServiceImpl.class);

	@Resource(name = "aphiEmpBonusCalculationMapper")
	private final AphiEmpBonusCalculationMapper aphiEmpBonusCalculationMapper = null;
	
	@Resource(name = "aphiEmpItemMapper")
	private final AphiEmpItemMapper aphiEmpItemMapper = null;

	@Override
	public String queryEmpBonusCalculationByReport(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		StringBuffer sql = new StringBuffer();//拼写要sum的工资项
		StringBuffer sql_sum = new StringBuffer();//拼写合计所有工资项SQL
		StringBuffer sql_colunm1 = new StringBuffer();
		StringBuffer sql_colunm2 = new StringBuffer();
		
		List<AphiEmpItem> empItemList = aphiEmpItemMapper.queryEmpItem(entityMap);
		
		if(empItemList.size() > 0 ){
			
			for(int x = 0 ; x < empItemList.size() ; x++){
				AphiEmpItem aebd = empItemList.get(x);
				
				sql.append("sum(nvl(case when aebd.item_code='" + aebd.getItem_code() +"' then aebd.bonus_money end,0)) as item_code_" + aebd.getItem_code()+ ",");
				sql_sum.append("nvl((case when aebd.item_code = '" + aebd.getItem_code() + "' then aebd.bonus_money end),0)+");
				
				sql_colunm1.append("null as item_code_" + aebd.getItem_code() + ",");
				sql_colunm2.append("t1.item_code_" + aebd.getItem_code() + ",");
			}
			
		}
		
		if(sql.length() > 0){
			String sumSql = "sum("+sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,";
			entityMap.put("sql", sql.toString()+sumSql);
			
			entityMap.put("sql_colunm1", sql_colunm1);
			entityMap.put("sql_colunm2", sql_colunm2);
		}

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiEmpBonusCalculationMapper.queryEmpBonusData(entityMap);
			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = aphiEmpBonusCalculationMapper.queryEmpBonusData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public String queryEmpBonusCalculationGrid(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("is_stip", "0");

		List<AphiEmpItem> queryEmpItemList = aphiEmpItemMapper.queryEmpItem(entityMap);

		StringBuffer columns = new StringBuffer();

		columns.append("{Rows:[");
		
		columns.append("{ display: \'核算年月\', name: \'year_month\', align: \'left\',width:160,frozen:true},");
		
		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:80,frozen:true},");
		
		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:160,frozen:true},");
		
		columns.append("{ display: \'职工编码\', name: \'emp_code\', align: \'left\',width:80},");

		columns.append("{ display: \'职工名称\', name: \'emp_name\', align: \'left\',width:120},");


		for (int i = 0; i < queryEmpItemList.size(); i++) {

			AphiEmpItem item = queryEmpItemList.get(i);
			
			columns.append("{display : \'" + item.getItem_name() + "\',name : \'item_code_" + item.getItem_code()+ "\',align : \'right\',formatter:\'###,##0.00\',width:160,editor:{type:\'float\'}"
					/*+ "totalSummary:{type: \'sum\',render: function (suminf, column, cell){ "
						+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}"
					+ "}"*/
					+ "},");	
		}

		columns.append("{ display: \'合计\', name: \'sum_money\', align: \'right\',formatter:\'###,##0.00\',width:160,"
				/*+ "totalSummary:{type: \'sum\',render: function (suminf, column, cell){ " 
					+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}"
				+ "}," */
				+ "render: function (rowdata , rowindex , value){" 
					+ "return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,2,1);}"
				+ "}");
		
		columns.append("]}");

		return columns.toString();
	}

	@Override
	public List<Map<String, Object>> queryEmpBonusCalculationByReportPrint(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id")==null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		StringBuffer sql = new StringBuffer();//拼写要sum的工资项
		StringBuffer sql_sum = new StringBuffer();//拼写合计所有工资项SQL
		StringBuffer sql_colunm1 = new StringBuffer();
		StringBuffer sql_colunm2 = new StringBuffer();
		
		if(MyConfig.getSysPara("09001") == null){
			entityMap.put("para_value", 0);
		}else{
			entityMap.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		List<AphiEmpItem> empItemList = aphiEmpItemMapper.queryEmpItem(entityMap);
		
		for(int x = 0 ; x < empItemList.size() ; x++){
			AphiEmpItem aebd = empItemList.get(x);
				
			sql.append("sum(nvl(case when aebd.item_code='" + aebd.getItem_code() +"' then aebd.bonus_money end,0)) as item_code_" + aebd.getItem_code()+ ",");
			sql_sum.append("nvl((case when aebd.item_code = '" + aebd.getItem_code() + "' then aebd.bonus_money end),0)+");
				
			sql_colunm1.append("null as item_code_" + aebd.getItem_code() + ",");
			sql_colunm2.append("t1.item_code_" + aebd.getItem_code() + ",");
		}
			
		
		if(sql.length() > 0){
			String sumSql = "sum("+sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,";
			entityMap.put("sql", sql.toString()+sumSql);
			
			entityMap.put("sql_colunm1", sql_colunm1);
			entityMap.put("sql_colunm2", sql_colunm2);
		}


		List<Map<String,Object>> list = aphiEmpBonusCalculationMapper.queryEmpBonusDataPrint(entityMap);
		
		return list;
	}

}
