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
import com.chd.hrp.mat.entity.MatStoreInv;
/**
 * 
 * @Description:
 * 04110 仓库材料信息
 * @Table:
 * MAT_STORE_INV 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
 
public interface MatStoreInvMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	public int addMatLocationInv(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int deleteStoreMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addStoreMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public int addBatchMatLocationInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	public int updateMatLocationInv(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量更新04110 仓库材料信息<BR> 
	 * @param  entityList
	 * @return MatStoreInv
	 * @throws DataAccessException
	*/
	public int updateBatchMatStoreInv(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	public int deleteBatchMatLocationInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取04110 仓库材料信息<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MatStoreInv
	 * @throws DataAccessException
	*/
	public MatStoreInv queryMatStoreInvByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取04110 仓库材料信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatStoreInv
	 * @throws DataAccessException
	*/
	public MatStoreInv queryMatStoreInvByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据条件查询 （联合） 物资材料记录(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatStoreInvNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据条件查询 （联合） 物资材料记录（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatStoreInvNew(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询出与当前仓库存在对应关系的材料ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvStoreId(Map<String, Object> mapVo)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 仓库安全设置--保存 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MatStoreInv> querySafeMatStoreInv(Map<String,Object> entityMap) throws DataAccessException;
	public List<MatStoreInv> querySafeMatStoreInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 仓库材料定义--全部材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvAllList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatInvAllList(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 判断材料是否材料是否存在于该仓库中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatInvInStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 仓库材料定义--查询材料申领科室
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvApplyStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 仓库材料定义--修改默认申领仓库状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除04110 仓库材料信息 只根据材料id删除<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMatStoreInvRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	/**
	 * 同步申请仓库到HIS  通过参数控制是否同步 04075   新增关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvApplyStoreByHisAdd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 同步申请仓库到HIS  通过参数控制是否同步 04075   删除关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatInvApplyStoreByHisDel(Map<String, Object> entityMap) throws DataAccessException;
	
	//批量修改安全库存
	public int updateSafeMatStoreInvBatch(@Param(value="map")Map<String, Object> map, @Param(value="list")List<String> list) throws DataAccessException;
}
