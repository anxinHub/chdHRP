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
import com.chd.hrp.acc.entity.AccSubjType;

/**
* @Title. @Description.
* 科目类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSubjTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科目类别<BR> 添加AccSubjType
	 * @param AccSubjType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量添加AccSubjType
	 * @param  AccSubjType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjType分页
	 * @param  entityMap RowBounds
	 * @return List<AccSubjType>
	 * @throws DataAccessException
	*/
	public List<AccSubjType> queryAccSubjType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjType所有数据
	 * @param  entityMap
	 * @return List<AccSubjType>
	 * @throws DataAccessException
	*/
	public List<AccSubjType> queryAccSubjType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccSubjType queryAccSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 删除AccSubjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量删除AccSubjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 科目类别<BR> 更新AccSubjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAccSubjByType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量更新AccSubjType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 查询科目类别名称是否可用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccSubjTypeNameExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryAccSubjTypeReferenced(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 用于集团化管理   集团科目选择器 --科目类型
	 * @Description 
	 * 科目类别<BR> 查询GroupAccSubjType所有数据
	 * @param  entityMap
	 * @return List<AccSubjType>
	 * @throws DataAccessException
	*/
	public List<AccSubjType> queryGroupAccSubjType(Map<String,Object> entityMap) throws DataAccessException;
}
