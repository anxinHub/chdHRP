/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
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
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.prepay.AssBackPrePayMain;
import com.chd.hrp.ass.entity.prepay.AssPrePayMain;
import com.chd.hrp.ass.service.prepay.AssPrePayDetailService;
import com.chd.hrp.ass.service.prepay.AssPrePayMainService;
import com.chd.hrp.ass.service.prepay.AssPrePayStageService;

/**
 * 
 * @Description:
 * 预付款主表
 * @Table:
 * ASS_PRE_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
@RequestMapping(value="/hrp/ass/assprepay/prepay")
public class AssPrePayMainController extends BaseController{
	private static Logger logger = Logger.getLogger(AssPrePayMainController.class);
	
	// 引入Service服务
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
	@RequestMapping(value = "assPrePayMainPage", method = RequestMethod.GET)
	public String assBackSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05085",MyConfig.getSysPara("05085"));
		
		return "hrp/ass/assprepay/prepay/main";

	}
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "assPrePayAddPage", method = RequestMethod.GET)
	public String assBackSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assprepay/prepay/add";

	}
	
	/**
	 * @Description 添加数据 预付款主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "saveAssPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssPrePayMain assPrePay = assPrePayMainService.queryByCode(mapVo);
		if(assPrePay != null){
			if(assPrePay.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认数据不能保存! \"}");
			}
		}

		String assPrePayJson = assPrePayMainService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assPrePayJson);

	}
	
	/**
	 * @Description 更新跳转页面  预付款主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "assPrePayUpdatePage", method = RequestMethod.GET)
	public String assPrePayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssPrePayMain assPrePay = new AssPrePayMain();

		assPrePay = assPrePayMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assPrePay.getGroup_id());
		mode.addAttribute("hos_id", assPrePay.getHos_id());
		mode.addAttribute("copy_code", assPrePay.getCopy_code());
		mode.addAttribute("pay_no", assPrePay.getPay_no());
		mode.addAttribute("pay_date", DateUtil.dateToString(assPrePay.getPay_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assPrePay.getVen_id());
		mode.addAttribute("ven_no", assPrePay.getVen_no());
		mode.addAttribute("ven_code", assPrePay.getVen_code());
		mode.addAttribute("ven_name", assPrePay.getVen_name());
		mode.addAttribute("pay_money", assPrePay.getPay_money());
		mode.addAttribute("note", assPrePay.getNote());
		mode.addAttribute("create_emp", assPrePay.getCreate_emp());
		mode.addAttribute("create_emp_name", assPrePay.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assPrePay.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_date", DateUtil.dateToString(assPrePay.getAudit_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assPrePay.getAudit_emp());
		mode.addAttribute("audit_emp_name", assPrePay.getAudit_emp_name());
		mode.addAttribute("state", assPrePay.getState());
		mode.addAttribute("state_name", assPrePay.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05085",MyConfig.getSysPara("05085"));
		
		return "hrp/ass/assprepay/prepay/update";
	}


	/**
	 * @Description 删除数据  预付款主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "deleteAssPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPrePayMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssPrePayMain assPrePay = assPrePayMainService.queryByCode(mapVo);
			if(assPrePay != null){
				if(assPrePay.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assPrePayJson = assPrePayMainService.deleteBatch(listVo);

		return JSONObject.parseObject(assPrePayJson);

	}

	/**
	 * @Description 查询数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPrePayMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assPrePayMainService.query(getPage(mapVo));

		return JSONObject.parseObject(listJson);

	}
	
	/**
	 * @Description 查询明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPrePayDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assPrePayDetailService.query(mapVo);

		return JSONObject.parseObject(listJson);

	}
	
	
	/**
	 * @Description 删除明细数据 
	 * @param paramVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "deleteAssPrePayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPrePayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode)
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
    
		String assPrePayMainJson = assPrePayDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(assPrePayMainJson);

	}
	
	/**
	 * 查询预付款支付方式
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssPrePayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPrePayStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String listJson = assPrePayStageService.query(mapVo);

		return JSONObject.parseObject(listJson);

	}
	
	
	/**
	 * @Description 保存预付款支付方式 
	 * @param mapVo
	 * @return Map<String, Object>
	 * @throws Exception
	 */	
	@RequestMapping(value = "saveAssPrePayStage", method = RequestMethod.POST)
	@ResponseBody
	public String saveAssPrePayStage(@RequestParam Map<String, Object> mapVo)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssPrePayMain assPrePay = assPrePayMainService.queryByCode(mapVo);
		if(assPrePay != null){
			if(assPrePay.getState() > 0){
				return "{\"warn\":\"已确认数据不能保存! \"}";
			}
		}
		
		return assPrePayStageService.add(mapVo);
	}
	
	/**
	 * @Description 删除预付款支付方式 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "deleteAssPrePayStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPrePayStage(@RequestParam(value="ParamVo") String paramVo, Model mode)
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
		String returnJson = assPrePayStageService.deleteBatch(listVo);

		return JSONObject.parseObject(returnJson);

	}
	
	
	/**
	 * @Description 查询数据 预付发票主表ass_pre_bill
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssPreBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPreBill(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPreBillJson = assPrePayMainService.queryAssPreBill(mapVo);

		return JSONObject.parseObject(assPreBillJson);

	}
			
	/**
	 * @Description 查询明细数据 预付发票主表ass_pre_bill_detail
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "queryAssPreBillDetail", method = RequestMethod.POST)
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
		return assPrePayMainService.queryAccPayType(mapVo);

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
			AssPrePayMain assPrePayMain = assPrePayMainService.queryByCode(mapVo);
			if(assPrePayMain == null || assPrePayMain.getState() > 0){
				continue;
			}
			
			//核定表使用
			mapVo.put("ven_id", assPrePayMain.getVen_id());
			mapVo.put("ven_no", assPrePayMain.getVen_no());
			
			listVo.add(mapVo);
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String returnJson = null;
		try {
			returnJson = assPrePayMainService.updateConfirmPrePay(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject(e.getMessage());
		}

		return JSONObject.parseObject(returnJson);

	}
	
	/**
	 * @Description 取消确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "updateCancelConfirmPrePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCancelConfirmPrePay(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			//mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			//mapVo.put("audit_emp", SessionManager.getUserId());
			
			//判断是否确认如果确认则忽略
			AssPrePayMain assPrePayMain = assPrePayMainService.queryByCode(mapVo);
			if(assPrePayMain == null || assPrePayMain.getState() == 2){
				continue;
			}
			
			//核定表使用
			//mapVo.put("ven_id", assPrePayMain.getVen_id());
			//mapVo.put("ven_no", assPrePayMain.getVen_no());
			
			listVo.add(mapVo);
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"请选择已确认数据! \"}");
		}

		String returnJson = assPrePayMainService.updateCancelConfirmPrePay(listVo);

		return JSONObject.parseObject(returnJson);

	}
	
	/**
	 * 资产预付款单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "queryAssPrepayState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPrepayState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assPrePayMainService.queryAssPrepayState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"资产预付款单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	
	
}
