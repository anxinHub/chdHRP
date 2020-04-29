package com.chd.hrp.budg.dao.project.begin;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgProjBegainMark;
import com.chd.hrp.budg.entity.BudgProjDetailYear;

public interface BudgProjDetailYearMapper extends SqlMapper {

	public int queryBudgProjDetailYear(List<Map<String, Object>> listVo) throws DataAccessException;
}
