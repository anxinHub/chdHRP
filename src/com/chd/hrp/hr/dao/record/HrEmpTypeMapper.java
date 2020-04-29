/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.dao.record;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.record.HrEmpType;

/**
 * 
 * @Description: 人员类别
 * @Table: HR_EMP_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface HrEmpTypeMapper extends SqlMapper {
	/**
	 * 删除人员类别
	 * 
	 * @param entityList
	 */
	void deleteHrEmpType(List<HrEmpType> entityList);

	/**
	 * 增加查询
	 * 
	 * @param entityMap
	 * @return
	 */
	List<HrEmpType> queryEmpTypeById(Map<String, Object> entityMap);

	Map<String, Object> queryEmpTypeByName(Map<String, Object> entityMap);

	HrEmpType queryByCodequeryByCodeName(Map<String, Object> saveMap);

}
