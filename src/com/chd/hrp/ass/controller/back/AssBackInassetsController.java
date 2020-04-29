/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.back;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.back.AssBackDetailInassets;
import com.chd.hrp.ass.entity.back.AssBackInassets;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.in.AssInDetailInassets;
import com.chd.hrp.ass.entity.in.AssInMainInassets;
import com.chd.hrp.ass.service.back.AssBackDetailInassetsService;
import com.chd.hrp.ass.service.back.AssBackInassetsService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.in.AssInDetailInassetsService;
import com.chd.hrp.ass.service.in.AssInMainInassetsService;

/**
 * 
 * @Description: 050701 资产退货单主表(其他无形资产)
 * @Table: ASS_BACK_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssBackInassetsController extends BaseController {

	private static Logger logger = Logger.getLogger(AssBackInassetsController.class);

	// 引入Service服务
	@Resource(name = "assBackInassetsService")
	private final AssBackInassetsService assBackInassetsService = null;

	@Resource(name = "assBackDetailInassetsService")
	private final AssBackDetailInassetsService assBackDetailInassetsService = null;
	
	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	
	@Resource(name = "assInMainInassetsService")
	private final AssInMainInassetsService assInMainInassetsService = null;
	
	@Resource(name = "assInDetailInassetsService")
	private final AssInDetailInassetsService assInDetailInassetsService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assBackInassetsMainPage", method = RequestMethod.GET)
	public String assBackInassetsMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05045",MyConfig.getSysPara("05045"));
		
		return "hrp/ass/assinassets/assback/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assBackInassetsAddPage", method = RequestMethod.GET)
	public String assBackInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05045",MyConfig.getSysPara("05045"));
		
		return "hrp/ass/assinassets/assback/add";

	}

	
	/**
	 * 引入采购入库
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assBackMainInassetsPage", method = RequestMethod.GET)
	public String assBackMainInassetsPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assinassets/assback/assImportmain";
	}
	
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assBackMainInassetsBatchPage", method = RequestMethod.GET)
	public String assBackMainInassetsBatchPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_no", mapVo.get("store_no"));
		mode.addAttribute("store_code", mapVo.get("store_code"));
		mode.addAttribute("store_name", mapVo.get("store_name"));
		
		mode.addAttribute("ven_id", mapVo.get("ven_id"));
		mode.addAttribute("ven_no", mapVo.get("ven_no"));
		mode.addAttribute("ven_code", mapVo.get("ven_code"));
		mode.addAttribute("ven_name", mapVo.get("ven_name"));
		
		mode.addAttribute("create_date", mapVo.get("create_date"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assback/assBatchmain";
	}
	
	/**
	 * 主页面店家引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assViewBackPage", method = RequestMethod.GET)
	public String assViewBackPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("ass_back_no", mapVo.get("ass_back_no"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assinassets/assback/assInassetsViewBack";
	}
	
	/**
	 * @Description 查询数据 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/queryAssInMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assInMainSpecial  = assInMainInassetsService.queryInMap(getPage(mapVo));
			 

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * 保存入库明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/addAssBackInassetsImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackInassetsImport(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		
		String assBackInassets = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		assBackInassets = assBackInassetsService.assImportBackIn(mapVo);
		
		return JSONObject.parseObject(assBackInassets);
		
	}
	
	/**
	 * @Description 更新跳转页面 资产入库主表(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assInMainInassetsUpdatePage", method = RequestMethod.GET)
	public String assInMainInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInMainInassets assInMainSpecial = new AssInMainInassets();

		assInMainSpecial = assInMainInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assInMainSpecial.getGroup_id());
		mode.addAttribute("hos_id", assInMainSpecial.getHos_id());
		mode.addAttribute("copy_code", assInMainSpecial.getCopy_code());
		mode.addAttribute("ass_in_no", assInMainSpecial.getAss_in_no());
		mode.addAttribute("store_id", assInMainSpecial.getStore_id());
		mode.addAttribute("store_no", assInMainSpecial.getStore_no());
		mode.addAttribute("ven_id", assInMainSpecial.getVen_id());
		mode.addAttribute("ven_no", assInMainSpecial.getVen_no());
		mode.addAttribute("purc_emp", assInMainSpecial.getPurc_emp());
		mode.addAttribute("dept_id", assInMainSpecial.getDept_id());
		mode.addAttribute("dept_no", assInMainSpecial.getDept_no());
		mode.addAttribute("ass_purpose", assInMainSpecial.getAss_purpose());
		mode.addAttribute("proj_id", assInMainSpecial.getProj_id());
		mode.addAttribute("proj_no", assInMainSpecial.getProj_no());
		mode.addAttribute("store_code", assInMainSpecial.getStore_code());
		mode.addAttribute("store_name", assInMainSpecial.getStore_name());
		mode.addAttribute("ven_code", assInMainSpecial.getVen_code());
		mode.addAttribute("ven_name", assInMainSpecial.getVen_name());
		mode.addAttribute("purc_emp_name", assInMainSpecial.getPurc_emp_name());
		mode.addAttribute("dept_code", assInMainSpecial.getDept_code());
		mode.addAttribute("dept_name", assInMainSpecial.getDept_name());
		mode.addAttribute("ass_purpose_name", assInMainSpecial.getAss_purpose_name());
		mode.addAttribute("proj_code", assInMainSpecial.getProj_code());
		mode.addAttribute("proj_name", assInMainSpecial.getProj_name());
		mode.addAttribute("in_money", assInMainSpecial.getIn_money());
		mode.addAttribute("note", assInMainSpecial.getNote());
		mode.addAttribute("create_emp", assInMainSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assInMainSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assInMainSpecial.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("in_date", assInMainSpecial.getIn_date());
		mode.addAttribute("confirm_emp", assInMainSpecial.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assInMainSpecial.getConfirm_emp_name());
		mode.addAttribute("state", assInMainSpecial.getState());
		mode.addAttribute("state_name", assInMainSpecial.getState_name());
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assback/assImportUpate";
	}
	
	/**
	 * @Description 查询数据 资产入库明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/queryAssInDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInDetailSpecial = assInDetailInassetsService.query(getPage(mapVo));
		return JSONObject.parseObject(assInDetailSpecial);
	}
	
	
	@RequestMapping(value = "/hrp/ass/assInassets/assback/queryAssInCardInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		assCard = assCardInassetsService.query(getPage(mapVo));
		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				jsonarray.remove(i);
			}
		}
		json.put("Rows", jsonarray);
		return JSONObject.parseObject(json.toString());
	}
	
	
	// 点击引入查询
		@RequestMapping(value = "/hrp/ass/assinassets/assback/queryAssBackInMainInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssBackInMainInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assApplyDept = assInMainInassetsService.queryAssBackInMainInassets(getPage(mapVo));

			return JSONObject.parseObject(assApplyDept);

		}
	
	
	
	/**
	 * @Description 添加数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/saveAssBackMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBackInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssBackInassets assBackInassets = assBackInassetsService.queryByCode(mapVo);
		if (assBackInassets != null) {
			if (assBackInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能保存! \"}");
			}
		}

		String assBackInassetsJson = assBackInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assBackInassetsJson);

	}

	/**
	 * @Description 退货确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/updateConfirmBackInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			mapVo.put("back_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("dispose_type", 41);

			mapVo.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));

			mapVo.put("confirm_emp", SessionManager.getUserId());
			AssBackInassets assBackInassets = assBackInassetsService.queryByCode(mapVo);
			if (DateUtil.compareDate(assBackInassets.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			List<AssBackDetailInassets> list = assBackDetailInassetsService.queryByAssBackNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssBackDetailInassets assBackDetailInassets : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailInassets.getGroup_id());
					listCardMap.put("hos_id", assBackDetailInassets.getHos_id());
					listCardMap.put("copy_code", assBackDetailInassets.getCopy_code());
					listCardMap.put("dispose_type", 41);
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", assBackDetailInassets.getAss_card_no());
					AssCardInassets assCard = assCardInassetsService.queryByCode(listCardMap);
					if(assCard == null){
						return JSONObject.parseObject("{\"warn\":\"存在无效卡片,不能进行退货! \"}");
					}
					if (assCard.getDispose_type() !=null && 41 == assCard.getDispose_type() || map.containsKey(assBackDetailInassets.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行退货! \"}");
					}
					map.put(assBackDetailInassets.getAss_card_no(), assBackDetailInassets.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assBackInassets.getState() == 2) {
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
			assInMainJson = assBackInassetsService.updateBackConfirm(listVo, listCardVo);
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
	@RequestMapping(value = "/hrp/ass/assinassets/assback/assBackInassetsUpdatePage", method = RequestMethod.GET)
	public String assBackInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssBackInassets assBackInassets = new AssBackInassets();

		assBackInassets = assBackInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assBackInassets.getGroup_id());
		mode.addAttribute("hos_id", assBackInassets.getHos_id());
		mode.addAttribute("copy_code", assBackInassets.getCopy_code());
		mode.addAttribute("ass_back_no", assBackInassets.getAss_back_no());
		mode.addAttribute("store_id", assBackInassets.getStore_id());
		mode.addAttribute("store_no", assBackInassets.getStore_no());
		mode.addAttribute("ven_id", assBackInassets.getVen_id());
		mode.addAttribute("ven_no", assBackInassets.getVen_no());
		mode.addAttribute("store_code", assBackInassets.getStore_code());
		mode.addAttribute("store_name", assBackInassets.getStore_name());
		mode.addAttribute("ven_code", assBackInassets.getVen_code());
		mode.addAttribute("ven_name", assBackInassets.getVen_name());
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
		mode.addAttribute("ass_05045",MyConfig.getSysPara("05045"));
		
		return "hrp/ass/assinassets/assback/update";
	}

	/**
	 * @Description 删除数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/deleteAssBackMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssBackInassets assBackInassets = assBackInassetsService.queryByCode(mapVo);
			if (assBackInassets != null) {
				if (assBackInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assBackInassetsJson = assBackInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackInassetsJson);

	}

	/**
	 * @Description 查询数据 050701 资产退货单主表(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/queryAssBackMainInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackInassets = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackInassets = assBackInassetsService.query(getPage(mapVo));
			
		}else{
			
			assBackInassets = assBackInassetsService.queryDetails(getPage(mapVo));
			
		}
		 

		return JSONObject.parseObject(assBackInassets);

	}

	@RequestMapping(value = "/hrp/ass/assinassets/assback/deleteAssBackDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBackDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssBackInassets assBackInassets = assBackInassetsService.queryByCode(mapVo);
			if (assBackInassets != null) {
				if (assBackInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已退货确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);
		}

		String assBackDetailInassetsJson = assBackDetailInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assBackDetailInassetsJson);

	}

	@RequestMapping(value = "/hrp/ass/assinassets/assback/queryAssBackDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackDetailInassets = assBackDetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailInassets);

	}
	
	/**
	 * 其他无形资产 采购退货   退货单状态查询 （打印时校验数据专用）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/assback/queryAssBackInassetsState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBackInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//其他无形资产采购退货   退货单状态查询  （打印时校验数据专用）
		List<String> list = assBackInassetsService.queryAssBackInassetsState(mapVo);
		
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
