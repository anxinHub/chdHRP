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
import com.chd.hrp.hr.entity.nursing.HrPeerEvaluation;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrPeerEvaluationService;

/**
 * 同行评价
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrPeerEvaluationController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrPeerEvaluationController.class);

	// 引入Service服务
	@Resource(name = "hrPeerEvaluationService")
	private final HrPeerEvaluationService hrPeerEvaluationService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrPeerEvaluationMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/peerevaluation/peerEvaluationMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPeerEvaluationPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/peerevaluation/peerEvaluationAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPeerEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPeerEvaluation(@RequestParam Map<String, Object> paramVo, Model mode) throws Exception {
		
		String hrJson;
		try {
			hrJson = hrPeerEvaluationService.addPeerEvaluation(paramVo);
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
	@RequestMapping(value = "/deletePeerEvaluation", method = RequestMethod.POST)
	@ResponseBody

	public String deletePeerEvaluation(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrPeerEvaluation> listVo = JSONArray.parseArray(paramVo, HrPeerEvaluation.class);
		
		List<HrPeerEvaluation> entityList = new ArrayList<HrPeerEvaluation>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		try {
			for (HrPeerEvaluation hrPeerEvaluation : listVo) {
				hrPeerEvaluation.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrPeerEvaluation.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
			/*	if (StringUtils.isBlank(hrPeerEvaluation.getYear()) && hrPeerEvaluation.getEmp_id() == null || StringUtils.isBlank(hrPeerEvaluation.getEmp_id().toString())) {
					listVo.remove(hrPeerEvaluation);
				}*/
				if (hrPeerEvaluation.getState() != null && hrPeerEvaluation.getState()!=0) {
					falg = false;
					continue;
				}
				
				//判断主键是否为空,避免误删数据
				if(hrPeerEvaluation.getYear() == null || hrPeerEvaluation.getEmp_id() ==null){
					continue;
				}
				if("".equals(hrPeerEvaluation.getYear())|| "".equals(String.valueOf(hrPeerEvaluation.getEmp_id()))){
					continue;
				}
				
				entityList.add(hrPeerEvaluation);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			
			return  hrPeerEvaluationService.deletePeerEvaluation(entityList);

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
	@RequestMapping(value = "/queryPeerEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPeerEvaluation(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrPeerEvaluationService.queryPeerEvaluation(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 * @author yangyunfei
	 * @data 2018-08-10
	 */
	@RequestMapping(value = "/confirmPeerEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmPeerEvaluation(@RequestParam String paramVo, Model mode)
			throws Exception {
		String msg="";
		List<HrPeerEvaluation> listVo = JSONArray.parseArray(paramVo, HrPeerEvaluation.class);
		try{
			msg = hrPeerEvaluationService.confirmPeerEvaluation(listVo);
		}catch(Exception e){
			msg = "{\"error\":\"" + e.getMessage() + "\",\"state\":\"false\"}";
		}
		return JSONObject.parseObject(msg);

	}

	/**
	 * @Description 取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmPeerEvaluation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmPeerEvaluation(@RequestParam String paramVo, Model mode)
			throws Exception {

		String msg = "";
		try {
			List<HrPeerEvaluation> listVo = JSONArray.parseArray(paramVo, HrPeerEvaluation.class);

			for (HrPeerEvaluation hrPeerEvaluation : listVo) {
				if (hrPeerEvaluation.getState() != 0) {
					hrPeerEvaluation.setState(0);
					hrPeerEvaluation.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrPeerEvaluation.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrPeerEvaluationService.reConfirmPeerEvaluationBatch(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/*
	* 导入数据
	*/
	@RequestMapping(value = "/importPEER", method = RequestMethod.POST)
	@ResponseBody
	public String importPEER(@RequestParam Map<String, Object> mapVo, Model mode,
	HttpServletRequest request) throws Exception {
	String reJson = hrPeerEvaluationService.importPEER(mapVo);
	return reJson;
	}
}
