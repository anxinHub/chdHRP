/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.assremould.land;

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
import com.chd.hrp.ass.entity.assremould.land.AssRemouldRdetailLand;
import com.chd.hrp.ass.entity.assremould.land.AssRemouldRland;
import com.chd.hrp.ass.service.assremould.land.AssRemouldADetailLandService;
import com.chd.hrp.ass.service.assremould.land.AssRemouldAlandService;
import com.chd.hrp.ass.service.assremould.land.AssRemouldRDetailLandService;
import com.chd.hrp.ass.service.assremould.land.AssRemouldRlandService;

/**
 * 
 * @Description: 050805 资产改造记录(土地)
 * @Table: ASS_REMOULD_R_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssRemouldRlandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssRemouldRlandController.class);

	// 引入Service服务
	@Resource(name = "assRemouldRlandService")
	private final AssRemouldRlandService assRemouldRlandService = null;
	// 引入Service服务
	@Resource(name = "assRemouldRDetailLandService")
	private final AssRemouldRDetailLandService assRemouldRDetailLandService = null;
	
	// 引入Service服务
	@Resource(name = "assRemouldAlandService")
	private final AssRemouldAlandService assRemouldAlandService = null;

	// 引入Service服务
	@Resource(name = "assRemouldADetailLandService")
	private final AssRemouldADetailLandService assRemouldADetailLandService = null;



	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assRemouldRlandMainPage", method = RequestMethod.GET)
	public String assRemouldRlandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/assremould/assremouldrland/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assRemouldRlandAddPage", method = RequestMethod.GET)
	public String assRemouldRlandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assremould/assremouldrland/add";

	}
	/**
	 * @Description 引入申请页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assChkRecordImportApplyPage", method = RequestMethod.GET)
	public String assChkRecordImportApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	       mode.addAttribute("create_date", mapVo.get("create_date"));
			mode.addAttribute("note", mapVo.get("note"));
			mode.addAttribute("bus_type_code", mapVo.get("bus_type_code"));
		return "hrp/ass/assland/assremould/assremouldrland/Import";

	}
	/**
	 * @Description 查询改造申请数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/queryAssRemouldAspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldAland(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String assRemouldAland = assRemouldAlandService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAland);

	}
	
	/**
	 * @Description 查看申报信息
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assRemouldAlandUpdatePage", method = RequestMethod.GET)
	public String assRemouldAlandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String,Object> assRemouldAlandMap = assRemouldAlandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldAlandMap.get("group_id"));
		mode.addAttribute("hos_id", assRemouldAlandMap.get("hos_id"));
		mode.addAttribute("copy_code", assRemouldAlandMap.get("copy_code"));
		mode.addAttribute("apply_no", assRemouldAlandMap.get("apply_no"));
		mode.addAttribute("bus_type_code", assRemouldAlandMap.get("bus_type_code"));
		mode.addAttribute("bus_type_name", assRemouldAlandMap.get("bus_type_name"));
		mode.addAttribute("ven_id", assRemouldAlandMap.get("ven_id"));
		mode.addAttribute("ven_no", assRemouldAlandMap.get("ven_no"));
		mode.addAttribute("sup_code", assRemouldAlandMap.get("sup_code"));
		mode.addAttribute("sup_name", assRemouldAlandMap.get("sup_name"));
		mode.addAttribute("create_emp", assRemouldAlandMap.get("create_emp"));
		mode.addAttribute("create_date", assRemouldAlandMap.get("create_date"));
		mode.addAttribute("create_emp_name", assRemouldAlandMap.get("create_emp_name"));
		mode.addAttribute("audit_emp_name", assRemouldAlandMap.get("audit_emp_name"));
		mode.addAttribute("audit_emp", assRemouldAlandMap.get("audit_emp"));
		mode.addAttribute("change_date", assRemouldAlandMap.get("change_date"));
		mode.addAttribute("state", assRemouldAlandMap.get("state"));
		mode.addAttribute("note", assRemouldAlandMap.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assremould/assremouldrland/Viewupdate";
	}
	/**
	 * @Description 查询改造申请明细数据 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/queryAssRemouldADict", method = RequestMethod.POST)
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
		String assRemouldAland = assRemouldAlandService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAland);

	}
	//查询明细到记录
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/addAssPlanDeptImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssPlanDeptImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		
	/*	mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("apply_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("apply_no", ids);
		
		
		String assBackDetailland = assRemouldADetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailland);*/
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String assAllotInGeneral = assRemouldRlandService.initAssCheckland(mapVo);

		return JSONObject.parseObject(assAllotInGeneral);
	}
	
	
	/**
	 * @Description 追溯盘亏申请单跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assViewSpecialPage", method = RequestMethod.GET)
	public String assViewApplyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("record_no", mapVo.get("record_no"));
		return "/hrp/ass/assland/assremould/assremouldrland/assPlanViewApply";
	}
	//queryCheckAplandByImport
	// 追溯盘亏申请
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/queryCheckAplandByImport", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssApplyDeptByApply(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assApplyDept = assRemouldAlandService.queryAssApplyDeptByPlanDept(getPage(mapVo));

		return JSONObject.parseObject(assApplyDept);

	}
	/**
	 * @Description 添加数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/addAssRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldRland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}

		String assRemouldRlandJson = assRemouldRlandService.add(mapVo);

		return JSONObject.parseObject(assRemouldRlandJson);

	}

	/**
	 * @Description 变动确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/updateConfirmRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAlandConfirmState(@RequestParam(value = "ParamVo") String paramVo,
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
			AssRemouldRland assRemouldAland = assRemouldRlandService.queryByCode(mapVo);
			if (assRemouldAland == null || assRemouldAland.getState() == 2) {
				continue;
			}
			if (DateUtil.compareDate(assRemouldAland.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			// 查询明细表
			List<AssRemouldRdetailLand> detailList = assRemouldRDetailLandService.queryByDisANo(mapVo);
			// 明细表存在进行判断卡片
			if (detailList != null && detailList.size() > 0) {
				for (AssRemouldRdetailLand detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAland.getBus_type_code());
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

		String assRemouldAlandJson = assRemouldRlandService.updateAssRemouldALandConfirmState(listVo, listCardVo);

		return JSONObject.parseObject(assRemouldAlandJson);

	}

	/**
	 * @Description 添加数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/saveAssRemouldRSourceland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldRSourceland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRemouldRlandJson = assRemouldRlandService.saveAssRemouldRSourceland(mapVo);

		return JSONObject.parseObject(assRemouldRlandJson);

	}

	/**
	 * @Description 更新跳转页面 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assRemouldRlandUpdatePage", method = RequestMethod.GET)
	public String assRemouldRlandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssRemouldRland assRemouldRland = new AssRemouldRland();

		assRemouldRland = assRemouldRlandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assRemouldRland.getGroup_id());
		mode.addAttribute("hos_id", assRemouldRland.getHos_id());
		mode.addAttribute("copy_code", assRemouldRland.getCopy_code());
		mode.addAttribute("record_no", assRemouldRland.getRecord_no());
		mode.addAttribute("bus_type_code", assRemouldRland.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldRland.getVen_id());
		mode.addAttribute("ven_no", assRemouldRland.getVen_no());
		mode.addAttribute("sup_code", assRemouldRland.getSup_code());
		mode.addAttribute("sup_name", assRemouldRland.getSup_name());
		mode.addAttribute("create_emp", assRemouldRland.getCreate_emp());
		mode.addAttribute("create_date", assRemouldRland.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldRland.getAudit_emp());
		mode.addAttribute("record_date", assRemouldRland.getRecord_date());
		mode.addAttribute("state", assRemouldRland.getState());
		mode.addAttribute("note", assRemouldRland.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assremould/assremouldrland/update";
	}

	/**
	 * @Description 更新数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/updateAssRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldRland(@RequestParam Map<String, Object> mapVo, Model mode)
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

		String assRemouldRlandJson = assRemouldRlandService.update(mapVo);

		return JSONObject.parseObject(assRemouldRlandJson);
	}

	/**
	 * @Description 更新数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/saveAssRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldRland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assRemouldRlandJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
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
		assRemouldRlandJson = assRemouldRlandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assRemouldRlandJson);
	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/deleteAssremoulddSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssremoulddSourceLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssRemouldRland assChangeland = assRemouldRlandService.queryByCode(mapVo);

			if (assChangeland != null) {
				if (assChangeland.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourcelandJson = assRemouldRlandService.deleteAssSourceLand(listVo);

		return JSONObject.parseObject(assChangeSourcelandJson);

	}
	/**
	 * @Description 删除数据 050805 资产原值变动明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/deleteAssremouldSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssremouldSourceLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssRemouldRland assChangeland = assRemouldRlandService.queryByCode(mapVo);

			if (assChangeland != null) {
				if (assChangeland.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetaillandJson = assRemouldRDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetaillandJson);

	}
	
	
	/**
	 * @Description 删除数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/deleteAssRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldRland(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssRemouldRland assRemouldAland = assRemouldRlandService.queryByCode(mapVo);
			if (assRemouldAland != null) {
				if (assRemouldAland.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}

			}
			listVo.add(mapVo);

		}
		  String de=    assRemouldRlandService.deleteBatchAssApplyPlanMap(listVo);

		String assRemouldRlandJson = assRemouldRlandService.deleteBatch(listVo);

		return JSONObject.parseObject(assRemouldRlandJson);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/queryAssRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRemouldRland = assRemouldRlandService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldRland);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/queryAssRemouldRdetailland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldRdetailland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());


		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRemouldRland = assRemouldRlandService.queryAssRemouldRdetailland(mapVo);

		return JSONObject.parseObject(assRemouldRland);

	}

	/**
	 * @Description 查询数据 050805 资产改造记录(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/queryAssRemouldSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldSourceLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assRemouldRland = assRemouldRlandService.queryAssRemouldSourceLand(mapVo);

		return JSONObject.parseObject(assRemouldRland);

	}

	/**
	 * @Description 导入跳转页面 050805 资产改造记录(土地)
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/assRemouldRlandImportPage", method = RequestMethod.GET)
	public String assRemouldRlandImportPage(Model mode) throws Exception {

		return "hrp/ass/assremould/assremouldrland/assRemouldRlandImport";

	}

	/**
	 * @Description 下载导入模版 050805 资产改造记录(土地)
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assremould/assremouldrland/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\downTemplate", "050805 资产改造记录(土地).xls");

		return null;
	}

	/**
	 * @Description 导入数据 050805 资产改造记录(土地)
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/readAssRemouldRlandFiles", method = RequestMethod.POST)
	public String readAssRemouldRlandFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<AssRemouldRland> list_err = new ArrayList<AssRemouldRland>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssRemouldRland assRemouldRland = new AssRemouldRland();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assRemouldRland.setRecord_no(temp[3]);
					mapVo.put("record_no", temp[3]);

				} else {

					err_sb.append("改造记录单号为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assRemouldRland.setBus_type_code(temp[4]);
					mapVo.put("bus_type_code", temp[4]);

				} else {

					err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assRemouldRland.setVen_id(Long.valueOf(temp[5]));
					mapVo.put("ven_id", temp[5]);

				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assRemouldRland.setVen_no(Long.valueOf(temp[6]));
					mapVo.put("ven_no", temp[6]);

				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assRemouldRland.setCreate_emp(Long.valueOf(temp[7]));
					mapVo.put("create_emp", temp[7]);

				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(temp[8])) {

					assRemouldRland.setCreate_date(DateUtil.stringToDate(temp[8]));
					mapVo.put("create_date", temp[8]);

				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assRemouldRland.setAudit_emp(Long.valueOf(temp[9]));
					mapVo.put("audit_emp", temp[9]);

				} else {

					err_sb.append("审核人为空  ");

				}

				if (StringTool.isNotBlank(temp[10])) {

					assRemouldRland.setRecord_date(DateUtil.stringToDate(temp[10]));
					mapVo.put("record_date", temp[10]);

				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(temp[11])) {

					assRemouldRland.setState(Integer.valueOf(temp[11]));
					mapVo.put("state", temp[11]);

				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(temp[12])) {

					assRemouldRland.setNote(temp[12]);
					mapVo.put("note", temp[12]);

				} else {

					err_sb.append("摘要为空  ");

				}

				AssRemouldRland data_exc_extis = assRemouldRlandService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRemouldRland.setError_type(err_sb.toString());

					list_err.add(assRemouldRland);

				} else {

					String dataJson = assRemouldRlandService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssRemouldRland data_exc = new AssRemouldRland();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050805 资产改造记录(土地)
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldrland/addBatchAssRemouldRland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssRemouldRland(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssRemouldRland> list_err = new ArrayList<AssRemouldRland>();

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

				AssRemouldRland assRemouldRland = new AssRemouldRland();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("record_no"))) {

					assRemouldRland.setRecord_no((String) jsonObj.get("record_no"));
					mapVo.put("record_no", jsonObj.get("record_no"));
				} else {

					err_sb.append("改造记录单号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {

					assRemouldRland.setBus_type_code((String) jsonObj.get("bus_type_code"));
					mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
				} else {

					err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {

					assRemouldRland.setVen_id(Long.valueOf((String) jsonObj.get("ven_id")));
					mapVo.put("ven_id", jsonObj.get("ven_id"));
				} else {

					err_sb.append("供应商ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {

					assRemouldRland.setVen_no(Long.valueOf((String) jsonObj.get("ven_no")));
					mapVo.put("ven_no", jsonObj.get("ven_no"));
				} else {

					err_sb.append("供应商变更ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {

					assRemouldRland.setCreate_emp(Long.valueOf((String) jsonObj.get("create_emp")));
					mapVo.put("create_emp", jsonObj.get("create_emp"));
				} else {

					err_sb.append("制单人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("create_date"))) {

					assRemouldRland.setCreate_date(DateUtil.stringToDate((String) jsonObj.get("create_date")));
					mapVo.put("create_date", jsonObj.get("create_date"));
				} else {

					err_sb.append("制单日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {

					assRemouldRland.setAudit_emp(Long.valueOf((String) jsonObj.get("audit_emp")));
					mapVo.put("audit_emp", jsonObj.get("audit_emp"));
				} else {

					err_sb.append("审核人为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("record_date"))) {

					assRemouldRland.setRecord_date(DateUtil.stringToDate((String) jsonObj.get("record_date")));
					mapVo.put("record_date", jsonObj.get("record_date"));
				} else {

					err_sb.append("入账日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("state"))) {

					assRemouldRland.setState(Integer.valueOf((String) jsonObj.get("state")));
					mapVo.put("state", jsonObj.get("state"));
				} else {

					err_sb.append("状态为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					assRemouldRland.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {

					err_sb.append("摘要为空  ");

				}

				AssRemouldRland data_exc_extis = assRemouldRlandService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assRemouldRland.setError_type(err_sb.toString());

					list_err.add(assRemouldRland);

				} else {

					String dataJson = assRemouldRlandService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			AssRemouldRland data_exc = new AssRemouldRland();

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
