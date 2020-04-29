package com.chd.hrp.acc.controller.paper;

import java.text.SimpleDateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccEInvoice;
import com.chd.hrp.acc.service.paper.AccEInvoiceService;
import com.chd.hrp.acc.service.paper.AccPaperIncomeService;

@Controller
public class AccEInvoiceController extends BaseController{

	private static Logger logger = Logger.getLogger(AccEInvoiceController.class);
	
	@Resource(name = "accEInvoiceService")
	private final AccEInvoiceService accEInvoiceService = null;
	
	/**
	 * 电子发票<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/accEInvoiceMainPage", method = RequestMethod.GET)
	public String accPaperBillMainPage(Model mode) throws Exception {
		return "hrp/acc/accpaper/acceinvoice/accEInvoiceMain";
	}
	
	/**
	 * 电子发票查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/queryAccEInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAccPaperBill(@RequestParam Map<String,Object> mapVo,Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return JSONObject.parseObject(accEInvoiceService.queryAccEInvoice(mapVo));
	}
	
	/**
	 * 电子发票<BR>
	 * 添加页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/accEInvoiceAddPage", method = RequestMethod.GET)
	public String accPaperBillAddPage(Model mode) throws Exception {
		
		return "hrp/acc/accpaper/acceinvoice/accEInvoiceAdd";
	}
	
	/**
	 * 电子发票添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/addAccEInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addPaperBill(@RequestParam Map<String,Object> mapVo,Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			return JSONObject.parseObject(accEInvoiceService.addAccEInvoice(mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 电子发票修改
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/updateAccEInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePaperbill(@RequestParam Map<String,Object> mapVo,Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try{
			return JSONObject.parseObject(accEInvoiceService.updateAccEInvoice(mapVo));
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	/**
	 * 电子发票修改-数据回显
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/accEInvoiceUpdatePage", method = RequestMethod.GET)
	public String accPaperBillUpdatePage(@RequestParam Map<String,Object> mapVo,Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//查询回显数据
		AccEInvoice accEInvoice = accEInvoiceService.queryAccEInvoiceByCode(mapVo);
		
		mode.addAttribute("ei_id", accEInvoice.getEi_id());
		mode.addAttribute("ei_code", accEInvoice.getEi_code());
		mode.addAttribute("ei_date", formatter.format(accEInvoice.getEi_date()));
		mode.addAttribute("check_code", accEInvoice.getCheck_code());
		mode.addAttribute("ei_money", accEInvoice.getEi_money());
		mode.addAttribute("reimburse_man", accEInvoice.getReimburse_man());
		mode.addAttribute("reimburse_date", formatter.format(accEInvoice.getReimburse_date()));
		
		return "hrp/acc/accpaper/acceinvoice/accEInvoiceUpdate";
	}
	
	/**
	 * <BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/acceinvoice/deleteBatchAccEInvoice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccPaperMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("ei_id", ids[0]);
			
			mapVo.put("ei_code", ids[1]);
			
			mapVo.put("check_code", ids[2]);
			
			listVo.add(mapVo);
		}

		String deleteBatchAccEInvoiceJson = accEInvoiceService.deleteBatchAccEInvoice(listVo);

		return JSONObject.parseObject(deleteBatchAccEInvoiceJson);

	}
}
