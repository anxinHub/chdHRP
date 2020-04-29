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
import com.chd.hrp.sys.entity.CusDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CusDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加CusDict
	 * @param CusDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加CusDict
	 * @param  CusDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCusDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询CusDict分页
	 * @param  entityMap RowBounds
	 * @return List<CusDict>
	 * @throws DataAccessException
	*/
	public List<CusDict> queryCusDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询CusDict所有数据
	 * @param  entityMap
	 * @return List<CusDict>
	 * @throws DataAccessException
	*/
	public List<CusDict> queryCusDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryCusDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CusDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CusDict queryCusDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除CusDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除CusDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCusDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新CusDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCusDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新CusDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCusDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新CusDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCusDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 修改客户不保留历史记录时 修改相应的客户变更记录
	 * @param entityMap
	 */
	public int updateCusDict_Cus(Map<String, Object> entityMap);
	
	/**
	 * 用于集团化管理   集团客户选择器---集团客户字典
	 * @Description 查询GroupCusDict所有数据
	 * @param  entityMap
	 * @return List<CusDict>
	 * @throws DataAccessException
	*/
	public List<CusDict> queryGroupCusDict(Map<String,Object> entityMap) throws DataAccessException;

	public List<CusDict> queryCusDictList(Map<String, Object> paramMap) throws DataAccessException;

	public List<CusDict> queryCusDictList(Map<String, Object> paramMap, RowBounds rowBounds) throws DataAccessException;
}
