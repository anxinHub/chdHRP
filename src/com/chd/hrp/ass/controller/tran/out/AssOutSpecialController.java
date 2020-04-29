
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
import com.chd.hrp.ass.entity.tran.out.AssOutDetailSpecial;
import com.chd.hrp.ass.entity.tran.out.AssOutSpecial;
import com.chd.hrp.ass.service.in.AssInMainSpecialService;
import com.chd.hrp.ass.service.tran.out.AssOutDetailSpecialService;
import com.chd.hrp.ass.service.tran.out.AssOutSpecialService;

/**
 * 
 * @Description: 050902 资产领用表(专用设备)
 * @Table: ASS_OUT_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssOutSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssOutSpecialController.class);

	// 引入Service服务
	@Resource(name = "assOutSpecialService")
	private final AssOutSpecialService assOutSpecialService = null;

	@Resource(name = "assOutDetailSpecialService")
	private final AssOutDetailSpecialService assOutDetailSpecialService = null;
	
	@Resource(name = "assInMainSpecialService")
	private final AssInMainSpecialService assInMainSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/assOutSpecialMainPage", method = RequestMethod.GET)
	public String assOutSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05088",MyConfig.getSysPara("05088"));
		
		return "hrp/ass/assspecial/asstran/out/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/assOutSpecialAddPage", method = RequestMethod.GET)
	public String assOutSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asstran/out/add";

	}

	
	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/assImportSpecialPage", method = RequestMethod.GET)
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
		
		return "hrp/ass/assspecial/asstran/out/importoutmain";
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/assViewInMainPage", method = RequestMethod.GET)
	public String assViewInMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("out_no", mapVo.get("out_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assspecial/asstran/out/viewinmain";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/assImportInPage", method = RequestMethod.GET)
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
		
		return "hrp/ass/assspecial/asstran/out/importin";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/queryInMainByOutImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInMainByOutImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial = null;
			
		assInMainSpecial = assInMainSpecialService.queryByInitOut(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/queryInMainByOutNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInMainByOutNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial = null;
			
		assInMainSpecial = assInMainSpecialService.queryInMainByOutNo(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/importAssInMainByOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAssInMainByOut(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String assPayImportMain = assOutSpecialService.importAssInMainByOut(mapVo);

		return JSONObject.parseObject(assPayImportMain);

	}
	
	
	
	
	/**
	 * @Description 添加数据 050902 资产领用表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/saveAssOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssOutSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		
		AssOutSpecial assOutSpecial = assOutSpecialService.queryByCode(mapVo);
		if(assOutSpecial != null){
			if(assOutSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}
		
		String assOutSpecialJson = assOutSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assOutSpecialJson);

	}
	
	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/updateConfirmOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmOutSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			
			AssOutSpecial assOutSpecial = assOutSpecialService.queryByCode(mapVo);
			if (assOutSpecial.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assOutSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			mapVo.put("bill_type", assOutSpecial.getBill_type());
			List<AssOutDetailSpecial> list = assOutSpecialService.queryByOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssOutDetailSpecial assOutDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assOutDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assOutDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assOutDetailSpecial.getCopy_code());
					listCardMap.put("ass_card_no", assOutDetailSpecial.getAss_card_no());
					listCardMap.put("bill_type", assOutDetailSpecial.getBill_type());
					listCardMap.put("store_id", assOutDetailSpecial.getStore_id());
					listCardMap.put("store_no", assOutDetailSpecial.getStore_no());
					listCardMap.put("dept_id", assOutDetailSpecial.getDept_id());
					listCardMap.put("dept_no", assOutDetailSpecial.getDept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assOutDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能确认! \"}");
					}
					map.put(assOutDetailSpecial.getAss_card_no(), assOutDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assOutSpecialService.updateConfirmOutSpecial(listVo, listCardVo);
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
	 */
	/*@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/updateConfirmOutSpecialdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmOutSpecialdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			
			AssOutSpecial assOutSpecial = assOutSpecialService.queryByCode(mapVo);
			if (assOutSpecial.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assOutSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			mapVo.put("bill_type", assOutSpecial.getBill_type());
			List<AssOutDetailSpecial> list = assOutSpecialService.queryByOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssOutDetailSpecial assOutDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assOutDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assOutDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assOutDetailSpecial.getCopy_code());
					listCardMap.put("ass_card_no", assOutDetailSpecial.getAss_card_no());
					listCardMap.put("bill_type", assOutDetailSpecial.getBill_type());
					listCardMap.put("store_id", assOutDetailSpecial.getStore_id());
					listCardMap.put("store_no", assOutDetailSpecial.getStore_no());
					listCardMap.put("dept_id", assOutDetailSpecial.getDept_id());
					listCardMap.put("dept_no", assOutDetailSpecial.getDept_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assOutDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能确认! \"}");
					}
					map.put(assOutDetailSpecial.getAss_card_no(), assOutDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assOutSpecialService.updateConfirmOutSpecial(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/
	
	
	
	/**
	 * @Description 更新跳转页面 050902 资产领用表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/assOutSpecialUpdatePage", method = RequestMethod.GET)
	public String assOutSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssOutSpecial assOutSpecial = new AssOutSpecial();

		assOutSpecial = assOutSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assOutSpecial.getGroup_id());
		mode.addAttribute("hos_id", assOutSpecial.getHos_id());
		mode.addAttribute("copy_code", assOutSpecial.getCopy_code());
		mode.addAttribute("out_no", assOutSpecial.getOut_no());
		mode.addAttribute("bill_type", assOutSpecial.getBill_type());
		mode.addAttribute("bill_type_name", assOutSpecial.getBill_type_name());
		mode.addAttribute("store_id", assOutSpecial.getStore_id());
		mode.addAttribute("store_no", assOutSpecial.getStore_no());
		mode.addAttribute("dept_id", assOutSpecial.getDept_id());
		mode.addAttribute("dept_no", assOutSpecial.getDept_no());
		mode.addAttribute("store_code", assOutSpecial.getStore_code());
		mode.addAttribute("store_name", assOutSpecial.getStore_name());
		mode.addAttribute("dept_code", assOutSpecial.getDept_code());
		mode.addAttribute("dept_name", assOutSpecial.getDept_name());
		mode.addAttribute("create_emp", assOutSpecial.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assOutSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assOutSpecial.getAudit_emp());
		mode.addAttribute("audit_date", assOutSpecial.getAudit_date());
		mode.addAttribute("state", assOutSpecial.getState());
		mode.addAttribute("note", assOutSpecial.getNote());
		mode.addAttribute("purc_emp", assOutSpecial.getPurc_emp());
		mode.addAttribute("purc_emp_name", assOutSpecial.getPurc_emp_name());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asstran/out/update";
	}

	/**
	 * @Description 删除数据 050902 资产领用表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/deleteAssOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssOutSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssOutSpecial assOutSpecial = assOutSpecialService.queryByCode(mapVo);
			if(assOutSpecial != null){
				if(assOutSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
				mapVo.put("bill_type", assOutSpecial.getBill_type());
				
			}

			listVo.add(mapVo);

		}

		String assOutSpecialJson = assOutSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assOutSpecialJson);

	}

	/**
	 * @Description 查询数据 050902 资产领用表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/queryAssOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssOutSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		String assOutSpecial = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assOutSpecial = assOutSpecialService.query(getPage(mapVo));
		}else{
			assOutSpecial = assOutSpecialService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assOutSpecial);

	}

	/**
	 * @Description 删除数据 050901 资产领用明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/deleteAssOutDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssOutDetailSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssOutSpecial assOutSpecial = assOutSpecialService.queryByCode(mapVo);
			if(assOutSpecial != null){
				if(assOutSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assOutDetailSpecialJson = assOutDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assOutDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产领用明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/queryAssOutDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssOutDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assOutDetailSpecial = assOutDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assOutDetailSpecial);

	}

	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assOutSpecialService.queryState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/out/offsetOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetOutSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			assJson = assOutSpecialService.offsetOutSpecial(listVo);
		} catch (Exception e) {
			assJson = e.getMessage();
		}
		return JSONObject.parseObject(assJson);
		
	}

}
