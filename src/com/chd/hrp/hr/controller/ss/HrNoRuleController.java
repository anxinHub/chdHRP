package com.chd.hrp.hr.controller.ss;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.ss.HrNoRuleService;

/**
 * @Title: @Description
 * 
 * @author LFH
 * @version 1.0
 *
 */

@Controller    //必须要继承 baseController
public class HrNoRuleController extends BaseController{
	
	//先要建立  映射 menu 菜单中配置的地址。hrp/hr/ss/hrNoRule/hrNoRuleMain + Page.do
	//维护  主页面跳转,跳转到菜单页面
	@RequestMapping(value = "/hrp/hr/ss/hrNoRule/hrNoRuleMainPage", method = RequestMethod.GET)
	public String hrNoRuleMainPage(Model mode) throws Exception {
		return "hrp/hr/ss/hrNoRule/hrNoRuleMain";
	}
	
	// !!!!!弹窗子页面的 请求  维护跳转
	@RequestMapping(value = "/hrp/hr/ss/hrNoRule/hrNoRuleAdd", method = RequestMethod.GET)
	public String hrNoRuleAdd(Model mode) throws Exception {
		return "hrp/hr/ss/hrNoRule/hrNoRuleAdd";
	}
	
	//@SuppressWarnings("unused")
	//private static Logger logger = Logger.getLogger(HrNoRuleController.class);
	
	// 注解方式   初始化 Service 层对象 （接口）
	@Resource(name="hrNoRuleService")
	private final HrNoRuleService hrNoRuleService=null;
		
	//查询  	//ajax请求必须用 @ResponseBody注解 响应体
	@RequestMapping(value = "/hrp/hr/ss/hrNoRule/queryHrNoRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrNoRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception { 
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id",SessionManager.getGroupId()); //从系统session中获取
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
/*		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code",SessionManager.getCopyCode());
		}*/
		String rules= hrNoRuleService.queryHrNoRule(mapVo); //调用 server 层（业务层）
		return JSONObject.parseObject(rules);
	}
	
/*	//修改 保存
	@RequestMapping(value= "/hrp/hr/ss/hrNoRule/updateHrNoRule", method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateHrNoRule(@RequestParam Map<String, Object> mapVo)throws Exception{
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id",SessionManager.getGroupId()); //从系统session中获取
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code",SessionManager.getCopyCode());
		}
		String rules= hrNoRuleService.queryHrNoRule(mapVo); //调用 server 层（业务层）
		
		return JSONObject.parseObject(rules);
	}*/
	
	//保存
	@RequestMapping(value ="/hrp/hr/ss/hrNoRule/savaHrNoRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHrNoRule(@RequestParam(value="formPara") String formParm, Model model)throws Exception{
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		for(String id: formParm.split(",")){   // //用分割？   将多个 json 字符串   , 分割成一条条记录。然后遍历
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//session中获取不变的
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
//			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//传过来的表中的值
			mapVo.put("table_code", id.split("@")[0]);  // id.split("@")[0]   //貌似不需要for 来变换id，在分割
			mapVo.put("table_name", id.split("@")[1]);
			mapVo.put("prefixe", id.split("@")[2]);
			mapVo.put("is_year", id.split("@")[3]);
			mapVo.put("is_month", id.split("@")[4]);
			mapVo.put("is_day", id.split("@")[5]);
			mapVo.put("seq_no", id.split("@")[6]);
			
			listVo.add(mapVo);
		}
		String result = hrNoRuleService.saveHrNoRule(listVo);
		return JSONObject.parseObject(result); // 转 json 对象
	}
	
	// 删除
	@RequestMapping (value="/hrp/hr/ss/hrNoRule/deleteHrNoRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHrNoRule(@RequestParam(value="paramVo") String paramVo, Model model)throws Exception{
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		for(String id: paramVo.split(",")){
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//session中获取不变的
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
//			mapVo.put("copy_code", SessionManager.getCopyCode());
			//传过来的表中的值
			mapVo.put("table_code",id);
			listVo.add(mapVo);
		}
		String result = hrNoRuleService.deleteHrNoRule(listVo);
		return JSONObject.parseObject(result);
	}
	
	// 新增
	@RequestMapping(value="/hrp/hr/ss/hrNoRule/addHrNoRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrNoRule(@RequestParam(value="paramVo") String paramVo) throws Exception{
		//List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		//session中获取不变的
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
//		mapVo.put("copy_code", SessionManager.getCopyCode());
		// 遍历输入的 内容
		for(String id: paramVo.split(",")){
			//传过来的表中的值
			mapVo.put(id.split("=")[0],id.split("=")[1]);
		}
		//listVo.add(mapVo);
		String result=hrNoRuleService.addHrNoRule(mapVo);
		return JSONObject.parseObject(result);
	}

}
