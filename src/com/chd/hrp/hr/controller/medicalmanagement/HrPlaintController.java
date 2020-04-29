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
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.entity.medicalmanagement.HrPlaint;
import com.chd.hrp.hr.service.medicalmanagement.HrPlaintService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_Plaint 投诉登记表
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/medicalsafety")
public class HrPlaintController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrPlaintController.class);
	
	//引入Service服务
	@Resource(name = "hrPlaintService")
	private final HrPlaintService hrPlaintService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrPlaintMainPage", method = RequestMethod.GET)
	public String hrPlaintMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/plaint/plaintMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addPlaint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrPlaint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("is_commit", 0);
		try {
			String hrPlaintJson = hrPlaintService.addPlaint(mapVo);

			return JSONObject.parseObject(hrPlaintJson);
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
	@RequestMapping(value = "/deletePlaint", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrPlaint(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrPlaint> listVo = JSONArray.parseArray(paramVo, HrPlaint.class);
		
		try {
		/*	for (HrPlaint hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
				if (hrDrugPerm.getIs_commit() != null && hrDrugPerm.getIs_commit()!=0) {

					return ("{\"error\":\"删除失败!请选择新建状态的投诉单\",\"state\":\"false\"}");
				}
			}
			*/
			  return hrPlaintService.deletePlaint(listVo);
			
					
		} catch (Exception e) {
			
			logger.error(e.getMessage(),e);
			
			throw new SysException("{\"error\":\"" + e.getMessage() + "\"}");
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
	@RequestMapping(value = "/queryPlaint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrPlaint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrPlaint = hrPlaintService.queryPlaint(getPage(mapVo));

		return JSONObject.parseObject(hrPlaint);
		
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importPlaint", method = RequestMethod.POST)
	@ResponseBody
	public String importPlaint(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrPlaintService.importPlaint(mapVo);
		return reJson;
	}
 
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmPlaint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPlaint(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrPlaint> listVo = JSONArray.parseArray(paramVo, HrPlaint.class);
			String msg = hrPlaintService.confirmPlaint(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmPlaint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmPlaint(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			List<HrPlaint> listVo = JSONArray.parseArray(paramVo, HrPlaint.class);
			String msg = hrPlaintService.reConfirmPlaint(listVo);
			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}

