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
import com.chd.hrp.acc.entity.AccWageItems;

/**
* @Title. @Description.
* 工资项目表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageItemsMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资项目表<BR> 添加AccWageItems
	 * @param AccWageItems entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量添加AccWageItems
	 * @param  AccWageItems entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageItems(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItems分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageItems>
	 * @throws DataAccessException
	*/
	public List<AccWageItems> queryAccWageItems(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItems所有数据
	 * @param  entityMap
	 * @return List<AccWageItems>
	 * @throws DataAccessException
	*/
	public List<AccWageItems> queryAccWageItems(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageItemsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItemsByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageItems queryAccWageItemsByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询queryAccWageColumnItems
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageItems queryAccWageColumnItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 删除AccWageItems
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量删除AccWageItems
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageItems(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资项目表<BR> 更新AccWageItems
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量更新AccWageItems
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageItems(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 添加工资项目时，动态增加业务表的工资项目字段 
	 * @param mapList
	 */
	public void queryAccWageItemsByAlter(Map<String, Object> entityMap);
	/**
	 * 继承工资项目（上年工资继承到本年） 
	 */
	public int extendAccWageItems(Map<String, Object> entityMap)throws DataAccessException;
	
	public AccWageItems queryAccWageColumnByCal(Map<String,Object> entityMap) throws DataAccessException;

	public AccWageItems queryAccWagePayScheme(Map<String,Object> entityMap) throws DataAccessException;

	public int updateSortcodeByItemcode(List<Map<String, Object>> listVo);

	public int updateByItemcode(Map<String, Object> mapData);

	public AccWageItems queryAccWageItemsByItemcode(Map<String, Object> mapData);

	public AccWageItems querySortcodeByWageCode(Map<String, Object> entityMap);


}
