/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.medicalmanagement;
import java.util.*;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.entity.medicalmanagement.HrDeal;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.medicalmanagement.HrDealService;
import com.chd.hrp.hr.service.medicalmanagement.HrResearchService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_DEAL 投诉处理结果表
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value = "/hrp/hr/privilegemanagement/medicalsafety")
public class HrDealController extends BaseController{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrDealController.class);

	// 引入Service服务
	@Resource(name = "hrDealService")
	private final HrDealService hrDealService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	// 引入Service服务
		@Resource(name = "hrResearchService")
		private final HrResearchService hrResearchService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrDealMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/medicalmanagement/deal/dealMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDealPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {	

		return "hrp/hr/medicalmanagement/deal/dealAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDeal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeal(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("deal_date", DateUtil.dateFormat(new Date(), "yyyy-MM-dd"));
		mapVo.put("is_commit", 0);
		try {
			String hosEmpKindJson = hrDealService.addDeal(mapVo);

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
	@RequestMapping(value = "/updateDealPage", method = RequestMethod.GET)
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
		HrDeal hrDeal = new HrDeal();
	    
		hrDeal = hrDealService.queryByCode(mapVo);
		if(hrDeal !=null){
		mode.addAttribute("group_id", hrDeal.getGroup_id());
		mode.addAttribute("hos_id", hrDeal.getHos_id());
		mode.addAttribute("occ_date", hrDeal.getOcc_date());
		mode.addAttribute("plaint_date", hrDeal.getPlaint_date());
		mode.addAttribute("in_hos_no", hrDeal.getIn_hos_no());
		mode.addAttribute("deal_type", hrDeal.getDeal_type());
		mode.addAttribute("damage", hrDeal.getDamage());
		mode.addAttribute("other", hrDeal.getOther());
		mode.addAttribute("is_commit", hrDeal.getIs_commit());
		}
		return "hrp/hr/medicalmanagement/deal/dealUpdate";

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDeal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeal(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("is_commit", 0);
		try {
			String hosEmpKindJson = hrDealService.updateDeal(mapVo);

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
	@RequestMapping(value = "/deleteDeal", method = RequestMethod.POST)
	@ResponseBody

	public String deleteDeal(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		List<HrDeal> listVo = JSONArray.parseArray(paramVo, HrDeal.class);
		try {
		/*	for (HrDeal hrDeal : listVo) {
				hrDeal.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDeal.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
	
			}*/
			return	 hrDealService.deleteDeal(listVo);

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
	@RequestMapping(value = "/deleteDealDetail", method = RequestMethod.POST)
	@ResponseBody

	public String deleteDealDetail(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
		List<HrDeal> listVo = JSONArray.parseArray(paramVo, HrDeal.class);
		try {
		/*	for (HrDeal hrDeal : listVo) {
				hrDeal.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
				hrDeal.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
	
			}*/
			return	 hrDealService.deleteDealDetail(listVo);

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
	@RequestMapping(value = "/queryDeal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeal(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrDealService.queryDeal(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDealDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDealDetail(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String DealDetail = hrDealService.queryDealDetail(getPage(mapVo));

		return JSONObject.parseObject(DealDetail);

	}
	
	/**
	 * @Description 提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmDeal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmDeal(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";
			List<HrDeal> listVo = JSONArray.parseArray(paramVo, HrDeal.class);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 1);
			if (listVo.size() == 0) {
				msg = "{\"error\":\"提交失败！请选择行！\",\"state\":\"false\"}";
			} else {
				for (HrDeal hrdeal : listVo) {
					hrdeal.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrdeal.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrdeal.setIs_commit(1);
				}
				if (hrDealService.queryByIsCommit(map, listVo) > 0) {
					msg = "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
				} else {
					msg = hrDealService.confirmDealBatch(listVo);
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
	@RequestMapping(value = "/reConfirmHrDeal", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrDeal(@RequestParam String paramVo, Model mode)
			throws Exception {
		try {
			String msg = "";

			List<HrDeal> listVo = JSONArray.parseArray(paramVo, HrDeal.class);

			for (HrDeal hrdeal : listVo) {
				if (hrdeal.getIs_commit() != 0) {
					hrdeal.setIs_commit(0);
					hrdeal.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrdeal.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				} else {
					msg = "{\"error\":\"撤回失败！状态不是提交状态！\",\"state\":\"false\"}";
					return JSONObject.parseObject(msg);
				}
			}
			msg = hrDealService.reConfirmHrDealBatch(listVo);

			return JSONObject.parseObject(msg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	/**
	 * @Description 添加页面提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirmHrDealAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmHrDealAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String msg="";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		HrDeal deal = JSONArray.parseObject(JSONObject.toJSONString(mapVo).toString(), HrDeal.class);
		msg = hrDealService.confirmDeal(deal);
		
		return JSONObject.parseObject(msg);

	}

	/**
	 * @Description 添加页面取消提交
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/reConfirmHrDealAdd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reConfirmHrDealAdd(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String msg = "";
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		HrDeal deal = JSONArray.parseObject(JSONObject.toJSONString(mapVo).toString(), HrDeal.class);
		msg = hrDealService.reConfirmHrDeal(deal);
		
		return JSONObject.parseObject(msg);
	}
	//
	/**
	 * 查询投诉详细信息
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryResearchD", method = RequestMethod.POST)
	@ResponseBody
	public String queryResearch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String hrdeptnursing = hrResearchService.queryByCodeResearchD(mapVo);

		return hrdeptnursing;

	}
	
	//
	/**
	 * 查询已提交投诉纠纷住院号
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryResearchInHosNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryinHosNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());

		String json = hrDealService.queryResearchInHosNo(mapVo);
		return json;

	}
}

