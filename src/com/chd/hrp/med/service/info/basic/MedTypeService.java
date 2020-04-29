package com.chd.hrp.med.service.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.MedType;
import com.chd.hrp.med.entity.MedType;

/**
 * 
 * @Description:药品分类
 * @Copyright: Copyright (c) 2016-11-28 上午10:15:27
 * @Author: xuyongwei
 * @Version: 6.0
 */
public interface MedTypeService {

	/**
	 * @Description 查medType菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMedTypeByTree(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象 08102 药品分类字典BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return MedType
	 * @throws DataAccessException
	*/
	public MedType queryMedTypeById(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String saveMedType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集 08102 药品分类字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取 08102 药品分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedType
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedTypeByCodeName(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impMedType(Map<String,Object> entityMap,Map<String,Object> utilMap)throws DataAccessException;
	
	public List<MedType> queryMedTypeList(Map<String,Object> entityMap)throws DataAccessException;
}
