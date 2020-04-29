/**
* @Copyright: Copyright (c) 2017-9-13 
* @CompanyInfo: 杭州亦童科技有限公司
*/
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
import com.chd.hrp.mat.service.cert.MatProdAuthorService;

@Controller
@RequestMapping(value="/hrp/mat/cert/author")
public class MatProdAuthorController extends BaseController{
	private static Logger logger = Logger.getLogger(MatProdAuthorController.class);
	
	// 引入Service服务
	@Resource(name = "matProdAuthorService")
	private final MatProdAuthorService matProdAuthorService = null;

	//页面跳转
	@RequestMapping(value = "/matProdAuthorListPage", method = RequestMethod.GET)
	public String matProdAuthorListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthorList";
	}
	
	//列表查询
	@RequestMapping(value = "queryMatProdAuthorList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdAuthorList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdAuthorService.queryMatProdAuthorList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	//查询
	@RequestMapping(value = "/queryMatProdAuthorById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdAuthorById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.queryMatProdAuthorById(mapVo);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//目标企业字典加载
	@RequestMapping(value = "/queryHosFacSup", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosFacSup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		return matProdAuthorService.queryHosFacSup(mapVo);
	}
	
	//维护页面跳转
	@RequestMapping(value = "/matProdAuthorPage", method = RequestMethod.GET)
	public String matProdAuthorPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthor";
	}
	
	//保存
	@RequestMapping(value = "/saveMatProdAuthor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatProdAuthor(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.saveMatProdAuthor(mapVo, request, response);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//删除
	@RequestMapping(value = "/deleteMatProdAuthor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatProdAuthor(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.deleteMatProdAuthor(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//认证
	@RequestMapping(value = "/updateMatProdAuthorToAuthent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdAuthorToAuthent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.updateMatProdAuthorToAuthent(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//取消认证
	@RequestMapping(value = "/updateMatProdAuthorToUnAuthent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdAuthorToUnAuthent(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.updateMatProdAuthorToUnAuthent(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//审核
	@RequestMapping(value = "/updateMatProdAuthorToCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdAuthorToCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.updateMatProdAuthorToCheck(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//消审
	@RequestMapping(value = "/updateMatProdAuthorToUnCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdAuthorToUnCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.updateMatProdAuthorToUnCheck(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//导入页面跳转
	@RequestMapping(value = "/matProdAuthorImportPage", method = RequestMethod.GET)
	public String matProdAuthorImportPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthorImport";
	}
	//导入
	@RequestMapping(value = "/addMatProdAuthorByImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatProdAuthorByImp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.addMatProdAuthorByImp(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}

	
	//选择材料维护页面跳转
	@RequestMapping(value = "/matInvListPage", method = RequestMethod.GET)
	public String matInvListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matInvList";
	}
	
	//选择材料列表查询
	@RequestMapping(value = "queryMatInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdAuthorService.queryMatInvList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}

	//关联材料列表页面跳转
	@RequestMapping(value = "/matProdAuthorInvPage", method = RequestMethod.GET)
	public String matProdAuthorInvPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthorInv";
	}
	
	//关联材料列表查询
	@RequestMapping(value = "queryMatProdAuthorInvList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdAuthorInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdAuthorService.queryMatProdAuthorInvList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	//关联材料
	@RequestMapping(value = "/saveMatProdAuthorInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatProdAuthorInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.saveMatProdAuthorInv(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	

	//更换新证页面跳转
	@RequestMapping(value = "/matProdAuthorCopyPage", method = RequestMethod.GET)
	public String matProdAuthorCopyPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthorCopy";
	}

	//查看新老证页面跳转
	@RequestMapping(value = "/matProdAuthorOtherPage", method = RequestMethod.GET)
	public String matProdAuthorOtherPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthorOther";
	}

	//选择新证页面跳转
	@RequestMapping(value = "/matProdAuthorChoosePage", method = RequestMethod.GET)
	public String matProdAuthorChoosePage(Model mode) throws Exception {
		
		return "hrp/mat/cert/author/matProdAuthorChoose";
	}
	
	//选择新证查询
	@RequestMapping(value = "queryMatProdAuthorChooseList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatProdAuthorChooseList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matProdAuthorService.queryMatProdAuthorChooseList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}
	
	//绑定新证
	@RequestMapping(value = "/updateMatProdAuthorToNewCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdAuthorToNewCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.updateMatProdAuthorToNewCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//取消绑定新证
	@RequestMapping(value = "/updateMatProdAuthorToUnNewCert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatProdAuthorToUnNewCert(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matProdAuthorService.updateMatProdAuthorToUnNewCert(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

