/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.emp;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccHosEmpKind;

/**
* @Title. @Description.
* 职工分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccHosEmpKindMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工分类<BR> 添加AccHosEmpKind
	 * @param AccHosEmpKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量添加AccHosEmpKind
	 * @param  AccHosEmpKind entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccHosEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKind分页
	 * @param  entityMap RowBounds
	 * @return List<AccHosEmpKind>
	 * @throws DataAccessException
	*/
	public List<AccHosEmpKind> queryAccHosEmpKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKind所有数据
	 * @param  entityMap
	 * @return List<AccHosEmpKind>
	 * @throws DataAccessException
	*/
	public List<AccHosEmpKind> queryAccHosEmpKind(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccHosEmpKindPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKindByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccHosEmpKind queryAccHosEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 删除AccHosEmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量删除AccHosEmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccHosEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 职工分类<BR> 更新AccHosEmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量更新AccHosEmpKind
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccHosEmpKind(List<Map<String, Object>> entityMap)throws DataAccessException;
}
