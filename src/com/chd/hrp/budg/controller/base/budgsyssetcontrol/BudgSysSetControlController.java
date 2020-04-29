package com.chd.hrp.budg.controller.base.budgsyssetcontrol;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgCDet;
import com.chd.hrp.budg.entity.BudgSysSetControlSet;
import com.chd.hrp.budg.service.base.budgsyssetcontrol.BudgSysSetControlSetService;
import com.chd.hrp.hr.entity.record.HosEmpKind;
/**
 * 
 * @Description:
 * 预算控制方案
 * @Table:
 * BUDG_CHARGE_STAN_DEPT_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgSysSetControlController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgSysSetControlController.class);
	
	//引入Service服务
	@Resource(name = "budgSysSetControlSetService")
	private final BudgSysSetControlSetService budgSysSetControlSetService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/budgSysSetControlMainPage", method = RequestMethod.GET)
	public String BudgSysSetControlSetMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsyssetcontrolset/budgSysSetControlMain";

	}
//
	/**
	 * @Description 
	 * 搜索跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsyssetsearchPage", method = RequestMethod.GET)
	public String BudgsyssetsearchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		return "hrp/budg/base/budgsyssetcontrolset/budgSysSetSearchPage";

	}
	/**
	 * 批量添加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgSetDetaddBatch", method = RequestMethod.GET)
	public String budgSetDetaddBatch(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		mode.addAttribute("link_code", mapVo.get("link_code"));
		mode.addAttribute("tab_code", mapVo.get("tab_code"));
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		return "hrp/budg/base/budgsyssetcontrolset/budgSetDetaddBatch";
	}
	//
	/**
	 * 批量添加跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgSetBatch", method = RequestMethod.GET)
	public String budgSetBatch(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("plan_code", mapVo.get("plan_code"));
		mode.addAttribute("mod_code", mapVo.get("mod_code"));
		mode.addAttribute("link_code", mapVo.get("link_code"));
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		return "hrp/budg/base/budgsyssetcontrolset/budgSetBatch";
	}
	//
	/**
	 * 复制跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgsysset/budgSetDetaddCopy", method = RequestMethod.GET)
	public String budgSetDetaddCopy(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			if(mapVo.get("budg_year") == null){
	    	mapVo.put("budg_year", SessionManager.getAcctYear());
			}
			
			Map<String, Object> item_Tab_Name= budgSysSetControlSetService.queryItemTabName(mapVo);
			mode.addAttribute("plan_code", mapVo.get("plan_code"));
			mode.addAttribute("plan_name", mapVo.get("plan_name"));
			mode.addAttribute("mod_code", mapVo.get("mod_code"));
			mode.addAttribute("link_code", mapVo.get("link_code"));
			mode.addAttribute("budg_year", mapVo.get("budg_year"));
			mode.addAttribute("item_tab_name", item_Tab_Name.get("ITEM_TAB_NAME"));
			mode.addAttribute("item_tab_code", item_Tab_Name.get("ITEM_TAB_CODE"));
		return "hrp/budg/base/budgsyssetcontrolset/budgSetDetaddCopy";
	}
	/**
	 * @Description 
	 * 添加数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/budgsysset/budgsysset/addBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgSysSetControlSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
    	mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
       
		String BudgSysSetControlSetJson = budgSysSetControlSetService.addBudgSysSetControl(mapVo);

		return JSONObject.parseObject(BudgSysSetControlSetJson);
		
	}
	
	/**
	 * @Description 
	 * 复制数据预算控制方案明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "hrp/budg/budgsysset/budgsysset/updateSetBudgCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSetBudgCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
    	mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
       
		String BudgSysSetControlSetJson = budgSysSetControlSetService.updateSetBudgCopy(mapVo);

		return JSONObject.parseObject(BudgSysSetControlSetJson);
		
	}
	/**
	 * @Description 
	 * 初始化数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/initBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String BudgSysSetControlSetJson;
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null||mapVo.get("budg_year").toString().equals("")){
			BudgSysSetControlSetJson=	"{\"error\":\"年度不能为空!\",\"state\":\"false\"}";
		}else{
		
       
		 BudgSysSetControlSetJson = budgSysSetControlSetService.initBudgSysSetControl(mapVo);}

		return JSONObject.parseObject(BudgSysSetControlSetJson);
		
	}
	/**
	 * @Description 
	 * 继承数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/extendBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
    	mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
       
		String BudgSysSetControlSetJson = budgSysSetControlSetService.extendBudgSysSetControl(mapVo);

		return JSONObject.parseObject(BudgSysSetControlSetJson);
		
	}
	/**
	 * @Description 
	 * 启用 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/checkBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String BudgSysSetControlSetJson ="";
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
    	mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
       
	
List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("paramVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		 BudgSysSetControlSetJson = budgSysSetControlSetService.checkBudgSysSetControl(detailVo);
		
		}
		return JSONObject.parseObject(BudgSysSetControlSetJson);
		
	}
	/**
	 * @Description 
	 * 停用 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/uncheckBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uncheckBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String BudgSysSetControlSetJson ="";
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
    	mapVo.put("budg_year", SessionManager.getAcctYear());
		}
	
     List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("paramVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		 BudgSysSetControlSetJson = budgSysSetControlSetService.uncheckBudgSysSetControl(detailVo);
		
		}
		return JSONObject.parseObject(BudgSysSetControlSetJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/BudgSysSetControlSetUpdatePage", method = RequestMethod.GET)
	public String BudgSysSetControlSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("budg_year") == null){
        mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		BudgSysSetControlSet BudgSysSetControlSet = new BudgSysSetControlSet();
    
		BudgSysSetControlSet = budgSysSetControlSetService.queryByCodeSysSetControlSet(mapVo);
		
		mode.addAttribute("group_id", BudgSysSetControlSet.getGroup_id());
		mode.addAttribute("hos_id", BudgSysSetControlSet.getHos_id());
		mode.addAttribute("copy_code", BudgSysSetControlSet.getCopy_code());
		mode.addAttribute("budg_year", BudgSysSetControlSet.getBudg_year());
		mode.addAttribute("plan_code", BudgSysSetControlSet.getPlan_code());
		mode.addAttribute("plan_name", BudgSysSetControlSet.getPlan_name());
		mode.addAttribute("mod_code", BudgSysSetControlSet.getMod_code());
		mode.addAttribute("mod_name", BudgSysSetControlSet.getMod_name());
		mode.addAttribute("link_code", BudgSysSetControlSet.getLink_code());
		mode.addAttribute("link_name", BudgSysSetControlSet.getLink_name());
		mode.addAttribute("tab_code", BudgSysSetControlSet.getTab_code());
		mode.addAttribute("cont_m", BudgSysSetControlSet.getCont_m());
		mode.addAttribute("cont_l", BudgSysSetControlSet.getCont_l());
		mode.addAttribute("cont_p", BudgSysSetControlSet.getCont_p());
		mode.addAttribute("cont_w", BudgSysSetControlSet.getCont_w());
		mode.addAttribute("use_nature", BudgSysSetControlSet.getUse_nature());
		mode.addAttribute("re_link", BudgSysSetControlSet.getRe_link());
		mode.addAttribute("cont_note", BudgSysSetControlSet.getCont_note());
		mode.addAttribute("use_state", BudgSysSetControlSet.getUse_state());
		/*mode.addAttribute("charge_stan_code", BudgSysSetControlSet.getCharge_stan_code());
		mode.addAttribute("dept_id", BudgSysSetControlSet.getDept_id());*/
		
		return "hrp/budg/base/budgsyssetcontrolset/budgSysSetControlSetUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*//*
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/updateBudgSysSetControlSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgSysSetControlSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String BudgSysSetControlSetJson = budgSysSetControlSetService.updateBudgSysSetControlSet(mapVo);
		
		return JSONObject.parseObject(BudgSysSetControlSetJson);
	}
*/
	/**
	 * @Description 
	 * 更新数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/updateBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String BudgSysSetControlSetJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	
		BudgSysSetControlSetJson = budgSysSetControlSetService.updateBudgSysSetControl(mapVo);
		return JSONObject.parseObject(BudgSysSetControlSetJson);
	}
	/**
	 * @Description 
	 * 批量更新数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/updateBatchBudgSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchBudgSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String BudgSysSetControlSetJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		BudgSysSetControlSetJson = budgSysSetControlSetService.updateBatchBudgSetControl(detailVo);
		
		}
		return JSONObject.parseObject(BudgSysSetControlSetJson);
	}
	/**
	 * @Description 
	 * 批量保存数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/updateSetBudgSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSetBudgSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String BudgSysSetControlSetJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
	  
		BudgSysSetControlSetJson = budgSysSetControlSetService.updateSetBudgSetControl(mapVo);
		
		
		return JSONObject.parseObject(BudgSysSetControlSetJson);
	}
	/**
	 * @Description 
	 * 更新数据 预算控制方案明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/updateDetailBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDetailBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String BudgSysSetControlSetJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("paramVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		BudgSysSetControlSetJson = budgSysSetControlSetService.updateDetailBudgSysSetControl(detailVo);
		
		}
		return JSONObject.parseObject(BudgSysSetControlSetJson);
	}
	/**
	 * @Description 
	 * 删除数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/deleteBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgSysSetControl(@RequestParam String paramVo, Model mode) throws Exception {
		
		List<BudgCDet> listVo = JSONArray.parseArray(paramVo, BudgCDet.class);
	if (listVo.size()>0) {
		for (BudgCDet budgCDet : listVo) {
			budgCDet.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
			budgCDet.setHos_id(Long.parseLong(SessionManager.getHosId()));
		}
	}
	    
		String BudgSysSetControlSetJson = budgSysSetControlSetService.deleteBudgSysSetControl(listVo);
			
	  return JSONObject.parseObject(BudgSysSetControlSetJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 预算控制方案
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/queryBudgSysSetControl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgSysSetControl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String BudgSysSetControlSet = budgSysSetControlSetService.queryBudgSysSetControl(getPage(mapVo));

		return JSONObject.parseObject(BudgSysSetControlSet);
		
	}
	/**
	 * @Description 
	 * 查询数据 预算控制方案明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/queryBudgCDet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCDet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String BudgSysSetControlSet = budgSysSetControlSetService.queryBudgCDet(getPage(mapVo));

		return JSONObject.parseObject(BudgSysSetControlSet);
		
	}
    /**
     * 
     * /**
	 * @Description 
	 * 查询数据 预算控制方案明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/queryItemCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryItemCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
		mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		String BudgSysSetControlSet = budgSysSetControlSetService.queryItemCode(getPage(mapVo));

		return JSONObject.parseObject(BudgSysSetControlSet);
		
	}
     
	/**
	 * 预算项：与预算方案关联，取自BUDG_C_PLAN中ITEM_SQL
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
  @RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/queryBudgModSelect", method = RequestMethod.POST)
  @ResponseBody
  public String queryBudgModSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
						
		mapVo.put("hos_id", SessionManager.getHosId());
						
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
							
			mapVo.put("budg_year", SessionManager.getAcctYear());
			
			}
		
		
			String resolve = budgSysSetControlSetService.queryBudgModSelect(mapVo);
						
			 return resolve;

					}
  
  /**
   * queryLinkName
   */
  /**
	 * 环节 取主表中数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
@RequestMapping(value = "/hrp/budg/budgsysset/budgsysset/queryLinkName", method = RequestMethod.POST)
@ResponseBody
public String queryLinkName(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
						
		mapVo.put("hos_id", SessionManager.getHosId());
						
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("budg_year") ==  null||mapVo.get("budg_year").toString().equals("")){
							
			mapVo.put("budg_year", SessionManager.getAcctYear());
			
			}
		
		
			String resolve = budgSysSetControlSetService.queryLinkName(mapVo);
						
			 return resolve;

					}
}

