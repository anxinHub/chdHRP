/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.budgcontrol;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 项目预算申报
 * @Table:
 * BUDG_PROJ_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjApplyMapper extends SqlMapper{
	
	/**
	 * 查询 支出项目数据 
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgPaymentItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 支出项目数据 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgPaymentItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据主键查询数据是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解  
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjApplyResolve(	Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 BUDG_PROJ_APPLY_RESOLVE  项目预算分解  汇总 数据（按支出项目汇总）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySumApplyResolve(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 期初项目预算记账BUDG_PROJ_BEGAIN_MARK 状态    为1记账时才可进行预算申报。
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBegainMark(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 提交 撤回 修改 数据状态
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgProjApplyState(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 审核时 修改数据状态
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int auditBudgProjApply(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 *  查询 项目预算数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询 项目预算明细数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgProjDetailData(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	/**
	 * 根据主键 查询 年度项目预算数据 
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBudgProjYearByCode(Map<String, Object> item) throws DataAccessException;
	
	
	/**
	 * 根据主键 查询 年度项目预算明细数据 
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBudgProjYearDetailByCode(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 添加  年度项目预算 数据
	 * @param addYearList
	 * @return
	 */
	public int addBudgProjYearData(List<Map<String, Object>> addYearList) throws DataAccessException;
	
	/**
	 * 修改  年度项目预算 数据
	 * @param upadateYearList
	 * @return
	 */
	public int updateBudgProjYearData(List<Map<String, Object>> upadateYearList) throws DataAccessException;
	
	/**
	 * 添加  年度项目预算明细 数据
	 * @param addYearDetailList
	 * @return
	 */
	public int addBudgProjYearDetailData(List<Map<String, Object>> addYearDetailList) throws DataAccessException;
	
	/**
	 * 修改  年度项目预算明细 数据
	 * @param upadateYearDetailList
	 * @return
	 */
	public int updateBudgProjYearDetailData(List<Map<String, Object>> upadateYearDetailList) throws DataAccessException;
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询  预算启用年度；
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgModStartYear(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据 申报预算年度  查询 结转状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYearIsCarried(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
}
