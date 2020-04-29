package com.chd.hrp.hr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SessionManager;
import com.chd.hrp.hr.util.FileUploadUtils;

/**
 * 文件上传
 * 
 * @author zhaonan
 *
 */
@Controller
public class FileUploadController {

	/**
	 * 通用上传请求
	 */
	@RequestMapping(value = "/hrp/hr/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request, @RequestParam MultipartFile[] files)
			throws Exception {
		Map<String, Object> reJson = new HashMap<String, Object>();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			String filePath = "/upLoad/hr/" + SessionManager.getGroupId() + "/" + SessionManager.getHosId()
					+ "/";
			String path = request.getServletContext().getRealPath("/") + filePath;
			String url = request.getContextPath() + filePath;

			for (MultipartFile file : files) {
				Map<String, String> obj = new HashMap<String, String>();
				String newFilename = FileUploadUtils.upload(path, file);
				obj.put("url", url + newFilename);
				obj.put("filename", file.getOriginalFilename());
				obj.put("randomname", newFilename);
				list.add(obj);
			}
			reJson.put("data", list);
			return reJson;
		} catch (Exception e) {
			reJson.put("error", e.getMessage());
			return reJson;
		}
	}
}
