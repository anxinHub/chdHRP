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
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.serviceImpl.AccProjAttrServiceImpl;

/**
* @Title. @Description.
* 项目字典属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccProjAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(AccProjAttrController.class);
	
	
	@Resource(name = "accProjAttrService")
	private final AccProjAttrServiceImpl accProjAttrService = null;
   
    
	/**
	*项目字典属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accprojattr/accProjAttrMainPage", method = RequestMethod.GET)
	public String accProjAttrMainPage(Model mode) throws Exception {

		return "hrp/acc/accprojattr/accProjAttrMain";

	}
	/**
	*项目字典属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accprojattr/accProjAttrAddPage", method = RequestMethod.GET)
	public String accProjAttrAddPage(Model mode) throws Exception {

		return "hrp/acc/accprojattr/accProjAttrAdd";

	}
	/**
	*项目字典属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accprojattr/addAccProjAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccProjAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("app_date", DateUtil.stringToDate(mapVo.get("app_date").toString(),"yyyy-MM-dd"));
		
		String accProjAttrJson = accProjAttrService.addAccProjAttr(mapVo);

		return JSONObject.parseObject(accProjAttrJson);
		
	}
	/**
	*项目字典属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accprojattr/queryAccProjAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccProjAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accProjAttr = accProjAttrService.queryAccProjAttr(getPage(mapVo));

		return JSONObject.parseObject(accProjAttr);
		
	}
	/**
	*项目字典属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accprojattr/deleteAccProjAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccProjAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accProjAttrJson = accProjAttrService.deleteBatchAccProjAttr(listVo);
	   return JSONObject.parseObject(accProjAttrJson);
			
	}
	
	/**
	*项目字典属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accprojattr/accProjAttrUpdatePage", method = RequestMethod.GET)
	
	public String accProjAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccProjAttr accProjAttr = new AccProjAttr();
		accProjAttr = accProjAttrService.queryAccProjAttrByCode(mapVo);
		mode.addAttribute("proj_id", accProjAttr.getProj_id());
		mode.addAttribute("group_id", accProjAttr.getGroup_id());
		mode.addAttribute("hos_id", accProjAttr.getHos_id());
		mode.addAttribute("level_code", accProjAttr.getLevel_code());
		mode.addAttribute("use_code", accProjAttr.getUse_code());
		mode.addAttribute("con_emp_id", accProjAttr.getCon_emp_id());
		mode.addAttribute("con_phone", accProjAttr.getCon_phone());
		mode.addAttribute("acc_emp_id", accProjAttr.getAcc_emp_id());
		mode.addAttribute("acc_phone", accProjAttr.getAcc_phone());
		mode.addAttribute("app_emp_id", accProjAttr.getApp_emp_id());
		mode.addAttribute("app_emp_id", accProjAttr.getApp_emp_id());
		mode.addAttribute("dept_id", accProjAttr.getDept_id());
		mode.addAttribute("dept_no", accProjAttr.getDept_no());
		mode.addAttribute("dept_code", accProjAttr.getDept_code());
		mode.addAttribute("dept_name", accProjAttr.getDept_name());
		mode.addAttribute("app_date", accProjAttr.getApp_date());
		mode.addAttribute("app_phone", accProjAttr.getApp_phone());
		mode.addAttribute("email", accProjAttr.getEmail());
		mode.addAttribute("note", accProjAttr.getNote());
		return "hrp/acc/accprojattr/accProjAttrUpdate";
	}
	/**
	*项目字典属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accprojattr/updateAccProjAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccProjAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accProjAttrJson = accProjAttrService.updateAccProjAttr(mapVo);
		
		return JSONObject.parseObject(accProjAttrJson);
	}
	/**
	*项目字典属性表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accprojattr/importAccProjAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccProjAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accProjAttrJson = accProjAttrService.importAccProjAttr(mapVo);
		
		return JSONObject.parseObject(accProjAttrJson);
	}

}

