package com.chd.hrp.pac.serviceImpl.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactPlanFKHTMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;
import com.chd.hrp.pac.service.fkht.pactinfo.PactPlanFKHTService;

@Service("pactPlanFKHTService")
public class PactPlanFKHTServiceImpl implements PactPlanFKHTService {

	private static Logger logger = Logger.getLogger(PactPlanFKHTServiceImpl.class);

	@Resource(name = "pactPlanFKHTMapper")
	private PactPlanFKHTMapper pactPlanFKHTMapper;

	@Override
	public String queryPactPlanFKHT(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("change_code") == null || mapVo.get("change_code") == "") {
				mapVo.put("table_code", "PACT_PLAN_FKHT");
			} else {
				mapVo.put("table_code", "PACT_PLAN_FKHT_C");
			}
			@SuppressWarnings("unchecked")
			List<PactPlanFKHTEntity> query = (List<PactPlanFKHTEntity>) pactPlanFKHTMapper.query(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactPlanFKHT(List<PactPlanFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactPlanFKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactPlanFKHTForEdit(Map<String, Object> mapVo) {
		try {
			List<PactPlanFKHTEntity> query = pactPlanFKHTMapper.queryForEdit(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
