/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;


/**
 * 
 * @Description:
 * 预算公用  信息 （如：预算科室、预算指标、收入科目、支出科目等）
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgCommonInfoService {
	
	/**
	 * 预算科室 基本信息查询（ID、no、code、name）
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap) throws DataAccessException ;
	
	/**
	 * 查询所有工资项目信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWageItemList(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 查询 所有职工信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEmpData(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询所有职工类别信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgEmpType(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询 资金来源 信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySourceInfo(Map<String, Object> map)	throws DataAccessException;
	
	/**
	 * 查询 资产分类信息(根据名称 匹配编码)
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssType(Map<String, Object> map) throws DataAccessException;
	 
	/**
	 * 查询 预算物资分类信息(根据名称 匹配编码) BUDG_MAT_TYPE_SUBJ物资分类与预算科目对应关系表
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	 public Map<String, Object> queryBudgMatType(Map<String, Object> map) throws DataAccessException;

}
