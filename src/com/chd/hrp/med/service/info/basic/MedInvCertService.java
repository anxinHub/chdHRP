
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.med.service.info.basic;
import java.text.ParseException;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedInvCert;
/**
 * 
 * @Description:
 * MED_INV_CERT
 * @Table:
 * MED_INV_CERT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedInvCertService {

	/**
	 * @Description 
	 * 添加MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String addMedInvCert(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	
	/**
	 * @Description 
	 * 更新MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	public String updateMedInvCert(Map<String,Object> entityMap)throws DataAccessException, ParseException;
	
	/**
	 * @Description 
	 * 批量更新MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchMedInvCert(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedInvCert(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchMedInvCert(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInvCert(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象MED_INV_CERT<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public MedInvCert queryMedInvCertByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取MED_INV_CERT<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedInvCert
	 * @throws DataAccessException
	*/
	public MedInvCert queryMedInvCertByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 获取材料列表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedCertInvList(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询对应关系明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInvCertDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除对应关系
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMedInvCertRela(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询选择的材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedCertInvChoiceInvList(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加 证件及证件供应商对应关系
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String addBatchMedCertSup(List<Map<String, Object>> entityList)	throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedInvCertBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedInvCertBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String importMedInvCert(Map<String, Object> entityMap) throws DataAccessException;
}
