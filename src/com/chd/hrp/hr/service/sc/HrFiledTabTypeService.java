package com.chd.hrp.hr.service.sc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.sc.HrFiledTabType;

public interface HrFiledTabTypeService extends SqlService{

	String deletehrFiledTabType(List<HrFiledTabType> listVo);
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;

}
