package com.chd.hrp.hr.controller.sysstruc;

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
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabType;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabTypeService;

/**
 * 
 * @ClassName: HrFiiedTabTypeController
 * @Description: 代码表分类
 * @author zn
 * @date 2017年10月17日 下午2:47:01
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrfiiedtabtype")
public class HrFiiedTabTypeController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrFiiedTabTypeController.class);
	
	// 引入Service服务
	@Resource(name="hrFiiedTabTypeService")
	private final HrFiiedTabTypeService hrFiiedTabTypeService = null;
	 
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabTypeMainPage", method = RequestMethod.GET)
	public String hrFiiedTabTypeMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrfiiedtabtype/hrFiiedTabTypeMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabTypeAddPage", method = RequestMethod.GET)
	public String hrFiiedTabTypeAddPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrfiiedtabtype/hrFiiedTabTypeAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabTypeUpdatePage", method = RequestMethod.GET)
	public String hrFiiedTabTypeUpdatePage(@RequestParam Map<String, Object> entityMap,Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiiedTabType hrFiiedTabType =hrFiiedTabTypeService.queryByCode(entityMap);
		mode.addAttribute("group_id", hrFiiedTabType.getGroup_id());
		mode.addAttribute("hos_id", hrFiiedTabType.getHos_id());
		//mode.addAttribute("copy_code", hrFiiedTabType.getCopy_code());
		mode.addAttribute("type_filed_code", hrFiiedTabType.getType_filed_code());
		mode.addAttribute("type_filed_name", hrFiiedTabType.getType_filed_name());
		mode.addAttribute("note", hrFiiedTabType.getNote());
		return "hrp/hr/sysstruc/hrfiiedtabtype/hrFiiedTabTypeUpdate";
	}
	/**
	 * @Description 查询数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiiedTabType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrFiiedTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		 
		return hrFiiedTabTypeService.query(mapVo);
	}
	/**
	 * @Description 添加数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrFiiedTabType", method = RequestMethod.POST)
	@ResponseBody
	public String addHrFiiedTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrFiiedTabTypeService.add(mapVo);
	}
	/**
	 * @Description 修改数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrFiiedTabType", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrFiiedTabType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrFiiedTabTypeService.update(mapVo);
	}
	/**
	 * @Description 修改数据
	 * @param mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrFiiedTabType", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrFiiedTabType(@RequestParam String paramVo, Model mode) throws Exception {
		List<HrFiiedTabType> listVo = JSONArray.parseArray(paramVo, HrFiiedTabType.class);

		try {
			
			return hrFiiedTabTypeService.deletehrFiiedTabType(listVo);
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
		String reJson = hrFiiedTabTypeService.importDate(mapVo);
		return reJson;
	}
}
