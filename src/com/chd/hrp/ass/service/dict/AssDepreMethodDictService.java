
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.service.dict;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.ass.entity.dict.AssDepreMethodDict;
/**
 * 
 * @Description:
 * 050106 资产折旧方法字典
 * @Table:
 * ASS_DEPRE_METHOD_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssDepreMethodDictService {

	/**
	 * @Description 
	 * 添加050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssDepreMethodDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssDepreMethodDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssDepreMethodDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssDepreMethodDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AssDepreMethodDict queryAssDepreMethodDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
