/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatStoreSet;
/**
 * 
 * @Description:
 * 04108 虚仓仓库设置
 * @Table:
 * MAT_STORE_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatStoreSetMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatStoreSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatStoreSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatStoreSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return MatStoreSet
	 * @throws DataAccessException
	*/
	public int updateBatchMatStoreSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatStoreSet(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04108 虚仓仓库设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatStoreSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04108 虚仓仓库设置<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreSet> queryMatStoreSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04108 虚仓仓库设置<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreSet> queryMatStoreSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04108 虚仓仓库设置<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatStoreSet
	 * @throws DataAccessException
	*/
	public MatStoreSet queryMatStoreSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04108 虚仓仓库设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStoreSet
	 * @throws DataAccessException
	*/
	public MatStoreSet queryMatStoreSetByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据输入的 名称查询 04108 虚仓仓库设置（判断名称是否已存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatStoreSet> queryMatStoreSetByName(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据用户的数据权限，查询出有数据权限的库房列表（不分页）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatStoreSet> queryMatStoreData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据用户的数据权限，查询出有数据权限的库房列表（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<MatStoreSet> queryMatStoreData(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 查询虚仓库房明细
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStoreDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 是否含有已在别的库房设置结账的库房
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String existsIsAccount(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 保存虚仓对应仓库是否结账
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchForIsAccount(@Param("group_id") String group_id, @Param("hos_id") String hos_id, @Param("set_id") String set_id, @Param("list") List<Map<String, Object>> list)throws DataAccessException;
	
}
