package com.chd.hrp.pac.serviceImpl.fkht.breach;

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
import com.chd.hrp.pac.dao.fkht.breach.PactSPFKHTMapper;
import com.chd.hrp.pac.entity.fkht.breach.PactSPFKHTEntity;
import com.chd.hrp.pac.service.fkht.breach.PactSPFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactSPFKHTService")
public class PactSPFKHTServiceImpl implements PactSPFKHTService {

	private static Logger logger = Logger.getLogger(PactSPFKHTServiceImpl.class);

	@Resource(name = "pactSPFKHTMapper")
	private PactSPFKHTMapper pactSPFKHTMapper;

	@Override
	public PactSPFKHTEntity queryPactSPBySPCode(Map<String, Object> mapVo) {
		try {
			PactSPFKHTEntity queryByCode = pactSPFKHTMapper.queryByCode(mapVo);
			return queryByCode;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactSPFKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactSPFKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactSPFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactSPFKHTEntity> query = (List<PactSPFKHTEntity>) pactSPFKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactSPFKHTEntity> list = (List<PactSPFKHTEntity>) pactSPFKHTMapper.query(entityMap, rowBounds);
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
	public String addPactSPFKHT(Map<String, Object> mapVo) {
		try {
			pactSPFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactSPFKHT(Map<String, Object> mapVo) {
		try {
			pactSPFKHTMapper.update(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactSPFKHT(List<PactSPFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactSPFKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactSPFKHT(List<PactSPFKHTEntity> listVo, String state) {
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
				pactSPFKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
