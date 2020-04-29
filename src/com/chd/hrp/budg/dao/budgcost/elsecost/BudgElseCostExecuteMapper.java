/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.elsecost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 其他支出预算执行 
 * @Description:
 * BUDG_ELSE_COST_EXECUTE
 * @Table:
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgElseCostExecuteMapper extends SqlMapper{
	
	/**
	 * 判断支出预算科目是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 支出预算科目下拉框（添加时用）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgCostSubj(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 删除  要采集  年月的 执行数据 (数据采集专用)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMonthData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 其他支出执行数据  采集 （财务取数）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int collectData(Map<String, Object> entityMap) throws DataAccessException ;
	
	
}
