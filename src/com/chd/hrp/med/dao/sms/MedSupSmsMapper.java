package com.chd.hrp.med.dao.sms;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedSupSmsMapper extends SqlMapper {
 
	public <T> T queryOrderByCode(Map<String, Object> entityMap) throws DataAccessException;
}
