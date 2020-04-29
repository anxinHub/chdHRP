
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmEmpScheme;
/**
 * 
 * @Description:
 * 0403 职工绩效方案表
 * @Table:
 * PRM_EMP_SCHEME
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpSchemeService {

	/**
	 * @Description 
	 * 添加0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmEmpScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmEmpScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmEmpScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmEmpScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmEmpScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpScheme(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpSchemeLeftName(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询对象0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpScheme queryPrmEmpSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<PrmEmpScheme> queryPrmEmpSchemeBySuperKpiCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public PrmEmpScheme queryPrmEmpSchemeMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description  按照生成条件覆盖生成或者增量生成
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String createPrmEmpScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description  引入
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	
	public String saveIntroduceEmpScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询对象0201 医院绩效考核指标表 查出结果 存储树形结构<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiTree(Map<String, Object> entityMap) throws DataAccessException ;
	
}
