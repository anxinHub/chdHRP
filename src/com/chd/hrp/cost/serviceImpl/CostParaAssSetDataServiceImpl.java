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
import com.chd.hrp.cost.dao.CostParaAssSetDataMapper;
import com.chd.hrp.cost.entity.CostParaAssSetData;
import com.chd.hrp.cost.service.CostParaAssSetDataService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 医辅分摊设置采集数据表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costParaAssSetDataService")
public class CostParaAssSetDataServiceImpl implements CostParaAssSetDataService {

	private static Logger logger = Logger.getLogger(CostParaAssSetDataServiceImpl.class);
	
	@Resource(name = "costParaAssSetDataMapper")
	private final CostParaAssSetDataMapper costParaAssSetDataMapper = null;
    
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 添加CostParaAssSetData
	 * @param CostParaAssSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException{
		
		/*CostParaAssSetData costParaAssSetData = queryCostParaAssSetDataByCode(entityMap);

		if (costParaAssSetData != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}*/
		
		try {
			
			costParaAssSetDataMapper.deleteCostParaAssSetData(entityMap);
			
			costParaAssSetDataMapper.addCostParaAssSetData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostParaAssSetData\"}";

		}

	}
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量添加CostParaAssSetData
	 * @param  CostParaAssSetData entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostParaAssSetData(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costParaAssSetDataMapper.addBatchCostParaAssSetData(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostParaAssSetData\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetData分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostParaAssSetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostParaAssSetData> list = costParaAssSetDataMapper.queryCostParaAssSetData(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostParaAssSetData> list = costParaAssSetDataMapper.queryCostParaAssSetData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 查询CostParaAssSetDataByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostParaAssSetData queryCostParaAssSetDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costParaAssSetDataMapper.queryCostParaAssSetDataByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量删除CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostParaAssSetData(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costParaAssSetDataMapper.deleteBatchCostParaAssSetData(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostParaAssSetData\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 删除CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostParaAssSetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costParaAssSetDataMapper.deleteCostParaAssSetData(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostParaAssSetData\"}";

		}
    }
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 更新CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostParaAssSetData(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costParaAssSetDataMapper.updateCostParaAssSetData(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaAssSetData\"}";

		}
	}
	
	/**
	 * @Description 
	 * 医辅分摊设置采集数据表<BR> 批量更新CostParaAssSetData
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostParaAssSetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costParaAssSetDataMapper.updateBatchCostParaAssSetData(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostParaAssSetData\"}";

		}
		
	}
}
