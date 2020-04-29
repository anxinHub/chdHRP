package com.chd.hrp.med.service.info.fim;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedFimType;


public interface MedFimTypeService  {

	String queryMedBillMain(Map<String, Object> page);

	String add(Map<String, Object> mapVo);

	public <T>T queryByCode(Map<String, Object> entityMap) throws DataAccessException;


	MedFimType updatepage(Map<String, Object> mapVo);

	String update(Map<String, Object> mapVo);

	String deleteBatch(List<Map<String, Object>> listVo);



}
