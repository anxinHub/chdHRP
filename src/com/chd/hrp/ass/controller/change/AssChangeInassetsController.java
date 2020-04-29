/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.change;

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
import com.chd.hrp.ass.entity.change.AssChangeInassets;
import com.chd.hrp.ass.service.change.AssChangeDetailInassetsService;
import com.chd.hrp.ass.service.change.AssChangeInassetsService;

/**
 * 
 * @Description: 050805 资产原值变动(无形资产)
 * @Table: ASS_Change_INASSETS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssChangeInassetsController extends BaseController {

	private static Logger logger = Logger.getLogger(AssChangeInassetsController.class);

	// 引入Service服务
	@Resource(name = "assChangeInassetsService")
	private final AssChangeInassetsService assChangeInassetsService = null;

	@Resource(name = "assChangeDetailInassetsService")
	private final AssChangeDetailInassetsService assChangeDetailInassetsService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/assChangeInassetsMainPage", method = RequestMethod.GET)
	public String assChangeInassetsMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05051",MyConfig.getSysPara("05051"));
		
		return "hrp/ass/assinassets/asschange/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/assChangeInassetsAddPage", method = RequestMethod.GET)
	public String assChangeInassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/asschange/add";

	}

	/**
	 * @Description 添加数据 050805 资产原值变动(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/saveAssChangeInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeInassets(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssChangeInassets assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

		if (assChangeInassets != null) {
			if (assChangeInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeInassetsJson = assChangeInassetsService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChangeInassetsJson);

	}

	@RequestMapping(value = "/hrp/ass/assinassets/asschange/updateConfirmChangeInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChangeInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("change_no", ids[3]);
			mapVo.put("change_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			AssChangeInassets assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

			if (assChangeInassets.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assChangeInassetsService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050805 资产原值变动(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/assChangeInassetsUpdatePage", method = RequestMethod.GET)
	public String assChangeInassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeInassets assChangeInassets = new AssChangeInassets();

		assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChangeInassets.getGroup_id());
		mode.addAttribute("hos_id", assChangeInassets.getHos_id());
		mode.addAttribute("copy_code", assChangeInassets.getCopy_code());
		mode.addAttribute("change_no", assChangeInassets.getChange_no());
		mode.addAttribute("ven_id", assChangeInassets.getVen_id());
		mode.addAttribute("ven_no", assChangeInassets.getVen_no());
		mode.addAttribute("ven_code", assChangeInassets.getVen_code());
		mode.addAttribute("ven_name", assChangeInassets.getVen_name());
		mode.addAttribute("create_emp", assChangeInassets.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assChangeInassets.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChangeInassets.getAudit_emp());
		mode.addAttribute("change_date", assChangeInassets.getChange_date());
		mode.addAttribute("state", assChangeInassets.getState());
		mode.addAttribute("note", assChangeInassets.getNote());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05051",MyConfig.getSysPara("05051"));
		
		return "hrp/ass/assinassets/asschange/update";
	}

	/**
	 * @Description 删除数据 050805 资产原值变动(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/deleteAssChangeInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeInassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeInassets assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

			if (assChangeInassets != null) {
				if (assChangeInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeInassetsJson = assChangeInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeInassetsJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/queryAssChangeInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeInassets = assChangeInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeInassets);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/deleteAssChangeDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeDetailInassets(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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

			AssChangeInassets assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

			if (assChangeInassets != null) {
				if (assChangeInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailInassetsJson = assChangeDetailInassetsService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailInassetsJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/queryAssChangeDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeDetailInassets = assChangeDetailInassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeDetailInassets);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/deleteAssChangeSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSourceInassets(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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

			AssChangeInassets assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

			if (assChangeInassets != null) {
				if (assChangeInassets.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceInassetsJson = assChangeInassetsService.deleteAssChangeSourceInassets(listVo);

		return JSONObject.parseObject(assChangeSourceInassetsJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/queryAssChangeSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assChangeSourceInassets = assChangeInassetsService.queryAssChangeSourceInassets(getPage(mapVo));

		return JSONObject.parseObject(assChangeSourceInassets);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(无形资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinassets/asschange/saveAssChangeSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeInassets assChangeInassets = assChangeInassetsService.queryByCode(mapVo);

		if (assChangeInassets != null) {
			if (assChangeInassets.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSourceInassetsJson = assChangeInassetsService.saveAssChangeSourceInassets(mapVo);

		return JSONObject.parseObject(assChangeSourceInassetsJson);

	}

		/**
		 * @Description
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/ass/assinassets/asschange/queryState", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
				throws Exception {
			String str = "";
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			List<String> list = assChangeInassetsService.queryState(mapVo);
			
			if(list.size()>0){
				for (String tran_no : list) {
					str += tran_no +" ";
				}
				return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据是未确认,无法打印!\",\"state\":\"true\"}");
				
			}else{
				return JSONObject.parseObject("{\"state\":\"true\"}");
			}
			
		}
		
}
