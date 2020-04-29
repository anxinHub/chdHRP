package com.chd.hrp.hr.dao.salarymanagement.socialSecurityManage;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.salarymanagement.socialSecurityManage.HrInsurKind;

public interface HrInsurKindMapper extends SqlMapper {

	/**
	 * 与query()方法区别在于这个的返回结果是bean集合，没有分页重载
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrInsurKind> queryInsurKind(Map<String, Object> paramMap) throws DataAccessException;

}
