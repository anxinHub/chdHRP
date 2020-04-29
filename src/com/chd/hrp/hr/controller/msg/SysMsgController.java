package com.chd.hrp.hr.controller.msg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.msg.SysMsg;
import com.chd.hrp.hr.service.msg.SysMsgService;

@Controller
@RequestMapping(value = "/hrp/hr/msg")
public class SysMsgController extends BaseController{

	@Autowired
	private SysMsgService sysMsgService;
	
	@RequestMapping(value = "/query/list",method=RequestMethod.GET)
	@ResponseBody
	public String getMsg(ModelMap mapVo){
		 
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("user_id", SessionManager.getUserId());
		List<SysMsg> list=sysMsgService.getUserPushMsg(mapVo);
		return JSONObject.toJSONString(list);
	}
}
