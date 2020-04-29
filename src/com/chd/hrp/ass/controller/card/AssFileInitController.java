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
import com.chd.hrp.ass.entity.file.AssFileInitGeneral;
import com.chd.hrp.ass.entity.file.AssFileInitHouse;
import com.chd.hrp.ass.entity.file.AssFileInitInassets;
import com.chd.hrp.ass.entity.file.AssFileInitLand;
import com.chd.hrp.ass.entity.file.AssFileInitOther;
import com.chd.hrp.ass.entity.file.AssFileInitSpecial;
import com.chd.hrp.ass.service.card.AssCardInitBasicService;
import com.chd.hrp.ass.service.file.AssFileInitGeneralService;
import com.chd.hrp.ass.service.file.AssFileInitHouseService;
import com.chd.hrp.ass.service.file.AssFileInitInassetsService;
import com.chd.hrp.ass.service.file.AssFileInitLandService;
import com.chd.hrp.ass.service.file.AssFileInitOtherService;
import com.chd.hrp.ass.service.file.AssFileInitSpecialService;

/**
 * 
 * @Description: 
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssFileInitController extends BaseController {

	private static Logger logger = Logger.getLogger(AssFileInitController.class);

	// 引入Service服务
	@Resource(name = "assFileInitGeneralService")
	private final AssFileInitGeneralService assFileInitGeneralService = null;

	@Resource(name = "assFileInitHouseService")
	private final AssFileInitHouseService assFileInitHouseService = null;

	@Resource(name = "assFileInitOtherService")
	private final AssFileInitOtherService assFileInitOtherService = null;

	@Resource(name = "assFileInitSpecialService")
	private final AssFileInitSpecialService assFileInitSpecialService = null;
	
	@Resource(name = "assFileInitInassetsService")
	private final AssFileInitInassetsService assFileInitInassetsService = null;

	@Resource(name = "assFileInitLandService")
	private final AssFileInitLandService assFileInitLandService = null;
	
	@Resource(name = "assCardInitBasicService")
	private final AssCardInitBasicService assCardInitBasicService = null;
	
	@RequestMapping(value = "/hrp/ass/asscardinit/assFileInitAddPage", method = RequestMethod.GET)
	public String assFileInitAddPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscardinit/fileinit/assFileInitAdd";
	}
	
	@RequestMapping(value = "/hrp/ass/asscardinit/assFileInitUpdatePage", method = RequestMethod.GET)
	public String assFileInitUpdatePage(Model mode, String ass_nature, String ass_card_no,@RequestParam Map<String, Object> mapVo) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		if (ass_nature.equals("01")) {
			AssFileInitHouse assFileHouse = assFileInitHouseService.queryByCode(mapVo);
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
			AssFileInitSpecial assFileSpecial = assFileInitSpecialService.queryByCode(mapVo);
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
			AssFileInitGeneral assFileGeneral = assFileInitGeneralService.queryByCode(mapVo);
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
			AssFileInitOther assFileOther = assFileInitOtherService.queryByCode(mapVo);
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
			AssFileInitInassets assFileInassets = assFileInitInassetsService.queryByCode(mapVo);
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
			AssFileInitLand assFileLand = assFileInitLandService.queryByCode(mapVo);
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
		
		return "hrp/ass/asscardinit/fileinit/assFileUpdate";
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/saveAssCardInitFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssCardInitFile(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assFileGeneralJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
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
				assFileGeneralJson = assFileInitHouseService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assFileGeneralJson = assFileInitSpecialService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assFileGeneralJson = assFileInitGeneralService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assFileGeneralJson = assFileInitOtherService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assFileGeneralJson = assFileInitInassetsService.add(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assFileGeneralJson = assFileInitLandService.add(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assFileGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assFileGeneralJson);
			}
			
			assFileGeneralJson = assCardInitBasicService.importFile(mapVo,file,request,response,filePath);
			
			if(assFileGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assFileGeneralJson);
	}
	
	@RequestMapping(value = "/hrp/ass/asscardinit/updateAssCardInitFile", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardInitFile(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file_url");
		String assFileGeneralJson = "";
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
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
				assFileGeneralJson = assFileInitHouseService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("02")) {
				assFileGeneralJson = assFileInitSpecialService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("03")) {
				assFileGeneralJson = assFileInitGeneralService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("04")) {
				assFileGeneralJson = assFileInitOtherService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("05")){
				assFileGeneralJson = assFileInitInassetsService.update(mapVo);
			} else if (mapVo.get("ass_nature").equals("06")){
				assFileGeneralJson = assFileInitLandService.update(mapVo);
			}
			
			JSONObject json = JSONObject.parseObject(assFileGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assFileGeneralJson);
			}
			
			assFileGeneralJson = assCardInitBasicService.importFile(mapVo,file,request,response,filePath);
			
			if(assFileGeneralJson.equals("error")){
				return JSONObject.parseObject("{\"error\":\"上传文件有误 \"}");
			}
			
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assFileGeneralJson);
	}
	
	

	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssFileInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssFileInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
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
			mapVo.put("file_code", ids[4]);
			mapVo.put("file_url", ids[5]);
			listVo.add(mapVo);

		}
		try {
			String assFileGeneralJson = "";
			if (ass_nature.equals("01")) {
				assFileGeneralJson = assFileInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assFileGeneralJson = assFileInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assFileGeneralJson = assFileInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assFileGeneralJson = assFileInitOtherService.deleteBatch(listVo);
			} else if (ass_nature.equals("05")){
				assFileGeneralJson = assFileInitInassetsService.deleteBatch(listVo);
			} else if (ass_nature.equals("06")){
				assFileGeneralJson = assFileInitLandService.deleteBatch(listVo);
			}
			
			JSONObject json = JSONObject.parseObject(assFileGeneralJson);
			if(!json.get("state").equals("true")){
				return JSONObject.parseObject(assFileGeneralJson);
			}
			
			assFileGeneralJson = assCardInitBasicService.deleteFile(listVo);

			return JSONObject.parseObject(assFileGeneralJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	@RequestMapping(value = "hrp/ass/asscardinit/downAssFileInit", method = RequestMethod.GET)
	public String downAssFileInit(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		assCardInitBasicService.downloadFile(response, mapVo);
		return null;
	}
	

	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssFileInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssFileInit(@RequestParam Map<String, Object> mapVo, Model mode)
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
			assFile = assFileInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assFile = assFileInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assFile = assFileInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assFile = assFileInitOtherService.query(getPage(mapVo));
		} else if (ass_nature.equals("05")) {
			assFile = assFileInitInassetsService.query(getPage(mapVo));
		} else if (ass_nature.equals("06")) {
			assFile = assFileInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assFile);

	}

}
