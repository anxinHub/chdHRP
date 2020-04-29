/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.service.tell.AccTellVeriService;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCashierCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCashierCheckController.class);
   
	
	@Resource(name = "accTellVeriService")
	private final AccTellVeriService accTellVeriService = null;
    
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acctell/accCashiercheckMainPage", method = RequestMethod.GET)
	public String accCashiercheckMainPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCashiercheckMain";
	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accCashiercheckAddPage", method = RequestMethod.GET)
	public String accCashiercheckAddPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCashiercheckAdd";
	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accCashiercheckInstallPage", method = RequestMethod.GET)
	public String accCashiercheckInstallPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accCashiercheckInstall";
	}
	
	/**
	 * 批量取消页面跳转
	 * @param mode
	 * @param subj_code
	 * @param subj_name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acctell/accUnitCancelTellCheckPage", method = RequestMethod.GET)
	public String accUnitCancelTellCheckPage(Model mode,String subj_code,String subj_name) throws Exception {
		mode.addAttribute("subj_code", subj_code);
		mode.addAttribute("subj_name", subj_name);
		return "hrp/acc/acctell/accUnitCancelTellCheck";
	}
	
	@RequestMapping(value = "/hrp/acc/acctell/accSaveAccTellVeriPage", method = RequestMethod.GET)
	public String accSaveAccTellVeriPage(Model mode) throws Exception {
		return "hrp/acc/acctell/accSaveAccTellVeri";
	}
	
	@RequestMapping(value = "/hrp/acc/acctell/accAccTellVeriInfoPage", method = RequestMethod.GET)
	public String accAccTellVeriInfoPage(Model mode) throws Exception {
		return "hrp/acc/acctell/accAccTellVeriInfo";
	}
	
	
	@RequestMapping(value = "/hrp/acc/acctell/saveAccTellVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccTellVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accLederJson;
		try {
			accLederJson = accTellVeriService.addAccTellVeri(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	/**
	 * 取消对账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acctell/cancelAccTellVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelAccTellVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accLederJson;
		try {
			accLederJson = accTellVeriService.deleteAccTellVeri(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	/**
	 * 批量取消
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acctell/batchCancelAccTellVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchCancelAccTellVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		mapVo.put("create_user", SessionManager.getUserId());
		
		mapVo.put("create_date", new Date());
		
		String accLederJson;
		try {
			accLederJson = accTellVeriService.deleteAccTellVeriBySubjAndDate(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	/**
	 * 出纳批量对账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/acctell/saveBatchAccTellVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchAccTellVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		
		String accLederJson;
		try {
			accLederJson = accTellVeriService.addBatchAccTellVeri(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	//会计账查询
	@RequestMapping(value = "/hrp/acc/acctell/queryAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouchCheck = accTellVeriService.queryAccVouchCheck(getPage(mapVo));

		return JSONObject.parseObject(accVouchCheck);
		
	}
	
	@RequestMapping(value = "/hrp/acc/acctell/queryAccTellDatas", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTellDatas(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accVouchCheck = accTellVeriService.queryAccTellDatas(getPage(mapVo));

		return JSONObject.parseObject(accVouchCheck);
		
	}
}

