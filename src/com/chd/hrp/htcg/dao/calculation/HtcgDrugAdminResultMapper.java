package com.chd.hrp.htcg.dao.calculation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgDrugAdminResult;

public interface HtcgDrugAdminResultMapper extends SqlMapper{

	public List<HtcgDrugAdminResult> queryHtcgDrugAdminResult(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrugAdminResult> queryHtcgDrugAdminResult(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;

}
