/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostChargeItemArrtMapper;
import com.chd.hrp.cost.dao.CostChargeKindArrtMapper;
import com.chd.hrp.cost.entity.CostChargeItemArrt;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.service.CostChargeKindArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_收费类别字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costChargeKindArrtService")
public class CostChargeKindArrtServiceImpl implements CostChargeKindArrtService {

	private static Logger logger = Logger.getLogger(CostChargeKindArrtServiceImpl.class);
	
	@Resource(name = "costChargeKindArrtMapper")
	private final CostChargeKindArrtMapper costChargeKindArrtMapper = null;
	
	@Resource(name = "costChargeItemArrtMapper")
	private final CostChargeItemArrtMapper costChargeItemArrtMapper = null;
    
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 添加CostChargeKindArrt
	 * @param CostChargeKindArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		CostChargeKindArrt costChargeKindArrt = queryCostChargeKindArrtByCode(entityMap);

		if (costChargeKindArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("charge_kind_code").toString() + "重复.\"}";

		}
		
		try {
			
			costChargeKindArrtMapper.addCostChargeKindArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostChargeKindArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量添加CostChargeKindArrt
	 * @param  CostChargeKindArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostChargeKindArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costChargeKindArrtMapper.addBatchCostChargeKindArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostChargeKindArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostChargeKindArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = costChargeKindArrtMapper.queryCostChargeKindArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = costChargeKindArrtMapper.queryCostChargeKindArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 查询CostChargeKindArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostChargeKindArrt queryCostChargeKindArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costChargeKindArrtMapper.queryCostChargeKindArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostChargeKindArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(int i = 0 ; i < entityList.size() ; i ++){
					Map<String,Object> map = entityList.get(i);
					CostChargeItemArrt chargeItemArrt = costChargeItemArrtMapper.queryCostChargeItemArrtByCode(map);
					if(chargeItemArrt != null){
						return "{\"error\":\"当前收费类别已被收费项目引用,不能删除.\"}";
					}
				}
				int state = costChargeKindArrtMapper.deleteBatchCostChargeKindArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostChargeKindArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 删除CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostChargeKindArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costChargeKindArrtMapper.deleteCostChargeKindArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostChargeKindArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostChargeKindArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costChargeKindArrtMapper.updateCostChargeKindArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostChargeKindArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收费类别字典<BR> 批量更新CostChargeKindArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostChargeKindArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costChargeKindArrtMapper.updateBatchCostChargeKindArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostChargeKindArrt\"}";

		}
		
	}

	@Override
	public List<Map<String, Object>> queryCostChargeKindArrtPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = costChargeKindArrtMapper.queryCostChargeKindArrt(entityMap);
		return list;
	}
}
