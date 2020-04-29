/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostDeptArea;
/**
 *  
 * @Description:
 * 成本_科室成本核算总表
 * @Table:
 * COST_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface CostDeptCostMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室直接成本集合
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryDeptDirData(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 受益科室分摊所占比例数
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryDeptAmount(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 服务科室分摊总比例数
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryDeptSumAmount(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 服务科室分摊比例数
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryDeptNumAmount(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 服务科室分摊配置集合
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryDeptParaListData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostDeptCostBatch(List<?> entityMap)throws DataAccessException;

	
	
	/**
	 * @Description 
	 * 服务科室分摊配置集合
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryDeptParaData(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 受益科室分摊配置集合
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryServerDeptParaData(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 成本分摊存储过程
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public Map<String,Object> saveCostDeptCostProc(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 成本分摊校验存储过程
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public Map<String,Object> saveCostDeptCostCheckProc(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryCostDeptCostPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCheck(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
}
