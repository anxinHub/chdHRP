
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

import com.chd.hrp.ass.entity.dict.AssFileTypeDict;
/**
 * 
 * @Description:
 * 050105 文档类别
 * @Table:
 * ASS_FILE_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssFileTypeDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050105 文档类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssFileTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050105 文档类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssFileTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050105 文档类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssFileTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050105 文档类别<BR> 
	 * @param  entityMap
	 * @return AssFileTypeDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssFileTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050105 文档类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssFileTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050105 文档类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssFileTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050105 文档类别<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssFileTypeDict> queryAssFileTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050105 文档类别<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssFileTypeDict> queryAssFileTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050105 文档类别<BR> 
	 * @param  entityMap
	 * @return AssFileTypeDict
	 * @throws DataAccessException
	*/
	public AssFileTypeDict queryAssFileTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public AssFileTypeDict queryAssFileTypeDictByName(Map<String, Object> entityMap)throws DataAccessException;
	
	
	
	
}
