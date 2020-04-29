package com.chd.hrp.hpm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.hrp.hpm.dao.AphiDeptAvgBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiDeptAvgBonusData;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiDeptAvgBonusDataService;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiDeptAvgBonusDataService")
public class AphiDeptAvgBonusDataServiceImpl implements AphiDeptAvgBonusDataService {

	private static Logger logger = Logger.getLogger(AphiDeptAvgBonusDataServiceImpl.class);

	@Resource(name = "aphiDeptAvgBonusDataMapper")
	private final AphiDeptAvgBonusDataMapper aphiDeptAvgBonusDataMapper = null;
	


	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException {
		int state = aphiDeptAvgBonusDataMapper.addDeptAvgBonusData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";
		}
	}

	/**
	 * 
	 */
	@Override
	public String queryDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<AphiItem> itemList = getGridTitleMap(entityMap);

		StringBuffer sql = new StringBuffer();
		
		//StringBuffer sqls = new StringBuffer();
		
		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("isnull((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0) as  bonus_money" + i + ",");

			//sqls.append("isnull((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)" + "+");
			
		}

		entityMap.put("sql", sql.toString());
		
		//entityMap.put("sqls", sqls.substring(0, sqls.length() - 1).toString());
		
		List deptBonuSDataList = aphiDeptAvgBonusDataMapper.queryDeptAvgBonusData(entityMap, rowBounds);

		StringBuilder json = new StringBuilder();

		json.append("{Rows:[");
		
		double dept_bonus = 0;
		
		double dept_avg_bonus = 0;
				
		
		if (deptBonuSDataList != null && deptBonuSDataList.size() > 0) {

			for (int i = 0; i < deptBonuSDataList.size(); i++) {

				Map<String, Object> map = (Map) deptBonuSDataList.get(i);
				
				json.append("{");
				
				for (Map.Entry<String, Object> entry : map.entrySet()) {

					if (!"rownum".equals(entry.getKey())) {

						json.append("\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",");
					}

				}
				
				json.append("},");

			}

			json.setCharAt(json.length() - 1, ']');

			if (sysPage.getTotal() == -1){
				
				json.append(",Total:" + deptBonuSDataList.size() + "}");

			}else{
				
				json.append(",Total:" + sysPage.getTotal() + "}");
				
			}
			
		} else {

			json.append("],Total:0}");

		}

		return json.toString();

	}

	/**
	 * 
	 */
	@Override
	public AphiDeptAvgBonusData queryDeptAvgBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiDeptAvgBonusDataMapper.queryDeptAvgBonusDataByCode(entityMap);
	}

	/**
	 * 
	 */

	public String deleteDeptAvgBonusData(List<Map<String, Object>> entityList) throws DataAccessException {
		String request = "";
		for (Map<String, Object> entityMap : entityList) {
			int state = aphiDeptAvgBonusDataMapper.deleteDeptAvgBonusData(entityMap);
			if (state > 0) {
				request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		}

		return request;
	}

	@Override
	public String deleteDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException {
		String request = "";
		int state = aphiDeptAvgBonusDataMapper.deleteDeptAvgBonusData(entityMap);
		if (state > 0) {
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;
	}

	/**
	 * 
	 */
	@Override
	public String deleteDeptAvgBonusDataById(String[] ids) throws DataAccessException {
		String request = "";
		if (ids != null && ids.length > 0) {
			for (String s : ids) {
				aphiDeptAvgBonusDataMapper.deleteDeptAvgBonusDataById(s);
			}
			request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} else {
			request = "{\"error\":\"没有要删除的数据.\",\"state\":\"false\"}";
		}
		return request;

	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String updateDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException {
		String resutlJson = "";
		String year_month = (String) entityMap.get("year_month");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);
		
//		AphiDeptBonusAudit dba =aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//		if(dba != null){
//			
//			if(dba.getIs_audit() == 1){
//				
//				return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
//				
//			}
//			if(dba.getIs_grant()== 1){
//				
//				return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
//				
//			}
//			
//		}
		
		try {
			
			List<AphiDeptAvgBonusData> list = aphiDeptAvgBonusDataMapper.queryDeptAvgBonusDataByCollect(entityMap);
			
			if (list.size() > 0) {
				
				double sumDeptBonus =0;
				
				double sumEmpCount = 0;
				
				for(AphiDeptAvgBonusData d : list){
					
					Double dept_bonus = d.getDept_bonus();
					
					Double empCount = d.getEmp_count();
					
					sumDeptBonus = sumDeptBonus+(dept_bonus ==null?0:dept_bonus);
					
					sumEmpCount = sumEmpCount+(empCount ==null?0:empCount);
				}
				
				double clinc_avg_bonus = sumDeptBonus/sumEmpCount;
				
				for (int i = 0; i < list.size(); i++) {
					
					Map<String,Object> map = new HashMap<String,Object>();
					
					AphiDeptAvgBonusData dbd = list.get(i);
					
					map.put("group_id", dbd.getGroup_id());
					
					map.put("hos_id", dbd.getHos_id());
					
					map.put("copy_code", dbd.getCopy_code());
					
					Double dept_bonus = dbd.getDept_bonus();
					
					Double empCount = dbd.getEmp_count();

					if(empCount == null || empCount==0.0){

						map.put("dept_avg_bonus",0);
						
					}else{
						
						map.put("dept_avg_bonus", (dept_bonus ==null?0:dept_bonus)/empCount);
						
					}

					map.put("clinc_avg_bonus", clinc_avg_bonus);
					
					map.put("acct_year", dbd.getAcct_year());
					
					map.put("acct_month", dbd.getAcct_month());
					
					//map.put("dept_id", dbd.getDept_id());
					
					int state = aphiDeptAvgBonusDataMapper.updateDeptAvgBonusData(map);
					
					resutlJson = "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
					
				}
			} else {
				resutlJson = "{\"msg\":\"计算生成数据.\",\"state\":\"false\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			resutlJson = "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  updateDeptAvgBonusData\"}";
		}
		return resutlJson;
	}

	@Override
	public String initDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException {
		
		String year_month = (String) entityMap.get("year_month");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);
		
//		AphiDeptBonusAudit dba =aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
//
//		if(dba != null){
//			
//			if(dba.getIs_audit() == 1){
//				
//				return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
//				
//			}
//			if(dba.getIs_grant()== 1){
//				
//				return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
//				
//			}
//			
//		}
		

		
		aphiDeptAvgBonusDataMapper.deleteDeptAvgBonusData(entityMap);
		
		int state = aphiDeptAvgBonusDataMapper.initDeptAvgBonusData(entityMap);
		
		if (state > 0) {
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"生成失败 请查看科室是否配置参与人均核算.\",\"state\":\"false\"}";
		}
	}
	
	public List<AphiItem> getGridTitleMap(Map<String, Object> map) throws DataAccessException {

		//map.put("app_mod_code", "('02','99')");
		
		map.put("is_avg", "1");

		List<AphiItem> itemList = aphiItemMapper.qeuryItemMap(map);

		if (itemList.size() > 0) {

			return itemList;

		}
		return aphiItemMapper.qeuryItemMapGrid(map);
	}
	
	@Override
	public String queryDeptAvgBonusDataGrid(Map<String, Object> entityMap) throws DataAccessException {

		List<AphiItem> queryItemDict = getGridTitleMap(entityMap);

		StringBuffer sb_columns_hosIfno = new StringBuffer();

		Integer size = queryItemDict.size();

		sb_columns_hosIfno.append("[");

		sb_columns_hosIfno.append("{ display: \'核算年月\', name: \'acct_year\', align: \'left\',width:120," + "render: function (rowdata , rowindex , value){" + "return 	rowdata.acct_year+rowdata.acct_month;" + "}" + "},");

		sb_columns_hosIfno.append("{ display: \'科室ID\', name: \'dept_id\', align: \'left\',width:120},");

		sb_columns_hosIfno.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180},");

		for (int i = 0; i < size; i++) {

			AphiItem item = queryItemDict.get(i);

			sb_columns_hosIfno.append("{display : \'" + item.getItem_name() + "\',name : \'bonus_money" + i + "\',align : \'left\',width:\'160\',totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}," + "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.bonus_money" + i + " ==null ? 0 : rowdata.bonus_money" + i + ",2,1);" + "}" + "},");

		}

		sb_columns_hosIfno.append("{ display: \'合计\', name: \'dept_bonus\', align: \'left\',width:160,totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}," + "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.dept_bonus ==null ? 0 : rowdata.dept_bonus,2,1);" + "}" + "},");

		sb_columns_hosIfno.append("{ display: \'科室人数\', name: \'emp_count\', align: \'left\',width:160,totalSummary:{type: \'sum\',render: function (suminf, column, cell){ return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';}}," + "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.emp_count ==null ? 0 : rowdata.emp_count,2,1);" + "}" + "},");

		sb_columns_hosIfno.append("{ display: \'人均奖\', name: \'dept_avg_bonus\', align: \'left\',width:160,totalSummary:{type: \'sum\',render: function (suminf, column, cell){ "
				+ "var dept_bonus = 0;var emp_count = 0;"
				+ "$.each(cell.Rows,function(idx,item){ dept_bonus = dept_bonus+(item.dept_bonus ==null ? 0 : parseFloat(item.dept_bonus));emp_count = emp_count+(item.emp_count ==null ? 0 : parseFloat(item.emp_count));});"
				+ "var clinc_avg_bonus = parseFloat(dept_bonus)/parseFloat(emp_count);"
				+ "return \'<div> 合计:\' +formatNumber(clinc_avg_bonus ==null ? 0 :clinc_avg_bonus,2,1);+ \'</div>\';}"
				+ ""
				+ ""
				+ "}," + "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.dept_avg_bonus ==null ? 0 : rowdata.dept_avg_bonus,2,1);" + "}" + "}]");

		return sb_columns_hosIfno.toString();
	}
}
