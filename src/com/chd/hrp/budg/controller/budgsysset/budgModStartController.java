/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.budg.controller.budgsysset;
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
import com.chd.base.SessionManager;
import com.chd.hrp.budg.serviceImpl.budgsysset.BudgModStartServiceImpl;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.serviceImpl.ModServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class budgModStartController extends BaseController{
	private static Logger logger = Logger.getLogger(budgModStartController.class);
	
	
	@Resource(name = "modService")
	private final ModServiceImpl modService = null;
   
	@Resource(name = "budgModStartService")
	private final BudgModStartServiceImpl budgModStartService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/budgModStartMainPage", method = RequestMethod.GET)
	public String modMainPage(Model mode) throws Exception {

		return "hrp/budg/budgsysset/budgmodstart/budgModStartMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/budgModStartAddPage", method = RequestMethod.GET)
	public String modAddPage(Model mode) throws Exception {

		return "hrp/budg/budgsysset/budgmodstart/budgModStartAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/addBudgModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addBudgModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("create_user", SessionManager.getUserCode());
        mapVo.put("create_date", new Date());
		String modJson = budgModStartService.addModStart(mapVo);

		return JSONObject.parseObject(modJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/queryBudgModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryBudgModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("mod_code", "02%");
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		String mod = modService.queryMod(getPage(mapVo));

		return JSONObject.parseObject(mod);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/deleteBudgModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgModStart(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String modJson = modService.deleteBatchMod(listVo);
	   return JSONObject.parseObject(modJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/budgModStartUpdatePage", method = RequestMethod.GET)
	
	public String modUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Mod mod = new Mod();
		mod = modService.queryModByCode(mapVo);
		mode.addAttribute("mod_code", mod.getMod_code());
		mode.addAttribute("mod_name", mod.getMod_name());
		mode.addAttribute("parent_code", mod.getParent_code());
		mode.addAttribute("is_sys", mod.getIs_sys());
		
		return "hrp/budg/budgsysset/budgmodstart/budgModStartUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/updateBudgModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modJson = modService.updateMod(mapVo);
		
		return JSONObject.parseObject(modJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/budg/budgsysset/budgmodstart/importBudgModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importBudgModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modJson = modService.importMod(mapVo);
		
		return JSONObject.parseObject(modJson);
	}

}

