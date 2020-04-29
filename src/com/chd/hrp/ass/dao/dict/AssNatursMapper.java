
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

import com.chd.hrp.ass.entity.dict.AssNaturs;
import com.chd.hrp.sys.entity.StoreDict;
/**
 * 
 * @Description:
 * 050103 资产性质
 * @Table:
 * ASS_NATURS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssNatursMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssNaturs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssNaturs(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssNaturs(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050103 资产性质<BR> 
	 * @param  entityMap
	 * @return AssNaturs
	 * @throws DataAccessException
	*/
	public int updateBatchAssNaturs(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssNaturs(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050103 资产性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssNaturs(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050103 资产性质<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssNaturs> queryAssNaturs(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050103 资产性质<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssNaturs> queryAssNaturs(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050103 资产性质<BR> 
	 * @param  entityMap
	 * @return AssNaturs
	 * @throws DataAccessException
	*/
	public AssNaturs queryAssNatursByCode(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryHosStoreDict(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public void updateAssNatursStore(List<Map<String, Object>> entityMap)throws DataAccessException;

	public void deleteAssNatursStore(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryHosStoreDictByNaturs(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryHosStoreDictAll(Map<String, Object> entityMap, RowBounds rowBounds);
	
	
	
	
}
