package com.chd.hrp.mat.service.fim;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.MatFimType;


public interface MatFimTypeServer  {

	String queryMatBillMain(Map<String, Object> page);

	String add(Map<String, Object> mapVo);

	public <T>T queryByCode(Map<String, Object> entityMap) throws DataAccessException;


	MatFimType updatepage(Map<String, Object> mapVo);

	String update(Map<String, Object> mapVo);

	String deleteBatch(List<Map<String, Object>> listVo);



}
