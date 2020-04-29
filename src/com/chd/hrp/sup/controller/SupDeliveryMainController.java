/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sup.entity.SupDeliveryMain;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/** 
 * @Description: 100101 送货单主表
 * @Table: SUP_DELIVERY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com 
 * @Version: 1.0
 */

@Controller
public class SupDeliveryMainController extends BaseController {
 
	private static Logger logger = Logger.getLogger(SupDeliveryMainController.class);

	// 引入Service服务
	@Resource(name = "supDeliveryMainService")
	private final SupDeliveryMainService supDeliveryMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryMainMainPage", method = RequestMethod.GET)
	public String supDeliveryMainMainPage(Model mode) throws Exception {

		mode.addAttribute("sup_id", SessionManager.getSupId());

		mode.addAttribute("sup_no", SessionManager.getSupNo());
		return "hrp/sup/supdeliverymain/supDeliveryMainMain";

	}

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "//hrp/sup/supdeliverymain/orderImpPage", method = RequestMethod.GET)
	public String orderImpPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_text", mapVo.get("sup_text"));
		return "hrp/sup/supdeliverymain/orderImp";

	}
	/**
	 * @Description 明细跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryDetailOrderCountPage", method = RequestMethod.GET)
	public String supDeliveryDetailOrderCountPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("in_id", mapVo.get("in_id"));
		mode.addAttribute("in_no", mapVo.get("in_no"));
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		//mode.addAttribute("delivery_id", mapVo.get("delivery_id"));
		//mode.addAttribute("delivery_no", mapVo.get("delivery_no"));
		return "hrp/mat/storage/in/deliveryDetailUpdate";
		
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryMainAddPage", method = RequestMethod.GET)
	public String supDeliveryMainAddPage(Model mode) throws Exception {

		return "hrp/sup/supdeliverymain/supDeliveryMainAdd";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryAddPage", method = RequestMethod.GET)
	public String supDeliveryAddPage(Model mode) throws Exception {

		mode.addAttribute("sup_id", SessionManager.getSupId());

		mode.addAttribute("sup_no", SessionManager.getSupNo());

		return "hrp/sup/supdeliverymain/add";

	}

	/**
	 * @Description 添加数据 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addSupDeliveryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupDeliveryMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());

		String supDeliveryMainJson = supDeliveryMainService.add(mapVo);

		return JSONObject.parseObject(supDeliveryMainJson);

	}

	/**
	 * @Description 添加数据 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addSupDelivery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupDelivery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("hos_id_in") == null) {
			mapVo.put("hos_id_in", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());
		mapVo.put("in_state", "0");
		mapVo.put("state", "0");

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("acc_month") == null) {
			mapVo.put("acc_month", MyConfig.getCurAccYearMonth().substring(4, 6));
		}
		if (mapVo.get("create_date") != null) {
			mapVo.put("create_date", DateUtil.stringToDate(mapVo.get("create_date").toString(), "yyyy-MM-dd"));
		}

		String supDeliveryMainJson = supDeliveryMainService.add(mapVo);

		return JSONObject.parseObject(supDeliveryMainJson);

	}

	/**
	 * @Description 更加订单生成送货单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addDeliveryGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeliveryGenerate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		//mapVo.put("sup_id", SessionManager.getSupId());
		//mapVo.put("sup_no", SessionManager.getSupNo());
		
		mapVo.put("state", "0");
		mapVo.put("brief", "根据送货单生成入库");
		mapVo.put("maker", SessionManager.getUserId());

		if (mapVo.get("year") == null) {
			mapVo.put("year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("month") == null) {
			mapVo.put("month", MyConfig.getCurAccYearMonth().substring(4, 6));
		}
		if (mapVo.get("in_date") != null) {
			mapVo.put("in_date", DateUtil.stringToDate(mapVo.get("in_date").toString(), "yyyy-MM-dd"));
		} else {
			mapVo.put("in_date", new Date());
		}

		String supDeliveryMainJson;
		try {
			supDeliveryMainJson = supDeliveryMainService.generate(mapVo);
		} catch (Exception e) {
			supDeliveryMainJson = e.getMessage();
		}
		
		if(supDeliveryMainJson.contains("true")){
			mapVo.put("is_com", "0");
			mapVo.put("state", "2");
			supDeliveryMainService.updateMatOrderState(mapVo);
		}
		
		return JSONObject.parseObject(supDeliveryMainJson);

	}
	
	/**
	 * @Description 更加订单生成送货单（代销入库）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addDeliveryGenerateAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeliveryGenerateAffi(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		//mapVo.put("sup_id", SessionManager.getSupId());
		//mapVo.put("sup_no", SessionManager.getSupNo());
		
		mapVo.put("state", "0");
		mapVo.put("brief", "根据送货单生成入库");
		mapVo.put("maker", SessionManager.getUserId());

		if (mapVo.get("year") == null) {
			mapVo.put("year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("month") == null) {
			mapVo.put("month", MyConfig.getCurAccYearMonth().substring(4, 6));
		}
		if (mapVo.get("in_date") != null) {
			mapVo.put("in_date", DateUtil.stringToDate(mapVo.get("in_date").toString(), "yyyy-MM-dd"));
		} else {
			mapVo.put("in_date", new Date());
		}

		String supDeliveryMainJson;
		try {
			supDeliveryMainJson = supDeliveryMainService.generateAffi(mapVo);
		} catch (Exception e) {
			supDeliveryMainJson = e.getMessage();
		}
		
		if(supDeliveryMainJson.contains("true")){
			mapVo.put("state", "2");
			supDeliveryMainService.updateMatAffiOrderState(mapVo);
		}

		return JSONObject.parseObject(supDeliveryMainJson);

	}
	
	/**
	 * @Description 送货单明细生成入库单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addGenerateInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addGenerateInDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		//mapVo.put("sup_id", SessionManager.getSupId());
		//mapVo.put("sup_no", SessionManager.getSupNo());
		
		mapVo.put("state", "0");
		mapVo.put("brief", "根据送货单生成入库");
		mapVo.put("maker", SessionManager.getUserId());

		if (mapVo.get("year") == null) {
			mapVo.put("year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("month") == null) {
			mapVo.put("month", MyConfig.getCurAccYearMonth().substring(4, 6));
		}
		if (mapVo.get("in_date") != null) {
			mapVo.put("in_date", DateUtil.stringToDate(mapVo.get("in_date").toString(), "yyyy-MM-dd"));
		} else {
			mapVo.put("in_date", new Date());
		}
		
		String supDeliveryMainJson;
		try {
			supDeliveryMainJson = supDeliveryMainService.generateInDetail(mapVo);
		} catch (Exception e) {
			supDeliveryMainJson = e.getMessage();
		}
		
		
		if(supDeliveryMainJson.contains("true")){
			mapVo.put("is_com", "0");
			mapVo.put("state", "2");
			supDeliveryMainService.updateMatOrderState(mapVo);
		}
		
		return JSONObject.parseObject(supDeliveryMainJson);

	}
	
	/**
	 * @Description 更加订单生成送货单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/updateDeliveryGenerate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeliveryGenerate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
//		mapVo.put("sup_id", SessionManager.getSupId());
//		mapVo.put("sup_no", SessionManager.getSupNo());
		mapVo.put("state", "0");
		
		if (mapVo.get("year") == null) {
			mapVo.put("year", MyConfig.getCurAccYear());
		}
		if (mapVo.get("month") == null) {
			mapVo.put("month", MyConfig.getCurAccYearMonth().substring(4, 6));
		}
		if (mapVo.get("in_date") != null) {
			mapVo.put("in_date", DateUtil.stringToDate(mapVo.get("in_date").toString(), "yyyy-MM-dd"));
		} else {
			mapVo.put("in_date", new Date());
		}
		
		String supDeliveryMainJson = supDeliveryMainService.updateDeliveryDetailGenerate(mapVo);
		
		return JSONObject.parseObject(supDeliveryMainJson);
		
	}

	/**
	 * @Description 更新跳转页面 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryMainUpdatePage", method = RequestMethod.GET)
	public String supDeliveryMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SupDeliveryMain supDeliveryMain = new SupDeliveryMain();

		supDeliveryMain = supDeliveryMainService.queryByCode(mapVo);

		mode.addAttribute("delivery_id", supDeliveryMain.getDelivery_id());
		mode.addAttribute("delivery_no", supDeliveryMain.getDelivery_no());
		mode.addAttribute("group_id", supDeliveryMain.getGroup_id());
		mode.addAttribute("hos_id", supDeliveryMain.getHos_id());
		mode.addAttribute("copy_code", supDeliveryMain.getCopy_code());
		mode.addAttribute("acc_year", supDeliveryMain.getAcc_year());
		mode.addAttribute("acc_month", supDeliveryMain.getAcc_month());
		mode.addAttribute("sup_id", supDeliveryMain.getSup_id());
		mode.addAttribute("sup_no", supDeliveryMain.getSup_no());
		mode.addAttribute("sup_code", supDeliveryMain.getSup_code());
		mode.addAttribute("sup_name", supDeliveryMain.getSup_name());
		if (supDeliveryMain.getCreate_date() == null) {
			mode.addAttribute("create_date", supDeliveryMain.getCreate_date());
		} else {
			mode.addAttribute("create_date", dateFormat.format(supDeliveryMain.getCreate_date()));
		}
		if (supDeliveryMain.getCheck_date() == null) {
			mode.addAttribute("check_date", supDeliveryMain.getCheck_date());
		} else {
			mode.addAttribute("check_date", dateFormat.format(supDeliveryMain.getCheck_date()));
		}
		mode.addAttribute("bill_type", supDeliveryMain.getBill_type());
		mode.addAttribute("hos_id_in", supDeliveryMain.getHos_id_in());
		mode.addAttribute("is_dir", supDeliveryMain.getIs_dir());
		mode.addAttribute("dir_dept_id", supDeliveryMain.getDir_dept_id());
		mode.addAttribute("dir_dept_no", supDeliveryMain.getDir_dept_no());
		mode.addAttribute("delivery_address", supDeliveryMain.getDelivery_address());
		mode.addAttribute("delivery_url", supDeliveryMain.getDelivery_url());
		mode.addAttribute("in_state", supDeliveryMain.getIn_state());
		mode.addAttribute("state", supDeliveryMain.getState());

		List<String> exists=supDeliveryMainService.queryDeliveryOrderRelaExists(mapVo);
		
		if(exists.size()>0){
			return "hrp/sup/supdeliverymain/updateOrder";
			
		}else{
			return "hrp/sup/supdeliverymain/update";
		}
		
		
	}

	/**
	 * @Description 更新数据 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/updateSupDeliveryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupDeliveryMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String create_date = String.valueOf(mapVo.get("create_date"));
		if (StringUtils.isNotEmpty(create_date)) {
			mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));
		}

		String supDeliveryMainJson = supDeliveryMainService.addOrUpdateBatch(mapVo);

		return JSONObject.parseObject(supDeliveryMainJson);
	}

	/**
	 * @Description 单据审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/updaeCheckState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updaeCheckState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String supDeliveryMainJson = null;
		if (mapVo.get("update_para") == null) {
			String m = "{\"msg\":\"参数无效.\",\"state\":\"true\"}";
			return JSONObject.parseObject(m);
		} else {
			for (String str : mapVo.get("update_para").toString().split(",")) {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				mapVo.put("sup_id", SessionManager.getSupId());
				mapVo.put("sup_no", SessionManager.getSupNo());
				mapVo.put("delivery_id", str.split("@")[0]);
				mapVo.put("delivery_no", str.split("@")[1]);
				mapVo.put("state", "1");

				supDeliveryMainJson = supDeliveryMainService.update(mapVo);
			}

			return JSONObject.parseObject(supDeliveryMainJson);
		}

	}

	/**
	 * @Description 单据消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/updaeCheckStateNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updaeCheckStateNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String supDeliveryMainJson = null;
		if (mapVo.get("update_para") == null) {
			String m = "{\"msg\":\"参数无效.\",\"state\":\"true\"}";
			return JSONObject.parseObject(m);
		} else {
			for (String str : mapVo.get("update_para").toString().split(",")) {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				mapVo.put("sup_id", SessionManager.getSupId());
				mapVo.put("sup_no", SessionManager.getSupNo());
				mapVo.put("delivery_id", str.split("|")[0]);
				mapVo.put("delivery_no", str.split("|")[1]);
				mapVo.put("state", "0");

				supDeliveryMainJson = supDeliveryMainService.update(mapVo);
			}

			return JSONObject.parseObject(supDeliveryMainJson);
		}
	}

	@RequestMapping(value = "/hrp/sup/supdeliverymain/querySupDeliveryDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String detail = supDeliveryMainService.queryDetailByCode(mapVo);

		return JSONObject.parseObject(detail);
	}

	/**
	 * @Description 更新数据 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addOrUpdateSupDeliveryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateSupDeliveryMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String supDeliveryMainJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			supDeliveryMainJson = supDeliveryMainService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(supDeliveryMainJson);
	}

	/**
	 * @Description 删除数据 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/deleteSupDeliveryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupDeliveryMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("delivery_id", ids[0]);
			mapVo.put("delivery_no", ids[1]);
			mapVo.put("group_id", ids[2]);
			mapVo.put("hos_id", ids[3]);
			mapVo.put("copy_code", ids[4]);
			mapVo.put("acc_year", ids[5]);
			mapVo.put("acc_month", ids[6]);

			listVo.add(mapVo);

		}

		String supDeliveryMainJson = supDeliveryMainService.deleteBatch(listVo);

		return JSONObject.parseObject(supDeliveryMainJson);

	}

	/**
	 * @Description 查询数据 100101 送货单主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/querySupDeliveryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupDeliveryMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		if (mapVo.get("create_date_begin") != null ) {
			if(!mapVo.get("create_date_begin").toString().equals("")){
			mapVo.put("create_date_begin", DateUtil.stringToDate(mapVo.get("create_date_begin").toString(), "yyyy-MM-dd"));
			}
		}
		if (mapVo.get("create_date_end") != null ) {
			if(!mapVo.get("create_date_end").toString().equals("")){
			mapVo.put("create_date_end", DateUtil.stringToDate(mapVo.get("create_date_end").toString(), "yyyy-MM-dd"));
			}
		}

		if (mapVo.get("create_date_begin") != null && mapVo.get("create_date_end") == null) {
			mapVo.put("create_date_end", new Date());
		}
		if (mapVo.get("check_date_begin") != null) {
			if(!mapVo.get("check_date_begin").toString().equals("")){
				mapVo.put("check_date_begin", DateUtil.stringToDate(mapVo.get("check_date_begin").toString(), "yyyy-MM-dd"));
			}
			
		}
		if (mapVo.get("check_date_end") != null  ) {
			if(!mapVo.get("check_date_end").toString().equals("")){
			mapVo.put("check_date_end", DateUtil.stringToDate(mapVo.get("check_date_end").toString(), "yyyy-MM-dd"));
			}
		}
		if (mapVo.get("check_date_begin") != null && mapVo.get("check_date_end") == null) {
			mapVo.put("check_date_end", new Date());
		}
		if (mapVo.get("bill_date") != null ) {
			if(!mapVo.get("bill_date").toString().equals("")){
			mapVo.put("bill_date", DateUtil.stringToDate(mapVo.get("bill_date").toString(), "yyyy-MM-dd"));
			}
		}
		
		//mapVo.put("sup_id", SessionManager.getSupId());
		//mapVo.put("sup_no", SessionManager.getSupNo());

		String supDeliveryMain = supDeliveryMainService.query(getPage(mapVo));

		return JSONObject.parseObject(supDeliveryMain);

	}

	/**
	 * @Description 导入跳转页面 100101 送货单主表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/supDeliveryMainImportPage", method = RequestMethod.GET)
	public String supDeliveryMainImportPage(Model mode) throws Exception {

		return "hrp/sup/supdeliverymain/supDeliveryMainImport";

	}

	/**
	 * @Description 下载导入模版 100101 送货单主表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/sup/supdeliverymain/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sup\\downTemplate", "100101 送货单主表.xls");

		return null;
	}

	/**
	 * @Description 导入数据 100101 送货单主表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/readSupDeliveryMainFiles", method = RequestMethod.POST)
	public String readSupDeliveryMainFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
	        throws IOException {

		List<SupDeliveryMain> list_err = new ArrayList<SupDeliveryMain>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				SupDeliveryMain supDeliveryMain = new SupDeliveryMain();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (StringTool.isNotBlank(temp[0])) {

					supDeliveryMain.setDelivery_id(Long.valueOf(temp[0]));
					mapVo.put("delivery_id", temp[0]);

				} else {

					err_sb.append("送货ID为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					supDeliveryMain.setDelivery_no(temp[1]);
					mapVo.put("delivery_no", temp[1]);

				} else {

					err_sb.append("送货单号为空  ");

				}

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[5])) {

					supDeliveryMain.setAcc_year(temp[5]);
					mapVo.put("acc_year", temp[5]);

				} else {

					err_sb.append("统计年度为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					supDeliveryMain.setAcc_month(temp[6]);
					mapVo.put("acc_month", temp[6]);

				} else {

					err_sb.append("统计月份为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					supDeliveryMain.setSup_id(Long.valueOf(temp[7]));
					mapVo.put("sup_id", temp[7]);

				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					supDeliveryMain.setSup_no(Long.valueOf(temp[8]));
					mapVo.put("sup_no", temp[8]);

				} else {

					err_sb.append("供应商NO为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					supDeliveryMain.setCreate_date(DateUtil.stringToDate(temp[9]));
					mapVo.put("create_data", temp[9]);

				} else {

					err_sb.append("编制日期为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					supDeliveryMain.setCheck_date(DateUtil.stringToDate(temp[10]));
					mapVo.put("check_date", temp[10]);

				} else {

					err_sb.append("确定日期为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					supDeliveryMain.setBill_type(temp[11]);
					mapVo.put("bill_type", temp[11]);

				} else {

					err_sb.append("单据类型为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					supDeliveryMain.setHos_id_in(Long.valueOf(temp[12]));
					mapVo.put("hos_id_in", temp[12]);

				} else {

					err_sb.append("医院名称为空  ");

				}

				if (StringTool.isNotBlank(temp[13])) {

					supDeliveryMain.setIs_dir(Integer.valueOf(temp[13]));
					mapVo.put("is_dir", temp[13]);

				} else {

					err_sb.append("是否定向为空  ");

				}

				if (StringTool.isNotBlank(temp[14])) {

					supDeliveryMain.setDir_dept_id(Long.valueOf(temp[14]));
					mapVo.put("dir_dept_id", temp[14]);

				} else {

					err_sb.append("定向科室为空  ");

				}

				if (StringTool.isNotBlank(temp[15])) {

					supDeliveryMain.setDelivery_address(temp[15]);
					mapVo.put("delivery_address", temp[15]);

				} else {

					err_sb.append("送货地址为空  ");

				}

				if (StringTool.isNotBlank(temp[16])) {

					supDeliveryMain.setDelivery_url(temp[16]);
					mapVo.put("delivery_url", temp[16]);

				} else {

					err_sb.append("单据条码地址为空  ");

				}

				if (StringTool.isNotBlank(temp[17])) {

					supDeliveryMain.setIn_state(Integer.valueOf(temp[17]));
					mapVo.put("in_state", temp[17]);

				} else {

					err_sb.append("入库状态为空  ");

				}

				if (StringTool.isNotBlank(temp[18])) {

					supDeliveryMain.setState(Integer.valueOf(temp[18]));
					mapVo.put("state", temp[18]);

				} else {

					err_sb.append("单据状态为空  ");

				}

				SupDeliveryMain data_exc_extis = supDeliveryMainService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					supDeliveryMain.setError_type(err_sb.toString());

					list_err.add(supDeliveryMain);

				} else {

					String dataJson = supDeliveryMainService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SupDeliveryMain data_exc = new SupDeliveryMain();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 100101 送货单主表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/supdeliverymain/addBatchSupDeliveryMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSupDeliveryMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<SupDeliveryMain> list_err = new ArrayList<SupDeliveryMain>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				SupDeliveryMain supDeliveryMain = new SupDeliveryMain();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("delivery_id"))) {

					supDeliveryMain.setDelivery_id(Long.valueOf((String) jsonObj.get("delivery_id")));
					mapVo.put("delivery_id", jsonObj.get("delivery_id"));
				} else {

					err_sb.append("送货ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delivery_no"))) {

					supDeliveryMain.setDelivery_no((String) jsonObj.get("delivery_no"));
					mapVo.put("delivery_no", jsonObj.get("delivery_no"));
				} else {

					err_sb.append("送货单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("acc_year"))) {

					supDeliveryMain.setAcc_year((String) jsonObj.get("acc_year"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
				} else {

					err_sb.append("统计年度为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("acc_month"))) {

					supDeliveryMain.setAcc_month((String) jsonObj.get("acc_month"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
				} else {

					err_sb.append("统计月份为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {

					supDeliveryMain.setSup_id(Long.valueOf((String) jsonObj.get("sup_id")));
					mapVo.put("sup_id", jsonObj.get("sup_id"));
				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sup_no"))) {

					supDeliveryMain.setSup_no(Long.valueOf((String) jsonObj.get("sup_no")));
					mapVo.put("sup_no", jsonObj.get("sup_no"));
				} else {

					err_sb.append("供应商NO为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_data"))) {

					supDeliveryMain.setCreate_date(DateUtil.stringToDate((String) jsonObj.get("create_data")));
					mapVo.put("create_data", jsonObj.get("create_data"));
				} else {

					err_sb.append("编制日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("check_date"))) {

					supDeliveryMain.setCheck_date(DateUtil.stringToDate((String) jsonObj.get("check_date")));
					mapVo.put("check_date", jsonObj.get("check_date"));
				} else {

					err_sb.append("确定日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bill_type"))) {

					supDeliveryMain.setBill_type((String) jsonObj.get("bill_type"));
					mapVo.put("bill_type", jsonObj.get("bill_type"));
				} else {

					err_sb.append("单据类型为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("hos_id_in"))) {

					supDeliveryMain.setHos_id_in(Long.valueOf((String) jsonObj.get("hos_id_in")));
					mapVo.put("hos_id_in", jsonObj.get("hos_id_in"));
				} else {

					err_sb.append("医院名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_dir"))) {

					supDeliveryMain.setIs_dir(Integer.valueOf((String) jsonObj.get("is_dir")));
					mapVo.put("is_dir", jsonObj.get("is_dir"));
				} else {

					err_sb.append("是否定向为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dir_dept"))) {

					supDeliveryMain.setDir_dept_id(Long.valueOf((String) jsonObj.get("dir_dept")));
					mapVo.put("dir_dept_id", jsonObj.get("dir_dept"));
				} else {

					err_sb.append("定向科室为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delivery_address"))) {

					supDeliveryMain.setDelivery_address((String) jsonObj.get("delivery_address"));
					mapVo.put("delivery_address", jsonObj.get("delivery_address"));
				} else {

					err_sb.append("送货地址为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("delivery_url"))) {

					supDeliveryMain.setDelivery_url((String) jsonObj.get("delivery_url"));
					mapVo.put("delivery_url", jsonObj.get("delivery_url"));
				} else {

					err_sb.append("单据条码地址为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("in_state"))) {

					supDeliveryMain.setIn_state(Integer.valueOf((String) jsonObj.get("in_state")));
					mapVo.put("in_state", jsonObj.get("in_state"));
				} else {

					err_sb.append("入库状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					supDeliveryMain.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("单据状态为空  ");

				}

				SupDeliveryMain data_exc_extis = supDeliveryMainService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					supDeliveryMain.setError_type(err_sb.toString());

					list_err.add(supDeliveryMain);

				} else {

					String dataJson = supDeliveryMainService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SupDeliveryMain data_exc = new SupDeliveryMain();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
