/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.affi.check;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedAffiCheckMain;
import com.chd.hrp.med.entity.MedCheckMain;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAffiCheckMainService extends SqlService {
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAffiCheckMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedAffiCheckMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 消审数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedAffiCheckMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAffiCheckMainByMedInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public MedAffiCheckMain queryMedAffiCheckMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAffiCheckDetailByCheckID(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成出入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String createInOut(List<Map<String, Object>> entityList)throws DataAccessException;

	public String queryMedAffiCheckByPrintTemlate(Map<String, Object> mapVo) throws DataAccessException;

	public String queryMedAffiStoreInvDetail(Map<String, Object> mapVo) throws DataAccessException;
}
