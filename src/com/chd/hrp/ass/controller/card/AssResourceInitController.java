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
import com.chd.hrp.ass.entity.card.AssCardInitGeneral;
import com.chd.hrp.ass.entity.card.AssCardInitHouse;
import com.chd.hrp.ass.entity.card.AssCardInitInassets;
import com.chd.hrp.ass.entity.card.AssCardInitLand;
import com.chd.hrp.ass.entity.card.AssCardInitOther;
import com.chd.hrp.ass.entity.card.AssCardInitSpecial;
import com.chd.hrp.ass.service.card.AssCardInitGeneralService;
import com.chd.hrp.ass.service.card.AssCardInitHouseService;
import com.chd.hrp.ass.service.card.AssCardInitInassetsService;
import com.chd.hrp.ass.service.card.AssCardInitLandService;
import com.chd.hrp.ass.service.card.AssCardInitOtherService;
import com.chd.hrp.ass.service.card.AssCardInitSpecialService;
import com.chd.hrp.ass.service.resource.AssResourceInitGeneralService;
import com.chd.hrp.ass.service.resource.AssResourceInitHouseService;
import com.chd.hrp.ass.service.resource.AssResourceInitInassetsService;
import com.chd.hrp.ass.service.resource.AssResourceInitLandService;
import com.chd.hrp.ass.service.resource.AssResourceInitOtherService;
import com.chd.hrp.ass.service.resource.AssResourceInitSpecialService;


@Controller
public class AssResourceInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssResourceInitController.class);

	// 引入Service服务
	@Resource(name = "assResourceInitGeneralService")
	private final AssResourceInitGeneralService assResourceInitGeneralService = null;

	@Resource(name = "assResourceInitHouseService")
	private final AssResourceInitHouseService assResourceInitHouseService = null;

	@Resource(name = "assResourceInitOtherService")
	private final AssResourceInitOtherService assResourceInitOtherService = null;

	@Resource(name = "assResourceInitSpecialService")
	private final AssResourceInitSpecialService assResourceInitSpecialService = null;
	
	@Resource(name = "assResourceInitInassetsService")
	private final AssResourceInitInassetsService assResourceInitInassetsService = null;

	@Resource(name = "assResourceInitLandService")
	private final AssResourceInitLandService assResourceInitLandService = null;
	
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

	@RequestMapping(value = "/hrp/ass/asscardinit/saveAssCardResourceInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardResourceInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		String assResourceGeneralJson = "";
		String ass_nature = (String) mapVo.get("ass_nature");
		Double card_price = 0.0;
		Double card_cur_money = 0.0;
		Double card_fore_money = 0.0;
		Double card_depre_money = 0.0;
		Double card_manage_depre_money = 0.0;
		Double source_total_price = 0.0;
		
		Double source_cur_money = 0.0;
		Double source_fore_money = 0.0;
		Double source_depre_money = 0.0;
		Double source_manage_depre_money = 0.0;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if (ass_nature.equals("01")) {
			AssCardInitHouse assCardHouse = assCardInitHouseService.queryByCode(mapVo);
			card_price = assCardHouse.getPrice();
			card_cur_money = assCardHouse.getCur_money();
			card_fore_money = assCardHouse.getFore_money() == null ?0.0:assCardHouse.getFore_money();
			card_depre_money = assCardHouse.getDepre_money();
			card_manage_depre_money = assCardHouse.getManage_depre_money() == null ? 0.0:assCardHouse.getManage_depre_money();
		} else if (ass_nature.equals("02")) {
			AssCardInitSpecial assCardSpecial = assCardInitSpecialService.queryByCode(mapVo);
			card_price = assCardSpecial.getPrice();
			card_cur_money = assCardSpecial.getCur_money();
			card_fore_money = assCardSpecial.getFore_money() == null ?0.0:assCardSpecial.getFore_money();
			card_depre_money = assCardSpecial.getDepre_money();
			card_manage_depre_money = assCardSpecial.getManage_depre_money() == null ? 0.0:assCardSpecial.getManage_depre_money();
		} else if (ass_nature.equals("03")) {
			AssCardInitGeneral assCardGeneral = assCardInitGeneralService.queryByCode(mapVo);
			card_price = assCardGeneral.getPrice();
			card_cur_money = assCardGeneral.getCur_money();
			card_fore_money = assCardGeneral.getFore_money() == null ?0.0:assCardGeneral.getFore_money();
			card_depre_money = assCardGeneral.getDepre_money();
			card_manage_depre_money = assCardGeneral.getManage_depre_money() == null ? 0.0:assCardGeneral.getManage_depre_money();
		} else if (ass_nature.equals("04")) {
			AssCardInitOther assCardOther = assCardInitOtherService.queryByCode(mapVo);
			card_price = assCardOther.getPrice();
			card_cur_money = assCardOther.getCur_money();
			card_fore_money = assCardOther.getFore_money() == null ?0.0:assCardOther.getFore_money();
			card_depre_money = assCardOther.getDepre_money();
			card_manage_depre_money = assCardOther.getManage_depre_money() == null ? 0.0:assCardOther.getManage_depre_money();
		} else if (ass_nature.equals("05")){
			AssCardInitInassets assCardInassets = assCardInitInassetsService.queryByCode(mapVo);
			card_price = assCardInassets.getPrice();
			card_cur_money = assCardInassets.getCur_money();
			card_fore_money = assCardInassets.getFore_money() == null ?0.0:assCardInassets.getFore_money();
			card_depre_money = assCardInassets.getDepre_money();
			card_manage_depre_money = assCardInassets.getManage_depre_money() == null ? 0.0:assCardInassets.getManage_depre_money();
		} else if (ass_nature.equals("06")){
			AssCardInitLand assCardLand = assCardInitLandService.queryByCode(mapVo);
			card_price = assCardLand.getPrice();
			card_cur_money = assCardLand.getCur_money();
			card_fore_money = assCardLand.getFore_money() == null ?0.0:assCardLand.getFore_money();
			card_depre_money = assCardLand.getDepre_money();
			card_manage_depre_money = assCardLand.getManage_depre_money() == null ? 0.0:assCardLand.getManage_depre_money();
		}
		java.text.DecimalFormat df  = new java.text.DecimalFormat("#.00");  
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			
			if(detailVo.get("source_id") == null || detailVo.get("source_id").equals("")){
				continue;
			}
			
			detailVo.put("ass_card_no", mapVo.get("ass_card_no") + "");
			String source_id = detailVo.get("source_id") == null ? "" : detailVo.get("source_id").toString();
			detailVo.put("source_id", Integer.parseInt(source_id.split("\\.")[0]) + "");
			detailVo.put("price", df.format(Double.parseDouble(detailVo.get("price").toString())) + "");
			
			source_total_price = source_total_price + Double.parseDouble(df.format(Double.parseDouble(detailVo.get("price").toString())));
			
			if(detailVo.get("depre_money") != null && !detailVo.get("depre_money").equals("")){
				source_depre_money = source_depre_money + Double.parseDouble(df.format(Double.parseDouble(detailVo.get("depre_money").toString())));
				detailVo.put("depre_money", df.format(Double.parseDouble(detailVo.get("depre_money").toString())) + "");
			}else{
				detailVo.put("depre_money", "0");
			}
			
			if(detailVo.get("cur_money") != null && !detailVo.get("cur_money").equals("")){
				source_cur_money = source_cur_money + Double.parseDouble(df.format(Double.parseDouble(detailVo.get("cur_money").toString())));
				detailVo.put("cur_money", df.format(Double.parseDouble(detailVo.get("cur_money").toString()))+ "");
			}else{
				detailVo.put("cur_money", "0");
			}
			
			if(detailVo.get("fore_money") != null && !detailVo.get("fore_money").equals("")){
				source_fore_money = source_fore_money + Double.parseDouble(detailVo.get("fore_money").toString());
				detailVo.put("fore_money", detailVo.get("fore_money") + "");
			}else{
				detailVo.put("fore_money", "0");
			}
			
			if(detailVo.get("manage_depre_money") != null && !detailVo.get("manage_depre_money").equals("")){
				source_manage_depre_money = source_manage_depre_money + Double.parseDouble(detailVo.get("manage_depre_money").toString());
				detailVo.put("manage_depre_money", detailVo.get("manage_depre_money") + "");
			}else{
				detailVo.put("manage_depre_money", "0");
			}
			
			if(detailVo.get("pay_money") != null && !detailVo.get("pay_money").equals("")){
				detailVo.put("pay_money", detailVo.get("pay_money") + "");
				detailVo.put("unpay_money", Double.parseDouble(detailVo.get("price").toString()) - Double.parseDouble(detailVo.get("pay_money").toString()) + "");
			}else{
				detailVo.put("pay_money", "0");
				detailVo.put("unpay_money", Double.parseDouble(detailVo.get("price").toString()) + "");
			}
			listVo.add(detailVo);
		}
		try {
			
			if(Double.parseDouble(df.format(source_total_price)) - card_price.doubleValue() != 0){
				return JSONObject.parseObject("{\"warn\":\"资金来源金额和卡片原值不一致\"}");
			}
			
			if(Double.parseDouble(df.format(source_cur_money)) - card_cur_money.doubleValue() != 0){
				return JSONObject.parseObject("{\"warn\":\"资金来源净值和卡片净值不一致\"}");
			}
			
			if(source_fore_money.doubleValue() - card_fore_money.doubleValue() != 0){
				return JSONObject.parseObject("{\"warn\":\"资金来源残值和卡片残值不一致\"}");
			}
			
			if(Double.parseDouble(df.format(source_depre_money)) - card_depre_money.doubleValue() != 0){
				return JSONObject.parseObject("{\"warn\":\"资金来源累计折旧和卡片累计折旧不一致\"}");
			}
			
			if(source_manage_depre_money.doubleValue() - card_manage_depre_money.doubleValue() != 0){
				return JSONObject.parseObject("{\"warn\":\"资金来源累计分摊和卡片累计分摊不一致\"}");
			}
			
			if(listVo.size() > 0){
				if (ass_nature.equals("01")) {
					assResourceGeneralJson = assResourceInitHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assResourceGeneralJson = assResourceInitSpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assResourceGeneralJson = assResourceInitGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assResourceGeneralJson = assResourceInitOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assResourceGeneralJson = assResourceInitInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assResourceGeneralJson = assResourceInitLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assResourceGeneralJson);
	}

	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssResourceInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssResourceInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("source_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			String assResourceGeneralJson = "";
			if (ass_nature.equals("01")) {
				assResourceGeneralJson = assResourceInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assResourceGeneralJson = assResourceInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assResourceGeneralJson = assResourceInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assResourceGeneralJson = assResourceInitOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assResourceGeneralJson = assResourceInitInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assResourceGeneralJson = assResourceInitLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assResourceGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssResourceInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssResourceInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assResource = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assResource = assResourceInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assResource = assResourceInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assResource = assResourceInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assResource = assResourceInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assResource = assResourceInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assResource = assResourceInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assResource);

	}

}
