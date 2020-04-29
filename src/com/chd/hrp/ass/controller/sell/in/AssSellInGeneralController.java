/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.sell.in;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.sell.in.AssSellInGeneral;
import com.chd.hrp.ass.entity.sell.out.AssSellOutGeneral;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailGeneralService;
import com.chd.hrp.ass.service.sell.in.AssSellInGeneralService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailGeneralService;
import com.chd.hrp.ass.service.sell.out.AssSellOutGeneralService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表（一般设备)
 * @Table:
 * ASS_SELL_IN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssSellInGeneralController extends BaseController{

	// 引入Service服务
	@Resource(name = "assSellInGeneralService")
	private final AssSellInGeneralService assSellInGeneralService = null;

	@Resource(name = "assSellInDetailGeneralService")
	private final AssSellInDetailGeneralService assSellInDetailGeneralService = null;

	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	@Resource(name = "assSellOutGeneralService")
	private final AssSellOutGeneralService assSellOutGeneralService = null;
	
	@Resource(name = "assSellOutDetailGeneralService")
	private final AssSellOutDetailGeneralService assSellOutDetailGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/assSellInGeneralMainPage", method = RequestMethod.GET)
	public String assSellInGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05029",MyConfig.getSysPara("05029"));
		
		return "hrp/ass/assgeneral/asssellin/main";

	}

	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/assImportSellOutGeneralPage", method = RequestMethod.GET)
	public String assImportSellOutGeneralPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("out_store_code", mapVo.get("out_store_code"));
		mode.addAttribute("out_store_name", mapVo.get("out_store_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("in_store_id", mapVo.get("in_store_id"));
		mode.addAttribute("in_store_no", mapVo.get("in_store_no"));
		mode.addAttribute("note", mapVo.get("note"));
		mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asssellin/importoutmain";
	}

	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/assViewSellOutGeneralPage", method = RequestMethod.GET)
	public String assViewSellOutGeneralPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("sell_in_no", mapVo.get("sell_in_no"));
		mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asssellin/viewoutmain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/assSellInGeneralAddPage", method = RequestMethod.GET)
	public String assSellInGeneralAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asssellin/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/saveAssSellInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssSellInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);
		if (assSellInGeneral != null) {
			if (assSellInGeneral.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assSellInGeneralJson = assSellInGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellInGeneralJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/assSellInGeneralUpdatePage", method = RequestMethod.GET)
	public String assSellInGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssSellInGeneral assSellInGeneral = new AssSellInGeneral();

		assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assSellInGeneral.getGroup_id());
		mode.addAttribute("hos_id", assSellInGeneral.getHos_id());
		mode.addAttribute("copy_code", assSellInGeneral.getCopy_code());
		mode.addAttribute("sell_in_no", assSellInGeneral.getSell_in_no());
		mode.addAttribute("in_store_id", assSellInGeneral.getIn_store_id());
		mode.addAttribute("in_store_no", assSellInGeneral.getIn_store_no());
		mode.addAttribute("in_store_code", assSellInGeneral.getIn_store_code());
		mode.addAttribute("in_store_name", assSellInGeneral.getIn_store_name());
		mode.addAttribute("out_group_id", assSellInGeneral.getOut_group_id());
		mode.addAttribute("out_group_name", assSellInGeneral.getOut_group_name());
		mode.addAttribute("out_hos_id", assSellInGeneral.getOut_hos_id());
		mode.addAttribute("out_hos_name", assSellInGeneral.getOut_hos_name());
		mode.addAttribute("out_store_id", assSellInGeneral.getOut_store_id());
		mode.addAttribute("out_store_no", assSellInGeneral.getOut_store_no());
		mode.addAttribute("out_store_code", assSellInGeneral.getOut_store_code());
		mode.addAttribute("out_store_name", assSellInGeneral.getOut_store_name());
		mode.addAttribute("price", assSellInGeneral.getPrice());
		mode.addAttribute("add_depre", assSellInGeneral.getAdd_depre());
		mode.addAttribute("cur_money", assSellInGeneral.getCur_money());
		mode.addAttribute("fore_money", assSellInGeneral.getFore_money());
		mode.addAttribute("create_emp", assSellInGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellInGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellInGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellInGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellInGeneral.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assSellInGeneral.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assSellInGeneral.getState());
		mode.addAttribute("state_name", assSellInGeneral.getState_name());
		mode.addAttribute("note", assSellInGeneral.getNote());
		mode.addAttribute("bus_type_code", assSellInGeneral.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellInGeneral.getBus_type_name());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05029",MyConfig.getSysPara("05029"));
		
		return "hrp/ass/assgeneral/asssellin/update";
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/updateConfirmSellInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellInMainGeneral(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("sell_in_no", ids[3]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);
			if(DateUtil.compareDate(assSellInGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<Map<String, Object>> list = assSellInDetailGeneralService.queryByInit(mapVo);
			if (list.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}

			for (Map<String, Object> temp : list) {

				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));

				mapExists.put("ass_in_no", temp.get("sell_in_no"));

				mapExists.put("ass_id", temp.get("ass_id"));

				mapExists.put("ass_no", temp.get("ass_no"));

				Integer use_state = assCardGeneralService.queryCardExistsByAssInNo(mapExists);

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardGeneralService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true, oriCardNo = true, specFlag = true, modelFlag = true, brandFlag = true,
						facFlag = true, priceFlag = true, depreFlag = true, depreMonthFlag = true, curMoneyFlag = true,
						foreMoneyFlag = true, inStoreFlag = true,busTypeFlag = true;

				for (Map<String, Object> card : cardList) {
					if (verification(temp.get("ass_id"), card.get("ass_id"))) {
						assFlag = false;
						break;
					}
					if (verification(temp.get("ass_ori_card_no"), card.get("ass_ori_card_no"))) {
						oriCardNo = false;
						break;
					}
					if (verification(temp.get("ass_spec"), card.get("ass_spec"))) {
						specFlag = false;
						break;
					}
					if (verification(temp.get("ass_model"), card.get("ass_mondl"))) {
						modelFlag = false;
						break;
					}
					if (verification(temp.get("ass_brand"), card.get("ass_brand"))) {
						brandFlag = false;
						break;
					}
					if (verification(temp.get("fac_id"), card.get("fac_id"))) {
						facFlag = false;
						break;
					}
					if (verification(temp.get("price"), card.get("price"))) {
						priceFlag = false;
						break;
					}
					if (verification(temp.get("add_depre"), card.get("depre_money"))) {
						depreFlag = false;
						break;
					}
					if (verification(temp.get("add_depre_month"), card.get("add_depre_month"))) {
						depreMonthFlag = false;
						break;
					}
					if (verification(temp.get("cur_money"), card.get("cur_money"))) {
						curMoneyFlag = false;
						break;
					}
					if (verification(temp.get("fore_money"), card.get("fore_money"))) {
						foreMoneyFlag = false;
						break;
					}
					if (verification(temp.get("in_store_id"), card.get("store_id"))) {
						inStoreFlag = false;
						break;
					}
					
					if (verification(temp.get("bus_type_code"), card.get("buy_type"))) {
						busTypeFlag = false;
						break;
					}
				}
				if (!assFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if (!oriCardNo) {
					return JSONObject.parseObject("{\"warn\":\"存在原始卡片号不匹配的资产,不能入库! \"}");
				}
				if (!specFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在规格不匹配的资产,不能入库! \"}");
				}
				if (!modelFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在型号不匹配的资产,不能入库! \"}");
				}
				if (!brandFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在品牌不匹配的资产,不能入库! \"}");
				}
				if (!facFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在生产厂商不匹配的资产,不能入库! \"}");
				}
				if (!priceFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产原值和卡片原值不匹配的资产,不能入库! \"}");
				}
				if (!depreFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧和卡片累计折旧不匹配的资产,不能入库! \"}");
				}
				if (!depreMonthFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧月份和卡片累计折旧月份不匹配的资产,不能入库! \"}");
				}
				if (!curMoneyFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产净值和卡片资产净值不匹配的资产,不能入库! \"}");
				}
				if (!foreMoneyFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产预留残值和预留残值不匹配的资产,不能入库! \"}");
				}
				if (!inStoreFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
				}
				if (!busTypeFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在业务类型不匹配的资产,不能入库! \"}");
				}
				
			}

		}

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("sell_in_no", ids[3]);

			mapVo.put("ass_in_no", ids[3]);

			mapVo.put("in_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());

			mapVo.put("audit_emp", SessionManager.getUserId());

			AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);

			if (assSellInGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assSellInGeneralService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	private boolean verification(Object obj1, Object obj2) {
		// true为不相等
		if (NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))) {
			Double number1 = obj1 == null || obj1.equals("") ? 0 : Double.parseDouble(String.valueOf(obj1));
			Double number2 = obj2 == null || obj2.equals("") ? 0 : Double.parseDouble(String.valueOf(obj2));
			if (Math.abs(number1 - number2) > 0) {
				return true;
			} else {
				return false;
			}
		}
		if (obj1 == null && obj2 == null) {
			return false;
		}
		if (obj1 == null && obj2 != null) {
			return true;
		}
		if (obj1 != null && obj2 == null) {
			return true;
		}
		if (obj1 != null && obj2 != null) {
			if (!obj1.equals(obj2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/initAssSellInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assSellInGeneralService.initAssSellInGeneral(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);

	}
	

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/deleteAssSellInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("sell_in_no", ids[3]);
			mapVo.put("ass_in_no", ids[3]);

			AssSellInGeneral assInGeneral = assSellInGeneralService.queryByCode(mapVo);

			if (assInGeneral.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assSellInGeneralJson = assSellInGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/queryAssSellInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assSellInGeneral = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellInGeneral = assSellInGeneralService.query(getPage(mapVo));
		}else{
			assSellInGeneral = assSellInGeneralService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellInGeneral);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/deleteAssSellInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("sell_in_no", ids[3]);
			mapVo.put("ass_id", ids[4]);
			mapVo.put("ass_no", ids[5]);
			mapVo.put("ass_ori_card_no", ids[6]);
			mapVo.put("ass_in_no", ids[3]);
			AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);

			if (assSellInGeneral.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}

		String assSellInDetailGeneralJson = assSellInDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/queryAssSellInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellInDetailGeneral = assSellInDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellInDetailGeneral);

	}

	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/queryAssSellInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardGeneralService.query(getPage(mapVo));

		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				jsonarray.remove(i);
			}
		}
		json.put("Rows", jsonarray);
		return JSONObject.parseObject(json.toString());
	}

	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/initAssSellInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);

			if (assSellInGeneral.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInGeneralService.initAssSellInCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/initAssSellInBatchCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInBatchCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);

			if (assSellInGeneral.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInGeneralService.initAssSellInBatchCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}

	// 查询出库单引入使用
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/queryBySellInImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBySellInImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assSellInGeneral = assSellOutGeneralService.queryBySellInImport(getPage(mapVo));

		return JSONObject.parseObject(assSellInGeneral);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/queryBySellInImportView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBySellInImportView(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assSellInGeneral = assSellOutGeneralService.queryBySellInImportView(getPage(mapVo));

		return JSONObject.parseObject(assSellInGeneral);
	}

	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/deleteAssSellInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInCardGeneral(@RequestParam(value = "ParamVo") String paramVo,
			String ass_nature, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		boolean flag1 = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("sell_in_no", ids[4]);
			mapVo.put("ass_in_no", ids[4]);
			AssSellInGeneral assSellInGeneral = assSellInGeneralService.queryByCode(mapVo);

			if (assSellInGeneral.getState() > 0) {
				flag = false;
				break;
			}

			AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			if (assCardGeneral.getUse_state() > 0) {
				flag1 = false;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		if (!flag1) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		String assCardGeneralJson = "";
		try {
			assCardGeneralJson = assCardGeneralService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/assSellOutGeneralViewPage", method = RequestMethod.GET)
	public String assSellOutGeneralViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssSellOutGeneral assSellOutGeneral = new AssSellOutGeneral();

		assSellOutGeneral = assSellOutGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assSellOutGeneral.getGroup_id());
		mode.addAttribute("hos_id", assSellOutGeneral.getHos_id());
		mode.addAttribute("copy_code", assSellOutGeneral.getCopy_code());
		mode.addAttribute("sell_out_no", assSellOutGeneral.getSell_out_no());
		mode.addAttribute("out_store_id", assSellOutGeneral.getOut_store_id());
		mode.addAttribute("out_store_no", assSellOutGeneral.getOut_store_no());
		mode.addAttribute("out_store_code", assSellOutGeneral.getOut_store_code());
		mode.addAttribute("out_store_name", assSellOutGeneral.getOut_store_name());
		mode.addAttribute("in_group_id", assSellOutGeneral.getIn_group_id());
		mode.addAttribute("in_group_name", assSellOutGeneral.getIn_group_name());
		mode.addAttribute("in_hos_id", assSellOutGeneral.getIn_hos_id());
		mode.addAttribute("in_hos_name", assSellOutGeneral.getIn_hos_name());
		mode.addAttribute("in_store_id", assSellOutGeneral.getIn_store_id());
		mode.addAttribute("in_store_no", assSellOutGeneral.getIn_store_no());
		mode.addAttribute("in_store_code", assSellOutGeneral.getIn_store_code());
		mode.addAttribute("in_store_name", assSellOutGeneral.getIn_store_name());
		mode.addAttribute("price", assSellOutGeneral.getPrice());
		mode.addAttribute("add_depre", assSellOutGeneral.getAdd_depre());
		mode.addAttribute("cur_money", assSellOutGeneral.getCur_money());
		mode.addAttribute("fore_money", assSellOutGeneral.getFore_money());
		mode.addAttribute("sell_price", assSellOutGeneral.getSell_price());
		mode.addAttribute("dispose_income", assSellOutGeneral.getDispose_income());
		mode.addAttribute("dispose_tax", assSellOutGeneral.getDispose_tax());
		mode.addAttribute("create_emp", assSellOutGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellOutGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellOutGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellOutGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellOutGeneral.getAudit_emp_name());
		mode.addAttribute("audit_date", assSellOutGeneral.getAudit_date());
		mode.addAttribute("state", assSellOutGeneral.getState());
		mode.addAttribute("state_name", assSellOutGeneral.getState_name());
		mode.addAttribute("note", assSellOutGeneral.getNote());
		mode.addAttribute("bus_type_code", assSellOutGeneral.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellOutGeneral.getBus_type_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asssellin/viewout";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/querySellOutDetBySellInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySellOutDetBySellInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailGeneral = assSellOutDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailGeneral);
	}
	
	/**
	 * 一般设备 调拨入库  入库单状态查询 （打印校验数据用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellin/queryAssSellInGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//一般设备 调拨入库 入库单状态查询  （打印时校验数据专用）
		List<String> list = assSellInGeneralService.queryAssSellInGeneralState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"入库单【"+str+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
	}
}
