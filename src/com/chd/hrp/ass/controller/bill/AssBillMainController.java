/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.bill;

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
import com.chd.hrp.ass.entity.bill.AssBillMain;
import com.chd.hrp.ass.entity.bill.AssBillStage;
import com.chd.hrp.ass.entity.pay.main.AssPayDetail;
import com.chd.hrp.ass.service.bill.AssBillDetailService;
import com.chd.hrp.ass.service.bill.AssBillMainService;
import com.chd.hrp.ass.service.bill.AssBillStageService;
import com.chd.hrp.ass.service.pay.main.AssPayDetailService;

/**
 * 
 * @Description: 发票主表
 * @Table: ASS_BILL_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBillMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBillMainController.class);

	// 引入Service服务
	@Resource(name = "assBillMainService")
	private final AssBillMainService assBillMainService = null;

	@Resource(name = "assBillDetailService")
	private final AssBillDetailService assBillDetailService = null;

	@Resource(name = "assBillStageService")
	private final AssBillStageService assBillStageService = null;
	
	@Resource(name = "assPayDetailService")
	private final AssPayDetailService assPayDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/assBillMainMainPage", method = RequestMethod.GET)
	public String assBillMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05086",MyConfig.getSysPara("05086"));
		
		return "hrp/ass/assbill/main/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/assBillMainAddPage", method = RequestMethod.GET)
	public String assBillMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbill/main/add";

	}
	
	/**
	 * @Description 添加生成发票单页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/assBillMainInvoicePage", method = RequestMethod.GET)
	public String assBillMainInvoicePage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assbill/main/invoice";

	}

	/**
	 * @Description 生成发票单查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/queryInMainBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInMainBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String inMainBill = assBillMainService.queryInMainBill(getPage(mapVo));
		
		return JSONObject.parseObject(inMainBill) ;

	}
	
	
	
	
	
	@RequestMapping(value = "/hrp/ass/assbill/main/assBillMainImportPage", method = RequestMethod.GET)
	public String assBillMainImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("bill_date", mapVo.get("bill_date"));
		mode.addAttribute("invoice_no", mapVo.get("invoice_no"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assbill/main/import";
	}

	@RequestMapping(value = "/hrp/ass/assbill/main/queryAssInAndAssChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInAndAssChange(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackBillMain = assBillMainService.queryAssInAndAssChange(getPage(mapVo));

		return JSONObject.parseObject(assBackBillMain);
	}

	// 引入分期付款
	@RequestMapping(value = "/hrp/ass/assbill/main/importInAndChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importInAndChange(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());

		String assAllotInSpecial = assBillMainService.importInAndChange(mapVo);

		return JSONObject.parseObject(assAllotInSpecial);

	}

	/**
	 * @Description 添加数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/saveAssBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());

		String yearmonth = mapVo.get("create_date").toString().substring(0, 4)
				+ mapVo.get("create_date").toString().substring(5, 7);
		// 启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();

		String startyearmonth = (String) start.get(SessionManager.getModCode());

		int result = startyearmonth.compareTo(yearmonth);


		if (result > 0) {

			return JSONObject.parseObject("{\"warn\":\"" + yearmonth + "月份在系统启动时间" + startyearmonth + "之前,不允许添加单据 \"}");

		} 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);

		if (assBillMain != null) {
			if (assBillMain.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assBillMainJson = assBillMainService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBillMainJson);

	}

	/**
	 * @Description 登记确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/updateConfirmAssBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssBill(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<AssBillStage> stagelist = assBillStageService.queryByConfirmAssBill(entityMap);
		Map<String, AssBillStage> keyMap = new HashMap<String, AssBillStage>();
		for(AssBillStage assBillStage : stagelist){
			if(keyMap.containsKey(assBillStage.getAss_card_no()+assBillStage.getStage_no())){
				return JSONObject.parseObject("{\"warn\":\"单据【"+assBillStage.getBill_no()+"】和【"+keyMap.get(assBillStage.getAss_card_no()+assBillStage.getStage_no()).getBill_no()+"】.存在相同卡片,不能登记确认! \"}");
			}
			keyMap.put(assBillStage.getAss_card_no()+assBillStage.getStage_no(), assBillStage);
			
		}

		String assBillMainJson = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("bill_no", ids[3]);

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_emp", SessionManager.getUserId());
			AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);
			if (assBillMain.getState() == 2) {
				continue;
			}

			/**
			 * 验证主表数据
			 */
			if (DateUtil.compareDate(assBillMain.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能登记确认! \"}");
			}

			if (assBillMain.getBill_money() <= 0) {
				return JSONObject.parseObject("{\"warn\":\"发票金额不能小于0,不能登记确认! \"}");
			}

			/**
			 * 验证明细数据
			 */
			List<Map<String, Object>> list = assBillDetailService.queryByAssBillNo(mapVo);

			if (list != null && list.size() > 0) {

				for (Map<String, Object> assBillDetail : list) {
					if (Double.parseDouble(assBillDetail.get("bill_money").toString()) <= 0) {
						return JSONObject.parseObject("{\"warn\":\"明细发票金额不能小于0,不能登记确认! \"}");
					}
				}
			} else {
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能登记确认! \"}");
			}

			/**
			 * 验证分期付款数据
			 */
			List<Map<String, Object>> stageList = assBillStageService.queryByAssBillNo(mapVo);
			if (stageList != null && stageList.size() > 0) {
				for (Map<String, Object> assStageDetail : stageList) {
					if (Double.parseDouble(assStageDetail.get("pay_plan_money").toString()) <= 0) {
						return JSONObject.parseObject("{\"warn\":\"付款金额不能小于0,不能登记确认! \"}");
					}
				}
			} else {
				return JSONObject.parseObject("{\"warn\":\"没有付款信息不能登记确认! \"}");
			}
			listVo.add(mapVo);
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复登记确认! \"}");
		}

		try {
			assBillMainJson = assBillMainService.updateBillConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBillMainJson);
	}

	// updateNotToExamineAssBill updateReAudit
	@RequestMapping(value = "/hrp/ass/assbill/main/updateNotToExamineAssBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReAudit(@RequestParam(value = "ParamVo") String paramVo, Model mode) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("bill_no", ids[3]);
			/*
			 * mapVo.put("state", 0); mapVo.put("audit_emp","");
			 * mapVo.put("audit_date", "");
			 */
			//判断是不是被付款单引用,如果引用不能取消确定状态
			 List<AssPayDetail> list = (List<AssPayDetail>) assPayDetailService.queryExists(mapVo);
			 if(list.size() > 0){
				 return JSONObject.parseObject("{\"warn\":\"被付款单引用,不能取消操作! \"}");
			 }
			// 判断是否确认如果确认则忽略
			AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);
			int state = assBillMain.getState() == null ? 0 : assBillMain.getState();
			if (assBillMain.getState() != 2) {
				continue;
			}

			listVo.add(mapVo);

		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复销审操作! \"}");
		}
		String assBillMainJson = assBillMainService.updateReAudit(listVo);
		return JSONObject.parseObject(assBillMainJson);

	}

	/**
	 * @Description 更新跳转页面 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/assBillMainUpdatePage", method = RequestMethod.GET)
	public String assBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBillMain assBillMain = new AssBillMain();

		assBillMain = assBillMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBillMain.getGroup_id());
		mode.addAttribute("hos_id", assBillMain.getHos_id());
		mode.addAttribute("copy_code", assBillMain.getCopy_code());
		mode.addAttribute("bill_no", assBillMain.getBill_no());
		mode.addAttribute("invoice_no", assBillMain.getInvoice_no());
		mode.addAttribute("bill_date", DateUtil.dateToString(assBillMain.getBill_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assBillMain.getVen_id());
		mode.addAttribute("ven_no", assBillMain.getVen_no());
		mode.addAttribute("ven_code", assBillMain.getVen_code());
		mode.addAttribute("ven_name", assBillMain.getVen_name());
		mode.addAttribute("store_id", assBillMain.getStore_id());
		mode.addAttribute("store_no", assBillMain.getStore_no());
		mode.addAttribute("store_code", assBillMain.getStore_code());
		mode.addAttribute("store_name", assBillMain.getStore_name());
		mode.addAttribute("pact_code", assBillMain.getPact_code());
		mode.addAttribute("bill_money", assBillMain.getBill_money());
		mode.addAttribute("state", assBillMain.getState());
		mode.addAttribute("create_emp", assBillMain.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assBillMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assBillMain.getAudit_emp());
		mode.addAttribute("audit_date", assBillMain.getAudit_date());
		mode.addAttribute("note", assBillMain.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05086",MyConfig.getSysPara("05086"));
		
		return "hrp/ass/assbill/main/update";
	}

	/**
	 * @Description 删除数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/deleteAssBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBillMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("bill_no", ids[3]);

			AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);

			if (assBillMain != null) {
				if (assBillMain.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBillMainJson = assBillMainService.deleteBatch(listVo);

		return JSONObject.parseObject(assBillMainJson);

	}

	/**
	 * @Description 查询数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/queryAssBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBillMain = assBillMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assBillMain);

	}

	/**
	 * @Description 删除数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/deleteAssBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBillDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("bill_no", ids[3]);
			mapVo.put("naturs_code", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);

			if (assBillMain != null) {
				if (assBillMain.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBillDetailJson = assBillDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(assBillDetailJson);

	}

	/**
	 * @Description 查询数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/queryAssBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBillDetail = assBillDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assBillDetail);
	}

	@RequestMapping(value = "/hrp/ass/assbill/main/saveAssBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBillStage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);

		if (assBillMain != null) {
			if (assBillMain.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assBillMainJson = assBillStageService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBillMainJson);

	}

	/**
	 * @Description 删除数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/deleteAssBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBillStage(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("bill_no", ids[3]);
			mapVo.put("naturs_code", ids[4]);
			mapVo.put("ass_card_no", ids[5]);
			mapVo.put("stage_no", ids[6]);

			AssBillMain assBillMain = assBillMainService.queryByCode(mapVo);

			if (assBillMain != null) {
				if (assBillMain.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assBillDetailJson = assBillStageService.deleteBatch(listVo);

		return JSONObject.parseObject(assBillDetailJson);

	}

	/**
	 * @Description 查询数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/queryAssBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillStage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBillDetail = assBillStageService.query(getPage(mapVo));

		return JSONObject.parseObject(assBillDetail);

	}

	/**
	 * 付款发票登记状态查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/queryAssBillState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 入库单状态查询 （打印时校验数据专用）
		List<String> list = assBillMainService.queryAssBillState(mapVo);

		if (list.size() == 0) {

			return JSONObject.parseObject("{\"state\":\"true\"}");

		} else {

			String str = "";
			for (String item : list) {

				str += item + ",";
			}

			return JSONObject.parseObject("{\"error\":\"付款发票登记单【" + str.substring(0, str.length() - 1)
					+ "】不是确认状态不能打印.\",\"state\":\"false\"}");
		}

	}
	
	/**
	 * 生成发票单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/main/importInAndMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importInAndMap(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());
		
		mapVo.put("bill_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
		
		mapVo.put("create_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

		String assAllotInSpecial = assBillMainService.importInAndMap(mapVo);

		return JSONObject.parseObject(assAllotInSpecial);

	}
	

}
