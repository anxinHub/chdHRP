package com.chd.hrp.hr.controller.organize;

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
import com.chd.hrp.hr.entity.orangnize.HrStationKind;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HrStationKindService;

/**
 * 岗位分类
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/organize/station")
public class HrStationKindController  extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrStationKindController.class);

	// 引入Service服务
	@Resource(name = "hrStationKindService")
	private final HrStationKindService hrStationKindService = null;	
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/stationKindMainPage", method = RequestMethod.GET)
	public String stationKindMainPage(Model mode) throws Exception {

		return "hrp/hr/organize/stationkind/stationKindMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationKindPage", method = RequestMethod.GET)
	public String addStationKindPage(Model mode) throws Exception {

		return "hrp/hr/organize/stationkind/addStationKind";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addStationKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStationKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		String hosEmpKindJson = hrStationKindService.add(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStationKindPage", method = RequestMethod.GET)
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
		HrStationKind hrStationKind = new HrStationKind();

		hrStationKind = hrStationKindService.queryByCode(mapVo);

		if ( hrStationKind!=null) {
			
			mode.addAttribute("group_id", hrStationKind.getGroup_id());
			mode.addAttribute("hos_id", hrStationKind.getHos_id());
			mode.addAttribute("kind_code", hrStationKind.getKind_code());
			mode.addAttribute("kind_name", hrStationKind.getKind_name());
			mode.addAttribute("spell_code", hrStationKind.getSpell_code());
			mode.addAttribute("wbx_code", hrStationKind.getWbx_code());
			mode.addAttribute("is_stop", hrStationKind.getIs_stop());
			mode.addAttribute("note", hrStationKind.getNote());
		}
		

		return "hrp/hr/organize/stationkind/updateStationKind";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStationKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStationKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

		String hosEmpKindJson = hrStationKindService.updateHrStationKind(mapVo);

		return JSONObject.parseObject(hosEmpKindJson);
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteStationKind", method = RequestMethod.POST)
	@ResponseBody

	public String deleteStationKind(@RequestParam String paramVo, Model mode)
			throws Exception {
		String str = "";
		boolean falg = true;
		List<HrStationKind> listVo = JSONArray.parseArray(paramVo, HrStationKind.class);
		try {
			for (HrStationKind hrStationKind : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HR_STATION_KIND", hrStationKind.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_STATION_KIND", hrStationKind.getKind_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的岗位分类被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return  hrStationKindService.deleteBatchStationKind(listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKind", method = RequestMethod.POST)
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
		String hosEmpKind = hrStationKindService.queryHrStationKind(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}
//
	/**
	 * @Description 查询数据(左侧菜单) 岗位定义
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHosEmpKindTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHosEmpKindTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrStationKindService.queryHosEmpKindTree(mapVo);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateSK", method = RequestMethod.POST)
	@ResponseBody
	public String importDateSK(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrStationKindService.importDate(mapVo);
		return reJson;
	}
}
