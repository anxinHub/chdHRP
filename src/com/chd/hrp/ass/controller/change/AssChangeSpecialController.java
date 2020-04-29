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
import com.chd.hrp.ass.entity.change.AssChangeSpecial;
import com.chd.hrp.ass.service.change.AssChangeDetailSpecialService;
import com.chd.hrp.ass.service.change.AssChangeSpecialService;

/**
 * 
 * @Description: 050805 资产原值变动(专用设备)
 * @Table: ASS_Change_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssChangeSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssChangeSpecialController.class);

	// 引入Service服务
	@Resource(name = "assChangeSpecialService")
	private final AssChangeSpecialService assChangeSpecialService = null;

	@Resource(name = "assChangeDetailSpecialService")
	private final AssChangeDetailSpecialService assChangeDetailSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/assChangeSpecialMainPage", method = RequestMethod.GET)
	public String assChangeSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05021",MyConfig.getSysPara("05021"));
		
		return "hrp/ass/assspecial/asschange/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/assChangeSpecialAddPage", method = RequestMethod.GET)
	public String assChangeSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asschange/add";

	}

	/**
	 * @Description 添加数据 050805 资产原值变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/saveAssChangeSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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
		AssChangeSpecial assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

		if (assChangeSpecial != null) {
			if (assChangeSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSpecialJson = assChangeSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChangeSpecialJson);

	}

	@RequestMapping(value = "/hrp/ass/assspecial/asschange/updateConfirmChangeSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChangeSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeSpecial assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

			if (assChangeSpecial.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assChangeSpecialService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050805 资产原值变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/assChangeSpecialUpdatePage", method = RequestMethod.GET)
	public String assChangeSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeSpecial assChangeSpecial = new AssChangeSpecial();

		assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChangeSpecial.getGroup_id());
		mode.addAttribute("hos_id", assChangeSpecial.getHos_id());
		mode.addAttribute("copy_code", assChangeSpecial.getCopy_code());
		mode.addAttribute("change_no", assChangeSpecial.getChange_no());
		mode.addAttribute("ven_id", assChangeSpecial.getVen_id());
		mode.addAttribute("ven_no", assChangeSpecial.getVen_no());
		mode.addAttribute("ven_code", assChangeSpecial.getVen_code());
		mode.addAttribute("ven_name", assChangeSpecial.getVen_name());
		mode.addAttribute("create_emp", assChangeSpecial.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assChangeSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChangeSpecial.getAudit_emp());
		mode.addAttribute("change_date", assChangeSpecial.getChange_date());
		mode.addAttribute("state", assChangeSpecial.getState());
		mode.addAttribute("note", assChangeSpecial.getNote());

		mode.addAttribute("invoice_no", assChangeSpecial.getInvoice_no());
		mode.addAttribute("invoice_date", DateUtil.dateToString(assChangeSpecial.getInvoice_date(),"yyyy-MM-dd"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05021",MyConfig.getSysPara("05021"));
		
		return "hrp/ass/assspecial/asschange/update";
	}

	/**
	 * @Description 删除数据 050805 资产原值变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/deleteAssChangeSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeSpecial assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSpecialJson = assChangeSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeSpecialJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/queryAssChangeSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeSpecial = assChangeSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeSpecial);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/deleteAssChangeDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeDetailSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeSpecial assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeDetailSpecialJson = assChangeDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assChangeDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/queryAssChangeDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assChangeDetailSpecial = assChangeDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assChangeDetailSpecial);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/deleteAssChangeSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChangeSourceSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssChangeSpecial assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

			if (assChangeSpecial != null) {
				if (assChangeSpecial.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChangeSourceSpecialJson = assChangeSpecialService.deleteAssChangeSourceSpecial(listVo);

		return JSONObject.parseObject(assChangeSourceSpecialJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/queryAssChangeSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChangeSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assChangeSourceSpecial = assChangeSpecialService.queryAssChangeSourceSpecial(getPage(mapVo));

		return JSONObject.parseObject(assChangeSourceSpecial);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/saveAssChangeSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChangeSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChangeSpecial assChangeSpecial = assChangeSpecialService.queryByCode(mapVo);

		if (assChangeSpecial != null) {
			if (assChangeSpecial.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assChangeSourceSpecialJson = assChangeSpecialService.saveAssChangeSourceSpecial(mapVo);

		return JSONObject.parseObject(assChangeSourceSpecialJson);

	}

	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<String> list = assChangeSpecialService.queryState(mapVo);

		if (list.size() > 0) {
			for (String tran_no : list) {
				str += tran_no + " ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为" + str + "的数据是未确认,无法打印!\",\"state\":\"true\"}");

		} else {
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}

	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/asschange/updateAssChangeMainSpecialBillNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssChangeMainSpecialBillNo(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = assChangeSpecialService.updateAssChangeMainBillNo(mapVo);

		return JSONObject.parseObject(assInMainSpecial);
	}

}
