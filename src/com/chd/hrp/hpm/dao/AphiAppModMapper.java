package com.chd.hrp.hpm.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiAppMod;


public interface AphiAppModMapper  extends SqlMapper{
  
	/**
	 * 查询
	 * @return
	 */
	public AphiAppMod queryAppModByCode(Map<String,Object> AppModMap) throws DataAccessException;
}
