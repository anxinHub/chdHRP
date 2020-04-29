/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatStoreType;
/**
 * 
 * @Description:
 * 04111 仓库类别信息
 * @Table:
 * MAT_STORE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatStoreTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return MatStoreType
	 * @throws DataAccessException
	*/
	public int updateBatchMatStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04111 仓库类别信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreType> queryMatStoreType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04111 仓库类别信息<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreType> queryMatStoreType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04111 仓库类别信息<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatStoreType
	 * @throws DataAccessException
	*/
	public MatStoreType queryMatStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04111 仓库类别信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStoreType
	 * @throws DataAccessException
	*/
	public MatStoreType queryMatStoreTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询出物资类别字典表 MAT_TYPE中IS_STOP=0的所有物资类别记录
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryStoreType(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询出物资类别字典表 MAT_TYPE中IS_STOP=0的所有物资类别记录
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryStoreType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 根据仓库ID 查询是否存在 对应对应关系数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryExist(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
	public List<Map<String,Object>> queryMatTypeByStore(Map<String, Object> entityMap) throws DataAccessException;
	
	public int deleteBatchMatTypeByStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addBatchMatTypeByStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
