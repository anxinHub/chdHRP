/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.toptodown.deptmonthinbudg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgMedIncomeDeptMonth;
/**
 * 
 * @Description:
 * 科室月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeDeptMonthMapper extends SqlMapper{
	/**
	 * 导入时 查询数据是否已存在  （专用  勿动）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询数据 科室月份医疗收入预算分解维护
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	public List<BudgMedIncomeDeptMonth> queryResolve(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<BudgMedIncomeDeptMonth> queryResolve(Map<String, Object> entityMap, RowBounds rowBounds);
	
	//	查询该科目下该科室指定月份上年收入
	public String queryDMLastYearIncome(Map<String, Object> mapVo);
	
	// 查询 所选年度、科目的计算数据
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo);

	//查询 所选年度、科目、科室 参考年度 所有月份的收入 总和
	public Map<String, Object> querySumLastYearIncome(Map<String, Object> lastMap);
	
	/**
	 * 结转时查询 当前预算年月预算值
	 * @param entityMap
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryBudgMedIncomeDeptMonthByYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * 结转:批量修改下月执行值
	 * @param List<Map<String,Object>>
	 * @return int
	 */
	public int updateBatchBudgMedIncomeDeptMonthValue(List<Map<String,Object>> entityList)throws DataAccessException;

	public Map<String, Object> queryDeptyearValue(Map<String, Object> mapVo);

	public String queryDataExistList(List<Map<String, Object>> addList);
	
	/**
	 * 根据 科室、月份、自定义分解系数 计算分解比例(科室月)
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryResolveDataRate(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量修改 预算值
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchBudgValue(List<Map<String, Object>> entityList) throws DataAccessException;
	
}
