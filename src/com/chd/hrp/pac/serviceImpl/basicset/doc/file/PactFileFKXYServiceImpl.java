package com.chd.hrp.pac.serviceImpl.basicset.doc.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileFKXYMapper;
import com.chd.hrp.pac.service.basicset.doc.doc.PactFileFKXYService;

@Service("pactFileFKXYService")
public class PactFileFKXYServiceImpl implements PactFileFKXYService {

	private static Logger logger = Logger.getLogger(PactFileFKXYServiceImpl.class);

	@Resource(name = "pactFileFKXYMapper")
	private PactFileFKXYMapper pactFileFKXYMapper;

	@Override
	public String queryPactExecDoc(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = (List<Map<String, Object>>) pactFileFKXYMapper.queryPactExecDoc(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactExecFKXYFile(List<Map<String, Object>> list) {
		try {
			if (!list.isEmpty()) {
				pactFileFKXYMapper.deletePactExecFKXYFileDoc(list);
				List<Map<String, Object>> delList = new ArrayList<>();
				for (Map<String, Object> map : list) {
					int file = pactFileFKXYMapper.queryExistsFile(map);
					if (file == 0) {
						delList.add(map);
					}
				}
				if (!delList.isEmpty()) {
					pactFileFKXYMapper.deletePactExecFKXYFile(delList);
				}
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFileTypeFKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> list = pactFileFKXYMapper.queryPactFileTypeFKXY(mapVo);
			if (list.isEmpty()) {
				list = (List<Map<String, Object>>) pactFileFKXYMapper.queryPactExecFile(mapVo);
			}
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String saveFileDocFKXY(List<Map<String, Object>> newList) {
		try {
			List<Map<String, Object>> list1 = new ArrayList<>();
			List<Map<String, Object>> list2 = new ArrayList<>();

			Map<String, String> exMap = new HashMap<>();
			
			Integer sort_id = 1;
			for (Map<String, Object> map : newList) {
				if (exMap.get(map.get("type_code").toString()) == null) {
					map.put("sort_id", sort_id++);
					map.put("note", map.get("note"));
					list1.add(map);
					exMap.put(map.get("type_code").toString(), map.get("type_code").toString());
				}

				if (map.get("doc_id") != null) {
					list2.add(map);
				}
			}

			if (!list1.isEmpty()) {
				pactFileFKXYMapper.deleteDocFileByPactCodeList(list1);
				pactFileFKXYMapper.deleteByPactCodeList(list1);
				pactFileFKXYMapper.addPactExecFKXYFileBatch(list1);
			}
			if (!list2.isEmpty()) {
				pactFileFKXYMapper.addExecSelectDoc(list2);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
