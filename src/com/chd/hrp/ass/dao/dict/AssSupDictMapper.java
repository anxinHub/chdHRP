/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.ass.dao.dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.dict.AssSupDict;
import com.chd.hrp.sys.entity.SupDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AssSupDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加SupDict
	 * @param SupDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加SupDict
	 * @param  SupDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchSupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询SupDict分页
	 * @param  entityMap RowBounds
	 * @return List<SupDict>
	 * @throws DataAccessException
	*/
	public List<SupDict> querySupDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询SupDict所有数据
	 * @param  entityMap
	 * @return List<SupDict>
	 * @throws DataAccessException
	*/
	public List<SupDict> querySupDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SupDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public SupDict querySupDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除SupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除SupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchSupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新SupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateSupDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新SupDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchSupDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新SupDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateSupDictState(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 修改供应商(供应商编码修改) 不保留历史记录时  修改供应商变更记录
	 * @param entityMap
	 */
	public int updateSupDict_Sup(Map<String, Object> entityMap);

	/**
	 * 名称或编码查询
	 * @param supMap
	 * @return
	 * @throws DataAccessException
	 */
	public AssSupDict queryAssSupDictByCodeOrName(Map<String, Object> supMap)throws DataAccessException;
}
