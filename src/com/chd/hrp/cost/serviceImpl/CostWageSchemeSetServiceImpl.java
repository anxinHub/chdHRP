/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostWageSchemeMapper;
import com.chd.hrp.cost.dao.CostWageSchemeSetMapper;
import com.chd.hrp.cost.entity.CostWageScheme;
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.service.CostWageSchemeSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 职工工资查询方案表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costWageSchemeSetService")
public class CostWageSchemeSetServiceImpl implements CostWageSchemeSetService {

	private static Logger logger = Logger.getLogger(CostWageSchemeSetServiceImpl.class);
	
	@Resource(name = "costWageSchemeSetMapper")
	private final CostWageSchemeSetMapper costWageSchemeSetMapper = null;
	
	@Resource(name = "costWageSchemeMapper")
	private final CostWageSchemeMapper costWageSchemeMapper = null;
    
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 添加CostWageSchemeSet
	 * @param CostWageSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException{
		
		CostWageSchemeSet  costWageSchemeSet = costWageSchemeSetMapper.queryCostWageSchemeName(entityMap);

		if (costWageSchemeSet !=null) {

			return "{\"error\":\"方案名称已存在\"}";

		}
		
		try {
			
			CostWageScheme costWageScheme= costWageSchemeMapper.queryCostWageSequence();
			
			entityMap.put("scheme_id", costWageScheme.getScheme_id());
			
			costWageSchemeMapper.addCostWageScheme(entityMap);
			
			costWageSchemeSetMapper.addCostWageSchemeSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostWageSchemeSet\"}";

		}

	}

	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量添加CostWageSchemeSet
	 * @param  CostWageSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostWageSchemeSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
/*
		int costWageSchemeSetname=costWageSchemeSetMapper.queryCostWageSchemeName(entityList);
		
		if(costWageSchemeSetname > 0){
			return "{\"error\":\"名称重复,请重新添加.\"}";
		}*/
		for(Map<String, Object> item : entityList){
			CostWageSchemeSet  costWageSchemeSet = costWageSchemeSetMapper.queryCostWageSchemeName(item);

			if (costWageSchemeSet !=null) {

				return "{\"error\":\"方案名称已存在\"}";

			}
	}
		try {
			
			costWageSchemeMapper.addCostWageScheme(entityList.get(0));
			
			costWageSchemeSetMapper.addBatchCostWageSchemeSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostWageSchemeSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostWageSchemeSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryCostWageSchemeSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryCostWageSchemeSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostWageSchemeSetPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costWageSchemeSetMapper.queryCostWageSchemeSetPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 查询CostWageSchemeSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostWageSchemeSet queryCostWageSchemeSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costWageSchemeSetMapper.queryCostWageSchemeSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量删除CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostWageSchemeSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costWageSchemeSetMapper.deleteBatchCostWageSchemeSet(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostWageSchemeSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 删除CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostWageSchemeSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costWageSchemeSetMapper.deleteCostWageSchemeSet(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostWageSchemeSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 更新CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostWageSchemeSet(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			costWageSchemeSetMapper.updateCostWageSchemeSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageSchemeSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案表<BR> 批量更新CostWageSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostWageSchemeSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costWageSchemeMapper.updateCostWageScheme(entityList.get(0));
			
			costWageSchemeSetMapper.deleteCostWageSchemeSet(entityList.get(0));
			
			costWageSchemeSetMapper.addBatchCostWageSchemeSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageSchemeSet\"}";

		}
		
	}

	@Override
	public String queryCostWageSchemeSetByTitle(Map<String, Object> entityMap)
			throws DataAccessException {
		
		String scheme_id = entityMap.get("scheme_id").toString();
		if(!"".equals(scheme_id) && scheme_id != null){
			List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryCostWageSchemeSetByTitle(entityMap);
			return ChdJson.toJson(list);
		}
		return ChdJson.toJson(new ArrayList<CostWageSchemeSet>());
	}

	@Override
	public List<CostWageSchemeSet> queryCostWageSchemeSetByTitleList(
			Map<String, Object> entityMap) throws DataAccessException {
		List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryCostWageSchemeSetByTitle(entityMap);
		return list;
	}
	/**
	 * @Description 
	 * 职工分类工资配置查询<BR> 查询queryCostWageList
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostWageList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costWageSchemeSetMapper.queryCostWageList(entityMap),true);
	}

	@Override
	public String queryCostWageMap(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costWageSchemeSetMapper.queryCostWageMap(entityMap),true);
	}

	@Override
	public String queryCostWageItemList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costWageSchemeSetMapper.queryCostWageItemList(entityMap),true);
	}


	@Override
	public String queryWageName(Map<String, Object> entityMap)
			throws DataAccessException {
	
		List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryWageName(entityMap);
		return ChdJson.toJson(list);
	}
}
