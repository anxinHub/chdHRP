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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.base.util.FileUtil;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocFKXYMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileFKXYMapper;
import com.chd.hrp.pac.dao.fkxy.pactinfo.PactChangeAfterFKXYMapper;
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

@Service("pactChangeAfterFKXYService")
public class PactChangeAfterFKXYServiceImpl implements PactChangeAfterFKXYService {

	private static Logger logger = Logger.getLogger(PactChangeAfterFKXYServiceImpl.class);

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
	
	@Resource(name = "pactChangeAfterFKXYMapper")
	private PactChangeAfterFKXYMapper pactChangeAfterFKXYMapper;

	@Override
	public String addPactFKXYC(Map<String, Object> mapVo, String pact_type_code) {
		try {
			int err = 0;
			Map<String, Object> hasMap = new HashMap<>();
			hasMap.put("group_id", SessionManager.getGroupId());
			hasMap.put("hos_id", SessionManager.getHosId());
			hasMap.put("copy_code", SessionManager.getCopyCode());
			hasMap.put("pact_code", mapVo.get("pact_code"));
			hasMap.put("sup_id", mapVo.get("sup_id"));
			hasMap.put("sup_no", mapVo.get("sup_no"));
			hasMap.put("is_exe", mapVo.get("is_exe"));
			List<Map<String, Object>> listMap = pactChangeMapper.queryPactMainChangeFKXY(hasMap);
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
				map.put("change_reason", "签订后变更");
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
					// 查询主协议
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
				pactChangeService.addPactMainXYChange(mapVo, "FKXY");
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
	public String deletePactFKXYC(List<Map<String, Object>> listVo) {
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
			deleteChangeAndCopy(listMap, "FKXY");
			
			for (Map<String, Object> m : listVo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", m.get("group_id"));
				map.put("hos_id", m.get("hos_id"));
				map.put("copy_code", m.get("copy_code"));
				map.put("pact_code", m.get("pact_code"));
				map.put("change_code", m.get("change_code"));
				List<Map<String, Object>> mainList =(List<Map<String, Object>>) pactChangeAfterFKXYMapper.queryMainFKXYC(map);
				if(mainList!=null&&!mainList.isEmpty()) {
					Map<String, Object> hasMap = mainList.get(0);
					hasMap.put("sign_date",sdf1.format(hasMap.get("sign_date")));
					hasMap.put("start_date",sdf1.format(hasMap.get("start_date")));
					hasMap.put("end_date",sdf1.format(hasMap.get("end_date")));
					pactChangeAfterFKXYMapper.updateMainFKXY(hasMap);
				}
				String code = m.get("change_code").toString();
				String change_code_bef = code.substring(0,code.indexOf("-"));
				String change_code_aft = code.substring(code.indexOf("-")+1);
				String change_code = change_code_bef+"-"+String.valueOf(Integer.parseInt(change_code_aft)-1);
				map.put("change_code",change_code);
				List<PactDetFKXYEntity> detList = pactChangeAfterFKXYMapper.queryDetFKXYC(map);
				pactChangeAfterFKXYMapper.deleteAllBatch(detList);
				pactChangeAfterFKXYMapper.addBatch(detList);
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
			pactChangeAfterFKXYMapper.deleteCopyDet(map);
			pactChangeAfterFKXYMapper.deleteCopyMain(map);
			pactChangeAfterFKXYMapper.deleteChangeMain(map);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String checkPactMainFKXYC(List<Map<String, Object>> listVo, String check, String is_init) {
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
				pactChangeAfterFKXYMapper.updateState(map);
				pactChangeAfterFKXYMapper.updateChangeFKXYState(map);
				if("confir".equals(check)) {
					List<Map<String, Object>> mainList =(List<Map<String, Object>>) pactChangeAfterFKXYMapper.queryMainFKXYC(map);
					if(mainList!=null&&!mainList.isEmpty()) {
						Map<String, Object> hasMap = mainList.get(0);
						hasMap.put("sign_date",sdf1.format(hasMap.get("sign_date")));
						hasMap.put("start_date",sdf1.format(hasMap.get("start_date")));
						hasMap.put("end_date",sdf1.format(hasMap.get("end_date")));
						pactChangeAfterFKXYMapper.updateMainFKXY(hasMap);
					}
					List<PactDetFKXYEntity> detList = pactChangeAfterFKXYMapper.queryDetFKXYC(map);
					if(detList!=null&&!detList.isEmpty()) {
						pactChangeAfterFKXYMapper.deleteAllBatch(detList);
						pactChangeAfterFKXYMapper.addBatch(detList);
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
	public PactMainFKXYEntity queryPactFKXYCByChangeCode(Map<String, Object> mapVo) {
		try {
			PactMainFKXYEntity entity = pactChangeAfterFKXYMapper.queryByCode(mapVo);
			return entity;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactFKXYC(Map<String, Object> mapVo) {
		try {

			pactChangeAfterFKXYMapper.updateMainFKXYC(mapVo);
			
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
					if(listVo!=null&&!listVo.isEmpty()) {
						pactChangeAfterFKXYMapper.deleteDetFKXYC(listVo);
						pactChangeAfterFKXYMapper.addDetFKXYC(listVo);
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
	public String deletePactDetFKXYC(List<PactDetFKXYEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				pactChangeAfterFKXYMapper.deletePactDetFKXYAllBatch(listVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
