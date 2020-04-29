package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrPeerEvaluation;
/**
 * 同行评价
 * @author Administrator
 *
 */
public interface HrPeerEvaluationMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrPeerEvaluation> queryPeerEvaluationById(Map<String, Object> entityMap);
    /**
     * 删除同行评价
     * @param entityList
     */
	void deletePeerEvaluation(List<HrPeerEvaluation> entityList);
	/**
	 * 删除全部同行评价
	 * @param entityMap
	 */
	void deleteHrPeer(Map<String, Object> entityMap);
	/**
	 * 提交同行评价到晋级汇总表
	 * @param hrPeerEvaluation
	 */
	void addPromotionEvaluate(HrPeerEvaluation hrPeerEvaluation);
	/**
	 * 修改状态为提交
	 * @param hrPeerEvaluation
	 */
	void confirmPeerEvaluation(HrPeerEvaluation hrPeerEvaluation);
	/**
	 * 撤回同行评价
	 * @param hrPeerEvaluation
	 */
	void reConfirmPromotionEvaluate(HrPeerEvaluation hrPeerEvaluation);
	/**
	 * 撤回同行评价（批量）
	 * @param List<HrPeerEvaluation>
	 */
	void reConfirmPromotionEvaluateBatch(@Param(value="list") List<HrPeerEvaluation> list);
	/**
	 * 修改状态为新建
	 * @param hrPeerEvaluation
	 */
	void reConfirmPeerEvaluation(HrPeerEvaluation hrPeerEvaluation);
	/**
	 * 修改状态为新建(批量)
	 * @param List<HrPeerEvaluation>
	 */
	void reConfirmPeerEvaluationBatch(@Param(value="list") List<HrPeerEvaluation> list);
	
	/**
	 * 查询是否重复
	 * @param alllistVo
	 * @return
	 */
	List<Map<String, Object>> queryPeerEvaluationByEmpId(List<HrPeerEvaluation> alllistVo);
	
	/**
	 * 查询指定集合中指定状态的数量
	 * @param map
	 * @param list
	 * @return
	 * @author yangyunfei
	 */
	int queryStateCount(@Param(value="map") Map<String, Object> map, 
			@Param(value="list") List<HrPeerEvaluation> list);
	
	/**
	 * 批量提交
	 * @param map
	 * @param list
	 * @author yangyunfei
	 */
	void confirmPeerEvaluationBatch(@Param(value="map") Map<String, Object> map, 
			@Param(value="list") List<HrPeerEvaluation> list);

}
