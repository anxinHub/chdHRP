/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.util.Date;
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
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.CostControlService;
import com.chd.hrp.cost.service.analysis.c06.C06Service;

/**
 * @Title. @Description. 排序分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class CostControlController extends BaseController {

	@Resource(name = "costControlService")
	private final CostControlService costControlService = null;
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0603MainPage", method = RequestMethod.GET)
	public String c0603MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0603Main";
		
	}
	
	
	/**
	 *临床直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0603", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0603(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "01");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0604MainPage", method = RequestMethod.GET)
	public String c0604MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0604Main";
		
	}
	
	/**
	 *医技直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0604", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0604(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "02");
		// 默认性质为自筹资金
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				sb.append("1");
				if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
					sb.append(",2");
				}
				if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
					sb.append(",3");
				}
				if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
					sb.append(",4");
				}
				sb.append(")");
				mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0605MainPage", method = RequestMethod.GET)
	public String c0605MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0605Main";
		
	}
	
	
	/**
	 *医辅直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0605", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0605(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "03");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0606MainPage", method = RequestMethod.GET)
	public String c0606MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0606Main";
		
	}
	
	
	
	/**
	 *管理直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0606", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0606(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "04");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0607MainPage", method = RequestMethod.GET)
	public String c0607MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0607Main";
		
	}
	
	
	/**
	 *临床医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0607", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0607(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "01");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0603(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0608MainPage", method = RequestMethod.GET)
	public String c0608MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0608Main";
		
	}
	
	/**
	 *医技医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0608", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0608(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "02");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0603(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0609MainPage", method = RequestMethod.GET)
	public String c0609MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0609Main";
		
	}
	
	/**
	 *医辅医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0609", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0609(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "03");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0603(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0610MainPage", method = RequestMethod.GET)
	public String c0610MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0610Main";
		
	}
	/**
	 临床医疗成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0610", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0610(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "01");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0604(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0622MainPage", method = RequestMethod.GET)
	public String c0622MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0622Main";
		
	}
	/**
	 *临床受益排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0622", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0622(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("type_code", "01");
		// 默认性质为自筹资金
				StringBuilder sb = new StringBuilder();
				sb.append("(");
				sb.append("1");
				if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
					sb.append(",2");
				}
				if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
					sb.append(",3");
				}
				if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
					sb.append(",4");
				}
				sb.append(")");
				mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0611(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costcontrol/c0623MainPage", method = RequestMethod.GET)
	public String c0623MainPage(Model mode) throws Exception {
		
		return "hrp/cost/costcontrol/c0623Main";
		
	}
	/**
	 *医技受益排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costcontrol/queryAnalysisC0623", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0623(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("type_code", "02");
		// 默认性质为自筹资金
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append("1");
		if (mapVo.get("source_attr_2") != null && mapVo.get("source_attr_2").equals("1")) {
			sb.append(",2");
		}
		if (mapVo.get("source_attr_3") != null && mapVo.get("source_attr_3").equals("1")) {
			sb.append(",3");
		}
		if (mapVo.get("source_attr_4") != null && mapVo.get("source_attr_4").equals("1")) {
			sb.append(",4");
		}
		sb.append(")");
		mapVo.put("source_attr", sb.toString());
		String analysis = costControlService.queryAnalysisC0612(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	
}
