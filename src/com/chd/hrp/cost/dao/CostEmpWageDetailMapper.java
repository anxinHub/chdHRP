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
import com.chd.hrp.cost.entity.CostEmpWageDetail;

/**
* @Title. @Description.
* 人员工资明细数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpWageDetailMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 添加CostEmpWageDetail
	 * @param CostEmpWageDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量添加CostEmpWageDetail
	 * @param  CostEmpWageDetail entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostEmpWageDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetail分页
	 * @param  entityMap RowBounds
	 * @return List<CostEmpWageDetail>
	 * @throws DataAccessException
	*/
	public List<CostEmpWageDetail> queryCostEmpWageDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetail所有数据
	 * @param  entityMap
	 * @return List<CostEmpWageDetail>
	 * @throws DataAccessException
	*/
	public List<CostEmpWageDetail> queryCostEmpWageDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetailByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostEmpWageDetail queryCostEmpWageDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 删除CostEmpWageDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量删除CostEmpWageDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostEmpWageDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 更新CostEmpWageDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量更新CostEmpWageDetail
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostEmpWageDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostEmpWageDetailPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
