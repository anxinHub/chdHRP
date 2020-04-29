package com.chd.hrp.pac.serviceImpl.skht.payment;

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
import com.chd.hrp.pac.dao.skht.pactinfo.PactMainSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactPlanSKHTMapper;
import com.chd.hrp.pac.dao.skht.payment.PactRecDetSKHTMapper;
import com.chd.hrp.pac.dao.skht.payment.PactRecMainSKHTMapper;
import com.chd.hrp.pac.dao.skht.payment.PactRecPlanSKHTMapper;
import com.chd.hrp.pac.entity.fkht.payment.PactPayDetFKHTEntity;
import com.chd.hrp.pac.entity.fkht.payment.PactPayMainFKHTEntity;
import com.chd.hrp.pac.entity.skht.payment.PactRecDetSKHTEntity;
import com.chd.hrp.pac.entity.skht.payment.PactRecMainSKHTEntity;
import com.chd.hrp.pac.entity.skht.payment.PactRecPlanSKHTEntity;
import com.chd.hrp.pac.service.skht.payment.PactRecMainSKHTService;
import com.github.pagehelper.PageInfo;

@Service("pactRecMainSKHTService")
public class PactRecMainSKHTServiceImpl implements PactRecMainSKHTService {

	private static Logger logger = Logger.getLogger(PactRecMainSKHTServiceImpl.class);

	@Resource(name = "pactRecMainSKHTMapper")
	private PactRecMainSKHTMapper pactRecMainSKHTMapper;

	@Resource(name = "pactRecPlanSKHTMapper")
	private PactRecPlanSKHTMapper pactRecPlanSKHTMapper;

	@Resource(name = "pactRecDetSKHTMapper")
	private PactRecDetSKHTMapper pactRecDetSKHTMapper;

	@Resource(name = "pactMainSKHTMapper")
	private PactMainSKHTMapper pactMainSKHTMapper;
	
	@Resource(name = "pactPlanSKHTMapper")
	private PactPlanSKHTMapper pactPlanSKHTMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactRecMainSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactRecMainSKHTEntity> query = (List<PactRecMainSKHTEntity>) pactRecMainSKHTMapper
						.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactRecMainSKHTEntity> list = (List<PactRecMainSKHTEntity>) pactRecMainSKHTMapper.query(entityMap,
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
	public String checkPactRecMainSKHTState(List<PactRecMainSKHTEntity> listVo, String check) {
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
				for(PactRecMainSKHTEntity entity1:listVo) {
					map.put("pact_code",entity1.getPact_code());
					map.put("rec_code",entity1.getRec_code());
					List<Map<String, Object>> ls = (List<Map<String, Object>>) pactPlanSKHTMapper.queryPactPlanList(map);
					for(Map<String, Object> m : ls) {
						m.put("payed_money",m.get("plan_money"));
						m.put("pay_flag",1);
						list.add(m);
					}
				}
				pactPlanSKHTMapper.updateBatch(list);
			}
			
			if(!listVo.isEmpty() && "unconfirm".equals(check)) {
				for(PactRecMainSKHTEntity entity1:listVo) {
					map.put("pact_code",entity1.getPact_code());
					map.put("rec_code",entity1.getRec_code());
					List<Map<String, Object>> ls = (List<Map<String, Object>>) pactPlanSKHTMapper.queryPactPlanList(map);
					for(Map<String, Object> m : ls) {
						m.put("payed_money","0.00");
						m.put("pay_flag",0);
						list.add(m);
					}
				}
				pactPlanSKHTMapper.updateBatch(list);
			}
			
			if (!listVo.isEmpty()) {
				map.put("list", listVo);
				pactRecMainSKHTMapper.updateState(map);
			}

			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactRecMainSKHT(String mapVo) {
		try {
			List<PactRecDetSKHTEntity> listVo2 = JSONArray.parseArray(mapVo, PactRecDetSKHTEntity.class);
			if (!listVo2.isEmpty()) {
				pactRecDetSKHTMapper.deleteByRecCode(listVo2);
			}
			
			List<PactRecPlanSKHTEntity> listVo3 = JSONArray.parseArray(mapVo, PactRecPlanSKHTEntity.class);
			if (!listVo3.isEmpty()) {
				pactRecPlanSKHTMapper.deleteBatchByRecCode(listVo3);
			}
			
			List<PactRecMainSKHTEntity> listVo = JSONArray.parseArray(mapVo, PactRecMainSKHTEntity.class);
			if (!listVo.isEmpty()) {
				pactRecMainSKHTMapper.deleteByRecCodeBatch(listVo);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactRecMainSKHTEntity queryPactRecMainSKHTByCode(Map<String, Object> mapVo) {
		try {
			PactRecMainSKHTEntity entity = pactRecMainSKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactRecMainSKHT(Map<String, Object> mapVo) {
		try {
			pactRecMainSKHTMapper.add(mapVo);

			List<PactRecPlanSKHTEntity> list = JSONObject.parseArray((String) mapVo.get("grid"),
					PactRecPlanSKHTEntity.class);
			if (!list.isEmpty()) {
				for (PactRecPlanSKHTEntity pactRecPlanSKHTEntity : list) {
					pactRecPlanSKHTEntity.setRec_code((String) mapVo.get("rec_code"));
				}
				pactRecPlanSKHTMapper.addBatch(list);
			}
			List<PactRecDetSKHTEntity> listVo = JSONArray.parseArray((String) mapVo.get("pay"),
					PactRecDetSKHTEntity.class);
			if (!listVo.isEmpty()) {
				Integer detail_id = pactRecDetSKHTMapper.queryMaxDetailId(mapVo);
				if(detail_id==null) {
					detail_id=0;
				}
				for (PactRecDetSKHTEntity pactRecDetSKHTEntity : listVo) {
					pactRecDetSKHTEntity.setRec_code((String) mapVo.get("rec_code"));
					pactRecDetSKHTEntity.setDetail_id(++detail_id);
				}
				pactRecDetSKHTMapper.addBatch(listVo);
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactRecDetSKHT(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<PactRecDetSKHTEntity> query = (List<PactRecDetSKHTEntity>) pactRecDetSKHTMapper.query(mapVo);
			return ChdJson.toJson(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String queryPactRecPlanSKHT(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<PactRecPlanSKHTEntity> query = (List<PactRecPlanSKHTEntity>) pactRecPlanSKHTMapper.query(mapVo);
			return ChdJson.toJson(query);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public String updatePactRecMainSKHT(Map<String, Object> mapVo) {
		try {
			pactRecMainSKHTMapper.update(mapVo);

			List<PactRecPlanSKHTEntity> list = JSONObject.parseArray((String) mapVo.get("grid"),
					PactRecPlanSKHTEntity.class);
			if (!list.isEmpty()) {
				pactRecPlanSKHTMapper.deleteBatchByRecCode(list);
				pactRecPlanSKHTMapper.addBatch(list);
			}

			List<PactRecDetSKHTEntity> listVo = JSONArray.parseArray((String) mapVo.get("pay"),
					PactRecDetSKHTEntity.class);
			if (!listVo.isEmpty()) {
				Integer detail_id = pactRecDetSKHTMapper.queryMaxDetailId(mapVo);
				if(detail_id==null) {
					detail_id=0;
				}
				for (PactRecDetSKHTEntity pactRecDetSKHTEntity : listVo) {
					pactRecDetSKHTEntity.setDetail_id(++detail_id);
				}
				pactRecDetSKHTMapper.deleteByRecCode(listVo);
				pactRecDetSKHTMapper.addBatch(listVo);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactRecMainSKHTPrint(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactRecMainSKHTMapper.queryForPrint(mapVo);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactRecMainSKHTDetail(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactRecMainSKHTMapper.queryPactRecMainSKHTDetail(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactRecMainSKHTMapper.queryPactRecMainSKHTDetail(entityMap, rowBounds);
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
	public String queryPactTypesSKHTByCode(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			Map<String, Object> entity = pactMainSKHTMapper.queryForDepRec(mapVo);
			return ChdJson.toJson(entity);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
