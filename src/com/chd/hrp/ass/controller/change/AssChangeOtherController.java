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
import com.chd.hrp.ass.entity.change.AssChangeOther;
import com.chd.hrp.ass.service.change.AssChangeDetailOtherService;
import com.chd.hrp.ass.service.change.AssChangeOtherService;

/**
 * 
 * @Description: 050805 资产原值变动(其他固定资产)
 * @Table: ASS_Change_OTHER
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssChangeOtherController extends BaseController {

	private static Logger logger = Logger.getLogger(AssChangeOtherController.class);

	// 引入Service服务
	@Resource(name = "assChangeOtherService")
	private final AssChangeOtherService assChangeOtherService = null;

	@Resource(name = "assChangeDetailOtherService")
	private final AssChangeDetailOtherService assChangeDetailOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/assChangeOtherMainPage", method = RequestMethod.GET)
	public String assChangeOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05041",MyConfig.getSysPara("05041"));
		
		return "hrp/ass/assother/asschange/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/assChangeOtherAddPage", method = RequestMethod.GET)
	public String assChangeOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/asschange/add";

	}

	/**
	 * @Description 添加数据 050805 资产原值变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/saveAssChangeOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeOther(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssChangeOther assChangeOther = assChangeOtherService.queryByCode(mapVo);

		if (assChangeOther != null) {
			if (assChangeOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeOtherJson = assChangeOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChangeOtherJson);

	}

	@RequestMapping(value = "/hrp/ass/assother/asschange/updateConfirmChangeOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChangeOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeOther assChangeOther = assChangeOtherService.queryByCode(mapVo);

			if (assChangeOther.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assChangeOtherService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050805 资产原值变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/assChangeOtherUpdatePage", method = RequestMethod.GET)
	public String assChangeOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeOther assChangeOther = new AssChangeOther();

		assChangeOther = assChangeOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChangeOther.getGroup_id());
		mode.addAttribute("hos_id", assChangeOther.getHos_id());
		mode.addAttribute("copy_code", assChangeOther.getCopy_code());
		mode.addAttribute("change_no", assChangeOther.getChange_no());
		mode.addAttribute("ven_id", assChangeOther.getVen_id());
		mode.addAttribute("ven_no", assChangeOther.getVen_no());
		mode.addAttribute("ven_code", assChangeOther.getVen_code());
		mode.addAttribute("ven_name", assChangeOther.getVen_name());
		mode.addAttribute("create_emp", assChangeOther.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assChangeOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChangeOther.getAudit_emp());
		mode.addAttribute("change_date", assChangeOther.getChange_date());
		mode.addAttribute("state", assChangeOther.getState());
		mode.addAttribute("note", assChangeOther.getNote());
		mode.addAttribute("invoice_no", assChangeOther.getInvoice_no());
		mode.addAttribute("invoice_date", DateUtil.dateToString(assChangeOther.getInvoice_date(),"yyyy-MM-dd"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05041",MyConfig.getSysPara("05041"));
		
		return "hrp/ass/assother/asschange/update";
	}

	/**
	 * @Description 删除数据 050805 资产原值变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/deleteAssChangeOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeOther assChangeOther = assChangeOtherService.queryByCode(mapVo);

			if (assChangeOther != null) {
				if (assChangeOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeOtherJson = assChangeOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeOtherJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/queryAssChangeOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeOther = assChangeOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeOther);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/deleteAssChangeDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeOther assChangeOther = assChangeOtherService.queryByCode(mapVo);

			if (assChangeOther != null) {
				if (assChangeOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailOtherJson = assChangeDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/queryAssChangeDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeDetailOther = assChangeDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeDetailOther);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/deleteAssChangeSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSourceOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeOther assChangeOther = assChangeOtherService.queryByCode(mapVo);

			if (assChangeOther != null) {
				if (assChangeOther.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceOtherJson = assChangeOtherService.deleteAssChangeSourceOther(listVo);

		return JSONObject.parseObject(assChangeSourceOtherJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/queryAssChangeSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSourceOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assChangeSourceOther = assChangeOtherService.queryAssChangeSourceOther(getPage(mapVo));

		return JSONObject.parseObject(assChangeSourceOther);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/saveAssChangeSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSourceOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeOther assChangeOther = assChangeOtherService.queryByCode(mapVo);

		if (assChangeOther != null) {
			if (assChangeOther.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSourceOtherJson = assChangeOtherService.saveAssChangeSourceOther(mapVo);

		return JSONObject.parseObject(assChangeSourceOtherJson);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/asschange/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assChangeOtherService.queryState(mapVo);
		
		if(list.size()>0){
			for (String tran_no : list) {
				str += tran_no +" ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据是未确认,无法打印!\",\"state\":\"true\"}");
			
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
		
	}
	
	@RequestMapping(value = "/hrp/ass/assother/asschange/updateAssChangeMainOtherBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssChangeMainOtherBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assChangeOtherService.updateAssChangeMainBillNo(mapVo);

		return JSONObject.parseObject(assInMainSpecial);
	}
}
