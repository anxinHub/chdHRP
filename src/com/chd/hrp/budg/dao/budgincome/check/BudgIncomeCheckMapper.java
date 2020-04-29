/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.check;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_MED_INCOME_CHECK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgIncomeCheckMapper extends SqlMapper{
	

	public List<Map<String,Object>> querySysDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查询员工所对应的科室
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptNameByUserId(Map<String, Object> map) throws  DataAccessException;
	/**
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptInformation(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 发送、召回 修改状态
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBc_state(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 *预算下达 、取消预算下达
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int cancelIssueOrIssue(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	/**
	 * 调整审批 医院医疗收入预算  查询(修改页面 未下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonth(Map<String, Object> entityMap);
	
	/**
	 * 初始审批 医院医疗收入预算  查询(修改页面 未下达)  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonth(Map<String, Object> entityMap, RowBounds rowBounds);
	
	/**
	 * 初始审批 医院医疗收入预算  查询 (修改页面 未下达)  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthAdjust(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 调整审批 医院医疗收入预算  查询(修改页面 未下达)   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthAdjust(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 初始审批 科室医疗收入预算  查询(修改页面 未下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeDeptMonth(Map<String, Object> entityMap);
	
	/**
	 * 初始审批 科室医疗收入预算  查询(修改页面 未下达)  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeDeptMonth(Map<String, Object> entityMap, RowBounds rowBounds);
	
	/**
	 * 调整审批 科室医疗收入预算  查询 (修改页面 未下达)  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeCheckDeptMonthAdjust(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 调整审批 科室医疗收入预算  查询 (修改页面 未下达)  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeCheckDeptMonthAdjust(Map<String, Object> entityMap ,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询医院明细 初始审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthCopy(Map<String, Object> entityMap);
	/**
	 * 查询医院明细 初始审核 (修改页面 已下达) 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询医院明细 调整审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询医院明细 调整审核 (修改页面 已下达) 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthAdjustCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询科室明细 初始审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeDeptMonthCopy(Map<String, Object> entityMap);
	/**
	 * 查询科室明细 初始审核 (修改页面 已下达) 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeDeptMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询科室明细 调整审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeCheckDeptMonthAdjustCopy(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询科室明细 调整审核 (修改页面 已下达) 不分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedIncomeCheckDeptMonthAdjustCopy(Map<String, Object> entityMap ,RowBounds rowBounds) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryIssueData(Map<String, Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryIssueData(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	public List<Map<String, Object>> queryBudgAdjFile(Map<String, Object> entityMap) throws  DataAccessException;
	public List<Map<String, Object>> queryBudgAdjFile(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 初始审批  校验数据是否存在
	 * @param entityMap
	 * @return
	 */
	public int queryDateExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据参数 查询 初始审批单是否已下达 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryInitDateExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据参数 查询 是否存在未下达的审批表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIssueDateExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据所传预算年度 查询 医疗收入预算编制模式
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryIncomeBudgMode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 校验 审批申请单 是否已调整
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIsAdjust(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询最大审批单号 （只允许删除最大审批单号的审批申请单）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMaxCheckCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改 预算单据号管理表中 该年度 医疗收入预算的最大单号
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateMaxNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查验医院月合计与医院年 数据 是否一致。
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryHosMonthDataCheck(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查验 科室年合计与医院年 数据 是否一致。
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryDeptYearDataCheck(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查验 科室月合计与科室年 数据 是否一致。
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryDeptMonthDataCheck(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 *  备份 医疗收入预算医院月份数据 BUDG_MED_INCOME_HM-COPY
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int copyHMData(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 备份 医疗收入预算 医院年度数据 BUDG_MED_INCOME_HY-COPY
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int  copyHYData(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 备份 医疗收入预算科 室年度数据  BUDG_MED_INCOME_DY-COPY
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int copyDYData(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 *  备份 医疗收入预算科室月份数据，BUDG_MED_INCOME_DM-COPY
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int copyDMData(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 删除医疗收入预算医院月份备份数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteHMCopy(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * @param listVo
	 * @return
	 * 删除 医疗收入预算医院年度备份数据
	 * @throws DataAccessException
	 */
	public int deleteHYCopy(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * @param listVo
	 * @return
	 * 删除  医疗收入预算科室月份备份数据
	 * @throws DataAccessException
	 */
	public int deleteDMCopy(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * @param listVo
	 * @return
	 *  删除 医疗收入预算科室年度备份数据
	 * @throws DataAccessException
	 */
	public int deleteDYCopy(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询 审批申请单是否已做调整
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryCheckIsAdjust(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgMedIncomeCheckState(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新医院意见
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateHosSuggest(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新科室意见
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateDeptSuggest(Map<String, Object> entityMap) throws DataAccessException;
}
