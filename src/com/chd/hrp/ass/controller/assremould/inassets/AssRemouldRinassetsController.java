/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.assremould.inassets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldRdetailInassets;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldRinassets;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldADetailInassetsService;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldAinassetsService;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldRDetailInassetsService;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldRinassetsService;

/**
 * 
 * @Description: 050805 资产改造记录(无形资产)
 * @Table: ASS_REMOULD_R_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssRemouldRinassetsController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRemouldRinassetsController.class);

	// 引入Service服务
	@Resource(name = "assRemouldRinassetsService")
	private final AssRemouldRinassetsService assRemouldRinassetsService = null;
	// 引入Service服务
	@Resource(name = "assRemouldRDetailInassetsService")
	private final AssRemouldRDetailInassetsService assRemouldRDetailInassetsService = null;

	// 引入Service服务
	@Resource(name = "assRemouldAinassetsService")
	private final AssRemouldAinassetsService assRemouldAinassetsService = null;

	// 引入Service服务
	@Resource(name = "assRemouldADetailInassetsService")
	private final AssRemouldADetailInassetsService assRemouldADetailInassetsService = null;


	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assRemouldRinassetsMainPage", method = RequestMethod.GET)
	public String assRemouldRinassetsMainPage(Model mode) throws Exception {

		return "hrp/ass/assinassets/assremould/assremouldrinassets/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assRemouldRinassetsAddPage", method = RequestMethod.GET)
	public String assRemouldRinassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assremould/assremouldrinassets/add";

	}
	/**
	 * @Description 引入申请页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       mode.addAttribute("create_date", mapVo.get("create_date"));
			mode.addAttribute("note", mapVo.get("note"));
			mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		return "hrp/ass/assinassets/assremould/assremouldrinassets/Import";

	}
	/**
	 * @Description 查询改造申请数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/queryAssRemouldAspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldAinassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRemouldAinassets = assRemouldAinassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAinassets);

	}
	
	/**
	 * @Description 查看申报信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assRemouldAspecialUpdatePage", method = RequestMethod.GET)
	public String assRemouldAinassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		Map<String,Object> assRemouldAinassetsMap = assRemouldADetailInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldAinassetsMap.get("group_id"));
		mode.addAttribute("hos_id", assRemouldAinassetsMap.get("hos_id"));
		mode.addAttribute("copy_code", assRemouldAinassetsMap.get("copy_code"));
		mode.addAttribute("apply_no", assRemouldAinassetsMap.get("apply_no"));
		mode.addAttribute("bus_type_code", assRemouldAinassetsMap.get("bus_type_code"));
		mode.addAttribute("bus_type_name", assRemouldAinassetsMap.get("bus_type_name"));
		mode.addAttribute("ven_id", assRemouldAinassetsMap.get("ven_id"));
		mode.addAttribute("ven_no", assRemouldAinassetsMap.get("ven_no"));
		mode.addAttribute("sup_code", assRemouldAinassetsMap.get("sup_code"));
		mode.addAttribute("sup_name", assRemouldAinassetsMap.get("sup_name"));
		mode.addAttribute("create_emp", assRemouldAinassetsMap.get("create_emp"));
		mode.addAttribute("create_date", assRemouldAinassetsMap.get("create_date"));
		mode.addAttribute("create_emp_name", assRemouldAinassetsMap.get("create_emp_name"));
		mode.addAttribute("audit_emp_name", assRemouldAinassetsMap.get("audit_emp_name"));
		mode.addAttribute("audit_emp", assRemouldAinassetsMap.get("audit_emp"));
		mode.addAttribute("change_date", assRemouldAinassetsMap.get("change_date"));
		mode.addAttribute("state", assRemouldAinassetsMap.get("state"));
		mode.addAttribute("note", assRemouldAinassetsMap.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assremould/assremouldrinassets/Viewupdate";
	}
	/**
	 * @Description 查询改造申请明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/queryAssRemouldADict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldADict(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldAinassets = assRemouldAinassetsService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAinassets);

	}
	//查询明细到记录
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/addAssPlanDeptImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		/*
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("apply_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("apply_no", ids);
		
		String assBackDetailinassets = assRemouldADetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailinassets);*/
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assRemouldRinassetsService.initAssCheckinassets(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);
	}
	
	
	/**
	 * @Description 追溯盘亏申请单跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assViewSpecialPage", method = RequestMethod.GET)
	public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("record_no", mapVo.get("record_no"));
		return "/hrp/ass/assinassets/assremould/assremouldrinassets/assPlanViewApply";
	}
	//queryCheckApinassetsByImport
	// 追溯盘亏申请
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/queryCheckApSpecialByImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assRemouldAinassetsService.queryAssApplyDeptByPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	/**
	 * @Description 添加数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/addAssRemouldRinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldRinassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		String assRemouldRinassetsJson = assRemouldRinassetsService.add(mapVo);

		return JSONObject.parseObject(assRemouldRinassetsJson);

	}

	/**
	 * @Description 添加数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/saveAssRemouldRSourceinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldRSourceinassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acct_year", SessionManager.getAcctYear());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		String assRemouldRinassetsJson = assRemouldRinassetsService.saveAssRemouldRSourceinassets(mapVo);

		return JSONObject.parseObject(assRemouldRinassetsJson);

	}

	/**
	 * @Description 变动确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/updateConfirmChangeInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAinassetsConfirmState(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("record_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			// mapVo.put("apply_no", ids[4]);
			// 查询主表是否存在
			AssRemouldRinassets assRemouldAinassets = assRemouldRinassetsService.queryByCode(mapVo);
			if (assRemouldAinassets == null || assRemouldAinassets.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assRemouldAinassets.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			// 查询明细表
			List<AssRemouldRdetailInassets> detailList = assRemouldRDetailInassetsService.queryByDisANo(mapVo);
			// 明细表存在进行判断卡片
			if (detailList != null && detailList.size() > 0) {
				for (AssRemouldRdetailInassets detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAinassets.getBus_type_code());
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if (map.containsKey(detail.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				// 如果明细表不存在返回没有数据
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		// 判断listVo是否有数据
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assRemouldAinassetsJson = assRemouldRinassetsService.updateAssRemouldAinassetsConfirmState(listVo,
				listCardVo);

		return JSONObject.parseObject(assRemouldAinassetsJson);

	}

	/**
	 * @Description 更新跳转页面 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assRemouldRinassetsUpdatePage", method = RequestMethod.GET)
	public String assRemouldRinassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssRemouldRinassets assRemouldRinassets = new AssRemouldRinassets();

		assRemouldRinassets = assRemouldRinassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldRinassets.getGroup_id());
		mode.addAttribute("hos_id", assRemouldRinassets.getHos_id());
		mode.addAttribute("copy_code", assRemouldRinassets.getCopy_code());
		mode.addAttribute("record_no", assRemouldRinassets.getRecord_no());
		mode.addAttribute("bus_type_code", assRemouldRinassets.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldRinassets.getVen_id());
		mode.addAttribute("ven_no", assRemouldRinassets.getVen_no());
		mode.addAttribute("sup_code", assRemouldRinassets.getSup_code());
		mode.addAttribute("sup_name", assRemouldRinassets.getSup_name());
		mode.addAttribute("create_emp", assRemouldRinassets.getCreate_emp());
		mode.addAttribute("create_date", assRemouldRinassets.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldRinassets.getAudit_emp());
		mode.addAttribute("record_date", assRemouldRinassets.getRecord_date());
		mode.addAttribute("state", assRemouldRinassets.getState());
		mode.addAttribute("note", assRemouldRinassets.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assremould/assremouldrinassets/update";
	}

	/**
	 * @Description 更新数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/updateAssRemouldRinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldRinassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assRemouldRinassetsJson = assRemouldRinassetsService.update(mapVo);

		return JSONObject.parseObject(assRemouldRinassetsJson);
	}

	/**
	 * @Description 更新数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/saveAssRemouldRinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldRinassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assRemouldRinassetsJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
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
		
         mapVo.put("create_emp", SessionManager.getUserId());
		assRemouldRinassetsJson = assRemouldRinassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assRemouldRinassetsJson);
	}


	/**
	 * @Description 删除数据 050805 资产资金来源
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/deleteAssRemoulddDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemoulddDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("source_id", ids[5]);

			AssRemouldRinassets assChangeinassets = assRemouldRinassetsService.queryByCode(mapVo);

			if (assChangeinassets != null) {
				if (assChangeinassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceinassetsJson = assRemouldRinassetsService.deleteAssSourceInassets(listVo);

		return JSONObject.parseObject(assChangeSourceinassetsJson);

	}
	
	/**
	 * @Description 删除数据 050805 资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/deleteAssRemouldDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);

			AssRemouldRinassets assChangeinassets = assRemouldRinassetsService.queryByCode(mapVo);

			if (assChangeinassets != null) {
				if (assChangeinassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailinassetsJson = assRemouldRDetailInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailinassetsJson);

	}
	
	
	/**
	 * @Description 删除数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/deleteAssRemouldRinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldRinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("record_no", ids[3]);
			AssRemouldRinassets assRemouldAinassets = assRemouldRinassetsService.queryByCode(mapVo);
			if (assRemouldAinassets != null) {
				if (assRemouldAinassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}

			}

			listVo.add(mapVo);

		}
		  String de=    assRemouldRinassetsService.deleteBatchAssApplyPlanMap(listVo);

		String assRemouldRinassetsJson = assRemouldRinassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assRemouldRinassetsJson);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/queryAssRemouldRinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRinassets(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldRinassets = assRemouldRinassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldRinassets);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/queryAssRemouldRdetailinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRdetailinassets(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldRinassets = assRemouldRinassetsService.queryAssRemouldRdetailinassets(mapVo);

		return JSONObject.parseObject(assRemouldRinassets);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/queryAssRemouldSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String assRemouldRinassets = assRemouldRinassetsService.queryAssRemouldSourceInassets(mapVo);

		return JSONObject.parseObject(assRemouldRinassets);

	}

	/**
	 * @Description 导入跳转页面 050805 资产改造记录(无形资产)
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/assRemouldRinassetsImportPage", method = RequestMethod.GET)
	public String assRemouldRinassetsImportPage(Model mode) throws Exception {

		return "hrp/ass/assremould/assremouldrinassets/assRemouldRinassetsImport";

	}

	/**
	 * @Description 下载导入模版 050805 资产改造记录(无形资产)
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assremould/assremouldrinassets/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\downTemplate", "050805 资产改造记录(无形资产).xls");

		return null;
	}

	/**
	 * @Description 导入数据 050805 资产改造记录(无形资产)
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/readAssRemouldRinassetsFiles", method = RequestMethod.POST)
	public String readAssRemouldRinassetsFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssRemouldRinassets> list_err = new ArrayList<AssRemouldRinassets>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssRemouldRinassets assRemouldRinassets = new AssRemouldRinassets();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assRemouldRinassets.setRecord_no(temp[3]);
					mapVo.put("record_no", temp[3]);

				} else {

					err_sb.append("改造记录单号为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assRemouldRinassets.setBus_type_code(temp[4]);
					mapVo.put("bus_type_code", temp[4]);

				} else {

					err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assRemouldRinassets.setVen_id(Long.valueOf(temp[5]));
					mapVo.put("ven_id", temp[5]);

				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assRemouldRinassets.setVen_no(Long.valueOf(temp[6]));
					mapVo.put("ven_no", temp[6]);

				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assRemouldRinassets.setCreate_emp(Long.valueOf(temp[7]));
					mapVo.put("create_emp", temp[7]);

				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assRemouldRinassets.setCreate_date(DateUtil.stringToDate(temp[8]));
					mapVo.put("create_date", temp[8]);

				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assRemouldRinassets.setAudit_emp(Long.valueOf(temp[9]));
					mapVo.put("audit_emp", temp[9]);

				} else {

					err_sb.append("审核人为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assRemouldRinassets.setRecord_date(DateUtil.stringToDate(temp[10]));
					mapVo.put("record_date", temp[10]);

				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assRemouldRinassets.setState(Integer.valueOf(temp[11]));
					mapVo.put("state", temp[11]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assRemouldRinassets.setNote(temp[12]);
					mapVo.put("note", temp[12]);

				} else {

					err_sb.append("摘要为空  ");

				}

				AssRemouldRinassets data_exc_extis = assRemouldRinassetsService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRemouldRinassets.setError_type(err_sb.toString());

					list_err.add(assRemouldRinassets);

				} else {

					String dataJson = assRemouldRinassetsService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssRemouldRinassets data_exc = new AssRemouldRinassets();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050805 资产改造记录(无形资产)
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrinassets/addBatchAssRemouldRinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssRemouldRinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssRemouldRinassets> list_err = new ArrayList<AssRemouldRinassets>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssRemouldRinassets assRemouldRinassets = new AssRemouldRinassets();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("record_no"))) {

					assRemouldRinassets.setRecord_no((String) jsonObj.get("record_no"));
					mapVo.put("record_no", jsonObj.get("record_no"));
				} else {

					err_sb.append("改造记录单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {

					assRemouldRinassets.setBus_type_code((String) jsonObj.get("bus_type_code"));
					mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
				} else {

					err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assRemouldRinassets.setVen_id(Long.valueOf((String) jsonObj.get("ven_id")));
					mapVo.put("ven_id", jsonObj.get("ven_id"));
				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assRemouldRinassets.setVen_no(Long.valueOf((String) jsonObj.get("ven_no")));
					mapVo.put("ven_no", jsonObj.get("ven_no"));
				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {

					assRemouldRinassets.setCreate_emp(Long.valueOf((String) jsonObj.get("create_emp")));
					mapVo.put("create_emp", jsonObj.get("create_emp"));
				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assRemouldRinassets.setCreate_date(DateUtil.stringToDate((String) jsonObj.get("create_date")));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {

					assRemouldRinassets.setAudit_emp(Long.valueOf((String) jsonObj.get("audit_emp")));
					mapVo.put("audit_emp", jsonObj.get("audit_emp"));
				} else {

					err_sb.append("审核人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("record_date"))) {

					assRemouldRinassets.setRecord_date(DateUtil.stringToDate((String) jsonObj.get("record_date")));
					mapVo.put("record_date", jsonObj.get("record_date"));
				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assRemouldRinassets.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					assRemouldRinassets.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("摘要为空  ");

				}

				AssRemouldRinassets data_exc_extis = assRemouldRinassetsService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRemouldRinassets.setError_type(err_sb.toString());

					list_err.add(assRemouldRinassets);

				} else {

					String dataJson = assRemouldRinassetsService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssRemouldRinassets data_exc = new AssRemouldRinassets();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

}
