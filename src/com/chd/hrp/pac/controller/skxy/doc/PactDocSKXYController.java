package com.chd.hrp.pac.controller.skxy.doc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.ftp.FtpUtil;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.service.basicset.doc.doc.PactDocSKXYService;

@Controller
@RequestMapping(value = "/hrp/pac/skxy/pactdoc")
public class PactDocSKXYController extends BaseController {

	@Resource(name = "pactDocSKXYService")
	private PactDocSKXYService pactDocSKXYService;

	@RequestMapping(value = "/pactDocSKXYMainPage", method = RequestMethod.GET)
	public String toPactDocSKXYMainPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("pact_type_code", mapVo.get("pact_type_code"));
		return "hrp/pac/skxy/pactdoc/pactDocSKXYMain";
	}

	@ResponseBody
	@RequestMapping(value = "/queryPactDocSKXY", method = RequestMethod.POST)
	public Map<String, Object> queryPactDocSKXY(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactDocSKXYService.queryPactDocSKXY(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPactSKXYType", method = RequestMethod.POST)
	public Map<String, Object> queryPactSKXYType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String query = pactDocSKXYService.queryPactSKXYType(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 添加文档
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addPactDocSKXY", method = RequestMethod.POST)
	public Map<String, Object> addPactDocSKXY(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDocEntity> listVo = JSONArray.parseArray(mapVo, PactDocEntity.class);
			if (!listVo.isEmpty()) {
				pactDocSKXYService.addBatchPactDocSKXY(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"添加成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 删除文档
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deletePactDocSKXY", method = RequestMethod.POST)
	public Map<String, Object> deletePactDocSKXY(@RequestParam String mapVo, Model mode) {
		try {
			List<PactDocEntity> listVo = JSONArray.parseArray(mapVo, PactDocEntity.class);
			if (!listVo.isEmpty()) {
				pactDocSKXYService.deletePactDocSKXY(listVo);
			}
			return JSONObject.parseObject("{\"msg\":\"删除成功.\",\"state\":\"true\"}");
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	

	/**
	 * 添加图片信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	@ResponseBody
	public String addPicture(@RequestParam Map<String, Object> mapVo,
			@RequestParam(required = false, value = "file") MultipartFile file, Model mode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 处理头像上传
		if (file != null) {
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String url = null;
			String fileExt = null;// 文件类型
			String fileName = file.getOriginalFilename();// 文件原名称
			fileExt = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
					: null;// 文件类型
			if (fileExt != null) {
				String basePath = "upLoad/" + group_id + "/" + hos_id + "/pac/skxy/" + copy_code + "/";
				// 文件保存目录url
				String saveUrl = request.getContextPath() + "/" + basePath;

				url = saveUrl + fileName;
				FtpUtil.uploadFileExt(file, "", basePath, request, response);
				return "{\"url\":\"" + url + "\"}";
			} else {
				return "{\"error\":\"文件错误\"}";
			}
		}
		return "{\"error\":\"文件错误\"}";
	}
}
