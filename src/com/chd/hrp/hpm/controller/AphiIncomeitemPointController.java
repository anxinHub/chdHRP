package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.base.SessionManager;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiIncomeItem;
import com.chd.hrp.hpm.entity.AphiIncomeitemPoint;
import com.chd.hrp.hpm.service.AphiIncomeItemService;
import com.chd.hrp.hpm.service.AphiIncomeitemPointService;

/**
 * alfred
 */

@Controller
public class AphiIncomeitemPointController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiIncomeitemPointController.class);

	@Resource(name = "aphiIncomeitemPointService")
	private final AphiIncomeitemPointService aphiIncomeitemPointService = null;

	@Resource(name = "aphiIncomeItemService")
	private final AphiIncomeItemService aphiIncomeItemService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointMainPage", method = RequestMethod.GET)
	public String incomeitemPointMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointMain";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointAddPage", method = RequestMethod.GET)
	public String incomeitemPointAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/addHpmIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addIncomeitemPoint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemPointJson = aphiIncomeitemPointService.addIncomeitemPoint(mapVo);

		return JSONObject.parseObject(incomeitemPointJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/queryHpmIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIncomeitemPoint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemPoint = aphiIncomeitemPointService.queryIncomeitemPoint(getPage(mapVo));

		return JSONObject.parseObject(incomeitemPoint);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/deleteHpmIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteIncomeitemPoint(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		Map map = new HashMap();

		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}
		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}
		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemPointJson = aphiIncomeitemPointService.deleteIncomeitemPoint(map, checkIds);

		return JSONObject.parseObject(incomeitemPointJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointUpdatePage", method = RequestMethod.GET)
	public String incomeitemPointUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AphiIncomeitemPoint incomeitemPoint = aphiIncomeitemPointService.queryIncomeitemPointByCode(mapVo);

		mode.addAttribute("income_item_code", incomeitemPoint.getIncome_item_code());

		mode.addAttribute("is_acc", incomeitemPoint.getIs_acc());

		mode.addAttribute("imcome_point", incomeitemPoint.getImcome_point());

		return "hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/updateHpmIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIncomeitemPoint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemPointJson = aphiIncomeitemPointService.updateIncomeitemPoint(mapVo);

		return JSONObject.parseObject(incomeitemPointJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/createHpmIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createIncomeitemPoint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBalancePercConfJson = aphiIncomeitemPointService.createIncomeitemPoint(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	// 快速添加跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointFastPage", method = RequestMethod.GET)
	public String incomeitemPointFastPage(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		mode.addAttribute("checkIds", checkIds);

		return "hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointFast";

	}

	// 点值设置
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointPage", method = RequestMethod.GET)
	public String incomeitemPointPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointValue";

	}

	// 快速添加保存
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/fastHpmIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastIncomeitemPoint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String deptBalancePercConfJson = aphiIncomeitemPointService.fastIncomeitemPoint(mapVo);

		return JSONObject.parseObject(deptBalancePercConfJson);

	}

	// 保存点值
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/saveIncomeitemPointValue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveIncomeitemPointValue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String incomeitemPointValue = aphiIncomeitemPointService.addIncomeitemPointValue(mapVo);

		return JSONObject.parseObject(incomeitemPointValue);

	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/readIncomeItemPointFiles", method = RequestMethod.POST)
	public String readIncomeItemPointFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		List<AphiIncomeitemPoint> list2 = new ArrayList<AphiIncomeitemPoint>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		// List<Item> errorList = new ArrayList<Item>();

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiIncomeitemPoint incomeitemPoint = new AphiIncomeitemPoint();

				String temp[] = list.get(i);// 行

				if (StringUtils.isNotEmpty(temp[0])) {

					incomeitemPoint.setIncome_item_code(temp[0]);

					mapVo.put("income_item_code", temp[0]);

				} else {

					err_sb.append("收入项目编码为空  ");

				}

				if (StringUtils.isNotEmpty(temp[1])) {

					incomeitemPoint.setIs_acc(Integer.parseInt(temp[1]));

					mapVo.put("is_acc", temp[1]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				if (StringUtils.isNotEmpty(temp[2])) {

					incomeitemPoint.setImcome_point((long) Double.parseDouble(temp[2]));

					mapVo.put("imcome_point", temp[2]);

				} else {

					err_sb.append("计提点数为空  ");

				}

				AphiIncomeitemPoint iip = aphiIncomeitemPointService.queryIncomeitemPointByCode(mapVo);

				if (iip != null) {

					err_sb.append("数据编码已经存在！ ");

				}

				AphiIncomeItem ii = aphiIncomeItemService.queryIncomeItemByCode(mapVo);

				if (ii == null) {

					err_sb.append("收入项目不存在！ ");

				}

				if (err_sb.toString().length() > 0) {

					incomeitemPoint.setError_type(err_sb.toString());

					list2.add(incomeitemPoint);

				} else {

					aphiIncomeitemPointService.addIncomeitemPoint(mapVo);

				}

			}

		} catch (DataAccessException e) {
			AphiIncomeitemPoint cd = new AphiIncomeitemPoint();

			cd.setError_type("导入系统出错");

			list2.add(cd);

			response.getWriter().print(JsonListBeanUtil.listToJson(list2, list2.size()));

			return null;
		}

		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2, list2.size()));

		return "/hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointImportMessage";
	}

	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/addBatchIncomeitemPoint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchIncomeitemPoint(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		List<AphiIncomeitemPoint> list_err = new ArrayList<AphiIncomeitemPoint>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		String s = null;

		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AphiIncomeitemPoint incomeitemPoint = new AphiIncomeitemPoint();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				// Set<String> key = jsonObj.keySet();

				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("income_item_code", jsonObj.get("income_item_code"));

				mapVo.put("imcome_point", jsonObj.get("imcome_point"));

				mapVo.put("is_acc", jsonObj.get("is_acc"));

				AphiIncomeitemPoint iip = aphiIncomeitemPointService.queryIncomeitemPointByCode(mapVo);

				if (iip != null) {

					err_sb.append("数据编码已经存在！ ");

				}

				AphiIncomeItem ii = aphiIncomeItemService.queryIncomeItemByCode(mapVo);

				if (ii == null) {

					err_sb.append("收入项目不存在！ ");

				}

				if (err_sb.toString().length() > 0) {

					incomeitemPoint.setIncome_item_code((String) jsonObj.get("income_item_code"));

					incomeitemPoint.setImcome_point((long) Double.parseDouble(jsonObj.get("imcome_point").toString()));

					incomeitemPoint.setIs_acc((Integer) jsonObj.get("is_acc"));

					incomeitemPoint.setError_type(err_sb.toString());

					list_err.add(incomeitemPoint);
				} else {

					s = aphiIncomeitemPointService.addIncomeitemPoint(mapVo);

				}
			}

		} catch (DataAccessException e) {

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	//导入
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointImportPage", method = RequestMethod.GET)
	public String hpmIncomeitemPointImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmincomeitemPoint/hpmIncomeitemPointImport";
	}
	
	//导入跳转
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/hpmIncomeitemPointImport", method = RequestMethod.POST)
	@ResponseBody
	public String hpmIncomeitemPointImport(@RequestParam Map<String, Object> mapVo){
		try {
			String impJson = aphiIncomeitemPointService.hpmIncomeitemPointImport(mapVo);
			return impJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmincomeitempoint/downTemplateHpmIncomeitemPoint", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\downTemplate", "核算项目点数维护.xlsx");
		return null;
	}

}
