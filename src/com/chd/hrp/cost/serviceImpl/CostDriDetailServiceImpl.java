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
import com.chd.hrp.cost.dao.CostDriDetailMapper;
import com.chd.hrp.cost.entity.CostDriDetail;
import com.chd.hrp.cost.service.CostDriDetailService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室成本明细数据表_平级分摊<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costDriDetailService")
public class CostDriDetailServiceImpl implements CostDriDetailService {

	private static Logger logger = Logger.getLogger(CostDriDetailServiceImpl.class);
	
	@Resource(name = "costDriDetailMapper")
	private final CostDriDetailMapper costDriDetailMapper = null;
    
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 添加CostDriDetail
	 * @param CostDriDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostDriDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		CostDriDetail costDriDetail = queryCostDriDetailByCode(entityMap);

		if (costDriDetail != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costDriDetailMapper.addCostDriDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDriDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量添加CostDriDetail
	 * @param  CostDriDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostDriDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costDriDetailMapper.addBatchCostDriDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDriDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostDriDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostDriDetail> list = costDriDetailMapper.queryCostDriDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDriDetail> list = costDriDetailMapper.queryCostDriDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostDriDetail queryCostDriDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costDriDetailMapper.queryCostDriDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量删除CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostDriDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costDriDetailMapper.deleteBatchCostDriDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDriDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 删除CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostDriDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costDriDetailMapper.deleteCostDriDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDriDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 更新CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostDriDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costDriDetailMapper.updateCostDriDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDriDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量更新CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostDriDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costDriDetailMapper.updateBatchCostDriDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDriDetail\"}";

		}
		
	}
}
