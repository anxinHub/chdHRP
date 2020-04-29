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
import com.chd.hrp.sys.entity.DeptKind;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DeptKindMapper extends SqlMapper{
	
	/**
	 * @Description 添加DeptKind
	 * @param DeptKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addDeptKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加DeptKind
	 * @param  DeptKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchDeptKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询DeptKind分页
	 * @param  entityMap RowBounds
	 * @return List<DeptKind>
	 * @throws DataAccessException
	*/
	public List<DeptKind> queryDeptKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询DeptKind所有数据
	 * @param  entityMap
	 * @return List<DeptKind>
	 * @throws DataAccessException
	*/
	public List<DeptKind> queryDeptKind(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptKindByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public DeptKind queryDeptKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除DeptKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteDeptKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除DeptKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchDeptKind(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新DeptKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateDeptKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新DeptKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchDeptKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 核算单元-科室分类-同步平台分类
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<DeptKind> quneryPlatformKind(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 核算单元-科室分类-同步平台分类(带分页)
	 * @param entityMap
	 * @param rowBounds 
	 * @return
	 */
	public List<DeptKind> quneryPlatformKind(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<DeptKind> queryDeptKindByName(Map<String, Object> entityMap)throws DataAccessException;

	public List<DeptKind> queryDeptKindByCodeName(Map<String, Object> entityMap)throws DataAccessException;
}
