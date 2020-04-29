
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.FileUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.prm.entity.PrmLed;
import com.chd.hrp.prm.service.PrmLedService;

/**
 * 
 * @Description: 0503 指示灯
 * @Table: PRM_LED
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class PrmLedController extends BaseController {

	private static Logger logger = Logger.getLogger(PrmLedController.class);

	// 引入Service服务
	@Resource(name = "prmLedService")
	private final PrmLedService prmLedService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/prmLedMainPage", method = RequestMethod.GET)
	public String prmLedMainPage(Model mode) throws Exception {

		return "hrp/prm/prmled/prmLedMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/prmLedAddPage", method = RequestMethod.GET)
	public String prmLedAddPage(Model mode) throws Exception {

		return "hrp/prm/prmled/prmLedAdd";

	}

	/**
	 * @Description 添加数据 0503 指示灯
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/addPrmLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPrmLed(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file =multipartRequest.getFile("led_path");
		
		Plupload plupload = new Plupload();
		plupload.setRequest(request);
		plupload.setMultipartFile(file);
		plupload.setName(file.getOriginalFilename());
		List<File> fileList = UploadUtil.upload(plupload, "prmLedImage",request, response);
		
		mapVo.put("led_path", "prmLedImage/"+fileList.get(0).getName());
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("sec_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("sec_name").toString()));

		String prmLedJson = prmLedService.addPrmLed(mapVo);

		return JSONObject.parseObject(prmLedJson);

	}

	/**
	 * @Description 更新跳转页面 0503 指示灯
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/prmLedUpdatePage", method = RequestMethod.GET)
	public String prmLedUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		PrmLed prmLed = new PrmLed();

		prmLed = prmLedService.queryPrmLedByCode(mapVo);

		mode.addAttribute("group_id", prmLed.getGroup_id());

		mode.addAttribute("hos_id", prmLed.getHos_id());

		mode.addAttribute("copy_code", prmLed.getCopy_code());

		mode.addAttribute("sec_code", prmLed.getSec_code());

		mode.addAttribute("sec_name", prmLed.getSec_name());

		mode.addAttribute("spell_code", prmLed.getSpell_code());

		mode.addAttribute("wbx_code", prmLed.getWbx_code());

		mode.addAttribute("kpi_beg_score", prmLed.getKpi_beg_score());

		mode.addAttribute("kpi_end_score", prmLed.getKpi_end_score());

		mode.addAttribute("led_path", prmLed.getLed_path());

		return "hrp/prm/prmled/prmLedUpdate";
	}

	/**
	 * @Description 更新数据 0503 指示灯
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/updatePrmLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmLed(HttpServletRequest request, HttpServletResponse response,@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+mapVo.get("old_led_path").toString());
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file =multipartRequest.getFile("led_path");
		
		Plupload plupload = new Plupload();
		plupload.setRequest(request);
		plupload.setMultipartFile(file);
		plupload.setName(file.getOriginalFilename());
		List<File> fileList = UploadUtil.upload(plupload, "prmLedImage",request, response);
		
		mapVo.put("led_path", "prmLedImage/"+fileList.get(0).getName());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("sec_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("sec_name").toString()));

		String prmLedJson = prmLedService.updatePrmLed(mapVo);

		return JSONObject.parseObject(prmLedJson);
	}

	/**
	 * @Description 删除数据 0503 指示灯
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/deletePrmLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmLed(HttpServletRequest request,@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("sec_code", ids[3]);
			FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+ids[4]);
			listVo.add(mapVo);

		}

		String prmLedJson = prmLedService.deleteBatchPrmLed(listVo);

		return JSONObject.parseObject(prmLedJson);

	}

	/**
	 * @Description 查询数据 0503 指示灯
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/queryPrmLed", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPrmLed(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String prmLed = prmLedService.queryPrmLed(getPage(mapVo));

		return JSONObject.parseObject(prmLed);

	}

	/**
	 * @Description 导入跳转页面 0503 指示灯
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/prm/prmled/prmLedImportPage", method = RequestMethod.GET)
	public String prmLedImportPage(Model mode) throws Exception {

		return "hrp/prm/prmled/prmLedImport";

	}

	/**
	 * @Description 下载导入模版 0503 指示灯
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/prm/prmled/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "prm\\基础设置", "指示灯模版.xls");

		return null;
	}

	/**
	 * @Description 导入数据 0503 指示灯
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/prm/prmled/readPrmLedFiles", method = RequestMethod.POST)
	public String readPrmLedFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<PrmLed> list_err = new ArrayList<PrmLed>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

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

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				PrmLed prmLed = new PrmLed();

				String temp[] = list.get(i);// 行

				if (StringTool.isNotBlank(temp[0])) {

					prmLed.setSec_code(temp[0]);

					mapVo.put("sec_code", temp[0]);

				} else {

					err_sb.append("等级代码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					prmLed.setSec_name(temp[1]);

					mapVo.put("sec_name", temp[1]);

				} else {

					err_sb.append("等级名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					prmLed.setKpi_beg_score(Double.valueOf(temp[2]));

					mapVo.put("kpi_beg_score", temp[2]);

				} else {

					err_sb.append("KPI起始分为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					prmLed.setKpi_end_score(Double.valueOf(temp[3]));

					mapVo.put("kpi_end_score", temp[3]);

				} else {

					err_sb.append("KPI结束分为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					prmLed.setLed_path(temp[4]);

					mapVo.put("led_path", temp[4]);

				} else {

					err_sb.append("指示灯路径为空  ");

				}

				PrmLed data_exc_extis = prmLedService.queryPrmLedByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					prmLed.setError_type(err_sb.toString());

					list_err.add(prmLed);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("sec_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("sec_name").toString()));

					String dataJson = prmLedService.addPrmLed(mapVo);

				}

			}

		} catch (DataAccessException e) {

			PrmLed data_exc = new PrmLed();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

}
