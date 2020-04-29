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
import com.chd.hrp.ass.service.card.AssCardInitBasicService;
import com.chd.hrp.ass.service.share.AssShareDeptInitGeneralService;
import com.chd.hrp.ass.service.share.AssShareDeptInitHouseService;
import com.chd.hrp.ass.service.share.AssShareDeptInitInassetsService;
import com.chd.hrp.ass.service.share.AssShareDeptInitLandService;
import com.chd.hrp.ass.service.share.AssShareDeptInitOtherService;
import com.chd.hrp.ass.service.share.AssShareDeptInitSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssShareDeptInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssShareDeptInitController.class);

	// 引入Service服务
	@Resource(name = "assShareDeptInitGeneralService")
	private final AssShareDeptInitGeneralService assShareDeptInitGeneralService = null;

	@Resource(name = "assShareDeptInitHouseService")
	private final AssShareDeptInitHouseService assShareDeptInitHouseService = null;

	@Resource(name = "assShareDeptInitOtherService")
	private final AssShareDeptInitOtherService assShareDeptInitOtherService = null;

	@Resource(name = "assShareDeptInitSpecialService")
	private final AssShareDeptInitSpecialService assShareDeptInitSpecialService = null;
	
	@Resource(name = "assShareDeptInitInassetsService")
	private final AssShareDeptInitInassetsService assShareDeptInitInassetsService = null;

	@Resource(name = "assShareDeptInitLandService")
	private final AssShareDeptInitLandService assShareDeptInitLandService = null;
	
	@Resource(name = "assCardInitBasicService")
	private final AssCardInitBasicService assCardInitBasicService = null;

	@RequestMapping(value = "/hrp/ass/asscardinit/saveAssCardShareDeptInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardShareDeptInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		String assShareDeptGeneralJson = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
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
			
			listVo.add(detailVo);
		}
		try {
			if(listVo.size() > 0){
				if (ass_nature.equals("01")) {
					assShareDeptGeneralJson = assShareDeptInitHouseService.addBatch(listVo);
				} else if (ass_nature.equals("02")) {
					assShareDeptGeneralJson = assShareDeptInitSpecialService.addBatch(listVo);
				} else if (ass_nature.equals("03")) {
					assShareDeptGeneralJson = assShareDeptInitGeneralService.addBatch(listVo);
				} else if (ass_nature.equals("04")) {
					assShareDeptGeneralJson = assShareDeptInitOtherService.addBatch(listVo);
				} else if (ass_nature.equals("05")){
					assShareDeptGeneralJson = assShareDeptInitInassetsService.addBatch(listVo);
				} else if (ass_nature.equals("06")){
					assShareDeptGeneralJson = assShareDeptInitLandService.addBatch(listVo);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有数据保存 \"}");
			}

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assShareDeptGeneralJson);
	}

	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssShareDeptInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssShareDeptInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("dept_id", ids[4]);
			mapVo.put("dept_no", ids[5]);
			listVo.add(mapVo);

		}
		try {
			String assShareDeptGeneralJson = "";
			if (ass_nature.equals("01")) {
				assShareDeptGeneralJson = assShareDeptInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assShareDeptGeneralJson = assShareDeptInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assShareDeptGeneralJson = assShareDeptInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assShareDeptGeneralJson = assShareDeptInitOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assShareDeptGeneralJson = assShareDeptInitInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assShareDeptGeneralJson = assShareDeptInitLandService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(assShareDeptGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscardinit/importAssShareDeptInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAssShareDeptInit(@RequestParam Map<String, Object> mapVo, String ass_nature,
			Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String result = assCardInitBasicService.importShareDept(mapVo);
		
		return JSONObject.parseObject(result);
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssShareDeptInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssShareDeptInit(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assShareDept = assShareDeptInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assShareDept = assShareDeptInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assShareDept = assShareDeptInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assShareDept = assShareDeptInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assShareDept = assShareDeptInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assShareDept = assShareDeptInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assShareDept);

	}

}
