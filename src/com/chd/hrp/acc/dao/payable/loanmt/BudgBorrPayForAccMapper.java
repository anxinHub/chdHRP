package com.chd.hrp.acc.dao.payable.loanmt;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgBorrReturn;

public interface BudgBorrPayForAccMapper extends SqlMapper {

	public List<Map<String,Object>> queryBudgBorrPayForAcc(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryBudgBorrPayForAcc(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
