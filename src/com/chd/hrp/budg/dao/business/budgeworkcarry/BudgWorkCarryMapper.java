/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.budgeworkcarry;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 业务预算结转
 * @Table:
 * BUDG_WORK_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkCarryMapper extends SqlMapper{
	
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

	//跟新医院结转下月的数据
	public int  updateBudgWorkHosCarriedNextMonth(Map<String, Object> mapVo) throws DataAccessException;
	//跟新科室的下月数据
	public int  updateBudgWorkDeptCarriedNextMonth(Map<String, Object> mapVo) throws DataAccessException;
	//清空医院结转下月的数据
	public int   clearBudgWorkHosCarriedNextMonth(Map<String, Object> mapVo) throws DataAccessException;
	//清空医院下一个月的结转
	public int clearBudgWorkDeptCarriedNextMonth(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 验证科室的年月是否和结转的年月相等
	 * 
	 * **/
	public String queryDeptYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 结转时 查询 科室月份业务预算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkDeptMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 结转时 查询 医院月份业务预算数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWorkHosMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 结转时 修改 科室月份业务预算数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgWorkDeptMonth(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 结转时 修改 医院月份业务预算数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgWorkHosMonth(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	public String  queryIsCarried(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
	public String queryYear(Map<String, Object> mapVo) throws DataAccessException;
	
	
	
	public String queryMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 业务预算模块 启用年月查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryYearMonthStart(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键 查询  业务预算结转  数据是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 修改业务预算结转 状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateIsCarried(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份  科室月份业务预算结转下月值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgWorkDeptMonthReCharge(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份  医院月份业务预算结转下月值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgWorkHosMonthReCharge(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份 下月  科室月份业务预算  上月结转值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgWorkDeptMonthNext(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结时 清空 反结月份  下月  医院月份业务预算 上月结转值
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgWorkHosMonthNext(Map<String, Object> mapVo) throws DataAccessException;
	

	
}
