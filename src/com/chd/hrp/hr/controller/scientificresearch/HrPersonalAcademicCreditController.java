package com.chd.hrp.hr.controller.scientificresearch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicCredit;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.scientificresearch.HrPersonalAcademicCreditService;

/**
 * 个人学术荣誉积分
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch")
public class HrPersonalAcademicCreditController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPersonalAcademicCreditController.class);

	// 引入Service服务
	@Resource(name = "hrPersonalAcademicCreditService")
	private final HrPersonalAcademicCreditService hrPersonalAcademicCreditService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPersonalAcademicCreditMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
	    
		return "hrp/hr/scientificresearch/personalacademiccredit/personalAcademicCreditMainPage";
	}
	/**
	 * 日期切换时查询满分标准
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAcadeHonor", method = RequestMethod.POST)
	@ResponseBody
	public String queryAcadeHonor(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		/*mapVo.put("year", SessionManager.getAcctYear());*/
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String entityMap =hrPersonalAcademicCreditService.queryAcadeHonorValue(mapVo);
		/*if(entityMap!=null){
			mode.addAttribute("acade_honor", entityMap.get("acade_honor"));
		}*/
		
		return entityMap;
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPersonalAcademicCreditPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/scientificresearch/personalacademiccredit/personalAcademicCreditAdd";

	}

	/**
	 * @Description 保存数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePersonalAcademicCredit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePersonalAcademicCredit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try {

			String hosEmpKindJson = hrPersonalAcademicCreditService.savePersonalAcademicCredit(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@RequestMapping(value = "/savePersonalAcadeHonor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePersonalAcadeHonor(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		try {
			String hosEmpKindJson = hrPersonalAcademicCreditService.savePersonalAcadeHonor(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonalAcademicCreditPage", method = RequestMethod.GET)
	public String updateHrDeptscientificresearchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrPersonalAcademicCredit hrdeptscientificresearch = new HrPersonalAcademicCredit();

		hrdeptscientificresearch = hrPersonalAcademicCreditService.queryByCodePersonalAcademicCredit(mapVo);
		return "hrp/hr/scientificresearch/personalacademiccredit/personalAcademicCreditUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePersonalAcademicCredit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePersonalAcademicCredit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrPersonalAcademicCreditService.updatePersonalAcademicCredit(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePersonalAcademicCredit", method = RequestMethod.POST)
	@ResponseBody

	public String deletePersonalAcademicCredit(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrPersonalAcademicCredit> listVo = JSONArray.parseArray(paramVo, HrPersonalAcademicCredit.class);
		try {
			for (HrPersonalAcademicCredit hrPersonalAcademicCredit : listVo) {
				/*str = str + hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_DUTY", hrDuty.getKind_code());*/
				hrPersonalAcademicCredit.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrPersonalAcademicCredit.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			
			
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的个人学术荣誉积分被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return hrPersonalAcademicCreditService.deletePersonalAcademicCredit(listVo);

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
	@RequestMapping(value = "/queryPersonalAcademicCredit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPersonalAcademicCredit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String stationTransef = hrPersonalAcademicCreditService.queryPersonalAcademicCredit(getPage(mapVo));
			System.out.println("controller:"+stationTransef);
		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据(左侧菜单) 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPersonalAcademicCreditTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryPersonalAcademicCreditTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrPersonalAcademicCreditService.queryPersonalAcademicCreditTree(mapVo);

	}
	/**
	 * 查询学术荣誉
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHonorsName", method = RequestMethod.POST)
	@ResponseBody
	public String queryHonorsName(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception{

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		return hrPersonalAcademicCreditService.queryHonorsName(mapVo);
	}
	/**
	 * 继承
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/inheritAcademicCredit", method = RequestMethod.POST)
	@ResponseBody
	public String inheritAcademicCredit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		try {

			return hrPersonalAcademicCreditService.copyAcademicCredit(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}
}
