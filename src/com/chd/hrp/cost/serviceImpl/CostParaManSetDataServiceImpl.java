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
import com.chd.hrp.cost.dao.CostParaManSetDataMapper;
import com.chd.hrp.cost.entity.CostParaManSetData;
import com.chd.hrp.cost.service.CostParaManSetDataService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 管理分摊设置采集数据表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costParaManSetDataService")
public class CostParaManSetDataServiceImpl implements CostParaManSetDataService {

	private static Logger logger = Logger.getLogger(CostParaManSetDataServiceImpl.class);
	
	@Resource(name = "costParaManSetDataMapper")
	private final CostParaManSetDataMapper costParaManSetDataMapper = null;
    
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 添加CostParaManSetData
	 * @param CostParaManSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException{
		
		/*CostParaManSetData costParaManSetData = queryCostParaManSetDataByCode(entityMap);

		if (costParaManSetData != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}*/
		
		try {
			
			costParaManSetDataMapper.deleteCostParaManSetData(entityMap);
			
			costParaManSetDataMapper.addCostParaManSetData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostParaManSetData\"}";

		}

	}
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量添加CostParaManSetData
	 * @param  CostParaManSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostParaManSetData(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costParaManSetDataMapper.addBatchCostParaManSetData(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostParaManSetData\"}";

		}
	}
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostParaManSetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostParaManSetData> list = costParaManSetDataMapper.queryCostParaManSetData(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostParaManSetData> list = costParaManSetDataMapper.queryCostParaManSetData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 查询CostParaManSetDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostParaManSetData queryCostParaManSetDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costParaManSetDataMapper.queryCostParaManSetDataByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量删除CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostParaManSetData(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costParaManSetDataMapper.deleteBatchCostParaManSetData(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostParaManSetData\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 删除CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostParaManSetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costParaManSetDataMapper.deleteCostParaManSetData(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostParaManSetData\"}";

		}
    }
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 更新CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostParaManSetData(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costParaManSetDataMapper.updateCostParaManSetData(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaManSetData\"}";

		}
	}
	
	/**
	 * @Description 
	 * 管理分摊设置采集数据表<BR> 批量更新CostParaManSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostParaManSetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costParaManSetDataMapper.updateBatchCostParaManSetData(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaManSetData\"}";

		}
		
	}
}
