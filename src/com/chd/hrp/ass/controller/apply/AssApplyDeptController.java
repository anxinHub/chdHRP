package com.chd.hrp.ass.controller.apply;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.ass.entity.apply.AssApplyPlanMap;
import com.chd.hrp.ass.entity.resource.AssApplyDeptResource;
import com.chd.hrp.ass.entity.resource.AssPlanDeptResource;
import com.chd.hrp.ass.service.apply.AssApplyDeptDetailService;
import com.chd.hrp.ass.service.apply.AssApplyDeptProofService;
import com.chd.hrp.ass.service.apply.AssApplyDeptService;
import com.chd.hrp.ass.service.apply.AssApplyPlanMapService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.resource.AssApplyDeptResourceService;
import com.chd.hrp.ass.service.resource.AssPlanResourceService;

/**
 * 
 * @Description: 050201 购置申请单
 * @Table: ASS_APPLY_DEPT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssApplyDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(AssApplyDeptController.class);

	// 引入Service服务
	@Resource(name = "assApplyDeptService")
	private final AssApplyDeptService assApplyDeptService = null;

	@Resource(name = "assApplyDeptDetailService")
	private final AssApplyDeptDetailService assApplyDeptDetailService = null;
	
	@Resource(name = "assApplyDeptProofService")
	private final AssApplyDeptProofService assApplyDeptProofService = null;


	@Resource(name = "assApplyPlanMapService")
	private final AssApplyPlanMapService assApplyPlanMapService = null;

	@Resource(name = "assApplyDeptResourceService")
	private final AssApplyDeptResourceService assApplyDeptResourceService = null;
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/assApplyDeptMainPage", method = RequestMethod.GET)
	public String assApplyDeptMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05008",MyConfig.getSysPara("05008"));
		return "hrp/ass/assapplydept/assApplyDeptMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/assApplyDeptAddPage", method = RequestMethod.GET)
	public String assApplyDeptAddPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/assapplydept/assApplyDeptAdd";

	}

	/**
	 * @Description 添加数据 050201 购置申请单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/addAssApplyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssApplyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//String assApplyDeptDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		AssApplyDept assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);
		if(assApplyDept != null){
			if(assApplyDept.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"当前单据已被审核,不能操作 \"}");
			}
		}

		String apply_date = String.valueOf(mapVo.get("apply_date"));

		if (StringUtils.isNotEmpty(apply_date)) {
			mapVo.put("apply_date", DateUtil.stringToDate(apply_date, "yyyy-MM-dd"));
		}
		
		String yearmonth = mapVo.get("apply_year").toString() + mapVo.get("apply_month").toString();
		
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
		}
		
		//当前会计期间
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		
		
		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();
		
		//String source_id = mapVo.get("source_id").toString();

		mapVo.put("dept_id", dept_id_no.split("@")[0]);

		mapVo.put("dept_no", dept_id_no.split("@")[1]);

		mapVo.put("create_emp", SessionManager.getUserId());

		Date date = new Date();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("create_date", DateUtil.stringToDate(format.format(date), "yyyy-MM-dd"));

		mapVo.put("check_emp", null);

		mapVo.put("check_date", null);

		mapVo.put("apply_money", "0");

		mapVo.put("state", "0");
		
		/*String assApplyDeptJson = "";
		try {
			assApplyDeptJson = assApplyDeptService.addOrUpdateAssApplyDept(mapVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}
		JSONObject jsonObj = JSONArray.parseObject(assApplyDeptJson);

		String apply_id = (String) jsonObj.get("apply_id");

		String apply_no = (String) jsonObj.get("apply_no");

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {
			
			if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
				continue;
			}

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			detailVo.put("apply_id", apply_id);

			detailVo.put("apply_no", apply_no);
            detailVo.put("source_id", source_id);
			if (detailVo.get("detail_id") == null) {
				detailVo.put("detail_id", "0");
			}

			if (StringUtils.isNotEmpty((String) detailVo.get("need_date"))) {
				detailVo.put("need_date", DateUtil.stringToDate(detailVo.get("need_date").toString(), "yyyy-MM-dd"));
			}

			if (detailVo.get("ass_id") != null) {

				String ass_id_no = detailVo.get("ass_id").toString();

				detailVo.put("ass_id", ass_id_no.split("@")[0]);

				detailVo.put("ass_no", ass_id_no.split("@")[1]);

			}

			if (detailVo.get("fac_id") != null) {

				String fac_id_no = detailVo.get("fac_id").toString();
				if(fac_id_no != null && fac_id_no.split("@").length == 2){
					detailVo.put("fac_id", fac_id_no.split("@")[0]);
					detailVo.put("fac_no", fac_id_no.split("@")[1]);
				}else{
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}
			}

			try {

				assApplyDeptDetailJson = assApplyDeptDetailService.addOrUpdateAssApplyDeptDetail(detailVo);
				JSONObject json = JSONArray.parseObject(assApplyDeptDetailJson);
				String detail_id = (String) json.get("detail_id");
				String money=(String)json.get("apply_money");
				detailVo.put("price", money);
				detailVo.put("detail_id", detail_id);
			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

			}
		}

		JSONObject json = JSONArray.parseObject(assApplyDeptDetailJson);

		json.put("apply_id", apply_id);

		json.put("apply_no", apply_no);*/
		/*json.put("apply_money", apply_money);*/

		return JSONObject.parseObject(assApplyDeptService.addOrUpdateAssApplyDept(mapVo));

	}
	/**
	 * 修改购置申请计划
	 */
	@RequestMapping(value="/hrp/ass/assapplydept/UpadateAssApplyDept")
	@ResponseBody
	public Map<String, Object> UpadateAssApplyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assApplyDeptDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		AssApplyDept assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);
		if(assApplyDept != null){
			if(assApplyDept.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"当前单据已被审核,不能操作 \"}");
			}
		}

		/*String apply_date = String.valueOf(mapVo.get("apply_date"));

		if (StringUtils.isNotEmpty(apply_date)) {
			mapVo.put("apply_date", DateUtil.stringToDate(apply_date, "yyyy-MM-dd"));
		}
		String yearmonths=apply_date.replace("-", "");
		
		String yearmonth=yearmonths.substring(0, 6);*/
		
		String apply_date = String.valueOf(mapVo.get("apply_date"));

		if (StringUtils.isNotEmpty(apply_date)) {
			mapVo.put("apply_date", DateUtil.stringToDate(apply_date, "yyyy-MM-dd"));
		}
		
		String yearmonth=mapVo.get("apply_year").toString()+mapVo.get("apply_month").toString();
		
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		} 
		
		//当前会计期间
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
				
		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();
		String source_id = mapVo.get("source_id").toString();

		mapVo.put("dept_id", dept_id_no.split("@")[0]);

		mapVo.put("dept_no", dept_id_no.split("@")[1]);

		mapVo.put("create_emp", SessionManager.getUserId());

		Date date = new Date();

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("create_date", DateUtil.stringToDate(format.format(date), "yyyy-MM-dd"));

		mapVo.put("check_emp", null);

		mapVo.put("check_date", null);

		mapVo.put("apply_money", "0");

		mapVo.put("state", "0");
		/*String assApplyDeptJson = "";
		try {
			assApplyDeptJson = assApplyDeptService.addOrUpdateAssApplyDept(mapVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}
		JSONObject jsonObj = JSONArray.parseObject(assApplyDeptJson);

		String apply_id = (String) jsonObj.get("apply_id");

		String apply_no = (String) jsonObj.get("apply_no");

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {
			
			if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
				continue;
			}

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			detailVo.put("apply_id", apply_id);

			detailVo.put("apply_no", apply_no);
			detailVo.put("source_id", source_id);

			if (detailVo.get("detail_id") == null) {
				detailVo.put("detail_id", "0");
			}

			if (StringUtils.isNotEmpty((String) detailVo.get("need_date"))) {
				detailVo.put("need_date", DateUtil.stringToDate(detailVo.get("need_date").toString(), "yyyy-MM-dd"));
			}

			if (detailVo.get("ass_id") != null) {

				String ass_id_no = detailVo.get("ass_id").toString();

				detailVo.put("ass_id", ass_id_no.split("@")[0]);

				detailVo.put("ass_no", ass_id_no.split("@")[1]);

			}

			if (detailVo.get("fac_id") != null) {

				String fac_id_no = detailVo.get("fac_id").toString();

				detailVo.put("fac_id", fac_id_no.split("@")[0]);

				detailVo.put("fac_no", fac_id_no.split("@")[1]);

			}

			try {

				assApplyDeptDetailJson = assApplyDeptDetailService.addOrUpdateAssApplyDeptDetail(detailVo);
				JSONObject json = JSONArray.parseObject(assApplyDeptDetailJson);
				String detail_id = (String) json.get("detail_id");
				String money=(String)json.get("apply_money");
				detailVo.put("price", money);
				detailVo.put("detail_id", detail_id);
				//assApplyDeptResourceService.saveResourceItem(detailVo);
				// 添加资金来源
				//if (null == detailVo.get("source_id")) {
				//	detailVo.put("source_id", 1);
					
				//	continue;

				//}
			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

			}
		}

		JSONObject json = JSONArray.parseObject(assApplyDeptDetailJson);

		json.put("apply_id", apply_id);

		json.put("apply_no", apply_no);
		//json.put("apply_money", apply_money);
*/
		return JSONObject.parseObject(assApplyDeptService.addOrUpdateAssApplyDept(mapVo));

	}
	/**
	 * 查询明细资金来源
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/queryAssPlanResource", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object>  queryAssPlanResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String apply_id = mapVo.get("apply_id") == null ? "" : mapVo.get("apply_id").toString();
			if (!apply_id.equals("")) {
				mapVo.put("apply_id", apply_id.split("@")[0]);
				// mapVo.put("dept_no", dept_id_no.split("@")[1]);
			}
			String detail_no = mapVo.get("detail_id") == null ? "" : mapVo.get("detail_id").toString();
			if (!detail_no.equals("")) {
				mapVo.put("detail_id", detail_no);
			}
			String  assPlanResource=assApplyDeptResourceService.queryAssPlanResourceList(getPage(mapVo));

			return JSONObject.parseObject(assPlanResource);
	}
//queryAssPlanResourceImprot
	/**
	 * 查询明细资金来源
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/queryAssPlanResourceImprot", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object>  queryAssPlanResourceImprot(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String apply_id = mapVo.get("apply_id") == null ? "" : mapVo.get("apply_id").toString();
			if (!apply_id.equals("")) {
				mapVo.put("apply_id", apply_id.split("@")[0]);
				// mapVo.put("dept_no", dept_id_no.split("@")[1]);
			}
			
			String  assPlanResource=assApplyDeptResourceService.queryAssPlanResourceList(getPage(mapVo));

			return JSONObject.parseObject(assPlanResource);
	}
	/**
	 * 修改资金来源 saveResourceItem
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/saveResourceItem", method = RequestMethod.POST)
	@ResponseBody
//	public Map<String, Object> saveResourceItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		public Map<String, Object> saveResourceItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			String assPlanResourceJson = "";
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("dataItem").toString());

			for (Map<String, Object> detailVo : detail) {
				String id = mapVo.get("ass_id").toString();
				String apply=mapVo.get("apply_id").toString();
				/*String resorce_price= detailVo.get("resource_price").toString();
				Integer price=Integer.valueOf(resorce_price);*/
				
				
				/*String source= detailVo.get("source_code").toString();
				Integer source_id=Integer.valueOf(source);
*/
				detailVo.put("apply_id", apply.split("@")[0]);
				detailVo.put("ass_id", id.split("@")[0]);
				detailVo.put("ass_no", id.split("@")[1]);
				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());
//				detailVo.put("apply_id", mapVo.get("apply_id"));
				detailVo.put("detail_id", mapVo.get("detail_id"));
				detailVo.put("apply_no", mapVo.get("apply_no"));
				
				detailVo.put("price", detailVo.get("resource_price"));
				detailVo.put("source_id", detailVo.get("source_code"));

				if (detailVo.get("source_code") == null || "".equals(detailVo.get("source_code"))) {
					continue;
				}


				listVo.add(detailVo);
			}
			try {
				assPlanResourceJson = assApplyDeptResourceService.saveResourceItem(listVo);
			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
			
			return JSONObject.parseObject(assPlanResourceJson);


	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/updateToExamineApplyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamineApplyDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("apply_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			Date date = new Date();

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("check_date", DateUtil.stringToDate(format.format(date), "yyyy-MM-dd"));

			AssApplyDept assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);
			 List<AssApplyDeptResource>            assApplyDeptResource  =assApplyDeptResourceService.queryResourceAssPlanDeptResource(mapVo);
		     if (assApplyDeptResource.size()==0) {
		    	 return JSONObject.parseObject("{\"error\":\"没有资金来源,无法审核! \"}");
			}
			
			if (assApplyDept.getState() != 0) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复审核! \"}");
		}
		
		try {
			assInMainJson = assApplyDeptService.updateToExamine(listVo);
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
	@RequestMapping(value = "/hrp/ass/assapplydept/updateNotToExamineApplyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineApplyDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			mapVo.put("apply_id", ids[3]);

			mapVo.put("check_emp", SessionManager.getUserId());

			Date date = new Date();

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("check_date", DateUtil.stringToDate(format.format(date), "yyyy-MM-dd"));

			AssApplyDept assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);
			
			List<AssApplyPlanMap> listApply = assApplyPlanMapService.queryAssApplyPlanMapList(mapVo);
			/**
			 * 判断该条数据是否被购置计划所引用 ，如已被引用则不可删除 。 删除时 先删除购置计划中引用该条数据的计划单 再删除申请单
			 */
			if (assApplyDept.getState() != 1 || listApply.size() > 0) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不是审核状态或已被购置计划引用! \"}");
		}

		try {
			assInMainJson = assApplyDeptService.updateNotToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);

	}

	/**
	 * @Description 更新跳转页面 050201 购置申请单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/hrp/ass/assapplydept/assApplyDeptUpdatePage"/*, method = RequestMethod.GET*/)
	public String assApplyDeptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssApplyDept assApplyDept = new AssApplyDept();
		
		assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);

		
		mapVo.put("apply_id", assApplyDept.getApply_id());
		mapVo.put("apply_no", assApplyDept.getApply_no());
		
		/*  AssApplyDeptResource assApplyDeptResource= new AssApplyDeptResource();
		
		assApplyDeptResource=assApplyDeptResourceService.queryResource(mapVo);*/
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
		
		mode.addAttribute("dept_code", assApplyDept.getDept_code());

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
		
      /*  mode.addAttribute("source_id",assApplyDeptResource.getSource_id());*/

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05008",MyConfig.getSysPara("05008"));
		
		return "hrp/ass/assapplydept/assApplyDeptUpdate";
	}

	/**
	 * @Description 删除数据 050201 购置申请单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/deleteAssApplyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssApplyDept(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assApplyDeptJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("apply_id", ids[3]);

		
			AssApplyDept assApplyDept = assApplyDeptService.queryAssApplyDeptByCode(mapVo);
			/**
			 * 判断状态为新建才可删除 ，其他都不可删除该数据
			 */
			if (assApplyDept.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}

		if (flag == false) {
			return JSONObject.parseObject("{\"warn\":\"已审核的数据不能删除! \"}");
		}

		
		try {
			assApplyDeptProofService.deleteProofInfo(listVo);
			assApplyDeptDetailService.deleteBatchAssApplyDeptDetail(listVo);
			assApplyDeptResourceService.deleteAssPlanResource(listVo);
			assApplyDeptJson = assApplyDeptService.deleteBatchAssApplyDept(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assApplyDeptJson);

	}

	/**
	 * @Description 查询数据 050201 购置申请单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/queryAssApplyDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();

		if (!dept_id_no.equals("")) {

			mapVo.put("dept_id", dept_id_no.split("@")[0]);

			mapVo.put("dept_no", dept_id_no.split("@")[1]);

		}

		String assApplyDept = assApplyDeptService.queryAssApplyDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}

	/**
	 * @Description 删除数据 050201 资产购置申请明显表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/deleteAssApplyDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssApplyDeptDetail(@RequestParam(value = "ParamVo") String ParamVo, Model mode)
			throws Exception {

		String assApplyDeptDetailJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : ParamVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("detail_id", ids[3]);

			mapVo.put("apply_id", ids[4]);

			mapVo.put("ass_id", ids[5]);

			mapVo.put("ass_no", ids[6]);
			String apply_id = mapVo.get("apply_id").toString();
			String str = assBaseService.isExistsDataByTable("ass_apply_dept_detail", apply_id);
			if (Strings.isNotBlank(str)) {
				String retErrot = "{\"error\":\"无法删除,购置申请被以下业务使用：【" + str.substring(0, str.length() - 1)
						+ "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			listVo.add(mapVo);

		}

		try {
			                         assApplyDeptResourceService.deleteAssPlanResource(listVo);
			assApplyDeptDetailJson = assApplyDeptDetailService.deleteBatchAssApplyDeptDetail(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assApplyDeptDetailJson);
	}

	/**
	 * @Description 查询数据 050201 资产购置申请明显表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/queryAssApplyDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDeptDetail = assApplyDeptDetailService.queryAssApplyDeptDetailByUpdate(getPage(mapVo));

		return JSONObject.parseObject(assApplyDeptDetail);

	}

	/**
	 * @Description 查询数据 050201 购置申请单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/queryAssApplyDepts", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDepts(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String dept_id_no = mapVo.get("dept_id") == null ? "" : mapVo.get("dept_id").toString();

		if (!dept_id_no.equals("")) {

			mapVo.put("dept_id", dept_id_no.split("@")[0]);

			mapVo.put("dept_no", dept_id_no.split("@")[1]);

		}
		String apply_id = mapVo.get("apply_id") == null ? "" : mapVo.get("apply_id").toString();
		if (!apply_id.equals("")) {
			mapVo.put("apply_id", apply_id.split("@")[0]);
		}
		mapVo.put("state", "1");
		String  assPlanResource=assApplyDeptResourceService.queryAssPlanResourceList(getPage(mapVo));
		String assApplyDept = assApplyDeptService.queryAssApplyDepts(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	/***
	 * 打印状态
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/queryApplyDeptState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryApplyDeptState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//打印时校验数据专用
		List<String> list = assApplyDeptService.queryAssApplyDeptState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"购置申请单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	/**
	 * 设备评估信息页面
	 * @param detail_id
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydept/assApplyProofPage", method = RequestMethod.GET)
	public String assApplyProofPage( @RequestParam Map<String, Object> mapVo,Model mode)
			throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("rowindex",mapVo.get("rowindex"));
		return "hrp/ass/assapplydept/assApplyproof";
	}
	
	
}
