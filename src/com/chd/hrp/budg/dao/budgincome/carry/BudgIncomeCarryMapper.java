/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.carry;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医疗收入预算结转
 * @Table:
 * BUDG_MED_INCOME_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgIncomeCarryMapper extends SqlMapper{
	
	/**
	 * 已结转月份查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryYearMonthBefore(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 待结转月份查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryYearMonth(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 验证科室的年月是否和结转的年月相等
	 * 
	 * **/
	public String queryDeptYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 结转时 查询 科室月份医疗收入预算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIncomeDeptMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 结转时 查询 医院月份医疗收入预算数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIncomeHosMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 结转时 修改 科室月份医疗收入预算数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeDeptMonth(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 结转时 修改 医院月份医疗收入预算数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeHosMonth(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	public String  queryIsCarried(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 医疗收入预算模块 启用年月查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryYearMonthStart(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询  医疗收入预算结转  数据是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 修改医疗收入预算结转 状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateIsCarried(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份  科室月份医疗收入预算结转下月值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeDeptMonthReCharge(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份  医院月份医疗收入预算结转下月值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeHosMonthReCharge(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份 下月  科室月份医疗收入预算  上月结转值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeDeptMonthNext(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份  下月  医院月份医疗收入预算 上月结转值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeHosMonthNext(Map<String, Object> mapVo) throws DataAccessException;
	

	
}
