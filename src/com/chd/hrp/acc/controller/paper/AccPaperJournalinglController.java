package com.chd.hrp.acc.controller.paper;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.acc.service.paper.AccPaperJournalinglService;

@Controller
public class AccPaperJournalinglController extends BaseController {

	private static Logger logger = Logger.getLogger(AccPaperJournalinglController.class);

	@Resource(name = "accPaperJournalinglService")
	private final AccPaperJournalinglService accPaperJournalinglService = null;
	

	/**
	 * 跳转票据库存汇总表<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperStockCollectDetailPage", method = RequestMethod.GET)
	public String accPaperStockCollectDetailQueryPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		
		mode.addAttribute("type_code", mapVo.get("type_code").toString());
		
		mode.addAttribute("end_date",mapVo.get("end_date"));
		
		mode.addAttribute("state", Integer.parseInt(mapVo.get("state").toString()));
		
		return "hrp/acc/accpaper/accpapernumerical/accPaperStockCollectDetail";

	}
	
	/**
	 * 票据库存汇总表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperStockCollectMainPage", method = RequestMethod.GET)
	public String accPaperStockCollectMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperStockCollectMain";

	}
	
	/**
	 * 票据库存汇总表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperStockCollectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperStockCollectDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		//System.out.println("mapVo = " + mapVo);

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockCollectJson = accPaperJournalinglService.queryAccPaperStockCollectDetail((getPage(mapVo)));

		return JSONObject.parseObject(accPaperStockCollectJson);

	}
	
	/**
	 * 票据库存汇总表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperStockCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperStockCollect(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockCollectJson = accPaperJournalinglService.queryAccPaperStockCollect(getPage(mapVo));

		return JSONObject.parseObject(accPaperStockCollectJson);

	}
	/**
	 * 票据库存明细表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperStockDetailMainPage", method = RequestMethod.GET)
	public String accPaperStockDetailMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperStockDetailMain";

	}
	
	/**
	 * 票据库存明细表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperStockDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperStockDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockDetailJson = accPaperJournalinglService.queryAccPaperStockDetail(getPage(mapVo));

		return JSONObject.parseObject(accPaperStockDetailJson);

	}
	
	/**
	 * 票据使用情况表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperUseSituationMainPage", method = RequestMethod.GET)
	public String accPaperUseSituationMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperUseSituationMain";

	}
	
	/**
	 *单张管理票据统计表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperSolaCountMainPage", method = RequestMethod.GET)
	public String accPaperSolaCountMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperSolaCountMain";

	}
	
	
	/**
	 * 单张管理票据统计表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperSolaCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperSolaCount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockCollectJson = accPaperJournalinglService.queryAccPaperSolaCount(getPage(mapVo));

		return JSONObject.parseObject(accPaperStockCollectJson);

	}
	
	/**
	 * 批量管理票据统计表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperBatchCountMainPage", method = RequestMethod.GET)
	public String accPaperBatchCountMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperBatchCountMain";

	}
	
	/**
	 * 票据汇总统计表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperCollectCountMainPage", method = RequestMethod.GET)
	public String accPaperCollectCountMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperCollectCountMain";

	}
	
	
	/**
	 * 票据库存汇总表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperCollectCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperCollectCount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockCollectJson = accPaperJournalinglService.queryAccPaperCollectCount(getPage(mapVo));

		return JSONObject.parseObject(accPaperStockCollectJson);

	}

	
	
	/**
	 * 票据出入库汇总表<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperInOutCollectMainPage", method = RequestMethod.GET)
	public String accPaperInOutCollectMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperInOutCollectMain";

	}
	
	/**
	 * 票据出入库汇总表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperInOutCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperInOutCollect(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockCollectJson = accPaperJournalinglService.queryAccPaperInOutCollect(getPage(mapVo));

		return JSONObject.parseObject(accPaperStockCollectJson);

	}
	
	/**
	 * 往来款收据核销明细账<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/accPaperIntercourseFundsDetailMainPage", method = RequestMethod.GET)
	public String accPaperIntercourseFundsDetailMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapernumerical/accPaperIntercourseFundsDetailMain";

	}
	
	/**
	 * 往来款收据核销明细账<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapernumerical/queryAccPaperIntercourseFundsDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperIntercourseFundsDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPaperStockCollectJson = accPaperJournalinglService.queryAccPaperIntercourseFundsDetail(getPage(mapVo));

		return JSONObject.parseObject(accPaperStockCollectJson);

	}
}