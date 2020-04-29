package com.chd.hrp.ass.dao.tongJiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssSupplierMapper extends SqlMapper{    

	List<Map<String, Object>> queryAssSupplier(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;

	List<Map<String, Object>> queryAssSupplierPrint(
			Map<String, Object> entityMap) throws DataAccessException;


}
