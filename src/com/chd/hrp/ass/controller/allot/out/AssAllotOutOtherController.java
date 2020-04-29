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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailOther;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutOther;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailOtherService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutOtherService;
import com.chd.hrp.ass.service.card.AssCardOtherService;

/**
 * 
 * @Description: 050901 资产无偿调拨出库单主表(其他固定资产)
 * @Table: ASS_ALLOT_OUT_OTHER
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssAllotOutOtherController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAllotOutOtherController.class);

	// 引入Service服务
	@Resource(name = "assAllotOutOtherService")
	private final AssAllotOutOtherService assAllotOutOtherService = null;

	@Resource(name = "assAllotOutDetailOtherService")
	private final AssAllotOutDetailOtherService assAllotOutDetailOtherService = null;
	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/assAllotOutOtherMainPage", method = RequestMethod.GET)
	public String assAllotOutOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05038",MyConfig.getSysPara("05038"));
		
		return "hrp/ass/assother/assallotout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/assAllotOutOtherAddPage", method = RequestMethod.GET)
	public String assAllotOutOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assallotout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/saveAssAllotOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAllotOutOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
		AssAllotOutOther assBackOther = assAllotOutOtherService.queryByCode(mapVo);
		if (assBackOther != null) {
			if (assBackOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assAllotOutOtherJson = assAllotOutOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotOutOtherJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/updateConfirmAllotOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotOutOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssAllotOutOther assAllotOutOther = assAllotOutOtherService.queryByCode(mapVo);
			if (assAllotOutOther.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assAllotOutOther.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssAllotOutDetailOther> list = assAllotOutDetailOtherService.queryByAssAllotOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssAllotOutDetailOther assBackDetailOther : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("dispose_type", 31);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					AssCardOther assCardOther = assCardOtherService.queryByCode(listCardMap);
					if (assCardOther == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardOther.getDispose_type() != null && 31 == assCardOther.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailOther.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assAllotOutOther.getState() == 2) {
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
			assInMainJson = assAllotOutOtherService.updateAllotOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/assAllotOutOtherUpdatePage", method = RequestMethod.GET)
	public String assAllotOutOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutOther assAllotOutOther = new AssAllotOutOther();

		assAllotOutOther = assAllotOutOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutOther.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutOther.getHos_id());
		mode.addAttribute("copy_code", assAllotOutOther.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutOther.getAllot_out_no());
		mode.addAttribute("out_store_id", assAllotOutOther.getOut_store_id());
		mode.addAttribute("out_store_no", assAllotOutOther.getOut_store_no());
		mode.addAttribute("out_store_code", assAllotOutOther.getOut_store_code());
		mode.addAttribute("out_store_name", assAllotOutOther.getOut_store_name());
		mode.addAttribute("in_group_id", assAllotOutOther.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutOther.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutOther.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutOther.getIn_hos_name());
		mode.addAttribute("in_store_id", assAllotOutOther.getIn_store_id());
		mode.addAttribute("in_store_no", assAllotOutOther.getIn_store_no());
		mode.addAttribute("in_store_code", assAllotOutOther.getIn_store_code());
		mode.addAttribute("in_store_name", assAllotOutOther.getIn_store_name());
		mode.addAttribute("price", assAllotOutOther.getPrice());
		mode.addAttribute("add_depre", assAllotOutOther.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutOther.getCur_money());
		mode.addAttribute("fore_money", assAllotOutOther.getFore_money());
		mode.addAttribute("create_emp", assAllotOutOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutOther.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutOther.getAudit_date());
		mode.addAttribute("state", assAllotOutOther.getState());
		mode.addAttribute("state_name", assAllotOutOther.getState_name());
		mode.addAttribute("note", assAllotOutOther.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05038",MyConfig.getSysPara("05038"));
		
		return "hrp/ass/assother/assallotout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/deleteAssAllotOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssAllotOutOther assAllotOutOther = assAllotOutOtherService.queryByCode(mapVo);
			if (assAllotOutOther != null) {
				if (assAllotOutOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assAllotOutOtherJson = assAllotOutOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（其他固定资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/queryAssAllotOutOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assAllotOutOther = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			assAllotOutOther = assAllotOutOtherService.query(getPage(mapVo));
		}else{
			assAllotOutOther = assAllotOutOtherService.queryDetails(getPage(mapVo));
		}
		

		return JSONObject.parseObject(assAllotOutOther);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/deleteAssAllotOutDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_card_no", ids[4]);
			AssAllotOutOther assAllotOutOther = assAllotOutOtherService.queryByCode(mapVo);
			if (assAllotOutOther != null) {
				if (assAllotOutOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assAllotOutDetailOtherJson = assAllotOutDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/queryAssAllotOutDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailOther = assAllotOutDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailOther);

	}

	/**
	 * 其他固定资产 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assallotout/queryAssAllotOutOtherState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutOtherState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 入库单状态查询 （打印时校验数据专用）
		List<String> list = assAllotOutOtherService.queryAssAllotOutOtherState(mapVo);

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
