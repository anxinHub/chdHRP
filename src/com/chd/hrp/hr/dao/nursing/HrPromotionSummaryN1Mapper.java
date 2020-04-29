package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN1;
/**
 * 护理晋级汇总审批表(N1)
 * @author Administrator
 *
 */
public interface HrPromotionSummaryN1Mapper  extends SqlMapper{
    /**
     * 删除护理晋级汇总审批表(N1)
     * @param entityList
     */
	void deletePromotionSummaryN1(List<HrPromotionSummaryN1> entityList);
	/**
	 * 护士长审核
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	void auditHnurseAuditN1(List<HrPromotionSummaryN1> listVo);
	/**
	 * 护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void reAuditHnurseAuditN1(Map<String, Object> entityMap);
	/**
	 * 科护士长审核
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	void auditDhnurseAuditN1(List<HrPromotionSummaryN1> listVo);
	/**
	 * 科护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void reAuditDhnurseAuditN1(Map<String, Object> entityMap);
	/**
	 * 晋级小组意见
	 * 
	 * @param hrPromotionSummaryN1
	 * @return
	 * @throws DataAccessException
	 */
	void auditPromotionAuditN1(HrPromotionSummaryN1 hrPromotionSummaryN1);
	/**
	 * 审核时候查询是否下级审核
	 * @param hrPromotionSummaryN1
	 * @return
	 */
	HrPromotionSummaryN1 queryPromotionSummaryN1ByCode(HrPromotionSummaryN1 hrPromotionSummaryN1);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPromotionN1ByPrint(Map<String, Object> entityMap);
	

}
