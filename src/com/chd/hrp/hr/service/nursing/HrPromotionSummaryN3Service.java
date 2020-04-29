package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;

/**
 *护理晋级汇总审批表(N3)
 * 
 * @author Administrator
 *
 */
public interface HrPromotionSummaryN3Service {
    /**
     * 查询护理晋级汇总审批表(N3)
     * @param page
     * @return
     */
	String queryPromotionSummaryN3(Map<String, Object> page)throws DataAccessException;
    /**
     * 护士长审核
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String auditHnurseAuditN3(List<HrPromotionSummaryN3> listVo)throws DataAccessException;
    /**
     * 护士长销审
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String reAuditHnurseAuditN3(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 科护士长审核
     * @param saveList
     * @return
     * @throws DataAccessException
     */
	String auditDhnurseAuditN3(List<HrPromotionSummaryN3> saveList)throws DataAccessException;
    /**
     * 科护士长销审
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String reAuditDhnurseAuditN3(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 晋级小组意见
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String auditPromotionAuditN3(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 审核时候查询是否下级审核
	 * @param hrPromotionSummaryN3
	 * @return
	 */
	HrPromotionSummaryN3 queryPromotionSummaryN3ByCode(HrPromotionSummaryN3 hrPromotionSummaryN3);
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
    List<Map<String,Object>> queryPromotionN3ByPrint(Map<String, Object> page)throws DataAccessException;

}
