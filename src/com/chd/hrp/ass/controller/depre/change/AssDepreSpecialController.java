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
import com.chd.hrp.ass.entity.depre.change.AssDepreSpecial;
import com.chd.hrp.ass.service.depre.change.AssDepreDetailSpecialService;
import com.chd.hrp.ass.service.depre.change.AssDepreSpecialService;

/**
 * 
 * @Description: 050806 资产累计折旧变动(专用设备)
 * @Table: ASS_DEPRE_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssDepreSpecialController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreSpecialController.class);

	// 引入Service服务
	@Resource(name = "assDepreSpecialService")
	private final AssDepreSpecialService assDepreSpecialService = null;

	@Resource(name = "assDepreDetailSpecialService")
	private final AssDepreDetailSpecialService assDepreDetailSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/assDepreSpecialMainPage", method = RequestMethod.GET)
	public String assDepreSpecialMainPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/assdepre/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/assDepreSpecialAddPage", method = RequestMethod.GET)
	public String assDepreSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/assdepre/add";

	}

	/**
	 * @Description 添加数据 050806 资产累计折旧变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/saveAssDepreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDepreSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssDepreSpecial assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);
		
		if(assDepreSpecial != null){
			if(assDepreSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreSpecialJson = assDepreSpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDepreSpecialJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/updateConfirmDepreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDepreSpecial(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssDepreSpecial assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);

			if (assDepreSpecial.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assDepreSpecialService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assInMainJson);
	}
	

	/**
	 * @Description 更新跳转页面 050806 资产累计折旧变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/assDepreSpecialUpdatePage", method = RequestMethod.GET)
	public String assDepreSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreSpecial assDepreSpecial = new AssDepreSpecial();

		assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDepreSpecial.getGroup_id());
		mode.addAttribute("hos_id", assDepreSpecial.getHos_id());
		mode.addAttribute("copy_code", assDepreSpecial.getCopy_code());
		mode.addAttribute("change_no", assDepreSpecial.getChange_no());
		mode.addAttribute("create_emp", assDepreSpecial.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assDepreSpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDepreSpecial.getAudit_emp());
		mode.addAttribute("audit_date", assDepreSpecial.getAudit_date());
		mode.addAttribute("state", assDepreSpecial.getState());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/assdepre/update";
	}

	/**
	 * @Description 删除数据 050806 资产累计折旧变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/deleteAssDepreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssDepreSpecial assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);
			
			if(assDepreSpecial != null){
				if(assDepreSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assDepreSpecialJson = assDepreSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreSpecialJson);

	}

	/**
	 * @Description 查询数据 050806 资产累计折旧变动(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/queryAssDepreSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreSpecial = assDepreSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreSpecial);

	}
	
	/**
	 * @Description 删除数据 050805 资产原值变动明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/deleteAssDepreDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreDetailSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDepreSpecial assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);
			
			if(assDepreSpecial != null){
				if(assDepreSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}


			listVo.add(mapVo);

		}

		String assDepreDetailSpecialJson = assDepreDetailSpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreDetailSpecialJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/queryAssDepreDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreDetailSpecial = assDepreDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreDetailSpecial);

	}
	
	
	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/deleteAssDepreSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreSourceSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDepreSpecial assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);
			
			if(assDepreSpecial != null){
				if(assDepreSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreSourceSpecialJson = assDepreSpecialService.deleteAssDepreSourceSpecial(listVo);

		return JSONObject.parseObject(assDepreSourceSpecialJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/queryAssDepreSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assDepreSourceSpecial = assDepreSpecialService.queryAssDepreSourceSpecial(getPage(mapVo));

		return JSONObject.parseObject(assDepreSourceSpecial);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdepre/saveAssDepreSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDepreSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssDepreSpecial assDepreSpecial = assDepreSpecialService.queryByCode(mapVo);
		
		if(assDepreSpecial != null){
			if(assDepreSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreSourceSpecialJson = assDepreSpecialService.saveAssDepreSourceSpecial(mapVo);

		return JSONObject.parseObject(assDepreSourceSpecialJson);

	}

}
