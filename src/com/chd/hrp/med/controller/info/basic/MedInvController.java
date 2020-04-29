/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.info.basic;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.img.Images;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ExcelWriterTo2007;
import com.chd.base.util.FileUtil;
import com.chd.base.util.PageModel;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoOtherMapper;
import com.chd.hrp.med.dao.info.basic.MedInvMapper;
import com.chd.hrp.med.dao.info.basic.MedTypeDictMapper;
import com.chd.hrp.med.entity.MedInv;
import com.chd.hrp.med.entity.MedInvCert;
import com.chd.hrp.med.entity.MedStoreType;
import com.chd.hrp.med.entity.MedType;
import com.chd.hrp.med.entity.MedTypeDict;
import com.chd.hrp.med.entity.info.basic.MedJx;
import com.chd.hrp.med.entity.info.basic.MedSx;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.base.MedInvPictureService;
import com.chd.hrp.med.service.info.basic.MedInvCertService;
import com.chd.hrp.med.service.info.basic.MedInvService;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreInvServiceImpl;
import com.chd.hrp.med.serviceImpl.info.basic.MedStoreTypeServiceImpl;
import com.chd.hrp.sys.dao.FacDictMapper;
import com.chd.hrp.sys.dao.SupDictMapper;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.FacDict;
import com.chd.hrp.sys.entity.SupDict;
import com.chd.hrp.sys.entity.Unit;
import com.chd.hrp.sys.service.SupDictService;

/**
 * 
 * @Description: 08105 药品材料表
 * @Table: MED_INV
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedInvController extends BaseController {

	private static Logger logger = Logger.getLogger(MedInvController.class);

	// 引入Service服务
	@Resource(name = "medInvService")
	private final MedInvService medInvService = null;

	@Resource(name = "medInvMapper")
	private final MedInvMapper medInvMapper = null;

	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;

	@Resource(name = "supDictService")
	private final SupDictService supDictService = null;

	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;

	@Resource(name = "facDictMapper")
	private final FacDictMapper facDictMapper = null;

	@Resource(name = "medTypeDictMapper")
	private final MedTypeDictMapper medTypeDictMapper = null;

	@Resource(name = "medNoOtherMapper")
	private final MedNoOtherMapper medNoOtherMapper = null;

	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	@Resource(name = "supDictMapper")
	private final SupDictMapper supDictMapper = null;
	
	@Resource(name = "medInvPictureService")
	private final MedInvPictureService medInvPictureService = null;

	@Resource(name = "medStoreTypeService")
	private final MedStoreTypeServiceImpl medStoreTypeService = null;
	
	@Resource(name = "medStoreInvService")
	private final MedStoreInvServiceImpl medStoreInvService = null;
	
	@Resource(name = "medInvCertService")
	private final MedInvCertService medInvCertService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvMainPage", method = RequestMethod.GET)
	public String medInvMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		mode.addAttribute("p08017", MyConfig.getSysPara("08017"));

		return "hrp/med/info/basic/inv/medInvMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvAddPage", method = RequestMethod.GET)
	public String medInvAddPage(Model mode) throws Exception {

		mode.addAttribute("p08001", MyConfig.getSysPara("08001"));
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/info/basic/inv/medInvAdd";
	}
	
	/**
	 * @Description 上传图片页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvPictureImportPage", method = RequestMethod.GET)
	public String medInvPictureImportPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		return "hrp/med/info/basic/inv/medInvPictureImport";
	}
	
	

	/**
	 * @Description 变更查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvDictMainPage", method = RequestMethod.GET)
	public String medInvDictMainPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("inv_code", mapVo.get("inv_code"));
		return "hrp/med/info/basic/inv/medInvDictMain";
	}

	/**
	 * @Description 变更查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvDictUpdatePage", method = RequestMethod.GET)
	public String medInvDictUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> medInvDict = medInvService.queryDictByCode(mapVo);
		if (medInvDict.get("sdate") != null
				&& !"".equals(medInvDict.get("sdate"))) {
			medInvDict.put("sdate", DateUtil.dateToString(
					(Date) medInvDict.get("sdate"), "yyyy-MM-dd"));
		}
		if (medInvDict.get("edate") != null
				&& !"".equals(medInvDict.get("edate"))) {
			medInvDict.put("edate", DateUtil.dateToString(
					(Date) medInvDict.get("edate"), "yyyy-MM-dd"));
		}
		if (medInvDict.get("bid_date") != null
				&& !"".equals(medInvDict.get("bid_date"))) {
			medInvDict.put("bid_date", DateUtil.dateToString(
					(Date) medInvDict.get("bid_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("medInvDict", medInvDict);
		
		
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		

		return "hrp/med/info/basic/inv/medInvDictUpdate";
	}

	/**
	 * @Description 添加数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/addMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInv(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 默认按批管理
		mapVo.put("is_batch", 1);
		mapVo.put("state", 0);
		// 处理日期
		if (mapVo.get("sdate") != null && !"".equals(mapVo.get("sdate"))) {
			mapVo.put("sdate", DateUtil.stringToDate(mapVo.get("sdate")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("edate") != null && !"".equals(mapVo.get("edate"))) {
			mapVo.put("edate", DateUtil.stringToDate(mapVo.get("edate")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("bid_date") != null && !"".equals(mapVo.get("bid_date"))) {
			mapVo.put("bid_date", DateUtil.stringToDate(mapVo.get("bid_date")
					.toString(), "yyyy-MM-dd"));
		}

		mapVo.put("spell_code",
				StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("inv_name").toString()));

		mapVo.put("alias_spell",
				StringTool.toPinyinShouZiMu(mapVo.get("alias").toString()));
		
		String medInvJson;
		try {
			
			
			medInvJson = medInvService.add(mapVo);
			
			//2017/03/23 将此处代码移动到了serviceImp中
			//Map<String, Object> medInv = medInvService.queryByCode(mapVo);
			
			/*MedStoreType medStoreType =   medStoreTypeService.queryMedStoreTypeByCode(mapVo);
			
			Map<String, Object> mapStore =new HashMap<String, Object>();
			
			mapStore.put("group_id",mapVo.get("group_id"));
			
			mapStore.put("hos_id",mapVo.get("hos_id"));
			
			mapStore.put("copy_code",mapVo.get("copy_code"));
			
			mapStore.put("store_id",medStoreType.getStore_id());
			
			mapStore.put("inv_id",medInv.get("inv_id"));
			
			medStoreInvService.addMedStoreInv(mapStore); */
			
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * @Description 添加数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/addMedInvSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedInvSup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medInvJson;
		try {
			medInvJson = medInvService.addMedInvSup(mapVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}
	/**
	 * 复制药品材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvCopyPage", method = RequestMethod.GET)
	public String medInvCopyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> medInv = medInvService.queryByCode(mapVo);

		if (medInv.get("sdate") != null && !"".equals(medInv.get("sdate"))) {
			medInv.put("sdate", DateUtil.dateToString(
					(Date) medInv.get("sdate"), "yyyy-MM-dd"));
		}
		if (medInv.get("edate") != null && !"".equals(medInv.get("edate"))) {
			medInv.put("edate", DateUtil.dateToString(
					(Date) medInv.get("edate"), "yyyy-MM-dd"));
		}
		if (medInv.get("bid_date") != null
				&& !"".equals(medInv.get("bid_date"))) {
			medInv.put("bid_date", DateUtil.dateToString(
					(Date) medInv.get("bid_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("medInv", medInv);
		
		mode.addAttribute("p08001", MyConfig.getSysPara("08001"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/info/basic/inv/medInvCopy";
	}
	/**
	 * @Description 更新跳转页面 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvUpdatePage", method = RequestMethod.GET)
	public String medInvUpdatePage(@RequestParam Map<String, Object> mapVo,	Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> medInv = medInvService.queryByCode(mapVo);

		if (medInv.get("sdate") != null && !"".equals(medInv.get("sdate"))) {
			medInv.put("sdate", DateUtil.dateToString(
					(Date) medInv.get("sdate"), "yyyy-MM-dd"));
		}
		if (medInv.get("edate") != null && !"".equals(medInv.get("edate"))) {
			medInv.put("edate", DateUtil.dateToString(
					(Date) medInv.get("edate"), "yyyy-MM-dd"));
		}
		if (medInv.get("bid_date") != null
				&& !"".equals(medInv.get("bid_date"))) {
			medInv.put("bid_date", DateUtil.dateToString(
					(Date) medInv.get("bid_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("medInv", medInv);
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/info/basic/inv/medInvUpdate";
	}

	/**
	 * @Description 更新数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/updateMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInv(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("is_batch", 1);
		// 处理日期
		if (mapVo.get("sdate") != null && !"".equals(mapVo.get("sdate"))) {
			mapVo.put("sdate", DateUtil.stringToDate(mapVo.get("sdate")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("edate") != null && !"".equals(mapVo.get("edate"))) {
			mapVo.put("edate", DateUtil.stringToDate(mapVo.get("edate")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("bid_date") != null && !"".equals(mapVo.get("bid_date"))) {
			mapVo.put("bid_date", DateUtil.stringToDate(mapVo.get("bid_date")
					.toString(), "yyyy-MM-dd"));
		}

		mapVo.put("spell_code",
				StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("inv_name").toString()));

		mapVo.put("alias_spell",
				StringTool.toPinyinShouZiMu(mapVo.get("alias").toString()));
		
		String medInvJson;
		try {
			medInvJson = medInvService.update(mapVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}
	/**
	 * @Description 批量更新跳转页面 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvUpdateBatchPage", method = RequestMethod.GET)
	public String medInvUpdateBatchPage(@RequestParam Map<String, Object> mapVo,	Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("inv_ids", mapVo.get("inv_ids"));
		
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/info/basic/inv/medInvUpdateBatch";
	}

	/**
	 * @Description 更新数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/updateMedInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedInvBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 处理日期
		if (mapVo.get("sdate") != null && !"".equals(mapVo.get("sdate"))) {
			mapVo.put("sdate", DateUtil.stringToDate(mapVo.get("sdate")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("edate") != null && !"".equals(mapVo.get("edate"))) {
			mapVo.put("edate", DateUtil.stringToDate(mapVo.get("edate")
					.toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("bid_date") != null && !"".equals(mapVo.get("bid_date"))) {
			mapVo.put("bid_date", DateUtil.stringToDate(mapVo.get("bid_date")
					.toString(), "yyyy-MM-dd"));
		}
		
		String medInvJson;
		try {
			medInvJson = medInvService.updateMedInvBatch(mapVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * @Description 删除数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/deleteMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInv(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("inv_id", ids[3]);
			listVo.add(mapVo);
		}
		
		String medInvJson;
		try {
			medInvJson = medInvService.deleteBatch(listVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * @Description 删除数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/auditMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMedInv(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("inv_id", ids[3]);

			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", new Date());
			listVo.add(mapVo);
		}

		String medInvJson;
		try {
			medInvJson = medInvService.auditMedInv(listVo);
		} catch (Exception e) {
			medInvJson = e.getMessage();
		}

		return JSONObject.parseObject(medInvJson);
	}

	/**
	 * @Description 查询数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInv(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String medInv = medInvService.query(getPage(mapVo));

		return JSONObject.parseObject(medInv);
	}

	/**
	 * @Description 查询数据 08105 药品材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		// 处理日期
		if (mapVo.get("begin_date") != null
				&& !"".equals(mapVo.get("begin_date"))) {
			mapVo.put("begin_date", DateUtil.stringToDate(
					mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if (mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))) {
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date")
					.toString(), "yyyy-MM-dd"));
		}
		String medInv = medInvService.queryDict(getPage(mapVo));

		return JSONObject.parseObject(medInv);
	}

	/**
	 * @Description 更新跳转页面 材料供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvSupUpdatePage", method = RequestMethod.GET)
	public String medInvSupUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("inv_code", mapVo.get("inv_code"));

		Map<String, Object> medInv = medInvService.queryByCode(mapVo);

		mode.addAttribute("inv_name", medInv.get("inv_name"));
		// mode.addAttribute("medInvSup", medInvService.queryMedInvSup(mapVo));

		return "hrp/med/info/basic/inv/medInvSupUpdate";
	}

	/**
	 * @Description 查询材料供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvSup(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medMsg = medInvService.queryMedInvSup(mapVo);

		return JSONObject.parseObject(medMsg);
	}

	/**
	 * @Description 供应商列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvSupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvSupList(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medMsg = medInvService.queryMedInvSupList(getPage(mapVo));

		return JSONObject.parseObject(medMsg);
	}
	
	/**
	 * @Description 不查停用材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvSupListDisable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvSupListDisable(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medMsg = medInvService.queryMedInvSupListDisable(getPage(mapVo));

		return JSONObject.parseObject(medMsg);
	}

	/**
	 * @Description 导入跳转页面 08105 药品材料表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvImportPage", method = RequestMethod.GET)
	public String medInvImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/inv/medInvImport";

	}

	/**
	 * @Description 导入跳转页面 08105 药品材料表供应商
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvSupImportPage", method = RequestMethod.GET)
	public String medInvImportVenPage(Model mode) throws Exception {

		return "hrp/med/info/basic/inv/medInvSupImport";

	}

	/**
	 * @Description 下载导入模版 08105 药品材料表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/info/basic/inv/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate", "08105药品字典导入模板.xlsx");

		return null;
	}

	/**
	 * @Description 下载导入模版 08105 药品材料表供应商
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/info/basic/inv/downTemplateSup", method = RequestMethod.GET)
	public String downTemplateSup(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "med\\downTemplate", "08105药品供应商.xls");

		return null;
	}
	
	
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvPicture", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvPicture(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInv = medInvPictureService.query(getPage(mapVo));

		return JSONObject.parseObject(medInv);
	}
	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvExist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvExist(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInv = medInvService.queryMedInvExist(mapVo);

		return JSONObject.parseObject(medInv);
	}
	/**
	 * @Description 删除数据 药品材料图片
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/deleteMedInvPicture", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedInvPicture(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode)
			throws Exception {
		String medInvJson = medInvPictureService.delete(mapVo);
		FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+mapVo.get("picture_url").toString()+"\\"+mapVo.get("picture_name").toString());
		String picture_name = mapVo.get("picture_name").toString().split("\\.")[0];
		String picture_type = mapVo.get("picture_name").toString().split("\\.")[1];
		String new_picture_name = picture_name+"_out."+picture_type;
		FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+mapVo.get("picture_url").toString()+"\\"+new_picture_name);
		return JSONObject.parseObject(medInvJson);
	}
	
	/**
	 * @Description 上传药品材料图片
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value="hrp/med/info/basic/inv/importInvPictureFile", method = RequestMethod.POST)  
	 public String importInvPictureFile(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,@RequestParam Map<String, Object> mapVo) throws IOException { 
		String result = "";
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String rPath = request.getSession().getServletContext().getRealPath("/");
		List<File> fileList = UploadUtil.upload(plupload, "medInvPicture",request, response);
		for(File file : fileList){
			String fileName = file.getName().split("\\.")[0];
			String fileType = file.getName().split("\\.")[1];
			String newFileName = fileName+"_out."+fileType;
			Images.zoomScale(rPath+"medInvPicture/"+file.getName(), rPath+"medInvPicture/"+newFileName, 180,180, Color.WHITE);
			mapVo.put("picture_name", file.getName());
			mapVo.put("picture_url", "medInvPicture");
				
			result = medInvPictureService.add(mapVo);
		}
		response.getWriter().print(result);
	    return null; 
	 }
	
	

	/**
	 * @Description 导入数据 08105 药品材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/readMedInvFiles", method = RequestMethod.POST)
	public String readMedInvFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		int para = Integer.valueOf(MyConfig.getSysPara("08001"));
		
		Map<String, Object> amortize_type = new HashMap<String, Object>();
		
		amortize_type.put("1", "1");
		
		amortize_type.put("2", "2");
		
		amortize_type.put("一次性摊销", "1");
		
		amortize_type.put("五五摊销", "2");
		
        Map<String, Object> price_type = new HashMap<String, Object>();
		
        price_type.put("0", "0");
		
        price_type.put("1", "1");
		
        price_type.put("2", "2");
		
        price_type.put("先进先出", "0");
        
        price_type.put("移动加权平均", "1");
		
        price_type.put("一次性加权平均", "2");
        
        Map<String, Object> state = new HashMap<String, Object>();
        
        state.put("0", "0");
		
        state.put("1", "1");
		
        state.put("否", "0");
		
        state.put("是", "1");
		
		
		/*----------------------查询计量单位---------------------------*/
		Map<String, Object> unitMapVo = new HashMap<String, Object>();

		unitMapVo.put("group_id", SessionManager.getGroupId());

		unitMapVo.put("hos_id", SessionManager.getHosId());

		unitMapVo.put("is_stop", "0");

		List<Unit> unit = unitMapper.queryUnit(unitMapVo);

		/*----------------------查询生产厂商---------------------------*/
		Map<String, Object> facDictMapVo = new HashMap<String, Object>();

		facDictMapVo.put("group_id", SessionManager.getGroupId());

		facDictMapVo.put("hos_id", SessionManager.getHosId());

		facDictMapVo.put("copy_code", SessionManager.getCopyCode());

		facDictMapVo.put("is_stop", "0");

		List<FacDict> FacDict = facDictMapper.queryFacDict(facDictMapVo);

		/*----------------------查询药品分类---------------------------*/
		Map<String, Object> MedTypeMapVo = new HashMap<String, Object>();

		MedTypeMapVo.put("group_id", SessionManager.getGroupId());

		MedTypeMapVo.put("hos_id", SessionManager.getHosId());

		MedTypeMapVo.put("copy_code", SessionManager.getCopyCode());

		MedTypeMapVo.put("is_stop", "0");

		List<MedTypeDict> MedTypeDict = medTypeDictMapper.queryMedTypeDict(MedTypeMapVo);
		/*----------------------查询供应商---------------------------*/
		Map<String, Object> supMapVo = new HashMap<String, Object>();

		supMapVo.put("group_id", SessionManager.getGroupId());

		supMapVo.put("hos_id", SessionManager.getHosId());

		supMapVo.put("copy_code", SessionManager.getCopyCode());

		supMapVo.put("is_stop", "0");

		List<SupDict> supDict = supDictMapper.querySupDict(supMapVo);
		
		/*----------------------查询药品属性---------------------------*/
		Map<String, Object> medSxMapVo = new HashMap<String, Object>();

		unitMapVo.put("group_id", SessionManager.getGroupId());

		unitMapVo.put("hos_id", SessionManager.getHosId());

		unitMapVo.put("is_stop", "0");

		List<MedSx> medSx = medInvMapper.queryMedSx(medSxMapVo);
		
		
		/*----------------------查询药品剂型---------------------------*/
		
		Map<String, Object> medJxMapVo = new HashMap<String, Object>();

		unitMapVo.put("group_id", SessionManager.getGroupId());

		unitMapVo.put("hos_id", SessionManager.getHosId());

		unitMapVo.put("is_stop", "0");

		List<MedJx> medJx = medInvMapper.queryMedJx(medJxMapVo);
		
		List<Map<String, Object>>  saveInvMaps = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> saveInvSupMaps = new ArrayList<Map<String, Object>>();

		List<MedInv> list_err = new ArrayList<MedInv>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			System.out.println(list.size());
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();

				MedInv medInv = new MedInv();

				String temp[] = list.get(i);// 行
				System.out.println("A"+i+" : " + temp[1]);

				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				Map<String, Object> invSupMap = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				invSupMap.put("group_id", SessionManager.getGroupId());

				invSupMap.put("hos_id", SessionManager.getHosId());

				invSupMap.put("copy_code", SessionManager.getCopyCode());

				if (validExceColunm(temp, 0)) {

					if(para == 1){
						
						mapVo.put("inv_code", "自动生成");
						
					}else if(para==0){
						
						mapVo.put("inv_code", temp[0]);
						
					}
					medInv.setInv_code(temp[0]);

				} else {

					err_sb.append("药品材料编码为空  ");
				}

				if (validExceColunm(temp, 1)) {

					medInv.setInv_name(temp[1]);

					mapVo.put("inv_name", temp[1]);
					
					invSupMap.put("inv_name", temp[1]);

				} else {

					err_sb.append("药品材料名称为空  ");

				}

				if (validExceColunm(temp, 2)) {

					medInv.setAlias(temp[2]);

					mapVo.put("alias", temp[2]);

					mapVo.put("alias_spell", StringTool.toPinyinShouZiMu(mapVo
							.get("alias").toString()));

				} else {

					medInv.setAlias(null);

					mapVo.put("alias", null);

				}

				boolean bMedType = false;

				if (validExceColunm(temp, 3)) {

					if (MedTypeDict.size() > 0) {
						for (MedTypeDict MedTypeDict1 : MedTypeDict) {

							if (temp[3].toString().equals(
									MedTypeDict1.getMed_type_code())) {

								mapVo.put("med_type_id",
										MedTypeDict1.getMed_type_id());

								mapVo.put("med_type_no",
										MedTypeDict1.getMed_type_no());

								bMedType = true;

								break;

							}

						}

					} else {

						err_sb.append("请先维护药品类别");
					}
					if (bMedType == false) {

						err_sb.append("药品类别编码不存在 ");

					}

					medInv.setMed_type_code(temp[3]);

					//medInv.setMed_type_name(temp[4]);

				} else {

					err_sb.append("药品类别编码为空 ");

				}

				if (validExceColunm(temp, 5)) {

					medInv.setInv_model(temp[5]);

					mapVo.put("inv_model", temp[5]);

				} else {

					medInv.setInv_model("");

					mapVo.put("inv_model", "");

				}

				boolean bUnit = false;
				if (validExceColunm(temp, 6)) {

					if (unit.size() > 0) {
						for (Unit unit1 : unit) {

							if (temp[6].toString().trim().equals(unit1.getUnit_name())) {

								mapVo.put("unit_code", unit1.getUnit_code());

								bUnit = true;

								break;

							}

						}
					} else {
						err_sb.append("请先维护计量单位");
					}
					if (bUnit == false) {

						err_sb.append("计量单位不存在");

					}

					medInv.setUnit_name(temp[6]);

				} else {

					medInv.setUnit_name("");

					mapVo.put("unit_code", "");

				}

				boolean bfac = false;

				if (validExceColunm(temp, 7)) {

					if (FacDict.size() > 0) {
						for (FacDict facDict1 : FacDict) {

							if (temp[7].toString().equals(
									facDict1.getFac_code())) {

								mapVo.put("fac_id", facDict1.getFac_id());
								
								mapVo.put("fac_no", facDict1.getFac_no());

								bfac = true;

								break;

							}

						}
					} else {

						err_sb.append("请先维护生产厂商");
					}

					if (bfac == false) {

						err_sb.append("生产厂商不存在");

					}

					medInv.setFac_code(temp[7]);

					medInv.setFac_name(temp[8]);

				} else {

					medInv.setFac_code(temp[7]);

					medInv.setFac_name(temp[8]);

					mapVo.put("fac_id", "");

				}

				if (validExceColunm(temp, 9)) {

					mapVo.put("amortize_type", amortize_type.get(temp[9]));
					
					medInv.setAmortize_type(Integer.parseInt(amortize_type.get(temp[9]).toString()));

				} else {

					mapVo.put("amortize_type","");

				}

				if (validExceColunm(temp, 10)) {

					mapVo.put("price_type", price_type.get(temp[10]));

					medInv.setPrice_type(Integer.parseInt(price_type.get(temp[10]).toString()));

				} else {

					err_sb.append("计价方法为空  ");

				}

				if (validExceColunm(temp, 11)) {

					medInv.setPlan_price(Double.parseDouble(temp[11]));

					mapVo.put("plan_price", temp[11]);

				} else {

					mapVo.put("plan_price", "");

				}

				if (validExceColunm(temp, 12)) {

					medInv.setPrice_rate(Double.parseDouble(temp[12]));

					mapVo.put("price_rate", temp[12]);

				} else {

					mapVo.put("price_rate", "");

				}

				if (validExceColunm(temp, 13)) {

					medInv.setSell_price(Double.parseDouble(temp[13]));

					mapVo.put("sell_price", temp[13]);

				} else {

					mapVo.put("sell_price", "");

				}

				boolean bsup = false;
				if (validExceColunm(temp, 14)) {

					if (supDict.size() > 0) {

						for (SupDict supDict1 : supDict) {

							if (temp[14].equals(supDict1.getSup_code())) {

								mapVo.put("sup_id", supDict1.getSup_id());

								bsup = true;

								break;
							}
						}

					} else {

						err_sb.append("请先维护供应商 ");
					}

					if (bsup == false) {

						err_sb.append("供应商编码不存在");

					}
					
					mapVo.put("sup_code", temp[14]);

				} else {

					mapVo.put("sup_code", "");

				}

				if (validExceColunm(temp, 15)) {

					mapVo.put("sup_name", temp[15]);

					
				} else {

					mapVo.put("sup_name", "");

				}

				if (validExceColunm(temp, 16)) {

					medInv.setIs_single_ven(Integer.parseInt(state.get(temp[16]).toString()));

					mapVo.put("is_single_ven", state.get(temp[16]));
					
				} else {

					err_sb.append("是否唯一供应商为空");

				}

				if (validExceColunm(temp, 17)) {

					medInv.setIs_batch(Integer.parseInt(state.get(temp[17]).toString()));

					mapVo.put("is_batch", state.get(temp[17]));

				} else {

					err_sb.append("是否批次管理为空  ");

				}

				if (validExceColunm(temp, 18)) {

					medInv.setIs_bar(Integer.parseInt(state.get(temp[18]).toString()));

					mapVo.put("is_bar", state.get(temp[18]));

				} else {

					err_sb.append("是否条码管理为空  ");

				}

				if (validExceColunm(temp, 19)) {

					medInv.setIs_per_bar(Integer.parseInt(state.get(temp[19]).toString()));

					mapVo.put("is_per_bar", state.get(temp[19]));

				} else {

					err_sb.append("是否个体条码为空  ");

				}

				if (validExceColunm(temp, 20)) {

					medInv.setIs_cert(Integer.parseInt(state.get(temp[20]).toString()));

					mapVo.put("is_cert", state.get(temp[20]));

				} else {

					err_sb.append("是否证件管理为空  ");

				}

				if (validExceColunm(temp, 21)) {

					medInv.setIs_quality(Integer.parseInt(state.get(temp[21]).toString()));

					mapVo.put("is_quality", state.get(temp[21]));

				} else {

					err_sb.append("是否保质期管理为空  ");

				}

				if (validExceColunm(temp, 22)) {

					medInv.setIs_disinfect(Integer.parseInt(state.get(temp[22]).toString()));

					mapVo.put("is_disinfect", state.get(temp[22]));

				} else {

					mapVo.put("is_disinfect", null);

				}

				if (validExceColunm(temp, 23)) {

					medInv.setIs_com(Integer.parseInt(state.get(temp[23]).toString()));

					mapVo.put("is_com", state.get(temp[23]));

				} else {

					mapVo.put("is_com",null);

				}

				if (validExceColunm(temp, 24)) {

					medInv.setIs_dura(Integer.parseInt(state.get(temp[24]).toString()));

					mapVo.put("is_dura", state.get(temp[24]));

				} else {

					mapVo.put("is_dura",null);

				}

				if (validExceColunm(temp, 25)) {

					medInv.setIs_highvalue(Integer.parseInt(state.get(temp[25]).toString()));

					mapVo.put("is_highvalue", state.get(temp[25]));

				} else {

					mapVo.put("is_highvalue", null);

				}

				if (validExceColunm(temp, 26)) {

					medInv.setIs_charge(Integer.parseInt(state.get(temp[26]).toString()));

					mapVo.put("is_charge", state.get(temp[26]));

				} else {

					mapVo.put("is_charge", null);

				}

				if (validExceColunm(temp, 27)) {

					medInv.setIs_sec_whg(Integer.parseInt(state.get(temp[27]).toString()));

					mapVo.put("is_sec_whg", state.get(temp[27]));

				} else {

					err_sb.append("是否作二级库管理为空  ");

				}

				if (validExceColunm(temp, 28)) {

					medInv.setIs_shel_make(Integer.parseInt(state.get(temp[28]).toString()));

					mapVo.put("is_shel_make", state.get(temp[28]));

				} else {

					mapVo.put("is_shel_make",null);
					//err_sb.append("是否自制品为空  ");

				}

				if (validExceColunm(temp, 29)) {

					medInv.setSdate(DateUtil.stringToDate(temp[29],"yyyy-MM-dd"));

					mapVo.put("sdate", DateUtil.stringToDate(temp[29].toString(), "yyyy-MM-dd"));

				} else {

					medInv.setSdate(null);

					mapVo.put("sdate", null);

				}

				if (validExceColunm(temp, 30)) {

					medInv.setEdate(DateUtil.stringToDate(temp[30],"yyyy-MM-dd"));

					mapVo.put("edate", DateUtil.stringToDate(temp[30].toString(), "yyyy-MM-dd"));

				} else {

					medInv.setEdate(null);

					mapVo.put("edate", null);

				}

				if (validExceColunm(temp, 31)) {

					medInv.setBar_code_new(temp[31]);

					mapVo.put("bar_code_new", temp[31]);

				} else {

					medInv.setBar_code_new("");

					mapVo.put("bar_code_new", "");

					// err_sb.append("品种条码为空  ");

				}

				if (validExceColunm(temp, 32)) {

					medInv.setAbc_type(temp[32]);

					mapVo.put("abc_type", temp[32]);

				} else {

					medInv.setAbc_type("");

					mapVo.put("abc_type", "");
					// err_sb.append("ABC分类为空 ");

				}

				if (validExceColunm(temp, 33)) {

					medInv.setPer_weight(Double.parseDouble(temp[33]));

					mapVo.put("per_weight", temp[33]);

				} else {

					mapVo.put("per_weight", null);

					// err_sb.append("单位重量为空  ");

				}

				if (validExceColunm(temp, 34)) {

					medInv.setPer_volum(Double.parseDouble(temp[34]));

					mapVo.put("per_volum", temp[34]);

				} else {

					// medInv.setPer_volum("");

					mapVo.put("per_volum", null);

					// err_sb.append("单位体积为空  ");

				}

				if (validExceColunm(temp, 35)) {

					medInv.setBrand_name(temp[35]);

					mapVo.put("brand_name", temp[35]);

				} else {

					medInv.setBrand_name("");

					mapVo.put("brand_name", "");
					// err_sb.append("品牌为空  ");

				}

				if (validExceColunm(temp, 36)) {

					medInv.setAgent_name(temp[36]);

					mapVo.put("agent_name", temp[36]);

				} else {

					medInv.setAgent_name("");

					mapVo.put("agent_name", "");

					// err_sb.append("代理商为空  ");

				}

				if (validExceColunm(temp, 37)) {

					medInv.setInv_structure(temp[37]);

					mapVo.put("inv_structure", temp[37]);

				} else {

					medInv.setInv_structure("");

					mapVo.put("inv_structure", "");
					// err_sb.append("材料结构为空  ");

				}

				if (validExceColunm(temp, 38)) {

					medInv.setInv_usage(temp[38]);

					mapVo.put("inv_usage", temp[38]);

				} else {

					medInv.setInv_usage("");

					mapVo.put("inv_usage", "");

					// err_sb.append("材料用途为空  ");

				}

				if (validExceColunm(temp, 39)) {

					medInv.setUse_state(Integer.parseInt(state.get(temp[39]).toString()));

					mapVo.put("use_state", state.get(temp[39]));

				} else {

					err_sb.append("是否在用为空  ");

				}

				if (validExceColunm(temp, 40)) {

					medInv.setNote(temp[40]);

					mapVo.put("note", temp[40]);

				} else {

					medInv.setNote("");

					mapVo.put("note", "");

					// err_sb.append("备注为空  ");
				}

				// 审核状态
				mapVo.put("state", "0");

				if (validExceColunm(temp, 41)) {

					medInv.setIs_bid(Integer.parseInt(state.get(temp[41]).toString()));

					mapVo.put("is_bid", state.get(temp[41]));
				} else {

					//medInv.setIs_bid(null);

					mapVo.put("is_bid", null);

					// err_sb.append("是否中标为空  ");

				}

				if (validExceColunm(temp, 42)) {

					medInv.setBid_date(DateUtil.stringToDate(temp[42],
							"yyyy-MM-dd"));

					mapVo.put("bid_date", DateUtil.stringToDate(
							temp[42].toString(), "yyyy-MM-dd"));

				} else {

					medInv.setBid_date(null);

					mapVo.put("bid_date", null);

				}

				if (validExceColunm(temp, 43)) {

					medInv.setBid_code(temp[43]);

					mapVo.put("bid_code", temp[43]);

				} else {

					medInv.setBid_code("");

					mapVo.put("bid_code", "");

				}

				if (validExceColunm(temp, 44)) {

					medInv.setIs_involved(Integer.parseInt(state.get(temp[44]).toString()));

					mapVo.put("is_involved", state.get(temp[44]));

				} else {

					//medInv.setIs_involved(null);

					mapVo.put("is_involved", null);

				}
				if (validExceColunm(temp, 45)) {

					medInv.setIs_implant(Integer.parseInt(state.get(temp[45]).toString()));

					mapVo.put("is_implant", state.get(temp[19]));
				} else {

					//medInv.setIs_implant(null);

					mapVo.put("is_implant", null);

				}

				if (validExceColunm(temp, 46)) {

					medInv.setStora_tran_cond(temp[46]);

					mapVo.put("stora_tran_cond", temp[46]);

				} else {

					//medInv.setStora_tran_cond(null);

					mapVo.put("stora_tran_cond", null);

				}

				if (validExceColunm(temp, 47)) {

					medInv.setIs_zero_store(Integer.parseInt(state.get(temp[47]).toString()));

					mapVo.put("is_zero_store", state.get(temp[47]));

				} else {

					//medInv.setIs_zero_store(null);

					mapVo.put("is_zero_store", null);

				}
				
				if (validExceColunm(temp, 48)) {

					medInv.setIs_inv_bar(Integer.parseInt(state.get(temp[48]).toString()));

					mapVo.put("is_inv_bar", state.get(temp[48]));

				} else {

					//medInv.setIs_inv_bar(null);

					mapVo.put("is_inv_bar", null);

				}
				boolean bMedSx = false;
				if (validExceColunm(temp, 49)) {

					if (medSx.size() > 0) {
						for (MedSx medSx1 : medSx) {

							if (temp[49].toString().trim().equals(medSx1.getMed_sx_name())) {

								mapVo.put("med_sx_id", medSx1.getMed_sx_id());
								mapVo.put("med_sx_name", temp[49].toString());
								medInv.setMed_sx_id(Integer.parseInt(medSx1.getMed_sx_id()));
								bMedSx = true;

								break;

							}

						}
					} else {
						err_sb.append("请先维护药品属性");
					}
					if (bMedSx == false) {

						err_sb.append("药品属性不存在");

					}

				} else {
					mapVo.put("med_sx_id", "");
				}
				
				boolean bMedJx = false;
				if (validExceColunm(temp, 50)) {

					if (medJx.size() > 0) {
						for (MedJx medJx1 : medJx) {

							if (temp[50].toString().trim().equals(medJx1.getMed_jx_name())) {

								mapVo.put("med_jx_id", medJx1.getMed_jx_id());
								mapVo.put("med_jx_name", temp[50].toString());
								medInv.setMed_jx_id(Integer.parseInt(medJx1.getMed_jx_id()));
								bMedJx = true;

								break;

							}

						}
					} else {
						err_sb.append("请先维护药品剂型");
					}
					if (bMedJx == false) {

						err_sb.append("药品剂型不存在");

					}

				} else {
					mapVo.put("med_jx_id", "");
				}
				
				//病区拆零系数
				if (validExceColunm(temp, 51)) {

					medInv.setBqplxs(Integer.parseInt(temp[51]));

					mapVo.put("bqplxs",temp[51]);

				} else {

					medInv.setBqplxs(null);

					mapVo.put("bqplxs", null);

				}
				//门诊拆零系数
				
				if (validExceColunm(temp, 52)) {

					medInv.setMzplxs(Integer.parseInt(temp[52]));

					mapVo.put("mzplxs",temp[52]);

				} else {

					medInv.setMzplxs(null);

					mapVo.put("mzplxs", null);

				}
				

				Map<String, Object> excDictMapVo = new HashMap<String, Object>();

				excDictMapVo.put("group_id", SessionManager.getGroupId());

				excDictMapVo.put("hos_id", SessionManager.getHosId());

				excDictMapVo.put("copy_code", SessionManager.getCopyCode());

				if (err_sb.toString().length() > 0) {

					medInv.setError_type(err_sb.toString());

					list_err.add(medInv);
					
					break;

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));

					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("inv_name").toString()));
					
					saveInvMaps.add(mapVo);
					

				}

			}
			//if(list_err.size() == 0){
				//20170523--hsy--修改为批量保存替换老的循环调用service的方式
				medInvService.addImpBatch(saveInvMaps);
			//}
			/*----老方法--循环调用services
			for(Map<String, Object> map :saveInvMaps){
				
				if(map.get("sup_id")!=null){
					
					Map<String, Object> supMap = new HashMap<String, Object>();
				
					supMap.put("group_id", SessionManager.getGroupId());

					supMap.put("hos_id", SessionManager.getHosId());

					supMap.put("copy_code", SessionManager.getCopyCode());
					
					medInvService.addimp(map);
					
					supMap.put("inv_id", medInvMapper.queryMedInvSeqCur());
					supMap.put("sup_id", map.get("sup_id"));
					//20170310增加导入默认供应商字段,解决导入少是否默认字段导致的报错问题;
					supMap.put("is_default", 1);
					Map<String, Object> MedInvSupbyCode = medInvMapper.queryMedInvSupbyCode(supMap);
					
					if(MedInvSupbyCode == null){

						saveInvSupMaps.add(supMap);
					}
					
				}else {
					
					medInvService.addimp(map);
					
				}
				
			}
			
			PageModel pm = new PageModel(saveInvSupMaps, 2000);

			int pagenum = 0;
			
			if (saveInvSupMaps.size() % 2000 == 0) {

				pagenum = saveInvSupMaps.size() / 2000;

			} else {

				pagenum = (saveInvSupMaps.size() / 2000) + 1;
			}
			
			
			for (int j = 1; j <= pagenum; j++) {
				
				List<Map<String, Object>> list1 = pm.getObjects(j);
				
				for (Map<String, Object> map :list1) {
					
					System.out.println("map = " + map );
				}
				
				medInvMapper.addMedInvSup(list1);
			}
		*/


		} catch (DataAccessException e) {

			e.printStackTrace();
			System.out.println("==="+e);
			MedInv data_exc = new MedInv();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 08105 药品材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/addBatchMedInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedInv(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		int para = Integer.valueOf(MyConfig.getSysPara("08001"));
		
		Map<String, Object> unitMapVo = new HashMap<String, Object>();

		unitMapVo.put("group_id", SessionManager.getGroupId());

		unitMapVo.put("hos_id", SessionManager.getHosId());

		unitMapVo.put("is_stop", "0");

		List<Unit> unit = unitMapper.queryUnit(unitMapVo);

		Map<String, Object> facDictMapVo = new HashMap<String, Object>();

		facDictMapVo.put("group_id", SessionManager.getGroupId());

		facDictMapVo.put("hos_id", SessionManager.getHosId());

		facDictMapVo.put("copy_code", SessionManager.getCopyCode());

		facDictMapVo.put("is_stop", "0");

		List<FacDict> facDict = facDictMapper.queryFacDict(facDictMapVo);

		Map<String, Object> MedTypeMapVo = new HashMap<String, Object>();

		MedTypeMapVo.put("group_id", SessionManager.getGroupId());

		MedTypeMapVo.put("hos_id", SessionManager.getHosId());

		MedTypeMapVo.put("copy_code", SessionManager.getCopyCode());

		MedTypeMapVo.put("is_stop", "0");

		List<Map<String, Object>>  saveInvMaps = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> saveInvSupMaps = new ArrayList<Map<String, Object>>();

		List<MedTypeDict> medTypeDict = medTypeDictMapper.queryMedTypeDict(MedTypeMapVo);

		List<MedInv> list_err = new ArrayList<MedInv>();

		JSONArray json = JSONArray.parseArray(paramVo);

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

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				MedInv medInv = new MedInv();

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				if (StringTool.isNotBlank(jsonObj.get("inv_code"))) {

					if(para ==1){
						
						mapVo.put("inv_code", "自动生成");
						
					}else if(para == 0){
						
						mapVo.put("inv_code", jsonObj.get("inv_code").toString());
					}
					medInv.setInv_code(jsonObj.get("inv_code").toString());

					

				} else {

					err_sb.append("药品材料编码为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("inv_name"))) {

					medInv.setInv_name(jsonObj.get("inv_name").toString());

					mapVo.put("inv_name", jsonObj.get("inv_name").toString());

				} else {

					err_sb.append("药品材料名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("alias"))) {

					medInv.setAlias(jsonObj.get("alias").toString());

					mapVo.put("alias", jsonObj.get("alias").toString());

					mapVo.put("alias_spell", StringTool.toPinyinShouZiMu(mapVo
							.get("alias").toString()));

				} else {

					medInv.setAlias("");

					mapVo.put("alias", "");

				}

				boolean bmedTypeDict = false;
				if (StringTool.isNotBlank(jsonObj.get("med_type_code"))) {

					for (MedTypeDict medTypeDict1 : medTypeDict) {

						if (jsonObj.get("med_type_code").toString()
								.equals(medTypeDict1.getMed_type_code())) {

							mapVo.put("med_type_id",
									medTypeDict1.getMed_type_id());

							mapVo.put("med_type_no",
									medTypeDict1.getMed_type_no());

							bmedTypeDict = true;

							break;

						}

					}

					if (bmedTypeDict == false) {

						err_sb.append("药品类别编码不存在 ");
					}

					medInv.setMed_type_code(jsonObj.get("med_type_code")
							.toString());

					medInv.setMed_type_name(jsonObj.get("med_type_name")
							.toString());

				} else {

					err_sb.append("药品类别编码为空 ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_model"))) {

					medInv.setInv_model(jsonObj.get("inv_model").toString());

					mapVo.put("inv_model", jsonObj.get("inv_model").toString());

				} else {

					medInv.setInv_model("");

					mapVo.put("inv_model", "");

				}
				boolean bunit = false;
				if (StringTool.isNotBlank(jsonObj.get("unit_code"))) {

					for (Unit unit1 : unit) {

						if (jsonObj.get("unit_code").toString()
								.equals(unit1.getUnit_code())) {

							mapVo.put("unit_code", jsonObj.get("unit_code"));

							bunit = true;

							break;
						}

					}

					if (bunit == false) {

						err_sb.append("计量单位不存在");
					}

					medInv.setUnit_code(jsonObj.get("unit_code").toString());

					medInv.setUnit_name(jsonObj.get("unit_name").toString());

				} else {

					medInv.setUnit_code("");

					medInv.setUnit_name("");

					mapVo.put("unit_code", "");

				}

				boolean bfacDict = false;
				if (StringTool.isNotBlank(jsonObj.get("fac_code"))) {

					for (FacDict facDict1 : facDict) {

						if (jsonObj.get("fac_code").toString()
								.equals(facDict1.getFac_code())) {

							mapVo.put("fac_id", facDict1.getFac_id());
							bfacDict = true;

							break;

						}
					}

					if (bfacDict == false) {

						err_sb.append("生产厂商不存在");
					}

					medInv.setFac_code(jsonObj.get("fac_code").toString());

					medInv.setFac_name(jsonObj.get("fac_name").toString());

				} else {

					medInv.setFac_code("");

					medInv.setFac_name("");

					mapVo.put("fac_id", "");

				}

				if (StringTool.isNotBlank(jsonObj.get("amortize_type"))) {

					if (Integer.parseInt(jsonObj.get("amortize_type")
							.toString()) == 1
							|| Integer.parseInt(jsonObj.get("amortize_type")
									.toString()) == 2) {

						mapVo.put("amortize_type", Integer.parseInt(jsonObj
								.get("amortize_type").toString()));

					} else {

						err_sb.append("摊销方式不存在");
					}

					medInv.setAmortize_type(Integer.parseInt(jsonObj.get(
							"amortize_type").toString()));

				} else {

					err_sb.append("摊销方式为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("price_type"))) {

					if (Integer.parseInt(jsonObj.get("price_type").toString()) == 0
							|| Integer.parseInt(jsonObj.get("price_type")
									.toString()) == 1
							|| Integer.parseInt(jsonObj.get("price_type")
									.toString()) == 2) {

						mapVo.put("price_type", Integer.parseInt(jsonObj.get(
								"price_type").toString()));

					} else {

						err_sb.append("计价方法不存在 ");
					}

					medInv.setPrice_type(Integer.parseInt(jsonObj.get(
							"price_type").toString()));

				} else {

					err_sb.append("计价方法为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("plan_price"))) {

					medInv.setPlan_price(Double.parseDouble(jsonObj.get(
							"plan_price").toString()));

					mapVo.put("plan_price", Double.parseDouble(jsonObj.get(
							"plan_price").toString()));

				} else {

					medInv.setPlan_price(null);

					mapVo.put("plan_price", null);

				}

				if (StringTool.isNotBlank(jsonObj.get("price_rate"))) {

					medInv.setPrice_rate(Double.parseDouble(jsonObj.get(
							"price_rate").toString()));

					mapVo.put("price_rate", Double.parseDouble(jsonObj.get(
							"price_rate").toString()));

				} else {

					medInv.setPrice_rate(null);

					mapVo.put("price_rate", null);

				}
				if (StringTool.isNotBlank(jsonObj.get("sell_price"))) {

					medInv.setSell_price(Double.parseDouble(jsonObj.get(
							"sell_price").toString()));

					mapVo.put("sell_price", Double.parseDouble(jsonObj.get(
							"sell_price").toString()));

				} else {

					medInv.setSell_price(null);

					mapVo.put("sell_price", null);

				}

				if (StringTool.isNotBlank(jsonObj.get("is_single_ven"))) {

					medInv.setIs_single_ven(Integer.parseInt(jsonObj.get(
							"is_single_ven").toString()));

					mapVo.put("is_single_ven", Integer.parseInt(jsonObj.get(
							"is_single_ven").toString()));

				} else {

					err_sb.append("是否唯一供应商为空");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_batch"))) {

					medInv.setIs_batch(Integer.parseInt(jsonObj.get("is_batch")
							.toString()));

					mapVo.put("is_batch", Integer.parseInt(jsonObj.get(
							"is_batch").toString()));

				} else {

					err_sb.append("是否批次管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_bar"))) {

					medInv.setIs_bar(Integer.parseInt(jsonObj.get("is_bar")
							.toString()));

					mapVo.put("is_bar",
							Integer.parseInt(jsonObj.get("is_bar").toString()));

				} else {

					err_sb.append("是否条码管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_per_bar"))) {

					medInv.setIs_per_bar(Integer.parseInt(jsonObj.get(
							"is_per_bar").toString()));

					mapVo.put("is_per_bar", Integer.parseInt(jsonObj.get(
							"is_per_bar").toString()));

				} else {

					err_sb.append("是否个体条码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_cert"))) {

					medInv.setIs_cert(Integer.parseInt(jsonObj.get("is_cert")
							.toString()));

					mapVo.put("is_cert",
							Integer.parseInt(jsonObj.get("is_cert").toString()));

				} else {

					err_sb.append("是否证件管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_quality"))) {

					medInv.setIs_quality(Integer.parseInt(jsonObj.get(
							"is_quality").toString()));

					mapVo.put("is_quality", Integer.parseInt(jsonObj.get(
							"is_quality").toString()));

				} else {

					err_sb.append("是否保质期管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_disinfect"))) {

					medInv.setIs_disinfect(Integer.parseInt(jsonObj.get(
							"is_disinfect").toString()));

					mapVo.put("is_disinfect", Integer.parseInt(jsonObj.get(
							"is_disinfect").toString()));

				} else {

					err_sb.append("是否灭菌材料为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_com"))) {

					medInv.setIs_com(Integer.parseInt(jsonObj.get("is_com")
							.toString()));

					mapVo.put("is_com",
							Integer.parseInt(jsonObj.get("is_com").toString()));

				} else {

					err_sb.append("是否代销品为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_dura"))) {

					medInv.setIs_dura(Integer.parseInt(jsonObj.get("is_dura")
							.toString()));

					mapVo.put("is_dura",
							Integer.parseInt(jsonObj.get("is_dura").toString()));

				} else {

					err_sb.append("是否为耐用品为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_highvalue"))) {

					medInv.setIs_highvalue(Integer.parseInt(jsonObj.get(
							"is_highvalue").toString()));

					mapVo.put("is_highvalue", Integer.parseInt(jsonObj.get(
							"is_highvalue").toString()));

				} else {

					err_sb.append("是否高值为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_charge"))) {

					medInv.setIs_charge(Integer.parseInt(jsonObj.get(
							"is_charge").toString()));

					mapVo.put("is_charge", Integer.parseInt(jsonObj.get(
							"is_charge").toString()));

				} else {

					err_sb.append("是否收费为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_sec_whg"))) {

					medInv.setIs_sec_whg(Integer.parseInt(jsonObj.get(
							"is_sec_whg").toString()));

					mapVo.put("is_sec_whg", Integer.parseInt(jsonObj.get(
							"is_sec_whg").toString()));

				} else {

					err_sb.append("是否作二级库管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_shel_make"))) {

					medInv.setIs_shel_make(Integer.parseInt(jsonObj.get(
							"is_shel_make").toString()));

					mapVo.put("is_shel_make", Integer.parseInt(jsonObj.get(
							"is_shel_make").toString()));

				} else {

					err_sb.append("是否自制品为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sdate"))) {

					medInv.setSdate(DateUtil.stringToDate(jsonObj.get("sdate")
							.toString(), "yyyy-MM-dd"));

					mapVo.put("sdate", DateUtil.stringToDate(
							jsonObj.get("sdate").toString(), "yyyy-MM-dd"));

				} else {

					medInv.setSdate(null);

					mapVo.put("sdate", null);

				}

				if (StringTool.isNotBlank(jsonObj.get("edate"))) {

					medInv.setEdate(DateUtil.stringToDate(jsonObj.get("edate")
							.toString(), "yyyy-MM-dd"));

					mapVo.put("edate", DateUtil.stringToDate(
							jsonObj.get("edate").toString(), "yyyy-MM-dd"));

				} else {

					medInv.setEdate(null);

					mapVo.put("edate", null);

				}

				// /////////////////////////////////////////////////////////////////////////
				if (StringTool.isNotBlank(jsonObj.get("bar_code_new"))) {

					medInv.setBar_code_new(jsonObj.get("bar_code_new")
							.toString());

					mapVo.put("bar_code_new", jsonObj.get("bar_code_new")
							.toString());

				} else {

					medInv.setBar_code_new("");

					mapVo.put("bar_code_new", "");
					// err_sb.append("品种条码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("abc_type"))) {

					medInv.setAbc_type(jsonObj.get("abc_type").toString());

					mapVo.put("abc_type", jsonObj.get("abc_type").toString());

				} else {

					medInv.setAbc_type("");

					mapVo.put("abc_type", "");
					// err_sb.append("ABC分类为空 ");

				}

				if (StringTool.isNotBlank(jsonObj.get("per_weight"))) {

					medInv.setPer_weight(Double.parseDouble(jsonObj.get(
							"per_weight").toString()));

					mapVo.put("per_weight", Double.parseDouble(jsonObj.get(
							"per_weight").toString()));

				} else {

					// medInv.setPer_weight("");

					mapVo.put("per_weight", "");
					// err_sb.append("单位重量为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("per_volum"))) {

					medInv.setPer_volum(Double.parseDouble(jsonObj.get(
							"per_volum").toString()));

					mapVo.put("per_volum", Double.parseDouble(jsonObj.get(
							"per_volum").toString()));

				} else {

					medInv.setPer_volum(null);

					mapVo.put("per_volum", null);

					// err_sb.append("单位体积为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("brand_name"))) {

					medInv.setBrand_name(jsonObj.get("brand_name").toString());

					mapVo.put("brand_name", jsonObj.get("brand_name")
							.toString());

				} else {

					medInv.setBrand_name("");

					mapVo.put("brand_name", "");
					// err_sb.append("品牌为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("agent_name"))) {

					medInv.setAgent_name(jsonObj.get("agent_name").toString());

					mapVo.put("agent_name", jsonObj.get("agent_name")
							.toString());

				} else {

					medInv.setAgent_name("");

					mapVo.put("agent_name", "");

					// err_sb.append("代理商为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_structure"))) {

					medInv.setInv_structure(jsonObj.get("inv_structure")
							.toString());

					mapVo.put("inv_structure", jsonObj.get("inv_structure")
							.toString());

				} else {

					medInv.setInv_structure("");

					mapVo.put("inv_structure", "");
					// err_sb.append("材料结构为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_usage"))) {

					medInv.setInv_usage(jsonObj.get("inv_usage").toString());

					mapVo.put("inv_usage", jsonObj.get("inv_usage").toString());

				} else {

					medInv.setInv_usage("");

					mapVo.put("inv_usage", "");

					// err_sb.append("材料用途为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("use_state"))) {

					medInv.setUse_state(Integer.parseInt(jsonObj.get(
							"use_state").toString()));

					mapVo.put("use_state", Integer.parseInt(jsonObj.get(
							"use_state").toString()));

				} else {

					err_sb.append("是否在用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					medInv.setNote(jsonObj.get("note").toString());

					mapVo.put("note", jsonObj.get("note").toString());

				} else {

					medInv.setNote("");

					mapVo.put("note", "");

					// err_sb.append("备注为空  ");
				}

				// 审核状态
				mapVo.put("state", "0");

				if (StringTool.isNotBlank(jsonObj.get("is_bid"))) {

					medInv.setIs_bid(Integer.parseInt(jsonObj.get("is_bid")
							.toString()));

					mapVo.put("is_bid",
							Integer.parseInt(jsonObj.get("is_bid").toString()));

				} else {

					medInv.setIs_bid(null);

					mapVo.put("is_bid", null);

					// err_sb.append("是否中标为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_date"))) {

					medInv.setBid_date(DateUtil.stringToDate(
							jsonObj.get("bid_date").toString(), "yyyy-MM-dd"));

					mapVo.put("bid_date", DateUtil.stringToDate(
							jsonObj.get("bid_date").toString(), "yyyy-MM-dd"));

				} else {

					medInv.setBid_date(null);

					mapVo.put("bid_date", null);

					// err_sb.append("中标日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_code"))) {

					medInv.setBid_code(jsonObj.get("bid_code").toString());

					mapVo.put("bid_code", jsonObj.get("bid_code").toString());

				} else {

					medInv.setBid_code("");

					mapVo.put("bid_code", "");

					// err_sb.append("项目中标编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_involved"))) {

					medInv.setIs_involved(Integer.parseInt(jsonObj.get(
							"is_involved").toString()));

					mapVo.put("is_involved", jsonObj.get("is_involved")
							.toString());
				} else {

					medInv.setIs_involved(null);

					mapVo.put("is_involved", null);

					// err_sb.append("是否介入为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_implant"))) {

					medInv.setIs_implant(Integer.parseInt(jsonObj.get(
							"is_implant").toString()));

					mapVo.put("is_implant", jsonObj.get("is_implant")
							.toString());
				} else {

					medInv.setIs_implant(null);

					mapVo.put("is_implant", null);

					// err_sb.append("是否植入为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("stora_tran_cond"))) {

					medInv.setStora_tran_cond(jsonObj.get("stora_tran_cond")
							.toString());

					mapVo.put("stora_tran_cond", jsonObj.get("stora_tran_cond")
							.toString());

				} else {

					medInv.setStora_tran_cond(null);

					mapVo.put("stora_tran_cond", null);

					// err_sb.append("储运条件为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_zero_store"))) {

					medInv.setIs_zero_store(Integer.parseInt(jsonObj.get(
							"is_zero_store").toString()));

					mapVo.put("c", Integer.parseInt(jsonObj
							.get("is_zero_store").toString()));

				} else {

					medInv.setIs_zero_store(null);

					mapVo.put("is_zero_store", null);

					// err_sb.append("是否零库存管理为空  ");
				}
				
				if (StringTool.isNotBlank(jsonObj.get("is_inv_bar"))) {

					medInv.setIs_inv_bar(Integer.parseInt(jsonObj.get(
							"is_inv_bar").toString()));

					mapVo.put("c", Integer.parseInt(jsonObj
							.get("is_inv_bar").toString()));

				} else {

					medInv.setIs_inv_bar(null);

					mapVo.put("is_inv_bar", null);

				}

				Map<String, Object> excDictMapVo = new HashMap<String, Object>();

				excDictMapVo.put("group_id", SessionManager.getGroupId());

				excDictMapVo.put("hos_id", SessionManager.getHosId());

				excDictMapVo.put("copy_code", SessionManager.getCopyCode());

				if (!("自动生成").equals(mapVo.get("inv_code").toString())) {

					if (mapVo.get("inv_code") != null) {

						excDictMapVo.put("inv_code", mapVo.get("inv_code")
								.toString());

						Map<String, Object> data_exc_extis = medInvService
								.queryByUniqueness(excDictMapVo);

						if (data_exc_extis != null) {

							err_sb.append("编码已经存在");

						}

					}
				}
				if (err_sb.toString().length() > 0) {

					medInv.setError_type(err_sb.toString());

					list_err.add(medInv);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo
							.get("inv_name").toString()));

					mapVo.put("wbx_code",
							StringTool.toWuBi(mapVo.get("inv_name").toString()));



				}

			}
			
	            for(Map<String, Object> map :saveInvMaps){
				
				if(map.get("sup_id")!=null){
					
					medInvService.add(map);
					
					map.put("inv_id", medInvMapper.queryMedInvSeqCur());
					
					saveInvSupMaps.add(map);
					
					
				}else {
					
					medInvService.add(map);
					
				}
				
			}
			
			PageModel pm = new PageModel(saveInvSupMaps, 2000);

			int pagenum = 0;
			
			if (saveInvSupMaps.size() % 2000 == 0) {

				pagenum = saveInvSupMaps.size() / 2000;

			} else {

				pagenum = (saveInvSupMaps.size() / 2000) + 1;
			}
			
			
			for (int j = 1; j <= pagenum; j++) {
				
				List<Map<String, Object>> list1 = pm.getObjects(j);
				
				medInvMapper.addMedInvSup(list1);
			}
		

		} catch (DataAccessException e) {

			MedInv data_exc = new MedInv();

			list_err.add(data_exc);

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

	/**
	 * @Description 导入数据 08105 药品材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/readMedInvSupFiles", method = RequestMethod.POST)
	public String readMedInvVenFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<Map<String, Object>> supList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> supList1 = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		PageModel pm = new PageModel(list, 2000);

		int pagenum = 0;

		if (list.size() % 2000 == 0) {

			pagenum = list.size() / 2000;

		} else {

			pagenum = (list.size() / 2000) + 1;
		}

		try {

			for (int j = 1; j <= pagenum; j++) {

				List<String[]> list1 = pm.getObjects(j);

				for (int i = 1; i < list1.size(); i++) {

					StringBuffer err_sb = new StringBuffer();

					Map<String, Object> error_mapVo = new HashMap<String, Object>();

					String temp[] = list1.get(i);// 行

					Map<String, Object> mapVo = new HashMap<String, Object>();

					mapVo.put("group_id", SessionManager.getGroupId());

					mapVo.put("hos_id", SessionManager.getHosId());

					mapVo.put("copy_code", SessionManager.getCopyCode());

					if (StringTool.isNotBlank(temp[0])) {

						Map<String, Object> invMapVo = new HashMap<String, Object>();

						invMapVo.put("group_id", SessionManager.getGroupId());

						invMapVo.put("hos_id", SessionManager.getHosId());

						invMapVo.put("copy_code", SessionManager.getCopyCode());

						invMapVo.put("inv_code", temp[0].toString());

						Map<String, Object> med_inv = medInvService
								.queryByUniqueness(invMapVo);

						if (med_inv != null) {

							mapVo.put("inv_id", med_inv.get("inv_id"));

						} else {

							err_sb.append("药品材料编码不存在");
						}

						error_mapVo.put("inv_code", temp[0]);

					} else {

						err_sb.append("药品材料编码为空");

					}

					if (StringTool.isNotBlank(temp[1])) {

						error_mapVo.put("inv_name", temp[1]);

					} else {

						err_sb.append("药品材料名称为空");

					}

					if (StringTool.isNotBlank(temp[2])) {

						Map<String, Object> supMapVo = new HashMap<String, Object>();

						supMapVo.put("group_id", SessionManager.getGroupId());

						supMapVo.put("hos_id", SessionManager.getHosId());

						supMapVo.put("copy_code", SessionManager.getCopyCode());

						supMapVo.put("is_stop", "0");

						supMapVo.put("sup_code", temp[2]);

						SupDict supDict = supDictService
								.querySupDictByCode(supMapVo);

						if (supDict != null) {

							mapVo.put("sup_id", supDict.getSup_id());

						} else {

							err_sb.append("供应商不存在");
						}

						error_mapVo.put("sup_code", temp[2]);

					} else {

						err_sb.append("供应商编码为空");

					}

					if (StringTool.isNotBlank(temp[3])) {

						error_mapVo.put("sup_name", temp[3]);

					} else {

						err_sb.append("供应商名称为空");

					}

					if (mapVo.get("sup_id") != null
							&& mapVo.get("inv_id") != null) {

						Map<String, Object> ex = medInvMapper
								.queryMedInvSupbyCode(mapVo);

						if (ex != null) {

							err_sb.append("一个材料只能维护一个供应商");

						}
					}

					if (err_sb.toString().length() > 0) {

						error_mapVo.put("error_type", err_sb.toString());

						list_err.add(error_mapVo);

					} else {

						supList.add(mapVo);

					}

				}

				if (supList.size() > 0) {

					medInvMapper.addMedInvSup(supList);

					supList.clear();
				}
			}

		} catch (DataAccessException e) {

			e.printStackTrace();
			System.out.println(">>>"+e);
			Map<String, Object> error_mapVo = new HashMap<String, Object>();

			error_mapVo.put("error_type", "导入系统出错");

			list_err.add(error_mapVo);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 08105 药品材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/addBatchMedInvSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedInvVen(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> supList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();

		JSONArray json = JSONArray.parseArray(paramVo);

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

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				Map<String, Object> error_mapVo = new HashMap<String, Object>();

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				if (StringTool.isNotBlank(jsonObj.get("inv_code"))) {

					Map<String, Object> invMapVo = new HashMap<String, Object>();

					invMapVo.put("group_id", SessionManager.getGroupId());

					invMapVo.put("hos_id", SessionManager.getHosId());

					invMapVo.put("copy_code", SessionManager.getCopyCode());

					invMapVo.put("inv_code", jsonObj.get("inv_code").toString());

					Map<String, Object> med_inv = medInvService
							.queryByUniqueness(invMapVo);

					if (med_inv != null) {

						mapVo.put("inv_id", med_inv.get("inv_id"));

					} else {

						err_sb.append("药品材料编码不存在");
					}

					error_mapVo.put("inv_code", jsonObj.get("inv_code")
							.toString());

				} else {

					err_sb.append("药品材料编码为空");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_name"))) {

					error_mapVo.put("inv_name", jsonObj.get("inv_name")
							.toString());

				} else {

					err_sb.append("药品材料名称为空");

				}

				if (StringTool.isNotBlank(jsonObj.get("sup_code"))) {

					Map<String, Object> supMapVo = new HashMap<String, Object>();

					supMapVo.put("group_id", SessionManager.getGroupId());

					supMapVo.put("hos_id", SessionManager.getHosId());

					supMapVo.put("copy_code", SessionManager.getCopyCode());

					supMapVo.put("is_stop", "0");

					supMapVo.put("sup_code", jsonObj.get("sup_code").toString());

					SupDict supDict = supDictService
							.querySupDictByCode(supMapVo);

					if (supDict != null) {

						mapVo.put("sup_id", supDict.getSup_id());

					} else {

						err_sb.append("供应商不存在");
					}

					error_mapVo.put("sup_code", jsonObj.get("sup_code")
							.toString());

				} else {

					err_sb.append("供应商编码为空");

				}

				if (StringTool.isNotBlank(jsonObj.get("sup_name"))) {

					error_mapVo.put("sup_name", jsonObj.get("sup_name")
							.toString());

				} else {

					err_sb.append("供应商名称为空");

				}

				Map<String, Object> ex = medInvMapper
						.queryMedInvSupbyCode(mapVo);

				if (ex != null) {

					err_sb.append("一个材料只能维护一个供应商");

				}

				if (err_sb.toString().length() > 0) {

					error_mapVo.put("error_type", err_sb.toString());

					list_err.add(error_mapVo);

				} else {

					supList.add(mapVo);
				}

			}

			if (supList.size() > 0) {
				medInvMapper.addMedInvSup(supList);
			}

		} catch (DataAccessException e) {

			Map<String, Object> error_mapVo = new HashMap<String, Object>();

			list_err.add(error_mapVo);

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

	public static boolean validExceColunm(String[] arr, int colunmNum) {

		if (arr.length - 1 >= colunmNum) {

			if (StringTool.isNotBlank(arr[colunmNum])
					&& arr[colunmNum].length() != 0
					&& !("").equals(arr[colunmNum])) {

				return true;

			} else {

				return false;
			}

		} else {

			return false;
		}

	}
	/**
	 * @Description 判断
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryByUniqueness", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByUniqueness(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 Map<String, Object> vmapVo=new HashMap<String, Object>();
		 vmapVo.put("group_id", SessionManager.getGroupId());
		 vmapVo.put("hos_id", SessionManager.getHosId());
		 vmapVo.put("copy_code", SessionManager.getCopyCode());
		 vmapVo.put("inv_name", mapVo.get("inv_name"));
		 
		List<Map<String, Object>> inv= (List<Map<String, Object>>) medInvService.queryExists(vmapVo);
		if(inv.size()>0){
			return JSONObject
					.parseObject("{\"info\":\"1\",\"state\":\"true\"}");
		}else{
			
			return JSONObject
					.parseObject("{\"info\":\"0\",\"state\":\"true\"}");
		}
	}
	
	//材料修改时   材料证件信息页面跳转
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvCertInfoMainPage", method = RequestMethod.GET)
	public String medInvCertInfoMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		if("1".equals(mapVo.get("is_main"))){
			mode.addAttribute("is_main", 1);
		}
		return "hrp/med/info/basic/inv/medInvCertInfoMain";
	}
	
	//材料添加时   材料证件信息页面跳转
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvCertInfoAddPage", method = RequestMethod.GET)
	public String medInvCertInfoAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "hrp/med/info/basic/inv/medInvCertInfoAdd";
	}
	
	@RequestMapping(value = "/hrp/med/info/basic/inv/addCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCertSup(@RequestParam Map<String, Object> entityMap,Model mode) throws Exception {

		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		String medInvJson;
		
		try {
			
			if(entityMap.get("allData") != null && !"".equals(entityMap.get("allData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("cert_code") != null && !"".equals(jsonObj.get("cert_code"))){
						Map<String,Object> dataMap = new HashMap<String,Object>();
						dataMap.put("group_id",SessionManager.getGroupId() );
						dataMap.put("hos_id", SessionManager.getHosId());
						dataMap.put("copy_code", SessionManager.getCopyCode());
						dataMap.put("cert_id", "");
						dataMap.put("cert_code", jsonObj.get("cert_code"));
						dataMap.put("cert_inv_name", jsonObj.get("cert_inv_name"));
						dataMap.put("type_id", jsonObj.get("type_id"));
						dataMap.put("start_date", jsonObj.get("start_date"));
						dataMap.put("end_date", jsonObj.get("end_date"));
						dataMap.put("cert_state", jsonObj.get("cert_state"));
						
						if(jsonObj.get("fac_id") == null || dataMap.put("fac_id", "") == ""){
							dataMap.put("fac_id", "");
						}else{
							dataMap.put("fac_id", jsonObj.get("fac_id"));
						}
						
						dataMap.put("cert_date", "");
						dataMap.put("issuing_authority", "");
						dataMap.put("link_person", "");
						dataMap.put("link_tel", "");
						dataMap.put("link_mobile", "");
						
						dataMap.put("cert_memo", "");
						dataMap.put("file_path", "");
						dataMap.put("file_address", "");
						
						dataMap.put("supData", jsonObj.get("supData"));
						
						if("1".equals(entityMap.get("is_main"))){
							dataMap.put("inv_id", entityMap.get("inv_id"));
						}
						
						dataList.add(dataMap);
						
					}
				}
			}
			if(dataList.size()>0){
				medInvJson = medInvCertService.addBatchMedCertSup(dataList);
			}else{
				medInvJson = "{\"error\":\"没有需要保存的数据.\",\"state\":\"true\"}";
			}
			
		}catch (Exception e) {
			medInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medInvJson);
					
	}
	
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvCertInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvCertInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInv = medInvService.queryMedInvCertInfo(getPage(mapVo));

		return JSONObject.parseObject(medInv);
	}
	
	/**
	 * 材料改变药品类别查询改类别材料数
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryChangeMedTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChangeMedTypeCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInv = medInvService.queryChangeMedTypeCode(mapVo);

		return JSONObject.parseObject(medInv);
	}
	
	/**
	 * 材料停用，验证库存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryStopTimeByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStopTimeByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInv = medInvService.queryStopTimeByCode(mapVo);

		return JSONObject.parseObject(medInv);
	}
	
	
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryHosFacDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = medInvService.queryHosFacDict(mapVo);
		return hrpMedSelect;
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvPrintSetPage", method = RequestMethod.GET)
	public String medInvPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../../acc/accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 
	 * 入库模板打印（包含主从表） 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/inv/queryMedInvByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String reJson=null;
		try {
			reJson=medInvService.queryMedInvByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	
	//导出
	@RequestMapping(value="/hrp/med/info/basic/inv/expMedInvToExcel", method = RequestMethod.GET)  
	public String expMedInvToExcel(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
			    HttpServletResponse response,Model mode) throws IOException { 
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		List<Map<String,Object>> medInvList = medInvService.queryMedInvList(mapVo);
		if(medInvList.size() == 0){
			return null;
		}
		
		List<List> list = new ArrayList<List>(); //所有数据List
		
		//------------------------表头 BEGIN----------------------
		List<String> headList = new ArrayList<String>();//表头List
		
		headList.add("药品编码");// 1
		headList.add("药品名称");// 2	
		headList.add("别名");// 3	
		headList.add("药品类别");// 4	
		headList.add("计价方法");// 5	
		
		
		headList.add("药品属性");// 6	
		headList.add("药品剂型");// 7	
		headList.add("药品规格");// 8	
		headList.add("入库单位");// 9	
		headList.add("病区拆零系数");// 10	
		
		
		headList.add("病区单位");// 11	
		headList.add("门诊拆零系数");// 12	
		headList.add("门诊单位");// 13	
		headList.add("生产厂商");// 14	
		headList.add("供应商	");// 15
		
		
		headList.add("计划价");	// 16
		headList.add("加价率	");// 17
		headList.add("零售价");	// 18
		headList.add("启用日期");// 19	
		headList.add("停用日期");// 20	
		
		
		headList.add("代理商");// 21
		headList.add("品牌名称");// 22
		headList.add("药品用途");// 23	
		headList.add("包装规格");// 24	
		headList.add("单位重量");// 25	
		
		
		headList.add("单位体积");// 26	
		headList.add("ABC分类");	// 27
		headList.add("管理类别");// 28	
		headList.add("是否在用");// 29	
		headList.add("储存条件");// 30	
		
		
		headList.add("是否中标");// 31	
		headList.add("中标日期");// 32	
		headList.add("中标编码");// 33	
		headList.add("是否唯一供应商");// 34	
		headList.add("存储编码");// 35	
		
		
		headList.add("计划来源");// 36	
		headList.add("是否零库存管理");// 37	
		headList.add("是否条码管理");// 38	
		headList.add("是否个体码管理");// 39	
		headList.add("是否品种码管理");// 40	
		
		
		headList.add("是否保质期管理");// 41	
		headList.add("是否证件管理");// 42	
		headList.add("是否科室库管理");// 43	
		headList.add("是否自制品");// 44

		list.add(headList);
		//------------------------表头 END----------------------
		
		
		//--------------------处理数据 BEGIN---------------------
		for(Map<String,Object> map : medInvList ){
			
			List<String> row = new ArrayList<String>();//数据List
			
			row.add(String.valueOf(map.get("inv_code")));// 1 药品编码
			row.add(String.valueOf(map.get("inv_name")));// 2 药品名称
			row.add(reg_isEmpty(map.get("alias")));// 3 别名
			row.add(reg_isEmpty(map.get("med_type_name")));// 4 药品类别
			String price_type = reg_isEmpty(map.get("price_type"));// 5 计价方法
			
			if("".equals(reg_isEmpty(price_type))){
				row.add("");
			}else{
				if("0".equals(price_type)){
					row.add("先进先出");
				}
				
				if("1".equals(price_type)){
					row.add("移动加权平均");
				}
				
				if("2".equals(price_type)){
					row.add("一次性加权平均");
				}
			}
			
			row.add(reg_isEmpty(map.get("med_sx_name")));// 6 药品属性
			row.add(reg_isEmpty(map.get("med_jx_name")));// 7 药品剂型
			row.add(reg_isEmpty(map.get("inv_model")));// 8 药品规格
			row.add(reg_isEmpty(map.get("unit_name")));// 9 入库单位
			row.add(reg_isEmpty(map.get("bqplxs")));// 10 病区拆零系数
			
			
			row.add("");// 11 病区单位
			row.add(reg_isEmpty(map.get("bqplxs")));// 12 门诊拆零系数
			row.add("");// 13门诊单位
			row.add(reg_isEmpty(map.get("fac_name")));// 14 生产厂商
			row.add(reg_isEmpty(map.get("sup_name")));// 15 供应商
			
			
			row.add(reg_isEmpty(map.get("plan_price")));// 16 计划价
			row.add(reg_isEmpty(map.get("price_rate")));// 17 加价率	
			row.add(reg_isEmpty(map.get("sell_price")));// 18 零售价
			row.add(reg_isEmpty(map.get("sdate")));// 19 启用日期
			row.add(reg_isEmpty(map.get("edate")));// 20 停用日期
			
			
			row.add(reg_isEmpty(map.get("agent_name")));// 21 代理商
			row.add(reg_isEmpty(map.get("brand_name")));// 22 品牌名称
			row.add(reg_isEmpty(map.get("inv_usage")));// 23 药品用途
			row.add(reg_isEmpty(map.get("inv_structure")));// 24 包装规格
			row.add(reg_isEmpty(map.get("per_weight")));// 25 单位重量
			
			
			row.add(reg_isEmpty(map.get("per_volum")));// 26 单位体积
			row.add(reg_isEmpty(map.get("abc_type")));// 27 ABC分类	
			row.add(reg_isEmpty(map.get("manage_type")));//28 管理类别
			row.add("".equals(reg_isEmpty(map.get("use_state"))) ? "" : "1".equals(String.valueOf(map.get("is_single_ven"))) ? "是" : "否"  );// 29 是否在用
			row.add(reg_isEmpty(map.get("stora_tran_cond")));// 30 储存条件
			
			
			row.add("".equals(reg_isEmpty(map.get("is_bid"))) ? "" : "1".equals(String.valueOf(map.get("is_single_ven"))) ? "是" : "否" );// 31 是否中标
			row.add(reg_isEmpty(map.get("bid_date")));// 32 中标日期
			row.add(reg_isEmpty(map.get("bid_code")));// 33 中标编码
			row.add("".equals(reg_isEmpty(map.get("is_single_ven"))) ? "" : "1".equals(String.valueOf(map.get("is_single_ven"))) ? "是" : "否" );// 34 是否唯一供应商
			row.add(reg_isEmpty(map.get("memory_encoding")));// 35 存储编码
			
			
			row.add("".equals(reg_isEmpty(map.get("source_plan"))) ? "" : "1".equals(String.valueOf(map.get("source_plan"))) ? "科室申领" : "仓库报备");// 36 计划来源
			row.add("".equals(reg_isEmpty(map.get("is_zero_store"))) ? "" : "1".equals(String.valueOf(map.get("is_zero_store"))) ? "是" : "否" );// 37 是否零库存管理
			row.add("".equals(reg_isEmpty(map.get("is_bar"))) ? "" : "1".equals(String.valueOf(map.get("is_bar"))) ? "是" : "否" );// 38 是否条码管理
			row.add("".equals(reg_isEmpty(map.get("is_per_bar"))) ? "" : "1".equals(String.valueOf(map.get("is_per_bar"))) ? "是" : "否" );// 39 是否个体码管理
			row.add("".equals(reg_isEmpty(map.get("is_inv_bar"))) ? "" : "1".equals(String.valueOf(map.get("is_inv_bar"))) ? "是" : "否" );// 40 是否品种码
			
			
			row.add("".equals(reg_isEmpty(map.get("is_quality"))) ? "" : "1".equals(String.valueOf(map.get("is_quality"))) ? "是" : "否" );// 41 是否保质期管理	
			row.add("".equals(reg_isEmpty(map.get("is_cert"))) ? "" : "1".equals(String.valueOf(map.get("is_cert"))) ? "是" : "否" );// 42 是否证件管理
			row.add("".equals(reg_isEmpty(map.get("is_sec_whg"))) ? "" :  "1".equals(String.valueOf(map.get("is_sec_whg"))) ? "是" : "否" );// 43 是否科室库
			row.add("".equals(reg_isEmpty(map.get("is_shel_make"))) ? "" : "1".equals(String.valueOf(map.get("is_shel_make"))) ? "是" : "否" );// 44 是否自制品
			
			list.add(row);
		}
		
		//--------------------处理数据 END---------------------

		String fileName = "药品目录.xlsx";
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\med\\downTemplate\\";
				
		String downLoadPath = ctxPath + fileName;
				
		ExcelWriterTo2007.exportExcel(new File(downLoadPath), list);
				
		printTemplate(request, response, "med\\downTemplate", fileName);
		
		return null;
	}
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/inv/medInvImportNewPage", method = RequestMethod.GET)
	public String medInvImportNewPage(Model mode) throws Exception {

		return "hrp/med/info/basic/inv/medInvImportNew";
	}
	
	/**
	 * @Description 
	 * 导入数据 MAT_INV_CERT
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hrp/med/info/basic/inv/import",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> matInvCertImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=medInvService.importData(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
	public String reg_isEmpty(Object obj){
		
		if(obj == null || "null".equals(obj) || "".equals(obj)){
			
			return "";
		}
		
		return String.valueOf(obj);
	}
}
