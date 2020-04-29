package com.chd.hrp.acc.controller.report;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccYearMonthMy97;
import com.chd.hrp.acc.service.AccYearMonthService;
import com.chd.hrp.acc.service.report.SuperReportEngineService;
import com.chd.hrp.acc.service.report.SuperReportManagerService;

@Controller
@RequestMapping(value="/hrp/acc/superReport")
public class SuperReportManagerController extends BaseController{
	
	private static Logger logger = Logger.getLogger(SuperReportManagerController.class);

	@Resource(name = "superReportManagerService")
	private final SuperReportManagerService superReportManagerService = null;

	@Resource(name = "accYearMonthService")
	private final AccYearMonthService accYearMonthService = null;
	
	@Resource(name = "superReportEngineService")
	private final SuperReportEngineService superReportEngineService = null;
	
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
		return "hrp/acc/superReport/manage/main";
	}
	@RequestMapping(value = "/manage/batchGeneratePage", method = RequestMethod.GET)
	public String batchGeneratePage(){		
		return "hrp/acc/superReport/manage/batchGenerate";
	}
	@RequestMapping(value = "/manage/batchGenerate", method = RequestMethod.POST)
	@ResponseBody
	public String batchGenerate(@RequestParam Map<String, Object> mapVo,Model mode){		
		String content=null;
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("mod_code",SessionManager.getModCode());//财务结账标识
			String[] repCodes=mapVo.get("listVo").toString().split(",");
			ReportMap rm=null;
			List<ReportMap> rlist=new ArrayList<ReportMap>();
			for(String code:repCodes){
				mapVo.put("report_code", code.split("@")[0]);
				rm=new ReportMap();
				rm.setReport_code(code.split("@")[0]);
				rm.setReport_name(code.split("@")[1]);
				
				try{
					content=superReportEngineService.saveSuperReportInstance(mapVo);
					if(content.startsWith("{\"error\"")){
						rm.setMsg(content);
						rm.setState("失败");
					}else if(content.equals("{}")){
						rm.setMsg("报表没有模板，无法生成");
						rm.setState("失败");
					}else{
						rm.setState("成功");
						rm.setMsg("");
					}
				}catch(Exception e){
					rm.setState("失败");
					rm.setMsg(e.getMessage());
				}
				rlist.add(rm);
			}
			content="{\"Rows\":"+JSONObject.toJSONString(rlist)+",\"Total\":"+rlist.size()+"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			content="{\"state\":false,\"msg\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return content;
	}
	
	class ReportMap{
		private String report_code;
		private String report_name;
		private String state;
		private String msg;
		public String getReport_code() {
			return report_code;
		}
		public void setReport_code(String report_code) {
			this.report_code = report_code;
		}
		public String getReport_name() {
			return report_name;
		}
		public void setReport_name(String report_name) {
			this.report_name = report_name;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		
	}
	//报表管理，主页面
	@RequestMapping(value = "/manage/reportPage", method = RequestMethod.GET)
	public String reportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
	    mode.addAttribute("mod_code",mapVo.get("mod_code"));
		mode.addAttribute("report_code",mapVo.get("report_code"));
		mode.addAttribute("acc_year",mapVo.get("acc_year"));
		mode.addAttribute("acc_month",mapVo.get("acc_month"));
		return "hrp/acc/superReport/manage/report";
	}
	
	
	//根据报表元素查询报表元素参数
	@RequestMapping(value = "/manage/getReportQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getReportQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        String reJson=superReportManagerService.getReportQuery(mapVo);
			return JSONObject.parseObject(reJson);
	}

	
	
	//报表管理页面，查询报表的所有实例
	@RequestMapping(value = "/manage/querySuperReportInstanceList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportInstanceList(@RequestParam Map<String, Object> map)throws Exception{
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
	        
			return JSONObject.parseObject(superReportManagerService.querySuperReportInstanceList(getPage(map)));
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
			res=superReportManagerService.deleteBatchSuperReportInstance(map);
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
			res=superReportManagerService.initSuperReportProc(map);
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
		return "hrp/acc/superReport/dict/main";
	}
	
	//报表字典页面，查询所有字典
	@RequestMapping(value = "/dict/querySuperReportDictList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySuperReportDictList(@RequestParam Map<String, Object> map)throws Exception{
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());
        
		return JSONObject.parseObject(superReportManagerService.querySuperReportDictList(getPage(map)));
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
			reJson=superReportManagerService.saveSuperReportDict(mapVo);
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
			reJson=superReportManagerService.deleteBatchSuperReportDict(mapVo);
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
		
		String reJson=superReportManagerService.querySuperReportDictByCode(mapVo);
		return JSONObject.parseObject(reJson);
		
	}
	
	
	/*******************************************报表元素********************************************************************/
	//报表元素页面，主页面
	@RequestMapping(value = "/ele/elePage", method = RequestMethod.GET)
	public String elePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/superReport/ele/main";
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
			reJson=superReportManagerService.querySuperReportEleManager(mapVo);
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
			reJson=superReportManagerService.querySuperReportSourceAndViews(mapVo);
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
		return "hrp/acc/superReport/ele/paraSet";
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
			reJson=superReportManagerService.saveSuperReportEle(mapVo);
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
			reJson=superReportManagerService.deleteSuperReportEle(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
}
