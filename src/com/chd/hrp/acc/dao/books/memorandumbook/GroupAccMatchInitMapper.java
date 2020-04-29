/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccMatchInit;

/**
* @Title. @Description.
* 配套资金初始账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface GroupAccMatchInitMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 添加AccMatchInit
	 * @param AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGroupAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量添加AccMatchInit
	 * @param  AccLederZcheck entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit分页
	 * @param  entityMap RowBounds
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public List<AccMatchInit> queryGroupAccMatchInit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit所有数据
	 * @param  entityMap
	 * @return List<AccLederZcheck>
	 * @throws DataAccessException
	*/
	public List<AccMatchInit> queryGroupAccMatchInit(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccMatchInitPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 查询AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccMatchInit queryGroupAccMatchInitByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 删除AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteGroupAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量删除AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 更新AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateGroupAccMatchInit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套资金初始账表<BR> 批量更新AccMatchInit
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)throws DataAccessException;
}
