/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.hr.controller.medicalmanagement;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdSum;
import com.chd.hrp.hr.service.medicalmanagement.HrFurstSummedService;

/**
 * 
 * @Description:
 * 进修总结
 * @Table:
 * HR_FURSTD_SUM
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
@RequestMapping(value = "hrp/hr/healthadministration/furstd")
public class HrFurstSummedController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrFurstSummedController.class);
	// 引入Service服务
	@Resource(name = "hrFurstSummedService")
	private final HrFurstSummedService hrFurstSummedService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFurstSummedMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/furstd/hrFurstSummedMainPage";
	}
	 /**
	  * 添加页面跳转
	  * 
	  * @param mode
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/addHrFurstSummedPage", method = RequestMethod.GET)
	 public String stationAddPage(Model mode) throws Exception {
		 return "hrp/hr/medicalmanagement/furstd/hrFurstSummedPageAdd";
	 }	 
	 
	 
	 /**
		 * @Description 添加数据
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/addFurstSummed", method = RequestMethod.POST)
		@ResponseBody
	public Map<String, Object> addFurstSummed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("is_commit", 0);
		try {
			String hosEmpKindJson = hrFurstSummedService.addFurstSummed(mapVo);

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
		@RequestMapping(value = "/updateFurstSummedPage", method = RequestMethod.GET)
		public String updateHrDepttechnologicalmanagementPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			HrFurstdSum hrFurstSummed = new HrFurstdSum();

			hrFurstSummed = hrFurstSummedService.queryByCodeFurstSummed(mapVo);
			SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			mode.addAttribute("emp_id",hrFurstSummed.getEmp_id() );
			mode.addAttribute("emp_name",hrFurstSummed.getEmp_name() );
			mode.addAttribute("app_no",hrFurstSummed.getApp_no());
			mode.addAttribute("sum_date",a.format(hrFurstSummed.getSum_date()) );
			mode.addAttribute("furstd_hos",hrFurstSummed.getFurstd_hos() );
			mode.addAttribute("teacher",hrFurstSummed.getTeacher());
			mode.addAttribute("tel",hrFurstSummed.getTel());
			mode.addAttribute("summary",hrFurstSummed.getSummary());
			mode.addAttribute("plan1",hrFurstSummed.getPlan1());
			mode.addAttribute("plan3",hrFurstSummed.getPlan3());
			mode.addAttribute("plan6",hrFurstSummed.getPlan6());
			mode.addAttribute("is_commit",hrFurstSummed.getIs_commit());
			return "hrp/hr/medicalmanagement/furstd/hrFurstSummedPageUpdate";

		}

		/**
		 * @Description 更新数据
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/updateFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateFurstSummed(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			String hosEmpKindJson = hrFurstSummedService.updateFurstSummed(mapVo);

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
		@RequestMapping(value = "/deleteFurstSummed", method = RequestMethod.POST)
		@ResponseBody

		public String deleteFurstSummed(@RequestParam String paramVo, Model mode) throws Exception {
			 String str = "";
			List<HrFurstdSum> listVo = JSONArray.parseArray(paramVo, HrFurstdSum.class);
			try {
				/*for (HrFurstdSum hrFurstSummed : listVo) {
					if (hrFurstSummed.getIs_commit()==0) {
						hrFurstSummed.setGroup_id(Integer.parseInt(SessionManager.getGroupId().toString()));
						hrFurstSummed.setHos_id(Integer.parseInt(SessionManager.getHosId().toString()));
			
					}else{
						return "{\"error\":\"删除失败！请选择新建状态删除！\",\"state\":\"false\"}";
					}
					
				}*/
				return	 hrFurstSummedService.deleteFurstSummed(listVo);

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
		@RequestMapping(value = "/queryFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryFurstSummed(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String stationTransef = hrFurstSummedService.queryFurstSummed(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);

		}

		/**
		 * @Description 提交
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/confirmHrFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> confirmHrFurstSummed(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdSum> listVo = JSONArray.parseArray(paramVo, HrFurstdSum.class);

			for (HrFurstdSum hrFurstSummed : listVo) {
				hrFurstSummed.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrFurstSummed.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				if (hrFurstSummed.getIs_commit() == 0) {
					hrFurstSummed.setIs_commit(1);
					msg = hrFurstSummedService.confirmHrFurstSummed(hrFurstSummed);
				} else {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
				}

			}

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
		@RequestMapping(value = "/reConfirmHrFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> reConfirmHrFurstSummed(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdSum> listVo = JSONArray.parseArray(paramVo, HrFurstdSum.class);

			for (HrFurstdSum hrFurstSummed : listVo) {
				if (hrFurstSummed.getIs_commit() != 0) {
					hrFurstSummed.setIs_commit(0);
					hrFurstSummed.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrFurstSummed.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					msg = hrFurstSummedService.reConfirmHrHrFurstSummed(hrFurstSummed);
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
		//审核
		@RequestMapping(value = "/auditHrFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> auditHrFurstSummed(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdSum> listVo = JSONArray.parseArray(paramVo, HrFurstdSum.class);

			for (HrFurstdSum hrFurstSummed : listVo) {
				hrFurstSummed.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrFurstSummed.setHos_id(Integer.parseInt(SessionManager.getHosId()));

				if (hrFurstSummed.getIs_commit() != null) {
					if (hrFurstSummed.getIs_commit() == 1) {
						hrFurstSummed.setIs_commit(2);
						msg = hrFurstSummedService.auditHrFurstSummed(hrFurstSummed);
					} else if (hrFurstSummed.getIs_commit() == 0) {
						msg = "{\"error\":\"审批失败！请勿先提交！\",\"state\":\"false\"}";
					} else {
						msg = "{\"error\":\"审批失败！请勿重复审批！\",\"state\":\"false\"}";
					}
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
		//销审
		@RequestMapping(value = "/unauditHrFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> unauditHrHrFurstSummed(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdSum> listVo = JSONArray.parseArray(paramVo, HrFurstdSum.class);

			for (HrFurstdSum hrFurstSummed : listVo) {
				if (hrFurstSummed.getIs_commit() == 2) {
					hrFurstSummed.setIs_commit(1);
					hrFurstSummed.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrFurstSummed.setHos_id(Integer.parseInt(SessionManager.getHosId()));

					msg = hrFurstSummedService.unauditHrHrFurstSummed(hrFurstSummed);
				} else {
					msg = "{\"error\":\"销审失败！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
		//未通过
		@RequestMapping(value = "/dispassHrFurstSummed", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> dispassHrHrFurstSummed(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrFurstdSum> listVo = JSONArray.parseArray(paramVo, HrFurstdSum.class);

			for (HrFurstdSum hrFurstSummed : listVo) {
				if (hrFurstSummed.getIs_commit() == 2) {
					hrFurstSummed.setIs_commit(3);
					hrFurstSummed.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrFurstSummed.setHos_id(Integer.parseInt(SessionManager.getHosId()));

					msg = hrFurstSummedService.dispassHrHrFurstSummed(hrFurstSummed);
				} else {
					msg = "{\"error\":\"未通过失败！\",\"state\":\"false\"}";
				}
			}

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
		/**
		 * 查询单号
		 * 
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/queryAppNo", method = RequestMethod.POST)
		@ResponseBody
		public String queryAppNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String json = hrFurstSummedService.queryAppNo(mapVo);
			return json;

		}
		/**
		 * 查询详细信息
		 * 
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/queryFurstdApp", method = RequestMethod.POST)
		@ResponseBody
		public String queryHosEmpDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}

			String hrdeptnursing = hrFurstSummedService.queryFurstdApp(mapVo);

			return hrdeptnursing;

		}
}
