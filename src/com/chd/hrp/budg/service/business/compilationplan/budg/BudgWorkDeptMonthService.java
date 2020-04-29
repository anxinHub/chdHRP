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
 * 科室月份业务预算
 * @Table:
 * BUDG_WORK_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptMonthService extends SqlService {
	
	/**
	 * 查询 数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgWorkDeptMonthUp(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算分解 页面 查询数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCollectData(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 查询 所传年度、指标 、科室的上年业务量
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearWorkLoad(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 增量生成时 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 *根据 指标、科室编码 查询 指标科室对应关系 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 批量修改 预算值
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBatchBudgValue(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo) throws DataAccessException;

	List<Map<String, Object>> getUpdatePrintData(Map<String, Object> mapVo)
			throws DataAccessException;

}
