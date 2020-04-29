/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.toptodown.hosyearinbudg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院年度医疗收入独立核算科目预算
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosIndependentSubjMapper extends SqlMapper{

	public Map<String, Object> queryLastYearIncome(Map<String, Object> entityMap);

	public Map<String, Object> queryGetWay(Map<String, Object> entityMap);

	public int queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo);

	public String queryGetWayFromBudgMedIncomeEditPlan(Map<String, Object> mapVo);
	
	/**
	 * 根据指标编码查询 其取值方法
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIndexGetWay(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 添加数据时  校验数据是否存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException ;
	
	/**
	 * 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getGrowRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度医疗收入独立核算科目预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;

	public List<Map<String, Object>> queryBudgSubjEditMethod(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryBudgSubjEditMethod(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
}
