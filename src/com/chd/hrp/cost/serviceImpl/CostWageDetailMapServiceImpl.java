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
import com.chd.hrp.cost.dao.CostWageDetailMapMapper;
import com.chd.hrp.cost.entity.CostWageDetailMap;
import com.chd.hrp.cost.service.CostWageDetailMapService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资明细数据与工资项关系表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costWageDetailMapService")
public class CostWageDetailMapServiceImpl implements CostWageDetailMapService {

	private static Logger logger = Logger.getLogger(CostWageDetailMapServiceImpl.class);
	
	@Resource(name = "costWageDetailMapMapper")
	private final CostWageDetailMapMapper costWageDetailMapMapper = null;
    
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 添加CostWageDetailMap
	 * @param CostWageDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException{
		
		CostWageDetailMap costWageDetailMap = queryCostWageDetailMapByCode(entityMap);

		if (costWageDetailMap != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costWageDetailMapMapper.addCostWageDetailMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostWageDetailMap\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量添加CostWageDetailMap
	 * @param  CostWageDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostWageDetailMap(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costWageDetailMapMapper.addBatchCostWageDetailMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostWageDetailMap\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMap分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostWageDetailMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostWageDetailMap> list = costWageDetailMapMapper.queryCostWageDetailMap(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostWageDetailMap> list = costWageDetailMapMapper.queryCostWageDetailMap(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMapByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostWageDetailMap queryCostWageDetailMapByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costWageDetailMapMapper.queryCostWageDetailMapByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量删除CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostWageDetailMap(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

			int state = costWageDetailMapMapper.deleteBatchCostWageDetailMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostWageDetailMap\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 删除CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostWageDetailMap(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			costWageDetailMapMapper.deleteCostWageDetailMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostWageDetailMap\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 更新CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costWageDetailMapMapper.updateCostWageDetailMap(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageDetailMap\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量更新CostWageDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostWageDetailMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costWageDetailMapMapper.updateBatchCostWageDetailMap(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageDetailMap\"}";

		}
		
	}

	@Override
	public CostWageDetailMap querySequence() throws DataAccessException {
		
		return costWageDetailMapMapper.querySequence();
	}
}
