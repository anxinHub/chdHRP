package com.chd.hrp.htcg.controller.making;

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
import com.chd.hrp.htcg.entity.making.HtcgSchemeIcd10Rule;
import com.chd.hrp.htcg.service.making.HtcgSchemeIcd10RuleService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgSchemeIcd10RuleController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgSchemeIcd10RuleController.class);

	@Resource(name = "htcgSchemeIcd10RuleService")
	private final HtcgSchemeIcd10RuleService htcgSchemeIcd10RuleService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/htcgSchemeIcd10RuleMainPage", method = RequestMethod.GET)
	public String htcgSchemeIcd10RuleMainPage(Model mode) throws Exception {
		return "hrp/htcg/making/schemeicd10rule/htcgSchemeIcd10RuleMain";

	}

	
	/**
	 * 核算方案病种入组规则查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/queryHtcgSchemeDrgsRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeDrgsRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String schemeDrgs = htcgSchemeIcd10RuleService.queryHtcgSchemeDrgsRule(getPage(mapVo));

		return JSONObject.parseObject(schemeDrgs);

	}
	
	
	
	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/htcgSchemeIcd10RuleAddPage", method = RequestMethod.GET)
	public String htcgSchemeIcd10RuleAddPage(@RequestParam String drgs_code,String scheme_code,int flag,Model mode) throws Exception {
		mode.addAttribute("drgs_code",drgs_code);
		mode.addAttribute("scheme_code",scheme_code);
		mode.addAttribute("flag",flag);
		return "hrp/htcg/making/schemeicd10rule/htcgIcd10RuleAdd";

	}

	/**
	 * 核算方案病种入组规则（诊断）保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/addHtcgSchemeIcd10Rule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgSchemeIcd10Rule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String schemeIcd10RuleJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			schemeIcd10RuleJson = htcgSchemeIcd10RuleService.addHtcgSchemeIcd10Rule(mapVo);
		
		}catch(Exception e){
			
			schemeIcd10RuleJson= e.getMessage();
			
		}
		
		return JSONObject.parseObject(schemeIcd10RuleJson);

	}
	/**
	 * 核算方案病种入组规则（诊断）删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/deleteHtcgSchemeIcd10Rule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeIcd10Rule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String schemeIcd10RuleJson ;
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String [] rs =id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("drgs_code", rs[0]); 
			mapVo.put("scheme_code", rs[1]); 
			mapVo.put("icd10_code", rs[2]); 
			listVo.add(mapVo);
		}
		
		try{
			
			schemeIcd10RuleJson = htcgSchemeIcd10RuleService.deleteBatchHtcgSchemeIcd10Rule(listVo);
 
		}catch(Exception e){
			
			schemeIcd10RuleJson= e.getMessage();
			
		}
		
		return JSONObject.parseObject(schemeIcd10RuleJson);

	}
	/**
	 * 核算方案病种入组规则（诊断）查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/queryHtcgSchemeIcd10Rule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeIcd10Rule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String schemeIcd10Rule = htcgSchemeIcd10RuleService.queryHtcgSchemeIcd10Rule(getPage(mapVo));

		return JSONObject.parseObject(schemeIcd10Rule);

	}
	
	/**
	 * 核算方案病种入组规则（手术）保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/addHtcgSchemeIcd9Rule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgSchemeIcd9Rule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String schemeIcd10RuleJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			schemeIcd10RuleJson = htcgSchemeIcd10RuleService.addHtcgSchemeIcd9Rule(mapVo);

		}catch(Exception e){
			
			schemeIcd10RuleJson= e.getMessage();
			
		}
		
		return JSONObject.parseObject(schemeIcd10RuleJson);

	}

	/**
	 * 核算方案病种入组规则（手术）删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/deleteHtcgSchemeIcd9Rule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeIcd9Rule(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		String schemeIcd9RuleJson ;
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			
			String [] rs =id.split("@");
			
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("drgs_code", rs[0]); 
			mapVo.put("scheme_code", rs[1]); 
			mapVo.put("icd9_code", rs[2]); 
			listVo.add(mapVo);
		}
		
		try{
			
			schemeIcd9RuleJson = htcgSchemeIcd10RuleService.deleteBatchHtcgSchemeIcd9Rule(listVo);
 
		}catch(Exception e){
			
			schemeIcd9RuleJson= e.getMessage();
			
		}
		
		return JSONObject.parseObject(schemeIcd9RuleJson);

	}
	
	/**
	 * 核算方案病种入组规则（手术）查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/queryHtcgSchemeIcd9Rule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeIcd9Rule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String schemeIcd9Rule = htcgSchemeIcd10RuleService.queryHtcgSchemeIcd9Rule(getPage(mapVo));

		return JSONObject.parseObject(schemeIcd9Rule);

	}

	/**
	 * 核算方案病种入组规则（自定义）查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/htcg/making/schemeicd10rule/queryHtcgSchemeGeneralRule", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeGeneralRule(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String schemeGeneralRule = htcgSchemeIcd10RuleService.queryHtcgSchemeGeneralRule(getPage(mapVo));

		return JSONObject.parseObject(schemeGeneralRule);

	}

	

}
