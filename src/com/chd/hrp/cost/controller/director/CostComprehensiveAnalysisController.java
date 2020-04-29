package com.chd.hrp.cost.controller.director;

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
import com.chd.hrp.cost.service.director.CostComprehensiveAnalysisService;

/**
 * 综合分析
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value="/hrp/cost/director/comprehensiveanalysis")
public class CostComprehensiveAnalysisController extends BaseController{

	private static Logger logger = Logger.getLogger(CostComprehensiveAnalysisController.class);
	
	@Resource(name = "costComprehensiveAnalysisService")
	private final CostComprehensiveAnalysisService costComprehensiveAnalysisService = null;
	
	/**
	 * 
	* @Title: costDepartmentOperationMainPage
	* @Description: 科室经营状况
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDepartmentOperationMainPage",method = RequestMethod.GET)
	public String costDepartmentOperationMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/comprehensiveanalysis/departmentoperation/costDepartmentOperationMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessage
	* @Description: 科室经营状况查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDepartmentOperation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDepartmentOperation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostDepartmentOperation(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	
	 /**
	   * @Title: costGeneralMessageMainPage
	   * @Description: 综合信息分析跳转页面
	   * @param @param mode
	   * @param @return
	   * @param @throws DataAccessException
	   * @return String    
	   * @date 2019年10月24日 上午10:21:39
	   * @author sjy
    */
	@RequestMapping(value="/costGeneralMessageMainPage",method = RequestMethod.GET)
	public String costGeneralMessageMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageMain";
	}
 /**
	* 
	* @Title: queryCostGeneralMessage
	* @Description: 综合信息分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessage(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	   * @Title: costGeneralMessageDetailMainPage
	   * @Description: 综合信息分析查询明细-页面跳转
	   * @param @param mode
	   * @param @return
	   * @param @throws DataAccessException
	   * @return String    
	   * @date 2019年10月24日 上午10:21:39
	   * @author sjy
  */
	@RequestMapping(value="/costGeneralMessageDetailMainPage",method = RequestMethod.GET)
	public String costGeneralMessageDetailMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("natur_code", mapVo.get("natur_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageDetailMain";
	}
	
	/**
	   * @Title: queryCostGeneralMessageDetail
	   * @Description: 综合信息分析查询明细
	   * @param @param mode
	   * @param @return
	   * @param @throws DataAccessException
	   * @return String    
	   * @date 2019年10月24日 上午10:21:39
	   * @author sjy
    */
	@RequestMapping(value="/queryCostGeneralMessageDetail",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageDetail (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageDetail(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageMed
	* @Description: 综合信息分析查询(药品)
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageMed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageMed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageMed(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	 * 
	* @Title: costGeneralMessageMedDetailMainPage
	* @Description: 综合信息分析查询明细页面跳转(药品)
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */
	@RequestMapping(value="/costGeneralMessageMedDetailMainPage",method = RequestMethod.GET)
	public String costGeneralMessageMedDetailMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("natur_code", mapVo.get("natur_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageMedDetailMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageMedDetail
	* @Description: 综合信息分析查询明细(药品)
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageMedDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageMedDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageMedDetail(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageMedDetail
	* @Description: 综合信息分析查询明细(药品) -保本工作量
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */
	@RequestMapping(value="/costGeneralMessageChartMainPage",method = RequestMethod.GET)
	public String costGeneralMessageChartMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("item_code", mapVo.get("item_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageChartMain";
	}
	
	
	/**
	 * 
	* @Title: queryCostGeneralMessage_t1
	* @Description: 综合信息分析查询-图表一
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月25日 上午10:57:44
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessage_t1", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessage_t1(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessage_t1(mapVo);
		return JSONObject.parseObject(analysisJson);

	}
	
	
	/**
	 * 
	* @Title: costGeneralMessage_t1DetailMainPage
	* @Description:  综合信息分析查询-图表一明细页面跳转
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessage_t1DetailMainPage",method = RequestMethod.GET)
	public String costGeneralMessage_t1DetailMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessage_t1DetailMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessage_t2
	* @Description: 综合信息分析查询-图表二
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月25日 上午10:57:44
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessage_t2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessage_t2(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessage_t2(mapVo);
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	 * 
	* @Title: costGeneralMessage_t2DetailMainPage
	* @Description: 综合信息分析查询-图表二明细页面跳转
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessage_t2DetailMainPage",method = RequestMethod.GET)
	public String costGeneralMessage_t2DetailMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessage_t2DetailMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessage_t3
	* @Description: 综合信息分析查询-图表三
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月25日 上午10:57:44
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessage_t3", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessage_t3(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessage_t3(mapVo);
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	 * 
	* @Title: costGeneralMessage_t3DetailMainPage
	* @Description: 综合信息分析查询-图表三明细页面跳转
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessage_t3DetailMainPage",method = RequestMethod.GET)
	public String costGeneralMessage_t3DetailMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessage_t3DetailMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageProfit
	* @Description: 本量利分析
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageProfit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageProfit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageProfit(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}

	/**
	 * 
	* @Title: costGeneralMessageProfitDetailMainPage
	* @Description: 本量利分析明细页面跳转
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	*/
	@RequestMapping(value="/costGeneralMessageProfitDetailMainPage",method = RequestMethod.GET)
	public String costGeneralMessageProfitDetailMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("natur_code", mapVo.get("natur_code"));
		/*
		 * 01门诊 02住院 03医技
		 * */
		if("01".equals(mapVo.get("natur_code"))){
			return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitClinicMain";
		}else if("02".equals(mapVo.get("natur_code"))){
			return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitInhosMain";
		}else { 
			return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitMedicalMain";
		}
	
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageProfitClinic
	* @Description: 本量利分析门诊明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageProfitClinic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageProfitClinic(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageProfitClinic(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
  /**
	* 
	* @Title: costGeneralMessage_t3DetailMainPage
	* @Description: 本量利分析门诊明细查询-本量利分析图
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessageProfitClinicChartMainPage",method = RequestMethod.GET)
	public String costGeneralMessageProfitClinicChartMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitClinicChartMain";
	}
	
	/**
	* 
	* @Title: costGeneralMessageProfitClinicCostMainPage
	* @Description: 本量利分析门诊明细查询-固定变动成本页面跳转
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessageProfitClinicCostMainPage",method = RequestMethod.GET)
	public String costGeneralMessageProfitClinicCostMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("nature_id", mapVo.get("nature_id"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitClinicCostMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageProfitClinicCost
	* @Description: 本量利分析门诊明细查询-固定变动成本查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageProfitClinicCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageProfitClinicCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageProfitClinicCost(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageProfitInhos
	* @Description: 本量利分析住院明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18 
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageProfitInhos", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageProfitInhos(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageProfitInhos(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	* 
	* @Title: costGeneralMessageProfitInhosChartMainPage
	* @Description: 本量利分析住院明细查询-本量利分析图
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessageProfitInhosChartMainPage",method = RequestMethod.GET)
	public String costGeneralMessageProfitInhosChartMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitInhosChartMain";
	}
	
	/**
	* 
	* @Title: costGeneralMessageProfitInhosCostMainPage
	* @Description: 本量利分析住院明细查询-固定变动成本页面跳转
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月24日 下午4:26:14
	* @author sjy
	 */ 
	@RequestMapping(value="/costGeneralMessageProfitInhosCostMainPage",method = RequestMethod.GET)
	public String costGeneralMessageProfitInhosCostMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("nature_id", mapVo.get("nature_id"));
		return "hrp/cost/costdirector/comprehensiveanalysis/generalmessage/costGeneralMessageProfitInhosCostMain";
	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageProfitInhosCost
	* @Description: 本量利分析住院明细查询-固定变动成本查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageProfitInhosCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageProfitInhosCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageProfitInhosCost(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	/**
	 * 
	* @Title: queryCostGeneralMessageProfitClinic
	* @Description: 本量利分析医技明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostGeneralMessageProfitMedical", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostGeneralMessageProfitMedical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostGeneralMessageProfitMedical(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	
  /**
	* 
	* @Title: costDirectMainPage
	* @Description: 科室直接分析
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costDirectMainPage",method = RequestMethod.GET)
	public String costDirectMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/comprehensiveanalysis/direct/costDirectMain";
	}
	
	/**
	 * 
	* @Title: queryCostDirectMain
	* @Description: 科室直接分析查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @date 2019年10月28日 上午10:23:18
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostDirect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostDirectMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costComprehensiveAnalysisService.queryCostDirect(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);

	}
	
	  /**
		* 
		* @Title: costDirectMainPage
		* @Description: 科室直接分析-收入-类别
		* @param @param mode
		* @param @return
		* @param @throws DataAccessException
		* @return String    
		* @author sjy
		 */
		@RequestMapping(value="/costDirectIncomeKindMainPage",method = RequestMethod.GET)
		public String costDirectIncomeKindMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
			mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
			mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
			mode.addAttribute("dept_code", mapVo.get("dept_code"));
			mode.addAttribute("state", mapVo.get("state"));
			return "hrp/cost/costdirector/comprehensiveanalysis/direct/costDirectIncomeKindMain";
		}
		/**
		 * 
		* @Title: queryCostDirectIncomeKind
		* @Description: 科室直接分析-收入-类别查询
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @date 2019年10月28日 上午10:23:18
		* @author sjy
		 */
		@RequestMapping(value = "/queryCostDirectIncomeKind", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryCostDirectIncomeKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryCostDirectIncomeKind(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);
		}
		/**
		 * 
		 * @Title: multiIncomeMainPage
		 * @Description: 多方位收益分析
		 * @param mode
		 * @param @return
		 * @param @throws DataAccessException
		 * @return String
		 * @author Fan Yang
		 * 
		 */
		@RequestMapping(value="/multiIncomeMainPage",method = RequestMethod.GET)
		public String mutilIncomeMainPage (Model mode)throws DataAccessException{
			return "hrp/cost/costdirector/comprehensiveanalysis/multiincomeanalysis/multiIncomeMain";
		}
		
		/**
		 * 
		* @Title: queryMultiIncomeMain
		* @Description: 多方位收益分析查询
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @date 2019年12月9日 下午15:36:18
		* @author Fan Yang
		 */
		@RequestMapping(value = "/queryMultiIncome", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryMultiIncomeMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryMultiIncome(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);

		}
		
		/**
		 * 
		 * @Title: multiIncomeByDeptDirPage
		 * @Description: 多方位收益分析-科室收入
		 * @param mode
		 * @param @return
		 * @param @throws DataAccessException
		 * @return String
		 * @author zfy
		 * 
		 */
		@RequestMapping(value="/multiIncomeByDeptDirPage",method = RequestMethod.GET)
		public String multiIncomeByDeptDirPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
			mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
			mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
//			mode.addAttribute("dept_code", mapVo.get("dept_code"));
//			mode.addAttribute("natur_code", mapVo.get("natur_code"));
			return "hrp/cost/costdirector/comprehensiveanalysis/multiincomeanalysis/multiIncomeByDeptDir";
		}
		
		/**
		 * 
		* @Title: queryMultiIncomeByDeptDir
		* @Description: 多方位收益分析表-科室收入明细
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @author zfy
		 */
		@RequestMapping(value = "/queryMultiIncomeByDeptDir", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryMultiIncomeByDeptDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryMultiIncomeByDeptDir(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);
		}
		
		
		/**
		 * 
		 * @Title: multiIncomeByDeptDirPage
		 * @Description: 多方位收益分析-科室收入
		 * @param mode
		 * @param @return
		 * @param @throws DataAccessException
		 * @return String
		 * @author zfy
		 * 
		 */
		@RequestMapping(value="/multiIncomeByChargeTypePage",method = RequestMethod.GET)
		public String multiIncomeByChargeTypePage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
			mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
			mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
			mode.addAttribute("chargeId", mapVo.get("chargeId"));
			return "hrp/cost/costdirector/comprehensiveanalysis/multiincomeanalysis/multiIncomeByChargeType";
		}
		
		/**
		 * 
		* @Title: queryMultiIncomeByChargeType
		* @Description: 多方位收益分析表-科室收入明细
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @author zfy
		 */
		@RequestMapping(value = "/queryMultiIncomeByChargeType", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryMultiIncomeByChargeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryMultiIncomeByChargeType(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);
		}
		
		
		
		
		/**
		 * 
		* @Title: multiIncomeTotalCostDirPage
		* @Description: 多方位收益分析表-全成本明细页面
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @author zfy
		 */
		@RequestMapping(value="/multiIncomeTotalCostDirPage",method = RequestMethod.GET)
		public String multiIncomeTotalCostDirPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
			mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
			mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
			mode.addAttribute("dept_code", mapVo.get("dept_code"));
			mode.addAttribute("natur_code", mapVo.get("natur_code"));
			return "hrp/cost/costdirector/comprehensiveanalysis/multiincomeanalysis/multiIncomeTotalCostDir";
		}
		
		/**
		 * 
		* @Title: queryMultiIncomeTotalCost
		* @Description: 多方位收益明细表-全成本明细查询
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @author zfy
		 */
		@RequestMapping(value = "/queryMultiIncomeTotalCost", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryMultiIncomeTotalCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryMultiIncomeTotalCost(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);
		}
		
		/**
		* @Title: multiIncomeDirectCostDirPage
		* @Description: 多方位收益明细表-变动成本明细
		* @param @param mode
		* @param @return
		* @param @throws DataAccessException
		* @return String    
		* @author zfy
		 */
		@RequestMapping(value="/multiIncomeDirectCostDirPage",method = RequestMethod.GET)
		public String multiIncomeDirectCostDirPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
			mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
			mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
			mode.addAttribute("dept_code", mapVo.get("dept_code"));
			mode.addAttribute("natur_code", mapVo.get("natur_code"));
			return "hrp/cost/costdirector/comprehensiveanalysis/multiincomeanalysis/multiIncomeDirectCostDir";
		}
		
		
		
		/**
		 * 
		* @Title: queryMultiIncomeDirectCostDir
		* @Description: 多方位收益明细表-变动成本明细查询
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @author sjy
		 */
		@RequestMapping(value = "/queryMultiIncomeDirectCostDir", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryMultiIncomeDirectCostDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryMultiIncomeDirectCostDir(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);
		}
		
		
		
		
		
		/**
		* @Title: multiIncomeChangeCostDirPage
		* @Description: 多方位收益明细表-变动成本明细
		* @param @param mode
		* @param @return
		* @param @throws DataAccessException
		* @return String    
		* @author zfy
		 */
		@RequestMapping(value="/multiIncomeChangeCostDirPage",method = RequestMethod.GET)
		public String multiIncomeChangeCostDirPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
			mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
			mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
			return "hrp/cost/costdirector/comprehensiveanalysis/multiincomeanalysis/multiIncomeChangeCostDir";
		}
		
		
		
		/**
		 * 
		* @Title: queryMultiIncomeChangeCostDir
		* @Description: 多方位收益明细表-变动成本明细查询
		* @param @param mapVo
		* @param @param mode
		* @param @return
		* @param @throws Exception
		* @return Map<String,Object>    
		* @author zfy
		 */
		@RequestMapping(value = "/queryMultiIncomeChangeCostDir", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryCostShareCostDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String analysisJson = costComprehensiveAnalysisService.queryMultiIncomeChangeCostDir(getPage(mapVo));
			return JSONObject.parseObject(analysisJson);
		}
}
