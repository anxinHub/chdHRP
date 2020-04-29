package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;
/**
 * 护理晋级汇总审批表(N2)
 * @author Administrator
 *
 */
public interface HrPromotionSummaryN2Mapper  extends SqlMapper{
    /**
     * 删除护理晋级汇总审批表(N2)
     * @param entityList
     */
	void deletePromotionSummaryN2(List<HrPromotionSummaryN2> entityList);
	/**
	 * 护士长审核
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	void auditHnurseAuditN2(List<HrPromotionSummaryN2> listVo);
	/**
	 * 护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void reAuditHnurseAuditN2(Map<String, Object> entityMap);
	/**
	 * 科护士长审核
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	void auditDhnurseAuditN2(List<HrPromotionSummaryN2> listVo);
	/**
	 * 科护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void reAuditDhnurseAuditN2(Map<String, Object> entityMap);
	/**
	 * 晋级小组意见
	 * 
	 * @param hrPromotionSummaryN2
	 * @return
	 * @throws DataAccessException
	 */
	void auditPromotionAuditN2(HrPromotionSummaryN2 hrPromotionSummaryN2);
	/**
	 * 审核时候查询是否下级审核
	 * @param hrPromotionSummaryN2
	 * @return
	 */
	HrPromotionSummaryN2 queryPromotionSummaryN2ByCode(HrPromotionSummaryN2 hrPromotionSummaryN2);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPromotionN2ByPrint(Map<String, Object> entityMap);
	

}
