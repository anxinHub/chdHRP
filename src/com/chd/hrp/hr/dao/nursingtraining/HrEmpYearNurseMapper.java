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
import com.chd.hrp.hr.entity.nursingtraining.HrEmpYearNurse;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_EMP_YEAR_NURSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrEmpYearNurseMapper extends SqlMapper{

	void deleteHrEmpYearNurse(List<HrEmpYearNurse> list) throws DataAccessException;

	List<HrEmpYearNurse> queryHrEmpYearNurse(Map<String, Object> entityMap) throws DataAccessException;

	List<HrEmpYearNurse> queryHrEmpYearNurse(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 打印
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryYearHNurseByPrint(Map<String, Object> entityMap);
	
}
