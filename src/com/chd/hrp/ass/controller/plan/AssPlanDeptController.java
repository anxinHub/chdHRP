package com.chd.hrp.ass.controller.plan;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.serviceImpl.AccLederServiceImpl;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.apply.AssApplyPlanMap;
import com.chd.hrp.ass.entity.bid.AssBidPlanMap;
import com.chd.hrp.ass.entity.dict.AssAcceptItem;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;
import com.chd.hrp.ass.service.apply.AssApplyDeptDetailService;
import com.chd.hrp.ass.service.apply.AssApplyDeptService;
import com.chd.hrp.ass.service.apply.AssApplyPlanMapService;
import com.chd.hrp.ass.service.bid.AssBidPlanMapService;
import com.chd.hrp.ass.service.plan.AssPlanDeptDetailService;
import com.chd.hrp.ass.service.plan.AssPlanDeptService;
import com.chd.hrp.ass.service.resource.AssApplyDeptResourceService;
import com.chd.hrp.ass.service.resource.AssPlanResourceService;
import com.chd.hrp.ass.serviceImpl.resource.AssPlanResourceServiceImpl;
import com.chd.hrp.sys.service.base.SysBaseService;

/**
 * 
 * @Description: 050301 购置计划单
 * @Table: ASS_PLAN_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPlanDeptController extends BaseController {      

	private static Logger logger = Logger.getLogger(AssPlanDeptController.class);

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	@Resource(name = "assPlanDeptService")
	private final AssPlanDeptService assPlanDeptService = null;

	@Resource(name = "assPlanDeptDetailService")
	private final AssPlanDeptDetailService assPlanDeptDetailService = null;

	@Resource(name = "assApplyDeptDetailService")
	private final AssApplyDeptDetailService assApplyDeptDetailService = null;

	@Resource(name = "assBidPlanMapService")
	private final AssBidPlanMapService assBidPlanMapService = null;

	@Resource(name = "assApplyPlanMapService")
	private final AssApplyPlanMapService assApplyPlanMapService = null;

	@Resource(name = "assApplyDeptService")
	private final AssApplyDeptService assApplyDeptService = null;

	@Resource(name = "accLederService")
	private final AccLederServiceImpl accLederService = null;

	@Resource(name = "assPlanResourceService")
	private final AssPlanResourceService assPlanResourceService = null;
	@Resource(name = "assApplyDeptResourceService")
	private final AssApplyDeptResourceService assApplyDeptResourceService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptMainPage", method = RequestMethod.GET)
	public String assPlanDeptMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05009",MyConfig.getSysPara("05009"));
		
		return "hrp/ass/assplandept/assPlanDeptMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptAddPage", method = RequestMethod.GET)
	public String assPlanDeptAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assplandept/assPlanDeptAdd";

	}

	/**
	 * @Description 跳转引入购置申请页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptImportApplyPage", method = RequestMethod.GET)
	public String assPlanDeptImportApplyPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandept/assPlanDeptImportApply";
	}

	/**
	 * @Description 审批页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptApprovePage", method = RequestMethod.GET)
	public String assPlanDeptApprovePage(Model mode, @RequestParam(value = "ParamVo") String paramVo) throws Exception {
		mode.addAttribute("paramVo", paramVo);

		return "hrp/ass/assplandept/assPlanDeptApprove";

	}

	@RequestMapping(value = "/hrp/ass/assplandept/assImportHosPlanDeptPage", method = RequestMethod.GET)
	public String assImportHosPlanDeptPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandept/assImportHosPlanDept";
	}

	/**
	 * @Description 追溯购置申请单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assViewApplyPage", method = RequestMethod.GET)
	public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("plan_id", mapVo.get("plan_id"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandept/assPlanViewApply";
	}

	// 追溯购置申请
	@RequestMapping(value = "/hrp/ass/assplandept/queryAssApplyDeptByApply", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assApplyDeptService.queryAssApplyDeptByPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}

	/**
	 * @Description 添加数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/addAssPlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		/* System.out.println("测试数据能否成功"); */
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("is_group", "0");

		AssPlanDept assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);
		if (assPlanDept != null) {
			System.out.println(assPlanDept.getState());
			if (assPlanDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"当前单据已被审核,不能操作 \"}");
			}
		}

		String create_date = String.valueOf(mapVo.get("create_date"));
		if (StringUtils.isNotEmpty(create_date)) {
			mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));
		}
		String yearmonths=create_date.replace("-", "");
		
		String yearmonth=yearmonths.substring(0, 6);
		
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
		
		String dept_id_no = mapVo.get("dept_id").toString();
		//String source_id = mapVo.get("source_id").toString();

		mapVo.put("dept_id", dept_id_no.split("@")[0]);
		mapVo.put("dept_no", dept_id_no.split("@")[1]);
		mapVo.put("plan_money", "0");
		mapVo.put("state", "0");
		try {

			/*String assPlanDeptJson = assPlanDeptService.addOrUpdateAssPlanDept(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assPlanDeptJson);
			String plan_id = (String) jsonObj.get("plan_id");
			String plan_no = (String) jsonObj.get("plan_no");

			String assPlanDeptDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {
				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());
				detailVo.put("price",detailVo.get("sum_price"));
				detailVo.put("plan_id", plan_id);
				detailVo.put("plan_no", plan_no);
				 detailVo.put("source_id", source_id);
				if (detailVo.get("plan_detail_id") == null) {
					detailVo.put("plan_detail_id", "0");
				}

				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}

				if (StringUtils.isNotEmpty((String) detailVo.get("need_date"))) {
					detailVo.put("need_date",
							DateUtil.stringToDate(detailVo.get("need_date").toString(), "yyyy-MM-dd"));
				}

				String id = detailVo.get("ass_id").toString();

				String fid = detailVo.get("fac_id").toString();
				// double sum_price= (Double) detailVo.get("sum_price");
				detailVo.put("ass_id", id.split("@")[0]);

				detailVo.put("ass_no", id.split("@")[1]);

				if(fid != null && fid.split("@").length == 2){
					detailVo.put("fac_id", fid.split("@")[0]);
					detailVo.put("fac_no", fid.split("@")[1]);
				}else{
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}
				// detailVo.put("sum_price", sum_price);

				assPlanDeptDetailJson = assPlanDeptDetailService.addOrUpdateAssPlanDeptDetail(detailVo);
				JSONObject json = JSONArray.parseObject(assPlanDeptDetailJson);
				String plan_detail_id = (String) json.get("plan_detail_id");
				detailVo.put("plan_detail_id", plan_detail_id);
				// 添加资金来源
			//	assPlanResourceService.addAssPlanSource(detailVo);
			

			}
			JSONObject jsons = JSONArray.parseObject(assPlanDeptDetailJson);
			jsons.put("plan_id", plan_id);
			jsons.put("plan_no", plan_no);*/
			return JSONObject.parseObject(assPlanDeptService.addOrUpdateAssPlanDept(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * 修改资金来源 saveResourceItem
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/saveResourceItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveResourceItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String assPlanResourceJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("dataItem").toString());

		for (Map<String, Object> detailVo : detail) {
			String id = mapVo.get("ass_id").toString();
           // Integer resource_price= Integer.parseInt(mapVo.get("resource_price").toString());
			detailVo.put("ass_id", id.split("@")[0]);
			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			detailVo.put("plan_id", mapVo.get("plan_id"));
			detailVo.put("plan_detail_id", mapVo.get("plan_detail_id"));
			detailVo.put("plan_no", mapVo.get("plan_no"));
			detailVo.put("ass_no", mapVo.get("ass_no"));
			detailVo.put("price", mapVo.get("resource_price"));

			if (detailVo.get("source_code") == null || "".equals(detailVo.get("source_code"))) {
				continue;
			}


			listVo.add(detailVo);
		}
		try {
			assPlanResourceJson = assPlanResourceService.saveResourceItem(listVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assPlanResourceJson);

	}

	// /
	/**
	 * 修改购置计划
	 */

	@RequestMapping(value = "/hrp/ass/assplandept/UpadateAssPlanDept")
	@ResponseBody
	public Map<String, Object> UpadateAssPlanDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("is_group", "0");

		AssPlanDept assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);
		if (assPlanDept != null) {
			if (assPlanDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"当前计划已保存,请勿重复保存 \"}");
			}
		}

		String create_date = String.valueOf(mapVo.get("create_date"));
		if (StringUtils.isNotEmpty(create_date)) {
			mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));
		}
		String yearmonths=create_date.replace("-", "");
		
		String yearmonth=yearmonths.substring(0, 6);
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
		String dept_id_no = mapVo.get("dept_id").toString();
		String source_id = mapVo.get("source_id").toString();
		mapVo.put("dept_id", dept_id_no.split("@")[0]);
		mapVo.put("dept_no", dept_id_no.split("@")[1]);
		mapVo.put("plan_money", "0");
		mapVo.put("state", "0");
		try {

			/*String assPlanDeptJson = assPlanDeptService.addOrUpdateAssPlanDept(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assPlanDeptJson);
			String plan_id = (String) jsonObj.get("plan_id");
			String plan_no = (String) jsonObj.get("plan_no");

			String assPlanDeptDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {
				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());
				detailVo.put("price",detailVo.get("sum_price"));
				detailVo.put("plan_id", plan_id);
				detailVo.put("plan_no", plan_no);
				detailVo.put("source_id", source_id);
				if (detailVo.get("plan_detail_id") == null) {
					detailVo.put("plan_detail_id", "0");
				}

				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}

				if (StringUtils.isNotEmpty((String) detailVo.get("need_date"))) {
					detailVo.put("need_date",
							DateUtil.stringToDate(detailVo.get("need_date").toString(), "yyyy-MM-dd"));
				}

				String id = detailVo.get("ass_id").toString();

				String fid = detailVo.get("fac_id").toString();
				// double sum_price= (Double) detailVo.get("sum_price");
				detailVo.put("ass_id", id.split("@")[0]);

				detailVo.put("ass_no", id.split("@")[1]);

				if(fid != null && fid.split("@").length == 2){
					detailVo.put("fac_id", fid.split("@")[0]);
					detailVo.put("fac_no", fid.split("@")[1]);
				}else{
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}
				
				// detailVo.put("sum_price", sum_price);

				assPlanDeptDetailJson = assPlanDeptDetailService.addOrUpdateAssPlanDeptDetail(detailVo);
				JSONObject json = JSONArray.parseObject(assPlanDeptDetailJson);
				String plan_detail_id = (String) json.get("plan_detail_id");
				detailVo.put("plan_detail_id", plan_detail_id);
				
				//assPlanResourceService.addAssPlanSource(detailVo);
			}
			JSONObject jsons = JSONArray.parseObject(assPlanDeptDetailJson);
			jsons.put("plan_id", plan_id);
			jsons.put("plan_no", plan_no);*/
			return JSONObject.parseObject(assPlanDeptService.addOrUpdateAssPlanDept(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 添加数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/addAssPlanDeptImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		Map<String, Object> allMapVo = new HashMap<String, Object>();
		String assPlanDeptJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("apply_id",mapVo.get("apply_ids"));
		try {
			
//			allMapVo.put("source_id", objectass.get("source_id"));
			List<AssApplyDeptDetail> dataillist = assApplyDeptDetailService.queryByAssApplyDeptDetailByPlanDept(mapVo);// 汇总相同资产的明细
			if (dataillist.size() > 0) {
				Map<String, Object> nMapVo = new HashMap<String, Object>();
				nMapVo.put("group_id", mapVo.get("group_id"));
				nMapVo.put("hos_id", mapVo.get("hos_id"));
				nMapVo.put("copy_code", mapVo.get("copy_code"));
				nMapVo.put("plan_year", mapVo.get("plan_year"));
				nMapVo.put("dept_id", mapVo.get("dept_id"));
				nMapVo.put("dept_no", mapVo.get("dept_no"));
				nMapVo.put("is_add", mapVo.get("is_add"));
				nMapVo.put("buy_code", mapVo.get("buy_code"));
				nMapVo.put("create_emp", SessionManager.getUserId());
				nMapVo.put("create_date", new Date());
				nMapVo.put("plan_money", "0");
				nMapVo.put("check_emp", null);
				nMapVo.put("check_date", null);
				nMapVo.put("audit_emp", null);
				nMapVo.put("audit_date", null);
				nMapVo.put("state", "0");
				nMapVo.put("is_group", "0");
				nMapVo.put("note", "引入购置申请");
				
				assPlanDeptJson = assPlanDeptService.addAssPlanDept(nMapVo);
                 
				Map<String, Object> object = (Map<String, Object>) ChdJson.fromJson(assPlanDeptJson);
				for (AssApplyDeptDetail temp : dataillist) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", temp.getGroup_id());
					map.put("hos_id", temp.getHos_id());
					map.put("copy_code", temp.getCopy_code());
					map.put("plan_id", object.get("plan_id"));
					map.put("plan_no", object.get("plan_no"));
					map.put("ass_id", temp.getAss_id());
					map.put("ass_no", temp.getAss_no());
					map.put("ass_spec", temp.getAss_spec());
					map.put("ass_model", temp.getAss_model());
					map.put("ass_brand", temp.getAss_brand());
					map.put("advice_price", 0);
					map.put("fac_id", temp.getFac_id());
					map.put("fac_no", temp.getFac_no());
					map.put("apply_id", temp.getApply_id());
					map.put("plan_amount", temp.getApply_amount());
					map.put("advice_price", temp.getAdvice_price());
					map.put("ass_usage_code", temp.getAss_usage_code());
					
					
					assPlanDeptJson=	assPlanDeptDetailService.addAssPlanDeptDetail(map);
					List<AssApplyDeptResource> listAssApply=assApplyDeptResourceService.queryAssPlanResourceListImport(map);
					for (AssApplyDeptResource assApplyDeptResource : listAssApply) {
						Map<String, Object> allObject = (Map<String, Object>) ChdJson.fromJson(assPlanDeptJson);
						allMapVo.put("group_id", temp.getGroup_id());
						allMapVo.put("hos_id", temp.getHos_id());
						allMapVo.put("copy_code", temp.getCopy_code());
						allMapVo.put("plan_id", object.get("plan_id"));
						allMapVo.put("plan_no", object.get("plan_no"));
						allMapVo.put("ass_id", temp.getAss_id());
						allMapVo.put("ass_no", temp.getAss_no());
						allMapVo.put("ass_spec", temp.getAss_spec());
						allMapVo.put("ass_model", temp.getAss_model());
						allMapVo.put("ass_brand", temp.getAss_brand());
						allMapVo.put("advice_price", 0);
						allMapVo.put("fac_id", temp.getFac_id());
						allMapVo.put("fac_no", temp.getFac_no());
						allMapVo.put("plan_amount", temp.getApply_amount());
						allMapVo.put("advice_price", temp.getAdvice_price());
						allMapVo.put("ass_usage_code", temp.getAss_usage_code());
						allMapVo.put("source_id", assApplyDeptResource.getSource_id());
						allMapVo.put("price", assApplyDeptResource.getPrice());
						allMapVo.put("plan_detail_id", allObject.get("plan_detail_id"));
						assPlanResourceService.addAssPlanSourceImport(allMapVo);
					}
					
				}

				for (String temp : mapVo.get("apply_ids").toString().split(",")) {// 循环生成单据的明细ID
					Map<String, Object> planApplyMapvo = new HashMap<String, Object>();
					planApplyMapvo.put("group_id", mapVo.get("group_id"));
					planApplyMapvo.put("hos_id", mapVo.get("hos_id"));
					planApplyMapvo.put("copy_code", mapVo.get("copy_code"));
					planApplyMapvo.put("plan_id", object.get("plan_id"));
					planApplyMapvo.put("apply_id", temp);

					assPlanDeptService.addAssPlanDeptImportBid(planApplyMapvo);
				}

				assPlanDeptJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + mapVo.get("group_id")
						+ "|" + mapVo.get("hos_id") + "|" + mapVo.get("copy_code") + "|" + object.get("plan_id") + "|"
						+ nMapVo.get("state") + "\"}";
			}
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPlanDeptJson);

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/updateToExaminePlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExaminePlanDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("check_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			AssPlanDept assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);
			 List<AssPlanDeptResource>            assPlanDeptResource  =assPlanResourceService.queryResourceAssPlanDeptResource(mapVo);
     if (assPlanDeptResource.size()==0) {
    	 return JSONObject.parseObject("{\"error\":\"没有资金来源,无法审核! \"}");
	}
			if (assPlanDept.getState() != 0) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
	
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"error\":\"不能重复审核! \"}");
		}

		try {
			assInMainJson = assPlanDeptService.updateToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 销审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/updateNotToExaminePlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExaminePlanDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			mapVo.put("check_date", new Date());

			AssPlanDept assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);

			/**
			 * 判断购置计划中的计划单号 是否已被 招标管理中 引用 如已被引用 则不能执行删除
			 */
			List<AssBidPlanMap> listPlan = assBidPlanMapService.queryAssBidPlanMapExists(mapVo);

			if (assPlanDept.getState() != 1 || listPlan.size() > 0) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不是审核状态或已被招标管理引用! \"}");
		}

		try {
			assInMainJson = assPlanDeptService.updateNotToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * 审批意见
	 * 
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptApprove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assPlanDeptApprove(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPlanDeptJson = null;

		String create_date = String.valueOf(mapVo.get("create_date"));
		if (StringUtils.isNotEmpty(create_date)) {
			mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));
		}
		String paramVo = (String) mapVo.get("paramVo");
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo1 = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("plan_id", ids[3]);
			mapVo.put("audit_emp", SessionManager.getUserId());

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
			try {
				assPlanDeptJson = assPlanDeptService.updateAssPlanDept(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		return JSONObject.parseObject(assPlanDeptJson);

	}

	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptViewApplyPage", method = RequestMethod.GET)
	public String assPlanDeptViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssApplyDept assApplyDept = new AssApplyDept();
		assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);

		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assApplyDept.getGroup_id());

		mode.addAttribute("hos_id", assApplyDept.getHos_id());

		mode.addAttribute("copy_code", assApplyDept.getCopy_code());

		mode.addAttribute("apply_id", assApplyDept.getApply_id());

		mode.addAttribute("apply_no", assApplyDept.getApply_no());

		mode.addAttribute("apply_year", assApplyDept.getApply_year());

		mode.addAttribute("apply_month", assApplyDept.getApply_month());

		mode.addAttribute("dept_id", assApplyDept.getDept_id());

		mode.addAttribute("dept_no", assApplyDept.getDept_no());

		mode.addAttribute("dept_name", assApplyDept.getDept_name());

		mode.addAttribute("apply_emp", assApplyDept.getApply_emp());

		if (assApplyDept.getApply_date() == null) {
			mode.addAttribute("apply_date", assApplyDept.getApply_date());
		} else {
			mode.addAttribute("apply_date", a.format(assApplyDept.getApply_date()));
		}

		mode.addAttribute("apply_emp_name", assApplyDept.getApply_emp_name());

		mode.addAttribute("apply_money", assApplyDept.getApply_money());

		mode.addAttribute("summary", assApplyDept.getSummary());

		mode.addAttribute("create_emp", assApplyDept.getCreate_emp());

		mode.addAttribute("create_emp_name", assApplyDept.getCreate_emp_name());

		if (assApplyDept.getCreate_date() == null) {
			mode.addAttribute("create_date", assApplyDept.getCreate_date());
		} else {
			mode.addAttribute("create_date", a.format(assApplyDept.getCreate_date()));
		}

		mode.addAttribute("check_emp", assApplyDept.getCheck_emp());

		mode.addAttribute("check_emp_name", assApplyDept.getCheck_emp_name());

		if (assApplyDept.getCheck_date() == null) {
			mode.addAttribute("check_date", assApplyDept.getCheck_date());
		} else {
			mode.addAttribute("check_date", a.format(assApplyDept.getCheck_date()));
		}

		mode.addAttribute("is_add", assApplyDept.getIs_add());

		mode.addAttribute("state", assApplyDept.getState());

		mode.addAttribute("note", assApplyDept.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandept/assPlanDeptViewApply";
	}

	/**
	 * @Description 更新跳转页面 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/assPlanDeptUpdatePage", method = RequestMethod.GET)
	public String assPlanDeptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPlanDept assPlanDept = new AssPlanDept();
		List<AssPlanDeptResource>  assPlanResource=null;
		assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);
//		 assPlanResource=assPlanResourceService.queryAssPlanDeptResource(getPage(mapVo));
//		AssPlanDeptResource assPlanResource=new AssPlanDeptResource();
			
		assPlanResource=assPlanResourceService.queryResource(mapVo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assPlanDept.getGroup_id());
		mode.addAttribute("hos_id", assPlanDept.getHos_id());
		mode.addAttribute("copy_code", assPlanDept.getCopy_code());
		mode.addAttribute("plan_id", assPlanDept.getPlan_id());
		mode.addAttribute("plan_no", assPlanDept.getPlan_no());
		mode.addAttribute("plan_year", assPlanDept.getPlan_year());
		mode.addAttribute("dept_id", assPlanDept.getDept_id());
		mode.addAttribute("dept_no", assPlanDept.getDept_no());
		mode.addAttribute("plan_money", assPlanDept.getPlan_money());
		mode.addAttribute("create_emp", assPlanDept.getCreate_emp());// DateUtil.stringToDate
		mode.addAttribute("create_emp_name", assPlanDept.getCreate_emp_name());
		mode.addAttribute("dept_code", assPlanDept.getDept_code());
		mode.addAttribute("dept_name", assPlanDept.getDept_name());
		mode.addAttribute("create_emp_name", assPlanDept.getCreate_emp_name());
		/*
		 * mode.addAttribute("source_code", assPlanResource.getSource_code());
		 * mode.addAttribute("source_name", assPlanResource.getSource_name());
		 */

		if (assPlanDept.getCreate_date() == null) {
			mode.addAttribute("create_date", assPlanDept.getCreate_date());
		} else {
			mode.addAttribute("create_date", sdf.format(assPlanDept.getCreate_date()));
		}
		mode.addAttribute("check_emp", assPlanDept.getCheck_emp());
		if (assPlanDept.getCheck_date() == null) {
			mode.addAttribute("check_date", assPlanDept.getCheck_date());
		} else {
			mode.addAttribute("check_date", sdf.format(assPlanDept.getCheck_date()));
		}
		if (assPlanDept.getAudit_date() == null) {
			mode.addAttribute("audit_date", assPlanDept.getAudit_date());
		} else {
			mode.addAttribute("audit_date", sdf.format(assPlanDept.getAudit_date()));
		}
		mode.addAttribute("is_add", assPlanDept.getIs_add());
		mode.addAttribute("state", assPlanDept.getState());
		mode.addAttribute("buy_code", assPlanDept.getBuy_code());
		mode.addAttribute("note", assPlanDept.getNote());
		mode.addAttribute("is_group", assPlanDept.getIs_group());
	  if (assPlanResource.size()==0) {
		  mode.addAttribute("source_id","1");
	}else{
//		mode.addAttribute("source_id",assPlanResource.);
		}
	  
	  mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
	  mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
	  mode.addAttribute("ass_05008",MyConfig.getSysPara("05008"));
	  mode.addAttribute("ass_05009",MyConfig.getSysPara("05009"));
	  
		return "hrp/ass/assplandept/assPlanDeptUpdate";
	}

	/**
	 * @Description 更新数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/updateAssPlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPlanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String create_date = String.valueOf(mapVo.get("create_date"));
		mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));
		String check_date = String.valueOf(mapVo.get("check_date"));
		mapVo.put("check_date", DateUtil.stringToDate(check_date, "yyyy-MM-dd"));
		String audit_date = String.valueOf(mapVo.get("audit_date"));
		mapVo.put("audit_date", DateUtil.stringToDate(audit_date, "yyyy-MM-dd"));

		String dept_id_no = mapVo.get("dept_id").toString();
		mapVo.put("dept_id", dept_id_no.split("@")[0]);
		mapVo.put("dept_no", dept_id_no.split("@")[1]);
		try {
			String assPlanDeptJson = assPlanDeptService.updateAssPlanDept(mapVo);

			return JSONObject.parseObject(assPlanDeptJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/addOrUpdateAssPlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssPlanDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assPlanDeptJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			try {
				assPlanDeptJson = assPlanDeptService.addOrUpdateAssPlanDept(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assPlanDeptJson);
	}

	/**
	 * @Description 删除数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/deleteAssPlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPlanDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assPlanDeptJson = "";

		boolean flag = true;

		boolean flag2 = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_id", ids[3]);

			AssPlanDept assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);

			if (assPlanDept.getState() != 0) {

				flag = false;

				break;

			}

			/**
			 * 判断购置计划中的计划单号 是否已被 招标管理中 引用 如已被引用 则不能执行删除
			 */
			List<AssBidPlanMap> listPlan = assBidPlanMapService.queryAssBidPlanMapExists(mapVo);

			if (listPlan.size() > 0) {
				flag2 = false;
				break;
			}

			listVo.add(mapVo);

		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建才能进行删除! \"}");
		}
		if (flag2 == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 当前数据已被招标管理中引用  请先删除招标管理! \"}");
		}

		try {
			assApplyPlanMapService.deleteBatchAssApplyPlanMap(listVo);
			assPlanResourceService.deleteAssPlanResource(listVo);
			assPlanDeptJson = assPlanDeptService.deleteBatchAssPlanDept(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assPlanDeptJson);

	}

	/**
	 * @Description 查询数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/queryAssPlanDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();
		if (!dept_id_no.equals("")) {
			mapVo.put("dept_id", dept_id_no.split("@")[0]);
			// mapVo.put("dept_no", dept_id_no.split("@")[1]);
		}
		mapVo.put("is_group", "0");
		String assPlanDept = assPlanDeptService.queryAssPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);

	}

	/**
	 * 查询明细资金来源
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/queryAssPlanResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryAssPlanResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String plan_id_no = mapVo.get("plan_id") == null ? "" : mapVo.get("plan_id").toString();
		if (!plan_id_no.equals("")) {
			mapVo.put("plan_id", plan_id_no);
		}
		String plan_detail_no = mapVo.get("plan_detail_id") == null ? "" : mapVo.get("plan_detail_id").toString();
		if (!plan_detail_no.equals("")) {
			mapVo.put("plan_detail_id", plan_detail_no);
		}
		
		String  assPlanResource=assPlanResourceService.queryAssPlanResourceList(getPage(mapVo));

		return JSONObject.parseObject(assPlanResource);
		

		// return JSONObject.parseObject(assPlanDept);

	}

	/**
	 * @Description 查询数据 050301 资产购置计划明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/queryAssPlanDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assPlanDeptDetail = assPlanDeptDetailService.queryAssPlanDeptDetail(getPage(mapVo));

		return JSONObject.parseObject(assPlanDeptDetail);
	}

	/**
	 * @Description 删除数据 050301 资产购置计划明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/deleteAssPlanDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPlanDeptDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_detail_id", ids[3]);

			mapVo.put("plan_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			assPlanResourceService.updateDeleteAssPlanResource(listVo);
			String assPlanDeptDetailJson = assPlanDeptDetailService.deleteBatchAssPlanDeptDetail(listVo);
                    
			return JSONObject.parseObject(assPlanDeptDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/queryAssPlanDepts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDepts(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();
		if (!dept_id_no.equals("")) {
			mapVo.put("dept_id", dept_id_no.split("@")[0]);
			mapVo.put("dept_no", dept_id_no.split("@")[1]);
		}

		mapVo.put("is_plan_dept", "1");
		String assPlanDept = assPlanDeptService.queryAssPlanDepts(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);

	}
	/***
	 * 打印状态
	 */
	@RequestMapping(value = "/hrp/ass/assplandept/queryPlanDeptState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPlanDeptState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//打印时校验数据专用
		List<String> list = assPlanDeptService.queryPlanDeptState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"购置计划单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
}
