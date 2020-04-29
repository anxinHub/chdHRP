/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationplan.budg;
import java.util.List;
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
 

public interface BudgWorkHosMonthService extends SqlService {
	
	/**
	 * 查询预算 指标的上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 导入时 查询预算指标是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int qureyBudgIndexExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 汇总
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgWorkHosMonthUp(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 分解计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String resolveData(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 根据主键  查询数据是否存在
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量修改 预算值
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBatchBudgValue(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询 分解计算的 医院月份数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryResolveData(Map<String, Object> mapVo) throws DataAccessException;

	List<Map<String, Object>> getPrintData(Map<String, Object> mapVo)
			throws DataAccessException;

	List<Map<String, Object>> getResolvePrintData(Map<String, Object> mapVo)
			throws DataAccessException;

	

}
