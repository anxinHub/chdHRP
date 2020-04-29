package com.chd.hrp.pac.serviceImpl.skht.changeaftersign;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.common.PactChangeMapper;
import com.chd.hrp.pac.dao.skht.changeaftersign.PactChangeAfterSignSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactDetSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactMainSKHTMapper;
import com.chd.hrp.pac.dao.skht.pactinfo.PactPlanSKHTMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.service.skht.changeaftersign.PactChangeAfterSignSKHTService;
import com.chd.hrp.pac.serviceImpl.skht.pactinfo.PactMainSKHTServiceImpl;
@Service("pactChangeAfterSignSKHTService")
public class PactChangeAfterSignSKHTServiceImpl implements
		PactChangeAfterSignSKHTService {
	private static Logger logger = Logger.getLogger(PactChangeAfterSignSKHTServiceImpl.class);
	@Resource(name="pactChangeAfterSignSKHTMapper")
	private final PactChangeAfterSignSKHTMapper pactChangeAfterSignSKHTMapper=null;
	
	@Resource(name = "pactMainSKHTMapper")
	private PactMainSKHTMapper pactMainSKHTMapper;
    
	@Resource(name = "pactChangeMapper")
	private PactChangeMapper pactChangeMapper;
	
	@Resource(name = "pactPlanSKHTMapper")
	private PactPlanSKHTMapper pactPlanSKHTMapper;
	
	@Resource(name = "pactDetSKHTMapper")
	private PactDetSKHTMapper pactDetSKHTMapper;
	
	/**
	 * 查询合同名称编码
	 */
	public String querySKHTbyCus(Map<String, Object> mapVo)
			throws DataAccessException {
		List<Map<String, Object>> list=pactChangeAfterSignSKHTMapper.querySKHTbyCus(mapVo);
		return JSON.toJSONString(list);
	}
	@Override
	public String queryExitsPactOthers(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			
			String count=pactChangeAfterSignSKHTMapper.queryExitsPactOthers(mapVo);
			return "{\"state\":\"true\",\"count\":\""+count+"\"}";
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}
	@Override
	public String addChangeAfterSign(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
		///如果没有备份变更前合同，先备份
        String pact_type_code="SKHT";
        mapVo.put("table_code", "PACT_CHANGE_" + pact_type_code.toUpperCase());
		Map<String, Object> map = new HashMap<>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("operator", SessionManager.getUserId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		map.put("change_date", format.format(new Date()));
		map.put("change_reason", "修改变更");
		map.put("pact_code", mapVo.get("pact_code"));
		// 更新合同变更表
		
		String change_code = pactChangeMapper.queryMaxId(mapVo);
		if ((change_code == null)&&(!"".equals(change_code))) {
			change_code = mapVo.get("pact_code").toString() + "-0";
			map.put("change_code", change_code);
			//pactChangeMapper.add(map);
			  Date mkdate=new Date();
			  String makedate=format.format(mkdate);
			  map.put("user_id", SessionManager.getUserId());
			  map.put("make_date", makedate);
			  map.put("is_exe", 0);
			pactChangeAfterSignSKHTMapper.addChangeSKHTMapper(map);
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
		String change_codeAfter=addPactChangeAfter(mapVo);
			
		 return "{\"msg\":\"添加成功\",\"state\":\"true\",\"change_code\":\""+change_codeAfter+"\"}";
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
		//return null;
	}
	@Override
	public Map<String, Object> querySKHTByChangeCode(Map<String, Object> mapVo)
			throws DataAccessException {
		Map<String, Object> map=pactChangeAfterSignSKHTMapper.querySKHTByChangeCode(mapVo);
		return map;
	}
  public String addPactChangeAfter(Map<String, Object> mapVo){
	try{
	  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
	    String pact_type_code="SKHT";
	  ///再次备份合同，用来修改
			//添加变更记录
			String change_codeafter = pactChangeMapper.queryMaxId(mapVo);
			if (change_codeafter == null) {
				change_codeafter = mapVo.get("pact_code").toString() + "-1";
			} else {
				String code = change_codeafter.substring(change_codeafter.indexOf("-") + 1, change_codeafter.length());
				int newCode = Integer.parseInt(code) + 1;
				change_codeafter = mapVo.get("pact_code").toString() + "-" + newCode;
			}
		   mapVo.put("change_code", change_codeafter);
		   Date mkdate=new Date();
		   String makedate=format.format(mkdate);
		   mapVo.put("user_id", SessionManager.getUserId());
		   mapVo.put("operator", SessionManager.getUserId());
		   mapVo.put("make_date", makedate);
		   mapVo.put("is_exe", 1);
		   if("true".equals(mapVo.get("is_money"))){mapVo.put("is_money_c", "1");}else{mapVo.put("is_money_c", "0");}
			int state=pactChangeAfterSignSKHTMapper.addChangeSKHTMapper(mapVo);
			
			/**
			 * 如果有金额变动，把变动金额存入金额变动表，并且明细进行备份
			 */
			if("true".equals(mapVo.get("is_money"))){
				
				// 备份明细表(变动后金额数量等)
				mapVo.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
				// 查询主合同
				List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(mapVo);
				mapVo.putAll(before.get(0));
				
				
				mapVo.put("table_code_f", "PACT_PLAN_" + pact_type_code.toUpperCase());
				List<Map<String, Object>>  planList = pactChangeMapper.queryChangeBefore(mapVo);
				pactChangeMapper.copyPactMainSKHT(mapVo);
				//备份明细表(变动后金额数量等)
				List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("sub").toString());
				
				//System.out.println(detail);
				for (Map<String, Object> mapdep : detail) {
					mapdep.put("change_code", change_codeafter);
					mapdep.put("group_id", SessionManager.getGroupId());
					mapdep.put("hos_id", SessionManager.getHosId());
					mapdep.put("copy_code", SessionManager.getCopyCode());
					mapdep.put("dept_id", mapdep.get("dept_no").toString().split("@")[0]);
					mapdep.put("dept_no", mapdep.get("dept_no").toString().split("@")[1]);
					mapdep.put("copy_code", SessionManager.getCopyCode());
					pactChangeAfterSignSKHTMapper.copyPactDetSKHT(mapdep);
					
					///同时把变动数据存储金额变动表
					pactChangeAfterSignSKHTMapper.addPactMoneycSKHT(mapdep);
				}
				
				if (!planList.isEmpty()) {
					//增加一条付款计划
					mapVo.put("list", planList);
					pactChangeMapper.copyPactPlanSKHT(mapVo);
					//System.out.println(mapVo);
					pactChangeAfterSignSKHTMapper.addPactPlanSKHT(mapVo);
				}	
				///更新主表金额
				pactChangeAfterSignSKHTMapper.updatePactMainMoney(mapVo);
			}else{
			// 备份明细表
			mapVo.put("table_code_f", "PACT_MAIN_" + pact_type_code.toUpperCase());
			// 查询主合同
			List<Map<String, Object>> before = pactChangeMapper.queryChangeBefore(mapVo);
			mapVo.putAll(before.get(0));
			// 查询明细表
			mapVo.put("table_code_f", "PACT_DET_" + pact_type_code.toUpperCase());
			List<Map<String, Object>> deList = pactChangeMapper.queryChangeBefore(mapVo);
			
			// 查询计划表
			//changeMoney(mapVo, map, pact_type_code);
			mapVo.put("table_code_f", "PACT_PLAN_" + pact_type_code.toUpperCase());
			List<Map<String, Object>>  planList = pactChangeMapper.queryChangeBefore(mapVo);
			pactChangeMapper.copyPactMainSKHT(mapVo);
			if (!deList.isEmpty()) {
				mapVo.put("list", deList);
				pactChangeMapper.copyPactDetSKHT(mapVo);
			}
			if (!planList.isEmpty()) {
				mapVo.put("list", planList);
				pactChangeMapper.copyPactPlanSKHT(mapVo);
			}	
			}
	  return change_codeafter;
	  
	}catch(Exception e){
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(), e);
	}
	
  }
  
	@Override
	public String queryPactDetSKHT(Map<String, Object> page) {
		try {
			
			if("pact_det_skht_c".equals(page.get("table_name"))){

				List<Map<String, Object>> query = (List<Map<String, Object>>) pactChangeAfterSignSKHTMapper.queryEdit(page);
				return ChdJson.toJson(query);
			}else{
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> query = (List<Map<String, Object>>) pactChangeAfterSignSKHTMapper.query(page);
			return ChdJson.toJson(query);
			}
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 删除所有备份数据
	 */
	public String deletePactMainSKHTC(
			List<Map<String, Object>> mapVo) throws DataAccessException {
		try{
			pactChangeAfterSignSKHTMapper.deletePactChangeSKHT(mapVo);
			pactChangeAfterSignSKHTMapper.deletePactDetSKHTC(mapVo);
			pactChangeAfterSignSKHTMapper.deletePactPlanSKHTC(mapVo);
			pactChangeAfterSignSKHTMapper.deletePactMnoeySKHTC(mapVo);
			pactChangeAfterSignSKHTMapper.deletePactMainSKHTC(mapVo);
			
			 return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}
	@Override
	public String updateSubmitPactMainSKHTC(List<Map<String, Object>> mapVo)
			throws DataAccessException {
		try{
			pactChangeAfterSignSKHTMapper.updateSubmitPactMainSKHTC(mapVo);
		
			
			 return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		}catch(DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	@Override
	public String updateCancelPactMainSKHTC(List<Map<String, Object>> mapVo)
			throws DataAccessException {
		try{
			pactChangeAfterSignSKHTMapper.updateCancelPactMainSKHTC(mapVo);
		
			
			 return "{\"msg\":\"撤销成功.\",\"state\":\"true\"}";
		}catch(DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	@Override
	public String confirmPactMainSKHTC(List<Map<String, Object>> listVo)
			throws DataAccessException {
		try{
			
		
		  //同时更新合同表相关数据(这里有可能是批量确认，需要循环)
			//思路1，合同主表更新：先查询合同备份表返回map,然后更新合同主表
			//2、明细和计划等直接用备份表去更新
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");    
			for (Map<String, Object> map : listVo) {
				pactChangeAfterSignSKHTMapper.confirmPactMainSKHTC(map);
				
				PactMainSKHTEntity entity=pactChangeMapper.queryPrePactMainSKHTByChangeCode(map);
				///日期格式转换为MAP后格式不对，这里重新处理
				
                Date sign_date=entity.getSign_date();
                Date start_date=entity.getStart_date();
                Date end_date=entity.getEnd_date();
				@SuppressWarnings("unchecked")
				Map<String, Object> mapeach= JSON.parseObject(JSON.toJSONString(entity), Map.class);
				mapeach.put("group_id", map.get("group_id"));
				mapeach.put("hos_id", map.get("hos_id"));
				mapeach.put("copy_code", map.get("copy_code"));
				///处理日期
				mapeach.put("sign_date", format.format(sign_date));
				mapeach.put("start_date", format.format(start_date));
				mapeach.put("end_date", format.format(end_date));
				
				//System.out.println(mapeach);
				///下面更新主表
				pactMainSKHTMapper.update(mapeach);
				
				///明细表删除后重新添加
				pactDetSKHTMapper.deleteByPactCode(map);
				pactChangeAfterSignSKHTMapper.addPactDetBySKHTC(map);
				
				///付款计划删除后重新添加
				pactPlanSKHTMapper.deleteByPactCode(map);
				pactChangeAfterSignSKHTMapper.addPactPlanBySKHTC(map);
			}
			 return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		}catch(DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 更新合同备份表（修改备份合同，确认后更新到主合同）
	 */
	public String updatePactMainSKHTC(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			pactChangeAfterSignSKHTMapper.updatePactMainSKHTC(mapVo);
            ///如果要过更新明细，下面考虑明细更新
			
			 return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch(DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
	/**
	 * 更新变更单(变更单修改)
	 */
	public String updateChangeAfterSign(Map<String, Object> mapVo)
			throws DataAccessException {
		try{
			pactChangeAfterSignSKHTMapper.updatePactChangeSKHT(mapVo);
            ///如果要更新明细，下面考虑明细更新
		if("true".equals(mapVo.get("is_money"))){
				 List<Map<String, Object>> listvo=new ArrayList();
				 listvo.add(mapVo);
				 ///先删除再重新添加明细he和金额变动数据
				 pactChangeAfterSignSKHTMapper.deletePactDetSKHTC(listvo); 
				 pactChangeAfterSignSKHTMapper.deletePactMnoeySKHTC(listvo);
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("sub").toString());
			//System.out.println(detail);
			for (Map<String, Object> mapdep : detail) {
				mapdep.put("change_code", mapVo.get("change_code"));
				mapdep.put("group_id", SessionManager.getGroupId());
				mapdep.put("hos_id", SessionManager.getHosId());
				mapdep.put("copy_code", SessionManager.getCopyCode());
				mapdep.put("dept_no", mapdep.get("dept_no").toString().split("@")[1]);
				mapdep.put("dept_id", mapdep.get("dept_no").toString().split("@")[0]);
				mapdep.put("copy_code", SessionManager.getCopyCode());
				pactChangeAfterSignSKHTMapper.copyPactDetSKHT(mapdep);
				
				///同时把变动数据存储金额变动表
				pactChangeAfterSignSKHTMapper.addPactMoneycSKHT(mapdep);
			}
			
			///更新主表金额
			pactChangeAfterSignSKHTMapper.updatePactMainMoney(mapVo);
			
			//下面考虑更新收款计划(先删除，然后更新)
			List<Map<String, Object>> list=new ArrayList();
			list.add(mapVo);
			pactChangeAfterSignSKHTMapper.deletePactPlanSKHTC(list);
			mapVo.put("table_code_f", "PACT_PLAN_SKHT") ;
			List<Map<String, Object>>  planList = pactChangeMapper.queryChangeBefore(mapVo);
			 if (!planList.isEmpty()) {
				//增加一条付款计划
				mapVo.put("list", planList);
				pactChangeMapper.copyPactPlanSKHT(mapVo);
				//System.out.println(mapVo);
				pactChangeAfterSignSKHTMapper.addPactPlanSKHT(mapVo);
			}	
		}
			 return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch(DataAccessException e) {
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}
}
