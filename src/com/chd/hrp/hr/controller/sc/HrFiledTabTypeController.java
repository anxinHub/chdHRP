package com.chd.hrp.hr.controller.sc;

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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.sc.HrFiledTabType;
import com.chd.hrp.hr.service.sc.HrFiledTabTypeService;

/**
 * 
 * @ClassName: HrFiledTabTypeController
 * @Description: 代码表分类
 * @author zn
 * @date 2017年10月17日 下午2:47:01
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrfiledtabtype")
public class HrFiledTabTypeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrFiledTabTypeController.class);
	
	// 引入Service服务
	@Resource(name="hrFiledTabTypeService")
	private final HrFiledTabTypeService hrFiledTabTypeService = null;
	 
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabTypeMainPage", method = RequestMethod.GET)
	public String hrFiledTabTypeMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrfiledtabtype/hrFiledTabTypeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabTypeAddPage", method = RequestMethod.GET)
	public String hrFiledTabTypeAddPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrfiledtabtype/hrFiledTabTypeAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiledTabTypeUpdatePage", method = RequestMethod.GET)
	public String hrFiledTabTypeUpdatePage(@RequestParam Map<String, Object> entityMap,Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiledTabType hrFiledTabType =hrFiledTabTypeService.queryByCode(entityMap);
		mode.addAttribute("group_id", hrFiledTabType.getGroup_id());
		mode.addAttribute("hos_id", hrFiledTabType.getHos_id());
		//mode.addAttribute("copy_code", hrFiledTabType.getCopy_code());
		mode.addAttribute("type_filed_code", hrFiledTabType.getType_filed_code());
		mode.addAttribute("type_filed_name", hrFiledTabType.getType_filed_name());
		mode.addAttribute("note", hrFiledTabType.getNote());
		return "hrp/hr/sc/hrfiledtabtype/hrFiledTabTypeUpdate";
	}
	/**
	 * @Description 查询数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiledTabType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFiledTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		 
		return hrFiledTabTypeService.query(mapVo);
	}
	/**
	 * @Description 添加数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrFiledTabType", method = RequestMethod.POST)
	@ResponseBody
	public String addHrFiledTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrFiledTabTypeService.add(mapVo);
	}
	/**
	 * @Description 修改数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrFiledTabType", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrFiledTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrFiledTabTypeService.update(mapVo);
	}
	/**
	 * @Description 修改数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrFiledTabType", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrFiledTabType(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrFiledTabType> listVo = JSONArray.parseArray(paramVo, HrFiledTabType.class);

		try {
			
			return hrFiledTabTypeService.deletehrFiledTabType(listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHDTT", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHDTT(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrFiledTabTypeService.importDate(mapVo);
		return reJson;
	}
}
