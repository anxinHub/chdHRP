/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.emp;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccBank;
import com.chd.hrp.acc.serviceImpl.emp.AccBankServiceImpl;

/**
* @Title. @Description.
* 银行信息
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBankController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBankController.class);
	
	
	@Resource(name = "accBankService")
	private final AccBankServiceImpl accBankService = null;
   
    
	/**
	 * 【工资管理-基础信息-账户信息-银行信息】：主页面<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accbank/accBankMainPage", method = RequestMethod.GET)
	public String accBankMainPage(Model mode) throws Exception {
		return "hrp/acc/accbank/accBankMain";
	}
	
	/**
	*银行信息<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbank/accBankAddPage", method = RequestMethod.GET)
	public String accBankAddPage(Model mode) throws Exception {

		return "hrp/acc/accbank/accBankAdd";

	}
	/**
	*银行信息<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accbank/addAccBank", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			/*if(mapVo.get("is_auto").equals("true")){*/
				// 根据名称生成拼音码
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bank_name").toString()));
	
			// 根据名称生成五笔码
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bank_name").toString()));
			//}
		
		String accBankJson = accBankService.addAccBank(mapVo);

		return JSONObject.parseObject(accBankJson);
		
	}
	/**
	*银行信息<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accbank/queryAccBank", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("bank_name") != null){
			//用来转换大写
			mapVo.put("bank_name",mapVo.get("bank_name").toString().toUpperCase());
		}
		
        String accBank = accBankService.queryAccBank(getPage(mapVo));

		return JSONObject.parseObject(accBank);
		
	}
	/**
	*银行信息<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accbank/deleteAccBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBank(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("bank_number", ids[0]);//实际实体类变量
            
            mapVo.put("group_id", ids[1]);
            
            mapVo.put("hos_id", ids[2]);
           
            listVo.add(mapVo);
        }
		
		String accBankJson = accBankService.deleteBatchAccBank(listVo);
	   
		return JSONObject.parseObject(accBankJson);
			
	}
	
	/**
	 * 【工资管理-基础信息-账户信息-银行信息】：主页面-修改页<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accbank/accBankUpdatePage", method = RequestMethod.GET)
	public String accBankUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccBank accBank = new AccBank();
		accBank = accBankService.queryAccBankByCode(mapVo);
		mode.addAttribute("bank_number", accBank.getBank_number());
		mode.addAttribute("bank_name", accBank.getBank_name());
		mode.addAttribute("group_id", accBank.getGroup_id());
		mode.addAttribute("hos_id", accBank.getHos_id());
		mode.addAttribute("bank_account", accBank.getBank_account());
		mode.addAttribute("bank_zh", accBank.getBank_zh());
		mode.addAttribute("bank_address", accBank.getBank_address());
		mode.addAttribute("simple_name", accBank.getSimple_name());
		mode.addAttribute("phone", accBank.getPhone());
		mode.addAttribute("spell_code", accBank.getSpell_code());
		mode.addAttribute("wbx_code", accBank.getWbx_code());
		mode.addAttribute("sort_code", accBank.getSort_code());
		mode.addAttribute("note", accBank.getNote());
		mode.addAttribute("is_stop", accBank.getIs_stop());
		return "hrp/acc/accbank/accBankUpdate";
	}
	/**
	*银行信息<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accbank/updateAccBank", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBank(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			//if(mapVo.get("is_auto").equals("true")){
				// 根据名称生成拼音码
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bank_name").toString()));
		
				// 根据名称生成五笔码
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bank_name").toString()));
			//}
		
		String AccBankJson = accBankService.updateAccBank(mapVo);
		
		return JSONObject.parseObject(AccBankJson);
	}
	

}

