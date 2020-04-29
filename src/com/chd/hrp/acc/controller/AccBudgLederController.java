/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.util.*;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.serviceImpl.verification.AccBudgLederServiceImpl;

/**
* @Title. @Description.
* 坏账提取表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBudgLederController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBudgLederController.class);
	
	
	@Resource(name = "accBudgLederService")
	private final AccBudgLederServiceImpl accBudgLederService = null;
   
   
	/**
	*坏账提取表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accbudgleder/accBudgLederMainPage", method = RequestMethod.GET)
	public String accBudgLederMainPage(Model mode) throws Exception {

		return "hrp/acc/accbudgleder/accBudgLederMain";

	}
	/**
	*坏账提取表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbudgleder/accBudgLederAddPage", method = RequestMethod.GET)
	public String accBudgLederAddPage(Model mode) throws Exception {

		return "hrp/acc/accbudgleder/accBudgLederAdd";

	}
	/**
	*坏账提取表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accbudgleder/addAccBudgLeder", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccBudgLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accBudgLederJson = accBudgLederService.addAccBudgLeder(mapVo);

		return JSONObject.parseObject(accBudgLederJson);
		
	}
	/**
	*坏账提取表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accbudgleder/queryAccBudgLeder", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccBudgLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String accBudgLeder = accBudgLederService.queryAccBudgLeder(getPage(mapVo));

		return JSONObject.parseObject(accBudgLeder);
		
	}
	/**
	*坏账提取表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accbudgleder/deleteAccBudgLeder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBudgLeder(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String accBudgLederJson = accBudgLederService.deleteBatchAccBudgLeder(listVo);
	   return JSONObject.parseObject(accBudgLederJson);
			
	}
	
	/**
	*坏账提取表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accbudgleder/accBudgLederUpdatePage", method = RequestMethod.GET)
	
	public String accBudgLederUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        AccBudgLeder accBudgLeder = new AccBudgLeder();
		accBudgLeder = accBudgLederService.queryAccBudgLederByCode(mapVo);
		mode.addAttribute("range_id", accBudgLeder.getRange_id());
		mode.addAttribute("group_id", accBudgLeder.getGroup_id());
		mode.addAttribute("hos_id", accBudgLeder.getHos_id());
		mode.addAttribute("copy_code", accBudgLeder.getCopy_code());
		mode.addAttribute("acc_year", accBudgLeder.getAcc_year());
		mode.addAttribute("acc_month", accBudgLeder.getAcc_month());
		mode.addAttribute("subj_code_hz", accBudgLeder.getSubj_code_hz());
		mode.addAttribute("subj_code_gl", accBudgLeder.getSubj_code_gl());
		mode.addAttribute("range_pro", accBudgLeder.getRange_pro());
		mode.addAttribute("range_type", accBudgLeder.getRange_type());
		return "hrp/acc/accbudgleder/accBudgLederUpdate";
	}
	/**
	*坏账提取表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accbudgleder/updateAccBudgLeder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBudgLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accBudgLederJson = accBudgLederService.updateAccBudgLeder(mapVo);
		
		return JSONObject.parseObject(accBudgLederJson);
	}
	/**
	*坏账提取表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accbudgleder/importAccBudgLeder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccBudgLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accBudgLederJson = accBudgLederService.importAccBudgLeder(mapVo);
		
		return JSONObject.parseObject(accBudgLederJson);
	}

}

