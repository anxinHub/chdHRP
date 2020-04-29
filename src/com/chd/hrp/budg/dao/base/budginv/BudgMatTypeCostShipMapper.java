/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budginv;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 物资分类与预算支出科目对应关系
 * @Table:
 * BUDG_MAT_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMatTypeCostShipMapper extends SqlMapper{
	
	//获取资产分类下拉框
	public List<Map<String,Object>> queryMatTypes(Map<String, Object> mapVo) throws DataAccessException;
	
	//获取资产分类下拉框(过滤)
	public List<Map<String,Object>> queryMatTypesFilter(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>>  queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	public Map<String, Object> queryBudgTypeDictByCode(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryPrevYearCostData(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryPrevYearIncomeData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量添加物资类别与收入科目的对应关系
	 * @param incomeList
	 * @throws DataAccessException
	 */
	public void addIncomeBatch(List<Map<String, Object>> incomeList) throws DataAccessException;
	
	/**
	 * 修改物资类别与收入科目的对应关系
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateIncome(Map<String, Object> entityMap)  throws DataAccessException;
	
	/**
	 * 批量删除物资类别与收入科目的对应关系
	 * @param incomeList
	 * @throws DataAccessException
	 */
	public int deleteIncomeBatch(List<Map<String, Object>> entityList) throws DataAccessException;

	public String queryCostExists(Map<String, Object> mapVo) throws DataAccessException;
	public String queryIncomeExists(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 添加物资类别与收入科目的对应关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addIncome(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除 旧的对应关系
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteIncomeOld(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询物资分类（导入使用）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatTypeList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询支出科目（导入使用）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCostSubjList(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询收入科目（导入使用）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIncomeSubjList(Map<String, Object> entityMap) throws DataAccessException;

}
