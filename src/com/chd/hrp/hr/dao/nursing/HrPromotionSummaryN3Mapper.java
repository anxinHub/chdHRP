package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;
/**
 * 护理晋级汇总审批表(N3)
 * @author Administrator
 *
 */
public interface HrPromotionSummaryN3Mapper  extends SqlMapper{
    /**
     * 删除护理晋级汇总审批表(N3)
     * @param entityList
     */
	void deletePromotionSummaryN3(List<HrPromotionSummaryN3> entityList);
	/**
	 * 护士长审核
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	void auditHnurseAuditN3(List<HrPromotionSummaryN3> listVo);
	/**
	 * 护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void reAuditHnurseAuditN3(Map<String, Object> entityMap);
	/**
	 * 科护士长审核
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	void auditDhnurseAuditN3(List<HrPromotionSummaryN3> listVo);
	/**
	 * 科护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	void reAuditDhnurseAuditN3(Map<String, Object> entityMap);
	/**
	 * 晋级小组意见
	 * 
	 * @param hrPromotionSummaryN3
	 * @return
	 * @throws DataAccessException
	 */
	void auditPromotionAuditN3(HrPromotionSummaryN3 hrPromotionSummaryN3);
	/**
	 * 审核时候查询是否下级审核
	 * @param hrPromotionSummaryN3
	 * @return
	 */
	HrPromotionSummaryN3 queryPromotionSummaryN3ByCode(HrPromotionSummaryN3 hrPromotionSummaryN3);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPromotionN3ByPrint(Map<String, Object> entityMap);
	

}
