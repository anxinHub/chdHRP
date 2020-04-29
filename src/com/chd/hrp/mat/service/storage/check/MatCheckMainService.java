/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.check;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.mat.entity.MatCheckMain;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_CHECK_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatCheckMainService extends SqlService {
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatCheckMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatCheckMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 消审数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatCheckMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
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
	public String queryMatCheckMainByMatInv(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public MatCheckMain queryMatCheckMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatCheckDetailByCheckID(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 盘点单模板打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatCheckByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 引入仓库材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException 
	 */
	public String queryMatStoreInvDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 盘点打印模板
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryMatCheckByPrintDY(Map<String, Object> map) throws DataAccessException;

	public String updateCheckInOut(List<Map<String, Object>> listVo);

	public String queryMatCheckDetailByInvBatch(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 修改某种材料对应批号的盘点明细信息
	 * @param mapVo
	 * @return
	 */
	public String updateCheckInvBatchDetail(Map<String, Object> mapVo);

}
