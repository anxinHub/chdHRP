package com.chd.hrp.pac.serviceImpl.skht.pactinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.skht.pactinfo.PactDetSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactDetSourSKHTMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSKHTEntity;
import com.chd.hrp.pac.service.skht.pactinfo.PactDetSKHTService;

@Service("pactDetSKHTService")
public class PactDetSKHTServiceImpl implements PactDetSKHTService {

	private static Logger logger = Logger.getLogger(PactDetSKHTServiceImpl.class);

	@Resource(name = "pactDetSKHTMapper")
	private PactDetSKHTMapper mapper;

	@Resource(name = "pactDetSourSKHTMapper")
	private PactDetSourSKHTMapper detSourSKHTMapper;

	@Override
	public String queryPactDetSKHT(Map<String, Object> page) {
		try {
			if (page.get("change_code") == null) {
				page.put("table_code", "PACT_DET_SKHT");
			} else {
				page.put("table_code", "PACT_DET_SKHT_C");
			}
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> query = (List<Map<String, Object>>) mapper.query(page);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetSKHT(List<PactDetSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				mapper.deleteAllBatch(listVo);
				
				/*List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (PactDetSKHTEntity entity : listVo) {
					Map<String, Object> entityMap = new HashMap<String, Object>();
					entityMap.put("group_id", SessionManager.getGroupId());
					entityMap.put("hos_id", SessionManager.getHosId());
					entityMap.put("copy_code", SessionManager.getCopyCode());
					entityMap.put("pact_code", entity.getPact_code());
					entityMap.put("detail_id", entity.getDetail_id());
					list.add(entityMap);
				}
				detSourSKHTMapper.deleteByDetailId(list);*/
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
