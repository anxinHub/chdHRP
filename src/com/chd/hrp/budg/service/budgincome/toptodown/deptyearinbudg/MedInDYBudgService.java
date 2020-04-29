/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.toptodown.deptyearinbudg;
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
 

public interface MedInDYBudgService extends SqlService {
	/**
	 * 添加页面  科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDept(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询预算科室是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectMedInDYBudgUp(Map<String, Object> mapVo) throws DataAccessException;

	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

	public String queryDeptYearLastYearWork(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 下发  撤回  取消确认  操作(自上而下)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String issuedOrRetract(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 确认(通过,不通过) (自上而下)
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String passOrDisPass(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 生成查询数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;

}
