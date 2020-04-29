package com.chd.hrp.budg.dao.common;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface BudgNoRulesMapper extends SqlMapper{

	public Map<String, Object> queryRule(Map<String, Object> map) throws DataAccessException;

}
