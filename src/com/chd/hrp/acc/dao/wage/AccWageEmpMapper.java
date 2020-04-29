/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageEmp;

/**
* @Title. @Description. 
* 工资套职工关系<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageEmpMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 添加AccWageEmp
	 * @param AccWageEmp entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量添加AccWageEmp
	 * @param  AccWageEmp entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmp分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageEmp>
	 * @throws DataAccessException
	*/
	public List<AccWageEmp> queryAccWageEmp(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<AccWageEmp> queryAccWageEmpList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AccWageEmp> queryAccWageEmpList(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmp所有数据
	 * @param  entityMap
	 * @return List<AccWageEmp>
	 * @throws DataAccessException
	*/
	public List<AccWageEmp> queryAccWageEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * @param  entityMap RowBounds
	 * @return List<AccWageEmp>
	 * @throws DataAccessException
	 * @deprecated
	*/
	public List<AccWageEmp> queryAccWageAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * @param  entityMap
	 * @return List<AccWageEmp>
	 * @throws DataAccessException
	 * @deprecated
	*/
	public List<AccWageEmp> queryAccWageAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 查询AccWageEmpByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	 * @deprecated
	*/
	public AccWageEmp queryAccWageEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 删除AccWageEmp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量删除AccWageEmp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资套职工关系<BR> 更新AccWageEmp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageEmp(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套职工关系<BR> 批量更新AccWageEmp
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageEmp(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 工资套继承
	 */
	public void accExtendOldWageEmpToNew(Map<String, Object> mapVo);
	
	//用户判断系统平台中职工是否存在，存在则不能进行删除  
	public int queryAccWageEmpCountByEmpId(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageEmpListPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public AccWageEmp queryAccWageEmpCodeByImp(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * 查询工资套与职工没有关联的记录
	 */
	public List<Map<String, Object>> queryAccWageEmpNotBind(Map<String, Object> paraMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccWageEmpNotBind(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 添加工资套与职工关联（批量）
	 */
	public void addAccWageEmpBatch(List<AccWageEmp> list)throws DataAccessException;
}
