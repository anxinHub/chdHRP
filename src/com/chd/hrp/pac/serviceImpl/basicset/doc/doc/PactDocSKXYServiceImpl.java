package com.chd.hrp.pac.serviceImpl.basicset.doc.doc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocSKXYMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKXYService;

@Service("pactDocSKXYService")
public class PactDocSKXYServiceImpl implements PactDocSKXYService {

	private static Logger logger = Logger.getLogger(PactDocSKXYServiceImpl.class);

	@Resource(name = "pactDocSKXYMapper")
	private PactDocSKXYMapper pactDocSKXYMapper;

	@Override
	public String queryPactDocSKXY(Map<String, Object> page) {
		try {
			@SuppressWarnings("unchecked")
			List<PactDocEntity> list = (List<PactDocEntity>) pactDocSKXYMapper.query(page);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactDocSKXY(Map<String, Object> mapVo) {
		try {
			pactDocSKXYMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDocSKXY(List<PactDocEntity> mapVo) {
		try {
			pactDocSKXYMapper.deleteAllBatch(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}


	@Override
	public String updatePactDocSKXY(Map<String, Object> mapVo) {
		try {
			pactDocSKXYMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addBatchPactDocSKXY(List<PactDocEntity> mapVo) {
		try {
			PactDocEntity pactDocEntity = mapVo.get(0);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", pactDocEntity.getGroup_id());
			map.put("hos_id", pactDocEntity.getHos_id());
			map.put("copy_code", pactDocEntity.getCopy_code());
			map.put("pact_code", pactDocEntity.getPact_code());

			Integer docId = pactDocSKXYMapper.queryMaxDocId(map);
			if (docId == null) {
				docId = 1;
				for (PactDocEntity pactDocEntity2 : mapVo) {
					pactDocEntity2.setDoc_id(docId++);
				}
			} else {
				for (PactDocEntity pactDocEntity2 : mapVo) {
					if (pactDocEntity2.getDoc_id() == null) {
						pactDocEntity2.setDoc_id(++docId);
					}
				}
			}
			
			pactDocSKXYMapper.deleteByPactCode(map);
			pactDocSKXYMapper.addBatch(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactDocSKXYForExec(Map<String, Object> mapVo) {
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
			
			List<PactDocEntity> list =  pactDocSKXYMapper.queryPactDocSKXYForExec(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactSKXYType(Map<String, Object> mapVo) {
		try {
			Map<String,Object> map = pactDocSKXYMapper.queryPactSKXYType(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
