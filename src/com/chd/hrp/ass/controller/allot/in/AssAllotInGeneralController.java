/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.allot.in;
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
import com.chd.hrp.ass.entity.allot.in.AssAllotInGeneral;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutGeneral;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.service.allot.in.AssAllotInDetailGeneralService;
import com.chd.hrp.ass.service.allot.in.AssAllotInGeneralService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailGeneralService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutGeneralService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表（一般设备）
 * @Table:
 * ASS_ALLOT_IN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssAllotInGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssAllotInGeneralController.class);

	// 引入Service服务
	@Resource(name = "assAllotInGeneralService")
	private final AssAllotInGeneralService assAllotInGeneralService = null;

	@Resource(name = "assAllotInDetailGeneralService")
	private final AssAllotInDetailGeneralService assAllotInDetailGeneralService = null;
	
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;
	
	@Resource(name = "assAllotOutGeneralService")
	private final AssAllotOutGeneralService assAllotOutGeneralService = null;
	
	@Resource(name = "assAllotOutDetailGeneralService")
	private final AssAllotOutDetailGeneralService assAllotOutDetailGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/assAllotInGeneralMainPage", method = RequestMethod.GET)
	public String assAllotInGeneralMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05028",MyConfig.getSysPara("05028"));
		return "hrp/ass/assgeneral/assallotin/main";
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/assImportAllotOutGeneralPage", method = RequestMethod.GET)
	public String assImportAllotOutGeneralPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("out_store_code", mapVo.get("out_store_code"));
		mode.addAttribute("out_store_name", mapVo.get("out_store_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("in_store_id", mapVo.get("in_store_id"));
		mode.addAttribute("in_store_no", mapVo.get("in_store_no"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assallotin/importoutmain";
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/assViewAllotOutGeneralPage", method = RequestMethod.GET)
	public String assViewAllotOutGeneralPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
		mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("allot_in_no", mapVo.get("allot_in_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assallotin/viewoutmain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/assAllotInGeneralAddPage", method = RequestMethod.GET)
	public String assAllotInGeneralAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assallotin/add";
	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/saveAssAllotInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssAllotInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
		if(assAllotInGeneral != null){
			if(assAllotInGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assAllotInGeneralJson = assAllotInGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotInGeneralJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/assAllotInGeneralUpdatePage", method = RequestMethod.GET)
	public String assAllotInGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssAllotInGeneral assAllotInGeneral = new AssAllotInGeneral();

		assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
		mode.addAttribute("is_import", mapVo.get("is_import"));
		mode.addAttribute("group_id", assAllotInGeneral.getGroup_id());
		mode.addAttribute("hos_id", assAllotInGeneral.getHos_id());
		mode.addAttribute("copy_code", assAllotInGeneral.getCopy_code());
		mode.addAttribute("allot_in_no", assAllotInGeneral.getAllot_in_no());
		mode.addAttribute("in_store_id", assAllotInGeneral.getIn_store_id());
		mode.addAttribute("in_store_no", assAllotInGeneral.getIn_store_no());
		mode.addAttribute("in_store_code", assAllotInGeneral.getIn_store_code());
		mode.addAttribute("in_store_name", assAllotInGeneral.getIn_store_name());
		mode.addAttribute("out_group_id", assAllotInGeneral.getOut_group_id());
		mode.addAttribute("out_group_name", assAllotInGeneral.getOut_group_name());
		mode.addAttribute("out_hos_id", assAllotInGeneral.getOut_hos_id());
		mode.addAttribute("out_hos_name", assAllotInGeneral.getOut_hos_name());
		mode.addAttribute("out_store_id", assAllotInGeneral.getOut_store_id());
		mode.addAttribute("out_store_no", assAllotInGeneral.getOut_store_no());
		mode.addAttribute("out_store_code", assAllotInGeneral.getOut_store_code());
		mode.addAttribute("out_store_name", assAllotInGeneral.getOut_store_name());
		mode.addAttribute("price", assAllotInGeneral.getPrice());
		mode.addAttribute("add_depre", assAllotInGeneral.getAdd_depre());
		mode.addAttribute("cur_money", assAllotInGeneral.getCur_money());
		mode.addAttribute("fore_money", assAllotInGeneral.getFore_money());
		mode.addAttribute("create_emp", assAllotInGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotInGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotInGeneral.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotInGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotInGeneral.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assAllotInGeneral.getAudit_date(),"yyyy-MM-dd"));
		mode.addAttribute("state", assAllotInGeneral.getState());
		mode.addAttribute("state_name", assAllotInGeneral.getState_name());
		mode.addAttribute("note", assAllotInGeneral.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05028",MyConfig.getSysPara("05028"));
		
		return "hrp/ass/assgeneral/assallotin/update";
	}
	
	
	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/updateConfirmAllotInMainGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotInMainGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("allot_in_no", ids[3]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			if (assAllotInGeneral.getState() == 2) {
				continue;
			}
			if(DateUtil.compareDate(assAllotInGeneral.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<Map<String, Object>> list = assAllotInDetailGeneralService.queryByInit(mapVo);
			if(list.size() == 0){
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for(Map<String, Object> temp : list){
				
				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));
				
				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));
				
				mapExists.put("ass_in_no", temp.get("allot_in_no"));
				
				mapExists.put("ass_id", temp.get("ass_id"));
				
				mapExists.put("ass_no", temp.get("ass_no"));
				
				Integer use_state = assCardGeneralService.queryCardExistsByAssInNo(mapExists);
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardGeneralService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true,
						oriCardNo = true,
						specFlag = true,
						modelFlag = true,
						brandFlag = true,
						facFlag = true,
						priceFlag = true,
						depreFlag = true,      
						depreMonthFlag = true,
						curMoneyFlag = true,      
						foreMoneyFlag = true,  
						inStoreFlag = true;
						
				for(Map<String, Object> card : cardList){
					if(verification(temp.get("ass_id"),card.get("ass_id"))){
						assFlag = false;
						break;
					}
					if(verification(temp.get("ass_ori_card_no"),card.get("ass_ori_card_no"))){
						oriCardNo = false;
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
					if(verification(temp.get("price"),card.get("price"))){
						priceFlag = false;
						break;
					}
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
					if(verification(temp.get("in_store_id"),card.get("store_id"))){
						inStoreFlag = false;
						break;
					}
				}
				if(!assFlag){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if(!oriCardNo){
					return JSONObject.parseObject("{\"warn\":\"存在原始卡片号不匹配的资产,不能入库! \"}");
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
				if(!inStoreFlag){
					return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
				}
			}
			
		}
		
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("allot_in_no", ids[3]);
			
			mapVo.put("ass_in_no", ids[3]);
			
			mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());
			
			mapVo.put("audit_emp", SessionManager.getUserId());
			

			AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			
			if (assAllotInGeneral.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assInMainJson = assAllotInGeneralService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	private boolean verification(Object obj1,Object obj2){
		//true为不相等
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
			Double number1 = obj1 == null || obj1.equals("") ? 0 : Double.parseDouble(String.valueOf(obj1));
			Double number2 = obj2 == null || obj2.equals("") ? 0 : Double.parseDouble(String.valueOf(obj2));
			if(Math.abs(number1 - number2) > 0){
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
	 * @Description 删除数据 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/deleteAssAllotInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotInGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("allot_in_no", ids[3]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssAllotInGeneral assInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			
			if(assInGeneral.getState() > 0){
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assAllotInGeneralJson = assAllotInGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotInGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库单主表（一般设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryAssAllotInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assAllotInGeneral = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
		
			assAllotInGeneral = assAllotInGeneralService.query(getPage(mapVo));
		
		}else{
			
			assAllotInGeneral = assAllotInGeneralService.queryDetails(getPage(mapVo));
			
		}

		return JSONObject.parseObject(assAllotInGeneral);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/initAssAllotInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAllotInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assAllotInGeneralService.initAssAllotInGeneral(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);

	}
	
	
	//查询出库单引入使用
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryByAllotInImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByAllotInImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assAllotInGeneral = assAllotOutGeneralService.queryByAllotInImport(getPage(mapVo));

		return JSONObject.parseObject(assAllotInGeneral);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryByAllotInImportView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByAllotInImportView(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assAllotInGeneral = assAllotOutGeneralService.queryByAllotInImportView(getPage(mapVo));

		return JSONObject.parseObject(assAllotInGeneral);
	}
	

	/**
	 * @Description 删除数据 050901 资产无偿调拨入库明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/deleteAssAllotInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotInDetailGeneral(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_in_no", ids[3]);
			mapVo.put("ass_id", ids[4]);
			mapVo.put("ass_no", ids[5]);
			mapVo.put("ass_ori_card_no", ids[6]);
			mapVo.put("ass_in_no", ids[3]);
			AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			
			if(assAllotInGeneral.getState() > 0){
				flag = false;
				break;
			}

			listVo.add(mapVo);

		}

		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assAllotInDetailGeneralJson = assAllotInDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotInDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨入库明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryAssAllotInDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotInDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotInDetailGeneral = assAllotInDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotInDetailGeneral);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryAssAllotInCardGeneral", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/initAssAllotInCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAllotInCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no",mapVo.get("allot_in_no").toString());
		
		String assCard = "";
		try {
			AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			
			if(assAllotInGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assAllotInGeneralService.initAssAllotInCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/initAssAllotInBatchCardGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAllotInBatchCardGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no",mapVo.get("allot_in_no").toString());
		
		String assCard = "";
		try {
			AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			
			if(assAllotInGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assAllotInGeneralService.initAssAllotInBatchCardGeneral(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/deleteAssAllotInCardGeneral", method = RequestMethod.POST)
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
			mapVo.put("allot_in_no", ids[4]);
			mapVo.put("ass_in_no", ids[4]);
			AssAllotInGeneral assAllotInGeneral = assAllotInGeneralService.queryByCode(mapVo);
			
			if(assAllotInGeneral.getState() > 0){
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
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/assAllotOutGeneralViewPage", method = RequestMethod.GET)
	public String assAllotOutGeneralViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutGeneral assAllotOutGeneral = new AssAllotOutGeneral();

		assAllotOutGeneral = assAllotOutGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutGeneral.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutGeneral.getHos_id());
		mode.addAttribute("copy_code", assAllotOutGeneral.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutGeneral.getAllot_out_no());
		mode.addAttribute("out_store_id", assAllotOutGeneral.getOut_store_id());
		mode.addAttribute("out_store_no", assAllotOutGeneral.getOut_store_no());
		mode.addAttribute("out_store_code", assAllotOutGeneral.getOut_store_code());
		mode.addAttribute("out_store_name", assAllotOutGeneral.getOut_store_name());
		mode.addAttribute("in_group_id", assAllotOutGeneral.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutGeneral.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutGeneral.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutGeneral.getIn_hos_name());
		mode.addAttribute("in_store_id", assAllotOutGeneral.getIn_store_id());
		mode.addAttribute("in_store_no", assAllotOutGeneral.getIn_store_no());
		mode.addAttribute("in_store_code", assAllotOutGeneral.getIn_store_code());
		mode.addAttribute("in_store_name", assAllotOutGeneral.getIn_store_name());
		mode.addAttribute("price", assAllotOutGeneral.getPrice());
		mode.addAttribute("add_depre", assAllotOutGeneral.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutGeneral.getCur_money());
		mode.addAttribute("fore_money", assAllotOutGeneral.getFore_money());
		mode.addAttribute("create_emp", assAllotOutGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutGeneral.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutGeneral.getAudit_date());
		mode.addAttribute("state", assAllotOutGeneral.getState());
		mode.addAttribute("state_name", assAllotOutGeneral.getState_name());
		mode.addAttribute("note", assAllotOutGeneral.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assallotin/viewout";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryAllotOutDetByAllotInGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllotOutDetByAllotInGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailGeneral = assAllotOutDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailGeneral);
	}
	
	/**
	 * 一般设备  资产调剂入库  入库单状态查询（打印校验数据用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assallotin/queryAssAllotInGeneralState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotInGeneralState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assAllotInGeneralService.queryAssAllotInGeneralState(mapVo);
		
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

