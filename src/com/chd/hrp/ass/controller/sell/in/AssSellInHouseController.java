/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.sell.in;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.NumberUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.allot.in.AssAllotInHouse;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.sell.in.AssSellInHouse;
import com.chd.hrp.ass.entity.sell.out.AssSellOutHouse;
import com.chd.hrp.ass.entity.share.AssShareDeptHouse;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.sell.in.AssSellInDetailHouseService;
import com.chd.hrp.ass.service.sell.in.AssSellInHouseService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailHouseService;
import com.chd.hrp.ass.service.sell.out.AssSellOutHouseService;
import com.chd.hrp.ass.service.share.AssShareDeptHouseService;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表（房屋及建筑物）
 * @Table:
 * ASS_SELL_IN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssSellInHouseController extends BaseController{

	private static Logger logger = Logger.getLogger(AssSellInHouseController.class);

	// 引入Service服务
	@Resource(name = "assSellInHouseService")
	private final AssSellInHouseService assSellInHouseService = null;

	@Resource(name = "assSellInDetailHouseService")
	private final AssSellInDetailHouseService assSellInDetailHouseService = null;
	
	@Resource(name = "assShareDeptHouseService")
	private final AssShareDeptHouseService assShareDeptHouseService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	@Resource(name = "assSellOutHouseService")
	private final AssSellOutHouseService assSellOutHouseService = null;

	@Resource(name = "assSellOutDetailHouseService")
	private final AssSellOutDetailHouseService assSellOutDetailHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/assSellInHouseMainPage", method = RequestMethod.GET)
	public String assSellInHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05059",MyConfig.getSysPara("05059"));
		
		return "hrp/ass/asshouse/asssellin/main";

	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/assImportSellOutHousePage", method = RequestMethod.GET)
	public String assImportSellOutHousePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		return "hrp/ass/asshouse/asssellin/importoutmain";
	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/assViewSellOutHousePage", method = RequestMethod.GET)
	public String assViewSellOutHousePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("sell_in_no", mapVo.get("sell_in_no"));
		mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asssellin/viewoutmain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/assSellInHouseAddPage", method = RequestMethod.GET)
	public String assSellInHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asssellin/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/saveAssSellInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssSellInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);
		if (assSellInHouse != null) {
			if (assSellInHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assSellInHouseJson = assSellInHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellInHouseJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/assSellInHouseUpdatePage", method = RequestMethod.GET)
	public String assSellInHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssSellInHouse assSellInHouse = new AssSellInHouse();

		assSellInHouse = assSellInHouseService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assSellInHouse.getGroup_id());
		mode.addAttribute("hos_id", assSellInHouse.getHos_id());
		mode.addAttribute("copy_code", assSellInHouse.getCopy_code());
		mode.addAttribute("sell_in_no", assSellInHouse.getSell_in_no());
		mode.addAttribute("out_group_id", assSellInHouse.getOut_group_id());
		mode.addAttribute("out_group_name", assSellInHouse.getOut_group_name());
		mode.addAttribute("out_hos_id", assSellInHouse.getOut_hos_id());
		mode.addAttribute("out_hos_name", assSellInHouse.getOut_hos_name());
		mode.addAttribute("price", assSellInHouse.getPrice());
		mode.addAttribute("add_depre", assSellInHouse.getAdd_depre());
		mode.addAttribute("cur_money", assSellInHouse.getCur_money());
		mode.addAttribute("fore_money", assSellInHouse.getFore_money());
		mode.addAttribute("create_emp", assSellInHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellInHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellInHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellInHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellInHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assSellInHouse.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("state", assSellInHouse.getState());
		mode.addAttribute("state_name", assSellInHouse.getState_name());
		mode.addAttribute("note", assSellInHouse.getNote());
		mode.addAttribute("bus_type_code", assSellInHouse.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellInHouse.getBus_type_name());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05059",MyConfig.getSysPara("05059"));
		
		return "hrp/ass/asshouse/asssellin/update";
	}

	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/updateConfirmSellInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellInMainHouse(@RequestParam(value = "ParamVo") String paramVo,
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
			//使用科室
			List<Map<String, Object>> listDept = assSellInHouseService.queryDept(mapVo);
			for(Map<String, Object> temp : listDept){
				Map<String, Object> listDeptt = new HashMap<String, Object>();
				listDeptt.put("group_id", temp.get("group_id"));

				listDeptt.put("hos_id", temp.get("hos_id"));

				listDeptt.put("copy_code", temp.get("copy_code"));

				listDeptt.put("ass_card_no", temp.get("ass_card_no"));
				
				listDeptt.put("dept_id", temp.get("dept_id"));
				
				List<AssShareDeptHouse> dept = (List<AssShareDeptHouse>)assShareDeptHouseService.queryExists(listDeptt);
				
				if(dept.size() == 0){
					return JSONObject.parseObject("{\"warn\":\"使用科室不能为空,不能入库! \"}");
				}
			}
			AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);
			if (DateUtil.compareDate(assSellInHouse.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<Map<String, Object>> list = assSellInDetailHouseService.queryByInit(mapVo);
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

				Integer use_state = assCardHouseService.queryCardExistsByAssInNo(mapExists);

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardHouseService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true, oriCardNo = true, specFlag = true, modelFlag = true, brandFlag = true,
						facFlag = true, priceFlag = true, depreFlag = true, depreMonthFlag = true, curMoneyFlag = true,
						foreMoneyFlag = true, inStoreFlag = true, busTypeFlag = true;

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

			AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);

			if (assSellInHouse.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assSellInHouseService.updateConfirm(listVo);
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

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/initAssSellInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());

		String assAllotInHouse = assSellInHouseService.initAssSellInHouse(mapVo);

		return JSONObject.parseObject(assAllotInHouse);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/deleteAssSellInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellInHouse assInHouse = assSellInHouseService.queryByCode(mapVo);

			if (assInHouse.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assSellInHouseJson = assSellInHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/queryAssSellInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assSellInHouse = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellInHouse = assSellInHouseService.query(getPage(mapVo));
		}else{
			assSellInHouse = assSellInHouseService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellInHouse);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/deleteAssSellInDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInDetailHouse(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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
			AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);

			if (assSellInHouse.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}

		String assSellInDetailHouseJson = assSellInDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellInDetailHouseJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/queryAssSellInDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellInDetailHouse = assSellInDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellInDetailHouse);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/queryAssSellInCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellInCardHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardHouseService.query(getPage(mapVo));
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

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/initAssSellInCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInCardHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_in_no", mapVo.get("sell_in_no").toString());

		String assCard = "";
		try {
			AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);

			if (assSellInHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assSellInHouseService.initAssSellInCardHouse(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/initAssSellInBatchCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssSellInBatchCardHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no",mapVo.get("sell_in_no").toString());
		
		String assCard = "";
		try {
			AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);
			
			if(assSellInHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assSellInHouseService.initAssSellInBatchCardHouse(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}

	// 查询出库单引入使用
	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/queryBySellInImport", method = RequestMethod.POST)
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

		String assSellInHouse = assSellOutHouseService.queryBySellInImport(getPage(mapVo));

		return JSONObject.parseObject(assSellInHouse);
	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/queryBySellInImportView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBySellInImportView(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assSellInHouse = assSellOutHouseService.queryBySellInImportView(getPage(mapVo));

		return JSONObject.parseObject(assSellInHouse);
	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/deleteAssSellInCardHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellInCardHouse(@RequestParam(value = "ParamVo") String paramVo,
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
			AssSellInHouse assSellInHouse = assSellInHouseService.queryByCode(mapVo);

			if (assSellInHouse.getState() > 0) {
				flag = false;
				break;
			}

			AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
			if (assCardHouse.getUse_state() > 0) {
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
			assCardGeneralJson = assCardHouseService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/assSellOutHouseViewPage", method = RequestMethod.GET)
	public String assSellOutHouseViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssSellOutHouse assSellOutHouse = new AssSellOutHouse();

		assSellOutHouse = assSellOutHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assSellOutHouse.getGroup_id());
		mode.addAttribute("hos_id", assSellOutHouse.getHos_id());
		mode.addAttribute("copy_code", assSellOutHouse.getCopy_code());
		mode.addAttribute("sell_out_no", assSellOutHouse.getSell_out_no());
		mode.addAttribute("in_group_id", assSellOutHouse.getIn_group_id());
		mode.addAttribute("in_group_name", assSellOutHouse.getIn_group_name());
		mode.addAttribute("in_hos_id", assSellOutHouse.getIn_hos_id());
		mode.addAttribute("in_hos_name", assSellOutHouse.getIn_hos_name());
		mode.addAttribute("price", assSellOutHouse.getPrice());
		mode.addAttribute("add_depre", assSellOutHouse.getAdd_depre());
		mode.addAttribute("cur_money", assSellOutHouse.getCur_money());
		mode.addAttribute("fore_money", assSellOutHouse.getFore_money());
		mode.addAttribute("sell_price", assSellOutHouse.getSell_price());
		mode.addAttribute("dispose_income", assSellOutHouse.getDispose_income());
		mode.addAttribute("dispose_tax", assSellOutHouse.getDispose_tax());
		mode.addAttribute("create_emp", assSellOutHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellOutHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellOutHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellOutHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellOutHouse.getAudit_emp_name());
		mode.addAttribute("audit_date", assSellOutHouse.getAudit_date());
		mode.addAttribute("state", assSellOutHouse.getState());
		mode.addAttribute("state_name", assSellOutHouse.getState_name());
		mode.addAttribute("note", assSellOutHouse.getNote());
		mode.addAttribute("bus_type_code", assSellOutHouse.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellOutHouse.getBus_type_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asssellin/viewout";
	}

	@RequestMapping(value = "/hrp/ass/asshouse/asssellin/querySellOutDetBySellInHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySellOutDetBySellInHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailHouse = assSellOutDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailHouse);
	}
	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/asssellin/queryAssSellInHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssSellInHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assSellInHouseService.queryAssSellInHouseStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"调拨单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}
}

