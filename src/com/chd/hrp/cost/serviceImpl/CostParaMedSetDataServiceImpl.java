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
import com.chd.hrp.cost.dao.CostParaMedSetDataMapper;
import com.chd.hrp.cost.entity.CostParaMedSetData;
import com.chd.hrp.cost.service.CostParaMedSetDataService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 医技分摊设置采集数据表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costParaMedSetDataService")
public class CostParaMedSetDataServiceImpl implements CostParaMedSetDataService {

	private static Logger logger = Logger.getLogger(CostParaMedSetDataServiceImpl.class);
	
	@Resource(name = "costParaMedSetDataMapper")
	private final CostParaMedSetDataMapper costParaMedSetDataMapper = null;
    
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 添加CostParaMedSetData
	 * @param CostParaMedSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException{
		
		/*CostParaMedSetData costParaMedSetData = queryCostParaMedSetDataByCode(entityMap);

		if (costParaMedSetData != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}*/
		
		try {
			
			costParaMedSetDataMapper.deleteCostParaMedSetData(entityMap);
			
			costParaMedSetDataMapper.addCostParaMedSetData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostParaMedSetData\"}";

		}

	}
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量添加CostParaMedSetData
	 * @param  CostParaMedSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostParaMedSetData(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costParaMedSetDataMapper.addBatchCostParaMedSetData(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostParaMedSetData\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostParaMedSetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostParaMedSetData> list = costParaMedSetDataMapper.queryCostParaMedSetData(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostParaMedSetData> list = costParaMedSetDataMapper.queryCostParaMedSetData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 查询CostParaMedSetDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostParaMedSetData queryCostParaMedSetDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costParaMedSetDataMapper.queryCostParaMedSetDataByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量删除CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostParaMedSetData(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costParaMedSetDataMapper.deleteBatchCostParaMedSetData(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostParaMedSetData\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 删除CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostParaMedSetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costParaMedSetDataMapper.deleteCostParaMedSetData(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostParaMedSetData\"}";

		}
    }
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 更新CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostParaMedSetData(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costParaMedSetDataMapper.updateCostParaMedSetData(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaMedSetData\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医技分摊设置采集数据表<BR> 批量更新CostParaMedSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostParaMedSetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costParaMedSetDataMapper.updateBatchCostParaMedSetData(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaMedSetData\"}";

		}
		
	}
}
