/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
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
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.in.AssInMainOther;
import com.chd.hrp.ass.entity.in.AssInMainOther;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.in.AssInDetailOtherService;
import com.chd.hrp.ass.service.in.AssInMainOtherService;
/**
 * 
 * @Description:
 * 资产入库主表(其他固定资产)
 * @Table:
 * ASS_IN_MAIN_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssInMainOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssInMainOtherController.class);

	// 引入Service服务
	@Resource(name = "assInMainOtherService")
	private final AssInMainOtherService assInMainOtherService = null;

	@Resource(name = "assInDetailOtherService")
	private final AssInDetailOtherService assInDetailOtherService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/assInMainOtherMainPage", method = RequestMethod.GET)
	public String assInMainOtherMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05034",MyConfig.getSysPara("05034"));
		
		return "hrp/ass/assother/assin/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/assInMainOtherAddPage", method = RequestMethod.GET)
	public String assInMainOtherAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05034",MyConfig.getSysPara("05034"));
		
		return "hrp/ass/assother/assin/add";
	}
	/**
	 * 跳转资金来源页面
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/assInMainOtherSourcePage", method = RequestMethod.GET)
	public String assInMainOtherSourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no", mapVo.get("ass_in_no"));
		
		AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
		
		mode.addAttribute("price", mapVo.get("price") != null && !"".equals(mapVo.get("price"))?mapVo.get("price"):assInMainOther.getIn_money());
		mode.addAttribute("proj_id", assInMainOther.getProj_id());
		
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assin/source";
	}
	/**
	 * @Description 添加数据 资产入库主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/saveAssInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssInMainOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
		if(assInMainOther != null){
			if(assInMainOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}
		

		String assInMainOtherJson = assInMainOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assInMainOtherJson);
	}

	/**
	 * @Description 更新跳转页面 资产入库主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/assInMainOtherUpdatePage", method = RequestMethod.GET)
	public String assInMainOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {



		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainOther assInMainOther = new AssInMainOther();

		assInMainOther = assInMainOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainOther.getGroup_id());
		mode.addAttribute("hos_id", assInMainOther.getHos_id());
		mode.addAttribute("copy_code", assInMainOther.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainOther.getAss_in_no());
		mode.addAttribute("store_id", assInMainOther.getStore_id());
		mode.addAttribute("store_no", assInMainOther.getStore_no());
		mode.addAttribute("ven_id", assInMainOther.getVen_id());
		mode.addAttribute("ven_no", assInMainOther.getVen_no());
		mode.addAttribute("purc_emp", assInMainOther.getPurc_emp());
		mode.addAttribute("dept_id", assInMainOther.getDept_id());
		mode.addAttribute("dept_no", assInMainOther.getDept_no());
		mode.addAttribute("ass_purpose", assInMainOther.getAss_purpose());
		mode.addAttribute("proj_id", assInMainOther.getProj_id());
		mode.addAttribute("proj_no", assInMainOther.getProj_no());
		mode.addAttribute("store_code", assInMainOther.getStore_code());
		mode.addAttribute("store_name", assInMainOther.getStore_name());
		mode.addAttribute("ven_code", assInMainOther.getVen_code());
		mode.addAttribute("ven_name", assInMainOther.getVen_name());
		mode.addAttribute("purc_emp_name", assInMainOther.getPurc_emp_name());
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
		mode.addAttribute("invoice_no", assInMainOther.getInvoice_no());
		mode.addAttribute("invoice_date", DateUtil.dateToString(assInMainOther.getInvoice_date(),"yyyy-MM-dd"));
	
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05034",MyConfig.getSysPara("05034"));
		
		return "hrp/ass/assother/assin/update";
	}
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/updateToExamineInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamineInMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssApplyDept assApplyDept = assInMainOtherService.queryByCode(mapVo);
			
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
			assInMainJson = assInMainOtherService.updateAudit(listVo);
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
	@RequestMapping(value = "/hrp/ass/assother/assin/updateNotToExamineInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineInMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssApplyDept assApplyDept = assInMainOtherService.queryByCode(mapVo);
			
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
			assInMainJson = assInMainOtherService.updateReAudit(listVo);
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
	@RequestMapping(value = "/hrp/ass/assother/assin/updateConfirmInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			if (assInMainOther.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assInMainOther.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			
			List<Map<String, Object>> list = assInDetailOtherService.queryByInit(mapVo);//资产明细数据
			
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
				
				Integer use_state = assCardOtherService.queryCardExistsByAssInNo(mapExists);//通过资产
				
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
			
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);//主表
			if (assInMainOther.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assInMainJson = assInMainOtherService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	/**
	 * 批量生成卡片
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/initAssInBatchCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInBatchCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			
			if(assInMainOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInMainOtherService.initAssInBatchCardOther(mapVo);
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
	@RequestMapping(value = "/hrp/ass/assother/assin/deleteAssInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInMainOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			
			if(assInMainOther.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainOtherJson = assInMainOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainOtherJson);

	}
	private boolean verification(Object obj1,Object obj2){
		//true为不相等
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
	 * @Description 查询数据 资产入库主表(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/queryAssInMainOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String assInMainOther = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assInMainOther = assInMainOtherService.query(getPage(mapVo));
		
		}else{
			
			assInMainOther = assInMainOtherService.queryDetails(getPage(mapVo));
	
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
	@RequestMapping(value = "/hrp/ass/assother/assin/deleteAssInDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			
			if(assInMainOther.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assInDetailOtherJson = assInDetailOtherService.deleteBatch(listVo);
		return JSONObject.parseObject(assInDetailOtherJson);
	}

	/**
	 * @Description 查询数据 资产入库明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/queryAssInDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailOther = assInDetailOtherService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailOther);
	}

	
	
	@RequestMapping(value = "/hrp/ass/assother/assin/queryAssInCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
	/**
	 * 生成卡片号
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/initAssInCardOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInCardOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			
			if(assInMainOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInMainOtherService.initAssInCardOther(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assother/assin/deleteAssInCardOther", method = RequestMethod.POST)
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
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			
			if(assInMainOther.getState() > 0){
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
	 * 其他固定资产  采购入库  入库单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/queryAssInOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//其他固定资产 采购入库  入库单状态查询  （打印时校验数据专用）
		List<String> list = assInMainOtherService.queryAssInOtherState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assother/assin/updateAssInMainOtherBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInMainOtherBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assInMainOtherService.updateAssInMainBillNo(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * 冲账(cjc)
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assin/offsetInOther", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> offsetInOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
			
			assJson = assInMainOtherService.offsetInOther(listVo);
			
		} catch (Exception e) {
			assJson = e.getMessage();
		}
		return JSONObject.parseObject(assJson);
		
	}
	
	@RequestMapping(value = "/hrp/ass/assother/assin/updateIsPrint", method = RequestMethod.POST)
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
		
		String assCard = assInMainOtherService.updateIsPrint(listVo);
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assother/assin/assInGenerateBill", method = RequestMethod.POST)
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
		
		String assCard = assInMainOtherService.assInGenerateBill(listVo);
		
		return JSONObject.parseObject(assCard);
	}
}

