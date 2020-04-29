package com.chd.hrp.pac.serviceImpl.skht.pactinfo;

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
import com.chd.hrp.pac.dao.skht.pactinfo.PactDetSourSKHTMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSourSKHTEntity;
import com.chd.hrp.pac.service.skht.pactinfo.PactDetSourSKHTService;

@Service("pactDetSourSKHTService")
public class PactDetSourSKHTServiceImpl implements PactDetSourSKHTService {

	private static Logger logger = Logger.getLogger(PactDetSourSKHTServiceImpl.class);

	@Resource(name = "pactDetSourSKHTMapper")
	private PactDetSourSKHTMapper detSourSKHTMapper;

	@Override
	public String addPactDetResourceSKHT(List<PactDetSourSKHTEntity> listVo) {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("pact_code", listVo.get(0).getPact_code());
			detSourSKHTMapper.deleteByPactCode(entityMap);
			detSourSKHTMapper.addBatch(listVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetResourceSKHT(List<PactDetSourSKHTEntity> list) {
		try {
			detSourSKHTMapper.deleteAllBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactDetResourceSKHT(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<PactDetSourSKHTEntity> query = (List<PactDetSourSKHTEntity>) detSourSKHTMapper.query(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
