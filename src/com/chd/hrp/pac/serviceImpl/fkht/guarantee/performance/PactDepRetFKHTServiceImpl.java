package com.chd.hrp.pac.serviceImpl.fkht.guarantee.performance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkht.guarantee.PactDepRetFKHTMapper;
import com.chd.hrp.pac.entity.fkht.guarantee.PactDepRetFKHTEntity;
import com.chd.hrp.pac.service.fkht.guarantee.performance.PactDepRetFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactDepRetFKHTService")
public class PactDepRetFKHTServiceImpl implements PactDepRetFKHTService {

	private static Logger logger = Logger.getLogger(PactDepRetFKHTServiceImpl.class);

	@Resource(name = "pactDepRetFKHTMapper")
	private PactDepRetFKHTMapper pactDepRetFKHTMapper;

	@Override
	public PactDepRetFKHTEntity queryPactDepRetByRetCode(Map<String, Object> mapVo) {
		try {
			PactDepRetFKHTEntity entity = pactDepRetFKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactDepRetFKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactDepRetFKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactDepRetFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDepRetFKHTEntity> query = (List<PactDepRetFKHTEntity>) pactDepRetFKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDepRetFKHTEntity> list = (List<PactDepRetFKHTEntity>) pactDepRetFKHTMapper.query(entityMap,
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
	public String addPactDepRetFKHT(Map<String, Object> mapVo) {
		try {
			pactDepRetFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDepRetFKHT(Map<String, Object> mapVo) {
		try {
			pactDepRetFKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDepRetFKHT(List<PactDepRetFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactDepRetFKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactDepRetFKHT(List<String> listVo, String state) {
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
				pactDepRetFKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHTForRet(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = pactDepRetFKHTMapper.queryPactFKHTForRet(mapVo);
			if (map == null) {
				return "{\"error\":\"当前合同不可添加保证金退款\"}";
			}
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHTSelectForRet(Map<String, Object> mapVo) {
		try {
			List<Map<String,Object>> list = pactDepRetFKHTMapper.queryPactFKHTSelectForRet(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
