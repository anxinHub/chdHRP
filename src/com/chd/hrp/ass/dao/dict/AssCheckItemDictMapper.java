
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

import com.chd.hrp.ass.entity.dict.AssCheckItemDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
/**
 * 
 * @Description:
 * 050109 检查项目字典
 * @Table:
 * ASS_CHECK_ITEM_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssCheckItemDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssCheckItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssCheckItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssCheckItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return AssCheckItemDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssCheckItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssCheckItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssCheckItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050109 检查项目字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssCheckItemDict> queryAssCheckItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050109 检查项目字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssCheckItemDict> queryAssCheckItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return AssCheckItemDict
	 * @throws DataAccessException
	*/
	public AssCheckItemDict queryAssCheckItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050109 检查项目字典<BR> 
	 * @param  entityMap
	 * @return AssCheckItemDict
	 * @throws DataAccessException
	*/
	public AssCheckItemDict queryAssCheckItemDictByUniqueness(Map<String, Object> entityMap)throws DataAccessException;

	public AssCheckItemDict queryByName(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
}
