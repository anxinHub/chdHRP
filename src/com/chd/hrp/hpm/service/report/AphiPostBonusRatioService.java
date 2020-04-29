package com.chd.hrp.hpm.service.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiPostBonusRatioService {
public String queryPostBonusRatioByCode(Map<String, Object> mapVo) throws DataAccessException;
}
