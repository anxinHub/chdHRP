/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.accBankVeri;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.acc.service.tell.AccBankVeriService;
import com.chd.hrp.acc.serviceImpl.accBankVeri.AccUnitBankCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankAccountServiceImpl;
import com.chd.hrp.acc.serviceImpl.tell.AccBankCheckServiceImpl;
 
/** 
* @Title. @Description.
* 单位银行账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
@Controller
public class AccUnitBankCheckController extends BaseController{
	private static Logger logger = Logger.getLogger(AccUnitBankCheckController.class);
   
	@Resource(name = "accBankVeriService")
	private final AccBankVeriService accBankVeriService = null;
	
	@Resource(name = "accParaService")
	private final AccParaService accParaService = null;
	
	@Resource(name = "accBankCheckService")
	private final AccBankCheckServiceImpl accBankCheckService = null;
	
	@Resource(name = "accBankAccountService")
	private final AccBankAccountServiceImpl accBankAccountService = null;
    
	
	@Resource(name = "accUnitBankCheckService")
	private final AccUnitBankCheckServiceImpl accUnitBankCheckService = null;
	/**
	 * 单位银行对账页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitBankCheckMainPage", method = RequestMethod.GET)
	public String accUnitBankCheckMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("a018", MyConfig.getSysPara("018"));
		return "hrp/acc/accbanktell/accUnitBankCheckMain";
	}
	
	/**
	 * 单位银行对账  单位账查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/queryAccTellOrVouchData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTellOrVouchData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String accBankCheck = accUnitBankCheckService.queryAccTellOrVouchData(getPage(mapVo));
		return JSONObject.parseObject(accBankCheck);
	}
	
	/**
	 * 单位银行对账--银行账查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/queryAccBankCheckData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBankCheckData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){	
		   mapVo.put("group_id", SessionManager.getGroupId());
		}
		 
		if(mapVo.get("hos_id") == null){	
		   mapVo.put("hos_id", SessionManager.getHosId());
		}
		 
		if(mapVo.get("copy_code") == null){			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String accBankAccount = accUnitBankCheckService.queryAccBankCheckData(getPage(mapVo));
		return JSONObject.parseObject(accBankAccount);
		
	}
	
	
	/**
	 * 单位银行对账--对账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/saveAccBankVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBankVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
			mapVo.put("acc_year",  SessionManager.getAcctYear());
		}
		
		String accLederJson;
		try {
			accLederJson = accUnitBankCheckService.addAccBankVeriMain(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
		//String accVouchCheckJson = accUnitBankCheckService.addAccBankVeriMain(entityList,logMap);
		//return JSONObject.parseObject(accVouchCheckJson);	
		
	}
	
	/**
	 * 单位银行对账--取消对账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/cancelAccBankVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelAccBankVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
			mapVo.put("acc_year",  SessionManager.getAcctYear());
		}
		
		String accLederJson;
		try {
			accLederJson = accUnitBankCheckService.deleteAccBankVeriIsChecked(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	/**
	 * 批量对账页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitBankBatchVeriPage", method = RequestMethod.GET)
	public String accSaveAccTellVeriPage(Model mode) throws Exception {
		return "hrp/acc/accbanktell/accUnitBankBatchVeri";
	}
	
	/**
	 * 批量对账
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/saveBatchAccBankVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBatchAccBankVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("acc_year",  SessionManager.getAcctYear());
		}
		
		String accLederJson;
		try {
			accLederJson = accUnitBankCheckService.addBatchAccUnitBankVeri(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);
		
	}
	
	/**
	 * 单位银行对账--批量删除
	 * @param mode
	 * @param subj_id
	 * @param subj_name
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitCancelBankCheckPage", method = RequestMethod.GET)
	public String accUnitCancelBankCheckPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		mode.addAttribute("user_id", SessionManager.getUserId());
		mode.addAttribute("user_name", SessionManager.getUserName());
		return "hrp/acc/accbanktell/accUnitCancelBankCheck";
	}
	
	/**
	 * 批量取消 确认按钮
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/batchCancelAccBankVeri", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> batchCancelAccBankVeri(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        mapVo.put("user_id", SessionManager.getUserId());
		String accLederJson;
		try {
			accLederJson = accUnitBankCheckService.deleteBatchAccBankVeri(mapVo);
		} catch (Exception e) {
			accLederJson = e.getMessage();
		}
		return JSONObject.parseObject(accLederJson);

	}
	
	/**
	 * 单位银行对账--查看对账信息页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/accAccBankVeriInfoPage", method = RequestMethod.GET)
	public String accAccBankVeriInfoPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        mode.addAttribute("veri_check_id", mapVo.get("veri_check_id"));
		mode.addAttribute("direct", mapVo.get("direct"));
		mode.addAttribute("objDirect", mapVo.get("objDirect"));
		
		mode.addAttribute("a018", MyConfig.getSysPara("018"));
		
		return "hrp/acc/accbanktell/accAccBankVeriInfo";
	}
	/**
	 * 单位银行对账 查看单位账明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/queryAccBankVeriDetail", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccBankVeriDetailD(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = "";
        
		if(mapVo.get("direct").equals("L")){
			result = accUnitBankCheckService.queryAccBankVerDetailD(getPage(mapVo));
		}else{
			result = accUnitBankCheckService.queryAccBankVerDetailB(getPage(mapVo));
		}
		
		return JSONObject.parseObject(result);
	}
	
	
	/**
	 * 出纳账<BR>
	 * 维护页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accbanktell/accUnitBankCheckAddPage", method = RequestMethod.GET)
	public String accUnitBankCheckAddPage(Model mode) throws Exception {

		return "hrp/acc/accbanktell/accUnitBankCheckAdd";
	}
	
	@RequestMapping(value = "/hrp/acc/accbanktell/accSaveAccBankVeriPage", method = RequestMethod.GET)
	public String accSaveAccBankVeriPage(Model mode) throws Exception {
		return "hrp/acc/accbanktell/accSaveAccBankVeri";
	}
	
	
}

