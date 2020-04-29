package com.chd.hrp.ass.dao.dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.dict.AssInspectionItem;

public interface AssInspectionItemMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssInsItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssInsItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssInsItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return AssAcceptItem
	 * @throws DataAccessException
	*/
	public int updateBatchAssInsItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssInsItem(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssInsItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInspectionItem> queryAssInsItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050107 验收项目字典<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssInspectionItem> queryAssInsItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050107 验收项目字典<BR> 
	 * @param  entityMap
	 * @return AssAcceptItem
	 * @throws DataAccessException
	*/
	public AssInspectionItem queryAssInsItemByCode(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssInspectionItem> queryByInsId(Map<String, Object> entityMap)throws DataAccessException;

	public void deleteAssInsItemByAssInsDetail(AssAcceptDetail entityMap)throws DataAccessException;
}
