/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.toptodown.plan;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院年度至医院月份医疗收入预算分解方案
 * @Table:
 * BUDG_MED_INCOME_HM_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeHmPlanMapper extends SqlMapper{
	
	public List<?> queryIncomeSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void addBatchMedIncomeHm(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询数据是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 校验 医疗收入科目 是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjExist(Map<String, Object> entityMap) throws DataAccessException;

}
