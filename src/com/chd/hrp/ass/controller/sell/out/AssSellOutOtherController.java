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
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailOther;
import com.chd.hrp.ass.entity.sell.out.AssSellOutOther;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailOtherService;
import com.chd.hrp.ass.service.sell.out.AssSellOutOtherService;

/**
 * 
 * @Description: 050901 资产有偿调拨出库单主表(其他固定资产)
 * @Table: ASS_SELL_OUT_OTHER
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellOutOtherController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellOutOtherController.class);

	// 引入Service服务
	@Resource(name = "assSellOutOtherService")
	private final AssSellOutOtherService assSellOutOtherService = null;

	@Resource(name = "assSellOutDetailOtherService")
	private final AssSellOutDetailOtherService assSellOutDetailOtherService = null;
	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/assSellOutOtherMainPage", method = RequestMethod.GET)
	public String assSellOutOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asssellout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/assSellOutOtherAddPage", method = RequestMethod.GET)
	public String assSellOutOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asssellout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/saveAssSellOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssSellOutOther(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellOutOther assBackOther = assSellOutOtherService.queryByCode(mapVo);
		if (assBackOther != null) {
			if (assBackOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assSellOutOtherJson = assSellOutOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellOutOtherJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/updateConfirmSellOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellOutOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutOther assSellOutOther = assSellOutOtherService.queryByCode(mapVo);
			if (DateUtil.compareDate(assSellOutOther.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssSellOutDetailOther> list = assSellOutDetailOtherService.queryByAssSellOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssSellOutDetailOther assBackDetailOther : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("dispose_type", 32);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_income", assBackDetailOther.getDispose_income() == null ? "0"
							: assBackDetailOther.getDispose_income());
					listCardMap.put("dispose_tax",
							assBackDetailOther.getDispose_tax() == null ? "0" : assBackDetailOther.getDispose_tax());
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					AssCardOther assCardOther = assCardOtherService.queryByCode(listCardMap);
					if (assCardOther == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardOther.getDispose_type() != null && 32 == assCardOther.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailOther.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assSellOutOther.getState() == 2) {
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
			assInMainJson = assSellOutOtherService.updateSellOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/assSellOutOtherUpdatePage", method = RequestMethod.GET)
	public String assSellOutOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asssellout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/deleteAssSellOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutOther assSellOutOther = assSellOutOtherService.queryByCode(mapVo);
			if (assSellOutOther != null) {
				if (assSellOutOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assSellOutOtherJson = assSellOutOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/queryAssSellOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutOther = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellOutOther = assSellOutOtherService.query(getPage(mapVo));
		}else{
			assSellOutOther = assSellOutOtherService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellOutOther);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/deleteAssSellOutDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_card_no", ids[4]);
			AssSellOutOther assSellOutOther = assSellOutOtherService.queryByCode(mapVo);
			if (assSellOutOther != null) {
				if (assSellOutOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assSellOutDetailOtherJson = assSellOutDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/queryAssSellOutDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailOther = assSellOutDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailOther);

	}

	/**
	 * 其他固定资产 资产调拨出库 出库单状态查询 （ 打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asssellout/queryAssSellOutOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutOtherState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 其他固定资产 资产调拨出库 出库单状态查询 （打印时校验数据专用）
		List<String> list = assSellOutOtherService.queryAssSellOutOtherState(mapVo);

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
