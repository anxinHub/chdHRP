/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budginv;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.budg.dao.base.budginv.BudgMatTypeCostShipMapper;
import com.chd.hrp.budg.entity.BudgMatTypeCostShip;
import com.chd.hrp.budg.service.base.budginv.BudgMatTypeCostShipService;
import com.chd.hrp.hr.util.RegExpValidatorUtils;
import com.ctc.wstx.util.StringUtil;
import com.github.pagehelper.PageInfo;

import antlr.StringUtils;

/**
 * 
 * @Description:
 * 物资分类与预算支出科目对应关系
 * @Table:
 * BUDG_MAT_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgMatTypeCostShipService")
public class BudgMatTypeCostShipServiceImpl implements BudgMatTypeCostShipService {

	private static Logger logger = Logger.getLogger(BudgMatTypeCostShipServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgMatTypeCostShipMapper")
	private final BudgMatTypeCostShipMapper budgMatTypeCostShipMapper = null;
    
	/**
	 * @Description 
	 * 添加物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@SuppressWarnings("unchecked")
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		//定义mapVo 接收物资类别 与 收入科目的多对多关系
		List<Map<String, Object>> incomeList= new ArrayList<Map<String,Object>>();
		String costStr = "";
		String incomeStr = "";
		try {
			
			if(entityMap.get("income_subj_code") != null && entityMap.get("income_subj_code") != ""){
				String[] incomeCode = String.valueOf(entityMap.get("income_subj_code")).split(";");
				
				entityMap.remove("income_subj_code");
				for (String income_subj_code : incomeCode) {
					//定义mapVo 接收物资类别 与 收入科目的对应关系
					Map<String,Object> mapVo = new HashMap<String, Object>();
					mapVo.putAll(entityMap);
					
					String cost_subj_code = budgMatTypeCostShipMapper.queryCostExists(mapVo);
					
					if(cost_subj_code != null){
						costStr += cost_subj_code+ " ";
					}
					
					mapVo.put("income_subj_code", income_subj_code);
					
					String income_code = budgMatTypeCostShipMapper.queryIncomeExists(mapVo);
					
					if(income_code != null){
						incomeStr += income_code+ " ";
					}
					
					incomeList.add(mapVo);
				}
				
				if(costStr != ""){
					return "{\"error\":\"物资类别与预算支出科目已存在对应关系,不可重复添加.\",\"state\":\"false\"}";
				}
				if(incomeStr != ""){
					return "{\"error\":\"物资类别与预算收入科目已存在对应关系,不可重复添加.\",\"state\":\"false\"}";
				}
				
				if(incomeList.size()>0){
					budgMatTypeCostShipMapper.addIncomeBatch(incomeList);
				}
				
				budgMatTypeCostShipMapper.add(entityMap);
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				String cost_subj_code = budgMatTypeCostShipMapper.queryCostExists(entityMap);
				
				if(cost_subj_code != null){
					costStr += cost_subj_code+ " ";
				}
				
				if(costStr != ""){
					return "{\"error\":\"物资类别与预算支出科目已存在对应关系,不可重复添加.\",\"state\":\"false\"}";
				}
				
				budgMatTypeCostShipMapper.add(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
	}
	/**
	 * @Description 
	 * 批量添加物资分类与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgMatTypeCostShipMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
		/**
	 * @Description 
	 * 更新物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
				
				budgMatTypeCostShipMapper.deleteIncomeOld(entityMap) ;
				
				if(entityMap.get("income_subj_code") != null && !entityMap.get("income_subj_code").toString().isEmpty()){
					String[] incomeCode = entityMap.get("income_subj_code").toString().split(";");
					
					for (String string : incomeCode) {
						entityMap.put("income_subj_code", string);
						budgMatTypeCostShipMapper.addIncome(entityMap);
					}
				}
				
				budgMatTypeCostShipMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";
		}	
		
	}
	
	/**
	 * @Description 
	 * 批量更新物资分类与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			budgMatTypeCostShipMapper.updateBatch(entityList);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";
		}	
		
	}
	/**
	 * @Description 
	 * 删除物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			budgMatTypeCostShipMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除物资分类与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgMatTypeCostShipMapper.deleteIncomeBatch(entityList);
			budgMatTypeCostShipMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败! 方法 deleteBatch\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@SuppressWarnings("unchecked")
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象物资分类与预算支出科目对应关系
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<BudgMatTypeCostShip> list = (List<BudgMatTypeCostShip>)budgMatTypeCostShipMapper.queryExists(mapVo);
		
		if (list.size()>0) {
			budgMatTypeCostShipMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		
		try {
			budgMatTypeCostShipMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<BudgMatTypeCostShip> list = (List<BudgMatTypeCostShip>)budgMatTypeCostShipMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<BudgMatTypeCostShip> list = (List<BudgMatTypeCostShip>)budgMatTypeCostShipMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgMatTypeCostShipMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgMatTypeCostShip
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgMatTypeCostShipMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取物资分类与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgMatTypeCostShip>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgMatTypeCostShipMapper.queryExists(entityMap);
	}
	
	/**
	 * 物资分类名称下拉框
	 */
	@Override
	public String queryMatTypes(Map<String, Object> mapVo) throws DataAccessException {
		 return JSON.toJSONString(budgMatTypeCostShipMapper.queryMatTypes(mapVo));
	}
	
	@Override
	public String queryMatTypesFilter(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgMatTypeCostShipMapper.queryMatTypesFilter(mapVo));
	}
	
	@Override
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgMatTypeCostShipMapper.queryBudgSubj(mapVo));
	}
	
	@Override
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException {
		return budgMatTypeCostShipMapper.queryCostSubjByCode(mapVo);
	}
	
	@Override
	public Map<String, Object> queryBudgTypeDictByCode(Map<String, Object> mapVo) throws DataAccessException {
		return budgMatTypeCostShipMapper.queryBudgTypeDictByCode(mapVo);
	}
	
	@Override
	public String extendBudgMatTypeCostShip(Map<String, Object> mapVo) throws DataAccessException {
		//点击弹出提示框“确定继承上一年度物资分类与预算科目对应关系”，若点击确认，继承上年数据。新增数据满足：物资分类存在于物资分类字典中，预算支出科目、预算收入科目在对应的本年度字典中。
		mapVo.put("prev_year", Integer.parseInt(String.valueOf(mapVo.get("budg_year")))-1);
		
		//查询上年 物资分类与支出科目对应关系
		List<Map<String,Object>> prevYearCostData = budgMatTypeCostShipMapper.queryPrevYearCostData(mapVo);
		
		//查询上年 物资分类与支出科目对应关系
		List<Map<String,Object>> prevYearIncomeData = budgMatTypeCostShipMapper.queryPrevYearIncomeData(mapVo);
		
		if(prevYearCostData != null && prevYearCostData.size()>0){
			for (Map<String, Object> map : prevYearCostData) {
				map.put("budg_year", mapVo.get("budg_year"));
			}
		}else{
			return "{\"error\":\"继承失败 物资分类与支出科目不存在可继承数据! \"}";
		}
		
		if(prevYearIncomeData != null && prevYearIncomeData.size()>0){
			for (Map<String, Object> map : prevYearIncomeData) {
				map.put("budg_year", mapVo.get("budg_year"));
			}
		}else{
			return "{\"error\":\"继承失败! 物资分类与收入科目不存在可继承数据! \"}";
		}
		
		try {
			budgMatTypeCostShipMapper.addBatch(prevYearCostData);
			
			budgMatTypeCostShipMapper.addIncomeBatch(prevYearIncomeData);
			
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String importExcel(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> save2List = new ArrayList<Map<String, Object>>();

		Map<String, Object> whereMap = new HashMap<String, Object>(3);
		whereMap.put("group_id", SessionManager.getGroupId());
		whereMap.put("hos_id", SessionManager.getHosId());
		whereMap.put("copy_code", SessionManager.getCopyCode());
		
		// 是否
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");

		// 物资分类
		Map<String, Object> matTypeMap = new HashMap<String, Object>();
		List<Map<String, Object>> matTypeList =  (List<Map<String, Object>>) budgMatTypeCostShipMapper.queryMatTypeList(whereMap);
		for (Map<String, Object> map : matTypeList) {
			matTypeMap.put(map.get("mat_type_code").toString(), map.get("mat_type_id"));
			matTypeMap.put(map.get("mat_type_name").toString(), map.get("mat_type_id"));
		}

		// 支出科目
		/*Map<String, Object> costSubjMap = new HashMap<String, Object>();
		List<Map<String, Object>> costSubjList = (List<Map<String, Object>>) budgMatTypeCostShipMapper.queryCostSubjList(whereMap);
		for (Map<String, Object> map : costSubjList) {
			costSubjMap.put(map.get("cost_subj_code").toString(), map.get("cost_subj_code"));
			costSubjMap.put(map.get("cost_subj_name").toString(), map.get("cost_subj_code"));
		}*/
		
		// 收入科目
		/*Map<String, Object> incomeSubjMap = new HashMap<String, Object>();
		List<Map<String, Object>> incomeSubjList = (List<Map<String, Object>>) budgMatTypeCostShipMapper.queryIncomeSubjList(whereMap);
		for (Map<String, Object> map : incomeSubjList) {
			incomeSubjMap.put(map.get("income_subj_code").toString(), map.get("income_subj_code"));
			incomeSubjMap.put(map.get("income_subj_name").toString(), map.get("income_subj_code"));
		}*/

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("budg_year", map.get("budg_year").get(1));
					
					//物资分类 
					if (matTypeMap.get(map.get("mat_type_name").get(1)) == null) {
						failureMsg.append("<br/>物资分类名称:" + map.get("mat_type_name") + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("mat_type_id", matTypeMap.get(map.get("mat_type_name").get(1)));
					
					//支出科目
					whereMap.put("budg_year", map.get("budg_year").get(1));
					whereMap.put("cost_subj_code", map.get("cost_subj_code").get(1));
					Map<String,Object> costSubjMap = budgMatTypeCostShipMapper.queryCostSubjList(whereMap);
					if (costSubjMap == null || costSubjMap.get("cost_subj_code") == null) {
						failureMsg.append("<br/>支出科目:" + map.get("cost_subj_code") + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("cost_subj_code", costSubjMap.get("cost_subj_code"));
					
					//收入科目
					if(map.get("income_subj_code") != null && map.get("income_subj_code").get(1) != null){
						whereMap.put("income_subj_code", map.get("income_subj_code").get(1));
						Map<String,Object> incomeSubjMap = budgMatTypeCostShipMapper.queryIncomeSubjList(whereMap);
						if (incomeSubjMap == null || incomeSubjMap.get("income_subj_code") == null) {
							failureMsg.append("<br/>收入科目:" + map.get("income_subj_code") + " 不存在; ");
							failureNum++;
							continue;
						}
						saveMap.put("income_subj_code", incomeSubjMap.get("income_subj_code"));
					}
					
					//非医用标识
					saveMap.put("no_medical", whetherMap.get(map.get("no_medical").get(1)));

					String costData = budgMatTypeCostShipMapper.queryCostExists(saveMap);
					
					if (costData != null) {
						failureMsg.append("<br/>预算年度:" + map.get("budg_year").get(1) + " 物资分类:" + map.get("mat_type_name").get(1) + " 支出科目:" + map.get("cost_subj_code").get(1) + " 已存在; ");
						failureNum++;
						continue;
					}
					
					if(saveMap.get("income_subj_code") != null){
						String incomeData = budgMatTypeCostShipMapper.queryIncomeExists(saveMap);
						
						if (incomeData != null) {
							failureMsg.append("<br/>预算年度:" + map.get("budg_year").get(1) + " 物资分类:" + map.get("mat_type_name").get(1) +" 收入科目:" + map.get("income_subj_code").get(1) + " 已存在; ");
							failureNum++;
							continue;
						}
						
						save2List.add(saveMap);
					}
					
					successNum++;
					saveList.add(saveMap);
					
				}
				if (saveList.size() > 0) {
					budgMatTypeCostShipMapper.addBatch(saveList);
					if(save2List.size() > 0){
						budgMatTypeCostShipMapper.addIncomeBatch(save2List);
					}
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}
	
}
