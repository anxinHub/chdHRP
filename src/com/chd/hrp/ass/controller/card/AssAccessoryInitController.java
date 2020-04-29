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
import com.chd.hrp.ass.service.accessory.AssAccessoryInitGeneralService;
import com.chd.hrp.ass.service.accessory.AssAccessoryInitHouseService;
import com.chd.hrp.ass.service.accessory.AssAccessoryInitInassetsService;
import com.chd.hrp.ass.service.accessory.AssAccessoryInitLandService;
import com.chd.hrp.ass.service.accessory.AssAccessoryInitOtherService;
import com.chd.hrp.ass.service.accessory.AssAccessoryInitSpecialService;
import com.chd.hrp.ass.service.card.AssCardInitBasicService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssAccessoryInitController extends BaseController {     

	private static Logger logger = Logger.getLogger(AssAccessoryInitController.class);

	// 引入Service服务
	@Resource(name = "assAccessoryInitGeneralService")
	private final AssAccessoryInitGeneralService assAccessoryInitGeneralService = null;

	@Resource(name = "assAccessoryInitHouseService")
	private final AssAccessoryInitHouseService assAccessoryInitHouseService = null;

	@Resource(name = "assAccessoryInitOtherService")
	private final AssAccessoryInitOtherService assAccessoryInitOtherService = null;

	@Resource(name = "assAccessoryInitSpecialService")
	private final AssAccessoryInitSpecialService assAccessoryInitSpecialService = null;
	
	@Resource(name = "assAccessoryInitInassetsService")
	private final AssAccessoryInitInassetsService assAccessoryInitInassetsService = null;

	@Resource(name = "assAccessoryInitLandService")
	private final AssAccessoryInitLandService assAccessoryInitLandService = null;
	
	@Resource(name = "assCardInitBasicService")
	private final AssCardInitBasicService assCardInitBasicService = null;

	@RequestMapping(value = "/hrp/ass/asscardinit/saveAssCardAccessoryInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardAccessoryInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		String assAccessoryGeneralJson = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
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
					assAccessoryGeneralJson = assAccessoryInitHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assAccessoryGeneralJson = assAccessoryInitSpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assAccessoryGeneralJson = assAccessoryInitGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assAccessoryGeneralJson = assAccessoryInitOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assAccessoryGeneralJson = assAccessoryInitInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assAccessoryGeneralJson = assAccessoryInitLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assAccessoryGeneralJson);
	}

	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssAccessoryInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAccessoryInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("accessory_code", ids[4]);
			listVo.add(mapVo);

		}
		try {
			String assAccessoryGeneralJson = "";
			if (ass_nature.equals("01")) {
				assAccessoryGeneralJson = assAccessoryInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assAccessoryGeneralJson = assAccessoryInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assAccessoryGeneralJson = assAccessoryInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assAccessoryGeneralJson = assAccessoryInitOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assAccessoryGeneralJson = assAccessoryInitInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assAccessoryGeneralJson = assAccessoryInitLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assAccessoryGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscardinit/importAssAccessoryInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAssAccessoryInit(@RequestParam Map<String, Object> mapVo, String ass_nature,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String result = assCardInitBasicService.importAccessory(mapVo);
		
		return JSONObject.parseObject(result);
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssAccessoryInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAccessoryInit(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assAccessory = assAccessoryInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assAccessory = assAccessoryInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assAccessory = assAccessoryInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assAccessory = assAccessoryInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assAccessory = assAccessoryInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assAccessory = assAccessoryInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assAccessory);

	}

}
