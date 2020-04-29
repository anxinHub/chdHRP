/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.pay.main;

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
import com.chd.hrp.ass.entity.pay.main.AssPayMain;
import com.chd.hrp.ass.service.bill.AssBillDetailService;
import com.chd.hrp.ass.service.bill.AssBillMainService;
import com.chd.hrp.ass.service.bill.AssBillStageService;
import com.chd.hrp.ass.service.pay.main.AssPayDetailService;
import com.chd.hrp.ass.service.pay.main.AssPayMainService;
import com.chd.hrp.ass.service.pay.main.AssPayStageService;
import com.chd.hrp.ass.service.prepay.AssPrePayDetailService;
import com.chd.hrp.ass.service.prepay.AssPrePayMainService;
import com.chd.hrp.ass.service.prepay.AssPrePayStageService;

/**
 * 
 * @Description: 发票主表
 * @Table: ASS_PAY_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssPayMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPayMainController.class);

	// 引入Service服务
	@Resource(name = "assPayMainService")
	private final AssPayMainService assPayMainService = null;

	@Resource(name = "assPayDetailService")
	private final AssPayDetailService assPayDetailService = null;
	
	@Resource(name = "assPayStageService")
	private final AssPayStageService assPayStageService = null;
	
	@Resource(name = "assBillMainService")
	private final AssBillMainService assBillMainService = null;
	
	@Resource(name = "assBillDetailService")
	private final AssBillDetailService assBillDetailService = null;
	
	@Resource(name = "assBillStageService")
	private final AssBillStageService assBillStageService = null;
	
	@Resource(name = "assPrePayMainService")
	private final AssPrePayMainService assPrePayMainService = null;

	@Resource(name = "assPrePayDetailService")
	private final AssPrePayDetailService assPrePayDetailService = null;
	
	@Resource(name = "assPrePayStageService")
	private final AssPrePayStageService assPrePayStageService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/assPayMainMainPage", method = RequestMethod.GET)
	public String assPayMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05087",MyConfig.getSysPara("05087"));
		
		return "hrp/ass/asspay/main/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/assPayMainAddPage", method = RequestMethod.GET)
	public String assPayMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asspay/main/add";

	}
	
	@RequestMapping(value = "/hrp/ass/asspay/main/assBillMainUpdatePage", method = RequestMethod.GET)
	public String assBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssBillMain assBillMain = new AssBillMain();

		assBillMain = assBillMainService.queryByCode(mapVo);

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
		
		return "hrp/ass/asspay/main/viewout";
	}
	
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPrePayDetail = assBillDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assPrePayDetail);
	}
	
	/**
	 * @Description 查询明细数据 预付发票主表ass_pre_bill_detail
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssPreBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPreBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPreBillJson = assPrePayMainService.queryAssPreBillDetail(mapVo);

		return JSONObject.parseObject(assPreBillJson);

	}
	/**
	 * 查询付款列表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assBillStageService.query(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	
	
	/**
	 * 支付方式 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return assPrePayMainService.queryAccPayType(mapVo);

	}
	/**
	 * 引入跳转页面
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/assImportPayPage", method = RequestMethod.GET)
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
		
		return "hrp/ass/asspay/main/importpaymain";
	}
	
	
	/**
	 * 批量增加支付方式跳转页面
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/assAddPayPage", method = RequestMethod.GET)
	public String assAddPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/ass/asspay/main/addpay";
	}
	
	/**
	 * 引入保存
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/initAssBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssBill(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String assPayImportMain = assPayMainService.initAssPaySave(mapVo);

		return JSONObject.parseObject(assPayImportMain);

	}
	
	@RequestMapping(value = "/hrp/ass/asspay/main/assViewPayPage", method = RequestMethod.GET)
	public String assViewPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("pay_no", mapVo.get("pay_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asspay/main/viewoutmain";
	}
	
	/**
	 * @Description  引入查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//--------------------------------------------
		String[] list = assPayDetailService.queryBillNo(mapVo);
		
		if(list.equals("") && list.length > 0){
			mapVo.put("bill_nos", list);
		} else {
			mapVo.put("bill_nos", "");
		}
		
		
		String listJson = assBillMainService.queryimports(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	/**
	 * @Description  引入查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssBillBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillBillMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String[] list = assPayDetailService.queryBillNo(mapVo);
		
		if(list.equals("") && list.length > 0){
			mapVo.put("bill_nos", list);
		} else {
			mapVo.put("bill_nos", "");
		}
		
		String listJson = assBillMainService.queryimport(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	/**
	 * @Description 添加数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/saveAssPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssPayMain assApplyDept = assPayMainService.queryByCode(mapVo);
		if(assApplyDept != null){
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许修改 \"}");
			}
		}
		

		String assPayMainJson = assPayMainService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assPayMainJson);

	}
	
	/**
	 * 主页面引入查询
	 */
	
	/**
	 * @Description 付款确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/updateConfirmAssPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssPay(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> allDetailList = assPayDetailService.queryByAll(entityMap);
		
		for(Map<String, Object> allDetail : allDetailList){
			if(Double.parseDouble(allDetail.get("bill_money").toString()) > Double.parseDouble(allDetail.get("bill_bill_money").toString())){
				return JSONObject.parseObject("{\"warn\":\"存在相同发票单付款金额大于发票金额的单据,不能付款确认! \"}");
			}
		}
		
		String assPayMainJson = "";
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("pay_no", ids[3]);
			
			mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("audit_emp", SessionManager.getUserId());
			AssPayMain assPayMain = assPayMainService.queryByCode(mapVo);
			if (assPayMain.getState() == 2) {
				continue;
			}
			
			/**
			 * 验证主表数据
			 */
			if(DateUtil.compareDate(assPayMain.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能付款确认! \"}");
			}
			
			if(assPayMain.getPay_money() <= 0){
				return JSONObject.parseObject("{\"warn\":\"付款金额不能小于0,不能付款确认! \"}");
			}
			
			
			/**
			 * 验证明细数据
			 */
			List<Map<String, Object>> list = assPayDetailService.queryByPayNo(mapVo);
			
			if(list != null && list.size() > 0){
				
				for(Map<String, Object> assBillDetail : list){
					if(Double.parseDouble(assBillDetail.get("bill_money").toString()) <= 0){
						return JSONObject.parseObject("{\"warn\":\"明细付款金额不能小于0,不能付款确认! \"}");
					}
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能付款确认! \"}");
			}
			
			
			/**
			 * 验证分期付款数据
			 */
			List<Map<String, Object>> stageList = assPayStageService.queryByPayNo(mapVo);
			if(stageList != null && stageList.size() > 0){
				for(Map<String, Object> assStageDetail : stageList){
					if(Double.parseDouble(assStageDetail.get("pay_money").toString()) <= 0){
						return JSONObject.parseObject("{\"warn\":\"付款金额不能小于0,不能付款确认! \"}");
					}
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有付款信息不能付款确认! \"}");
			}
			listVo.add(mapVo);
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复付款确认! \"}");
		}
		
		try {
			assPayMainJson = assPayMainService.updatePayConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assPayMainJson);
	}
	

	/**
	 * @Description 更新跳转页面 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/assPayMainUpdatePage", method = RequestMethod.GET)
	public String assPayMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPayMain assPayMain = new AssPayMain();

		assPayMain = assPayMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assPayMain.getGroup_id());
		mode.addAttribute("hos_id", assPayMain.getHos_id());
		mode.addAttribute("copy_code", assPayMain.getCopy_code());
		mode.addAttribute("pay_no", assPayMain.getPay_no());
		mode.addAttribute("pay_date", DateUtil.dateToString(assPayMain.getPay_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assPayMain.getVen_id());
		mode.addAttribute("ven_no", assPayMain.getVen_no());
		mode.addAttribute("ven_code", assPayMain.getVen_code());
		mode.addAttribute("ven_name", assPayMain.getVen_name());
		mode.addAttribute("pay_money", assPayMain.getPay_money());
		mode.addAttribute("state", assPayMain.getState());
		mode.addAttribute("create_emp", assPayMain.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assPayMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assPayMain.getAudit_emp());
		mode.addAttribute("audit_date", assPayMain.getAudit_date());
		mode.addAttribute("note", assPayMain.getNote());
		mode.addAttribute("store_id", assPayMain.getStore_id());
		mode.addAttribute("store_no", assPayMain.getStore_no());
		mode.addAttribute("store_code", assPayMain.getStore_code());
		mode.addAttribute("store_name", assPayMain.getStore_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05087",MyConfig.getSysPara("05087"));
		
		return "hrp/ass/asspay/main/update";
	}

	/**
	 * @Description 删除数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/deleteAssPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssPayMain assApplyDept = assPayMainService.queryByCode(mapVo);
			
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许删除 \"}");
			}

			listVo.add(mapVo);

		}

		String assPayMainJson = assPayMainService.deleteBatch(listVo);

		return JSONObject.parseObject(assPayMainJson);

	}

	/**
	 * @Description 查询数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssPayMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPayMain = assPayMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assPayMain);

	}

	/**
	 * @Description 删除数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/deleteAssPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPayDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssPayMain assApplyDept = assPayMainService.queryByCode(mapVo);
			
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许删除 \"}");
			}
			
			listVo.add(mapVo);

		}

		String assPayDetailJson = assPayDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(assPayDetailJson);

	}

	/**
	 * @Description 查询数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPayDetail = assPayDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assPayDetail);

	}
	
	/**
	 * 批量增加支付方式
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/saveAssAccPayPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAccPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssPayMain assApplyDept = assPayMainService.queryByCode(mapVo);
		
		if (assApplyDept.getState() > 0) {
			return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许修改 \"}");
		}

		String assPayMainJson = assPayStageService.addOrUpdateAss(mapVo);

		return JSONObject.parseObject(assPayMainJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/asspay/main/saveAssAccPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssPayStage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssPayMain assApplyDept = assPayMainService.queryByCode(mapVo);
		
		if (assApplyDept.getState() > 0) {
			return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许修改 \"}");
		}

		String assPayMainJson = assPayStageService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assPayMainJson);

	}
	
	/**
	 * @Description 删除数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/deleteAssAccPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPayStage(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssPayMain assApplyDept = assPayMainService.queryByCode(mapVo);
			
			if (assApplyDept.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的单据不允许删除 \"}");
			}
			
			listVo.add(mapVo);

		}

		String assPayDetailJson = assPayStageService.deleteBatch(listVo);

		return JSONObject.parseObject(assPayDetailJson);

	}
	
	
	
	/**
	 * @Description 查询数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssAccPayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPayDetail = assPayStageService.query(getPage(mapVo));

		return JSONObject.parseObject(assPayDetail);

	}
	
	/**
	 * @Description 维护页面加号中查询发票分期付款
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssBillStageByDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBillStageByDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assPayDetail = assPayStageService.queryBillStageDetail(getPage(mapVo));

		return JSONObject.parseObject(assPayDetail);

	}

	
	/**
	 * 资产付款单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asspay/main/queryAssPayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPayState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assPayMainService.queryAssPayState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"资产付款单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
}
