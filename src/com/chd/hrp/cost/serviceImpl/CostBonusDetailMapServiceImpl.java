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
import com.chd.hrp.cost.entity.CostBonusDetailMap;
import com.chd.hrp.cost.entity.CostWageDetailMap;
import com.chd.hrp.cost.service.CostBonusDetailMapService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 奖金明细数据与工资项关系表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costBonusDetailMapService")
public class CostBonusDetailMapServiceImpl implements CostBonusDetailMapService {

	private static Logger logger = Logger.getLogger(CostBonusDetailMapServiceImpl.class);
	
	@Resource(name = "costBonusDetailMapMapper")
	private final CostBonusDetailMapMapper costBonusDetailMapMapper = null;
    
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 添加CostBonusDetailMap
	 * @param CostBonusDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException{
		
		CostBonusDetailMap costBonusDetailMap = queryCostBonusDetailMapByCode(entityMap);

		if (costBonusDetailMap != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costBonusDetailMapMapper.addCostBonusDetailMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostBonusDetailMap\"}";

		}

	}
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量添加CostBonusDetailMap
	 * @param  CostBonusDetailMap entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostBonusDetailMap(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costBonusDetailMapMapper.addBatchCostBonusDetailMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostBonusDetailMap\"}";

		}
	}
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMap分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostBonusDetailMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostBonusDetailMap> list = costBonusDetailMapMapper.queryCostBonusDetailMap(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostBonusDetailMap> list = costBonusDetailMapMapper.queryCostBonusDetailMap(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMapByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostBonusDetailMap queryCostBonusDetailMapByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costBonusDetailMapMapper.queryCostBonusDetailMapByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量删除CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostBonusDetailMap(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

			int state = costBonusDetailMapMapper.deleteBatchCostBonusDetailMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostBonusDetailMap\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 删除CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostBonusDetailMap(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			costBonusDetailMapMapper.deleteCostBonusDetailMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostBonusDetailMap\"}";

		}
    }
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 更新CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costBonusDetailMapMapper.updateCostBonusDetailMap(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusDetailMap\"}";

		}
	}
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量更新CostBonusDetailMap
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostBonusDetailMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costBonusDetailMapMapper.updateBatchCostBonusDetailMap(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusDetailMap\"}";

		}
		
	}

	@Override
	public CostBonusDetailMap querySequenceById() throws DataAccessException {

		return costBonusDetailMapMapper.querySequenceById();
	}
}
