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
import com.chd.hrp.ass.entity.back.rest.AssBackRestDetailInassets;
import com.chd.hrp.ass.entity.back.rest.AssBackRestInassets;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.service.back.rest.AssBackRestDetailInassetsService;
import com.chd.hrp.ass.service.back.rest.AssBackRestInassetsService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;

/**
 * 
 * @Description: 050701 资产其他退货单主表(其他无形资产)
 * @Table: ASS_BACK_REST_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBackRestInassetsController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackRestInassetsController.class);

	// 引入Service服务
	@Resource(name = "assBackRestInassetsService")
	private final AssBackRestInassetsService assBackRestInassetsService = null;

	@Resource(name = "assBackRestDetailInassetsService")
	private final AssBackRestDetailInassetsService assBackRestDetailInassetsService = null;
	
	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/assBackRestInassetsMainPage", method = RequestMethod.GET)
	public String assBackRestInassetsMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05047",MyConfig.getSysPara("05047"));
		
		return "hrp/ass/assinassets/assrestback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/assBackRestInassetsAddPage", method = RequestMethod.GET)
	public String assBackRestInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assrestback/add";

	}

	/**
	 * @Description 添加数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/saveAssBackRestMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBackRestMainInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssBackRestInassets assBackRestInassets = assBackRestInassetsService.queryByCode(mapVo);
		if (assBackRestInassets != null) {
			if (assBackRestInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackInassetsJson = assBackRestInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackInassetsJson);

	}

	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/updateConfirmBackRestInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmBackRestInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssBackRestInassets assBackRestInassets = assBackRestInassetsService.queryByCode(mapVo);
			if (DateUtil.compareDate(assBackRestInassets.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssBackRestDetailInassets> list = assBackRestDetailInassetsService.queryByAssBackNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssBackRestDetailInassets assBackRestDetailInassets : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackRestDetailInassets.getGroup_id());
					listCardMap.put("hos_id", assBackRestDetailInassets.getHos_id());
					listCardMap.put("copy_code", assBackRestDetailInassets.getCopy_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("dispose_type", 41);
					listCardMap.put("ass_card_no", assBackRestDetailInassets.getAss_card_no());
					AssCardInassets assCard = assCardInassetsService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if (assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackRestDetailInassets.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackRestDetailInassets.getAss_card_no(), assBackRestDetailInassets.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assBackRestInassets.getState() == 2) {
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
			assInMainJson = assBackRestInassetsService.updateBackConfirm(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/assBackRestInassetsUpdatePage", method = RequestMethod.GET)
	public String assBackRestInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackRestInassets assBackInassets = new AssBackRestInassets();

		assBackInassets = assBackRestInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackInassets.getGroup_id());
		mode.addAttribute("hos_id", assBackInassets.getHos_id());
		mode.addAttribute("copy_code", assBackInassets.getCopy_code());
		mode.addAttribute("ass_back_no", assBackInassets.getAss_back_no());
		mode.addAttribute("store_id", assBackInassets.getStore_id());
		mode.addAttribute("store_no", assBackInassets.getStore_no());
		mode.addAttribute("store_code", assBackInassets.getStore_code());
		mode.addAttribute("store_name", assBackInassets.getStore_name());
		mode.addAttribute("bus_type_code", assBackInassets.getBus_type_code());
		mode.addAttribute("bus_type_name", assBackInassets.getBus_type_name());
		mode.addAttribute("back_money", assBackInassets.getBack_money());
		mode.addAttribute("note", assBackInassets.getNote());
		mode.addAttribute("create_emp", assBackInassets.getCreate_emp());
		mode.addAttribute("create_emp_name", assBackInassets.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assBackInassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("back_date", DateUtil.dateToString(assBackInassets.getBack_date(), "yyyy-MM-dd"));
		mode.addAttribute("confirm_emp", assBackInassets.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assBackInassets.getConfirm_emp_name());
		mode.addAttribute("state", assBackInassets.getState());
		mode.addAttribute("state_name", assBackInassets.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05047",MyConfig.getSysPara("05047"));
		
		return "hrp/ass/assinassets/assrestback/update";
	}

	/**
	 * @Description 删除数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/deleteAssBackRestMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssBackRestInassets assBackRestInassets = assBackRestInassetsService.queryByCode(mapVo);
			if (assBackRestInassets != null) {
				if (assBackRestInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackInassetsJson = assBackRestInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackInassetsJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/queryAssBackRestMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackInassets = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
		
			assBackInassets = assBackRestInassetsService.query(getPage(mapVo));
			
		}else{
			
			assBackInassets = assBackRestInassetsService.queryDetails(getPage(mapVo));
			
		}	
		return JSONObject.parseObject(assBackInassets);

	}

	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/deleteAssBackRestDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackRestDetailInassets(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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
			AssBackRestInassets assBackRestInassets = assBackRestInassetsService.queryByCode(mapVo);
			if (assBackRestInassets != null) {
				if (assBackRestInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);
		}

		String assBackDetailInassetsJson = assBackRestDetailInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackDetailInassetsJson);

	}

	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/queryAssBackRestDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackDetailInassets = assBackRestDetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailInassets);

	}
	
	/**
	 * 一般设备 其他退货 退货单状态查询 (批量打印 校验数据用)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assrestback/queryAssBackRestInassetsState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackRestInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assBackRestInassetsService.queryAssBackRestInassetsState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"退货单【"+str+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
	}
}
