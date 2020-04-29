/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.deptindex;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgDeptBasicIndexDataService extends SqlService {
	
	/**
	 * 添加页面 科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDept(Map<String, Object> mapVo) throws DataAccessException;

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
	 */
	public Map<String, Object> queryDeptID(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 科室基本指标生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptIndexData(Map<String, Object> mapVo) throws DataAccessException;

	public String importBudgBasicDeptIndex(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgDeptBasicIndexData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException ;





}
