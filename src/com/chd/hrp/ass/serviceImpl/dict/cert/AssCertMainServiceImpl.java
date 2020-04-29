package com.chd.hrp.ass.serviceImpl.dict.cert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.cert.AssCertDetailMapper;
import com.chd.hrp.ass.dao.dict.cert.AssCertMainMapper;
import com.chd.hrp.ass.entity.dict.cert.AssCertMain;
import com.chd.hrp.ass.service.dict.cert.AssCertMainService;
import com.github.pagehelper.PageInfo;
@Service("assCertMainService")
public class AssCertMainServiceImpl implements AssCertMainService {
	
	private static Logger logger = Logger.getLogger(AssCertMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCertMainMapper")
	private final AssCertMainMapper assCertMainMapper = null;
	
	@Resource(name = "assCertDetailMapper")
	private final AssCertDetailMapper assCertDetailMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("cert_code", entityMap.get("cert_code"));

		List<AssCertMain> list = (List<AssCertMain>) assCertMainMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				return "{\"error\":\"编码重复,请重新添加.\"}";
			} else {
				
				int state = assCertMainMapper.add(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("cert_code", entityMap.get("cert_code"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);


				listVo.add(detailVo);
			}

			assCertDetailMapper.delete(entityMap);
			assCertDetailMapper.addBatch(listVo);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"cert_code\":\"" + entityMap.get("cert_code").toString()
					+ "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("cert_code", entityMap.get("cert_code"));

		List<AssCertMain> list = (List<AssCertMain>) assCertMainMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				int state = assCertMainMapper.update(entityMap);
			} 

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("cert_code", entityMap.get("cert_code"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);


				listVo.add(detailVo);
			}

			assCertDetailMapper.delete(entityMap);
			assCertDetailMapper.addBatch(listVo);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"cert_code\":\"" + entityMap.get("cert_code").toString()
					+ "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assCertDetailMapper.deleteBatch(entityMap);
			assCertMainMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<?> list = assCertMainMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<?> list = assCertMainMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assCertMainMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			  int state = assCertMainMapper.updateToExamine(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}

	@Override
	public String updateNotToExamine(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			  int state = assCertMainMapper.updateNotToExamine(entityMap);
				
				return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}

}
