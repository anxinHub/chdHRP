package com.chd.hrp.pac.serviceImpl.basicset.common;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.pac.dao.basicset.common.PactDeleteMapper;
import com.chd.hrp.pac.service.basicset.common.PactDeleteService;

@Service("pactDeleteService")
public class PactDeleteServiceImpl implements PactDeleteService {

	private static Logger logger = Logger.getLogger(PactDeleteServiceImpl.class);

	@Resource(name = "pactDeleteMapper")
	private PactDeleteMapper pactDeleteMapper;

	@Override
	public String isExistsDataByTable(String tableName, Object id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("dict_code", tableName.toUpperCase());
			map.put("dict_id_str", id.toString());
			pactDeleteMapper.isExistsDataByTable(map);

			return map.get("reNote") != null ? map.get("reNote").toString() : null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
