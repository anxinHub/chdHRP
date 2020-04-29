/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.dao.base; 
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.MatDeptReqOutRela;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_OUT_RESOURCE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatDeptReqOutRelaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询科室申请与出库调拨关系
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public Map<String,Object> queryMatDeptReqOutRela(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询科室申请与出库调拨关系列表
	 * @param  entityMap
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatDeptReqOutRelaList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询科室申请与出库调拨关系列表
	 * @param  entityList
	 * @return Date
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatDeptReqOutRelaList(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 插入入库科室申请与出库调拨关系
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int addMatDeptReqOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 修改入库科室申请与出库调拨关系
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateMatDeptReqOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 删除入库科室申请与出库调拨关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatDeptReqOutRela(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 批量删除入库科室申请与出库调拨关系
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMatDeptReqOutRelaBatch(List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * @Description 
	 * 批量判断单据是否可删除、修改
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String existsMatDeptReqOutRelaStateBatch(List<Map<String,Object>> entityList) throws DataAccessException;
}
