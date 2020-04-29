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
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptDetailOther;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptOther;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptDetailOtherService;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptOtherService;
/**
 * 
 * @Description:
 * 050901 资产转移主表(科室到科室)_其他固定资产
 * @Table:
 * ASS_TRAN_DEPT_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssTranDeptOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssTranDeptOtherController.class);

	// 引入Service服务
	@Resource(name = "assTranDeptOtherService")
	private final AssTranDeptOtherService assTranDeptOtherService = null;

	@Resource(name = "assTranDeptDetailOtherService")
	private final AssTranDeptDetailOtherService assTranDeptDetailOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/assTranDeptOtherMainPage", method = RequestMethod.GET)
	public String assTranDeptOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asstran/dept/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/assTranDeptOtherAddPage", method = RequestMethod.GET)
	public String assTranDeptOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asstran/dept/add";

	}
	
	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/assImportSpecialPage", method = RequestMethod.GET)
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
		
		return "hrp/ass/assother/asstran/dept/importoutmain";
	}
	

	/**
	 * @Description 添加数据 050901 资产转移主表(科室到科室)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/saveAssTranDeptOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTranDeptOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssTranDeptOther assTranDeptOther = assTranDeptOtherService.queryByCode(mapVo);
		if(assTranDeptOther != null){
			if(assTranDeptOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能保存! \"}");
			}
		}

		String assTranDeptOtherJson = assTranDeptOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assTranDeptOtherJson);

	}
	
	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/updateConfirmTranDeptOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranDeptOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssTranDeptOther assTranDeptOther = assTranDeptOtherService.queryByCode(mapVo);
			if (DateUtil.compareDate(assTranDeptOther.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranDeptDetailOther> list = assTranDeptOtherService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranDeptDetailOther assBackDetailOther : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					listCardMap.put("out_dept_id", assBackDetailOther.getOut_dept_id());
					listCardMap.put("out_dept_no", assBackDetailOther.getOut_dept_no());
					listCardMap.put("in_dept_id", assBackDetailOther.getIn_dept_id());
					listCardMap.put("in_dept_no", assBackDetailOther.getIn_dept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailOther.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assTranDeptOther.getState() == 2) {
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
			assInMainJson = assTranDeptOtherService.updateConfirmTranDeptOther(listVo, listCardVo);
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
	 */
	/*@RequestMapping(value = "/hrp/ass/assother/asstran/dept/updateConfirmTranDeptOtherdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranDeptOtherdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssTranDeptOther assTranDeptOther = assTranDeptOtherService.queryByCode(mapVo);
			if (DateUtil.compareDate(assTranDeptOther.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranDeptDetailOther> list = assTranDeptOtherService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranDeptDetailOther assBackDetailOther : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					listCardMap.put("out_dept_id", assBackDetailOther.getOut_dept_id());
					listCardMap.put("out_dept_no", assBackDetailOther.getOut_dept_no());
					listCardMap.put("in_dept_id", assBackDetailOther.getIn_dept_id());
					listCardMap.put("in_dept_no", assBackDetailOther.getIn_dept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailOther.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assTranDeptOther.getState() == 2) {
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
			assInMainJson = assTranDeptOtherService.updateConfirmTranDeptOther(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/
	
	/**
	 * @Description 更新跳转页面 050901 资产转移主表(科室到科室)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/assTranDeptOtherUpdatePage", method = RequestMethod.GET)
	public String assTranDeptOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssTranDeptOther assTranDeptOther = new AssTranDeptOther();

		assTranDeptOther = assTranDeptOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assTranDeptOther.getGroup_id());
		mode.addAttribute("hos_id", assTranDeptOther.getHos_id());
		mode.addAttribute("copy_code", assTranDeptOther.getCopy_code());
		mode.addAttribute("tran_no", assTranDeptOther.getTran_no());
		mode.addAttribute("out_dept_id", assTranDeptOther.getOut_dept_id());
		mode.addAttribute("out_dept_no", assTranDeptOther.getOut_dept_no());
		mode.addAttribute("out_dept_code", assTranDeptOther.getOut_dept_code());
		mode.addAttribute("out_dept_name", assTranDeptOther.getOut_dept_name());
		mode.addAttribute("in_dept_id", assTranDeptOther.getIn_dept_id());
		mode.addAttribute("in_dept_no", assTranDeptOther.getIn_dept_no());
		mode.addAttribute("in_dept_code", assTranDeptOther.getIn_dept_code());
		mode.addAttribute("in_dept_name", assTranDeptOther.getIn_dept_name());
		mode.addAttribute("create_emp", assTranDeptOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assTranDeptOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assTranDeptOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assTranDeptOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assTranDeptOther.getAudit_emp_name());
		mode.addAttribute("audit_date", assTranDeptOther.getAudit_date());
		mode.addAttribute("state", assTranDeptOther.getState());
		mode.addAttribute("state_name", assTranDeptOther.getState_name());
		mode.addAttribute("note", assTranDeptOther.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asstran/dept/update";
	}

	/**
	 * @Description 删除数据 050901 资产转移主表(科室到科室)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/deleteAssTranDeptOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranDeptOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssTranDeptOther assTranDeptOther = assTranDeptOtherService.queryByCode(mapVo);
			if(assTranDeptOther != null){
				if(assTranDeptOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assTranDeptOtherJson = assTranDeptOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranDeptOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移主表(科室到科室)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/queryAssTranDeptOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranDeptOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String assTranDeptOther = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assTranDeptOther = assTranDeptOtherService.query(getPage(mapVo));
		}else{
			assTranDeptOther = assTranDeptOtherService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assTranDeptOther);

	}

	/**
	 * @Description 删除数据 050901 资产转移明细(科室到科室)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/deleteAssTranDeptDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranDeptDetailOther(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssTranDeptOther assTranDeptOther = assTranDeptOtherService.queryByCode(mapVo);
			if(assTranDeptOther != null){
				if(assTranDeptOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assTranDeptDetailOtherJson = assTranDeptDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranDeptDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移明细(科室到科室)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/queryAssTranDeptDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranDeptDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranDeptDetailOther = assTranDeptDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assTranDeptDetailOther);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/dept/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<String> list = assTranDeptOtherService.queryState(mapVo);
		
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

