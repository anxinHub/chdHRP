/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrClinc;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPermDetail;
import com.chd.hrp.hr.service.medicalmanagement.HrDrugPermDetailService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DRUG_PERM_DETAIL 药品权限管理明细
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement")
@SuppressWarnings("unused")
public class HrDrugPermDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrDrugPermDetailController.class);
	
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
	@RequestMapping(value = "/hrDrugPermDetailMainPage", method = RequestMethod.GET)
	public String hrDrugPermDetailMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
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
	
		return "hrp/hr/medicalmanagement/hrdrugpermdetail/hrDrugPermDetailMain";

	}

	

	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addHrDrugPermDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrDrugPermDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		try {
			String hrDrugPermDetailJson = hrDrugPermDetailService.addDrugPermDetail(mapVo);

			return JSONObject.parseObject(hrDrugPermDetailJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

		

	
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteHrDrugPermDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHrDrugPermDetail(@RequestParam String paramVo, Model mode) throws Exception {
		String hrDrugPermDetailJson = "";
		List<HrDrugPermDetail> listVo = JSONArray.parseArray(paramVo, HrDrugPermDetail.class);
		try {
			if (listVo.size() > 0) {
				hrDrugPermDetailJson = hrDrugPermDetailService.deleteBatch(listVo);
			}

			return JSONObject.parseObject(hrDrugPermDetailJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryHrDrugPermDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrDrugPermDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String hrDrugPermDetail = hrDrugPermDetailService.queryHrDrugPermDetail(getPage(mapVo));

		return JSONObject.parseObject(hrDrugPermDetail);
		
	}
	
 
    
}

