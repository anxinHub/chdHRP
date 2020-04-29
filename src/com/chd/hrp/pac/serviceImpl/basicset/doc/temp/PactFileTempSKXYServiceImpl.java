package com.chd.hrp.pac.serviceImpl.basicset.doc.temp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.doc.temp.PactFileTempSKXYMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactFileTempEntity;
import com.chd.hrp.pac.entity.basicset.nature.PactNatureEntity;
import com.chd.hrp.pac.service.basicset.doc.temp.PactFileTempSKXYService;

@Service("pactFileTempSKXYService")
public class PactFileTempSKXYServiceImpl implements PactFileTempSKXYService {

	private static Logger logger = Logger.getLogger(PactFileTempSKXYServiceImpl.class);

	@Resource(name = "pactFileTempSKXYMapper")
	private PactFileTempSKXYMapper mapper;

	@Override
	public String queryPactFileTempSKXY(Map<String, Object> page) {
		try {
			@SuppressWarnings("unchecked")
			List<PactNatureEntity> list = (List<PactNatureEntity>) mapper.query(page);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactFileTempSKXY(List<PactFileTempEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("pact_type_code", listVo.get(0).getPact_type_code());
				mapper.delete(mapVo);
				if (!(listVo.size() == 1 && listVo.get(0).getFile_type() == null)) {
					mapper.addBatch(listVo);
				}
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactTypeSKXYTree(Map<String, Object> page) {
		try {
			List<Map<String, Object>> Filelist = mapper.queryTree(page);
			for (Map<String, Object> map : Filelist) {
				if (map.get("id") == null) {
					map.put("id", "");
				}
			}
			return JSONArray.toJSONString(Filelist);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@Override
	
	
	public String queryPactFileTempSKXYfile(Map<String ,Object>entityMap){
		
		try{
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
		
				List<Map<String, Object>> list= mapper.queryPactFileTempSKXYfile(entityMap);
				return ChdJson.toJsonLower(list);
			
		}catch (Exception e){
			logger.warn(e.getMessage(), e);
			throw new SysException (e.getMessage(),e);
			
		}
	}
	
	
}
