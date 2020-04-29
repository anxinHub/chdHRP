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
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.in.AssInMainInassets;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.in.AssInDetailInassetsService;
import com.chd.hrp.ass.service.in.AssInMainInassetsService;

/**
 * 
 * @Description: 资产入库主表(无形资产)
 * @Table: ASS_IN_MAIN_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssInMainInassetsController extends BaseController {    

	private static Logger logger = Logger.getLogger(AssInMainInassetsController.class);

	// 引入Service服务
	@Resource(name = "assInMainInassetsService")
	private final AssInMainInassetsService assInMainInassetsService = null;

	@Resource(name = "assInDetailInassetsService")
	private final AssInDetailInassetsService assInDetailInassetsService = null;

	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/assInMainInassetsMainPage", method = RequestMethod.GET)
	public String assInMainInassetsMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05044",MyConfig.getSysPara("05044"));
		
		return "hrp/ass/assinassets/assin/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/assInMainInassetsAddPage", method = RequestMethod.GET)
	public String assInMainInassetsAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05044",MyConfig.getSysPara("05044"));
		
		return "hrp/ass/assinassets/assin/add";
	}

	@RequestMapping(value = "/hrp/ass/assinassets/assin/assInMainInassetsSourcePage", method = RequestMethod.GET)
	public String assInMainInassetsSourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("ass_in_no", mapVo.get("ass_in_no"));
		
		AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);
		
		mode.addAttribute("price", mapVo.get("price") != null && !"".equals(mapVo.get("price"))?mapVo.get("price"):assInMainInassets.getIn_money());
		mode.addAttribute("proj_id", assInMainInassets.getProj_id());
		
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assin/source";
	}
	
	/**
	 * @Description 添加数据 资产入库主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/saveAssInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssInMainInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);
		if (assInMainInassets != null) {
			if (assInMainInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
		}

		String assInMainInassetsJson = assInMainInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assInMainInassetsJson);
	}

	/**
	 * @Description 更新跳转页面 资产入库主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/assInMainInassetsUpdatePage", method = RequestMethod.GET)
	public String assInMainInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainInassets assInMainInassets = new AssInMainInassets();

		assInMainInassets = assInMainInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainInassets.getGroup_id());
		mode.addAttribute("hos_id", assInMainInassets.getHos_id());
		mode.addAttribute("copy_code", assInMainInassets.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainInassets.getAss_in_no());
		mode.addAttribute("store_id", assInMainInassets.getStore_id());
		mode.addAttribute("store_no", assInMainInassets.getStore_no());
		mode.addAttribute("ven_id", assInMainInassets.getVen_id());
		mode.addAttribute("ven_no", assInMainInassets.getVen_no());
		mode.addAttribute("purc_emp", assInMainInassets.getPurc_emp());
		mode.addAttribute("dept_id", assInMainInassets.getDept_id());
		mode.addAttribute("dept_no", assInMainInassets.getDept_no());
		mode.addAttribute("ass_purpose", assInMainInassets.getAss_purpose());
		mode.addAttribute("proj_id", assInMainInassets.getProj_id());
		mode.addAttribute("proj_no", assInMainInassets.getProj_no());
		mode.addAttribute("store_code", assInMainInassets.getStore_code());
		mode.addAttribute("store_name", assInMainInassets.getStore_name());
		mode.addAttribute("ven_code", assInMainInassets.getVen_code());
		mode.addAttribute("ven_name", assInMainInassets.getVen_name());
		mode.addAttribute("purc_emp_name", assInMainInassets.getPurc_emp_name());
		mode.addAttribute("dept_code", assInMainInassets.getDept_code());
		mode.addAttribute("dept_name", assInMainInassets.getDept_name());
		mode.addAttribute("ass_purpose_name", assInMainInassets.getAss_purpose_name());
		mode.addAttribute("proj_code", assInMainInassets.getProj_code());
		mode.addAttribute("proj_name", assInMainInassets.getProj_name());
		mode.addAttribute("in_money", assInMainInassets.getIn_money());
		mode.addAttribute("note", assInMainInassets.getNote());
		mode.addAttribute("create_emp", assInMainInassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assInMainInassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assInMainInassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("in_date", assInMainInassets.getIn_date());
		mode.addAttribute("confirm_emp", assInMainInassets.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assInMainInassets.getConfirm_emp_name());
		mode.addAttribute("state", assInMainInassets.getState());
		mode.addAttribute("state_name", assInMainInassets.getState_name());
		mode.addAttribute("invoice_no", assInMainInassets.getInvoice_no());
		mode.addAttribute("invoice_date", DateUtil.dateToString(assInMainInassets.getInvoice_date(),"yyyy-MM-dd"));
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05044",MyConfig.getSysPara("05044"));
		
		return "hrp/ass/assinassets/assin/update";
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/updateToExamineInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamineInMainInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssApplyDept assApplyDept = assInMainInassetsService.queryByCode(mapVo);

			if (assApplyDept.getState() == 1) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复审核! \"}");
		}

		try {
			assInMainJson = assInMainInassetsService.updateAudit(listVo);
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
	@RequestMapping(value = "/hrp/ass/assinassets/assin/updateNotToExamineInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineInMainInassets(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);

			AssApplyDept assApplyDept = assInMainInassetsService.queryByCode(mapVo);

			if (assApplyDept.getState() == 0) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复销审! \"}");
		}

		try {
			assInMainJson = assInMainInassetsService.updateReAudit(listVo);
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
	@RequestMapping(value = "/hrp/ass/assinassets/assin/updateConfirmInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			
			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);
			if (DateUtil.compareDate(assInMainInassets.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<Map<String, Object>> list = assInDetailInassetsService.queryByInit(mapVo);// 资产明细数据

			if (list.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for (Map<String, Object> temp : list) {

				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));

				mapExists.put("ass_id", temp.get("ass_id"));

				mapExists.put("ass_no", temp.get("ass_no"));

				Integer use_state = assCardInassetsService.queryCardExistsByAssInNo(mapExists);// 通过资产

				if (use_state == 0) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}

				if (use_state != Integer.parseInt(temp.get("in_amount").toString())) {
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}

				List<Map<String, Object>> cardList = assCardInassetsService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true, specFlag = true, modelFlag = true, brandFlag = true, facFlag = true,
						unitFlag = true, priceFlag = true, storeFlag = true, venFlag = true, //deptFlag = true,
						purposeFlag = true, projFlag = true;

				for (Map<String, Object> card : cardList) {
					if (verification(temp.get("ass_id"), card.get("ass_id"))) {
						assFlag = false;
						break;
					}
					if (verification(temp.get("ass_spec"), card.get("ass_spec"))) {
						specFlag = false;
						break;
					}
					if (verification(temp.get("ass_model"), card.get("ass_mondl"))) {
						modelFlag = false;
						break;
					}
					if (verification(temp.get("ass_brand"), card.get("ass_brand"))) {
						brandFlag = false;
						break;
					}
					if (verification(temp.get("fac_id"), card.get("fac_id"))) {
						facFlag = false;
						break;
					}
					if (verification(temp.get("unit_code"), card.get("unit_code"))) {
						unitFlag = false;
						break;
					}
					//if (verification(temp.get("price"), card.get("price"))) {
						//priceFlag = false;
						//break;
					//}
					if (verification(temp.get("store_id"), card.get("store_id"))) {
						storeFlag = false;
						break;
					}
					if (verification(temp.get("ven_id"), card.get("ven_id"))) {
						venFlag = false;
						break;
					}
					//if (verification(temp.get("dept_id"), card.get("dept_id"))) {
						//deptFlag = false;
						//break;
					//}
					if (verification(temp.get("ass_purpose"), card.get("ass_purpose"))) {
						purposeFlag = false;
						break;
					}
					if (verification(temp.get("proj_id"), card.get("proj_id"))) {
						projFlag = false;
						break;
					}
				}
				if (!assFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if (!specFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在规格不匹配的资产,不能入库! \"}");
				}
				if (!modelFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在型号不匹配的资产,不能入库! \"}");
				}
				if (!brandFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在品牌不匹配的资产,不能入库! \"}");
				}
				if (!facFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在生产厂商不匹配的资产,不能入库! \"}");
				}
				if (!unitFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在计量单位不匹配的资产,不能入库! \"}");
				}
				if (!priceFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
				if (!storeFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
				}
				if (!venFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在供应商不匹配的资产,不能入库! \"}");
				}
				//if (!deptFlag) {
					//return JSONObject.parseObject("{\"warn\":\"存在领用科室不匹配的资产,不能入库! \"}");
				//}
				if (!purposeFlag) {
					return JSONObject.parseObject("{\"warn\":\"存在用途不匹配的资产,不能入库! \"}");
				}
				if (!projFlag) {
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

			mapVo.put("in_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());

			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);// 主表
			if (assInMainInassets.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}

		try {
			assInMainJson = assInMainInassetsService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	private boolean verification(Object obj1, Object obj2) {
		// true为不相等
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
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/deleteAssInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInMainInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);

			if (assInMainInassets.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainInassetsJson = assInMainInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainInassetsJson);

	}

	/**
	 * @Description 查询数据 资产入库主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/queryAssInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String assInMainInassets = null;

		if("0".equals(mapVo.get("show_detail").toString())){
		
			assInMainInassets = assInMainInassetsService.query(getPage(mapVo));
		
		}else{
			
			assInMainInassets = assInMainInassetsService.queryDetails(getPage(mapVo));
			
		}
		return JSONObject.parseObject(assInMainInassets);
	}

	/**
	 * @Description 删除数据 明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/deleteAssInDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);

			if (assInMainInassets.getState() > 0) {
				flag = false;
				break;
			}

			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}

		String assInDetailInassetsJson = assInDetailInassetsService.deleteBatch(listVo);
		return JSONObject.parseObject(assInDetailInassetsJson);
	}

	/**
	 * @Description 查询数据 资产入库明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/queryAssInDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailInassets = assInDetailInassetsService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailInassets);
	}

	@RequestMapping(value = "/hrp/ass/assinassets/assin/queryAssInCardInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardInassetsService.query(getPage(mapVo));
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

	@RequestMapping(value = "/hrp/ass/assinassets/assin/initAssInCardInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);

			if (assInMainInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}

			assCard = assInMainInassetsService.initAssInCardInassets(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assinassets/assin/initAssInBatchCardInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInBatchCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);
			
			if(assInMainInassets.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			
			assCard = assInMainInassetsService.initAssInBatchCardInassets(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assinassets/assin/deleteAssInCardInassets", method = RequestMethod.POST)
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
			AssInMainInassets assInMainInassets = assInMainInassetsService.queryByCode(mapVo);

			if (assInMainInassets.getState() > 0) {
				flag = false;
				break;
			}

			AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
			if (assCardInassets.getUse_state() > 0) {
				flag1 = false;
			}

			listVo.add(mapVo);

		}

		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		if (!flag1) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		String assCardGeneralJson = "";
		try {
			assCardGeneralJson = assCardInassetsService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	/**
	 * 其他无形资产  采购入库  入库单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assin/queryAssInassetsState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//其他无形资产   采购入库  入库单状态查询  （打印时校验数据专用）
		List<String> list = assInMainInassetsService.queryAssInassetsState(mapVo);
		
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
	@RequestMapping(value = "/hrp/ass/assinassets/assin/updateAssInMainInassetsBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInMainInassetsBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assInMainInassetsService.updateAssInMainBillNo(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	@RequestMapping(value = "/hrp/ass/assinassets/assin/assInGenerateBill", method = RequestMethod.POST)
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
		
		String assCard = assInMainInassetsService.assInGenerateBill(listVo);
		
		return JSONObject.parseObject(assCard);
	}
}
