package com.chd.hrp.hr.service.sysstruc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabType;
import com.chd.hrp.hr.entity.sysstruc.HrTabType;

public interface HrFiiedTabTypeService extends SqlService{

	String deletehrFiiedTabType(List<HrFiiedTabType> listVo);
	/**
	 * 导入数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String importDate(Map<String, Object> entityMap) throws DataAccessException;

}
