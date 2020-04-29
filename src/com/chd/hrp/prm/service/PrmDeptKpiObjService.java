/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmDeptKpiObj;

/**
 * 
 * @Description: 0302 科室绩效方案核算对象表
 * @Table: PRM_DEPT_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmDeptKpiObjService {

	/**
	 * @Description 添加0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addPrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addBatchPrmDeptKpiObj(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String updatePrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String updateBatchPrmDeptKpiObj(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 删除0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deletePrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量删除0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deleteBatchPrmDeptKpiObj(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryPrmDeptKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对象0302 科室绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public PrmDeptKpiObj queryPrmDeptKpiObjByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集0202 科室绩效 left join Dept <BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryPrmDeptKpiObjDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询 科室绩效 add by alfred 返回JSP页面医院coloum名称JSON
	 */
	public String queryPrmDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询 科室绩效add by alfred 返回controller 所有科室的信息 为保存是使用
	 */
	public List<PrmDeptKpiObj> queryPrmDeptForAdd(Map<String, Object> entityMap) throws DataAccessException;

}
