
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.prm.entity.PrmFormulaStack;
/**
 * 
 * @Description:
 * 9907 绩效计算公式指标栈
 * @Table:
 * PRM_FORMULA_STACK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmFormulaStackService {

	/**
	 * @Description 
	 * 添加9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmFormulaStack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmFormulaStack(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmFormulaStack(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmFormulaStack(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmFormulaStack queryPrmFormulaStackByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String queryPrmFormulaStackByScore(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryPrmHosFormulaStackByScore(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryPrmEmpFormulaStackByScore(Map<String,Object> entityMap) throws DataAccessException;
	
}
