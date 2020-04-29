package com.chd.hrp.ass.service.dict.cert;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AssCertMainService  extends SqlService {
	public String updateToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String updateNotToExamine(List<Map<String, Object>> entityMap)throws DataAccessException;
}
                  