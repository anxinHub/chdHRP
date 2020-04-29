package com.chd.hrp.hr.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.ExportExcelService;
import com.chd.hrp.hr.util.excel.ExcelUtils;



/**
 * 
 * @author Administrator
 *导出
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class ExportExcelController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ExportExcelController.class);
	
	// 引入Service服务
	@Resource(name = "exportExcelService")
	private final ExportExcelService exportExcelService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;


	//导出
		@RequestMapping(value="/**/exportExcel{*}")  
		public String exportExcel(@RequestParam Map<String, Object> entityMap,HttpServletResponse response,HttpServletRequest request,Model mode) throws IOException { 
			String pageCode = request.getServletPath();
			String url=pageCode.substring(pageCode.lastIndexOf("/")+12,pageCode.length());
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("design_code", "query"+url);
			try {
				List<String> header = new ArrayList<String>();
				
				HrTableStruc hrTableStruc = exportExcelService.queryTabColsByCodeByExport(entityMap);
				if(hrTableStruc != null && hrTableStruc.getTab_col() != null && !"".equals(hrTableStruc.getTab_col())){
					List<Map> data = JSONArray.parseArray(hrTableStruc.getTab_col(), Map.class);
				for (Map map : data) {
					if(!map.get("col_code").toString().toLowerCase().equals("group_id")&&!map.get("col_code").toString().toLowerCase().equals("hos_id")){
						header.add(map.get("col_name").toString());
					}
					
				}
				
				}
				/*header.add("预算年度");
				header.add("科目编码");
				header.add("科目名称");
				header.add("01月");
				header.add("02月");
				header.add("03月");
				header.add("04月");
				header.add("05月");
				header.add("06月");
				header.add("07月");
				header.add("08月");
				header.add("09月");
				header.add("10月");
				header.add("11月");
				header.add("12月");*/
				
				List<List<String>> list = exportExcelService.queryExportData(entityMap);
				String filename = hrTableStruc.getTab_name()+".xlsx";
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
				ExcelUtils.getInstance().exportObjects2Excel(list, header,response.getOutputStream());
				return null;
			} catch (Exception e) {
				return "{\"error\":\"导出失败\"}";
			}
		}
}