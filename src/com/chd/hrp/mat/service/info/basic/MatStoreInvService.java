/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 

public interface MatStoreInvService {

	/**
	 * @Description 
	 * 添加04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	 
	
	public String addMatStoreInvAll(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMatStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新04110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMatStoreInv(Map<String,Object> entityMap)throws DataAccessException;

	
	
	/**
	 * @Description 
	 * 查询对象04110 仓库材料信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
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
	 * 仓库安全设置--删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteSafeMatStoreInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 仓库安全设置--保存
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String addSafeMatStoreInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 仓库安全设置--查询
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String querySafeMatStoreInv(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库材料定义--保存材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addMatStoreInvCert(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库材料定义--全部材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvAllList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 判断材料是否已经存在于该仓库中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatInvInStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询材料申领仓库
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInvApplyStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 设置材料默认申领仓库
	 * @param List
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException; 
	
	/**
	 * 取消设置申领仓库
	 * @param List
	 * @return
	 * @throws DataAccessException
	 */
	public String updateCancelMatInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException; 
	
	
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
	
	/**
	 * 根据条件查询 （联合） 物资材料记录
	 * 
	 * @param entityMap
	 * @return
	 */
	public String queryMatStoreInvNew(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 弹出材料列表信息（查询出与当前仓库不存在对应关系的材料列表）
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 用于his同步数据
	 * 
	 * @param inv_ids
	 *            材料id集合
	 * @param store_id
	 *            申请仓库
	 * @param flag
	 *            1：增加 2 删除
	 * @return
	 */
	public LinkedHashMap<String, Object> wbPass(String inv_ids, String store_id, int flag);
	
	/**
	 * 批量修改安全库存
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> updateSafeMatStoreInvBatch(Map<String, Object> map) throws DataAccessException;
}
