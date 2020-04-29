/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.serviceImpl.AccCashFlowServiceImpl;

/**
* @Title. @Description.
* 现金流量标注
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCashFlowController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCashFlowController.class);
	
	
	@Resource(name = "accCashFlowService")
	private final AccCashFlowServiceImpl accCashFlowService = null;
   
   
	/**
	*现金流量标注<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashflow/accCashFlowMainPage", method = RequestMethod.GET)
	public String accCashFlowMainPage(Model mode) throws Exception {

		return "hrp/acc/acccashflow/accCashFlowMain";

	}
	/**
	*现金流量标注<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccashflow/accCashFlowAddPage", method = RequestMethod.GET)
	public String accCashFlowAddPage(Model mode) throws Exception {

		return "hrp/acc/acccashflow/accCashFlowAdd";

	}
	/**
	*现金流量标注<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acccashflow/addAccCashFlow", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accCashFlowJson = accCashFlowService.addAccCashFlow(mapVo);

		return JSONObject.parseObject(accCashFlowJson);
		
	}
	/**
	*现金流量标注<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acccashflow/queryAccCashFlow", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accCashFlow = accCashFlowService.queryAccCashFlow(getPage(mapVo));

		return JSONObject.parseObject(accCashFlow);
		
	}
	/**
	*现金流量标注<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acccashflow/deleteAccCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCashFlow(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
			}
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accCashFlowJson = accCashFlowService.deleteBatchAccCashFlow(listVo);
	   return JSONObject.parseObject(accCashFlowJson);
			
	}
	
	/**
	*现金流量标注<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashflow/accCashFlowUpdatePage", method = RequestMethod.GET)
	
	public String accCashFlowUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        AccCashFlow accCashFlow = new AccCashFlow();
		accCashFlow = accCashFlowService.queryAccCashFlowByCode(mapVo);
		mode.addAttribute("cash_id", accCashFlow.getCash_id());
		mode.addAttribute("vouch_detail_id", accCashFlow.getVouch_detail_id());
		mode.addAttribute("group_id", accCashFlow.getGroup_id());
		mode.addAttribute("hos_id", accCashFlow.getHos_id());
		mode.addAttribute("copy_code", accCashFlow.getCopy_code());
		mode.addAttribute("cash_item_id", accCashFlow.getCash_item_id());
		mode.addAttribute("cash_money", accCashFlow.getCash_money());
		mode.addAttribute("create_user", accCashFlow.getCreate_user());
		mode.addAttribute("create_date", accCashFlow.getCreate_date());
		return "hrp/acc/acccashflow/accCashFlowUpdate";
	}
	/**
	*现金流量标注<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acccashflow/updateAccCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accCashFlowJson = accCashFlowService.updateAccCashFlow(mapVo);
		
		return JSONObject.parseObject(accCashFlowJson);
	}
	/**
	*现金流量标注<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashflow/importAccCashFlow", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCashFlow(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCashFlowJson = accCashFlowService.importAccCashFlow(mapVo);
		
		return JSONObject.parseObject(accCashFlowJson);
	}

}

