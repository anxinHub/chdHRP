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

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostBonusDetailMapMapper;
import com.chd.hrp.cost.dao.CostBonusItemArrtMapper;
import com.chd.hrp.cost.entity.CostBonusDetailMap;
import com.chd.hrp.cost.entity.CostBonusItemArrt;
import com.chd.hrp.cost.service.CostBonusItemArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_奖金项属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costBonusItemArrtService")
public class CostBonusItemArrtServiceImpl implements CostBonusItemArrtService {

	private static Logger logger = Logger.getLogger(CostBonusItemArrtServiceImpl.class);
	
	@Resource(name = "costBonusItemArrtMapper")
	private final CostBonusItemArrtMapper costBonusItemArrtMapper = null;
	
	@Resource(name = "costBonusDetailMapMapper")
	private final CostBonusDetailMapMapper costBonusDetailMapMapper = null;
    
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 添加CostBonusItemArrt
	 * @param CostBonusItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		CostBonusItemArrt costBonusItemArrt = queryCostBonusItemArrtByCode(entityMap);

		if (costBonusItemArrt != null ) {

			return "{\"error\":\"编码：" + costBonusItemArrt.getBonus_item_code() +"已存在.\"}";

		}
				
		try {
			
			costBonusItemArrtMapper.addCostBonusItemArrt(entityMap);
			
			costBonusDetailMapMapper.addCostBonusDetailMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostBonusItemArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量添加CostBonusItemArrt
	 * @param  CostBonusItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostBonusItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costBonusItemArrtMapper.addBatchCostBonusItemArrt(entityList);
			
			costBonusDetailMapMapper.addBatchCostBonusDetailMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostBonusItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostBonusItemArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostBonusItemArrt> list = costBonusItemArrtMapper.queryCostBonusItemArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostBonusItemArrt> list = costBonusItemArrtMapper.queryCostBonusItemArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostBonusItemArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costBonusItemArrtMapper.queryCostBonusItemArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostBonusItemArrt queryCostBonusItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costBonusItemArrtMapper.queryCostBonusItemArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量删除CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostBonusItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costBonusItemArrtMapper.deleteBatchCostBonusItemArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostBonusItemArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 删除CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostBonusItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costBonusItemArrtMapper.deleteCostBonusItemArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostBonusItemArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 更新CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costBonusItemArrtMapper.updateCostBonusItemArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量更新CostBonusItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostBonusItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costBonusItemArrtMapper.updateBatchCostBonusItemArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusItemArrt\"}";

		}
		
	}
}
