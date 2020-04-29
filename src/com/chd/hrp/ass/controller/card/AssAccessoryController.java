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
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.service.accessory.AssAccessoryGeneralService;
import com.chd.hrp.ass.service.accessory.AssAccessoryHouseService;
import com.chd.hrp.ass.service.accessory.AssAccessoryInassetsService;
import com.chd.hrp.ass.service.accessory.AssAccessoryLandService;
import com.chd.hrp.ass.service.accessory.AssAccessoryOtherService;
import com.chd.hrp.ass.service.accessory.AssAccessorySpecialService;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssAccessoryController extends BaseController {     

	private static Logger logger = Logger.getLogger(AssAccessoryController.class);

	// 引入Service服务
	@Resource(name = "assAccessoryGeneralService")
	private final AssAccessoryGeneralService assAccessoryGeneralService = null;

	@Resource(name = "assAccessoryHouseService")
	private final AssAccessoryHouseService assAccessoryHouseService = null;

	@Resource(name = "assAccessoryOtherService")
	private final AssAccessoryOtherService assAccessoryOtherService = null;

	@Resource(name = "assAccessorySpecialService")
	private final AssAccessorySpecialService assAccessorySpecialService = null;
	
	@Resource(name = "assAccessoryInassetsService")
	private final AssAccessoryInassetsService assAccessoryInassetsService = null;

	@Resource(name = "assAccessoryLandService")
	private final AssAccessoryLandService assAccessoryLandService = null;
	
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

	@RequestMapping(value = "/hrp/ass/asscard/saveAssCardAccessory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardAccessory(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		String assAccessoryGeneralJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
			if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
			}
		} else if (ass_nature.equals("02")) {
			AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
			if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
			}
		} else if (ass_nature.equals("03")) {
			AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
			}
			
		} else if (ass_nature.equals("04")) {
			AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
			if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
			}
			
		} else if (ass_nature.equals("05")){
			AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
			if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
			}
			
		} else if (ass_nature.equals("06")){
			AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
			if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
			}
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			
			if(detailVo.get("accessory_code") == null || detailVo.get("accessory_code").equals("")){
				continue;
			}
			detailVo.put("ass_card_no", mapVo.get("ass_card_no"));
			detailVo.put("accessory_name",detailVo.get("accessory_name"));
			detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("accessory_name").toString()));
			detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("accessory_name").toString()));
			if(detailVo.get("accessory_amount") != null && !detailVo.get("accessory_amount").equals("")){
				detailVo.put("accessory_amount",detailVo.get("accessory_amount"));
			}else{
				detailVo.put("accessory_amount",null); 
			}
			
			if(detailVo.get("accessory_price") != null && !detailVo.get("accessory_price").equals("") && !detailVo.get("accessory_price").equals("0.00")){
				detailVo.put("accessory_price",detailVo.get("accessory_price"));
			}else{
				detailVo.put("accessory_price",null);
			}
			
			if(detailVo.get("accessory_money") != null && !detailVo.get("accessory_money").equals("") && !detailVo.get("accessory_money").equals("0.00")){
				detailVo.put("accessory_money",detailVo.get("accessory_money"));
			}else{
				detailVo.put("accessory_money",null);
			}
			
			if(detailVo.get("naturs_code") != null && !detailVo.get("naturs_code").equals("") && !detailVo.get("naturs_code").equals(" ")){
				detailVo.put("naturs_code", detailVo.get("naturs_code").toString().split(" ")[0]);
			}else{
				detailVo.put("naturs_code","");
			}
			
			if(detailVo.get("accessory_card_no") != null && !detailVo.get("accessory_card_no").equals("")){
				detailVo.put("accessory_card_no", detailVo.get("accessory_card_no"));
			}else{
				detailVo.put("accessory_card_no","");
			}
			if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
				detailVo.put("note", detailVo.get("note"));
			}else{
				detailVo.put("note","");
			}
			if(detailVo.get("is_stop") != null && !detailVo.get("is_stop").equals("")){
				detailVo.put("is_stop", Integer.parseInt(detailVo.get("is_stop").toString()));
			}else{
				detailVo.put("is_stop",0);
			}
			
			listVo.add(detailVo);
		}
		try {
			if(listVo.size() > 0){
				if (ass_nature.equals("01")) {
					assAccessoryGeneralJson = assAccessoryHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assAccessoryGeneralJson = assAccessorySpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assAccessoryGeneralJson = assAccessoryGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assAccessoryGeneralJson = assAccessoryOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assAccessoryGeneralJson = assAccessoryInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assAccessoryGeneralJson = assAccessoryLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assAccessoryGeneralJson);
	}

	@RequestMapping(value = "/hrp/ass/asscard/deleteAssAccessory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAccessory(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("accessory_code", ids[4]);
			if (ass_nature.equals("01")) {
				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
					flag = false;
					break;
				}
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
					flag = false;
					break;
				}
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
					flag = false;
					break;
				}
			} else if (ass_nature.equals("04")) {
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
					flag = false;
					break;
				}
				
			} else if (ass_nature.equals("05")){
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
					flag = false;
					break;
				}
				
			} else if (ass_nature.equals("06")){
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
					flag = false;
					break;
				}
			}
			listVo.add(mapVo);
		}
		try {
			
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能删除! \"}");
			}
			
			String assAccessoryGeneralJson = "";
			if (ass_nature.equals("01")) {
				assAccessoryGeneralJson = assAccessoryHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assAccessoryGeneralJson = assAccessorySpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assAccessoryGeneralJson = assAccessoryGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assAccessoryGeneralJson = assAccessoryOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assAccessoryGeneralJson = assAccessoryInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assAccessoryGeneralJson = assAccessoryLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assAccessoryGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscard/importAssAccessory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAssAccessory(@RequestParam Map<String, Object> mapVo, String ass_naturs,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (mapVo.get("ass_naturs").equals("01")) {
			AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
			if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		} else if (mapVo.get("ass_naturs").equals("02")) {
			AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
			if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
			
		} else if (ass_naturs.equals("03")) {
			AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		} else if (mapVo.get("ass_naturs").equals("04")) {
			AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
			if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
			
		} else if (mapVo.get("ass_naturs").equals("05")){
			AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
			if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
			
		} else if (mapVo.get("ass_naturs").equals("06")){
			AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
			if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		}
		
		String result = assCardBasicService.importAccessory(mapVo);
		
		return JSONObject.parseObject(result);
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/queryAssAccessory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAccessory(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAccessory = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assAccessory = assAccessoryHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assAccessory = assAccessorySpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assAccessory = assAccessoryGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assAccessory = assAccessoryOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assAccessory = assAccessoryInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assAccessory = assAccessoryLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assAccessory);

	}

}
