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
import com.chd.hrp.cost.dao.CostIncomeMapper;
import com.chd.hrp.cost.entity.CostIncome;
import com.chd.hrp.cost.service.CostIncomeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室收入数据总表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIncomeService")
public class CostIncomeServiceImpl implements CostIncomeService {

	private static Logger logger = Logger.getLogger(CostIncomeServiceImpl.class);
	
	@Resource(name = "costIncomeMapper")
	private final CostIncomeMapper costIncomeMapper = null;
    
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 添加CostIncome
	 * @param CostIncome entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIncome(Map<String,Object> entityMap)throws DataAccessException{
		
		CostIncome costIncome = queryCostIncomeByCode(entityMap);

		if (costIncome != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costIncomeMapper.addCostIncome(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIncome\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量添加CostIncome
	 * @param  CostIncome entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIncome(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIncomeMapper.addBatchCostIncome(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIncome\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncome分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIncome(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIncome> list = costIncomeMapper.queryCostIncome(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIncome> list = costIncomeMapper.queryCostIncome(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncomeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIncome queryCostIncomeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIncomeMapper.queryCostIncomeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量删除CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIncome(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costIncomeMapper.deleteBatchCostIncome(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIncome\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 删除CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIncome(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIncomeMapper.deleteCostIncome(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIncome\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 更新CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIncome(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIncomeMapper.updateCostIncome(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIncome\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量更新CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIncome(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIncomeMapper.updateBatchCostIncome(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIncome\"}";

		}
		
	}
}
