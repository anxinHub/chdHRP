package com.chd.hrp.pac.dao.fkht.payment;

import java.util.List;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.payment.PactPayPlanFKHTEntity;

public interface PactPayPlanFKHTMapper extends SqlMapper {

	void deleteBatchByPayCode(List<PactPayPlanFKHTEntity> listVo3);

}
