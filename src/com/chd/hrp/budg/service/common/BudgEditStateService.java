/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 预算编辑状态管理 
 * @Table:
 * BUDG_EDTI_STATE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgEditStateService extends SqlService{
	
	/**
	 * 查询  业务预算可编辑标记 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWorkFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询  医疗收入可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedIncomeFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询  其他收入可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryElseIncomeFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询  医疗支出可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedCostFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询  其他支出可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryElseCostFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询 材料采购可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPurFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询 药品采购可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPurFlag(Map<String, Object> mapVo)throws DataAccessException;

}
