/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.pay.back;

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
import com.chd.hrp.ass.entity.bill.back.AssBackBillMain;
import com.chd.hrp.ass.entity.pay.back.AssBackPayMain;
import com.chd.hrp.ass.service.bill.back.AssBackBillDetailService;
import com.chd.hrp.ass.service.bill.back.AssBackBillMainService;
import com.chd.hrp.ass.service.bill.back.AssBackBillStageService;
import com.chd.hrp.ass.service.pay.back.AssBackPayDetailService;
import com.chd.hrp.ass.service.pay.back.AssBackPayMainService;
import com.chd.hrp.ass.service.pay.back.AssBackPayStageService;

/**
 * 
 * @Description: 发票主表
 * @Table: ASS_PAY_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBackPayMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackPayMainController.class);

	// 引入Service服务
	@Resource(name = "assBackPayMainService")
	private final AssBackPayMainService assBackPayMainService = null;

	@Resource(name = "assBackPayDetailService")
	private final AssBackPayDetailService assBackPayDetailService = null;
	
	@Resource(name = "assBackPayStageService")
	private final AssBackPayStageService assBackPayStageService = null;
	
	@Resource(name = "assBackBillMainService")
	private final AssBackBillMainService assBackBillMainService = null;
	
	@Resource(name = "assBackBillDetailService")
	private final AssBackBillDetailService assBackBillDetailService = null;
	
	@Resource(name = "assBackBillStageService")
	private final AssBackBillStageService assBackBillStageService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/assBackPayMainMainPage", method = RequestMethod.GET)
	public String assBackPayMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05088",MyConfig.getSysPara("05088"));
		
		return "hrp/ass/asspay/back/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/assBackPayMainAddPage", method = RequestMethod.GET)
	public String assBackPayMainAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asspay/back/add";

	}
	
	/**
	 * 引入跳转页面
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/assImportBackPayPage", method = RequestMethod.GET)
	public String assImportPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("ven_id", mapVo.get("ven_id").toString().split("@")[0]);
		mode.addAttribute("ven_no", mapVo.get("ven_id").toString().split("@")[1]);
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		mode.addAttribute("store_id", mapVo.get("store_id").toString().split("@")[0]);
		mode.addAttribute("store_no", mapVo.get("store_id").toString().split("@")[1]);
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		mode.addAttribute("pay_date",mapVo.get("pay_date"));
		mode.addAttribute("create_date",mapVo.get("create_date"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asspay/back/importpaymain";
	}
	
	/**
	 * @Description  点击引入查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//--------------------------------------------------
		
		String[] list = assBackPayDetailService.queryBillNo(mapVo);
		
		if(list.equals("") && list.length > 0){
			mapVo.put("bill_nos", list);
		} else {
			mapVo.put("bill_nos", "");
		}
		
		String listJson = assBackBillMainService.queryimports(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	/**
	 * 引入之后点击详情
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/assBackBillMainUpdatePage", method = RequestMethod.GET)
	public String assBackBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssBackBillMain assBillMain = new AssBackBillMain();

		assBillMain = assBackBillMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBillMain.getGroup_id());
		mode.addAttribute("hos_id", assBillMain.getHos_id());
		mode.addAttribute("copy_code", assBillMain.getCopy_code());
		mode.addAttribute("ven_no", assBillMain.getVen_no());
		mode.addAttribute("ven_name", assBillMain.getVen_name());
		mode.addAttribute("bill_money", assBillMain.getBill_money());
		mode.addAttribute("bill_no", assBillMain.getBill_no());
		mode.addAttribute("invoice_no", assBillMain.getInvoice_no());
		mode.addAttribute("pact_code", assBillMain.getPact_code());
		mode.addAttribute("ven_id", assBillMain.getVen_id());
		mode.addAttribute("ven_code", assBillMain.getVen_code());
		mode.addAttribute("state_name", assBillMain.getState_name());
		mode.addAttribute("state", assBillMain.getState());
		mode.addAttribute("note", assBillMain.getNote());
		mode.addAttribute("create_emp_name", assBillMain.getCreate_emp_name());
		mode.addAttribute("create_emp", assBillMain.getCreate_emp());
		mode.addAttribute("bill_date", DateUtil.dateToString(assBillMain.getBill_date(), "yyyy-MM-dd"));
		mode.addAttribute("create_date", DateUtil.dateToString(assBillMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp_name", assBillMain.getAudit_emp_name());
		mode.addAttribute("audit_date", DateUtil.dateToString(assBillMain.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assBillMain.getAudit_emp());
		mode.addAttribute("store_id", assBillMain.getStore_id());
		mode.addAttribute("store_no", assBillMain.getStore_no());
		mode.addAttribute("store_code", assBillMain.getStore_code());
		mode.addAttribute("store_name", assBillMain.getStore_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asspay/back/viewout";
	}
	
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPrePayDetail = assBackBillDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assPrePayDetail);
	}
	
	/**
	 * 查询退款列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assBackBillStageService.query(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	/**
	 * 引入保存
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/initAssBackBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssBackBill(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String assPayImportMain = assBackPayMainService.initAssBackPaySave(mapVo);

		return JSONObject.parseObject(assPayImportMain);

	}
	
	/**
	 * 主页面点击引入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/assViewPayPage", method = RequestMethod.GET)
	public String assViewPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("pay_no", mapVo.get("pay_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asspay/back/viewoutmain";
	}
	
	/**
	 * @Description  引入查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBillBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillBillMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String[] list = assBackPayDetailService.queryBillNo(mapVo);
		
		if(list.equals("") && list.length > 0){
			mapVo.put("bill_nos", list);
		} else {
			mapVo.put("bill_nos", "");
		}
		
		String listJson = assBackBillMainService.queryimport(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	
	/**
	 * @Description 添加数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/saveAssBackPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssBackPayMain assApplyDept = assBackPayMainService.queryByCode(mapVo);
		if(assApplyDept != null){
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许修改 \"}");
			}
		}
		

		String assBackPayMainJson = assBackPayMainService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackPayMainJson);

	}
	
	
	/**
	 * @Description 退款确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/updateConfirmAssBackPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssBackPay(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> allDetailList = assBackPayDetailService.queryByAll(entityMap);
		
		for(Map<String, Object> allDetail : allDetailList){
			if(Math.abs(Double.parseDouble(allDetail.get("bill_money").toString())) > Math.abs(Double.parseDouble(allDetail.get("bill_bill_money").toString()))){
				return JSONObject.parseObject("{\"warn\":\"存在相同发票单退款金额大于发票退款金额的单据,不能退款确认! \"}");
			}
		}
		
		String assBackPayMainJson = "";
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("pay_no", ids[3]);
			
			mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("audit_emp", SessionManager.getUserId());
			AssBackPayMain assBackPayMain = assBackPayMainService.queryByCode(mapVo);
			if (assBackPayMain.getState() == 2) {
				continue;
			}
			
			/**
			 * 验证主表数据
			 */
			if(DateUtil.compareDate(assBackPayMain.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能退款确认! \"}");
			}
			
			if(Math.abs(assBackPayMain.getPay_money()) <= 0){
				return JSONObject.parseObject("{\"warn\":\"退款金额不能小于0,不能退款确认! \"}");
			}
			
			
			/**
			 * 验证明细数据
			 */
			List<Map<String, Object>> list = assBackPayDetailService.queryByPayNo(mapVo);
			
			if(list != null && list.size() > 0){
				
				for(Map<String, Object> assBillDetail : list){
					if(Math.abs(Double.parseDouble(assBillDetail.get("bill_money").toString())) <= 0){
						return JSONObject.parseObject("{\"warn\":\"明细退款金额不能小于0,不能退款确认! \"}");
					}
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退款确认! \"}");
			}
			
			
			/**
			 * 验证分期退款数据
			 */
			List<Map<String, Object>> stageList = assBackPayStageService.queryByPayNo(mapVo);
			if(stageList != null && stageList.size() > 0){
				for(Map<String, Object> assStageDetail : stageList){
					if(Math.abs(Double.parseDouble(assStageDetail.get("pay_money").toString())) <= 0){
						return JSONObject.parseObject("{\"warn\":\"退款金额不能小于0,不能退款确认! \"}");
					}
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有退款信息不能退款确认! \"}");
			}
			listVo.add(mapVo);
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复退款确认! \"}");
		}
		
		try {
			assBackPayMainJson = assBackPayMainService.updateBackPayConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBackPayMainJson);
	}
	

	/**
	 * @Description 更新跳转页面 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/assBackPayMainUpdatePage", method = RequestMethod.GET)
	public String assBackPayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackPayMain assBackPayMain = new AssBackPayMain();

		assBackPayMain = assBackPayMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackPayMain.getGroup_id());
		mode.addAttribute("hos_id", assBackPayMain.getHos_id());
		mode.addAttribute("copy_code", assBackPayMain.getCopy_code());
		mode.addAttribute("pay_no", assBackPayMain.getPay_no());
		mode.addAttribute("pay_date", DateUtil.dateToString(assBackPayMain.getPay_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assBackPayMain.getVen_id());
		mode.addAttribute("ven_no", assBackPayMain.getVen_no());
		mode.addAttribute("ven_code", assBackPayMain.getVen_code());
		mode.addAttribute("ven_name", assBackPayMain.getVen_name());
		mode.addAttribute("pay_money", assBackPayMain.getPay_money());
		mode.addAttribute("state", assBackPayMain.getState());
		mode.addAttribute("create_emp", assBackPayMain.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackPayMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assBackPayMain.getAudit_emp());
		mode.addAttribute("audit_date", assBackPayMain.getAudit_date());
		mode.addAttribute("note", assBackPayMain.getNote());
		mode.addAttribute("store_id", assBackPayMain.getStore_id());
		mode.addAttribute("store_no", assBackPayMain.getStore_no());
		mode.addAttribute("store_code", assBackPayMain.getStore_code());
		mode.addAttribute("store_name", assBackPayMain.getStore_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05088",MyConfig.getSysPara("05088"));
		
		return "hrp/ass/asspay/back/update";
	}

	/**
	 * @Description 删除数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/deleteAssBackPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("pay_no", ids[3]);
			
			AssBackPayMain assApplyDept = assBackPayMainService.queryByCode(mapVo);
			
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许删除 \"}");
			}

			listVo.add(mapVo);

		}

		String assBackPayMainJson = assBackPayMainService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackPayMainJson);

	}

	/**
	 * @Description 查询数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackPayMain = assBackPayMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackPayMain);

	}

	/**
	 * @Description 删除数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/deleteAssBackPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPayDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("pay_no", ids[3]);
			mapVo.put("bill_no", ids[4]);
			
			AssBackPayMain assApplyDept = assBackPayMainService.queryByCode(mapVo);
			
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许删除 \"}");
			}

			listVo.add(mapVo);

		}

		String assBackPayDetailJson = assBackPayDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackPayDetailJson);

	}

	/**
	 * @Description 查询数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPayDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackPayDetail = assBackPayDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackPayDetail);

	}
	
	
	@RequestMapping(value = "/hrp/ass/asspay/back/saveAssAccBackPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackPayStage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssBackPayMain assApplyDept = assBackPayMainService.queryByCode(mapVo);
		
		if (assApplyDept.getState() > 0) {
			return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许修改 \"}");
		}

		String assBackPayMainJson = assBackPayStageService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackPayMainJson);

	}
	
	/**
	 * @Description 删除数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/deleteAssAccBackPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPayStage(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("pay_no", ids[3]);
			mapVo.put("bill_no", ids[4]);
			mapVo.put("pay_code", ids[5]);
			AssBackPayMain assApplyDept = assBackPayMainService.queryByCode(mapVo);
			
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许删除 \"}");
			}
			
			listVo.add(mapVo);

		}

		String assBackPayDetailJson = assBackPayStageService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackPayDetailJson);

	}
	
	
	
	/**
	 * @Description 查询数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssAccBackPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPayStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackPayDetail = assBackPayStageService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackPayDetail);

	}
	
	/**
	 * @Description 维护页面加号中查询发票分期退款
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackBillStageByDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillStageByDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assBackPayDetail = assBackPayStageService.queryBackBillStageDetail(getPage(mapVo));

		return JSONObject.parseObject(assBackPayDetail);

	}

	/**
	 * 资产退款单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/back/queryAssBackPayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPayState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackPayMainService.queryAssBackPayState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"资产退款单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	
	
}
