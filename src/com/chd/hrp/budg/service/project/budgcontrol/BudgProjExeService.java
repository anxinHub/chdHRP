/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.budgcontrol;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_PROJ_EXE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjExeService extends SqlService {

	/**
	 * 项目下拉框  添加使用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资金来源下拉框  添加使用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSource(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出项目下拉框  添加使用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 确认
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmBudgProjExe(Map<String, Object> mapVo) throws DataAccessException;
}
