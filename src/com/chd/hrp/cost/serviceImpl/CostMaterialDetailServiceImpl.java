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
import com.chd.hrp.cost.dao.CostMaterialDetailMapper;
import com.chd.hrp.cost.entity.CostMaterialDetail;
import com.chd.hrp.cost.service.CostMaterialDetailService;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室材料支出明细表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costMaterialDetailService")
public class CostMaterialDetailServiceImpl implements CostMaterialDetailService {
  
	private static Logger logger = Logger.getLogger(CostMaterialDetailServiceImpl.class);
	
	@Resource(name = "costMaterialDetailMapper")
	private final CostMaterialDetailMapper costMaterialDetailMapper = null;
	
	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;
    
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 添加CostMaterialDetail
	 * @param CostMaterialDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostMaterialDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("year_month", entityMap.get("year_month"));
		
		byCodeMap.put("dept_code", entityMap.get("dept_code"));
		
		byCodeMap.put("mate_type_code", entityMap.get("mate_type_code"));
		

		byCodeMap.put("source_code", "001");
		
		Source source= sourceMapper.querySourceByCode(byCodeMap);
		
		entityMap.put("source_id", source.getSource_id());
		
		CostMaterialDetail costMaterialDetail = queryCostMaterialDetailByCode(byCodeMap);

		if (costMaterialDetail != null) {

			return "{\"error\":\"当月下存在配置.\"}";

		}
		
		try {
			
			costMaterialDetailMapper.addCostMaterialDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostMaterialDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 批量添加CostMaterialDetail
	 * @param  CostMaterialDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostMaterialDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costMaterialDetailMapper.addBatchCostMaterialDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostMaterialDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 查询CostMaterialDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostMaterialDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostMaterialDetail> list = costMaterialDetailMapper.queryCostMaterialDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostMaterialDetail> list = costMaterialDetailMapper.queryCostMaterialDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostMaterialDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costMaterialDetailMapper.queryCostMaterialDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 查询CostMaterialDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostMaterialDetail queryCostMaterialDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costMaterialDetailMapper.queryCostMaterialDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 批量删除CostMaterialDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostMaterialDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costMaterialDetailMapper.deleteBatchCostMaterialDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostMaterialDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 删除CostMaterialDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostMaterialDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costMaterialDetailMapper.deleteCostMaterialDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostMaterialDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 更新CostMaterialDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostMaterialDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costMaterialDetailMapper.updateCostMaterialDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMaterialDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室材料支出明细表<BR> 批量更新CostMaterialDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostMaterialDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costMaterialDetailMapper.updateBatchCostMaterialDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMaterialDetail\"}";

		}
		
	}
	@Override
	public String addSynData(Map<String, Object> entityMap) throws DataAccessException {
		try {

			costMaterialDetailMapper.addSynData(entityMap);

			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  updateCostMaterialDetail\"}";

		}
	}
}
