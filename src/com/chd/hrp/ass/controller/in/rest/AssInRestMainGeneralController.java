/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.in.rest;
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
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.in.rest.AssInRestMainGeneral;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.in.rest.AssInRestDetailGeneralService;
import com.chd.hrp.ass.service.in.rest.AssInRestMainGeneralService;
/**
 * 
 * @Description:
 * 050701 资产其他入库主表(一般设备)
 * @Table:
 * ASS_IN_REST_MAIN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssInRestMainGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssInRestMainGeneralController.class);

	// 引入Service服务
	@Resource(name = "assInRestMainGeneralService")
	private final AssInRestMainGeneralService assInRestMainGeneralService = null;

	@Resource(name = "assInRestDetailGeneralService")
	private final AssInRestDetailGeneralService assInRestDetailGeneralService = null;

	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/assInRestMainGeneralMainPage", method = RequestMethod.GET)
	public String assInRestMainGeneralMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05026",MyConfig.getSysPara("05026"));
		
		return "hrp/ass/assgeneral/assrestin/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/assInRestMainGeneralAddPage", method = RequestMethod.GET)
	public String assInRestMainGeneralAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assrestin/add";
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/assInRestMainGeneralSourcePage", method = RequestMethod.GET)
	public String assInRestMainGeneralSourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no", mapVo.get("ass_in_no"));
		
		AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
		
		mode.addAttribute("price", mapVo.get("price") != null && !"".equals(mapVo.get("price"))?mapVo.get("price"):assInRestMainGeneral.getIn_money());
		mode.addAttribute("proj_id", assInRestMainGeneral.getProj_id());
		
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assrestin/source";
	}

	/**
	 * @Description 添加数据 资产入库主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/saveAssInRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssInRestMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
		if(assInRestMainGeneral != null){
			if(assInRestMainGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}
		

		String assInMainGeneralJson = assInRestMainGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assInMainGeneralJson);
	}

	/**
	 * @Description 更新跳转页面 资产入库主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/assInRestMainGeneralUpdatePage", method = RequestMethod.GET)
	public String assInRestMainGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInRestMainGeneral assInMainGeneral = new AssInRestMainGeneral();

		assInMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainGeneral.getGroup_id());
		mode.addAttribute("hos_id", assInMainGeneral.getHos_id());
		mode.addAttribute("copy_code", assInMainGeneral.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainGeneral.getAss_in_no());
		mode.addAttribute("store_id", assInMainGeneral.getStore_id());
		mode.addAttribute("store_no", assInMainGeneral.getStore_no());
		mode.addAttribute("dept_id", assInMainGeneral.getDept_id());
		mode.addAttribute("dept_no", assInMainGeneral.getDept_no());
		mode.addAttribute("ass_purpose", assInMainGeneral.getAss_purpose());
		mode.addAttribute("proj_id", assInMainGeneral.getProj_id());
		mode.addAttribute("proj_no", assInMainGeneral.getProj_no());
		mode.addAttribute("store_code", assInMainGeneral.getStore_code());
		mode.addAttribute("store_name", assInMainGeneral.getStore_name());
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
		mode.addAttribute("bus_type_code", assInMainGeneral.getBus_type_code());
		mode.addAttribute("bus_type_name", assInMainGeneral.getBus_type_name());

		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05026",MyConfig.getSysPara("05026"));
		
		return "hrp/ass/assgeneral/assrestin/update";
	}
	
	
	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/updateConfirmInRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInRestMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			if (assInRestMainGeneral.getState() == 2) {
				continue;
			}
			
			if(DateUtil.compareDate(assInRestMainGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			List<Map<String, Object>> list = assInRestDetailGeneralService.queryByInit(mapVo);
			
			
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
				
				Integer use_state = assCardGeneralService.queryCardExistsByAssInNo(mapExists);
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
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
						depreFlag = true,      
						depreMonthFlag = true,
						curMoneyFlag = true,      
						foreMoneyFlag = true,     
						storeFlag = true,
						busTypeFlag = true,
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
					if(verification(temp.get("add_depre"),card.get("depre_money"))){
						depreFlag = false;
						break;
					}
					if(verification(temp.get("add_depre_month"),card.get("add_depre_month"))){
						depreMonthFlag = false;
						break;
					}
					if(verification(temp.get("cur_money"),card.get("cur_money"))){
						curMoneyFlag = false;
						break;
					}
					if(verification(temp.get("fore_money"),card.get("fore_money"))){
						foreMoneyFlag = false;
						break;
					}
					if(verification(temp.get("store_id"),card.get("store_id"))){
						storeFlag = false;
						break;
					}
					if(verification(temp.get("bus_type_code"),card.get("buy_type"))){
						busTypeFlag = false;
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
					return JSONObject.parseObject("{\"warn\":\"存在资产原值和卡片原值不匹配的资产,不能入库! \"}");
				}
				if(!depreFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧和卡片累计折旧不匹配的资产,不能入库! \"}");
				}
				if(!depreMonthFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧月份和卡片累计折旧月份不匹配的资产,不能入库! \"}");
				}
				if(!curMoneyFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产净值和卡片资产净值不匹配的资产,不能入库! \"}");
				}
				if(!foreMoneyFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产预留残值和预留残值不匹配的资产,不能入库! \"}");
				}
				if(!storeFlag){
					return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
				}
				if(!busTypeFlag){
					return JSONObject.parseObject("{\"warn\":\"存在业务类型不匹配的资产,不能入库! \"}");
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
		
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);
			
			mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());

			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			
			if (assInRestMainGeneral.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assInMainJson = assInRestMainGeneralService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	private boolean verification(Object obj1,Object obj2){
		//true为不相等
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
			int number1 = obj1 == null || obj1.equals("") ? 0 : Integer.parseInt(String.valueOf(obj1));
			int number2 = obj2 == null || obj2.equals("") ? 0 : Integer.parseInt(String.valueOf(obj2));
			if(number1 != number2){
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
	 * 批量生成卡片
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/initAssInRestBatchCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInRestBatchCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			
			if(assInRestMainGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInRestMainGeneralService.initAssInRestBatchCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/deleteAssInRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			
			if(assInRestMainGeneral.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainGeneralJson = assInRestMainGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainGeneralJson);

	}

	/**
	 * @Description 查询数据 资产入库主表(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/queryAssInRestMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestMainGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainGeneral = null;

		if("0".equals(mapVo.get("show_detail").toString())){
		
			assInMainGeneral = assInRestMainGeneralService.query(getPage(mapVo));

		}else{
			
			assInMainGeneral = assInRestMainGeneralService.queryDetails(getPage(mapVo));
			
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
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/deleteAssInRestDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			
			if(assInRestMainGeneral.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assInDetailGeneralJson = assInRestDetailGeneralService.deleteBatch(listVo);
		return JSONObject.parseObject(assInDetailGeneralJson);
	}

	/**
	 * @Description 查询数据 资产入库明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/queryAssInRestDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailGeneral = assInRestDetailGeneralService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailGeneral);
	}

	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/queryAssInRestCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/initAssInRestCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInRestCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			
			if(assInRestMainGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInRestMainGeneralService.initAssInCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/deleteAssInRestCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestCardGeneral(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			AssInRestMainGeneral assInRestMainGeneral = assInRestMainGeneralService.queryByCode(mapVo);
			
			if(assInRestMainGeneral.getState() > 0){
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
	 * 一般设备 其他入库  入库单状态查询 （打印校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assrestin/queryAssInRestGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assInRestMainGeneralService.queryAssInRestGeneralState(mapVo);
		
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
}

