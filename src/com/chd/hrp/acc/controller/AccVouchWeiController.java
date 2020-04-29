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
import com.chd.hrp.acc.entity.AccVouchWei;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchWeiServiceImpl;

/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccVouchWeiController extends BaseController {
	private static Logger logger = Logger.getLogger(AccVouchWeiController.class);

	@Resource(name = "accVouchWeiService")
	private final AccVouchWeiServiceImpl accVouchWeiService = null;
	/**
	 * 凭证分册<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/accVouchWeiMainPage", method = RequestMethod.GET)
	public String accVouchWeiMainPage(Model mode) throws Exception {

		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		return "hrp/acc/accvouch/vouchwei/accVouchWeiMain";

	}
	
	/**
	 * 凭证分册<BR>
	 * 添加页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/accVouchWeiAddPage", method = RequestMethod.GET)
	public String accVouchWeiAddPage(Model mode) throws Exception {
		mode.addAttribute("acc_year_month", MyConfig.getCurAccYearMonth());
		return "hrp/acc/accvouch/vouchwei/accVouchWeiAdd";

	}
	
	/**
	 * 凭证分册<BR>
	 *修改页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/accVouchWeiUpdatePage", method = RequestMethod.GET)
	public String accVouchWeiUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
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
		
		AccVouchWei accVouchWei = accVouchWeiService.queryAccVouchWeiByCode(mapVo);
		
		mode.addAttribute("acc_year", accVouchWei.getAcc_year());
		
		mode.addAttribute("acc_month", accVouchWei.getAcc_month());
		
		mode.addAttribute("wei_id", accVouchWei.getWei_id());
		
		mode.addAttribute("group_id", accVouchWei.getGroup_id());
		
		mode.addAttribute("hos_id", accVouchWei.getHos_id());
		
		mode.addAttribute("copy_code", accVouchWei.getCopy_code());
		
		mode.addAttribute("wei_name", accVouchWei.getWei_name());
		
		mode.addAttribute("vouch_type_code", accVouchWei.getVouch_type_code());
		
		mode.addAttribute("vouch_type_name",accVouchWei.getVouch_type_name());
		
		mode.addAttribute("vouch_no_begin",accVouchWei.getVouch_no_begin());
		
		mode.addAttribute("vouch_no_end",accVouchWei.getVouch_no_end());
		
		mode.addAttribute("note",accVouchWei.getNote());
		
		return "hrp/acc/accvouch/vouchwei/accVouchWeiUpdate";

	}
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/queryAccVouchWei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchWei(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchWeiService.queryAccVouchWei(mapVo);

		return JSONObject.parseObject(accVouch);

	}
	
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/addAccVouchWei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccVouchWei(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		mapVo.put("create_user", SessionManager.getUserId());
		
		mapVo.put("create_date", new Date());
		
		String accVouch = accVouchWeiService.addAccVouchWei(mapVo);

		return JSONObject.parseObject(accVouch);

	}
	
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/updateAccVouchWei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchWei(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String accVouch = accVouchWeiService.updateAccVouchWei(mapVo);

		return JSONObject.parseObject(accVouch);

	}
	
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/deleteAccVouchWei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchWei(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String [] ids = id.split("@");
			
            mapVo.put("wei_id", ids[0]);
            
            mapVo.put("group_id", ids[1]);
            
            mapVo.put("hos_id", ids[2]);
            
            mapVo.put("copy_code", ids[3]);
            
            mapVo.put("acc_year", ids[4]);
            
            listVo.add(mapVo);
        }
		
		String accVouch = accVouchWeiService.deleteAccVouchWei(listVo);

		return JSONObject.parseObject(accVouch);

	}
	
	/**
	 * 打印表头<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/queryAccSubjByVouchBT", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjByVouchBT(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchWeiService.queryAccSubjByVouchBT(mapVo);

		return JSONObject.parseObject(accVouch);

	}
	/**
	 * 凭证主表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accvouch/vouchwei/queryAccSubjByVouchWei", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjByVouchWei(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouch = accVouchWeiService.queryAccSubjByVouchWei(mapVo);

		return JSONObject.parseObject(accVouch);

	}
}
