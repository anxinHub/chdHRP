package com.chd.hrp.ass.service.assdisposal.land;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description: 051001资产处置申报(土地)
 * @Table: ASS_DISPOSAL_A_INASSETS
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyLandService extends SqlService{

	String updateConfirmDisposalApplyLand(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo)throws DataAccessException;

	String queryAssApplyDeptByPlanDept(Map<String, Object> page);

	String queryDetails(Map<String, Object> page);

}
