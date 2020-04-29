/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostEmpKindWageItemSetMapper;
import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;
import com.chd.hrp.cost.service.CostEmpKindWageItemSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_职工分类工资项配置表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpKindWageItemSetService")
public class CostEmpKindWageItemSetServiceImpl implements CostEmpKindWageItemSetService {

	private static Logger logger = Logger.getLogger(CostEmpKindWageItemSetServiceImpl.class);
	
	@Resource(name = "costEmpKindWageItemSetMapper")
	private final CostEmpKindWageItemSetMapper costEmpKindWageItemSetMapper = null;
    
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 添加CostEmpKindWageItemSet
	 * @param CostEmpKindWageItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException{
		
		List<CostEmpKindWageItemSet> costEmpKindWageItemSetList = costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSet(entityMap);

		if (costEmpKindWageItemSetList.size()>0) {

			return "{\"error\":\"已经存在此对应关系.\"}";

		}
		
		try {
			
			costEmpKindWageItemSetMapper.addCostEmpKindWageItemSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpKindWageItemSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量添加CostEmpKindWageItemSet
	 * @param  CostEmpKindWageItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpKindWageItemSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			/*
			 * 2016/10/27 lxj
			 * 判断职工分类工资项配置表对应关系是否存在 
			 * */
			int recordCount = costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSetByBatchCode(entityList);
			
			if(recordCount > 0 ){
				
				return "{\"error\":\"添加失败 已经保存过的对应关系不能重复保存\"}";
			}
			
			costEmpKindWageItemSetMapper.addBatchCostEmpKindWageItemSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpKindWageItemSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpKindWageItemSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpKindWageItemSet> list = costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpKindWageItemSet> list = costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpKindWageItemSetPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSetPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 查询CostEmpKindWageItemSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpKindWageItemSet queryCostEmpKindWageItemSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量删除CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpKindWageItemSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpKindWageItemSetMapper.deleteBatchCostEmpKindWageItemSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpKindWageItemSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 删除CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpKindWageItemSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpKindWageItemSetMapper.deleteCostEmpKindWageItemSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpKindWageItemSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 更新CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpKindWageItemSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costEmpKindWageItemSetMapper.updateCostEmpKindWageItemSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpKindWageItemSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工分类工资项配置表<BR> 批量更新CostEmpKindWageItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpKindWageItemSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpKindWageItemSetMapper.updateBatchCostEmpKindWageItemSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpKindWageItemSet\"}";

		}
		
	}
	
	@Override
	public String queryCostEmpWageList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindWageItemSetMapper.queryCostEmpWageList(entityMap),true);
	}

	@Override
	public String queryCostEmpWageMap(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindWageItemSetMapper.queryCostEmpWageMap(entityMap),true);
	}

	@Override
	public String queryCostEmpWageItemList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindWageItemSetMapper.queryCostEmpWageItemList(entityMap),true);
	}
	
	@Override
	public String queryCostEmpWageItem(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindWageItemSetMapper.queryCostEmpWageItem(entityMap),true);
	}

	@Override
	public List<CostEmpKindWageItemSet> queryCostEmpKindWageItemSetByEmpKindCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSetByEmpKindCode(entityMap);
	}
}
