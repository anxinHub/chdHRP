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
import com.chd.hrp.hr.entity.medicalmanagement.HrHeartDocWork;
import com.chd.hrp.hr.service.medicalmanagement.HrHeartDocWorkService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_HEART_DOC_WORK
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/workload")
public class HrHeartDocWorkController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrHeartDocWorkController.class);
	
	//引入Service服务
	@Resource(name = "hrHeartDocWorkService")
	private final HrHeartDocWorkService hrHeartDocWorkService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrHeartDocWorkloadMainPage", method = RequestMethod.GET)
	public String hrHeartDocWorkMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/heartdocworkload/heartDocWorkloadMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addHeartDocWorkload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrHeartDocWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
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

			String hrHeartDocWorkJson = hrHeartDocWorkService.addHeartDocWorkload(mapVo);

			return JSONObject.parseObject(hrHeartDocWorkJson);
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
	@RequestMapping(value = "/deleteHeartDocWorkload", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrHeartDocWork(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrHeartDocWork> listVo = JSONArray.parseArray(paramVo, HrHeartDocWork.class);
		
		try {
			/*for (HrHeartDocWork hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
			}*/
			
			
			  return hrHeartDocWorkService.deleteHeartDocWorkload(listVo);
			
					
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
	@RequestMapping(value = "/queryHeartDocWorkload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrHeartDocWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrHeartDocWork = hrHeartDocWorkService.queryHeartDocWorkload(getPage(mapVo));

		return JSONObject.parseObject(hrHeartDocWork);
		
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importHeartDocWork", method = RequestMethod.POST)
	@ResponseBody
	public String importHeartDocWork(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrHeartDocWorkService.importHeartDocWork(mapVo);
		return reJson;
	}
 
    
}

