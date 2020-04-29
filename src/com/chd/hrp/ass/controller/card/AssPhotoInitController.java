/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.card;

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
import com.chd.hrp.ass.entity.photo.AssPhotoInitGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoInitHouse;
import com.chd.hrp.ass.entity.photo.AssPhotoInitInassets;
import com.chd.hrp.ass.entity.photo.AssPhotoInitLand;
import com.chd.hrp.ass.entity.photo.AssPhotoInitOther;
import com.chd.hrp.ass.entity.photo.AssPhotoInitSpecial;
import com.chd.hrp.ass.service.card.AssCardInitBasicService;
import com.chd.hrp.ass.service.photo.AssPhotoInitGeneralService;
import com.chd.hrp.ass.service.photo.AssPhotoInitHouseService;
import com.chd.hrp.ass.service.photo.AssPhotoInitInassetsService;
import com.chd.hrp.ass.service.photo.AssPhotoInitLandService;
import com.chd.hrp.ass.service.photo.AssPhotoInitOtherService;
import com.chd.hrp.ass.service.photo.AssPhotoInitSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPhotoInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPhotoInitController.class);

	// 引入Service服务
	@Resource(name = "assPhotoInitGeneralService")
	private final AssPhotoInitGeneralService assPhotoInitGeneralService = null;

	@Resource(name = "assPhotoInitHouseService")
	private final AssPhotoInitHouseService assPhotoInitHouseService = null;

	@Resource(name = "assPhotoInitOtherService")
	private final AssPhotoInitOtherService assPhotoInitOtherService = null;

	@Resource(name = "assPhotoInitSpecialService")
	private final AssPhotoInitSpecialService assPhotoInitSpecialService = null;
	
	@Resource(name = "assPhotoInitInassetsService")
	private final AssPhotoInitInassetsService assPhotoInitInassetsService = null;

	@Resource(name = "assPhotoInitLandService")
	private final AssPhotoInitLandService assPhotoInitLandService = null;
	
	@Resource(name = "assCardInitBasicService")
	private final AssCardInitBasicService assCardInitBasicService = null;
	
	@RequestMapping(value = "/hrp/ass/asscardinit/assPhotoInitAddPage", method = RequestMethod.GET)
	public String assPhotoInitAddPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscardinit/photoinit/assPhotoInitAdd";
	}
	
	@RequestMapping(value = "/hrp/ass/asscardinit/assPhotoInitUpdatePage", method = RequestMethod.GET)
	public String assPhotoInitUpdatePage(Model mode, String ass_nature, String ass_card_no,@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		if (ass_nature.equals("01")) {
			AssPhotoInitHouse assPhotoHouse = assPhotoInitHouseService.queryByCode(mapVo);
			mode.addAttribute("group_id", assPhotoHouse.getGroup_id());
			mode.addAttribute("hos_id", assPhotoHouse.getHos_id());
			mode.addAttribute("copy_code", assPhotoHouse.getCopy_code());
			mode.addAttribute("ass_card_no", assPhotoHouse.getAss_card_no());
			mode.addAttribute("photo_code", assPhotoHouse.getPhoto_code());
			mode.addAttribute("photo_name", assPhotoHouse.getPhoto_name());
			mode.addAttribute("file_url", assPhotoHouse.getFile_url());
			mode.addAttribute("spell_code", assPhotoHouse.getSpell_code());
			mode.addAttribute("wbx_code", assPhotoHouse.getWbx_code());
			mode.addAttribute("is_stop", assPhotoHouse.getIs_stop());
			
		} else if (ass_nature.equals("02")) {
			AssPhotoInitSpecial assPhotoSpecial = assPhotoInitSpecialService.queryByCode(mapVo);
			mode.addAttribute("group_id", assPhotoSpecial.getGroup_id());
			mode.addAttribute("hos_id", assPhotoSpecial.getHos_id());
			mode.addAttribute("copy_code", assPhotoSpecial.getCopy_code());
			mode.addAttribute("ass_card_no", assPhotoSpecial.getAss_card_no());
			mode.addAttribute("photo_code", assPhotoSpecial.getPhoto_code());
			mode.addAttribute("photo_name", assPhotoSpecial.getPhoto_name());
			mode.addAttribute("file_url", assPhotoSpecial.getFile_url());
			mode.addAttribute("spell_code", assPhotoSpecial.getSpell_code());
			mode.addAttribute("wbx_code", assPhotoSpecial.getWbx_code());
			mode.addAttribute("is_stop", assPhotoSpecial.getIs_stop());
			
		} else if (ass_nature.equals("03")) {
			AssPhotoInitGeneral assPhotoGeneral = assPhotoInitGeneralService.queryByCode(mapVo);
			mode.addAttribute("group_id", assPhotoGeneral.getGroup_id());
			mode.addAttribute("hos_id", assPhotoGeneral.getHos_id());
			mode.addAttribute("copy_code", assPhotoGeneral.getCopy_code());
			mode.addAttribute("ass_card_no", assPhotoGeneral.getAss_card_no());
			mode.addAttribute("photo_code", assPhotoGeneral.getPhoto_code());
			mode.addAttribute("photo_name", assPhotoGeneral.getPhoto_name());
			mode.addAttribute("file_url", assPhotoGeneral.getFile_url());
			mode.addAttribute("spell_code", assPhotoGeneral.getSpell_code());
			mode.addAttribute("wbx_code", assPhotoGeneral.getWbx_code());
			mode.addAttribute("is_stop", assPhotoGeneral.getIs_stop());
		} else if (ass_nature.equals("04")) {
			AssPhotoInitOther assPhotoOther = assPhotoInitOtherService.queryByCode(mapVo);
			mode.addAttribute("group_id", assPhotoOther.getGroup_id());
			mode.addAttribute("hos_id", assPhotoOther.getHos_id());
			mode.addAttribute("copy_code", assPhotoOther.getCopy_code());
			mode.addAttribute("ass_card_no", assPhotoOther.getAss_card_no());
			mode.addAttribute("photo_code", assPhotoOther.getPhoto_code());
			mode.addAttribute("photo_name", assPhotoOther.getPhoto_name());
			mode.addAttribute("file_url", assPhotoOther.getFile_url());
			mode.addAttribute("spell_code", assPhotoOther.getSpell_code());
			mode.addAttribute("wbx_code", assPhotoOther.getWbx_code());
			mode.addAttribute("is_stop", assPhotoOther.getIs_stop());
		} else if (ass_nature.equals("05")) {
			AssPhotoInitInassets assPhotoInassets = assPhotoInitInassetsService.queryByCode(mapVo);
			mode.addAttribute("group_id", assPhotoInassets.getGroup_id());
			mode.addAttribute("hos_id", assPhotoInassets.getHos_id());
			mode.addAttribute("copy_code", assPhotoInassets.getCopy_code());
			mode.addAttribute("ass_card_no", assPhotoInassets.getAss_card_no());
			mode.addAttribute("photo_code", assPhotoInassets.getPhoto_code());
			mode.addAttribute("photo_name", assPhotoInassets.getPhoto_name());
			mode.addAttribute("file_url", assPhotoInassets.getFile_url());
			mode.addAttribute("spell_code", assPhotoInassets.getSpell_code());
			mode.addAttribute("wbx_code", assPhotoInassets.getWbx_code());
			mode.addAttribute("is_stop", assPhotoInassets.getIs_stop());
		} else if (ass_nature.equals("06")) {
			AssPhotoInitLand assPhotoLand = assPhotoInitLandService.queryByCode(mapVo);
			mode.addAttribute("group_id", assPhotoLand.getGroup_id());
			mode.addAttribute("hos_id", assPhotoLand.getHos_id());
			mode.addAttribute("copy_code", assPhotoLand.getCopy_code());
			mode.addAttribute("ass_card_no", assPhotoLand.getAss_card_no());
			mode.addAttribute("photo_code", assPhotoLand.getPhoto_code());
			mode.addAttribute("photo_name", assPhotoLand.getPhoto_name());
			mode.addAttribute("file_url", assPhotoLand.getFile_url());
			mode.addAttribute("spell_code", assPhotoLand.getSpell_code());
			mode.addAttribute("wbx_code", assPhotoLand.getWbx_code());
			mode.addAttribute("is_stop", assPhotoLand.getIs_stop());
		}
		
		return "hrp/ass/asscardinit/photoinit/assPhotoInitUpdate";
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/saveAssCardPhotoInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardPhotoInit(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assPhotoGeneralJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String basePath = "ass";
			String group_id = mapVo.get("group_id").toString();
			String hos_id = mapVo.get("hos_id").toString();
			String copy_code = mapVo.get("copy_code").toString();
			String assPhotoPath = "assphoto";
			String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assPhotoPath + "/" + mapVo.get("ass_nature").toString() + "/" + mapVo.get("ass_card_no").toString();
			
			String photoName = new String(file.getOriginalFilename());
			mapVo.put("file_url", filePath + "/" + photoName);
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("photo_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("photo_name").toString()));
			
			
			if (mapVo.get("ass_nature").equals("01")) {
				assPhotoGeneralJson = assPhotoInitHouseService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assPhotoGeneralJson = assPhotoInitSpecialService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assPhotoGeneralJson = assPhotoInitGeneralService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assPhotoGeneralJson = assPhotoInitOtherService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assPhotoGeneralJson = assPhotoInitInassetsService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assPhotoGeneralJson = assPhotoInitLandService.add(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assPhotoGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assPhotoGeneralJson);
			}
			
			assPhotoGeneralJson = assCardInitBasicService.importPhoto(mapVo,file,request,response,filePath);
			
			if(assPhotoGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPhotoGeneralJson);
	}
	
	@RequestMapping(value = "/hrp/ass/asscardinit/updateAssCardPhotoInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardPhotoInit(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assPhotoGeneralJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			String basePath = "ass";
			String group_id = mapVo.get("group_id").toString();
			String hos_id = mapVo.get("hos_id").toString();
			String copy_code = mapVo.get("copy_code").toString();
			String assPhotoPath = "assphoto";
			String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assPhotoPath + "/" + mapVo.get("ass_nature").toString() + "/" + mapVo.get("ass_card_no").toString();
			
			String photoName = new String(file.getOriginalFilename());
			mapVo.put("file_url", filePath + "/" + photoName);
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("photo_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("photo_name").toString()));
			
			
			if (mapVo.get("ass_nature").equals("01")) {
				assPhotoGeneralJson = assPhotoInitHouseService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assPhotoGeneralJson = assPhotoInitSpecialService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assPhotoGeneralJson = assPhotoInitGeneralService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assPhotoGeneralJson = assPhotoInitOtherService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assPhotoGeneralJson = assPhotoInitInassetsService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assPhotoGeneralJson = assPhotoInitLandService.update(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assPhotoGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assPhotoGeneralJson);
			}
			
			assPhotoGeneralJson = assCardInitBasicService.importPhoto(mapVo,file,request,response,filePath);
			
			if(assPhotoGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPhotoGeneralJson);
	}
	
	

	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssPhotoInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPhotoInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("photo_code", ids[4]);
			mapVo.put("file_url", ids[5]);
			listVo.add(mapVo);

		}
		try {
			String assPhotoGeneralJson = "";
			if (ass_nature.equals("01")) {
				assPhotoGeneralJson = assPhotoInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assPhotoGeneralJson = assPhotoInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assPhotoGeneralJson = assPhotoInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assPhotoGeneralJson = assPhotoInitOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assPhotoGeneralJson = assPhotoInitInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assPhotoGeneralJson = assPhotoInitLandService.deleteBatch(listVo);
			}
			
			JSONObject json = JSONObject.parseObject(assPhotoGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assPhotoGeneralJson);
			}
			
			assPhotoGeneralJson = assCardInitBasicService.deletePhoto(listVo);

			return JSONObject.parseObject(assPhotoGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	@RequestMapping(value = "hrp/ass/asscardinit/downAssPhotoInit", method = RequestMethod.GET)
	public String downAssPhotoInit(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardInitBasicService.downloadPhoto(response, mapVo);
		return null;
	}
	
	@RequestMapping(value = "hrp/ass/asscardinit/viewAssPhotoInit", method = RequestMethod.GET)
	public String viewAssPhotoInit(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardInitBasicService.viewPhoto(response, mapVo);
		return null;
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssPhotoInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPhotoInit(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assPhoto = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assPhoto = assPhotoInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assPhoto = assPhotoInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assPhoto = assPhotoInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assPhoto = assPhotoInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assPhoto = assPhotoInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assPhoto = assPhotoInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assPhoto);

	}

}
