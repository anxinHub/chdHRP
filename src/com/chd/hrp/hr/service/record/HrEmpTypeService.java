
package com.chd.hrp.hr.service.record;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.record.HrEmpType;

/**
 * 
 * @Description: 人员类别
 * @Table: HR_EMP_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface HrEmpTypeService {
	/**
	 * 删除人员类别
	 * 
	 * @param listVo
	 * @return
	 */
	String deleteHrEmpType(List<HrEmpType> listVo) throws DataAccessException;

	/**
	 * 查询左侧列表
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryHosEmpTypeTree(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 新增人员类别
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String add(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改查询人员类别
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	HrEmpType queryByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改人员类别
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String update(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询人员类别
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String query(Map<String, Object> page) throws DataAccessException;
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;

}
