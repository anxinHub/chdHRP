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
import com.chd.hrp.cost.entity.CostAllocationProcess;
import com.chd.hrp.cost.entity.CostAllocationProcess;

/**
 * @Title. @Description. 科室成本明细数据表_医辅分摊<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostAllocationProcessMapper extends SqlMapper {

	/**
	 * @Description 科室成本明细数据表_医辅分摊<BR>
	 *              批量添加CostAllocationProcess
	 * @param CostAllocationProcess
	 *            entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addBatchCostAllocationProcess(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 科室成本明细数据表_医辅分摊<BR>
	 *              删除CostAllocationProcess
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteCostAllocationProcess(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCostAllocationProcess(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCostAllocationProcess(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

}
