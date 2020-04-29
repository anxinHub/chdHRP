package com.chd.hrp.htcg.dao.collect;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgIntegratedQuery;

public interface HtcgIntegratedQueryMapper extends SqlMapper{
	
	    public List<HtcgIntegratedQuery> queryHtcgIntegratedQuery(Map<String, Object> entityMap) throws DataAccessException;
		public List<HtcgIntegratedQuery> queryHtcgIntegratedQuery(Map<String, Object> entityMap,RowBounds rowbounds) throws DataAccessException;
}
