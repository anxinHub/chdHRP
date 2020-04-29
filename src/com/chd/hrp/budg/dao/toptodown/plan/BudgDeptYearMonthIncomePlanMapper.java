/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.toptodown.plan;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptYearMonthIncomePlanMapper extends SqlMapper{
	
	/**
	 * 校验 医疗收入科目 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询数据 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/*
	 * 生成
	 */
	public void addBatchMedIncomeDym(Map<String, Object> entityMap) throws DataAccessException;
	
}
