
package com.chd.hrp.mat.controller.cert;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.hrp.mat.service.cert.MatProdCertService;

@Controller
@RequestMapping(value="/hrp/mat/cert/prod")
public class MatProdCertController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatProdCertController.class);
	
	//引入Service服务
	@Resource(name = "matProdCertService")
	private final MatProdCertService matProdCertService = null;

	//产品注册证页面跳转
	@RequestMapping(value = "/matProdCertListPage", method = RequestMethod.GET)
	public String matProdCertListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCertList";
	}
	
	//产品注册证列表查询
	@RequestMapping(value = "queryMatProdCertList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdCertList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdCertService.queryMatProdCertList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	//产品注册证查询
	@RequestMapping(value = "/queryMatProdCertById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdCertById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.queryMatProdCertById(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证维护页面跳转
	@RequestMapping(value = "/matProdCertPage", method = RequestMethod.GET)
	public String matProdCertPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCert";
	}
	
	//产品注册证保存
	@RequestMapping(value = "/saveMatProdCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatProdCert(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.saveMatProdCert(mapVo, request, response);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证删除
	@RequestMapping(value = "/deleteMatProdCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatProdCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.deleteMatProdCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证认证
	@RequestMapping(value = "/updateMatProdCertToAuthent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdCertToAuthent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.updateMatProdCertToAuthent(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证取消认证
	@RequestMapping(value = "/updateMatProdCertToUnAuthent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdCertToUnAuthent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.updateMatProdCertToUnAuthent(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证审核
	@RequestMapping(value = "/updateMatProdCertToCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdCertToCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.updateMatProdCertToCheck(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证消审
	@RequestMapping(value = "/updateMatProdCertToUnCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdCertToUnCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.updateMatProdCertToUnCheck(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证导入页面跳转
	@RequestMapping(value = "/matProdCertImportPage", method = RequestMethod.GET)
	public String matProdCertImportPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCertImport";
	}
	//产品注册证导入
	@RequestMapping(value = "/addMatProdCertByImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatProdCertByImp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.addMatProdCertByImp(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}

	
	//产品注册证选择材料维护页面跳转
	@RequestMapping(value = "/matInvListPage", method = RequestMethod.GET)
	public String matInvListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matInvList";
	}
	
	//产品注册证选择材料列表查询
	@RequestMapping(value = "queryMatInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdCertService.queryMatInvList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}

	//产品注册证关联材料页面跳转
	@RequestMapping(value = "/matProdCertInvPage", method = RequestMethod.GET)
	public String matProdCertInvPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCertInv";
	}
	
	//产品注册证关联材料列表查询
	@RequestMapping(value = "queryMatProdCertInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdCertInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdCertService.queryMatProdCertInvList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	//产品注册证关联材料
	@RequestMapping(value = "/saveMatProdCertInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatProdCertInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.saveMatProdCertInv(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	

	//产品注册证更换新证页面跳转
	@RequestMapping(value = "/matProdCertCopyPage", method = RequestMethod.GET)
	public String matProdCertCopyPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCertCopy";
	}

	//产品注册证查看新老证页面跳转
	@RequestMapping(value = "/matProdCertOtherPage", method = RequestMethod.GET)
	public String matProdCertOtherPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCertOther";
	}

	//产品注册证选择新证页面跳转
	@RequestMapping(value = "/matProdCertChoosePage", method = RequestMethod.GET)
	public String matProdCertChoosePage(Model mode) throws Exception {
		
		return "hrp/mat/cert/prod/matProdCertChoose";
	}
	
	//产品注册证选择新证查询
	@RequestMapping(value = "queryMatProdCertChooseList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdCertChooseList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdCertService.queryMatProdCertChooseList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	//产品注册证绑定新证
	@RequestMapping(value = "/updateMatProdCertToNewCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdCertToNewCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.updateMatProdCertToNewCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//产品注册证取消绑定新证
	@RequestMapping(value = "/updateMatProdCertToUnNewCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdCertToUnNewCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdCertService.updateMatProdCertToUnNewCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

