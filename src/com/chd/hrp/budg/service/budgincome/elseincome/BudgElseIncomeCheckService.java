/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.elseincome;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 其他收入预算审批申请
 * @Table:
 * BUDG_ELSE_INCOME_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgElseIncomeCheckService extends SqlService {
	
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
	 * 医院业务预算  初始审批 查询 (未下达)
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgElseIncome(Map<String, Object> mapVo) throws  DataAccessException;
	
	/**
	 * 医院业务预算  初始审批 查询 (已下达)
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgElseIncomeCopy(Map<String, Object> mapVo) throws  DataAccessException;
	
	/**
	 * 医院其他业务 调整审批 查询(未下达)
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgElseIncomeAdjust(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院其他业务 调整审批 查询 (已下达)
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgElseIncomeAdjustCopy(Map<String, Object> mapVo) throws DataAccessException;
	

	public String  queryIssueData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 根据 所传参数 查询数据状态
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgElseIncomeCheckState(Map<String, Object> entityMap)throws DataAccessException;
	
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
	public int queryInitDateExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 * @param initMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIssueDateExist(Map<String, Object> mapVo) throws DataAccessException;
	
}
