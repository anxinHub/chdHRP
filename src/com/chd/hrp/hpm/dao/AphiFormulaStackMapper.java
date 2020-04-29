
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiFormulaStack;

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
 


public interface AphiFormulaStackMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmFormulaStack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return PrmFormulaStack
	 * @throws DataAccessException
	*/
	public int updateBatchPrmFormulaStack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmFormulaStack(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmFormulaStack(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9907 绩效计算公式指标栈<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiFormulaStack> queryPrmFormulaStack(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9907 绩效计算公式指标栈<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiFormulaStack> queryPrmFormulaStack(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取9907 绩效计算公式指标栈<BR> 
	 * @param  entityMap
	 * @return PrmFormulaStack
	 * @throws DataAccessException
	*/
	public AphiFormulaStack queryPrmFormulaStackByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<AphiFormulaStack> queryPrmFormulaStackByScore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AphiFormulaStack> queryPrmFormulaStackByScore(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AphiFormulaStack> queryPrmHosFormulaStackByScore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AphiFormulaStack> queryPrmHosFormulaStackByScore(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AphiFormulaStack> queryPrmEmpFormulaStackByScore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AphiFormulaStack> queryPrmEmpFormulaStackByScore(Map<String,Object> entityMap) throws DataAccessException;
	
}
