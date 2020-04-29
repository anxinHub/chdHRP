/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.dura.init;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description: 耐用品科室期初登记表
 * @Table: MAT_DURA_DEPT_REG
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatDuraInitDeptMapper extends SqlMapper{

	/**
	 * @Description 
	 * 获取自增序列
	 * @param  entityMap
	 * @return Long
	 * @throws DataAccessException
	*/
	public Long querySeq() throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int add(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatch(List<?> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int update(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> query(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 审核或消审<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAudit(List<Map<String, Object>> entityList)throws DataAccessException;

	/**
	 * @Description 
	 * 获取期初是否已审核<BR> 
	 * @param  entityList
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsState(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询耐用品材料信息用于导入 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryInvListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询耐用品科室信息用于导入
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDeptListForImport(Map<String,Object> entityMap) throws DataAccessException;
	
}
