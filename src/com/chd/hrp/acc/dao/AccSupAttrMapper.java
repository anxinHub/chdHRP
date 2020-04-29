/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.acc.entity.AccSupAttr;

/**
* @Title. @Description.
* 供应商字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSupAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 添加AccSupAttr
	 * @param AccSupAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量添加AccSupAttr
	 * @param  AccSupAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttr分页
	 * @param  entityMap RowBounds
	 * @return List<AccSupAttr>
	 * @throws DataAccessException
	*/
	public List<AccSupAttr> queryAccSupAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttr所有数据
	 * @param  entityMap
	 * @return List<AccSupAttr>
	 * @throws DataAccessException
	*/
	public List<AccSupAttr> queryAccSupAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSupAttr queryAccSupAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public AccSupAttr queryAccSupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccSupAttr queryHosSupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 删除AccSupAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量删除AccSupAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 更新AccSupAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量更新AccSupAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
}
