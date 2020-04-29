package com.chd.hrp.budg.dao.common;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


/**
 * 整个 预算  流程管理 控制点
 * @Description:
 * 预算编辑状态管理
 * @Table:
 * BUDG_EDIT_STATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgEditStateMapper extends SqlMapper{

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

	/**
	 * 修改  业务预算可编辑标记 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateWorkFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 修改  医疗收入可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedIncomeFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 修改 其他收入可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateElseIncomeFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 修改  医疗支出可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedCostFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 修改 其他支出可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateElseCostFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 修改 材料采购可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMatPurFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 修改 药品采购可编辑标记
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateMedPurFlag(Map<String, Object> mapVo)throws DataAccessException;
	
	
	/**
	 * 根据主键查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo)throws DataAccessException;
}
