package com.chd.hrp.acc.controller.paper;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.paper.AccPaperExchangeService;
import com.chd.hrp.acc.service.paper.AccPaperTypeService;

@Controller
public class AccPaperExchangeController extends BaseController{

	private static Logger logger = Logger.getLogger(AccPaperExchangeController.class);
	
	@Resource(name = "accPaperExchangeService")
	private final AccPaperExchangeService accPaperExchangeService = null;
	
	/**
	 * 汇率<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperexchange/accPaperExchangeMainPage", method = RequestMethod.GET)
	public String accPaperExchangeMainPage(Model mode) throws Exception {
		return "hrp/acc/accpaper/accpaperexchange/accPaperExchangeMain";
	}
	
	/**
	 * 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperexchange/queryAccExchange", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String,Object> queryAccExchange(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperExchangeJson = accPaperExchangeService.queryAccExchange(mapVo);
		
		return JSONObject.parseObject(accPaperExchangeJson);
	}
	
	/**
	 * 新增页面加载  修改页面加载(共用)
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperexchange/accPaperExchangeAddPage", method = RequestMethod.GET)
	public String accPaperExchangeAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("RATE_CODE",mapVo.get("RATE_CODE"));
		mode.addAttribute("RATE_NAME",mapVo.get("RATE_NAME"));
		mode.addAttribute("RATE",mapVo.get("RATE"));
		mode.addAttribute("IS_STOP",mapVo.get("IS_STOP"));
		mode.addAttribute("NOTE",mapVo.get("NOTE"));
		mode.addAttribute("code",mapVo.get("code"));
		
		return "hrp/acc/accpaper/accpaperexchange/accPaperExchangeAdd";

	}
	
	/**
	 * 汇率修改方法
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperexchange/updatePaperExchange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePaperExchange(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String updatePaperExchangeJson = accPaperExchangeService.updatePaperExchange(mapVo);
		
		return JSONObject.parseObject(updatePaperExchangeJson);

	}
	
	/**
	 * 汇率新增方法
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperexchange/addPaperExchange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addPaperExchange(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String addPaperExchangeJson = accPaperExchangeService.addPaperExchange(mapVo);
		
		return JSONObject.parseObject(addPaperExchangeJson);

	}
	
	/**
	 * 汇率删除方法
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpaperexchange/deletePaperExchange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deletePaperExchange(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String addPaperExchangeJson = accPaperExchangeService.deletePaperExchange(mapVo);
		
		return JSONObject.parseObject(addPaperExchangeJson);

	}
	
}
