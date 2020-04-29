/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.info.basic;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
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
import com.chd.base.util.FileUtil;
import com.chd.base.util.PageModel;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeDictMapper;
import com.chd.hrp.mat.entity.MatInv;
import com.chd.hrp.mat.entity.MatTypeDict;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.base.MatInvPictureService;
import com.chd.hrp.mat.service.info.basic.MatInvCertService;
import com.chd.hrp.mat.service.info.basic.MatInvService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreInvServiceImpl;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreTypeServiceImpl;
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
 * @Description: 04105 物资材料表
 * @Table: MAT_INV
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatInvController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInvController.class);

	// 引入Service服务
	@Resource(name = "matInvService")
	private final MatInvService matInvService = null;

	@Resource(name = "matInvMapper")
	private final MatInvMapper matInvMapper = null;

	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;

	@Resource(name = "supDictService")
	private final SupDictService supDictService = null;

	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;

	@Resource(name = "facDictMapper")
	private final FacDictMapper facDictMapper = null;

	@Resource(name = "matTypeDictMapper")
	private final MatTypeDictMapper matTypeDictMapper = null;

	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;

	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	@Resource(name = "supDictMapper")
	private final SupDictMapper supDictMapper = null;
	
	@Resource(name = "matInvPictureService")
	private final MatInvPictureService matInvPictureService = null;

	@Resource(name = "matStoreTypeService")
	private final MatStoreTypeServiceImpl matStoreTypeService = null;
	
	@Resource(name = "matStoreInvService")
	private final MatStoreInvServiceImpl matStoreInvService = null;
	
	@Resource(name = "matInvCertService")
	private final MatInvCertService matInvCertService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvMainPage", method = RequestMethod.GET)
	public String matInvMainPage(Model mode) throws Exception {

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04017", MyConfig.getSysPara("04017"));
		mode.addAttribute("p04080", MyConfig.getSysPara("04080"));
		
		return "hrp/mat/info/basic/inv/matInvMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvAddPage", method = RequestMethod.GET)
	public String matInvAddPage(Model mode) throws Exception {

		mode.addAttribute("p04001", MyConfig.getSysPara("04001"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04070", MyConfig.getSysPara("04070"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04080", MyConfig.getSysPara("04080"));
		
		return "hrp/mat/info/basic/inv/matInvAdd";
	}
	
	/**
	 * @Description 上传图片页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvPictureImportPage", method = RequestMethod.GET)
	public String matInvPictureImportPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		return "hrp/mat/info/basic/inv/matInvPictureImport";
	}
	
	

	/**
	 * @Description 变更查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvDictMainPage", method = RequestMethod.GET)
	public String matInvDictMainPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("inv_code", mapVo.get("inv_code"));
		return "hrp/mat/info/basic/inv/matInvDictMain";
	}

	/**
	 * @Description 变更查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvDictUpdatePage", method = RequestMethod.GET)
	public String matInvDictUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		Map<String, Object> matInvDict = matInvService.queryDictByCode(mapVo);
		if (matInvDict.get("sdate") != null
				&& !"".equals(matInvDict.get("sdate"))) {
			matInvDict.put("sdate", DateUtil.dateToString(
					(Date) matInvDict.get("sdate"), "yyyy-MM-dd"));
		}
		if (matInvDict.get("edate") != null
				&& !"".equals(matInvDict.get("edate"))) {
			matInvDict.put("edate", DateUtil.dateToString(
					(Date) matInvDict.get("edate"), "yyyy-MM-dd"));
		}
		if (matInvDict.get("bid_date") != null
				&& !"".equals(matInvDict.get("bid_date"))) {
			matInvDict.put("bid_date", DateUtil.dateToString(
					(Date) matInvDict.get("bid_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("matInvDict", matInvDict);

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/info/basic/inv/matInvDictUpdate";
	}

	/**
	 * @Description 添加数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/addMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInv(
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
		
		String matInvJson;
		try {
			
			
			matInvJson = matInvService.add(mapVo);
			
			//2017/03/23 将此处代码移动到了serviceImp中
			//Map<String, Object> matInv = matInvService.queryByCode(mapVo);
			
			/*MatStoreType matStoreType =   matStoreTypeService.queryMatStoreTypeByCode(mapVo);
			
			Map<String, Object> mapStore =new HashMap<String, Object>();
			
			mapStore.put("group_id",mapVo.get("group_id"));
			
			mapStore.put("hos_id",mapVo.get("hos_id"));
			
			mapStore.put("copy_code",mapVo.get("copy_code"));
			
			mapStore.put("store_id",matStoreType.getStore_id());
			
			mapStore.put("inv_id",matInv.get("inv_id"));
			
			matStoreInvService.addMatStoreInv(mapStore); */
			
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * @Description 添加数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/addMatInvSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvSup(
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
		
		String matInvJson;
		try {
			matInvJson = matInvService.addMatInvSup(mapVo);
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}
	/**
	 * 复制物资材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvCopyPage", method = RequestMethod.GET)
	public String matInvCopyPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		Map<String, Object> matInv = matInvService.queryByCode(mapVo);

		if (matInv.get("sdate") != null && !"".equals(matInv.get("sdate"))) {
			matInv.put("sdate", DateUtil.dateToString(
					(Date) matInv.get("sdate"), "yyyy-MM-dd"));
		}
		if (matInv.get("edate") != null && !"".equals(matInv.get("edate"))) {
			matInv.put("edate", DateUtil.dateToString(
					(Date) matInv.get("edate"), "yyyy-MM-dd"));
		}
		if (matInv.get("bid_date") != null
				&& !"".equals(matInv.get("bid_date"))) {
			matInv.put("bid_date", DateUtil.dateToString(
					(Date) matInv.get("bid_date"), "yyyy-MM-dd"));
		}
		//复制的物资材料不带 品牌名称 和 材料用途
		matInv.remove("brand_name");//品牌名称
		matInv.remove("inv_usage");//材料用途
		mode.addAttribute("matInv", matInv);

		mode.addAttribute("p04001", MyConfig.getSysPara("04001"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/info/basic/inv/matInvCopy";
	}
	/**
	 * @Description 更新跳转页面 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvUpdatePage", method = RequestMethod.GET)
	public String matInvUpdatePage(@RequestParam Map<String, Object> mapVo,	Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> matInv = matInvService.queryByCode(mapVo);

		if (matInv.get("sdate") != null && !"".equals(matInv.get("sdate"))) {
			matInv.put("sdate", DateUtil.dateToString(
					(Date) matInv.get("sdate"), "yyyy-MM-dd"));
		}
		if (matInv.get("edate") != null && !"".equals(matInv.get("edate"))) {
			matInv.put("edate", DateUtil.dateToString(
					(Date) matInv.get("edate"), "yyyy-MM-dd"));
		}
		if (matInv.get("bid_date") != null
				&& !"".equals(matInv.get("bid_date"))) {
			matInv.put("bid_date", DateUtil.dateToString(
					(Date) matInv.get("bid_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("matInv", matInv);
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04070", MyConfig.getSysPara("04070"));
		mode.addAttribute("p04072", MyConfig.getSysPara("04072"));
		mode.addAttribute("p04080", MyConfig.getSysPara("04080"));
		
		return "hrp/mat/info/basic/inv/matInvUpdate";
	}

	/**
	 * @Description 更新数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/updateMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInv(
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
		if (mapVo.get("change_spell_code")==null || !"1".equals(mapVo.get("change_spell_code").toString())) {
			mapVo.put("spell_code",
					StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		}
		
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("inv_name").toString()));

		mapVo.put("alias_spell",
				StringTool.toPinyinShouZiMu(mapVo.get("alias").toString()));
		
		String matInvJson;
		try {
			matInvJson = matInvService.update(mapVo);
			
			/*
			 *材料修改调用webservice 根据04075 参数判断
			 * */
			//------------------------------------------------------------//
			if(("1").equals(MyConfig.getSysPara("04075"))){
				
				Map<String, Object> m=new HashMap<String, Object>();
				m.put("group_id", mapVo.get("group_id"));
				m.put("hos_id", mapVo.get("hos_id"));
				m.put("copy_code", mapVo.get("copy_code"));
				m.put("inv_ids", mapVo.get("inv_id"));
				m.put("inv_no", mapVo.get("inv_no"));
				
				Map<String, Object> matInvDict = matInvService.queryDictByCode(m);
				//变更计划价格同步数据
				if(!matInvDict.get("plan_price").equals(mapVo.get("plan_price")))
				{
					matStoreInvService.wbPassUpdateInv(m);
				}
				
			}
			//------------------------------------------------------------//
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}
	/**
	 * @Description 批量更新跳转页面 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvUpdateBatchPage", method = RequestMethod.GET)
	public String matInvUpdateBatchPage(@RequestParam Map<String, Object> mapVo,	Model mode) throws Exception {

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
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/info/basic/inv/matInvUpdateBatch";
	}

	/**
	 * @Description 更新数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/updateMatInvBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvBatch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matInvJson;
		try {
			matInvJson = matInvService.updateMatInvBatch(mapVo);
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * @Description 删除数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/deleteMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInv(
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
		
		String matInvJson;
		try {
			matInvJson = matInvService.deleteBatch(listVo);
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/auditMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatInv(@RequestParam Map<String,Object> mapVo, Model mode) throws Exception {

		/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
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
			mapVo.put("state", "1");
			listVo.add(mapVo);
		}*/

		String matInvJson;
		try {
			
			if(MyConfig.getSysPara("04080").equals("0")){
				matInvJson = "{\"warn\":\"当前程序无需对材料进行审核!\"}";
			}else{
			
				if(mapVo.containsKey("flag")){
					matInvJson = matInvService.auditMatInvById(mapVo);
				}else{
					matInvJson = matInvService.auditMatInv(mapVo);
				}
			}
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}
	/**
	 * @Description 销审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/unAuditMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatInv(@RequestParam Map<String,Object>mapVo, Model mode) throws Exception {

		/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("inv_id", ids[3]);

			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", "");
			mapVo.put("state", "0");
			listVo.add(mapVo);
		}*/

		String matInvJson;
		try {
			if(mapVo.containsKey("flag")){
				matInvJson = matInvService.auditMatInvById(mapVo);
			}else{
				matInvJson = matInvService.auditMatInv(mapVo);
			}
			
			
		} catch (Exception e) {
			matInvJson = e.getMessage();
		}

		return JSONObject.parseObject(matInvJson);
	}

	/**
	 * @Description 查询数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInv(
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
		String matInv = matInvService.query(getPage(mapVo));

		return JSONObject.parseObject(matInv);
	}

	/**
	 * @Description 查询数据 04105 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvDict(
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
		String matInv = matInvService.queryDict(getPage(mapVo));

		return JSONObject.parseObject(matInv);
	}

	/**
	 * @Description 更新跳转页面 材料供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvSupUpdatePage", method = RequestMethod.GET)
	public String matInvSupUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		Map<String, Object> matInv = matInvService.queryByCode(mapVo);

		mode.addAttribute("inv_name", matInv.get("inv_name"));
		// mode.addAttribute("matInvSup", matInvService.queryMatInvSup(mapVo));

		return "hrp/mat/info/basic/inv/matInvSupUpdate";
	}

	/**
	 * @Description 查询材料供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvSup(
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

		String matMsg = matInvService.queryMatInvSup(mapVo);

		return JSONObject.parseObject(matMsg);
	}

	/**
	 * @Description 供应商列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvSupList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvSupList(
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

		String matMsg = matInvService.queryMatInvSupList(getPage(mapVo));

		return JSONObject.parseObject(matMsg);
	}
	
	/**
	 * @Description 不查停用材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvSupListDisable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvSupListDisable(
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

		String matMsg = matInvService.queryMatInvSupListDisable(getPage(mapVo));

		return JSONObject.parseObject(matMsg);
	}

	/**
	 * @Description 导入跳转页面 04105 物资材料表
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvImportPage", method = RequestMethod.GET)
	public String matInvImportPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/inv/matInvImport";

	}

	/**
	 * @Description 导入跳转页面 04105 物资材料表供应商
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvSupImportPage", method = RequestMethod.GET)
	public String matInvImportVenPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/inv/matInvSupImport";

	}

	/**
	 * @Description 下载导入模版 04105 物资材料表
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/info/basic/inv/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate", "04105物资字典导入模板.xls");

		return null;
	}

	/**
	 * @Description 下载导入模版 04105 物资材料表供应商
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/info/basic/inv/downTemplateSup", method = RequestMethod.GET)
	public String downTemplateSup(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "mat\\downTemplate", "04105物资供应商.xls");

		return null;
	}
	
	
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvPicture", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvPicture(
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
		String matInv = matInvPictureService.query(getPage(mapVo));

		return JSONObject.parseObject(matInv);
	}
	/**
	 * 复制 时 根据材料名称、规格 查询 材料是否已存在
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvExist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvExist(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInv = matInvService.queryMatInvExist(mapVo);

		return JSONObject.parseObject(matInv);
	}
	/**
	 * @Description 删除数据 物资材料图片
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/deleteMatInvPicture", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvPicture(@RequestParam Map<String, Object> mapVo,HttpServletRequest request, Model mode)
			throws Exception {
		String matInvJson = matInvPictureService.delete(mapVo);
		FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+mapVo.get("picture_url").toString()+"\\"+mapVo.get("picture_name").toString());
		String picture_name = mapVo.get("picture_name").toString().split("\\.")[0];
		String picture_type = mapVo.get("picture_name").toString().split("\\.")[1];
		String new_picture_name = picture_name+"_out."+picture_type;
		FileUtil.deleteFile(request.getSession().getServletContext().getRealPath("\\")+mapVo.get("picture_url").toString()+"\\"+new_picture_name);
		return JSONObject.parseObject(matInvJson);
	}
	
	/**
	 * @Description 上传物资材料图片
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value="hrp/mat/info/basic/inv/importInvPictureFile", method = RequestMethod.POST)  
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
		List<File> fileList = UploadUtil.upload(plupload, "matInvPicture",request, response);
		for(File file : fileList){
			String fileName = file.getName().split("\\.")[0];
			String fileType = file.getName().split("\\.")[1];
			String newFileName = fileName+"_out."+fileType;
			Images.zoomScale(rPath+"\\matInvPicture\\"+file.getName(), rPath+"\\matInvPicture\\"+newFileName, 180,180, Color.WHITE);
			mapVo.put("picture_name", file.getName());
			mapVo.put("picture_url", "matInvPicture");
				
			result = matInvPictureService.add(mapVo);
		}
		response.getWriter().print(result);
	    return null; 
	 }
	
	

	/**
	 * @Description 导入数据 04105 物资材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/readMatInvFiles", method = RequestMethod.POST)
	public String readMatInvFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		int para = Integer.valueOf(MyConfig.getSysPara("04001"));
		
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

		/*----------------------查询物资分类---------------------------*/
		Map<String, Object> MatTypeMapVo = new HashMap<String, Object>();

		MatTypeMapVo.put("group_id", SessionManager.getGroupId());

		MatTypeMapVo.put("hos_id", SessionManager.getHosId());

		MatTypeMapVo.put("copy_code", SessionManager.getCopyCode());

		MatTypeMapVo.put("is_stop", "0");

		List<MatTypeDict> MatTypeDict = matTypeDictMapper.queryMatTypeDict(MatTypeMapVo);
		/*----------------------查询供应商---------------------------*/
		Map<String, Object> supMapVo = new HashMap<String, Object>();

		supMapVo.put("group_id", SessionManager.getGroupId());

		supMapVo.put("hos_id", SessionManager.getHosId());

		supMapVo.put("copy_code", SessionManager.getCopyCode());

		supMapVo.put("is_stop", "0");

		List<SupDict> supDict = supDictMapper.querySupDict(supMapVo);
		
		List<Map<String, Object>>  saveInvMaps = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> saveInvSupMaps = new ArrayList<Map<String, Object>>();

		List<MatInv> list_err = new ArrayList<MatInv>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			//System.out.println(list.size());
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();

				MatInv matInv = new MatInv();

				String temp[] = list.get(i);// 行
				//System.out.println("A"+i+" : " + temp[1]);

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
					matInv.setInv_code(temp[0]);

				} else {

					err_sb.append("物资材料编码为空  ");
				}

				if (validExceColunm(temp, 1)) {

					matInv.setInv_name(temp[1]);

					mapVo.put("inv_name", temp[1]);
					
					invSupMap.put("inv_name", temp[1]);

				} else {

					err_sb.append("物资材料名称为空  ");

				}

				if (validExceColunm(temp, 2)) {

					matInv.setAlias(temp[2]);

					mapVo.put("alias", temp[2]);

					mapVo.put("alias_spell", StringTool.toPinyinShouZiMu(mapVo
							.get("alias").toString()));

				} else {

					matInv.setAlias(null);

					mapVo.put("alias", null);

				}

				boolean bMatType = false;

				if (validExceColunm(temp, 3)) {

					if (MatTypeDict.size() > 0) {
						for (MatTypeDict MatTypeDict1 : MatTypeDict) {

							if (temp[3].toString().equals(
									MatTypeDict1.getMat_type_code())) {

								mapVo.put("mat_type_id",
										MatTypeDict1.getMat_type_id());

								mapVo.put("mat_type_no",
										MatTypeDict1.getMat_type_no());

								bMatType = true;

								break;

							}

						}

					} else {

						err_sb.append("请先维护物资类别");
					}
					if (bMatType == false) {

						err_sb.append("物资类别编码不存在 ");

					}

					matInv.setMat_type_code(temp[3]);

					matInv.setMat_type_name(temp[4]);

				} else {

					err_sb.append("物资类别编码为空 ");

				}

				if (validExceColunm(temp, 5)) {

					matInv.setInv_model(temp[5]);

					mapVo.put("inv_model", temp[5]);

				} else {

					matInv.setInv_model("");

					mapVo.put("inv_model", "");

				}

				boolean bUnit = false;
				if (validExceColunm(temp, 6)) {

					if (unit.size() > 0) {
						for (Unit unit1 : unit) {

							if (temp[6].toString().equals(unit1.getUnit_name())) {

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

					matInv.setUnit_name(temp[6]);

				} else {

					matInv.setUnit_name("");

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

					matInv.setFac_code(temp[7]);

					matInv.setFac_name(temp[8]);

				} else {

					matInv.setFac_code(temp[7]);

					matInv.setFac_name(temp[8]);

					mapVo.put("fac_id", "");

				}

				if (validExceColunm(temp, 9)) {

					mapVo.put("amortize_type", amortize_type.get(temp[9]));
					
					matInv.setAmortize_type(Integer.parseInt(amortize_type.get(temp[9]).toString()));

				} else {

					err_sb.append("摊销方式为空  ");

				}

				if (validExceColunm(temp, 10)) {

					mapVo.put("price_type", price_type.get(temp[10]));

					matInv.setPrice_type(Integer.parseInt(price_type.get(temp[10]).toString()));

				} else {

					err_sb.append("计价方法为空  ");

				}

				if (validExceColunm(temp, 11)) {

					matInv.setPlan_price(Double.parseDouble(temp[11]));

					mapVo.put("plan_price", temp[11]);

				} else {

					mapVo.put("plan_price", "");

				}

				if (validExceColunm(temp, 12)) {

					matInv.setPrice_rate(Double.parseDouble(temp[12]));

					mapVo.put("price_rate", temp[12]);

				} else {

					mapVo.put("price_rate", "");

				}

				if (validExceColunm(temp, 13)) {

					matInv.setSell_price(Double.parseDouble(temp[13]));

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

					matInv.setIs_single_ven(Integer.parseInt(state.get(temp[16]).toString()));

					mapVo.put("is_single_ven", state.get(temp[16]));
					
				} else {

					err_sb.append("是否唯一供应商为空");

				}

				if (validExceColunm(temp, 17)) {

					matInv.setIs_batch(Integer.parseInt(state.get(temp[17]).toString()));

					mapVo.put("is_batch", state.get(temp[17]));

				} else {

					err_sb.append("是否批次管理为空  ");

				}

				if (validExceColunm(temp, 18)) {

					matInv.setIs_bar(Integer.parseInt(state.get(temp[18]).toString()));

					mapVo.put("is_bar", state.get(temp[18]));

				} else {

					err_sb.append("是否条码管理为空  ");

				}

				if (validExceColunm(temp, 19)) {

					matInv.setIs_per_bar(Integer.parseInt(state.get(temp[19]).toString()));

					mapVo.put("is_per_bar", state.get(temp[19]));

				} else {

					err_sb.append("是否个体条码为空  ");

				}

				if (validExceColunm(temp, 20)) {

					matInv.setIs_cert(Integer.parseInt(state.get(temp[20]).toString()));

					mapVo.put("is_cert", state.get(temp[20]));

				} else {

					err_sb.append("是否证件管理为空  ");

				}

				if (validExceColunm(temp, 21)) {

					matInv.setIs_quality(Integer.parseInt(state.get(temp[21]).toString()));

					mapVo.put("is_quality", state.get(temp[21]));

				} else {

					err_sb.append("是否保质期管理为空  ");

				}

				if (validExceColunm(temp, 22)) {

					matInv.setIs_disinfect(Integer.parseInt(state.get(temp[22]).toString()));

					mapVo.put("is_disinfect", state.get(temp[22]));

				} else {

					err_sb.append("是否灭菌材料为空  ");

				}

				if (validExceColunm(temp, 23)) {

					matInv.setIs_com(Integer.parseInt(state.get(temp[23]).toString()));

					mapVo.put("is_com", state.get(temp[23]));

				} else {

					err_sb.append("是否代销品为空  ");

				}

				if (validExceColunm(temp, 24)) {

					matInv.setIs_dura(Integer.parseInt(state.get(temp[24]).toString()));

					mapVo.put("is_dura", state.get(temp[24]));

				} else {

					err_sb.append("是否为耐用品为空  ");

				}

				if (validExceColunm(temp, 25)) {

					matInv.setIs_highvalue(Integer.parseInt(state.get(temp[25]).toString()));

					mapVo.put("is_highvalue", state.get(temp[25]));

				} else {

					err_sb.append("是否高值为空  ");

				}

				if (validExceColunm(temp, 26)) {

					matInv.setIs_charge(Integer.parseInt(state.get(temp[26]).toString()));

					mapVo.put("is_charge", state.get(temp[26]));

				} else {

					err_sb.append("是否收费为空  ");

				}

				if (validExceColunm(temp, 27)) {

					matInv.setIs_sec_whg(Integer.parseInt(state.get(temp[27]).toString()));

					mapVo.put("is_sec_whg", state.get(temp[27]));

				} else {

					err_sb.append("是否作二级库管理为空  ");

				}

				if (validExceColunm(temp, 28)) {

					matInv.setIs_shel_make(Integer.parseInt(state.get(temp[28]).toString()));

					mapVo.put("is_shel_make", state.get(temp[28]));

				} else {

					err_sb.append("是否自制品为空  ");

				}

				if (validExceColunm(temp, 29)) {

					matInv.setSdate(DateUtil.stringToDate(temp[29],"yyyy-MM-dd"));

					mapVo.put("sdate", DateUtil.stringToDate(temp[29].toString(), "yyyy-MM-dd"));

				} else {

					matInv.setSdate(null);

					mapVo.put("sdate", null);

				}

				if (validExceColunm(temp, 30)) {

					matInv.setEdate(DateUtil.stringToDate(temp[30],"yyyy-MM-dd"));

					mapVo.put("edate", DateUtil.stringToDate(temp[30].toString(), "yyyy-MM-dd"));

				} else {

					matInv.setEdate(null);

					mapVo.put("edate", null);

				}

				if (validExceColunm(temp, 31)) {

					matInv.setBar_code_new(temp[31]);

					mapVo.put("bar_code_new", temp[31]);

				} else {

					matInv.setBar_code_new("");

					mapVo.put("bar_code_new", "");

					// err_sb.append("品种条码为空  ");

				}

				if (validExceColunm(temp, 32)) {

					matInv.setAbc_type(temp[32]);

					mapVo.put("abc_type", temp[32]);

				} else {

					matInv.setAbc_type("");

					mapVo.put("abc_type", "");
					// err_sb.append("ABC分类为空 ");

				}

				if (validExceColunm(temp, 33)) {

					matInv.setPer_weight(Double.parseDouble(temp[33]));

					mapVo.put("per_weight", temp[33]);

				} else {

					mapVo.put("per_weight", null);

					// err_sb.append("单位重量为空  ");

				}

				if (validExceColunm(temp, 34)) {

					matInv.setPer_volum(Double.parseDouble(temp[34]));

					mapVo.put("per_volum", temp[34]);

				} else {

					// matInv.setPer_volum("");

					mapVo.put("per_volum", null);

					// err_sb.append("单位体积为空  ");

				}

				if (validExceColunm(temp, 35)) {

					matInv.setBrand_name(temp[35]);

					mapVo.put("brand_name", temp[35]);

				} else {

					matInv.setBrand_name("");

					mapVo.put("brand_name", "");
					// err_sb.append("品牌为空  ");

				}

				if (validExceColunm(temp, 36)) {

					matInv.setAgent_name(temp[36]);

					mapVo.put("agent_name", temp[36]);

				} else {

					matInv.setAgent_name("");

					mapVo.put("agent_name", "");

					// err_sb.append("代理商为空  ");

				}

				if (validExceColunm(temp, 37)) {

					matInv.setInv_structure(temp[37]);

					mapVo.put("inv_structure", temp[37]);

				} else {

					matInv.setInv_structure("");

					mapVo.put("inv_structure", "");
					// err_sb.append("材料结构为空  ");

				}

				if (validExceColunm(temp, 38)) {

					matInv.setInv_usage(temp[38]);

					mapVo.put("inv_usage", temp[38]);

				} else {

					matInv.setInv_usage("");

					mapVo.put("inv_usage", "");

					// err_sb.append("材料用途为空  ");

				}

				if (validExceColunm(temp, 39)) {

					matInv.setUse_state(Integer.parseInt(state.get(temp[39]).toString()));

					mapVo.put("use_state", state.get(temp[39]));

				} else {

					err_sb.append("是否在用为空  ");

				}

				if (validExceColunm(temp, 40)) {

					matInv.setNote(temp[40]);

					mapVo.put("note", temp[40]);

				} else {

					matInv.setNote("");

					mapVo.put("note", "");

					// err_sb.append("备注为空  ");
				}

				// 审核状态
				mapVo.put("state", "0");

				if (validExceColunm(temp, 41)) {

					matInv.setIs_bid(Integer.parseInt(state.get(temp[41]).toString()));

					mapVo.put("is_bid", state.get(temp[41]));
				} else {

					matInv.setIs_bid(null);

					mapVo.put("is_bid", null);

					// err_sb.append("是否中标为空  ");

				}

				if (validExceColunm(temp, 42)) {

					matInv.setBid_date(DateUtil.stringToDate(temp[42],
							"yyyy-MM-dd"));

					mapVo.put("bid_date", DateUtil.stringToDate(
							temp[42].toString(), "yyyy-MM-dd"));

				} else {

					matInv.setBid_date(null);

					mapVo.put("bid_date", null);

				}

				if (validExceColunm(temp, 43)) {

					matInv.setBid_code(temp[43]);

					mapVo.put("bid_code", temp[43]);

				} else {

					matInv.setBid_code("");

					mapVo.put("bid_code", "");

				}

				if (validExceColunm(temp, 44)) {

					matInv.setIs_involved(Integer.parseInt(state.get(temp[44]).toString()));

					mapVo.put("is_involved", state.get(temp[44]));

				} else {

					matInv.setIs_involved(null);

					mapVo.put("is_involved", null);

				}
				if (validExceColunm(temp, 45)) {

					matInv.setIs_implant(Integer.parseInt(state.get(temp[45]).toString()));

					mapVo.put("is_implant", state.get(temp[45]));
				} else {

					matInv.setIs_implant(null);

					mapVo.put("is_implant", null);

				}

				if (validExceColunm(temp, 46)) {

					matInv.setStora_tran_cond(temp[46]);

					mapVo.put("stora_tran_cond", temp[46]);

				} else {

					matInv.setStora_tran_cond(null);

					mapVo.put("stora_tran_cond", null);

				}

				if (validExceColunm(temp, 47)) {

					matInv.setIs_zero_store(Integer.parseInt(state.get(temp[47]).toString()));

					mapVo.put("is_zero_store", state.get(temp[47]));

				} else {

					matInv.setIs_zero_store(null);

					mapVo.put("is_zero_store", null);

				}
				
				if (validExceColunm(temp, 48)) {

					matInv.setSource_plan(Integer.parseInt(state.get(temp[48]).toString()));

					mapVo.put("source_plan", state.get(temp[48]));

				} else {

					matInv.setSource_plan(null);

					mapVo.put("source_plan", null);

				}
				
				if (validExceColunm(temp, 49)) {

					matInv.setIs_inv_bar(Integer.parseInt(state.get(temp[49]).toString()));

					mapVo.put("is_inv_bar", state.get(temp[49]));

				} else {

					matInv.setIs_inv_bar(null);

					mapVo.put("is_inv_bar", null);

				}

			
				Map<String, Object> excDictMapVo = new HashMap<String, Object>();

				excDictMapVo.put("group_id", SessionManager.getGroupId());

				excDictMapVo.put("hos_id", SessionManager.getHosId());

				excDictMapVo.put("copy_code", SessionManager.getCopyCode());

				if (err_sb.toString().length() > 0) {

					matInv.setError_type(err_sb.toString());

					list_err.add(matInv);
					
					break;

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));

					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("inv_name").toString()));
					
					saveInvMaps.add(mapVo);
					

				}

			}
			//if(list_err.size() == 0){
				//20170523--hsy--修改为批量保存替换老的循环调用service的方式
				matInvService.addImpBatch(saveInvMaps);
			//}
			/*----老方法--循环调用services
			for(Map<String, Object> map :saveInvMaps){
				
				if(map.get("sup_id")!=null){
					
					Map<String, Object> supMap = new HashMap<String, Object>();
				
					supMap.put("group_id", SessionManager.getGroupId());

					supMap.put("hos_id", SessionManager.getHosId());

					supMap.put("copy_code", SessionManager.getCopyCode());
					
					matInvService.addimp(map);
					
					supMap.put("inv_id", matInvMapper.queryMatInvSeqCur());
					supMap.put("sup_id", map.get("sup_id"));
					//20170310增加导入默认供应商字段,解决导入少是否默认字段导致的报错问题;
					supMap.put("is_default", 1);
					Map<String, Object> MatInvSupbyCode = matInvMapper.queryMatInvSupbyCode(supMap);
					
					if(MatInvSupbyCode == null){

						saveInvSupMaps.add(supMap);
					}
					
				}else {
					
					matInvService.addimp(map);
					
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
				
				matInvMapper.addMatInvSup(list1);
			}
		*/


		} catch (DataAccessException e) {

			e.printStackTrace();
			//System.out.println("==="+e);
			MatInv data_exc = new MatInv();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 04105 物资材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/addBatchMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatInv(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		int para = Integer.valueOf(MyConfig.getSysPara("04001"));
		
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

		Map<String, Object> MatTypeMapVo = new HashMap<String, Object>();

		MatTypeMapVo.put("group_id", SessionManager.getGroupId());

		MatTypeMapVo.put("hos_id", SessionManager.getHosId());

		MatTypeMapVo.put("copy_code", SessionManager.getCopyCode());

		MatTypeMapVo.put("is_stop", "0");

		List<Map<String, Object>>  saveInvMaps = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> saveInvSupMaps = new ArrayList<Map<String, Object>>();

		List<MatTypeDict> matTypeDict = matTypeDictMapper.queryMatTypeDict(MatTypeMapVo);

		List<MatInv> list_err = new ArrayList<MatInv>();

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

				MatInv matInv = new MatInv();

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				if (StringTool.isNotBlank(jsonObj.get("inv_code"))) {

					if(para ==1){
						
						mapVo.put("inv_code", "自动生成");
						
					}else if(para == 0){
						
						mapVo.put("inv_code", jsonObj.get("inv_code").toString());
					}
					matInv.setInv_code(jsonObj.get("inv_code").toString());

					

				} else {

					err_sb.append("物资材料编码为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("inv_name"))) {

					matInv.setInv_name(jsonObj.get("inv_name").toString());

					mapVo.put("inv_name", jsonObj.get("inv_name").toString());

				} else {

					err_sb.append("物资材料名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("alias"))) {

					matInv.setAlias(jsonObj.get("alias").toString());

					mapVo.put("alias", jsonObj.get("alias").toString());

					mapVo.put("alias_spell", StringTool.toPinyinShouZiMu(mapVo
							.get("alias").toString()));

				} else {

					matInv.setAlias("");

					mapVo.put("alias", "");

				}

				boolean bmatTypeDict = false;
				if (StringTool.isNotBlank(jsonObj.get("mat_type_code"))) {

					for (MatTypeDict matTypeDict1 : matTypeDict) {

						if (jsonObj.get("mat_type_code").toString()
								.equals(matTypeDict1.getMat_type_code())) {

							mapVo.put("mat_type_id",
									matTypeDict1.getMat_type_id());

							mapVo.put("mat_type_no",
									matTypeDict1.getMat_type_no());

							bmatTypeDict = true;

							break;

						}

					}

					if (bmatTypeDict == false) {

						err_sb.append("物资类别编码不存在 ");
					}

					matInv.setMat_type_code(jsonObj.get("mat_type_code")
							.toString());

					matInv.setMat_type_name(jsonObj.get("mat_type_name")
							.toString());

				} else {

					err_sb.append("物资类别编码为空 ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_model"))) {

					matInv.setInv_model(jsonObj.get("inv_model").toString());

					mapVo.put("inv_model", jsonObj.get("inv_model").toString());

				} else {

					matInv.setInv_model("");

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

					matInv.setUnit_code(jsonObj.get("unit_code").toString());

					matInv.setUnit_name(jsonObj.get("unit_name").toString());

				} else {

					matInv.setUnit_code("");

					matInv.setUnit_name("");

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

					matInv.setFac_code(jsonObj.get("fac_code").toString());

					matInv.setFac_name(jsonObj.get("fac_name").toString());

				} else {

					matInv.setFac_code("");

					matInv.setFac_name("");

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

					matInv.setAmortize_type(Integer.parseInt(jsonObj.get(
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

					matInv.setPrice_type(Integer.parseInt(jsonObj.get(
							"price_type").toString()));

				} else {

					err_sb.append("计价方法为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("plan_price"))) {

					matInv.setPlan_price(Double.parseDouble(jsonObj.get(
							"plan_price").toString()));

					mapVo.put("plan_price", Double.parseDouble(jsonObj.get(
							"plan_price").toString()));

				} else {

					matInv.setPlan_price(null);

					mapVo.put("plan_price", null);

				}

				if (StringTool.isNotBlank(jsonObj.get("price_rate"))) {

					matInv.setPrice_rate(Double.parseDouble(jsonObj.get(
							"price_rate").toString()));

					mapVo.put("price_rate", Double.parseDouble(jsonObj.get(
							"price_rate").toString()));

				} else {

					matInv.setPrice_rate(null);

					mapVo.put("price_rate", null);

				}
				if (StringTool.isNotBlank(jsonObj.get("sell_price"))) {

					matInv.setSell_price(Double.parseDouble(jsonObj.get(
							"sell_price").toString()));

					mapVo.put("sell_price", Double.parseDouble(jsonObj.get(
							"sell_price").toString()));

				} else {

					matInv.setSell_price(null);

					mapVo.put("sell_price", null);

				}

				if (StringTool.isNotBlank(jsonObj.get("is_single_ven"))) {

					matInv.setIs_single_ven(Integer.parseInt(jsonObj.get(
							"is_single_ven").toString()));

					mapVo.put("is_single_ven", Integer.parseInt(jsonObj.get(
							"is_single_ven").toString()));

				} else {

					err_sb.append("是否唯一供应商为空");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_batch"))) {

					matInv.setIs_batch(Integer.parseInt(jsonObj.get("is_batch")
							.toString()));

					mapVo.put("is_batch", Integer.parseInt(jsonObj.get(
							"is_batch").toString()));

				} else {

					err_sb.append("是否批次管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_bar"))) {

					matInv.setIs_bar(Integer.parseInt(jsonObj.get("is_bar")
							.toString()));

					mapVo.put("is_bar",
							Integer.parseInt(jsonObj.get("is_bar").toString()));

				} else {

					err_sb.append("是否条码管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_per_bar"))) {

					matInv.setIs_per_bar(Integer.parseInt(jsonObj.get(
							"is_per_bar").toString()));

					mapVo.put("is_per_bar", Integer.parseInt(jsonObj.get(
							"is_per_bar").toString()));

				} else {

					err_sb.append("是否个体条码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_cert"))) {

					matInv.setIs_cert(Integer.parseInt(jsonObj.get("is_cert")
							.toString()));

					mapVo.put("is_cert",
							Integer.parseInt(jsonObj.get("is_cert").toString()));

				} else {

					err_sb.append("是否证件管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_quality"))) {

					matInv.setIs_quality(Integer.parseInt(jsonObj.get(
							"is_quality").toString()));

					mapVo.put("is_quality", Integer.parseInt(jsonObj.get(
							"is_quality").toString()));

				} else {

					err_sb.append("是否保质期管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_disinfect"))) {

					matInv.setIs_disinfect(Integer.parseInt(jsonObj.get(
							"is_disinfect").toString()));

					mapVo.put("is_disinfect", Integer.parseInt(jsonObj.get(
							"is_disinfect").toString()));

				} else {

					err_sb.append("是否灭菌材料为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_com"))) {

					matInv.setIs_com(Integer.parseInt(jsonObj.get("is_com")
							.toString()));

					mapVo.put("is_com",
							Integer.parseInt(jsonObj.get("is_com").toString()));

				} else {

					err_sb.append("是否代销品为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_dura"))) {

					matInv.setIs_dura(Integer.parseInt(jsonObj.get("is_dura")
							.toString()));

					mapVo.put("is_dura",
							Integer.parseInt(jsonObj.get("is_dura").toString()));

				} else {

					err_sb.append("是否为耐用品为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_highvalue"))) {

					matInv.setIs_highvalue(Integer.parseInt(jsonObj.get(
							"is_highvalue").toString()));

					mapVo.put("is_highvalue", Integer.parseInt(jsonObj.get(
							"is_highvalue").toString()));

				} else {

					err_sb.append("是否高值为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_charge"))) {

					matInv.setIs_charge(Integer.parseInt(jsonObj.get(
							"is_charge").toString()));

					mapVo.put("is_charge", Integer.parseInt(jsonObj.get(
							"is_charge").toString()));

				} else {

					err_sb.append("是否收费为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_sec_whg"))) {

					matInv.setIs_sec_whg(Integer.parseInt(jsonObj.get(
							"is_sec_whg").toString()));

					mapVo.put("is_sec_whg", Integer.parseInt(jsonObj.get(
							"is_sec_whg").toString()));

				} else {

					err_sb.append("是否作二级库管理为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_shel_make"))) {

					matInv.setIs_shel_make(Integer.parseInt(jsonObj.get(
							"is_shel_make").toString()));

					mapVo.put("is_shel_make", Integer.parseInt(jsonObj.get(
							"is_shel_make").toString()));

				} else {

					err_sb.append("是否自制品为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sdate"))) {

					matInv.setSdate(DateUtil.stringToDate(jsonObj.get("sdate")
							.toString(), "yyyy-MM-dd"));

					mapVo.put("sdate", DateUtil.stringToDate(
							jsonObj.get("sdate").toString(), "yyyy-MM-dd"));

				} else {

					matInv.setSdate(null);

					mapVo.put("sdate", null);

				}

				if (StringTool.isNotBlank(jsonObj.get("edate"))) {

					matInv.setEdate(DateUtil.stringToDate(jsonObj.get("edate")
							.toString(), "yyyy-MM-dd"));

					mapVo.put("edate", DateUtil.stringToDate(
							jsonObj.get("edate").toString(), "yyyy-MM-dd"));

				} else {

					matInv.setEdate(null);

					mapVo.put("edate", null);

				}

				// /////////////////////////////////////////////////////////////////////////
				if (StringTool.isNotBlank(jsonObj.get("bar_code_new"))) {

					matInv.setBar_code_new(jsonObj.get("bar_code_new")
							.toString());

					mapVo.put("bar_code_new", jsonObj.get("bar_code_new")
							.toString());

				} else {

					matInv.setBar_code_new("");

					mapVo.put("bar_code_new", "");
					// err_sb.append("品种条码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("abc_type"))) {

					matInv.setAbc_type(jsonObj.get("abc_type").toString());

					mapVo.put("abc_type", jsonObj.get("abc_type").toString());

				} else {

					matInv.setAbc_type("");

					mapVo.put("abc_type", "");
					// err_sb.append("ABC分类为空 ");

				}

				if (StringTool.isNotBlank(jsonObj.get("per_weight"))) {

					matInv.setPer_weight(Double.parseDouble(jsonObj.get(
							"per_weight").toString()));

					mapVo.put("per_weight", Double.parseDouble(jsonObj.get(
							"per_weight").toString()));

				} else {

					// matInv.setPer_weight("");

					mapVo.put("per_weight", "");
					// err_sb.append("单位重量为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("per_volum"))) {

					matInv.setPer_volum(Double.parseDouble(jsonObj.get(
							"per_volum").toString()));

					mapVo.put("per_volum", Double.parseDouble(jsonObj.get(
							"per_volum").toString()));

				} else {

					matInv.setPer_volum(null);

					mapVo.put("per_volum", null);

					// err_sb.append("单位体积为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("brand_name"))) {

					matInv.setBrand_name(jsonObj.get("brand_name").toString());

					mapVo.put("brand_name", jsonObj.get("brand_name")
							.toString());

				} else {

					matInv.setBrand_name("");

					mapVo.put("brand_name", "");
					// err_sb.append("品牌为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("agent_name"))) {

					matInv.setAgent_name(jsonObj.get("agent_name").toString());

					mapVo.put("agent_name", jsonObj.get("agent_name")
							.toString());

				} else {

					matInv.setAgent_name("");

					mapVo.put("agent_name", "");

					// err_sb.append("代理商为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_structure"))) {

					matInv.setInv_structure(jsonObj.get("inv_structure")
							.toString());

					mapVo.put("inv_structure", jsonObj.get("inv_structure")
							.toString());

				} else {

					matInv.setInv_structure("");

					mapVo.put("inv_structure", "");
					// err_sb.append("材料结构为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_usage"))) {

					matInv.setInv_usage(jsonObj.get("inv_usage").toString());

					mapVo.put("inv_usage", jsonObj.get("inv_usage").toString());

				} else {

					matInv.setInv_usage("");

					mapVo.put("inv_usage", "");

					// err_sb.append("材料用途为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("use_state"))) {

					matInv.setUse_state(Integer.parseInt(jsonObj.get(
							"use_state").toString()));

					mapVo.put("use_state", Integer.parseInt(jsonObj.get(
							"use_state").toString()));

				} else {

					err_sb.append("是否在用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					matInv.setNote(jsonObj.get("note").toString());

					mapVo.put("note", jsonObj.get("note").toString());

				} else {

					matInv.setNote("");

					mapVo.put("note", "");

					// err_sb.append("备注为空  ");
				}

				// 审核状态
				mapVo.put("state", "0");

				if (StringTool.isNotBlank(jsonObj.get("is_bid"))) {

					matInv.setIs_bid(Integer.parseInt(jsonObj.get("is_bid")
							.toString()));

					mapVo.put("is_bid",
							Integer.parseInt(jsonObj.get("is_bid").toString()));

				} else {

					matInv.setIs_bid(null);

					mapVo.put("is_bid", null);

					// err_sb.append("是否中标为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_date"))) {

					matInv.setBid_date(DateUtil.stringToDate(
							jsonObj.get("bid_date").toString(), "yyyy-MM-dd"));

					mapVo.put("bid_date", DateUtil.stringToDate(
							jsonObj.get("bid_date").toString(), "yyyy-MM-dd"));

				} else {

					matInv.setBid_date(null);

					mapVo.put("bid_date", null);

					// err_sb.append("中标日期为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("bid_code"))) {

					matInv.setBid_code(jsonObj.get("bid_code").toString());

					mapVo.put("bid_code", jsonObj.get("bid_code").toString());

				} else {

					matInv.setBid_code("");

					mapVo.put("bid_code", "");

					// err_sb.append("项目中标编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_involved"))) {

					matInv.setIs_involved(Integer.parseInt(jsonObj.get(
							"is_involved").toString()));

					mapVo.put("is_involved", jsonObj.get("is_involved")
							.toString());
				} else {

					matInv.setIs_involved(null);

					mapVo.put("is_involved", null);

					// err_sb.append("是否介入为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_implant"))) {

					matInv.setIs_implant(Integer.parseInt(jsonObj.get(
							"is_implant").toString()));

					mapVo.put("is_implant", jsonObj.get("is_implant")
							.toString());
				} else {

					matInv.setIs_implant(null);

					mapVo.put("is_implant", null);

					// err_sb.append("是否植入为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("stora_tran_cond"))) {

					matInv.setStora_tran_cond(jsonObj.get("stora_tran_cond")
							.toString());

					mapVo.put("stora_tran_cond", jsonObj.get("stora_tran_cond")
							.toString());

				} else {

					matInv.setStora_tran_cond(null);

					mapVo.put("stora_tran_cond", null);

					// err_sb.append("储运条件为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_zero_store"))) {

					matInv.setIs_zero_store(Integer.parseInt(jsonObj.get(
							"is_zero_store").toString()));

					mapVo.put("c", Integer.parseInt(jsonObj
							.get("is_zero_store").toString()));

				} else {

					matInv.setIs_zero_store(null);

					mapVo.put("is_zero_store", null);

					// err_sb.append("是否零库存管理为空  ");
				}
				
				if (StringTool.isNotBlank(jsonObj.get("source_plan"))) {

					matInv.setSource_plan(Integer.parseInt(jsonObj.get(
							"source_plan").toString()));

					mapVo.put("c", Integer.parseInt(jsonObj
							.get("source_plan").toString()));

				} else {

					matInv.setSource_plan(null);

					mapVo.put("source_plan", null);

				}
				
				if (StringTool.isNotBlank(jsonObj.get("is_inv_bar"))) {

					matInv.setIs_inv_bar(Integer.parseInt(jsonObj.get(
							"is_inv_bar").toString()));

					mapVo.put("c", Integer.parseInt(jsonObj
							.get("is_inv_bar").toString()));

				} else {

					matInv.setIs_inv_bar(null);

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

						Map<String, Object> data_exc_extis = matInvService
								.queryByUniqueness(excDictMapVo);

						if (data_exc_extis != null) {

							err_sb.append("编码已经存在");

						}

					}
				}
				if (err_sb.toString().length() > 0) {

					matInv.setError_type(err_sb.toString());

					list_err.add(matInv);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo
							.get("inv_name").toString()));

					mapVo.put("wbx_code",
							StringTool.toWuBi(mapVo.get("inv_name").toString()));



				}

			}
			
	            for(Map<String, Object> map :saveInvMaps){
				
				if(map.get("sup_id")!=null){
					
					matInvService.add(map);
					
					map.put("inv_id", matInvMapper.queryMatInvSeqCur());
					
					saveInvSupMaps.add(map);
					
					
				}else {
					
					matInvService.add(map);
					
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
				
				matInvMapper.addMatInvSup(list1);
			}
		

		} catch (DataAccessException e) {

			MatInv data_exc = new MatInv();

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
	 * @Description 导入数据 04105 物资材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/readMatInvSupFiles", method = RequestMethod.POST)
	public String readMatInvVenFiles(Plupload plupload,
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

						Map<String, Object> mat_inv = matInvService
								.queryByUniqueness(invMapVo);

						if (mat_inv != null) {

							mapVo.put("inv_id", mat_inv.get("inv_id"));

						} else {

							err_sb.append("物资材料编码不存在");
						}

						error_mapVo.put("inv_code", temp[0]);

					} else {

						err_sb.append("物资材料编码为空");

					}

					if (StringTool.isNotBlank(temp[1])) {

						error_mapVo.put("inv_name", temp[1]);

					} else {

						err_sb.append("物资材料名称为空");

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

						Map<String, Object> ex = matInvMapper
								.queryMatInvSupbyCode(mapVo);

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

					matInvMapper.addMatInvSup(supList);

					supList.clear();
				}
			}

		} catch (DataAccessException e) {

			e.printStackTrace();
			//System.out.println(">>>"+e);
			Map<String, Object> error_mapVo = new HashMap<String, Object>();

			error_mapVo.put("error_type", "导入系统出错");

			list_err.add(error_mapVo);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 04105 物资材料表
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/addBatchMatInvSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatInvVen(
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

					Map<String, Object> mat_inv = matInvService
							.queryByUniqueness(invMapVo);

					if (mat_inv != null) {

						mapVo.put("inv_id", mat_inv.get("inv_id"));

					} else {

						err_sb.append("物资材料编码不存在");
					}

					error_mapVo.put("inv_code", jsonObj.get("inv_code")
							.toString());

				} else {

					err_sb.append("物资材料编码为空");

				}

				if (StringTool.isNotBlank(jsonObj.get("inv_name"))) {

					error_mapVo.put("inv_name", jsonObj.get("inv_name")
							.toString());

				} else {

					err_sb.append("物资材料名称为空");

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

				Map<String, Object> ex = matInvMapper
						.queryMatInvSupbyCode(mapVo);

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
				matInvMapper.addMatInvSup(supList);
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
					|| arr[colunmNum].length() != 0
					|| !("").equals(arr[colunmNum])) {

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
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryByUniqueness", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryByUniqueness(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		 Map<String, Object> vmapVo=new HashMap<String, Object>();
		 vmapVo.put("group_id", SessionManager.getGroupId());
		 vmapVo.put("hos_id", SessionManager.getHosId());
		 vmapVo.put("copy_code", SessionManager.getCopyCode());
		 vmapVo.put("inv_name", mapVo.get("inv_name"));
		 
		List<Map<String, Object>> inv= (List<Map<String, Object>>) matInvService.queryExists(vmapVo);
		if(inv.size()>0){
			return JSONObject
					.parseObject("{\"info\":\"1\",\"state\":\"true\"}");
		}else{
			
			return JSONObject
					.parseObject("{\"info\":\"0\",\"state\":\"true\"}");
		}
	}
	
	//材料修改时   材料证件信息页面跳转
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvCertInfoMainPage", method = RequestMethod.GET)
	public String matInvCertInfoMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		if (mapVo.get("group_id") != null && !"".equals(mapVo.get("group_id"))) {
			mode.addAttribute("group_id", mapVo.get("group_id"));
		}
		if (mapVo.get("hos_id") != null && !"".equals(mapVo.get("hos_id"))) {
			mode.addAttribute("hos_id", mapVo.get("hos_id"));
		}
		if (mapVo.get("copy_code") != null && !"".equals(mapVo.get("copy_code"))) {
			mode.addAttribute("copy_code", mapVo.get("copy_code"));
		}
		if (mapVo.get("inv_id") != null && !"".equals(mapVo.get("inv_id"))) {
			mode.addAttribute("inv_id", mapVo.get("inv_id"));
		}
		if (mapVo.get("fac_id") != null && !"".equals(mapVo.get("fac_id"))) {
			mode.addAttribute("fac_id", mapVo.get("fac_id"));
		}
		if (mapVo.get("fac_no") != null && !"".equals(mapVo.get("fac_no"))) {
			mode.addAttribute("fac_no", mapVo.get("fac_no"));
		}
		if (mapVo.get("fac_text") != null && !"".equals(mapVo.get("fac_text"))) {
			mode.addAttribute("fac_text", mapVo.get("fac_text"));
		}
		
		Map<String,Object> supMap = new HashMap<String,Object>();
		supMap = matInvService.queryMatInvSupDefault(mapVo);
		if(supMap != null){
			mode.addAttribute("sup_id", supMap.get("sup_id"));
			mode.addAttribute("sup_no", supMap.get("sup_no"));
			mode.addAttribute("sup_code", supMap.get("sup_code"));
			mode.addAttribute("sup_name", supMap.get("sup_name"));
		}else{
			mode.addAttribute("sup_id", "");
			mode.addAttribute("sup_no",  "");
			mode.addAttribute("sup_code",  "");
			mode.addAttribute("sup_name",  "");
		}
		
		if("1".equals(mapVo.get("is_main"))){
			mode.addAttribute("is_main", 1);
		}
		return "hrp/mat/info/basic/inv/matInvCertInfoMain";
	}
	
	//材料添加时   材料证件信息页面跳转
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvCertInfoAddPage", method = RequestMethod.GET)
	public String matInvCertInfoAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "hrp/mat/info/basic/inv/matInvCertInfoAdd";
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/inv/addCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCertSup(@RequestParam Map<String, Object> entityMap,Model mode) throws Exception {

		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		
		String matInvJson;
		
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
						if(jsonObj.get("sup_id") == null || dataMap.put("sup_id", "") == ""){
							dataMap.put("sup_id", "");
						}else{
							dataMap.put("sup_id", jsonObj.get("sup_id"));
						}
						
						dataMap.put("cert_date", "");
						dataMap.put("issuing_authority", "");
						dataMap.put("link_person", "");
						dataMap.put("link_tel", "");
						dataMap.put("link_mobile", "");
						
						dataMap.put("cert_memo", "");
						dataMap.put("file_path", "");
						dataMap.put("file_address", "");
						
						//dataMap.put("supData", jsonObj.get("supData"));
						
						if("1".equals(entityMap.get("is_main"))){
							dataMap.put("inv_id", entityMap.get("inv_id"));
						}
						
						dataList.add(dataMap);
						
					}
				}
			}
			if(dataList.size()>0){
				matInvJson = matInvCertService.addBatchMatCertSup(dataList);
			}else{
				matInvJson = "{\"error\":\"没有需要保存的数据.\",\"state\":\"true\"}";
			}
			
		}catch (Exception e) {
			matInvJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matInvJson);
					
	}
	
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvCertInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvCertInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInv = matInvService.queryMatInvCertInfo(getPage(mapVo));

		return JSONObject.parseObject(matInv);
	}
	
	/**
	 * 材料改变物资类别查询改类别材料数
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryChangeMatTypeCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChangeMatTypeCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInv = matInvService.queryChangeMatTypeCode(mapVo);

		return JSONObject.parseObject(matInv);
	}
	
	/**
	 * 材料停用，验证库存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryStopTimeByCode", method = RequestMethod.POST)
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
		String matInv = matInvService.queryStopTimeByCode(mapVo);

		return JSONObject.parseObject(matInv);
	}
	
	
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryHosFacDict", method = RequestMethod.POST)
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
		String hrpMatSelect = matInvService.queryHosFacDict(mapVo);
		return hrpMatSelect;
	}
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvPrintSetPage", method = RequestMethod.GET)
	public String matInvPrintSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
	@RequestMapping(value = "/hrp/mat/info/basic/inv/queryMatInvByPrintTemlate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvByPrintTemlate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		String reJson=null;
		try {
			reJson=matInvService.queryMatInvByPrintTemlate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/inv/matInvImportNewPage", method = RequestMethod.GET)
	public String matInvImportNewPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/inv/matInvImportNew";
	}
	
	/**
	 * @Description 
	 * 导入数据 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hrp/mat/info/basic/inv/import",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> importData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=matInvService.importData(mapVo);
			
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	}
	
}
