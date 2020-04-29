 package com.chd.hrp.ass.service.assdisposal.other;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description: 050701 资产处置申报单明细(一般设备) 
 * @Table: ASS_DISPOSAL_A_DETAIL_Other
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */ 
public interface AssDisposalApplyOtherService extends SqlService{    

	String updateConfirmDisposalApplyOther(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;
	
	String updateUnConfirmDisposalApplyOther(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;

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
	public Map<String, Object> assDisposalApplyOtherByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;

}
