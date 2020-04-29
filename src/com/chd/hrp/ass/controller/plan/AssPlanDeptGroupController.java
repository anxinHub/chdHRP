/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

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
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.apply.AssApplyPlanMap;
import com.chd.hrp.ass.entity.bid.AssBidPlanMap;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
import com.chd.hrp.ass.entity.plan.AssPlanGroupHos;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;
import com.chd.hrp.ass.service.apply.AssApplyDeptDetailService;
import com.chd.hrp.ass.service.apply.AssApplyDeptService;
import com.chd.hrp.ass.service.apply.AssApplyPlanMapService;
import com.chd.hrp.ass.service.bid.AssBidPlanMapService;
import com.chd.hrp.ass.service.plan.AssPlanDeptDetailService;
import com.chd.hrp.ass.service.plan.AssPlanDeptService;
import com.chd.hrp.ass.service.plan.AssPlanGroupHosService;
import com.chd.hrp.ass.service.resource.AssApplyDeptResourceService;
import com.chd.hrp.ass.service.resource.AssPlanResourceService;
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
public class AssPlanDeptGroupController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPlanDeptGroupController.class);

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	@Resource(name = "assPlanDeptService")
	private final AssPlanDeptService assPlanDeptService = null;

	@Resource(name = "assPlanDeptDetailService")
	private final AssPlanDeptDetailService assPlanDeptDetailService = null;

	@Resource(name = "assApplyDeptDetailService")
	private final AssApplyDeptDetailService assApplyDeptDetailService = null;

	@Resource(name = "assPlanGroupHosService")
	private final AssPlanGroupHosService assPlanGroupHosService = null;

	@Resource(name = "assBidPlanMapService")
	private final AssBidPlanMapService assBidPlanMapService = null;
	
	@Resource(name = "assApplyPlanMapService")
	private final AssApplyPlanMapService assApplyPlanMapService = null;
	
	@Resource(name = "assApplyDeptService")
	private final AssApplyDeptService assApplyDeptService = null;
	
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupMainPage", method = RequestMethod.GET)
	public String assPlanDeptGroupMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05009",MyConfig.getSysPara("05009"));
		
		return "hrp/ass/assplandeptgroup/assPlanDeptGroupMain";
	}

	/**
	 * @Description 跳转引入购置申请页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupImportApplyPage", method = RequestMethod.GET)
	public String assPlanDeptImportGroupApplyPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandeptgroup/assPlanDeptGroupImportApply";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupAddPage", method = RequestMethod.GET)
	public String assPlanDeptAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assplandeptgroup/assPlanDeptGroupAdd";

	}

	/**
	 * @Description 审批页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupApprovePage", method = RequestMethod.GET)
	public String assPlanDeptGroupApprovePage(Model mode, @RequestParam(value = "ParamVo") String paramVo)
			throws Exception {
		mode.addAttribute("paramVo", paramVo);

		return "hrp/ass/assplandeptgroup/assPlanDeptGroupApprove";

	}

	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assImportHosPlanDeptGroupPage", method = RequestMethod.GET)
	public String assImportHosPlanDeptGroupPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandeptgroup/assImportHosPlanDeptGroup";

	}
	
	
	/**
	 * @Description 追溯购置申请单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assViewApplyGroupPage", method = RequestMethod.GET)
	public String assViewApplyGroupPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("plan_id", mapVo.get("plan_id"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandeptgroup/assPlanViewApplyGroup";
	}
	
	
	/**
	 * @Description 追溯购置申请单
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assViewPlanGroupPage", method = RequestMethod.GET)
	public String assViewPlanGroupPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("plan_id", mapVo.get("plan_id"));

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assplandeptgroup/assViewHosPlanDeptGroup";
	}
	
	
	//追溯购置申请
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssApplyDeptByApplyGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApplyGroup(@RequestParam Map<String, Object> mapVo, Model mode)
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/addAssPlanDeptGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptGroup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssPlanDept assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);
		
		if(assPlanDept != null){
			if(assPlanDept.getState() > 0){
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

		mapVo.put("check_emp", null);

		mapVo.put("check_date", null);

		mapVo.put("audit_emp", null);

		mapVo.put("audit_date", null);

		mapVo.put("plan_money", "0");

		mapVo.put("state", "0");

		mapVo.put("is_group", "1");

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

				detailVo.put("ass_id", id.split("@")[0]);

				detailVo.put("ass_no", id.split("@")[1]);

				if(fid != null && fid.split("@").length == 2){
					detailVo.put("fac_id", fid.split("@")[0]);
					detailVo.put("fac_no", fid.split("@")[1]);
				}else{
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}
				

				assPlanDeptDetailJson = assPlanDeptDetailService.addOrUpdateAssPlanDeptDetail(detailVo);
				JSONObject json = JSONArray.parseObject(assPlanDeptDetailJson);
				String plan_detail_id = (String) json.get("plan_detail_id");
				detailVo.put("plan_detail_id", plan_detail_id);
				// 添加资金来源
				//	assPlanResourceService.addAssPlanSource(detailVo);
				//if (null == detailVo.get("source_id")) {
				//	detailVo.put("source_id", 1);
				//	assPlanResourceService.addAssPlanSource(detailVo);
				//	continue;

				//}
			}

			JSONObject json = JSONArray.parseObject(assPlanDeptDetailJson);

			json.put("plan_id", plan_id);

			json.put("plan_no", plan_no);*/

			return JSONObject.parseObject(assPlanDeptService.addOrUpdateAssPlanDept(mapVo));

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	/**
	 * 查询明细资金来源
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssPlanResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryAssPlanResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String plan_id_no = mapVo.get("plan_id") == null ? "" : mapVo.get("plan_id").toString();
		if (!plan_id_no.equals("")) {
			mapVo.put("plan_id", plan_id_no);
			// mapVo.put("dept_no", dept_id_no.split("@")[1]);
		}
		String plan_detail_no = mapVo.get("plan_detail_id") == null ? "" : mapVo.get("plan_detail_id").toString();
		if (!plan_detail_no.equals("")) {
			mapVo.put("plan_detail_id", plan_detail_no);
		}
		String  assPlanResource=assPlanResourceService.queryAssPlanResourceList(getPage(mapVo));

		return JSONObject.parseObject(assPlanResource);

	}
	/**
	 * 修改资金来源 saveResourceItem
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/saveResourceItem", method = RequestMethod.POST)
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
	 * @Description 添加数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/addAssPlanDeptGroupImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptGroupImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> allMapVo = new HashMap<String, Object>();
		String assPlanDeptJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("apply_id",mapVo.get("apply_ids"));
		try {
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
				nMapVo.put("is_group", "1");
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

					assPlanDeptJson= assPlanDeptDetailService.addAssPlanDeptDetail(map);
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
				assPlanDeptJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\""
						+mapVo.get("group_id") + "|" + mapVo.get("hos_id")+ "|" + mapVo.get("copy_code") + "|"+ object.get("plan_id") + "|"+nMapVo.get("state")+"\"}";
			}
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPlanDeptJson);

	}

	@RequestMapping(value = "/hrp/ass/assplandeptgroup/importHosPlanGroupDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHosPlanGroupDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		Map<String, Object> allMapVo = new HashMap<String, Object>();
		String assInMainJson = "";
		String plan_id = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("plan_id",mapVo.get("plan_ids"));
		try {
			List<AssPlanDeptDetail> list = assPlanDeptDetailService.queryAssPlanDeptDetailList(mapVo);
			if (list.size() > 0) {
				Map<String, Object> mainMapVo = new HashMap<String, Object>();
				mainMapVo.put("group_id", mapVo.get("group_id"));
				mainMapVo.put("hos_id", mapVo.get("hos_id"));
				mainMapVo.put("copy_code", mapVo.get("copy_code"));
				mainMapVo.put("dept_id", mapVo.get("dept_id"));
				mainMapVo.put("dept_no", mapVo.get("dept_no"));
				mainMapVo.put("plan_year", mapVo.get("plan_year"));
				mainMapVo.put("create_emp", SessionManager.getUserId());
				mainMapVo.put("create_date", new Date());
				mainMapVo.put("id_add", mapVo.get("id_add"));
				mainMapVo.put("state", "0");
				mainMapVo.put("buy_code", mapVo.get("buy_code"));
				mainMapVo.put("is_group", "1");
				mainMapVo.put("plan_id", "0");
				mainMapVo.put("note", "引入医院购置计划");
				String str = assPlanDeptService.addAssPlanDept(mainMapVo);
				JSONObject jsonObj = JSONArray.parseObject(str);
				plan_id = (String) jsonObj.get("plan_id");
				String plan_no = (String) jsonObj.get("plan_no");
				for (AssPlanDeptDetail temp : list) {
					Map<String, Object> detailMapVo = new HashMap<String, Object>();
					detailMapVo.put("group_id", temp.getGroup_id());
					detailMapVo.put("hos_id", temp.getHos_id());
					detailMapVo.put("copy_code", temp.getCopy_code());
					detailMapVo.put("plan_id", plan_id);
					detailMapVo.put("plan_no", plan_no);
					detailMapVo.put("ass_id", temp.getAss_id());
					detailMapVo.put("ass_no", temp.getAss_no());
					detailMapVo.put("ass_spec", temp.getAss_spec());
					detailMapVo.put("ass_model", temp.getAss_model());
					detailMapVo.put("ass_brand", temp.getAss_brand());
					detailMapVo.put("fac_id", temp.getFac_id());
					detailMapVo.put("fac_no", temp.getFac_no());
					detailMapVo.put("plan_id", temp.getPlan_id());
					detailMapVo.put("plan_amount", temp.getPlan_amount());
					detailMapVo.put("advice_price", temp.getAdvice_price());
					detailMapVo.put("ass_usage_code", temp.getAss_usage_code());
					detailMapVo.put("plan_detail_id", "0");
					assInMainJson=	assPlanDeptDetailService.addOrUpdateAssPlanDeptDetail(detailMapVo);
					List<AssPlanDeptResource> listAssApply=assPlanResourceService.queryAssPlanResourceListImport(detailMapVo);
					for (AssPlanDeptResource assApplyDeptResource : listAssApply) {
						Map<String, Object> allObject = (Map<String, Object>) ChdJson.fromJson(assInMainJson);
						allMapVo.put("group_id", temp.getGroup_id());
						allMapVo.put("hos_id", temp.getHos_id());
						allMapVo.put("copy_code", temp.getCopy_code());
						allMapVo.put("plan_id", plan_id);
						allMapVo.put("plan_no", plan_no);
						allMapVo.put("ass_id", temp.getAss_id());
						allMapVo.put("ass_no", temp.getAss_no());
						allMapVo.put("ass_spec", temp.getAss_spec());
						allMapVo.put("ass_model", temp.getAss_model());
						allMapVo.put("ass_brand", temp.getAss_brand());
						allMapVo.put("advice_price", 0);
						allMapVo.put("fac_id", temp.getFac_id());
						allMapVo.put("fac_no", temp.getFac_no());
						allMapVo.put("plan_amount", temp.getPlan_amount());
						allMapVo.put("advice_price", temp.getAdvice_price());
						allMapVo.put("ass_usage_code", temp.getAss_usage_code());
						allMapVo.put("source_id", assApplyDeptResource.getSource_id());
						allMapVo.put("price", assApplyDeptResource.getPrice());
						allMapVo.put("plan_detail_id", allObject.get("plan_detail_id"));
						assPlanResourceService.addAssPlanSourceImport(allMapVo);
					}
				}
			}

			String[] plan_ids = mapVo.get("plan_ids").toString().split(",");
			String[] group_ids = mapVo.get("group_ids").toString().split(",");
			String[] hos_ids = mapVo.get("hos_ids").toString().split(",");
			String[] copy_codes = mapVo.get("copy_codes").toString().split(",");
			
			
			for (int i = 0; i < plan_ids.length; i++) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				entityMap.put("group_id", mapVo.get("group_id"));
				entityMap.put("hos_id", mapVo.get("hos_id"));
				entityMap.put("copy_code", mapVo.get("copy_code"));
				entityMap.put("hos_plan_id", plan_ids[i]);
				entityMap.put("hos_group_id", group_ids[i]);
				entityMap.put("hos_hos_id", hos_ids[i]);
				entityMap.put("hos_copy_code", copy_codes[i]);
				entityMap.put("group_plan_id", plan_id);

				assInMainJson = assPlanGroupHosService.addAssPlanGroupHos(entityMap);
			}
			
			assInMainJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\""
					+mapVo.get("group_id") + "|" + mapVo.get("hos_id")+ "|" + mapVo.get("copy_code") + "|"+ plan_id + "|0\"}";

			return JSONObject.parseObject(assInMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/updateToExamineGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamineGroup(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			}else{
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/updateNotToExamineGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineGroup(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			}else{
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupApprove", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assPlanDeptGroupApprove(@RequestParam Map<String, Object> mapVo, Model mode)
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

	/**
	 * @Description 更新跳转页面 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupUpdatePage", method = RequestMethod.GET)
	public String assPlanDeptGroupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPlanDept assPlanDept = new AssPlanDept();

		assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);
		List<AssPlanDeptResource> assPlanResource=null;
		
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
		mode.addAttribute("dept_code", assPlanDept.getDept_code());
		mode.addAttribute("dept_name", assPlanDept.getDept_name());
		mode.addAttribute("create_emp_name", assPlanDept.getCreate_emp_name());
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
			//mode.addAttribute("source_id",assPlanResource.getSource_id());
			}
	
		  mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		  mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		  mode.addAttribute("ass_05008",MyConfig.getSysPara("05008"));
		  mode.addAttribute("ass_05009",MyConfig.getSysPara("05009"));
		  
		return "hrp/ass/assplandeptgroup/assPlanDeptGroupUpdate";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupViewPage", method = RequestMethod.GET)
	public String assPlanDeptGroupViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPlanDept assPlanDept = new AssPlanDept();

		assPlanDept = assPlanDeptService.queryAssPlanDeptByCode(mapVo);

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
		mode.addAttribute("dept_code", assPlanDept.getDept_code());
		mode.addAttribute("dept_name", assPlanDept.getDept_name());
		mode.addAttribute("create_emp_name", assPlanDept.getCreate_emp_name());
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

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assplandeptgroup/assPlanDeptGroupView";
	}
	
	
	
	
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/assPlanDeptGroupViewApplyPage", method = RequestMethod.GET)
	public String assPlanDeptGroupViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		return "hrp/ass/assplandeptgroup/assPlanDeptGroupViewApply";
	}
	// /
		/**
		 * 修改购置计划
		 */

		@RequestMapping(value = "/hrp/ass/assplandeptgroup/UpadateAssPlanDeptGroup")
		@ResponseBody
		public Map<String, Object> UpadateAssPlanDeptGroup(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("is_group", "1");

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
	 * @Description 更新数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/updateAssPlanDeptGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPlanDeptGroup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/addOrUpdateAssPlanDeptGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssPlanDeptGroup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assPlanDeptJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());
			detailVo.put("is_group", "1");
			try {
				assPlanDeptJson = assPlanDeptService.addOrUpdateAssPlanDept(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assPlanDeptJson);
	}
//updateDeleteAssPlanDeptGroup
	/**
	 * @Description 修改删除数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/updateDeleteAssPlanDeptGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeleteAssPlanDeptGroup(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assPlanDeptJson;

		boolean flag = true;

		boolean flag1 = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("plan_detail_id", ids[3]);
			
			mapVo.put("plan_id", ids[4]);
			

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
				flag1 = false;
				break;
			}

			listVo.add(mapVo);

		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建才能进行删除! \"}");
		}
		if (flag1 == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 当前数据已被招标管理中引用  请先删除招标管理! \"}");
		}
		try {
			assPlanGroupHosService.deleteBatchAssPlanGroupHos(listVo);
			assApplyPlanMapService.deleteBatchAssApplyPlanMap(listVo);
			assPlanDeptJson = assPlanDeptService.deleteBatchAssPlanDept(listVo);
			assPlanResourceService.updateDeleteAssPlanResource(listVo);
			return JSONObject.parseObject(assPlanDeptJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 删除数据 050301 购置计划单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/deleteAssPlanDeptGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPlanDeptGroup(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assPlanDeptJson;
//		AssPlanDeptResource assPlanResource=new AssPlanDeptResource();
		List<AssPlanDeptResource>	assPlanResource=null;
		boolean flag = true;

		boolean flag1 = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			//mapVo.put("plan_detail_id", ids[3]);
			
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
				flag1 = false;
				break;
			}

			listVo.add(mapVo);
			  assPlanResource=assPlanResourceService.queryResourceDelete(mapVo);

		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建才能进行删除! \"}");
		}
		if (flag1 == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 当前数据已被招标管理中引用  请先删除招标管理! \"}");
		}
		try {
			assPlanGroupHosService.deleteBatchAssPlanGroupHos(listVo);
			assApplyPlanMapService.deleteBatchAssApplyPlanMap(listVo);
			assPlanDeptJson = assPlanDeptService.deleteBatchAssPlanDept(listVo);
			if (assPlanResource.size()>0) {
				assPlanResourceService.deleteAssPlanResource(listVo);
			}
			
			return JSONObject.parseObject(assPlanDeptJson);
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssPlanDeptGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDeptGroup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("is_group", "1");
		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();
		if (!dept_id_no.equals("")) {
			mapVo.put("dept_id", dept_id_no.split("@")[0]);
			// mapVo.put("dept_no", dept_id_no.split("@")[1]);
		}

		String assPlanDept = assPlanDeptService.queryAssPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);

	}

	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssPlanDeptGroupHosView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDeptGroupHosView(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assPlanDept = assPlanDeptService.queryAssPlanDeptsByHosGroup(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssPlanDeptGroupHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDeptGroupHos(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		//mapVo.put("hos_id", SessionManager.getCopyCode());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("is_group", "0");
		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();
		if (!dept_id_no.equals("")) {
			mapVo.put("dept_id", dept_id_no.split("@")[0]);
		}

		String assPlanDept = assPlanDeptService.queryAssPlanDeptByGroup(getPage(mapVo));

		return JSONObject.parseObject(assPlanDept);

	}
	

	/**
	 * @Description 查询数据 050301 资产购置计划明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssPlanDeptGroupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDeptGroupDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/deleteAssPlanDeptGroupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPlanDeptGroupDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryAssPlanDeptsGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPlanDeptsGroup(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
	@RequestMapping(value = "/hrp/ass/assplandeptgroup/queryPlanGroupDeptState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPlanGroupDeptState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//打印时校验数据专用
		List<String> list = assPlanGroupHosService.queryPlanGroupDeptState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"集团购置计划单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
}
