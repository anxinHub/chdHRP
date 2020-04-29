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
import com.chd.hrp.ass.entity.depre.change.AssDepreLand;
import com.chd.hrp.ass.service.depre.change.AssDepreDetailLandService;
import com.chd.hrp.ass.service.depre.change.AssDepreLandService;

/**
 * 
 * @Description: 050806 资产累计折旧变动(土地)
 * @Table: ASS_DEPRE_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class AssDepreLandController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDepreLandController.class);

	// 引入Service服务
	@Resource(name = "assDepreLandService")
	private final AssDepreLandService assDepreLandService = null;

	@Resource(name = "assDepreDetailLandService")
	private final AssDepreDetailLandService assDepreDetailLandService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/assDepreLandMainPage", method = RequestMethod.GET)
	public String assDepreLandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/assdepre/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/assDepreLandAddPage", method = RequestMethod.GET)
	public String assDepreLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assdepre/add";

	}

	/**
	 * @Description 添加数据 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/saveAssDepreLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDepreLand(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssDepreLand assDepreLand = assDepreLandService.queryByCode(mapVo);

		if (assDepreLand != null) {
			if (assDepreLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreLandJson = assDepreLandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDepreLandJson);

	}

	@RequestMapping(value = "/hrp/ass/assland/assdepre/updateConfirmDepreLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDepreLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("audit_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());

			AssDepreLand assDepreLand = assDepreLandService.queryByCode(mapVo);

			if (assDepreLand.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assDepreLandService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}

	/**
	 * @Description 更新跳转页面 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/assDepreLandUpdatePage", method = RequestMethod.GET)
	public String assDepreLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreLand assDepreLand = new AssDepreLand();

		assDepreLand = assDepreLandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDepreLand.getGroup_id());
		mode.addAttribute("hos_id", assDepreLand.getHos_id());
		mode.addAttribute("copy_code", assDepreLand.getCopy_code());
		mode.addAttribute("change_no", assDepreLand.getChange_no());
		mode.addAttribute("create_emp", assDepreLand.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assDepreLand.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDepreLand.getAudit_emp());
		mode.addAttribute("audit_date", assDepreLand.getAudit_date());
		mode.addAttribute("state", assDepreLand.getState());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assdepre/update";
	}

	/**
	 * @Description 删除数据 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/deleteAssDepreLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssDepreLand assDepreLand = assDepreLandService.queryByCode(mapVo);

			if (assDepreLand != null) {
				if (assDepreLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assDepreLandJson = assDepreLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreLandJson);

	}

	/**
	 * @Description 查询数据 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/queryAssDepreLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreLand = assDepreLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreLand);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/deleteAssDepreDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreDetailLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssDepreLand assDepreLand = assDepreLandService.queryByCode(mapVo);

			if (assDepreLand != null) {
				if (assDepreLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreDetailLandJson = assDepreDetailLandService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreDetailLandJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/queryAssDepreDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreDetailLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreDetailLand = assDepreDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreDetailLand);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/deleteAssDepreSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreSourceLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssDepreLand assDepreLand = assDepreLandService.queryByCode(mapVo);

			if (assDepreLand != null) {
				if (assDepreLand.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreSourceLandJson = assDepreLandService.deleteAssDepreSourceLand(listVo);

		return JSONObject.parseObject(assDepreSourceLandJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/queryAssDepreSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreSourceLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assDepreSourceLand = assDepreLandService.queryAssDepreSourceLand(getPage(mapVo));

		return JSONObject.parseObject(assDepreSourceLand);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/assdepre/saveAssDepreSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDepreSourceLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreLand assDepreLand = assDepreLandService.queryByCode(mapVo);

		if (assDepreLand != null) {
			if (assDepreLand.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreSourceLandJson = assDepreLandService.saveAssDepreSourceLand(mapVo);

		return JSONObject.parseObject(assDepreSourceLandJson);

	}

}
