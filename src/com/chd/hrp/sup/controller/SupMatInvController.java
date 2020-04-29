/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.controller;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeDictMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.base.MatInvPictureService;
import com.chd.hrp.mat.service.info.basic.MatInvCertService;
import com.chd.hrp.mat.service.info.basic.MatInvService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreInvServiceImpl;
import com.chd.hrp.mat.serviceImpl.info.basic.MatStoreTypeServiceImpl;
import com.chd.hrp.sup.entity.SupMatInv;
import com.chd.hrp.sup.service.SupMatInvService;
import com.chd.hrp.sys.dao.FacDictMapper;
import com.chd.hrp.sys.dao.SupDictMapper;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.service.SupDictService;

/**
 * @Description: 100003 物资材料表
 * @Table: SUP_MAT_INV
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class SupMatInvController extends BaseController {

	private static Logger logger = Logger.getLogger(SupMatInvController.class);

	// 引入Service服务
	@Resource(name = "supMatInvService")
	private final SupMatInvService supMatInvService = null;


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
	@RequestMapping(value = "/hrp/sup/supmatinv/supMatInvMainPage", method = RequestMethod.GET)
	public String supMatInvMainPage(Model mode) throws Exception {

		return "hrp/sup/supmatinv/supMatInvMain";

	}

	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/supMatInvAuditAddPage", method = RequestMethod.GET)
	public String supMatInvAuditAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", SessionManager.getGroupId());
		map.put("hos_id", SessionManager.getHosId());
		map.put("copy_code", SessionManager.getCopyCode());

		mode.addAttribute("para_04001", MyConfig.getSysPara("04001"));

		if (mapVo.get("acct_year") == null) {
			map.put("acct_year", SessionManager.getAcctYear());
		}
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());

		SupMatInv supMatInv = new SupMatInv();

		supMatInv = supMatInvService.queryByCode(mapVo);

		mode.addAttribute("group_id", supMatInv.getGroup_id());
		mode.addAttribute("hos_id", supMatInv.getHos_id());
		mode.addAttribute("copy_code", supMatInv.getCopy_code());
		mode.addAttribute("inv_id", supMatInv.getInv_id());
		mode.addAttribute("inv_code", supMatInv.getInv_code());
		mode.addAttribute("inv_name", supMatInv.getInv_name());
		mode.addAttribute("alias", supMatInv.getAlias());
		mode.addAttribute("spell_code", supMatInv.getSpell_code());
		mode.addAttribute("wbx_code", supMatInv.getWbx_code());
		mode.addAttribute("mat_type_id", supMatInv.getMat_type_id());
		mode.addAttribute("mat_type_no", supMatInv.getMat_type_no());
		mode.addAttribute("mat_type_code", supMatInv.getMat_type_code());
		mode.addAttribute("mat_type_name", supMatInv.getMat_type_name());
		mode.addAttribute("inv_model", supMatInv.getInv_model());
		mode.addAttribute("unit_code", supMatInv.getUnit_code());
		mode.addAttribute("unit_name", supMatInv.getUnit_name());
		mode.addAttribute("fac_id", supMatInv.getFac_id());
		mode.addAttribute("fac_name", supMatInv.getFac_name());
		mode.addAttribute("plan_price", supMatInv.getPlan_price());
		mode.addAttribute("price_rate", supMatInv.getPrice_rate());
		mode.addAttribute("sell_price", supMatInv.getSell_price());
		mode.addAttribute("brand_name", supMatInv.getBrand_name());
		mode.addAttribute("agent_name", supMatInv.getAgent_name());
		mode.addAttribute("note", supMatInv.getNote());
		mode.addAttribute("state", supMatInv.getState());
		mode.addAttribute("check_date", supMatInv.getCheck_date());
		mode.addAttribute("checker", supMatInv.getChecker());
		mode.addAttribute("sup_id", supMatInv.getSup_id());
		mode.addAttribute("sup_code", supMatInv.getSup_code());
		mode.addAttribute("sup_name", supMatInv.getSup_name());

		return "hrp/sup/supmatinv/matInvAdd";
	}
	
	
	/**
	 * @Description 变更查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/supMatInvAuditUpdatePage", method = RequestMethod.GET)
	public String supMatInvAuditUpdatePage(@RequestParam Map<String, Object> mapVo,
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

		Map<String, Object> matInvDict = matInvService.queryByCode(mapVo);
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
		mode.addAttribute("matInv", matInvDict);
		
		mode.addAttribute("para_04001", MyConfig.getSysPara("04001"));


		return "hrp/sup/supmatinv/matInvUpdate";
	}

	
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/supMatInvAddPage", method = RequestMethod.GET)
	public String supMatInvAddPage(Model mode) throws Exception {

		return "hrp/sup/supmatinv/supMatInvAdd";

	}

	/**
	 * @Description 添加数据 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/addSupMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("inv_name").toString()));

		String supMatInvJson = supMatInvService.add(mapVo);
		
		mode.addAttribute("para_04001", MyConfig.getSysPara("04001"));

		return JSONObject.parseObject(supMatInvJson);

	}

	/**
	 * @Description 更新跳转页面 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/supMatInvUpdatePage", method = RequestMethod.GET)
	public String supMatInvUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());

		SupMatInv supMatInv = new SupMatInv();

		supMatInv = supMatInvService.queryByCode(mapVo);

		mode.addAttribute("group_id", supMatInv.getGroup_id());
		mode.addAttribute("hos_id", supMatInv.getHos_id());
		mode.addAttribute("copy_code", supMatInv.getCopy_code());
		mode.addAttribute("inv_id", supMatInv.getInv_id());
		mode.addAttribute("inv_code", supMatInv.getInv_code());
		mode.addAttribute("inv_name", supMatInv.getInv_name());
		mode.addAttribute("alias", supMatInv.getAlias());
		mode.addAttribute("spell_code", supMatInv.getSpell_code());
		mode.addAttribute("wbx_code", supMatInv.getWbx_code());
		mode.addAttribute("mat_type_id", supMatInv.getMat_type_id());
		mode.addAttribute("mat_type_code", supMatInv.getMat_type_code());
		mode.addAttribute("mat_type_name", supMatInv.getMat_type_name());
		mode.addAttribute("inv_model", supMatInv.getInv_model());
		mode.addAttribute("unit_code", supMatInv.getUnit_code());
		mode.addAttribute("unit_name", supMatInv.getUnit_name());
		mode.addAttribute("fac_id", supMatInv.getFac_id());
		mode.addAttribute("fac_name", supMatInv.getFac_name());
		mode.addAttribute("plan_price", supMatInv.getPlan_price());
		mode.addAttribute("price_rate", supMatInv.getPrice_rate());
		mode.addAttribute("sell_price", supMatInv.getSell_price());
		mode.addAttribute("brand_name", supMatInv.getBrand_name());
		mode.addAttribute("agent_name", supMatInv.getAgent_name());
		mode.addAttribute("note", supMatInv.getNote());
		mode.addAttribute("state", supMatInv.getState());
		mode.addAttribute("check_date", supMatInv.getCheck_date());
		mode.addAttribute("checker", supMatInv.getChecker());
		mode.addAttribute("sup_id", supMatInv.getSup_id());
		
		mode.addAttribute("para_04001", MyConfig.getSysPara("04001"));

		return "hrp/sup/supmatinv/supMatInvUpdate";
	}

	/**
	 * @Description 更新数据 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/updateSupMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("inv_name").toString()));

		String supMatInvJson = supMatInvService.update(mapVo);

		return JSONObject.parseObject(supMatInvJson);
	}
	/**
	 * @Description 更新数据 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/updateSupState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupState(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("state", "1");
		mapVo.put("check_date", DateUtil.getCurrenDate());
		mapVo.put("checker", SessionManager.getUserId());
		
		String supMatInvJson = supMatInvService.update(mapVo);
		
		return JSONObject.parseObject(supMatInvJson);
	}

	/**
	 * @Description 更新数据 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/addOrUpdateSupMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateSupMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String supMatInvJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		mapVo.put("sup_id", SessionManager.getSupId());
		mapVo.put("sup_no", SessionManager.getSupNo());
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("inv_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("inv_name").toString()));

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			detailVo.put("sup_id", SessionManager.getSupId());
			detailVo.put("sup_no", SessionManager.getSupNo());

			detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("inv_name").toString()));

			detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("inv_name").toString()));

			supMatInvJson = supMatInvService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(supMatInvJson);
	}

	/**
	 * @Description 删除数据 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/deleteSupMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupMatInv(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

		String supMatInvJson = supMatInvService.deleteBatch(listVo);

		return JSONObject.parseObject(supMatInvJson);

	}

	/**
	 * @Description 查询数据 100003 物资材料表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/querySupMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupMatInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if(SessionManager.getSupId() != null && !"".equals(SessionManager.getSupId())){
			mapVo.put("sup_id", SessionManager.getSupId());
			mapVo.put("sup_no", SessionManager.getSupNo());
		}
		String supMatInv = supMatInvService.query(getPage(mapVo));

		return JSONObject.parseObject(supMatInv);

	}
	/**
	 * @Description 查询材料供应商
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supmatinv/queryMatInvSup", method = RequestMethod.POST)
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

		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sup_id", mapVo.get("sup_id"));
		map.put("sup_code", mapVo.get("sup_code"));
		map.put("sup_name", mapVo.get("sup_name"));
		map.put("is_default", "0");
		list.add(map);
		String msg=ChdJson.toJsonLower(list);
		return JSONObject.parseObject(msg);
	}

}
