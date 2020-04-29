package com.chd.hrp.hpm.controller.customreport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.ExcelWriterTo2007;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiCustomTemplateReportConf;
import com.chd.hrp.hpm.service.customconf.AphiCustomTemplateReportConfService;
import com.chd.hrp.hpm.service.customreport.AphiCustomTemplateReportService;

/**
 * 
 * @Title.
 * 自定义报表
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class AphiCustomTemplateReportController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AphiCustomTemplateReportController.class);
	
	//引入SERVICE服务
	
	@Resource(name = "aphiCustomTemplateReportService")
	private final AphiCustomTemplateReportService aphiCustomTemplateReportService = null;
	
	@Resource(name = "aphiCustomTemplateReportConfService")
	private final AphiCustomTemplateReportConfService aphiCustomTemplateReportConfService = null;
	
	@RequestMapping(value="hrp/hpm/customreport/{pagePath} ",method = RequestMethod.GET)
	public String hpmReportPage(@PathVariable("pagePath")String pagePath,Model mode) throws Exception {
		
		String pageCode = pagePath.substring(pagePath.indexOf("_")+1, pagePath.indexOf("."));
		
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("template_code", pageCode);
		
		
		//查询当前配置在具备哪些功能
		List<AphiCustomTemplateReportConf> actrcList = aphiCustomTemplateReportConfService.queryAphiCustomTemplateReportConfListByCode(mapVo);
		StringBuffer sb = new StringBuffer();
		
		for(int x = 0; x < actrcList.size() ; x++){
		
			AphiCustomTemplateReportConf actrc = actrcList.get(x);
			
			sb.append(actrc.getTemplate_type());
			sb.append(",");
			
			if(x == 0){
				mode.addAttribute("template_name", actrc.getTemplate_name());
			}
		}
		
		if(sb.length() >0){
			sb = sb.deleteCharAt(sb.length() - 1);
		}
		
		mode.addAttribute("templateTypes", sb.toString());
		
		//SessionManager.
		
		
		//拼装URL
		String queryURL = "queryReport_" + pageCode;//这种写法,页面如果要自动查询,需在页面加载完以后调query()函数
		String initURL = "initReport_" + pageCode;
		String updateURL = "updateReport_" + pageCode;
		String auditURL = "auditReport_" + pageCode;
		String reAuditURL = "reAuditReport_" + pageCode;
		String importURL = "importReport_" + pageCode;
		String deleteURL = "deleteReport_" + pageCode;
		String addURL = "addReport_" + pageCode;
		
		mode.addAttribute("queryURL", queryURL);//查询请求
		mode.addAttribute("initURL", initURL);//生成请求
		mode.addAttribute("updateURL", updateURL);//修改请求
		mode.addAttribute("auditURL", auditURL);//审核请求
		mode.addAttribute("reAuditURL", reAuditURL);//消审请求
		mode.addAttribute("importURL", importURL);//导入请求
		mode.addAttribute("deleteURL", deleteURL);//删除请求
		mode.addAttribute("addURL", addURL);//删除请求
		
		if(!SessionManager.existsUserPerm(queryURL)){
			mode.addAttribute("is_query_hide", true);
		}else{
			mode.addAttribute("is_query_hide", false);
		}
		
		if(!SessionManager.existsUserPerm(initURL)){
			mode.addAttribute("is_init_hide", true);
		}else{
			mode.addAttribute("is_init_hide", false);
		}
		
		if(!SessionManager.existsUserPerm(updateURL)){
			mode.addAttribute("is_update_hide", false);
		}else{
			mode.addAttribute("is_update_hide", true);
		}
		
		if(!SessionManager.existsUserPerm(auditURL)){
			mode.addAttribute("is_audit_hide", true);
		}else{
			mode.addAttribute("is_audit_hide", false);
		}
		
		if(!SessionManager.existsUserPerm(reAuditURL)){
			mode.addAttribute("is_reAudit_hide", true);
		}else{
			mode.addAttribute("is_reAudit_hide", false);
		}
		
		if(!SessionManager.existsUserPerm(importURL)){
			mode.addAttribute("is_import_hide", true);
		}else{
			mode.addAttribute("is_import_hide", false);
		}
		
		if(!SessionManager.existsUserPerm(deleteURL)){
			mode.addAttribute("is_delete_hide", true);
		}else{
			mode.addAttribute("is_delete_hide", false);
		}
		
		if(!SessionManager.existsUserPerm(addURL)){
			mode.addAttribute("is_add_hide", true);
		}else{
			mode.addAttribute("is_add_hide", false);
		}
		
		mode.addAttribute("pageCode", pageCode);
		
		return "hrp/hpm/customreport/hpmReport_" + pageCode;
	}
	
	
	
	@RequestMapping(value = "/hrp/hpm/customreport/{pagePath}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> operationAphiCustomTemplateReport(@RequestParam Map<String, Object> mapVo,@PathVariable("pagePath")String pagePath, Model mode) throws Exception {
		
		String reportJson = null;
		
		try {
			
			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			if(mapVo.get("user_id") == null){
				mapVo.put("user_id", SessionManager.getUserId());
			}
			
			
			if(mapVo.get("template_code") == null || "".equals(String.valueOf(mapVo.get("template_code")))){
				
				reportJson =  "{\"warn\":\"请求参数模板编码不能为空.\",\"state\":\"false\"}";
				
				return JSONObject.parseObject(reportJson);
			}
			
			
			if(mapVo.get("template_type") == null || "".equals(String.valueOf(mapVo.get("template_type")))){
				
				reportJson =  "{\"warn\":\"请求参数功能类型编码不能为空.\",\"state\":\"false\"}";
				
				return JSONObject.parseObject(reportJson);
			}
			
			
			if(pagePath.startsWith("query")){//查询
				
				reportJson = aphiCustomTemplateReportService.queryAphiCustomTemplateReportForParseSql(getPage(mapVo));
			}else if(pagePath.startsWith("init")){//生成
				
				reportJson = aphiCustomTemplateReportService.initAphiCustomTemplateReportForParseSql(mapVo);
			}else if(pagePath.startsWith("update")){//修改
				
				reportJson = aphiCustomTemplateReportService.updateAphiCustomTemplateReportForParseSql(mapVo);
			}else if(pagePath.startsWith("audit")){//审核
				
				reportJson = aphiCustomTemplateReportService.auditAphiCustomTemplateReportForParseSql(mapVo);
			}else if(pagePath.startsWith("reAudit")){//反审核
				
				reportJson = aphiCustomTemplateReportService.reAuditAphiCustomTemplateReportForParseSql(mapVo);
			}else if(pagePath.startsWith("import")){//导入
				
				reportJson = aphiCustomTemplateReportService.importAphiCustomTemplateReportForParseSql(mapVo);
			}else if(pagePath.startsWith("delete")){//删除
				
				reportJson = aphiCustomTemplateReportService.deleteAphiCustomTemplateReportForParseSql(mapVo);
			}else if(pagePath.startsWith("add")){//添加
				
				reportJson = aphiCustomTemplateReportService.addAphiCustomTemplateReportForParseSql(mapVo);
			}else{
				
				reportJson =  "{\"warn\":\"请更改路径前缀.\",\"state\":\"false\"}";
			}
			
		} catch (Exception e) {
		
			reportJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
		
		return JSONObject.parseObject(reportJson);
	}
	
	
	
	//下载导入模版
	@RequestMapping(value="/hrp/hpm/customreport/downTemplate", method = RequestMethod.GET)  
	public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
			    HttpServletResponse response,Model mode) throws IOException { 
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("template_code") == null){
			return null;
		}
		
		if(mapVo.get("template_type") == null){
			mapVo.put("template_type", "08");
		}
		
		
		//1.获取模板中导入配置
		AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfService.queryAphiCustomTemplateReportConfByCode(mapVo);
		if(actrc == null){
			return null;
		}
		
		//2.取出SQL语句
		String template_sql = new String(actrc.getTemplate_sql(),"UTF-8");
		
		//String template_sql = actrc.getTemplate_sql();
		String add_sql = template_sql.substring(template_sql.toLowerCase().indexOf("insert"),template_sql.lastIndexOf(");")+2);
		
		
		List<String> paraList = new ArrayList<String>();
		
		//3.取出添加SQL中的添加参数列表
		
		//3.取出添加SQL中的添加参数列表
//		Matcher m = Pattern.compile("#\\[.*?\\]").matcher(add_sql);
//		while (m.find()) {
//			String key = m.group() ;//获取匹配到的参数
//			
//			key = key.replaceAll("#", "").replaceAll("\\[", "").replaceAll("\\]", "");
//			paraList.add(key);
//		}
		
		Matcher matcher = Pattern.compile("#\\{.*?\\}|#\\[.*?\\]").matcher(add_sql);
		while (matcher.find()) {
			String key = matcher.group() ;//获取匹配到的参数
			
			key = key.replaceAll("#", "").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\\[", "").replaceAll("\\]", "");
			paraList.add(key);
		}
		
		if(paraList.size() == 0){
			return null;
		}
		
		
		List<List> list = new ArrayList<List>();
		list.add(paraList);
		
		
		String fileName = actrc.getTemplate_name() +".xlsx";
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\hpm\\downTemplate\\";
				
		String downLoadPath = ctxPath + fileName;
				
		ExcelWriterTo2007.exportExcel(new File(downLoadPath), list);
				
		printTemplate(request, response, "hpm\\downTemplate", fileName);

		return null; 
	}
	
	
	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/customreport/hpmReportAddPage", method = RequestMethod.GET)
	public String hpmReportAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String template_code = String.valueOf(mapVo.get("template_code"));
		
		String addURL= String.valueOf(mapVo.get("addURL"));
		
		mode.addAttribute("template_code",template_code);
		mode.addAttribute("addURL",addURL);
		
		return "hrp/hpm/customreport/hpmReportAdd_" + template_code;

	}
	
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hpm/customreport/hpmReportImportPage", method = RequestMethod.GET)
	public String hpmReportImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("template_code",mapVo.get("template_code"));
		
		return "hrp/hpm/customconf/hpmReportImport";

	}
}
