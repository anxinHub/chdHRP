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
import com.chd.hrp.hr.entity.record.HosEmpKind;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.record.HosEmpKindService;

/**
 * 人员分类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/record")
public class HosEmpKinkController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosEmpKinkController.class);

	// 引入Service服务
	@Resource(name = "hosEmpKindService")
	private final HosEmpKindService hosEmpKindService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hosEmpKinkMainPage", method = RequestMethod.GET)
	public String hosEmpKindMainPage(Model mode) throws Exception {

		return "hrp/hr/record/hosempkind/hosEmpKindMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHosEmpKinkPage", method = RequestMethod.GET)
	public String hosEmpKindAddPage(Model mode) throws Exception {

		return "hrp/hr/record/hosempkind/hosEmpKindAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHosEmpKink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

		String hosEmpKindJson = hosEmpKindService.add(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHosEmpKinkPage", method = RequestMethod.GET)
	public String hosEmpKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		HosEmpKind hosEmpKind = new HosEmpKind();

		hosEmpKind = hosEmpKindService.queryByCode(mapVo);
		if (hosEmpKind != null) {

			mode.addAttribute("group_id", hosEmpKind.getGroup_id());
			mode.addAttribute("hos_id", hosEmpKind.getHos_id());
			mode.addAttribute("kind_code", hosEmpKind.getKind_code());
			mode.addAttribute("kind_name", hosEmpKind.getKind_name());
			mode.addAttribute("spell_code", hosEmpKind.getSpell_code());
			mode.addAttribute("wbx_code", hosEmpKind.getWbx_code());
			mode.addAttribute("is_stop", hosEmpKind.getIs_stop());
			mode.addAttribute("note", hosEmpKind.getNote());
		}
		return "hrp/hr/record/hosempkind/hosEmpKindUpdate";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHosEmpKink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

		String hosEmpKindJson = hosEmpKindService.update(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHosEmpKink", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHosEmpKind(@RequestParam String paramVo, Model mode) throws Exception {

        String str = "";
		boolean falg = true;
		List<HosEmpKind> listVo = JSONArray.parseArray(paramVo, HosEmpKind.class);
		try {
			for (HosEmpKind hosEmpKind : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HR_EMP_KIND", hosEmpKind.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_EMP_KIND", hosEmpKind.getKind_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}

			if (!falg) {
		return ("{\"error\":\"删除失败，选择的人员分类被以下业务使用：【" + str.substring(0, str.length() - 1)
		+ "】。\",\"state\":\"false\"}");
	}
			return hosEmpKindService.deleteBatchHosEmpKind(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKink", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String hosEmpKind = hosEmpKindService.query(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}
	/**
	 * @Description 查询数据(左侧菜单) 人员分类
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKinkTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosEmpKinkTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hosEmpKindService.queryHosEmpKinkTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDat", method = RequestMethod.POST)
	@ResponseBody
	public String importDat(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hosEmpKindService.importDate(mapVo);
		return reJson;
	}
}
