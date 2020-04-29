package com.chd.hrp.app.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface DgmMapper extends SqlMapper{
	
	public int addRepair(Map<String,Object> map) throws  DataAccessException;
	
	public int updateRepairPaidan(Map<String,Object> map) throws  DataAccessException;
	
	public int addWeChatMsg(Map<String,Object> map)throws  DataAccessException;
}
