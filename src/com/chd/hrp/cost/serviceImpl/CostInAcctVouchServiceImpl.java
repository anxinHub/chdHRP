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
import com.chd.hrp.cost.dao.CostInAcctVouchMapper;
import com.chd.hrp.cost.entity.CostInAcctVouch;
import com.chd.hrp.cost.service.CostInAcctVouchService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室收入总账<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costInAcctVouchService")
public class CostInAcctVouchServiceImpl implements CostInAcctVouchService {

	private static Logger logger = Logger.getLogger(CostInAcctVouchServiceImpl.class);
	
	@Resource(name = "costInAcctVouchMapper")
	private final CostInAcctVouchMapper costInAcctVouchMapper = null;
    
	/**
	 * @Description 
	 * 科室收入总账<BR> 添加CostInAcctVouch
	 * @param CostInAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException{
		
		CostInAcctVouch costInAcctVouch = queryCostInAcctVouchByCode(entityMap);

		if (costInAcctVouch != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costInAcctVouchMapper.addCostInAcctVouch(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostInAcctVouch\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量添加CostInAcctVouch
	 * @param  CostInAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostInAcctVouch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costInAcctVouchMapper.addBatchCostInAcctVouch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostInAcctVouch\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostInAcctVouch(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostInAcctVouch> list = costInAcctVouchMapper.queryCostInAcctVouch(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostInAcctVouch> list = costInAcctVouchMapper.queryCostInAcctVouch(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 查询CostInAcctVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostInAcctVouch queryCostInAcctVouchByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costInAcctVouchMapper.queryCostInAcctVouchByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量删除CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostInAcctVouch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costInAcctVouchMapper.deleteBatchCostInAcctVouch(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostInAcctVouch\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 删除CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostInAcctVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costInAcctVouchMapper.deleteCostInAcctVouch(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostInAcctVouch\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 更新CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostInAcctVouch(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costInAcctVouchMapper.updateCostInAcctVouch(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostInAcctVouch\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室收入总账<BR> 批量更新CostInAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostInAcctVouch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costInAcctVouchMapper.updateBatchCostInAcctVouch(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostInAcctVouch\"}";

		}
		
	}
}
