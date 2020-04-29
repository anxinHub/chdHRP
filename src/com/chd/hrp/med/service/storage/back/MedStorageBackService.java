/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.back;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 08105 药品药品表
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedStorageBackService extends SqlService {
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMedStorageBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量复制<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyMedStorageBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedStorageBack(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedStorageBack(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMedStorageBack(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedStorageBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedStorageBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库单结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryIn(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryInDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBackDetailByImp(Map<String,Object> entityMap) throws DataAccessException;

	public	String queryMedInByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

}
