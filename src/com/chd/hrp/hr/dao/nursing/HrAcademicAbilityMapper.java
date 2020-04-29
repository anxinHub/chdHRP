
package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
/**
 * 学术能力
 * @author Administrator
 *
 */
public interface HrAcademicAbilityMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicAbility> queryAcademicAbilityById(Map<String, Object> entityMap);
    /**
     * 删除学术能力
     * @param entityMap
     */
	void deleteAcademicAbility(Map<String, Object> entityMap);
	 /**
     * 添加学术能力
     * @param entityList
     */
	void addStoreCondition(List<HrAcademicAbility> addlistVo);
	/**
	 * 批量删除学术能力
	 * @param addlistVo
	 */
	void deleteBatchStoreCondition(List<HrAcademicAbility> addlistVo);
	/**
	 * 提交学术能力到晋级汇总表(单条)
	 * @param hrAcademicAbility
	 */
	void addPromotionEvaluate(HrAcademicAbility hrAcademicAbility);
	/**
	 * 提交学术能力到晋级汇总表(批量)
	 * @param list
	 * @param map
	 */ 
	void addPromotionEvaluateBatch(@Param(value="list") List<HrAcademicAbility> list);
	/**
	 * 修改提交状态(单条)
	 * @param hrAcademicAbility
	 */
	void confirmAcademicAbility(HrAcademicAbility hrAcademicAbility);
	/**
	 * 修改提交状态(批量)
	 * @param list
	 * @param map
	 */
	void confirmAcademicAbilityBatch(@Param(value="list") List<HrAcademicAbility> list);
	/**
	 * 撤回学术能力
	 * @param hrAcademicAbility
	 */
	void reConfirmPromotionEvaluate(HrAcademicAbility hrAcademicAbility);
	/**
	 * 撤回学术能力(批量)
	 * @param hrAcademicAbility
	 */
	void reConfirmPromotionEvaluateBatch(@Param(value="list") List<HrAcademicAbility> list);
	/**
	 * 查询是否存在
	 * @param alllistVo
	 * @return
	 */
	List<Map<String, Object>> queryAcademicAbilityByEmpId(List<HrAcademicAbility> alllistVo);
	/**
	 * 导入查询人员
	 * @param saveMap
	 * @return
	 */
	Map<String, Object> queryEmp(Map<String, Object> saveMap);
	

}
