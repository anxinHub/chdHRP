package com.chd.hrp.ass.controller.inspection;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

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
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;
import com.chd.hrp.ass.service.inspection.AssInspectionDetailService;
import com.chd.hrp.ass.service.inspection.AssInspectionMainService;

@Controller 
public class AssInspectionMainController extends BaseController {
	private static Logger logger = Logger.getLogger(AssInspectionMainController.class);

	// 引入Service服务
	@Resource(name = "assInspectionMainService")
	private final AssInspectionMainService assInspectionMainService = null;

	@Resource(name = "assInspectionDetailService")
	private final AssInspectionDetailService assInspectionDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/assInspectionMainPage", method = RequestMethod.GET)
	public String assInspectionMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05012",MyConfig.getSysPara("05012"));
		
		return "hrp/ass/assinspection/assInspectionMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/assInspectionAddPage", method = RequestMethod.GET)
	public String assInspectionAddPage(Model mode) throws Exception {

		return "hrp/ass/assinspection/assInspectionAdd";

	}

	/**
	 * @Description 添加数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/addAssInspectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInspectionMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("state", "0");

		mapVo.put("create_emp", SessionManager.getUserId());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

		mapVo.put("audit_emp", "");

		mapVo.put("audit_date", "");
		try {
			String assInspectionMainJson = assInspectionMainService.addOrUpdateAssInspectionMain(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assInspectionMainJson);
			String ins_id = (String) jsonObj.get("ins_id");
			String ins_no = (String) jsonObj.get("ins_no");
			String assInspectionDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());
				
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("ins_id", ins_id);

				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				}

				
				String yearmonth=mapVo.get("ass_year").toString()+mapVo.get("ass_month").toString();
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
				
				assInspectionDetailJson = assInspectionDetailService.addOrUpdateAssInspectionDetail(detailVo);

			}
			JSONObject json = JSONArray.parseObject(assInspectionDetailJson);
			json.put("ins_id", ins_id);
			json.put("ins_no", ins_no);
			return JSONObject.parseObject(json.toJSONString());
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/assInspectionUpdatePage", method = RequestMethod.GET)
	public String addAssInspectionUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		AssInspectionMain assInspectionMain = new AssInspectionMain();

		assInspectionMain = assInspectionMainService.queryAssInspectionMainByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assInspectionMain.getGroup_id());

		mode.addAttribute("hos_id", assInspectionMain.getHos_id());

		mode.addAttribute("copy_code", assInspectionMain.getCopy_code());

		mode.addAttribute("ins_id", assInspectionMain.getIns_id());

		mode.addAttribute("ins_no", assInspectionMain.getIns_no());

		mode.addAttribute("ins_name", assInspectionMain.getIns_name());

		mode.addAttribute("ass_year", assInspectionMain.getAss_year());

		mode.addAttribute("ass_month", assInspectionMain.getAss_month());

		mode.addAttribute("ass_nature", assInspectionMain.getAss_nature());

		mode.addAttribute("dept_id", assInspectionMain.getDept_id());

		mode.addAttribute("dept_no", assInspectionMain.getDept_no());
		
		mode.addAttribute("dept_code", assInspectionMain.getDept_code());

		mode.addAttribute("dept_name", assInspectionMain.getDept_name());

		mode.addAttribute("create_emp", assInspectionMain.getCreate_emp());

		mode.addAttribute("create_emp_name", assInspectionMain.getCreate_emp_name());

		mode.addAttribute("create_date", sdf.format(assInspectionMain.getCreate_date()));

		if (assInspectionMain.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assInspectionMain.getAudit_emp());

		}

		if (assInspectionMain.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assInspectionMain.getAudit_date()));

		}

		mode.addAttribute("state", assInspectionMain.getState());

		if (assInspectionMain.getNote() == null) {

			mode.addAttribute("note", "");

		} else {

			mode.addAttribute("note", assInspectionMain.getNote());

		}

		return "hrp/ass/assinspection/assInspectionUpdate";
	}

	/**
	 * @Description 更新数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/updateAssInspectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInspectionMain(@RequestParam Map<String, Object> mapVo, Model mode)
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
		try {
			String assInspectionMainJson = assInspectionMainService.updateAssInspectionMain(mapVo);

			return JSONObject.parseObject(assInspectionMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/addOrUpdateAssInspectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssInspectionMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assInspectionMainJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {

				detailVo.put("group_id", SessionManager.getGroupId());

			}

			if (detailVo.get("hos_id") == null) {

				detailVo.put("hos_id", SessionManager.getHosId());

			}

			if (detailVo.get("copy_code") == null) {

				detailVo.put("copy_code", SessionManager.getCopyCode());

			}
			try {
				assInspectionMainJson = assInspectionMainService.addOrUpdateAssInspectionMain(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assInspectionMainJson);
	}

	/**
	 * @Description 删除数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/deleteAssInspectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInspectionMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		String assInspectionMainJson;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			if (Integer.parseInt(ids[4]) != 0) {

				flag = false;
				break;
			}

			listVo.add(mapVo);
		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被审核或被终止! \"}");

		}
		try {
			assInspectionDetailService.deleteBatchAssInspectionDetail(listVo);
			assInspectionMainJson = assInspectionMainService.deleteBatchAssInspectionMain(listVo);

			return JSONObject.parseObject(assInspectionMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/queryAssInspectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInspectionMain(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assInspectionMain = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assInspectionMain = assInspectionMainService.queryAssInspectionMain(getPage(mapVo));
			 
		}else{

			assInspectionMain = assInspectionMainService.queryDetails(getPage(mapVo));
			 
		}

		return JSONObject.parseObject(assInspectionMain);

	}

	/**
	 * @Description 审核数据 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/auditAssInspection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAssInspection(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			if (Integer.parseInt(ids[4]) != 0) {

				flag = false;

				break;

			}

			mapVo.put("state", "1");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");

		}
		try {
			String assInspectionJson = assInspectionMainService.updateBatchAssInspectionMain(listVo);

			return JSONObject.parseObject(assInspectionJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 消审数据 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/backAssInspection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backAssInspection(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			mapVo.put("state", "0");

			mapVo.put("audit_emp", "");

			mapVo.put("audit_date", "");

			// 判断状态不是审核不能进行消审
			if (!ids[4].equals("1")) {

				flag = false;

				break;

			}

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");

		}
		try {
			String assInspectionJson = assInspectionMainService.updateBatchAssInspectionMainBack(listVo);

			return JSONObject.parseObject(assInspectionJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 终止计划 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/stopAssInspection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stopAssInspection(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			mapVo.put("state", "2");

			listVo.add(mapVo);

		}
		try {
			String assInspectionJson = assInspectionMainService.updateBatchAssInspectionMainStop(listVo);

			return JSONObject.parseObject(assInspectionJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	
	/**
	 * @Description 根据资产性质来选择资产卡片号 下拉框表格
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinspection/choseAssCardNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> choseAssCardNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		mapVo.put("store_id", mapVo.containsKey("store_id") == true ? mapVo.get("store_id") : "");

		mapVo.put("user_dept_id", mapVo.containsKey("user_dept_id") == true ? mapVo.get("user_dept_id") : "");

		String assMaintainPlanJson = "";

		if (("").equals(mapVo.get("ass_nature").toString())) {

			return JSONObject.parseObject("{\"error\":\"请先选择主表的资产性质! \"}");

		}

		if (mapVo.get("ass_nature").equals("02")) {
			assMaintainPlanJson = assInspectionMainService.queryAssCardSpecial(getPage(mapVo));
		}
		if (mapVo.get("ass_nature").equals("03")) {

		assMaintainPlanJson = assInspectionMainService.queryAssCardGeneral(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("01")) {

			assMaintainPlanJson = assInspectionMainService.queryAssCardHouse(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("05")) {

			assMaintainPlanJson = assInspectionMainService.queryAssCardInassets(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("04")) {

			assMaintainPlanJson = assInspectionMainService.queryAssCardOther(getPage(mapVo));

		}
		if (mapVo.get("ass_nature").equals("06")) {

			assMaintainPlanJson = assInspectionMainService.queryAssCardLand(getPage(mapVo));

		}

		return JSONObject.parseObject(assMaintainPlanJson);

	}

	
	// 生成验收项目
		@RequestMapping(value = "/hrp/ass/assinspection/buildAssInsItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> buildAssInsItem(@RequestParam Map<String, Object> mapVo, Model model)
				throws Exception {
			String assAcceptMainJson = "";
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			assAcceptMainJson = assInspectionMainService.buildAssInsItem(mapVo);

			return JSONObject.parseObject(assAcceptMainJson);
		}

		// 保存验收项目
		@RequestMapping(value = "/hrp/ass/assinspection/saveAssInsItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> saveAssInsItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String assAcceptDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ins_item_data").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("item_code") == null || "".equals(detailVo.get("item_code"))) {
					continue;
				}

				detailVo.put("is_normal", Integer.parseInt(detailVo.get("is_normal").toString()));

				listVo.add(detailVo);
			}

			try {
				assAcceptDetailJson = assInspectionMainService.saveAssInsItem(listVo);
			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assAcceptDetailJson);

		}
	
	
	// 查询验收项目
		@RequestMapping(value = "/hrp/ass/assinspection/queryAssInsItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssAcceptItem(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			String assAcceptMain = "";

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			assAcceptMain = assInspectionMainService.queryAssInsItem(getPage(mapVo));

			return JSONObject.parseObject(assAcceptMain);
		}

		// 删除验收项目
		@RequestMapping(value = "/hrp/ass/assinspection/deleteAssInsItem", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssAcceptItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			String assAcceptMainJson;
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				// 表的主键
				mapVo.put("group_id", ids[0]);

				mapVo.put("hos_id", ids[1]);

				mapVo.put("copy_code", ids[2]);

				mapVo.put("ins_id", ids[3]);

				mapVo.put("detail_id", ids[4]);

				mapVo.put("item_code", ids[5]);

				listVo.add(mapVo);
			}
			try {
				                 
				assAcceptMainJson = assInspectionMainService.deleteAssInsItem(listVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assAcceptMainJson);
		}

		/**
		 * 巡检单状态查询
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinspection/queryInSpectionMainState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryInSpectionMainState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//入库单状态查询  （打印时校验数据专用）
			List<String> list = assInspectionMainService.queryInSpectionMainState(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"state\":\"true\"}");
				
			}else{
				
				String  str = "" ;
				for(String item : list){
					
					str += item +"," ;
				}
				
				return JSONObject.parseObject("{\"error\":\"巡检单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
			}
			
			
		}	
		
}
