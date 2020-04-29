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
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailSpecial;
import com.chd.hrp.ass.entity.sell.out.AssSellOutSpecial;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailSpecialService;
import com.chd.hrp.ass.service.sell.out.AssSellOutSpecialService;

/**
 * 
 * @Description: 050901 资产有偿调拨出库单主表（专用设备）
 * @Table: ASS_SELL_OUT_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellOutSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellOutSpecialController.class);

	// 引入Service服务
	@Resource(name = "assSellOutSpecialService")
	private final AssSellOutSpecialService assSellOutSpecialService = null;

	@Resource(name = "assSellOutDetailSpecialService")
	private final AssSellOutDetailSpecialService assSellOutDetailSpecialService = null;
	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/assSellOutSpecialMainPage", method = RequestMethod.GET)
	public String assSellOutSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asssellout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/assSellOutSpecialAddPage", method = RequestMethod.GET)
	public String assSellOutSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asssellout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/saveAssSellOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssSellOutSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellOutSpecial assBackSpecial = assSellOutSpecialService.queryByCode(mapVo);
		if (assBackSpecial != null) {
			if (assBackSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assSellOutSpecialJson = assSellOutSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellOutSpecialJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/updateConfirmSellOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellOutSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutSpecial assSellOutSpecial = assSellOutSpecialService.queryByCode(mapVo);
			if (assSellOutSpecial.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assSellOutSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssSellOutDetailSpecial> list = assSellOutDetailSpecialService.queryByAssSellOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssSellOutDetailSpecial assBackDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("dispose_type", 32);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_income", assBackDetailSpecial.getDispose_income() == null ? "0"
							: assBackDetailSpecial.getDispose_income());
					listCardMap.put("dispose_tax", assBackDetailSpecial.getDispose_tax() == null ? "0"
							: assBackDetailSpecial.getDispose_tax());
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					AssCardSpecial assCardSpecial = assCardSpecialService.queryByCode(listCardMap);
					if (assCardSpecial == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardSpecial.getDispose_type() != null && 32 == assCardSpecial.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}

		try {
			assInMainJson = assSellOutSpecialService.updateSellOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/assSellOutSpecialUpdatePage", method = RequestMethod.GET)
	public String assSellOutSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asssellout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/deleteAssSellOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutSpecial assSellOutSpecial = assSellOutSpecialService.queryByCode(mapVo);
			if (assSellOutSpecial != null) {
				if (assSellOutSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assSellOutSpecialJson = assSellOutSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/queryAssSellOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutSpecial = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellOutSpecial = assSellOutSpecialService.query(getPage(mapVo));
		}else{
			assSellOutSpecial = assSellOutSpecialService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellOutSpecial);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/deleteAssSellOutDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutDetailSpecial(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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
			AssSellOutSpecial assSellOutSpecial = assSellOutSpecialService.queryByCode(mapVo);
			if (assSellOutSpecial != null) {
				if (assSellOutSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assSellOutDetailSpecialJson = assSellOutDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/queryAssSellOutDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailSpecial = assSellOutDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailSpecial);

	}

	/**
	 * 专用设备 资产调拨出库 出库单状态查询 （ 打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asssellout/queryAssSellOutSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 专用设备 资产调拨出库 出库单状态查询 （打印时校验数据专用）
		List<String> list = assSellOutSpecialService.queryAssSellOutSpecialState(mapVo);

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
