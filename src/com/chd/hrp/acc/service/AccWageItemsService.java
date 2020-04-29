/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageItems;

/**
* @Title. @Description.
* 工资项目表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageItemsService {

	/**
	 * @Description 
	 * 工资项目表<BR> 添加AccWageItems
	 * @param AccWageItems entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量添加AccWageItems
	 * @param  AccWageItems entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageItems(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItems分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageItems(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageItemsPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 查询AccWageItemsByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageItems queryAccWageItemsByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 删除AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量删除AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageItems(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 更新AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 批量更新AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageItems(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目表<BR> 导入AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 继承工资项目 AccWageItems
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccWageItems(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据code查询出当前工资套中的最大排序号
	 * @return
	 */
	public AccWageItems querySortcodeByWageCode(Map<String, Object> entityMap) throws DataAccessException;

}
