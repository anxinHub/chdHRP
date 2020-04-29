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
import com.chd.hrp.acc.entity.AccCheckItem;
import com.chd.hrp.acc.entity.AccCheckItemType;
import com.chd.hrp.acc.entity.AccCheckType;

/**
* @Title. @Description.
* 核算分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCheckItemTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 核算分类<BR> 添加AccCheckItemType
	 * @param AccCheckItemType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量添加AccCheckItemType
	 * @param  AccCheckItemType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCheckItemType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemType分页
	 * @param  entityMap RowBounds
	 * @return List<AccCheckItemType>
	 * @throws DataAccessException
	*/
	public List<AccCheckItemType> queryAccCheckItemType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemType所有数据
	 * @param  entityMap
	 * @return List<AccCheckItemType>
	 * @throws DataAccessException
	*/
	public List<AccCheckItemType> queryAccCheckItemType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccCheckItemTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemType所有数据
	 * @param  entityMap
	 * @return List<AccCheckItemType>
	 * @throws DataAccessException
	*/
	public List<AccCheckItemType> queryAccCheckItemTypeBySelect(Map<String,Object> entityMap) throws DataAccessException;
	public List<AccCheckItemType> queryCheckTable(Map<String,Object> entityMap) throws DataAccessException;
	public List<AccCheckItemType> queryCheckTypeBySubjId(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int queryAccCheckItemTypeByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 查询AccCheckItemTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCheckItemType queryAccCheckItemTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 核算分类<BR> 删除AccCheckItemType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量删除AccCheckItemType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCheckItemType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 核算分类<BR> 更新AccCheckItemType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCheckItemType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 批量更新AccCheckItemType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCheckItemType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 核算分类<BR> 继承数据   查询AccCheckItemType所有数据
	 * @param  entityMap
	 * @return List<AccCheckItemType>
	 * @throws DataAccessException
	*/
	public List<AccCheckItemType> queryAccCheckItemTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 当前集团、当前医院、当前帐套下 核算继承数据 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AccCheckItemType> queryAccCheckItemTypeExtend(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询核算分类最大  check_type_id
	 * @return
	 */
	public AccCheckItemType queryMaxId() throws DataAccessException;
	/**
	 * 继承核算分类  所有数据添加
	 * @param mapList
	 */
	public void insertBatchAccCheckItemType(List<Map<String, Object>> mapList);


	public AccCheckItemType queryAccCheckItemTypeByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsUsed(Map<String, Object> item) throws DataAccessException;

}
