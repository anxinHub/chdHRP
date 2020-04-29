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
import com.chd.hrp.cost.dao.CostDrugDetailMapper;
import com.chd.hrp.cost.entity.CostDrugDetail;
import com.chd.hrp.cost.service.CostDrugDetailService;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.entity.Source;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 科室药品费用表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costDrugDetailService")
public class CostDrugDetailServiceImpl implements CostDrugDetailService {

	private static Logger logger = Logger.getLogger(CostDrugDetailServiceImpl.class);
	
	@Resource(name = "costDrugDetailMapper")
	private final CostDrugDetailMapper costDrugDetailMapper = null;
	
	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;
    
	/**
	 * @Description 
	 * 科室药品费用表<BR> 添加CostDrugDetail
	 * @param CostDrugDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		
		
		entityMap.put("source_code", "001");

		Source source = sourceMapper.querySourceByCode(entityMap);
		
		entityMap.put("source_id", source.getSource_id());
		
		CostDrugDetail costDrugDetail = queryCostDrugDetailByCode(entityMap);

		if (costDrugDetail != null) {

			return "{\"error\":\"数据已存在.\"}";

		}
		
		try {
			
			costDrugDetailMapper.addCostDrugDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostDrugDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量添加CostDrugDetail
	 * @param  CostDrugDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostDrugDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costDrugDetailMapper.addBatchCostDrugDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostDrugDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostDrugDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostDrugDetail> list = costDrugDetailMapper.queryCostDrugDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostDrugDetail> list = costDrugDetailMapper.queryCostDrugDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostDrugDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costDrugDetailMapper.queryCostDrugDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 科室药品费用表<BR> 查询CostDrugDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostDrugDetail queryCostDrugDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costDrugDetailMapper.queryCostDrugDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量删除CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostDrugDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costDrugDetailMapper.deleteBatchCostDrugDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostDrugDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 删除CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostDrugDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costDrugDetailMapper.deleteCostDrugDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostDrugDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 更新CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostDrugDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		
		entityMap.put("source_code", "001");

		Source source = sourceMapper.querySourceByCode(entityMap);
    
        entityMap.put("source_id", source.getSource_id());
       
		try {

			costDrugDetailMapper.updateCostDrugDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDrugDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 科室药品费用表<BR> 批量更新CostDrugDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostDrugDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costDrugDetailMapper.updateBatchCostDrugDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostDrugDetail\"}";

		}
		
	}
}
