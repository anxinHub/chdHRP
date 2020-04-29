/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wagedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccWageScheme;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageSchemeItemServiceImpl;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageSchemeKindServiceImpl;
import com.chd.hrp.acc.serviceImpl.wagedata.AccWageSchemeServiceImpl;

/**
 * @Title. @Description. 工资方案
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccWageSchemeController extends BaseController {
	private static Logger logger = Logger.getLogger(AccWageSchemeController.class);

	@Resource(name = "accWageSchemeService")
	private final AccWageSchemeServiceImpl accWageSchemeService = null;

	@Resource(name = "accWageSchemeItemService")
	private final AccWageSchemeItemServiceImpl accWageSchemeItemService = null;

	@Resource(name = "accWageSchemeKindService")
	private final AccWageSchemeKindServiceImpl accWageSchemeKindService = null;

	/**
	 * 【工资管理-工资数据】：维护方案
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWageSchemeMainPage", method = RequestMethod.GET)
	public String accWageSchemeMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("wage_code", mapVo.get("node").toString().split(" ")[0]);
		mode.addAttribute("wage_name", mapVo.get("node").toString().split(" ")[1]);
		if (!"".equals(mapVo.get("scheme_code").toString()) && !"undefined".equals(mapVo.get("scheme_code").toString())
				&& null != mapVo.get("scheme_code").toString()) {
			mode.addAttribute("scheme_id", mapVo.get("scheme_code").toString());
		}
		mode.addAttribute("acc_year", mapVo.get("acc_year").toString());
		mode.addAttribute("scheme_type_code", mapVo.get("scheme_type_code").toString());
		if (!"".equals(mapVo.get("scheme_name").toString()) && !"undefined".equals(mapVo.get("scheme_name").toString())
				&& null != mapVo.get("scheme_name").toString()) {
			mode.addAttribute("scheme_code", mapVo.get("scheme_name").toString().split(" ")[0]);
			mode.addAttribute("scheme_name", mapVo.get("scheme_name").toString().split(" ")[1]);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("wage_code", mapVo.get("node").toString().split(" ")[0]);
			map.put("scheme_code", mapVo.get("scheme_name").toString().split(" ")[0]);
			AccWageScheme scheme = accWageSchemeService.queryAccWageSchemeByCode(map);
			if(scheme != null){
				mode.addAttribute("is_gzt", scheme.getIs_gzt());
			}
		}
		mode.addAttribute("page_type", mapVo.get("page_type"));
		return "hrp/acc/accwagepay/accWageSchemeMain";
	}

	/**
	 * 工资方案<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwagepay/accWageSchemeAddPage", method = RequestMethod.GET)
	public String accWageSchemeAddPage(Model mode) throws Exception {

		return "hrp/acc/accwagepay/accWageSchemeAdd";

	}

	/**
	 * 工资方案<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/addAccWageScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccWageScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("create_user", SessionManager.getUserId());
			mapVo.put("create_date", new Date());

			String accWageSchemeJson = accWageSchemeService.addAccWageScheme(mapVo);
			return JSONObject.parseObject(accWageSchemeJson);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	/**
	 * 工资方案<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String accWageScheme = accWageSchemeService.queryAccWageScheme(getPage(mapVo));

			return JSONObject.parseObject(accWageScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	/**
	 * 菜单:工资方案<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageSchemeByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageSchemeByMenu(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String accWageScheme = accWageSchemeService.queryAccWageSchemeByMenu(getPage(mapVo));

			return JSONObject.parseObject(accWageScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	/**
	 * 【工资管理-工资数据】：删除方案
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/deleteAccWageScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String accWageSchemeJson = accWageSchemeService.deleteAccWageScheme(mapVo);
			return JSONObject.parseObject(accWageSchemeJson);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}
	}

	/**
	 * 工资方案<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/accWageSchemeUpdate", method = RequestMethod.GET)
	public String accWageSchemeUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		/*
		 * AccWageScheme accWageScheme = new AccWageScheme(); AccWageScheme =
		 * AccWageSchemeService.queryAccWageSchemeByCode(mapVo);
		 * mode.addAttribute("para_code", AccWageScheme.getPara_code());
		 * mode.addAttribute("para_name", AccWageScheme.getPara_name());
		 * mode.addAttribute("group_id", AccWageScheme.getGroup_id());
		 * mode.addAttribute("hos_id", AccWageScheme.getHos_id());
		 * mode.addAttribute("copy_code", AccWageScheme.getCopy_code());
		 * mode.addAttribute("mod_code", AccWageScheme.getMod_code());
		 * mode.addAttribute("para_type", AccWageScheme.getPara_type());
		 * mode.addAttribute("para_json", AccWageScheme.getPara_json());
		 * mode.addAttribute("para_value", AccWageScheme.getPara_value());
		 * mode.addAttribute("note", AccWageScheme.getNote());
		 * mode.addAttribute("is_stop", AccWageScheme.getIs_stop());
		 */
		return "hrp/acc/accwagepay/accWageSchemeUpdate";
	}

	/**
	 * 工资方案<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accwagepay/updateAccWageScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("wage_code", "001");
			String accWageSchemeJson = accWageSchemeService.updateAccWageScheme(mapVo);
			return JSONObject.parseObject(accWageSchemeJson);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}
	}

	/**
	 * 工资方案<BR>
	 * 导入
	 */

	@RequestMapping(value = "/hrp/acc/accwagepay/importAccWageScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWageScheme(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			String accWageSchemeJson = accWageSchemeService.importAccWageScheme(mapVo);

			return JSONObject.parseObject(accWageSchemeJson);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}
	}

	/**
	 * 菜单:工资方案<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageBySchemeList", method = RequestMethod.POST)
	@ResponseBody

	public String queryAccWageBySchemeList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accWageScheme = accWageSchemeService.queryAccWageBySchemeList(getPage(mapVo));

		return accWageScheme;

	}

	/**
	 * 【工资管理-工资数据-工资录入】：维护方案-保存方案
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/setAccWageSchemeRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setAccWageSchemeRela(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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
			mapVo.put("create_user", SessionManager.getUserId());
			String accWageSchemeJson = accWageSchemeService.setAccWageSchemeRela(mapVo);
			return JSONObject.parseObject(accWageSchemeJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@RequestMapping(value = "/hrp/acc/accwagepay/setAccWageItemSumSchemeRela", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> setAccWageItemSumSchemeRela(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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

			String accWageSchemeJson = accWageSchemeService.setAccWageItemSumSchemeRela(mapVo);
			return JSONObject.parseObject(accWageSchemeJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageSchemeEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageSchemeEmpKind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String accWageScheme = accWageSchemeKindService.queryAccWageSchemeKind(getPage(mapVo));
			return JSONObject.parseObject(accWageScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}
	}

	/**
	 * 【工资管理-工资数据-工资录入】：主页面-维护方案-查询工资方案与职工分类关系
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageSKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageSKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String json = accWageSchemeKindService.queryAccWageSKind(getPage(mapVo));
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}
	}

	@RequestMapping(value = "/hrp/acc/accwagepay/queryAccWageSchemeItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageSchemeItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		try {
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String accWageScheme = accWageSchemeItemService.queryAccWageSchemeItem(getPage(mapVo));

			return JSONObject.parseObject(accWageScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	@RequestMapping(value = "/hrp/acc/accwagepay/deleteAccWageSchemeKind", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> deleteAccWageSchemeKind(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("emp_id", id.split("@")[0]);
				mapVo.put("scheme_id", id.split("@")[1]);
				listVo.add(mapVo);
			}

			String accWageScheme = accWageSchemeKindService.deleteBatchAccWageSchemeKind(listVo);
			return JSONObject.parseObject(accWageScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	/**
	 * 【工资管理-工资数据-工资录入】：维护方案-删除方案与职工关系
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/deleteAccWageSKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageSKind(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		try {
			String json = accWageSchemeKindService.deleteAccWageSKind(paramMap);
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	/**
	 * 【工资管理-工资数据】：维护方案-右侧删除
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/deleteAccWageSchemeItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWageSchemeItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		try {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("column_item", id.split("@")[0]);

				mapVo.put("scheme_id", id.split("@")[1]);

				listVo.add(mapVo);
			}

			String accWageScheme = accWageSchemeItemService.deleteBatchAccWageSchemeItem(listVo);
			return JSONObject.parseObject(accWageScheme);
		} catch (Exception e) {
			e.printStackTrace();
			return JSONObject.parseObject(e.getMessage());
		}

	}

	/**
	 * 【工资管理-工资数据-工资录入】：维护方案-保存职工分类与工资方案关系
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/saveAccWageSKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccWageSKind(@RequestParam Map<String, Object> paramMap, Model mode)
			throws Exception {
		try {
			String json = accWageSchemeKindService.saveAccWageSKind(paramMap);
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 维护方案：保存方案关联的职工
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/addBatchAccWageSchemeKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccWageSchemeKind(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("para", paramVo);
		try {
			String json = accWageSchemeService.addBatchAccWageSchemeKind(mapVo);
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 【工资管理-工资数据】：维护方案-导入工资项页面-确认
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/addBatchAccWageSchemeItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccWageSchemeItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		mapVo.put("paraValue", paramVo);

		try {

			String AccWageSchemeJson = accWageSchemeService.addBatchAccWageSchemeItem(mapVo);

			return JSONObject.parseObject(AccWageSchemeJson);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 工资方案维护：按工资套生成工资方案与职工关联关系
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/addBatchAccWageSchemeEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccWageSchemeEmpKind(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());

			String json = accWageSchemeService.initWageSchemeKindByWage(mapVo);
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 【工资管理-工资数据】：维护方案-右侧按工资套重新生成
	 */
	@RequestMapping(value = "/hrp/acc/accwagepay/addBatchAccWageSchemeItemCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAccWageSchemeItemCode(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
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

			if (mapVo.get("acc_year") == null) {

				mapVo.put("acc_year", SessionManager.getAcctYear());

			}

			String AccWageSchemeJson = accWageSchemeService.initWageSchemeItemByWage(mapVo);

			return JSONObject.parseObject(AccWageSchemeJson);

		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
}
