package com.chd.hrp.pac.serviceImpl.skht.guarantee.performance;

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
import com.chd.hrp.pac.dao.skht.guarantee.PactDepRetSKHTMapper;
import com.chd.hrp.pac.entity.skht.guarantee.PactDepRetSKHTEntity;
import com.chd.hrp.pac.service.skht.guarantee.performance.PactDepRetSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactDepRetSKHTService")
public class PactDepRetSKHTServiceImpl implements PactDepRetSKHTService {

	private static Logger logger = Logger.getLogger(PactDepRetSKHTServiceImpl.class);

	@Resource(name = "pactDepRetSKHTMapper")
	private PactDepRetSKHTMapper pactDepRetSKHTMapper;

	@Override
	public PactDepRetSKHTEntity queryPactDepRetByRetCode(Map<String, Object> mapVo) {
		try {
			PactDepRetSKHTEntity entity = pactDepRetSKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactDepRetSKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactDepRetSKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactDepRetSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDepRetSKHTEntity> query = (List<PactDepRetSKHTEntity>) pactDepRetSKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDepRetSKHTEntity> list = (List<PactDepRetSKHTEntity>) pactDepRetSKHTMapper.query(entityMap,
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
	public String addPactDepRetSKHT(Map<String, Object> mapVo) {
		try {
			pactDepRetSKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDepRetSKHT(Map<String, Object> mapVo) {
		try {
			pactDepRetSKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDepRetSKHT(List<PactDepRetSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactDepRetSKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactDepRetSKHT(List<String> listVo, String state) {
		String returnMsg = "{\"error\":\"状态有误\"}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		

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
				map.put("list", listVo);
				pactDepRetSKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactSKHTForRet(Map<String, Object> mapVo) {
		try {
			Map<String,Object> entity = pactDepRetSKHTMapper.queryPactSKHTForRet(mapVo);
			if (entity == null) {
				return "{\"error\":\"当前合同不可添加保证金收回\"}";
			}
			return ChdJson.toJson(entity);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactSKHTSelectForRet(Map<String, Object> mapVo) {
		try {
			List<Map<String,Object>> list = pactDepRetSKHTMapper.queryPactSKHTSelectForRet(mapVo);
			return JSONArray.toJSONString(list);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
