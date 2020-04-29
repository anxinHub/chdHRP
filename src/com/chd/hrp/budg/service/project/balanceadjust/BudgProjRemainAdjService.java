package com.chd.hrp.budg.service.project.balanceadjust;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface BudgProjRemainAdjService extends SqlService{

	/**
	 * 查询可用余额 作为调整资金回显
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryUsableAmount(Map<String, Object> page) throws DataAccessException;

	/**
	 * 根据项目 查询资金来源 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryBudgSourceByProj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据项目ID 资金来源ID  查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryProjMessage(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 生成调整单号
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String setBudgApplyCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据条件组合   查询数据明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryProjDetailByCondition(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 修改查询明细数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String querBudgProjBalanceDetail(Map<String, Object> page)throws DataAccessException;

	/**
	 * 审核  修改数据 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAdjSate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 销审  修改数据 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateCancelBatch(List<Map<String, Object>> listVo) throws DataAccessException;

}

