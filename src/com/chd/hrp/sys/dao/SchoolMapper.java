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
import com.chd.hrp.sys.entity.School;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SchoolMapper extends SqlMapper{
	
	/**
	 * @Description 添加School
	 * @param School entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addSchool(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加School
	 * @param  School entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchSchool(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询School分页
	 * @param  entityMap RowBounds
	 * @return List<School>
	 * @throws DataAccessException
	*/
	public List<School> querySchool(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询School所有数据
	 * @param  entityMap
	 * @return List<School>
	 * @throws DataAccessException
	*/
	public List<School> querySchool(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SchoolByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public School querySchoolByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除School
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteSchool(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除School
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchSchool(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新School
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateSchool(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新School
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchSchool(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 添加、修改生产厂商时 查询 与输入的生产厂商编码 、生产厂商名称一样的记录（ 判断 输入的 生产厂商编码 、生产厂商名称 是否已经存在）
	 * @param entityMap
	 * @return
	 */
	
}
