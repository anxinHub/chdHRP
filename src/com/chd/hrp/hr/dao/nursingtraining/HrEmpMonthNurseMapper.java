/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.nursingtraining;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrEmpMonthNurse;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_MONTH_NURSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrEmpMonthNurseMapper extends SqlMapper{

	void deleteHrEmpMonthNurse(List<HrEmpMonthNurse> list) throws DataAccessException;

	List<HrEmpMonthNurse> queryHrEmpMonthNurse(Map<String, Object> entityMap) throws DataAccessException;

	List<HrEmpMonthNurse> queryHrEmpMonthNurse(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	List<Map<String, Object>> queryByPrint(Map<String, Object> entityMap);
	
}
