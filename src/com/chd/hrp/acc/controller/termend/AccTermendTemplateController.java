package com.chd.hrp.acc.controller.termend;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.termend.AccTermendTemplateService;

/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccTermendTemplateController extends BaseController {
	private static Logger logger = Logger.getLogger(AccTermendTemplateController.class);
	
	@Resource(name = "accTermendTemplateService")
	private final AccTermendTemplateService accTermendTemplateService = null;
	
	/**
	 * 科目设置<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/accTermendTemplateSubjPage", method = RequestMethod.GET)
	public String accTermendTemplateSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		mode.addAttribute("subjParms", mapVo);
		return "hrp/acc/termend/accTermendTemplateSubj";
	}
	
	/**
	 * 凭证维护<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/accTermendTemplateVouchPage", method = RequestMethod.GET)
	public String accTermendTemplateVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("template_type_code", mapVo.get("template_type_code"));
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		return "hrp/acc/termend/accTermendTemplateVouch";
	}
	
	/**
	 * 期末处理<BR>
	 * 凭证查询
	 */
	@RequestMapping(value = "hrp/acc/termend/queryAccTermendVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTermendVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplateVouch(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 期末处理<BR>
	 * 模板查询
	 */
	@RequestMapping(value = "hrp/acc/termend/queryAccTermendTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTermendTemplateCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String accTemplateData = accTermendTemplateService.queryAccTermendTemplate(getPage(mapVo));

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 期末处理<BR>
	 * 模板保存
	 */
	@RequestMapping(value = "hrp/acc/termend/saveAccTermendTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccTermendTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		if (mapVo.get("rate") == null || "".equals(mapVo.get("rate"))) {
			mapVo.put("rate", "0");
		}
		String msg = "";
		try {
			if (mapVo.get("template_id") == null || "".equals(mapVo.get("template_id"))) {
				msg = accTermendTemplateService.addAccTermendTemplate(mapVo);
			}else{
				msg = accTermendTemplateService.updateAccTermendTemplate(mapVo);
			}
		} catch (Exception e) {
			
			msg = e.getMessage();
		}
	
		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 期末处理<BR>
	 * 模板删除
	 */
	@RequestMapping(value = "hrp/acc/termend/deleteAccTermendTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccTermendTemplate(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("template_id", id);// 实际实体类变量
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			listVo.add(mapVo);
		}
		String msg = "";
		try {
			msg = accTermendTemplateService.deleteBatchAccTermendTemplate(listVo);
		} catch (Exception e) {
			msg = e.getMessage();
		}

		return JSONObject.parseObject(msg);
	}
	
	/**
	 * 期末处理<BR>
	 * 模板明细查询
	 */
	@RequestMapping(value = "hrp/acc/termend/queryAccTermendTemplateDetail", method = RequestMethod.POST)
	@ResponseBody 
	public Map<String, Object> queryAccTermendTemplateDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String accDetailData = accTermendTemplateService.queryAccTermendTemplateDetail(mapVo);

		return JSONObject.parseObject(accDetailData);
	}
	
	/**
	 * 期末处理<BR>
	 * 科目查询
	 */
	@RequestMapping(value = "hrp/acc/termend/queryAccTermendTemplateSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTermendTemplateSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String accSubj = accTermendTemplateService.queryAccTermendTemplateSubj(getPage(mapVo));

		return JSONObject.parseObject(accSubj);
	}
	
	/**
	 * 期末处理<BR>
	 * 凭证维护
	 */
	@RequestMapping(value = "hrp/acc/termend/queryAccTermendTemplateVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccTermendTemplateVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		//格式化日期
		if (mapVo.get("create_date_b") != null && !"".equals(mapVo.get("create_date_b").toString())) {
			mapVo.put("create_date_b", DateUtil.stringToDate(mapVo.get("create_date_b").toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("create_date_e") != null && !"".equals(mapVo.get("create_date_e").toString())) {
			mapVo.put("create_date_e", DateUtil.stringToDate(mapVo.get("create_date_e").toString(), "yyyy-MM-dd"));
		}
		
		String accSubj = accTermendTemplateService.queryAccTermendTemplateVouch(getPage(mapVo));

		return JSONObject.parseObject(accSubj);
	}
	
	/**
	 * @Description 删除数据 模板明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/acc/termend/monthend/incomcost/deleteAccTermendTemplateDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccTermendTemplateDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		String resuleJson = null;
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("template_id", ids[3]);
			
			mapVo.put("template_detail_id", ids[4]);
		 
			listVo.add(mapVo);

		}
 
		try {
		 
			resuleJson = accTermendTemplateService.deleteAccTermendTemplateDetail(listVo);
			  
			return JSONObject.parseObject(resuleJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		 
	}
	
}
