package com.chd.hrp.hr.controller.nursing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.hr.entity.nursing.HrIntensiveCare;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrIntensiveCareService;

/**
 * 重症护理能力
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrIntensiveCareController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrIntensiveCareController.class);

	// 引入Service服务
	@Resource(name = "hrIntensiveCareService")
	private final HrIntensiveCareService hrIntensiveCareService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrIntensiveCareMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/intensivecare/intensiveCareMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addIntensiveCarePage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/intensivecare/intensiveCareAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addIntensiveCare", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIntensiveCare(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("state", 0);
		try {
			String hosEmpKindJson = hrIntensiveCareService.addIntensiveCare(mapVo);

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
	@RequestMapping(value = "/deleteIntensiveCare", method = RequestMethod.POST)
	@ResponseBody

	public String deleteIntensiveCare(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrIntensiveCare> listVo = JSONArray.parseArray(paramVo, HrIntensiveCare.class);
		
		List<HrIntensiveCare> entityList = new ArrayList<HrIntensiveCare>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		try {
			for (HrIntensiveCare hrTeachingAbility : listVo) {
				hrTeachingAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrTeachingAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
			/*	if (StringUtils.isBlank(hrPeerEvaluation.getYear()) && hrPeerEvaluation.getEmp_id() == null || StringUtils.isBlank(hrPeerEvaluation.getEmp_id().toString())) {
					listVo.remove(hrPeerEvaluation);
				}*/
			
				if (hrTeachingAbility.getState() != null && hrTeachingAbility.getState()!=0) {
					falg = false;
					continue;
				}
				
				//判断主键是否为空,避免误删数据
				if(hrTeachingAbility.getYear() == null || hrTeachingAbility.getDept_id() ==null ){
					continue;
				}
				if("".equals(hrTeachingAbility.getYear())|| "".equals(String.valueOf(hrTeachingAbility.getDept_id()))){
					continue;
				}
				
				entityList.add(hrTeachingAbility);
			}
			
			if (!falg) {
				return "{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}";
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return hrIntensiveCareService.deleteIntensiveCare(entityList);

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
	@RequestMapping(value = "/queryIntensiveCare", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIntensiveCare(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("dept_code") !=null && StringUtils.isNotBlank(mapVo.get("dept_code").toString())) {
			String dept_id_no = mapVo.get("dept_code").toString();

			mapVo.put("dept_no", dept_id_no.split("@")[0]);
			mapVo.put("dept_id", dept_id_no.split("@")[1]);
		}
		String stationTransef = hrIntensiveCareService.queryIntensiveCare(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/*
	* 导入数据
	*/
	@RequestMapping(value = "/importAdmin2", method = RequestMethod.POST)
	@ResponseBody
	public String importAdmin2(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
		try {
	String reJson = hrIntensiveCareService.importAdmin2(mapVo);
	return reJson;
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
}
