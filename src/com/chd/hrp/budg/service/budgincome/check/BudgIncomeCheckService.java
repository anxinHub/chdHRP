/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.check;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医疗收入预算审批申请
 * @Table:
 * BUDG_MED_INCOME_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgIncomeCheckService extends SqlService {
	
	public String  queryDeptNameByUserId(Map<String, Object> map) throws DataAccessException;

	public String queryDeptInformation(Map<String, Object> page) throws  DataAccessException;

	/**
	 * 发送、召回 修改状态
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public  String updateBc_state(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 预算下达 、取消预算下达
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String cancelIssueOrIssue(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 医院收入预算  初始审批 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeHosMonth(Map<String, Object> page) throws  DataAccessException;
	
	/**
	 * 医院医疗收入 调整审批 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeHosMonthAdjust(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 科室医疗收入 初始审批  查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeDeptMonth(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 科室医疗收入 调整审批  查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeCheckDeptMonthAdjust(Map<String, Object> page) throws DataAccessException;

	/**
	 * 查询医院明细   初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeHosMonthCopy(Map<String, Object> mapVo) throws  DataAccessException;
	
	/**
	 * 查询医院明细   调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeHosMonthAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;

	
	/**
	 * 查询科室明细  初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeDeptMonthCopy(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询科室明细  调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedIncomeCheckDeptMonthAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;
	

	public String  queryIssueData(Map<String, Object> mapVo) throws DataAccessException;

	public String queryBudgAdjFile(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 初始审批  校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDateExist(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 * @param initMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInitDateExist(Map<String, Object> initMap) throws DataAccessException;
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 * @param initMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIssueDateExist(Map<String, Object> initMap) throws DataAccessException ;
	
	/**
	 * 根据所传预算年度  查询 医疗收入预算编制模式
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryIncomeBudgMode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgMedIncomeCheckState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 更新医院意见
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateHosSuggest(Map<String, Object> mapVo ) throws DataAccessException;
	
	/**
	 * 更新科室意见
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateDeptSuggest(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
