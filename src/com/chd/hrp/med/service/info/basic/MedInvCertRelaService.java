/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.info.basic;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedInvCertRela;
/**
 * 
 * @Description:
 * 

 * @Table:
 * MED_INV_CERT_RELA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedInvCertRelaService {

	/**
	 * @Description 
	 * 添加
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedInvCertRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchMedInvCertRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedInvCertRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedInvCertRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedInvCertRela(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedInvCertRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新
<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateMedInvCertRela(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集
<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInvCertRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象
<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedInvCertRela queryMedInvCertRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取
<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvCertRela
	 * @throws DataAccessException
	*/
	public MedInvCertRela queryMedInvCertRelaByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 判断材料是否已经存在于对应关系中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMedInvInCert(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加材料 保存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addCertInv(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 添加材料 删除
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteBatchCertInv(List<Map<String, Object>> entityList) throws DataAccessException;
}
