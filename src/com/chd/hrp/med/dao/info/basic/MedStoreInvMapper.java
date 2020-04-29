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
import com.chd.hrp.med.entity.MedStoreInv;
/**
 * 
 * @Description:
 * 08110 仓库材料信息
 * @Table:
 * MED_STORE_INV 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedStoreInvMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	public int addMedLocationInv(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteStoreMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addStoreMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addBatchMedLocationInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	public int updateMedLocationInv(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量更新08110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return MedStoreInv
	 * @throws DataAccessException
	*/
	public int updateBatchMedStoreInv(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	public int deleteBatchMedLocationInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取08110 仓库材料信息<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedStoreInv
	 * @throws DataAccessException
	*/
	public MedStoreInv queryMedStoreInvByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取08110 仓库材料信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStoreInv
	 * @throws DataAccessException
	*/
	public MedStoreInv queryMedStoreInvByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据条件查询 （联合） 药品材料记录(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedStoreInvNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据条件查询 （联合） 药品材料记录（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedStoreInvNew(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询出与当前仓库存在对应关系的材料ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvStoreId(Map<String, Object> mapVo)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 仓库安全设置--保存 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedStoreInv> querySafeMedStoreInv(Map<String,Object> entityMap) throws DataAccessException;
	public List<MedStoreInv> querySafeMedStoreInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 仓库材料定义--全部材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvAllList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedInvAllList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 判断材料是否材料是否存在于该仓库中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMedInvInStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 仓库材料定义--查询材料申领科室
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInvApplyStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 仓库材料定义--修改默认申领仓库状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除08110 仓库材料信息 只根据材料id删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedStoreInvRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	 
}
