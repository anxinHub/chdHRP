package com.chd.hrp.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.base.SessionManager;
import com.chd.hrp.sys.service.SysHisDeptRefService;

@Controller
public class SysHisDeptRefController extends BaseController {

	private static Logger logger = Logger.getLogger(SysHisDeptRefController.class);
	
	@Resource(name = "sysHisDeptRefService")
	private final SysHisDeptRefService sysHisDeptRefService = null; 
	
	
  /**
	* 
	* @Title: sysHisDeptRefMainPage
	* @Description: 页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/sys/syshisdeptref/sysHisDeptRefMainPage", method = RequestMethod.GET)
	public String sysHisDeptRefMainPage(Model mode) throws Exception {
		return "hrp/sys/syshisdeptref/sysHisDeptRefMain";
	}
	
	
	/**
	 * 
	* @Title: sysHisDeptRefAddPage
	* @Description: 添加
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/sys/syshisdeptref/sysHisDeptRefAddPage", method = RequestMethod.GET)
	public String sysHisDeptRefAddPage(Model mode) throws Exception {
		return "hrp/sys/syshisdeptref/sysHisDeptRefAdd";
	}
	
	
	// 保存
	@RequestMapping(value = "/hrp/sys/syshisdeptref/addSysHisDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSysHisDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String sysHisDeptRefJson ="";
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			sysHisDeptRefJson = sysHisDeptRefService.addSysHisDeptRef(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			sysHisDeptRefJson = e.getMessage();
		}

		return JSONObject.parseObject(sysHisDeptRefJson);
		
	}
	/**
	 * 
	* @Title: querySysHisDeptRef
	* @Description: 查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/sys/syshisdeptref/querySysHisDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysHisDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String sysHisDeptRefJson = sysHisDeptRefService.querySysHisDeptRef(getPage(mapVo));
		return JSONObject.parseObject(sysHisDeptRefJson);
	}
	
	
	//删除
	@RequestMapping(value = "/hrp/sys/syshisdeptref/deleteSysHisDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSysHisDeptRef(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
            mapVo.put("group_id", ids[0]);//实际实体类变量
            mapVo.put("hos_id", ids[1]);//实际实体类变量
            mapVo.put("hrp_dept_code", ids[2]);//实际实体类变量
            mapVo.put("his_dept_code", ids[3]);//实际实体类变量
            mapVo.put("dept_type", ids[4]);//实际实体类变量
            mapVo.put("natur_code", ids[5]);//实际实体类变量
            listVo.add(mapVo);
        }
		String sysHisDeptRefJson = null;
		try {
			
			sysHisDeptRefJson = sysHisDeptRefService.deleteBatchSysHisDeptRef(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			sysHisDeptRefJson = e.getMessage();
		}
	   return JSONObject.parseObject(sysHisDeptRefJson);
			
	}
	
}
