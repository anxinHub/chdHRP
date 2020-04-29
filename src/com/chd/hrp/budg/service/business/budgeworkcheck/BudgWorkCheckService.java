/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.budgeworkcheck;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 业务预算 审批
 * @Table:
 * BUDG_WORK_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkCheckService extends SqlService {


	public String  queryDeptNameByUserId(Map<String, Object> map) throws DataAccessException;

	public String queryDeptInformation(Map<String, Object> page) throws  DataAccessException;

	/**
	 * 发送、召回 修改状态
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public  String sendOrRecall(List<Map<String, Object>> list) throws DataAccessException;
	
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
	 * 查询医院明细   初始审核 (修改页面 未下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkHosMonth(Map<String, Object> mapVo) throws  DataAccessException;
	
	/**
	 * 查询医院明细   调整审核 (修改页面 未下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkHosMonthAdjust(Map<String, Object> mapVo) throws DataAccessException;

	
	/**
	 * 查询科室明细  初始审核 (修改页面 未下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkDeptMonth(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询科室明细  调整审核 (修改页面 未下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkCheckDeptMonthAdjust(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 查询医院明细   初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkHosMonthCopy(Map<String, Object> mapVo) throws  DataAccessException;
	
	/**
	 * 查询医院明细   调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkHosMonthAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;

	
	/**
	 * 查询科室明细  初始审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkDeptMonthCopy(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询科室明细  调整审核 (修改页面 已下达)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWorkCheckDeptMonthAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
	public String queryBudgWorkCheckYearAdjust(Map<String, Object> mapVo) throws DataAccessException;
	public String queryBudgWorkCheckYear(Map<String, Object> mapVo) throws DataAccessException;
	public String queryBudgWorkCheckDeptYear(Map<String, Object> mapVo) throws DataAccessException;
	public String queryBudgWorkCheckDeptYearAdjust(Map<String, Object> mapVo) throws DataAccessException;

	public String  queryIssueData(Map<String, Object> mapVo) throws DataAccessException;

	public String queryBudgAdjFile(Map<String, Object> mapVo) throws DataAccessException;
	
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
	public int queryInitDateExist(Map<String, Object> initMap)  throws DataAccessException;
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 * @param initMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIssueDateExist(Map<String, Object> initMap)  throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgWorkCheckState(Map<String, Object> mapVo) throws DataAccessException;
	
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
	
	/**
	 * 根据所传预算年度  查询 业务预算编制模式
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWorkBudgMode(Map<String, Object> mapVo) throws DataAccessException;

	
	

}
