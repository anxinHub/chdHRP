/**
 * @Copyright: Copyright (c) 2116-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller.analysis;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.cost.service.analysis.AnalysisService;

/**
 * @Title. @Description. 构成分析请求类
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com 
 * @Version: 1.0 
 */
@Controller
public class AnalysisController extends BaseController {

	private static Logger logger = Logger.getLogger(AnalysisController.class);
	@Resource(name = "analysisService")
	private final AnalysisService analysisService = null;
	
	@RequestMapping(value="hrp/cost/analysis/costReportMainPage1",method = RequestMethod.GET)
	public String costReportMainPage1 (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/costReportMain1";
	}
	
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/queryCostDeptReport_1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReport_1(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
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
		String costReport = analysisService.queryCostDeptReport_1(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}
	
	
	@RequestMapping(value="hrp/cost/analysis/costReportMainPage",method = RequestMethod.GET)
	public String costReportMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/costReportMain";
	}
    
	
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/queryCostDeptReportThead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReportThead(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String costReport = analysisService.queryCostDeptReportThead(mapVo);

		return JSONObject.parseObject(costReport);

	}
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/queryCostDeptReport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
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
		String costReport = analysisService.queryCostDeptReport(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}
	
	@RequestMapping(value="hrp/cost/analysis/chengbenMainPage",method = RequestMethod.GET)
	public String chengbenMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/chengbenMain";
	}
	
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/querychengbenMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querychengbenMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
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
		String costReport = analysisService.querychengbenMain(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}
	
	
	@RequestMapping(value="hrp/cost/analysis/chengbenMainPage2",method = RequestMethod.GET)
	public String chengbenMainPage2 (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/chengbenMain2";
	}
	
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/querychengbenMain2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querychengbenMain2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
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
		String costReport = analysisService.querychengbenMain2(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}
	
	@RequestMapping(value="hrp/cost/analysis/chengbenMainPage3",method = RequestMethod.GET)
	public String chengbenMainPage3 (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/chengbenMain3";
	}
	
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/querychengbenMain3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querychengbenMain3(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
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
		String costReport = analysisService.querychengbenMain3(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}
	
	@RequestMapping(value="hrp/cost/analysis/costReportMainPage2",method = RequestMethod.GET)
	public String costReportMainPage2 (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/costReportMain2";
	}
    
	/**
	 * 科室成本明细数据表_医辅分摊<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/queryCostDeptReport_2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptReport_2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
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
		String costReport = analysisService.queryCostDeptReport_2(getPage(mapVo));

		return JSONObject.parseObject(costReport);

	}
	
	
	
	@RequestMapping(value="hrp/cost/analysis/c0201MainPage",method = RequestMethod.GET)
	public String c0201MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c0201Main";
	}
	
	/**
	 * 医疗构成分析总表c0203查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/queryAnalysisC0201" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0201(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0203 = analysisService.queryAnalysisC0201(getPage(mapVo));

			return JSONObject.parseObject(analysisC0203);
	}
	
	/**
	 * 医疗构成分析总表c0203页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c0203MainPage",method = RequestMethod.GET)
	public String c0203MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c0203Main";
	};
	
	/**
	 * 医疗构成分析总表c0203查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/queryAnalysisC0203" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0203(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0203 = analysisService.queryAnalysisC0203(getPage(mapVo));

			return JSONObject.parseObject(analysisC0203);
	}
	
	/**
	 * 医疗构成分析总表c0204页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c0204MainPage",method = RequestMethod.GET)
	public String c0204MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c0204Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/queryAnalysisC0204" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0204(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0204 = analysisService.queryAnalysisC0204(getPage(mapVo));

			return JSONObject.parseObject(analysisC0204);
	}
	
	/**
	 * 医疗构成分析总表c0205页面跳转
	 * */
	@RequestMapping(value="hrp/cost/analysis/c0205MainPage",method = RequestMethod.GET)
	public String c0205MainPage (Model mode)throws DataAccessException{
		return "hrp/cost/analysis/c0205Main";
	};
	
	/**
	 * 医疗构成分析总表c0204查询页面请求
	 * */
	@RequestMapping(value="hrp/cost/analysis/queryAnalysisC0205" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0205(@RequestParam Map<String, Object> mapVo, Model mode) throws DataAccessException{
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
//			//时间查询的处理
//			String month = (String) mapVo.get("acc_month1");
//			int  month1 = Integer.parseInt(month);
//			mapVo.put("acc_month1", (month1+1)+"");
			
			String analysisC0205 = analysisService.queryAnalysisC0205(getPage(mapVo));

			return JSONObject.parseObject(analysisC0205);
	}
	
	
	@RequestMapping(value="/hrp/cost/analysis/c0303MainPage", method = RequestMethod.GET)
	public String c0303MainPage(Model mode) throws Exception{
		return "hrp/cost/analysis/c0303Main";
	}
	
	/**
	*收入分类分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryAnalysisC0303", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAnalysisC0301(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		    mapVo.put("group_id", SessionManager.getGroupId());
		    
		}
		
		if(mapVo.get("hos_id") == null){
			
		    mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
            mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		
		String analysis = analysisService.queryAnalysisC0303(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	 }
	
	/**
	 *结余分析主页面<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/analysis/c0304MainPage", method = RequestMethod.GET)
	public String c0304MainPage(Model mode) throws Exception {
		
		return "hrp/cost/analysis/c0304Main";
		
	}
	
	
	/**
	 *结余分析查询<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/analysis/queryAnalysisC0304", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0304(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		
		String analysis = analysisService.queryAnalysisC0304(getPage(mapVo));
		
		return JSONObject.parseObject(analysis);
	}
	
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*维护页面跳转
	*hrp/cost/analysis/c04/c0401MainPage
	*/
	
	@RequestMapping(value = "/hrp/cost/analysis/c0401MainPage", method = RequestMethod.GET)
	public String c0401MainPage(Model mode) throws Exception {

		return "hrp/cost/analysis/c0401Main";
    }
	
		
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryAnalysisC0401", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAnalysisC0401(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("year_month_end", mapVo.get("year_month_begin"));
		
		
		String analysis = analysisService.queryAnalysisC0401(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	
	@RequestMapping(value = "/hrp/cost/costfentan/costDeptFenTanMainPage", method = RequestMethod.GET)
	public String costDeptFenTanMainPage(Model mode) throws Exception {

		return "hrp/cost/costfentan/costDeptFenTanMain";
    }
	
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costfentan/queryCostDeptCost", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostDeptCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		
		String analysis = analysisService.queryCostDeptCost(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	@RequestMapping(value = "/hrp/cost/costfentan/costFLFenTanMainPage", method = RequestMethod.GET)
	public String costFLFenTanMainPage(Model mode) throws Exception {

		return "hrp/cost/costfentan/costFLFenTanMain";
    }
	
	/**
	*科室成本信息习性分析表（变动性）<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costfentan/queryCostFLCost", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostFLCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		
		String analysis = analysisService.queryCostFLCost(getPage(mapVo));

		return JSONObject.parseObject(analysis);
	}
	
	
		/**
		*综合信息分析<BR>
		*/
	
		@RequestMapping(value = "/hrp/cost/analysis/costGeneralMainPage", method = RequestMethod.GET)
		public String costGeneralMainPage(Model mode) throws Exception {
	
			return "hrp/cost/analysis/costGeneralMain";
	    }
	
	
	/**
	*综合信息分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostGeneralHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           	
		   String analysis = analysisService.queryCostGeneralHos(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*综合信息分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostGeneralHosMed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralHosMed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           	
		   String analysis = analysisService.queryCostGeneralHosMed(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*综合信息分析明细<BR>
	*/

	@RequestMapping(value = "/hrp/cost/analysis/costGeneralDetailMainPage", method = RequestMethod.GET)
	public String costGeneralDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		
		String pathUrl = "";
		
		int gridhead = Integer.parseInt(mapVo.get("gridhead").toString());
		
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		
		mode.addAttribute("item_code", mapVo.get("item_code"));
		
		if(0==gridhead){
			
			pathUrl = "hrp/cost/analysis/costGeneralDetailHosMain";
			
		}else if(1==gridhead){
			
			pathUrl = "hrp/cost/analysis/costGeneralDetailHosMedMain";
			
		}
		
		return pathUrl;

    }
	
	/**
	*综合信息明细<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostGeneralDetailHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralDetailHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostGeneralDetailHos(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*综合信息明细<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostGeneralDetailMedHos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralDetailMedHos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           	
		   String analysis = analysisService.queryCostGeneralDetailMedHos(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*本量利分析<BR>
	*/

	@RequestMapping(value = "/hrp/cost/analysis/costVolumeProfitMainPage", method = RequestMethod.GET)
	public String costVolumeProfitMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costVolumeProfitMain";

    }
	
	/**
	*本量利分析<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostVolumeProfit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostVolumeProfit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
           mapVo.put("user_id", SessionManager.getUserId());
           
		   String analysis = analysisService.queryCostVolumeProfit(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*本量利分析<BR>
	*查询明细
	*/
	@RequestMapping(value = "/hrp/cost/analysis/costVolumeProfitDetailMainPage", method = RequestMethod.GET)
	public String costVolumeProfitDetailMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

        mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		
		mode.addAttribute("item_code", mapVo.get("item_code"));
		
		String item_name = "";
		
	       if("01".equals(mapVo.get("item_code").toString())){
	    	   
	    	   item_name = "门诊";
	    	   
	       }else if("02".equals(mapVo.get("item_code").toString())){
	    	   
	    	   item_name = "住院";
	    	   
	       }else if("03".equals(mapVo.get("item_code").toString())){
	    	   
	    	   item_name = "医技";
	       }
	       
	       mode.addAttribute("item_name", item_name);
	       
		return "hrp/cost/analysis/costVolumeProfitDetailMain";
	}
	
	/**
	*本量利分析<BR>
	*查询明细
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostVolumeProfitDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostVolumeProfitDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
           mapVo.put("user_id", SessionManager.getUserId());
           
		   String analysis = analysisService.queryCostVolumeProfitDetail(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*本量利分析<BR>
	*图表
	*/
	@RequestMapping(value = "/hrp/cost/analysis/costVolumeProfitDetailChartMainPage", method = RequestMethod.GET)
	public String costVolumeProfitDetailChartMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String begin = mapVo.get("year_month_begin").toString();
		
		String end = mapVo.get("year_month_end").toString();

		 mode.addAttribute("begin", begin.substring(0, 4) + "年" + begin.substring(4, 6)+"月");
			
		 mode.addAttribute("end", end.substring(0, 4) + "年" + end.substring(4, 6)+"月");
		 
		 mode.addAttribute("year_month_begin", begin);
		 
		 mode.addAttribute("year_month_end", end);
		 
		 mode.addAttribute("dept_code", mapVo.get("dept_code"));

		 mode.addAttribute("natur_code", mapVo.get("natur_code"));
		 
		return "hrp/cost/analysis/costVolumeProfitDetailChartMain";

    }
	
	/**
	*本量利分析<BR>
	*图表查询
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostVolumeProfitDetailChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostVolumeProfitDetailChart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
     
		   String analysis = analysisService.queryCostVolumeProfitDetailChart(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	
	/**
	*科室盈亏分析<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/costBreakevenMainPage", method = RequestMethod.GET)
	public String costBreakevenMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costBreakevenMain";

    }
	/**
	*科室盈亏分析<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostBreakeven", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakeven(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostBreakeven(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*科室盈亏明细分析<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/costBreakevenDetailMainPage", method = RequestMethod.GET)
	public String costBreakevenDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {


        mode.addAttribute("dept_id", mapVo.get("dept_id"));
		 
        mode.addAttribute("dept_no", mapVo.get("dept_no"));
		 
        mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
        
        mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
        
        mode.addAttribute("state", mapVo.get("state"));
        
		return "hrp/cost/analysis/costBreakevenDetailMain";

    }
	
	/**
	*科室盈亏分析收入查询<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostBreakevenDetailIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenDetailIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostBreakevenDetailIncome(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*科室盈亏分析成本查询<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostBreakevenDetailCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostBreakevenDetailCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostBreakevenDetailCost(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*全院科室成本环比分析表<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/costRingRatioMainPage", method = RequestMethod.GET)
	public String costRingRatioMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costRingRatioMain";

    }
	
	/**
	*全院科室成本环比分析表查询<BR>
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostRingRatio", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostRingRatio(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostRingRatio(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*全院科室成本环比分析明细表<BR>
	*
	*/
	@RequestMapping(value = "/hrp/cost/analysis/costRingRatioDetailMainPage", method = RequestMethod.GET)
	public String costRingRatioDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		
		return "hrp/cost/analysis/costRingRatioDetailMain";
	}
	
	/**
	*全院科室成本环比分析明细表查询<BR>
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostRingRatioDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostRingRatioDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostRingRatioDetail(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	/**
	*全院科室成本环比分析图形查询<BR>
	*/
	@RequestMapping(value = "/hrp/cost/analysis/queryCostRingRatioChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostRingRatioChart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostRingRatioChart((mapVo));

		  return JSONObject.parseObject(analysis);
	}
	

	
	//临床科室收入分析构成表按照收费项目查询表头
	@RequestMapping(value = "/hrp/cost/analysis/queryCostClinicalDeptIncomeItemColumns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostClinicalDeptIncomeItemColumns(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostClinicalDeptIncomeItemColumns((mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	
	  //临床科室收入分析构成表
		@RequestMapping(value = "/hrp/cost/analysis/costClinicalDeptIncomeAnalysisMainPage", method = RequestMethod.GET)
		public String costClinicalDeptIncomeAnalysisMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

			return "hrp/cost/analysis/costClinicalDeptIncomeAnalysisMain";

	    }
		
		//临床科室收入分析构成表
		@RequestMapping(value = "/hrp/cost/analysis/queryCostClinicalDeptIncomeAnalysis", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryCostClinicalDeptIncomeAnalysis(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			   mapVo.put("group_id", SessionManager.getGroupId());
				
			   mapVo.put("hos_id", SessionManager.getHosId());
			
	           mapVo.put("copy_code", SessionManager.getCopyCode());
	           
			   String analysis = analysisService.queryCostClinicalDeptIncomeAnalysis(getPage(mapVo));

			  return JSONObject.parseObject(analysis);
		}
		
		
	//临床科室收入分析构成表(开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/costClinicalDeptIncomeAnalysisApplMainPage", method = RequestMethod.GET)
	public String costClinicalDeptIncomeAnalysisApplMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costClinicalDeptIncomeAnalysisApplMain";

    }
	
	//临床科室收入分析构成表(开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/queryCostClinicalDeptIncomeAnalysisAppl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostClinicalDeptIncomeAnalysisAppl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostClinicalDeptIncomeAnalysisAppl(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	//临床科室收入分析构成表(开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/costCustomDeptIncomeAnalysisApplMainPage", method = RequestMethod.GET)
	public String costCustomDeptIncomeAnalysisApplMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costCustomDeptIncomeAnalysisApplMain";

    }
	
	//临床科室收入分析构成表(自定义开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/queryCostCustomDeptIncomeAnalysisAppl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostCustomDeptIncomeAnalysisAppl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostCustomDeptIncomeAnalysisAppl(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	//临床科室收入分析构成表(开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/costCustomDeptIncomeAnalysisExecMainPage", method = RequestMethod.GET)
	public String costCustomDeptIncomeAnalysisExecMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costCustomDeptIncomeAnalysisExecMain";

    }
	//临床科室收入分析构成表(自定义执行科室)
	@RequestMapping(value = "/hrp/cost/analysis/queryCostCustomDeptIncomeAnalysisExec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostCustomDeptIncomeAnalysisExec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostCustomDeptIncomeAnalysisExec(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}

	//临床科室收入分析构成表(执行科室)
	@RequestMapping(value = "/hrp/cost/analysis/costClinicalDeptIncomeAnalysisExecMainPage", method = RequestMethod.GET)
	public String costClinicalDeptIncomeAnalysisExecMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costClinicalDeptIncomeAnalysisExecMain";

    }
	
	//临床科室收入分析构成表(执行科室)
	@RequestMapping(value = "/hrp/cost/analysis/queryCostClinicalDeptIncomeAnalysisExec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostClinicalDeptIncomeAnalysisExec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostClinicalDeptIncomeAnalysisExec(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
	
	//科室收入分析构成表(绩效开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/costDeptIncomeAnalysisAchievementsApplMainPage", method = RequestMethod.GET)
	public String costDeptIncomeAnalysisAchievementsApplMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/cost/analysis/costDeptIncomeAnalysisAchievementsApplMain";

    }
	
	//科室收入分析构成表(绩效开单科室)
	@RequestMapping(value = "/hrp/cost/analysis/queryCostDeptIncomeAnalysisAchievementsAppl", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDeptIncomeAnalysisAchievementsAppl(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		   mapVo.put("group_id", SessionManager.getGroupId());
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
           mapVo.put("copy_code", SessionManager.getCopyCode());
           
		   String analysis = analysisService.queryCostDeptIncomeAnalysisAchievementsAppl(getPage(mapVo));

		  return JSONObject.parseObject(analysis);
	}
}
