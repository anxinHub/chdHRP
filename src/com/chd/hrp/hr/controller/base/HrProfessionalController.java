/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.base;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.base.HrProfessional;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrProfessionalService;
/**
 * 
 * @Description:
 * 专业信息
 * @Table:
 * HR_PROFESSIONAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/base")
public class HrProfessionalController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrProfessionalController.class);
	
	//引入Service服务
	@Resource(name = "hrProfessionalService")
	private final HrProfessionalService hrProfessionalService = null;
	
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/professionalInfoMainPage", method = RequestMethod.GET)
	public String hrProfessionalMainPage(Model mode) throws Exception {

		return "hrp/hr/base/hrprofessional/hrProfessionalMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/addProfessionalInfoPage", method = RequestMethod.GET)
	public String hrProfessionalAddPage(Model mode) throws Exception {

		return "hrp/hr/base/hrprofessional/hrProfessionalAdd";

	}

	/**
	 * @Description 
	 * 添加数据 专业信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addProfessionalInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrProfessional(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("professional_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("professional_name").toString()));
       
	
		String hrJson;
		try {
			hrJson = hrProfessionalService.add(mapVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);
        
	}
/**
	 * @Description 
	 * 更新跳转页面 专业信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateProfessionalInfoPage", method = RequestMethod.GET)
	public String hrProfessionalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		HrProfessional hrProfessional = new HrProfessional();
    
		hrProfessional = hrProfessionalService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", hrProfessional.getGroup_id());
		mode.addAttribute("hos_id", hrProfessional.getHos_id());
		mode.addAttribute("professional_code", hrProfessional.getProfessional_code());
		mode.addAttribute("professional_name", hrProfessional.getProfessional_name());
		mode.addAttribute("is_stop", hrProfessional.getIs_stop());
		mode.addAttribute("spell_code", hrProfessional.getSpell_code());
		mode.addAttribute("wbx_code", hrProfessional.getWbx_code());
		mode.addAttribute("note", hrProfessional.getNote());
		
		return "hrp/hr/base/hrprofessional/hrProfessionalUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 专业信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateProfessionalInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrProfessional(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("professional_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("professional_name").toString()));

			String hrProfessionalJson = hrProfessionalService.update(mapVo);

			return JSONObject.parseObject(hrProfessionalJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
	/**
	 * @Description 
	 * 删除数据 专业信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteProfessionalInfo", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrProfessional(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrProfessional> listVo= JSONArray.parseArray(paramVo, HrProfessional.class);
		
	    try {
	    	for (HrProfessional hrProfessional : listVo) {
	    		str = str + hrBaseService.isExistsDataByTable("HR_PROFESSIONAL", hrProfessional.getProfessional_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_PROFESSIONAL", hrProfessional.getProfessional_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			
			}
	    	if (!falg) {
				return ("{\"error\":\"删除失败，选择的专业信息被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
	    	return  hrProfessionalService.deleteHrProfessional(listVo);
	    }catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
			
	}
	
	/**
	 * @Description 
	 * 查询数据 专业信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryHrProfessional", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrProfessional(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
	
		String hrProfessional = hrProfessionalService.query(getPage(mapVo));

		return JSONObject.parseObject(hrProfessional);
		
	}
	/**
	 * @Description 查询数据(左侧菜单) 专业信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrProfessionalTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrProfessionalTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrProfessionalService.queryHrProfessionalTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHPF", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHPF(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrProfessionalService.importDate(mapVo);
		return reJson;
	}
  
}

