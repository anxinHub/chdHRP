
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.dict;
import java.util.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

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
 


public interface AssDepreMethodDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssDepreMethodDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return AssDepreMethodDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssDepreMethodDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssDepreMethodDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssDepreMethodDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050106 资产折旧方法字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreMethodDict> queryAssDepreMethodDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050106 资产折旧方法字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssDepreMethodDict> queryAssDepreMethodDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050106 资产折旧方法字典<BR> 
	 * @param  entityMap
	 * @return AssDepreMethodDict
	 * @throws DataAccessException
	*/
	public AssDepreMethodDict queryAssDepreMethodDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
