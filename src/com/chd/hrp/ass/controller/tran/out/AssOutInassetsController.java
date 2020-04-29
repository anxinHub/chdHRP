/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.tran.out;
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
import com.chd.hrp.ass.entity.tran.out.AssOutDetailInassets;
import com.chd.hrp.ass.entity.tran.out.AssOutInassets;
import com.chd.hrp.ass.service.in.AssInMainInassetsService;
import com.chd.hrp.ass.service.tran.out.AssOutDetailInassetsService;
import com.chd.hrp.ass.service.tran.out.AssOutInassetsService;
/**
 * 
 * @Description:
 * 050902 资产领用表_无形资产
 * @Table:
 * ASS_OUT_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssOutInassetsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssOutInassetsController.class);
	
	// 引入Service服务
		@Resource(name = "assOutInassetsService")
		private final AssOutInassetsService assOutInassetsService = null;

		@Resource(name = "assOutDetailInassetsService")
		private final AssOutDetailInassetsService assOutDetailInassetsService = null;
		
		@Resource(name = "assInMainInassetsService")
		private final AssInMainInassetsService assInMainInassetsService = null;

		/**
		 * @Description 主页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/assOutInassetsMainPage", method = RequestMethod.GET)
		public String assOutInassetsMainPage(Model mode) throws Exception {

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05091",MyConfig.getSysPara("05091"));
			
			return "hrp/ass/assinassets/asstran/out/main";

		}

		
		/**
		 *  引入跳转页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/assImportSpecialPage", method = RequestMethod.GET)
		public String assImportSpecialPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
			mode.addAttribute("dept_code", mapVo.get("dept_code"));
			mode.addAttribute("dept_name", mapVo.get("dept_name"));
			mode.addAttribute("create_date", mapVo.get("create_date"));
			mode.addAttribute("store_id", mapVo.get("store_id"));
			mode.addAttribute("store_no", mapVo.get("store_no"));
			mode.addAttribute("store_code", mapVo.get("store_code"));
			mode.addAttribute("store_name", mapVo.get("store_name"));
			mode.addAttribute("bill_type", mapVo.get("bill_type"));
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/asstran/out/importoutmain";
		}
		
		/**
		 * @Description 添加页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/assOutInassetsAddPage", method = RequestMethod.GET)
		public String assOutInassetsAddPage(Model mode) throws Exception {

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/asstran/out/add";

		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/assViewInMainPage", method = RequestMethod.GET)
		public String assViewInMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("out_no", mapVo.get("out_no"));
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			
			return "hrp/ass/assinassets/asstran/out/viewinmain";
		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/assImportInPage", method = RequestMethod.GET)
		public String assImportInPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
			mode.addAttribute("dept_name", mapVo.get("dept_name"));
			mode.addAttribute("store_id", mapVo.get("store_id"));
			mode.addAttribute("store_no", mapVo.get("store_no"));
			mode.addAttribute("store_name", mapVo.get("store_name"));
			mode.addAttribute("bill_type", mapVo.get("bill_type"));
			mode.addAttribute("note", mapVo.get("note"));
			mode.addAttribute("purc_emp", mapVo.get("purc_emp"));
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			
			return "hrp/ass/assinassets/asstran/out/importin";
		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/queryInMainByOutImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryInMainByOutImport(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String assInMainSpecial = null;
				
			assInMainSpecial = assInMainInassetsService.queryByInitOut(getPage(mapVo));

			return JSONObject.parseObject(assInMainSpecial);
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/queryInMainByOutNo", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryInMainByOutNo(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String assInMainSpecial = null;
				
			assInMainSpecial = assInMainInassetsService.queryInMainByOutNo(getPage(mapVo));

			return JSONObject.parseObject(assInMainSpecial);
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/importAssInMainByOut", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> importAssInMainByOut(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			
			String assPayImportMain = assOutInassetsService.importAssInMainByOut(mapVo);

			return JSONObject.parseObject(assPayImportMain);

		}

		/**
		 * @Description 添加数据 050902 资产领用表(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/saveAssOutInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssOutInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
			AssOutInassets assOutInassets = assOutInassetsService.queryByCode(mapVo);
			if(assOutInassets != null){
				if(assOutInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
				}
			}
			
			String assOutInassetsJson = assOutInassetsService.addOrUpdate(mapVo);

			return JSONObject.parseObject(assOutInassetsJson);

		}
		
		/**
		 * @Description 确认
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/updateConfirmOutInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmOutInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
				mapVo.put("out_no", ids[3]);
				mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
				mapVo.put("audit_emp", SessionManager.getUserId());
				
				AssOutInassets assOutInassets = assOutInassetsService.queryByCode(mapVo);
				if (DateUtil.compareDate(assOutInassets.getCreate_date(), new Date())) {
					return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
				}
				mapVo.put("bill_type", assOutInassets.getBill_type());
				List<AssOutDetailInassets> list = assOutInassetsService.queryByOutNo(mapVo);

				if (list != null && list.size() > 0) {
					for (AssOutDetailInassets assOutDetailInassets : list) {
						Map<String, Object> listCardMap = new HashMap<String, Object>();
						listCardMap.put("group_id", assOutDetailInassets.getGroup_id());
						listCardMap.put("hos_id", assOutDetailInassets.getHos_id());
						listCardMap.put("copy_code", assOutDetailInassets.getCopy_code());
						listCardMap.put("ass_card_no", assOutDetailInassets.getAss_card_no());
						listCardMap.put("bill_type", assOutDetailInassets.getBill_type());
						listCardMap.put("store_id", assOutDetailInassets.getStore_id());
						listCardMap.put("store_no", assOutDetailInassets.getStore_no());
						listCardMap.put("dept_id", assOutDetailInassets.getDept_id());
						listCardMap.put("dept_no", assOutDetailInassets.getDept_no());
						listCardMap.put("note", assOutDetailInassets.getNote());
						listCardMap.put("ass_year", current_year);
						listCardMap.put("ass_month", current_month);
						if (map.containsKey(assOutDetailInassets.getAss_card_no())) {
							return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能确认! \"}");
						}
						map.put(assOutDetailInassets.getAss_card_no(), assOutDetailInassets.getAss_card_no());
						listCardVo.add(listCardMap);
					}
				} else {
					flag = false;
					break;
				}

				if (assOutInassets.getState() == 2) {
					continue;
				} else {
					listVo.add(mapVo);
				}

			}
			if (!flag) {
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能确认! \"}");
			}

			if (listVo.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
			}

			try {
				assInMainJson = assOutInassetsService.updateConfirmOutInassets(listVo, listCardVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assInMainJson);
		}
		
		/**
		 * @Description 增加确认
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		/*@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/updateConfirmOutInassetsdetail", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmOutInassetsdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
				mapVo.put("out_no", ids[3]);
				mapVo.put("ass_card_no", ids[4]);
				mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
				mapVo.put("audit_emp", SessionManager.getUserId());

				
				AssOutInassets assOutInassets = assOutInassetsService.queryByCode(mapVo);
				if (DateUtil.compareDate(assOutInassets.getCreate_date(), new Date())) {
					return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
				}
				mapVo.put("bill_type", assOutInassets.getBill_type());
				List<AssOutDetailInassets> list = assOutInassetsService.queryByOutNo(mapVo);

				if (list != null && list.size() > 0) {
					for (AssOutDetailInassets assOutDetailInassets : list) {
						Map<String, Object> listCardMap = new HashMap<String, Object>();
						listCardMap.put("group_id", assOutDetailInassets.getGroup_id());
						listCardMap.put("hos_id", assOutDetailInassets.getHos_id());
						listCardMap.put("copy_code", assOutDetailInassets.getCopy_code());
						listCardMap.put("ass_card_no", assOutDetailInassets.getAss_card_no());
						listCardMap.put("bill_type", assOutDetailInassets.getBill_type());
						listCardMap.put("store_id", assOutDetailInassets.getStore_id());
						listCardMap.put("store_no", assOutDetailInassets.getStore_no());
						listCardMap.put("dept_id", assOutDetailInassets.getDept_id());
						listCardMap.put("dept_no", assOutDetailInassets.getDept_no());
						listCardMap.put("note", assOutDetailInassets.getNote());
						listCardMap.put("ass_year", current_year);
						listCardMap.put("ass_month", current_month);
						if (map.containsKey(assOutDetailInassets.getAss_card_no())) {
							return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能确认! \"}");
						}
						map.put(assOutDetailInassets.getAss_card_no(), assOutDetailInassets.getAss_card_no());
						listCardVo.add(listCardMap);
					}
				} else {
					flag = false;
					break;
				}

				if (assOutInassets.getState() == 2) {
					continue;
				} else {
					listVo.add(mapVo);
				}

			}
			if (!flag) {
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能确认! \"}");
			}

			if (listVo.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"不能重复移库! \"}");
			}

			try {
				assInMainJson = assOutInassetsService.updateConfirmOutInassets(listVo, listCardVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assInMainJson);
		}*/
		

		/**
		 * @Description 更新跳转页面 050902 资产领用表(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/assOutInassetsUpdatePage", method = RequestMethod.GET)
		public String assOutInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			AssOutInassets assOutInassets = new AssOutInassets();

			assOutInassets = assOutInassetsService.queryByCode(mapVo);

			mode.addAttribute("group_id", assOutInassets.getGroup_id());
			mode.addAttribute("hos_id", assOutInassets.getHos_id());
			mode.addAttribute("copy_code", assOutInassets.getCopy_code());
			mode.addAttribute("out_no", assOutInassets.getOut_no());
			mode.addAttribute("bill_type", assOutInassets.getBill_type());
			mode.addAttribute("bill_type_name", assOutInassets.getBill_type_name());
			mode.addAttribute("store_id", assOutInassets.getStore_id());
			mode.addAttribute("store_no", assOutInassets.getStore_no());
			mode.addAttribute("dept_id", assOutInassets.getDept_id());
			mode.addAttribute("dept_no", assOutInassets.getDept_no());
			mode.addAttribute("store_code", assOutInassets.getStore_code());
			mode.addAttribute("store_name", assOutInassets.getStore_name());
			mode.addAttribute("dept_code", assOutInassets.getDept_code());
			mode.addAttribute("dept_name", assOutInassets.getDept_name());
			mode.addAttribute("create_emp", assOutInassets.getCreate_emp());
			mode.addAttribute("create_date", DateUtil.dateToString(assOutInassets.getCreate_date(), "yyyy-MM-dd"));
			mode.addAttribute("audit_emp", assOutInassets.getAudit_emp());
			mode.addAttribute("audit_date", assOutInassets.getAudit_date());
			mode.addAttribute("state", assOutInassets.getState());
			mode.addAttribute("note", assOutInassets.getNote());
			mode.addAttribute("purc_emp", assOutInassets.getPurc_emp());
			mode.addAttribute("purc_emp_name", assOutInassets.getPurc_emp_name());
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			mode.addAttribute("ass_05049",MyConfig.getSysPara("05049"));
			
			return "hrp/ass/assinassets/asstran/out/update";
		}

		/**
		 * @Description 删除数据 050902 资产领用表(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/deleteAssOutInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssOutInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("out_no", ids[3]);
				
				AssOutInassets assOutInassets = assOutInassetsService.queryByCode(mapVo);
				if(assOutInassets != null){
					if(assOutInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
					}
				}

				listVo.add(mapVo);

			}

			String assOutInassetsJson = assOutInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assOutInassetsJson);

		}

		/**
		 * @Description 查询数据 050902 资产领用表(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/queryAssOutInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssOutInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("user_id", SessionManager.getUserId());

			String assOutInassets = null;
			if("0".equals(mapVo.get("show_detail").toString())){
				assOutInassets = assOutInassetsService.query(getPage(mapVo));
			}else{
				assOutInassets = assOutInassetsService.queryDetails(getPage(mapVo));
			}
			return JSONObject.parseObject(assOutInassets);

		}

		/**
		 * @Description 删除数据 050901 资产领用明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/deleteAssOutDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssOutDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("out_no", ids[3]);
				mapVo.put("ass_card_no", ids[4]);
				AssOutInassets assOutInassets = assOutInassetsService.queryByCode(mapVo);
				if(assOutInassets != null){
					if(assOutInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
					}
				}
				listVo.add(mapVo);

			}

			String assOutDetailInassetsJson = assOutDetailInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assOutDetailInassetsJson);

		}

		/**
		 * @Description 查询数据 050901 资产领用明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/queryAssOutDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssOutDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String assOutDetailInassets = assOutDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assOutDetailInassets);

		}
		
		/**
		 * @Description
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/out/queryState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			String str = "";
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			List<String> list = assOutInassetsService.queryState(mapVo);
			
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

