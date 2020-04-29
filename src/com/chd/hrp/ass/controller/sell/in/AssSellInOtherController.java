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
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.sell.in.AssSellInOther;
import com.chd.hrp.ass.entity.sell.out.AssSellOutOther;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailOtherService;
import com.chd.hrp.ass.service.sell.in.AssSellInOtherService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailOtherService;
import com.chd.hrp.ass.service.sell.out.AssSellOutOtherService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表(其他固定资产)
 * @Table:
 * ASS_SELL_IN_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssSellInOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssSellInOtherController.class);

	// 引入Service服务
	@Resource(name = "assSellInOtherService")
	private final AssSellInOtherService assSellInOtherService = null;

	@Resource(name = "assSellInDetailOtherService")
	private final AssSellInDetailOtherService assSellInDetailOtherService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	@Resource(name = "assSellOutOtherService")
	private final AssSellOutOtherService assSellOutOtherService = null;
	
	@Resource(name = "assSellOutDetailOtherService")
	private final AssSellOutDetailOtherService assSellOutDetailOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/assSellInOtherMainPage", method = RequestMethod.GET)
	public String assSellInOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asssellin/main";

	}

	@RequestMapping(value = "/hrp/ass/assother/asssellin/assImportSellOutOtherPage", method = RequestMethod.GET)
	public String assImportSellOutOtherPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		return "hrp/ass/assother/asssellin/importoutmain";
	}

	@RequestMapping(value = "/hrp/ass/assother/asssellin/assViewSellOutOtherPage", method = RequestMethod.GET)
	public String assViewSellOutOtherPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("sell_in_no", mapVo.get("sell_in_no"));
		mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asssellin/viewoutmain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/assSellInOtherAddPage", method = RequestMethod.GET)
	public String assSellInOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asssellin/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨入库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/saveAssSellInOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssSellInOther(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);
		if (assSellInOther != null) {
			if (assSellInOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assSellInOtherJson = assSellInOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellInOtherJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/assSellInOtherUpdatePage", method = RequestMethod.GET)
	public String assSellInOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssSellInOther assSellInOther = new AssSellInOther();

		assSellInOther = assSellInOtherService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assSellInOther.getGroup_id());
		mode.addAttribute("hos_id", assSellInOther.getHos_id());
		mode.addAttribute("copy_code", assSellInOther.getCopy_code());
		mode.addAttribute("sell_in_no", assSellInOther.getSell_in_no());
		mode.addAttribute("in_store_id", assSellInOther.getIn_store_id());
		mode.addAttribute("in_store_no", assSellInOther.getIn_store_no());
		mode.addAttribute("in_store_code", assSellInOther.getIn_store_code());
		mode.addAttribute("in_store_name", assSellInOther.getIn_store_name());
		mode.addAttribute("out_group_id", assSellInOther.getOut_group_id());
		mode.addAttribute("out_group_name", assSellInOther.getOut_group_name());
		mode.addAttribute("out_hos_id", assSellInOther.getOut_hos_id());
		mode.addAttribute("out_hos_name", assSellInOther.getOut_hos_name());
		mode.addAttribute("out_store_id", assSellInOther.getOut_store_id());
		mode.addAttribute("out_store_no", assSellInOther.getOut_store_no());
		mode.addAttribute("out_store_code", assSellInOther.getOut_store_code());
		mode.addAttribute("out_store_name", assSellInOther.getOut_store_name());
		mode.addAttribute("price", assSellInOther.getPrice());
		mode.addAttribute("add_depre", assSellInOther.getAdd_depre());
		mode.addAttribute("cur_money", assSellInOther.getCur_money());
		mode.addAttribute("fore_money", assSellInOther.getFore_money());
		mode.addAttribute("create_emp", assSellInOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellInOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellInOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellInOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellInOther.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assSellInOther.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assSellInOther.getState());
		mode.addAttribute("state_name", assSellInOther.getState_name());
		mode.addAttribute("note", assSellInOther.getNote());
		mode.addAttribute("bus_type_code", assSellInOther.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellInOther.getBus_type_name());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asssellin/update";
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/updateConfirmSellInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellInMainOther(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);
			if(DateUtil.compareDate(assSellInOther.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<Map<String, Object>> list = assSellInDetailOtherService.queryByInit(mapVo);
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

				Integer use_state = assCardOtherService.queryCardExistsByAssInNo(mapExists);

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardOtherService.queryAssCardByAssInNo(mapExists);
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

			AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);

			if (assSellInOther.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assSellInOtherService.updateConfirm(listVo);
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
	
	@RequestMapping(value = "/hrp/ass/assother/asssellin/initAssSellInOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInOther = assSellInOtherService.initAssSellInOther(mapVo);

		return JSONObject.parseObject(assAllotInOther);

	}
	

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/deleteAssSellInOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellInOther assInOther = assSellInOtherService.queryByCode(mapVo);

			if (assInOther.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assSellInOtherJson = assSellInOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/queryAssSellInOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assSellInOther = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellInOther = assSellInOtherService.query(getPage(mapVo));
		}else{
			assSellInOther = assSellInOtherService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellInOther);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/deleteAssSellInDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);

			if (assSellInOther.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}

		String assSellInDetailOtherJson = assSellInDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/queryAssSellInDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellInDetailOther = assSellInDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellInDetailOther);

	}

	@RequestMapping(value = "/hrp/ass/assother/asssellin/queryAssSellInCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardOtherService.query(getPage(mapVo));
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

	@RequestMapping(value = "/hrp/ass/assother/asssellin/initAssSellInCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);

			if (assSellInOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInOtherService.initAssSellInCardOther(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assother/asssellin/initAssSellInBatchCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInBatchCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);

			if (assSellInOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInOtherService.initAssSellInBatchCardOther(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}

	// 查询出库单引入使用
	@RequestMapping(value = "/hrp/ass/assother/asssellin/queryBySellInImport", method = RequestMethod.POST)
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

		String assSellInOther = assSellOutOtherService.queryBySellInImport(getPage(mapVo));

		return JSONObject.parseObject(assSellInOther);
	}
	
	@RequestMapping(value = "/hrp/ass/assother/asssellin/queryBySellInImportView", method = RequestMethod.POST)
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
		
		String assSellInOther = assSellOutOtherService.queryBySellInImportView(getPage(mapVo));

		return JSONObject.parseObject(assSellInOther);
	}

	@RequestMapping(value = "/hrp/ass/assother/asssellin/deleteAssSellInCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInCardOther(@RequestParam(value = "ParamVo") String paramVo,
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
			AssSellInOther assSellInOther = assSellInOtherService.queryByCode(mapVo);

			if (assSellInOther.getState() > 0) {
				flag = false;
				break;
			}

			AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
			if (assCardOther.getUse_state() > 0) {
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
			assCardGeneralJson = assCardOtherService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	@RequestMapping(value = "/hrp/ass/assother/asssellin/assSellOutOtherViewPage", method = RequestMethod.GET)
	public String assSellOutOtherViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssSellOutOther assSellOutOther = new AssSellOutOther();

		assSellOutOther = assSellOutOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assSellOutOther.getGroup_id());
		mode.addAttribute("hos_id", assSellOutOther.getHos_id());
		mode.addAttribute("copy_code", assSellOutOther.getCopy_code());
		mode.addAttribute("sell_out_no", assSellOutOther.getSell_out_no());
		mode.addAttribute("out_store_id", assSellOutOther.getOut_store_id());
		mode.addAttribute("out_store_no", assSellOutOther.getOut_store_no());
		mode.addAttribute("out_store_code", assSellOutOther.getOut_store_code());
		mode.addAttribute("out_store_name", assSellOutOther.getOut_store_name());
		mode.addAttribute("in_group_id", assSellOutOther.getIn_group_id());
		mode.addAttribute("in_group_name", assSellOutOther.getIn_group_name());
		mode.addAttribute("in_hos_id", assSellOutOther.getIn_hos_id());
		mode.addAttribute("in_hos_name", assSellOutOther.getIn_hos_name());
		mode.addAttribute("in_store_id", assSellOutOther.getIn_store_id());
		mode.addAttribute("in_store_no", assSellOutOther.getIn_store_no());
		mode.addAttribute("in_store_code", assSellOutOther.getIn_store_code());
		mode.addAttribute("in_store_name", assSellOutOther.getIn_store_name());
		mode.addAttribute("price", assSellOutOther.getPrice());
		mode.addAttribute("add_depre", assSellOutOther.getAdd_depre());
		mode.addAttribute("cur_money", assSellOutOther.getCur_money());
		mode.addAttribute("fore_money", assSellOutOther.getFore_money());
		mode.addAttribute("sell_price", assSellOutOther.getSell_price());
		mode.addAttribute("dispose_income", assSellOutOther.getDispose_income());
		mode.addAttribute("dispose_tax", assSellOutOther.getDispose_tax());
		mode.addAttribute("create_emp", assSellOutOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellOutOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellOutOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellOutOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellOutOther.getAudit_emp_name());
		mode.addAttribute("audit_date", assSellOutOther.getAudit_date());
		mode.addAttribute("state", assSellOutOther.getState());
		mode.addAttribute("state_name", assSellOutOther.getState_name());
		mode.addAttribute("note", assSellOutOther.getNote());
		mode.addAttribute("bus_type_code", assSellOutOther.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellOutOther.getBus_type_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asssellin/viewout";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/asssellin/querySellOutDetBySellInOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySellOutDetBySellInOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailOther = assSellOutDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailOther);
	}
	
	/**
	 * 其他固定资产 调拨入库  入库单状态查询 （打印校验数据用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellin/queryAssSellInOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInOtherState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//其他固定资产 调拨入库 入库单状态查询  （打印时校验数据专用）
		List<String> list = assSellInOtherService.queryAssSellInOtherState(mapVo);
		
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

