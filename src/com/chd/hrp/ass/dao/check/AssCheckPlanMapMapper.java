package com.chd.hrp.ass.dao.check;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssCheckPlanMapMapper extends SqlMapper{
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addGenerateStore(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateDept(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateStoreDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int addGenerateDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 盘点单树形展示
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryByTree(Map<String, Object> entityMap) throws DataAccessException;
	public Integer queryCheckS_s(Map<String, Object> map) throws DataAccessException;
	public Integer queryCheckS_g(Map<String, Object> map) throws DataAccessException;
	public Integer queryCheckS_o(Map<String, Object> map) throws DataAccessException;
	
	
	public Integer queryCheckD_s(Map<String, Object> map)throws DataAccessException;
	public Integer queryCheckD_g(Map<String, Object> map)throws DataAccessException;
	public Integer queryCheckD_o(Map<String, Object> map)throws DataAccessException;
	
	
	public List<Map<String, Object>> queryStores(Map<String, Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDepts(Map<String, Object> entityMap)throws DataAccessException;
	
	//倉庫
	public List<Map<String,Object>> queryCheckPlanStore(Map<String,Object> mapVo,RowBounds rowBounds) throws DataAccessException;
		
	//科室
	public List<Map<String,Object>> queryCheckPlanDept(Map<String,Object> mapVo,RowBounds rowBounds) throws DataAccessException;
		
}
