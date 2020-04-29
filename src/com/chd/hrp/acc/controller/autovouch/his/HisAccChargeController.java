/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.controller.autovouch.his;
 
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
import com.chd.hrp.acc.service.autovouch.his.HisAccChargeService;
import com.chd.hrp.acc.service.autovouch.his.HisAccPreService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;

/**
 * @Title. @Description. 出纳账
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HisAccChargeController extends BaseController {
	private static Logger logger = Logger.getLogger(HisAccChargeController.class);

	@Resource(name = "hisAccChargeService")
	private final HisAccChargeService hisAccChargeService = null;
	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;
	@Resource(name = "hisAccPreService")
	private final HisAccPreService hisAccPreService = null;
	
	/*************************************************门诊收费*************************************************/
	//门诊收费主页面
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/hisChargeOMainPage", method = RequestMethod.GET)
	public String hisChargeOMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//查询最大凭证日期
		String p029="0";
		if(MyConfig.getSysPara("029")!=null){
			p029=MyConfig.getSysPara("029").toString();
		}
		mapVo.put("p029", p029);
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		
		return "hrp/acc/autovouch/his/charge/charge_o";
	}

	//查询凭证模板
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryAutoBusiTemplate", method = RequestMethod.POST)
	@ResponseBody
	public String queryAutoBusiTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String resJson=hisAccChargeService.queryAutoBusiTemplate(mapVo);
		return resJson;
		
	}
	
	//查询门诊收费类别-表头
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisChargeHeadO", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisChargeHeadO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisChargeHeadO(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	//查询门诊收费数据
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisChargeDataO", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisChargeDataO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("column_name")==null || mapVo.get("column_name").equals("")){
			return JSONObject.parseObject("{\"error\":\"表头为空！\"}");
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisChargeDataO(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}

	
	//查询门诊结算数据 
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisBalO", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisBalO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisBalO(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	//查询门诊收费结算凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisChargeVouchO", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisChargeVouchO(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "01");
		mapVo.put("busi_type_code", "0102");
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.saveHisChargeVouchO(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	
	/*************************************************住院收费*************************************************/
	
	//住院收费主页面
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/hisChargeIMainPage", method = RequestMethod.GET)
	public String hisChargeIMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//查询最大凭证日期
		String p029="0";
		if(MyConfig.getSysPara("029")!=null){
			p029=MyConfig.getSysPara("029").toString();
		}
		mapVo.put("p029", p029);
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
	
		mapVo.put("his_table", "acc_charge_i");
		int type=hisAccPreService.queryHisAccType(mapVo);
		mode.addAttribute("type", type);
		
		return "hrp/acc/autovouch/his/charge/charge_i";
	}

	//查询住院收费类别-表头
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisChargeHeadI", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisChargeHeadI(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisChargeHeadI(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}

	
	//查询住院收费数据
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisChargeDataI", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisChargeDataI(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("column_name")==null || mapVo.get("column_name").equals("")){
			return JSONObject.parseObject("{\"error\":\"表头为空！\"}");
		}
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisChargeDataI(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	
	//查询住院收费凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisChargeVouchI", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisChargeVouchI(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "01");
		mapVo.put("busi_type_code", "0103");
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisChargeVouchI(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	//查询住院收费凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryVouchNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryVouchNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "01");
		mapVo.put("busi_type_code", "0103");
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryVouchNo(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	
	/*************************************************住院结算*************************************************/
	
	
	//住院结算主页面
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/hisBalIMainPage", method = RequestMethod.GET)
	public String hisBalIMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//查询最大凭证日期
		String p029="0";
		if(MyConfig.getSysPara("029")!=null){
			p029=MyConfig.getSysPara("029");
		}
		mapVo.put("p029", p029);
		String reMaxVouchDate=superVouchService.queryMaxVouchDate(mapVo);
		mode.addAttribute("vouch_date", reMaxVouchDate);
		
		mapVo.put("his_table", "acc_charge_i");
		int type=hisAccPreService.queryHisAccType(mapVo);
		mode.addAttribute("type", type);
		
		return "hrp/acc/autovouch/his/charge/bal_i";
	}
	
	
	//查询住院结算数据 
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisBalI", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisBalI(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisBalI(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}
	
	
	//查询住院结算凭证json
	@RequestMapping(value = "/hrp/acc/autovouch/his/charge/queryHisBalVouchI", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHisBalVouchI(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("mod_code", "01");
		mapVo.put("busi_type_code", "0104");
		
		String reJson=null;
		try {
			reJson=hisAccChargeService.queryHisBalVouchI(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);

	}

}
