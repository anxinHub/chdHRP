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
import com.chd.hrp.ass.entity.file.AssFileGeneral;
import com.chd.hrp.ass.entity.file.AssFileHouse;
import com.chd.hrp.ass.entity.file.AssFileInassets;
import com.chd.hrp.ass.entity.file.AssFileLand;
import com.chd.hrp.ass.entity.file.AssFileOther;
import com.chd.hrp.ass.entity.file.AssFileSpecial;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.file.AssFileGeneralService;
import com.chd.hrp.ass.service.file.AssFileHouseService;
import com.chd.hrp.ass.service.file.AssFileInassetsService;
import com.chd.hrp.ass.service.file.AssFileLandService;
import com.chd.hrp.ass.service.file.AssFileOtherService;
import com.chd.hrp.ass.service.file.AssFileSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssFileController extends BaseController {

	private static Logger logger = Logger.getLogger(AssFileController.class);

	// 引入Service服务
	@Resource(name = "assFileGeneralService")
	private final AssFileGeneralService assFileGeneralService = null;

	@Resource(name = "assFileHouseService")
	private final AssFileHouseService assFileHouseService = null;

	@Resource(name = "assFileOtherService")
	private final AssFileOtherService assFileOtherService = null;

	@Resource(name = "assFileSpecialService")
	private final AssFileSpecialService assFileSpecialService = null;
	
	@Resource(name = "assFileInassetsService")
	private final AssFileInassetsService assFileInassetsService = null;

	@Resource(name = "assFileLandService")
	private final AssFileLandService assFileLandService = null;
	
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
	
	@RequestMapping(value = "/hrp/ass/asscard/assFileAddPage", method = RequestMethod.GET)
	public String assFileAddPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/file/assFileAdd";
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/assFileUpdatePage", method = RequestMethod.GET)
	public String assFileUpdatePage(Model mode, String ass_nature, String ass_card_no,@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		if (ass_nature.equals("01")) {
			AssFileHouse assFileHouse = assFileHouseService.queryByCode(mapVo);
			mode.addAttribute("group_id", assFileHouse.getGroup_id());
			mode.addAttribute("hos_id", assFileHouse.getHos_id());
			mode.addAttribute("copy_code", assFileHouse.getCopy_code());
			mode.addAttribute("ass_card_no", assFileHouse.getAss_card_no());
			mode.addAttribute("file_code", assFileHouse.getFile_code());
			mode.addAttribute("file_name", assFileHouse.getFile_name());
			mode.addAttribute("equi_usage_code", assFileHouse.getEqui_usage_code());
			mode.addAttribute("equi_usage_name", assFileHouse.getEqui_usage_name());
			mode.addAttribute("file_url", assFileHouse.getFile_url());
			mode.addAttribute("location", assFileHouse.getLocation());
			mode.addAttribute("spell_code", assFileHouse.getSpell_code());
			mode.addAttribute("wbx_code", assFileHouse.getWbx_code());
			mode.addAttribute("is_stop", assFileHouse.getIs_stop());
			
		} else if (ass_nature.equals("02")) {
			AssFileSpecial assFileSpecial = assFileSpecialService.queryByCode(mapVo);
			mode.addAttribute("group_id", assFileSpecial.getGroup_id());
			mode.addAttribute("hos_id", assFileSpecial.getHos_id());
			mode.addAttribute("copy_code", assFileSpecial.getCopy_code());
			mode.addAttribute("ass_card_no", assFileSpecial.getAss_card_no());
			mode.addAttribute("file_code", assFileSpecial.getFile_code());
			mode.addAttribute("file_name", assFileSpecial.getFile_name());
			mode.addAttribute("equi_usage_code", assFileSpecial.getEqui_usage_code());
			mode.addAttribute("equi_usage_name", assFileSpecial.getEqui_usage_name());
			mode.addAttribute("file_url", assFileSpecial.getFile_url());
			mode.addAttribute("location", assFileSpecial.getLocation());
			mode.addAttribute("spell_code", assFileSpecial.getSpell_code());
			mode.addAttribute("wbx_code", assFileSpecial.getWbx_code());
			mode.addAttribute("is_stop", assFileSpecial.getIs_stop());
			
		} else if (ass_nature.equals("03")) {
			AssFileGeneral assFileGeneral = assFileGeneralService.queryByCode(mapVo);
			mode.addAttribute("group_id", assFileGeneral.getGroup_id());
			mode.addAttribute("hos_id", assFileGeneral.getHos_id());
			mode.addAttribute("copy_code", assFileGeneral.getCopy_code());
			mode.addAttribute("ass_card_no", assFileGeneral.getAss_card_no());
			mode.addAttribute("file_code", assFileGeneral.getFile_code());
			mode.addAttribute("file_name", assFileGeneral.getFile_name());
			mode.addAttribute("equi_usage_code", assFileGeneral.getEqui_usage_code());
			mode.addAttribute("equi_usage_name", assFileGeneral.getEqui_usage_name());
			mode.addAttribute("file_url", assFileGeneral.getFile_url());
			mode.addAttribute("location", assFileGeneral.getLocation());
			mode.addAttribute("spell_code", assFileGeneral.getSpell_code());
			mode.addAttribute("wbx_code", assFileGeneral.getWbx_code());
			mode.addAttribute("is_stop", assFileGeneral.getIs_stop());
		} else if (ass_nature.equals("04")) {
			AssFileOther assFileOther = assFileOtherService.queryByCode(mapVo);
			mode.addAttribute("group_id", assFileOther.getGroup_id());
			mode.addAttribute("hos_id", assFileOther.getHos_id());
			mode.addAttribute("copy_code", assFileOther.getCopy_code());
			mode.addAttribute("ass_card_no", assFileOther.getAss_card_no());
			mode.addAttribute("file_code", assFileOther.getFile_code());
			mode.addAttribute("file_name", assFileOther.getFile_name());
			mode.addAttribute("equi_usage_code", assFileOther.getEqui_usage_code());
			mode.addAttribute("equi_usage_name", assFileOther.getEqui_usage_name());
			mode.addAttribute("file_url", assFileOther.getFile_url());
			mode.addAttribute("location", assFileOther.getLocation());
			mode.addAttribute("spell_code", assFileOther.getSpell_code());
			mode.addAttribute("wbx_code", assFileOther.getWbx_code());
			mode.addAttribute("is_stop", assFileOther.getIs_stop());
		} else if (ass_nature.equals("05")) {
			AssFileInassets assFileInassets = assFileInassetsService.queryByCode(mapVo);
			mode.addAttribute("group_id", assFileInassets.getGroup_id());
			mode.addAttribute("hos_id", assFileInassets.getHos_id());
			mode.addAttribute("copy_code", assFileInassets.getCopy_code());
			mode.addAttribute("ass_card_no", assFileInassets.getAss_card_no());
			mode.addAttribute("file_code", assFileInassets.getFile_code());
			mode.addAttribute("file_name", assFileInassets.getFile_name());
			mode.addAttribute("equi_usage_code", assFileInassets.getEqui_usage_code());
			mode.addAttribute("equi_usage_name", assFileInassets.getEqui_usage_name());
			mode.addAttribute("file_url", assFileInassets.getFile_url());
			mode.addAttribute("location", assFileInassets.getLocation());
			mode.addAttribute("spell_code", assFileInassets.getSpell_code());
			mode.addAttribute("wbx_code", assFileInassets.getWbx_code());
			mode.addAttribute("is_stop", assFileInassets.getIs_stop());
		} else if (ass_nature.equals("06")) {
			AssFileLand assFileLand = assFileLandService.queryByCode(mapVo);
			mode.addAttribute("group_id", assFileLand.getGroup_id());
			mode.addAttribute("hos_id", assFileLand.getHos_id());
			mode.addAttribute("copy_code", assFileLand.getCopy_code());
			mode.addAttribute("ass_card_no", assFileLand.getAss_card_no());
			mode.addAttribute("file_code", assFileLand.getFile_code());
			mode.addAttribute("file_name", assFileLand.getFile_name());
			mode.addAttribute("equi_usage_code", assFileLand.getEqui_usage_code());
			mode.addAttribute("equi_usage_name", assFileLand.getEqui_usage_name());
			mode.addAttribute("file_url", assFileLand.getFile_url());
			mode.addAttribute("location", assFileLand.getLocation());
			mode.addAttribute("spell_code", assFileLand.getSpell_code());
			mode.addAttribute("wbx_code", assFileLand.getWbx_code());
			mode.addAttribute("is_stop", assFileLand.getIs_stop());
		}
		
		return "hrp/ass/asscard/file/assFileUpdate";
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/saveAssCardFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardFile(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assFileGeneralJson = "";
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
			String assFilePath = "assfile";
			String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/" + mapVo.get("ass_nature").toString() + "/" + mapVo.get("ass_card_no").toString();
			
			String fileName = new String(file.getOriginalFilename());
			mapVo.put("file_url", filePath + "/" + fileName);
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("file_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("file_name").toString()));
			
			
			if (mapVo.get("ass_nature").equals("01")) {
				assFileGeneralJson = assFileHouseService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assFileGeneralJson = assFileSpecialService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assFileGeneralJson = assFileGeneralService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assFileGeneralJson = assFileOtherService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assFileGeneralJson = assFileInassetsService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assFileGeneralJson = assFileLandService.add(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assFileGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assFileGeneralJson);
			}
			
			assFileGeneralJson = assCardBasicService.importFile(mapVo,file,request,response,filePath);
			
			if(assFileGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assFileGeneralJson);
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/updateAssCardFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardFile(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assFileGeneralJson = "";
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
			String assFilePath = "assfile";
			String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/" + mapVo.get("ass_nature").toString() + "/" + mapVo.get("ass_card_no").toString();
			
			String fileName = new String(file.getOriginalFilename());
			mapVo.put("file_url", filePath + "/" + fileName);
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("file_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("file_name").toString()));
			
			
			if (mapVo.get("ass_nature").equals("01")) {
				assFileGeneralJson = assFileHouseService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assFileGeneralJson = assFileSpecialService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assFileGeneralJson = assFileGeneralService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assFileGeneralJson = assFileOtherService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assFileGeneralJson = assFileInassetsService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assFileGeneralJson = assFileLandService.update(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assFileGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assFileGeneralJson);
			}
			
			assFileGeneralJson = assCardBasicService.importFile(mapVo,file,request,response,filePath);
			
			if(assFileGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assFileGeneralJson);
	}
	
	

	@RequestMapping(value = "/hrp/ass/asscard/deleteAssFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssFile(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {
		boolean flag = true;
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("file_code", ids[4]);
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
			
			String assFileGeneralJson = "";
			if (ass_nature.equals("01")) {
				assFileGeneralJson = assFileHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assFileGeneralJson = assFileSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assFileGeneralJson = assFileGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assFileGeneralJson = assFileOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assFileGeneralJson = assFileInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assFileGeneralJson = assFileLandService.deleteBatch(listVo);
			}
			
			JSONObject json = JSONObject.parseObject(assFileGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assFileGeneralJson);
			}
			
			assFileGeneralJson = assCardBasicService.deleteFile(listVo);

			return JSONObject.parseObject(assFileGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	@RequestMapping(value = "hrp/ass/asscard/downAssFile", method = RequestMethod.GET)
	public String downAssFile(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardBasicService.downloadFile(response, mapVo);
		return null;
	}
	

	@RequestMapping(value = "/hrp/ass/asscard/queryAssFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssFile(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assFile = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assFile = assFileHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assFile = assFileSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assFile = assFileGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assFile = assFileOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assFile = assFileInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assFile = assFileLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assFile);

	}

}
