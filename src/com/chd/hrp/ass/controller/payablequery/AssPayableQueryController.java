package com.chd.hrp.ass.controller.payablequery;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.payablequery.AssPayableQueryService;
/**
 * 
 * @author XWL
 *
 */
@Controller
public class AssPayableQueryController extends BaseController{
	private static Logger logger = Logger.getLogger(AssPayableQueryController.class);
	
	@Resource(name = "assPayableQueryService")
	private final AssPayableQueryService assPayableQueryService = null;
	
	/**
	 * 货到票未到跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspayablequery/assGoodsArrivedInvoiceNotPage", method = RequestMethod.GET)
	public String assGoodsArrivedInvoiceNotPage(Model mode) throws Exception {
		return "hrp/ass/asspayablequery/assGoodsArrivedInvoiceNot";
	}
	
	/**
	 * 票到款未付跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspayablequery/assInvoiceArrivedMoneyNotPage", method = RequestMethod.GET)
	public String assInvoiceArrivedMoneyNotPage(Model mode) throws Exception {
		return "hrp/ass/asspayablequery/assInvoiceArrivedMoneyNot";
	}
	
	/**
	 * 应付款总账跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspayablequery/assPayableLedgerPage", method = RequestMethod.GET)
	public String assPayableLedgerPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asspayablequery/assPayableLedger";
	}
	
	/**
	 * 应付款明细账跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspayablequery/assPayableDetailLedgerPage", method = RequestMethod.GET)
	public String assPayableDetailLedgerPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asspayablequery/assPayableDetailLedger";
	}
	
	/**
	 * 资产付款查询跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspayablequery/assPaySearchPage", method = RequestMethod.GET)
	public String assPaySearchPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asspayablequery/assPaySearch";
	}
	
	
	@RequestMapping(value = "/hrp/ass/asspayablequery/queryGoodsArrivedInvoiceNot", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = assPayableQueryService.queryGoodsArrivedInvoiceNot(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/ass/asspayablequery/queryPaySearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPaySearch(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = assPayableQueryService.queryPaySearch(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/ass/asspayablequery/queryPayCardSearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPayCardSearch(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = assPayableQueryService.queryPayCardSearch(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/ass/asspayablequery/queryPayVenSearch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPayVenSearch(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String result = assPayableQueryService.queryPayVenSearch(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
}
