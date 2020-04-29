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
import com.chd.hrp.cost.dao.CostChargeKindArrtMapper;
import com.chd.hrp.cost.dao.CostIncomeTypeMapper;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.entity.CostIncomeType;
import com.chd.hrp.cost.service.CostIncomeTypeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIncomeTypeService")
public class CostIncomeTypeServiceImpl implements CostIncomeTypeService {

	private static Logger logger = Logger.getLogger(CostIncomeTypeServiceImpl.class);
	
	@Resource(name = "costIncomeTypeMapper")
	private final CostIncomeTypeMapper costIncomeTypeMapper = null;
	
	@Resource(name = "costChargeKindArrtMapper")
	private final CostChargeKindArrtMapper costChargeKindArrtMapper = null;
    
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 添加CostIncomeType
	 * @param CostIncomeType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIncomeType(Map<String,Object> entityMap)throws DataAccessException{
		
		CostIncomeType costIncomeType = queryCostIncomeTypeByCode(entityMap);

		if (costIncomeType != null) {

			return "{\"error\":\"编码：" + entityMap.get("income_type_code").toString() + "重复.\"}";

		}
		
		try {
			
			costIncomeTypeMapper.addCostIncomeType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIncomeType\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量添加CostIncomeType
	 * @param  CostIncomeType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIncomeType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIncomeTypeMapper.addBatchCostIncomeType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIncomeType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIncomeType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIncomeType> list = costIncomeTypeMapper.queryCostIncomeType(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncomeType> list = costIncomeTypeMapper.queryCostIncomeType(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 查询CostIncomeTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIncomeType queryCostIncomeTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIncomeTypeMapper.queryCostIncomeTypeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量删除CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIncomeType(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			for(int i = 0 ; i < entityList.size() ; i ++){
				Map<String,Object> map = entityList.get(i);
				CostChargeKindArrt chargeKindArrt = costChargeKindArrtMapper.queryCostChargeKindArrtByCode(map);
				if(chargeKindArrt != null){
					return "{\"error\":\"当前收入类型已被收费类别引用,不能删除.\"}";
				}
			}
			
				int state = costIncomeTypeMapper.deleteBatchCostIncomeType(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIncomeType\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 删除CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIncomeType(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIncomeTypeMapper.deleteCostIncomeType(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIncomeType\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 更新CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIncomeType(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIncomeTypeMapper.updateCostIncomeType(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIncomeType\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR> 批量更新CostIncomeType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIncomeType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIncomeTypeMapper.updateBatchCostIncomeType(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIncomeType\"}";

		}
		
	}
}
