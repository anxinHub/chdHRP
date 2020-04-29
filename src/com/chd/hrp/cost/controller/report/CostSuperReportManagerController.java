package com.chd.hrp.cost.controller.report;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccYearMonthMy97;
import com.chd.hrp.acc.service.AccYearMonthService;
import com.chd.hrp.cost.service.report.CostSuperReportManagerService;

@Controller
@RequestMapping(value="/hrp/cost/superReport")
public class CostSuperReportManagerController extends BaseController{
	
	private static Logger logger = Logger.getLogger(CostSuperReportManagerController.class);

	@Resource(name = "costSuperReportManagerService")
	private final CostSuperReportManagerService costSuperReportManagerService = null;

	@Resource(name = "accYearMonthService")
	private final AccYearMonthService accYearMonthService = null;
	
	/*******************************************报表管理********************************************************************/
	//报表管理查询主页面
	@RequestMapping(value = "/manage/managePage", method = RequestMethod.GET)
	public String managePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("flag","acc_flag");//财务结账标识
		
		//查询会计年度
		AccYearMonthMy97 accYearMonthMy97=new AccYearMonthMy97();
	    accYearMonthMy97=accYearMonthService.queryYearMonthByMy97(mapVo);
	    if(accYearMonthMy97!=null && !accYearMonthMy97.getMinDate().equals("0")){
	    	accYearMonthMy97.setCurDate(accYearMonthMy97.getCurDate().substring(0, 5));
	    	accYearMonthMy97.setMaxDate(accYearMonthMy97.getMaxDate().substring(0, 5));
	    	accYearMonthMy97.setMinDate(accYearMonthMy97.getMinDate().substring(0, 5));
	    }
	    
	    mode.addAttribute("accYearMonthMy97",accYearMonthMy97);
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/cost/superReport/manage/main";
	}
	
	
	//报表管理，主页面
	@RequestMapping(value = "/manage/reportPage", method = RequestMethod.GET)
	public String reportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
	    mode.addAttribute("mod_code",mapVo.get("mod_code"));
		mode.addAttribute("report_code",mapVo.get("report_code"));
		mode.addAttribute("acc_year",mapVo.get("acc_year"));
		mode.addAttribute("acc_month",mapVo.get("acc_month"));
		return "hrp/cost/superReport/manage/report";
	}
	
	
	//报表管理页面，查询报表的所有实例
	@RequestMapping(value = "/manage/querySuperReportInstanceList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportInstanceList(@RequestParam Map<String, Object> map)throws Exception{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
        
		return JSONObject.parseObject(costSuperReportManagerService.querySuperReportInstanceList(getPage(map)));
	}
	
	
	//报表管理页面，删除报表实例
	@RequestMapping(value = "/manage/deleteBatchSuperReportInstance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchSuperReportInstance(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		String res=null;
		try {
			res=costSuperReportManagerService.deleteBatchSuperReportInstance(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			res="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(res);
	}
	
	//重新加载报表存储过程
	@RequestMapping(value = "/ele/initSuperReportProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initSuperReportProc(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
		
		String res=null;
		try {
			res=costSuperReportManagerService.initSuperReportProc(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			res="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(res);
	}
	
	
	
	
	/*******************************************报表字典********************************************************************/
	//报表字典页面，主页面
	@RequestMapping(value = "/dict/dictPage", method = RequestMethod.GET)
	public String dictPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/cost/superReport/dict/main";
	}
	
	//报表字典页面，查询所有字典
	@RequestMapping(value = "/dict/querySuperReportDictList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportDictList(@RequestParam Map<String, Object> map)throws Exception{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
        
		return JSONObject.parseObject(costSuperReportManagerService.querySuperReportDictList(getPage(map)));
	}
	
	//报表字典页面，保存报表字典
	@RequestMapping(value = "/dict/saveSuperReportDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperReportDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=costSuperReportManagerService.saveSuperReportDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	//报表字典页面，删除报表字典
	@RequestMapping(value = "/dict/deleteBatchSuperReportDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchSuperReportDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=costSuperReportManagerService.deleteBatchSuperReportDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	
	//报表字典页面，根据字典编码查询报表字典
	@RequestMapping(value = "/dict/querySuperReportDictByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportDictByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=costSuperReportManagerService.querySuperReportDictByCode(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	
	
	/*******************************************报表元素********************************************************************/
	//报表元素页面，主页面
	@RequestMapping(value = "/ele/elePage", method = RequestMethod.GET)
	public String elePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/cost/superReport/ele/main";
	}
	
	//报表元素页面，查询报表元素
	@RequestMapping(value = "/ele/querySuperReportEleManager", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportEleManager(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String reJson=null;
        try {
			reJson=costSuperReportManagerService.querySuperReportEleManager(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	//查询存储过程、函数(user_source)、视图(user_views)
	@RequestMapping(value = "/ele/querySuperReportSourceAndViews", method = RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String querySuperReportSourceAndViews(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String reJson="";
		try {
			reJson=costSuperReportManagerService.querySuperReportSourceAndViews(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return reJson;
		
	}
	
	//报表元素页面，参数设置页面
	@RequestMapping(value = "/ele/paraSetPage", method = RequestMethod.GET)
	public String paraSetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "hrp/cost/superReport/ele/paraSet";
	}
	
	//报表元素页面，保存报表元素、报表参数
	@RequestMapping(value = "/ele/saveSuperReportEle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperReportEle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=costSuperReportManagerService.saveSuperReportEle(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	//报表元素页面，删除报表元素、报表参数
	@RequestMapping(value = "/ele/deleteSuperReportEle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSuperReportEle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=costSuperReportManagerService.deleteSuperReportEle(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
}
