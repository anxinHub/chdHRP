/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dura.init;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.init.MatDuraInitDeptService;

/**
 * 
 * @Description:  耐用品科室期初登记表 
 * @Table: MAT_DURA_DEPT_REG
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatDuraInitDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(MatDuraInitDeptController.class);

	// 引入Service服务
	@Resource(name = "matDuraInitDeptService")
	private final MatDuraInitDeptService matDuraInitDeptService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/mainPage", method = RequestMethod.GET)
	public String MatDuraInitDeptMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/dura/init/dept/main";
	}

	/**
	 * @Description 查询数据  耐用品科室期初登记表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/queryMatDuraInitDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDuraInitDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		//转换日期格式
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		String matDura = matDuraInitDeptService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matDura);
	}

	/**
	 * @Description 添加数据  耐用品科室期初登记表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/saveMatDuraInitDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatDuraInitDept(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			
			matJson = matDuraInitDeptService.save(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据  耐用品科室期初登记表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/deleteMatDuraInitDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDuraInitDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String matJson;
		try {
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dept_id", ids[3]);
				mapVo.put("reg_id", ids[4]);
				mapVo.put("state", 2);  //用于判断状态审核后不能删除
				listVo.add(mapVo);
			}
			
			matJson = matDuraInitDeptService.deleteBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/auditMatDuraInitDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatDuraInitDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String matJson;
		try {
			Date date = new Date();
			String userId = SessionManager.getUserId();
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dept_id", ids[3]);
				mapVo.put("reg_id", ids[4]);
				mapVo.put("checker", userId);
				mapVo.put("check_date", date);
				mapVo.put("state", 2);
				listVo.add(mapVo);
			}
			
			matJson = matDuraInitDeptService.auditOrUnAudit(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/unAuditMatDuraInitDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatDuraInitDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String matJson;
		try {
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("dept_id", ids[3]);
				mapVo.put("reg_id", ids[4]);
				mapVo.put("checker", null);
				mapVo.put("check_date", null);
				mapVo.put("state", 1);
				listVo.add(mapVo);
			}
			
			matJson = matDuraInitDeptService.auditOrUnAudit(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 下载导入模版  耐用品科室期初登记表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/dura/init/dept/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate",
				"耐用品科室期初登记表.xls");

		return null;
	}
	
	/**
	 * @Description 导入跳转页面  耐用品科室期初登记表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/importPage", method = RequestMethod.GET)
	public String MatDuraInitDeptImportPage(Model mode) throws Exception {

		return "hrp/mat/dura/init/dept/import";
	}
	
	/**
	 * @Description 耐用品库房期初导入
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dura/init/dept/import", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> matDuraInitDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=matDuraInitDeptService.importData(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
}
