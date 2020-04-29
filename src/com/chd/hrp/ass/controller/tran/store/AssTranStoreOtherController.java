/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.tran.store;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import com.chd.hrp.ass.entity.tran.store.AssTranStoreDetailOther;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreOther;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreOther;
import com.chd.hrp.ass.service.tran.store.AssTranStoreDetailOtherService;
import com.chd.hrp.ass.service.tran.store.AssTranStoreOtherService;
import com.chd.hrp.ass.service.tran.store.AssTranStoreOtherService;
/**
 * 
 * @Description:
 * 050901 资产转移主表(仓库到仓库)_其他固定资产
 * @Table:
 * ASS_TRAN_STORE_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssTranStoreOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssTranStoreOtherController.class);

	// 引入Service服务
	@Resource(name = "assTranStoreOtherService")
	private final AssTranStoreOtherService assTranStoreOtherService = null;

	@Resource(name = "assTranStoreDetailOtherService")
	private final AssTranStoreDetailOtherService assTranStoreDetailOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/assTranStoreOtherMainPage", method = RequestMethod.GET)
	public String assTranStoreOtherMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05090",MyConfig.getSysPara("05090"));
		
		return "hrp/ass/assother/asstran/store/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/assTranStoreOtherAddPage", method = RequestMethod.GET)
	public String assTranStoreOtherAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asstran/store/add";
	}

	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/assImportSpecialPage", method = RequestMethod.GET)
	public String assImportSpecialPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
		mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
		mode.addAttribute("out_store_code", mapVo.get("out_store_code"));
		mode.addAttribute("out_store_name", mapVo.get("out_store_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("in_store_id", mapVo.get("in_store_id"));
		mode.addAttribute("in_store_no", mapVo.get("in_store_no"));
		mode.addAttribute("in_store_code", mapVo.get("in_store_code"));
		mode.addAttribute("in_store_name", mapVo.get("in_store_name"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asstran/store/importoutmain";
	}
	
	/**
	 * @Description 添加数据 050901 资产转移主表(仓库到仓库)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/saveAssTranStoreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTranStoreOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssTranStoreOther assTranStoreOther = assTranStoreOtherService.queryByCode(mapVo);
		if(assTranStoreOther != null){
			if(assTranStoreOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能保存! \"}");
			}
		}
		
		String assTranStoreOtherJson = assTranStoreOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assTranStoreOtherJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产转移主表(仓库到仓库)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/assTranStoreOtherUpdatePage", method = RequestMethod.GET)
	public String assTranStoreOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssTranStoreOther assTranStoreOther = new AssTranStoreOther();

		assTranStoreOther = assTranStoreOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assTranStoreOther.getGroup_id());
		mode.addAttribute("hos_id", assTranStoreOther.getHos_id());
		mode.addAttribute("copy_code", assTranStoreOther.getCopy_code());
		mode.addAttribute("tran_no", assTranStoreOther.getTran_no());
		mode.addAttribute("out_store_id", assTranStoreOther.getOut_store_id());
		mode.addAttribute("out_store_no", assTranStoreOther.getOut_store_no());
		mode.addAttribute("out_store_code", assTranStoreOther.getOut_store_code());
		mode.addAttribute("out_store_name", assTranStoreOther.getOut_store_name());
		mode.addAttribute("in_store_id", assTranStoreOther.getIn_store_id());
		mode.addAttribute("in_store_no", assTranStoreOther.getIn_store_no());
		mode.addAttribute("in_store_code", assTranStoreOther.getIn_store_code());
		mode.addAttribute("in_store_name", assTranStoreOther.getIn_store_name());
		mode.addAttribute("create_emp", assTranStoreOther.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assTranStoreOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assTranStoreOther.getAudit_emp());
		mode.addAttribute("audit_date", assTranStoreOther.getAudit_date());
		mode.addAttribute("state", assTranStoreOther.getState());
		mode.addAttribute("note", assTranStoreOther.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05039",MyConfig.getSysPara("05039"));
		
		return "hrp/ass/assother/asstran/store/update";
	}

	/**
	 * @Description 删除数据 050901 资产转移主表(仓库到仓库)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/deleteAssTranStoreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranStoreOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			
			AssTranStoreOther assTranStoreOther = assTranStoreOtherService.queryByCode(mapVo);
			if(assTranStoreOther != null){
				if(assTranStoreOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}
			
			listVo.add(mapVo);

		}

		String assTranStoreOtherJson = assTranStoreOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranStoreOtherJson);

	}

	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/updateConfirmTranStoreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranStoreOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String str = df.format(date);
		String current_year = str.substring(0, 4);
		String current_month = str.substring(4, 6);
		
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			
			
			AssTranStoreOther assTranStoreOther = assTranStoreOtherService.queryByCode(mapVo);
			if (DateUtil.compareDate(assTranStoreOther.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranStoreDetailOther> list = assTranStoreOtherService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranStoreDetailOther assBackDetailOther : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					listCardMap.put("out_store_id", assBackDetailOther.getOut_store_id());
					listCardMap.put("out_store_no", assBackDetailOther.getOut_store_no());
					listCardMap.put("in_store_id", assBackDetailOther.getIn_store_id());
					listCardMap.put("in_store_no", assBackDetailOther.getIn_store_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailOther.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assTranStoreOther.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranStoreOtherService.updateConfirmTranStoreOther(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	

	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/hrp/ass/assother/asstran/store/updateConfirmTranStoreOtherdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranStoreOtherdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Date date=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
		String str = df.format(date);
		String current_year = str.substring(0, 4);
		String current_month = str.substring(4, 6);
		
		String assInMainJson = "";
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			if(  ids[0].length() == 0){
				return JSONObject.parseObject("{\"warn\":\"单据没有保存,不能入库! \"}");
			}
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			
			
			AssTranStoreOther assTranStoreOther = assTranStoreOtherService.queryByCode(mapVo);
			if (DateUtil.compareDate(assTranStoreOther.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranStoreDetailOther> list = assTranStoreOtherService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranStoreDetailOther assBackDetailOther : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailOther.getGroup_id());
					listCardMap.put("hos_id", assBackDetailOther.getHos_id());
					listCardMap.put("copy_code", assBackDetailOther.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailOther.getAss_card_no());
					listCardMap.put("out_store_id", assBackDetailOther.getOut_store_id());
					listCardMap.put("out_store_no", assBackDetailOther.getOut_store_no());
					listCardMap.put("in_store_id", assBackDetailOther.getIn_store_id());
					listCardMap.put("in_store_no", assBackDetailOther.getIn_store_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailOther.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailOther.getAss_card_no(), assBackDetailOther.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}

			if (assTranStoreOther.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranStoreOtherService.updateConfirmTranStoreOther(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/
	
	/**
	 * @Description 查询数据 050901 资产转移主表(仓库到仓库)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/queryAssTranStoreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranStoreOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranStoreOther = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assTranStoreOther = assTranStoreOtherService.query(getPage(mapVo));
		}else{
			assTranStoreOther = assTranStoreOtherService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assTranStoreOther);

	}

	/**
	 * @Description 删除数据 050901 资产转移明细(仓库到仓库)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/deleteAssTranStoreDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranStoreDetailOther(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("tran_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			
			AssTranStoreOther assTranStoreOther = assTranStoreOtherService.queryByCode(mapVo);
			if(assTranStoreOther != null){
				if(assTranStoreOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}
			
			listVo.add(mapVo);

		}

		String assTranStoreDetailOtherJson = assTranStoreDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranStoreDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移明细(仓库到仓库)_其他固定资产
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/queryAssTranStoreDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranStoreDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranStoreDetailOther = assTranStoreDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assTranStoreDetailOther);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asstran/store/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<String> list = assTranStoreOtherService.queryState(mapVo);
		
		if(list.size()>0){
			
			for (String tran_no : list) {
				str += tran_no +" ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据未确认,无法打印!\",\"state\":\"true\"}");
			
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
		
	}

}

