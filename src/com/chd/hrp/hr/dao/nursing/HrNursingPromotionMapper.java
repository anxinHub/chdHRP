package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrNursingPromotion;
import com.chd.hrp.hr.entity.nursing.HrPromotionLeave;
/**
 * 护理晋级申请
 * @author Administrator
 *
 */
public interface HrNursingPromotionMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrNursingPromotion> queryNursingPromotionById(Map<String, Object> entityMap);
    /**
     * 删除护理晋级申请
     * @param entityList
     */
	void deleteNursingPromotion(List<HrNursingPromotion> entityList);
	/**
	 * 审核护理晋级申请
	 * @param entityList
	 * @return
	 */
	String auditNursingPromotion(List<Map<String, Object>> entityList);
	/**
	 * 取消护理晋级申请
	 * @param entityList
	 * @return
	 */
	String reAuditNursingPromotion(List<Map<String, Object>> entityList);
	/**
	 * 提交护理晋级申请（单条）
	 * @param hrNursingPromotion
	 * @return
	 */
	void confirmNursingPromotion(HrNursingPromotion hrNursingPromotion);
	/**
	 * 提交护理晋级申请(批量)
	 * @param hrNursingPromotion
	 * @return
	 */
	void confirmNursingPromotionBatch(@Param(value = "list") List<HrNursingPromotion> list);
	/**
	 * 取消提交晋级申请
	 * @param hrNursingPromotion
	 * @return
	 */
	void reConfirmNursingPromotion(HrNursingPromotion hrNursingPromotion);
	/**
	 * 查询员工明细
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryHosEmpDetail(Map<String, Object> entityMap);
	/**
	 * 查询级别
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryLevel(Map<String, Object> entityMap);
	/**
	 * 查询三年资料
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttend(Map<String, Object> entityMap);
	/**
	 * 添加近三年资料
	 * @param entityMap
	 * @return
	 */
	int addaddNursingPromotion(List<HrPromotionLeave> entityMap);
	/**
	 * 删除近三年资料
	 * @param entityList
	 */
	void deletePromotionLeave(List<HrNursingPromotion> entityList);
	/**
	 * 提交护理晋级（单条）
	 * @param hrNursingPromotion
	 */
	void addPromotionEvaluate(HrNursingPromotion hrNursingPromotion);
	/**
	 * 提交护理晋级（批量）
	 * @param List<HrNursingPromotion>
	 */
	void addPromotionEvaluateBatch(@Param(value = "list") List<HrNursingPromotion> list);
	/**
	 * 修改近三年资料
	 * @param entityMap
	 * @return
	 */
	int UpdateAttend(List<HrPromotionLeave> entityMap);
	/**
	 * 护理晋级申请查询
	 * @param entityMap
	 * @return
	 */
	List<HrNursingPromotion> queryNursingPromotion(Map<String, Object> entityMap);
	/**
	 * 护理晋级申请查询带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrNursingPromotion> queryNursingPromotion(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 撤回提交申请（单条）
	 * @param hrNursingPromotion
	 */
	void reConfirmPromotionEvaluate(HrNursingPromotion hrNursingPromotion);
	/**
	 * 撤回提交申请(批量)
	 * @param List<HrNursingPromotion>
	 */
	void reConfirmPromotionEvaluateBatch(@Param(value = "list") List<HrNursingPromotion> list);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryNursingPromotionByPrint(Map<String, Object> entityMap);


}
