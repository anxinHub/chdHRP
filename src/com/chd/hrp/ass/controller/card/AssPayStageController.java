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
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.pay.AssPayStageGeneralService;
import com.chd.hrp.ass.service.pay.AssPayStageHouseService;
import com.chd.hrp.ass.service.pay.AssPayStageInassetsService;
import com.chd.hrp.ass.service.pay.AssPayStageLandService;
import com.chd.hrp.ass.service.pay.AssPayStageOtherService;
import com.chd.hrp.ass.service.pay.AssPayStageSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPayStageController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPayStageController.class);

	// 引入Service服务
	@Resource(name = "assPayStageGeneralService")
	private final AssPayStageGeneralService assPayStageGeneralService = null;

	@Resource(name = "assPayStageHouseService")
	private final AssPayStageHouseService assPayStageHouseService = null;

	@Resource(name = "assPayStageOtherService")
	private final AssPayStageOtherService assPayStageOtherService = null;

	@Resource(name = "assPayStageSpecialService")
	private final AssPayStageSpecialService assPayStageSpecialService = null;
	
	@Resource(name = "assPayStageInassetsService")
	private final AssPayStageInassetsService assPayStageInassetsService = null;

	@Resource(name = "assPayStageLandService")
	private final AssPayStageLandService assPayStageLandService = null;
	
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;
	
	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;

	@RequestMapping(value = "/hrp/ass/asscard/saveAssPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssPayStage(@RequestParam Map<String, Object> mapVo, Model mode)
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
			AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
			card_price = assCardHouse.getPrice();
			/*if(assCardHouse.getUse_state() != 0){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能保存! \"}");
			}*/
		} else if (ass_nature.equals("02")) {
			AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
			card_price = assCardSpecial.getPrice();
			/*if(assCardSpecial.getUse_state() != 0){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能保存! \"}");
			}*/
		} else if (ass_nature.equals("03")) {
			AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			card_price = assCardGeneral.getPrice();
			/*if(assCardGeneral.getUse_state() != 0){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能保存! \"}");
			}*/
		} else if (ass_nature.equals("04")) {
			AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
			card_price = assCardOther.getPrice();
			/*if(assCardOther.getUse_state() != 0){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能保存! \"}");
			}*/
		} else if (ass_nature.equals("05")){
			AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
			card_price = assCardInassets.getPrice();
			/*if(assCardInassets.getUse_state() != 0){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能保存! \"}");
			}*/
		} else if (ass_nature.equals("06")){
			AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
			card_price = assCardLand.getPrice();
			/*if(assCardLand.getUse_state() != 0){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能保存! \"}");
			}*/
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
					assPayStageGeneralJson = assPayStageHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assPayStageGeneralJson = assPayStageSpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assPayStageGeneralJson = assPayStageGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assPayStageGeneralJson = assPayStageOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assPayStageGeneralJson = assPayStageInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assPayStageGeneralJson = assPayStageLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPayStageGeneralJson);
	}

	@RequestMapping(value = "/hrp/ass/asscard/deleteAssPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPayStage(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			if("undefined".equals(ids[3])){
				mapVo.put("ass_card_no", '0');
			} else {
				mapVo.put("ass_card_no", ids[3]);
			}
			//mapVo.put("ass_card_no", ids[3]);
			mapVo.put("stage_no", ids[4]);
			if (ass_nature.equals("01")) {
				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse != null){
					if(assCardHouse.getUse_state() != 0){
						flag = false;
						break;
					}
				}
				
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial != null){
				if(assCardSpecial.getUse_state() != 0){
					flag = false;
					break;
				}
				}
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral != null){
				if(assCardGeneral.getUse_state() != 0){
					flag = false;
					break;
				}
				}
			} else if (ass_nature.equals("04")) {
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther != null){
				if(assCardOther.getUse_state() != 0){
					flag = false;
					break;
				}
				}
			} else if (ass_nature.equals("05")){
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets != null){
				if(assCardInassets.getUse_state() != 0){
					flag = false;
					break;
				}
				}
			} else if (ass_nature.equals("06")){
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand != null ){
				if(assCardLand.getUse_state() != 0){
					flag = false;
					break;
				}
				}
			}
			listVo.add(mapVo);
		}
		try {
			
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"卡片不是新建状态,不能删除! \"}");
			}
			
			String assPayStageGeneralJson = "";
			if (ass_nature.equals("01")) {
				assPayStageGeneralJson = assPayStageHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assPayStageGeneralJson = assPayStageSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assPayStageGeneralJson = assPayStageGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assPayStageGeneralJson = assPayStageOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assPayStageGeneralJson = assPayStageInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assPayStageGeneralJson = assPayStageLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assPayStageGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/queryAssPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayStage(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assPayStage = assPayStageHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assPayStage = assPayStageSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assPayStage = assPayStageGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assPayStage = assPayStageOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assPayStage = assPayStageInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assPayStage = assPayStageLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assPayStage);

	}

}
