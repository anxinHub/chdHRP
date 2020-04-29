package com.chd.hrp.pac.serviceImpl.basicset.doc.doc;

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
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocFKXYMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKXYService;

@Service("pactDocFKXYService")
public class PactDocFKXYServiceImpl implements PactDocFKXYService {

	private static Logger logger = Logger.getLogger(PactDocFKXYServiceImpl.class);

	@Resource(name = "pactDocFKXYMapper")
	private PactDocFKXYMapper pactDocFKXYMapper;

	@Override
	public String queryPactDocFKXY(Map<String, Object> page) {
		try {
			@SuppressWarnings("unchecked")
			List<PactDocEntity> list = (List<PactDocEntity>) pactDocFKXYMapper.query(page);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactDocFKXY(Map<String, Object> mapVo) {
		try {
			pactDocFKXYMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDocFKXY(List<PactDocEntity> list) {
		try {
			if (!list.isEmpty()) {
				pactDocFKXYMapper.deleteAllBatch(list);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}


	@Override
	public String updatePactDocFKXY(Map<String, Object> mapVo) {
		try {
			pactDocFKXYMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addBatchPactDocFKXY(List<PactDocEntity> list) {
		try {
			if (!list.isEmpty()) {
				Map<String,Object> map = new HashMap<>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("pact_code", list.get(0).getPact_code());
				Integer doc_id = pactDocFKXYMapper.queryMaxDocId(map );
				if (doc_id == null) {
					doc_id = 1;
					for (PactDocEntity pactDocEntity : list) {
						pactDocEntity.setDoc_id(doc_id++);
					}
				}else {
					for (PactDocEntity pactDocEntity : list) {
						if (pactDocEntity.getDoc_id() == null) {
							pactDocEntity.setDoc_id(++doc_id);
						}
					}
				}
				pactDocFKXYMapper.deleteByPactCode(map);
				pactDocFKXYMapper.addBatch(list);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactDocFKXYForExec(Map<String, Object> mapVo) {
		try {
			Object docs = mapVo.get("doc_ids");
			if (docs != null && !"".equals(docs.toString())) {
				String doc_ids = docs.toString();
				String[] ids = doc_ids.split(",");
				StringBuffer buffer = new StringBuffer();
				for (String id : ids) {
					buffer.append("'").append(id).append("',");
				}
				buffer.deleteCharAt(buffer.length() - 1);
				mapVo.put("doc_ids", buffer.toString());
			} else {
				mapVo.put("doc_ids", null);
			}
			
			List<PactDocEntity> list =  pactDocFKXYMapper.queryPactDocFKXYForExec(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	@Override
	public String queryPactFKXYType(Map<String, Object> mapVo) {
		try {
			Map<String,Object> map = pactDocFKXYMapper.queryPactFKXYType(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
