package com.chd.hrp.acc.controller.report;

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

@Controller
@RequestMapping(value="/hrp/acc/superReport")
public class SuperReportEngineController extends BaseController{
	
	private static Logger logger = Logger.getLogger(SuperReportEngineController.class);

	@Resource(name = "superReportEngineService")
	private final SuperReportEngineService superReportEngineService = null;

	@Resource(name = "accYearMonthService")
	private final AccYearMonthService accYearMonthService = null;
	
	//报表查询主页面
	@RequestMapping(value = "/query/queryMainPage", method = RequestMethod.GET)
	public String mainPage(Model mode) throws Exception {
		
		mode.addAttribute("mod_code", SessionManager.getModCode());
		return "hrp/acc/superReport/query/main";
	}
	
	
	//根据系统编码查询报表,返回tree
	@RequestMapping(value = "/queryAccSuperReportByPerm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSuperReportByPerm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("user_id", SessionManager.getUserId());
        String content=superReportEngineService.queryAccSuperReportByPerm(mapVo);
		return JSONObject.parseObject(content);
		
	}
	
	//点击报表，查询查询条件
	@RequestMapping(value = "/queryAccSuperReportByWhere", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSuperReportByWhere(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("flag","acc_flag");//财务结账标识
        
        //查询年月会计期间
        AccYearMonthMy97 accYearMonthMy97=new AccYearMonthMy97();
        accYearMonthMy97=accYearMonthService.queryYearMonthByMy97(mapVo);
        
        StringBuffer reJson=new StringBuffer();
        reJson.append("{");
        reJson.append("\"minDate\":\""+accYearMonthMy97.getMinDate()+"\",");
        reJson.append("\"maxDate\":\""+accYearMonthMy97.getMaxDate()+"\",");
        reJson.append("\"curDate\":\""+accYearMonthMy97.getCurDate()+"\"");
        reJson.append("}");
		
		return JSONObject.parseObject(reJson.toString());
		
	}
	
	//根据会计年度查找月份
	@RequestMapping(value = "/queryYearMonthByAccYearSelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryYearMonthByAccYearSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		return accYearMonthService.queryYearMonthByAccYearSelect(mapVo);
		
	}
	
	//根据报表编码查询报表实例数据
	@RequestMapping(value = "/querySuperReportInstance", method = RequestMethod.POST)//,produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> querySuperReportInstance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 //auto_param=acc_year#ZREP_HBZJ_name=本年度,acc_year#ZREP_HBZJ=本年度,hos_id#ZREP_HBZJ_name=本医院,hos_id#ZREP_HBZJ=本医院
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String params="";
		String value=null;
		if(mapVo.get("auto_param")!=null)
			params=mapVo.get("auto_param").toString();
		String[] param=params.split(",");
		String[] tmp=null;
		for(String s:param){
			tmp=s.split("#");
			if(tmp!=null&&tmp.length==2){
				if(tmp[1].split("=")!=null&&tmp[1].split("=").length==2){
					value=tmp[1].split("=")[1];
					
					if(tmp[1].split("=")[0].endsWith("_name")){
						if(value.equals("本年度")){
							 value=SessionManager.getAcctYear();
						}else if(value.equals("本医院")){
							value=SessionManager.getHosName();
						}
						mapVo.put(tmp[0]+"_name", value);
					}else{
						if(value.equals("本年度")){
							 value=SessionManager.getAcctYear();
						}else if(value.equals("本医院")){
							value=SessionManager.getHosId();
						}
						mapVo.put(tmp[0], value);
					}
				}
			}
		}
		
        
        if (mapVo.get("acc_year") == null || mapVo.get("acc_year").toString().equals("")) {
			mapVo.put("acc_year", "0000");
		}
        if (mapVo.get("acc_month") == null || mapVo.get("acc_month").toString().equals("")) {
			mapVo.put("acc_month", "00");
		}
        String content=null;
        if(mapVo.get("rtype")!=null&&mapVo.get("rtype").toString().equals("1"))
        	content=superReportEngineService.getSuperReportContent(mapVo);
        else
        	content=superReportEngineService.querySuperReportInstance(mapVo);
		return JSONObject.parseObject(content);
		
	}
	
	//根据报表模板生成报表实例数据
	@RequestMapping(value = "/saveSuperReportInstance", method = RequestMethod.POST)//,produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> saveSuperReportInstance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        if (mapVo.get("acc_year") == null || mapVo.get("acc_year").toString().equals("")) {
			mapVo.put("acc_year", "0000");
		}
        if (mapVo.get("acc_month") == null || mapVo.get("acc_month").toString().equals("")) {
			mapVo.put("acc_month", "00");
		}
        
		String content=null;
		try {
			content=superReportEngineService.saveSuperReportInstance(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			content="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(content);
		
	}
	
	
	//保存计算完后的报表内容，为了取报表单元格的数据
	@RequestMapping(value = "/saveSuperReportContent", method = RequestMethod.POST)//,produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> saveSuperReportContent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        if (mapVo.get("acc_year") == null || mapVo.get("acc_year").toString().equals("")) {
			mapVo.put("acc_year", "0000");
		}
        if (mapVo.get("acc_month") == null || mapVo.get("acc_month").toString().equals("")) {
			mapVo.put("acc_month", "00");
		}
        
		String content=null;
		try {
			content=superReportEngineService.saveSuperReportContent(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			content="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(content);
		
	}
	
	/**
	 * 导入页面
	 */
	@RequestMapping(value = "/importPage", method = RequestMethod.GET)
	public String importPage() throws Exception {
		return "hrp/acc/superReport/query/import";
	}

}
