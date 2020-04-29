package com.chd.hrp.med.service.info.basic;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MedTypeDictService {
	
	
	/**
	 * @Description 
	 * 添加药品分类变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String saveMedTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集 药品分类变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 树形结构（含变更号）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryMedTypeDictByTree(Map<String,Object> entityMap) throws DataAccessException;
}
