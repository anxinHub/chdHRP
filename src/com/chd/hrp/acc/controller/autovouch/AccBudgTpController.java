/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.autovouch;
import java.util.HashMap;
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
import com.chd.hrp.acc.service.autovouch.AccBudgTpService;

/**
* @Title. @Description.
* 平行记账模板
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

@Controller
public class AccBudgTpController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBudgTpController.class);
	
	
	@Resource(name = "accBudgTpService")
	private final AccBudgTpService accBudgTpService = null;
   
   
	/**
	* 主页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/accBudgTpMainPage", method = RequestMethod.GET)
	public String accBudgTpMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/accbudgtp/accBudgTpMain";

	}
	
	/**
	* 维护页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/accBudgTpDetailPage", method = RequestMethod.GET)
	public String accBudgTpAddPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/accbudgtp/accBudgTpDetail";
	}
	
	/**
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/queryAccBudgTp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBudgTp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accBudgTp = accBudgTpService.queryAccBudgTp(getPage(mapVo));

		return JSONObject.parseObject(accBudgTp);
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/saveAccBudgTp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBudgTp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = accBudgTpService.saveAccBudgTp(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
	
	

	//凭证制单-保存平行记账模板页面
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/saveAccBudgTpByVouchPage", method = RequestMethod.GET)
	public String saveAccBudgTpByVouchPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		return "hrp/acc/accvouch/superVouch/savePxTemplate";
	}
	
	//凭证制单-保存平行记账模板
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/saveAccBudgTpByVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBudgTpByVouch(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		Map<String, Object> retMap = null;
		try {
			retMap = accBudgTpService.saveAccBudgTpByVouch(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
		
	}
	
	/**
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/deleteAccBudgTp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBudgTp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = accBudgTpService.deleteAccBudgTp(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	*修改页面跳转(暂时不用)
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/accBudgTpUpdatePage", method = RequestMethod.GET)
	public String accBudgTpUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		mode.addAttribute("accBudgTp", accBudgTpService.queryMainByCode(mapVo));
		return "hrp/acc/autovouch/accbudgtp/accBudgTpDetail";
	}
	
	/**
	* 明细精准查询
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/queryDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accBudgTp = accBudgTpService.queryDetailByCode(mapVo);

		return JSONObject.parseObject(accBudgTp);
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/querySubjSelect", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return accBudgTpService.querySubjSelect(mapVo);
	}

	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/queryUserSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryUserSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return accBudgTpService.queryUserSelect(mapVo);
	}
	
	/**
	* 凭证页面-查询明细
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accbudgtp/queryDetailByCodeVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDetailByCodeVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accBudgTp = accBudgTpService.queryDetailByCodeVouch(mapVo);

		return JSONObject.parseObject(accBudgTp);
	}
	
}

