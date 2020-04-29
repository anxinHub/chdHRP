package com.chd.hrp.pac.controller.fkht.change;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.fkht.pactinfo.PactMainFKHTService;
import com.chd.hrp.pac.service.pacsysset.PactNoRuleService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/change")
public class PactChangeMainFKHTController extends BaseController{

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;
	
	@Resource(name = "pactNoRuleService")
	private PactNoRuleService pactNoRuleService;
	
	@Resource(name = "pactMainFKHTService")
	private PactMainFKHTService pactMainFKHTService;
	
	
	@RequestMapping(value = "/pactMainChangeFKHTMainPage")
	public String toPactChangeMainFKHTPage() {
		return "hrp/pac/fkht/change/pactChangeFKHTMain";
	}

	@RequestMapping(value = "/pactMoneyChangeFKHTMainPage")
	public String toPactMoneyChangeFKHTMainPage() {
		return "hrp/pac/fkht/change/pactChangeMoneyFKHTMain";
	}
	
	/**
	 * 签订后合同变动页面跳转
	 * @return
	 */
	@RequestMapping(value = "/pactMainChangeFKHTAfterMainPage")
	public String pactMoneyChangeFKHTMainAfterPage() {
		return "hrp/pac/fkht/change/after/pactChangeFKHTAfterMain";
	}
	/**
	 * 签订后合同变动 添加页面跳转
	 * @return
	 */
	@RequestMapping(value = "/pactChangeFKHTAfterAddPage")
	public String pactChangeFKHTAfterAddPage(Model mode) {
		mode.addAttribute("pact_11002", MyConfig.getSysPara("11002"));
		return "hrp/pac/fkht/change/after/pactChangeFKHTAfterAdd";
	}
	
	/**
	 * 签订后合同变动 修改页面跳转
	 * @return
	 */
	@RequestMapping(value = "/pactChangeFKHTAfterUpdatePage")
	public String pactChangeFKHTAfterUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mode.addAttribute("pact_11002", MyConfig.getSysPara("11002"));
		
		//签订后合同变动 修改页面 信息查询
		Map<String,Object> map = pactChangeService.queryPactChangeFKHTAfterByCode(mapVo);
		if (map != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			mode.addAttribute("change_code", map.get("change_code"));
			mode.addAttribute("sup_id", map.get("sup_id"));
			mode.addAttribute("sup_no", map.get("sup_no"));
			mode.addAttribute("pact_code", map.get("pact_code"));
			mode.addAttribute("change_reason", map.get("change_reason"));
			mode.addAttribute("is_money_c", map.get("is_money_c"));
			mode.addAttribute("pay_id", map.get("pay_id"));
			mode.addAttribute("pay_type", map.get("pay_type"));
			mode.addAttribute("summary", map.get("summary"));
			mode.addAttribute("state", map.get("state"));
			mode.addAttribute("pay_cond", map.get("pay_cond"));
			mode.addAttribute("source_id", map.get("source_id"));
			mode.addAttribute("plan_money", map.get("plan_money"));
			mode.addAttribute("change_date", format.format(map.get("change_date")));
			if(map.get("pay_date")!= null){
				mode.addAttribute("pay_date", format.format(map.get("pay_date")));
			}else{
				mode.addAttribute("pay_date", null);
			}
			mode.addAttribute("value_c_code", map.get("value_c_code"));
		}
		
		return "hrp/pac/fkht/change/after/pactChangeFKHTAfterUpdate";
	}
	
	/**
	 * 签订后合同变动 修改页面跳转
	 * @return
	 */
	@RequestMapping(value = "/pactFKHTEdit")
	public String pactFKHTEdit(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		PactMainFKHTEntity entity =  pactMainFKHTService.queryPactMainFKHTByCodeCopy(mapVo);
			
		mode.addAttribute("change_code", mapVo.get("change_code"));
		//变更状态
		mode.addAttribute("change_state", mapVo.get("change_state"));
		
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
		}
			
		
		return "hrp/pac/fkht/change/after/pactFKHTEdit";
	}
	@RequestMapping(value = "/pactMainChangeFKHTPrePage")
	public String toPactMainChangeFKHTPrePage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainFKHTEntity entity = pactChangeService.queryPrePactMainFKHTByChangeCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
			mode.addAttribute("change_code", mapVo.get("change_code"));
		}
		return "hrp/pac/fkht/change/pactExecFKHTEdit";
	}

	@RequestMapping(value = "/pactMainChangeFKHTPDetailPage")
	public String toPactMainChangeFKHTPDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("change_code", mapVo.get("change_code"));
		return "hrp/pac/fkht/change/pactMainChangeFKHTPDetail";
	}

	/**
	 * 查询合同变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactMainChangeFKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询合同金额变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeMoneyFKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeMoneyFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactMainChangeMoneyFKHT(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询合同金额变更记录
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMoneyChangeFKHTDet", method = RequestMethod.POST)
	public Map<String, Object> queryPactMoneyChangeFKHTDet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			// 必填项
			mapVo.put("table_code", "PACT_MONEY_C_FKHT");
			mapVo.put("inner_table_code", "PACT_CHANGE_FKHT");
			String string = pactChangeService.queryPactMoneyChangeDet(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactPlanFKHTForPre", method = RequestMethod.POST)
	public Map<String, Object> queryPactPlanFKHTForPre(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			// 必填项
			String string = pactChangeService.queryPactPlanFKHTForPre(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
	/**
	 * 签订后合同变动 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeFKHTAfter", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeFKHTAfter(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactMainChangeFKHTAfter(getPage(mapVo));
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * 签订后合同变动 添加页面 明细数据查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactMainChangeFKHTAfterDet", method = RequestMethod.POST)
	public Map<String, Object> queryPactMainChangeFKHTAfterDet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactMainChangeFKHTAfterDet(getPage(mapVo));
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addPactChangeFKHTAfter", method = RequestMethod.POST)
	public Map<String, Object> addPactMainFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			
			// 签订后变更 添加时 根据合同编码 查询 是否存在 未确认 变更数据
			int count = pactChangeService.checkUnconfirmData(mapVo);
			if(count == 0){
				String json = pactChangeService.addPactMainChange(mapVo,"FKHT");
				return JSONObject.parseObject(json);
			}else{
				return JSONObject.parseObject("{\"error\":\"该合同存在未确认变更 数据,请确认后添加.\",\"state\":\"false\"}");
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 签订后合同变动 修改页面 明细数据查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactChangeFKHTAfterDetUpdate", method = RequestMethod.POST)
	public Map<String, Object> queryPactChangeFKHTAfterDetUpdate(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String string = pactChangeService.queryPactChangeFKHTAfterDetUpdate(getPage(mapVo));
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePactChangeFKHTAfter", method = RequestMethod.POST)
	public Map<String, Object> updatePactChangeFKHTAfter(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String json = pactChangeService.updatePactMainChange(mapVo,"FKHT");
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/submitPactChangeFKHTAfter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitPactChangeFKHTAfter(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("change_code", ids[0]);
			mapVo.put("state", ids[1]);
			
			mapVo.put("submiter", SessionManager.getUserId());
			mapVo.put("submit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			     
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = pactChangeService.updateChangeFKHTState(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 撤回
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/unSubmitPactChangeFKHTAfter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unSubmitPactChangeFKHTAfter(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("change_code", ids[0]);
			mapVo.put("state", ids[1]);
			
			mapVo.put("submiter", null);
			mapVo.put("submit_date", null);
			     
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = pactChangeService.updateChangeFKHTState(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmPactChangeFKHTAfter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPactChangeFKHTAfter(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("pact_code", ids[0]);
			mapVo.put("change_code", ids[1]);
			mapVo.put("state", ids[2]);
			
			mapVo.put("confirmer", SessionManager.getUserId());
			mapVo.put("confirm_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			     
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = pactChangeService.confirmPactChangeFKHTAfter(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 签订后变更 删除
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePactMainFKHTAfter", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactMainFKHTAfter(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id",  ids[1]);
			mapVo.put("copy_code",  ids[2]);
			mapVo.put("pact_code", ids[3]);
			mapVo.put("change_code", ids[4]);
			     
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = pactChangeService.deletePactMainFKHTAfter(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 签订后变动  备份表数据 修改
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatePactMainFKHTCopy", method = RequestMethod.POST)
	public Map<String, Object> updatePactMainFKHTCopy(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String string = pactChangeService.updatePactMainFKHTCopy(mapVo);
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
