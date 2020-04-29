/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
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
import com.chd.hrp.ass.entity.tran.store.AssTranStoreDetailInassets;
import com.chd.hrp.ass.entity.tran.store.AssTranStoreInassets;
import com.chd.hrp.ass.service.tran.store.AssTranStoreDetailInassetsService;
import com.chd.hrp.ass.service.tran.store.AssTranStoreInassetsService;
/**
 * 
 * @Description:
 * 050901 资产转移主表(仓库到仓库)_其他无形资产
 * @Table:
 * ASS_TRAN_STORE_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssTranStoreInassetsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssTranStoreInassetsController.class);
	
	// 引入Service服务
		@Resource(name = "assTranStoreInassetsService")
		private final AssTranStoreInassetsService assTranStoreInassetsService = null;

		@Resource(name = "assTranStoreDetailInassetsService")
		private final AssTranStoreDetailInassetsService assTranStoreDetailInassetsService = null;

		/**
		 * @Description 主页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/assTranStoreInassetsMainPage", method = RequestMethod.GET)
		public String assTranStoreInassetsMainPage(Model mode) throws Exception {
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05091",MyConfig.getSysPara("05091"));
			
			return "hrp/ass/assinassets/asstran/store/main";
		}

		/**
		 * @Description 添加页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/assTranStoreInassetsAddPage", method = RequestMethod.GET)
		public String assTranStoreInassetsAddPage(Model mode) throws Exception {
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/asstran/store/add";
		}
		
		/**
		 *  引入跳转页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/assImportSpecialPage", method = RequestMethod.GET)
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
			
			return "hrp/ass/assinassets/asstran/store/importoutmain";
		}

		/**
		 * @Description 添加数据 050901 资产转移主表(仓库到仓库)_无形资产
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/saveAssTranStoreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssTranStoreInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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
			
			AssTranStoreInassets assTranStoreInassets = assTranStoreInassetsService.queryByCode(mapVo);
			if(assTranStoreInassets != null){
				if(assTranStoreInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能保存! \"}");
				}
			}
			
			String assTranStoreInassetsJson = assTranStoreInassetsService.addOrUpdate(mapVo);

			return JSONObject.parseObject(assTranStoreInassetsJson);

		}

		/**
		 * @Description 更新跳转页面 050901 资产转移主表(仓库到仓库)_无形资产
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/assTranStoreInassetsUpdatePage", method = RequestMethod.GET)
		public String assTranStoreInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			AssTranStoreInassets assTranStoreInassets = new AssTranStoreInassets();

			assTranStoreInassets = assTranStoreInassetsService.queryByCode(mapVo);

			mode.addAttribute("group_id", assTranStoreInassets.getGroup_id());
			mode.addAttribute("hos_id", assTranStoreInassets.getHos_id());
			mode.addAttribute("copy_code", assTranStoreInassets.getCopy_code());
			mode.addAttribute("tran_no", assTranStoreInassets.getTran_no());
			mode.addAttribute("out_store_id", assTranStoreInassets.getOut_store_id());
			mode.addAttribute("out_store_no", assTranStoreInassets.getOut_store_no());
			mode.addAttribute("out_store_code", assTranStoreInassets.getOut_store_code());
			mode.addAttribute("out_store_name", assTranStoreInassets.getOut_store_name());
			mode.addAttribute("in_store_id", assTranStoreInassets.getIn_store_id());
			mode.addAttribute("in_store_no", assTranStoreInassets.getIn_store_no());
			mode.addAttribute("in_store_code", assTranStoreInassets.getIn_store_code());
			mode.addAttribute("in_store_name", assTranStoreInassets.getIn_store_name());
			mode.addAttribute("create_emp", assTranStoreInassets.getCreate_emp());
			mode.addAttribute("create_date", DateUtil.dateToString(assTranStoreInassets.getCreate_date(), "yyyy-MM-dd"));
			mode.addAttribute("audit_emp", assTranStoreInassets.getAudit_emp());
			mode.addAttribute("audit_date", assTranStoreInassets.getAudit_date());
			mode.addAttribute("state", assTranStoreInassets.getState());
			mode.addAttribute("note", assTranStoreInassets.getNote());

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			mode.addAttribute("ass_05049",MyConfig.getSysPara("05049"));
			
			return "hrp/ass/assinassets/asstran/store/update";
		}

		/**
		 * @Description 删除数据 050901 资产转移主表(仓库到仓库)_无形资产
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/deleteAssTranStoreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssTranStoreInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
				
				AssTranStoreInassets assTranStoreInassets = assTranStoreInassetsService.queryByCode(mapVo);
				if(assTranStoreInassets != null){
					if(assTranStoreInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);

			}

			String assTranStoreInassetsJson = assTranStoreInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assTranStoreInassetsJson);

		}

		/**
		 * @Description 确认
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/updateConfirmTranStoreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmTranStoreInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

				
				
				AssTranStoreInassets assTranStoreInassets = assTranStoreInassetsService.queryByCode(mapVo);
				if (DateUtil.compareDate(assTranStoreInassets.getCreate_date(), new Date())) {
					return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
				}

				List<AssTranStoreDetailInassets> list = assTranStoreInassetsService.queryByTranNo(mapVo);

				if (list != null && list.size() > 0) {
					for (AssTranStoreDetailInassets assBackDetailInassets : list) {
						Map<String, Object> listCardMap = new HashMap<String, Object>();
						listCardMap.put("group_id", assBackDetailInassets.getGroup_id());
						listCardMap.put("hos_id", assBackDetailInassets.getHos_id());
						listCardMap.put("copy_code", assBackDetailInassets.getCopy_code());
						listCardMap.put("ass_card_no", assBackDetailInassets.getAss_card_no());
						listCardMap.put("out_store_id", assBackDetailInassets.getOut_store_id());
						listCardMap.put("out_store_no", assBackDetailInassets.getOut_store_no());
						listCardMap.put("in_store_id", assBackDetailInassets.getIn_store_id());
						listCardMap.put("in_store_no", assBackDetailInassets.getIn_store_no());
						listCardMap.put("note", assBackDetailInassets.getNote());
						listCardMap.put("ass_year", current_year);
						listCardMap.put("ass_month", current_month);
						if (map.containsKey(assBackDetailInassets.getAss_card_no())) {
							return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
						}
						map.put(assBackDetailInassets.getAss_card_no(), assBackDetailInassets.getAss_card_no());
						listCardVo.add(listCardMap);
					}
				} else {
					flag = false;
					break;
				}

				if (assTranStoreInassets.getState() == 2) {
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
				assInMainJson = assTranStoreInassetsService.updateConfirmTranStoreInassets(listVo, listCardVo);
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
		/*@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/updateConfirmTranStoreInassetsdetail", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmTranStoreInassetsdetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

				
				
				AssTranStoreInassets assTranStoreInassets = assTranStoreInassetsService.queryByCode(mapVo);
				if (DateUtil.compareDate(assTranStoreInassets.getCreate_date(), new Date())) {
					return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
				}

				List<AssTranStoreDetailInassets> list = assTranStoreInassetsService.queryByTranNo(mapVo);

				if (list != null && list.size() > 0) {
					for (AssTranStoreDetailInassets assBackDetailInassets : list) {
						Map<String, Object> listCardMap = new HashMap<String, Object>();
						listCardMap.put("group_id", assBackDetailInassets.getGroup_id());
						listCardMap.put("hos_id", assBackDetailInassets.getHos_id());
						listCardMap.put("copy_code", assBackDetailInassets.getCopy_code());
						listCardMap.put("ass_card_no", assBackDetailInassets.getAss_card_no());
						listCardMap.put("out_store_id", assBackDetailInassets.getOut_store_id());
						listCardMap.put("out_store_no", assBackDetailInassets.getOut_store_no());
						listCardMap.put("in_store_id", assBackDetailInassets.getIn_store_id());
						listCardMap.put("in_store_no", assBackDetailInassets.getIn_store_no());
						listCardMap.put("note", assBackDetailInassets.getNote());
						listCardMap.put("ass_year", current_year);
						listCardMap.put("ass_month", current_month);
						if (map.containsKey(assBackDetailInassets.getAss_card_no())) {
							return JSONObject.parseObject("{\"warn\":\"存在转移相同的卡片,不能移库! \"}");
						}
						map.put(assBackDetailInassets.getAss_card_no(), assBackDetailInassets.getAss_card_no());
						listCardVo.add(listCardMap);
					}
				} else {
					flag = false;
					break;
				}

				if (assTranStoreInassets.getState() == 2) {
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
				assInMainJson = assTranStoreInassetsService.updateConfirmTranStoreInassets(listVo, listCardVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assInMainJson);
		}*/

		/**
		 * @Description 查询数据 050901 资产转移主表(仓库到仓库)_无形资产
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/queryAssTranStoreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssTranStoreInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assTranStoreInassets = null;
			if("0".equals(mapVo.get("show_detail").toString())){
				assTranStoreInassets = assTranStoreInassetsService.query(getPage(mapVo));
			}else{
				assTranStoreInassets = assTranStoreInassetsService.queryDetails(getPage(mapVo));
			}
			return JSONObject.parseObject(assTranStoreInassets);

		}

		/**
		 * @Description 删除数据 050901 资产转移明细(仓库到仓库)_无形资产
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/deleteAssTranStoreDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssTranStoreDetailInassets(@RequestParam(value = "ParamVo") String paramVo,
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
				
				AssTranStoreInassets assTranStoreInassets = assTranStoreInassetsService.queryByCode(mapVo);
				if(assTranStoreInassets != null){
					if(assTranStoreInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已移库确认的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);

			}

			String assTranStoreDetailInassetsJson = assTranStoreDetailInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assTranStoreDetailInassetsJson);

		}

		/**
		 * @Description 查询数据 050901 资产转移明细(仓库到仓库)_无形资产
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/queryAssTranStoreDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssTranStoreDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String assTranStoreDetailInassets = assTranStoreDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assTranStoreDetailInassets);

		}
    
		/**
		 * @Description
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asstran/store/queryState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			String str = "";
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			List<String> list = assTranStoreInassetsService.queryState(mapVo);
			
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

