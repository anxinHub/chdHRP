/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.affi.back;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 04105 物资材料表
 * @Table:
 * MAT_INV 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatAffiBackService extends SqlService {
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAffiDetailById(Map<String,Object> entityMap)throws DataAccessException;
	public String queryAffiDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMatAffiBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量复制<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyMatAffiBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatAffiBack(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatAffiBack(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMatAffiBack(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatAffiBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatAffiBackBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库单结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAffiIn(Map<String,Object> entityMap) throws DataAccessException;
	public String queryMatAffiBackInSup (Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 入库单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAffiInDetail(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 入库单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBackDetailByImp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取上一张、下一张
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiInBeforeOrNext(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

}
