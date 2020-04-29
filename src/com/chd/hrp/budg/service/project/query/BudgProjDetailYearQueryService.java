/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.query;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 年度项目预算
 * @Table:
 * BUDG_PROJ_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjDetailYearQueryService extends SqlService {
	
	/**
	 * 查询数据 年度项目预算明细查询(一)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjDetailYearQuery(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询数据 年度项目预算明细查询(二)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjDetailYearQueryT(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询数据 年度项目预算明细查询(一)(集团)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjDetailYearGroupQuery(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询数据 年度项目预算明细查询(二)(集团)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjDetailYearGroupQueryT(Map<String, Object> mapVo) throws DataAccessException;

}
