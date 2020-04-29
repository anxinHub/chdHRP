package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;

/**
 *护理晋级汇总审批表(N2)
 * 
 * @author Administrator
 *
 */
public interface HrPromotionSummaryN2Service {
    /**
     * 查询护理晋级汇总审批表(N2)
     * @param page
     * @return
     */
	String queryPromotionSummaryN2(Map<String, Object> page)throws DataAccessException;
    /**
     * 护士长审核
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String auditHnurseAuditN2(List<HrPromotionSummaryN2> listVo)throws DataAccessException;
    /**
     * 护士长销审
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String reAuditHnurseAuditN2(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 科护士长审核
     * @param saveList
     * @return
     * @throws DataAccessException
     */
	String auditDhnurseAuditN2(List<HrPromotionSummaryN2> saveList)throws DataAccessException;
    /**
     * 科护士长销审
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String reAuditDhnurseAuditN2(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 晋级小组意见
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String auditPromotionAuditN2(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 审核时候查询是否下级审核
	 * @param hrPromotionSummaryN2
	 * @return
	 */
	HrPromotionSummaryN2 queryPromotionSummaryN2ByCode(HrPromotionSummaryN2 hrPromotionSummaryN2);
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    List<Map<String,Object>> queryPromotionN2ByPrint(Map<String, Object> page)throws DataAccessException;

}
