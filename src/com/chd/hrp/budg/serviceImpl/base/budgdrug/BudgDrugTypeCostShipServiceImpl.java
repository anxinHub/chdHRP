/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budgdrug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgdrug.BudgDrugTypeCostShipMapper;
import com.chd.hrp.budg.entity.BudgDrugTypeCostShip;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeCostShipService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 药品分类与预算支出科目对应关系
 * @Table:
 * BUDG_DRUG_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgDrugTypeCostShipService")
public class BudgDrugTypeCostShipServiceImpl implements BudgDrugTypeCostShipService {

	private static Logger logger = Logger.getLogger(BudgDrugTypeCostShipServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgDrugTypeCostShipMapper")
	private final BudgDrugTypeCostShipMapper budgDrugTypeCostShipMapper = null;
    
	/**
	 * @Description 
	 * 添加药品分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//定义mapVo 接收药品类别 与 收入科目的多对多关系
		List<Map<String, Object>> incomeList= new ArrayList<Map<String,Object>>();
		
		try {
			String[] incomeCode = String.valueOf(entityMap.get("income_subj_code")).split(";");
			
			entityMap.remove("income_subj_code");
			for (String income_subj_code : incomeCode) {
				//定义mapVo 接收药品类别 与 收入科目的对应关系
				Map<String,Object> mapVo = new HashMap<String, Object>();
				mapVo.putAll(entityMap);
				mapVo.put("income_subj_code", income_subj_code);
				incomeList.add(mapVo);
			}
			
			budgDrugTypeCostShipMapper.addIncomeBatch(incomeList);
			budgDrugTypeCostShipMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
	}
	/**
	 * @Description 
	 * 批量添加药品分类与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgDrugTypeCostShipMapper.addBatch(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";
		}
		
	}
	
		/**
	 * @Description 
	 * 更新药品分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			budgDrugTypeCostShipMapper.updateIncome(entityMap);
			budgDrugTypeCostShipMapper.update(entityMap);
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
	 * 批量更新药品分类与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgDrugTypeCostShipMapper.updateBatch(entityList);
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
	 * 删除药品分类与预算支出科目对应关系<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			budgDrugTypeCostShipMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}
		
	}
    
	/**
	 * @Description 
	 * 批量删除药品分类与预算支出科目对应关系<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			budgDrugTypeCostShipMapper.deleteIncomeBatch(entityList);
			budgDrugTypeCostShipMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 添加药品分类与预算支出科目对应关系<BR> 
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
		//判断是否存在对象药品分类与预算支出科目对应关系
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("budg_year", entityMap.get("budg_year"));
		
		List<BudgDrugTypeCostShip> list = (List<BudgDrugTypeCostShip>)budgDrugTypeCostShipMapper.queryExists(mapVo);
		
		if (list.size()>0) {
			budgDrugTypeCostShipMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		
		try {
			budgDrugTypeCostShipMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集药品分类与预算支出科目对应关系<BR> 
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
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDrugTypeCostShipMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = (List<Map<String,Object>>)budgDrugTypeCostShipMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象药品分类与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return budgDrugTypeCostShipMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取药品分类与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return BudgDrugTypeCostShip
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgDrugTypeCostShipMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取药品分类与预算支出科目对应关系<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<BudgDrugTypeCostShip>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return budgDrugTypeCostShipMapper.queryExists(entityMap);
	}
	
	@Override
	public String queryMedTypes(Map<String, Object> mapVo) throws DataAccessException {
		 return JSON.toJSONString(budgDrugTypeCostShipMapper.queryMedTypes(mapVo));
	}
	
	@Override
	public String queryMedTypesFilter(Map<String, Object> mapVo) throws DataAccessException {
		 return JSON.toJSONString(budgDrugTypeCostShipMapper.queryMedTypesFilter(mapVo));
	}
	
	@Override
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgDrugTypeCostShipMapper.queryBudgSubj(mapVo));
	}
	
	@Override
	public String extendBudgDrugTypeCostShip(Map<String, Object> mapVo) throws DataAccessException {
		mapVo.put("prev_year", Integer.parseInt(String.valueOf(mapVo.get("budg_year")))-1);
		//查询上年 药品分类与支出科目对应关系
		List<Map<String,Object>> prevYearCostData = budgDrugTypeCostShipMapper.queryPrevYearData(mapVo);
		
		//查询上年 药品分类与支出科目对应关系
		List<Map<String,Object>> prevYearIncomeData = budgDrugTypeCostShipMapper.queryPrevYearIncomeData(mapVo);
		try {
			
			if(prevYearCostData != null && prevYearCostData.size()>0){
				for (Map<String, Object> map : prevYearCostData) {
					map.put("budg_year", mapVo.get("budg_year"));
				}
			}else{
				return "{\"error\":\"继承失败 药品分类与支出科目不存在可继承数据! \"}";
			}
			
			if(prevYearIncomeData != null && prevYearIncomeData.size()>0){
				for (Map<String, Object> map : prevYearIncomeData) {
					map.put("budg_year", mapVo.get("budg_year"));
				}
			}else{
				return "{\"error\":\"继承失败! 药品分类与收入科目不存在可继承数据! \"}";
			}
			
			budgDrugTypeCostShipMapper.addBatch(prevYearCostData);
			
			budgDrugTypeCostShipMapper.addIncomeBatch(prevYearIncomeData);
			
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
}
