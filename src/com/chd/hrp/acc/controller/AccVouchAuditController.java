package com.chd.hrp.acc.controller;

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
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.serviceImpl.AccVouchAuditServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccVouchCashierServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchServiceImpl;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchAuditController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchAuditController.class);

	@Resource(name = "accVouchAuditService")
	private final AccVouchAuditServiceImpl accVouchAuditService = null;
	
	@Resource(name = "accVouchCashierService")
	private final AccVouchCashierServiceImpl accVouchCashierService = null;
	
	@Resource(name = "accVouchService")
	private final AccVouchServiceImpl accVouchService = null;
	/**
	 * 凭证审核<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/audit/accVouchAuditMainPage", method = RequestMethod.GET)
	public String accVouchAuditMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();//SessionManager.getSysYearMonth("acc_flag");
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("copy_nature", SessionManager.getCopyNature());
		
		return "hrp/acc/accvouch/audit/accVouchAuditMain";

	}
	
		// 高级查询页面
		@RequestMapping(value = "/hrp/acc/accvouch/audit/accVouchAuditSearchPage", method = RequestMethod.GET)
		public String accVouchAuditSearchPage(Model mode) throws Exception {

			return "hrp/acc/accvouch/audit/accVouchAuditSearch";

		}
		
		//标注页面
		@RequestMapping(value = "/hrp/acc/accvouch/audit/accVouchAuditLabelPage", method = RequestMethod.GET)
		public String accVouchAuditLabelPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mode.addAttribute("paramVo",mapVo.get("paramVo"));
			
			return "hrp/acc/accvouch/audit/accVouchAuditLabel";

		}
	
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/audit/queryAccVouchAudit", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccVouchAudit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accVouch = null;
		
		/*if("1".equals(mapVo.get("pay_type_code"))){
			
			accVouch = accVouchAuditService.queryAccVouchAudit(getPage(mapVo));
			
		}else{
			
			accVouch = accVouchService.queryAccVouchPayTypeCode(getPage(mapVo));
		}*/
		//凭证制单、凭证签字、凭证审核共用，方便维护
		try{
			if(	"1".equals(mapVo.get("pay_type_code"))){
				
				accVouch = accVouchService.queryAccVouch(getPage(mapVo));
			}else{
				
				accVouch = accVouchService.queryAccVouchPayTypeCode(getPage(mapVo));
			}
	
			return JSONObject.parseObject(accVouch);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	//审核
	@RequestMapping(value = "/hrp/acc/accvouch/audit/auditAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAccVouch(@RequestParam(value = "ParamVo") String paramVo,String state, Model mode) throws Exception {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		//entityMap.put("acc_year", SessionManager.getAcctYear());
		entityMap.put("state", state);
		String vouch_id = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String [] res = id.split("@");
			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			//mapVo.put("acc_year", SessionManager.getAcctYear());
			mapVo.put("state", "3");
			mapVo.put("vouch_state", res[1]);
			mapVo.put("audit", "0");
			mapVo.put("audit_user", SessionManager.getUserId());
			mapVo.put("audit_date", new Date());
			vouch_id = vouch_id + res[0]+",";
			listVo.add(mapVo);
		}
		vouch_id = vouch_id.substring(0,vouch_id.lastIndexOf(","));
		entityMap.put("vouchid", vouch_id);
		
		//父节点是出纳签字
		if(entityMap.get("state").toString().equals("2")){
			
			//包含现金银行
			entityMap.put("flag", 1);
			List<String> list1=accVouchService.queryAccVouchDetailByVouchIdList(entityMap);
			//System.out.println(list1.size()+","+list1 == null);
			if(list1!=null && list1.size()>0){
				
				return JSONObject.parseObject("{\"msg\":\"现金银行凭证必须出纳签字："+list1.toString()+"\",\"state\":\"false\"}");
			}
			//不包含现金银行
			entityMap.put("flag", 2);
			entityMap.put("state",1);
			List<String> list2=accVouchService.queryAccVouchDetailByVouchIdList(entityMap);
			if(list2!=null && list2.size()>0){
				
				return JSONObject.parseObject("{\"msg\":\"非现金银行凭证必须是新建凭证："+list2.toString()+"\",\"state\":\"false\"}");
			}
			
		}else{
			entityMap.put("flag", 3);
			List<String> list3=accVouchService.queryAccVouchDetailByVouchIdList(entityMap);
			if(list3!=null && list3.size()>0){
				
				return JSONObject.parseObject("{\"msg\":\"凭证必须是新建凭证："+list3.toString()+"\",\"state\":\"false\"}");
			}
		}
		
		
		String accVouchJson = accVouchAuditService.updateBatchAccVouchAudit(listVo);
		
		return JSONObject.parseObject(accVouchJson);

	}
	
	//取消审核
	@RequestMapping(value = "/hrp/acc/accvouch/audit/unauditAccVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unauditAccVouch(@RequestParam(value = "ParamVo") String paramVo,String state, Model mode) throws Exception {
		String accVouchJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			String [] res = id.split("@");
			
			mapVo.put("vouch_id", res[0]);// 实际实体类变量
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("state", res[2]);
			
			mapVo.put("parent_state", state);
			
			mapVo.put("vouch_state", res[1]);
			
			mapVo.put("audit", "0");
			
			mapVo.put("audit_user", "");
			
			mapVo.put("audit_date","");
			
			listVo.add(mapVo);
		}
		
		
		try {
			
			
			//父节点是出纳签字
			if(state.equals("2")){
				//有现金银行科目分开处理
				accVouchJson = accVouchAuditService.updateBatchAccVouchAuditBank(listVo);
			}else{
				accVouchJson = accVouchAuditService.updateBatchAccVouchAudit(listVo);
			}
			return JSONObject.parseObject(accVouchJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
		
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/audit/accVouchLabel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accVouchLabel(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		String paramVo = map.get("paramVo").toString();
		
		for (String id : paramVo.split("@")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			mapVo.put("vouch_id", id);// 实际实体类变量
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("note", map.get("note"));
			
			listVo.add(mapVo);
		
		}
		
		String accVouchJson = accVouchAuditService.updateBatchAccVouchAuditLabel(listVo);
		
		return JSONObject.parseObject(accVouchJson);
	}
	
	@RequestMapping(value = "/hrp/acc/accvouch/audit/accVouchUnlabel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accVouchUnlabel(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			
			mapVo.put("vouch_id", id);// 实际实体类变量
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("acc_year", SessionManager.getAcctYear());
			
			mapVo.put("note", "");
			
			listVo.add(mapVo);
		
		}
		
		String accVouchJson = accVouchAuditService.updateBatchAccVouchAuditLabel(listVo);
		
		return JSONObject.parseObject(accVouchJson);

	}
	
	//全部审核
	@RequestMapping(value = "/hrp/acc/accvouch/audit/auditAllPage", method = RequestMethod.GET)
	public String auditAllPage(Model mode) throws Exception {

		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		
		return "hrp/acc/accvouch/audit/auditAll";

	}
	
	//全部审核
	@RequestMapping(value = "/hrp/acc/accvouch/audit/auditAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAll(@RequestParam Map<String, Object> map,Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		map.put("user_id", SessionManager.getUserId());
		
		try{
			String accVouchJson = accVouchAuditService.auditAll(map);
			return JSONObject.parseObject(accVouchJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}
}
