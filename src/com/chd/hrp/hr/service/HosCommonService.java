package com.chd.hrp.hr.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HosCommonService {

	List<Map<String, Object>> queryHosUserPermByUserId(Map<String, Object> mapVo)throws DataAccessException;




}
