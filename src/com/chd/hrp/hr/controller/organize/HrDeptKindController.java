package com.chd.hrp.hr.controller.organize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hr.entity.orangnize.HrDeptKind;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HrDeptKindService;

/**
 * 
 * @ClassName: DeptKindController
 * @Description: 部门分类
 * @author zn
 * @date 2017年10月13日 下午8:07:00
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/deptKind")
public class HrDeptKindController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrDeptKindController.class);

	// 引入Service服务

	@Resource(name = "hrDeptKindService")
	private final HrDeptKindService hrDeptKindService = null;

	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deptKindMainPage", method = RequestMethod.GET)
	public String deptKindMainPage(Model mode) throws Exception {
		return "hrp/hr/organize/deptkind/deptKindMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addDeptKindPage", method = RequestMethod.GET)
	public String deptKindAddPage(Model mode) throws Exception {

		return "hrp/hr/organize/deptkind/deptKindAdd";

	}

	/**
	 * 新增部门分类
	 */
	@RequestMapping(value = "/addDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deptKindAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String deptKindJson = hrDeptKindService.addDeptKind(mapVo);
			return JSONObject.parseObject(deptKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deptKindUpdatePage", method = RequestMethod.GET)
	public String deptKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		HrDeptKind deptKind = new HrDeptKind();

		deptKind = hrDeptKindService.queryByCode(mapVo);

		mode.addAttribute("group_id", deptKind.getGroup_id());
		mode.addAttribute("hos_id", deptKind.getHos_id());
		mode.addAttribute("kind_code", deptKind.getKind_code());
		mode.addAttribute("kind_name", deptKind.getKind_name());
		mode.addAttribute("is_stop", deptKind.getIs_stop());
		mode.addAttribute("note", deptKind.getNote());

		return "hrp/hr/organize/deptkind/deptKindUpdate";

	}
	/**
	 * 修改部门分类
	 */
	@RequestMapping(value = "/updateDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deptKindUpate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String deptKindJson = hrDeptKindService.deptKindUpate(mapVo);
			return JSONObject.parseObject(deptKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	
	
    /**
     * 删除部门
     * @param paramVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteDeptKind")
	@ResponseBody
	public Map<String, Object> deleteDeptKind(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("kind_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String deptKindJson = hrDeptKindService.deleteDeptKind(listVo);
	   return JSONObject.parseObject(deptKindJson);
			
	}

	/**
	 * 查询所有部门
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAllDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAllDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = hrDeptKindService.query(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	@RequestMapping(value = "queryDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String deptKind = hrDeptKindService.queryDeptKindDict(mapVo);
		return deptKind;

	}
	
	//下载
	@RequestMapping(value = "/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "hr\\downTemplate", "部门分类.xls");

		return null;
	}
	//导入页面
	@RequestMapping(value = "/deptKindImportPage", method = RequestMethod.GET)
	public String deptKindImportPage(Model mode) throws Exception {

		return "hrp/hr/organize/deptkind/deptKindImport";

	}
	
	/**
	 * 部门分类<BR>
	 * 导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/readDeptKindFiles", method = RequestMethod.POST)
	public String readDeptKindFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws Exception {

		List<HrDeptKind> list_err = new ArrayList<HrDeptKind>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				HrDeptKind deptKind = new HrDeptKind();

				String temp[] = list.get(i);// 行

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(temp[0])) {

					mapVo.put("kind_code", temp[0]);

					deptKind.setKind_code(temp[0]);

				} else {

					err_sb.append("部门分类编码为空");
				}

				if (StringTool.isNotBlank(temp[1])) {

					mapVo.put("kind_name", temp[1]);

					deptKind.setKind_name(temp[1]);

				} else {

					err_sb.append("部门分类名称为空");
				}

				if (StringTool.isNotBlank(temp[2]) && null != temp[2]) {

					mapVo.put("is_stop", temp[2]);

					deptKind.setIs_stop(Integer.parseInt(temp[2].toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (temp.length -1 >= 3) {

					if (StringTool.isNotBlank(temp[3])) {

						mapVo.put("note", temp[3]);

						deptKind.setNote(temp[3]);

					}

				} else {

					mapVo.put("note", "");

					deptKind.setNote("");
				}

				HrDeptKind eDeptKind = hrDeptKindService.queryByCode(mapVo);

				if (eDeptKind != null) {

					err_sb.append("部门分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					deptKind.setError_type(err_sb.toString());

					list_err.add(deptKind);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

					hrDeptKindService.addDeptKind(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			HrDeptKind data_exc = new HrDeptKind();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}
	
	@RequestMapping(value = "/addImportDeptKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportDeptKindDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<HrDeptKind> list_err = new ArrayList<HrDeptKind>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				StringBuffer err_sb = new StringBuffer();

				HrDeptKind deptKind = new HrDeptKind();

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(jsonObj.get("kind_code"))) {

					mapVo.put("kind_code", jsonObj.get("kind_code").toString());

					deptKind.setKind_code(jsonObj.get("kind_code").toString());

				} else {

					err_sb.append("部门分类编码为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("kind_name"))) {

					mapVo.put("kind_name", jsonObj.get("kind_name").toString());

					deptKind.setKind_name(jsonObj.get("kind_name").toString());

				} else {

					err_sb.append("部门分类名称为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					mapVo.put("is_stop",Integer.parseInt(jsonObj.get("is_stop").toString()));

					deptKind.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note").toString());

					deptKind.setNote(jsonObj.get("note").toString());

				} else {

					mapVo.put("note", "");

					deptKind.setNote("");
				}

				HrDeptKind eDeptKind = hrDeptKindService.queryDeptKindByCode(mapVo);

				if (eDeptKind != null) {

					err_sb.append("部门分类编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					deptKind.setError_type(err_sb.toString());

					list_err.add(deptKind);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_code").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_code").toString()));

					hrDeptKindService.addDeptKind(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));

		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
	
}
