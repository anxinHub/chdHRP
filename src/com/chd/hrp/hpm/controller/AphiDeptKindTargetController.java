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
import com.chd.hrp.hpm.entity.AphiDeptKindTarget;
import com.chd.hrp.hpm.service.AphiDeptKindTargetService;

/**
 * alfred
 */

@Controller
public class AphiDeptKindTargetController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptKindTargetController.class);

	@Resource(name = "aphiDeptKindTargetService")
	private final AphiDeptKindTargetService aphiDeptKindTargetService = null;

	// @Resource(name = "aphiTargetService")
	// private final AphiTargetService aphiTargetService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetMainPage", method = RequestMethod.GET)
	public String deptKindTargetMainPage(Model mode) throws Exception {
		
		return "hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetImportPage", method = RequestMethod.GET)
	public String deptKindTargetImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetImport";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetAddPage", method = RequestMethod.GET)
	public String deptKindTargetAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/addHpmDeptKindTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptKindTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTargetJson = aphiDeptKindTargetService.addDeptKindTarget(mapVo);

		return JSONObject.parseObject(deptKindTargetJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/queryHpmDeptKindTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptKindTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTarget = aphiDeptKindTargetService.queryDeptKindTarget(getPage(mapVo));

		return JSONObject.parseObject(deptKindTarget);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/deleteHpmDeptKindTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptKindTarget(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map map = new HashMap();

		if (map.get("group_id") == null) {

			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {

			map.put("hos_id", SessionManager.getHosId());
		}

		map.put("copy_code", COPY_CODE);

		String deptKindTargetJson = aphiDeptKindTargetService.deleteDeptKindTarget(map, checkIds);

		return JSONObject.parseObject(deptKindTargetJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetUpdatePage", method = RequestMethod.GET)
	public String deptKindTargetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiDeptKindTarget deptKindTarget = aphiDeptKindTargetService.queryDeptKindTargetByCode(mapVo);

		mode.addAttribute("target_code", deptKindTarget.getTarget_code());

		mode.addAttribute("is_acc", deptKindTarget.getIs_acc());

		return "hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/updateHpmDeptKindTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptKindTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptKindTargetJson = aphiDeptKindTargetService.updateDeptKindTarget(mapVo);

		return JSONObject.parseObject(deptKindTargetJson);
	}

	/** 上传处理方法 */
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/readDeptKindTarget", method = RequestMethod.POST)
	public String readDeptKindTarget(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		String COPY_CODE = SessionManager.getCopyCode();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		List<AphiDeptKindTarget> list2 = new ArrayList<AphiDeptKindTarget>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		// List<Item> errorList = new ArrayList<Item>();

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AphiDeptKindTarget deptKindTarget = new AphiDeptKindTarget();

				String temp[] = list.get(i);// 行

				if (StringUtils.isNotEmpty(temp[0])) {

					deptKindTarget.setTarget_code(temp[0]);

					mapVo.put("target_code", temp[0]);

				} else {

					err_sb.append("指标编码为空  ");

				}

				if (StringUtils.isNotEmpty(temp[1])) {

					deptKindTarget.setIs_acc(Integer.parseInt(temp[1]));

					mapVo.put("is_acc", temp[1]);

				} else {

					err_sb.append("分配动因为空  ");

				}

				AphiDeptKindTarget dkt = aphiDeptKindTargetService.queryDeptKindTargetByCode(mapVo);

				if (dkt != null) {

					err_sb.append("数据编码已经存在！ ");

				}

				// AphiTarget target =
				// aphiTargetService.queryTargetByCode(mapVo);
				//
				// if (target == null) {
				//
				// err_sb.append("指标编码不存在 ");
				//
				// }

				if (err_sb.toString().length() > 0) {

					deptKindTarget.setError_type(err_sb.toString());

					list2.add(deptKindTarget);

				} else {

					aphiDeptKindTargetService.addDeptKindTarget(mapVo);

				}

			}

		} catch (DataAccessException e) {
			AphiDeptKindTarget cd = new AphiDeptKindTarget();

			cd.setError_type("导入系统出错");

			list2.add(cd);

			response.getWriter().print(JsonListBeanUtil.listToJson(list2, list2.size()));

			return null;
		}

		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2, list2.size()));
		return "/hrp/hpm/hpmdeptkindtarget/hpmDeptKindTargetImportMessage";
	}

	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/addBatchDeptKindTargetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchDeptKindTargetDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		List<AphiDeptKindTarget> list_err = new ArrayList<AphiDeptKindTarget>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		String s = null;

		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AphiDeptKindTarget deptKindTarget = new AphiDeptKindTarget();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				// Set<String> key = jsonObj.keySet();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("target_code", jsonObj.get("target_code"));

				mapVo.put("is_acc", jsonObj.get("is_acc"));

				AphiDeptKindTarget dkt = aphiDeptKindTargetService.queryDeptKindTargetByCode(mapVo);

				if (dkt != null) {

					err_sb.append("数据编码已经存在！ ");
				}

				// AphiTarget target =
				// aphiTargetService.queryTargetByCode(mapVo);
				//
				// if (target == null) {
				//
				// err_sb.append("指标编码不存在 ");
				//
				// }

				if (err_sb.toString().length() > 0) {

					deptKindTarget.setTarget_code((String) jsonObj.get("target_code"));

					deptKindTarget.setIs_acc((Integer) jsonObj.get("is_acc"));

					deptKindTarget.setError_type(err_sb.toString());

					list_err.add(deptKindTarget);
				} else {

					s = aphiDeptKindTargetService.addDeptKindTarget(mapVo);

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

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdeptkindtarget/downTemplateDeptKindTarget", method = RequestMethod.GET)
	public String downTemplateDeptKindTarget(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\方案制定\\总额预算制计将模式\\分配动因配置", "科室分类分配动因配置.xls");
		return null;
	}

}
