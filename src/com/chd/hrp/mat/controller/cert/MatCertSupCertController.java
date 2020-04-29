
package com.chd.hrp.mat.controller.cert;

import java.util.HashMap;
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
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.cert.MatCertSupCertService;

@Controller
@RequestMapping(value="/hrp/mat/cert/sup")
public class MatCertSupCertController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatCertSupCertController.class);
	
	//引入Service服务
	@Resource(name = "matCertSupCertService")
	private final MatCertSupCertService matCertSupCertService = null;

	//供应商证件页面跳转
	@RequestMapping(value = "/matSupCertListPage", method = RequestMethod.GET)
	public String matSupCertListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/sup/matSupCertList";
	}
	
	//供应商证件维护页面跳转
	@RequestMapping(value = "/matSupCertEditPage", method = RequestMethod.GET)
	public String matSupCertEditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		return "hrp/mat/cert/sup/matSupCertEdit";
	}
	
	//供应商证件添加页面跳转
	@RequestMapping(value = "/matSupCertAddPage", method = RequestMethod.GET)
	public String matSupCertAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		return "hrp/mat/cert/sup/matSupCertAdd";
	}
	
	//供应商证件修改页面跳转
	@RequestMapping(value = "/matSupCertUpdatePage", method = RequestMethod.GET)
	public String matSupCertUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> supCertMap = matCertSupCertService.queryMatSupCertById(mapVo);
		
		mode.addAttribute("supCert", supCertMap);
		
		return "hrp/mat/cert/sup/matSupCertUpdate";
	}
	
	//供应商材料页面跳转
	@RequestMapping(value = "/matSupCertInvPage", method = RequestMethod.GET)
	public String matSupCertInvPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		mode.addAttribute("sup_name", mapVo.get("sup_name"));
		
		return "hrp/mat/cert/sup/matSupCertInv";
	}
	
	//供应商查询
	@RequestMapping(value = "/queryMatSupCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupCert(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matCertSupCertService.queryMatSupCert(getPage(mapVo));
		
		return JSONObject.parseObject(retJson);
	}
	
	//供应商证件查询
	@RequestMapping(value = "/queryMatSupCertInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupCertInfo(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matCertSupCertService.queryMatSupCertInfo(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
	
	//供应商材料查询
	@RequestMapping(value = "/queryMatSupCertInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSupCertInv(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matCertSupCertService.queryMatSupCertInv(getPage(mapVo));
		
		return JSONObject.parseObject(retJson);
	}
	
	//供应商证件保存
	@RequestMapping(value = "/saveMatSupCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatSupCert(
			@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertSupCertService.saveMatSupCert(mapVo, request, response);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//供应商证件更新
	@RequestMapping(value = "/updateMatSupCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatSupCert(
			@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> retMap = null;
		try{
			retMap = matCertSupCertService.updateMatSupCert(mapVo, request, response);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//供应商证件删除
	@RequestMapping(value = "/deleteMatSupCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatSupCert(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertSupCertService.deleteMatSupCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//供应商证件认证
	@RequestMapping(value = "/authentMatCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> authentMatCertSup(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("oper", "authent");
			retMap = matCertSupCertService.updateMatCertSupState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//供应商证件取消认证
	@RequestMapping(value = "/unAuthentMatCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuthentMatCertSup(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("oper", "unAuthent");
			retMap = matCertSupCertService.updateMatCertSupState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}	
	
	//供应商证件审核
	@RequestMapping(value = "/checkMatCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMatCertSup(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("oper", "check");
			retMap = matCertSupCertService.updateMatCertSupState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
		
	//供应商证件取消审核
	@RequestMapping(value = "/unCheckMatCertSup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unCheckMatCertSup(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;
	
		try{
			mapVo.put("oper", "unCheck");
			retMap = matCertSupCertService.updateMatCertSupState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}	
	
	//供应商证件取消审核
	@RequestMapping(value = "/queryCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCertType(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = matCertSupCertService.queryCertType(mapVo);	
			
		return retMap;
	}	
}

