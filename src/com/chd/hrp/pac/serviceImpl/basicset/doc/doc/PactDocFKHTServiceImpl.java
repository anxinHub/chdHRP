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
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocFKHTMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKHTService;

@Service("pactDocFKHTService")
public class PactDocFKHTServiceImpl implements PactDocFKHTService {

	private static Logger logger = Logger.getLogger(PactDocFKHTServiceImpl.class);

	@Resource(name = "pactDocFKHTMapper")
	private PactDocFKHTMapper pactDocFKHTMapper;

	@Override
	public String queryPactDocFKHT(Map<String, Object> page) {
		try {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> list = (List<Map<String, Object>>) pactDocFKHTMapper.query(page);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactDocFKHT(Map<String, Object> mapVo) {
		try {
			pactDocFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDocFKHT(List<PactDocEntity> list) {
		try {
			//if (!list.isEmpty()) {
			///	pactDocFKHTMapper.deleteAllBatch(list);
			//}
			
			if (list.size()>0) {
				PactDocEntity pact = list.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("IFB_GROUPID", pact.getGroup_id());
				map.put("IFB_PrjName", pact.getHos_id());
				map.put("COPY_CODE", pact.getCopy_code());	
				pactDocFKHTMapper.deleteAllBatch(list);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
/*	yh 元
	@Override
	public String updatePactDocFKHT(Map<String, Object> mapVo) {
		try {
			pactDocFKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
*/
	@Override
	public String addBatchPactDocFKHT(List<PactDocEntity> list) {
		try {
			if (!list.isEmpty()) {
				PactDocEntity pactDocEntity = list.get(0);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", pactDocEntity.getGroup_id());
				map.put("hos_id", pactDocEntity.getHos_id());
				map.put("copy_code", pactDocEntity.getCopy_code());
				map.put("pact_code", pactDocEntity.getPact_code());

				Integer docId = pactDocFKHTMapper.queryMaxDocId(map);
				if (docId == null) {
					docId = 1;
					for (PactDocEntity pactDocEntity2 : list) {
						pactDocEntity2.setDoc_id(docId++);
					}
				} else {
					for (PactDocEntity pactDocEntity2 : list) {
						if (pactDocEntity2.getDoc_id() == null) {
							pactDocEntity2.setDoc_id(++docId);
						}
					}
				}

				pactDocFKHTMapper.deleteByPactCode(map);
				pactDocFKHTMapper.addBatch(list);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactDocFKHTForExec(Map<String, Object> mapVo) {
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

			List<PactDocEntity> list = pactDocFKHTMapper.queryPactDocFKHTForExec(mapVo);
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHTType(Map<String, Object> mapVo) {
		try {
			Map<String,Object> map = pactDocFKHTMapper.queryPactFKHTType(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDocFKHT(List<PactDocEntity> mapVo) {
		// TODO Auto-generated method stub
		try {
			pactDocFKHTMapper.updatePactDocEntity(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
