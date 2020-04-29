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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
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
import com.chd.hrp.ass.service.share.AssShareDeptGeneralService;
import com.chd.hrp.ass.service.share.AssShareDeptHouseService;
import com.chd.hrp.ass.service.share.AssShareDeptInassetsService;
import com.chd.hrp.ass.service.share.AssShareDeptLandService;
import com.chd.hrp.ass.service.share.AssShareDeptOtherService;
import com.chd.hrp.ass.service.share.AssShareDeptSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssShareDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(AssShareDeptController.class);

	// 引入Service服务
	@Resource(name = "assShareDeptGeneralService")
	private final AssShareDeptGeneralService assShareDeptGeneralService = null;

	@Resource(name = "assShareDeptHouseService")
	private final AssShareDeptHouseService assShareDeptHouseService = null;

	@Resource(name = "assShareDeptOtherService")
	private final AssShareDeptOtherService assShareDeptOtherService = null;

	@Resource(name = "assShareDeptSpecialService")
	private final AssShareDeptSpecialService assShareDeptSpecialService = null;
	
	@Resource(name = "assShareDeptInassetsService")
	private final AssShareDeptInassetsService assShareDeptInassetsService = null;

	@Resource(name = "assShareDeptLandService")
	private final AssShareDeptLandService assShareDeptLandService = null;
	
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

	@RequestMapping(value = "/hrp/ass/asscard/saveAssCardShareDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardShareDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		String assShareDeptGeneralJson = "";
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
			
			if(detailVo.get("dept_id") == null || detailVo.get("dept_id").equals("")){
				continue;
			}
			
			detailVo.put("ass_card_no", mapVo.get("ass_card_no"));
			String dept_id = detailVo.get("dept_id") == null ? "" : detailVo.get("dept_id").toString();
			detailVo.put("dept_id", Integer.parseInt(dept_id.split("@")[0]));
			detailVo.put("dept_no", Integer.parseInt(dept_id.split("@")[1]));
			
			if(detailVo.get("note") == null || detailVo.get("note").equals("")){
				detailVo.put("note","");
			}else{
				detailVo.put("note",detailVo.get("note"));
			}
			
			detailVo.put("ass_year",getAssYear());
			
			detailVo.put("ass_month",getAssMonth());
			
			listVo.add(detailVo);
		}
		try {
			if(listVo.size() > 0){
				if (ass_nature.equals("01")) {
					assShareDeptGeneralJson = assShareDeptHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assShareDeptGeneralJson = assShareDeptSpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assShareDeptGeneralJson = assShareDeptGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assShareDeptGeneralJson = assShareDeptOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assShareDeptGeneralJson = assShareDeptInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assShareDeptGeneralJson = assShareDeptLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assShareDeptGeneralJson);
	}
	
	private String getAssYear(){
		String yearMonth=MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}
	
	private String getAssMonth(){
		String yearMonth=MyConfig.getCurAccYearMonth();
		return  yearMonth.substring(4, 6);
	}

	@RequestMapping(value = "/hrp/ass/asscard/deleteAssShareDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssShareDept(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("dept_id", ids[4]);
			mapVo.put("dept_no", ids[5]);
			mapVo.put("ass_year", getAssYear());
			mapVo.put("ass_month", getAssMonth());
			
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
			
			String assShareDeptGeneralJson = "";
			if (ass_nature.equals("01")) {
				assShareDeptGeneralJson = assShareDeptHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assShareDeptGeneralJson = assShareDeptSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assShareDeptGeneralJson = assShareDeptGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assShareDeptGeneralJson = assShareDeptOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assShareDeptGeneralJson = assShareDeptInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assShareDeptGeneralJson = assShareDeptLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assShareDeptGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscard/importAssShareDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAssShareDept(@RequestParam Map<String, Object> mapVo, 
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String ass_nature = mapVo.get("ass_naturs").toString();
		if (ass_nature.equals("01")) {
			AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
			if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		} else if (ass_nature.equals("02")) {
			AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
			if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		} else if (ass_nature.equals("03")) {
			AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		} else if (ass_nature.equals("04")) {
			AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
			if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
			
		} else if (ass_nature.equals("05")){
			AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
			if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
			
		} else if (ass_nature.equals("06")){
			AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
			if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能导入! \"}");
			}
		}
		String result = assCardBasicService.importShareDept(mapVo);
		
		return JSONObject.parseObject(result);
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/queryAssShareDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssShareDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assShareDept = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assShareDept = assShareDeptHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assShareDept = assShareDeptSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assShareDept = assShareDeptGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assShareDept = assShareDeptOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assShareDept = assShareDeptInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assShareDept = assShareDeptLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assShareDept);

	}

}
