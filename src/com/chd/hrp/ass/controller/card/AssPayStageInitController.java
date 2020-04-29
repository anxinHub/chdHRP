/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.card;

import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.card.AssCardInitGeneral;
import com.chd.hrp.ass.entity.card.AssCardInitHouse;
import com.chd.hrp.ass.entity.card.AssCardInitInassets;
import com.chd.hrp.ass.entity.card.AssCardInitLand;
import com.chd.hrp.ass.entity.card.AssCardInitOther;
import com.chd.hrp.ass.entity.card.AssCardInitSpecial;
import com.chd.hrp.ass.service.card.AssCardInitBasicService;
import com.chd.hrp.ass.service.card.AssCardInitGeneralService;
import com.chd.hrp.ass.service.card.AssCardInitHouseService;
import com.chd.hrp.ass.service.card.AssCardInitInassetsService;
import com.chd.hrp.ass.service.card.AssCardInitLandService;
import com.chd.hrp.ass.service.card.AssCardInitOtherService;
import com.chd.hrp.ass.service.card.AssCardInitSpecialService;
import com.chd.hrp.ass.service.pay.AssPayStageInitGeneralService;
import com.chd.hrp.ass.service.pay.AssPayStageInitHouseService;
import com.chd.hrp.ass.service.pay.AssPayStageInitInassetsService;
import com.chd.hrp.ass.service.pay.AssPayStageInitLandService;
import com.chd.hrp.ass.service.pay.AssPayStageInitOtherService;
import com.chd.hrp.ass.service.pay.AssPayStageInitSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPayStageInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPayStageInitController.class);

	// 引入Service服务
	@Resource(name = "assPayStageInitGeneralService")
	private final AssPayStageInitGeneralService assPayStageInitGeneralService = null;

	@Resource(name = "assPayStageInitHouseService")
	private final AssPayStageInitHouseService assPayStageInitHouseService = null;

	@Resource(name = "assPayStageInitOtherService")
	private final AssPayStageInitOtherService assPayStageInitOtherService = null;

	@Resource(name = "assPayStageInitSpecialService")
	private final AssPayStageInitSpecialService assPayStageInitSpecialService = null;
	
	@Resource(name = "assPayStageInitInassetsService")
	private final AssPayStageInitInassetsService assPayStageInitInassetsService = null;

	@Resource(name = "assPayStageInitLandService")
	private final AssPayStageInitLandService assPayStageInitLandService = null;
	
	@Resource(name = "assCardInitBasicService")
	private final AssCardInitBasicService assCardInitBasicService = null;
	
	@Resource(name = "assCardInitGeneralService")
	private final AssCardInitGeneralService assCardInitGeneralService = null;

	@Resource(name = "assCardInitHouseService")
	private final AssCardInitHouseService assCardInitHouseService = null;

	@Resource(name = "assCardInitOtherService")
	private final AssCardInitOtherService assCardInitOtherService = null;

	@Resource(name = "assCardInitSpecialService")
	private final AssCardInitSpecialService assCardInitSpecialService = null;

	@Resource(name = "assCardInitInassetsService")
	private final AssCardInitInassetsService assCardInitInassetsService = null;

	@Resource(name = "assCardInitLandService")
	private final AssCardInitLandService assCardInitLandService = null;

	@RequestMapping(value = "/hrp/ass/asscardinit/saveAssPayStageInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssPayStageInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		String assPayStageGeneralJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String ass_nature = (String) mapVo.get("ass_nature");
		
		Double card_price = 0.0;
		Double pay_plan_money = 0.0;
		Double pay_money = 0.0;
		
		if (ass_nature.equals("01")) {
			AssCardInitHouse assCardHouse = assCardInitHouseService.queryByCode(mapVo);
			card_price = assCardHouse.getPrice();
		} else if (ass_nature.equals("02")) {
			AssCardInitSpecial assCardSpecial = assCardInitSpecialService.queryByCode(mapVo);
			card_price = assCardSpecial.getPrice();
		} else if (ass_nature.equals("03")) {
			AssCardInitGeneral assCardGeneral = assCardInitGeneralService.queryByCode(mapVo);
			card_price = assCardGeneral.getPrice();
		} else if (ass_nature.equals("04")) {
			AssCardInitOther assCardOther = assCardInitOtherService.queryByCode(mapVo);
			card_price = assCardOther.getPrice();
		} else if (ass_nature.equals("05")){
			AssCardInitInassets assCardInassets = assCardInitInassetsService.queryByCode(mapVo);
			card_price = assCardInassets.getPrice();
		} else if (ass_nature.equals("06")){
			AssCardInitLand assCardLand = assCardInitLandService.queryByCode(mapVo);
			card_price = assCardLand.getPrice();
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			
			if(detailVo.get("pay_term_id") == null || detailVo.get("pay_term_id").equals("")){
				continue;
			}
			detailVo.put("ass_card_no", mapVo.get("ass_card_no"));
			detailVo.put("pay_term_id",detailVo.get("pay_term_id").toString() +"");
			detailVo.put("stage_no",detailVo.get("stage_no").toString() +"");
			
			
			if(detailVo.get("stage_name") != null && !detailVo.get("stage_name").equals("")){
				detailVo.put("stage_name",detailVo.get("stage_name") +"");
			}else{
				detailVo.put("stage_name","");
			}
			
			if(detailVo.get("pay_plan_date") != null && !detailVo.get("pay_plan_date").equals("")){
				detailVo.put("pay_plan_date",DateUtil.dateToString(DateUtil.stringToDate(detailVo.get("pay_plan_date").toString(), "yyyy-MM-dd"), "yyyy-MM-dd"));
			}else{
				detailVo.put("pay_plan_date",null);
			}
			
			if(detailVo.get("pay_plan_percent") != null && !detailVo.get("pay_plan_percent").equals("") && !detailVo.get("pay_plan_percent").equals("0.00")){
				detailVo.put("pay_plan_percent",detailVo.get("pay_plan_percent").toString());
			}else{
				detailVo.put("pay_plan_percent","0");
			}
			
			if(detailVo.get("pay_plan_money") != null && !detailVo.get("pay_plan_money").equals("") && !detailVo.get("pay_plan_money").equals("0.00")){
				detailVo.put("pay_plan_money",detailVo.get("pay_plan_money").toString());
				pay_plan_money = pay_plan_money + Double.parseDouble(detailVo.get("pay_plan_money").toString());
			}else{
				detailVo.put("pay_plan_money","0");
			}
			
			if(detailVo.get("pay_money") != null && !detailVo.get("pay_money").equals("") && !detailVo.get("pay_money").equals("0.00")){
				detailVo.put("pay_money",detailVo.get("pay_money").toString());
				detailVo.put("bill_money",detailVo.get("pay_money").toString());
				
			}else{
				detailVo.put("pay_money","0");
				detailVo.put("bill_money","0");
			}
			
			pay_money = pay_money + Double.parseDouble(detailVo.get("pay_money").toString());
			
			if(detailVo.get("unpay_money") != null && !detailVo.get("unpay_money").equals("") && !detailVo.get("unpay_money").equals("0.00")){
				detailVo.put("unpay_money",detailVo.get("unpay_money").toString());
				detailVo.put("unbill_money",detailVo.get("unpay_money").toString());
			}else{
				detailVo.put("unpay_money","0");
				detailVo.put("unbill_money","0");
			}
			
			
			if(detailVo.get("ven_id") != null && !detailVo.get("ven_id").equals("")){
				String ven = detailVo.get("ven_id").toString();
				detailVo.put("ven_id", ven.split("@")[0]);
				detailVo.put("ven_no", ven.split("@")[1]);
			}else{
				detailVo.put("ven_id","");
				detailVo.put("ven_no","");
			}
			
			if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
				detailVo.put("note", detailVo.get("note").toString());
			}else{
				detailVo.put("note","");
			}
			if(detailVo.get("is_pre") != null && !detailVo.get("is_pre").equals("")){
				detailVo.put("is_pre", Integer.parseInt(detailVo.get("is_pre").toString()));
			}else{
				detailVo.put("is_pre",0);
			}
			
			listVo.add(detailVo);
		}
		try {
			if(pay_plan_money - card_price > 0){
				return JSONObject.parseObject("{\"warn\":\"付款金额不能大于卡片原值\"}");
			}
			
			if(pay_money > 0){
				if(pay_money > pay_plan_money){
					return JSONObject.parseObject("{\"warn\":\"已付金额不能大于付款金额\"}");
				}
				
				if(pay_money  - card_price  > 0){
					return JSONObject.parseObject("{\"warn\":\"已付金额不能大于卡片原值\"}");
				}
			}
			
			if(listVo.size() > 0){
				if (ass_nature.equals("01")) {
					assPayStageGeneralJson = assPayStageInitHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assPayStageGeneralJson = assPayStageInitSpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assPayStageGeneralJson = assPayStageInitGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assPayStageGeneralJson = assPayStageInitOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assPayStageGeneralJson = assPayStageInitInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assPayStageGeneralJson = assPayStageInitLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPayStageGeneralJson);
	}

	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssPayStageInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPayStageInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("stage_no", ids[4]);
			listVo.add(mapVo);
		}
		try {
			
			
			String assPayStageGeneralJson = "";
			if (ass_nature.equals("01")) {
				assPayStageGeneralJson = assPayStageInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assPayStageGeneralJson = assPayStageInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assPayStageGeneralJson = assPayStageInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assPayStageGeneralJson = assPayStageInitOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assPayStageGeneralJson = assPayStageInitInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assPayStageGeneralJson = assPayStageInitLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assPayStageGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssPayStageInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayStageInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPayStage = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assPayStage = assPayStageInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assPayStage = assPayStageInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assPayStage = assPayStageInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assPayStage = assPayStageInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assPayStage = assPayStageInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assPayStage = assPayStageInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assPayStage);

	}

}
