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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocSKXYMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileSKXYMapper;
import com.chd.hrp.pac.dao.skxy.pactinfo.PactChangeAfterSKXYMapper;
import com.chd.hrp.pac.dao.skxy.pactinfo.PactDetSKXYMapper;
import com.chd.hrp.pac.dao.skxy.pactinfo.PactMainSKXYMapper;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKXYService;
import com.chd.hrp.pac.service.skxy.pactinfo.PactChangeAfterSKXYService;

@Service("pactChangeAfterSKXYService")
public class PactChangeAfterSKXYServiceImpl implements PactChangeAfterSKXYService {

	private static Logger logger = Logger.getLogger(PactChangeAfterSKXYServiceImpl.class);

	@Resource(name = "pactMainSKXYMapper")
	private PactMainSKXYMapper pactMainSKXYMapper;

	@Resource(name = "pactDetSKXYMapper")
	private PactDetSKXYMapper pactDetSKXYMapper;

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;

	@Resource(name = "pactFileSKXYMapper")
	private PactFileSKXYMapper pactFileSKXYMapper;

	@Resource(name = "pactDocSKXYService")
	private PactDocSKXYService pactDocSKXYService;

	@Resource(name = "pactDocSKXYMapper")
	private PactDocSKXYMapper pactDocSKXYMapper;

	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactChangeAfterSKXYMapper")
	private PactChangeAfterSKXYMapper pactChangeAfterSKXYMapper;

	@Override
	public String addPactSKXYC(Map<String, Object> mapVo, String pact_type_code) {
		try {
			int err = 0;
			Map<String, Object> hasMap = new HashMap<>();
			hasMap.put("group_id", SessionManager.getGroupId());
			hasMap.put("hos_id", SessionManager.getHosId());
			hasMap.put("copy_code", SessionManager.getCopyCode());
			hasMap.put("pact_code", mapVo.get("pact_code"));
			hasMap.put("cus_id", mapVo.get("cus_id"));
			hasMap.put("cus_no", mapVo.get("cus_no"));
			hasMap.put("is_exe", mapVo.get("is_exe"));
			List<Map<String, Object>> listMap = pactChangeMapper.queryPactMainChangeSKXY(hasMap);
			for (Map<String, Object> m : listMap) {
				if (Integer.valueOf(m.get("state").toString())!=3) {
					err += 1;
				}
			}
			if(err>0) {
				return "{\"error\":\"添加失败！存在未确认的变动单.\"}";
			}else {
				Map<String, Object> map = new HashMap<>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("operator", SessionManager.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("change_date", format.format(new Date()));
				map.put("change_reason",  "签订后变更");
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

					pactChangeMapper.copyPactMainSKXY(map);
					if (!detList.isEmpty()) {
						map.put("list", detList);
						pactChangeMapper.copyPactDetSKXY(map);
					}
				}
				pactChangeService.addPactMainXYChange(mapVo, "SKXY");
				return "{\"msg\":\"添加成功！.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	/**
	 * 删除变更记录
	 */
	@Override
	public String deletePactSKXYC(List<Map<String, Object>> listVo) {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> m : listVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", m.get("group_id"));
				map.put("hos_id", m.get("hos_id"));
				map.put("copy_code", m.get("copy_code"));
				map.put("pact_code", m.get("pact_code"));
				map.put("change_code", m.get("change_code"));
				listMap.add(map);
			}
			//删除变更表、备份主表及明细表
			deleteChangeAndCopy(listMap, "SKXY");
			
			for (Map<String, Object> m : listVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", m.get("group_id"));
				map.put("hos_id", m.get("hos_id"));
				map.put("copy_code", m.get("copy_code"));
				map.put("pact_code", m.get("pact_code"));
				map.put("change_code", m.get("change_code"));
				List<Map<String, Object>> mainList =(List<Map<String, Object>>) pactChangeAfterSKXYMapper.queryMainSKXYC(map);
				if(mainList!=null&&!mainList.isEmpty()) {
					Map<String, Object> hasMap = mainList.get(0);
					hasMap.put("sign_date",sdf1.format(hasMap.get("sign_date")));
					hasMap.put("start_date",sdf1.format(hasMap.get("start_date")));
					hasMap.put("end_date",sdf1.format(hasMap.get("end_date")));
					pactChangeAfterSKXYMapper.updateMainSKXY(hasMap);
				}
				String code = m.get("change_code").toString();
				String change_code_bef = code.substring(0,code.indexOf("-"));
				String change_code_aft = code.substring(code.indexOf("-")+1);
				String change_code = change_code_bef+"-"+String.valueOf(Integer.parseInt(change_code_aft)-1);
				map.put("change_code",change_code);
				List<PactDetSKXYEntity> detList = pactChangeAfterSKXYMapper.queryDetSKXYC(map);
				pactChangeAfterSKXYMapper.deleteAllBatch(detList);
				pactChangeAfterSKXYMapper.addBatch(detList);
			}
			return "{\"msg\":\"刪除成功！.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteChangeAndCopy(List<Map<String, Object>> listMap, String pact_type_code) {
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("list", listMap);
			map.put("change_table", "PACT_CHANGE_" + pact_type_code.toUpperCase());
			map.put("main_table", "PACT_MAIN_" + pact_type_code.toUpperCase() + "_C");
			map.put("det_table", "PACT_DET_" + pact_type_code.toUpperCase() + "_C");
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			pactChangeAfterSKXYMapper.deleteCopyDet(map);
			pactChangeAfterSKXYMapper.deleteCopyMain(map);
			pactChangeAfterSKXYMapper.deleteChangeMain(map);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String checkPactMainSKXYC(List<Map<String, Object>> listVo, String check, String is_init) {
		// TODO Auto-generated method stub
		try {
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("change_code", listVo.get(0).get("change_code"));
			map.put("pact_code", listVo.get(0).get("pact_code"));

			String returnMsg = null;
			switch (check) {
			case "check":
				map.put("state", "2");
				map.put("submit_date", sdf1.format(new Date()));
				map.put("submiter", SessionManager.getUserId());
				returnMsg = "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
				break;
			case "uncheck":
				map.put("state", "1");
				returnMsg = "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
				break;
			case "confir":
				map.put("confirm_date", sdf1.format(new Date()));
				map.put("confirmer", SessionManager.getUserId());
				map.put("state", "3");
				returnMsg = "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
				break;
			default:
				return "{\"error\":请求有误\"\"}";
			}
			if (!listVo.isEmpty()) {
				pactChangeAfterSKXYMapper.updateState(map);
				pactChangeAfterSKXYMapper.updateChangeSKXYState(map);
				
				if("confir".equals(check)) {
					List<Map<String, Object>> mainList =(List<Map<String, Object>>) pactChangeAfterSKXYMapper.queryMainSKXYC(map);
					if(mainList!=null&&!mainList.isEmpty()) {
						Map<String, Object> hasMap = mainList.get(0);
						hasMap.put("sign_date",sdf1.format(hasMap.get("sign_date")));
						hasMap.put("start_date",sdf1.format(hasMap.get("start_date")));
						hasMap.put("end_date",sdf1.format(hasMap.get("end_date")));
						pactChangeAfterSKXYMapper.updateMainSKXY(hasMap);
					}
					List<PactDetSKXYEntity> detList = pactChangeAfterSKXYMapper.queryDetSKXYC(map);
					if(detList!=null&&!detList.isEmpty()) {
						pactChangeAfterSKXYMapper.deleteAllBatch(detList);
						pactChangeAfterSKXYMapper.addBatch(detList);
					}
				}
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactMainSKXYEntity queryPactSKXYCByChangeCode(Map<String, Object> mapVo) {
		try {
			PactMainSKXYEntity entity = pactChangeAfterSKXYMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactSKXYC(Map<String, Object> mapVo) {
		try {

			pactChangeAfterSKXYMapper.updateMainSKXYC(mapVo);
			
			if (mapVo.get("sub") != null) {
				List<PactDetSKXYEntity> listVo = JSONArray.parseArray(mapVo.get("sub").toString(),
						PactDetSKXYEntity.class);
				if (!listVo.isEmpty()) {
					Set<Integer> set = new HashSet<>();
					for (PactDetSKXYEntity pactDetSKXYEntity : listVo) {
						set.add(pactDetSKXYEntity.getSubject_id());
					}
					if (set.size() != listVo.size()) {
						throw new SysException("标的物重复");
					}

					Map<String, Object> map = new HashMap<>();
					map.put("group_id", listVo.get(0).getGroup_id());
					map.put("hos_id", listVo.get(0).getHos_id());
					map.put("copy_code", listVo.get(0).getCopy_code());
					map.put("pact_code", listVo.get(0).getPact_code());
					Integer detailId = pactDetSKXYMapper.queryMaxDetailId(map);
					if (detailId == null) {
						detailId = 1;
						for (PactDetSKXYEntity pactDetSKXYEntity : listVo) {
							pactDetSKXYEntity.setDetail_id(detailId++);
						}
					} else {
						for (PactDetSKXYEntity pactDetSKXYEntity : listVo) {
							if (pactDetSKXYEntity.getDetail_id() == null) {
								pactDetSKXYEntity.setDetail_id(++detailId);
							}
						}
					}
					if(listVo!=null&&!listVo.isEmpty()) {
						pactChangeAfterSKXYMapper.deleteDetSKXYC(listVo);
						pactChangeAfterSKXYMapper.addDetSKXYC(listVo);
					}
				}
			}
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactDetSKXYC(List<PactDetSKXYEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactChangeAfterSKXYMapper.deletePactDetSKXYAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
