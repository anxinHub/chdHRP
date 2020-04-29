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
import com.chd.hrp.hr.entity.medicalmanagement.HrInnovation;
import com.chd.hrp.hr.service.medicalmanagement.HrInnovationService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_Innovation 创新能力
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/healthadministration/innovation")
public class HrInnovationController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrInnovationController.class);
	
	//引入Service服务
	@Resource(name = "hrInnovationService")
	private final HrInnovationService hrInnovationService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrInnovationMainPage", method = RequestMethod.GET)
	public String hrInnovationMainPage(Model mode) throws Exception {

		return "hrp/hr/medicalmanagement/innovation/innovationMainPage";

	}


	/**
	 * @Description 
	 * 添加数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addInnovation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrInnovation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String hrInnovationJson = hrInnovationService.addInnovation(mapVo);

			return JSONObject.parseObject(hrInnovationJson);
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
	@RequestMapping(value = "/deleteInnovation", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrInnovation(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
			List<HrInnovation> listVo = JSONArray.parseArray(paramVo, HrInnovation.class);
		
		try {
		/*	for (HrInnovation hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
				hrDrugPerm.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
				if (hrDrugPerm.getIs_commit()!=0) {

					return ("{\"error\":\"删除失败!请选择新建状态的\",\"state\":\"false\"}");
				}
			}*/
			
			
			  return hrInnovationService.deleteInnovation(listVo);
			
					
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
	@RequestMapping(value = "/queryInnovation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrInnovation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String hrInnovation = hrInnovationService.queryInnovation(getPage(mapVo));

		return JSONObject.parseObject(hrInnovation);
		
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importInnovation", method = RequestMethod.POST)
	@ResponseBody
	public String importInnovation(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrInnovationService.importInnovation(mapVo);
		return reJson;
	}
 
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmInnovation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmInnovation(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrInnovation> listVo = JSONArray.parseArray(paramVo, HrInnovation.class);
			for (HrInnovation hrInnovation : listVo) {
				hrInnovation.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrInnovation.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				HrInnovation innovation = hrInnovationService.queryByCode(hrInnovation);
				if (innovation != null) {
					if (hrInnovation.getIs_commit() == 0) {
						hrInnovation.setIs_commit(1);
						msg = hrInnovationService.confirmInnovation(hrInnovation);
					} else {
						msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
					}
				}
			}
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
	@RequestMapping(value = "/reConfirmInnovation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmInnovation(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrInnovation> listVo = JSONArray.parseArray(paramVo, HrInnovation.class);

			for (HrInnovation hrInnovation : listVo) {
				if (hrInnovation.getIs_commit() != 0) {
					hrInnovation.setIs_commit(0);
					hrInnovation.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrInnovation.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrInnovationService.reConfirmInnovation(hrInnovation);
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
}

