/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.allot.in;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.ass.entity.allot.in.AssAllotInInassets;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutInassets;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.service.allot.in.AssAllotInDetailInassetsService;
import com.chd.hrp.ass.service.allot.in.AssAllotInInassetsService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutDetailInassetsService;
import com.chd.hrp.ass.service.allot.out.AssAllotOutInassetsService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表(无形资产)
 * @Table:
 * ASS_ALLOT_IN_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssAllotInInassetsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssAllotInInassetsController.class);
	
	// 引入Service服务
		@Resource(name = "assAllotInInassetsService")
		private final AssAllotInInassetsService assAllotInInassetsService = null;

		@Resource(name = "assAllotInDetailInassetsService")
		private final AssAllotInDetailInassetsService assAllotInDetailInassetsService = null;
		
		@Resource(name = "assCardInassetsService")
		private final AssCardInassetsService assCardInassetsService = null;
		
		@Resource(name = "assAllotOutInassetsService")
		private final AssAllotOutInassetsService assAllotOutInassetsService = null;
		
		@Resource(name = "assAllotOutDetailInassetsService")
		private final AssAllotOutDetailInassetsService assAllotOutDetailInassetsService = null;

		/**
		 * @Description 主页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/assAllotInInassetsMainPage", method = RequestMethod.GET)
		public String assAllotInInassetsMainPage(Model mode) throws Exception {
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			mode.addAttribute("ass_05048",MyConfig.getSysPara("05048"));
			
			return "hrp/ass/assinassets/assallotin/main";
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/assImportAllotOutInassetsPage", method = RequestMethod.GET)
		public String assImportAllotOutInassetsPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
			mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
			mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
			mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
			mode.addAttribute("out_store_code", mapVo.get("out_store_code"));
			mode.addAttribute("out_store_name", mapVo.get("out_store_name"));
			mode.addAttribute("create_date", mapVo.get("create_date"));
			mode.addAttribute("in_store_id", mapVo.get("in_store_id"));
			mode.addAttribute("in_store_no", mapVo.get("in_store_no"));
			mode.addAttribute("note", mapVo.get("note"));
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/assallotin/importoutmain";
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/assViewAllotOutInassetsPage", method = RequestMethod.GET)
		public String assViewAllotOutInassetsPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			mode.addAttribute("out_group_id", mapVo.get("out_group_id"));
			mode.addAttribute("out_hos_id", mapVo.get("out_hos_id"));
			mode.addAttribute("out_store_id", mapVo.get("out_store_id"));
			mode.addAttribute("out_store_no", mapVo.get("out_store_no"));
			mode.addAttribute("allot_in_no", mapVo.get("allot_in_no"));
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/assallotin/viewoutmain";
		}

		/**
		 * @Description 添加页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/assAllotInInassetsAddPage", method = RequestMethod.GET)
		public String assAllotInInassetsAddPage(Model mode) throws Exception {
			
			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/assallotin/add";
		}

		/**
		 * @Description 添加数据 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/saveAssAllotInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssAllotInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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
			
			AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
			if(assAllotInInassets != null){
				if(assAllotInInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
			}

			String assAllotInInassetsJson = assAllotInInassetsService.addOrUpdate(mapVo);

			return JSONObject.parseObject(assAllotInInassetsJson);

		}

		/**
		 * @Description 更新跳转页面 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/assAllotInInassetsUpdatePage", method = RequestMethod.GET)
		public String assAllotInInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			AssAllotInInassets assAllotInInassets = new AssAllotInInassets();

			assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
			mode.addAttribute("is_import", mapVo.get("is_import"));
			mode.addAttribute("group_id", assAllotInInassets.getGroup_id());
			mode.addAttribute("hos_id", assAllotInInassets.getHos_id());
			mode.addAttribute("copy_code", assAllotInInassets.getCopy_code());
			mode.addAttribute("allot_in_no", assAllotInInassets.getAllot_in_no());
			mode.addAttribute("in_store_id", assAllotInInassets.getIn_store_id());
			mode.addAttribute("in_store_no", assAllotInInassets.getIn_store_no());
			mode.addAttribute("in_store_code", assAllotInInassets.getIn_store_code());
			mode.addAttribute("in_store_name", assAllotInInassets.getIn_store_name());
			mode.addAttribute("out_group_id", assAllotInInassets.getOut_group_id());
			mode.addAttribute("out_group_name", assAllotInInassets.getOut_group_name());
			mode.addAttribute("out_hos_id", assAllotInInassets.getOut_hos_id());
			mode.addAttribute("out_hos_name", assAllotInInassets.getOut_hos_name());
			mode.addAttribute("out_store_id", assAllotInInassets.getOut_store_id());
			mode.addAttribute("out_store_no", assAllotInInassets.getOut_store_no());
			mode.addAttribute("out_store_code", assAllotInInassets.getOut_store_code());
			mode.addAttribute("out_store_name", assAllotInInassets.getOut_store_name());
			mode.addAttribute("price", assAllotInInassets.getPrice());
			mode.addAttribute("add_depre", assAllotInInassets.getAdd_depre());
			mode.addAttribute("cur_money", assAllotInInassets.getCur_money());
			mode.addAttribute("fore_money", assAllotInInassets.getFore_money());
			mode.addAttribute("create_emp", assAllotInInassets.getCreate_emp());
			mode.addAttribute("create_emp_name", assAllotInInassets.getCreate_emp_name());
			mode.addAttribute("create_date", DateUtil.dateToString(assAllotInInassets.getCreate_date(),"yyyy-MM-dd"));
			mode.addAttribute("audit_emp", assAllotInInassets.getAudit_emp());
			mode.addAttribute("audit_emp_name", assAllotInInassets.getAudit_emp_name());
			mode.addAttribute("audit_date", DateUtil.dateToString(assAllotInInassets.getAudit_date(),"yyyy-MM-dd"));
			mode.addAttribute("state", assAllotInInassets.getState());
			mode.addAttribute("state_name", assAllotInInassets.getState_name());
			mode.addAttribute("note", assAllotInInassets.getNote());

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			mode.addAttribute("ass_05048",MyConfig.getSysPara("05048"));
			
			return "hrp/ass/assinassets/assallotin/update";
		}
		
		
		/**
		 * @Description 入库确认
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/updateConfirmAllotInMainInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmAllotInMainInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			String assInMainJson = "";
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("allot_in_no", ids[3]);
				mapVo.put("ass_in_no", ids[3]);
				AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
				if(DateUtil.compareDate(assAllotInInassets.getCreate_date(), new Date())){
					return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
				}
				List<Map<String, Object>> list = assAllotInDetailInassetsService.queryByInit(mapVo);
				if(list.size() == 0){
					return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
				}
				for(Map<String, Object> temp : list){
					
					Map<String, Object> mapExists = new HashMap<String, Object>();

					mapExists.put("group_id", temp.get("group_id"));

					mapExists.put("hos_id", temp.get("hos_id"));
					
					mapExists.put("copy_code", temp.get("copy_code"));

					mapExists.put("ass_in_no", temp.get("ass_in_no"));
					
					mapExists.put("ass_in_no", temp.get("allot_in_no"));
					
					mapExists.put("ass_id", temp.get("ass_id"));
					
					mapExists.put("ass_no", temp.get("ass_no"));
					
					Integer use_state = assCardInassetsService.queryCardExistsByAssInNo(mapExists);
					
					if(use_state == 0){
						return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
					}
					
					List<Map<String, Object>> cardList = assCardInassetsService.queryAssCardByAssInNo(mapExists);
					boolean assFlag = true,
							oriCardNo = true,
							specFlag = true,
							modelFlag = true,
							brandFlag = true,
							facFlag = true,
							priceFlag = true,
							depreFlag = true,      
							depreMonthFlag = true,
							curMoneyFlag = true,      
							foreMoneyFlag = true,  
							inStoreFlag = true;
							
					for(Map<String, Object> card : cardList){
						if(verification(temp.get("ass_id"),card.get("ass_id"))){
							assFlag = false;
							break;
						}
						if(verification(temp.get("ass_ori_card_no"),card.get("ass_ori_card_no"))){
							oriCardNo = false;
							break;
						}
						if(verification(temp.get("ass_spec"),card.get("ass_spec"))){
							specFlag = false;
							break;
						}
						if(verification(temp.get("ass_model"),card.get("ass_mondl"))){
							modelFlag = false;
							break;
						}
						if(verification(temp.get("ass_brand"),card.get("ass_brand"))){
							brandFlag = false;
							break;
						}
						if(verification(temp.get("fac_id"),card.get("fac_id"))){
							facFlag = false;
							break;
						}
						if(verification(temp.get("price"),card.get("price"))){
							priceFlag = false;
							break;
						}
						if(verification(temp.get("add_depre"),card.get("depre_money"))){
							depreFlag = false;
							break;
						}
						if(verification(temp.get("add_depre_month"),card.get("add_depre_month"))){
							depreMonthFlag = false;
							break;
						}
						if(verification(temp.get("cur_money"),card.get("cur_money"))){
							curMoneyFlag = false;
							break;
						}
						if(verification(temp.get("fore_money"),card.get("fore_money"))){
							foreMoneyFlag = false;
							break;
						}
						if(verification(temp.get("in_store_id"),card.get("store_id"))){
							inStoreFlag = false;
							break;
						}
					}
					if(!assFlag){
						return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
					}
					if(!oriCardNo){
						return JSONObject.parseObject("{\"warn\":\"存在原始卡片号不匹配的资产,不能入库! \"}");
					}
					if(!specFlag){
						return JSONObject.parseObject("{\"warn\":\"存在规格不匹配的资产,不能入库! \"}");
					}
					if(!modelFlag){
						return JSONObject.parseObject("{\"warn\":\"存在型号不匹配的资产,不能入库! \"}");
					}
					if(!brandFlag){
						return JSONObject.parseObject("{\"warn\":\"存在品牌不匹配的资产,不能入库! \"}");
					}
					if(!facFlag){
						return JSONObject.parseObject("{\"warn\":\"存在生产厂商不匹配的资产,不能入库! \"}");
					}
					if(!priceFlag){
						return JSONObject.parseObject("{\"warn\":\"存在资产原值和卡片原值不匹配的资产,不能入库! \"}");
					}
					if(!depreFlag){
						return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧和卡片累计折旧不匹配的资产,不能入库! \"}");
					}
					if(!depreMonthFlag){
						return JSONObject.parseObject("{\"warn\":\"存在资产累计折旧月份和卡片累计折旧月份不匹配的资产,不能入库! \"}");
					}
					if(!curMoneyFlag){
						return JSONObject.parseObject("{\"warn\":\"存在资产净值和卡片资产净值不匹配的资产,不能入库! \"}");
					}
					if(!foreMoneyFlag){
						return JSONObject.parseObject("{\"warn\":\"存在资产预留残值和预留残值不匹配的资产,不能入库! \"}");
					}
					if(!inStoreFlag){
						return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
					}
				}
				
			}
			
			
			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				mapVo.put("group_id", ids[0]);

				mapVo.put("hos_id", ids[1]);

				mapVo.put("copy_code", ids[2]);

				mapVo.put("allot_in_no", ids[3]);
				
				mapVo.put("ass_in_no", ids[3]);
				
				mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
				
				mapVo.put("audit_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
				
				mapVo.put("confirm_emp", SessionManager.getUserId());
				
				mapVo.put("audit_emp", SessionManager.getUserId());
				

				AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
				
				if (assAllotInInassets.getState() == 2) {
					continue;
				}else{
					listVo.add(mapVo);
				}
				
			}
			
			if(listVo.size() == 0){
				return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
			}
			
			try {
				assInMainJson = assAllotInInassetsService.updateConfirm(listVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

			return JSONObject.parseObject(assInMainJson);
		}
		
		private boolean verification(Object obj1,Object obj2){
			//true为不相等
			if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
				Double number1 = obj1 == null || obj1.equals("") ? 0 : Double.parseDouble(String.valueOf(obj1));
				Double number2 = obj2 == null || obj2.equals("") ? 0 : Double.parseDouble(String.valueOf(obj2));
				if(Math.abs(number1 - number2) > 0){
					return true;
				}else{
					return false;
				}
			}
			if(obj1 == null && obj2 == null){
				return false;
			}
			if(obj1 == null && obj2 != null){
				return true;
			}
			if(obj1 != null && obj2 == null){
				return true;
			}
			if(obj1 != null && obj2 != null){
				if(!obj1.equals(obj2)){
					return true;
				}else {
					return false;
				}
			}else{
				return false;
			}
		}

		/**
		 * @Description 删除数据 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/deleteAssAllotInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssAllotInInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			boolean flag = true;
			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("allot_in_no", ids[3]);
				mapVo.put("ass_in_no", ids[3]);
				
				AssAllotInInassets assInInassets = assAllotInInassetsService.queryByCode(mapVo);
				
				if(assInInassets.getState() > 0){
					flag = false;
					break;
				}

				listVo.add(mapVo);

			}
			
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
			}

			String assAllotInInassetsJson = assAllotInInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assAllotInInassetsJson);

		}

		/**
		 * @Description 查询数据 050901 资产无偿调拨入库单主表（无形资产）
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryAssAllotInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssAllotInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String assAllotInInassets = null;
			
			if("0".equals(mapVo.get("show_detail").toString())){
			
				assAllotInInassets = assAllotInInassetsService.query(getPage(mapVo));
			
			}else{
				
				assAllotInInassets = assAllotInInassetsService.queryDetails(getPage(mapVo));
				
			}
			
			return JSONObject.parseObject(assAllotInInassets);

		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/initAssAllotInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> initAssAllotInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("create_emp", SessionManager.getUserId());
			
			String assAllotInInassets = assAllotInInassetsService.initAssAllotInInassets(mapVo);

			return JSONObject.parseObject(assAllotInInassets);

		}
		
		
		//查询出库单引入使用
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryByAllotInImport", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryByAllotInImport(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String assAllotInInassets = assAllotOutInassetsService.queryByAllotInImport(getPage(mapVo));

			return JSONObject.parseObject(assAllotInInassets);
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryByAllotInImportView", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryByAllotInImportView(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			if(mapVo.get("group_id") == null){
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String assAllotInInassets = assAllotOutInassetsService.queryByAllotInImportView(getPage(mapVo));

			return JSONObject.parseObject(assAllotInInassets);
		}
		

		/**
		 * @Description 删除数据 050901 资产无偿调拨入库明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/deleteAssAllotInDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssAllotInDetailInassets(@RequestParam(value = "ParamVo") String paramVo,
				Model mode) throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			boolean flag = true;
			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("allot_in_no", ids[3]);
				mapVo.put("ass_id", ids[4]);
				mapVo.put("ass_no", ids[5]);
				mapVo.put("ass_ori_card_no", ids[6]);
				mapVo.put("ass_in_no", ids[3]);
				AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
				
				if(assAllotInInassets.getState() > 0){
					flag = false;
					break;
				}

				listVo.add(mapVo);

			}

			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			String assAllotInDetailInassetsJson = assAllotInDetailInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assAllotInDetailInassetsJson);

		}

		/**
		 * @Description 查询数据 050901 资产无偿调拨入库明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryAssAllotInDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssAllotInDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assAllotInDetailInassets = assAllotInDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assAllotInDetailInassets);

		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryAssAllotInCardInassets", method = RequestMethod.POST)
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
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/initAssAllotInCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> initAssAllotInCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("ass_in_no",mapVo.get("allot_in_no").toString());
			
			String assCard = "";
			try {
				AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
				
				if(assAllotInInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
				
				
				assCard = assAllotInInassetsService.initAssAllotInCardInassets(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
			
			return JSONObject.parseObject(assCard);
		}

		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/initAssAllotInBatchCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> initAssAllotInBatchCardInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("ass_in_no",mapVo.get("allot_in_no").toString());
			
			String assCard = "";
			try {
				AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
				
				if(assAllotInInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
				
				
				assCard = assAllotInInassetsService.initAssAllotInBatchCardInassets(mapVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
			
			return JSONObject.parseObject(assCard);
		}
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/deleteAssAllotInCardInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssCard(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
				Model mode) throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			boolean flag = true;
			boolean flag1 = true;
			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("ass_card_no", ids[3]);
				mapVo.put("allot_in_no", ids[4]);
				mapVo.put("ass_in_no", ids[4]);
				AssAllotInInassets assAllotInInassets = assAllotInInassetsService.queryByCode(mapVo);
				
				if(assAllotInInassets.getState() > 0){
					flag = false;
					break;
				}

				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if (assCardInassets.getUse_state() > 0) {
					flag1 = false;
				}

				listVo.add(mapVo);

			}
			
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
			}

			if (!flag1) {
				return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
			}

			String assCardGeneralJson = "";
			try {
				assCardGeneralJson = assCardInassetsService.deleteBatch(listVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
			return JSONObject.parseObject(assCardGeneralJson);

		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/assAllotOutInassetsViewPage", method = RequestMethod.GET)
		public String assAllotOutInassetsViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			
			return "hrp/ass/assinassets/assallotin/viewout";
		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryAllotOutDetByAllotInInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAllotOutDetByAllotInInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assAllotOutDetailInassets = assAllotOutDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assAllotOutDetailInassets);
		}
		
		/**
		 * 其他无形资产  资产调剂入库  入库单状态查询（打印校验数据用）
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assallotin/queryAssAllotInInassetsState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssAllotInInassetsState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			//入库单状态查询  （打印时校验数据专用）
			List<String> list = assAllotInInassetsService.queryAssAllotInInassetsState(mapVo);
			
			if(list.size() == 0){
				
				return JSONObject.parseObject("{\"state\":\"true\"}");
				
			}else{
				
				String  str = "" ;
				for(String item : list){
					
					str += item +"," ;
				}
				
				return JSONObject.parseObject("{\"error\":\"入库单【"+str+"】不是确认状态不能打印.\",\"state\":\"false\"}");
			}
			
			
		}

}

