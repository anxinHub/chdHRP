/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.EmpDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface EmpDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加EmpDict
	 * @param EmpDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加EmpDict
	 * @param  EmpDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询EmpDict分页
	 * @param  entityMap RowBounds
	 * @return List<EmpDict>
	 * @throws DataAccessException
	*/
	public List<EmpDict> queryEmpDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询EmpDict所有数据
	 * @param  entityMap
	 * @return List<EmpDict>
	 * @throws DataAccessException
	*/
	public List<EmpDict> queryEmpDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryEmpDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询EmpDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public EmpDict queryEmpDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除EmpDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除EmpDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新EmpDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateEmpDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新EmpDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateEmpDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询职工序列号
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryEmpDictSeqNextVal()throws DataAccessException;
	
	/**
	 * @Description 批量更新EmpDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchEmpDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询EmpDict所有数据 用于导入时,匹配职工是否存在
	 * @param  entityMap
	 * @return List<EmpDict>
	 * @throws DataAccessException
	*/
	public List<EmpDict> queryEmpDictAll(Map<String,Object> entityMap) throws DataAccessException;
	
	public int queryEmpDictNextval(Map<String, Object> entityMap) throws DataAccessException;

	public List<EmpDict> queryEmpDictList(Map<String, Object> paramMap) throws DataAccessException;
	public List<EmpDict> queryEmpDictList(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
}
