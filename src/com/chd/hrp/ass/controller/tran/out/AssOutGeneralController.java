/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.tran.out;
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
import com.chd.hrp.ass.entity.tran.out.AssOutDetailGeneral;
import com.chd.hrp.ass.entity.tran.out.AssOutGeneral;
import com.chd.hrp.ass.service.in.AssInMainGeneralService;
import com.chd.hrp.ass.service.tran.out.AssOutDetailGeneralService;
import com.chd.hrp.ass.service.tran.out.AssOutGeneralService;
/**
 * 
 * @Description:
 * 050902 资产领用表_一般设备
 * @Table:
 * ASS_OUT_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssOutGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssOutGeneralController.class);

	// 引入Service服务
	@Resource(name = "assOutGeneralService")
	private final AssOutGeneralService assOutGeneralService = null;

	@Resource(name = "assOutDetailGeneralService")
	private final AssOutDetailGeneralService assOutDetailGeneralService = null;
	
	@Resource(name = "assInMainGeneralService")
	private final AssInMainGeneralService assInMainGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/assOutGeneralMainPage", method = RequestMethod.GET)
	public String assOutGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05089",MyConfig.getSysPara("05089"));
		
		return "hrp/ass/assgeneral/asstran/out/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/assOutGeneralAddPage", method = RequestMethod.GET)
	public String assOutGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asstran/out/add";

	}

	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/assImportSpecialPage", method = RequestMethod.GET)
	public String assImportSpecialPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("dept_name", mapVo.get("dept_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		mode.addAttribute("bill_type", mapVo.get("bill_type"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asstran/out/importoutmain";
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/assViewInMainPage", method = RequestMethod.GET)
	public String assViewInMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("out_no", mapVo.get("out_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assgeneral/asstran/out/viewinmain";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/assImportInPage", method = RequestMethod.GET)
	public String assImportInPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_name", mapVo.get("dept_name"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		mode.addAttribute("bill_type", mapVo.get("bill_type"));
		mode.addAttribute("note", mapVo.get("note"));
		mode.addAttribute("purc_emp", mapVo.get("purc_emp"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assgeneral/asstran/out/importin";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/queryInMainByOutImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInMainByOutImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial = null;
			
		assInMainSpecial = assInMainGeneralService.queryByInitOut(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/queryInMainByOutNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInMainByOutNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial = null;
			
		assInMainSpecial = assInMainGeneralService.queryInMainByOutNo(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/importAssInMainByOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAssInMainByOut(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String assPayImportMain = assOutGeneralService.importAssInMainByOut(mapVo);

		return JSONObject.parseObject(assPayImportMain);

	}
	
	
	/**
	 * @Description 添加数据 050902 资产领用表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/saveAssOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssOutGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssOutGeneral assOutGeneral = assOutGeneralService.queryByCode(mapVo);
		if(assOutGeneral != null){
			if(assOutGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}
		
		String assOutGeneralJson = assOutGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assOutGeneralJson);

	}
	
	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/updateConfirmOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmOutGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("out_no", ids[3]);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssOutGeneral assOutGeneral = assOutGeneralService.queryByCode(mapVo);
			if (DateUtil.compareDate(assOutGeneral.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			mapVo.put("bill_type", assOutGeneral.getBill_type());
			List<AssOutDetailGeneral> list = assOutGeneralService.queryByOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssOutDetailGeneral assOutDetailGeneral : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assOutDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assOutDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assOutDetailGeneral.getCopy_code());
					listCardMap.put("ass_card_no", assOutDetailGeneral.getAss_card_no());
					listCardMap.put("bill_type", assOutDetailGeneral.getBill_type());
					listCardMap.put("store_id", assOutDetailGeneral.getStore_id());
					listCardMap.put("store_no", assOutDetailGeneral.getStore_no());
					listCardMap.put("dept_id", assOutDetailGeneral.getDept_id());
					listCardMap.put("dept_no", assOutDetailGeneral.getDept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assOutDetailGeneral.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能确认! \"}");
					}
					map.put(assOutDetailGeneral.getAss_card_no(), assOutDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assOutGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assOutGeneralService.updateConfirmOutGeneral(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	/**
	 * @Description 增加确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/updateConfirmOutGeneraldetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmOutGeneraldetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("out_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			AssOutGeneral assOutGeneral = assOutGeneralService.queryByCode(mapVo);
			if (DateUtil.compareDate(assOutGeneral.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			mapVo.put("bill_type", assOutGeneral.getBill_type());
			List<AssOutDetailGeneral> list = assOutGeneralService.queryByOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssOutDetailGeneral assOutDetailGeneral : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assOutDetailGeneral.getGroup_id());
					listCardMap.put("hos_id", assOutDetailGeneral.getHos_id());
					listCardMap.put("copy_code", assOutDetailGeneral.getCopy_code());
					listCardMap.put("ass_card_no", assOutDetailGeneral.getAss_card_no());
					listCardMap.put("bill_type", assOutDetailGeneral.getBill_type());
					listCardMap.put("store_id", assOutDetailGeneral.getStore_id());
					listCardMap.put("store_no", assOutDetailGeneral.getStore_no());
					listCardMap.put("dept_id", assOutDetailGeneral.getDept_id());
					listCardMap.put("dept_no", assOutDetailGeneral.getDept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assOutDetailGeneral.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能确认! \"}");
					}
					map.put(assOutDetailGeneral.getAss_card_no(), assOutDetailGeneral.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assOutGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assOutGeneralService.updateConfirmOutGeneral(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/

	/**
	 * @Description 更新跳转页面 050902 资产领用表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/assOutGeneralUpdatePage", method = RequestMethod.GET)
	public String assOutGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssOutGeneral assOutGeneral = new AssOutGeneral();

		assOutGeneral = assOutGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assOutGeneral.getGroup_id());
		mode.addAttribute("hos_id", assOutGeneral.getHos_id());
		mode.addAttribute("copy_code", assOutGeneral.getCopy_code());
		mode.addAttribute("out_no", assOutGeneral.getOut_no());
		mode.addAttribute("bill_type", assOutGeneral.getBill_type());
		mode.addAttribute("bill_type_name", assOutGeneral.getBill_type_name());
		mode.addAttribute("store_id", assOutGeneral.getStore_id());
		mode.addAttribute("store_no", assOutGeneral.getStore_no());
		mode.addAttribute("dept_id", assOutGeneral.getDept_id());
		mode.addAttribute("dept_no", assOutGeneral.getDept_no());
		mode.addAttribute("store_code", assOutGeneral.getStore_code());
		mode.addAttribute("store_name", assOutGeneral.getStore_name());
		mode.addAttribute("dept_code", assOutGeneral.getDept_code());
		mode.addAttribute("dept_name", assOutGeneral.getDept_name());
		mode.addAttribute("create_emp", assOutGeneral.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assOutGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assOutGeneral.getAudit_emp());
		mode.addAttribute("audit_date", assOutGeneral.getAudit_date());
		mode.addAttribute("state", assOutGeneral.getState());
		mode.addAttribute("note", assOutGeneral.getNote());
		mode.addAttribute("purc_emp", assOutGeneral.getPurc_emp());
		mode.addAttribute("purc_emp_name", assOutGeneral.getPurc_emp_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assgeneral/asstran/out/update";
	}

	/**
	 * @Description 删除数据 050902 资产领用表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/deleteAssOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssOutGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("out_no", ids[3]);
			
			AssOutGeneral assOutGeneral = assOutGeneralService.queryByCode(mapVo);
			if(assOutGeneral != null){
				if(assOutGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assOutGeneralJson = assOutGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assOutGeneralJson);

	}

	/**
	 * @Description 查询数据 050902 资产领用表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/queryAssOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssOutGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		String assOutGeneral = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assOutGeneral = assOutGeneralService.query(getPage(mapVo));
		}else{
			assOutGeneral = assOutGeneralService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assOutGeneral);

	}

	/**
	 * @Description 删除数据 050901 资产领用明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/deleteAssOutDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssOutDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("out_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssOutGeneral assOutGeneral = assOutGeneralService.queryByCode(mapVo);
			if(assOutGeneral != null){
				if(assOutGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assOutDetailGeneralJson = assOutDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assOutDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产领用明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/queryAssOutDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssOutDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assOutDetailGeneral = assOutDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assOutDetailGeneral);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assOutGeneralService.queryState(mapVo);
		
		if(list.size()>0){
			for (String tran_no : list) {
				str += tran_no +" ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据未确认,无法打印!\",\"state\":\"true\"}");
			
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
		
	}
	
	/**
	 * 冲账
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asstran/out/offsetOutGeneral", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetOutGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date  = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("create_emp", SessionManager.getUserId());
			
			mapVo.put("out_no", ids[3]);
			
			listVo.add(mapVo);
		}
		
		String assJson;
		try {
			assJson = assOutGeneralService.offsetOutGeneral(listVo);
		} catch (Exception e) {
			assJson = e.getMessage();
		}
		return JSONObject.parseObject(assJson);
		
	}
	
}

