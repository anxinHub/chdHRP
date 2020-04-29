package com.chd.hrp.ass.service.assdisposal.general;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.general.AssDisposalApplyDetailGeneral;

/**
 * 
 * @Description: 050701 资产处置申报单明细(一般设备) 
 * @Table: ASS_DISPOSAL_A_DETAIL_General
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyDetailGeneralService extends SqlService{

	List<AssDisposalApplyDetailGeneral> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;

}
