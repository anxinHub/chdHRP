/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.assremould;

import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.assremould.AssRemouldRdetailSpecial;
import com.chd.hrp.ass.entity.assremould.AssRemouldRspecial;
import com.chd.hrp.ass.service.assremould.AssRemouldADetailSpecialService;
import com.chd.hrp.ass.service.assremould.AssRemouldAspecialService;
import com.chd.hrp.ass.service.assremould.AssRemouldRDetailSpecialService;
import com.chd.hrp.ass.service.assremould.AssRemouldRspecialService;

/**
 * 
 * @Description: 050805 资产改造记录(专用设备)
 * @Table: ASS_REMOULD_R_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssRemouldRspecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRemouldRspecialController.class);

	// 引入Service服务
	@Resource(name = "assRemouldRspecialService")
	private final AssRemouldRspecialService assRemouldRspecialService = null;
	// 引入Service服务
	@Resource(name = "assRemouldRDetailSpecialService")
	private final AssRemouldRDetailSpecialService assRemouldRDetailSpecialService = null;

	// 引入Service服务
		@Resource(name = "assRemouldAspecialService")
		private final AssRemouldAspecialService assRemouldAspecialService = null;

		// 引入Service服务
		@Resource(name = "assRemouldADetailSpecialService")
		private final AssRemouldADetailSpecialService assRemouldADetailSpecialService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/assRemouldRspecialMainPage", method = RequestMethod.GET)
	public String assRemouldRspecialMainPage(Model mode) throws Exception {

		return "hrp/ass/assremould/assremouldrspecial/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/assRemouldRspecialAddPage", method = RequestMethod.GET)
	public String assRemouldRspecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assremould/assremouldrspecial/add";

	}
	/**
	 * @Description 引入申请页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		return "hrp/ass/assremould/assremouldrspecial/Import";

	}
	/**
	 * @Description 查询改造申请数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/queryAssRemouldAspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldAspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldAspecial = assRemouldAspecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAspecial);

	}
	
	/**
	 * @Description 查看申报信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/assRemouldAspecialUpdatePage", method = RequestMethod.GET)
	public String assRemouldAspecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		Map<String,Object> assRemouldAspecialMap = assRemouldAspecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldAspecialMap.get("group_id"));
		mode.addAttribute("hos_id", assRemouldAspecialMap.get("hos_id"));
		mode.addAttribute("copy_code", assRemouldAspecialMap.get("copy_code"));
		mode.addAttribute("apply_no", assRemouldAspecialMap.get("apply_no"));
		mode.addAttribute("bus_type_code", assRemouldAspecialMap.get("bus_type_code"));
		mode.addAttribute("bus_type_name", assRemouldAspecialMap.get("bus_type_name"));
		mode.addAttribute("ven_id", assRemouldAspecialMap.get("ven_id"));
		mode.addAttribute("ven_no", assRemouldAspecialMap.get("ven_no"));
		mode.addAttribute("sup_code", assRemouldAspecialMap.get("sup_code"));
		mode.addAttribute("sup_name", assRemouldAspecialMap.get("sup_name"));
		mode.addAttribute("create_emp", assRemouldAspecialMap.get("create_emp"));
		mode.addAttribute("create_date", assRemouldAspecialMap.get("create_date"));
		mode.addAttribute("create_emp_name", assRemouldAspecialMap.get("create_emp_name"));
		mode.addAttribute("audit_emp_name", assRemouldAspecialMap.get("audit_emp_name"));
		mode.addAttribute("audit_emp", assRemouldAspecialMap.get("audit_emp"));
		mode.addAttribute("change_date", assRemouldAspecialMap.get("change_date"));
		mode.addAttribute("state", assRemouldAspecialMap.get("state"));
		mode.addAttribute("note", assRemouldAspecialMap.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assremould/assremouldrspecial/Viewupdate";
	}
	/**
	 * @Description 查询改造申请明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/queryAssRemouldADict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldADict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldAspecial = assRemouldAspecialService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAspecial);

	}
	//查询明细到记录
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/addAssPlanDeptImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
	/*	
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String carno = mapVo.get("apply_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("apply_no", ids);
		
		
		String assBackDetailSpecial = assRemouldADetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);*/
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assRemouldRspecialService.initAssCheckSpecial(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);
	}
	
	
	/**
	 * @Description 追溯盘亏申请单跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/assViewSpecialPage", method = RequestMethod.GET)
	public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("record_no", mapVo.get("record_no"));
		return "/hrp/ass/assremould/assremouldrspecial/assPlanViewApply";
	}
	//queryCheckApSpecialByImport
	// 追溯盘亏申请
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/queryCheckApSpecialByImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assRemouldAspecialService.queryAssApplyDeptByPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	/**
	 * @Description 添加数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/addAssRemouldRspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldRspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		String assRemouldRspecialJson = assRemouldRspecialService.add(mapVo);

		return JSONObject.parseObject(assRemouldRspecialJson);

	}

	/**
	 * @Description 添加数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/saveAssRemouldRSourcespecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldRSourcespecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		mapVo.put("create_emp", SessionManager.getUserId());
		String assRemouldRspecialJson = assRemouldRspecialService.saveAssRemouldRSourcespecial(mapVo);

		return JSONObject.parseObject(assRemouldRspecialJson);

	}

	/**
	 * @Description 更新跳转页面 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/assRemouldRspecialUpdatePage", method = RequestMethod.GET)
	public String assRemouldRspecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssRemouldRspecial assRemouldRspecial = new AssRemouldRspecial();

		assRemouldRspecial = assRemouldRspecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldRspecial.getGroup_id());
		mode.addAttribute("hos_id", assRemouldRspecial.getHos_id());
		mode.addAttribute("copy_code", assRemouldRspecial.getCopy_code());
		mode.addAttribute("record_no", assRemouldRspecial.getRecord_no());
		mode.addAttribute("bus_type_code", assRemouldRspecial.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldRspecial.getVen_id());
		mode.addAttribute("ven_no", assRemouldRspecial.getVen_no());
		mode.addAttribute("sup_code", assRemouldRspecial.getSup_code());
		mode.addAttribute("sup_name", assRemouldRspecial.getSup_name());
		mode.addAttribute("create_emp", assRemouldRspecial.getCreate_emp());
		mode.addAttribute("create_date", assRemouldRspecial.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldRspecial.getAudit_emp());
		mode.addAttribute("record_date", assRemouldRspecial.getRecord_date());
		mode.addAttribute("state", assRemouldRspecial.getState());
		mode.addAttribute("note", assRemouldRspecial.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assremould/assremouldrspecial/update";
	}

	/**
	 * @Description 更新数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/updateAssRemouldRspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldRspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String assRemouldRspecialJson = assRemouldRspecialService.update(mapVo);

		return JSONObject.parseObject(assRemouldRspecialJson);
	}

	/**
	 * @Description 更新数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/saveAssRemouldRspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldRspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assRemouldRspecialJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
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
		if (mapVo.get("user_id") == null) {
			mapVo.put("create_emp", SessionManager.getUserId());
		}
		assRemouldRspecialJson = assRemouldRspecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assRemouldRspecialJson);
	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/deleteAssremoulddSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssremoulddSourceSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("source_id", ids[5]);

			AssRemouldRspecial assChangeSpecial = assRemouldRspecialService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}
		String assChangeSourceSpecialJson = assRemouldRspecialService.deleteAssSourceSpecial(listVo);

		return JSONObject.parseObject(assChangeSourceSpecialJson);

	}
	/**
	 * @Description 删除数据 050805 资产原值变动明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/deleteAssRemouldDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldDetailSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);

			AssRemouldRspecial assChangeSpecial = assRemouldRspecialService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailSpecialJson = assRemouldRDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailSpecialJson);

	}
	
	
	
	
	
	/**
	 * @Description 删除数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/deleteAssRemouldRspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldRspecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			AssRemouldRspecial assRemouldAspecial = assRemouldRspecialService.queryByCode(mapVo);
			if (assRemouldAspecial != null) {
				if (assRemouldAspecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}

			}

			listVo.add(mapVo);

		}
		  String de=    assRemouldRspecialService.deleteBatchAssApplyPlanMap(listVo);

		String assRemouldRspecialJson = assRemouldRspecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assRemouldRspecialJson);

	}

	/**
	 * @Description 变动确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/updateConfirmChangeSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAspecialConfirmState(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("record_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			// mapVo.put("apply_no", ids[4]);
			// 查询主表是否存在
			AssRemouldRspecial assRemouldAspecial = assRemouldRspecialService.queryByCode(mapVo);
			if (assRemouldAspecial == null || assRemouldAspecial.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			// 查询明细表
			List<AssRemouldRdetailSpecial> detailList = assRemouldRDetailSpecialService.queryByDisANo(mapVo);
			// 明细表存在进行判断卡片
			if (detailList != null && detailList.size() > 0) {
				for (AssRemouldRdetailSpecial detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAspecial.getBus_type_code());
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if (map.containsKey(detail.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				// 如果明细表不存在返回没有数据
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		// 判断listVo是否有数据
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assRemouldAspecialJson = assRemouldRspecialService.updateAssRemouldAspecialConfirmState(listVo,
				listCardVo);

		return JSONObject.parseObject(assRemouldAspecialJson);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/queryAssRemouldRspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldRspecial = assRemouldRspecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldRspecial);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/queryAssRemouldRdetailspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRdetailspecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldRspecial = assRemouldRspecialService.queryAssRemouldRdetailspecial(mapVo);

		return JSONObject.parseObject(assRemouldRspecial);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrspecial/queryAssRemouldSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldRspecial = assRemouldRspecialService.queryAssRemouldSourceSpecial(mapVo);

		return JSONObject.parseObject(assRemouldRspecial);

	}

}
