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
import com.chd.hrp.cost.dao.CostIassetDetailMapper;
import com.chd.hrp.cost.entity.CostIassetDetail;
import com.chd.hrp.cost.service.CostIassetDetailService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室无形资产折旧明细<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIassetDetailService")
public class CostIassetDetailServiceImpl implements CostIassetDetailService {
 
	private static Logger logger = Logger.getLogger(CostIassetDetailServiceImpl.class);
	
	@Resource(name = "costIassetDetailMapper")
	private final CostIassetDetailMapper costIassetDetailMapper = null;
    
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 添加CostIassetDetail
	 * @param CostIassetDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("year_month", entityMap.get("year_month"));
		
		byCodeMap.put("dept_code", entityMap.get("dept_code"));
		
		byCodeMap.put("asset_type_code", entityMap.get("asset_type_code"));
		
		byCodeMap.put("source_id", entityMap.get("source_id"));
		
		CostIassetDetail costIassetDetail = queryCostIassetDetailByCode(byCodeMap);

		if (costIassetDetail != null) {

			return "{\"error\":\"当月下存在配置.\"}";

		}
		
		try {
			
			costIassetDetailMapper.addCostIassetDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIassetDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量添加CostIassetDetail
	 * @param  CostIassetDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIassetDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIassetDetailMapper.addBatchCostIassetDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIassetDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIassetDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIassetDetail> list = costIassetDetailMapper.queryCostIassetDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIassetDetail> list = costIassetDetailMapper.queryCostIassetDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostIassetDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costIassetDetailMapper.queryCostIassetDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 查询CostIassetDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIassetDetail queryCostIassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIassetDetailMapper.queryCostIassetDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量删除CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIassetDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costIassetDetailMapper.deleteBatchCostIassetDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIassetDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 删除CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIassetDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIassetDetailMapper.deleteCostIassetDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIassetDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 更新CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIassetDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIassetDetailMapper.updateCostIassetDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室无形资产折旧明细<BR> 批量更新CostIassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIassetDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIassetDetailMapper.updateBatchCostIassetDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetDetail\"}";

		}
		
	}

	@Override
	public String addSynData(Map<String, Object> entityMap) throws DataAccessException {
		try {

			costIassetDetailMapper.addSynData(entityMap);

			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  updateCostMaterialDetail\"}";

		}
	}
}
