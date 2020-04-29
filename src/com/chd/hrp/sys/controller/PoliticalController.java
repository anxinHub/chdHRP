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
import com.chd.hrp.sys.entity.Political;
import com.chd.hrp.sys.serviceImpl.PoliticalServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class PoliticalController extends BaseController{
	private static Logger logger = Logger.getLogger(PoliticalController.class);
	
	
	@Resource(name = "politicalService")
	private final PoliticalServiceImpl politicalService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/political/politicalMainPage", method = RequestMethod.GET)
	public String politicalMainPage(Model mode) throws Exception {

		return "hrp/sys/political/politicalMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/political/politicalAddPage", method = RequestMethod.GET)
	public String politicalAddPage(Model mode) throws Exception {

		return "hrp/sys/political/politicalAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/political/addPolitical", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addPolitical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String politicalJson = politicalService.addPolitical(mapVo);

		return JSONObject.parseObject(politicalJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/political/queryPolitical", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryPolitical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String political = politicalService.queryPolitical(getPage(mapVo));

		return JSONObject.parseObject(political);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/political/deletePolitical", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePolitical(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("political_code", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String politicalJson = politicalService.deleteBatchPolitical(listVo);
	   return JSONObject.parseObject(politicalJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/political/politicalUpdatePage", method = RequestMethod.GET)
	
	public String politicalUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Political political = new Political();
		political = politicalService.queryPoliticalByCode(mapVo);
		mode.addAttribute("political_code", political.getPolitical_code());
		mode.addAttribute("political_name", political.getPolitical_name());
		mode.addAttribute("is_stop", political.getIs_stop());
		mode.addAttribute("note", political.getNote());
		
		return "hrp/sys/political/politicalUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/political/updatePolitical", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePolitical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String politicalJson = politicalService.updatePolitical(mapVo);
		
		return JSONObject.parseObject(politicalJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/political/importPolitical", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPolitical(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String politicalJson = politicalService.importPolitical(mapVo);
		
		return JSONObject.parseObject(politicalJson);
	}

}

