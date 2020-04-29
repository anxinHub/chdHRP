/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.toptodown.hosmonthinbudg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeHosMonth;
/**
 * 
 * @Description:
 * 医院月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeHosMonthMapper extends SqlMapper{
	/**
	 * 查询 科室月份医疗收入预算 汇总数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 导入时 查询数据是否已存在  （专用  勿动）
	 * @param entityMap
	 * @return
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 结转时查询 当前预算年月预算值
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgMedIncomeHosMonthByYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 结转:批量修改下月执行值
	 * @param List<Map<String,Object>>
	 * @return int
	 */
	public int updateBatchBudgMedIncomeHosMonthValue(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * 批量修改 预算值
	 * @param updateList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateValueBatch(List<Map<String, Object>> updateList) throws DataAccessException;
	
	/**
	 * 查询数据 医院月份医疗收入预算  预算分解维护  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<BudgMedIncomeHosMonth> queryResolve(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询数据 医院月份医疗收入预算  预算分解维护 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<BudgMedIncomeHosMonth> queryResolve(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询 所选年度、科目的计算数据  计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCountData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 所选年度、科目  参考年限所有月份的收入 总和
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> querySumLastYearIncome(Map<String, Object> item)throws DataAccessException;
	
	/**
	 * 根据 月份、自定义分解系数 计算分解比例(医院月)
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryResolveDataRate(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 批量更新 预算值
	 * @param entityList
	 * @throws DataAccessException
	 */
	public int updateBatchBudgValue(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询上年收入
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearIncome(Map<String, Object> map) throws DataAccessException;
	/**
	 * 增量生成时 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
