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

import org.apache.log4j.Logger;
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
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.sell.in.AssSellInSpecial;
import com.chd.hrp.ass.entity.sell.out.AssSellOutSpecial;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailSpecialService;
import com.chd.hrp.ass.service.sell.in.AssSellInSpecialService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailSpecialService;
import com.chd.hrp.ass.service.sell.out.AssSellOutSpecialService;

/**
 * 
 * @Description: 050901 资产有偿调拨入库单主表（专用设备)
 * @Table: ASS_SELL_IN_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellInSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellInSpecialController.class);

	// 引入Service服务
	@Resource(name = "assSellInSpecialService")
	private final AssSellInSpecialService assSellInSpecialService = null;

	@Resource(name = "assSellInDetailSpecialService")
	private final AssSellInDetailSpecialService assSellInDetailSpecialService = null;

	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	@Resource(name = "assSellOutSpecialService")
	private final AssSellOutSpecialService assSellOutSpecialService = null;
	
	@Resource(name = "assSellOutDetailSpecialService")
	private final AssSellOutDetailSpecialService assSellOutDetailSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/assSellInSpecialMainPage", method = RequestMethod.GET)
	public String assSellInSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asssellin/main";

	}

	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/assImportSellOutSpecialPage", method = RequestMethod.GET)
	public String assImportSellOutSpecialPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		return "hrp/ass/assspecial/asssellin/importoutmain";
	}

	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/assViewSellOutSpecialPage", method = RequestMethod.GET)
	public String assViewSellOutSpecialPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("sell_in_no", mapVo.get("sell_in_no"));
		mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asssellin/viewoutmain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/assSellInSpecialAddPage", method = RequestMethod.GET)
	public String assSellInSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asssellin/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨入库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/saveAssSellInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssSellInSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);
		if (assSellInSpecial != null) {
			if (assSellInSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assSellInSpecialJson = assSellInSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellInSpecialJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/assSellInSpecialUpdatePage", method = RequestMethod.GET)
	public String assSellInSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssSellInSpecial assSellInSpecial = new AssSellInSpecial();

		assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assSellInSpecial.getGroup_id());
		mode.addAttribute("hos_id", assSellInSpecial.getHos_id());
		mode.addAttribute("copy_code", assSellInSpecial.getCopy_code());
		mode.addAttribute("sell_in_no", assSellInSpecial.getSell_in_no());
		mode.addAttribute("in_store_id", assSellInSpecial.getIn_store_id());
		mode.addAttribute("in_store_no", assSellInSpecial.getIn_store_no());
		mode.addAttribute("in_store_code", assSellInSpecial.getIn_store_code());
		mode.addAttribute("in_store_name", assSellInSpecial.getIn_store_name());
		mode.addAttribute("out_group_id", assSellInSpecial.getOut_group_id());
		mode.addAttribute("out_group_name", assSellInSpecial.getOut_group_name());
		mode.addAttribute("out_hos_id", assSellInSpecial.getOut_hos_id());
		mode.addAttribute("out_hos_name", assSellInSpecial.getOut_hos_name());
		mode.addAttribute("out_store_id", assSellInSpecial.getOut_store_id());
		mode.addAttribute("out_store_no", assSellInSpecial.getOut_store_no());
		mode.addAttribute("out_store_code", assSellInSpecial.getOut_store_code());
		mode.addAttribute("out_store_name", assSellInSpecial.getOut_store_name());
		mode.addAttribute("price", assSellInSpecial.getPrice());
		mode.addAttribute("add_depre", assSellInSpecial.getAdd_depre());
		mode.addAttribute("cur_money", assSellInSpecial.getCur_money());
		mode.addAttribute("fore_money", assSellInSpecial.getFore_money());
		mode.addAttribute("create_emp", assSellInSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellInSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellInSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellInSpecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellInSpecial.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assSellInSpecial.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assSellInSpecial.getState());
		mode.addAttribute("state_name", assSellInSpecial.getState_name());
		mode.addAttribute("note", assSellInSpecial.getNote());
		mode.addAttribute("bus_type_code", assSellInSpecial.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellInSpecial.getBus_type_name());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asssellin/update";
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/updateConfirmSellInMainSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellInMainSpecial(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);
			if (assSellInSpecial.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assSellInSpecial.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<Map<String, Object>> list = assSellInDetailSpecialService.queryByInit(mapVo);
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

				Integer use_state = assCardSpecialService.queryCardExistsByAssInNo(mapExists);

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardSpecialService.queryAssCardByAssInNo(mapExists);
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

			AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);

			if (assSellInSpecial.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assSellInSpecialService.updateConfirm(listVo);
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
	
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/initAssSellInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInSpecial = assSellInSpecialService.initAssSellInSpecial(mapVo);

		return JSONObject.parseObject(assAllotInSpecial);

	}
	

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/deleteAssSellInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellInSpecial assInSpecial = assSellInSpecialService.queryByCode(mapVo);

			if (assInSpecial.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assSellInSpecialJson = assSellInSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/queryAssSellInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assSellInSpecial = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellInSpecial = assSellInSpecialService.query(getPage(mapVo));
		}else{
			assSellInSpecial = assSellInSpecialService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellInSpecial);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/deleteAssSellInDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInDetailSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);

			if (assSellInSpecial.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}

		String assSellInDetailSpecialJson = assSellInDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/queryAssSellInDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellInDetailSpecial = assSellInDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellInDetailSpecial);

	}

	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/queryAssSellInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardSpecialService.query(getPage(mapVo));
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

	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/initAssSellInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);

			if (assSellInSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInSpecialService.initAssSellInCardSpecial(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/initAssSellInBatchCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInBatchCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);

			if (assSellInSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInSpecialService.initAssSellInBatchCardSpecial(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}

	// 查询出库单引入使用
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/queryBySellInImport", method = RequestMethod.POST)
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

		String assSellInSpecial = assSellOutSpecialService.queryBySellInImport(getPage(mapVo));

		return JSONObject.parseObject(assSellInSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/queryBySellInImportView", method = RequestMethod.POST)
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
		
		String assSellInSpecial = assSellOutSpecialService.queryBySellInImportView(getPage(mapVo));

		return JSONObject.parseObject(assSellInSpecial);
	}

	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/deleteAssSellInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInCardSpecial(@RequestParam(value = "ParamVo") String paramVo,
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
			AssSellInSpecial assSellInSpecial = assSellInSpecialService.queryByCode(mapVo);

			if (assSellInSpecial.getState() > 0) {
				flag = false;
				break;
			}

			AssCardSpecial assCardSpecial = assCardSpecialService.queryByCode(mapVo);
			if (assCardSpecial.getUse_state() > 0) {
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
			assCardGeneralJson = assCardSpecialService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/assSellOutSpecialViewPage", method = RequestMethod.GET)
	public String assSellOutSpecialViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssSellOutSpecial assSellOutSpecial = new AssSellOutSpecial();

		assSellOutSpecial = assSellOutSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assSellOutSpecial.getGroup_id());
		mode.addAttribute("hos_id", assSellOutSpecial.getHos_id());
		mode.addAttribute("copy_code", assSellOutSpecial.getCopy_code());
		mode.addAttribute("sell_out_no", assSellOutSpecial.getSell_out_no());
		mode.addAttribute("out_store_id", assSellOutSpecial.getOut_store_id());
		mode.addAttribute("out_store_no", assSellOutSpecial.getOut_store_no());
		mode.addAttribute("out_store_code", assSellOutSpecial.getOut_store_code());
		mode.addAttribute("out_store_name", assSellOutSpecial.getOut_store_name());
		mode.addAttribute("in_group_id", assSellOutSpecial.getIn_group_id());
		mode.addAttribute("in_group_name", assSellOutSpecial.getIn_group_name());
		mode.addAttribute("in_hos_id", assSellOutSpecial.getIn_hos_id());
		mode.addAttribute("in_hos_name", assSellOutSpecial.getIn_hos_name());
		mode.addAttribute("in_store_id", assSellOutSpecial.getIn_store_id());
		mode.addAttribute("in_store_no", assSellOutSpecial.getIn_store_no());
		mode.addAttribute("in_store_code", assSellOutSpecial.getIn_store_code());
		mode.addAttribute("in_store_name", assSellOutSpecial.getIn_store_name());
		mode.addAttribute("price", assSellOutSpecial.getPrice());
		mode.addAttribute("add_depre", assSellOutSpecial.getAdd_depre());
		mode.addAttribute("cur_money", assSellOutSpecial.getCur_money());
		mode.addAttribute("fore_money", assSellOutSpecial.getFore_money());
		mode.addAttribute("sell_price", assSellOutSpecial.getSell_price());
		mode.addAttribute("dispose_income", assSellOutSpecial.getDispose_income());
		mode.addAttribute("dispose_tax", assSellOutSpecial.getDispose_tax());
		mode.addAttribute("create_emp", assSellOutSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellOutSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellOutSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellOutSpecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellOutSpecial.getAudit_emp_name());
		mode.addAttribute("audit_date", assSellOutSpecial.getAudit_date());
		mode.addAttribute("state", assSellOutSpecial.getState());
		mode.addAttribute("state_name", assSellOutSpecial.getState_name());
		mode.addAttribute("note", assSellOutSpecial.getNote());
		mode.addAttribute("bus_type_code", assSellOutSpecial.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellOutSpecial.getBus_type_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asssellin/viewout";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/querySellOutDetBySellInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySellOutDetBySellInSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailSpecial = assSellOutDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailSpecial);
	}
	
	/**
	 * 专用设备 调拨入库  入库单状态查询 （打印校验数据用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellin/queryAssSellInSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//专用设备 调拨入库 入库单状态查询  （打印时校验数据专用）
		List<String> list = assSellInSpecialService.queryAssSellInSpecialState(mapVo);
		
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
