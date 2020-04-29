/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.toptodown.hosyearinbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeHosYearMapper extends SqlMapper{
	
	/**
	 * 上年收入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryLastYearIncome(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据是否存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException ;
	
	/**
	 * 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 查询计算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo) throws DataAccessException ;
	
	/**
	 * 根据科目 查询 其同级独立核算项目的预算之和
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Double querySumValue(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 参数 查询  与其上级科目的的一级所有子科目  数量 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCount(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 科目 汇总其所有下级科目预算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public double querySumSubjValue(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 更新 医院年度医疗收入预算  计算值、预算值
	 * @param resolveList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateValue(List<Map<String, Object>> resolveList) throws DataAccessException;
	
	/**
	 * 更新 医院医疗收入科目分解比例维护表 分解比例
	 * @param resolveList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateResolveRate(List<Map<String, Object>> resolveList) throws DataAccessException;
	/**
	 * 科室各级科目预算值汇总
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> sumDeptBudgValue(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据科室汇总数据  更新对应医院年预算值
	 * @param sumList
	 */
	public void updateHosBudgValue(List<Map<String, Object>> sumList);
	
	/**
	 * 医院年度医疗收入预算  更新 计算值、预算值
	 * @param listVo
	 * @throws DataAccessException
	 */
	public int updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据科目 查询 其同级独立核算科目 及同级 汇总科目  预算数据
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySubjList(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 根据科目 查询 其父级科目信息
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryParentSubj(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 查询  计算 所传科目 预算 所需 数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryCountData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据所传科目 查询 所有下一级科目信息 及 预算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryChildSubj(Map<String, Object> mapVo) throws DataAccessException;

	public int addBatchResolveRate(List<Map<String, Object>> entityList)throws DataAccessException;

	public int deleteBatchResolveRate(List<Map<String, Object>> entityList);
	
}
