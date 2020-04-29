package com.chd.hrp.ass.service.assdisposal.land;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.land.AssDisposalApplyDetailLand;

/**
 * 
 * @Description: 050701 资产处置申报单明细(土地) 
 * @Table: ASS_DISPOSAL_A_DETAIL_LAND
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyDetailLandService extends SqlService{

	List<AssDisposalApplyDetailLand> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;

}
