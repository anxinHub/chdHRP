/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrClinicDocWork;
import com.chd.hrp.hr.service.medicalmanagement.HrClinicDocWorkService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_CLINIC_DOC_WORK 临床医生工作量
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/workload")
public class HrClinicDocWorkController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrClinicDocWorkController.class);
	
	//引入Service服务
	@Resource(name = "hrClinicDocWorkService")
	private final HrClinicDocWorkService hrClinicDocWorkService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrClinicalWorkloadMainPage", method = RequestMethod.GET)
	public String hrClinicDocWorkMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/clinicalworkload/clinicalWorkloadMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addClinicalWorkload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrClinicDocWork(@RequestParam Map<String, Object> mapVo, Model mode)
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

			String hrClinicDocWorkJson = hrClinicDocWorkService.addClinicalWorkload(mapVo);

			return JSONObject.parseObject(hrClinicDocWorkJson);
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
	@RequestMapping(value = "/deleteClinicalWorkload", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrClinicDocWork(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrClinicDocWork> listVo = JSONArray.parseArray(paramVo, HrClinicDocWork.class);
		
		try {
			/*for (HrClinicDocWork hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
			}*/
			
			
			  return hrClinicDocWorkService.deleteClinicalWorkload(listVo);
			
					
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
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
	@RequestMapping(value = "/queryClinicalWorkload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrClinicDocWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrClinicDocWork = hrClinicDocWorkService.queryClinicalWorkload(getPage(mapVo));

		return JSONObject.parseObject(hrClinicDocWork);
		
	}
	
 //
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importClinicalDocWork", method = RequestMethod.POST)
	@ResponseBody
	public String importClinicalDocWork(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrClinicDocWorkService.importClinicalDocWork(mapVo);
		return reJson;
	}
    
}

