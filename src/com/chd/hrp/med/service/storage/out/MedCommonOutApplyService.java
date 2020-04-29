/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.out;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 08312 科室申请单主表
 * @Table:
 * MED_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedCommonOutApplyService extends SqlService {
	
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
	 * 批量发送<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateSendMedCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量作废<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String failedMedCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 配套表结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatch(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 新增、修改操作 中途改变响应仓库时 查询询之前表格中已添加的药品 是否存在于当前选择的响应仓库中
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreExistInv(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 组装数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreInvData(Map<String, Object> mapVo) throws DataAccessException;

	public String queryApplyDetails(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * @Description 取消发送<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String updatebackSendMedCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException;

	
	/**
	 * @Description 打印<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMedOutByPrintPage(Map<String, Object> entityMap)throws DataAccessException;
}
