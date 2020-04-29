package com.chd.hrp.acc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.service.AccCashFlowService;
import com.chd.hrp.acc.service.AccVouchCashFlowService;
import com.chd.hrp.acc.service.commonbuilder.AccCashItemService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchCashFlowController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchCashFlowController.class);

	@Resource(name = "accVouchCashFlowService")
	private final AccVouchCashFlowService accVouchCashFlowService = null;
	
	@Resource(name = "accCashItemService")
	private final AccCashItemService accCashItemService = null;
	
	@Resource(name = "accCashFlowService")
	private final AccCashFlowService accCashFlowService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;

	
	/**
	 * 现金流量标注<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/accVouchCashFlowMainPage", method = RequestMethod.GET)
	public String accVouchCashFlowMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("mod_code", "01");
		map.put("para_code", "'004'");//004:现金流量与辅助核算一起保存
		List<Map<String,Object>> paralist=new ArrayList<Map<String,Object>>();
		paralist=superVouchService.queryVouchParaListByParaCode(map);
		if(paralist!=null && paralist.size()>0){
			mode.addAttribute("para004",paralist.get(0).get("para_value").toString());
		}
		return "hrp/acc/accvouch/cashflow/accVouchCashFlowMain";
		
	}


	/**
	 * 凭证主表<BR>
	 * 按照凭证类型统计凭证记账相关数据
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryAccVouchCashFlow", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccVouchCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		String accVouch = accVouchCashFlowService.queryAccVouchCashFlow(getPage(mapVo));
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/accVouchCashFlowUpdatePage",  method = RequestMethod.GET)
	public String accVouchCashFlowUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("vouch_id", mapVo.get("vouch_id").toString());
		
		mode.addAttribute("vouch_detail_id", mapVo.get("vouch_detail_id").toString());
		
		mode.addAttribute("cash_dire", mapVo.get("cash_dire").toString());
		
		mode.addAttribute("cash_item_id", mapVo.get("cash_item_id").toString());
		
		return "hrp/acc/accvouch/cashflow/accVouchCashFlowUpdate";
		
	}
	
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/AccVouchCashFlowUnbatch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> AccVouchCashFlowUnbatch(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		String [] vouchDetailId = paramVo.split(",");
		
		for (int i = 0; i < vouchDetailId.length; i++) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();			
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code",SessionManager.getCopyCode());
			
			mapVo.put("vouch_id", vouchDetailId[i].split("@")[0]);
			
			mapVo.put("vouch_detail_id", vouchDetailId[i].split("@")[1]);
			
			listVo.add(mapVo);
			
		}
		
		String accVouch = accVouchCashFlowService.deleteAccVouchCashFlow(listVo);
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryAccVouchCashItemFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchCashItemFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("acc_month") == null) {
			mapVo.put("acc_month", "11");
		}
		String accVouch =  accCashItemService.queryAccCashItem(getPage(mapVo));
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/saveAccVouchCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccVouchCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<AccCashFlow> list = accVouchCashFlowService.queryAccCashFlowListByVouchId(mapVo);
			
		String [] cash_item =mapVo.get("cash_item_id").toString().split(",");
		
		for (AccCashFlow accCashFlow : list) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			String [] money =cash_item[0].split("@");
			
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			
			map.put("cash_id",UUIDLong.absStringUUID());
			
			map.put("create_user", SessionManager.getUserId());
			
			map.put("create_date", new Date());
			
			map.put("cash_money", accCashFlow.getCash_money());
			
			map.put("summary", accCashFlow.getSummary());
			
			map.put("cash_item_id", money[0]);
			
			map.put("vouch_id", mapVo.get("vouch_id").toString());
			
			map.put("vouch_detail_id", mapVo.get("vouch_detail_id").toString());
			
			listVo.add(map);
			
		}
		
		String accVouch =  accVouchCashFlowService.saveAccVouchCashFlow(listVo);
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/deleteAccVouchCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("acc_month") == null) {
			mapVo.put("acc_month", "11");
		}
		String accVouch =  accCashFlowService.deleteBatchAccCashFlow(listVo);
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryAccVouchCashFlowTemplate", method = RequestMethod.GET)
	public String queryAccVouchCashFlowTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("acc_month") == null) {
			mapVo.put("acc_month", "11");
		}
		String accVouch = accVouchCashFlowService.queryAccVouchCashFlow(getPage(mapVo));
		
		return "hrp/acc/accvouch/cashflow/accVouchCashFlowTemplate";
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryAccVouchCashFlowSetSubj", method = RequestMethod.GET)
	public String queryAccVouchCashFlowSetSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("subj_code_other",mapVo.get("subj_code_other"));
		
		return "hrp/acc/accvouch/cashflow/accVouchCashFlowTemplateSet";
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryBatchAccVouchCashFlowSetSubj", method = RequestMethod.GET)
	public String queryBatchAccVouchCashFlowSetSubj(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<String> list = new ArrayList<String>();
		for (String id : paramVo.split(",")) {
			list.add(id);
		}
		mode.addAttribute("subj_code_other",list);
		return "hrp/acc/accvouch/cashflow/accVouchCashFlowTemplateSet";
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryCashFlowSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCashFlowSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("acc_month") == null) {
			mapVo.put("acc_month", "11");
		}
		String accVouch =  accVouchCashFlowService.queryCashFlowSubj(getPage(mapVo));
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/updateAccVouchCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchCashFlow(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		String cash_item = paramVo.split("@")[1];
		
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("cash_item_code", cash_item);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			mapVo.put("subj_code_other", id.split("@")[0]);
			listVo.add(mapVo);
		}
		
		String accVouch =  accVouchCashFlowService.updateBatchAccVouchCashFlow(listVo);
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/AccVouchBatchUnset", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> AccVouchBatchUnset(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("cash_item_code", "");// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			mapVo.put("subj_code_other", id.split("@")[0]);
			listVo.add(mapVo);
		}
		
		String accVouch =  accVouchCashFlowService.updateBatchAccVouchCashFlow(listVo);
		
		return JSONObject.parseObject(accVouch);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/queryAccCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String accVouch =  accVouchCashFlowService.queryAccCashFlow(mapVo);
		
		return JSONObject.parseObject(accVouch);
		
	}

	//批量标注页面跳转
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/AccVouchCashFlowBatch",  method = RequestMethod.GET)
	public String AccVouchCashFlowBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/acc/accvouch/cashflow/accVouchCashFlowBatch";
	}

	//批量标注保存
	@RequestMapping(value = "/hrp/acc/accvouch/cashflow/saveAccVouchCashFlowBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccVouchCashFlowBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		Map<String, Object> retMap = null;
		
		try {
			retMap =  accVouchCashFlowService.saveAccVouchCashFlowBatch(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
		
	}
}
