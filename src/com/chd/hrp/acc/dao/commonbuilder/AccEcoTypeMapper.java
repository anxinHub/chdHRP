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
import com.chd.hrp.acc.entity.AccEcoType;
import com.chd.hrp.acc.entity.AccFunType;

/**
* @Title. @Description.
* 支出经济分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEcoTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 添加AccEcoType
	 * @param AccEcoType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量添加AccEcoType
	 * @param  AccEcoType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccEcoType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoType分页
	 * @param  entityMap RowBounds
	 * @return List<AccEcoType>
	 * @throws DataAccessException
	*/
	public List<AccEcoType> queryAccEcoType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoType所有数据
	 * @param  entityMap
	 * @return List<AccEcoType>
	 * @throws DataAccessException
	*/
	public List<AccEcoType> queryAccEcoType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccEcoTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccEcoType queryAccEcoTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 删除AccEcoType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量删除AccEcoType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccEcoType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 支出经济分类<BR> 更新AccEcoType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量更新AccEcoType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccEcoType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccEcoType> queryAccEcoTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 输入的 支出经济分类 查询其上级支出功能分类（上级部门编码不存在则不允许添加、修改）
	 * @param entityMap 
	 * @return
	 */
	public List<Map<String,Object>> qureySurp_code(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<AccEcoType> queryAccEcoTypeById(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询 支出经济分类是否正被引用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsUsed(Map<String, Object> entityMap) throws DataAccessException;
}
