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
import com.chd.hrp.cost.dao.CostEmpKindBonusItemSetMapper;
import com.chd.hrp.cost.entity.CostEmpKindBonusItemSet;
import com.chd.hrp.cost.service.CostEmpKindBonusItemSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_职工分类奖金项配置表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpKindBonusItemSetService")
public class CostEmpKindBonusItemSetServiceImpl implements CostEmpKindBonusItemSetService {

	private static Logger logger = Logger.getLogger(CostEmpKindBonusItemSetServiceImpl.class);
	
	@Resource(name = "costEmpKindBonusItemSetMapper")
	private final CostEmpKindBonusItemSetMapper costEmpKindBonusItemSetMapper = null;
    
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 添加CostEmpKindBonusItemSet
	 * @param CostEmpKindBonusItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException{
		
		List<CostEmpKindBonusItemSet> costEmpKindBonusItemSetList = costEmpKindBonusItemSetMapper.queryCostEmpKindBonusItemSet(entityMap);

		if (costEmpKindBonusItemSetList.size()>0) {

			return "{\"error\":\"已经存在此对应关系.\"}";

		}
		
		try {
			
			costEmpKindBonusItemSetMapper.addCostEmpKindBonusItemSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpKindBonusItemSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量添加CostEmpKindBonusItemSet
	 * @param  CostEmpKindBonusItemSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpKindBonusItemSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			/*
			 	2016/11/8 lxj
			 	解决添加了重复数据时报异常的问题
			 */
			int count = costEmpKindBonusItemSetMapper.queryRecordNumByBatchCode(entityList);
			
			if(count > 0 ){
				
				return "{\"error\":\"对应关系已存在.\"}";
			}
			
			costEmpKindBonusItemSetMapper.addBatchCostEmpKindBonusItemSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpKindBonusItemSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpKindBonusItemSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpKindBonusItemSet> list = costEmpKindBonusItemSetMapper.queryCostEmpKindBonusItemSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpKindBonusItemSet> list = costEmpKindBonusItemSetMapper.queryCostEmpKindBonusItemSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpKindBonusItemSetPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpKindBonusItemSetMapper.queryCostEmpKindBonusItemSetPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 查询CostEmpKindBonusItemSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpKindBonusItemSet queryCostEmpKindBonusItemSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpKindBonusItemSetMapper.queryCostEmpKindBonusItemSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量删除CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpKindBonusItemSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpKindBonusItemSetMapper.deleteBatchCostEmpKindBonusItemSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpKindBonusItemSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 删除CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpKindBonusItemSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpKindBonusItemSetMapper.deleteCostEmpKindBonusItemSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpKindBonusItemSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 更新CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpKindBonusItemSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costEmpKindBonusItemSetMapper.updateCostEmpKindBonusItemSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpKindBonusItemSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工分类奖金项配置表<BR> 批量更新CostEmpKindBonusItemSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpKindBonusItemSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpKindBonusItemSetMapper.updateBatchCostEmpKindBonusItemSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpKindBonusItemSet\"}";

		}
		
	}
	
	@Override
	public String queryCostEmpBonusList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindBonusItemSetMapper.queryCostEmpBonusList(entityMap),true);
	}

	@Override
	public String queryCostEmpBonusMap(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindBonusItemSetMapper.queryCostEmpBonusMap(entityMap),true);
	}

	@Override
	public String queryCostEmpBonusItemList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindBonusItemSetMapper.queryCostEmpBonusItemList(entityMap),true);
	}
	
	@Override
	public String queryCostEmpBonusItem(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costEmpKindBonusItemSetMapper.queryCostEmpBonusItem(entityMap),true);
	}

	@Override
	public List<CostEmpKindBonusItemSet> queryCostBonusCostRelaByEmpKindCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return costEmpKindBonusItemSetMapper.queryCostBonusCostRelaByEmpKindCode(entityMap);
	}
}
