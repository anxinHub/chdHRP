/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hr.service.medicalmanagement.HrAuthorityViewService;
import com.chd.hrp.hr.service.medicalmanagement.HrDrugPermDetailService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM 药品权限查看
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement")
@SuppressWarnings("unused")
public class HrAuthorityViewController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrAuthorityViewController.class);
	
	//引入Service服务
	@Resource(name = "hrAuthorityViewService")
	private final HrAuthorityViewService hrAuthorityViewService = null;
   
	//引入Service服务
		@Resource(name = "hrDrugPermDetailService")
		private final HrDrugPermDetailService hrDrugPermDetailService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrAuthorityViewMainPage", method = RequestMethod.GET)
	public String hrAuthorityViewMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/hrAuthorityView/hrAuthorityViewMain";

	}

	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrAuthorityViewDetailPage", method = RequestMethod.GET)
	public String hrAuthorityViewDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			if(mapVo.get("copy_code") == null){
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
			}
	Map<String, Object>	emp=hrDrugPermDetailService.queryEmp(mapVo);
		mode.addAttribute("emp_id",emp.get("emp_id") );
		mode.addAttribute("emp_code",emp.get("emp_code") );
		mode.addAttribute("emp_name",emp.get("emp_name") );
		mode.addAttribute("dept_name",emp.get("dept_name") );
		mode.addAttribute("dept_code",emp.get("dept_code") );
		mode.addAttribute("perm_type",mapVo.get("perm_type") );
		mode.addAttribute("state", "提交".equals(mapVo.get("state"))?1:0);
		if (mapVo.get("view")!=null) {
			mode.addAttribute("view", mapVo.get("view"));
		}
	
		return "hrp/hr/medicalmanagement/hrAuthorityView/hrAuthorityViewDetailPage";

	}
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryHrAuthorityView", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrAuthorityView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
	
		String hrAuthorityView = hrAuthorityViewService.queryHrAuthorityView(getPage(mapVo));

		return JSONObject.parseObject(hrAuthorityView);
		
	}
}

