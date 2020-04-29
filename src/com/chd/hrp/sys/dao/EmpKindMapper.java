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

import com.chd.hrp.sys.entity.EmpKind;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface EmpKindMapper extends SqlMapper{
	
	/**
	 * @Description 添加EmpKind
	 * @param EmpKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加EmpKind
	 * @param  EmpKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询EmpKind分页
	 * @param  entityMap RowBounds
	 * @return List<EmpKind>
	 * @throws DataAccessException
	*/
	public List<EmpKind> queryEmpKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询EmpKind所有数据
	 * @param  entityMap
	 * @return List<EmpKind>
	 * @throws DataAccessException
	*/
	public List<EmpKind> queryEmpKind(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询EmpKindByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public EmpKind queryEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除EmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除EmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新EmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新EmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
}
