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
import com.chd.hrp.acc.entity.AccFunType;

/**
* @Title. @Description.
* 支出功能分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccFunTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 添加AccFunType
	 * @param AccFunType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量添加AccFunType
	 * @param  AccFunType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunType分页
	 * @param  entityMap RowBounds
	 * @return List<AccFunType>
	 * @throws DataAccessException
	*/
	public List<AccFunType> queryAccFunType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunType所有数据
	 * @param  entityMap
	 * @return List<AccFunType>
	 * @throws DataAccessException
	*/
	public List<AccFunType> queryAccFunType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccFunTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccFunType queryAccFunTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 删除AccFunType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量删除AccFunType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 支出功能分类<BR> 更新AccFunType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量更新AccFunType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccFunType> queryAccFunTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 输入的 支出功能分类 查询其上级支出功能分类（上级部门编码不存在则不允许添加、修改）
	 * @param entityMap 
	 * @return
	 */
	public List<Map<String,Object>> qureySurp_code(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AccFunType> queryAccFunTypeById(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询  支出功能分类是否正在被引用
	 */
	public int queryIsUsed(Map<String, Object> item) throws DataAccessException;
	
	
	
}
