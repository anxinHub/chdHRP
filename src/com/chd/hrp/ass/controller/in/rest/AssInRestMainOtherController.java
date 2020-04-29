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
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.in.rest.AssInRestMainOther;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.in.rest.AssInRestDetailOtherService;
import com.chd.hrp.ass.service.in.rest.AssInRestMainOtherService;
/**
 * 
 * @Description:
 * 050701 资产其他入库主表(其他固定资产)
 * @Table:
 * ASS_IN_REST_MAIN_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssInRestMainOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssInRestMainOtherController.class);

	// 引入Service服务
	@Resource(name = "assInRestMainOtherService")
	private final AssInRestMainOtherService assInRestMainOtherService = null;

	@Resource(name = "assInRestDetailOtherService")
	private final AssInRestDetailOtherService assInRestDetailOtherService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/assInRestMainOtherMainPage", method = RequestMethod.GET)
	public String assInRestMainOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05036",MyConfig.getSysPara("05036"));
		
		return "hrp/ass/assother/assrestin/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/assInRestMainOtherAddPage", method = RequestMethod.GET)
	public String assInRestMainOtherAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assrestin/add";
	}

	/**
	 * 跳转资金来源页面
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/assInRestMainOtherSourcePage", method = RequestMethod.GET)
	public String assInRestMainOtherSourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no", mapVo.get("ass_in_no"));
		
		AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
		
		mode.addAttribute("price", mapVo.get("price") != null && !"".equals(mapVo.get("price"))?mapVo.get("price"):assInRestMainOther.getIn_money());
		mode.addAttribute("proj_id", assInRestMainOther.getProj_id());
		
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assrestin/source";
	}

	/**
	 * @Description 添加数据 资产入库主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/saveAssInRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssInRestMainOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
		if(assInRestMainOther != null){
			if(assInRestMainOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}
		

		String assInMainOtherJson = assInRestMainOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assInMainOtherJson);
	}

	/**
	 * @Description 更新跳转页面 资产入库主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/assInRestMainOtherUpdatePage", method = RequestMethod.GET)
	public String assInRestMainOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInRestMainOther assInMainOther = new AssInRestMainOther();

		assInMainOther = assInRestMainOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainOther.getGroup_id());
		mode.addAttribute("hos_id", assInMainOther.getHos_id());
		mode.addAttribute("copy_code", assInMainOther.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainOther.getAss_in_no());
		mode.addAttribute("store_id", assInMainOther.getStore_id());
		mode.addAttribute("store_no", assInMainOther.getStore_no());
		mode.addAttribute("dept_id", assInMainOther.getDept_id());
		mode.addAttribute("dept_no", assInMainOther.getDept_no());
		mode.addAttribute("ass_purpose", assInMainOther.getAss_purpose());
		mode.addAttribute("proj_id", assInMainOther.getProj_id());
		mode.addAttribute("proj_no", assInMainOther.getProj_no());
		mode.addAttribute("store_code", assInMainOther.getStore_code());
		mode.addAttribute("store_name", assInMainOther.getStore_name());
		mode.addAttribute("dept_code", assInMainOther.getDept_code());
		mode.addAttribute("dept_name", assInMainOther.getDept_name());
		mode.addAttribute("ass_purpose_name", assInMainOther.getAss_purpose_name());
		mode.addAttribute("proj_code", assInMainOther.getProj_code());
		mode.addAttribute("proj_name", assInMainOther.getProj_name());
		mode.addAttribute("in_money", assInMainOther.getIn_money());
		mode.addAttribute("note", assInMainOther.getNote());
		mode.addAttribute("create_emp", assInMainOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assInMainOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assInMainOther.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("in_date", assInMainOther.getIn_date());
		mode.addAttribute("confirm_emp", assInMainOther.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assInMainOther.getConfirm_emp_name());
		mode.addAttribute("state", assInMainOther.getState());
		mode.addAttribute("state_name", assInMainOther.getState_name());
		mode.addAttribute("bus_type_code", assInMainOther.getBus_type_code());
		mode.addAttribute("bus_type_name", assInMainOther.getBus_type_name());

		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05036",MyConfig.getSysPara("05036"));
		
		return "hrp/ass/assother/assrestin/update";
	}
	
	
	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/updateConfirmInRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInRestMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			if (assInRestMainOther.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assInRestMainOther.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			List<Map<String, Object>> list = assInRestDetailOtherService.queryByInit(mapVo);
			
			
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
				
				Integer use_state = assCardOtherService.queryCardExistsByAssInNo(mapExists);
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state != Integer.parseInt(temp.get("in_amount").toString())){
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardOtherService.queryAssCardByAssInNo(mapExists);
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

			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			
			if (assInRestMainOther.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assInMainJson = assInRestMainOtherService.updateConfirm(listVo);
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
	@RequestMapping(value = "/hrp/ass/assother/assrestin/initAssInRestBatchCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInRestBatchCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			
			if(assInRestMainOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInRestMainOtherService.initAssInRestBatchCardOther(mapVo);
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
	@RequestMapping(value = "/hrp/ass/assother/assrestin/deleteAssInRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			
			if(assInRestMainOther.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainOtherJson = assInRestMainOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainOtherJson);

	}

	/**
	 * @Description 查询数据 资产入库主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/queryAssInRestMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestMainOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainOther = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
		
			assInMainOther = assInRestMainOtherService.query(getPage(mapVo));

		}else{
			
			assInMainOther = assInRestMainOtherService.queryDetails(getPage(mapVo));
			
		}
		return JSONObject.parseObject(assInMainOther);
	}

	/**
	 * @Description 删除数据 明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/deleteAssInRestDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			
			if(assInRestMainOther.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assInDetailOtherJson = assInRestDetailOtherService.deleteBatch(listVo);
		return JSONObject.parseObject(assInDetailOtherJson);
	}

	/**
	 * @Description 查询数据 资产入库明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/queryAssInRestDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailOther = assInRestDetailOtherService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailOther);
	}

	@RequestMapping(value = "/hrp/ass/assother/assrestin/queryAssInRestCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardOtherService.query(getPage(mapVo));
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
	
	@RequestMapping(value = "/hrp/ass/assother/assrestin/initAssInRestCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInRestCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			
			if(assInRestMainOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInRestMainOtherService.initAssInCardOther(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assother/assrestin/deleteAssInRestCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInRestCardOther(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			AssInRestMainOther assInRestMainOther = assInRestMainOtherService.queryByCode(mapVo);
			
			if(assInRestMainOther.getState() > 0){
				flag = false;
				break;
			}

			AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
			if (assCardOther.getUse_state() > 0) {
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

		String assCardOtherJson = "";
		try {
			assCardOtherJson = assCardOtherService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardOtherJson);

	}
	
	/**
	 * 其他固定资产 其他入库  入库单状态查询 （打印校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assrestin/queryAssInRestOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInRestOtherState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assInRestMainOtherService.queryAssInRestOtherState(mapVo);
		
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

