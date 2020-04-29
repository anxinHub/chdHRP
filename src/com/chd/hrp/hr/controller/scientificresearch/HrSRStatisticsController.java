package com.chd.hrp.hr.controller.scientificresearch;

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

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.scientificresearch.HrResearchTotSet;
import com.chd.hrp.hr.service.scientificresearch.HrSRStatisticsService;

@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch/statistics")
public class HrSRStatisticsController extends BaseController{
	private static Logger logger = Logger.getLogger(HrSRStatisticsController.class);

	// 引入Service服务
	@Resource(name = "hrSRStatisticsService")
	private final HrSRStatisticsService hrSRStatisticsService = null;

	/**
	 * 页面跳转 个人学术荣誉统计
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrHonorStatisticsMainPage", method = RequestMethod.GET)
	public String hrHonorStatisticsMainPage(Model mode) throws Exception {
		logger.debug("--------------");
		return "hrp/hr/scientificresearch/statistics/hrHonorStatisticsMain";
	}
	
	/**
	 * 查询 个人学术荣誉统计
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHonorStatistics", method = RequestMethod.POST)
	@ResponseBody
	public String queryHonorStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("field_tab_code", "DIC_ACADE_HONOR");//代码表名
		
		return hrSRStatisticsService.queryHonorStatistics(getPage(mapVo));
	}
	
	/**
	 * 页面跳转 个人学术地位统计
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStatusStatisticsMainPage", method = RequestMethod.GET)
	public String hrStatusStatisticsMainPage(Model mode) throws Exception {
		
		return "hrp/hr/scientificresearch/statistics/hrStatusStatisticsMain";
	}
	
	/**
	 * 查询 个人学术地位统计
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStatusStatistics", method = RequestMethod.POST)
	@ResponseBody
	public String queryStatusStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("field_tab_code", "DIC_ACADE_STATUS");//代码表名
		
		return hrSRStatisticsService.queryStatusStatistics(getPage(mapVo));
	}
	
	/**
	 * 页面跳转 科研项目与成果统计
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrScientificStatisticsMainPage", method = RequestMethod.GET)
	public String hrScientificStatisticsMainPage(Model mode) throws Exception {
		
		return "hrp/hr/scientificresearch/statistics/hrScientificStatisticsMain";
	}
	
	/**
	 * 查询 科研项目与成果统计
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryScientificStatistics", method = RequestMethod.POST)
	@ResponseBody
	public String queryScientificStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("field_tab_code", "DIC_PROJ_LEVEL");//代码表名
		
		return hrSRStatisticsService.queryScientificStatistics(getPage(mapVo));
	}
	
	/**
	 * 页面跳转 学术论文发表统计 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPaperStatisticsMainPage", method = RequestMethod.GET)
	public String hrPaperStatisticsMainPage(Model mode) throws Exception {
		
		return "hrp/hr/scientificresearch/statistics/hrPaperStatisticsMain";
	}
	
	/**
	 * 查询 学术论文发表统计
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPaperStatistics", method = RequestMethod.POST)
	@ResponseBody
	public String queryPaperStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("field_tab_code", "DIC_PAPER_TYPE");//代码表名
		
		return hrSRStatisticsService.queryPaperStatistics(getPage(mapVo));
	}
	
	/**
	 * 动态表头
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value = "/queryDynamicTabHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryDynamicTabHead(@RequestParam Map<String, Object> mapVo){
		
		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> tabHead = hrSRStatisticsService.queryDynamicTabHead(mapVo);
		reMap.put("columns", tabHead);
		
		//满分标准
		HrResearchTotSet fullScore = hrSRStatisticsService.queryScoreStandard(mapVo);
		
		reMap.put("fullScore", fullScore == null ? new HrResearchTotSet() : fullScore);
		
		return reMap;
		
	}
	
	/**
	 * 科研项目与成果统计 动态表头
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value = "/queryScientificDynamicTabHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryScientificDynamicTabHead(@RequestParam Map<String, Object> mapVo){
		
		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//动态表头
		List<Map<String, Object>> tabHead = hrSRStatisticsService.queryScientificDynamicTabHead(mapVo);
		reMap.put("columns", tabHead);
		
		//满分标准
		HrResearchTotSet fullScore = hrSRStatisticsService.queryScoreStandard(mapVo);
		
		reMap.put("fullScore", fullScore == null ? new HrResearchTotSet() : fullScore);
		
		return reMap;
		
	}
	
	/**
	 * 学术论文发表统计 动态表头
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value = "/queryPaperDynamicTabHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryPaperDynamicTabHead(@RequestParam Map<String, Object> mapVo){
		
		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//动态表头
		List<Map<String, Object>> tabHead = hrSRStatisticsService.queryPaperDynamicTabHead(mapVo);
		reMap.put("columns", tabHead);
		
		//满分标准
		HrResearchTotSet fullScore = hrSRStatisticsService.queryScoreStandard(mapVo);
		
		reMap.put("fullScore", fullScore == null ? new HrResearchTotSet() : fullScore);
		
		return reMap;
		
	}
	
	/**
	 * 页面跳转 个人科研积分统计
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrSRStatisticsMainPage", method = RequestMethod.GET)
	public String hrSRStatisticsMainPage(Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//满分标准
		//HrResearchTotSet fullScore = hrSRStatisticsService.queryScoreStandard(mapVo);
		
		//mode.addAttribute(fullScore);
		
		return "hrp/hr/scientificresearch/statistics/hrSRStatisticsMain";
	}
	
	/**
	 * 查询 个人科研积分统计
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySRStatistics", method = RequestMethod.POST)
	@ResponseBody
	public String querySRStatistics(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrSRStatisticsService.querySRStatistics(getPage(mapVo));
	}
	
	/**
	 * 个人科研积分统计 动态表头
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value = "/querySRDynamicTabHead", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> querySRDynamicTabHead(@RequestParam Map<String, Object> mapVo){
		
		Map<String,Object> reMap = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//满分标准
		HrResearchTotSet fullScore = hrSRStatisticsService.queryScoreStandard(mapVo);
		
		reMap.put("fullScore", fullScore == null ? new HrResearchTotSet() : fullScore);
		if (fullScore!=null) {
			reMap.put("totScore", fullScore.getTotScore());
		}else{
			reMap.put("totScore", 0);
		}
		
		
		return reMap;
		
	}
	
}
