package com.chd.hrp.pac.controller.fkxy.logistics;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.fkxy.logistics.PactLogisticsProtocolService;

@Controller
@RequestMapping(value = "/hrp/pac/fkxy/pactlogistics")
public class PactLogisticsProtocolController {

	@Resource(name = "pactLogisticsProtocolService")
	private PactLogisticsProtocolService pactLogisticsProtocolService;

	/**
	 * 协议汇总
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pactProtocolSummaryMainPage")
	public String toPactProtocolSummaryMainPage() {
		return "hrp/pac/fkxy/logistics/pactProtocolSummaryMain";
	}

	/**
	 * 协议明细
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pactProtocolDetailMainPage")
	public String toPactProtocolDetailMainPage() {
		return "hrp/pac/fkxy/logistics/pactProtocolDetailMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactProtocalSummaryFKXY")
	public Map<String, Object> queryPactProtocalSummaryFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("hos_id", SessionManager.getHosId());
		String result = pactLogisticsProtocolService.queryPactProtocalSummaryFKXY(mapVo);
		return JSONObject.parseObject(result);
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactProtocolDetailFKXY")
	public Map<String, Object> queryPactPrptocolDetailFKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("hos_id", SessionManager.getHosId());
		String result = pactLogisticsProtocolService.queryPactProtocolDetailFKXY(mapVo);
		return JSONObject.parseObject(result);
	}
	
	/**
	 * 链接物流材料入库
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toMatMain", method = RequestMethod.GET)
	public String toMatMain(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04011", MyConfig.getSysPara("04011"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04044", MyConfig.getSysPara("04044"));
		mode.addAttribute("p04045", MyConfig.getSysPara("04045"));
		mode.addAttribute("p04047", MyConfig.getSysPara("04047"));
		
		mode.addAttribute("protocol_code", MyConfig.getSysPara("protocol_code"));
		mode.addAttribute("inv_id", MyConfig.getSysPara("inv_id"));
		mode.addAttribute("begin_in_date", MyConfig.getSysPara("begin_in_date"));
		mode.addAttribute("end_in_date", MyConfig.getSysPara("end_in_date"));
		return "hrp/mat/storage/in/main";
	}
	
	/**
	 * 链接物流预付款单
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/toPrePay", method = RequestMethod.GET)
	public String toPrePay(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		
		mode.addAttribute("protocol_code", MyConfig.getSysPara("protocol_code"));
		mode.addAttribute("bill_detail_id", MyConfig.getSysPara("bill_detail_id"));
		
		return "hrp/mat/matprepay/matprepaymain/matPrePayMainMain";
	}
	
	/*
	 * 链接物流付款单
	 */
	@ResponseBody
	@RequestMapping(value = "/toPay", method = RequestMethod.GET)
	public String toPay(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		
		mode.addAttribute("protocol_code", MyConfig.getSysPara("protocol_code"));
		mode.addAttribute("bill_detail_id", MyConfig.getSysPara("bill_detail_id"));
		return "hrp/mat/payment/pay/matPayList";
	}
	
}
