package com.chd.hrp.hr.service;

import java.util.List;
import java.util.Map;
import java.util.stream.BaseStream;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface DeleteService {
	/**
	 * 删除
	 * @param paramVo
	 * @param servletPath 
	 * @return
	 * @throws DataAccessException
	 */
	String deleteBaseInfo(String paramVo, String servletPath)throws DataAccessException;

}
