package com.chd.hrp.budg.dao.control;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface BudgControlPaymentMapper  extends SqlMapper{
	
	/**
	 * 查询项目预算的可用余额
	 * @param mapVo
	 * @return
	 */
	public Double queryBudgValueForProjYear(Map<String,Object> mapVo);
	
	/**
	 * 获取费用申请预算数据
	 * @param mapVo
	 * @return
	 */
	public Double queryBudgValueForExpenseApply(Map<String,Object> mapVo);
	/**
	 * 获取MedCost预算数据
	 * @param mapVo
	 * @return
	 */
	public Double queryBudgValueForMedCost(Map<String,Object> mapVo);
	/**
	 * 按主表项目分组获取用户数据
	 * @param mapVo
	 * @return
	 */
	
	public List<Map<String,Object>>  queryUserDataValueForMain(Map<String,Object> mapVo);
	/**
	 * 根据明细分组获取用户数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>>  queryUserDataValueForDetail(Map<String,Object> mapVo);
	
	/**
	 * 按主表查询与用户数据相关的执行数据	
	 * @param mapVo
	 * @return
	 */
	public Double  queryExeDataValueForMain(Map<String,Object> mapVo);
	/**
	 * 按明细查询与用户数据相关的执行数据
	 * @param mapVo
	 * @return
	 */
	public Double  queryExeDataValueForDetail(Map<String,Object> mapVo);
	
	/**
	 * 获取支出预算的已执行数据
	 * @param mapVo
	 * @return
	 */
	public Double queryExeDataValueForMedCost(Map<String,Object> mapVo);
	
	
}
