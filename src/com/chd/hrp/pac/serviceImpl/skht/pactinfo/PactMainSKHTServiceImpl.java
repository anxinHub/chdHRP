package com.chd.hrp.pac.serviceImpl.skht.pactinfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.chd.hrp.budg.dao.project.information.HosProjDictMapper;
import com.chd.hrp.budg.entity.HosProjDict;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocSKHTMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileSKHTMapper;
import com.chd.hrp.pac.dao.skht.guarantee.PactDepRecSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactDetSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactDetSourSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactMainSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactPlanSKHTMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSKHTEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactPlanSKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKHTService;
import com.chd.hrp.pac.service.skht.pactinfo.PactMainSKHTService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactMainSKHTService")
public class PactMainSKHTServiceImpl implements PactMainSKHTService {

	private static Logger logger = Logger.getLogger(PactMainSKHTServiceImpl.class);

	@Resource(name = "pactMainSKHTMapper")
	private PactMainSKHTMapper pactMainSKHTMapper;

	@Resource(name = "hosProjDictMapper")
	private HosProjDictMapper projDictMapper;

	@Resource(name = "pactPlanSKHTMapper")
	private PactPlanSKHTMapper pactPlanSKHTMapper;

	@Resource(name = "pactDetSKHTMapper")
	private PactDetSKHTMapper pactDetSKHTMapper;

	@Resource(name = "pactDocSKHTMapper")
	private PactDocSKHTMapper pactDocSKHTMapper;

	@Resource(name = "pactDetSourSKHTMapper")
	private PactDetSourSKHTMapper detSourSKHTMapper;

	@Resource(name = "pactDepRecSKHTMapper")
	private PactDepRecSKHTMapper pactDepRecSKHTMapper;

	@Resource(name = "pactDocSKHTService")
	private PactDocSKHTService pactDocSKHTService;

	@Resource(name = "pactFileSKHTMapper")
	private PactFileSKHTMapper pactFileSKHTMapper;

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;
  
	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public String queryPactMainSKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainSKHTEntity> query = (List<PactMainSKHTEntity>) pactMainSKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainSKHTEntity> list = (List<PactMainSKHTEntity>) pactMainSKHTMapper.query(entityMap,
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
	public String queryHosProjDict(Map<String, Object> mapVo) {
		try {
			@SuppressWarnings("unchecked")
			List<HosProjDict> list = (List<HosProjDict>) projDictMapper.query(mapVo);
			return ChdJson.toJson(list);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public void addPactMainSKHT(Map<String, Object> page) {
		try {
			pactMainSKHTMapper.add(page);

			if (page.get("sub") != null) {
				List<PactDetSKHTEntity> detList = JSONArray.parseArray(page.get("sub").toString(),
						PactDetSKHTEntity.class);
				if (!detList.isEmpty()) {
					addPactDetSKHT(detList, page.get("pact_code").toString());
					
					List<PactPlanSKHTEntity> list = new ArrayList<>();
					if (page.get("plan") == null || "[]".equals(page.get("plan").toString())) {
						if (!page.get("pact_money").toString().equals("0")) {
							PactPlanSKHTEntity entity = new PactPlanSKHTEntity();
							entity.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
							entity.setHos_id(Integer.parseInt(SessionManager.getHosId()));
							entity.setCopy_code(SessionManager.getCopyCode());
							entity.setRec_id(1);
							entity.setSummary("全款");
							String end_date = (String) page.get("end_date");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							entity.setRec_date(dateFormat.parse(end_date));
							//entity.setSource_id(1);
							//entity.setSource_name("自筹资金");
							entity.setPlan_money(Double.valueOf(page.get("pact_money").toString()));
							list.add(entity);
						}
					} else {
						list = JSONArray.parseArray(page.get("plan").toString(), PactPlanSKHTEntity.class);
					}
					if (!list.isEmpty()) {
						addPactPlanSKHT(list, page.get("pact_code").toString());
					}
				}
			}
			
			if (page.get("doc") != null) {
				List<PactDocEntity> listVo = JSONArray.parseArray(page.get("doc").toString(), PactDocEntity.class);
				if (!listVo.isEmpty()) {
					for (PactDocEntity pactDocEntity : listVo) {
						pactDocEntity.setPact_code(page.get("pact_code").toString());
					}
					pactDocSKHTService.addBatchPactDocSKHT(listVo);
				}
			}

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	/**
	 * 添加付款合同明细
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	private String addPactDetSKHT(List<PactDetSKHTEntity> listVo, String pact_code) {
		try {
			Set<Integer> set =new HashSet<>();
			for (PactDetSKHTEntity pactDetSKHTEntity : listVo) {
				set.add(pactDetSKHTEntity.getSubject_id());
			}
			if (set.size() != listVo.size()) {
				throw new SysException("标的物重复");
			}
			
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("pact_code", listVo.get(0).getPact_code());

			Integer detailId = pactDetSKHTMapper.queryMaxDetailId(entityMap);
			if (detailId == null) {
				detailId = 1;
				for (PactDetSKHTEntity pactDetSKHTEntity : listVo) {
					pactDetSKHTEntity.setPact_code(pact_code);
					pactDetSKHTEntity.setDetail_id(detailId++);
				}
			} else {
				for (PactDetSKHTEntity pactDetSKHTEntity : listVo) {
					if (pactDetSKHTEntity.getDetail_id() == null) {
						pactDetSKHTEntity.setPact_code(pact_code);
						pactDetSKHTEntity.setDetail_id(++detailId);
					}
				}
			}

			pactDetSKHTMapper.deleteByPactCode(entityMap);
			pactDetSKHTMapper.addBatch(listVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	/**
	 * 添加付款计划
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	private String addPactPlanSKHT(List<PactPlanSKHTEntity> listVo, String pact_code) {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("pact_code", listVo.get(0).getPact_code());

			Integer detail_id = pactPlanSKHTMapper.queryMaxDetailId(entityMap);
			if (detail_id == null) {
				detail_id = 1;
				for (PactPlanSKHTEntity pactPlanSKHTEntity : listVo) {
					pactPlanSKHTEntity.setPact_code(pact_code);
					pactPlanSKHTEntity.setPlan_detail_id(detail_id++);
				}
			} else {
				for (PactPlanSKHTEntity pactPlanSKHTEntity : listVo) {
					if (pactPlanSKHTEntity.getPlan_detail_id() == null) {
						pactPlanSKHTEntity.setPact_code(pact_code);
						pactPlanSKHTEntity.setPlan_detail_id(++detail_id);
					}
				}
			}
			pactPlanSKHTMapper.deleteByPactCode(entityMap);
			pactPlanSKHTMapper.addBatch(listVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactMainSKHTEntity queryPactMainSKHTByCode(Map<String, Object> mapVo) {
		try {
			PactMainSKHTEntity entity = pactMainSKHTMapper.queryByCode(mapVo);
			return entity;
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactMainSKHT(List<PactMainSKHTEntity> listVo) {
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (PactMainSKHTEntity entity : listVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entity.getGroup_id());
				map.put("hos_id", entity.getHos_id());
				map.put("copy_code", entity.getCopy_code());
				map.put("pact_code", entity.getPact_code());
				listMap.add(map);
			}

			pactPlanSKHTMapper.deleteByPactCodeList(listMap);
			pactDetSKHTMapper.deleteByPactCodeList(listMap);
			pactDocSKHTMapper.deleteByPactCodeList(listMap);
			//detSourSKHTMapper.deleteByPactCodeList(listMap);
			pactDepRecSKHTMapper.deleteByPactCodeList(listMap);
			pactFileSKHTMapper.deleteDocFileByPactCodeList(listMap);
			pactFileSKHTMapper.deleteByPactCodeList(listMap);
			pactChangeService.deleteChangeAndCopy(listMap, "SKHT");
			pactMainSKHTMapper.deleteAllBatch(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
   /**
    * editor:cyw  2020-03-06
    * 收款合同修改方法
    * 增加注释，方便查看代码
    */
	@Override
	public String updatePactMainSKHT(Map<String, Object> mapVo) {
		try {
			/**
			 * 如果需要添加变更记录，首先添加表更前记录，然后修改表，修改完成后再次备份
			 */
			String pact_type_code = "SKHT";
			if (mapVo.get("change_reason") != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("operator", SessionManager.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("change_date", format.format(new Date()));
				map.put("change_reason", mapVo.get("change_reason"));
				map.put("pact_code", mapVo.get("pact_code"));
				// 更新合同变更表
				map.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
				String change_code = pactChangeMapper.queryMaxId(map);
				if ((change_code == null)&&(!"".equals(change_code))) {
					change_code = mapVo.get("pact_code").toString() + "-0";
					map.put("change_code", change_code);
					pactChangeMapper.add(map);
                    
					// 备份明细表
					map.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
					// 查询主合同
					List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(map);
					map.putAll(before.get(0));
					// 查询明细表
					map.put("table_code_f", "PACT_DET_" + pact_type_code.toUpperCase());
					List<Map<String, Object>> deList = pactChangeMapper.queryChangeBefore(map);
					
					// 查询计划表
					//changeMoney(mapVo, map, pact_type_code);
					map.put("table_code_f", "PACT_PLAN_" + pact_type_code.toUpperCase());
					List<Map<String, Object>>  planList = pactChangeMapper.queryChangeBefore(map);
					pactChangeMapper.copyPactMainSKHT(map);
					if (!deList.isEmpty()) {
						map.put("list", deList);
						pactChangeMapper.copyPactDetSKHT(map);
					}
					if (!planList.isEmpty()) {
						map.put("list", planList);
						pactChangeMapper.copyPactPlanSKHT(map);
					}
				}
			}
			///下面更新
			pactMainSKHTMapper.update(mapVo);

			if (mapVo.get("sub") != null) {
				List<PactDetSKHTEntity> detList = JSONArray.parseArray(mapVo.get("sub").toString(),
						PactDetSKHTEntity.class);
				if (!detList.isEmpty()) {
					addPactDetSKHT(detList, mapVo.get("pact_code").toString());
					
					List<PactPlanSKHTEntity> list = new ArrayList<>();
					if (mapVo.get("plan") == null || "[]".equals(mapVo.get("plan").toString())) {
						if (!mapVo.get("pact_money").toString().equals("0")) {
							PactPlanSKHTEntity entity = new PactPlanSKHTEntity();
							entity.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
							entity.setHos_id(Integer.parseInt(SessionManager.getHosId()));
							entity.setCopy_code(SessionManager.getCopyCode());
							entity.setPact_code(mapVo.get("pact_code").toString());
							entity.setRec_id(1);
							entity.setSummary("全款");
							String end_date = (String) mapVo.get("end_date");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							entity.setRec_date(dateFormat.parse(end_date));
							//entity.setSource_id(1);
							//entity.setSource_name("自筹资金");
							entity.setPlan_money(Double.valueOf(mapVo.get("pact_money").toString()));
							list.add(entity);
						}
					} else {
						list = JSONArray.parseArray(mapVo.get("plan").toString(), PactPlanSKHTEntity.class);
					}
					if (!list.isEmpty()) {
						addPactPlanSKHT(list, mapVo.get("pact_code").toString());
					}
				}
			}
		
			if (mapVo.get("doc") != null) {
				List<PactDocEntity> listVo = JSONArray.parseArray(mapVo.get("doc").toString(), PactDocEntity.class);
				if (!listVo.isEmpty()) {
					for (PactDocEntity pactDocEntity : listVo) {
						pactDocEntity.setPact_code(mapVo.get("pact_code").toString());
					}
					pactDocSKHTService.addBatchPactDocSKHT(listVo);
				}
			}
            ///修改成功后再次备份修改后数据
			if (mapVo.get("change_reason") != null) {
				pactChangeService.addPactMainChange(mapVo, "SKHT");
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String updatePactMainSKHTState(List<String> listVo, String check, String is_init) {
		try {
			String returnMsg = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("state", "03");

			switch (check) {
			case "check":
				map.put("check_date", new Date());
				map.put("checker", SessionManager.getUserId());
				map.put("state", "02");
				Map<String, Object> checkPactMainSKHT = checkPactMainSKHT(map, listVo);
				String err = (String) checkPactMainSKHT.get("err_msg");
				listVo = (List<String>) checkPactMainSKHT.get("list");
				if (err.length() == 0 && !listVo.isEmpty()) {
					returnMsg = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
				} else if (err.length() != 0 && listVo.isEmpty()) {
					returnMsg = "{\"error\":\""+err+"\"}";
				} else if (err.length() != 0 && !listVo.isEmpty()) {
					returnMsg = "{\"msg\":\"部分审核成功，其中" + err + "\",\"state\":\"true\"}";
				}
				break;
			case "uncheck":
				map.put("state", "01");
				returnMsg = "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
				break;
			case "confir":
				map.put("confirm_date", new Date());
				map.put("confirmer", SessionManager.getUserId());
				if (!"1".equals(is_init))
					map.put("state_code", "12");
				returnMsg = "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
				break;
			case "unconfir":
				map.put("state", "02");
				if (!"1".equals(is_init))
					map.put("state_code", "11");
				returnMsg = "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
				break;
			case "file":
				map.put("state_code", "15");
				map.put("file_date", new Date());
				map.put("filer", SessionManager.getUserId());
				returnMsg = "{\"msg\":\"归档成功.\",\"state\":\"true\"}";
				break;
			case "unfile":
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"取消归档成功.\",\"state\":\"true\"}";
				break;
			case "stop":
				map.put("state_code", "14");
				map.put("stop_date", new Date());
				map.put("stoper", SessionManager.getUserId());
				returnMsg = "{\"msg\":\"中止成功.\",\"state\":\"true\"}";
				break;
			case "unstop":
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"取消中止成功.\",\"state\":\"true\"}";
				break;
			case "unuse":
				map.put("state_code", "13");
				returnMsg = "{\"msg\":\"作废成功.\",\"state\":\"true\"}";
				break;
			case "recover":
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"恢复成功.\",\"state\":\"true\"}";
				break;
			default:
				return "{\"error\":请求有误\"\"}";
			}
			
			
			///判断所选合同是否有签订后变更单，如果有不可以取消确认
			
			if (!listVo.isEmpty()) {
				map.put("list", listVo);
				String number=pactMainSKHTMapper.queryExitsPactOthers(map);
				int count=Integer.parseInt(number);
				if((count>0)&&(check.equals("unconfir"))){
					returnMsg = "{\"msg\":\"取消失败，合同存在变更单.\",\"state\":\"true\"}";
				}else{
				pactMainSKHTMapper.updateState(map);
				}
			}

			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	private Map<String, Object> checkPactMainSKHT(Map<String, Object> map, List<String> listVo) {
		StringBuffer err = new StringBuffer();
		List<String> finish_pact_code = new ArrayList<>();
		map.put("list", listVo);
		List<PactDetSKHTEntity> list = pactDetSKHTMapper.queryByPactCodeList(map);
		if (list.isEmpty()) {
			err.append("审核失败，所有合同无付款计划明细！");
		} else {
			for (String pact_code : listVo) {
				Double money = 0d;
				for (PactDetSKHTEntity pactDetSKHTEntity : list) {
					if (pact_code.equals(pactDetSKHTEntity.getPact_code())) {
						money += pactDetSKHTEntity.getMoney();
					}
				}
				map.put("pact_code", pact_code);
				PactMainSKHTEntity entity = pactMainSKHTMapper.queryByCode(map);
				Double pact_money = entity.getPact_money();
				int compareTo = BigDecimal.valueOf(money).compareTo(BigDecimal.valueOf(pact_money));
				if (compareTo == 0 && money != 0 && pact_money != 0) {
					@SuppressWarnings("unchecked")
					List<PactPlanSKHTEntity> planList = (List<PactPlanSKHTEntity>) pactPlanSKHTMapper.query(map);
					if (planList.isEmpty()) {
						err.append("合同[" + entity.getPact_name() + "]审核失败，无付款计划明细！");
					} else {
						Double plan_money = 0d;
						for (PactPlanSKHTEntity pactPlanSKHTEntity : planList) {
							plan_money += pactPlanSKHTEntity.getPlan_money();
						}
						int compare = BigDecimal.valueOf(money).compareTo(BigDecimal.valueOf(plan_money));
						if (compare == 0 && plan_money != 0 && money != 0) {
							finish_pact_code.add(pact_code);
						} else {
							err.append("合同[" + entity.getPact_name() + "]审核失败，付款计划金额与合同金额不一致！");
						}
					}
				} else {
					err.append("合同[" + entity.getPact_name() + "]审核失败，无标的物明细！");
				}
			}
		}
		map.put("err_msg", err.toString());
		map.put("list", finish_pact_code);
		return map;
	}

	@Override
	public String queryPactMainSKHTByStateCode(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainSKHTEntity> query = (List<PactMainSKHTEntity>) pactMainSKHTMapper
						.queryByStateCode(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainSKHTEntity> list = (List<PactMainSKHTEntity>) pactMainSKHTMapper
						.queryByStateCode(entityMap, rowBounds);

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
	public String queryPactMainSKHTForWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainSKHTMapper.queryPactMainSKHTForWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainSKHTMapper.queryPactMainSKHTForWarning(entityMap, rowBounds);
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
	public List<Map<String, Object>> queryPactMainSKHTForWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainSKHTMapper.queryPactMainSKHTForWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMainSKHTForRetWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainSKHTMapper.queryPactMainSKHTForRetWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainSKHTMapper.queryPactMainSKHTForRetWarning(entityMap,
						rowBounds);
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
	public List<Map<String, Object>> queryPactMainSKHTForRetWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainSKHTMapper.queryPactMainSKHTForRetWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMainSKHTForPayWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainSKHTMapper.queryPactMainSKHTForPayWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainSKHTMapper.queryPactMainSKHTForPayWarning(entityMap,
						rowBounds);
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
	public List<Map<String, Object>> queryPactMainSKHTForPayWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainSKHTMapper.queryPactMainSKHTForPayWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryPactSKHTMainByTypeAndName(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			List<Map<String, Object>> li = pactMainSKHTMapper.queryPactSKHTMainByTypeAndName(entityMap);
			return li;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}
}
