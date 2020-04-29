/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.deptindex;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室基本指标数据维护
 * @Table:
 * BUDG_DEPT_BASIC_INDEX_DATA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptBasicIndexDataMapper extends SqlMapper{

	/**
	 * 添加页面 科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryBudgDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据主键查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 通过 科室编码 查询 预算科室ID
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 科室基本指标生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptIndexData(Map<String, Object> mapVo) throws DataAccessException;


	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
}
