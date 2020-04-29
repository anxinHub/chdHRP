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
import com.chd.hrp.cost.dao.CostFassetDetailMapper;
import com.chd.hrp.cost.entity.CostFassetDetail;
import com.chd.hrp.cost.service.CostFassetDetailService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室固定资产折旧明细<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costFassetDetailService")
public class CostFassetDetailServiceImpl implements CostFassetDetailService {
 
	private static Logger logger = Logger.getLogger(CostFassetDetailServiceImpl.class);
	
	@Resource(name = "costFassetDetailMapper")
	private final CostFassetDetailMapper costFassetDetailMapper = null;
    
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 添加CostFassetDetail
	 * @param CostFassetDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostFassetDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("year_month", entityMap.get("year_month"));
		
		byCodeMap.put("dept_code", entityMap.get("dept_code"));
		
		byCodeMap.put("asset_type_code", entityMap.get("asset_type_code"));
		
		byCodeMap.put("source_id", entityMap.get("source_id"));
		
		CostFassetDetail costFassetDetail = queryCostFassetDetailByCode(byCodeMap);

		if (costFassetDetail != null) {

			return "{\"error\":\"当月下存在配置.\"}";

		}
		
		try {
			
			costFassetDetailMapper.addCostFassetDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostFassetDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 批量添加CostFassetDetail
	 * @param  CostFassetDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostFassetDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costFassetDetailMapper.addBatchCostFassetDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostFassetDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 查询CostFassetDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostFassetDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostFassetDetail> list = costFassetDetailMapper.queryCostFassetDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostFassetDetail> list = costFassetDetailMapper.queryCostFassetDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostFassetDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costFassetDetailMapper.queryCostFassetDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 查询CostFassetDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostFassetDetail queryCostFassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costFassetDetailMapper.queryCostFassetDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 批量删除CostFassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostFassetDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costFassetDetailMapper.deleteBatchCostFassetDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostFassetDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 删除CostFassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostFassetDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costFassetDetailMapper.deleteCostFassetDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostFassetDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 更新CostFassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostFassetDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costFassetDetailMapper.updateCostFassetDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室固定资产折旧明细<BR> 批量更新CostFassetDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostFassetDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costFassetDetailMapper.updateBatchCostFassetDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetDetail\"}";

		}
		
	}

	@Override
	public String addSynData(Map<String, Object> entityMap) throws DataAccessException {
		try {

			costFassetDetailMapper.addSynData(entityMap);

			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  updateCostMaterialDetail\"}";

		}
	}
}
