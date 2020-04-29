/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.order.audit;
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
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.service.order.audit.MatOrderAuditService;
import com.chd.hrp.mat.service.sms.MatSupSmsService;
/**
 * 
 * @Description:
 *  订单审核
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 
@Controller
public class MatOrderAuditController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatOrderAuditController.class);
	
	//引入Service服务
	@Resource(name = "matOrderAuditService")
	private final MatOrderAuditService matOrderAuditService = null;
	
   
	/**
	 * @Description 
	 * 订单审核--主页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/order/audit/matOrderAuditMainPage", method = RequestMethod.GET)
	public String matOrderAuditMainPage(Model mode) throws Exception {
		
		String url=ConfigInit.getConfigProperties("qzj_url");
		if(url!=null && !url.equals("")){
			mode.addAttribute("is_qzj", "true");
		}
		
		return "hrp/mat/order/audit/matOrderAuditMain";

	}
	
	/**
	 * 订单审核--主查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/queryMatOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		mapVo.put("user_id", SessionManager.getUserId());
		
		
		if(mapVo.get("show_detail") != null && "1".equals(mapVo.get("show_detail").toString())){
			String matOrder = matOrderAuditService.queryShowDetailCheck(getPage(mapVo));
			return JSONObject.parseObject(matOrder);
		}else{
			String matOrder = matOrderAuditService.queryOrderAudit(getPage(mapVo));
			return JSONObject.parseObject(matOrder);
		}
		
		
		
	}
	
	/**
	 * 订单审核--审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/auditMatOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> auditMatOrderAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matOrderMain = matOrderAuditService.auditOrderMain(listVo);
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 订单审核--取消审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/unAuditMatOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unAuditMatOrderAudit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matOrderMain = matOrderAuditService.unAuditOrderMain(listVo);
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 订单审核--发送
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/sendOutMatOrderAudit", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendOutMatOrderAudit(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		String paramVo=map.get("ParamVo").toString();
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
		
		String matOrderMain=null;
		try{
			matOrderMain = matOrderAuditService.sendOutOrderMain(listVo,map);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			matOrderMain="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 订单撤销
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/revokeOutMatOrder", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> revokeOutMatOrder(@RequestParam("ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("order_id", ids[0]);
			mapVo.put("order_code", ids[1]);
			listVo.add(mapVo);
		}
		
		String matOrderMain=null;
		try{
			matOrderMain = matOrderAuditService.revokeOutOrderMain(listVo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			matOrderMain="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	 * 订单修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/matOrderAuditDetailPage", method = RequestMethod.GET)
	public String matOrderAuditDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		Map<String, Object> matOrderMain = matOrderAuditService.queryByCode(mapVo);
		
		mode.addAttribute("matOrderMain", matOrderMain);

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04023", MyConfig.getSysPara("04023"));
		
		return "hrp/mat/order/audit/matOrderAuditDetail";

	}
	
	/**
	 * @Description 订单审核：根据主键查询明细信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/queryMatOrderAuditDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOrderAuditDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

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
		
		String matJson = matOrderAuditService.queryDetail(mapVo);

		return JSONObject.parseObject(matJson);
	}
	
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception   
	*/
	@RequestMapping(value = "/hrp/mat/order/audit/matOrderAuditPrintSetPage", method = RequestMethod.GET)
	public String matAffiTranPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/order/audit/queryMatOrderAuditByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiTranByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		String reJson=null;
		try {
			reJson=matOrderAuditService.queryMatOrderAuditByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	
}

