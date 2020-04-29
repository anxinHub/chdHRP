package com.chd.hrp.ass.service.assdisposal.inassets;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.inassets.AssDisposalApplyDetailInassets;

/**
 * 
 * @Description: 050701 资产处置申报单明细(无形资产) 
 * @Table: ASS_DISPOSAL_A_DETAIL_INASSETS
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyDetailInassetsService extends SqlService{

	List<AssDisposalApplyDetailInassets> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;

}
