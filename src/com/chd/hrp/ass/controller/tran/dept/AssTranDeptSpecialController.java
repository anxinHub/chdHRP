
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
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptDetailSpecial;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptSpecial;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptDetailSpecialService;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptSpecialService;

/**
 * 
 * @Description: 050901 资产转移主表(科室到科室)_专用设备
 * @Table: ASS_TRAN_DEPT_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssTranDeptSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssTranDeptSpecialController.class);

	// 引入Service服务
	@Resource(name = "assTranDeptSpecialService")
	private final AssTranDeptSpecialService assTranDeptSpecialService = null;

	@Resource(name = "assTranDeptDetailSpecialService")
	private final AssTranDeptDetailSpecialService assTranDeptDetailSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/assTranDeptSpecialMainPage", method = RequestMethod.GET)
	public String assTranDeptSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asstran/dept/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/assTranDeptSpecialAddPage", method = RequestMethod.GET)
	public String assTranDeptSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asstran/dept/add";

	}

	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/assImportSpecialPage", method = RequestMethod.GET)
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
		
		return "hrp/ass/assspecial/asstran/dept/importoutmain";
	}
	
	
	
	
	/**
	 * @Description 添加数据 050901 资产转移主表(科室到科室)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/saveAssTranDeptSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTranDeptSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssTranDeptSpecial assTranDeptSpecial = assTranDeptSpecialService.queryByCode(mapVo);
		if(assTranDeptSpecial != null){
			if(assTranDeptSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能保存! \"}");
			}
		}

		String assTranDeptSpecialJson = assTranDeptSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assTranDeptSpecialJson);

	}
	
	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/updateConfirmTranDeptSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranDeptSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssTranDeptSpecial assTranDeptSpecial = assTranDeptSpecialService.queryByCode(mapVo);
			if (assTranDeptSpecial.getState() == 2) {
				continue;
			} 
			if (DateUtil.compareDate(assTranDeptSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranDeptDetailSpecial> list = assTranDeptSpecialService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranDeptDetailSpecial assBackDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					listCardMap.put("out_dept_id", assBackDetailSpecial.getOut_dept_id());
					listCardMap.put("out_dept_no", assBackDetailSpecial.getOut_dept_no());
					listCardMap.put("in_dept_id", assBackDetailSpecial.getIn_dept_id());
					listCardMap.put("in_dept_no", assBackDetailSpecial.getIn_dept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranDeptSpecialService.updateConfirmTranDeptSpecial(listVo, listCardVo);
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
	/*@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/updateConfirmTranDeptSpecialdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranDeptSpecialdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssTranDeptSpecial assTranDeptSpecial = assTranDeptSpecialService.queryByCode(mapVo);
			if (assTranDeptSpecial.getState() == 2) {
				continue;
			} 
			if (DateUtil.compareDate(assTranDeptSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranDeptDetailSpecial> list = assTranDeptSpecialService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranDeptDetailSpecial assBackDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					listCardMap.put("out_dept_id", assBackDetailSpecial.getOut_dept_id());
					listCardMap.put("out_dept_no", assBackDetailSpecial.getOut_dept_no());
					listCardMap.put("in_dept_id", assBackDetailSpecial.getIn_dept_id());
					listCardMap.put("in_dept_no", assBackDetailSpecial.getIn_dept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranDeptSpecialService.updateConfirmTranDeptSpecial(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/

	/**
	 * @Description 更新跳转页面 050901 资产转移主表(科室到科室)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/assTranDeptSpecialUpdatePage", method = RequestMethod.GET)
	public String assTranDeptSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssTranDeptSpecial assTranDeptSpecial = new AssTranDeptSpecial();

		assTranDeptSpecial = assTranDeptSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assTranDeptSpecial.getGroup_id());
		mode.addAttribute("hos_id", assTranDeptSpecial.getHos_id());
		mode.addAttribute("copy_code", assTranDeptSpecial.getCopy_code());
		mode.addAttribute("tran_no", assTranDeptSpecial.getTran_no());
		mode.addAttribute("out_dept_id", assTranDeptSpecial.getOut_dept_id());
		mode.addAttribute("out_dept_no", assTranDeptSpecial.getOut_dept_no());
		mode.addAttribute("out_dept_code", assTranDeptSpecial.getOut_dept_code());
		mode.addAttribute("out_dept_name", assTranDeptSpecial.getOut_dept_name());
		mode.addAttribute("in_dept_id", assTranDeptSpecial.getIn_dept_id());
		mode.addAttribute("in_dept_no", assTranDeptSpecial.getIn_dept_no());
		mode.addAttribute("in_dept_code", assTranDeptSpecial.getIn_dept_code());
		mode.addAttribute("in_dept_name", assTranDeptSpecial.getIn_dept_name());
		mode.addAttribute("create_emp", assTranDeptSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assTranDeptSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assTranDeptSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assTranDeptSpecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assTranDeptSpecial.getAudit_emp_name());
		mode.addAttribute("audit_date", assTranDeptSpecial.getAudit_date());
		mode.addAttribute("state", assTranDeptSpecial.getState());
		mode.addAttribute("state_name", assTranDeptSpecial.getState_name());
		mode.addAttribute("note", assTranDeptSpecial.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asstran/dept/update";
	}

	/**
	 * @Description 删除数据 050901 资产转移主表(科室到科室)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/deleteAssTranDeptSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranDeptSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssTranDeptSpecial assTranDeptSpecial = assTranDeptSpecialService.queryByCode(mapVo);
			if(assTranDeptSpecial != null){
				if(assTranDeptSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assTranDeptSpecialJson = assTranDeptSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranDeptSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移主表(科室到科室)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/queryAssTranDeptSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranDeptSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		String assTranDeptSpecial = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assTranDeptSpecial = assTranDeptSpecialService.query(getPage(mapVo));
		}else{
			assTranDeptSpecial = assTranDeptSpecialService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assTranDeptSpecial);

	}

	/**
	 * @Description 删除数据 050901 资产转移明细(科室到科室)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/deleteAssTranDeptDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranDeptDetailSpecial(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssTranDeptSpecial assTranDeptSpecial = assTranDeptSpecialService.queryByCode(mapVo);
			if(assTranDeptSpecial != null){
				if(assTranDeptSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assTranDeptDetailSpecialJson = assTranDeptDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranDeptDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移明细(科室到科室)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/queryAssTranDeptDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranDeptDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranDeptDetailSpecial = assTranDeptDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assTranDeptDetailSpecial);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/dept/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<String> list = assTranDeptSpecialService.queryState(mapVo);
		
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
