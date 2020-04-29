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

import com.chd.hrp.acc.entity.AccSubjNature;

/**
* @Title. @Description.
* 科目性质<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSubjNatureMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科目性质<BR> 添加AccSubjNature
	 * @param AccSubjNature entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量添加AccSubjNature
	 * @param  AccSubjNature entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccSubjNature(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNature分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjNature>
	 * @throws DataAccessException
	*/
	public List<AccSubjNature> queryAccSubjNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNature所有数据
	 * @param  entityMap
	 * @return List<AccSubjNature>
	 * @throws DataAccessException
	*/
	public List<AccSubjNature> queryAccSubjNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNatureByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubjNature queryAccSubjNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 删除AccSubjNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量删除AccSubjNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccSubjNature(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科目性质<BR> 更新AccSubjNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccSubjNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目性质<BR> 批量更新AccSubjNature
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccSubjNature(List<Map<String, Object>> entityMap)throws DataAccessException;
}
