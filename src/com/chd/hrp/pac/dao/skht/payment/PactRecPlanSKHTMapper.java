package com.chd.hrp.pac.dao.skht.payment;

import java.util.List;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.payment.PactRecPlanSKHTEntity;

public interface PactRecPlanSKHTMapper extends SqlMapper {

	void deleteBatchByRecCode(List<PactRecPlanSKHTEntity> listVo3);

}
