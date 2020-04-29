/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.order.audit;
import java.util.ArrayList;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.order.audit.MedOrderAuditService;
import com.chd.hrp.med.service.sms.MedSupSmsService;
/**
 * 
 * @Description:
 *  订单审核
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedOrderAuditController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedOrderAuditController.class);
	
	//引入Service服务
	@Resource(name = "medOrderAuditService")
	private final MedOrderAuditService medOrderAuditService = null;
	//引入Service服务
	@Resource(name = "medSupSmsService")
	private final MedSupSmsService medSupSmsService = null;
   
	/**
	 * @Description 
	 * 订单审核--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/audit/medOrderAuditMainPage", method = RequestMethod.GET)
	public String medOrderAuditMainPage(Model mode) throws Exception {
		
		return "hrp/med/order/audit/medOrderAuditMain";

	}
	
	/**
	 * 订单审核--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/audit/queryMedOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medOrder = medOrderAuditService.queryOrderAudit(getPage(mapVo));
		
		return JSONObject.parseObject(medOrder);
	}
	
	/**
	 * 订单审核--审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/audit/auditMedOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMedOrderAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("order_id", ids[3]);
			if(mapVo.get("show_history") == null){
				mapVo.put("show_history", MyConfig.getSysPara("03001"));
			}
			listVo.add(mapVo);
		}
		String medOrderMain = medOrderAuditService.auditOrderMain(listVo);
		
		return JSONObject.parseObject(medOrderMain);
	}
	
	/**
	 * 订单审核--取消审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/audit/unAuditMedOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMedOrderAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("order_id", ids[3]);
			if(mapVo.get("show_history") == null){
				mapVo.put("show_history", MyConfig.getSysPara("03001"));
			}
			listVo.add(mapVo);
		}
		String medOrderMain = medOrderAuditService.unAuditOrderMain(listVo);
		
		return JSONObject.parseObject(medOrderMain);
	}
	
	/**
	 * 订单审核--发送
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/audit/sendOutMedOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendOutMedOrderAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("order_id", ids[3]);
			if(mapVo.get("show_history") == null){
				mapVo.put("show_history", MyConfig.getSysPara("03001"));
			}
			listVo.add(mapVo);
		}
		String medOrderMain = medOrderAuditService.sendOutOrderMain(listVo);
		
		Object send_para=MyConfig.getSysPara("08036");
		send_para=send_para==null?"0":send_para;
		int is_send = Integer.valueOf(send_para.toString());
		
		if(is_send==1){
			//短信通知供应商
			medSupSmsService.addBatch(listVo);
		}
		
		return JSONObject.parseObject(medOrderMain);
	}
	
	/**
	 * 订单修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/audit/medOrderAuditDetailPage", method = RequestMethod.GET)
	public String medOrderAuditDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		Map<String, Object> medOrderMain = medOrderAuditService.queryByCode(mapVo);
		
		mode.addAttribute("medOrderMain", medOrderMain);
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08023", MyConfig.getSysPara("08023"));
		
		return "hrp/med/order/audit/medOrderAuditDetail";

	}
	
	/**
	 * @Description 订单审核：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/order/audit/queryMedOrderAuditDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOrderAuditDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medJson = medOrderAuditService.queryDetail(mapVo);

		return JSONObject.parseObject(medJson);
	}
	
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/med/order/audit/medOrderAuditPrintSetPage", method = RequestMethod.GET)
	public String medAffiTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/order/audit/queryMedOrderAuditByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		String reJson=null;
		try {
			reJson=medOrderAuditService.queryMedOrderAuditByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
}

