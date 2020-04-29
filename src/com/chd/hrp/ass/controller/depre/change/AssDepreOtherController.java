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
import com.chd.hrp.ass.entity.depre.change.AssDepreOther;
import com.chd.hrp.ass.service.depre.change.AssDepreDetailOtherService;
import com.chd.hrp.ass.service.depre.change.AssDepreOtherService;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(其他固定资产)
 * @Table:
 * ASS_DEPRE_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssDepreOtherController extends BaseController{

	private static Logger logger = Logger.getLogger(AssDepreOtherController.class);

	// 引入Service服务
	@Resource(name = "assDepreOtherService")
	private final AssDepreOtherService assDepreOtherService = null;

	@Resource(name = "assDepreDetailOtherService")
	private final AssDepreDetailOtherService assDepreDetailOtherService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/assDepreOtherMainPage", method = RequestMethod.GET)
	public String assDepreOtherMainPage(Model mode) throws Exception {

		return "hrp/ass/assother/assdepre/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/assDepreOtherAddPage", method = RequestMethod.GET)
	public String assDepreOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdepre/add";

	}

	/**
	 * @Description 添加数据 050806 资产累计折旧变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/saveAssDepreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDepreOther(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		AssDepreOther assDepreOther = assDepreOtherService.queryByCode(mapVo);
		
		if(assDepreOther != null){
			if(assDepreOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreOtherJson = assDepreOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDepreOtherJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assdepre/updateConfirmDepreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDepreOther(@RequestParam(value = "ParamVo") String paramVo,
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
			
			AssDepreOther assDepreOther = assDepreOtherService.queryByCode(mapVo);

			if (assDepreOther.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assDepreOtherService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assInMainJson);
	}
	

	/**
	 * @Description 更新跳转页面 050806 资产累计折旧变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/assDepreOtherUpdatePage", method = RequestMethod.GET)
	public String assDepreOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreOther assDepreOther = new AssDepreOther();

		assDepreOther = assDepreOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDepreOther.getGroup_id());
		mode.addAttribute("hos_id", assDepreOther.getHos_id());
		mode.addAttribute("copy_code", assDepreOther.getCopy_code());
		mode.addAttribute("change_no", assDepreOther.getChange_no());
		mode.addAttribute("create_emp", assDepreOther.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assDepreOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDepreOther.getAudit_emp());
		mode.addAttribute("audit_date", assDepreOther.getAudit_date());
		mode.addAttribute("state", assDepreOther.getState());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdepre/update";
	}

	/**
	 * @Description 删除数据 050806 资产累计折旧变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/deleteAssDepreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssDepreOther assDepreOther = assDepreOtherService.queryByCode(mapVo);
			
			if(assDepreOther != null){
				if(assDepreOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assDepreOtherJson = assDepreOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreOtherJson);

	}

	/**
	 * @Description 查询数据 050806 资产累计折旧变动(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/queryAssDepreOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreOther = assDepreOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreOther);

	}
	
	/**
	 * @Description 删除数据 050805 资产原值变动明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/deleteAssDepreDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreDetailOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDepreOther assDepreOther = assDepreOtherService.queryByCode(mapVo);
			
			if(assDepreOther != null){
				if(assDepreOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}


			listVo.add(mapVo);

		}

		String assDepreDetailOtherJson = assDepreDetailOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreDetailOtherJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/queryAssDepreDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreDetailOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreDetailOther = assDepreDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreDetailOther);

	}
	
	
	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/deleteAssDepreSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreSourceOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDepreOther assDepreOther = assDepreOtherService.queryByCode(mapVo);
			
			if(assDepreOther != null){
				if(assDepreOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreSourceOtherJson = assDepreOtherService.deleteAssDepreSourceOther(listVo);

		return JSONObject.parseObject(assDepreSourceOtherJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/queryAssDepreSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreSourceOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assDepreSourceOther = assDepreOtherService.queryAssDepreSourceOther(getPage(mapVo));

		return JSONObject.parseObject(assDepreSourceOther);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdepre/saveAssDepreSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDepreSourceOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		AssDepreOther assDepreOther = assDepreOtherService.queryByCode(mapVo);
		
		if(assDepreOther != null){
			if(assDepreOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreSourceOtherJson = assDepreOtherService.saveAssDepreSourceOther(mapVo);

		return JSONObject.parseObject(assDepreSourceOtherJson);

	}

}

