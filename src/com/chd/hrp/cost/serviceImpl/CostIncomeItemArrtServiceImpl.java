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
import com.chd.hrp.cost.dao.CostChargeKindArrtMapper;
import com.chd.hrp.cost.dao.CostIncomeItemArrtMapper;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.entity.CostIncomeItemArrt;
import com.chd.hrp.cost.service.CostIncomeItemArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_收入项目字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIncomeItemArrtService")
public class CostIncomeItemArrtServiceImpl implements CostIncomeItemArrtService {

	private static Logger logger = Logger.getLogger(CostIncomeItemArrtServiceImpl.class);
	
	@Resource(name = "costIncomeItemArrtMapper")
	private final CostIncomeItemArrtMapper costIncomeItemArrtMapper = null;
	
	@Resource(name = "costChargeKindArrtMapper")
	private final CostChargeKindArrtMapper costChargeKindArrtMapper = null;
    
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 添加CostIncomeItemArrt
	 * @param CostIncomeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		CostIncomeItemArrt costIncomeItemArrt = queryCostIncomeItemArrtByCode(entityMap);

		if (costIncomeItemArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("income_item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costIncomeItemArrtMapper.addCostIncomeItemArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIncomeItemArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量添加CostIncomeItemArrt
	 * @param  CostIncomeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIncomeItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIncomeItemArrtMapper.addBatchCostIncomeItemArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIncomeItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIncomeItemArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIncomeItemArrt> list = costIncomeItemArrtMapper.queryCostIncomeItemArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeItemArrt> list = costIncomeItemArrtMapper.queryCostIncomeItemArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	 @Override
		public List<Map<String, Object>> queryCostIncomeItemArrtPrint(Map<String, Object> entityMap) throws DataAccessException {
			
			
			List<Map<String, Object>> list=costIncomeItemArrtMapper.queryCostIncomeItemArrtPrint(entityMap);
			
			return list;

		}
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 查询CostIncomeItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIncomeItemArrt queryCostIncomeItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIncomeItemArrtMapper.queryCostIncomeItemArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量删除CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIncomeItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(int i = 0 ; i < entityList.size() ; i ++){
					Map<String,Object> map = entityList.get(i);
					List<Map<String,Object>> chargeKindArrt = costChargeKindArrtMapper.queryCostChargeKindArrt(map);
					if(chargeKindArrt.size()>0){
						return "{\"error\":\"当前收入项目已被收费类别引用,不能删除.\"}";
					}
				}
				int state = costIncomeItemArrtMapper.deleteBatchCostIncomeItemArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIncomeItemArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 删除CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIncomeItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIncomeItemArrtMapper.deleteCostIncomeItemArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIncomeItemArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 更新CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIncomeItemArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIncomeItemArrtMapper.updateCostIncomeItemArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIncomeItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收入项目字典<BR> 批量更新CostIncomeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIncomeItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIncomeItemArrtMapper.updateBatchCostIncomeItemArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIncomeItemArrt\"}";

		}
		
	}

	@Override
	public List<CostIncomeItemArrt> queryCostIncomeItemArrtList(
			Map<String, Object> entityMap) throws DataAccessException {
		List<CostIncomeItemArrt> list = costIncomeItemArrtMapper.queryCostIncomeItemArrt(entityMap);
		return list;
	}
}
