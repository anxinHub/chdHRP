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
import com.chd.hrp.cost.dao.CostIassetArrtMapper;
import com.chd.hrp.cost.entity.CostIassetArrt;
import com.chd.hrp.cost.service.CostIassetArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_无形资产字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIassetArrtService")
public class CostIassetArrtServiceImpl implements CostIassetArrtService {

	private static Logger logger = Logger.getLogger(CostIassetArrtServiceImpl.class);
	
	@Resource(name = "costIassetArrtMapper")
	private final CostIassetArrtMapper costIassetArrtMapper = null;
    
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 添加CostIassetArrt
	 * @param CostIassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("asset_code", entityMap.get("asset_code"));
		
		CostIassetArrt costIassetArrt = queryCostIassetArrtByCode(byCodeMap);

		if (costIassetArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("asset_code").toString() + "重复.\"}";

		}
		
		try {
			
			costIassetArrtMapper.addCostIassetArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIassetArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量添加CostIassetArrt
	 * @param  CostIassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIassetArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIassetArrtMapper.addBatchCostIassetArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIassetArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIassetArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIassetArrt> list = costIassetArrtMapper.queryCostIassetArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIassetArrt> list = costIassetArrtMapper.queryCostIassetArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostIassetArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costIassetArrtMapper.queryCostIassetArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 查询CostIassetArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIassetArrt queryCostIassetArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIassetArrtMapper.queryCostIassetArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量删除CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIassetArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costIassetArrtMapper.deleteBatchCostIassetArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIassetArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 删除CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIassetArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIassetArrtMapper.deleteCostIassetArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIassetArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 更新CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIassetArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIassetArrtMapper.updateCostIassetArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_无形资产字典<BR> 批量更新CostIassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIassetArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIassetArrtMapper.updateBatchCostIassetArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetArrt\"}";

		}
		
	}

	@Override
	public String syncCostIassetArrt(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
