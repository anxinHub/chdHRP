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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.depre.change.AssDepreGeneral;
import com.chd.hrp.ass.service.depre.change.AssDepreDetailGeneralService;
import com.chd.hrp.ass.service.depre.change.AssDepreGeneralService;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(一般设备)
 * @Table:
 * ASS_DEPRE_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssDepreGeneralController extends BaseController{

	private static Logger logger = Logger.getLogger(AssDepreGeneralController.class);

	// 引入Service服务
	@Resource(name = "assDepreGeneralService")
	private final AssDepreGeneralService assDepreGeneralService = null;

	@Resource(name = "assDepreDetailGeneralService")
	private final AssDepreDetailGeneralService assDepreDetailGeneralService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/assDepreGeneralMainPage", method = RequestMethod.GET)
	public String assDepreGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assdepre/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/assDepreGeneralAddPage", method = RequestMethod.GET)
	public String assDepreGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assdepre/add";

	}

	/**
	 * @Description 添加数据 050806 资产累计折旧变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/saveAssDepreGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDepreGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssDepreGeneral assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);
		
		if(assDepreGeneral != null){
			if(assDepreGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreGeneralJson = assDepreGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDepreGeneralJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/updateConfirmDepreGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDepreGeneral(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssDepreGeneral assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);

			if (assDepreGeneral.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assDepreGeneralService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assInMainJson);
	}
	

	/**
	 * @Description 更新跳转页面 050806 资产累计折旧变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/assDepreGeneralUpdatePage", method = RequestMethod.GET)
	public String assDepreGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreGeneral assDepreGeneral = new AssDepreGeneral();

		assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDepreGeneral.getGroup_id());
		mode.addAttribute("hos_id", assDepreGeneral.getHos_id());
		mode.addAttribute("copy_code", assDepreGeneral.getCopy_code());
		mode.addAttribute("change_no", assDepreGeneral.getChange_no());
		mode.addAttribute("create_emp", assDepreGeneral.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assDepreGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDepreGeneral.getAudit_emp());
		mode.addAttribute("audit_date", assDepreGeneral.getAudit_date());
		mode.addAttribute("state", assDepreGeneral.getState());

		return "hrp/ass/assgeneral/assdepre/update";
	}

	/**
	 * @Description 删除数据 050806 资产累计折旧变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/deleteAssDepreGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssDepreGeneral assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);
			
			if(assDepreGeneral != null){
				if(assDepreGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assDepreGeneralJson = assDepreGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreGeneralJson);

	}

	/**
	 * @Description 查询数据 050806 资产累计折旧变动(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/queryAssDepreGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreGeneral = assDepreGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreGeneral);

	}
	
	/**
	 * @Description 删除数据 050805 资产原值变动明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/deleteAssDepreDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreDetailGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDepreGeneral assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);
			
			if(assDepreGeneral != null){
				if(assDepreGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}


			listVo.add(mapVo);

		}

		String assDepreDetailGeneralJson = assDepreDetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreDetailGeneralJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/queryAssDepreDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreDetailGeneral = assDepreDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreDetailGeneral);

	}
	
	
	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/deleteAssDepreSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreSourceGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDepreGeneral assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);
			
			if(assDepreGeneral != null){
				if(assDepreGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreSourceGeneralJson = assDepreGeneralService.deleteAssDepreSourceGeneral(listVo);

		return JSONObject.parseObject(assDepreSourceGeneralJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/queryAssDepreSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreSourceGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assDepreSourceGeneral = assDepreGeneralService.queryAssDepreSourceGeneral(getPage(mapVo));

		return JSONObject.parseObject(assDepreSourceGeneral);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdepre/saveAssDepreSourceGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDepreSourceGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssDepreGeneral assDepreGeneral = assDepreGeneralService.queryByCode(mapVo);
		
		if(assDepreGeneral != null){
			if(assDepreGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreSourceGeneralJson = assDepreGeneralService.saveAssDepreSourceGeneral(mapVo);

		return JSONObject.parseObject(assDepreSourceGeneralJson);

	}

}

