/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.base.budgybinfor;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室单病种维护
 * @Table:
 * BUDG_DEPT_SINGLE_DISEASE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptSingleDiseaseService extends SqlService {

	/**
	 * 科室基本信息
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 病种编码信息
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDiseaseData(Map<String, Object> paraMap) throws DataAccessException;

}
