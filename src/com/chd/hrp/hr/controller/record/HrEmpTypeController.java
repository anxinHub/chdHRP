/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.controller.record;

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
import com.chd.hrp.hr.entity.record.HrEmpType;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.record.HrEmpTypeService;
/**
 * 
 * @Description: 人员类别
 * @Table: HR_EMP_TYPE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
@RequestMapping(value = "/hrp/hr/record")
public class HrEmpTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(HrEmpTypeController.class);

	// 引入Service服务
	@Resource(name = "hrEmpTypeService")
	private final HrEmpTypeService hrEmpTypeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrEmpTypeMainPage", method = RequestMethod.GET)
	public String hrEmpTypeMainPage(Model mode) throws Exception {

		return "hrp/hr/record/hremptype/hrEmpTypeMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrEmpTypePage", method = RequestMethod.GET)
	public String hrEmpTypeAddPage(Model mode) throws Exception {

		return "hrp/hr/record/hremptype/hrEmpTypeAdd";

	}

	/**
	 * @Description 添加数据 人员类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrEmpType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHrEmpType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

		String hrEmpTypeJson = hrEmpTypeService.add(mapVo);

		return JSONObject.parseObject(hrEmpTypeJson);

	}

	/**
	 * @Description 更新跳转页面 人员类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrEmpTypePage", method = RequestMethod.GET)
	public String hrEmpTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrEmpType hrEmpType = new HrEmpType();

		hrEmpType = hrEmpTypeService.queryByCode(mapVo);
		if (hrEmpType != null) {
			mode.addAttribute("group_id", hrEmpType.getGroup_id());
			mode.addAttribute("hos_id", hrEmpType.getHos_id());
			//mode.addAttribute("copy_code", hrEmpType.getCopy_code());
			mode.addAttribute("type_code", hrEmpType.getType_code());
			mode.addAttribute("type_name", hrEmpType.getType_name());
			mode.addAttribute("is_stop", hrEmpType.getIs_stop());
			mode.addAttribute("spell_code", hrEmpType.getSpell_code());
			mode.addAttribute("wbx_code", hrEmpType.getWbx_code());
			mode.addAttribute("note", hrEmpType.getNote());
		}

		return "hrp/hr/record/hremptype/hrEmpTypeUpdate";
	}

	/**
	 * @Description 更新数据 人员类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrEmpType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrEmpType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));

		String hrEmpTypeJson = hrEmpTypeService.update(mapVo);

		return JSONObject.parseObject(hrEmpTypeJson);
	}

	/**
	 * @Description 删除数据 人员类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrEmpType", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrEmpType(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrEmpType> listVo = JSONArray.parseArray(paramVo, HrEmpType.class);

		try {
			for (HrEmpType hrEmpType : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HOS_EMP", hrEmpType.getType_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HOS_EMP", hrEmpType.getType_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的人员类别被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return hrEmpTypeService.deleteHrEmpType(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 查询数据 人员类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrEmpType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrEmpType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String hrEmpType = hrEmpTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(hrEmpType);

	}

	/**
	 * @Description 查询数据(左侧菜单) 人员类别
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrEmpTypeTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosEmpTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrEmpTypeService.queryHosEmpTypeTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDate", method = RequestMethod.POST)
	@ResponseBody
	public String importDate(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrEmpTypeService.importDate(mapVo);
		return reJson;
	}

}
