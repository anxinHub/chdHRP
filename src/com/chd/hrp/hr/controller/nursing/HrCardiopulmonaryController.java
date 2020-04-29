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
import com.chd.hrp.hr.entity.nursing.HrCardiopulmonary;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrCardiopulmonaryService;

/**
 * CRP考核
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrCardiopulmonaryController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCardiopulmonaryController.class);

	// 引入Service服务
	@Resource(name = "hrCardiopulmonaryService")
	private final HrCardiopulmonaryService hrCardiopulmonaryService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCardiopulmonaryMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/cardiopulmonary/cardiopulmonaryMainPage";
	}


	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addCardiopulmonary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCardiopulmonary(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {

		String hrJson;
		try {
			hrJson = hrCardiopulmonaryService.addCardiopulmonary(paramVo);
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
	@RequestMapping(value = "/deleteCardiopulmonary", method = RequestMethod.POST)
	@ResponseBody

	public String deleteCardiopulmonary(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrCardiopulmonary> listVo = JSONArray.parseArray(paramVo, HrCardiopulmonary.class);
		
		List<HrCardiopulmonary> entityList = new ArrayList<HrCardiopulmonary>();
				
		try {
			for (HrCardiopulmonary hrCardiopulmonary : listVo) {
				hrCardiopulmonary.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrCardiopulmonary.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
		/*		if (StringUtils.isBlank(hrCardiopulmonary.getYear()) && hrCardiopulmonary.getEmp_id() == null || StringUtils.isBlank(hrCardiopulmonary.getEmp_id().toString())) {
					listVo.remove(hrCardiopulmonary);
				}*/
				
				
				//判断主键是否为空,避免误删数据
				if(hrCardiopulmonary.getYear() == null || hrCardiopulmonary.getEmp_id() == null){
					continue;
				}
				if("".equals(hrCardiopulmonary.getYear()) || "".equals(hrCardiopulmonary.getEmp_id())){
					continue;
				}
			
				if (hrCardiopulmonary.getState() != null && hrCardiopulmonary.getState()!=0) {
					falg = false;
					continue;
				}
				
				entityList.add(hrCardiopulmonary);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if(entityList.size() == 0 ){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return  hrCardiopulmonaryService.deleteCardiopulmonary(listVo);

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
	@RequestMapping(value = "/queryCardiopulmonary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCardiopulmonary(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrCardiopulmonaryService.queryCardiopulmonary(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmCardiopulmonary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmCardiopulmonary(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrCardiopulmonary> listVo = JSONArray.parseArray(paramVo, HrCardiopulmonary.class);

			for (HrCardiopulmonary hrCardiopulmonary : listVo) {
				if (hrCardiopulmonary.getState() != 1) {
					hrCardiopulmonary.setState(1);
					hrCardiopulmonary.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrCardiopulmonary.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrCardiopulmonaryService.confirmCardiopulmonaryBatch(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmCardiopulmonary", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmCardiopulmonary(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrCardiopulmonary> listVo = JSONArray.parseArray(paramVo, HrCardiopulmonary.class);

			for (HrCardiopulmonary hrCardiopulmonary : listVo) {
				if (hrCardiopulmonary.getState() != 0) {
					hrCardiopulmonary.setState(0);
					hrCardiopulmonary.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrCardiopulmonary.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrCardiopulmonaryService.reConfirmCardiopulmonaryBatch(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/*
	* 导入数据
	*/
	@RequestMapping(value = "/importCPR", method = RequestMethod.POST)
	@ResponseBody
	public String importCPR(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request) throws Exception {
		
		String reJson = null;
		
		try {
			reJson = hrCardiopulmonaryService.importCPR(mapVo);
			
		} catch (Exception e) {
			reJson = e.getMessage();
			
			logger.error(e.getMessage(), e);
		}
	 
	return reJson;
	}
}
