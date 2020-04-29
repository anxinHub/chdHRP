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
import com.chd.hrp.cost.dao.CostIassetTypeArrtMapper;
import com.chd.hrp.cost.entity.CostIassetTypeArrt;
import com.chd.hrp.cost.service.CostIassetTypeArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_无形资产分类字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIassetTypeArrtService")
public class CostIassetTypeArrtServiceImpl implements CostIassetTypeArrtService {
 
	private static Logger logger = Logger.getLogger(CostIassetTypeArrtServiceImpl.class);
	
	@Resource(name = "costIassetTypeArrtMapper")
	private final CostIassetTypeArrtMapper costIassetTypeArrtMapper = null;
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 添加CostIassetTypeArrt
	 * @param CostIassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("asset_type_code", entityMap.get("asset_type_code"));
		
		CostIassetTypeArrt costIassetTypeArrt = queryCostIassetTypeArrtByCode(entityMap);

		if (costIassetTypeArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("asset_type_code").toString() + "重复.\"}";

		}
		
		try {
			
			costIassetTypeArrtMapper.addCostIassetTypeArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIassetTypeArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量添加CostIassetTypeArrt
	 * @param  CostIassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIassetTypeArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIassetTypeArrtMapper.addBatchCostIassetTypeArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIassetTypeArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIassetTypeArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIassetTypeArrt> list = costIassetTypeArrtMapper.queryCostIassetTypeArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIassetTypeArrt> list = costIassetTypeArrtMapper.queryCostIassetTypeArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostIassetTypeArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costIassetTypeArrtMapper.queryCostIassetTypeArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 查询CostIassetTypeArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIassetTypeArrt queryCostIassetTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIassetTypeArrtMapper.queryCostIassetTypeArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量删除CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIassetTypeArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costIassetTypeArrtMapper.deleteBatchCostIassetTypeArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIassetTypeArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 删除CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIassetTypeArrtMapper.deleteCostIassetTypeArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIassetTypeArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 更新CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIassetTypeArrtMapper.updateCostIassetTypeArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetTypeArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_无形资产分类字典<BR> 批量更新CostIassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIassetTypeArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIassetTypeArrtMapper.updateBatchCostIassetTypeArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetTypeArrt\"}";

		}
		
	}

	@Override
	public String syncCostIassetTypeArrt(Map<String, Object> entityMap)throws DataAccessException {
		try {
		costIassetTypeArrtMapper.syncCostIassetTypeArrt(entityMap);
		return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostIassetTypeArrt\"}";
	
		}
	}
}
