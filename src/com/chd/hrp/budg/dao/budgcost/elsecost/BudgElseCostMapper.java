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
 * 其他支出预算
 * @Description:
 * 
 * @Table:
 * BUDG_ELSE_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface BudgElseCostMapper extends SqlMapper{
	
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
	 * 根据填写数据 查询上年支出
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setLastCost(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成 （根据上年执行数据生成）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int addBudgElseCost(Map<String, Object> mapVo) throws DataAccessException;
	

	/**
	 * 根据年月查询预算值、上月结转、执行预算值
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgElseCostByYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量修改:上月结转、下月结转
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchBudgElseCostValue(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/*
	 * 医院支出预算报表
	 */
	public List<Map<String, Object>> queryElseCostReport(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryElseCostReport(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
