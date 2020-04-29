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
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailInassets;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutInassets;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailInassetsService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutInassetsService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;

/**
 * 
 * @Description: 050901 资产无偿调拨出库单主表(无形资产)
 * @Table: ASS_ALLOT_OUT_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssAllotOutInassetsController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAllotOutInassetsController.class);

	// 引入Service服务
	@Resource(name = "assAllotOutInassetsService")
	private final AssAllotOutInassetsService assAllotOutInassetsService = null;

	@Resource(name = "assAllotOutDetailInassetsService")
	private final AssAllotOutDetailInassetsService assAllotOutDetailInassetsService = null;

	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/assAllotOutInassetsMainPage", method = RequestMethod.GET)
	public String assAllotOutInassetsMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05048",MyConfig.getSysPara("05048"));
		
		return "hrp/ass/assinassets/assallotout/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/assAllotOutInassetsAddPage", method = RequestMethod.GET)
	public String assAllotOutInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assallotout/add";

	}

	/**
	 * @Description 添加数据 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/saveAssAllotOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAllotOutInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssAllotOutInassets assBackInassets = assAllotOutInassetsService.queryByCode(mapVo);
		if (assBackInassets != null) {
			if (assBackInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能保存! \"}");
			}
		}

		String assAllotOutInassetsJson = assAllotOutInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assAllotOutInassetsJson);

	}

	/**
	 * @Description 出库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/updateConfirmAllotOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAllotOutInassets(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
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
			AssAllotOutInassets assAllotOutInassets = assAllotOutInassetsService.queryByCode(mapVo);
			if (DateUtil.compareDate(assAllotOutInassets.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssAllotOutDetailInassets> list = assAllotOutDetailInassetsService.queryByAssAllotOutNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssAllotOutDetailInassets assBackDetailInassets : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailInassets.getGroup_id());
					listCardMap.put("hos_id", assBackDetailInassets.getHos_id());
					listCardMap.put("copy_code", assBackDetailInassets.getCopy_code());
					listCardMap.put("dispose_type", 31);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailInassets.getAss_card_no());
					AssCardInassets assCardInassets = assCardInassetsService.queryByCode(listCardMap);
					if (assCardInassets == null) {
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行出库! \"}");
					}
					if (assCardInassets.getDispose_type() != null && 31 == assCardInassets.getDispose_type()) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置卡片,不能进行出库! \"}");
					}
					if (map.containsKey(assBackDetailInassets.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行出库! \"}");
					}
					map.put(assBackDetailInassets.getAss_card_no(), assBackDetailInassets.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assAllotOutInassets.getState() == 2) {
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
			assInMainJson = assAllotOutInassetsService.updateAllotOutConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/assAllotOutInassetsUpdatePage", method = RequestMethod.GET)
	public String assAllotOutInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssAllotOutInassets assAllotOutInassets = new AssAllotOutInassets();

		assAllotOutInassets = assAllotOutInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assAllotOutInassets.getGroup_id());
		mode.addAttribute("hos_id", assAllotOutInassets.getHos_id());
		mode.addAttribute("copy_code", assAllotOutInassets.getCopy_code());
		mode.addAttribute("allot_out_no", assAllotOutInassets.getAllot_out_no());
		mode.addAttribute("out_store_id", assAllotOutInassets.getOut_store_id());
		mode.addAttribute("out_store_no", assAllotOutInassets.getOut_store_no());
		mode.addAttribute("out_store_code", assAllotOutInassets.getOut_store_code());
		mode.addAttribute("out_store_name", assAllotOutInassets.getOut_store_name());
		mode.addAttribute("in_group_id", assAllotOutInassets.getIn_group_id());
		mode.addAttribute("in_group_name", assAllotOutInassets.getIn_group_name());
		mode.addAttribute("in_hos_id", assAllotOutInassets.getIn_hos_id());
		mode.addAttribute("in_hos_name", assAllotOutInassets.getIn_hos_name());
		mode.addAttribute("in_store_id", assAllotOutInassets.getIn_store_id());
		mode.addAttribute("in_store_no", assAllotOutInassets.getIn_store_no());
		mode.addAttribute("in_store_code", assAllotOutInassets.getIn_store_code());
		mode.addAttribute("in_store_name", assAllotOutInassets.getIn_store_name());
		mode.addAttribute("price", assAllotOutInassets.getPrice());
		mode.addAttribute("add_depre", assAllotOutInassets.getAdd_depre());
		mode.addAttribute("cur_money", assAllotOutInassets.getCur_money());
		mode.addAttribute("fore_money", assAllotOutInassets.getFore_money());
		mode.addAttribute("create_emp", assAllotOutInassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assAllotOutInassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assAllotOutInassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assAllotOutInassets.getAudit_emp());
		mode.addAttribute("audit_emp_name", assAllotOutInassets.getAudit_emp_name());
		mode.addAttribute("audit_date", assAllotOutInassets.getAudit_date());
		mode.addAttribute("state", assAllotOutInassets.getState());
		mode.addAttribute("state_name", assAllotOutInassets.getState_name());
		mode.addAttribute("note", assAllotOutInassets.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05048",MyConfig.getSysPara("05048"));
		
		return "hrp/ass/assinassets/assallotout/update";
	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/deleteAssAllotOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssAllotOutInassets assAllotOutInassets = assAllotOutInassetsService.queryByCode(mapVo);
			if (assAllotOutInassets != null) {
				if (assAllotOutInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assAllotOutInassetsJson = assAllotOutInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutInassetsJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单主表（无形资产）
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/queryAssAllotOutInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());


		String assAllotOutInassets = null;

		if("0".equals(mapVo.get("show_detail").toString())){
			assAllotOutInassets = assAllotOutInassetsService.query(getPage(mapVo));
		}else{
			assAllotOutInassets = assAllotOutInassetsService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assAllotOutInassets);

	}

	/**
	 * @Description 删除数据 050901 资产无偿调拨出库单明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/deleteAssAllotOutDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAllotOutDetailInassets(@RequestParam(value = "ParamVo") String paramVo,
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
			AssAllotOutInassets assAllotOutInassets = assAllotOutInassetsService.queryByCode(mapVo);
			if (assAllotOutInassets != null) {
				if (assAllotOutInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已出库确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assAllotOutDetailInassetsJson = assAllotOutDetailInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assAllotOutDetailInassetsJson);

	}

	/**
	 * @Description 查询数据 050901 资产无偿调拨出库单明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/queryAssAllotOutDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAllotOutDetailInassets = assAllotOutDetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assAllotOutDetailInassets);

	}

	/**
	 * 其他无形资产 资产调剂出口库 出库单状态查询（打印校验数据用）
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assallotout/queryAssAllotOutInassetsState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAllotOutInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		// 入库单状态查询 （打印时校验数据专用）
		List<String> list = assAllotOutInassetsService.queryAssAllotOutInassetsState(mapVo);

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
