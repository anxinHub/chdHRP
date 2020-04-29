/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCur;

/**
* @Title. @Description.
* 币种<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCurMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 币种<BR> 添加AccCur
	 * @param AccCur entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 批量添加AccCur
	 * @param  AccCur entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCur(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 查询AccCur分页
	 * @param  entityMap RowBounds
	 * @return List<AccCur>
	 * @throws DataAccessException
	*/
	public List<AccCur> queryAccCur(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 币种<BR> 查询AccCur所有数据
	 * @param  entityMap
	 * @return List<AccCur>
	 * @throws DataAccessException
	*/
	public List<AccCur> queryAccCur(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 查询AccCurByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCur queryAccCurByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 删除AccCur
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 批量删除AccCur
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCur(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 币种<BR> 更新AccCur
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 批量更新AccCur
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCur(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public List<AccCur> queryAccCurByExtend(Map<String,Object> entityMap) throws DataAccessException;

	public int queryAccCurExitsByName(Map<String, Object> entityMap);
	public int queryAccCurExitsByCode(Map<String, Object> entityMap);
}
