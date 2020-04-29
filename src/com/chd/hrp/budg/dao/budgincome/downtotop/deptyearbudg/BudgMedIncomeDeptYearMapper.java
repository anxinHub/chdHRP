/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.downtotop.deptyearbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室年度医疗收入预算
 * @Table:
 * BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedIncomeDeptYearMapper extends SqlMapper{

	public Map<String, Object> queryLastYearIncome(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询数据状态
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public String queryState(Map<String, Object> item) throws DataAccessException;
	/**
	 * 提交  撤回  取消审核   更新数据
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void issuedOrRetractUpdate(List<Map<String, Object>> entityList) throws DataAccessException; 
	
	/**
	 * 科室年度医疗收入预算  更新 计算值、预算值
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 审核  更新数据
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void passOrDisPassUpdate(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 计算数据查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCollectData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据科目 查询 其同级独立核算科目的预算之和
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public double querySumValue(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 分解计算 平均分摊 根据 参数 查询  与其上级科目的的一级所有子科目  数量 
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCount(Map<String, Object> item) throws DataAccessException;
	/**
	 * 根据 参数 汇总其所有末级级科目预算
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public double querySumSubjValue(Map<String, Object> item) throws DataAccessException;
	/**
	 * 更新 科室年度医疗收入预算  计算值、预算值
	 * @param resolveList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateValue(List<Map<String, Object>> resolveList) throws DataAccessException;
	/**
	 * 更新 科室医疗收入科目分解比例维护表 分解比例
	 * @param resolveList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateResolveRate(List<Map<String, Object>> resolveList) throws DataAccessException;
	/**
	 *  分解计算 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException; 
	
	
}
