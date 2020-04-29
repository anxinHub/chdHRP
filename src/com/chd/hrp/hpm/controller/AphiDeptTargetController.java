package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiDeptTarget;
import com.chd.hrp.hpm.service.AphiDeptTargetService;

/**
 * alfred
 */

@Controller
public class AphiDeptTargetController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiDeptTargetController.class);

	@Resource(name = "aphiDeptTargetService")
	private final AphiDeptTargetService aphiDeptTargetService = null;

	// @Resource(name = "aphiTargetService")
	// private final AphiTargetService aphiTargetService = null;

//	@Resource(name = "aphiDeptKindService")
//	private final AphiDeptKindService aphiDeptKindService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/hpmDeptTargetMainPage", method = RequestMethod.GET)
	public String deptTargetMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepttarget/hpmDeptTargetMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/hpmDeptTargetAddPage", method = RequestMethod.GET)
	public String deptTargetAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepttarget/hpmDeptTargetAdd";

	}

	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/hpmDeptTargetImportPage", method = RequestMethod.GET)
	public String deptTargetImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmdepttarget/hpmDeptTargetImport";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/addHpmDeptTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDeptTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptTargetJson = aphiDeptTargetService.addDeptTarget(mapVo);

		return JSONObject.parseObject(deptTargetJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/queryHpmDeptTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptTarget = aphiDeptTargetService.queryDeptTarget(getPage(mapVo));

		return JSONObject.parseObject(deptTarget);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/deleteHpmDeptTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDeptTarget(@RequestParam(value = "checkIds", required = true) String checkIds, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		Map map = new HashMap();

		if (map.get("group_id") == null) {

			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {

			map.put("hos_id", SessionManager.getHosId());
		}

		map.put("copy_code", COPY_CODE);

		String deptTargetJson = aphiDeptTargetService.deleteDeptTarget(map, checkIds);

		return JSONObject.parseObject(deptTargetJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/hpmDeptTargetUpdatePage", method = RequestMethod.GET)
	public String deptTargetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		AphiDeptTarget deptTarget = aphiDeptTargetService.queryDeptTargetByCode(mapVo);

		mode.addAttribute("target_code", deptTarget.getTarget_code());

		mode.addAttribute("dept_kind_code", deptTarget.getDept_kind_code());

		mode.addAttribute("is_acc", deptTarget.getIs_acc());

		return "hrp/hpm/hpmdepttarget/hpmDeptTargetUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/updateHpmDeptTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDeptTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptTargetJson = aphiDeptTargetService.updateDeptTarget(mapVo);

		return JSONObject.parseObject(deptTargetJson);
	}

//	/** 上传处理方法 */
//	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/readDeptTarget", method = RequestMethod.POST)
//	public String readDeptTarget(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
//
//		String COPY_CODE = SessionManager.getCopyCode();
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		if (mapVo.get("group_id") == null) {
//
//			mapVo.put("group_id", SessionManager.getGroupId());
//		}
//
//		if (mapVo.get("hos_id") == null) {
//
//			mapVo.put("hos_id", SessionManager.getHosId());
//		}
//
//		mapVo.put("copy_code", COPY_CODE);
//
//		List<AphiDeptTarget> list2 = new ArrayList<AphiDeptTarget>();
//
//		List<String[]> list = UploadUtil.readFile(plupload, request, response);
//
//		// List<Item> errorList = new ArrayList<Item>();
//
//		try {
//
//			for (int i = 1; i < list.size(); i++) {
//
//				StringBuffer err_sb = new StringBuffer();
//
//				AphiDeptTarget deptTarget = new AphiDeptTarget();
//
//				String temp[] = list.get(i);// 行
//
//				if (temp[2].length() == 1) {
//
//					temp[2] = "0" + temp[2];
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[0])) {
//
//					deptTarget.setTarget_code(temp[0]);
//
//					mapVo.put("target_code", temp[0]);
//
//				} else {
//
//					err_sb.append("指标编码为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[1])) {
//
//					deptTarget.setDept_kind_code(temp[1]);
//
//					mapVo.put("dept_kind_code", temp[1]);
//
//				} else {
//
//					err_sb.append("科室类别名称为空  ");
//
//				}
//
//				if (StringUtils.isNotEmpty(temp[2])) {
//
//					deptTarget.setIs_acc(Integer.parseInt(temp[2]));
//
//					mapVo.put("is_acc", temp[2]);
//
//				} else {
//
//					err_sb.append("分配动因为空  ");
//
//				}
//
//				AphiDeptTarget dt = aphiDeptTargetService.queryDeptTargetByCode(mapVo);
//
//				if (dt != null) {
//
//					err_sb.append("数据编码已经存在！ ");
//
//				}
//
//				// AphiTarget target =
//				// aphiTargetService.queryTargetByCode(mapVo);
//				//
//				// if (target == null) {
//				//
//				// err_sb.append("指标编码不存在 ");
//				//
//				// }
//
//				AphiDeptKind dk = aphiDeptKindService.queryDeptKindByCode(mapVo);
//
//				if (dk == null) {
//
//					err_sb.append("科室类别不存在 ");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					deptTarget.setError_type(err_sb.toString());
//
//					list2.add(deptTarget);
//
//				} else {
//
//					aphiDeptTargetService.addDeptTarget(mapVo);
//
//				}
//
//			}
//
//		} catch (DataAccessException e) {
//			AphiDeptTarget cd = new AphiDeptTarget();
//
//			cd.setError_type("导入系统出错");
//
//			list2.add(cd);
//
//			response.getWriter().print(JsonListBeanUtil.listToJson(list2, list2.size()));
//
//			return null;
//		}
//
//		mode.addAttribute("resultsJson", JsonListBeanUtil.listToJson(list2, list2.size()));
//		return "/hrp/hpm/hpmdepttarget/hpmDeptTargetImportMessage";
//	}
//
//	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/addBatchDeptTargetDict", method = RequestMethod.POST)
//	@ResponseBody
//	public Map<String, Object> addBatchDeptTargetDict(@RequestParam(value = "ParamVo") String paramVo, Model mode, HttpServletResponse response)
//			throws Exception {
//
//		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
//
//		List<AphiDeptTarget> list_err = new ArrayList<AphiDeptTarget>();
//
//		JSONArray json = JSONArray.parseArray(paramVo);
//
//		Map<String, Object> mapVo = new HashMap<String, Object>();
//
//		String s = null;
//
//		Iterator it = json.iterator();
//		try {
//			while (it.hasNext()) {
//
//				StringBuffer err_sb = new StringBuffer();
//
//				AphiDeptTarget deptTarget = new AphiDeptTarget();
//
//				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
//
//				// Set<String> key = jsonObj.keySet();
//
//				if (mapVo.get("group_id") == null) {
//
//					mapVo.put("group_id", SessionManager.getGroupId());
//				}
//
//				if (mapVo.get("hos_id") == null) {
//
//					mapVo.put("hos_id", SessionManager.getHosId());
//				}
//
//				mapVo.put("copy_code", SessionManager.getCopyCode());
//
//				mapVo.put("target_code", jsonObj.get("target_code"));
//
//				mapVo.put("dept_kind_code", jsonObj.get("dept_kind_code"));
//
//				mapVo.put("is_acc", jsonObj.get("is_acc"));
//
//				AphiDeptTarget dt = aphiDeptTargetService.queryDeptTargetByCode(mapVo);
//
//				if (dt != null) {
//
//					err_sb.append("数据编码已经存在！");
//
//				}
//
//				// AphiTarget target =
//				// aphiTargetService.queryTargetByCode(mapVo);
//				//
//				// if (target == null) {
//				//
//				// err_sb.append("指标编码不存在 ");
//				//
//				// }
//
//				AphiDeptKind dk = aphiDeptKindService.queryDeptKindByCode(mapVo);
//
//				if (dk == null) {
//
//					err_sb.append("科室类别不存在 ");
//
//				}
//
//				if (err_sb.toString().length() > 0) {
//
//					deptTarget.setTarget_code((String) jsonObj.get("target_code"));
//
//					deptTarget.setDept_kind_code((String) jsonObj.get("dept_kind_code"));
//
//					deptTarget.setIs_acc((Integer) jsonObj.get("is_acc"));
//
//					deptTarget.setError_type(err_sb.toString());
//
//					list_err.add(deptTarget);
//
//				} else {
//
//					s = aphiDeptTargetService.addDeptTarget(mapVo);
//				}
//			}
//
//		} catch (DataAccessException e) {
//
//			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
//
//		}
//
//		if (list_err.size() > 0) {
//
//			return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err, list_err.size()));
//
//		} else {
//
//			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
//
//		}
//
//	}

	// 下载导入模板
	@RequestMapping(value = "/hrp/hpm/hpmdepttarget/downTemplateDeptTarget", method = RequestMethod.GET)
	public String downTemplateDeptTarget(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "hpm\\方案制定\\总额预算制计将模式\\分配动因配置", "科室分类分配动因配置.xls");
		return null;
	}
}
