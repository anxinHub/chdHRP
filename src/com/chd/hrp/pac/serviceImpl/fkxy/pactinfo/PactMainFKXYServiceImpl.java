package com.chd.hrp.pac.serviceImpl.fkxy.pactinfo;

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
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.base.util.FileUtil;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocFKXYMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileFKXYMapper;
import com.chd.hrp.pac.dao.fkxy.pactinfo.PactDetFKXYMapper;
import com.chd.hrp.pac.dao.fkxy.pactinfo.PactMainFKXYMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKXYService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactChangeAfterFKXYService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactMainFKXYService;
import com.github.pagehelper.PageInfo;

@Service("pactMainFKXYService")
public class PactMainFKXYServiceImpl implements PactMainFKXYService {

	private static Logger logger = Logger.getLogger(PactMainFKXYServiceImpl.class);

	@Resource(name = "pactMainFKXYMapper")
	private PactMainFKXYMapper pactMainFKXYMapper;

	@Resource(name = "pactDetFKXYMapper")
	private PactDetFKXYMapper pactDetFKXYMapper;

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;

	@Resource(name = "pactFileFKXYMapper")
	private PactFileFKXYMapper pactFileFKXYMapper;

	@Resource(name = "pactDocFKXYService")
	private PactDocFKXYService pactDocFKXYService;

	@Resource(name = "pactDocFKXYMapper")
	private PactDocFKXYMapper pactDocFKXYMapper;

	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactChangeAfterFKXYService")
	private PactChangeAfterFKXYService pactChangeAfterFKXYService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryPactFKXY(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper.query(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper.query(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactFKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> li = pactMainFKXYMapper.queryPactFKXYMainByTypeAndName(mapVo);
			if(li!=null && !li.isEmpty()) {
				return "{\"error\":\"该条数据已经存在，请重新选择协议类型或重新输入协议名称\",\"state\":\"false\"}";
			}
			pactMainFKXYMapper.add(mapVo);

			if (mapVo.get("sub") != null) {
				List<PactDetFKXYEntity> listVo = JSONArray.parseArray(mapVo.get("sub").toString(),
						PactDetFKXYEntity.class);
				if (!listVo.isEmpty()) {
					Set<Integer> set = new HashSet<>();
					for (PactDetFKXYEntity pactDetFKXYEntity : listVo) {
						set.add(pactDetFKXYEntity.getSubject_id());
					}
					if (set.size() != listVo.size()) {
						throw new SysException("标的物重复");
					}
					Map<String, Object> map = new HashMap<>();
					map.put("group_id", listVo.get(0).getGroup_id());
					map.put("hos_id", listVo.get(0).getHos_id());
					map.put("copy_code", listVo.get(0).getCopy_code());
					Integer detailId = 1;
					for (PactDetFKXYEntity pactDetFKXYEntity : listVo) {
						pactDetFKXYEntity.setPact_code(mapVo.get("pact_code").toString());
						pactDetFKXYEntity.setDetail_id(detailId++);
					}
					pactDetFKXYMapper.addBatch(listVo);
				}
			}

			if (mapVo.get("doc") != null) {
				List<PactDocEntity> doclist = JSONArray.parseArray(mapVo.get("doc").toString(), PactDocEntity.class);
				if (!doclist.isEmpty()) {
					for (PactDocEntity pactDocEntity : doclist) {
						pactDocEntity.setPact_code(mapVo.get("pact_code").toString());
					}
					pactDocFKXYService.addBatchPactDocFKXY(doclist);
				}
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactFKXY(List<PactMainFKXYEntity> listVo) {
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (PactMainFKXYEntity entity : listVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entity.getGroup_id());
				map.put("hos_id", entity.getHos_id());
				map.put("copy_code", entity.getCopy_code());
				map.put("pact_code", entity.getPact_code());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactDocFKXYMapper.query(map);
				for (Map<String, Object> m : list) {
					String file_path = m.get("file_path").toString().substring(9);
					Boolean b = FileUtil.deleteFile(LoadSystemInfo.getProjectUrl() + file_path); // 删除文件
				}
				pactDocFKXYMapper.deleteByPactCode(map);
				listMap.add(map);
			}
			pactDetFKXYMapper.deletePactDetFKXYByPactCode(listMap);
			pactFileFKXYMapper.deleteDocFileByPactCodeList(listMap);
			pactFileFKXYMapper.deleteByPactCodeList(listMap);
			pactChangeService.deleteChangeAndCopy(listMap, "FKXY");
			pactMainFKXYMapper.deleteAllBatch(listVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		} 
	}

	@Override
	public PactMainFKXYEntity queryPactFKXYByPactCode(Map<String, Object> mapVo) {
		try {
			PactMainFKXYEntity entity = pactMainFKXYMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactFKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> li = pactMainFKXYMapper.queryPactFKXYMainByTypeAndName(mapVo);
			int i=0;
			for (Map<String, Object> map : li) {
				if(!map.get("pact_code").toString().equals(mapVo.get("pact_code").toString()) && map.get("pact_type_code").toString().equals( mapVo.get("pact_type_code").toString())) {
					i+=1;
				}
			}
			if(i>0) {
				return "{\"error\":\"该条数据已经存在，请重新输入协议名称\",\"state\":\"false\"}";
			}
			String pact_type_code = "FKXY";
			if (mapVo.get("change_reason") != null) {
				Map<String, Object> map = new HashMap<>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("operator", SessionManager.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("change_date", format.format(new Date()));
				map.put("make_date", format.format(new Date()));
				map.put("maker", SessionManager.getUserId());
				map.put("change_reason", mapVo.get("change_reason"));
				map.put("pact_code", mapVo.get("pact_code"));
				map.put("is_exe", mapVo.get("is_exe"));
				map.put("state", "3");
				// 更新协议变更表
				map.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
				String change_code = pactChangeMapper.queryMaxId(map);
				if (change_code == null) {
					change_code = mapVo.get("pact_code").toString() + "-0";
					map.put("change_code", change_code);
					pactChangeMapper.addPactChangeXY(map);

					// 备份明细表
					map.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
					// 查询主合同
					List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(map);
					map.putAll(before.get(0));
					// 查询明细表
					map.put("table_code_f", "PACT_DET_" + pact_type_code.toUpperCase());
					List<Map<String, Object>> detList = pactChangeMapper.queryChangeBefore(map);

					pactChangeMapper.copyPactMainFKXY(map);
					if (!detList.isEmpty()) {
						map.put("list", detList);
						pactChangeMapper.copyPactDetFKXY(map);
					}
				}
			}

			if (mapVo.get("sub") != null) {
				List<PactDetFKXYEntity> listVo = JSONArray.parseArray(mapVo.get("sub").toString(),
						PactDetFKXYEntity.class);
				if (!listVo.isEmpty()) {
					Set<Integer> set = new HashSet<>();
					for (PactDetFKXYEntity pactDetFKXYEntity : listVo) {
						set.add(pactDetFKXYEntity.getSubject_id());
					}
					if (set.size() != listVo.size()) {
						throw new SysException("标的物重复");
					}

					Map<String, Object> map = new HashMap<>();
					map.put("group_id", listVo.get(0).getGroup_id());
					map.put("hos_id", listVo.get(0).getHos_id());
					map.put("copy_code", listVo.get(0).getCopy_code());
					map.put("pact_code", listVo.get(0).getPact_code());
					Integer detailId = pactDetFKXYMapper.queryMaxDetailId(map);
					if (detailId == null) {
						detailId = 1;
						for (PactDetFKXYEntity pactDetFKXYEntity : listVo) {
							pactDetFKXYEntity.setDetail_id(detailId++);
						}
					} else {
						for (PactDetFKXYEntity pactDetFKXYEntity : listVo) {
							if (pactDetFKXYEntity.getDetail_id() == null) {
								pactDetFKXYEntity.setDetail_id(++detailId);
							}
						}
					}
					pactDetFKXYMapper.deleteAllBatch(listVo);
					pactDetFKXYMapper.addBatch(listVo);
				}
			}

			if (mapVo.get("doc") != null) {
				List<PactDocEntity> doclist = JSONArray.parseArray(mapVo.get("doc").toString(), PactDocEntity.class);
				if (!doclist.isEmpty()) {
					for (PactDocEntity pactDocEntity : doclist) {
						pactDocEntity.setPact_code(mapVo.get("pact_code").toString());
					}
					pactDocFKXYService.deletePactDocFKXY(doclist);
					pactDocFKXYService.addBatchPactDocFKXY(doclist);
				}
			}
			pactMainFKXYMapper.update(mapVo);
			mapVo.put("state", "3");
			if (mapVo.get("change_reason") != null) {
				mapVo.put("change_reason", mapVo.get("change_reason"));
				pactChangeService.addPactMainXYChange(mapVo, "FKXY");
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkPactMainFKXY(List<String> listVo, String check, String is_init) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("list", listVo);

			String returnMsg = null;
			switch (check) {
			case "check":
				map.put("check_date", new Date());
				map.put("checker", SessionManager.getUserId());
				map.put("state", "02");
				Map<String, Object> checkPactMainFKXY = checkPactMainFKXY(map, listVo);
				String err = (String) checkPactMainFKXY.get("err_msg");
				listVo = (List<String>) checkPactMainFKXY.get("list");
				if (err.length() == 0 && !listVo.isEmpty()) {
					returnMsg = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
				} else if (err.length() != 0 && listVo.isEmpty()) {
					returnMsg = "{\"error\":\"" + err + "\"}";
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
				map.put("state", "03");
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
				map.put("state", "03");
				map.put("state_code", "15");
				map.put("file_date", new Date());
				map.put("filer", SessionManager.getUserId());
				returnMsg = "{\"msg\":\"归档成功.\",\"state\":\"true\"}";
				break;
			case "unfile":
				map.put("state", "03");
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"取消归档成功.\",\"state\":\"true\"}";
				break;
			case "stop":
				map.put("state", "03");
				map.put("state_code", "14");
				map.put("stop_date", new Date());
				map.put("stoper", SessionManager.getUserId());
				returnMsg = "{\"msg\":\"中止成功.\",\"state\":\"true\"}";
				break;
			case "unstop":
				map.put("state", "03");
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"取消中止成功.\",\"state\":\"true\"}";
				break;
			case "unuse":
				map.put("state", "03");
				map.put("state_code", "13");
				returnMsg = "{\"msg\":\"作废成功.\",\"state\":\"true\"}";
				break;
			case "recover":
				map.put("state", "03");
				map.put("state_code", "12");
				returnMsg = "{\"msg\":\"恢复成功.\",\"state\":\"true\"}";
				break;
			default:
				return "{\"error\":请求有误\"\"}";
			}

			if (!listVo.isEmpty()) {
				if("unconfir".equals(check)) {
					StringBuffer err = new StringBuffer();
					for (String pact_code : listVo) {
						Map<String, Object> hasMap = new HashMap<>();
						hasMap.put("group_id", SessionManager.getGroupId());
						hasMap.put("hos_id", SessionManager.getHosId());
						hasMap.put("copy_code", SessionManager.getCopyCode());
						hasMap.put("pact_code", pact_code);
						PactMainFKXYEntity entity = pactMainFKXYMapper.queryByCode(hasMap);
						hasMap.put("sup_id", entity.getSup_id());
						hasMap.put("sup_no",entity.getSup_no());
						List<Map<String, Object>> listMap = pactChangeMapper.queryPactMainChangeFKXY(hasMap);
						if(!listMap.isEmpty()) {
							err.append("取消确认失败！");
						}
					}
					if(err.length() > 0) {
						return "{\"error\":\"已经存在变动单，取消确认失败！.\",\"state\":\"false\"}";
					}
				}
				pactMainFKXYMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	private Map<String, Object> checkPactMainFKXY(Map<String, Object> map, List<String> listVo) {
		StringBuffer err = new StringBuffer();
		List<String> finish_pact_code = new ArrayList<>();
		map.put("list", listVo);
		List<PactDetFKXYEntity> list = pactDetFKXYMapper.queryByPactCodeList(map);
		if (list.isEmpty()) {
			err.append("审核失败，所有协议无付款计划明细！");
		} else {
			for (String pact_code : listVo) {
				List<PactDetFKXYEntity> execlist = new ArrayList<>();
				for (PactDetFKXYEntity pactDetFKXYEntity : list) {
					if (pact_code.equals(pactDetFKXYEntity.getPact_code())) {
						execlist.add(pactDetFKXYEntity);
					}
				}
				map.put("pact_code", pact_code);
				PactMainFKXYEntity entity = pactMainFKXYMapper.queryByCode(map);
				if (!execlist.isEmpty()) {
					finish_pact_code.add(pact_code);
				} else {
					err.append("协议[" + entity.getPact_name() + "]审核失败，无标的物明细！");
				}
			}
		}
		map.put("err_msg", err.toString());
		map.put("list", finish_pact_code);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryPactFKXYForDeadline(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper
						.queryPactFKXYForDeadline(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper
						.queryPactFKXYForDeadline(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryPactMainFKXYByStateCode(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper
						.queryPactMainFKXYByStateCode(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper
						.queryPactMainFKXYByStateCode(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	/*
	 * 协议执行总额预警查询
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryPactMoneyProgress(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper
						.queryPactMoneyProgress(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainFKXYEntity> list = (List<PactMainFKXYEntity>) pactMainFKXYMapper
						.queryPactMoneyProgress(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
