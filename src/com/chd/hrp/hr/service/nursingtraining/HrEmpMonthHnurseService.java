/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.nursingtraining;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_MONTH_HNURSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrEmpMonthHnurseService {

	String saveHrEmpMonthHnurse(Map<String, Object> entityMap) throws DataAccessException;

	String queryHrEmpMonthHnurse(Map<String, Object> entityMap) throws DataAccessException;

	String deleteHrEmpMonthHnurse(Map<String, Object> entityMap) throws DataAccessException;

	String importMonthHNurse(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryHNurseByPrint(Map<String, Object> page)throws DataAccessException;
}
