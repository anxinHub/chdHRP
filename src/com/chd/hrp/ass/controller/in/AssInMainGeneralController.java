package com.chd.hrp.ass.controller.in;

import java.math.BigDecimal;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.in.AssInMainGeneral;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.in.AssInDetailGeneralService;
import com.chd.hrp.ass.service.in.AssInMainGeneralService;

/**
 * 
 * @Description: 资产入库主表(一般设备)
 * @Table: ASS_IN_MAIN_General
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssInMainGeneralController extends BaseController {

	private static Logger logger = Logger.getLogger(AssInMainGeneralController.class);

	// 引入Service服务
	@Resource(name = "assInMainGeneralService")
	private final AssInMainGeneralService assInMainGeneralService = null;

	@Resource(name = "assInDetailGeneralService")
	private final AssInDetailGeneralService assInDetailGeneralService = null;

	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/assInMainGeneralMainPage", method = RequestMethod.GET)
	public String assInMainGeneralMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05024",MyConfig.getSysPara("05024"));
		
		return "hrp/ass/assgeneral/assin/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/assInMainGeneralAddPage", method = RequestMethod.GET)
	public String assInMainGeneralAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05024",MyConfig.getSysPara("05024"));
		
		return "hrp/ass/assgeneral/assin/add";
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/assInMainGeneralSourcePage", method = RequestMethod.GET)
	public String assInMainGeneralSourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no", mapVo.get("ass_in_no"));
		
		AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
		
		mode.addAttribute("price", mapVo.get("price") != null && !"".equals(mapVo.get("price"))?mapVo.get("price"):assInMainGeneral.getIn_money());
		mode.addAttribute("proj_id", assInMainGeneral.getProj_id());
		
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assin/source";
	}

	/**
	 * @Description 添加数据 资产入库主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/saveAssInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssInMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
		if(assInMainGeneral != null){
			if(assInMainGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}
		

		String assInMainGeneralJson = assInMainGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assInMainGeneralJson);
	}

	/**
	 * @Description 更新跳转页面 资产入库主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/assInMainGeneralUpdatePage", method = RequestMethod.GET)
	public String assInMainGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {



		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainGeneral assInMainGeneral = new AssInMainGeneral();

		assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainGeneral.getGroup_id());
		mode.addAttribute("hos_id", assInMainGeneral.getHos_id());
		mode.addAttribute("copy_code", assInMainGeneral.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainGeneral.getAss_in_no());
		mode.addAttribute("store_id", assInMainGeneral.getStore_id());
		mode.addAttribute("store_no", assInMainGeneral.getStore_no());
		mode.addAttribute("ven_id", assInMainGeneral.getVen_id());
		mode.addAttribute("ven_no", assInMainGeneral.getVen_no());
		mode.addAttribute("purc_emp", assInMainGeneral.getPurc_emp());
		mode.addAttribute("dept_id", assInMainGeneral.getDept_id());
		mode.addAttribute("dept_no", assInMainGeneral.getDept_no());
		mode.addAttribute("ass_purpose", assInMainGeneral.getAss_purpose());
		mode.addAttribute("proj_id", assInMainGeneral.getProj_id());
		mode.addAttribute("proj_no", assInMainGeneral.getProj_no());
		mode.addAttribute("store_code", assInMainGeneral.getStore_code());
		mode.addAttribute("store_name", assInMainGeneral.getStore_name());
		mode.addAttribute("ven_code", assInMainGeneral.getVen_code());
		mode.addAttribute("ven_name", assInMainGeneral.getVen_name());
		mode.addAttribute("purc_emp_name", assInMainGeneral.getPurc_emp_name());
		mode.addAttribute("dept_code", assInMainGeneral.getDept_code());
		mode.addAttribute("dept_name", assInMainGeneral.getDept_name());
		mode.addAttribute("ass_purpose_name", assInMainGeneral.getAss_purpose_name());
		mode.addAttribute("proj_code", assInMainGeneral.getProj_code());
		mode.addAttribute("proj_name", assInMainGeneral.getProj_name());
		mode.addAttribute("in_money", assInMainGeneral.getIn_money());
		mode.addAttribute("note", assInMainGeneral.getNote());
		mode.addAttribute("create_emp", assInMainGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assInMainGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assInMainGeneral.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("in_date", assInMainGeneral.getIn_date());
		mode.addAttribute("confirm_emp", assInMainGeneral.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assInMainGeneral.getConfirm_emp_name());
		mode.addAttribute("state", assInMainGeneral.getState());
		mode.addAttribute("state_name", assInMainGeneral.getState_name());
		mode.addAttribute("invoice_no", assInMainGeneral.getInvoice_no());
		mode.addAttribute("invoice_date", DateUtil.dateToString(assInMainGeneral.getInvoice_date(),"yyyy-MM-dd"));
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05024",MyConfig.getSysPara("05024"));

		return "hrp/ass/assgeneral/assin/update";
	}
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/updateToExamineInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamineInMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);

			AssApplyDept assApplyDept = assInMainGeneralService.queryByCode(mapVo);
			
			if (assApplyDept.getState() == 1) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复审核! \"}");
		}
		
		try {
			assInMainJson = assInMainGeneralService.updateAudit(listVo);
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
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/updateNotToExamineInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineInMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);

			AssApplyDept assApplyDept = assInMainGeneralService.queryByCode(mapVo);
			
			if (assApplyDept.getState() == 0) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复销审! \"}");
		}
		
		try {
			assInMainJson = assInMainGeneralService.updateReAudit(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/updateConfirmInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String vdata : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = vdata.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			
			
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			if (assInMainGeneral.getState() == 2) {
				continue;
			}
			
			if(DateUtil.compareDate(assInMainGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			
			List<Map<String, Object>> list = assInDetailGeneralService.queryByInit(mapVo);//资产明细数据
			
			if(list.size() == 0){
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for(Map<String, Object> temp : list){
				
				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));
				
				mapExists.put("ass_id", temp.get("ass_id"));
				
				mapExists.put("ass_no", temp.get("ass_no"));
				
				mapExists.put("ass_spec", temp.get("ass_spec"));
				
				mapExists.put("ass_mondl", temp.get("ass_model"));
				
				Integer use_state = assCardGeneralService.queryCardExistsByAssInNo(mapExists);//通过资产
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state != Integer.parseInt(temp.get("in_amount").toString())){
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardGeneralService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true,
						specFlag = true,
						modelFlag = true,
						brandFlag = true,
						facFlag = true,
						unitFlag = true,
						priceFlag = true,
						storeFlag = true,
						venFlag = true,
						//deptFlag = true,
						purposeFlag = true,
						projFlag = true;
				
				for(Map<String, Object> card : cardList){
					if(verification(temp.get("ass_id"),card.get("ass_id"))){
						assFlag = false;
						break;
					}
					if(verification(temp.get("ass_spec"),card.get("ass_spec"))){
						specFlag = false;
						break;
					}
					if(verification(temp.get("ass_model"),card.get("ass_mondl"))){
						modelFlag = false;
						break;
					}
					if(verification(temp.get("ass_brand"),card.get("ass_brand"))){
						brandFlag = false;
						break;
					}
					if(verification(temp.get("fac_id"),card.get("fac_id"))){
						facFlag = false;
						break;
					}
					if(verification(temp.get("unit_code"),card.get("unit_code"))){
						unitFlag = false;
						break;
					}
					//if(verification(temp.get("price"),card.get("price"))){
						//priceFlag = false;
						//break;
					//}
					if(verification(temp.get("store_id"),card.get("store_id"))){
						storeFlag = false;
						break;
					}
					if(verification(temp.get("ven_id"),card.get("ven_id"))){
						venFlag = false;
						break;
					}
					//if(verification(temp.get("dept_id"),card.get("dept_id"))){
						//deptFlag = false;
						//break;
					//}
					if(verification(temp.get("ass_purpose"),card.get("ass_purpose"))){
						purposeFlag = false;
						break;
					}
					if(verification(temp.get("proj_id"),card.get("proj_id"))){
						projFlag = false;
						break;
					}
				}
				if(!assFlag){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if(!specFlag){
					return JSONObject.parseObject("{\"warn\":\"存在规格不匹配的资产,不能入库! \"}");
				}
				if(!modelFlag){
					return JSONObject.parseObject("{\"warn\":\"存在型号不匹配的资产,不能入库! \"}");
				}
				if(!brandFlag){
					return JSONObject.parseObject("{\"warn\":\"存在品牌不匹配的资产,不能入库! \"}");
				}
				if(!facFlag){
					return JSONObject.parseObject("{\"warn\":\"存在生产厂商不匹配的资产,不能入库! \"}");
				}
				if(!unitFlag){
					return JSONObject.parseObject("{\"warn\":\"存在计量单位不匹配的资产,不能入库! \"}");
				}
				if(!priceFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
				if(!storeFlag){
					return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
				}
				if(!venFlag){
					return JSONObject.parseObject("{\"warn\":\"存在供应商不匹配的资产,不能入库! \"}");
				}
				//if(!deptFlag){
					//return JSONObject.parseObject("{\"warn\":\"存在领用科室不匹配的资产,不能入库! \"}");
				//}
				if(!purposeFlag){
					return JSONObject.parseObject("{\"warn\":\"存在用途不匹配的资产,不能入库! \"}");
				}
				if(!projFlag){
					return JSONObject.parseObject("{\"warn\":\"存在项目不匹配的资产,不能入库! \"}");
				}
			}
			
		}
		
		
		for (String data : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = data.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);
			
			mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());
			
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);//主表
			if (assInMainGeneral.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assInMainJson = assInMainGeneralService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/deleteAssInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_in_no", ids[3]);
			
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			
			if(assInMainGeneral.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainGeneralJson = assInMainGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainGeneralJson);
 
	}
	private boolean verification(Object obj1,Object obj2){
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
				
				BigDecimal bd1 = new BigDecimal(obj1.toString());
				
				BigDecimal bd2 = new BigDecimal(obj2.toString());
				
				
				if(bd1.compareTo(bd2) != 0){
					return true;
				}else{
					return false;
				}
			}
			if(obj1 == null && obj2 == null){
				return false;
			}
			if(obj1 == null && obj2 != null){
				return true;
			}
			if(obj1 != null && obj2 == null){
				return true;
			}
			if(obj1 != null && obj2 != null){
				if(!obj1.equals(obj2)){
					return true;
				}else {
					return false;
				}
			}else{
				return false;
			}
	}
	/**
	 * @Description 查询数据 资产入库主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/queryAssInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		String assInMainGeneral = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assInMainGeneral = assInMainGeneralService.query(getPage(mapVo));
		
		}else{
			
			assInMainGeneral = assInMainGeneralService.queryDetails(getPage(mapVo));
			
		}
		return JSONObject.parseObject(assInMainGeneral);
	}
	/**
	 * @Description 删除数据 明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/deleteAssInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_in_no", ids[3]);
			mapVo.put("ass_id", ids[4]);
			mapVo.put("ass_no", ids[5]);
			mapVo.put("ass_spec", ids[6]);
			mapVo.put("ass_model", ids[7]);
			
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			
			if(assInMainGeneral.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assInDetailGeneralJson = assInDetailGeneralService.deleteBatch(listVo);
		return JSONObject.parseObject(assInDetailGeneralJson);
	}

	/**
	 * @Description 查询数据 资产入库明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/queryAssInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailGeneral = assInDetailGeneralService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailGeneral);
	}

	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/queryAssInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardGeneralService.query(getPage(mapVo));
		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				jsonarray.remove(i);
			}
		}
		json.put("Rows", jsonarray);
		return JSONObject.parseObject(json.toString());
	}
	/**
	 * 生成卡片号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/initAssInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			
			if(assInMainGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInMainGeneralService.initAssInCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
   /**
    * 批量生产卡片号
    * @param mapVo
    * @param mode
    * @return
    * @throws Exception
    */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/initAssInBatchCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInBatchCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			
			if(assInMainGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInMainGeneralService.initAssInBatchCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/deleteAssInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCard(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		boolean flag1 = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("ass_in_no", ids[4]);
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			
			if(assInMainGeneral.getState() > 0){
				flag = false;
				break;
			}

			AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			if (assCardGeneral.getUse_state() > 0) {
				flag1 = false;
			}

			listVo.add(mapVo);

		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		if (!flag1) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		String assCardGeneralJson = "";
		try {
			assCardGeneralJson = assCardGeneralService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	/**
	 * 一般设备 采购入库  入库单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/queryAssInGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//一般设备 采购入库  入库单状态查询  （打印时校验数据专用）
		List<String> list = assInMainGeneralService.queryAssInGeneralState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"入库单【"+str+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	/**
	 * @Description 更新发票号
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/updateAssInMainGeneralBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInMainGeneralBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assInMainGeneralService.updateAssInMainBillNo(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * 冲账(cjc)
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/offsetInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetInGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		//把页面传过了的数据按,拆分,并且赋值
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("create_emp", SessionManager.getUserId());
			
			mapVo.put("ass_in_no", ids[3]);
			
			listVo.add(mapVo);
		}
		
		String assJson;
		try {
			
			assJson = assInMainGeneralService.offsetInGeneral(listVo);
			
		} catch (Exception e) {
			assJson = e.getMessage();
		}
		return JSONObject.parseObject(assJson);
		
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/updateIsPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIsPrint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String paramVo = mapVo.get("paramVo").toString();
		for (String id : paramVo.split(",")) {
			Map<String, Object> map= new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("ass_in_no", id);
			
			listVo.add(map);
		}
		
		String assCard = assInMainGeneralService.updateIsPrint(listVo);
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assin/assInGenerateBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> assInGenerateBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String paramVo = mapVo.get("params").toString();
		for (String id : paramVo.split(",")) {
			Map<String, Object> map= new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("ass_in_no", id);
			
			listVo.add(map);
		}
		
		String assCard = assInMainGeneralService.assInGenerateBill(listVo);
		
		return JSONObject.parseObject(assCard);
	}

}
