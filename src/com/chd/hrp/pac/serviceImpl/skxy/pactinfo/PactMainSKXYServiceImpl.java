package com.chd.hrp.pac.serviceImpl.skxy.pactinfo;

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
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocSKXYMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileSKXYMapper;
import com.chd.hrp.pac.dao.skxy.pactinfo.PactDetSKXYMapper;
import com.chd.hrp.pac.dao.skxy.pactinfo.PactMainSKXYMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKXYService;
import com.chd.hrp.pac.service.fkxy.pactinfo.PactChangeAfterFKXYService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactChangeAfterSKXYService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactMainSKXYService;
import com.github.pagehelper.PageInfo;

@Service("pactMainSKXYService")
public class PactMainSKXYServiceImpl implements PactMainSKXYService {

	private static Logger logger = Logger.getLogger(PactMainSKXYServiceImpl.class);

	@Resource(name = "pactMainSKXYMapper")
	private PactMainSKXYMapper pactMainSKXYMapper;

	@Resource(name = "pactDetSKXYMapper")
	private PactDetSKXYMapper pactDetSKXYMapper;

	@Resource(name = "pactFileSKXYMapper")
	private PactFileSKXYMapper pactFileSKXYMapper;

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;
	
	@Resource(name = "pactDocSKXYService")
	private PactDocSKXYService pactDocSKXYService;
	
	@Resource(name = "pactDocSKXYMapper")
	private PactDocSKXYMapper pactDocSKXYMapper;
	
	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactChangeAfterSKXYService")
	private PactChangeAfterSKXYService pactChangeAfterSKXYService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryPactSKXY(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainSKXYEntity> list = (List<PactMainSKXYEntity>) pactMainSKXYMapper.query(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainSKXYEntity> list = (List<PactMainSKXYEntity>) pactMainSKXYMapper.query(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String addPactSKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> li = pactMainSKXYMapper.queryPactSKXYMainByTypeAndName(mapVo);
			if(li!=null && !li.isEmpty()) {
				return "{\"error\":\"该条数据已经存在，请重新选择协议类型或重新输入协议名称\",\"state\":\"false\"}";
			}
			pactMainSKXYMapper.add(mapVo);
			
			if (mapVo.get("sub") != null) {
				List<PactDetSKXYEntity> list = JSONArray.parseArray(mapVo.get("sub").toString(), PactDetSKXYEntity.class);
				if (!list.isEmpty()) {
					Set<Integer> set =new HashSet<>();
					for (PactDetSKXYEntity pactDetSKXYEntity : list) {
						set.add(pactDetSKXYEntity.getSubject_id());
					}
					if (set.size() != list.size()) {
						throw new SysException("标的物重复");
					}
					
					Map<String, Object> map = new HashMap<>();
					map.put("group_id", list.get(0).getGroup_id());
					map.put("hos_id", list.get(0).getHos_id());
					map.put("copy_code", list.get(0).getCopy_code());
					Integer detailId = 1;
					for (PactDetSKXYEntity pactDetSKXYEntity : list) {
						pactDetSKXYEntity.setPact_code(mapVo.get("pact_code").toString());
						pactDetSKXYEntity.setDetail_id(detailId++);
					}
					pactDetSKXYMapper.addBatch(list);
				}
			}
			if (mapVo.get("doc") != null) {
				List<PactDocEntity> doclist = JSONArray.parseArray(mapVo.get("doc").toString(), PactDocEntity.class);
				if (!doclist.isEmpty()) {
					for (PactDocEntity pactDocEntity : doclist) {
						pactDocEntity.setPact_code(mapVo.get("pact_code").toString());
					}
					pactDocSKXYService.addBatchPactDocSKXY(doclist);
				}
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactSKXY(List<PactMainSKXYEntity> listVo) {
		try {
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (PactMainSKXYEntity entity : listVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entity.getGroup_id());
				map.put("hos_id", entity.getHos_id());
				map.put("copy_code", entity.getCopy_code());
				map.put("pact_code", entity.getPact_code());
				List<PactDocEntity> list =  (List<PactDocEntity>) pactDocSKXYMapper.query(map);
				for (PactDocEntity pactDocEntity : list) {
					String file_path = pactDocEntity.getFile_path().substring(9);
					Boolean b = FileUtil.deleteFile( LoadSystemInfo.getProjectUrl()+file_path); //删除文件
				}
				pactDocSKXYMapper.deleteByPactCode(map);
				listMap.add(map);
			}
			pactDetSKXYMapper.deletePactDetSKXYByPactCode(listMap);
			pactFileSKXYMapper.deleteDocFileByPactCodeList(listMap);
			pactFileSKXYMapper.deleteByPactCodeList(listMap);
			pactChangeService.deleteChangeAndCopy(listMap, "SKXY");
			pactMainSKXYMapper.deleteAllBatch(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactMainSKXYEntity queryPactSKXYByPactCode(Map<String, Object> mapVo) {
		try {
			return pactMainSKXYMapper.queryByCode(mapVo);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactSKXY(Map<String, Object> mapVo) { 
		try {
			List<Map<String, Object>> li = pactMainSKXYMapper.queryPactSKXYMainByTypeAndName(mapVo);
			int i=0;
			for (Map<String, Object> map : li) {
				if(!map.get("pact_code").toString().equals(mapVo.get("pact_code").toString()) && map.get("pact_type_code").toString().equals( mapVo.get("pact_type_code").toString())) {
					i+=1;
				}
			}
			if(i>0) {
				return "{\"error\":\"该条数据已经存在，请重新输入协议名称\",\"state\":\"false\"}";
			}
			String pact_type_code = "SKXY";
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
				map.put("state", "3");
				// 更新收款协议变更表
				map.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
				String change_code = pactChangeMapper.queryMaxId(map);
				if (change_code == null) {
					change_code = mapVo.get("pact_code").toString() + "-0";
					map.put("change_code", change_code);
					pactChangeMapper.addPactChangeXY(map);
					// 备份明细表
					map.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
					// 查询主协议
					List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(map);
					map.putAll(before.get(0));
					// 查询明细表
					map.put("table_code_f", "PACT_DET_" + pact_type_code.toUpperCase());
					List<Map<String, Object>> detList = pactChangeMapper.queryChangeBefore(map);
					
					pactChangeMapper.copyPactMainSKXY(map);
					if (!detList.isEmpty()) {
						map.put("list", detList);
						pactChangeMapper.copyPactDetSKXY(map);
					}
				}
			}
			
			List<PactDetSKXYEntity> list = JSONArray.parseArray(mapVo.get("sub").toString(), PactDetSKXYEntity.class);
			if (!list.isEmpty()) {
				Set<Integer> set =new HashSet<>();
				for (PactDetSKXYEntity pactDetSKXYEntity : list) {
					set.add(pactDetSKXYEntity.getSubject_id());
				}
				if (set.size() != list.size()) {
					throw new SysException("标的物重复");
				}
				Map<String, Object> map = new HashMap<>();
				map.put("group_id", mapVo.get("group_id"));
				map.put("hos_id", mapVo.get("hos_id"));
				map.put("copy_code", mapVo.get("copy_code"));
				map.put("pact_code", mapVo.get("pact_code"));
				Integer detailId = pactDetSKXYMapper.queryMaxDetailId(map);
				if (detailId == null) {
					detailId = 1;
					for (PactDetSKXYEntity pactDetSKXYEntity : list) {
						pactDetSKXYEntity.setDetail_id(detailId++);
					}
				} else {
					for (PactDetSKXYEntity pactDetSKXYEntity : list) {
						if (pactDetSKXYEntity.getDetail_id() == null) {
							pactDetSKXYEntity.setDetail_id(++detailId);
						}
					}
				}
				pactDetSKXYMapper.deleteAllBatch(list);
				pactDetSKXYMapper.addBatch(list);
			}
			if (mapVo.get("doc") != null) {
				List<PactDocEntity> doclist = JSONArray.parseArray(mapVo.get("doc").toString(), PactDocEntity.class);
				if (!doclist.isEmpty()) {
					for (PactDocEntity pactDocEntity : doclist) {
						pactDocEntity.setPact_code(mapVo.get("pact_code").toString());
					}
					pactDocSKXYService.deletePactDocSKXY(doclist);
					pactDocSKXYService.addBatchPactDocSKXY(doclist);
				}
			}
			pactMainSKXYMapper.update(mapVo);
			mapVo.put("state","3");
			if (mapVo.get("change_reason") != null) {
				mapVo.put("change_reason", mapVo.get("change_reason"));
				pactChangeService.addPactMainXYChange(mapVo, "SKXY");
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkPactMainSKXY(List<String> listVo, String check, String is_init) {
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
				Map<String, Object> checkPactMainFKXY = checkPactMainSKXY(map, listVo);
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
						PactMainSKXYEntity entity = pactMainSKXYMapper.queryByCode(hasMap);
						hasMap.put("cus_id", entity.getCus_id());
						hasMap.put("cus_no",entity.getCus_no());
						List<Map<String, Object>> listMap = pactChangeMapper.queryPactMainChangeSKXY(hasMap);
						if(!listMap.isEmpty()) {
							err.append("取消确认失败！");
						}
					}
					if(err.length() > 0) {
						return "{\"error\":\"已经存在变动单，取消确认失败！.\",\"state\":\"false\"}";
					}
				}
				pactMainSKXYMapper.updateState(map);
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	private Map<String, Object> checkPactMainSKXY(Map<String, Object> map, List<String> listVo) {
		StringBuffer err = new StringBuffer();
		List<String> finish_pact_code = new ArrayList<>();
		map.put("list", listVo);
		List<PactDetSKXYEntity> list = pactDetSKXYMapper.queryByPactCodeList(map);
		if (list.isEmpty()) {
			err.append("审核失败，所有协议无付款计划明细！");
		} else {
			for (String pact_code : listVo) {
				List<PactDetSKXYEntity> execlist = new ArrayList<>();
				for (PactDetSKXYEntity pactDetSKXYEntity : list) {
					if (pact_code.equals(pactDetSKXYEntity.getPact_code())) {
						execlist.add(pactDetSKXYEntity);
					}
				}
				map.put("pact_code", pact_code);
				PactMainSKXYEntity entity = pactMainSKXYMapper.queryByCode(map);
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
	public String queryPactSKXYForDeadline(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainSKXYEntity> list = (List<PactMainSKXYEntity>) pactMainSKXYMapper
						.queryPactSKXYForDeadline(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainSKXYEntity> list = (List<PactMainSKXYEntity>) pactMainSKXYMapper
						.queryPactSKXYForDeadline(mapVo, rowBounds);
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
	public String queryPactMainSKXYByStateCode(Map<String, Object> mapVo) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) mapVo.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainSKXYEntity> list = (List<PactMainSKXYEntity>) pactMainSKXYMapper
						.queryPactMainSKXYByStateCode(mapVo);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainSKXYEntity> list = (List<PactMainSKXYEntity>) pactMainSKXYMapper
						.queryPactMainSKXYByStateCode(mapVo, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
