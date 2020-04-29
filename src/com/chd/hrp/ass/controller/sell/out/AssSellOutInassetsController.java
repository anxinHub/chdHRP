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
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailInassets;
import com.chd.hrp.ass.entity.sell.out.AssSellOutInassets;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.sell.out.AssSellOutDetailInassetsService;
import com.chd.hrp.ass.service.sell.out.AssSellOutInassetsService;

/**
 * 
 * @Description: 050901 资产有偿调拨出库单主表(无形资产)
 * @Table: ASS_SELL_OUT_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssSellOutInassetsController extends BaseController {

	private static Logger logger = Logger.getLogger(AssSellOutInassetsController.class);

	// 引入Service服务
	@Resource(name = "assSellOutInassetsService")
	private final AssSellOutInassetsService assSellOutInassetsService = null;

	@Resource(name = "assSellOutDetailInassetsService")
	private final AssSellOutDetailInassetsService assSellOutDetailInassetsService = null;
	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/assSellOutInassetsMainPage", method = RequestMethod.GET)
	public String assSellOutInassetsMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05049",MyConfig.getSysPara("05049"));
		
		return "hrp/ass/assinassets/asssellout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/assSellOutInassetsAddPage", method = RequestMethod.GET)
	public String assSellOutInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asssellout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/saveAssSellOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssSellOutInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssSellOutInassets assBackInassets = assSellOutInassetsService.queryByCode(mapVo);
		if (assBackInassets != null) {
			if (assBackInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assSellOutInassetsJson = assSellOutInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assSellOutInassetsJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/updateConfirmSellOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmSellOutInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutInassets assSellOutInassets = assSellOutInassetsService.queryByCode(mapVo);
			if (DateUtil.compareDate(assSellOutInassets.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssSellOutDetailInassets> list = assSellOutDetailInassetsService.queryByAssSellOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssSellOutDetailInassets assBackDetailInassets : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailInassets.getGroup_id());
					listCardMap.put("hos_id", assBackDetailInassets.getHos_id());
					listCardMap.put("copy_code", assBackDetailInassets.getCopy_code());
					listCardMap.put("dispose_type", 32);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_income", assBackDetailInassets.getDispose_income() == null ? "0"
							: assBackDetailInassets.getDispose_income());
					listCardMap.put("dispose_tax", assBackDetailInassets.getDispose_tax() == null ? "0"
							: assBackDetailInassets.getDispose_tax());
					listCardMap.put("ass_card_no", assBackDetailInassets.getAss_card_no());
					AssCardInassets assCardInassets = assCardInassetsService.queryByCode(listCardMap);
					if (assCardInassets == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardInassets.getDispose_type() != null && 32 == assCardInassets.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailInassets.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailInassets.getAss_card_no(), assBackDetailInassets.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assSellOutInassets.getState() == 2) {
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
			assInMainJson = assSellOutInassetsService.updateSellOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/assSellOutInassetsUpdatePage", method = RequestMethod.GET)
	public String assSellOutInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mode.addAttribute("ass_05049",MyConfig.getSysPara("05049"));
		
		return "hrp/ass/assinassets/asssellout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/deleteAssSellOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssSellOutInassets assSellOutInassets = assSellOutInassetsService.queryByCode(mapVo);
			if (assSellOutInassets != null) {
				if (assSellOutInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assSellOutInassetsJson = assSellOutInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutInassetsJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/queryAssSellOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutInassets = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assSellOutInassets = assSellOutInassetsService.query(getPage(mapVo));
		}else{
			assSellOutInassets = assSellOutInassetsService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assSellOutInassets);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/deleteAssSellOutDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssSellOutDetailInassets(@RequestParam(value = "ParamVo") String paramVo,
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
			AssSellOutInassets assSellOutInassets = assSellOutInassetsService.queryByCode(mapVo);
			if (assSellOutInassets != null) {
				if (assSellOutInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assSellOutDetailInassetsJson = assSellOutDetailInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assSellOutDetailInassetsJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/queryAssSellOutDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assSellOutDetailInassets = assSellOutDetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assSellOutDetailInassets);

	}

	/**
	 * 其他无形资产 资产调拨出库 出库单状态查询 （ 打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asssellout/queryAssSellOutInassetsState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssSellOutInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 其他无形资产 资产调拨出库 出库单状态查询 （打印时校验数据专用）
		List<String> list = assSellOutInassetsService.queryAssSellOutInassetsState(mapVo);

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
