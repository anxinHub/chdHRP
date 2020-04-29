/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.toptodown.deptmonthinbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室月份医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface MedInDMBudgService extends SqlService {
	
	/**
	 * 导入时 查询数据是否已存在  （专用  勿动）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

	public String queryResolve(Map<String, Object> page) throws DataAccessException;

	public String queryDMLastYearIncome(Map<String, Object> mapVo) throws DataAccessException;

	public String collectMedInDMBudgUp(Map<String, Object> mapVo) throws DataAccessException;

	public String queryDeptyearValue(Map<String, Object> mapVo) throws DataAccessException;

	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 增量生成  查询数据
	 * @param mapVo
	 * @return
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
	 * 批量添加 生成数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addGenerateBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	public List<Map<String,Object>> queryMedInDMBudgUpPrintDate(Map<String,Object> mapVo) throws DataAccessException;
	
	public List<Map<String,Object>> queryMedInHYBudgUpData(Map<String,Object> mapVo) throws DataAccessException;
	 	public List<Map<String,Object>> queryDeptMontBudgUpData(Map<String,Object> mapVo) throws DataAccessException;
}
