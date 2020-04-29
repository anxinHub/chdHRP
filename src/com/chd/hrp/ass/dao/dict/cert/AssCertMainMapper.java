package com.chd.hrp.ass.dao.dict.cert;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssCertMainMapper extends SqlMapper{
	public int updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public int updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
}
