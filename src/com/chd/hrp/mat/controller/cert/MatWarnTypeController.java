
package com.chd.hrp.mat.controller.cert;

import java.io.File;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.cert.MatWarnTypeService;

@Controller 
@RequestMapping(value="/hrp/mat/cert/warn")
public class MatWarnTypeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatWarnTypeController.class);
	
	//引入Service服务
	@Resource(name = "matWarnTypeService")
	private final MatWarnTypeService matWarnTypeService = null;

	//预警类型页面跳转
	@RequestMapping(value = "/matWarnTypeListPage", method = RequestMethod.GET)
	public String matWarnTypeListPage(Model mode) throws Exception {
		
		return "hrp/mat/cert/warn/matWarnTypeList";
	}
	
	//预警类型添加页面跳转
	@RequestMapping(value = "/matWarnTypeAddPage", method = RequestMethod.GET)
	public String matWarnTypeAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/mat/cert/warn/matWarnTypeAdd";
	}
	
	//预警类型修改页面跳转
	@RequestMapping(value = "/matWarnTypeUpdatePage", method = RequestMethod.GET)
	public String matWarnTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> warnTypeMap = matWarnTypeService.queryMatWarnTypeByCode(mapVo);
		mode.addAttribute("warnType", warnTypeMap);
		
		return "hrp/mat/cert/warn/matWarnTypeUpdate";
	}
	
	//预警类型查询
	@RequestMapping(value = "/queryMatWarnType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatWarnType(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String retJson = matWarnTypeService.queryMatWarnType(getPage(mapVo));
		
		return JSONObject.parseObject(retJson);
	}
	
	//预警类型保存
	@RequestMapping(value = "/saveMatWarnType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatWarnType(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			
			if(mapVo.containsKey("is_update")){
				retMap = matWarnTypeService.updateMatWarnType(mapVo);
			}else{
				retMap = matWarnTypeService.saveMatWarnType(mapVo);
			}	
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//预警类型删除
	@RequestMapping(value = "/deleteMatWarnType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatWarnType(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matWarnTypeService.deleteMatWarnType(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//预警类型启用
	@RequestMapping(value = "/updateMatWarnTypeToStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatWarnTypeToStart(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("is_stop", "0");
			retMap = matWarnTypeService.updateMatWarnTypeState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	//预警类型停用
	@RequestMapping(value = "/updateMatWarnTypeToStop", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatWarnTypeToStop(
			@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			Map<String, Object> retMap = null;

		try{
			mapVo.put("is_stop", "1");
			retMap = matWarnTypeService.updateMatWarnTypeState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}	
	
	//预警类型图片寻找
	@RequestMapping(value = "/queryMatWarnTypeImg", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatWarnTypeImg(Model mode) throws Exception {
		
		String url = this.getClass().getResource("").getPath();
		int subLen = url.indexOf("WEB-INF");
		String path = url.substring(0, subLen);
		File files = new File(path + "\\dhc\\images\\warnType");
		String[] tempList = files.list();
		
		return JSON.toJSONString(tempList);
	}
}

