/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.toptodown.plan;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgMedIncomeHmPlanService extends SqlService {
	/**
	 * 生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String generate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 校验 医疗收入科目 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjExist(Map<String, Object> mapVo) throws DataAccessException;
}
