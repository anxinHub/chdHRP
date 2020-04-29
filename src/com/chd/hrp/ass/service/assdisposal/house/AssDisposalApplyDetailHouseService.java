package com.chd.hrp.ass.service.assdisposal.house;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalApplyDetailHouse;

/**
 * 
 * @Description: 050701 资产处置申报单明细(土地) 
 * @Table: ASS_DISPOSAL_A_DETAIL_House
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssDisposalApplyDetailHouseService extends SqlService{

	List<AssDisposalApplyDetailHouse> queryByDisANo(Map<String, Object> mapVo)throws DataAccessException;

}
