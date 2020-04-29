/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.execute;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医疗收入执行数据
 * @Table:
 * BUDG_MED_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedInExecuteService extends SqlService {
	/**
	 * 财务取数
	 * @param mapVo
	 * @return
	 */
	public String getDatafromAcc(Map<String, Object> mapVo) throws DataAccessException;
	
	public String getDatafromAcc2(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * his收入数据 采集 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String saveHisExecuteData(Map<String, Object> mapVo) throws DataAccessException;

}
