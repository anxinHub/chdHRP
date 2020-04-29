/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.check;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface MedCheckMainService extends SqlService {
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedCheckMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedCheckMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 消审数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedCheckMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 生成出入库单<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public String addInOut(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedCheckMainByMedInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public MedCheckMain queryMedCheckMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedCheckDetailByCheckID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 盘点单模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedCheckByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 引入仓库药品
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedStoreInvDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 盘点打印模板
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryMedCheckByPrintDY(Map<String, Object> map) throws DataAccessException;

}
