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
import com.chd.hrp.sys.entity.StoreDict;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface StoreDictMapper extends SqlMapper{
	
	
	/**
	 * @Description 添加StoreDict
	 * @param StoreDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加StoreDict
	 * @param  StoreDict entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchStoreDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询StoreDict分页
	 * @param  entityMap RowBounds
	 * @return List<StoreDict>
	 * @throws DataAccessException
	*/
	public List<StoreDict> queryStoreDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询StoreDict所有数据
	 * @param  entityMap
	 * @return List<StoreDict>
	 * @throws DataAccessException
	*/
	public List<StoreDict> queryStoreDict(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryStoreDictPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询StoreDictByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public StoreDict queryStoreDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除StoreDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除StoreDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchStoreDict(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新StoreDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateStoreDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新StoreDict状态
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateStoreDictState(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateisdisableState(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新StoreDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchStoreDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 修改库房记录 不保留历史记录时 修改相应的库房变更记录
	 * @param entityMap
	 * @return
	 */
	public int updateStoreDict_Store(Map<String, Object> entityMap);

	/**
	 * 编码或名称查询 导入期初卡片使用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public StoreDict queryStoreDictByCodeOrName(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 用于集团化管理  集团库房选择器--集团库房字典查询
	 * @Description 查询GroupStoreDict所有数据
	 * @param  entityMap
	 * @return List<StoreDict>
	 * @throws DataAccessException
	*/
	public List<StoreDict> queryGroupStoreDict(Map<String,Object> entityMap) throws DataAccessException;

	public List<StoreDict> queryStoreDictList(Map<String, Object> paramMap);

	public List<StoreDict> queryStoreDictList(Map<String, Object> paramMap, RowBounds rowBounds);

}
