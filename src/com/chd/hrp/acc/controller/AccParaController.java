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
import com.chd.base.exception.SysException;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;

/**
* @Title. @Description.
* 系统参数
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccParaController extends BaseController{
	private static Logger logger = Logger.getLogger(AccParaController.class);
	
	
	@Resource(name = "accParaService")
	private final AccParaServiceImpl accParaService = null;
	
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
   
    
	/**
	*所有系统模块参数<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accpara/accParaMainPage", method = RequestMethod.GET)
	public String accParaMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
//		mode.addAttribute("query_perm_code", mapVo.get("query"));
//		mode.addAttribute("update_perm_code", mapVo.get("update"));
		return "hrp/acc/accpara/accParaMain";

	}
	
	/**
	*系统参数<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accpara/queryAccPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modCode=SessionManager.getModCode();
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("user_id", SessionManager.getUserId());
        mapVo.put("mod_code", modCode);
        mapVo.put("is_stop", 0);
        
        //判断操作权限
//        if(modCode.equals("00")){
//			modCode="98";
//			mapVo.put("mod_code", modCode);
//		}else if(superVouchService.queryVouchFlowByPerm(mapVo)==0){
//			return JSONObject.parseObject("{\"error\":\"没有该操作权限！\",\"state\":\"false\"}");
//		}
		String accPara = accParaService.queryAccPara(mapVo);

		return JSONObject.parseObject(accPara);
		
	}
	
	/**
	*系统参数<BR>
	*批量保存
	*/
	@RequestMapping(value = "/hrp/acc/accpara/updateBatchAccPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchAccPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modCode=SessionManager.getModCode();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("mod_code", modCode);
		
        //判断操作权限
//		if(modCode.equals("00")){
//			modCode="98";
//			mapVo.put("mod_code", modCode);
//		}else if(superVouchService.queryVouchFlowByPerm(mapVo)==0){
//			return JSONObject.parseObject("{\"error\":\"没有该操作权限！\",\"state\":\"false\"}");
//		}
		
		try{
			String accParaJson = accParaService.updateBatchAccPara(mapVo);
			return JSONObject.parseObject(accParaJson);
		}catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}

}

