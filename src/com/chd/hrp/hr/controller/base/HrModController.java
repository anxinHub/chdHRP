/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.hr.controller.base;
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
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.serviceImpl.ModServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class HrModController extends BaseController{
	private static Logger logger = Logger.getLogger(HrModController.class);
	
	
	@Resource(name = "modService")
	private final ModServiceImpl modService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/hr/mod/modMainPage", method = RequestMethod.GET)
	public String modMainPage(Model mode) throws Exception {

		return "hrp/hr/hrmod/hrModMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hr/mod/modAddPage", method = RequestMethod.GET)
	public String modAddPage(Model mode) throws Exception {

		return "hrp/hr/hrmod/hrModAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hr/mod/addMod", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String modJson = modService.addMod(mapVo);

			return JSONObject.parseObject(modJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	// 查询
	@RequestMapping(value = "/hrp/hr/mod/queryMod", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("mod_code", "06%");
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		String mod = modService.queryMod(getPage(mapVo));

		return JSONObject.parseObject(mod);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/hr/mod/deleteMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMod(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("temp", id);// 实际实体类变量
				listVo.add(mapVo);
			}
			String modJson = modService.deleteBatchMod(listVo);
			return JSONObject.parseObject(modJson);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/hr/mod/modUpdatePage", method = RequestMethod.GET)
	
	public String modUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Mod mod = new Mod();
		mod = modService.queryModByCode(mapVo);
		mode.addAttribute("mod_code", mod.getMod_code());
		mode.addAttribute("mod_name", mod.getMod_name());
		mode.addAttribute("parent_code", mod.getParent_code());
		mode.addAttribute("is_sys", mod.getIs_sys());
		
		return "hrp/hr/hrmod/hrModUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/hr/mod/updateMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String modJson = modService.updateMod(mapVo);

			return JSONObject.parseObject(modJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	// 导入
	@RequestMapping(value = "/hrp/hr/mod/importMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modJson = modService.importMod(mapVo);
		
		return JSONObject.parseObject(modJson);
	}

}

