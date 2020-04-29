/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.depre.change;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.depre.change.AssDepreHouse;
import com.chd.hrp.ass.entity.depre.change.AssDepreHouse;
import com.chd.hrp.ass.service.depre.change.AssDepreDetailHouseService;
import com.chd.hrp.ass.service.depre.change.AssDepreHouseService;
import com.chd.hrp.ass.service.depre.change.AssDepreHouseService;
/**
 * 
 * @Description:
 * 050806 资产累计折旧变动(房屋及建筑物)
 * @Table:
 * ASS_DEPRE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssDepreHouseController extends BaseController{

	private static Logger logger = Logger.getLogger(AssDepreHouseController.class);

	// 引入Service服务
	@Resource(name = "assDepreHouseService")
	private final AssDepreHouseService assDepreHouseService = null;

	@Resource(name = "assDepreDetailHouseService")
	private final AssDepreDetailHouseService assDepreDetailHouseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/assDepreHouseMainPage", method = RequestMethod.GET)
	public String assDepreHouseMainPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/assdepre/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/assDepreHouseAddPage", method = RequestMethod.GET)
	public String assDepreHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assdepre/add";

	}

	/**
	 * @Description 添加数据 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/saveAssDepreHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDepreHouse(@RequestParam Map<String, Object> mapVo, Model mode)
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

		AssDepreHouse assDepreHouse = assDepreHouseService.queryByCode(mapVo);

		if (assDepreHouse != null) {
			if (assDepreHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreHouseJson = assDepreHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDepreHouseJson);

	}

	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/updateConfirmDepreHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDepreHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssDepreHouse assDepreHouse = assDepreHouseService.queryByCode(mapVo);

			if (assDepreHouse.getState() == 2) {
				continue;
			} else {
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认! \"}");
		}
		try {
			assInMainJson = assDepreHouseService.updateConfirm(listVo);
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
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/assDepreHouseUpdatePage", method = RequestMethod.GET)
	public String assDepreHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreHouse assDepreHouse = new AssDepreHouse();

		assDepreHouse = assDepreHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDepreHouse.getGroup_id());
		mode.addAttribute("hos_id", assDepreHouse.getHos_id());
		mode.addAttribute("copy_code", assDepreHouse.getCopy_code());
		mode.addAttribute("change_no", assDepreHouse.getChange_no());
		mode.addAttribute("create_emp", assDepreHouse.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assDepreHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDepreHouse.getAudit_emp());
		mode.addAttribute("audit_date", assDepreHouse.getAudit_date());
		mode.addAttribute("state", assDepreHouse.getState());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assdepre/update";
	}

	/**
	 * @Description 删除数据 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/deleteAssDepreHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssDepreHouse assDepreHouse = assDepreHouseService.queryByCode(mapVo);

			if (assDepreHouse != null) {
				if (assDepreHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}
			listVo.add(mapVo);

		}

		String assDepreHouseJson = assDepreHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreHouseJson);

	}

	/**
	 * @Description 查询数据 050806 资产累计折旧变动(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/queryAssDepreHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreHouse = assDepreHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreHouse);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/deleteAssDepreDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreDetailHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssDepreHouse assDepreHouse = assDepreHouseService.queryByCode(mapVo);

			if (assDepreHouse != null) {
				if (assDepreHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreDetailHouseJson = assDepreDetailHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assDepreDetailHouseJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动明细(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/queryAssDepreDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assDepreDetailHouse = assDepreDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assDepreDetailHouse);

	}

	/**
	 * @Description 删除数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/deleteAssDepreSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDepreSourceHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			AssDepreHouse assDepreHouse = assDepreHouseService.queryByCode(mapVo);

			if (assDepreHouse != null) {
				if (assDepreHouse.getState() > 0) {
					return JSONObject.parseObject("{\"warn\":\"已确认的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDepreSourceHouseJson = assDepreHouseService.deleteAssDepreSourceHouse(listVo);

		return JSONObject.parseObject(assDepreSourceHouseJson);

	}

	/**
	 * @Description 查询数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/queryAssDepreSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDepreSourceHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assDepreSourceHouse = assDepreHouseService.queryAssDepreSourceHouse(getPage(mapVo));

		return JSONObject.parseObject(assDepreSourceHouse);
	}

	/**
	 * @Description 添加数据 050805 资产原值变动资金来源(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdepre/saveAssDepreSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDepreSourceHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDepreHouse assDepreHouse = assDepreHouseService.queryByCode(mapVo);

		if (assDepreHouse != null) {
			if (assDepreHouse.getState() > 0) {
				return JSONObject.parseObject("{\"warn\":\"已确认的数据不能保存! \"}");
			}
		}

		String assDepreSourceHouseJson = assDepreHouseService.saveAssDepreSourceHouse(mapVo);

		return JSONObject.parseObject(assDepreSourceHouseJson);

	}

}

