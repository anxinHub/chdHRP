
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.dict;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.dict.AssAcceptItem;
/**
 * 
 * @Description:
 * 050107 验收项目字典
 * @Table:
 * ASS_ACCEPT_ITEM_
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssAcceptItemMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssAcceptItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssAcceptItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssAcceptItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return AssAcceptItem
	 * @throws DataAccessException
	*/
	public int updateBatchAssAcceptItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssAcceptItem(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssAcceptItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptItem> queryAssAcceptItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssAcceptItem> queryAssAcceptItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return AssAcceptItem
	 * @throws DataAccessException
	*/
	public AssAcceptItem queryAssAcceptItemByCode(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssAcceptItem> queryByAcceptId(Map<String, Object> entityMap)throws DataAccessException;

	public void deleteAssAcceptItemByAssAcceptDetail(AssAcceptDetail entityMap)throws DataAccessException;
	
	
	
	
}
