package com.chd.hrp.pac.serviceImpl.fkht.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactMainFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactPlanFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayDetFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayMainFKHTMapper;
import com.chd.hrp.pac.dao.fkht.payment.PactPayPlanFKHTMapper;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;
import com.chd.hrp.pac.entity.fkht.payment.PactPayDetFKHTEntity;
import com.chd.hrp.pac.entity.fkht.payment.PactPayMainFKHTEntity;
import com.chd.hrp.pac.entity.fkht.payment.PactPayPlanFKHTEntity;
import com.chd.hrp.pac.service.fkht.payment.PactPayMainFKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactPayMainFKHTService")
public class PactPayMainFKHTServiceImpl implements PactPayMainFKHTService {

	private static Logger logger = Logger.getLogger(PactPayMainFKHTServiceImpl.class);

	@Resource(name = "pactPayMainFKHTMapper")
	private PactPayMainFKHTMapper pactPayMainFKHTMapper;

	@Resource(name = "pactPayPlanFKHTMapper")
	private PactPayPlanFKHTMapper pactPayPlanFKHTMapper;

	@Resource(name = "pactPayDetFKHTMapper")
	private PactPayDetFKHTMapper pactPayDetFKHTMapper;

	@Resource(name = "pactMainFKHTMapper")
	private PactMainFKHTMapper pactMainFKHTMapper;
	
	@Resource(name = "pactPlanFKHTMapper")
	private PactPlanFKHTMapper pactPlanFKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactPayMainFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactPayMainFKHTEntity> query = (List<PactPayMainFKHTEntity>) pactPayMainFKHTMapper
						.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactPayMainFKHTEntity> list = (List<PactPayMainFKHTEntity>) pactPayMainFKHTMapper.query(entityMap,
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
	public String checkPactPayMainFKHTState(List<PactPayMainFKHTEntity> listVo, String check) {
		try {
			String returnMsg = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());

			switch (check) {
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
				map.put("confirm_date", new Date());
				map.put("confirmer", SessionManager.getUserId());
				map.put("state", "03");
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
				break;
			case "unconfirm":
				map.put("state", "02");
				map.put("state_code", "11");
				returnMsg = "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
				break;
			default:
				return "{\"error\":请求有误\"\"}";
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			if(!listVo.isEmpty() && "confirm".equals(check)) {
				for(PactPayMainFKHTEntity entity1:listVo) {
					map.put("pact_code",entity1.getPact_code());
					map.put("pay_code",entity1.getPay_code());
					List<Map<String, Object>> ls = (List<Map<String, Object>>) pactPlanFKHTMapper.queryPactPlanList(map);
					for(Map<String, Object> m : ls) {
						m.put("payed_money",m.get("plan_money"));
						m.put("pay_flag",1);
						list.add(m);
					}
				}
				pactPlanFKHTMapper.updateBatch(list);
			}
			if(!listVo.isEmpty() && "unconfirm".equals(check)) {
				for(PactPayMainFKHTEntity entity1:listVo) {
					map.put("pact_code",entity1.getPact_code());
					map.put("pay_code",entity1.getPay_code());
					List<Map<String, Object>> ls = (List<Map<String, Object>>) pactPlanFKHTMapper.queryPactPlanList(map);
					for(Map<String, Object> m : ls) {
						m.put("payed_money","0.00");
						m.put("pay_flag",0);
						list.add(m);
					}
				}
				pactPlanFKHTMapper.updateBatch(list);
			}
			if (!listVo.isEmpty()) {
				map.put("list", listVo);
				pactPayMainFKHTMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactPayMainFKHT(String mapVo) {
		try {

			List<PactPayDetFKHTEntity> listVo2 = JSONArray.parseArray(mapVo, PactPayDetFKHTEntity.class);
			if (!listVo2.isEmpty()) {
				pactPayDetFKHTMapper.deleteByPayCode(listVo2);
			}

			List<PactPayPlanFKHTEntity> listVo3 = JSONArray.parseArray(mapVo, PactPayPlanFKHTEntity.class);
			if (!listVo3.isEmpty()) {
				pactPayPlanFKHTMapper.deleteBatchByPayCode(listVo3);
			}

			List<PactPayMainFKHTEntity> listVo = JSONArray.parseArray(mapVo, PactPayMainFKHTEntity.class);
			if (!listVo.isEmpty()) {
				pactPayMainFKHTMapper.deleteByPayCodeBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactPayMainFKHTEntity queryPactPayMainFKHTByCode(Map<String, Object> mapVo) {
		try {
			PactPayMainFKHTEntity entity = pactPayMainFKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactPayMainFKHT(Map<String, Object> mapVo) {
		try {
			pactPayMainFKHTMapper.add(mapVo);

			List<PactPayPlanFKHTEntity> list = JSONObject.parseArray((String) mapVo.get("grid"),
					PactPayPlanFKHTEntity.class);
			if (!list.isEmpty()) {
				for (PactPayPlanFKHTEntity pactPayPlanFKHTEntity : list) {
					pactPayPlanFKHTEntity.setPay_code((String) mapVo.get("pay_code"));
				}
				pactPayPlanFKHTMapper.addBatch(list);
			}

			List<PactPayDetFKHTEntity> listVo = JSONArray.parseArray((String) mapVo.get("pay"),
					PactPayDetFKHTEntity.class);
			if (!listVo.isEmpty()) {
				Integer detail_id = pactPayDetFKHTMapper.queryMaxDetailId(mapVo);
				if(detail_id==null) {
					detail_id=0;
				}
				for (PactPayDetFKHTEntity pactPayDetFKHTEntity : listVo) {
					pactPayDetFKHTEntity.setPay_code((String) mapVo.get("pay_code"));
					pactPayDetFKHTEntity.setDetail_id(++detail_id);
				}
				pactPayDetFKHTMapper.addBatch(listVo);
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactPayDetFKHT(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<PactPayDetFKHTEntity> query = (List<PactPayDetFKHTEntity>) pactPayDetFKHTMapper.query(mapVo);
			return ChdJson.toJson(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String queryPactPayPlanFKHT(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<PactPayPlanFKHTEntity> query = (List<PactPayPlanFKHTEntity>) pactPayPlanFKHTMapper.query(mapVo);
			return ChdJson.toJson(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String updatePactPayMainFKHT(Map<String, Object> mapVo) {
		try {
			pactPayMainFKHTMapper.update(mapVo);

			List<PactPayPlanFKHTEntity> list = JSONObject.parseArray((String) mapVo.get("grid"),
					PactPayPlanFKHTEntity.class);
			if (!list.isEmpty()) {
				pactPayPlanFKHTMapper.deleteBatchByPayCode(list);
				pactPayPlanFKHTMapper.addBatch(list);
			}

			List<PactPayDetFKHTEntity> listVo = JSONArray.parseArray((String) mapVo.get("pay"),
					PactPayDetFKHTEntity.class);
			
			if (!listVo.isEmpty()) {
				Integer detail_id = pactPayDetFKHTMapper.queryMaxDetailId(mapVo);
				if(detail_id==null) {
					detail_id=0;
				}
				for (PactPayDetFKHTEntity pactPayDetFKHTEntity : listVo) {
					pactPayDetFKHTEntity.setDetail_id(++detail_id);
				}
				pactPayDetFKHTMapper.deleteByPayCode(listVo);
				pactPayDetFKHTMapper.addBatch(listVo);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactPayMainFKHTPrint(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactPayMainFKHTMapper.queryForPrint(mapVo);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactPayMainFKHTDetail(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactPayMainFKHTMapper.queryPactPayMainFKHTDetail(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactPayMainFKHTMapper.queryPactPayMainFKHTDetail(entityMap, rowBounds);
				@SuppressWarnings({ "rawtypes", "unchecked" })
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactFKHTForPayMent(Map<String, Object> mapVo) {
		try {
			Map<String, Object> map = pactMainFKHTMapper.queryForDepRec(mapVo);
			return ChdJson.toJson(map);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
