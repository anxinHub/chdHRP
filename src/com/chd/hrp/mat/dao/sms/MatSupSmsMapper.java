package com.chd.hrp.mat.dao.sms;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatSupSmsMapper extends SqlMapper {
 
	public <T> T queryOrderByCode(Map<String, Object> entityMap) throws DataAccessException;
}
