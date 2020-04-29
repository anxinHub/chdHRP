package com.chd.hrp.med.service.purchase.make;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * @Description:
 * 04114 采购计划编制
 * @Table:
 * MED_PUR_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedPurMainService extends SqlService{
	
	/**
	 * @Description 
	 * 采购计划编制<BR>中止采购计划
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedPurPlanState(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 采购计划编制<BR>查询 按采购计划主表ID查询明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedPurDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科室需求计划主表
	 * @param entityMap
	 * @return String 
	 * @throws DataAccessException
	 */
	public String queryMedRequireMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 统购采购计划汇总<BR>查询 按药品ID 查询药品当前库存明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedInvCurAmountDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 按科室计划单ID 查询科室需求计划主表
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public Map<String,Object> queryMedRequireMainByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 按一个或多个科室计划单ID 查询科室计划单明细 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMedRequireDetailByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 按需求计划生成 添加采购计划 
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addProdMedPurMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 计划数量明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMedRequireAmountDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 科室需求计划明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMedReqByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * 安全库存导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurSafe(Map<String, Object> entityMap) throws DataAccessException;
	//采购模板打印（包含主从表）
	public String queryMedMakeByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	//采购模板打印（按供应商汇总明细打印） 
	public String queryMedMakeByDetailPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * 查询 仓库需求计划明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMedReqStoreMain(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 组装汇总的仓库明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreCollectData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 仓库需求计划明细
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryMedReqDeptMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 组装汇总的科室明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptCollectData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查看供应商信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySupDetails(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 组装明细数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPurDetailCollectData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 关闭药品
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedPurCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 取消关闭药品
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedPurCancleCloseInv(List<Map<String, Object>> entityList)  throws DataAccessException;
	/**
	 * 查看关闭的药品
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 采购数量来源查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurAmountRela(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改药品数量对应关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedPurAmountRela(Map<String, Object> entityMap) throws DataAccessException;

	Map<String, Object> queryMedPurMain(Map<String, Object> map) throws DataAccessException;

	Map<String, Object> queryMedPurMainDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public String addMedPurPlanBySecuLimit(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMedPurDetailInv(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String updateMedPurRelaTables(Map<String, Object> entityMap) throws DataAccessException;
	

	//public String queryPurDetailOrder(List<Map<String, Object>> listVo) throws DataAccessException;
}
