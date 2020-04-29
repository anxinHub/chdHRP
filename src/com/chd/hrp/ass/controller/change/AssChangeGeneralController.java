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
import com.chd.hrp.ass.entity.change.AssChangeGeneral;
import com.chd.hrp.ass.service.change.AssChangeDetailGeneralService;
import com.chd.hrp.ass.service.change.AssChangeGeneralService;

/**
 * 
 * @Description: 050805 资产原值变动(一般设备)
 * @Table: ASS_Change_GENERAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssChangeGeneralController extends BaseController {

	private static Logger logger = Logger.getLogger(AssChangeGeneralController.class);

	// 引入Service服务
	@Resource(name = "assChangeGeneralService")
	private final AssChangeGeneralService assChangeGeneralService = null;

	@Resource(name = "assChangeDetailGeneralService")
	private final AssChangeDetailGeneralService assChangeDetailGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/assChangeGeneralMainPage", method = RequestMethod.GET)
	public String assChangeGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05031",MyConfig.getSysPara("05031"));
		
		return "hrp/ass/assgeneral/asschange/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/assChangeGeneralAddPage", method = RequestMethod.GET)
	public String assChangeGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asschange/add";

	}

	/**
	 * @Description 添加数据 050805 资产原值变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/saveAssChangeGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssChangeGeneral assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

		if (assChangeGeneral != null) {
			if (assChangeGeneral.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeGeneralJson = assChangeGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChangeGeneralJson);

	}

	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/updateConfirmChangeGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChangeGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeGeneral assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

			if (assChangeGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assChangeGeneralService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050805 资产原值变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/assChangeGeneralUpdatePage", method = RequestMethod.GET)
	public String assChangeGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeGeneral assChangeGeneral = new AssChangeGeneral();

		assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChangeGeneral.getGroup_id());
		mode.addAttribute("hos_id", assChangeGeneral.getHos_id());
		mode.addAttribute("copy_code", assChangeGeneral.getCopy_code());
		mode.addAttribute("change_no", assChangeGeneral.getChange_no());
		mode.addAttribute("ven_id", assChangeGeneral.getVen_id());
		mode.addAttribute("ven_no", assChangeGeneral.getVen_no());
		mode.addAttribute("ven_code", assChangeGeneral.getVen_code());
		mode.addAttribute("ven_name", assChangeGeneral.getVen_name());
		mode.addAttribute("create_emp", assChangeGeneral.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assChangeGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChangeGeneral.getAudit_emp());
		mode.addAttribute("change_date", assChangeGeneral.getChange_date());
		mode.addAttribute("state", assChangeGeneral.getState());
		mode.addAttribute("note", assChangeGeneral.getNote());
		mode.addAttribute("invoice_no", assChangeGeneral.getInvoice_no());
		mode.addAttribute("invoice_date", DateUtil.dateToString(assChangeGeneral.getInvoice_date(),"yyyy-MM-dd"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05031",MyConfig.getSysPara("05031"));
		
		return "hrp/ass/assgeneral/asschange/update";
	}

	/**
	 * @Description 删除数据 050805 资产原值变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/deleteAssChangeGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeGeneral assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

			if (assChangeGeneral != null) {
				if (assChangeGeneral.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeGeneralJson = assChangeGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeGeneralJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/queryAssChangeGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeGeneral = assChangeGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeGeneral);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/deleteAssChangeDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeGeneral assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

			if (assChangeGeneral != null) {
				if (assChangeGeneral.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailGeneralJson = assChangeDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/queryAssChangeDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeDetailGeneral = assChangeDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeDetailGeneral);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/deleteAssChangeSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSourceGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeGeneral assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

			if (assChangeGeneral != null) {
				if (assChangeGeneral.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceGeneralJson = assChangeGeneralService.deleteAssChangeSourceGeneral(listVo);

		return JSONObject.parseObject(assChangeSourceGeneralJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/queryAssChangeSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSourceGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assChangeSourceGeneral = assChangeGeneralService.queryAssChangeSourceGeneral(getPage(mapVo));

		return JSONObject.parseObject(assChangeSourceGeneral);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/saveAssChangeSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSourceGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeGeneral assChangeGeneral = assChangeGeneralService.queryByCode(mapVo);

		if (assChangeGeneral != null) {
			if (assChangeGeneral.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSourceGeneralJson = assChangeGeneralService.saveAssChangeSourceGeneral(mapVo);

		return JSONObject.parseObject(assChangeSourceGeneralJson);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<String> list = assChangeGeneralService.queryState(mapVo);

		if (list.size() > 0) {
			for (String tran_no : list) {
				str += tran_no + " ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为" + str + "的数据是未确认,无法打印!\",\"state\":\"true\"}");

		} else {
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}

	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/asschange/updateAssChangeMainGeneralBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssChangeMainGeneralBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assChangeGeneralService.updateAssChangeMainBillNo(mapVo);

		return JSONObject.parseObject(assInMainSpecial);
	}

}
