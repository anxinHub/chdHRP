/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;

import java.util.ArrayList;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrTechDocWork;
import com.chd.hrp.hr.service.medicalmanagement.HrTechDocWorkService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_TECH_DOC_WORK 医技医生工作量
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/workload")
public class HrTechDocWorkController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrTechDocWorkController.class);
	
	//引入Service服务
	@Resource(name = "hrTechDocWorkService")
	private final HrTechDocWorkService hrTechDocWorkService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrTechDocWorkloadMainPage", method = RequestMethod.GET)
	public String hrTechDocWorkMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/techdocworkload/techDocWorkloadMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addTechDocWorkload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrTechDocWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String hrTechDocWorkJson = null;
		
		try {
			
			hrTechDocWorkJson = hrTechDocWorkService.addTechDocWorkload(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			
			hrTechDocWorkJson = e.getMessage();
			
			logger.error(e.getMessage(),e);
		}
       

		return JSONObject.parseObject(hrTechDocWorkJson);
		
	}

	
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteTechDocWorkload", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTechDocWork(@RequestParam String paramVo, Model mode) throws Exception {
			List<HrTechDocWork> listVo = JSONArray.parseArray(paramVo, HrTechDocWork.class);
			List<HrTechDocWork> entityList = new ArrayList<HrTechDocWork>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		
		try {
			for (HrTechDocWork hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
				
				//判断主键是否为空,避免误删数据
				if(hrDrugPerm.getDept_id() == null || hrDrugPerm.getEmp_id() ==null || hrDrugPerm.getYear_month() == null){
					continue;
				}
				if("".equals(hrDrugPerm.getDept_id())|| "".equals(String.valueOf(hrDrugPerm.getEmp_id())) || "".equals(hrDrugPerm.getYear_month())){
					continue;
				}
				
				entityList.add(hrDrugPerm);
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return hrTechDocWorkService.deleteTechDocWorkload(entityList);
			
					
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
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
	@RequestMapping(value = "/queryTechDocWorkload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrTechDocWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrTechDocWork = hrTechDocWorkService.queryTechDocWorkload(getPage(mapVo));

		return JSONObject.parseObject(hrTechDocWork);
		
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHTL(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrTechDocWorkService.importTechDocWork(mapVo);
		return reJson;
	}
 
    
}

