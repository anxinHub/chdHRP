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
import com.chd.hrp.ass.entity.dict.AssSup;
import com.chd.hrp.sys.entity.Sup;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AssSupMapper extends SqlMapper{
	
	/**
	 * @Description 添加Sup
	 * @param Sup entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Sup
	 * @param  Sup entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Sup分页
	 * @param  entityMap RowBounds
	 * @return List<Sup>
	 * @throws DataAccessException
	*/
	public List<AssSup> querySup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Sup所有数据
	 * @param  entityMap
	 * @return List<Sup>
	 * @throws DataAccessException
	*/
	public List<AssSup> querySup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SupByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AssSup querySupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Sup
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Sup
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Sup
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateSup(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateSysUserSup(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateSupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateSupByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Sup
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询SUP序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long querySupCurrentSequence()throws DataAccessException;
	
	
	public AssSup  querySupMaxTypeCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public AssSup  querySupMaxTypePy(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 添加、修改供应商 时 查询  与输入供应商编码或供应商名称相同的 供应商记录（ 判断输入供应商编码、供应商名称是否已存在）
	 * @param entityMap
	 * @return
	 */
	public List<AssSup> querySupById(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询当前集团、医院下  供应商的最大排序号
	 * @return
	 * @throws DataAccessException
	 */
	public Sup queryMaxSup_sort(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 查询当前集团、医院下  与输入的排序号相同的供应商记录 （判断排序号是否已经存在）
	 * @param entityMap
	 * @return
	 */
	public Sup querySup_sort(Map<String, Object> entityMap);
	
	public int querySupDict(Map<String,Object> entityMap)throws DataAccessException;
}
