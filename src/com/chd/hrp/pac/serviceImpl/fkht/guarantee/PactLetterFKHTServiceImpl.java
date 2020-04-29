package com.chd.hrp.pac.serviceImpl.fkht.guarantee;

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
import com.chd.hrp.pac.dao.fkht.guarantee.PactLetterFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactMainFKHTMapper;
import com.chd.hrp.pac.entity.fkht.guarantee.PactLetterFKHTEntity;
import com.chd.hrp.pac.service.fkht.guarantee.PactLetterFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactLetterFKHTService")
public class PactLetterFKHTServiceImpl implements PactLetterFKHTService {

	private static Logger logger = Logger.getLogger(PactLetterFKHTServiceImpl.class);

	@Resource(name = "pactLetterFKHTMapper")
	private PactLetterFKHTMapper pactLetterFKHTMapper;

	@Resource(name = "pactMainFKHTMapper")
	private PactMainFKHTMapper pactMainFKHTMapper;

	@Override
	public PactLetterFKHTEntity queryPactLetterByLetterCode(Map<String, Object> mapVo) {
		try {
			PactLetterFKHTEntity queryByCode = pactLetterFKHTMapper.queryByCode(mapVo);
			return queryByCode;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactLetterFKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactLetterFKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactLetterFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactLetterFKHTEntity> query = (List<PactLetterFKHTEntity>) pactLetterFKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactLetterFKHTEntity> list = (List<PactLetterFKHTEntity>) pactLetterFKHTMapper.query(entityMap,
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
	public String addPactLetterFKHT(Map<String, Object> mapVo) {
		try {
			pactLetterFKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactLetterFKHT(Map<String, Object> mapVo) {
		try {
			pactLetterFKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactLetterFKHT(List<PactLetterFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactLetterFKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactLetterFKHT(List<PactLetterFKHTEntity> listVo, String state) {
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
				pactLetterFKHTMapper.updateState(map);
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
			Map<String, Object> map = pactLetterFKHTMapper.queryPactBankDetailInfo(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHTSelectForLetter(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> map = pactMainFKHTMapper.queryPactFKHTSelectForLetter(mapVo);
			return JSONArray.toJSONString(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
