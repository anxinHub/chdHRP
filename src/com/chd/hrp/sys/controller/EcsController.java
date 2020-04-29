package com.chd.hrp.sys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.ChdToken;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.sys.service.EcsService;

@Controller
public class EcsController extends BaseController{

	private static Logger logger = Logger.getLogger(SupController.class);
	
	@Resource(name="ecsService")
	private  final EcsService ecsService = null; 
	
	/**
	 * @Description 
	 * 供应商证件主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/sys/ecs/sup/querySupCertPage", method = RequestMethod.GET)
	public String matSupCertPage(Model mode) throws Exception {
		
		return "hrp/mat/ecs/supCert/main";
	}
	
	/**
	 * @Description 
	 * 供应商产品主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/sys/ecs/sup/querySupProdPage", method = RequestMethod.GET)
	public String matSupProdSpecPage(Model mode) throws Exception {
		return "hrp/mat/ecs/supPro/main";
	}
	/**
	 * @Description 
	 * 供应商产品审核通w过页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/matSupProdAuditPage", method = RequestMethod.GET)
	public String matSupProdAudit(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		
		return "hrp/mat/ecs/supPro/matSupProdAudit";
	}
	/**
	 * @Description 
	 * 供应商产品审核未通过页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/matSupProdNoPassPage", method = RequestMethod.GET)
	public String matSupProdNoSass(Model mode) throws Exception {
		return "hrp/mat/ecs/supPro/matSupProdNoPass";
	}
	/**
	 * @Description 
	 * 产品明细页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/matSupProdUpdatePage", method = RequestMethod.GET)
	public String matSupProdUpdate( @RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> map=ecsService.querySupProdSpecStateById(mapVo);
		mode.addAttribute("sup_name",map.get("SUP_NAME"));
		mode.addAttribute("inv_name",map.get("INV_NAME"));
		mode.addAttribute("fac_name",map.get("FAC_NAME"));
		mode.addAttribute("prod_type_name1",map.get("PROD_TYPE_NAME1"));
		mode.addAttribute("prod_type_name2",map.get("PROD_TYPE_NAME2"));
		mode.addAttribute("prod_type_name3",map.get("PROD_TYPE_NAME3"));
		mode.addAttribute("prod_type_name4",map.get("PROD_TYPE_NAME4"));
		mode.addAttribute("cert_code",map.get("CERT_CODE"));
		mode.addAttribute("cert_batch",map.get("CERT_BATCH"));
		mode.addAttribute("start_date",map.get("START_DATE"));
		mode.addAttribute("end_date",map.get("END_DATE"));
		mode.addAttribute("is_long",map.get("IS_LONG"));
		mode.addAttribute("contents",map.get("CONTENTS"));
		mode.addAttribute("indications",map.get("INDICATIONS"));
		mode.addAttribute("standard",map.get("STANDARD"));
		mode.addAttribute("taboo",map.get("TABOO"));
		mode.addAttribute("alias1",map.get("ALIAS1"));
		mode.addAttribute("origin_type",map.get("ORIGIN_TYPE"));
		mode.addAttribute("brand_name",map.get("BRAND_NAME"));
		mode.addAttribute("origin_name",map.get("ORIGIN_NAME"));
		mode.addAttribute("is_cold",map.get("IS_COLD"));
		mode.addAttribute("stora_term",map.get("STORA_TERM"));
		mode.addAttribute("spec_name",map.get("SPEC_NAME"));
		mode.addAttribute("term_name",map.get("TERM_NAME"));
		mode.addAttribute("spec_name_sec",map.get("SPEC_NAME_SEC"));
		mode.addAttribute("packages",map.get("PACKAGE"));//PACKAGE特殊字符
		mode.addAttribute("is_impl",map.get("IS_IMPL"));
		mode.addAttribute("bar_code",map.get("BAR_CODE"));
		mode.addAttribute("tender_name",map.get("TENDER_NAME"));
		mode.addAttribute("bid_code",map.get("BID_CODE"));
		mode.addAttribute("price",map.get("PRICE"));
		mode.addAttribute("check_state",map.get("CHECK_STATE"));
		return "hrp/mat/ecs/supPro/matSupProdUpdate";
	}
	/**
	 * @Description 
	 * 数据查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/queryMatSupProdSpec", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatSupProdSpec( @RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String MatSupProdSpec = ecsService.queryMatSupProdSpec(getPage(mapVo));
		
		return MatSupProdSpec;
	}
	/**
	 * @Description 
	 * 数据查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/addMatSupProdSpecInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMatSupProdSpecInv( @RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		Map<String,Object> MatSupProdSpec = ecsService.addMatSupProdSpecInv(mapVo);
		return MatSupProdSpec;
	}
	/**
	 * @Description 
	 * 数据查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/queryMatInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatInvList( @RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String MatSupProdSpec = ecsService.queryMatInvList(getPage(mapVo));
		return JSON.parseObject(MatSupProdSpec);
	}
	/**
	 * @Description 
	 * 新增产品页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/addInvPage", method = RequestMethod.GET)
	public String matAddInvNew(Model mode) throws Exception {
		
		mode.addAttribute("p04001", MyConfig.getSysPara("04001"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/ecs/supPro/addInv";
	}
	


	/**
	 * @Description 保存产品
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/sup/addMatInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInv(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> retMap;
		try {
			retMap = ecsService.addMatInv(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}

		return retMap;
	}

	/**
	 * @Description 
	 * 送货单页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/querySupDeliveryPage", method = RequestMethod.GET)
	public String supDeilveryMain(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/ecs/supDeli/main";
	}

	/**
	 * @Description 
	 * 送货单入库添加页面
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/addInPage", method = RequestMethod.GET)
	public String addInPage(Model mode) throws Exception {
		return "hrp/mat/ecs/supDeli/addIn";
	}

	/**
	 * @Description 
	 * 送货单查询
	 * @param  mode 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/queryMatDeliveryList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatDeliveryList( @RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = ecsService.queryMatDeliveryList(getPage(mapVo));
		
		return JSON.parseObject(retJson);
	}

	/**
	 * @Description 
	 * 送货单主表精准查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/queryMatDeliveryMainByCode", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> queryMatDeliveryMainByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		Map<String, Object> retMap;
		try {
			retMap = ecsService.queryMatDeliveryMainByCode(mapVo);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 
	 * 送货单明细查询
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/queryMatDeliveryDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatDeliveryDetailByCode( @RequestParam Map<String,Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId()); 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = ecsService.queryMatDeliveryDetailByCode(mapVo);
		
		return JSON.parseObject(retJson);
	}

	/**
	 * @Description 
	 * 送货单签收/作废
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/updateMatDeliveryState", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> updateMatDeliveryState(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		Map<String, Object> retMap;
		try {
			retMap = ecsService.updateMatDeliveryState(mapVo);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}

	/**
	 * @Description 
	 * 新增材料入库
	 * @param  mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sys/ecs/supDeli/addMatInByDelivery", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> addMatInByDelivery(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		Map<String, Object> retMap;
		try {
			retMap = ecsService.addMatInByDelivery(mapVo);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
}
