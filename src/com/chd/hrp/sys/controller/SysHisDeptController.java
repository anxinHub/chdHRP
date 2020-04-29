package com.chd.hrp.sys.controller;

import java.io.IOException;
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
import com.chd.hrp.sys.service.SysHisDeptService;

@Controller
public class SysHisDeptController extends BaseController{
	
	private static Logger logger = Logger.getLogger(SysHisDeptController.class);
	
	
	@Resource(name = "sysHisDeptService")
	private final SysHisDeptService sysHisDeptService = null;
   
	/**
	 * 
	* @Title: supDictMainPage
	* @Description: HIS科室字典页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/sys/syshisdept/sysHisDeptMainPage", method = RequestMethod.GET)
	public String sysHisDeptMainPage(Model mode) throws Exception {
		return "hrp/sys/syshisdept/sysHisDeptMain";

	}
	
	/**
	 * 
	* @Title: querySysHisDept
	* @Description: 查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/sys/syshisdept/querySysHisDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysHisDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String sysHisDeptJson = sysHisDeptService.querySysHisDept(getPage(mapVo));
		return JSONObject.parseObject(sysHisDeptJson);
	}
	
	/**
	 * 
	* @Title: deleteSysHisDept
	* @Description: 删除
	* @param @param paramVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/sys/syshisdept/deleteSysHisDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSysHisDept(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("his_dept_code",ids[0]);   
            listVo.add(mapVo);
        }
		String sysHisDeptJson;
		try {
			
			sysHisDeptJson = sysHisDeptService.deleteBatchSysHisDept(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			sysHisDeptJson = e.getMessage();
		}
	   return JSONObject.parseObject(sysHisDeptJson);
	}
	
	/**
	 * 
	* @Title: importSysHisDept
	* @Description: 导入
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws IOException
	* @return String 
	* @date 2020年2月17日   
	* @author sjy
	 */
	@RequestMapping(value="/hrp/sys/syshisdept/importSysHisDept",method = RequestMethod.POST)  
	@ResponseBody
    public  Map<String, Object> importSysHisDept(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 
		
		String sysHisDeptJson = "";
		
		try {
			
			 sysHisDeptJson=sysHisDeptService.importSysHisDept(mapVo);
			
		} catch (Exception e) {
			sysHisDeptJson = e.getMessage();
		}
		
		return  JSONObject.parseObject(sysHisDeptJson);
    }  
	
}
