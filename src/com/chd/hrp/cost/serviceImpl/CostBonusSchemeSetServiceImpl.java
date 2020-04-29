/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.ArrayList;
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
import com.chd.hrp.cost.dao.CostBonusSchemeMapper;
import com.chd.hrp.cost.dao.CostBonusSchemeSetMapper;
import com.chd.hrp.cost.entity.CostBonusScheme;
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.service.CostBonusSchemeSetService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 职工奖金查询方案表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costBonusSchemeSetService")
public class CostBonusSchemeSetServiceImpl implements CostBonusSchemeSetService {

	private static Logger logger = Logger.getLogger(CostBonusSchemeSetServiceImpl.class);
	
	@Resource(name = "costBonusSchemeSetMapper")
	private final CostBonusSchemeSetMapper costBonusSchemeSetMapper = null;
    
	@Resource(name = "costBonusSchemeMapper")
	private final CostBonusSchemeMapper costBonusSchemeMapper = null;
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 添加CostBonusSchemeSet
	 * @param CostBonusSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException{
		
		CostBonusSchemeSet costBonusSchemeSet = costBonusSchemeSetMapper.queryCostBonusSchemeName(entityMap);

		if (costBonusSchemeSet != null) {

			return "{\"error\":\"此对应关系已存在.\"}";

		}
		
		try {
			
			CostBonusScheme BonusScheme= costBonusSchemeMapper.queryCostBonusSequence();
			
			entityMap.put("scheme_id", BonusScheme.getScheme_id());
			
			costBonusSchemeMapper.addCostBonusScheme(entityMap);
			
			costBonusSchemeSetMapper.addCostBonusSchemeSet(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostBonusSchemeSet\"}";

		}

	}
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量添加CostBonusSchemeSet
	 * @param  CostBonusSchemeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostBonusSchemeSet(List<Map<String,Object>> entityList)throws DataAccessException{
       for(Map<String, Object> item : entityList){
    		CostBonusSchemeSet costBonusSchemeSet = costBonusSchemeSetMapper.queryCostBonusSchemeName(item);
    		if (costBonusSchemeSet !=null) {

				return "{\"error\":\"方案名称已存在\"}";

			}
       }
		try {
			
			costBonusSchemeMapper.addCostBonusScheme(entityList.get(0));
			
			costBonusSchemeSetMapper.addBatchCostBonusSchemeSet(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostBonusSchemeSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSet分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostBonusSchemeSet(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostBonusSchemeSet> list = costBonusSchemeSetMapper.queryCostBonusSchemeSet(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostBonusSchemeSet> list = costBonusSchemeSetMapper.queryCostBonusSchemeSet(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostBonusSchemeSetPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costBonusSchemeSetMapper.queryCostBonusSchemeSetPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 查询CostBonusSchemeSetByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostBonusSchemeSet queryCostBonusSchemeSetByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costBonusSchemeSetMapper.queryCostBonusSchemeSetByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量删除CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostBonusSchemeSet(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			

				int state = costBonusSchemeSetMapper.deleteBatchCostBonusSchemeSet(entityList);
				costBonusSchemeMapper.deleteBatchCostBonusScheme(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostBonusSchemeSet\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 删除CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostBonusSchemeSet(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costBonusSchemeSetMapper.deleteCostBonusSchemeSet(entityMap);
				costBonusSchemeMapper.deleteCostBonusScheme(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostBonusSchemeSet\"}";

		}
    }
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 更新CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostBonusSchemeSet(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costBonusSchemeSetMapper.updateCostBonusSchemeSet(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusSchemeSet\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工奖金查询方案表<BR> 批量更新CostBonusSchemeSet
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostBonusSchemeSet(List<Map<String,Object>> entityList)throws DataAccessException{
		
		
		
		try {
			
			costBonusSchemeMapper.updateCostBonusScheme(entityList.get(0));
			
			costBonusSchemeSetMapper.deleteCostBonusSchemeSet(entityList.get(0));
			
			costBonusSchemeSetMapper.addBatchCostBonusSchemeSet(entityList);

			//costBonusSchemeSetMapper.updateBatchCostBonusSchemeSet(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusSchemeSet\"}";

		}
		
	}

	@Override
	public String queryCostBonusSchemeSetByTitle(Map<String, Object> entityMap)
			throws DataAccessException {
		String scheme_id = entityMap.get("scheme_id").toString();
		if(!"".equals(scheme_id) && scheme_id != null){
			List<CostBonusSchemeSet> list = costBonusSchemeSetMapper.queryCostBonusSchemeSetByTitle(entityMap);
			return ChdJson.toJson(list);
		}
		return ChdJson.toJson(new ArrayList<CostBonusSchemeSet>());
	}

	@Override
	public List<CostBonusSchemeSet> queryCostBonusSchemeSetByTitleList(
			Map<String, Object> entityMap) throws DataAccessException {
		return costBonusSchemeSetMapper.queryCostBonusSchemeSetByTitle(entityMap);
	}

	@Override
	public String queryCostBonusList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costBonusSchemeSetMapper.queryCostBonusList(entityMap),true);
	}

	@Override
	public String queryCostBonusMap(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costBonusSchemeSetMapper.queryCostBonusMap(entityMap),true);
	}

	@Override
	public String queryCostBonusItemList(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(costBonusSchemeSetMapper.queryCostBonusItemList(entityMap),true);
	}
}
