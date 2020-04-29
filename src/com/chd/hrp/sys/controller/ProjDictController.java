/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import com.chd.base.SessionManager;
import com.chd.hrp.sys.entity.ProjDict;
import com.chd.hrp.sys.serviceImpl.ProjDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class ProjDictController extends BaseController{
	private static Logger logger = Logger.getLogger(ProjDictController.class);
	
	
	@Resource(name = "projDictService")
	private final ProjDictServiceImpl projDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/projdict/projDictMainPage", method = RequestMethod.GET)
	public String projDictMainPage(Model mode) throws Exception {

		return "hrp/sys/projdict/projDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/projdict/projDictAddPage", method = RequestMethod.GET)
	public String projDictAddPage(Model mode) throws Exception {

		return "hrp/sys/projdict/projDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/projdict/addProjDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String projDictJson = projDictService.addProjDict(mapVo);

		return JSONObject.parseObject(projDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/projdict/queryProjDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		if(mapVo.get("emp_code") != null){
			//用来转换大写
			mapVo.put("emp_code",mapVo.get("emp_code").toString().toUpperCase());
		}
		
		String projDict = projDictService.queryProjDict(getPage(mapVo));

		return JSONObject.parseObject(projDict);
		
	}
	
	/**
	 * 【项目信息-项目维护】：主查询
	 */
	@RequestMapping(value = "/hrp/sys/projdict/queryProjDictList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProjDictList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id")==null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id")==null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code")==null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String projDict = projDictService.queryProjDictList(getPage(mapVo));
		return JSONObject.parseObject(projDict);
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/projdict/deleteProjDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProjDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String projDictJson = projDictService.deleteBatchProjDict(listVo);
	   return JSONObject.parseObject(projDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/projdict/projDictUpdatePage", method = RequestMethod.GET)
	
	public String projDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        ProjDict projDict = new ProjDict();
		projDict = projDictService.queryProjDictByCode(mapVo);
		mode.addAttribute("proj_no", projDict.getProj_no());
		mode.addAttribute("group_id", projDict.getGroup_id());
		mode.addAttribute("hos_id", projDict.getHos_id());
		mode.addAttribute("proj_id", projDict.getProj_id());
		mode.addAttribute("proj_code", projDict.getProj_code());
		mode.addAttribute("proj_name", projDict.getProj_name());
		mode.addAttribute("user_code", projDict.getUser_code());
		mode.addAttribute("create_date", projDict.getCreate_date());
		mode.addAttribute("note", projDict.getNote());
		mode.addAttribute("is_stop", projDict.getIs_stop());
		
		return "hrp/sys/projdict/projDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/projdict/updateProjDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projDictJson = projDictService.updateProjDict(mapVo);
		
		return JSONObject.parseObject(projDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/projdict/importProjDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importProjDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String projDictJson = projDictService.importProjDict(mapVo);
		
		return JSONObject.parseObject(projDictJson);
	}

}

