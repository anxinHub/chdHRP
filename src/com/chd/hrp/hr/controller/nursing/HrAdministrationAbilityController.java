package com.chd.hrp.hr.controller.nursing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.hr.entity.nursing.HrAdministrationAbility;
import com.chd.hrp.hr.entity.nursing.HrWritten;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrAdministrationAbilityService;

/**
 * 行政能力
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrAdministrationAbilityController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAdministrationAbilityController.class);

	// 引入Service服务
	@Resource(name = "hrAdministrationAbilityService")
	private final HrAdministrationAbilityService hrAdministrationAbilityService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrAdministrationAbilityMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/administrationability/administrationAbilityMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAdministrationAbilityPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/administrationability/administrationAbilityAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAdministrationAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAdministrationAbility(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {
		
		String hrJson;
		try {
			hrJson = hrAdministrationAbilityService.addAdministrationAbility(paramVo);
		} catch (Exception e) {
			hrJson = e.getMessage();
		}
		return JSONObject.parseObject(hrJson);
        
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAdministrationAbility", method = RequestMethod.POST)
	@ResponseBody

	public String deleteAdministrationAbility(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrAdministrationAbility> listVo = JSONArray.parseArray(paramVo, HrAdministrationAbility.class);
		List<HrAdministrationAbility> entityList = new ArrayList<HrAdministrationAbility>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		try {
			for (HrAdministrationAbility hrAdministrationAbility : listVo) {
				hrAdministrationAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrAdministrationAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
			/*	if (StringUtils.isBlank(hrPeerEvaluation.getYear()) && hrPeerEvaluation.getEmp_id() == null || StringUtils.isBlank(hrPeerEvaluation.getEmp_id().toString())) {
					listVo.remove(hrPeerEvaluation);
				}*/
				if (hrAdministrationAbility.getState() != null && hrAdministrationAbility.getState()!=0) {
					falg = false;
					continue;
				}
				
				//判断主键是否为空,避免误删数据
				if(hrAdministrationAbility.getYear() == null || hrAdministrationAbility.getEmp_id() ==null){
					continue;
				}
				if("".equals(hrAdministrationAbility.getYear())|| "".equals(String.valueOf(hrAdministrationAbility.getEmp_id()))){
					continue;
				}
				
				entityList.add(hrAdministrationAbility);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			return hrAdministrationAbilityService.deleteAdministrationAbility(listVo);

		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
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
	@RequestMapping(value = "/queryAdministrationAbility", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAdministrationAbility(@RequestParam Map<String, Object> mapVo, Model mode)
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
		}
		String stationTransef = hrAdministrationAbilityService.queryAdministrationAbility(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * 查询获奖下拉表格
	 */
	@RequestMapping(value = "/queryPrize", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrize(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String stationTransef = hrAdministrationAbilityService.queryPrize(getPage(mapVo));
		return stationTransef;


	}
	/*
	* 导入数据
	*/
	@RequestMapping(value = "/importAdmin", method = RequestMethod.POST)
	@ResponseBody
	public String importAdmin(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
	String reJson = hrAdministrationAbilityService.importAdmin(mapVo);
	return reJson;
	}
}
