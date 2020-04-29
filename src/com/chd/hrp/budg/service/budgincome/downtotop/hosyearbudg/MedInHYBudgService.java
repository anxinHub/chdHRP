/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.downtotop.hosyearbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface MedInHYBudgService extends SqlService {
	/**
	 * 添加页面  医院下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDept(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询预算医院是否存在
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
	public String collectMedInHYBudgUp(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据科目编码查询上年收入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearIncome(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

}
