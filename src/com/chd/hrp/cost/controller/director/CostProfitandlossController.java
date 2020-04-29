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
import com.chd.hrp.cost.service.director.CostProfitandlossService;

/**
 * 盈亏分析
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value="/hrp/cost/director/profitandloss") 
public class CostProfitandlossController  extends BaseController{

	private static Logger logger = Logger.getLogger(CostProfitandlossController.class);
	
	@Resource(name = "costProfitandlossService")
	private final CostProfitandlossService costProfitandlossService = null;
	
	/**
	* 
	* @Title: costShareMainPage
	* @Description: 分摊汇总表
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costShareMainPage",method = RequestMethod.GET)
	public String costShareMainPage (Model mode)throws DataAccessException{
		return "hrp/cost/costdirector/profitandloss/share/costShareMain";
	}
	
	/**
	 * 
	* @Title: queryCostShare
	* @Description: 分摊汇总表-查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostShare", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostShare(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String analysisJson = costProfitandlossService.queryCostShare(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	* @Title: costShareCostMainPage
	* @Description: 分摊汇总表-全成本明细
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costShareCostMainPage",method = RequestMethod.GET)
	public String costShareCostMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		return "hrp/cost/costdirector/profitandloss/share/costShareCostMain";
	}
	
	/**
	 * 
	* @Title: queryCostShare
	* @Description: 分摊汇总表-全成本明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/querycostShareCost", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querycostShareCost(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costProfitandlossService.querycostShareCost(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	* @Title: costShareCostDirMainPage
	* @Description: 分摊汇总表-直接成本明细
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costShareCostDirMainPage",method = RequestMethod.GET)
	public String costShareCostDirMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("natur_code", mapVo.get("natur_code"));
		return "hrp/cost/costdirector/profitandloss/share/costShareCostDirMain";
	}
	
	/**
	 * 
	* @Title: queryCostShareCostDir
	* @Description: 分摊汇总表-直接成本明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostShareCostDir", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostShareCostDir(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costProfitandlossService.queryCostShareCostDir(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	* @Title: costShareCostDirMainPage
	* @Description: 分摊汇总表-医辅成本明细
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costShareCostAssMainPage",method = RequestMethod.GET)
	public String costShareCostAssMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("natur_code", mapVo.get("natur_code"));
		return "hrp/cost/costdirector/profitandloss/share/costShareCostAssMain";
	}
	
	/**
	 * 
	* @Title: queryCostShareCostDir
	* @Description: 分摊汇总表-医辅成本明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostShareCostAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostShareCostAss(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costProfitandlossService.queryCostShareCostAss(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
	
	/**
	* @Title: costShareCostDirMainPage
	* @Description: 分摊汇总表-医技明细
	* @param @param mode
	* @param @return
	* @param @throws DataAccessException
	* @return String    
	* @author sjy
	 */
	@RequestMapping(value="/costShareCostMedMainPage",method = RequestMethod.GET)
	public String costShareCostMedMainPage (@RequestParam Map<String, Object> mapVo, Model mode)throws DataAccessException{
		mode.addAttribute("year_month_begin", mapVo.get("year_month_begin"));
		mode.addAttribute("year_month_end", mapVo.get("year_month_end"));
		mode.addAttribute("dept_code", mapVo.get("dept_code"));
		mode.addAttribute("natur_code", mapVo.get("natur_code"));
		return "hrp/cost/costdirector/profitandloss/share/costShareCostMedMain";
	}
	
	/**
	 * 
	* @Title: queryCostShareCostDir
	* @Description: 分摊汇总表-医技成本明细查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object>    
	* @author sjy
	 */
	@RequestMapping(value = "/queryCostShareCostMed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostShareCostMed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String analysisJson = costProfitandlossService.queryCostShareCostMed(getPage(mapVo));
		return JSONObject.parseObject(analysisJson);
	}
}
