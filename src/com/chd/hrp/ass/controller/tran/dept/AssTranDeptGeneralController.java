/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.tran.dept;
import java.text.SimpleDateFormat;
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
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptDetailGeneral;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptGeneral;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptDetailGeneralService;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptGeneralService;
/**
 * 
 * @Description:
 * 050901 资产转移主表(科室到科室)_一般设备
 * @Table:
 * ASS_TRAN_DEPT_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssTranDeptGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssTranDeptGeneralController.class);

	// 引入Service服务
	@Resource(name = "assTranDeptGeneralService")
	private final AssTranDeptGeneralService assTranDeptGeneralService = null;

	@Resource(name = "assTranDeptDetailGeneralService")
	private final AssTranDeptDetailGeneralService assTranDeptDetailGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/assTranDeptGeneralMainPage", method = RequestMethod.GET)
	public String assTranDeptGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assgeneral/asstran/dept/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/assTranDeptGeneralAddPage", method = RequestMethod.GET)
	public String assTranDeptGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asstran/dept/add";

	}

	
	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/assImportSpecialPage", method = RequestMethod.GET)
	public String assImportSpecialPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("out_dept_id", mapVo.get("out_dept_id"));
		mode.addAttribute("out_dept_no", mapVo.get("out_dept_no"));
		mode.addAttribute("out_dept_code", mapVo.get("out_dept_code"));
		mode.addAttribute("out_dept_name", mapVo.get("out_dept_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("in_dept_id", mapVo.get("in_dept_id"));
		mode.addAttribute("in_dept_no", mapVo.get("in_dept_no"));
		mode.addAttribute("in_dept_code", mapVo.get("in_dept_code"));
		mode.addAttribute("in_dept_name", mapVo.get("in_dept_name"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asstran/dept/importoutmain";
	}
	
	
	/**
	 * @Description 添加数据 050901 资产转移主表(科室到科室)_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/saveAssTranDeptGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTranDeptGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
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
		
		AssTranDeptGeneral assTranDeptGeneral = assTranDeptGeneralService.queryByCode(mapVo);
		if(assTranDeptGeneral != null){
			if(assTranDeptGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能保存! \"}");
			}
		}

		String assTranDeptGeneralJson = assTranDeptGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assTranDeptGeneralJson);

	}
	
	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/updateConfirmTranDeptGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranDeptGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String str = df.format(date);
		String current_year = str.substring(0, 4);
		String current_month = str.substring(4, 6);
		
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			
			AssTranDeptGeneral assTranDeptGeneral = assTranDeptGeneralService.queryByCode(mapVo);
			if (DateUtil.compareDate(assTranDeptGeneral.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranDeptDetailGeneral> list = assTranDeptGeneralService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranDeptDetailGeneral assBackDetailGeneral : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assBackDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assBackDetailGeneral.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailGeneral.getAss_card_no());
					listCardMap.put("out_dept_id", assBackDetailGeneral.getOut_dept_id());
					listCardMap.put("out_dept_no", assBackDetailGeneral.getOut_dept_no());
					listCardMap.put("in_dept_id", assBackDetailGeneral.getIn_dept_id());
					listCardMap.put("in_dept_no", assBackDetailGeneral.getIn_dept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailGeneral.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailGeneral.getAss_card_no(), assBackDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assTranDeptGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranDeptGeneralService.updateConfirmTranDeptGeneral(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/updateConfirmTranDeptGeneraldetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranDeptGeneraldetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String str = df.format(date);
		String current_year = str.substring(0, 4);
		String current_month = str.substring(4, 6);
		
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			if(  ids[0].length() == 0){
				return JSONObject.parseObject("{\"warn\":\"单据没有保存,不能入库! \"}");
			}
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			AssTranDeptGeneral assTranDeptGeneral = assTranDeptGeneralService.queryByCode(mapVo);
			if (DateUtil.compareDate(assTranDeptGeneral.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranDeptDetailGeneral> list = assTranDeptGeneralService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranDeptDetailGeneral assBackDetailGeneral : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assBackDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assBackDetailGeneral.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailGeneral.getAss_card_no());
					listCardMap.put("out_dept_id", assBackDetailGeneral.getOut_dept_id());
					listCardMap.put("out_dept_no", assBackDetailGeneral.getOut_dept_no());
					listCardMap.put("in_dept_id", assBackDetailGeneral.getIn_dept_id());
					listCardMap.put("in_dept_no", assBackDetailGeneral.getIn_dept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailGeneral.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailGeneral.getAss_card_no(), assBackDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assTranDeptGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranDeptGeneralService.updateConfirmTranDeptGeneral(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/
	
	/**
	 * @Description 更新跳转页面 050901 资产转移主表(科室到科室)_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/assTranDeptGeneralUpdatePage", method = RequestMethod.GET)
	public String assTranDeptGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssTranDeptGeneral assTranDeptGeneral = new AssTranDeptGeneral();

		assTranDeptGeneral = assTranDeptGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assTranDeptGeneral.getGroup_id());
		mode.addAttribute("hos_id", assTranDeptGeneral.getHos_id());
		mode.addAttribute("copy_code", assTranDeptGeneral.getCopy_code());
		mode.addAttribute("tran_no", assTranDeptGeneral.getTran_no());
		mode.addAttribute("out_dept_id", assTranDeptGeneral.getOut_dept_id());
		mode.addAttribute("out_dept_no", assTranDeptGeneral.getOut_dept_no());
		mode.addAttribute("out_dept_code", assTranDeptGeneral.getOut_dept_code());
		mode.addAttribute("out_dept_name", assTranDeptGeneral.getOut_dept_name());
		mode.addAttribute("in_dept_id", assTranDeptGeneral.getIn_dept_id());
		mode.addAttribute("in_dept_no", assTranDeptGeneral.getIn_dept_no());
		mode.addAttribute("in_dept_code", assTranDeptGeneral.getIn_dept_code());
		mode.addAttribute("in_dept_name", assTranDeptGeneral.getIn_dept_name());
		mode.addAttribute("create_emp", assTranDeptGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assTranDeptGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assTranDeptGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assTranDeptGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assTranDeptGeneral.getAudit_emp_name());
		mode.addAttribute("audit_date", assTranDeptGeneral.getAudit_date());
		mode.addAttribute("state", assTranDeptGeneral.getState());
		mode.addAttribute("state_name", assTranDeptGeneral.getState_name());
		mode.addAttribute("note", assTranDeptGeneral.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assgeneral/asstran/dept/update";
	}

	/**
	 * @Description 删除数据 050901 资产转移主表(科室到科室)_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/deleteAssTranDeptGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranDeptGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			
			AssTranDeptGeneral assTranDeptGeneral = assTranDeptGeneralService.queryByCode(mapVo);
			if(assTranDeptGeneral != null){
				if(assTranDeptGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assTranDeptGeneralJson = assTranDeptGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranDeptGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移主表(科室到科室)_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/queryAssTranDeptGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranDeptGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String assTranDeptGeneral = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assTranDeptGeneral = assTranDeptGeneralService.query(getPage(mapVo));
		}else{
			assTranDeptGeneral = assTranDeptGeneralService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assTranDeptGeneral);

	}

	/**
	 * @Description 删除数据 050901 资产转移明细(科室到科室)_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/deleteAssTranDeptDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranDeptDetailGeneral(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			
			AssTranDeptGeneral assTranDeptGeneral = assTranDeptGeneralService.queryByCode(mapVo);
			if(assTranDeptGeneral != null){
				if(assTranDeptGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assTranDeptDetailGeneralJson = assTranDeptDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranDeptDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移明细(科室到科室)_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/queryAssTranDeptDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranDeptDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranDeptDetailGeneral = assTranDeptDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assTranDeptDetailGeneral);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/dept/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<String> list = assTranDeptGeneralService.queryState(mapVo);
		
		if(list.size()>0){
			for (String tran_no : list) {
				str += tran_no +" ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据未确认,无法打印!\",\"state\":\"true\"}");
			
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
		
	}

	
}

