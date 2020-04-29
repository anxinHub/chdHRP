/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeDeptYearService extends SqlService {
	String queryLastYearIncome(Map<String, Object> mapVo);

	String save(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 提交  撤回  取消审核(自下而上)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String reviewOrUnreview(List<Map<String, Object>> listVo) throws DataAccessException; 
	
	/**
	 *审核 (通过,不通过) (自下而上)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String passOrDisPassDown(List<Map<String, Object>> listVo) throws DataAccessException;

	/**
	 * 科室年度 分解计算 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 分解计算 增量生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 分解计算  增量生成 添加数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String addGenerateData(List<Map<String, Object>> listVo) throws DataAccessException;
	public List<Map<String,Object>> queryBudgMedIncomeDeptYear(Map<String,Object> mapVo) throws DataAccessException;
    /**
     * 生成分解比例
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	public String generateResolveRateDept(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<Map<String, Object>> queryDataDept(Map<String, Object> mapVo)throws DataAccessException;
}
