/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.allot.out;

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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailSpecial;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutSpecial;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailSpecialService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutSpecialService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;

/**
 * 
 * @Description: 050901 资产无偿调拨出库单主表（专用设备）
 * @Table: ASS_ALLOT_OUT_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssAllotOutSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAllotOutSpecialController.class);

	// 引入Service服务
	@Resource(name = "assAllotOutSpecialService")
	private final AssAllotOutSpecialService assAllotOutSpecialService = null;

	@Resource(name = "assAllotOutDetailSpecialService")
	private final AssAllotOutDetailSpecialService assAllotOutDetailSpecialService = null;
	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/assAllotOutSpecialMainPage", method = RequestMethod.GET)
	public String assAllotOutSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05018",MyConfig.getSysPara("05018"));
		
		return "hrp/ass/assspecial/assallotout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/assAllotOutSpecialAddPage", method = RequestMethod.GET)
	public String assAllotOutSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/assallotout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/saveAssAllotOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAllotOutSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("create_emp", SessionManager.getUserId());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
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
		AssAllotOutSpecial assBackSpecial = assAllotOutSpecialService.queryByCode(mapVo);
		if (assBackSpecial != null) {
			if (assBackSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assAllotOutSpecialJson = assAllotOutSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotOutSpecialJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/updateConfirmAllotOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotOutSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			mapVo.put("allot_out_no", ids[3]);

			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("audit_emp", SessionManager.getUserId());
			AssAllotOutSpecial assAllotOutSpecial = assAllotOutSpecialService.queryByCode(mapVo);
			if (assAllotOutSpecial.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

			if (DateUtil.compareDate(assAllotOutSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能出库! \"}");
			}

			List<AssAllotOutDetailSpecial> list = assAllotOutDetailSpecialService.queryByAssAllotOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssAllotOutDetailSpecial assBackDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("dispose_type", 31);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					AssCardSpecial assCardSpecial = assCardSpecialService.queryByCode(listCardMap);
					if (assCardSpecial == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardSpecial.getDispose_type() != null && 31 == assCardSpecial.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能退库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复退货! \"}");
		}

		try {
			assInMainJson = assAllotOutSpecialService.updateAllotOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/assAllotOutSpecialUpdatePage", method = RequestMethod.GET)
	public String assAllotOutSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutSpecial assAllotOutSpecial = new AssAllotOutSpecial();

		assAllotOutSpecial = assAllotOutSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutSpecial.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutSpecial.getHos_id());
		mode.addAttribute("copy_code", assAllotOutSpecial.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutSpecial.getAllot_out_no());
		mode.addAttribute("out_store_id", assAllotOutSpecial.getOut_store_id());
		mode.addAttribute("out_store_no", assAllotOutSpecial.getOut_store_no());
		mode.addAttribute("out_store_code", assAllotOutSpecial.getOut_store_code());
		mode.addAttribute("out_store_name", assAllotOutSpecial.getOut_store_name());
		mode.addAttribute("in_group_id", assAllotOutSpecial.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutSpecial.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutSpecial.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutSpecial.getIn_hos_name());
		mode.addAttribute("in_store_id", assAllotOutSpecial.getIn_store_id());
		mode.addAttribute("in_store_no", assAllotOutSpecial.getIn_store_no());
		mode.addAttribute("in_store_code", assAllotOutSpecial.getIn_store_code());
		mode.addAttribute("in_store_name", assAllotOutSpecial.getIn_store_name());
		mode.addAttribute("price", assAllotOutSpecial.getPrice());
		mode.addAttribute("add_depre", assAllotOutSpecial.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutSpecial.getCur_money());
		mode.addAttribute("fore_money", assAllotOutSpecial.getFore_money());
		mode.addAttribute("create_emp", assAllotOutSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutSpecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutSpecial.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutSpecial.getAudit_date());
		mode.addAttribute("state", assAllotOutSpecial.getState());
		mode.addAttribute("state_name", assAllotOutSpecial.getState_name());
		mode.addAttribute("note", assAllotOutSpecial.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05018",MyConfig.getSysPara("05018"));
		
		return "hrp/ass/assspecial/assallotout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/deleteAssAllotOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_out_no", ids[3]);

			AssAllotOutSpecial assAllotOutSpecial = assAllotOutSpecialService.queryByCode(mapVo);
			if (assAllotOutSpecial != null) {
				if (assAllotOutSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assAllotOutSpecialJson = assAllotOutSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（专用设备）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/queryAssAllotOutSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assAllotOutSpecial = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			
			 assAllotOutSpecial = assAllotOutSpecialService.query(getPage(mapVo));
			
		}else{
			
			assAllotOutSpecial = assAllotOutSpecialService.queryDetails(getPage(mapVo));
			
			
		}
		

		return JSONObject.parseObject(assAllotOutSpecial);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/deleteAssAllotOutDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutDetailSpecial(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("allot_out_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			AssAllotOutSpecial assAllotOutSpecial = assAllotOutSpecialService.queryByCode(mapVo);
			if (assAllotOutSpecial != null) {
				if (assAllotOutSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assAllotOutDetailSpecialJson = assAllotOutDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/queryAssAllotOutDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailSpecial = assAllotOutDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailSpecial);

	}

	/**
	 * 专用设备 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assallotout/queryAssAllotOutSpecialState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutSpecialState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 入库单状态查询 （打印时校验数据专用）
		List<String> list = assAllotOutSpecialService.queryAssAllotOutSpecialState(mapVo);

		if (list.size() == 0) {

			return JSONObject.parseObject("{\"state\":\"true\"}");

		} else {

			String str = "";
			for (String item : list) {

				str += item + ",";
			}

			return JSONObject.parseObject("{\"error\":\"出库单【" + str + "】不是确认状态不能打印.\",\"state\":\"false\"}");
		}

	}

}
