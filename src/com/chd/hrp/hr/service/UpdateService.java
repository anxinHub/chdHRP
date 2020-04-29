package com.chd.hrp.hr.service;

import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface UpdateService {
   
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String updateBaseInfo(Map<String, Object> mapVo)throws DataAccessException;

}
