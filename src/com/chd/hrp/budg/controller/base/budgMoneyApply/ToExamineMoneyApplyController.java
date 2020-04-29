/***
 * zzb 2018-4-30 15:29:16
 * 
 * 用款申请审核
 */
package com.chd.hrp.budg.controller.base.budgMoneyApply;

import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.hrp.budg.service.base.budgMoneyApply.MoneyApplyService;
import com.chd.hrp.budg.service.base.budgMoneyApply.ToExamineMoneyApplyService;

@Controller
public class ToExamineMoneyApplyController extends BaseController{

	private static Logger logger = Logger.getLogger(ToExamineMoneyApplyController.class);
	
	@Resource(name="toExamineMoneyApplyService")
	private final ToExamineMoneyApplyService toExamineMoneyApplyService = null;
	
	/**
	 * 主页面跳转
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/ToExamineMoneyApplyMainPage",method = RequestMethod.GET)
	public String MoneyApplyMainPage() {
		
		
		return "hrp/budg/base/budgMoneyApply/ToExamineMoneyApply/ToExamineMoneyApplyMain";
	}
	
	
	
	
	/**
	 * 主页面查询数据
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryToExamineMoneyApply",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> queryToExamineMoneyApply(@RequestParam Map<String, Object> mapVo, Model mode){
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (!"".equals(mapVo.get("proj_dict")) && mapVo.get("proj_dict")!=null ) {
			mapVo.put("proj_dict", mapVo.get("proj_dict").toString().split("\\.")[0]);
		}
		if (!"".equals(mapVo.get("apply_name")) && mapVo.get("apply_name")!=null ) {
			mapVo.put("apply_name", mapVo.get("apply_name").toString().split("\\.")[0]);
		}
		
		String data = toExamineMoneyApplyService.queryToExamineMoneyApply(getPage(mapVo));
		
		return JSONObject.parseObject(data);
	}
	

	
	
	

	
	/**
	 * 明细页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/ToExamineMoneyApplyDetail", method = RequestMethod.GET)
	public String ToExamineMoneyApplyDetail(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		Map<String,Object> map=toExamineMoneyApplyService.ToExamineMoneyApplyDetail(mapVo);
		mode.addAllAttributes(map);
		return "hrp/budg/base/budgMoneyApply/ToExamineMoneyApply/ToExamineMoneyApplyDetail";
		
	}
	
	
	
	/**
	 * 修改页面的查询明细
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/queryToExamineMoneyApplyDet",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> queryToExamineMoneyApplyDet(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String data = toExamineMoneyApplyService.queryToExamineMoneyApplyDet(mapVo);
		
		return JSONObject.parseObject(data);
	}
	

	
	
	/**
	 * 主页面审核、明细页面审核   state_t 01新建、02已提交、03已审核
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/updateToExamineMoneyApplyState",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> updateToExamineMoneyApplyState(@RequestParam Map<String, Object> mapVo, Model mode){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String data : mapVo.get("paramVo").toString().split(",")) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("apply_code", data);
			
			
			list.add(map);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("checker",SessionManager.getUserId());
		map.put("list", list );
		String stat = toExamineMoneyApplyService.updateToExamineMoneyApplyState(map);

		
		return JSONObject.parseObject(stat);
	}
	
	
	/**
	 * 主页面消审、明细页面消审   state_t 01新建、02已提交、03已审核
	 * @return
	 */
	@RequestMapping(value = "hrp/budg/base/budgMoneyApply/Apply/updateToExamineMoneyApplyStateRevoke",method = RequestMethod.POST )
	@ResponseBody
	public Map<String,Object> updateToExamineMoneyApplyStateRevoke(@RequestParam Map<String, Object> mapVo, Model mode){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for (String data : mapVo.get("paramVo").toString().split(",")) {
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			
			map.put("hos_id", SessionManager.getHosId());
			
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("apply_code", data);

			
			list.add(map);
		}
		
		String stat = toExamineMoneyApplyService.updateToExamineMoneyApplyStateRevoke(list);

		
		return JSONObject.parseObject(stat);
	}
	

	
	
}
