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
import com.chd.hrp.sys.entity.Store;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StoreMapper extends SqlMapper{
	
	/**
	 * @Description 查询刚查询序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 添加Store
	 * @param Store entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addStore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Store
	 * @param  Store entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Store分页
	 * @param  entityMap RowBounds
	 * @return List<Store>
	 * @throws DataAccessException
	*/
	public List<Store> queryStore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Store所有数据
	 * @param  entityMap
	 * @return List<Store>
	 * @throws DataAccessException
	*/
	public List<Store> queryStore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StoreByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Store queryStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Store
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteStore(Map<String,Object> entityMap)throws DataAccessException;
	

	
	/**
	 * @Description 批量删除Store
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchStore(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Store
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateStore(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateStoreByName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Store
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 添加、修改 库房 时 查询 与输入库房编码 、库房名称 相同的  库房记录 （判断输入的 库房编码 、库房名称 是否存在）
	 * @param entityMap
	 * @return
	 */
	public List<Store> queryStoreById(Map<String, Object> entityMap);
	/**
	 * 查询当前集团当前医院下 库房的最大 排序号
	 * @param entityMap
	 * @return
	 */
	public Store queryMaxStore_sort(Map<String, Object> entityMap);
	/**
	 * 根据输入的  排序号  查询当前集团、医院下是否有库房在使用（判断排序号是否已存在）
	 * @return
	 */
	public Store quryStore_sort(Map<String, Object> entityMap);
}
