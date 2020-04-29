/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgdrug;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 药品分类与预算支出科目对应关系
 * @Table:
 * BUDG_DRUG_TYPE_COST_SHIP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDrugTypeCostShipMapper extends SqlMapper{
	
	/**
	 * 药品类别 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedTypes(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 药品类别 下拉框(添加页面用)
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedTypesFilter(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算科目 下拉框(添加页面用)
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 继承上一年度数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPrevYearData(Map<String, Object> mapVo) throws DataAccessException;
	
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
	public void deleteIncomeBatch(List<Map<String, Object>> entityList);
	
	
}
