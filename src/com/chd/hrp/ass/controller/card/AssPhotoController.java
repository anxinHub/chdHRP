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
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.photo.AssPhotoGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoHouse;
import com.chd.hrp.ass.entity.photo.AssPhotoInassets;
import com.chd.hrp.ass.entity.photo.AssPhotoLand;
import com.chd.hrp.ass.entity.photo.AssPhotoOther;
import com.chd.hrp.ass.entity.photo.AssPhotoSpecial;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.photo.AssPhotoGeneralService;
import com.chd.hrp.ass.service.photo.AssPhotoHouseService;
import com.chd.hrp.ass.service.photo.AssPhotoInassetsService;
import com.chd.hrp.ass.service.photo.AssPhotoLandService;
import com.chd.hrp.ass.service.photo.AssPhotoOtherService;
import com.chd.hrp.ass.service.photo.AssPhotoSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssPhotoController extends BaseController {

	private static Logger logger = Logger.getLogger(AssPhotoController.class);

	// 引入Service服务
	@Resource(name = "assPhotoGeneralService")
	private final AssPhotoGeneralService assPhotoGeneralService = null;

	@Resource(name = "assPhotoHouseService")
	private final AssPhotoHouseService assPhotoHouseService = null;

	@Resource(name = "assPhotoOtherService")
	private final AssPhotoOtherService assPhotoOtherService = null;

	@Resource(name = "assPhotoSpecialService")
	private final AssPhotoSpecialService assPhotoSpecialService = null;
	
	@Resource(name = "assPhotoInassetsService")
	private final AssPhotoInassetsService assPhotoInassetsService = null;

	@Resource(name = "assPhotoLandService")
	private final AssPhotoLandService assPhotoLandService = null;
	
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;
	
	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;
	
	@RequestMapping(value = "/hrp/ass/asscard/assPhotoAddPage", method = RequestMethod.GET)
	public String assPhotoAddPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/photo/assPhotoAdd";
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/assPhotoUpdatePage", method = RequestMethod.GET)
	public String assPhotoUpdatePage(Model mode, String ass_nature, String ass_card_no,@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		if (ass_nature.equals("01")) {
			AssPhotoHouse assPhotoHouse = assPhotoHouseService.queryByCode(mapVo);
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
			AssPhotoSpecial assPhotoSpecial = assPhotoSpecialService.queryByCode(mapVo);
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
			AssPhotoGeneral assPhotoGeneral = assPhotoGeneralService.queryByCode(mapVo);
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
			AssPhotoOther assPhotoOther = assPhotoOtherService.queryByCode(mapVo);
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
			AssPhotoInassets assPhotoInassets = assPhotoInassetsService.queryByCode(mapVo);
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
			AssPhotoLand assPhotoLand = assPhotoLandService.queryByCode(mapVo);
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
		
		return "hrp/ass/asscard/photo/assPhotoUpdate";
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/saveAssCardPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardPhoto(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assPhotoGeneralJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String ass_nature = (String) mapVo.get("ass_nature");
			if (ass_nature.equals("01")) {
				
				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
			} else if (ass_nature.equals("04")) {
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
			} else if (ass_nature.equals("05")){
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
			} else if (ass_nature.equals("06")){
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
			}
			
			
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
				assPhotoGeneralJson = assPhotoHouseService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assPhotoGeneralJson = assPhotoSpecialService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assPhotoGeneralJson = assPhotoGeneralService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assPhotoGeneralJson = assPhotoOtherService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assPhotoGeneralJson = assPhotoInassetsService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assPhotoGeneralJson = assPhotoLandService.add(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assPhotoGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assPhotoGeneralJson);
			}
			
			assPhotoGeneralJson = assCardBasicService.importPhoto(mapVo,file,request,response,filePath);
			
			if(assPhotoGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPhotoGeneralJson);
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/updateAssCardPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardPhoto(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assPhotoGeneralJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String ass_nature = (String) mapVo.get("ass_nature");
			if (ass_nature.equals("01")) {
				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡卡片已被处置或待处置,不能保存! \"}");
				}
				
			} else if (ass_nature.equals("04")) {
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
			} else if (ass_nature.equals("05")){
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
			} else if (ass_nature.equals("06")){
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
			}
			
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
				assPhotoGeneralJson = assPhotoHouseService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assPhotoGeneralJson = assPhotoSpecialService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assPhotoGeneralJson = assPhotoGeneralService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assPhotoGeneralJson = assPhotoOtherService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assPhotoGeneralJson = assPhotoInassetsService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assPhotoGeneralJson = assPhotoLandService.update(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assPhotoGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assPhotoGeneralJson);
			}
			
			assPhotoGeneralJson = assCardBasicService.importPhoto(mapVo,file,request,response,filePath);
			
			if(assPhotoGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assPhotoGeneralJson);
	}
	
	

	@RequestMapping(value = "/hrp/ass/asscard/deleteAssPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssPhoto(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("photo_code", ids[4]);
			mapVo.put("file_url", ids[5]);
			
			if (ass_nature.equals("01")) {
				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
					flag = false;
					break;
				}
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
					flag = false;
					break;
				}
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
					flag = false;
					break;
				}
			} else if (ass_nature.equals("04")) {
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
					flag = false;
					break;
				}
				
			} else if (ass_nature.equals("05")){
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
					flag = false;
					break;
				}
				
			} else if (ass_nature.equals("06")){
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
					flag = false;
					break;
				}
			}
			
			listVo.add(mapVo);

		}
		try {
			if(!flag){
				return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能删除! \"}");
			}
			
			String assPhotoGeneralJson = "";
			if (ass_nature.equals("01")) {
				assPhotoGeneralJson = assPhotoHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assPhotoGeneralJson = assPhotoSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assPhotoGeneralJson = assPhotoGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assPhotoGeneralJson = assPhotoOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assPhotoGeneralJson = assPhotoInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assPhotoGeneralJson = assPhotoLandService.deleteBatch(listVo);
			}
			
			JSONObject json = JSONObject.parseObject(assPhotoGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assPhotoGeneralJson);
			}
			
			assPhotoGeneralJson = assCardBasicService.deletePhoto(listVo);

			return JSONObject.parseObject(assPhotoGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	@RequestMapping(value = "hrp/ass/asscard/downAssPhoto", method = RequestMethod.GET)
	public String downAssPhoto(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardBasicService.downloadPhoto(response, mapVo);
		return null;
	}
	
	@RequestMapping(value = "hrp/ass/asscard/viewAssPhoto", method = RequestMethod.GET)
	public String viewAssPhoto(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardBasicService.viewPhoto(response, mapVo);
		return null;
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/queryAssPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssPhoto(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assPhoto = assPhotoHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assPhoto = assPhotoSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assPhoto = assPhotoGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assPhoto = assPhotoOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assPhoto = assPhotoInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assPhoto = assPhotoLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assPhoto);

	}

}
