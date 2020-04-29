package com.chd.hrp.pac.serviceImpl.basicset.common;

import java.text.DecimalFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.fkht.change.PactChangeFKHTMapper;
import com.chd.hrp.pac.dao.fkht.change.PactDetFKHTCMapper;
import com.chd.hrp.pac.dao.fkht.change.PactMainFKHTCMapper;
import com.chd.hrp.pac.dao.fkht.change.PactMoneyCFKHTMapper;
import com.chd.hrp.pac.dao.fkht.change.PactPlanFKHTCMapper;
import com.chd.hrp.pac.dao.fkht.change.PactSourceCFKHTMapper;
import com.chd.hrp.pac.dao.fkht.change.PactSourceFKHTCMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactAssApplyFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactBidFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactDetFKHTMapper;
import com.chd.hrp.pac.dao.fkht.pactinfo.PactSourceFKHTMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;
import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocFKHTService;
import com.github.pagehelper.PageInfo;

@Service(value = "pactChangeService")
public class PactChangeServiceImpl implements PactChangeService {

	private static Logger logger = Logger.getLogger(PactChangeServiceImpl.class);

	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactDetFKHTMapper")
	private PactDetFKHTMapper pactDetFKHTMapper;
	
	@Resource(name = "pactSourceFKHTMapper")
	private PactSourceFKHTMapper pactSourceFKHTMapper;
	
	// 付款合同 金额变动DAO
	@Resource(name = "pactMoneyCFKHTMapper")
	private PactMoneyCFKHTMapper pactMoneyCFKHTMapper;
	//付款合同 明细资金来源 变动DAO
	@Resource(name = "pactSourceCFKHTMapper")
	private PactSourceCFKHTMapper pactSourceCFKHTMapper;
	
	// 付款合同 付款计划备份DAO
	@Resource(name = "pactPlanFKHTCMapper")
	private PactPlanFKHTCMapper pactPlanFKHTCMapper;
	
	// 付款合同 变更DAO
	@Resource(name = "pactChangeFKHTMapper")
	private PactChangeFKHTMapper pactChangeFKHTMapper;
	
	
	@Resource(name = "pactBidFKHTMapper")
	private PactBidFKHTMapper pactBidFKHTMapper;
	
	@Resource(name = "pactAssApplyFKHTMapper")
	private PactAssApplyFKHTMapper pactAssApplyFKHTMapper;
	
	@Resource(name = "pactSourceFKHTCMapper")
	private PactSourceFKHTCMapper pactSourceFKHTCMapper;
	
	@Resource(name = "pactDetFKHTCMapper")
	private PactDetFKHTCMapper pactDetFKHTCMapper;
	
	@Resource(name = "pactMainFKHTCMapper")
	private PactMainFKHTCMapper pactMainFKHTCMapper;
	
	
	
	@Resource(name = "pactDocFKHTService")
	private PactDocFKHTService pactDocFKHTService;

	

	@Override
	public String addPactMainChange(Map<String, Object> map, String pact_type_code) {
		try {
			Map<String, Object> mapVo = new HashMap<>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("operator", SessionManager.getUserId());
			mapVo.put("state", 0);
			mapVo.put("maker", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("make_date", format.format(new Date()));
			
			mapVo.put("submiter", null);
			mapVo.put("submit_date", null);
			mapVo.put("confirmer", null);
			mapVo.put("confirm_date", null);
			
			if(!"1".equals(map.get("is_exe"))){//签订前变更数据处理
				
				mapVo.put("change_date", format.format(new Date()));
				mapVo.put("change_reason", map.get("change_reason"));
				mapVo.put("state", 2);
				mapVo.put("is_exe", 0);
				mapVo.put("is_money_c", 0);
				mapVo.put("pay_id", null);
				mapVo.put("pay_type", null);
				mapVo.put("summary", null);
				mapVo.put("pay_date", null);
				
				mapVo.put("pay_cond", null);
				mapVo.put("plan_money", null);
				mapVo.put("value_c_code", null);
			}else{//签订后变更数据处理
				mapVo.put("change_date", map.get("change_date"));
				mapVo.put("change_reason", map.get("change_reason"));
				
				mapVo.put("is_exe", 1);
				mapVo.put("is_money_c", map.get("is_money_c"));
				mapVo.put("pay_id", map.get("pay_id"));
				mapVo.put("pay_type", map.get("pay_type"));
				mapVo.put("summary", map.get("summary"));
				mapVo.put("pay_date", map.get("pay_date"));
				
				mapVo.put("pay_cond", map.get("pay_cond"));
				mapVo.put("plan_money", map.get("plan_money"));
				mapVo.put("value_c_code", map.get("value_c_code"));
			}
			
			mapVo.put("pact_code", map.get("pact_code"));
			
			mapVo.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
			//查询 最大 变更号
			String change_code = pactChangeMapper.queryMaxId(mapVo);
			int newCode = 0;// 记录 变更号 尾号数字 用
			if (change_code == null) {
				change_code = map.get("pact_code").toString() + "-0";
				mapVo.put("state", 2); // 0号 变更数据状态 置为 2 确认 
			} else {
				String code = change_code.substring(change_code.indexOf("-") + 1, change_code.length());
				newCode = Integer.parseInt(code) + 1;
				change_code = map.get("pact_code").toString() + "-" + newCode;
			}
			mapVo.put("change_code", change_code);
			
			if(("1".equals(map.get("is_exe"))&& newCode==0) || !"1".equals(map.get("is_exe")) ){// 签订后变动 没有 变更记录  及签订前变动 走此逻辑
				pactChangeMapper.add(mapVo);
				// 备份明细表
				mapVo.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
				// 查询主合同
				List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(mapVo);
				mapVo.putAll(before.get(0));
				// 查询明细表
				mapVo.put("table_code_f", "PACT_DET_" + pact_type_code.toUpperCase());
				List<Map<String, Object>> detList = pactChangeMapper.queryChangeBefore(mapVo);

				switch (pact_type_code.toUpperCase()) {
					case "FKHT":
						// 查询计划表
						pactChangeMapper.copyPactMainFKHT(mapVo);
						mapVo.put("table_code_f", "PACT_PLAN_" + pact_type_code.toUpperCase());
						List<Map<String, Object>> planList = pactChangeMapper.queryChangeBefore(mapVo);
						changeMoney(mapVo, map, pact_type_code);
						if (!detList.isEmpty()) {
							mapVo.put("list", detList);
							pactChangeMapper.copyPactDetFKHT(mapVo);
						}
						if (!planList.isEmpty()) {
							mapVo.put("list", planList);
							pactChangeMapper.copyPactPlanFKHT(mapVo);
						}
						pactChangeFKHTMapper.copySourceData1(mapVo);
						break;
					case "SKHT":
						// 查询计划表
						changeMoney(mapVo, map, pact_type_code);
						mapVo.put("table_code_f", "PACT_PLAN_" + pact_type_code.toUpperCase());
						planList = pactChangeMapper.queryChangeBefore(mapVo);
						pactChangeMapper.copyPactMainSKHT(mapVo);
						if (!detList.isEmpty()) {
							mapVo.put("list", detList);
							pactChangeMapper.copyPactDetSKHT(mapVo);
						}
						if (!planList.isEmpty()) {
							mapVo.put("list", planList);
							pactChangeMapper.copyPactPlanSKHT(mapVo);
						}
						break;
					case "FKXY":
						pactChangeMapper.copyPactMainFKXY(mapVo);
						if (!detList.isEmpty()) {
							mapVo.put("list", detList);
							pactChangeMapper.copyPactDetFKXY(mapVo);
						}
						break;
					case "SKXY":
						pactChangeMapper.copyPactMainSKXY(mapVo);
						if (!detList.isEmpty()) {
							mapVo.put("list", detList);
							pactChangeMapper.copyPactDetSKXY(mapVo);
						}
						break;
					default:
						break;
				}
				
				if("FKHT".equals(pact_type_code.toUpperCase()) && "1".equals(map.get("is_exe"))){
					//签订后变更  有金额变动
					addCopyAfter(map, pact_type_code);
				}
				
			}else{// 签订后变动有 变更记录   走此逻辑
				if("FKHT".equals(pact_type_code.toUpperCase())){
					addCopyAfter(map, pact_type_code);
				}
				
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	//签订后变更  付款合同数据备份 mms 20200308
	private void addCopyAfter(Map<String, Object> map, String pact_type_code){
		try {
			
			map.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
			String change_code = "";
			if(map.get("change_code") == null){//新增
				
				map.put("operator", SessionManager.getUserId());
				map.put("state", 0);
				map.put("maker", SessionManager.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				map.put("make_date", format.format(new Date()));
				
				map.put("submiter", null);
				map.put("submit_date", null);
				map.put("confirmer", null);
				map.put("confirm_date", null);
				
				//查询 最大 变更号
				change_code = pactChangeMapper.queryMaxId(map);
				String code = change_code.substring(change_code.indexOf("-") + 1, change_code.length());
				int newCode = Integer.parseInt(code) + 1;
				change_code = map.get("pact_code").toString() + "-" + newCode;
				
				map.put("change_code", change_code);
				
				pactChangeFKHTMapper.add(map);
			}else{//修改
				pactChangeFKHTMapper.update(map);
			}
			
			JSONArray json = JSONArray.parseArray(map.get("sub").toString());
			Double sum_money = 0.00;
			
			// 记录  付款合同金额变动 数据
			List<Map<String,Object>> moneyList =  new ArrayList<Map<String,Object>>();
			// 记录 明细资金来源 变动数据 
			List<Map<String,Object>> sourceList =  new ArrayList<Map<String,Object>>();
			
			if (!json.isEmpty()) {
				Iterator itData = json.iterator();
				while (itData.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
					
					// 金额变动数据
					Map<String,Object> moneyMap = new HashMap<String,Object>();
					moneyMap.put("group_id", SessionManager.getGroupId());
					moneyMap.put("hos_id", SessionManager.getHosId());
					moneyMap.put("copy_code", SessionManager.getCopyCode());
					moneyMap.put("pact_code", map.get("pact_code"));
					moneyMap.put("change_code", change_code);
					moneyMap.put("detail_id",jsonObj.get("detail_id"));
					moneyMap.put("subject_type", jsonObj.get("subject_type"));
					moneyMap.put("subject_id", jsonObj.get("subject_id"));
					moneyMap.put("subject_no", jsonObj.get("subject_no"));
					
					moneyMap.put("amount_a", jsonObj.get("amount"));//变动后
					moneyMap.put("price_a", jsonObj.get("price"));//变动后
					moneyMap.put("money_a", jsonObj.get("money"));//变动后
					moneyMap.put("amount_b", jsonObj.get("before_amount"));//变动前
					moneyMap.put("price_b", jsonObj.get("before_price"));//变动前
					moneyMap.put("money_b", jsonObj.get("before_money"));//变动前
					moneyMap.put("amount_c", jsonObj.get("change_amount"));//变动
					moneyMap.put("price_c", jsonObj.get("change_price"));//变动
					moneyMap.put("money_c", jsonObj.get("change_money"));//变动
					
					sum_money += Double.parseDouble(jsonObj.get("money").toString());
					
					moneyList.add(moneyMap);
					
					//资金来源 变更数据
					if(jsonObj.get("sourceData")!=null && !"".equals(jsonObj.get("sourceData").toString())){
						
						JSONObject sourceData = JSONObject.parseObject(jsonObj.get("sourceData").toString());
						JSONArray source = JSONArray.parseArray(sourceData.get("Rows").toString());
						
						Iterator itSource = source.iterator();
						while (itSource.hasNext()) {
							JSONObject pactSource = JSONObject.parseObject(itSource.next().toString());
							
							Map<String,Object> sourceMap = new HashMap<String,Object>();
							
							sourceMap.put("group_id", SessionManager.getGroupId());
							sourceMap.put("hos_id", SessionManager.getHosId());
							sourceMap.put("copy_code", SessionManager.getCopyCode());
							sourceMap.put("pact_code", map.get("pact_code"));
							sourceMap.put("change_code", change_code);
							sourceMap.put("detail_id", moneyMap.get("detail_id"));
							sourceMap.put("source_id", pactSource.get("source_id"));
							sourceMap.put("money_b", pactSource.get("money"));
							sourceMap.put("money_c", pactSource.get("money_c"));
							sourceMap.put("money_a", pactSource.get("money_a"));
							sourceList.add(sourceMap);
						}
						
					}
				}
			}
			
			// 签订后变动  添加 付款合同金额变动
			if(moneyList.size()>0){
				pactMoneyCFKHTMapper.deleteBatch(moneyList);
				pactMoneyCFKHTMapper.addBatch(moneyList);
			}
			// 签订后变动  添加 明细资金来源变动
			if(sourceList.size()>0){
				pactSourceCFKHTMapper.deleteBatch(sourceList);
				pactSourceCFKHTMapper.addBatch(sourceList);
			}
			
			/**********以下代码往各备份表添加数据************/
			Map<String,Object> copyMap = new HashMap<String,Object>();
			copyMap.put("group_id", SessionManager.getGroupId());
			copyMap.put("hos_id", SessionManager.getHosId());
			copyMap.put("copy_code", SessionManager.getCopyCode());
			copyMap.put("pact_code", map.get("pact_code"));
			copyMap.put("change_code", change_code);
			// 签订后变动 复制合同主表数据
			pactChangeFKHTMapper.deleteCopyMainData(copyMap);
			pactChangeFKHTMapper.copyMainData(copyMap);
			//签订后变动 复制合同明细表数据
			pactChangeFKHTMapper.deleteCopyDetData(copyMap);
			pactChangeFKHTMapper.copyDetData(copyMap);
			//签订后变动 复制合同付款计划表数据
			pactChangeFKHTMapper.deleteCopyPlanData(copyMap);
			pactChangeFKHTMapper.copyPlanData(copyMap);
			//签订后变动 复制合同明细资金来源数据
			pactChangeFKHTMapper.deleteCopySourceData(copyMap);
			pactChangeFKHTMapper.copySourceData(copyMap);
			
			/******** 备份数据 结束*********/
			Map<String,Object> planMap = new HashMap<String,Object>();
			if("1".equals(map.get("is_money_c"))){
				planMap.put("group_id", SessionManager.getGroupId());
				planMap.put("hos_id", SessionManager.getHosId());
				planMap.put("copy_code", SessionManager.getCopyCode());
				planMap.put("pact_code", map.get("pact_code"));
				planMap.put("change_code", change_code);
				if(map.get("plan_detail_id") == null){
					planMap.put("plan_detail_id",pactPlanFKHTCMapper.queryMaxDetailId(planMap)+1);
				}else{
					planMap.put("plan_detail_id",map.get("plan_detail_id"));
				}
				
				planMap.put("pay_id", map.get("pay_id"));
				planMap.put("pay_type", map.get("pay_type"));
				planMap.put("summry", map.get("summry"));
				planMap.put("pay_date", map.get("pay_date"));
				planMap.put("plan_money", map.get("plan_money"));
				planMap.put("pay_cond", map.get("pay_cond"));
				DecimalFormat df = new DecimalFormat("#.00");
				double rate = sum_money ==0.00? 0.00:Double.parseDouble(map.get("plan_money").toString())/sum_money * 100;
				planMap.put("rate", df.format(rate));
				planMap.put("payed_money", 0.00);
				
			}
			// 签订后变动 新增 付款计划
			if(planMap != null){
				if(map.get("plan_detail_id") == null){//添加
					pactPlanFKHTCMapper.add(planMap);
				}else{//修改
					pactPlanFKHTCMapper.update(planMap);
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	/**
	 * 更新金额变更表
	 * 
	 * @param mapVo
	 * @param map
	 * @param pact_type_code
	 */
	private void changeMoney(Map<String, Object> mapVo, Map<String, Object> map, String pact_type_code) {
		mapVo.put("table_code", "PACT_DET_" + pact_type_code.toUpperCase());
		List<Map<String, Object>> preList = pactChangeMapper.queryMainDet(mapVo);
		List<Map<String, Object>> newList = JSON.parseObject(map.get("sub").toString(),
				new TypeReference<List<Map<String, Object>>>() {
				});

		List<Map<String, Object>> dataList = new ArrayList<>();
		for (Map<String, Object> map2 : preList) {
			for (Map<String, Object> map3 : newList) {
				if (map2.get("detail_id").toString().equals(map3.get("detail_id").toString())
						&& !map2.get("money").toString().equals(map3.get("money").toString())) {
					map2.put("amount_a", map3.get("amount"));//变动后数量
					map2.put("price_a", map3.get("price"));//变动后单价
					map2.put("money_a", map3.get("money"));//变动后金额
					//变动数量
					map2.put("price_c", Double.parseDouble(map3.get("price").toString())
							- Double.parseDouble(map2.get("price_b").toString()));
					//变动单价
					map2.put("amount_c", Double.parseDouble(map3.get("amount").toString())
							- Double.parseDouble(map2.get("amount_b").toString()));
					//变动金额
					map2.put("money_c", Double.parseDouble(map3.get("money").toString())
							- Double.parseDouble(map2.get("money").toString()));
					dataList.add(map2);
				}
			}
		}

		if (!dataList.isEmpty()) {
			mapVo.put("list", dataList);
			mapVo.put("table_code", "PACT_MONEY_C_" + pact_type_code.toUpperCase());
			pactChangeMapper.addChangeMoney(mapVo);
		}
	}

	@Override
	public String queryPactMainChangeFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeFKHT(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public PactMainFKHTEntity queryPrePactMainFKHTByChangeCode(Map<String, Object> mapVo) {
		try {
			return pactChangeMapper.queryPrePactMainFKHTByChangeCode(mapVo);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeMoneyFKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeMoneyFKHT(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryPactMainChangeMoneyFKHTPrint(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeMoneyFKHT(mapVo);
			return query;
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMoneyChangeDet(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMoneyChangeDet(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public PactMainSKHTEntity queryPrePactMainSKHTByChangeCode(Map<String, Object> mapVo) {
		try {
			return pactChangeMapper.queryPrePactMainSKHTByChangeCode(mapVo);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeSKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeSKHT(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeMoneySKHT(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeMoneySKHT(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryPactMainChangeMoneySKHTPrint(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeMoneySKHT(mapVo);
			return query;
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeFKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeFKXY(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public PactMainFKXYEntity queryPrePactMainFKXYByChangeCode(Map<String, Object> mapVo) {
		try {
			return pactChangeMapper.queryPrePactMainFKXYByChangeCode(mapVo);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeSKXY(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactMainChangeSKXY(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public PactMainSKXYEntity queryPrePactMainSKXYByChangeCode(Map<String, Object> mapVo) {
		try {
			return pactChangeMapper.queryPrePactMainSKXYByChangeCode(mapVo);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactPlanFKHTForPre(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactPlanFKHTForPre(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactPlanSKHTForPre(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> query = pactChangeMapper.queryPactPlanSKHTForPre(mapVo);
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
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

			if ("FKHT".equals(pact_type_code) || "SKHT".equals(pact_type_code)) {
				map.put("money_table", "PACT_MONEY_C_" + pact_type_code.toUpperCase());
				pactChangeMapper.deleteCopyMoney(map);
				map.put("plan_table", "PACT_PLAN_" + pact_type_code.toUpperCase() + "_C");
				pactChangeMapper.deleteCopyPlan(map);
			}
			pactChangeMapper.deleteCopyDet(map);
			pactChangeMapper.deleteCopyMain(map);
			pactChangeMapper.deleteChangeMain(map);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeFKHTAfter(Map<String, Object> entityMap) throws DataAccessException {
		try {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String,Object>> query = (List<Map<String,Object>>) pactChangeMapper.queryPactMainChangeFKHTAfter(entityMap);
				return ChdJson.toJson(query);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = (List<Map<String,Object>>) pactChangeMapper.queryPactMainChangeFKHTAfter(entityMap,rowBounds);
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
	public Map<String, Object> queryPactChangeFKHTAfterByCode(Map<String, Object> mapVo) throws DataAccessException {
		try {
			return pactChangeMapper.queryPactChangeFKHTAfterByCode(mapVo);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryPactMainChangeFKHTAfterDet(Map<String, Object> page)	throws DataAccessException {
		try {
			if (page.get("change_code") == null) {
				page.put("table_code", "PACT_DET_FKHT");
			} else {
				page.put("table_code", "PACT_DET_FKHT_C");
			}
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> query = (List<Map<String,Object>>) pactChangeMapper.queryPactMainChangeFKHTAfterDet(page);
			for(Map<String,Object> item:query){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", item.get("group_id"));
				map.put("hos_id", item.get("hos_id"));
				map.put("copy_code", item.get("copy_code"));
				map.put("pact_code", item.get("pact_code"));
				map.put("detail_id", item.get("detail_id"));
				
				List<Map<String,Object>> sourceData = (List<Map<String, Object>>) pactSourceFKHTMapper.query(map);
				
				item.put("sourceData", JSONObject.parseObject(ChdJson.toJson(sourceData)));
			}
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	@Override
	public String queryPactChangeFKHTAfterDetUpdate(Map<String, Object> page) throws DataAccessException {
		try {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> query = (List<Map<String,Object>>) pactMoneyCFKHTMapper.queryPactChangeFKHTAfterDetUpdate(page);
			for(Map<String,Object> item:query){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", item.get("group_id"));
				map.put("hos_id", item.get("hos_id"));
				map.put("copy_code", item.get("copy_code"));
				map.put("pact_code", item.get("pact_code"));
				map.put("change_code", item.get("change_code"));
				map.put("detail_id", item.get("detail_id"));
				
				List<Map<String,Object>> sourceData = (List<Map<String, Object>>) pactSourceCFKHTMapper.query(map);
				
				item.put("sourceData", JSONObject.parseObject(ChdJson.toJson(sourceData)));
			}
			return ChdJson.toJson(query);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	@Override
	public String updatePactMainChange(Map<String, Object> map, String pact_type_code) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("operator", SessionManager.getUserId());
			mapVo.put("change_date", map.get("change_date"));
			mapVo.put("change_reason", map.get("change_reason"));
			mapVo.put("is_exe", 1);
			mapVo.put("is_money_c", map.get("is_money_c"));
			mapVo.put("pay_id", map.get("pay_id"));
			mapVo.put("pay_type", map.get("pay_type"));
			mapVo.put("summary", map.get("summary"));
			mapVo.put("pay_date", map.get("pay_date"));
			mapVo.put("pay_cond", map.get("pay_cond"));
			mapVo.put("plan_money", map.get("plan_money"));
			mapVo.put("value_c_code", map.get("value_c_code"));
			mapVo.put("pact_code", map.get("pact_code"));
			mapVo.put("change_code", map.get("change_code"));
			
			pactChangeFKHTMapper.update(mapVo);
			
			if("1".equals(map.get("is_exe")) ){
				//签订后变更  有金额变动
				addCopyAfter(map, pact_type_code);
			}
				
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String updateChangeFKHTState(List<Map<String, Object>> listVo) throws DataAccessException {
		
		try {	
			
			pactChangeFKHTMapper.updateChangeFKHTState(listVo);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}	
		
		
	}
	@Override
	public String confirmPactChangeFKHTAfter(List<Map<String, Object>> listVo)	throws DataAccessException {
		try {	
			
			pactChangeFKHTMapper.updateChangeFKHTState(listVo);
			
			// 修改 合同相关表 数据 先删除 后添加
			pactChangeFKHTMapper.deletePactSource(listVo);
			pactChangeFKHTMapper.deletePactDet(listVo);
			pactChangeFKHTMapper.deletePactPlan(listVo);
			pactChangeFKHTMapper.deletePactMain(listVo);
			
			pactChangeFKHTMapper.addPactMain(listVo);
			pactChangeFKHTMapper.addPactDet(listVo);
			pactChangeFKHTMapper.addPactSource(listVo);
			pactChangeFKHTMapper.addPactPlan(listVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	@Override
	public String deletePactMainFKHTAfter(List<Map<String, Object>> listVo)	throws DataAccessException {
		try {	
			
			for(Map<String,Object> item : listVo){
				
				String change_code = item.get("change_code").toString();
				
				String code = change_code.substring(change_code.indexOf("-")+1, change_code.length());
				if("1".equals(code)){ // 等于 1 要删除  0 号相关的数据 否则 下次添加数据有问题
					Map<String,Object> map = new HashMap<String,Object>();
					
					map.putAll(item);
					map.put("change_code", map.get("pact_code").toString() +"-0");
					
					// 删除 变更主表表数
					pactChangeFKHTMapper.delete(map);
					pactMoneyCFKHTMapper.delete(map);
					pactSourceCFKHTMapper.delete(map);
					// 删除 合同备份相关表 数据
					pactChangeFKHTMapper.deleteCopySourceData(map);
					pactChangeFKHTMapper.deleteCopyDetData(map);
					pactChangeFKHTMapper.deleteCopyPlanData(map);
					pactChangeFKHTMapper.deleteCopyMainData(map);
				}
				// 删除 变更主表表数
				pactChangeFKHTMapper.delete(item);
				pactMoneyCFKHTMapper.delete(item);
				pactSourceCFKHTMapper.delete(item);
				// 删除 合同备份相关表 数据
				pactChangeFKHTMapper.deleteCopySourceData(item);
				pactChangeFKHTMapper.deleteCopyDetData(item);
				pactChangeFKHTMapper.deleteCopyPlanData(item);
				pactChangeFKHTMapper.deleteCopyMainData(item);
			}
			
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	@Override
	public int checkUnconfirmData(Map<String, Object> mapVo) throws DataAccessException {
		try {	
			//签订后变更 添加时 根据合同编码 查询 是否存在 未确认 变更数据
			int count = pactChangeFKHTMapper.checkUnconfirmData(mapVo);
			
			return count;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
		}	
	}
	@Override
	public String updatePactMainFKHTCopy(Map<String, Object> mapVo)	throws DataAccessException {
		try {
			if (mapVo.get("sub") != null) {
				JSONArray json = JSONArray.parseArray(mapVo.get("sub").toString());
				if (!json.isEmpty()) {
					addPactDetFKHTCopy(json, mapVo.get("pact_code").toString(),mapVo.get("change_code").toString());
					
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
							entity.setPlan_money(Double.valueOf(mapVo.get("pact_money").toString()));
							entity.setSummary(null);
							entity.setPay_flag(0);
							list.add(entity);
						}
					} else {
						list = JSONArray.parseArray(mapVo.get("plan").toString(), PactPlanFKHTEntity.class);
					}
					if (!list.isEmpty()) {
						addPactPlanFKHTCopy(list, mapVo.get("pact_code").toString(),mapVo.get("change_code").toString());
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

			pactMainFKHTCMapper.update(mapVo);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
			
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加付款合同明细 备份表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	private String addPactDetFKHTCopy(JSONArray json, String pact_code,String change_code) {
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
			map.put("pact_code", change_code);

			Integer detailId = pactDetFKHTCMapper.queryMaxDetailId(map);
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
				detailMap.put("change_code", change_code);
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
					bidMap.put("change_code", change_code);
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
					applyMap.put("change_code", change_code);
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
					sourceMap.put("change_code", change_code);
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
						sourceMap.put("change_code", change_code);
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
			
			pactSourceFKHTCMapper.deleteBatchByDet(sourceList);
			pactSourceFKHTCMapper.addBatch(sourceList);
			pactDetFKHTCMapper.deleteBatch(detailList);
			pactDetFKHTCMapper.addBatch(detailList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	
	/**
	 * 添加付款计划 备份表
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	private String addPactPlanFKHTCopy(List<PactPlanFKHTEntity> listVo, String pact_code,String change_code) {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("pact_code", pact_code);
			entityMap.put("change_code", change_code);

			Integer detail_id = pactPlanFKHTCMapper.queryMaxDetailId(entityMap);
			if (detail_id == null) {
				detail_id = 1;
				for (PactPlanFKHTEntity pactPlanSKHTEntity : listVo) {
					pactPlanSKHTEntity.setPact_code(pact_code);
					pactPlanSKHTEntity.setChange_code(change_code);
					pactPlanSKHTEntity.setPlan_detail_id(detail_id++);
				}
			} else {
				for (PactPlanFKHTEntity pactPlanSKHTEntity : listVo) {
					if (pactPlanSKHTEntity.getPlan_detail_id() == null) {
						pactPlanSKHTEntity.setPact_code(pact_code);
						pactPlanSKHTEntity.setChange_code(change_code);
						pactPlanSKHTEntity.setPlan_detail_id(++detail_id);
					}else{
						pactPlanSKHTEntity.setPact_code(pact_code);
						pactPlanSKHTEntity.setChange_code(change_code);
					}
				}
			}

			pactPlanFKHTCMapper.deleteBatchByEntity(listVo);
			pactPlanFKHTCMapper.addBatch(listVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	@Override
	public void addPactMainXYChange(Map<String, Object> map, String pact_type_code) {
		try {
			Map<String, Object> mapVo = new HashMap<>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("operator", SessionManager.getUserId());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("change_date", format.format(new Date()));
			mapVo.put("change_reason", map.get("change_reason"));
			mapVo.put("pact_code", map.get("pact_code"));
			mapVo.put("is_exe",  map.get("is_exe"));
			mapVo.put("state",  map.get("state"));
			// 更新合同变更表
			mapVo.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
			String change_code = pactChangeMapper.queryMaxId(mapVo);
			if (change_code == null) {
				change_code = map.get("pact_code").toString() + "-1";
			} else {
				String code = change_code.substring(change_code.indexOf("-") + 1, change_code.length());
				int newCode = Integer.parseInt(code) + 1;
				change_code = map.get("pact_code").toString() + "-" + newCode;
			}
			mapVo.put("change_code", change_code);
			pactChangeMapper.addPactChangeXY(mapVo);

			// 备份明细表
			mapVo.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
			// 查询主合同
			List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(mapVo);
			mapVo.putAll(before.get(0));
			// 查询明细表
			mapVo.put("table_code_f", "PACT_DET_" + pact_type_code.toUpperCase());
			List<Map<String, Object>> detList = pactChangeMapper.queryChangeBefore(mapVo);

			switch (pact_type_code.toUpperCase()) {
			case "FKXY":
				pactChangeMapper.copyPactMainFKXY(mapVo);
				if (!detList.isEmpty()) {
					mapVo.put("list", detList);
					pactChangeMapper.copyPactDetFKXY(mapVo);
				}
				break;
			case "SKXY":
				pactChangeMapper.copyPactMainSKXY(mapVo);
				if (!detList.isEmpty()) {
					mapVo.put("list", detList);
					pactChangeMapper.copyPactDetSKXY(mapVo);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
