/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.sell.out;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailGeneral;
import com.chd.hrp.ass.entity.sell.out.AssSellOutGeneral;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailGeneralService;
import com.chd.hrp.ass.service.sell.out.AssSellOutGeneralService;

/**
 * 
 * @Description: 050901 资产有偿调拨出库单主表（一般设备）
 * @Table: ASS_SELL_OUT_GENERAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellOutGeneralController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellOutGeneralController.class);

	// 引入Service服务
	@Resource(name = "assSellOutGeneralService")
	private final AssSellOutGeneralService assSellOutGeneralService = null;

	@Resource(name = "assSellOutDetailGeneralService")
	private final AssSellOutDetailGeneralService assSellOutDetailGeneralService = null;
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/assSellOutGeneralMainPage", method = RequestMethod.GET)
	public String assSellOutGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05029",MyConfig.getSysPara("05029"));
		
		return "hrp/ass/assgeneral/asssellout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/assSellOutGeneralAddPage", method = RequestMethod.GET)
	public String assSellOutGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asssellout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/saveAssSellOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssSellOutGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellOutGeneral assBackGeneral = assSellOutGeneralService.queryByCode(mapVo);
		if (assBackGeneral != null) {
			if (assBackGeneral.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assSellOutGeneralJson = assSellOutGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellOutGeneralJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/updateConfirmSellOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellOutGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("sell_out_no", ids[3]);

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_emp", SessionManager.getUserId());

			AssSellOutGeneral assSellOutGeneral = assSellOutGeneralService.queryByCode(mapVo);
			if (DateUtil.compareDate(assSellOutGeneral.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssSellOutDetailGeneral> list = assSellOutDetailGeneralService.queryByAssSellOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssSellOutDetailGeneral assBackDetailGeneral : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assBackDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assBackDetailGeneral.getCopy_code());
					listCardMap.put("dispose_type", 32);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_income", assBackDetailGeneral.getDispose_income() == null ? "0"
							: assBackDetailGeneral.getDispose_income());
					listCardMap.put("dispose_tax", assBackDetailGeneral.getDispose_tax() == null ? "0"
							: assBackDetailGeneral.getDispose_tax());
					listCardMap.put("ass_card_no", assBackDetailGeneral.getAss_card_no());
					AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(listCardMap);
					if (assCardGeneral == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardGeneral.getDispose_type() != null && 32 == assCardGeneral.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailGeneral.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailGeneral.getAss_card_no(), assBackDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assSellOutGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}

		try {
			assInMainJson = assSellOutGeneralService.updateSellOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/assSellOutGeneralUpdatePage", method = RequestMethod.GET)
	public String assSellOutGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mode.addAttribute("ass_05029",MyConfig.getSysPara("05029"));
		
		return "hrp/ass/assgeneral/asssellout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/deleteAssSellOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("sell_out_no", ids[3]);

			AssSellOutGeneral assSellOutGeneral = assSellOutGeneralService.queryByCode(mapVo);
			if (assSellOutGeneral != null) {
				if (assSellOutGeneral.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assSellOutGeneralJson = assSellOutGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/queryAssSellOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutGeneral = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellOutGeneral = assSellOutGeneralService.query(getPage(mapVo));
		}else{
			assSellOutGeneral = assSellOutGeneralService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellOutGeneral);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/deleteAssSellOutDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutDetailGeneral(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("Sell_out_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssSellOutGeneral assSellOutGeneral = assSellOutGeneralService.queryByCode(mapVo);
			if (assSellOutGeneral != null) {
				if (assSellOutGeneral.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assSellOutDetailGeneralJson = assSellOutDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/queryAssSellOutDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailGeneral = assSellOutDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailGeneral);

	}

	/**
	 * 一般设备 资产调拨出库 出库单状态查询 （ 打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asssellout/queryAssSellOutGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 一般设备 资产调拨出库 出库单状态查询 （打印时校验数据专用）
		List<String> list = assSellOutGeneralService.queryAssSellOutGeneralState(mapVo);

		if (list.size() == 0) {

			return JSONObject.parseObject("{\"state\":\"true\"}");

		} else {

			String str = "";
			for (String item : list) {

				str += item + ",";
			}

			return JSONObject.parseObject("{\"error\":\"出库单【" + str + "】不是确认状态不能打印.\",\"state\":\"false\"}");
		}

	}
}
