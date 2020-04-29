
package com.chd.hrp.ass.controller.tran.store;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.ass.entity.tran.store.AssTranStoreDetailSpecial;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreSpecial;
import com.chd.hrp.ass.service.tran.store.AssTranStoreDetailSpecialService;
import com.chd.hrp.ass.service.tran.store.AssTranStoreSpecialService;

/**
 * 
 * @Description: 050901 资产转移主表(仓库到仓库)_专用设备
 * @Table: ASS_TRAN_STORE_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssTranStoreSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssTranStoreSpecialController.class);

	// 引入Service服务
	@Resource(name = "assTranStoreSpecialService")
	private final AssTranStoreSpecialService assTranStoreSpecialService = null;

	@Resource(name = "assTranStoreDetailSpecialService")
	private final AssTranStoreDetailSpecialService assTranStoreDetailSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/assTranStoreSpecialMainPage", method = RequestMethod.GET)
	public String assTranStoreSpecialMainPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05088",MyConfig.getSysPara("05088"));
		
		return "hrp/ass/assspecial/asstran/store/main";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/assTranStoreSpecialAddPage", method = RequestMethod.GET)
	public String assTranStoreSpecialAddPage(Model mode) throws Exception {
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asstran/store/add";
	}

	/**
	 *  引入跳转页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/assImportSpecialPage", method = RequestMethod.GET)
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
		
		return "hrp/ass/assspecial/asstran/store/importoutmain";
	}
	
	
	
	/**
	 * @Description 添加数据 050901 资产转移主表(仓库到仓库)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/saveAssTranStoreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssTranStoreSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssTranStoreSpecial assTranStoreSpecial = assTranStoreSpecialService.queryByCode(mapVo);
		if(assTranStoreSpecial != null){
			if(assTranStoreSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能保存! \"}");
			}
		}
		
		String assTranStoreSpecialJson = assTranStoreSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assTranStoreSpecialJson);

	}

	/**
	 * @Description 更新跳转页面 050901 资产转移主表(仓库到仓库)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/assTranStoreSpecialUpdatePage", method = RequestMethod.GET)
	public String assTranStoreSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssTranStoreSpecial assTranStoreSpecial = new AssTranStoreSpecial();

		assTranStoreSpecial = assTranStoreSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assTranStoreSpecial.getGroup_id());
		mode.addAttribute("hos_id", assTranStoreSpecial.getHos_id());
		mode.addAttribute("copy_code", assTranStoreSpecial.getCopy_code());
		mode.addAttribute("tran_no", assTranStoreSpecial.getTran_no());
		mode.addAttribute("out_store_id", assTranStoreSpecial.getOut_store_id());
		mode.addAttribute("out_store_no", assTranStoreSpecial.getOut_store_no());
		mode.addAttribute("out_store_code", assTranStoreSpecial.getOut_store_code());
		mode.addAttribute("out_store_name", assTranStoreSpecial.getOut_store_name());
		mode.addAttribute("in_store_id", assTranStoreSpecial.getIn_store_id());
		mode.addAttribute("in_store_no", assTranStoreSpecial.getIn_store_no());
		mode.addAttribute("in_store_code", assTranStoreSpecial.getIn_store_code());
		mode.addAttribute("in_store_name", assTranStoreSpecial.getIn_store_name());
		mode.addAttribute("create_emp", assTranStoreSpecial.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assTranStoreSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assTranStoreSpecial.getAudit_emp());
		mode.addAttribute("audit_date", assTranStoreSpecial.getAudit_date());
		mode.addAttribute("state", assTranStoreSpecial.getState());
		mode.addAttribute("note", assTranStoreSpecial.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05019",MyConfig.getSysPara("05019"));
		
		return "hrp/ass/assspecial/asstran/store/update";
	}

	/**
	 * @Description 删除数据 050901 资产转移主表(仓库到仓库)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/deleteAssTranStoreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranStoreSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssTranStoreSpecial assTranStoreSpecial = assTranStoreSpecialService.queryByCode(mapVo);
			if(assTranStoreSpecial != null){
				if(assTranStoreSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}
			
			listVo.add(mapVo);

		}

		String assTranStoreSpecialJson = assTranStoreSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranStoreSpecialJson);

	}

	/**
	 * @Description 确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/updateConfirmTranStoreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranStoreSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			
			
			AssTranStoreSpecial assTranStoreSpecial = assTranStoreSpecialService.queryByCode(mapVo);
			if (assTranStoreSpecial.getState() == 2) {
				continue;
			} 
			if (DateUtil.compareDate(assTranStoreSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranStoreDetailSpecial> list = assTranStoreSpecialService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranStoreDetailSpecial assBackDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					listCardMap.put("out_store_id", assBackDetailSpecial.getOut_store_id());
					listCardMap.put("out_store_no", assBackDetailSpecial.getOut_store_no());
					listCardMap.put("in_store_id", assBackDetailSpecial.getIn_store_id());
					listCardMap.put("in_store_no", assBackDetailSpecial.getIn_store_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranStoreSpecialService.updateConfirmTranStoreSpecial(listVo, listCardVo);
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
	/*@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/updateConfirmTranStoreSpecialdetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmTranStoreSpecialdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			
			AssTranStoreSpecial assTranStoreSpecial = assTranStoreSpecialService.queryByCode(mapVo);
			if (assTranStoreSpecial.getState() == 2) {
				continue;
			} 
			if (DateUtil.compareDate(assTranStoreSpecial.getCreate_date(), new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}

			List<AssTranStoreDetailSpecial> list = assTranStoreSpecialService.queryByTranNo(mapVo);

			if (list != null && list.size() > 0) {
				for (AssTranStoreDetailSpecial assBackDetailSpecial : list) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", assBackDetailSpecial.getGroup_id());
					listCardMap.put("hos_id", assBackDetailSpecial.getHos_id());
					listCardMap.put("copy_code", assBackDetailSpecial.getCopy_code());
					listCardMap.put("ass_card_no", assBackDetailSpecial.getAss_card_no());
					listCardMap.put("out_store_id", assBackDetailSpecial.getOut_store_id());
					listCardMap.put("out_store_no", assBackDetailSpecial.getOut_store_no());
					listCardMap.put("in_store_id", assBackDetailSpecial.getIn_store_id());
					listCardMap.put("in_store_no", assBackDetailSpecial.getIn_store_no());
					listCardMap.put("ass_year", current_year);
					listCardMap.put("ass_month", current_month);
					if (map.containsKey(assBackDetailSpecial.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
					}
					map.put(assBackDetailSpecial.getAss_card_no(), assBackDetailSpecial.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (!flag) {
			return JSONObject.parseObject("{\"warn\":\"没有明细数据不能移库确认! \"}");
		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
		}

		try {
			assInMainJson = assTranStoreSpecialService.updateConfirmTranStoreSpecial(listVo, listCardVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}*/
	

	/**
	 * @Description 查询数据 050901 资产转移主表(仓库到仓库)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/queryAssTranStoreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranStoreSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranStoreSpecial = null;
		if("0".equals(mapVo.get("show_detail").toString())){
			assTranStoreSpecial = assTranStoreSpecialService.query(getPage(mapVo));
		}else{
			assTranStoreSpecial = assTranStoreSpecialService.queryDetails(getPage(mapVo));
		}
		return JSONObject.parseObject(assTranStoreSpecial);

	}

	/**
	 * @Description 删除数据 050901 资产转移明细(仓库到仓库)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/deleteAssTranStoreDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssTranStoreDetailSpecial(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssTranStoreSpecial assTranStoreSpecial = assTranStoreSpecialService.queryByCode(mapVo);
			if(assTranStoreSpecial != null){
				if(assTranStoreSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
				}
			}
			
			listVo.add(mapVo);

		}

		String assTranStoreDetailSpecialJson = assTranStoreDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assTranStoreDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050901 资产转移明细(仓库到仓库)_专用设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/queryAssTranStoreDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTranStoreDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assTranStoreDetailSpecial = assTranStoreDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assTranStoreDetailSpecial);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asstran/store/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<String> list = assTranStoreSpecialService.queryState(mapVo);
		
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
