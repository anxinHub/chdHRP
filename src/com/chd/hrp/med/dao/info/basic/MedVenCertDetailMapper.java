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
import com.chd.hrp.med.entity.MedVenCertDetail;
/**
 * 
 * @Description:
 * cert_state 1：在用 0：停用
 * @Table:
 * MED_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedVenCertDetailMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加cert_state 1：在用  0：停用 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchMedVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return MedVenCertDetail
	 * @throws DataAccessException
	*/
	public int updateBatchMedVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMedVenCertDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchMedVenCertDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集cert_state 1：在用 0：停用 <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedVenCertDetail> queryMedVenCertDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集cert_state 1：在用 0：停用 <BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<MedVenCertDetail> queryMedVenCertDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取cert_state 1：在用  0：停用 <BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return MedVenCertDetail
	 * @throws DataAccessException
	*/
	public MedVenCertDetail queryMedVenCertDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取cert_state 1：在用 0：停用 <BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedVenCertDetail
	 * @throws DataAccessException
	*/
	public MedVenCertDetail queryMedVenCertDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 按照查询条件，查询出供应商 （不分页）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedVenCertDetail> queryMedVenCertDetailSUP(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 按照查询条件，查询出供应商 （分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedVenCertDetail> queryMedVenCertDetailSUP(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询 指定供应商的证件明细(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedVenCertDetail> queryMedVenCertDetailCert(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 指定供应商的证件明细（分页）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<MedVenCertDetail> queryMedVenCertDetailCert(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询指定的 供应商证件信息 (详细信息 链表查询)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public MedVenCertDetail queryMedVenCertDetailByID(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改（最新）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedVenCertDetailNew(Map<String, Object> entityMap) throws DataAccessException;
	
}
