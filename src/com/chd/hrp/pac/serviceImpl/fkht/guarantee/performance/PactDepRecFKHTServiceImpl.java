package com.chd.hrp.pac.serviceImpl.fkht.guarantee.performance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkht.guarantee.PactDepRecFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactMainFKHTMapper;
import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRecFKHTEntity;
import com.chd.hrp.pac.service.fkht.guarantee.performance.PactDepRecFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactDepRecFKHTService")
public class PactDepRecFKHTServiceImpl implements PactDepRecFKHTService {

	private static Logger logger = Logger.getLogger(PactDepRecFKHTServiceImpl.class);

	@Resource(name = "pactDepRecFKHTMapper")
	private PactDepRecFKHTMapper pactDepRecFKHTMapper;

	@Resource(name = "pactMainFKHTMapper")
	private PactMainFKHTMapper pactMainFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactDepRecFKHT(Map<String, Object> entityMap) {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDepRecFKHTEntity> query = (List<PactDepRecFKHTEntity>) pactDepRecFKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDepRecFKHTEntity> list = (List<PactDepRecFKHTEntity>) pactDepRecFKHTMapper.query(entityMap,
						rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactDepRecFKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactDepRecFKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactDepRecFKHT(Map<String, Object> mapVo) {
		try {
			pactDepRecFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDepRecFKHT(List<PactDepRecFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactDepRecFKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactDepRecFKHT(List<String> listVo, String state) {
		String returnMsg = "{\"error\":\"状态有误\"}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("list", listVo);

		switch (state) {
		case "check":
			map.put("check_date", new Date());
			map.put("checker", SessionManager.getUserId());
			map.put("state", "02");
			returnMsg = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			break;
		case "uncheck":
			map.put("state", "01");
			returnMsg = "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			break;
		case "confirm":
			map.put("state", "03");
			map.put("confirm_date", new Date());
			map.put("confirmer", SessionManager.getUserId());
			returnMsg = "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
			break;
		case "unconfirm":
			map.put("state", "02");
			returnMsg = "{\"msg\":\"取消成功.\",\"state\":\"true\"}";
			break;
		}
		try {
			if (!listVo.isEmpty()) {
				pactDepRecFKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHT(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			Map<String, Object> entity = pactMainFKHTMapper.queryForDepRec(mapVo);
			if (entity == null) {
				return "{\"error\":\"当前合同不可添加保证金收款\"}";
			}
			return ChdJson.toJson(entity);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactDepRecFKHTEntity queryPactDepRecByRecCode(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			PactDepRecFKHTEntity entity = pactDepRecFKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDepRecFKHT(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			pactDepRecFKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
