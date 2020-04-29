/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.accept;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.entity.accept.AssAcceptPhoto;
import com.chd.hrp.ass.service.accept.AssAcceptPhotoService;
import com.chd.hrp.ass.service.card.AssCardBasicService;
/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssAcceptPhotoController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAcceptPhotoController.class);

	// 引入Service服务
	@Resource(name = "assAcceptPhotoService")
	private final AssAcceptPhotoService assAcceptPhotoService = null;

	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;
	
	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoAddPage", method = RequestMethod.GET)
	public String assAcceptPhotoAddPage(Model mode, String accept_no) throws Exception {
		mode.addAttribute("accept_no", accept_no);
		return "hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoAdd";
	}
	
	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoUpdatePage", method = RequestMethod.GET)
	public String assAcceptPhotoUpdatePage(Model mode, String accept_no,@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("accept_no", accept_no);
		
		AssAcceptPhoto assAcceptPhoto = assAcceptPhotoService.queryByCode(mapVo);
		mode.addAttribute("group_id", assAcceptPhoto.getGroup_id());
		mode.addAttribute("hos_id", assAcceptPhoto.getHos_id());
		mode.addAttribute("copy_code", assAcceptPhoto.getCopy_code());
		mode.addAttribute("accept_no", assAcceptPhoto.getAccept_no());
		mode.addAttribute("photo_code", assAcceptPhoto.getPhoto_code());
		mode.addAttribute("photo_name", assAcceptPhoto.getPhoto_name());
		mode.addAttribute("file_url", assAcceptPhoto.getFile_url());
		mode.addAttribute("spell_code", assAcceptPhoto.getSpell_code());
		mode.addAttribute("wbx_code", assAcceptPhoto.getWbx_code());
		mode.addAttribute("is_stop", assAcceptPhoto.getIs_stop());
		
		return "hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoUpdate";
	}
	

	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/saveAssCardPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardPhoto(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assAcceptPhotoJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			
				
			
			String basePath = "ass";
			String group_id = mapVo.get("group_id").toString();
			String hos_id = mapVo.get("hos_id").toString();
			String copy_code = mapVo.get("copy_code").toString();
			String assPhotoPath = "assacceptphoto";
			String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assPhotoPath + "/" + mapVo.get("accept_no").toString();
			
			String photoName = new String(file.getOriginalFilename());
			mapVo.put("file_url", filePath + "/" + photoName);
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("photo_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("photo_name").toString()));
			
			assAcceptPhotoJson = assAcceptPhotoService.add(mapVo);
			
			JSONObject json = JSONObject.parseObject(assAcceptPhotoJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assAcceptPhotoJson);
			}
			
			assAcceptPhotoJson = assCardBasicService.importPhoto(mapVo,file,request,response,filePath);
			
			if(assAcceptPhotoJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assAcceptPhotoJson);
	}
	
	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/updateAssCardPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardPhoto(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assAcceptPhotoJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String basePath = "ass";
			String group_id = mapVo.get("group_id").toString();
			String hos_id = mapVo.get("hos_id").toString();
			String copy_code = mapVo.get("copy_code").toString();
			String assPhotoPath = "assacceptphoto";
			String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assPhotoPath + "/" + mapVo.get("accept_no").toString();
			
			String photoName = new String(file.getOriginalFilename());
			mapVo.put("file_url", filePath + "/" + photoName);
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("photo_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("photo_name").toString()));
			
			assAcceptPhotoJson = assAcceptPhotoService.update(mapVo);
			
			JSONObject json = JSONObject.parseObject(assAcceptPhotoJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assAcceptPhotoJson);
			}
			
			assAcceptPhotoJson = assCardBasicService.importPhoto(mapVo,file,request,response,filePath);
			
			if(assAcceptPhotoJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assAcceptPhotoJson);
	}
	
	

	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/deleteAssAcceptPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAcceptPhoto(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("accept_no", ids[3]);
			mapVo.put("photo_code", ids[4]);
			mapVo.put("file_url", ids[5]);
			
			
			listVo.add(mapVo);

		}
		try {
			
			String assAcceptPhotoJson = assAcceptPhotoService.deleteBatch(listVo);
			
			
			JSONObject json = JSONObject.parseObject(assAcceptPhotoJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assAcceptPhotoJson);
			}
			
			assAcceptPhotoJson = assCardBasicService.deletePhoto(listVo);

			return JSONObject.parseObject(assAcceptPhotoJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	@RequestMapping(value = "hrp/ass/assacceptmain/acceptphoto/downAssPhoto", method = RequestMethod.GET)
	public String downAssPhoto(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardBasicService.downloadPhoto(response, mapVo);
		return null;
	}
	
	@RequestMapping(value = "hrp/ass/assacceptmain/acceptphoto/viewAssPhoto", method = RequestMethod.GET)
	public String viewAssPhoto(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardBasicService.viewPhoto(response, mapVo);
		return null;
	}
	

	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/queryAssAcceptPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptPhoto(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPhoto  = assAcceptPhotoService.query(getPage(mapVo));
		

		return JSONObject.parseObject(assPhoto);

	}

}
