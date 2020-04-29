package com.chd.hrp.ass.service.assdisposal.special;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalApplyDetailSpecial;

/**
 * 
 * @Description: 050701 资产处置申报单明细(专用设备) 
 * @Table: ASS_DISPOSAL_A_DETAIL_SPECIAL
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */ 
public interface AssDisposalApplyDetailSpecialService extends SqlService{

	List<AssDisposalApplyDetailSpecial> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;

}
