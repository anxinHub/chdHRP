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
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.sell.in.AssSellInInassets;
import com.chd.hrp.ass.entity.sell.in.AssSellInInassets;
import com.chd.hrp.ass.entity.sell.in.AssSellInSpecial;
import com.chd.hrp.ass.entity.sell.out.AssSellOutInassets;
import com.chd.hrp.ass.entity.sell.out.AssSellOutInassets;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailInassetsService;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailInassetsService;
import com.chd.hrp.ass.service.sell.in.AssSellInInassetsService;
import com.chd.hrp.ass.service.sell.in.AssSellInInassetsService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailInassetsService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailInassetsService;
import com.chd.hrp.ass.service.sell.out.AssSellOutInassetsService;
import com.chd.hrp.ass.service.sell.out.AssSellOutInassetsService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表(无形资产)
 * @Table:
 * ASS_SELL_IN_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssSellInInassetsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssSellInInassetsController.class);
	
	// 引入Service服务
		@Resource(name = "assSellInInassetsService")
		private final AssSellInInassetsService assSellInInassetsService = null;

		@Resource(name = "assSellInDetailInassetsService")
		private final AssSellInDetailInassetsService assSellInDetailInassetsService = null;

		@Resource(name = "assCardInassetsService")
		private final AssCardInassetsService assCardInassetsService = null;

		@Resource(name = "assSellOutInassetsService")
		private final AssSellOutInassetsService assSellOutInassetsService = null;
		
		@Resource(name = "assSellOutDetailInassetsService")
		private final AssSellOutDetailInassetsService assSellOutDetailInassetsService = null;

		/**
		 * @Description 主页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/assSellInInassetsMainPage", method = RequestMethod.GET)
		public String assSellInInassetsMainPage(Model mode) throws Exception {

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			mode.addAttribute("ass_05049",MyConfig.getSysPara("05049"));
			
			return "hrp/ass/assinassets/asssellin/main";

		}

		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/assImportSellOutInassetsPage", method = RequestMethod.GET)
		public String assImportSellOutInassetsPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			
			return "hrp/ass/assinassets/asssellin/importoutmain";
		}

		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/assViewSellOutInassetsPage", method = RequestMethod.GET)
		public String assViewSellOutInassetsPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
			mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
			mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
			mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
			mode.addAttribute("sell_in_no", mapVo.get("sell_in_no"));
			mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/asssellin/viewoutmain";
		}

		/**
		 * @Description 添加页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/assSellInInassetsAddPage", method = RequestMethod.GET)
		public String assSellInInassetsAddPage(Model mode) throws Exception {

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/asssellin/add";

		}

		/**
		 * @Description 添加数据 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/saveAssSellInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssSellInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

			AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);
			if (assSellInInassets != null) {
				if (assSellInInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
			}

			String assSellInInassetsJson = assSellInInassetsService.addOrUpdate(mapVo);

			return JSONObject.parseObject(assSellInInassetsJson);

		}

		/**
		 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/assSellInInassetsUpdatePage", method = RequestMethod.GET)
		public String assSellInInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			AssSellInInassets assSellInInassets = new AssSellInInassets();

			assSellInInassets = assSellInInassetsService.queryByCode(mapVo);
			mode.addAttribute("is_import", mapVo.get("is_import"));
			mode.addAttribute("group_id", assSellInInassets.getGroup_id());
			mode.addAttribute("hos_id", assSellInInassets.getHos_id());
			mode.addAttribute("copy_code", assSellInInassets.getCopy_code());
			mode.addAttribute("sell_in_no", assSellInInassets.getSell_in_no());
			mode.addAttribute("in_store_id", assSellInInassets.getIn_store_id());
			mode.addAttribute("in_store_no", assSellInInassets.getIn_store_no());
			mode.addAttribute("in_store_code", assSellInInassets.getIn_store_code());
			mode.addAttribute("in_store_name", assSellInInassets.getIn_store_name());
			mode.addAttribute("out_group_id", assSellInInassets.getOut_group_id());
			mode.addAttribute("out_group_name", assSellInInassets.getOut_group_name());
			mode.addAttribute("out_hos_id", assSellInInassets.getOut_hos_id());
			mode.addAttribute("out_hos_name", assSellInInassets.getOut_hos_name());
			mode.addAttribute("out_store_id", assSellInInassets.getOut_store_id());
			mode.addAttribute("out_store_no", assSellInInassets.getOut_store_no());
			mode.addAttribute("out_store_code", assSellInInassets.getOut_store_code());
			mode.addAttribute("out_store_name", assSellInInassets.getOut_store_name());
			mode.addAttribute("price", assSellInInassets.getPrice());
			mode.addAttribute("add_depre", assSellInInassets.getAdd_depre());
			mode.addAttribute("cur_money", assSellInInassets.getCur_money());
			mode.addAttribute("fore_money", assSellInInassets.getFore_money());
			mode.addAttribute("create_emp", assSellInInassets.getCreate_emp());
			mode.addAttribute("create_emp_name", assSellInInassets.getCreate_emp_name());
			mode.addAttribute("create_date", DateUtil.dateToString(assSellInInassets.getCreate_date(), "yyyy-MM-dd"));
			mode.addAttribute("audit_emp", assSellInInassets.getAudit_emp());
			mode.addAttribute("audit_emp_name", assSellInInassets.getAudit_emp_name());
			mode.addAttribute("audit_date", DateUtil.dateToString(assSellInInassets.getAudit_date(), "yyyy-MM-dd"));
			mode.addAttribute("state", assSellInInassets.getState());
			mode.addAttribute("state_name", assSellInInassets.getState_name());
			mode.addAttribute("note", assSellInInassets.getNote());
			mode.addAttribute("bus_type_code", assSellInInassets.getBus_type_code());
			mode.addAttribute("bus_type_name", assSellInInassets.getBus_type_name());

			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05049",MyConfig.getSysPara("05049"));
			
			return "hrp/ass/assinassets/asssellin/update";
		}

		/**
		 * @Description 入库确认
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/updateConfirmSellInMainInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmSellInMainInassets(@RequestParam(value = "ParamVo") String paramVo,
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
				
				AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);
				if(DateUtil.compareDate(assSellInInassets.getCreate_date(), new Date())){
					return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
				}

				List<Map<String, Object>> list = assSellInDetailInassetsService.queryByInit(mapVo);
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

					Integer use_state = assCardInassetsService.queryCardExistsByAssInNo(mapExists);

					if (use_state == 0) {
						return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
					}

					List<Map<String, Object>> cardList = assCardInassetsService.queryAssCardByAssInNo(mapExists);
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

				AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);

				if (assSellInInassets.getState() == 2) {
					continue;
				} else {
					listVo.add(mapVo);
				}

			}

			if (listVo.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
			}

			try {
				assInMainJson = assSellInInassetsService.updateConfirm(listVo);
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
		
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/initAssSellInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> initAssSellInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInInassets = assSellInInassetsService.initAssSellInInassets(mapVo);

			return JSONObject.parseObject(assAllotInInassets);

		}
		

		/**
		 * @Description 删除数据 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/deleteAssSellInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssSellInInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

				AssSellInInassets assInInassets = assSellInInassetsService.queryByCode(mapVo);

				if (assInInassets.getState() > 0) {
					flag = false;
					break;
				}

				listVo.add(mapVo);

			}

			if (!flag) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
			}

			String assSellInInassetsJson = assSellInInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assSellInInassetsJson);

		}

		/**
		 * @Description 查询数据 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/queryAssSellInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssSellInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String assSellInInassets = null;
			if("0".equals(mapVo.get("show_detail").toString())){
				assSellInInassets = assSellInInassetsService.query(getPage(mapVo));
			}else{
				assSellInInassets = assSellInInassetsService.queryDetails(getPage(mapVo));
			}

			return JSONObject.parseObject(assSellInInassets);

		}

		/**
		 * @Description 删除数据 050901 资产无偿调拨入库明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/deleteAssSellInDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssSellInDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
				AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);

				if (assSellInInassets.getState() > 0) {
					flag = false;
					break;
				}

				listVo.add(mapVo);

			}

			if (!flag) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			String assSellInDetailInassetsJson = assSellInDetailInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assSellInDetailInassetsJson);

		}

		/**
		 * @Description 查询数据 050901 资产无偿调拨入库明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/queryAssSellInDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssSellInDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assSellInDetailInassets = assSellInDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assSellInDetailInassets);

		}

		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/queryAssSellInCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssSellInCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assCard = "";
			assCard = assCardInassetsService.query(getPage(mapVo));
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

		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/initAssSellInCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> initAssSellInCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

			String assCard = "";
			try {
				AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);

				if (assSellInInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}

				assCard = assSellInInassetsService.initAssSellInCardInassets(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assCard);
		}

		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/initAssSellInBatchCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> initAssSellInBatchCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

			String assCard = "";
			try {
				AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);

				if (assSellInInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}

				assCard = assSellInInassetsService.initAssSellInBatchCardInassets(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assCard);
		}
		
		// 查询出库单引入使用
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/queryBySellInImport", method = RequestMethod.POST)
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

			String assSellInInassets = assSellOutInassetsService.queryBySellInImport(getPage(mapVo));

			return JSONObject.parseObject(assSellInInassets);
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/queryBySellInImportView", method = RequestMethod.POST)
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
			
			String assSellInInassets = assSellOutInassetsService.queryBySellInImportView(getPage(mapVo));

			return JSONObject.parseObject(assSellInInassets);
		}

		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/deleteAssSellInCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssSellInCardInassets(@RequestParam(value = "ParamVo") String paramVo,
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
				AssSellInInassets assSellInInassets = assSellInInassetsService.queryByCode(mapVo);

				if (assSellInInassets.getState() > 0) {
					flag = false;
					break;
				}

				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if (assCardInassets.getUse_state() > 0) {
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
				assCardGeneralJson = assCardInassetsService.deleteBatch(listVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
			return JSONObject.parseObject(assCardGeneralJson);

		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/assSellOutInassetsViewPage", method = RequestMethod.GET)
		public String assSellOutInassetsViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			AssSellOutInassets assSellOutInassets = new AssSellOutInassets();

			assSellOutInassets = assSellOutInassetsService.queryByCode(mapVo);

			mode.addAttribute("group_id", assSellOutInassets.getGroup_id());
			mode.addAttribute("hos_id", assSellOutInassets.getHos_id());
			mode.addAttribute("copy_code", assSellOutInassets.getCopy_code());
			mode.addAttribute("sell_out_no", assSellOutInassets.getSell_out_no());
			mode.addAttribute("out_store_id", assSellOutInassets.getOut_store_id());
			mode.addAttribute("out_store_no", assSellOutInassets.getOut_store_no());
			mode.addAttribute("out_store_code", assSellOutInassets.getOut_store_code());
			mode.addAttribute("out_store_name", assSellOutInassets.getOut_store_name());
			mode.addAttribute("in_group_id", assSellOutInassets.getIn_group_id());
			mode.addAttribute("in_group_name", assSellOutInassets.getIn_group_name());
			mode.addAttribute("in_hos_id", assSellOutInassets.getIn_hos_id());
			mode.addAttribute("in_hos_name", assSellOutInassets.getIn_hos_name());
			mode.addAttribute("in_store_id", assSellOutInassets.getIn_store_id());
			mode.addAttribute("in_store_no", assSellOutInassets.getIn_store_no());
			mode.addAttribute("in_store_code", assSellOutInassets.getIn_store_code());
			mode.addAttribute("in_store_name", assSellOutInassets.getIn_store_name());
			mode.addAttribute("price", assSellOutInassets.getPrice());
			mode.addAttribute("add_depre", assSellOutInassets.getAdd_depre());
			mode.addAttribute("cur_money", assSellOutInassets.getCur_money());
			mode.addAttribute("fore_money", assSellOutInassets.getFore_money());
			mode.addAttribute("sell_price", assSellOutInassets.getSell_price());
			mode.addAttribute("dispose_income", assSellOutInassets.getDispose_income());
			mode.addAttribute("dispose_tax", assSellOutInassets.getDispose_tax());
			mode.addAttribute("create_emp", assSellOutInassets.getCreate_emp());
			mode.addAttribute("create_emp_name", assSellOutInassets.getCreate_emp_name());
			mode.addAttribute("create_date", DateUtil.dateToString(assSellOutInassets.getCreate_date(), "yyyy-MM-dd"));
			mode.addAttribute("audit_emp", assSellOutInassets.getAudit_emp());
			mode.addAttribute("audit_emp_name", assSellOutInassets.getAudit_emp_name());
			mode.addAttribute("audit_date", assSellOutInassets.getAudit_date());
			mode.addAttribute("state", assSellOutInassets.getState());
			mode.addAttribute("state_name", assSellOutInassets.getState_name());
			mode.addAttribute("note", assSellOutInassets.getNote());
			mode.addAttribute("bus_type_code", assSellOutInassets.getBus_type_code());
			mode.addAttribute("bus_type_name", assSellOutInassets.getBus_type_name());
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/asssellin/viewout";
		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/querySellOutDetBySellInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> querySellOutDetBySellInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assSellOutDetailInassets = assSellOutDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assSellOutDetailInassets);
		}
		
		/**
		 * 其他无形资产 调拨入库  入库单状态查询 （打印校验数据用）
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asssellin/queryAssSellInInassetsState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssSellInInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//其他无形资产 调拨入库 入库单状态查询  （打印时校验数据专用）
			List<String> list = assSellInInassetsService.queryAssSellInInassetsState(mapVo);
			
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

