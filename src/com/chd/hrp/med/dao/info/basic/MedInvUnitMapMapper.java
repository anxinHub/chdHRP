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
import com.chd.hrp.med.entity.MedInvUnitMap;
/**
 * 
 * @Description:
 * 08116 材料包装单位关系表
 * @Table:
 * MED_INV_UNIT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedInvUnitMapMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedInvUnitMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedInvUnitMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInvUnitMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return MedInvUnitMap
	 * @throws DataAccessException
	*/
	public int updateBatchMedInvUnitMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInvUnitMap(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除08116 材料包装单位关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedInvUnitMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08116 材料包装单位关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedInvUnitMap> queryMedInvUnitMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集08116 材料包装单位关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedInvUnitMap> queryMedInvUnitMap(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取08116 材料包装单位关系表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedInvUnitMap
	 * @throws DataAccessException
	*/
	public MedInvUnitMap queryMedInvUnitMapByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取08116 材料包装单位关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvUnitMap
	 * @throws DataAccessException
	*/
	public MedInvUnitMap queryMedInvUnitMapByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 多表联合查询 材料包装单位关系记录(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvUnitMapNew(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 多表联合查询 材料包装单位关系记录(分页)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvUnitMapNew(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 弹出选择材料页面，根据查询条件查询出药品材料结果集（MED_INV中IS_STOP=0）（分页）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 弹出选择材料页面，根据查询条件查询出药品材料结果集（MED_INV中IS_STOP=0）（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 获取08116 材料包装单位关系表<BR>(多表联合查询)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedInvUnitMapByID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 页面 包装单位修改时   更新材料包装单位关系
	 * @param updateList
	 * @throws DataAccessException
	 */
	public void updateBatchMedInvUnitMapNew(List<Map<String, Object>> updateList) throws DataAccessException;
	
	
	
}
