package com.chd.hrp.ass.service.assdisposal.special;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description: 051001资产处置申报(专用设备)
 * @Table: ASS_DISPOSAL_A_SPECIAL
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */ 
public interface AssDisposalApplySpecialService extends SqlService{     

	String updateConfirmDisposalApplySpecial(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;
	
	String updateUnConfirmDisposalApplySpecial(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;

	String queryAssApplyDeptByPlanDept(Map<String, Object> page)throws DataAccessException;

	String queryDetails(Map<String, Object> page)throws DataAccessException;

	/**
	 * 查询所有未确认数据单号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String>  queryState(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 新版打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> assDisposalApplySpecialByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	
}
