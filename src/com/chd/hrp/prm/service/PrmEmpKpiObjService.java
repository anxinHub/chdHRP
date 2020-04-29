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

import com.chd.hrp.prm.entity.PrmEmpKpiObj;

/**
 * 
 * @Description: 0402 职工绩效方案核算对象表
 * @Table: PRM_EMP_KPI_OBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmEmpKpiObjService {

	/**
	 * @Description 添加0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addPrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量添加0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String addBatchPrmEmpKpiObj(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 更新0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String updatePrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量更新0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public String updateBatchPrmEmpKpiObj(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 删除0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deletePrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 批量删除0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String deleteBatchPrmEmpKpiObj(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * @Description 查询结果集0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryPrmEmpKpiObj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询对象0402 职工绩效方案核算对象表<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public PrmEmpKpiObj queryPrmEmpKpiObjByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询 职工绩效 add by alfred 返回JSP页面医院coloum名称JSON
	 */
	public String queryPrmEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询 职工绩效 add by alfred 返回controller 所有职工的信息 为保存是使用
	 */
	public List<PrmEmpKpiObj> queryPrmEmpForAdd(Map<String, Object> entityMap) throws DataAccessException;

}
