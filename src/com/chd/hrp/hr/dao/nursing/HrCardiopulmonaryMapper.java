package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrCardiopulmonary;
/**
 * CPR考核
 * @author Administrator
 *
 */
public interface HrCardiopulmonaryMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrCardiopulmonary> queryCardiopulmonaryById(Map<String, Object> entityMap);
    /**
     * 删除CPR考核
     * @param entityList
     */
	void deleteCardiopulmonary(List<HrCardiopulmonary> entityList);
	/**
	 * 删除CPR
	 * @param entityMap
	 */
	void deleteCardiopul(Map<String, Object> entityMap);
	/**
	 * 提交CPR到晋级汇总表（单条）
	 * @param hrCardiopulmonary
	 */
	void addPromotionEvaluate(HrCardiopulmonary hrCardiopulmonary);
	/**
	 * 提交CPR到晋级汇总表(批量)
	 * @param List<HrCardiopulmonary>
	 */
	void addPromotionEvaluateBatch(@Param(value = "list") List<HrCardiopulmonary> list);
	/**
	 * 修改状态为提交（单条）
	 * @param hrCardiopulmonary
	 */
	void confirmCardiopulmonary(HrCardiopulmonary hrCardiopulmonary);
	/**
	 * 修改状态为提交（批量）
	 * @param List<HrCardiopulmonary>
	 */
	void confirmCardiopulmonaryBatch(@Param(value = "list") List<HrCardiopulmonary> list);
	/**
	 * 撤回CPR
	 * @param hrCardiopulmonary
	 */
	void reConfirmPromotionEvaluate(HrCardiopulmonary hrCardiopulmonary);
	/**
	 * 撤回CPR(批量)
	 * @param List<HrCardiopulmonary>
	 */
	void reConfirmPromotionEvaluateBatch(@Param(value = "list") List<HrCardiopulmonary> list);
	/**
	 * 查询是否重复
	 * @param alllistVo
	 * @return
	 */
	List<Map<String, Object>> queryCardiopulmonaryByEmpId(List<HrCardiopulmonary> alllistVo);

}
