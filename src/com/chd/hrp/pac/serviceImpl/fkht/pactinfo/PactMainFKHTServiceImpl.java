package com.chd.hrp.pac.serviceImpl.fkht.pactinfo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.dao.project.information.HosProjDictMapper;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactDocFKHTMapper;
import com.chd.hrp.pac.dao.basicset.doc.doc.PactFileFKHTMapper;
import com.chd.hrp.pac.dao.fkht.guarantee.PactDepRecFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactAssApplyFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactBidFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactDetFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactMainFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactPlanFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactSourceFKHTMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactDetFKHTEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKHTService;
import com.chd.hrp.pac.service.fkht.pactinfo.PactMainFKHTService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactMainFKHTService")
public class PactMainFKHTServiceImpl implements PactMainFKHTService {

	private static Logger logger = Logger.getLogger(PactMainFKHTServiceImpl.class);

	@Resource(name = "pactMainFKHTMapper")
	private PactMainFKHTMapper pactMainFKHTMapper;

	@Resource(name = "hosProjDictMapper")
	private HosProjDictMapper projDictMapper;

	@Resource(name = "pactPlanFKHTMapper")
	private PactPlanFKHTMapper pactPlanFKHTMapper;

	@Resource(name = "pactDetFKHTMapper")
	private PactDetFKHTMapper pactDetFKHTMapper;
	
	@Resource(name = "pactAssApplyFKHTMapper")
	private PactAssApplyFKHTMapper pactAssApplyFKHTMapper;
	
	@Resource(name = "pactBidFKHTMapper")
	private PactBidFKHTMapper pactBidFKHTMapper;

	@Resource(name = "pactSourceFKHTMapper")
	private PactSourceFKHTMapper pactSourceFKHTMapper;
	
	@Resource(name = "pactDocFKHTMapper")
	private PactDocFKHTMapper pactDocFKHTMapper;

	@Resource(name = "pactDepRecFKHTMapper")
	private PactDepRecFKHTMapper pactDepRecFKHTMapper;

	@Resource(name = "pactDocFKHTService")
	private PactDocFKHTService pactDocFKHTService;

	@Resource(name = "pactFileFKHTMapper")
	private PactFileFKHTMapper pactFileFKHTMapper;

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;
	
	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;

	@SuppressWarnings("unchecked")
	@Override
	public String queryPactMainFKHT(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainFKHTEntity> query = (List<PactMainFKHTEntity>) pactMainFKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainFKHTEntity> list = (List<PactMainFKHTEntity>) pactMainFKHTMapper.query(entityMap,
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
	public String addPactMainFKHT(Map<String, Object> page) {
		try {
				List<Map<String, Object>> li = pactMainFKHTMapper.queryPactFKHTMainByTypeAndName(page);
				if(li!=null && !li.isEmpty()) {
					return "{\"error\":\"该条数据已经存在，请重新选择合同类型或重新输入合同名称\",\"state\":\"false\"}";
				}
				pactMainFKHTMapper.add(page);

				if (page.get("sub") != null) {
					JSONArray json = JSONArray.parseArray(page.get("sub").toString());
					if (!json.isEmpty()) {
						addPactDetFKHT(json, page.get("pact_code").toString());
						
						List<PactPlanFKHTEntity> list = new ArrayList<>();
						if (page.get("plan") == null || "[]".equals(page.get("plan").toString())) {
							if (!page.get("pact_money").toString().equals("0")) {
								PactPlanFKHTEntity entity = new PactPlanFKHTEntity();
								entity.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
								entity.setHos_id(Integer.parseInt(SessionManager.getHosId()));
								entity.setCopy_code(SessionManager.getCopyCode());
								entity.setPay_id(1);
								entity.setPay_type(1);//1全款、2预付款、3期款、4尾款; 未填付款计划  默认 全款
								entity.setRate(100.0);//  全款 付款比例 100
								String end_date = (String) page.get("end_date");
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
								entity.setPay_date(dateFormat.parse(end_date));
								entity.setSource_id(1);
								entity.setSource_name("自筹资金");
								entity.setPlan_money(Double.valueOf(page.get("pact_money").toString()));
								entity.setSummary(null);
								list.add(entity);
							}
						} else {
							list = JSONArray.parseArray(page.get("plan").toString(), PactPlanFKHTEntity.class);
						}
						if (!list.isEmpty()) {
							addPactPlanFKHT(list, page.get("pact_code").toString());
						}
					}
				}

				if (page.get("doc") != null) {
					List<PactDocEntity> doclist = JSONArray.parseArray(page.get("doc").toString(), PactDocEntity.class);
					if (!doclist.isEmpty()) {
						for (PactDocEntity pactDocEntity : doclist) {
							pactDocEntity.setPact_code(page.get("pact_code").toString());
						}
						pactDocFKHTService.addBatchPactDocFKHT(doclist);
					}
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"pact_code\":\"" + page.get("pact_code").toString() + "\"}";
			
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
	private String addPactPlanFKHT(List<PactPlanFKHTEntity> listVo, String pact_code) {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("pact_code", pact_code);

			Integer detail_id = pactPlanFKHTMapper.queryMaxDetailId(entityMap);
			if (detail_id == null) {
				detail_id = 1;
				for (PactPlanFKHTEntity pactPlanSKHTEntity : listVo) {
					pactPlanSKHTEntity.setPact_code(pact_code);
					pactPlanSKHTEntity.setPlan_detail_id(detail_id++);
				}
			} else {
				for (PactPlanFKHTEntity pactPlanSKHTEntity : listVo) {
					if (pactPlanSKHTEntity.getPlan_detail_id() == null) {
						pactPlanSKHTEntity.setPact_code(pact_code);
						pactPlanSKHTEntity.setPlan_detail_id(++detail_id);
					}else{
						pactPlanSKHTEntity.setPact_code(pact_code);
					}
				}
			}

			pactPlanFKHTMapper.deleteByPactCode(entityMap);
			pactPlanFKHTMapper.addBatch(listVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
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
	private String addPactDetFKHT(JSONArray json, String pact_code) {
		try {
			Set<Integer> set = new HashSet<>();
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				set.add(Integer.parseInt(jsonObj.get("subject_id").toString()));
			}
			if (set.size() != json.size()) {
				throw new SysException("标的物重复");
			}

			Map<String, Object> map = new HashMap<>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("pact_code", pact_code);

			Integer detailId = pactDetFKHTMapper.queryMaxDetailId(map);
			if (detailId == null) {
				detailId = 0;
			}
			// 存明细数据
			List<Map<String,Object>> detailList =  new ArrayList<Map<String,Object>>();
			// 存 付款合同与资产采购申请对应关系数据
			List<Map<String,Object>> applyList =  new ArrayList<Map<String,Object>>();
			//付款合同与定标对应关系
			List<Map<String,Object>> bidList =  new ArrayList<Map<String,Object>>();
			
			//付款合同明细 资金来源
			List<Map<String,Object>> sourceList =  new ArrayList<Map<String,Object>>();
			
			Iterator itData = json.iterator();
			
			while (itData.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
				Map<String,Object> detailMap = new HashMap<String,Object>();
				
				detailMap.put("group_id", SessionManager.getGroupId());
				detailMap.put("hos_id", SessionManager.getHosId());
				detailMap.put("copy_code", SessionManager.getCopyCode());
				detailMap.put("pact_code", pact_code);
				if(jsonObj.get("detail_id") != null && !"".equals(jsonObj.get("detail_id"))){//修改
					detailMap.put("detail_id",jsonObj.get("detail_id"));
				}else{//新增
					detailMap.put("detail_id", ++detailId);
				}
				
				
				detailMap.put("subject_type", jsonObj.get("subject_type"));
				detailMap.put("subject_id", jsonObj.get("subject_id"));
				detailMap.put("subject_no", jsonObj.get("subject_no"));
				detailMap.put("item_spec", jsonObj.get("item_spec"));
				detailMap.put("item_model", jsonObj.get("item_model"));
				detailMap.put("fac_id", jsonObj.get("fac_id"));
				detailMap.put("fac_no", jsonObj.get("fac_no"));
				detailMap.put("unit_code", jsonObj.get("unit_code"));
				detailMap.put("amount", jsonObj.get("amount"));
				detailMap.put("price", jsonObj.get("price"));
				detailMap.put("money", jsonObj.get("money"));
				if(jsonObj.get("arrive_date") != null && !"".equals(jsonObj.get("arrive_date"))){
					detailMap.put("arrive_date", DateUtil.stringToDate(jsonObj.get("arrive_date").toString(), "yyyy-MM-dd"));
				}else{
					detailMap.put("arrive_date", null);
				}
				
				
				detailMap.put("repair_months", jsonObj.get("repair_months"));
				detailMap.put("note", jsonObj.get("note"));
				if(jsonObj.get("dept_no") != null && !"".equals(jsonObj.get("dept_no"))){
					detailMap.put("dept_id", jsonObj.get("dept_no").toString().split("@")[0]);
					detailMap.put("dept_no", jsonObj.get("dept_no").toString().split("@")[1]);
				}else{
					detailMap.put("dept_id", null);
					detailMap.put("dept_no", null);
				}
				
				detailMap.put("item_name", jsonObj.get("item_name"));
				detailMap.put("item_brand", jsonObj.get("item_brand"));
				detailMap.put("source", jsonObj.get("source"));
				
				detailList.add(detailMap);
				
				if("1".equals(jsonObj.get("source").toString())){//引入定标时  添加 付款合同与定标对应关系
					Map<String,Object> bidMap = new HashMap<String,Object>();
					
					bidMap.put("group_id", SessionManager.getGroupId());
					bidMap.put("hos_id", SessionManager.getHosId());
					bidMap.put("copy_code", SessionManager.getCopyCode());
					bidMap.put("pact_code", pact_code);
					bidMap.put("pact_det_id", detailMap.get("detail_id"));
					bidMap.put("bid_id", jsonObj.get("bid_id"));
					bidMap.put("bid_det_id", jsonObj.get("bid_det_id"));
					bidMap.put("pact_amount", jsonObj.get("amount"));
					
					bidList.add(bidMap);
				}else if("2".equals(jsonObj.get("source").toString())){// 引入资产采购申请时 添加 付款合同与资产采购申请对应关系
					
					Map<String,Object> applyMap = new HashMap<String,Object>();
					
					applyMap.put("group_id", SessionManager.getGroupId());
					applyMap.put("hos_id", SessionManager.getHosId());
					applyMap.put("copy_code", SessionManager.getCopyCode());
					applyMap.put("pact_code", pact_code);
					applyMap.put("pact_det_id", detailMap.get("detail_id"));
					applyMap.put("apply_id", jsonObj.get("apply_id"));
					applyMap.put("apply_det_id", jsonObj.get("apply_det_id"));
					applyMap.put("pact_amount", jsonObj.get("amount"));
					
					applyList.add(applyMap);
				}
				//资金来源
				if(jsonObj.get("sourceData")==null || "".equals(jsonObj.get("sourceData").toString())){
					
					Map<String,Object> sourceMap = new HashMap<String,Object>();
					
					sourceMap.put("group_id", SessionManager.getGroupId());
					sourceMap.put("hos_id", SessionManager.getHosId());
					sourceMap.put("copy_code", SessionManager.getCopyCode());
					sourceMap.put("pact_code", pact_code);
					sourceMap.put("detail_id", detailMap.get("detail_id"));
					sourceMap.put("source_id", 1);
					sourceMap.put("money", detailMap.get("money"));
					sourceMap.put("note", null);
					
					sourceList.add(sourceMap);
					
				}else{
					
					JSONObject sourceData = JSONObject.parseObject(jsonObj.get("sourceData").toString());
					JSONArray source = JSONArray.parseArray(sourceData.get("Rows").toString());
					
					Iterator itSource = source.iterator();
					while (itSource.hasNext()) {
						JSONObject pactSource = JSONObject.parseObject(itSource.next().toString());
						
						Map<String,Object> sourceMap = new HashMap<String,Object>();
						
						sourceMap.put("group_id", SessionManager.getGroupId());
						sourceMap.put("hos_id", SessionManager.getHosId());
						sourceMap.put("copy_code", SessionManager.getCopyCode());
						sourceMap.put("pact_code", pact_code);
						sourceMap.put("detail_id", detailMap.get("detail_id"));
						sourceMap.put("source_id", pactSource.get("source_id"));
						sourceMap.put("money", pactSource.get("money"));
						sourceMap.put("note", pactSource.get("note"));
						sourceList.add(sourceMap);
					}
					
				}
				
				
			}
			pactDetFKHTMapper.deleteByPactCode(map);
			if(bidList.size()>0){
				pactBidFKHTMapper.deleteBatchByDet(bidList);
				pactBidFKHTMapper.addBatch(bidList);
			}
			if(applyList.size()>0){
				pactAssApplyFKHTMapper.deleteBatchByDet(applyList);
				pactAssApplyFKHTMapper.addBatch(applyList);
			}
			
			pactSourceFKHTMapper.deleteBatchByDet(sourceList);
			pactSourceFKHTMapper.addBatch(sourceList);
			pactDetFKHTMapper.deleteBatch(detailList);
			pactDetFKHTMapper.addBatch(detailList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public PactMainFKHTEntity queryPactMainFKHTByCode(Map<String, Object> mapVo) {
		try {
			return pactMainFKHTMapper.queryByCode(mapVo);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deletePactMainFKHT(List<PactMainFKHTEntity> listVo) {
		try {
			if (!listVo.isEmpty()) {
				List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
				for (PactMainFKHTEntity entity : listVo) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", entity.getGroup_id());
					map.put("hos_id", entity.getHos_id());
					map.put("copy_code", entity.getCopy_code());
					map.put("pact_code", entity.getPact_code());
					listMap.add(map);
				}
				pactPlanFKHTMapper.deleteByPactCodeList(listMap);
				
				// 删除 付款合同与定标对应关系
				pactBidFKHTMapper.deleteBatchByPactCode(listMap);
				//删除 付款合同与资产采购申请对应关系
				pactAssApplyFKHTMapper.deleteBatchByPactCode(listMap);
				//删除  付款合同资金来源表
				pactSourceFKHTMapper.deleteBatchByPactCode(listMap);
				pactDetFKHTMapper.deleteByPactCodeList(listMap);
				
				pactDocFKHTMapper.deleteByPactCodeList(listMap);
				pactFileFKHTMapper.deleteByPactCodeList(listMap);
				pactFileFKHTMapper.deleteDocFileByPactCodeList(listMap);
				pactDepRecFKHTMapper.deleteByPactCodeList(listMap);
				pactChangeService.deleteChangeAndCopy(listMap, "FKHT");
				pactMainFKHTMapper.deleteAllBatch(listVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String updatePactMainFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> li = pactMainFKHTMapper.queryPactFKHTMainByTypeAndName(mapVo);
			int i=0;
			for (Map<String, Object> map : li) {
				if(!map.get("pact_code").toString().equals(mapVo.get("pact_code").toString()) && map.get("pact_type_code").toString().equals( mapVo.get("pact_type_code").toString())) {
					i+=1;
				}
			}
			if(i>0) {
				return "{\"error\":\"该条数据已经存在，请重新输入合同名称\",\"state\":\"false\"}";
			}
			if ("1".equals(mapVo.get("is_change").toString())) {
				
				mapVo.put("table_code", "PACT_CHANGE_FKHT");
				//查询 最大 变更号
				String change_code = pactChangeMapper.queryMaxId(mapVo);
				if (change_code == null) {//保存历史记录    不存在变更情况下  首先添加 变更号为 合同号-0 得变更 并备份数据
					pactChangeService.addPactMainChange(mapVo, "FKHT");
				}
				
			}

			if (mapVo.get("sub") != null) {
				JSONArray json = JSONArray.parseArray(mapVo.get("sub").toString());
				if (!json.isEmpty()) {
					addPactDetFKHT(json, mapVo.get("pact_code").toString());
					
					//如果存在标的物则生成计划
					List<PactPlanFKHTEntity> list = new ArrayList<>();
					if (mapVo.get("plan") == null || "[]".equals(mapVo.get("plan").toString())) {
						if (!mapVo.get("pact_money").toString().equals("0")) {
							PactPlanFKHTEntity entity = new PactPlanFKHTEntity();
							entity.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
							entity.setHos_id(Integer.parseInt(SessionManager.getHosId()));
							entity.setCopy_code(SessionManager.getCopyCode());
							entity.setPay_id(1);
							entity.setPay_type(1);//1全款、2预付款、3期款、4尾款; 未填付款计划  默认 全款
							entity.setRate(100.0);//  全款 付款比例 100
							String end_date = (String) mapVo.get("end_date");
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							entity.setPay_date(dateFormat.parse(end_date));
							entity.setSource_id(1);
							entity.setSource_name("自筹资金");
							entity.setPlan_money(Double.valueOf(mapVo.get("pact_money").toString()));
							entity.setSummary(null);
							list.add(entity);
						}
					} else {
						list = JSONArray.parseArray(mapVo.get("plan").toString(), PactPlanFKHTEntity.class);
					}
					if (!list.isEmpty()) {
						addPactPlanFKHT(list, mapVo.get("pact_code").toString());
					}
				}
			}

			if (mapVo.get("doc") != null) {
				List<PactDocEntity> doclist = JSONArray.parseArray(mapVo.get("doc").toString(), PactDocEntity.class);
				if (!doclist.isEmpty()) {
					for (PactDocEntity pactDocEntity : doclist) {
						pactDocEntity.setPact_code(mapVo.get("pact_code").toString());
					}
					pactDocFKHTService.deletePactDocFKHT(doclist);
					pactDocFKHTService.addBatchPactDocFKHT(doclist);
				}
			}

			pactMainFKHTMapper.update(mapVo);
			if ("1".equals(mapVo.get("is_change").toString())) {
				//添加 变更记录 并备份 变更后的数据
				pactChangeService.addPactMainChange(mapVo, "FKHT");
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String updatePactMainFKHTState(List<String> listVo, String check, String is_init) {
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
				Map<String, Object> checkPactMainFKHT = checkPactMainFKHT(map, listVo);
				String err = (String) checkPactMainFKHT.get("err_msg");
				listVo = (List<String>) checkPactMainFKHT.get("list");
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

			if (!listVo.isEmpty()) {
				map.put("list", listVo);
				int count =0;
				if("unconfir".equals(check)){
					count =pactMainFKHTMapper.queryIsExeChange(map);
				}
				if(count == 0){
					pactMainFKHTMapper.updateState(map);
				}else{
					returnMsg = "{\"error\":\"存在签订后变动单据,取消确认失败.\",\"state\":\"false\"}";
				}

				
			}
			return returnMsg;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	private Map<String, Object> checkPactMainFKHT(Map<String, Object> map, List<String> listVo) {
		StringBuffer err = new StringBuffer();
		List<String> finish_pact_code = new ArrayList<>();
		map.put("list", listVo);
		List<Map<String,Object>> list = pactDetFKHTMapper.queryByPactCodeList(map);
		if (list.isEmpty()) {
			err.append("审核失败，合同无标的物明细！");
		} else {
			for (String pact_code : listVo) {
				Double money = 0d;
				for (Map<String,Object> item : list) {
					if (pact_code.equals(item.get("pact_code"))) {
						money += Double.parseDouble(item.get("money").toString());
					}
				}
				map.put("pact_code", pact_code);
				PactMainFKHTEntity entity = pactMainFKHTMapper.queryByCode(map);
				Double pact_money = entity.getPact_money();
				int compareTo = BigDecimal.valueOf(money).compareTo(BigDecimal.valueOf(pact_money));
				if (compareTo == 0 && money != 0 && pact_money != 0) {
					@SuppressWarnings("unchecked")
					List<PactPlanFKHTEntity> planList = (List<PactPlanFKHTEntity>) pactPlanFKHTMapper.query(map);
					if (planList.isEmpty()) {
						err.append("合同[" + entity.getPact_name() + "]审核失败，无付款计划明细！");
					} else {
						Double plan_money = 0d;
						for (PactPlanFKHTEntity pactPlanFKHTEntity : planList) {
							plan_money += pactPlanFKHTEntity.getPlan_money();
						}
						//int compare = BigDecimal.valueOf(money).compareTo(BigDecimal.valueOf(plan_money));
						if (plan_money != 0 && money != 0) {
							finish_pact_code.add(pact_code);
						} //else {
							//err.append("合同[" + entity.getPact_name() + "]审核失败，付款计划金额与合同金额不一致！");
						  //}
					}
				} else {
					err.append("合同[" + entity.getPact_name() + "]审核失败，标的物金额与合同金额不一致！");
				}
			}
		}
		map.put("err_msg", err.toString());
		map.put("list", finish_pact_code);
		return map;
	}

	@Override
	public String queryPactMainFKHTByStateCode(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<PactMainFKHTEntity> query = (List<PactMainFKHTEntity>) pactMainFKHTMapper
						.queryByStateCode(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<PactMainFKHTEntity> list = (List<PactMainFKHTEntity>) pactMainFKHTMapper
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
	public String queryPactMainFKHTForWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainFKHTMapper.queryPactMainFKHTForWarning(entityMap, rowBounds);
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
	public List<Map<String, Object>> queryPactMainFKHTForWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMainFKHTForRetWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForRetWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainFKHTMapper.queryPactMainFKHTForRetWarning(entityMap,
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
	public List<Map<String, Object>> queryPactMainFKHTForRetWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForRetWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMainFKHTForPayWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForPayWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainFKHTMapper.queryPactMainFKHTForPayWarning(entityMap,
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
	public List<Map<String, Object>> queryPactMainFKHTForPayWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForPayWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactMainFKHTForNearRepairWarning(Map<String, Object> entityMap) {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForNearRepairWarning(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = pactMainFKHTMapper.queryPactMainFKHTForNearRepairWarning(entityMap,
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
	public List<Map<String, Object>> queryPactMainFKHTForNearRepairWarningPrint(Map<String, Object> entityMap) {
		try {
			entityMap.put("state", 3);
			entityMap.put("state_code", 12);
			List<Map<String, Object>> query = pactMainFKHTMapper.queryPactMainFKHTForNearRepairWarning(entityMap);
			return query;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryPactAssApplyFKHT(Map<String, Object> entityMap) 	throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactMainFKHTMapper.queryPactAssApplyFKHT(entityMap);
				return ChdJson.toJsonLower(query);
			} else {
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactMainFKHTMapper.queryPactAssApplyFKHT(entityMap,	rowBounds);
				
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}

	}

	@Override
	public String queryPactBidFKHT(Map<String, Object> entityMap)throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactMainFKHTMapper.queryPactBidFKHT(entityMap);
				return ChdJson.toJsonLower(list);
			} else {
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactMainFKHTMapper.queryPactBidFKHT(entityMap,	rowBounds);
				
				PageInfo page = new PageInfo(list);
				return ChdJson.toJsonLower(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	/**
	 * 校验 明细数据各资金来源总金额  与付款计划 各资金来源总金额是否一致
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Boolean testSourceMoney(Map<String,Object> map)throws DataAccessException{
		
		Map<String,Object> sourceMap = new HashMap<String,Object>();
		
		Map<String,Object> planMap = new HashMap<String,Object>();
		
		if (map.get("sub") != null) {
			JSONArray json = JSONArray.parseArray(map.get("sub").toString());
			if (!json.isEmpty()) {
				
				Iterator itData = json.iterator();
				
				while (itData.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
					
					//资金来源数据
					// 明细数据 没有资金来源数据  默认 自筹资金
					if(jsonObj.get("sourceData")==null || "".equals(jsonObj.get("sourceData").toString())){
						if(sourceMap.get("1") == null){
							
							sourceMap.put("1", Double.parseDouble(jsonObj.get("money").toString()));
							
						}else{
							sourceMap.put("1", Double.parseDouble(sourceMap.get("1").toString())+Double.parseDouble(jsonObj.get("money").toString()));
						}
							
						
					}else{ // 明细数据 存在 资金来源数据  
						
						JSONObject sourceData = JSONObject.parseObject(jsonObj.get("sourceData").toString());
						JSONArray source = JSONArray.parseArray(sourceData.get("Rows").toString());
						
						Iterator itSource = source.iterator();
						while (itSource.hasNext()) {
							JSONObject pactSource = JSONObject.parseObject(itSource.next().toString());
							
							if(sourceMap.get(pactSource.get("source_id").toString()) == null){
								
								sourceMap.put(pactSource.get("source_id").toString(), Double.parseDouble(pactSource.get("money").toString()));
								
							}else{
								sourceMap.put(pactSource.get("source_id").toString(), 
										Double.parseDouble(sourceMap.get(pactSource.get("source_id").toString()).toString())+Double.parseDouble(pactSource.get("money").toString()));
							}
						}
						
					}
				} 
			}
		}
		if (map.get("plan") == null || "[]".equals(map.get("plan").toString())) {
			
			planMap.put("1",Double.parseDouble(map.get("pact_money").toString()));
			
		}else{
			JSONArray planJson = JSONArray.parseArray(map.get("plan").toString());
			
			Iterator itPlan = planJson.iterator();
			
			while (itPlan.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(itPlan.next().toString());
				
				if(planMap.get(jsonObj.get("source_id").toString()) == null){
					
					planMap.put(jsonObj.get("source_id").toString(), Double.parseDouble(planMap.get("money").toString()));
					
				}else{
					planMap.put(planMap.get("source_id").toString(), 
							Double.parseDouble(sourceMap.get(planMap.get("source_id").toString()).toString())+Double.parseDouble(planMap.get("money").toString()));
				}
			}
		}
		
		int flag = 0 ;
		
		if(sourceMap.keySet().size() == planMap.keySet().size()){
			Iterator keys= sourceMap.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next().toString();
				if(planMap.containsKey(key)){
					
					if(planMap.get(key).equals(sourceMap.get(key))){//如果相等继续 ，否则返回false
						flag = 1;
					}else{
						flag = 0 ;
						break ;
					}
					
				}else{
					flag = 0;
					break ;
				}
			}
			if(flag == 1){
				return true ;
			}else{
				return false ;
			}
		}else{
			
			return false;
		}
		
		
		
	}

	@Override
	public String queryPactSourceFKHT(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String, Object>>) pactSourceFKHTMapper.query(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) pactSourceFKHTMapper.query(entityMap,rowBounds);
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
	public PactMainFKHTEntity queryPactMainFKHTByCodeCopy(	Map<String, Object> mapVo) throws DataAccessException {
		try {
			return pactMainFKHTMapper.queryByCodeCopy(mapVo);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
}
