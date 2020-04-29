package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN1;

/**
 *护理晋级汇总审批表(N1)
 * 
 * @author Administrator
 *
 */
public interface HrPromotionSummaryN1Service {
    /**
     * 查询护理晋级汇总审批表(N1)
     * @param page
     * @return
     */
	String queryPromotionSummaryN1(Map<String, Object> page)throws DataAccessException;
    /**
     * 护士长审核
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String auditHnurseAuditN1(List<HrPromotionSummaryN1> listVo)throws DataAccessException;
    /**
     * 护士长销审
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String reAuditHnurseAuditN1(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 科护士长审核
     * @param saveList
     * @return
     * @throws DataAccessException
     */
	String auditDhnurseAuditN1(List<HrPromotionSummaryN1> saveList)throws DataAccessException;
    /**
     * 科护士长销审
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String reAuditDhnurseAuditN1(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 晋级小组意见
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String auditPromotionAuditN1(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 审核时候查询是否下级审核
	 * @param hrPromotionSummaryN1
	 * @return
	 */
	HrPromotionSummaryN1 queryPromotionSummaryN1ByCode(HrPromotionSummaryN1 hrPromotionSummaryN1);
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    List<Map<String,Object>> queryPromotionN1ByPrint(Map<String, Object> page)throws DataAccessException;

}
