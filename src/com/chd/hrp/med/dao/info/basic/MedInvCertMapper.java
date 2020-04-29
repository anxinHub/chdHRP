
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
 

public interface MedInvCertMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedInvCert(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedInvCert(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedInvCert(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return MedInvCert
	 * @throws DataAccessException
	*/
	public int updateBatchMedInvCert(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedInvCert(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除MED_INV_CERT<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedInvCert(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集MED_INV_CERT<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedInvCert> queryMedInvCert(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集MED_INV_CERT<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedInvCert> queryMedInvCert(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取MED_INV_CERT<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedInvCert
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
	 * 查询结果集MED_INV_CERT<BR>全部（不分页   left join MED_INV_CERT_TYPE ）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedInvCert> queryMedInvCertNew(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询结果集MED_INV_CERT<BR>全部（分页   left join MED_INV_CERT_TYPE ）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedInvCert> queryMedInvCertNew(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 修改时 查询获取MED_INV_CERT<BR>(页面回值)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public MedInvCert queryMedInvCertByCodeNew(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询 MedInvCert（判断证件编号 是否已经存在）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedInvCert> queryMedInvCertByID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询MedInvCert 数据是否被引用（引用不允许删除）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDelDate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 获取材料列表
	 */
	public List<Map<String,Object>> queryMedCertInvList(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryMedCertInvList(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 获取序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryMedInvCertNextId() throws DataAccessException;
	
	/**
	 * 查询 选择的材料
	 */
	public List<Map<String,Object>> queryMedCertInvChoiceInvList(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	 /**
		 * @Description 
		 * 批量审核或消审<BR> 
		 * @param  List<Map<String, Object>>
		 * @return int
		 * @throws DataAccessException
		*/
		public int auditOrUnAuditBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
		
	/**
	 * @Description 
	 * 查询证件信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCertListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询证件类别信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCertTypeListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询生产厂商信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryFacListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询材料信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryInvListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询供应商信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> querySupListForImport(Map<String,Object> entityMap) throws DataAccessException;
}
