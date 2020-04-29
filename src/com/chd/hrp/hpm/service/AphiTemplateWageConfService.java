package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/*
 * 工资与奖金项目配置
 * */
public interface AphiTemplateWageConfService {
	
	/**
	 * @Description 
	 * 添加主表数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addAphiTemplateWageConf(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 所有配置明细数据 <BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String queryAphiTemplateWageConfDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询工资项目 分页<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String queryWageItem(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询工资项目 by code<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public Map<String,Object> queryAphiTemplateWageConfByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询工资项目 by code<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public Map<String,Object> queryWage(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchWage(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
}
