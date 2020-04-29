package com.chd.hrp.htc.service.relative.plan.chargeitemvalue;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.relative.plan.chargeitemvalue.HtcRelativeChargeItemValue;

public interface HtcRelativeChargeItemValueService {
	
    public String addHtcRelativeChargeItemValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcRelativeChargeItemValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcRelativeChargeItemValue queryHtcRelativeChargeItemValueByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcRelativeChargeItemValue(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcRelativeChargeItemValue(Map<String,Object> entityMap)throws DataAccessException;
}
