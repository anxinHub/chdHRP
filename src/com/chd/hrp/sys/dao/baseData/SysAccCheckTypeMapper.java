/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao.baseData;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCheckItem;
import com.chd.hrp.acc.entity.AccCheckType;

/**
* @Title. @Description.
* 核算类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SysAccCheckTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 核算类<BR> 添加AccCheckType
	 * @param AccCheckType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 批量添加AccCheckType
	 * @param  AccCheckType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCheckType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckType分页
	 * @param  entityMap RowBounds
	 * @return List<AccCheckType>
	 * @throws DataAccessException
	*/
	public List<AccCheckType> queryAccCheckType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckType所有数据
	 * @param  entityMap
	 * @return List<AccCheckType>
	 * @throws DataAccessException
	*/
	public List<AccCheckType> queryAccCheckType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckType所有数据
	 * @param  entityMap
	 * @return List<AccCheckType>
	 * @throws DataAccessException
	*/
	public List<AccCheckType> queryAccCheckTypeBySelect(Map<String,Object> entityMap) throws DataAccessException;
	public List<AccCheckType> queryCheckTable(Map<String,Object> entityMap) throws DataAccessException;
	public List<AccCheckType> queryCheckTypeBySubjId(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCheckType queryAccCheckTypeByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCheckType queryAccCheckTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 核算类<BR> 删除AccCheckType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 批量删除AccCheckType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCheckType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 核算类<BR> 更新AccCheckType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 批量更新AccCheckType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCheckType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算类<BR> 继承数据   查询AccCheckType所有数据
	 * @param  entityMap
	 * @return List<AccCheckType>
	 * @throws DataAccessException
	*/
	public List<AccCheckType> queryAccCheckTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 当前集团、当前医院、当前帐套下 核算继承数据 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccCheckType> queryAccCheckTypeExtend(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询核算类最大  check_type_id
	 * @return
	 */
	public AccCheckType queryMaxId() throws DataAccessException;
	/**
	 * 继承核算类  所有数据添加
	 * @param mapList
	 */
	public void insertBatchAccCheckType(List<Map<String, Object>> mapList);

	
	
	/**
	 * 添加辅助核算类时，动态增加业务表核算类型字段 
	 * @param mapList
	 */
	public void queryAccCheckTypeByAlter(Map<String, Object> entityMap);

	public AccCheckType queryCheckType(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCheckType queryCheckColumn(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询 核算类编码 是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int existCode(Map<String, Object> entityMap) throws DataAccessException;

}
