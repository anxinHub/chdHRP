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
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailLand;
import com.chd.hrp.ass.entity.sell.out.AssSellOutLand;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailLandService;
import com.chd.hrp.ass.service.sell.out.AssSellOutLandService;

/**
 * 
 * @Description: 050901 资产有偿调拨出库单主表(土地)
 * @Table: ASS_SELL_OUT_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellOutLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellOutLandController.class);

	// 引入Service服务
	@Resource(name = "assSellOutLandService")
	private final AssSellOutLandService assSellOutLandService = null;

	@Resource(name = "assSellOutDetailLandService")
	private final AssSellOutDetailLandService assSellOutDetailLandService = null;
	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/assSellOutLandMainPage", method = RequestMethod.GET)
	public String assSellOutLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05069",MyConfig.getSysPara("05069"));
		
		return "hrp/ass/assland/asssellout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/assSellOutLandAddPage", method = RequestMethod.GET)
	public String assSellOutLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asssellout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/saveAssSellOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssSellOutLand(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellOutLand assBackLand = assSellOutLandService.queryByCode(mapVo);
		if (assBackLand != null) {
			if (assBackLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assSellOutLandJson = assSellOutLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellOutLandJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/updateConfirmSellOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellOutLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutLand assSellOutLand = assSellOutLandService.queryByCode(mapVo);
			if (DateUtil.compareDate(assSellOutLand.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssSellOutDetailLand> list = assSellOutDetailLandService.queryByAssSellOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssSellOutDetailLand assBackDetailLand : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailLand.getGroup_id());
					listCardMap.put("hos_id", assBackDetailLand.getHos_id());
					listCardMap.put("copy_code", assBackDetailLand.getCopy_code());
					listCardMap.put("dispose_type", 32);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_income", assBackDetailLand.getDispose_income() == null ? "0"
							: assBackDetailLand.getDispose_income());
					listCardMap.put("dispose_tax",
							assBackDetailLand.getDispose_tax() == null ? "0" : assBackDetailLand.getDispose_tax());
					listCardMap.put("ass_card_no", assBackDetailLand.getAss_card_no());
					AssCardLand assCardLand = assCardLandService.queryByCode(listCardMap);
					if (assCardLand == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardLand.getDispose_type() != null && 32 == assCardLand.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailLand.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailLand.getAss_card_no(), assBackDetailLand.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assSellOutLand.getState() == 2) {
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
			assInMainJson = assSellOutLandService.updateSellOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/assSellOutLandUpdatePage", method = RequestMethod.GET)
	public String assSellOutLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssSellOutLand assSellOutLand = new AssSellOutLand();

		assSellOutLand = assSellOutLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assSellOutLand.getGroup_id());
		mode.addAttribute("hos_id", assSellOutLand.getHos_id());
		mode.addAttribute("copy_code", assSellOutLand.getCopy_code());
		mode.addAttribute("sell_out_no", assSellOutLand.getSell_out_no());
		mode.addAttribute("in_group_id", assSellOutLand.getIn_group_id());
		mode.addAttribute("in_group_name", assSellOutLand.getIn_group_name());
		mode.addAttribute("in_hos_id", assSellOutLand.getIn_hos_id());
		mode.addAttribute("in_hos_name", assSellOutLand.getIn_hos_name());
		mode.addAttribute("price", assSellOutLand.getPrice());
		mode.addAttribute("add_depre", assSellOutLand.getAdd_depre());
		mode.addAttribute("cur_money", assSellOutLand.getCur_money());
		mode.addAttribute("fore_money", assSellOutLand.getFore_money());
		mode.addAttribute("sell_price", assSellOutLand.getSell_price());
		mode.addAttribute("dispose_income", assSellOutLand.getDispose_income());
		mode.addAttribute("dispose_tax", assSellOutLand.getDispose_tax());
		mode.addAttribute("create_emp", assSellOutLand.getCreate_emp());
		mode.addAttribute("create_emp_name", assSellOutLand.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assSellOutLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assSellOutLand.getAudit_emp());
		mode.addAttribute("audit_emp_name", assSellOutLand.getAudit_emp_name());
		mode.addAttribute("audit_date", assSellOutLand.getAudit_date());
		mode.addAttribute("state", assSellOutLand.getState());
		mode.addAttribute("state_name", assSellOutLand.getState_name());
		mode.addAttribute("note", assSellOutLand.getNote());
		mode.addAttribute("bus_type_code", assSellOutLand.getBus_type_code());
		mode.addAttribute("bus_type_name", assSellOutLand.getBus_type_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asssellout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/deleteAssSellOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutLand assSellOutLand = assSellOutLandService.queryByCode(mapVo);
			if (assSellOutLand != null) {
				if (assSellOutLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assSellOutLandJson = assSellOutLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutLandJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（土地）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/queryAssSellOutLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutLand = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellOutLand = assSellOutLandService.query(getPage(mapVo));
		}else{
			assSellOutLand = assSellOutLandService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellOutLand);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/deleteAssSellOutDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutDetailLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
			AssSellOutLand assSellOutLand = assSellOutLandService.queryByCode(mapVo);
			if (assSellOutLand != null) {
				if (assSellOutLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assSellOutDetailLandJson = assSellOutDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutDetailLandJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/asssellout/queryAssSellOutDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailLand = assSellOutDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailLand);

	}
	
	/**
 * 状态查询
 * @param mapVo
 * @param mode
 * @return
 * @throws Exception
 */
@RequestMapping(value = "/hrp/ass/assland/asssellout/queryAssSellOutLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssSellOutLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception {

	mapVo.put("group_id", SessionManager.getGroupId());

	mapVo.put("hos_id", SessionManager.getHosId());

	mapVo.put("copy_code", SessionManager.getCopyCode());
	
	//入库单状态查询  （打印时校验数据专用）
	List<String> list = assSellOutLandService.queryAssSellOutLandStates(mapVo);
	
	if(list.size() == 0){
		
		return JSONObject.parseObject("{\"state\":\"true\"}");
		
	}else{
		
		String  str = "" ;
		for(String item : list){
			
			str += item +"," ;
		}
		
		return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
	}
	
	
}

}
