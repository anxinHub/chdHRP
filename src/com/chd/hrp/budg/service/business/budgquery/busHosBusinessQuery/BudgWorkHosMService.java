﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.budgquery.busHosBusinessQuery;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosMService extends SqlService {

	public  String queryBudgWorkLinkMonth(Map<String, Object> page) throws DataAccessException;

	public String queryHosYearCopy(Map<String, Object> page) throws DataAccessException;

	public String queryHosMonthCopy(Map<String, Object> page) throws DataAccessException;
	public String queryGroupYearCopy(Map<String, Object> page) throws DataAccessException;
	public String queryGroupMonthCopy(Map<String, Object> page) throws DataAccessException;

}