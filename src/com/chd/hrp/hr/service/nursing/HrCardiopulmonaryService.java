package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrCardiopulmonary;

/**
 * CRP考核
 * 
 * @author Administrator
 *
 */
public interface HrCardiopulmonaryService {
	/**
	 * 添加CRP考核
	 * 
	 * @param mapVo
	 * @return
	 */
	String addCardiopulmonary(Map<String, Object> mapVo)throws DataAccessException;

	
    /**
     * 删除CRP考核
     * @param listVo
     */
	String deleteCardiopulmonary(List<HrCardiopulmonary> listVo)throws DataAccessException;
    /**
     * 查询CRP考核
     * @param page
     * @return
     */
	String queryCardiopulmonary(Map<String, Object> page)throws DataAccessException;
	/**
     * 提交（单条）
     * @param hrCardiopulmonary
     * @return
     */
    String confirmCardiopulmonary(HrCardiopulmonary hrCardiopulmonary) throws DataAccessException ;
    /**
     * 提交(批量)
     * @param List<HrCardiopulmonary>
     * @return
     */
    String confirmCardiopulmonaryBatch(List<HrCardiopulmonary> list) throws DataAccessException;
    /**
     * 撤回
     * @param hrCardiopulmonary
     * @return
     */
	String reConfirmCardiopulmonary(HrCardiopulmonary hrCardiopulmonary) throws DataAccessException ;
	/**
     * 撤回(批量)
     * @param List<HrCardiopulmonary>
     * @return
     */
	String reConfirmCardiopulmonaryBatch(List<HrCardiopulmonary> list) throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importCPR(Map<String, Object> mapVo)throws DataAccessException ;




}
