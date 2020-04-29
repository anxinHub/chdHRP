/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.depre.change;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.depre.change.AssDepreInassets;
import com.chd.hrp.ass.entity.depre.change.AssDepreInassets;
import com.chd.hrp.ass.service.depre.change.AssDepreDetailInassetsService;
import com.chd.hrp.ass.service.depre.change.AssDepreInassetsService;
import com.chd.hrp.ass.service.depre.change.AssDepreInassetsService;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(无形资产)
 * @Table:
 * ASS_DEPRE_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssDepreInassetsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssDepreInassetsController.class);
	
	// 引入Service服务
		@Resource(name = "assDepreInassetsService")
		private final AssDepreInassetsService assDepreInassetsService = null;

		@Resource(name = "assDepreDetailInassetsService")
		private final AssDepreDetailInassetsService assDepreDetailInassetsService = null;

		/**
		 * @Description 主页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/assDepreInassetsMainPage", method = RequestMethod.GET)
		public String assDepreInassetsMainPage(Model mode) throws Exception {

			return "hrp/ass/assinassets/assdepre/main";

		}

		/**
		 * @Description 添加页面跳转
		 * @param mode
		 * @return String
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/assDepreInassetsAddPage", method = RequestMethod.GET)
		public String assDepreInassetsAddPage(Model mode) throws Exception {

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/assdepre/add";

		}

		/**
		 * @Description 添加数据 050806 资产累计折旧变动(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/saveAssDepreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addAssDepreInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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
			
			AssDepreInassets assDepreInassets = assDepreInassetsService.queryByCode(mapVo);
			
			if(assDepreInassets != null){
				if(assDepreInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
				}
			}

			String assDepreInassetsJson = assDepreInassetsService.addOrUpdate(mapVo);

			return JSONObject.parseObject(assDepreInassetsJson);

		}
		
		
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/updateConfirmDepreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> updateConfirmDepreInassets(@RequestParam(value = "ParamVo") String paramVo,
				Model mode) throws Exception {
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			String assInMainJson = "";
			for (String id : paramVo.split(",")) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("change_no", ids[3]);
				mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
				mapVo.put("audit_emp", SessionManager.getUserId());
				
				AssDepreInassets assDepreInassets = assDepreInassetsService.queryByCode(mapVo);

				if (assDepreInassets.getState() == 2) {
					continue;
				} else {
					listVo.add(mapVo);
				}
			}
			if (listVo.size() == 0) {
				return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
			}
			try {
				assInMainJson = assDepreInassetsService.updateConfirm(listVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
			
			return JSONObject.parseObject(assInMainJson);
		}
		

		/**
		 * @Description 更新跳转页面 050806 资产累计折旧变动(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/assDepreInassetsUpdatePage", method = RequestMethod.GET)
		public String assDepreInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			AssDepreInassets assDepreInassets = new AssDepreInassets();

			assDepreInassets = assDepreInassetsService.queryByCode(mapVo);

			mode.addAttribute("group_id", assDepreInassets.getGroup_id());
			mode.addAttribute("hos_id", assDepreInassets.getHos_id());
			mode.addAttribute("copy_code", assDepreInassets.getCopy_code());
			mode.addAttribute("change_no", assDepreInassets.getChange_no());
			mode.addAttribute("create_emp", assDepreInassets.getCreate_emp());
			mode.addAttribute("create_date", DateUtil.dateToString(assDepreInassets.getCreate_date(), "yyyy-MM-dd"));
			mode.addAttribute("audit_emp", assDepreInassets.getAudit_emp());
			mode.addAttribute("audit_date", assDepreInassets.getAudit_date());
			mode.addAttribute("state", assDepreInassets.getState());

			mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
			mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
			
			return "hrp/ass/assinassets/assdepre/update";
		}

		/**
		 * @Description 删除数据 050806 资产累计折旧变动(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/deleteAssDepreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssDepreInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("change_no", ids[3]);
				AssDepreInassets assDepreInassets = assDepreInassetsService.queryByCode(mapVo);
				
				if(assDepreInassets != null){
					if(assDepreInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
					}
				}
				listVo.add(mapVo);

			}

			String assDepreInassetsJson = assDepreInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assDepreInassetsJson);

		}

		/**
		 * @Description 查询数据 050806 资产累计折旧变动(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/queryAssDepreInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssDepreInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assDepreInassets = assDepreInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assDepreInassets);

		}
		
		/**
		 * @Description 删除数据 050805 资产原值变动明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/deleteAssDepreDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssDepreDetailInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("change_no", ids[3]);
				mapVo.put("ass_card_no", ids[4]);
				
				AssDepreInassets assDepreInassets = assDepreInassetsService.queryByCode(mapVo);
				
				if(assDepreInassets != null){
					if(assDepreInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
					}
				}


				listVo.add(mapVo);

			}

			String assDepreDetailInassetsJson = assDepreDetailInassetsService.deleteBatch(listVo);

			return JSONObject.parseObject(assDepreDetailInassetsJson);

		}

		/**
		 * @Description 查询数据 050805 资产原值变动明细(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/queryAssDepreDetailInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssDepreDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			String assDepreDetailInassets = assDepreDetailInassetsService.query(getPage(mapVo));

			return JSONObject.parseObject(assDepreDetailInassets);

		}
		
		
		/**
		 * @Description 删除数据 050805 资产原值变动资金来源(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/deleteAssDepreSourceInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> deleteAssDepreSourceInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String[] ids = id.split("@");

				// 表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("change_no", ids[3]);
				mapVo.put("ass_card_no", ids[4]);
				mapVo.put("source_id", ids[5]);
				
				AssDepreInassets assDepreInassets = assDepreInassetsService.queryByCode(mapVo);
				
				if(assDepreInassets != null){
					if(assDepreInassets.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
					}
				}

				listVo.add(mapVo);

			}

			String assDepreSourceInassetsJson = assDepreInassetsService.deleteAssDepreSourceInassets(listVo);

			return JSONObject.parseObject(assDepreSourceInassetsJson);

		}

		/**
		 * @Description 查询数据 050805 资产原值变动资金来源(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/queryAssDepreSourceInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryAssDepreSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			String assDepreSourceInassets = assDepreInassetsService.queryAssDepreSourceInassets(getPage(mapVo));

			return JSONObject.parseObject(assDepreSourceInassets);
		}

		/**
		 * @Description 添加数据 050805 资产原值变动资金来源(无形资产)
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/assdepre/saveAssDepreSourceInassets", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> saveAssDepreSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {

			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			AssDepreInassets assDepreInassets = assDepreInassetsService.queryByCode(mapVo);
			
			if(assDepreInassets != null){
				if(assDepreInassets.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
				}
			}

			String assDepreSourceInassetsJson = assDepreInassetsService.saveAssDepreSourceInassets(mapVo);

			return JSONObject.parseObject(assDepreSourceInassetsJson);

		}
	
}

