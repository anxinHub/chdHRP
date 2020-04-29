
package com.chd.hrp.mat.controller.cert;

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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.hrp.mat.service.cert.MatCertTypeService;

@Controller
@RequestMapping(value="/hrp/mat/cert/type")
public class MatCertTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatCertTypeController.class);
	
	//引入Service服务
	@Resource(name = "matCertTypeService")
	private final MatCertTypeService matCertTypeService = null;

	//证件类型页面跳转
	@RequestMapping(value = "/matCertTypeListPage", method = RequestMethod.GET)
	public String matCertTypeListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/type/matCertTypeList";
	}
	
	//证件类型查询
	@RequestMapping(value = "queryMatCertTypeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCertTypeList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matCertTypeService.queryMatCertTypeList(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	//证件类型维护页面跳转
	@RequestMapping(value = "/matCertTypePage", method = RequestMethod.GET)
	public String matCertTypePage(Model mode) throws Exception {
		
		return "hrp/mat/cert/type/matCertType";
	}
	
	//证件类型保存
	@RequestMapping(value = "/saveMatCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertTypeService.saveMatCertType(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//证件类型删除
	@RequestMapping(value = "/deleteMatCertType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatCertType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertTypeService.deleteMatCertType(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//证件类型启用
	@RequestMapping(value = "/updateMatCertTypeToStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatCertTypeToStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertTypeService.updateMatCertTypeToStart(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//证件类型停用
	@RequestMapping(value = "/updateMatCertTypeToStop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatCertTypeToStop(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertTypeService.updateMatCertTypeToStop(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//证件类型导入页面跳转
	@RequestMapping(value = "/matCertTypeImportPage", method = RequestMethod.GET)
	public String matCertTypeImportPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/type/matCertTypeImport";
	}
	//证件类型导入
	@RequestMapping(value = "/addMatCertTypeByImp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatCertTypeByImp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matCertTypeService.addMatCertTypeByImp(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

