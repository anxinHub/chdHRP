/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wage;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccWage;
import com.chd.hrp.acc.serviceImpl.wage.AccWageServiceImpl;

/**
 * @Title. @Description. 工资套
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccWageController extends BaseController {
	private static Logger logger = Logger.getLogger(AccWageController.class);

	@Resource(name = "accWageService")
	private final AccWageServiceImpl accWageService = null;

	/**
	 * 工资套<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accwage/accWageMainPage", method = RequestMethod.GET)
	public String accWageMainPage(Model mode) throws Exception {

		return "hrp/acc/accwage/accWageMain";

	}

	/**
	 * 工资套<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwage/accWageAddPage", method = RequestMethod.GET)
	public String accWageAddPage(Model mode) throws Exception {

		return "hrp/acc/accwage/accWageAdd";

	}

	/**
	 * 工资套<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accwage/addAccWage", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> addAccWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String AccWageJson = accWageService.addAccWage(mapVo);

		return JSONObject.parseObject(AccWageJson);

	}

	/**
	 * 工资套<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accwage/queryAccWage", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String AccWage = accWageService.queryAccWage(getPage(mapVo));

		return JSONObject.parseObject(AccWage);

	}

	/**
	 * 工资套<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accwage/deleteAccWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccWage(@RequestParam(name = "paramVo") String paramVo, Model mode)
			throws Exception {
		StringBuffer wage_code = new StringBuffer();
		List<String> list = JSONArray.parseArray(paramVo, String.class);
		for (String string : list) {
			wage_code.append("'" + string + "'");
			wage_code.append(",");
		}
		wage_code.delete(wage_code.length() - 1, wage_code.length());

		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("wage_code", wage_code.toString());
		mapVo.put("list", list);
		mapVo.put("acc_year", SessionManager.getAcctYear());

		String AccWageJson;
		try {
			AccWageJson = accWageService.deleteBatchAccWage(mapVo);
		} catch (Exception e) {
			AccWageJson = e.getMessage();
			e.printStackTrace();
		}
		return JSONObject.parseObject(AccWageJson);

	}

	/**
	 * 工资套<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accwage/accWageUpdatePage", method = RequestMethod.GET)

	public String accWageUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		AccWage accWage = new AccWage();

		accWage = accWageService.queryAccWageByCode(mapVo);

		mode.addAttribute("wage_code", accWage.getWage_code());

		mode.addAttribute("wage_name", accWage.getWage_name());

		mode.addAttribute("group_id", accWage.getGroup_id());

		mode.addAttribute("hos_id", accWage.getHos_id());

		mode.addAttribute("copy_code", accWage.getCopy_code());

		mode.addAttribute("note", accWage.getNote());

		return "hrp/acc/accwage/accWageUpdate";
	}

	/**
	 * 工资套<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accwage/updateAccWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String AccWageJson = accWageService.updateAccWage(mapVo);

		return JSONObject.parseObject(AccWageJson);
	}

	/**
	 * 工资套<BR>
	 * 导入
	 */

	@RequestMapping(value = "/hrp/acc/accwage/importAccWage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccWage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String AccWageJson = accWageService.importAccWage(mapVo);

		return JSONObject.parseObject(AccWageJson);
	}

}
