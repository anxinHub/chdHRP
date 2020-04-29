package com.chd.hrp.htcg.dao.calculation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDeptPdrgsCost;

public interface HtcgDeptDrgsCostQueryMapper extends SqlMapper{ 

	public List<HtcgDeptPdrgsCost> queryHtcgDeptDrgsCostQuery(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDeptPdrgsCost> queryHtcgDeptDrgsCostQuery(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
