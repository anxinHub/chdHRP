package com.chd.hrp.pac.serviceImpl.skht.guarantee.performance;

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
import com.chd.hrp.pac.dao.skht.guarantee.PactDepRecSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactMainSKHTMapper;
import com.chd.hrp.pac.entity.skht.guarantee.PactDepRecSKHTEntity;
import com.chd.hrp.pac.service.skht.guarantee.performance.PactDepRecSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactDepRecSKHTService")
public class PactDepRecSKHTServiceImpl implements PactDepRecSKHTService {

	private static Logger logger = Logger.getLogger(PactDepRecSKHTServiceImpl.class);

	@Resource(name = "pactDepRecSKHTMapper")
	private PactDepRecSKHTMapper pactDepRecSKHTMapper;

	@Resource(name = "pactMainSKHTMapper")
	private PactMainSKHTMapper pactMainSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactDepRecSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactDepRecSKHTEntity> query = (List<PactDepRecSKHTEntity>) pactDepRecSKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactDepRecSKHTEntity> list = (List<PactDepRecSKHTEntity>) pactDepRecSKHTMapper.query(entityMap,
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
	public List<Map<String, Object>> queryPactDepRecSKHTPrint(Map<String, Object> entityMap) {
		try {
			List<Map<String, Object>> query = pactDepRecSKHTMapper.queryForPrint(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactDepRecSKHT(Map<String, Object> mapVo) {
		try {
			pactDepRecSKHTMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDepRecSKHT(List<PactDepRecSKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactDepRecSKHTMapper.deleteAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String checkPactDepRecSKHT(List<String> listVo, String state) {
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
				pactDepRecSKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactSKHT(Map<String, Object> mapVo) {
		try {
			Map<String, Object> entity = pactMainSKHTMapper.queryForDepRec(mapVo);
			if (entity == null) {
				return "{\"error\":\"当前合同不可添加保证金付款\"}";
			}
			return ChdJson.toJson(entity);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactDepRecSKHTEntity queryPactDepRecByRecCode(Map<String, Object> mapVo) {
		try {
			PactDepRecSKHTEntity entity = pactDepRecSKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactDepRecSKHT(Map<String, Object> mapVo) {
		try {
			pactDepRecSKHTMapper.update(mapVo);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
