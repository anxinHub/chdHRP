/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis.c06;

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
import com.chd.hrp.cost.service.analysis.c06.C06Service;

/**
 * @Title. @Description. 排序分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class C06Controller extends BaseController {

	@Resource(name = "c06Service")
	private final C06Service c06Service = null;
	
	/**
	*结余分析主页面<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0601MainPage", method = RequestMethod.GET)
	public String c0601MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c06/c0601Main";

	}
	
	/**
	*门诊收入排名<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0601", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0601(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("natur_code", "01");
		
		String analysis = c06Service.queryAnalysisC0601(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0602MainPage", method = RequestMethod.GET)
	public String c0602MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0602Main";
		
	}
	
	
	/**
	 *住院收入排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0602", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0602(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("natur_code", "02");
		
		String analysis = c06Service.queryAnalysisC0601(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0603MainPage", method = RequestMethod.GET)
	public String c0603MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0603Main";
		
	}
	
	
	/**
	 *临床直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0603", method = RequestMethod.POST)
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
		String analysis = c06Service.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0604MainPage", method = RequestMethod.GET)
	public String c0604MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0604Main";
		
	}
	
	/**
	 *医技直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0604", method = RequestMethod.POST)
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
		String analysis = c06Service.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0605MainPage", method = RequestMethod.GET)
	public String c0605MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0605Main";
		
	}
	
	
	/**
	 *医辅直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0605", method = RequestMethod.POST)
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
		String analysis = c06Service.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0606MainPage", method = RequestMethod.GET)
	public String c0606MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0606Main";
		
	}
	
	
	
	/**
	 *管理直接成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0606", method = RequestMethod.POST)
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
		String analysis = c06Service.queryAnalysisC0602(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0607MainPage", method = RequestMethod.GET)
	public String c0607MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0607Main";
		
	}
	
	
	/**
	 *临床医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0607", method = RequestMethod.POST)
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
		
		String analysis = c06Service.queryAnalysisC0603(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0608MainPage", method = RequestMethod.GET)
	public String c0608MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0608Main";
		
	}
	
	/**
	 *医技医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0608", method = RequestMethod.POST)
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
		
		String analysis = c06Service.queryAnalysisC0603(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0609MainPage", method = RequestMethod.GET)
	public String c0609MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0609Main";
		
	}
	
	/**
	 *医辅医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0609", method = RequestMethod.POST)
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
		
		String analysis = c06Service.queryAnalysisC0603(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0610MainPage", method = RequestMethod.GET)
	public String c0610MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0610Main";
		
	}
	/**
	 临床医疗成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0610", method = RequestMethod.POST)
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
		
		String analysis = c06Service.queryAnalysisC0604(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0611MainPage", method = RequestMethod.GET)
	public String c0611MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0611Main";
		
	}
	/**
	 *医技医疗成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0611", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0611(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0604(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0612MainPage", method = RequestMethod.GET)
	public String c0612MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0612Main";
		
	}
	/**
	 *医辅医疗成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0612", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0612(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0604(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0613MainPage", method = RequestMethod.GET)
	public String c0613MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0613Main";
		
	}
	/**
	 *管理医疗成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0613", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0613(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0604(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0614MainPage", method = RequestMethod.GET)
	public String c0614MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0614Main";
		
	}
	/**
	 *临床医疗全成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0614", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0614(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0605(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0615MainPage", method = RequestMethod.GET)
	public String c0615MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0615Main";
		
	}
	/**
	 *医技医疗全成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0615", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0615(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0605(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0616MainPage", method = RequestMethod.GET)
	public String c0616MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0616Main";
		
	}
	/**
	 *医辅医疗全成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0616", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0616(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0605(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0617MainPage", method = RequestMethod.GET)
	public String c0617MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0617Main";
		
	}
	/**
	 *管理医疗全成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0617", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0617(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0605(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0618MainPage", method = RequestMethod.GET)
	public String c0618MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0618Main";
		
	}
	/**
	 *临床医院成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0618", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0618(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0606(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0619MainPage", method = RequestMethod.GET)
	public String c0619MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0619Main";
		
	}
	/**
	 *医技医院成本<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0619", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0619(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0606(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0620MainPage", method = RequestMethod.GET)
	public String c0620MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0620Main";
		
	}
	/**
	 *医辅医院成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0620", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0620(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0606(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0621MainPage", method = RequestMethod.GET)
	public String c0621MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0621Main";
		
	}
	/**
	 *管理医院成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0621", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0621(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String analysis = c06Service.queryAnalysisC0606(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0622MainPage", method = RequestMethod.GET)
	public String c0622MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0622Main";
		
	}
	/**
	 *临床受益排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0622", method = RequestMethod.POST)
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
		String analysis = c06Service.queryAnalysisC0611(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0623MainPage", method = RequestMethod.GET)
	public String c0623MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0623Main";
		
	}
	/**
	 *医技受益排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0623", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0623(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		mapVo.put("type_code", "02");
		
		String analysis = c06Service.queryAnalysisC0612(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0624MainPage", method = RequestMethod.GET)
	public String c0624MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0624Main";
		
	}
	/**
	 *集团医疗业务成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0624", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0624(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String analysis = c06Service.queryAnalysisC0607(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0625MainPage", method = RequestMethod.GET)
	public String c0625MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0625Main";
		
	}
	/**
	 *集团医疗成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0625", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0625(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String analysis = c06Service.queryAnalysisC0608(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0626MainPage", method = RequestMethod.GET)
	public String c0626MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0626Main";
		
	}
	/**
	 *集团医疗全成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0626", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0626(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String year_month_begin=mapVo.get("year_month_begin").toString();
		mapVo.put("acc_year_begin", year_month_begin.substring(0, 4));
		mapVo.put("acc_month_begin", year_month_begin.substring(4));
//		String year_month_end=mapVo.get("year_month_end").toString();
//		mapVo.put("acc_year_end", year_month_end.substring(0, 4));
//		mapVo.put("acc_month_end", year_month_end.substring(4));
		
		String analysis = c06Service.queryAnalysisC0609(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c06/c0627MainPage", method = RequestMethod.GET)
	public String c0627MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c06/c0627Main";
		
	}
	/**
	 *集团医院成本排名<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/c06/queryAnalysisC0627", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0627(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String year_month_begin=mapVo.get("year_month_begin").toString();
		mapVo.put("acc_year_begin", year_month_begin.substring(0, 4));
		mapVo.put("acc_month_begin", year_month_begin.substring(4));
//		String year_month_end=mapVo.get("year_month_end").toString();
//		mapVo.put("acc_year_end", year_month_end.substring(0, 4));
//		mapVo.put("acc_month_end", year_month_end.substring(4));
		
		String analysis = c06Service.queryAnalysisC0610(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
}
