
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

import com.chd.hrp.ass.entity.dict.AssAcceptItemDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
/**
 * 
 * @Description:
 * 050107 验收项目字典
 * @Table:
 * ASS_ACCEPT_ITEM_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssAcceptItemDictMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssAcceptItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssAcceptItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssAcceptItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return AssAcceptItemDict
	 * @throws DataAccessException
	*/
	public int updateBatchAssAcceptItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssAcceptItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssAcceptItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptItemDict> queryAssAcceptItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptItemDict> queryAssAcceptItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return AssAcceptItemDict
	 * @throws DataAccessException
	*/
	public AssAcceptItemDict queryAssAcceptItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 获取050107 验收项目字典 是否唯一<BR> 
	 * @param  entityMap
	 * @return AssAcceptItemDict
	 * @throws DataAccessException
	*/
	public AssAcceptItemDict queryAcceptItemDictByUniqueness(Map<String, Object> entityMap)throws DataAccessException;

	public AssAcceptItemDict queryByName(Map<String, Object> entityMap)throws DataAccessException;

	
}
