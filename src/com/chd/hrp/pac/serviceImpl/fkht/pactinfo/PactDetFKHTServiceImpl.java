package com.chd.hrp.pac.serviceImpl.fkht.pactinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkht.change.PactSourceFKHTCMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactDetFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactSourceFKHTMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactDetFKHTEntity;
import com.chd.hrp.pac.service.fkht.pactinfo.PactDetFKHTService;

@Service("pactDetFKHTService")
public class PactDetFKHTServiceImpl implements PactDetFKHTService {

	private static Logger logger = Logger.getLogger(PactDetFKHTServiceImpl.class);

	@Resource(name = "pactDetFKHTMapper")
	private PactDetFKHTMapper pactDetFKHTMapper;
	
	
	@Resource(name = "pactSourceFKHTMapper")
	private PactSourceFKHTMapper pactSourceFKHTMapper;
	
	@Resource(name = "pactSourceFKHTCMapper")
	private PactSourceFKHTCMapper pactSourceFKHTCMapper;
	

	@Override
	public String queryPactDetFKHT(Map<String, Object> page) {
		try {
			if (page.get("change_code") == null || page.get("change_code") == "" ) {
				page.put("table_code", "PACT_DET_FKHT");
			} else {
				page.put("table_code", "PACT_DET_FKHT_C");
			}
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> query = (List<Map<String,Object>>) pactDetFKHTMapper.query(page);
			for(Map<String,Object> item:query){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", item.get("group_id"));
				map.put("hos_id", item.get("hos_id"));
				map.put("copy_code", item.get("copy_code"));
				map.put("pact_code", item.get("pact_code"));
				map.put("detail_id", item.get("detail_id"));
				if(item.get("change_code") != null){
					map.put("change_code", item.get("change_code"));
				}
				
				List<Map<String,Object>> sourceData = null;
				if(page.get("change_code") == null || page.get("change_code") == "" ){
					sourceData = (List<Map<String, Object>>) pactSourceFKHTMapper.query(map);
				}else{
					sourceData = (List<Map<String, Object>>) pactSourceFKHTCMapper.query(map);
				}
				
				
				item.put("sourceData", JSONObject.parseObject(ChdJson.toJson(sourceData)));
			}
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetFKHT(List<PactDetFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactDetFKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
