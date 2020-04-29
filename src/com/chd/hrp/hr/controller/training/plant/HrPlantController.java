package com.chd.hrp.hr.controller.training.plant;

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
import com.chd.hrp.hr.entity.training.plant.HrPlant;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.training.plant.HrPlantService;


/**
 * 培训方式
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/training/plant")
public class HrPlantController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPlantController.class);

	// 引入Service服务
	@Resource(name = "hrPlantService")
	private final HrPlantService hrPlantService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/plantMainPage", method = RequestMethod.GET)
	public String hosDutyMainPage(Model mode) throws Exception {

		return "hrp/hr/training/plant/plantMainPage";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPlantPage", method = RequestMethod.GET)
	public String addPlantPage(Model mode) throws Exception {

		return "hrp/hr/training/plant/addPlantPage";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPlant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPlant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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

			String Plant = hrPlantService.addPlant(mapVo);

			return JSONObject.parseObject(Plant);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 更新跳转页面
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePlantPage", method = RequestMethod.GET)
	public String updatePlantPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		HrPlant hrPlant = new HrPlant();

		hrPlant = hrPlantService.queryByCode(mapVo);

		if (hrPlant != null) {
		
			mode.addAttribute("group_id", hrPlant.getGroup_id());
			mode.addAttribute("hos_id", hrPlant.getHos_id());
			mode.addAttribute("plan_id", hrPlant.getPlan_id());
			mode.addAttribute("train_type_code", hrPlant.getTrain_type_code());
			mode.addAttribute("dept_id", hrPlant.getDept_id());
			mode.addAttribute("dept_name", hrPlant.getDept_name());
			mode.addAttribute("dept_code", hrPlant.getDept_no()+"@"+hrPlant.getDept_id());
			mode.addAttribute("year", hrPlant.getYear());
			mode.addAttribute("month", hrPlant.getMonth());
			mode.addAttribute("train_title", hrPlant.getTrain_title());
			mode.addAttribute("train_way_code", hrPlant.getTrain_way_code());
			mode.addAttribute("train_object", hrPlant.getTrain_object());
			mode.addAttribute("is_exam", hrPlant.getIs_exam());
			mode.addAttribute("is_cert", hrPlant.getIs_cert());
			mode.addAttribute("credit_hour", hrPlant.getCredit_hour());
			mode.addAttribute("note", hrPlant.getNote());
			//查询是否存在发证信息
			int a= hrPlantService.queryByCodeCert(mapVo);
			mode.addAttribute("is_certa", a);
			//查询是否存在考核信息
			int b=hrPlantService.queryByCodeExam(mapVo);
			mode.addAttribute("is_exama", b);
		}

		return "hrp/hr/training/plant/updatePlantPage";
	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updatePlant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePlant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}


			String Plant = hrPlantService.updatePlant(mapVo);

			return JSONObject.parseObject(Plant);
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
	@RequestMapping(value = "/queryUserPlan", method = RequestMethod.POST)
	@ResponseBody

	public String queryUserPlan(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrPlant> listVo = JSONArray.parseArray(paramVo, HrPlant.class);
		try {
			for (HrPlant hrPlant : listVo) {
				str =hrPlantService.queryUsePlant(hrPlant);
				if (str!=0) {
					falg = false;
					continue;
				}
			}
			if(!falg){
				return (  "{\"state\":\"true\"}");
			}else{
				return ("{\"state\":\"false\"}");
			}
				
			

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePlant", method = RequestMethod.POST)
	@ResponseBody

	public String deletePlant(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrPlant> listVo = JSONArray.parseArray(paramVo, HrPlant.class);
		try {
		
			return hrPlantService.deletePlant(listVo);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletePlantBach", method = RequestMethod.POST)
	@ResponseBody

	public String deletePlantBach(@RequestParam String paramVo, Model mode) throws Exception {
		    int str = 0;
			boolean falg = true;
		List<HrPlant> listVo = JSONArray.parseArray(paramVo, HrPlant.class);
		try {
		
			return hrPlantService.deletePlantBach(listVo);

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
	@RequestMapping(value = "/queryPlant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPlant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		
		
		String hosEmpKind = hrPlantService.queryPlant(getPage(mapVo));

		return JSONObject.parseObject(hosEmpKind);

	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHTL", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHTL(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrPlantService.importDate(mapVo);
		return reJson;
	}

}
