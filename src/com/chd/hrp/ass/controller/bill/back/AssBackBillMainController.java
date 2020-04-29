/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.bill.back;

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
import com.chd.hrp.ass.entity.bill.back.AssBackBillStage;
import com.chd.hrp.ass.entity.pay.back.AssBackPayDetail;
import com.chd.hrp.ass.service.bill.back.AssBackBillDetailService;
import com.chd.hrp.ass.service.bill.back.AssBackBillMainService;
import com.chd.hrp.ass.service.bill.back.AssBackBillStageService;
import com.chd.hrp.ass.service.pay.back.AssBackPayDetailService;

/**
 * 
 * @Description: 红冲发票主表
 * @Table: ASS_BACK_BILL_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBackBillMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackBillMainController.class);

	// 引入Service服务
	@Resource(name = "assBackBillMainService")
	private final AssBackBillMainService assBackBillMainService = null;

	@Resource(name = "assBackBillDetailService")
	private final AssBackBillDetailService assBackBillDetailService = null;
	
	@Resource(name = "assBackBillStageService")
	private final AssBackBillStageService assBackBillStageService = null;
	
	@Resource(name = "assBackPayDetailService")
	private final AssBackPayDetailService assBackPayDetailService = null;
	

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/assBackBillMainMainPage", method = RequestMethod.GET)
	public String assBackBillMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05086",MyConfig.getSysPara("05086"));
		
		return "hrp/ass/assbill/back/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/assBackBillMainAddPage", method = RequestMethod.GET)
	public String assBackBillMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assbill/back/add";
	}
	
	
	@RequestMapping(value = "/hrp/ass/assbill/back/assBackBillMainImportPage", method = RequestMethod.GET)
	public String assBackBillMainImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		return "hrp/ass/assbill/back/import";
	}

	/**
	 * @Description 添加数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/saveAssBackBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssBackBillMain assBillMain = assBackBillMainService.queryByCode(mapVo);
		
		if(assBillMain != null){
			if(assBillMain.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}
		
		String assBackBillMainJson = assBackBillMainService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackBillMainJson);

	}
	
	/**
	 * @Description 登记确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/updateConfirmAssBackBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssBackBill(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		
		List<AssBackBillStage> stagelist = assBackBillStageService.queryByConfirmAssBill(entityMap);
		Map<String, AssBackBillStage> keyMap = new HashMap<String, AssBackBillStage>();
		for(AssBackBillStage assBillStage : stagelist){
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
			
			mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("audit_emp", SessionManager.getUserId());
			AssBackBillMain assBackBillMain = assBackBillMainService.queryByCode(mapVo);
			if (assBackBillMain.getState() == 2) {
				continue;
			}
			
			/**
			 * 验证主表数据
			 */
			if(DateUtil.compareDate(assBackBillMain.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能登记确认! \"}");
			}
			
			if(Math.abs(assBackBillMain.getBill_money()) <= 0){
				return JSONObject.parseObject("{\"warn\":\"退票金额不能小于0,不能登记确认! \"}");
			}
			
			
			/**
			 * 验证明细数据
			 */
			List<Map<String, Object>> list = assBackBillDetailService.queryByAssBillNo(mapVo);
			
			if(list != null && list.size() > 0){
				
				for(Map<String, Object> assBillDetail : list){
					if(Math.abs(Double.parseDouble(assBillDetail.get("bill_money").toString())) <= 0){
						return JSONObject.parseObject("{\"warn\":\"明细退票金额不能小于0,不能登记确认! \"}");
					}
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能登记确认! \"}");
			}
			
			
			/**
			 * 验证分期付款数据
			 */
			List<Map<String, Object>> stageList = assBackBillStageService.queryByAssBackBillNo(mapVo);
			if(stageList != null && stageList.size() > 0){
				for(Map<String, Object> assStageDetail : stageList){
					if(Math.abs(Double.parseDouble(assStageDetail.get("pay_plan_money").toString())) <= 0){
						return JSONObject.parseObject("{\"warn\":\"退票金额不能小于0,不能登记确认! \"}");
					}
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有退款信息不能登记确认! \"}");
			}
			listVo.add(mapVo);
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复登记确认! \"}");
		}
		
		try {
			assBillMainJson = assBackBillMainService.updateBackBillConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBillMainJson);
	}
	
	//updateNotToExamineBackBillMain
	
	@RequestMapping(value = "/hrp/ass/assbill/back/updateNotToExamineBackBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamineBackBillMain(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String retErrot = "";
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("bill_no", ids[3]);
			
			//判断是不是被退款单引用 如果引用不能取消
			 List<AssBackPayDetail> list =  (List<AssBackPayDetail>) assBackPayDetailService.queryExists(mapVo);
			 if(list.size() > 0){
				 return JSONObject.parseObject("{\"warn\":\"被退款单引用,不能取消操作! \"}");
			 }
			//判断是否确认如果确认则忽略
			AssBackBillMain assBackBillMain = assBackBillMainService.queryByCode(mapVo);
			int state = assBackBillMain.getState()==null? 0:assBackBillMain.getState();
			if(assBackBillMain.getState()!=2){
				continue;
			}
			
			listVo.add(mapVo);
			
		}
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复销审操作! \"}");
		}
		String assPreBillMainJson = assBackBillMainService.updateNotToExamineBackBillMain(listVo);
		
		return JSONObject.parseObject(assPreBillMainJson);
		
	}
	
	
	/**
	 * @Description 更新跳转页面 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/assBackBillMainUpdatePage", method = RequestMethod.GET)
	public String assBackBillMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackBillMain assBackBillMain = new AssBackBillMain();

		assBackBillMain = assBackBillMainService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackBillMain.getGroup_id());
		mode.addAttribute("hos_id", assBackBillMain.getHos_id());
		mode.addAttribute("copy_code", assBackBillMain.getCopy_code());
		mode.addAttribute("bill_no", assBackBillMain.getBill_no());
		mode.addAttribute("invoice_no", assBackBillMain.getInvoice_no());
		mode.addAttribute("bill_date", DateUtil.dateToString(assBackBillMain.getBill_date(), "yyyy-MM-dd"));
		mode.addAttribute("ven_id", assBackBillMain.getVen_id());
		mode.addAttribute("ven_no", assBackBillMain.getVen_no());
		mode.addAttribute("ven_code", assBackBillMain.getVen_code());
		mode.addAttribute("ven_name", assBackBillMain.getVen_name());
		mode.addAttribute("store_id", assBackBillMain.getStore_id());
		mode.addAttribute("store_no", assBackBillMain.getStore_no());
		mode.addAttribute("store_code", assBackBillMain.getStore_code());
		mode.addAttribute("store_name", assBackBillMain.getStore_name());
		mode.addAttribute("pact_code", assBackBillMain.getPact_code());
		mode.addAttribute("bill_money", assBackBillMain.getBill_money());
		mode.addAttribute("state", assBackBillMain.getState());
		mode.addAttribute("create_emp", assBackBillMain.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackBillMain.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assBackBillMain.getAudit_emp());
		mode.addAttribute("audit_date", assBackBillMain.getAudit_date());
		mode.addAttribute("note", assBackBillMain.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05086",MyConfig.getSysPara("05086"));
		
		return "hrp/ass/assbill/back/update";
	}

	/**
	 * @Description 删除数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/deleteAssBackBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackBillMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackBillMain assBillMain = assBackBillMainService.queryByCode(mapVo);
			
			if(assBillMain != null){
				if(assBillMain.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackBillMainJson = assBackBillMainService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackBillMainJson);

	}

	/**
	 * @Description 查询数据 发票主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/queryAssBackBillMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackBillMain = assBackBillMainService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackBillMain);
	}
	
	@RequestMapping(value = "/hrp/ass/assbill/back/queryAssInAndAssChangeBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInAndAssChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackBillMain = assBackBillMainService.queryAssInAndAssChange(getPage(mapVo));

		return JSONObject.parseObject(assBackBillMain);
	}
	
	
	//引入分期付款
	@RequestMapping(value = "/hrp/ass/assbill/back/importInAndChangeBack", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importInAndChangeBack(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInSpecial = assBackBillMainService.importInAndChangeBack(mapVo);

		return JSONObject.parseObject(assAllotInSpecial);

	}

	/**
	 * @Description 删除数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/deleteAssBackBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackBillDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackBillMain assBillMain = assBackBillMainService.queryByCode(mapVo);
			
			if(assBillMain != null){
				if(assBillMain.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackBillDetailJson = assBackBillDetailService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackBillDetailJson);

	}

	/**
	 * @Description 查询数据 发票明细表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/queryAssBackBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackBillDetail = assBackBillDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackBillDetail);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assbill/back/saveAssBackBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackBillStage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssBackBillMain assBillMain = assBackBillMainService.queryByCode(mapVo);
		
		if(assBillMain != null){
			if(assBillMain.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assBackBillMainJson = assBackBillStageService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackBillMainJson);

	}
	
	/**
	 * @Description 删除数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/deleteAssBackBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackBillStage(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssBackBillMain assBillMain = assBackBillMainService.queryByCode(mapVo);
			
			if(assBillMain != null){
				if(assBillMain.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
				}
			}
			
			listVo.add(mapVo);

		}

		String assBackBillDetailJson = assBackBillStageService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackBillDetailJson);

	}
	
	
	
	/**
	 * @Description 查询数据 发票
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/queryAssBackBillStage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillStage(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackBillDetail = assBackBillStageService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackBillDetail);

	}
	
	/**
	 * 退款发票登记状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbill/back/queryAssBackBillState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackBillState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackBillMainService.queryAssBackBillState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"退款发票登记单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
}
