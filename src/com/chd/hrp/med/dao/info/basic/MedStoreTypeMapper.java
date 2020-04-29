/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.info.basic;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedStoreType;
/**
 * 
 * @Description:
 * 08111 仓库类别信息
 * @Table:
 * MED_STORE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStoreTypeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return MedStoreType
	 * @throws DataAccessException
	*/
	public int updateBatchMedStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedStoreType(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除08111 仓库类别信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedStoreType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08111 仓库类别信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreType> queryMedStoreType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08111 仓库类别信息<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreType> queryMedStoreType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取08111 仓库类别信息<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedStoreType
	 * @throws DataAccessException
	*/
	public MedStoreType queryMedStoreTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取08111 仓库类别信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreType
	 * @throws DataAccessException
	*/
	public MedStoreType queryMedStoreTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询出药品类别字典表 MED_TYPE中IS_STOP=0的所有药品类别记录
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryStoreType(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询出药品类别字典表 MED_TYPE中IS_STOP=0的所有药品类别记录
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
	
	
	
	public List<Map<String,Object>> queryMedTypeByStore(Map<String, Object> entityMap) throws DataAccessException;
	
	public int deleteBatchMedTypeByStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addBatchMedTypeByStore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
