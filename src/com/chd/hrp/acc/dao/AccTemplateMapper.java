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

import com.chd.hrp.acc.entity.AccTemplate;

/**
* @Title. @Description.
* 凭证模板主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTemplateMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 添加AccTemplate
	 * @param AccTemplate entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量添加AccTemplate
	 * @param  AccTemplate entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplate分页
	 * @param  entityMap RowBounds
	 * @return List<AccTemplate>
	 * @throws DataAccessException
	*/
	public List<AccTemplate> queryAccTemplate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplate所有数据
	 * @param  entityMap
	 * @return List<AccTemplate>
	 * @throws DataAccessException
	*/
	public List<AccTemplate> queryAccTemplate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplateByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccTemplate queryAccTemplateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 删除AccTemplate
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量删除AccTemplate
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 凭证模板主表<BR> 更新AccTemplate
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccTemplate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量更新AccTemplate
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccTemplate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccAddTemplateAll(Map<String,Object> entityMap)throws DataAccessException;
	
}
