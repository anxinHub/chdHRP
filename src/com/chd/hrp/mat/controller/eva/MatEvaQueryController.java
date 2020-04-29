
package com.chd.hrp.mat.controller.eva;

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
import com.chd.hrp.mat.service.eva.MatEvaQueryService;

@Controller
@RequestMapping(value="/hrp/mat/eva/query")
public class MatEvaQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatEvaQueryController.class);
	
	//引入Service服务
	@Resource(name = "matEvaQueryService")
	private final MatEvaQueryService matEvaQueryService = null;
	
	//供应商评价结果页面跳转
	@RequestMapping(value = "/matEvaQueryMainPage", method = RequestMethod.GET)
	public String matEvaQueryMainPage(Model mode) throws Exception {
		
		return "hrp/mat/eva/query/matEvaQueryMain";
	}
	
	//供应商评价明细数据页面跳转
	@RequestMapping(value = "/matEvaDetailQueryPage", method = RequestMethod.GET)
	public String matEvaDetailQueryPage(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_no", mapVo.get("sup_no"));
		mode.addAttribute("begin_date", mapVo.get("begin_date"));
		mode.addAttribute("end_date", mapVo.get("end_date"));
		mode.addAttribute("target_code", mapVo.get("target_code"));
		mode.addAttribute("target_name", mapVo.get("target_name"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		return "hrp/mat/eva/query/matEvaDetailQuery";
	}
	
	/**
	 * 供应商评价结果
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaReportMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaReportMain(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaQueryService.queryMatEvaReportMain(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * 供应商评价明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryMatEvaReportDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatEvaReportDetail(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaQueryService.queryMatEvaReportDetail(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * 根据指标自动加载列
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTargetCodeThead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTargetCodeThead(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("scheme_code", "sys01");
		
		String retJson = matEvaQueryService.queryTargetCodeThead(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
	
	// 指标查询
	@RequestMapping(value = "/queryMatEvaTarget", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatEvaTarget(@RequestParam Map<String, Object> mapVo, Model mode) 
			throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String retMatSelect = matEvaQueryService.queryMatEvaTarget(mapVo);
		return retMatSelect;
	}
}

