package com.chd.hrp.ass.service.assdisposal.inassets;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description: 051001资产处置申报(无形资产)
 * @Table: ASS_DISPOSAL_A_INASSETS
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyInassetsService extends SqlService{

	String updateConfirmDisposalApplyInassets(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;

	String queryAssApplyDeptByPlanDept(Map<String, Object> page);

	String queryDetails(Map<String, Object> page);

}
