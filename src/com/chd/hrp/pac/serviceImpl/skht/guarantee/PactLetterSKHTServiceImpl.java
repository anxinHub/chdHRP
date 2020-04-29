package com.chd.hrp.pac.serviceImpl.skht.guarantee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.skht.guarantee.PactLetterSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactMainSKHTMapper;
import com.chd.hrp.pac.entity.skht.guarantee.PactLetterSKHTEntity;
import com.chd.hrp.pac.service.skht.guarantee.PactLetterSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactLetterSKHTService")
public class PactLetterSKHTServiceImpl implements PactLetterSKHTService {

	private static Logger logger = Logger.getLogger(PactLetterSKHTServiceImpl.class);

	@Resource(name = "pactLetterSKHTMapper")
	private PactLetterSKHTMapper pactLetterSKHTMapper;

	@Resource(name = "pactMainSKHTMapper")
	private PactMainSKHTMapper pactMainSKHTMapper;

	@Override
	public PactLetterSKHTEntity queryPactLetterByLetterCode(Map<String, Object> mapVo) {
		try {
			PactLetterSKHTEntity queryByCode = pactLetterSKHTMapper.queryByCode(mapVo);
			return queryByCode;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactLetterSKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactLetterSKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactLetterSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactLetterSKHTEntity> query = (List<PactLetterSKHTEntity>) pactLetterSKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactLetterSKHTEntity> list = (List<PactLetterSKHTEntity>) pactLetterSKHTMapper.query(entityMap,
						rowBounds);
				@SuppressWarnings("rawtypes")
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}

		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactLetterSKHT(Map<String, Object> mapVo) {
		try {
			pactLetterSKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactLetterSKHT(Map<String, Object> mapVo) {
		try {
			pactLetterSKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactLetterSKHT(List<PactLetterSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactLetterSKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactLetterSKHT(List<PactLetterSKHTEntity> listVo, String state) {
		String returnMsg = "{\"error\":\"状态有误\"}";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		switch (state) {
		case "check":
			map.put("check_date", new Date());
			map.put("checker", SessionManager.getUserId());
			map.put("letter_state", "02");
			returnMsg = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			break;
		case "uncheck":
			map.put("letter_state", "01");
			returnMsg = "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			break;
		case "confirm":
			map.put("letter_state", "03");
			map.put("confirm_date", new Date());
			map.put("confirmer", SessionManager.getUserId());
			returnMsg = "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
			break;
		case "unconfirm":
			map.put("letter_state", "02");
			returnMsg = "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
			break;
		case "disable":
			map.put("letter_state", "04");
			map.put("disable_date", new Date());
			map.put("disabler", SessionManager.getUserId());
			returnMsg = "{\"msg\":\"失效成功.\",\"state\":\"true\"}";
			break;
		case "undisable":
			map.put("letter_state", "03");
			returnMsg = "{\"msg\":\"恢复成功.\",\"state\":\"true\"}";
			break;
		}

		try {
			if (!listVo.isEmpty()) {
				map.put("list", listVo);
				pactLetterSKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactBankDetailInfo(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = pactLetterSKHTMapper.queryPactBankDetailInfo(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactSKHTSelectForLetter(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> map = pactMainSKHTMapper.queryPactSKHTSelectForLetter(mapVo);
			return JSONArray.toJSONString(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
