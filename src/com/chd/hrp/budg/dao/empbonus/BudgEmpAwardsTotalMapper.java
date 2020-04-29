/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.empbonus;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室职工奖金总表 
 * @Table:
 * BUDG_EMP_AWARDS_TOTAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgEmpAwardsTotalMapper extends SqlMapper{
	
	/**
	 * 查询所有奖金项目
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgAwardsItem(Map<String, Object> entityMap) throws DataAccessException ;
	/**
	 * 查询 科室基本信息(根据编码 匹配ID用)
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> map) throws DataAccessException ;

	/**
	 * 查询 所有职工信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEmpData(Map<String, Object> map)  throws DataAccessException;
	
	/**
	 * 查询所有职工类别信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgEmpType(Map<String, Object> map) throws DataAccessException;
	/**
	 * 根据主键查询数据是否已存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
	
}
