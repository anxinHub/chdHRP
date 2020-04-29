/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.tell;
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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccTell;
import com.chd.hrp.acc.serviceImpl.tell.AccTellServiceImpl;

/**
* @Title. @Description.
* 出纳账
* @Author: LiuYingDuo
* @email: bell@s-chd.com     
* @Version: 1.0
*/


@Controller
public class AccTellController extends BaseController{
	private static Logger logger = Logger.getLogger(AccTellController.class);
	
	
	@Resource(name = "accTellService")
	private final AccTellServiceImpl accTellService = null;
   
    
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acctell/accTellMainPage", method = RequestMethod.GET)
	public String accTellMainPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accTellMain";

	}
	/**
	*出纳账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accTellAddPage", method = RequestMethod.GET)
	public String accTellAddPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accTellAdd";

	}
	
	/**
	*出纳账<BR>
	*初始余额页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctell/accTellInstallPage", method = RequestMethod.GET)
	public String accTellInstallPage(Model mode) throws Exception {

		return "hrp/acc/acctell/accTellInstall";

	}
	
	/**
	*出纳账<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acctell/addAccTell", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccTell(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accTellJson = accTellService.addAccTell(mapVo);

		return JSONObject.parseObject(accTellJson);
		
	}
	/**
	*出纳账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acctell/queryAccTell", method = RequestMethod.POST)
	@ResponseBody  
	
	public Map<String, Object> queryAccTell(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String accTell = accTellService.queryAccTell(getPage(mapVo));
		return JSONObject.parseObject(accTell);
		
	}
	/**
	*出纳账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctell/deleteAccTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccTell(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accTellJson = accTellService.deleteBatchAccTell(listVo);
	   return JSONObject.parseObject(accTellJson);
			
	}
	
	/**
	*出纳账<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctell/accTellUpdatePage", method = RequestMethod.GET)
	
	public String accTellUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccTell accTell = new AccTell();
		accTell = accTellService.queryAccTellByCode(mapVo);
		mode.addAttribute("tell_id", accTell.getTell_id());
		mode.addAttribute("group_id", accTell.getGroup_id());
		mode.addAttribute("hos_id", accTell.getHos_id());
		mode.addAttribute("copy_code", accTell.getCopy_code());
		mode.addAttribute("acc_year", accTell.getAcc_year());
		mode.addAttribute("cash_subj_code", accTell.getCash_subj_code());
		mode.addAttribute("other_subj_id", accTell.getOther_subj_id());
		mode.addAttribute("summary", accTell.getSummary());
		mode.addAttribute("att_num", accTell.getAtt_num());
		mode.addAttribute("debit", accTell.getDebit());
		mode.addAttribute("credit", accTell.getCredit());
		mode.addAttribute("cur_code", accTell.getCur_code());
		mode.addAttribute("debit_w", accTell.getDebit_w());
		mode.addAttribute("credit_w", accTell.getCredit_w());
		mode.addAttribute("bal_w", accTell.getBal_w());
		mode.addAttribute("check_no", accTell.getCheck_no());
		mode.addAttribute("busi_type", accTell.getBusi_type());
		mode.addAttribute("business_no", accTell.getBusiness_no());
		mode.addAttribute("occur_date", accTell.getOccur_date());
		mode.addAttribute("pay_type_code", accTell.getPay_type_code());
		mode.addAttribute("create_user", accTell.getCreate_user());
		mode.addAttribute("create_date", accTell.getCreate_date());
		mode.addAttribute("con_user", accTell.getCon_user());
		mode.addAttribute("con_date", accTell.getCon_date());
		mode.addAttribute("is_check", accTell.getIs_check());
		mode.addAttribute("check_user", accTell.getCheck_user());
		mode.addAttribute("check_date", accTell.getCheck_date());
		mode.addAttribute("is_init", accTell.getIs_init());
		mode.addAttribute("vouch_check_id", accTell.getVouch_check_id());
		mode.addAttribute("vouch_id", accTell.getVouch_id());
		mode.addAttribute("is_import", accTell.getIs_import());
		return "hrp/acc/acctell/accTellUpdate";
	}
	/**
	*出纳账<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acctell/updateAccTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccTell(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	 
	   
		String accTellJson = accTellService.updateAccTell(mapVo);
		
		return JSONObject.parseObject(accTellJson);
	}
	/**
	*出纳账<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acctell/importAccTell", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccTell(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accTellJson = accTellService.importAccTell(mapVo);
		
		return JSONObject.parseObject(accTellJson);
	}

}

