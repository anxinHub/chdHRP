package com.chd.hrp.ass.controller.prepay;

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
import com.chd.hrp.ass.entity.prepay.AssBackPrePayMain;
import com.chd.hrp.ass.service.prepay.AssBackPrePayDetailService;
import com.chd.hrp.ass.service.prepay.AssBackPrePayMainService;
import com.chd.hrp.ass.service.prepay.AssBackPrePayStageService;

/**
 * 
 * @Description:
 * 预退款主表
 * @Table:
 * ASS_BACK_PRE_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
@RequestMapping(value="/hrp/ass/assprepay/backprepay")
public class AssBackPrePayMainController extends BaseController{
	private static Logger logger = Logger.getLogger(AssBackPrePayMainController.class);
	
	// 引入Service服务
	@Resource(name = "assBackPrePayMainService")
	private final AssBackPrePayMainService assBackPrePayMainService = null;
	
	@Resource(name = "assBackPrePayDetailService")
	private final AssBackPrePayDetailService assBackPrePayDetailService = null;
	
	@Resource(name = "assBackPrePayStageService")
	private final AssBackPrePayStageService assBackPrePayStageService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assBackPrePayMainPage", method = RequestMethod.GET)
	public String assBackSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05085",MyConfig.getSysPara("05085"));
		
		return "hrp/ass/assprepay/backprepay/main";

	}
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assBackPrePayAddPage", method = RequestMethod.GET)
	public String assBackSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assprepay/backprepay/add";

	}
	
	/**
	 * @Description 添加数据 预退款主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "saveAssBackPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		mapVo.put("create_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		
		mapVo.put("state", "0");
		
		String yearmonth = mapVo.get("pay_date").toString().substring(0, 4) + mapVo.get("pay_date").toString().substring(5, 7);
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
		
		AssBackPrePayMain assBackPrePay = assBackPrePayMainService.queryByCode(mapVo);
		if(assBackPrePay != null){
			if(assBackPrePay.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认数据不能保存! \"}");
			}
		}

		String assBackPrePayJson = assBackPrePayMainService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackPrePayJson);

	}
	
	/**
	 * @Description 更新跳转页面  预退款主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "assBackPrePayUpdatePage", method = RequestMethod.GET)
	public String assBackPrePayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackPrePayMain assBackPrePay = new AssBackPrePayMain();

		assBackPrePay = assBackPrePayMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackPrePay.getGroup_id());
		mode.addAttribute("hos_id", assBackPrePay.getHos_id());
		mode.addAttribute("copy_code", assBackPrePay.getCopy_code());
		mode.addAttribute("pay_no", assBackPrePay.getPay_no());
		mode.addAttribute("pay_date", DateUtil.dateToString(assBackPrePay.getPay_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assBackPrePay.getVen_id());
		mode.addAttribute("ven_no", assBackPrePay.getVen_no());
		mode.addAttribute("ven_code", assBackPrePay.getVen_code());
		mode.addAttribute("ven_name", assBackPrePay.getVen_name());
		mode.addAttribute("pay_money", assBackPrePay.getPay_money());
		mode.addAttribute("note", assBackPrePay.getNote());
		mode.addAttribute("create_emp", assBackPrePay.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackPrePay.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackPrePay.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_date", DateUtil.dateToString(assBackPrePay.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assBackPrePay.getAudit_emp());
		mode.addAttribute("audit_emp_name", assBackPrePay.getAudit_emp_name());
		mode.addAttribute("state", assBackPrePay.getState());
		mode.addAttribute("state_name", assBackPrePay.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05085",MyConfig.getSysPara("05085"));
		
		return "hrp/ass/assprepay/backprepay/update";
	}


	/**
	 * @Description 删除数据  预退款主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteAssBackPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPrePayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackPrePayMain assBackPrePay = assBackPrePayMainService.queryByCode(mapVo);
			if(assBackPrePay != null){
				if(assBackPrePay.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackPrePayJson = assBackPrePayMainService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackPrePayJson);

	}

	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssBackPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assBackPrePayMainService.query(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	/**
	 * @Description 查询明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssBackPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPrePayDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assBackPrePayDetailService.query(mapVo);

		return JSONObject.parseObject(listJson);

	}
	
	
	/**
	 * @Description 删除明细数据 
	 * @param paramVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "deleteAssBackPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPrePayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("pay_no", ids[3]);
			mapVo.put("bill_no", ids[4]);
			
			listVo.add(mapVo);
	      
	    }
    
		String assBackPrePayMainJson = assBackPrePayDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackPrePayMainJson);

	}
	
	/**
	 * 查询预付款支付方式
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssBackPrePayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPrePayStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assBackPrePayStageService.query(mapVo);

		return JSONObject.parseObject(listJson);

	}
	
	
	/**
	 * @Description 保存预退款支付方式 
	 * @param mapVo
	 * @return Map<String, Object>
	 * @throws Exception
	 */	
	@RequestMapping(value = "saveAssBackPrePayStage", method = RequestMethod.POST)
	@ResponseBody
	public String saveAssBackPrePayStage(@RequestParam Map<String, Object> mapVo)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssBackPrePayMain assBackPrePay = assBackPrePayMainService.queryByCode(mapVo);
		if(assBackPrePay != null){
			if(assBackPrePay.getState() > 0){
				return "{\"warn\":\"已确认数据不能保存! \"}";
			}
		}
		
		return assBackPrePayStageService.add(mapVo);
	}
	
	/**
	 * @Description 删除预退款支付方式 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "deleteAssBackPrePayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackPrePayStage(@RequestParam(value="ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("pay_no", ids[3]);
			mapVo.put("bill_no", ids[4]);
			mapVo.put("pay_code", ids[5]);
			
			listVo.add(mapVo);
	      
	    }
		String returnJson = assBackPrePayStageService.deleteBatch(listVo);

		return JSONObject.parseObject(returnJson);

	}
	
	
	/**
	 * @Description 查询数据 预退发票主表ass_back_pre_bill
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssBackPreBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPreBill(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPreBillJson = assBackPrePayMainService.queryAssBackPreBill(mapVo);

		return JSONObject.parseObject(assPreBillJson);

	}
	
	/**
	 * @Description 查询明细数据 预付发票主表ass_pre_bill_detail
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssBackPreBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPreBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPreBillJson = assBackPrePayMainService.queryAssBackPreBillDetail(mapVo);

		return JSONObject.parseObject(assPreBillJson);

	}
	
	/**
	 * 支付方式 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAccPayType", method = RequestMethod.POST)
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
		return assBackPrePayMainService.queryAccPayType(mapVo);

	}
	
	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "updateConfirmPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmPrePay(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			//判断是否确认如果确认则忽略
			AssBackPrePayMain assBackPrePayMain = assBackPrePayMainService.queryByCode(mapVo);
			if(assBackPrePayMain == null || assBackPrePayMain.getState() > 0){
				continue;
			}
			
			//核定表使用
			mapVo.put("ven_id", assBackPrePayMain.getVen_id());
			mapVo.put("ven_no", assBackPrePayMain.getVen_no());
			
			listVo.add(mapVo);
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String returnJson = null;
		try {
			returnJson = assBackPrePayMainService.updateConfirmPrePay(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject(e.getMessage());
		}

		return JSONObject.parseObject(returnJson);

	}
	
	
	/**
	 * 资产预退款单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssBackPrepayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackPrepayState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackPrePayMainService.queryAssBackPrepayState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"资产预退款单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
}
