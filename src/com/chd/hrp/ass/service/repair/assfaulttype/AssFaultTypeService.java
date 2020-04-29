package com.chd.hrp.ass.service.repair.assfaulttype;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AssFaultTypeService extends SqlService{

	String queryAssFaultTypeTree(Map<String, Object> mapVo) throws DataAccessException;

	String queryAssFaultType(Map<String, Object> mapVo) throws DataAccessException;

	String deleteAssFaultTypeBatch(List<Map> listVo) throws DataAccessException;

	public Map<String, Object> queryAssFaultTypeByCode(Map<String, Object> mapVo) throws DataAccessException;

}
