package com.chd.hrp.hr.serviceImpl.salarymanagement.empWage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.salarymanagement.empWage.HrWageQueryMapper;
import com.chd.hrp.hr.dao.salarymanagement.wagePlanManage.HrWagePlanManageMapper;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItem;
import com.chd.hrp.hr.service.salarymanagement.empWage.HrWageQueryService;
import com.github.pagehelper.PageInfo;


@Service("hrWageQueryService")
public class HrWageQueryServiceImpl implements HrWageQueryService{
	
	private static Logger logger = Logger.getLogger(HrWageQueryServiceImpl.class);
	
	// 引入DAO
	@Resource(name = "hrWageQueryMapper")
	private final HrWageQueryMapper hrWageQueryMapper = null;
	
	
	@Resource(name = "hrWagePlanManageMapper")
	private final HrWagePlanManageMapper hrWagePlanManageMapper = null;
	
	
	
	
	
	@Override
	public String queryWageCheckQueryGrid(Map<String, Object> paramMap)
			throws DataAccessException {
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
		/*	if(paramMap.get("dept_id") != null && paramMap.get("dept_id").toString() != ""){
				paramMap.put("dept_id", paramMap.get("dept_id").toString().split("@")[1]);
			}*/
			paramMap.put("is_stop", 0);
			List<HrWageItem> wageItemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(paramMap);
			// 工资项列
			if(wageItemList.size() > 0){
				StringBuilder columns = new StringBuilder();
				StringBuilder count = new StringBuilder();
				for(HrWageItem item : wageItemList){
					columns.append(",hr_wage.").append(item.getColumn_item());
					if(item.getIs_sum() == 1){
						count.append("+nvl(hr_wage."+item.getColumn_item()+",0)");
					}
				}
				count.append(" as wage_count");
				paramMap.put("columns", columns.append(",").append(count.substring(1)));
			}
			
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) paramMap.get("sysPage");
			if(sysPage.getTotal() == -1){
				List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) hrWageQueryMapper.query(paramMap);
				return ChdJson.toJson(resultMapList);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) hrWageQueryMapper.query(paramMap, rowBounds);
				PageInfo page = new PageInfo(resultMapList);
				return ChdJson.toJson(resultMapList, page.getTotal());
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"内容查询失败.\",\"state\":\"false\"}";
		}
	}





	@Override
	public String queryWageQueryForm(Map<String, Object> entityMap, int colNum)
			throws DataAccessException {
		Map<String, Object> formMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		formMap.put("colNum", colNum);// 页面form表单列的个数
	List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		List<HrWageItem> 	hrWageItem=hrWagePlanManageMapper.queryHrWageItemsByPlanCode(entityMap);
	/*	for (HrWageItem hrWageItem1 : hrWageItem) {
			Map<String, Object> fieldItem = new HashMap<String, Object>();
			StringBuffer str=new StringBuffer();
			
			
			str.append("<p> <input type=\"checkbox\" style=\"width:180px;\" name=\"");
			str.append(hrWageItem1.getColumn_item()+"\" value=\"");
			str.append(hrWageItem1.getItem_name()+"\" >");
			str.append(hrWageItem1.getItem_name()+"</p>");
			fieldItems.add(str);
			<input type="checkbox" id="state" name="state" value="1" checked="checked"/>
			<label>只显示启用</label>
			fieldItem.put("id",hrWageItem1.getColumn_item().toString());
			fieldItem.put("name",hrWageItem1.getItem_name());
			fieldItem.put("type","checkbox");
			fieldItem.put("required",false);
			fieldItem.put("value",hrWageItem1.getItem_name());
			//fieldItem.put("width", "100px");// 字段宽度
			fieldItems.add(fieldItem);
		}
	formMap.put("fieldItems", fieldItems);
		
		return JSONArray.toJSONString(formMap);*/
		
		int j=0;
		StringBuilder retJson = new StringBuilder();
		retJson.append("[");
		
			retJson.append("{\"Row\": [");
			for(HrWageItem hrWageItem1 : hrWageItem){
				if(j > 0){
					retJson.append(",");
				}
				retJson.append("{\"id\":\"").append(hrWageItem1.getColumn_item().toString().toLowerCase()).append("\",");
				retJson.append("\"name\":\"").append(hrWageItem1.getItem_name().toString()).append("\"}");
				j++;
				/*retJson.append("\"parent_node_id\":\"").append(initMap.get("parent_node_id") == null ? "" : initMap.get("parent_node_id").toString()).append("\",");
				retJson.append("\"note\":\"").append(initMap.get("note").toString());*/
			}
			//retJson.deleteCharAt(retJson.length() - 1);
			retJson.append("]}");
		
		retJson.append("]");
		
		return retJson.toString();
	}





	@Override
	public List<Map<String, Object>> queryWageQueryByPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
	/*	if(paramMap.get("dept_id") != null && paramMap.get("dept_id").toString() != ""){
			paramMap.put("dept_id", paramMap.get("dept_id").toString().split("@")[1]);
		}*/
		entityMap.put("is_stop", 0);
		List<HrWageItem> wageItemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(entityMap);
		// 工资项列
		if(wageItemList.size() > 0){
			StringBuilder columns = new StringBuilder();
			StringBuilder count = new StringBuilder();
			for(HrWageItem item : wageItemList){
				columns.append(",hr_wage.").append(item.getColumn_item());
				if(item.getIs_sum() == 1){
					count.append("+nvl(hr_wage."+item.getColumn_item()+",0)");
				}
			}
			count.append(" as wage_count");
			entityMap.put("columns", columns.append(",").append(count.substring(1)));
		}
		
		 List<Map<String,Object>> list = ChdJson.toListLower(hrWageQueryMapper.queryWageQueryByPrint(entityMap));
		 return list;
	}

}
