/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostOhterDetail;

/**
* @Title. @Description.
* 科室其他费用表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostOhterDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 添加CostOhterDetail
	 * @param CostOhterDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostOhterDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 批量添加CostOhterDetail
	 * @param  CostOhterDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostOhterDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 查询CostOhterDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostOhterDetail>
	 * @throws DataAccessException
	*/
	public List<CostOhterDetail> queryCostOhterDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室其他费用表<BR> 查询CostOhterDetail所有数据
	 * @param  entityMap
	 * @return List<CostOhterDetail>
	 * @throws DataAccessException
	*/
	public List<CostOhterDetail> queryCostOhterDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 查询CostOhterDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostOhterDetail queryCostOhterDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 删除CostOhterDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostOhterDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 批量删除CostOhterDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostOhterDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科室其他费用表<BR> 更新CostOhterDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostOhterDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 批量更新CostOhterDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostOhterDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostOhterDetailPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
