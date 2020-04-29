/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.bid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.ass.entity.bid.AssBidProject;
import com.chd.hrp.ass.service.bid.AssBidProjectDetailService;
import com.chd.hrp.ass.service.bid.AssBidProjectService;

/**
 * 
 * @Description: 050401 招标管理
 * @Table: ASS_BID_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssBidProjectController extends BaseController {

	// 引入Service服务
	@Resource(name = "assBidProjectService")
	private final AssBidProjectService assBidProjectService = null;

	@Resource(name = "assBidProjectDetailService")
	private final AssBidProjectDetailService assBidProjectDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/assBidProjectMainPage", method = RequestMethod.GET)
	public String assBidProjectMainPage(Model mode) throws Exception {

		return "hrp/ass/assbidproject/assBidProjectMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/assBidProjectAddPage", method = RequestMethod.GET)
	public String assBidProjectAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbidproject/assBidProjectAdd";

	}

	/**
	 * @Description 添加数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/addAssBidProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssBidProject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String bid_date = String.valueOf(mapVo.get("bid_date"));

		if (StringUtils.isNotEmpty(bid_date)) {

			mapVo.put("bid_date", DateUtil.stringToDate(bid_date, "yyyy-MM-dd"));

		}

		mapVo.put("is_group", "0");
		String assBidProjectJson = "";
		try {

			assBidProjectJson = assBidProjectService.addOrUpdateAssBidProject(mapVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		JSONObject jsonObj = JSONArray.parseObject(assBidProjectJson);

		String project_id = (String) jsonObj.get("project_id");

		String project_no = (String) jsonObj.get("project_no");

		String assBidProjectDetailJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
				continue;
			}

			int ass_price = Integer.parseInt(detailVo.get("ass_price").toString());

			int ass_num = Integer.parseInt(detailVo.get("ass_num").toString());

			detailVo.put("ass_money", ass_price * ass_num);

			detailVo.put("project_id", project_id);

			detailVo.put("project_no", project_no);

			String id = detailVo.get("ass_id").toString();

			if (detailVo.get("fac_id") == null || detailVo.get("fac_id") == "") {

				detailVo.put("fac_id", "");

				detailVo.put("fac_no", "");

			} else {

				String fid = detailVo.get("fac_id").toString();

				detailVo.put("fac_id", fid.split("@")[0]);

				detailVo.put("fac_no", fid.split("@")[1]);
			}

			detailVo.put("ass_id", id.split("@")[0]);

			detailVo.put("ass_no", id.split("@")[1]);

			try {

				assBidProjectDetailJson = assBidProjectDetailService.addOrUpdateAssBidProjectDetail(detailVo);

			} catch (Exception e) {

				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}

		}

		JSONObject json = JSONArray.parseObject(assBidProjectDetailJson);

		json.put("project_id", project_id);

		json.put("project_no", project_no);

		return JSONObject.parseObject(json.toJSONString());
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/updateToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		return JSONObject.parseObject("");
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/updateNotToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		return JSONObject.parseObject("");
	}

	/**
	 * @Description 更新跳转页面 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/assBidProjectUpdatePage", method = RequestMethod.GET)
	public String assBidProjectUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssBidProject assBidProject = new AssBidProject();

		assBidProject = assBidProjectService.queryAssBidProjectByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assBidProject.getGroup_id());

		mode.addAttribute("hos_id", assBidProject.getHos_id());

		mode.addAttribute("copy_code", assBidProject.getCopy_code());

		mode.addAttribute("project_id", assBidProject.getProject_id());

		mode.addAttribute("project_no", assBidProject.getProject_no());

		if (assBidProject.getBid_date() == null) {
			mode.addAttribute("bid_date", assBidProject.getBid_date());

		} else {
			mode.addAttribute("bid_date", sdf.format(assBidProject.getBid_date()));
		}

		mode.addAttribute("is_group", assBidProject.getIs_group());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assbidproject/assBidProjectUpdate";
	}

	/**
	 * @Description 删除数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/deleteAssBidProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBidProject(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assBidProjectJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("project_id", ids[3]);

			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态不是新建状态的不能进行删除! \"}");
		}
		try {
			assBidProjectDetailService.deleteBatchAssBidProjectDetail(listVo);
			assBidProjectJson = assBidProjectService.deleteBatchAssBidProject(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBidProjectJson);

	}

	/**
	 * @Description 查询数据 050401 招标管理
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/queryAssBidProject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidProject(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		mapVo.put("is_group", "0");

		String assBidProject = assBidProjectService.queryAssBidProject(getPage(mapVo));

		return JSONObject.parseObject(assBidProject);

	}

	/**
	 * @Description 删除数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/deleteAssBidProjectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBidProjectDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assBidProjectDetailJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("project_id", ids[3]);

			mapVo.put("project_detail_id", ids[4]);

			listVo.add(mapVo);

		}

		try {

			assBidProjectDetailJson = assBidProjectDetailService.deleteBatchAssBidProjectDetail(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assBidProjectDetailJson);

	}

	/**
	 * @Description 查询数据 050401 招标资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assbidproject/queryAssBidProjectDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBidProjectDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String assBidProjectDetail = assBidProjectDetailService.queryAssBidProjectDetail(getPage(mapVo));

		return JSONObject.parseObject(assBidProjectDetail);

	}

}
