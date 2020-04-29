/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.budgmedriskfund;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 提取医疗风险基金预算编制
 * @Table:
 * BUDG_MED_RISK_FUND
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgMedRiskFundMapper extends SqlMapper{

	/**
	 * 查询科室列表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int copyBudgMedRiskFund(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 从BUDG_MED_INCOME_DEPT_MONTH中取所选预算年度各月份各科室收入预算值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgMedIncomeDeptMonth(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 提取比例取自预算系统设置BUDG_SYS_SET,该预算年度的RISK_FUND_RATE。
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Double queryRiskFundRate(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询数据是否存在（批量）
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 查询 所传 科室 的 科室月份收入预算值  同时查询医疗风险基金的提取比例
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryWorkload(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 科室基本信息(根据编码 匹配ID用)
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 校验数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
}
