package com.chd.hrp.mat.service.storage.out;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatOutApplyNotStoreService extends SqlService {
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
	public String updateSendMatCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量作废<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String failedMatCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatCommonOutApplyBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 配套表结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatch(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 新增、修改操作 中途改变响应仓库时 查询询之前表格中已添加的材料 是否存在于当前选择的响应仓库中
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreExistInv(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 主页面查询明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryNDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 *取消发送
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBackSendMatCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException;

}
