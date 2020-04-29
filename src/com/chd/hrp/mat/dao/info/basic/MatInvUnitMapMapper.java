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
import com.chd.hrp.mat.entity.MatInvUnitMap;
/**
 * 
 * @Description:
 * 04116 材料包装单位关系表
 * @Table:
 * MAT_INV_UNIT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatInvUnitMapMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatInvUnitMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatInvUnitMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatInvUnitMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return MatInvUnitMap
	 * @throws DataAccessException
	*/
	public int updateBatchMatInvUnitMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatInvUnitMap(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatInvUnitMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04116 材料包装单位关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvUnitMap> queryMatInvUnitMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集04116 材料包装单位关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatInvUnitMap> queryMatInvUnitMap(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取04116 材料包装单位关系表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatInvUnitMap
	 * @throws DataAccessException
	*/
	public MatInvUnitMap queryMatInvUnitMapByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04116 材料包装单位关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvUnitMap
	 * @throws DataAccessException
	*/
	public MatInvUnitMap queryMatInvUnitMapByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 多表联合查询 材料包装单位关系记录(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvUnitMapNew(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 多表联合查询 材料包装单位关系记录(分页)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvUnitMapNew(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 弹出选择材料页面，根据查询条件查询出物资材料结果集（MAT_INV中IS_STOP=0）（分页）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 弹出选择材料页面，根据查询条件查询出物资材料结果集（MAT_INV中IS_STOP=0）（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 获取04116 材料包装单位关系表<BR>(多表联合查询)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatInvUnitMapByID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 页面 包装单位修改时   更新材料包装单位关系
	 * @param updateList
	 * @throws DataAccessException
	 */
	public void updateBatchMatInvUnitMapNew(List<Map<String, Object>> updateList) throws DataAccessException;
	
	
	
}
