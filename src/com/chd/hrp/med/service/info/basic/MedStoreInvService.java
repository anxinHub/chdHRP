/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 

public interface MedStoreInvService {

	/**
	 * @Description 
	 * 添加08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String addMedStoreInvAll(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedStoreInv(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新08110 仓库材料信息<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedStoreInv(Map<String,Object> entityMap)throws DataAccessException;

	
	
	/**
	 * @Description 
	 * 查询对象08110 仓库材料信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
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
	 * 仓库安全设置--删除
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteSafeMedStoreInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 仓库安全设置--保存
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String addSafeMedStoreInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 仓库安全设置--查询
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String querySafeMedStoreInv(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库材料定义--保存材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addMedStoreInvCert(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库材料定义--全部材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvAllList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 判断材料是否已经存在于该仓库中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMedInvInStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询材料申领仓库
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvApplyStore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 设置材料默认申领仓库
	 * @param List
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException; 
	
	/**
	 * 取消设置申领仓库
	 * @param List
	 * @return
	 * @throws DataAccessException
	 */
	public String updateCancelMedInvApplyStore(List<Map<String, Object>> entityMap) throws DataAccessException; 
}
