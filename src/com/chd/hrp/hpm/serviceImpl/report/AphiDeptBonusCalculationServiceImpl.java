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
import com.chd.hrp.hpm.dao.AphiDeptBonusCalculationMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.report.AphiDeptBonusCalculationService;
import com.github.pagehelper.PageInfo;

@Service("aphiDeptBonusCalculationService")
public class AphiDeptBonusCalculationServiceImpl implements AphiDeptBonusCalculationService {

	private static Logger logger = Logger.getLogger(AphiDeptBonusCalculationServiceImpl.class);

	@Resource(name = "aphiDeptBonusCalculationMapper")
	private AphiDeptBonusCalculationMapper aphiDeptBonusCalculationMapper = null;
	

	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Override
	public String queryDeptBonusCalculation(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<AphiItem> itemList = getGridTitleMap(entityMap);

		if (itemList.size() == 0) {

			return "{\"error\":\"生成失败 ,没有查询到核算的项目.\",\"state\":\"false\"}";

		}

		StringBuffer sql = new StringBuffer();

		StringBuffer sql_sum = new StringBuffer();

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("sum(nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)) as  item_code" + item.getItem_code() + ",");
			
			
			//只合计工资项目维护is_sum为1的工资项
			if("1".equals(String.valueOf(item.getIs_sum()))){
				
				sql_sum.append("nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
			}
		}

		entityMap.put("sql", sql.toString());

		entityMap.put("sql_sum", "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,");

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = aphiDeptBonusCalculationMapper.queryDeptBonusForBonusMoney(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = aphiDeptBonusCalculationMapper.queryDeptBonusForBonusMoney(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	
	}

	public List<AphiItem> getGridTitleMap(Map<String, Object> map) throws DataAccessException {

		List<AphiItem> itemList = aphiItemMapper.qeuryItemMap(map);

		if (itemList.size() > 0) {

			return itemList;

		}
		return aphiItemMapper.qeuryItemMapGrid(map);
	}
	
	public String queryDeptBonusCalculationGrid(Map<String, Object> entityMap) throws DataAccessException {
		
		List<AphiItem> queryItemDict = getGridTitleMap(entityMap);

		StringBuffer columns = new StringBuffer();

		columns.append("[");

		columns.append("{ display: \'核算年月\', name: \'year_month\', align: \'left\',width:120,frozen:true},");

		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:120,frozen:true},");

		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180,frozen:true},");

		for (int i = 0; i < queryItemDict.size(); i++) {

			AphiItem item = queryItemDict.get(i);
			
			int length = String.valueOf(item.getItem_name()).length();//获取列名称长度
			int minWidth = 110;//初始化列宽度
			
			if(length / 5 != 0){
				minWidth += length / 5 * 50;//计算列宽度
			}

			columns.append("{display : \'" + item.getItem_name() + "\',name : \'item_code" + item.getItem_code().toLowerCase()
					+ "\',align : \'right\',width:"+ minWidth +",formatter:\'###,##0.00\',totalSummary:{type: \'sum\'," + "render: function (suminf, column, cell){ "
					+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}" + "},"
					+ "render: function (rowdata, rowindex,value,col){" 
					+ "return \'<a href=\\'#\\' onclick=openFormula(\\'' + rowdata.dept_id + '\\',\\'' + rowdata.dept_no + '\\',\\'"+item.getItem_code()+"\\');>\'+formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],2,1);+\'</a>\'"
					+ "}"
					+ "},");

		}
		//
		//return '<a href=\'#\' onclick=openUpdate(\''+rowdata.acct_year+'\',\''+rowdata.acct_month+'\');>'+rowdata.acct_year+'</a>';
		columns.append("{ display: \'合计\', name: \'sum_money\', align: \'right\',width:160,formatter:\'###,##0.00\',totalSummary:{type: \'sum\',"
				+ "render: function (suminf, column, cell){ " + "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}"
				+ "}," + "render: function (rowdata , rowindex , value){" + "return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,2,1);" + "}"
				+ "}");

		columns.append("]");

		return columns.toString();
	}

	@Override
	public List<Map<String,Object>> queryDeptBonusCalculationPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		
		List<AphiItem> itemList = getGridTitleMap(entityMap);

		if (itemList.size() == 0) {
			return null;
		}

		StringBuffer sql = new StringBuffer();
		StringBuffer sql_sum = new StringBuffer();

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("sum(nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)) as  item_code" + item.getItem_code() + ",");
			//只合计工资项目维护is_sum为1的工资项
			if("1".equals(String.valueOf(item.getIs_sum()))){
				sql_sum.append("nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
			}
			
		}

		entityMap.put("sql", sql.toString());
		entityMap.put("sql_sum", "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,");


		List<Map<String, Object>> list = aphiDeptBonusCalculationMapper.queryDeptBonusForBonusMoneyPrint(entityMap);

		return list;
	}

}
