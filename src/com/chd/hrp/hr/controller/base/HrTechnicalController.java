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
import com.chd.hrp.hr.entity.base.HrTechnical;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.base.HrTechnicalService;
/**
 * 
 * @Description:
 * 技术等级
 * @Table:
 * HR_TECHNICAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Controller
@RequestMapping(value = "/hrp/hr/base")
public class HrTechnicalController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrTechnicalController.class);
	
	//引入Service服务
	@Resource(name = "hrTechnicalService")
	private final HrTechnicalService hrTechnicalService = null;
   
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/technicalLevelMainPage", method = RequestMethod.GET)
	public String hrTechnicalMainPage(Model mode) throws Exception {

		return "hrp/hr/base/hrtechnical/hrTechnicalMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/addTechnicalLevelPage", method = RequestMethod.GET)
	public String hrTechnicalAddPage(Model mode) throws Exception {

		return "hrp/hr/base/hrtechnical/hrTechnicalAdd";

	}

	/**
	 * @Description 
	 * 添加数据 技术等级
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addTechnicalLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrTechnical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("technical_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("technical_name").toString()));

			String hrTechnicalJson = hrTechnicalService.add(mapVo);

			return JSONObject.parseObject(hrTechnicalJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
/**
	 * @Description 
	 * 更新跳转页面 技术等级
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateTechnicalLevelPage", method = RequestMethod.GET)
	public String hrTechnicalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrTechnical hrTechnical = new HrTechnical();
    
		hrTechnical = hrTechnicalService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", hrTechnical.getGroup_id());
		mode.addAttribute("hos_id", hrTechnical.getHos_id());
		//mode.addAttribute("copy_code", hrTechnical.getCopy_code());
		mode.addAttribute("technical_code", hrTechnical.getTechnical_code());
		mode.addAttribute("technical_name", hrTechnical.getTechnical_name());
		mode.addAttribute("is_stop", hrTechnical.getIs_stop());
		mode.addAttribute("spell_code", hrTechnical.getSpell_code());
		mode.addAttribute("wbx_code", hrTechnical.getWbx_code());
		mode.addAttribute("note", hrTechnical.getNote());
		
		return "hrp/hr/base/hrtechnical/hrTechnicalUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 技术等级
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateTechnicalLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrTechnical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("technical_name").toString()));

			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("technical_name").toString()));

			String hrTechnicalJson = hrTechnicalService.update(mapVo);

			return JSONObject.parseObject(hrTechnicalJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	 	
	/**
	 * @Description 
	 * 删除数据 技术等级
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteTechnicalLevel", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTechnical(@RequestParam String paramVo, Model mode)
			throws Exception {
		String str = "";
		boolean falg = true;
		List<HrTechnical> listVo = JSONArray.parseArray(paramVo, HrTechnical.class);
		try {
			for (HrTechnical hrTechnical : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HR_TECHNICAL", hrTechnical.getTechnical_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_TECHNICAL", hrTechnical.getTechnical_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的技术信息被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return  hrTechnicalService.deleteHrTechnical(listVo);
			
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 
	 * 查询数据 技术等级
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryTechnicalLevel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrTechnical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String hrTechnical = hrTechnicalService.query(getPage(mapVo));

		return JSONObject.parseObject(hrTechnical);
		
	}
	/**
	 * @Description 查询数据(左侧菜单) 技术等级
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTechnicalLevelTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryTechnicalLevelTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrTechnicalService.queryTechnicalLevelTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importData", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrTechnicalService.importData(mapVo);
		return reJson;
	}
}

