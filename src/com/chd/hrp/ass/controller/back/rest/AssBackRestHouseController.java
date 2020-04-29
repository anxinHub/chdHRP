/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.back.rest;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailHouse;
import com.chd.hrp.ass.entity.back.rest.AssBackRestHouse;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailHouseService;
import com.chd.hrp.ass.service.back.rest.AssBackRestHouseService;
import com.chd.hrp.ass.service.card.AssCardHouseService;

/**
 * 
 * @Description: 050701 资产其他退账单主表(房屋及建筑物)
 * @Table: ASS_BACK_REST_HOUSE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBackRestHouseController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackRestHouseController.class);

	// 引入Service服务
	@Resource(name = "assBackRestHouseService")
	private final AssBackRestHouseService assBackRestHouseService = null;

	@Resource(name = "assBackRestDetailHouseService")
	private final AssBackRestDetailHouseService assBackRestDetailHouseService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/assBackRestHouseMainPage", method = RequestMethod.GET)
	public String assBackRestHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05057",MyConfig.getSysPara("05057"));
		
		return "hrp/ass/asshouse/assrestback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/assBackRestHouseAddPage", method = RequestMethod.GET)
	public String assBackRestHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assrestback/add";

	}

	/**
	 * @Description 添加数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/saveAssBackRestMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackRestMainHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());

		String yearmonth = mapVo.get("create_date").toString().substring(0, 4)
				+ mapVo.get("create_date").toString().substring(5, 7);
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		}  
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		AssBackRestHouse assBackRestHouse = assBackRestHouseService.queryByCode(mapVo);
		if (assBackRestHouse != null) {
			if (assBackRestHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackHouseJson = assBackRestHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackHouseJson);

	}

	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/updateConfirmBackRestHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmBackRestHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_back_no", ids[3]);

			mapVo.put("dispose_type", 41);

			mapVo.put("back_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());
			AssBackRestHouse assBackRestHouse = assBackRestHouseService.queryByCode(mapVo);
			if (assBackRestHouse.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assBackRestHouse.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssBackRestDetailHouse> list = assBackRestDetailHouseService.queryByAssBackNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssBackRestDetailHouse assBackRestDetailHouse : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackRestDetailHouse.getGroup_id());
					listCardMap.put("hos_id", assBackRestDetailHouse.getHos_id());
					listCardMap.put("copy_code", assBackRestDetailHouse.getCopy_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_type", 41);
					listCardMap.put("ass_card_no", assBackRestDetailHouse.getAss_card_no());
					AssCardHouse assCard = assCardHouseService.queryByCode(listCardMap);
					if (assCard == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if (assCard.getDispose_type() != null && 41 == assCard.getDispose_type()
							|| map.containsKey(assBackRestDetailHouse.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackRestDetailHouse.getAss_card_no(), assBackRestDetailHouse.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assBackRestHouse.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}

		try {
			assInMainJson = assBackRestHouseService.updateBackConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/assBackRestHouseUpdatePage", method = RequestMethod.GET)
	public String assBackRestHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackRestHouse assBackHouse = new AssBackRestHouse();

		assBackHouse = assBackRestHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackHouse.getGroup_id());
		mode.addAttribute("hos_id", assBackHouse.getHos_id());
		mode.addAttribute("copy_code", assBackHouse.getCopy_code());
		mode.addAttribute("ass_back_no", assBackHouse.getAss_back_no());
		mode.addAttribute("bus_type_code", assBackHouse.getBus_type_code());
		mode.addAttribute("bus_type_name", assBackHouse.getBus_type_name());
		mode.addAttribute("back_money", assBackHouse.getBack_money());
		mode.addAttribute("note", assBackHouse.getNote());
		mode.addAttribute("create_emp", assBackHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackHouse.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackHouse.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackHouse.getConfirm_emp_name());
		mode.addAttribute("state", assBackHouse.getState());
		mode.addAttribute("state_name", assBackHouse.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05057",MyConfig.getSysPara("05057"));
		
		return "hrp/ass/asshouse/assrestback/update";
	}

	/**
	 * @Description 删除数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/deleteAssBackRestMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_back_no", ids[3]);

			AssBackRestHouse assBackRestHouse = assBackRestHouseService.queryByCode(mapVo);
			if (assBackRestHouse != null) {
				if (assBackRestHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackHouseJson = assBackRestHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackHouseJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/queryAssBackRestMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackHouse = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){

			assBackHouse = assBackRestHouseService.query(getPage(mapVo));
			
		}else{
			
			assBackHouse = assBackRestHouseService.queryDetails(getPage(mapVo));
			
		}
		return JSONObject.parseObject(assBackHouse);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/deleteAssBackRestDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestDetailHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_back_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssBackRestHouse assBackRestHouse = assBackRestHouseService.queryByCode(mapVo);
			if (assBackRestHouse != null) {
				if (assBackRestHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);
		}

		String assBackDetailHouseJson = assBackRestDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackDetailHouseJson);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/queryAssBackRestDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackDetailHouse = assBackRestDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailHouse);

	}

	/**
	 * 状态查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assrestback/queryAssBackRestHouseStates", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 入库单状态查询 （打印时校验数据专用）
		List<String> list = assBackRestHouseService.queryAssBackRestHouseStates(mapVo);

		if (list.size() == 0) {

			return JSONObject.parseObject("{\"state\":\"true\"}");

		} else {

			String str = "";
			for (String item : list) {

				str += item + ",";
			}

			return JSONObject.parseObject(
					"{\"error\":\"退库单【" + str.substring(0, str.length() - 1) + "】不是确认状态不能打印.\",\"state\":\"false\"}");
		}

	}
}
