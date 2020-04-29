package com.chd.hrp.mat.service.affi.out;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SqlService;


public interface MatAffiOutCommonService extends SqlService{
	/**
	 * 根据out_id查询明细表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryByCodeDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 配套导入  查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutMatchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 审核 代销出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String auditMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 取消审核 代销出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String unAuditMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 删除 代销出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 代销出库--整单出库查询主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiInMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--整单出库查询材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiInInv(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 代销出库--整单出库查询添加明细材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiInWholeDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--添加材料查及时库存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutByInvHold(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--查询 mat_affi_fifo 查有批次结存表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutByFifoBalance(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 代销出库--复制出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 代销出库--冲销出库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String offsetMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 代销出库--出库确认
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmMatAffiOutCommon(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 代销出库--上一张、下一张的ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAffiOutIds(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 配套导入 组装明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiOutDetailByMatch(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询明细
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiOutDetailById(Map<String, Object> page)throws DataAccessException; 
	public String queryAffiOutDetailByCode(Map<String, Object> page)throws DataAccessException; 
	
	
	//入库模板打印（包含主从表）
    public String matAffiOutCommonService(Map<String,Object> entityMap) throws DataAccessException;
    /**
     * 代销出库选择材料
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryMatAffiInvBatchList(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 选择材料	
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiOutInvListByChoiceInv(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessExceptio
	 */
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;
		
	public Map<String, Object> matAffiOutCommonTemplate(Map<String,Object> entityMap) throws DataAccessException;

	public void updateMatAffiOutRela(Map<String, Object> entityMap)throws DataAccessException;

	public int queryMatAffiOutIsApply(Map<String, Object> mapVo)throws DataAccessException;
	
}
