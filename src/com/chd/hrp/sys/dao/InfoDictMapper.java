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
import com.chd.hrp.sys.entity.InfoDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface InfoDictMapper extends SqlMapper{
	
	/**
	 * @Description 添加InfoDict
	 * @param InfoDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addInfoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加InfoDict
	 * @param  InfoDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchInfoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询InfoDict分页
	 * @param  entityMap RowBounds
	 * @return List<InfoDict>
	 * @throws DataAccessException
	*/
	public List<InfoDict> queryInfoDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询InfoDict所有数据
	 * @param  entityMap
	 * @return List<InfoDict>
	 * @throws DataAccessException
	*/
	public List<InfoDict> queryInfoDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询InfoDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public InfoDict queryInfoDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除InfoDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteInfoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除InfoDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchInfoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新InfoDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateInfoDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新InfoDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateInfoDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新InfoDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchInfoDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 通过医院id 获取 医院信息和对应的集团信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryHosInfoToGroupInfo(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 查询InfoDict所有数据(辅助核算查询)
	 * @param  entityMap
	 * @return List<InfoDict>
	 * @throws DataAccessException
	*/
	public List<InfoDict> queryHosInfoList(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHosInfoListPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<InfoDict> queryHosInfoList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
