package com.chd.hrp.hr.serviceImpl.salarymanagement.empWage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.salarymanagement.HrStandardMapper;
import com.chd.hrp.hr.dao.salarymanagement.empWage.HrWageCheckComputeMapper;
import com.chd.hrp.hr.dao.salarymanagement.wagePlanManage.HrWagePlanManageMapper;
import com.chd.hrp.hr.entity.salarymanagement.empWage.HrWage;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItem;
import com.chd.hrp.hr.service.salarymanagement.empWage.HrWageCheckComputeService;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author yang
 *
 */
@Service("hrWageCheckComputeService")
public class HrWageCheckComputeServiceImpl implements HrWageCheckComputeService{

	private static Logger logger = Logger.getLogger(HrWageCheckComputeServiceImpl.class);
	
	// 引入DAO
	@Resource(name = "hrWageCheckComputeMapper")
	private final HrWageCheckComputeMapper hrWageCheckComputeMapper = null;
	
	@Resource(name = "hrWagePlanManageMapper")
	private final HrWagePlanManageMapper hrWagePlanManageMapper = null;
	
	@Resource(name = "hrStandardMapper")
	private final HrStandardMapper hrStandardMapper = null;
	
	// TODO 生成方法
	@Override
	public String generateEmpWage(Map<String, Object> paramMap) throws DataAccessException{
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		try{
			/*
			// 生成本月工资表之前先删除表中的数据
			hrWageCheckComputeMapper.deleteEmpWage(paramMap);*/
			// 生成基础数据并保存
			hrWageCheckComputeMapper.generateEmpWage(paramMap);
			// 生成社保基础数据并保存
			hrWageCheckComputeMapper.generateEmpInsur(paramMap);
			// 生成公积金基础数据并保存
			hrWageCheckComputeMapper.generateEmpFund(paramMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"生成失败.\",\"state\":\"false\"}";
		}
	}

	// 返回新表头
	@Override
	public String queryWageCheckComputeHead(Map<String, Object> paramMap) throws DataAccessException {
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> columnsMapList = new ArrayList<Map<String, Object>>();
			paramMap.put("is_stop", "0");
			List<HrWageItem> wageItemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(paramMap);
			String[] count_col = new String[wageItemList.size() + 1];
			for(int i = 0; i < wageItemList.size(); i++){
				count_col[i] = wageItemList.get(i).getColumn_item().toLowerCase();
				Map<String, Object> columnsMap = new HashMap<String, Object>();
				columnsMap.put("align", "center");
				columnsMap.put("width", 120);
				columnsMap.put("hidden", false);
				columnsMap.put("display", wageItemList.get(i).getItem_name());
				columnsMap.put("name", wageItemList.get(i).getColumn_item().toLowerCase());
				columnsMap.put("editor", new HashMap<String, Object>(){{put("type", "number");}});
				
				columnsMapList.add(columnsMap);
			}
			count_col[wageItemList.size()] = "wage_count";
			Map<String, Object> headMap = new HashMap<String, Object>();
			headMap.put("columns", columnsMapList);
			headMap.put("totalColumns", count_col);
			return JSONArray.toJSONString(headMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"返回表头失败.\",\"state\":\"false\"}";
		}
	}

	// 主查询
	@Override
	public String queryWageCheckComputeGrid(Map<String, Object> paramMap) throws DataAccessException {
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			if(paramMap.get("dept_id") != null && paramMap.get("dept_id").toString() != ""){
				paramMap.put("dept_id", paramMap.get("dept_id").toString().split("@")[1]);
			}
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
				List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) hrWageCheckComputeMapper
						.query(paramMap);
				return ChdJson.toJson(resultMapList);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> resultMapList = (List<Map<String, Object>>) hrWageCheckComputeMapper
						.query(paramMap, rowBounds);
				PageInfo page = new PageInfo(resultMapList);
				return ChdJson.toJson(resultMapList, page.getTotal());
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"内容查询失败.\",\"state\":\"false\"}";
		}
	}
	
	// 保存
	@Override
	public String saveEmpHrWage(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		try{
			List<Map> listVo = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), Map.class);
			paramMap.put("is_stop", "0");
			List<HrWageItem> itemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(paramMap);
			
			Map<String, Object> map = new HashMap<String, Object>();
			if(itemList.size() > 0){
				StringBuilder columns = new StringBuilder();
				for(HrWageItem item : itemList){
					columns.append(",").append(item.getColumn_item());
				}
				map.put("columns", columns);
				
				for(Map m : listVo){
					m.put("wage_plan", paramMap.get("plan_code"));
					StringBuilder columnsValue = new StringBuilder();
					for(HrWageItem item : itemList){
						columnsValue.append(",").append(m.get(item.getColumn_item().toLowerCase()));
					}
					m.put("columnsValue", columnsValue);
				}
			}
			
			List<String> empIdList = new ArrayList<String>();
			for(Map item : listVo){
				empIdList.add(item.get("emp_id").toString());
			}
			paramMap.put("empIdList", empIdList);
			
			hrWageCheckComputeMapper.deleteEmpHrWageByEmpIds(paramMap);
			hrWageCheckComputeMapper.saveHrWageBatch(map, listVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"保存失败.\",\"state\":\"false\"}";
		}
	}

	// TODO 计算
	@Override
	public String collectEmpHrWage(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
			WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
			DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
					.getBean("transactionManager");
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
//			List<AccSubj> list = new ArrayList<AccSubj>();
			String prm_ErrTxt = "";
			try{
				
				hrWageCheckComputeMapper.collectEmpHrWage(entityMap);
				
				String prm_AppCode = entityMap.get("prm_AppCode").toString();
				
				
				
				if("0".equals(prm_AppCode)){
					//计算成功！提交事务
					transactionManager.commit(status);
					
					return "{\"msg_text\":\"计算成功.\",\"is_ok\":\""+prm_AppCode+"\"}";
					
				}else if("-1".equals(prm_AppCode)||"100".equals(prm_AppCode)){
					//计算失败！回滚事务
					transactionManager.rollback(status);
					
				}
				
				if(!"".equals(entityMap.get("prm_ErrTxt").toString()) && null != entityMap.get("prm_ErrTxt").toString()){
					
					prm_ErrTxt = entityMap.get("prm_ErrTxt").toString();
					
				}
				
				return "{\"msg_text\":\""+prm_ErrTxt+".\",\"is_ok\":\""+prm_AppCode+"\"}";
				
				
				
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"计算失败.\",\"state\":\"false\"}";
		}
	}

	// 删除
	@Override
	public String deleteEmpHrWage(Map<String, Object> paramMap) throws DataAccessException {
		try{
			List<HrWage> list = JSONArray.parseArray(String.valueOf(paramMap.get("paramVo")), HrWage.class);
			
			// 根据主键批量删除
			hrWageCheckComputeMapper.deleteHrWageBatch(list);
			return "{\"msg\":\"删除成功\", \"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败\", \"state\":\"false\"}";
		}
	}

	@Override
	public String queryEmpByWagePlanEmpKind(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		List<Map<String, Object>> list = hrWageCheckComputeMapper.queryEmpByWagePlanEmpKind(paramMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String importCheckCompute(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
		entityMap.put("is_stop", "0");
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		List<Map<String, Object>> columnsMapList = new ArrayList<Map<String, Object>>();
		List<HrWageItem> wageItemList = hrWagePlanManageMapper.queryHrWageItemsByPlanCode(entityMap);
		String[] count_col = new String[wageItemList.size() + 1];
		for(int i = 0; i < wageItemList.size(); i++){
			count_col[i] = wageItemList.get(i).getColumn_item().toLowerCase();
			Map<String, Object> columnsMap = new HashMap<String, Object>();
			
			columnsMap.put("name", wageItemList.get(i).getColumn_item().toLowerCase());
			columnsMapList.add(columnsMap);
		}
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					Map<String,Object> queryMap = new HashMap<String,Object>();
					Map<String,Object> updateMap = new HashMap<String,Object>();
					
					queryMap.put("group_id", SessionManager.getGroupId());
					queryMap.put("hos_id", SessionManager.getHosId());
					queryMap.put("dept_name", map.get("dept_name").get(1));
					queryMap.put("emp_code", map.get("emp_code").get(1));
					
					//查询职工是否存在
					Map<String,Object> emp_id=	hrWageCheckComputeMapper.queryHosEmpId(queryMap);
				if(emp_id==null){
					failureMsg.append("员工; "+map.get("emp_code").get(1)+"不存在");
					failureNum++;
					continue;
				}else{
					queryMap.put("wage_plan", entityMap.get("plan_code"));
					queryMap.put("emp_id", emp_id.get("EMP_ID"));
					//查询职工是否已经存在工资核算
					Map<String,Object> emp=	hrWageCheckComputeMapper.queryHrWage(queryMap);
					if(emp!=null){
						updateMap.put("group_id", SessionManager.getGroupId());
						updateMap.put("hos_id", SessionManager.getHosId());
						updateMap.put("emp_id", emp_id.get("EMP_ID"));
						updateMap.put("wage_plan", entityMap.get("plan_code"));
					}else{
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("wage_plan", entityMap.get("plan_code"));
						saveMap.put("emp_id", emp_id.get("EMP_ID"));
					}
					
				}
				
			for (Map<String, Object> map2 : columnsMapList) {
				if(!map.get(map2.get("name")).equals("")){
				
					if(saveMap.size()>0){
						//saveMap.put("key", map2.get("name").toString());
						saveMap.put(map2.get("name").toString(), map.get(map2.get("name")).get(1));
					}
				
					if(updateMap.size()>0){
						//updateMap.put("key", map2.get("name").toString());
						updateMap.put(map2.get("name").toString(), map.get(map2.get("name")).get(1));
					}
					
				}
			}
					successNum++;
					if(saveMap.size()>0){
						saveList.add(saveMap);
					}
					if(updateMap.size()>0){
						updateList.add(updateMap);
					}
					
					
				}
				if(saveList.size() > 0){
					hrWageCheckComputeMapper.addBatchWage(saveList);
				}
				if(updateList.size() > 0){
					hrWageCheckComputeMapper.updateBatchWage(updateList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public String wageItemSelect(Map<String, Object> entityMap)
			throws DataAccessException {

		List<Map<String, Object>> list = hrWageCheckComputeMapper.wageItemSelect(entityMap);
		
		return JSONArray.toJSONString(list);
	
	}

	@Override
	public String updateItem(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			hrWageCheckComputeMapper.updateItem(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"修改失败！\"}";
		}
		
		
	}

}
