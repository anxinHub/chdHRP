
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
import com.chd.hrp.mat.service.cert.MatCertFacCertService;

@Controller
@RequestMapping(value="/hrp/mat/cert/fac")
public class MatCertFacCertController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatCertFacCertController.class);
	
	//引入Service服务
	@Resource(name = "matCertFacCertService")
	private final MatCertFacCertService matCertFacCertService = null;

	//生产厂商证件页面跳转
	@RequestMapping(value = "/matFacCertListPage", method = RequestMethod.GET)
	public String matFacCertListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/fac/matFacCertList";
	}
	
	//生产厂商证件维护页面跳转
	@RequestMapping(value = "/matFacCertEditPage", method = RequestMethod.GET)
	public String matFacCertEditPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("fac_id", mapVo.get("fac_id"));
		mode.addAttribute("fac_name", mapVo.get("fac_name"));
		
		return "hrp/mat/cert/fac/matFacCertEdit";
	}
	
	//生产厂商证件添加页面跳转
	@RequestMapping(value = "/matFacCertAddPage", method = RequestMethod.GET)
	public String matFacCertAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("fac_id", mapVo.get("fac_id"));
		mode.addAttribute("fac_name", mapVo.get("fac_name"));
		
		return "hrp/mat/cert/fac/matFacCertAdd";
	}
	
	//生产厂商证件修改页面跳转
	@RequestMapping(value = "/matFacCertUpdatePage", method = RequestMethod.GET)
	public String matFacCertUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> facCertMap = matCertFacCertService.queryMatFacCertById(mapVo);
		
		mode.addAttribute("facCert", facCertMap);
		
		return "hrp/mat/cert/fac/matFacCertUpdate";
	}
	
	//生产厂商材料页面跳转
	@RequestMapping(value = "/matFacCertInvPage", method = RequestMethod.GET)
	public String matFacCertInvPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("fac_id", mapVo.get("fac_id"));
		mode.addAttribute("fac_name", mapVo.get("fac_name"));
		
		return "hrp/mat/cert/fac/matFacCertInv";
	}
	
	//生产厂商查询
	@RequestMapping(value = "/queryMatFacCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFacCert(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matCertFacCertService.queryMatFacCert(getPage(mapVo));
		
		return JSONObject.parseObject(retJson);
	}
	
	//生产厂商证件查询
	@RequestMapping(value = "/queryMatFacCertInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFacCertInfo(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matCertFacCertService.queryMatFacCertInfo(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
	
	//生产厂商材料查询
	@RequestMapping(value = "/queryMatFacCertInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFacCertInv(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matCertFacCertService.queryMatFacCertInv(getPage(mapVo));
		
		return JSONObject.parseObject(retJson);
	}
	
	//生产厂商证件保存
	@RequestMapping(value = "/saveMatFacCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatFacCert(
			@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertFacCertService.saveMatFacCert(mapVo, request, response);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//生产厂商证件更新
	@RequestMapping(value = "/updateMatFacCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFacCert(
			@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> retMap = null;
		try{
			retMap = matCertFacCertService.updateMatFacCert(mapVo, request, response);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//生产厂商证件删除
	@RequestMapping(value = "/deleteMatFacCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatFacCert(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertFacCertService.deleteMatFacCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//生产厂商证件认证
	@RequestMapping(value = "/authentMatCertFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> authentMatCertFac(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("oper", "authent");
			retMap = matCertFacCertService.updateMatCertFacState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	//生产厂商证件取消认证
	@RequestMapping(value = "/unAuthentMatCertFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuthentMatCertFac(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("oper", "unAuthent");
			retMap = matCertFacCertService.updateMatCertFacState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}	
	
	//生产厂商证件审核
	@RequestMapping(value = "/checkMatCertFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkMatCertFac(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("oper", "check");
			retMap = matCertFacCertService.updateMatCertFacState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
		
	//生产厂商证件取消审核
	@RequestMapping(value = "/unCheckMatCertFac", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unCheckMatCertFac(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;
	
		try{
			mapVo.put("oper", "unCheck");
			retMap = matCertFacCertService.updateMatCertFacState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}	
	
	//生产厂商证件类型
	@RequestMapping(value = "/queryCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCertType(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = matCertFacCertService.queryCertType(mapVo);	
			
		return retMap;
	}	
}

