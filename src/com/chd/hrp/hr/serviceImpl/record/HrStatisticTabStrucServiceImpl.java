package com.chd.hrp.hr.serviceImpl.record;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.record.HrStatisticTabStrucMapper;
import com.chd.hrp.hr.service.record.HrStatisticTabStrucService;

@Service("hrStatisticTabStrucService")
public class HrStatisticTabStrucServiceImpl implements HrStatisticTabStrucService {
	
	@Resource(name = "hrStatisticTabStrucMapper")
	private final HrStatisticTabStrucMapper hrStatisticTabStrucMapper = null;

	@Override
	public String queryHrStatisticSetTab(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> tabCols = hrStatisticTabStrucMapper.queryHrStatisticSetTab(entityMap);
		return ChdJson.toJson(tabCols);
	}

}
