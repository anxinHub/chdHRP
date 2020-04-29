package com.chd.hrp.hr.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.orangnize.HosStation;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.QueryService;



/**
 * 
 * 默认拦截路径
 * nm	护理管理
 * os	组织架构
 * pf	人事档案
 * pt	人事调动
 * am	考勤管理
 * srm	科研管理
 * em	教学管理
 * tm	培训管理
 * mm	医务管理
 * hm	院办管理
 * pom	党办管理
 * lm	后勤管理
 * sm	薪资管理
 * cm	合同管理
 * rm	招聘管理
 * 
 * 此路径为人力系统所有业务处理路径根目录 对应hr_table_type表数据 
 * 如果有新增数据 也在此处增加默认
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/{nm|os|pf|pt|am|srm|em|tm|mm|hm|pom|lm|sm|cm|rm}/")
public class QueryController  extends BaseController{
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(QueryController.class);
	
	// 引入Service服务
	@Resource(name = "queryService")
	private final QueryService queryService = null;


	/**
	 * @Description 通用query拦截 示例： servletPath = hrp/hr/os/basestation/queryStationBasic.do
	 * @param rjt(return json type) 示例： rjt = grid 返回表格JSON
	 *                                    rjt = tree 返回树形JSON
	 * @param pagePath  示例: pagePath  = StationBasic
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping(value = "{*}/query{pagePath}", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String query(@PathVariable("pagePath")String pagePath,@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode) throws Exception {
		
		//mode中放入一些基本参数 如果页面使用可以随时调用
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		//组装design_code
         String servletPath = request.getServletPath();//       servletPath示例：hrp/hr/os/basestation/baseStationMainPage.do
		
 		servletPath = servletPath.substring(servletPath.lastIndexOf("/")+1, servletPath.length());//    servletPath示例：hrp/hr/os/basestation/baseStationMain
		mapVo.put("design_code",servletPath);  		
		String json = null;
		
		//判断返回类型
		String rjt = "grid";//rjt 缺省参数
		
		if(mapVo.get("rjt") != null)rjt = mapVo.get("rjt").toString();
		
		
		if("tree".equals(rjt)) {
			json = queryService.queryTree(mapVo);
			//json=json.toLowerCase();
		}
		
		if("grid".equals(rjt)) json = queryService.query(getPage(mapVo));
		//返回普通json数据
		if("json".equals(rjt)) json=queryService.queryJson(mapVo);
		//返回普通json数据
				if("column".equals(rjt)) json=queryService.queryHrExcelColumn(mapVo);
		
		//返回普通jsonDetail数据 一览统计
		if("jsonDetail".equals(rjt)) json=queryService.queryJsonDetail(mapVo);
		return json;
		

	}
	/**
	 * 导入功能动态表头
	 * 
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrExcelColumn", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrExcelColumn(@RequestParam Map<String, Object> mapVo) throws Exception {
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String formEle = queryService.queryHrExcelColumn(mapVo);
		
		return formEle;
	}	
}