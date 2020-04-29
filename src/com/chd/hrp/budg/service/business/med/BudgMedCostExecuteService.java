/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.med;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医疗支出执行数据
 * @Table:
 * BUDG_MED_COST_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedCostExecuteService extends SqlService {
	
	/**
	 * 医疗支出执行数据  采集 （财务取数）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectData(Map<String, Object> mapVo) throws DataAccessException;

}
