package com.chd.hrp.pac.serviceImpl.skht.breach;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.skht.breach.PactSPSKHTMapper;
import com.chd.hrp.pac.entity.skht.breach.PactSPSKHTEntity;
import com.chd.hrp.pac.service.skht.breach.PactSPSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactSPSKHTService")
public class PactSPSKHTServiceImpl implements PactSPSKHTService {

	private static Logger logger = Logger.getLogger(PactSPSKHTServiceImpl.class);

	@Resource(name = "pactSPSKHTMapper")
	private PactSPSKHTMapper pactSPSKHTMapper;

	@Override
	public PactSPSKHTEntity queryPactSPBySPCode(Map<String, Object> mapVo) {
		try {
			PactSPSKHTEntity queryByCode = pactSPSKHTMapper.queryByCode(mapVo);
			return queryByCode;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactSPSKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactSPSKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactSPSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactSPSKHTEntity> query = (List<PactSPSKHTEntity>) pactSPSKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactSPSKHTEntity> list = (List<PactSPSKHTEntity>) pactSPSKHTMapper.query(entityMap, rowBounds);
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
	public String addPactSPSKHT(Map<String, Object> mapVo) {
		try {
			pactSPSKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactSPSKHT(Map<String, Object> mapVo) {
		try {
			pactSPSKHTMapper.update(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactSPSKHT(List<PactSPSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactSPSKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactSPSKHT(List<PactSPSKHTEntity> listVo, String state) {
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
			returnMsg = "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
			break;
		case "disable":
			map.put("state", "04");
			map.put("disable_date", new Date());
			map.put("disabler", SessionManager.getUserId());
			returnMsg = "{\"msg\":\"失效成功.\",\"state\":\"true\"}";
			break;
		case "undisable":
			map.put("state", "03");
			returnMsg = "{\"msg\":\"恢复成功.\",\"state\":\"true\"}";
			break;
		}

		try {
			if (!listVo.isEmpty()) {
				pactSPSKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
