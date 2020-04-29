/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcost.medcostcheck;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医疗支出预算审批申请
 * @Table:
 * BUDG_MED_COST_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedCostCheckService extends SqlService {


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
	public String queryBudgMedCostHosMonth(Map<String, Object> page) throws  DataAccessException;
	
	/**
	 * 医院医疗收入 调整审批 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostHosMonthAdjust(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 科室医疗收入 初始审批  查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostDeptMonth(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 科室医疗收入 调整审批  查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostCheckDeptMonthAdjust(Map<String, Object> page) throws DataAccessException;

	/**
	 * 查询医院明细   初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostHosMonthCopy(Map<String, Object> mapVo) throws  DataAccessException;
	
	/**
	 * 查询医院明细   调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostHosMonthAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;

	
	/**
	 * 查询科室明细  初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostDeptMonthCopy(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询科室明细  调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedCostCheckDeptMonthAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgMedCostCheckState(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 初始审批  校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDateExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 * @param initMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInitDateExist(Map<String, Object> initMap) throws DataAccessException ;
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 * @param initMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIssueDateExist(Map<String, Object> initMap) throws DataAccessException;
}
