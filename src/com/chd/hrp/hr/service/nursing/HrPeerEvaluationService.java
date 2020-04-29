package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrPeerEvaluation;
import com.chd.hrp.hr.entity.nursing.HrPeerEvaluation;

/**
 * 同行评价
 * @author Administrator
 *
 */
public interface HrPeerEvaluationService {
	/**
	 * 添加同行评价
	 * 
	 * @param mapVo
	 * @return
	 */
	String addPeerEvaluation(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除同行评价
     * @param listVo
     */
	String deletePeerEvaluation(List<HrPeerEvaluation> listVo)throws DataAccessException;
    /**
     * 查询同行评价
     * @param page
     * @return
     */
	String queryPeerEvaluation(Map<String, Object> page)throws DataAccessException;
	/**
     * 提交
     * @param hrPeerEvaluation
     * @return
     */
    String confirmPeerEvaluation(HrPeerEvaluation hrPeerEvaluation) throws DataAccessException ;
    /**
	 * 批量提交
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 * @author yangyunfei
	 */
	String confirmPeerEvaluation(List<HrPeerEvaluation> listVo) throws DataAccessException;
    /**
     * 撤回（单条）
     * @param hrPeerEvaluation
     * @return
     */
	String reConfirmPeerEvaluation(HrPeerEvaluation hrPeerEvaluation) throws DataAccessException ;
	/**
	 * 撤回(批量)
	 * @param hrPeerEvaluation
	 * @return
	 */
	String reConfirmPeerEvaluationBatch(List<HrPeerEvaluation> list) throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String importPEER(Map<String, Object> mapVo)throws DataAccessException ;

}
