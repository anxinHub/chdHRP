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
import com.chd.hrp.cost.dao.CostRiskDetailMapper;
import com.chd.hrp.cost.entity.CostRiskDetail;
import com.chd.hrp.cost.service.CostRiskDetailService;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室提取医疗风险基金表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costRiskDetailService")
public class CostRiskDetailServiceImpl implements CostRiskDetailService {

	private static Logger logger = Logger.getLogger(CostRiskDetailServiceImpl.class);
	
	@Resource(name = "costRiskDetailMapper")
	private final CostRiskDetailMapper costRiskDetailMapper = null;
	

	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;
    
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 添加CostRiskDetail
	 * @param CostRiskDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostRiskDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("source_code", "001");

		Source source = sourceMapper.querySourceByCode(entityMap);
		
		entityMap.put("source", source.getSource_id());
		
		CostRiskDetail costRiskDetail = queryCostRiskDetailByCode(entityMap);

		if (costRiskDetail != null) {

			return "{\"error\":\"此年月对应关系已存在.\"}";

		}
		
		try {
			
			costRiskDetailMapper.addCostRiskDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostRiskDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 批量添加CostRiskDetail
	 * @param  CostRiskDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostRiskDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costRiskDetailMapper.addBatchCostRiskDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostRiskDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 查询CostRiskDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostRiskDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostRiskDetail> list = costRiskDetailMapper.queryCostRiskDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostRiskDetail> list = costRiskDetailMapper.queryCostRiskDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostIncomeMainPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costRiskDetailMapper.queryCostIncomeMainPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 查询CostRiskDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostRiskDetail queryCostRiskDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costRiskDetailMapper.queryCostRiskDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 批量删除CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostRiskDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costRiskDetailMapper.deleteBatchCostRiskDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostRiskDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 删除CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostRiskDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costRiskDetailMapper.deleteCostRiskDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostRiskDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 更新CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostRiskDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			entityMap.put("source_code", "001");

			Source source = sourceMapper.querySourceByCode(entityMap);
			
			entityMap.put("source_id", source.getSource_id());

			costRiskDetailMapper.updateCostRiskDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostRiskDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室提取医疗风险基金表<BR> 批量更新CostRiskDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostRiskDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costRiskDetailMapper.updateBatchCostRiskDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostRiskDetail\"}";

		}
		
	}

	@Override
	public CostRiskDetail queryCostSource(Map<String, Object> entityMap) throws DataAccessException {
		
		return costRiskDetailMapper.queryCostSource(entityMap);
	}
}
