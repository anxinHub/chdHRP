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
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
* @Title. @Description.
* 系统启用
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class ModStartController extends BaseController{
	private static Logger logger = Logger.getLogger(ModStartController.class);
	
	
	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;
   
   
	/**
	*系统启用<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/sys/modstart/modStartMainPage", method = RequestMethod.GET)
	public String modStartMainPage(Model mode) throws Exception {

		return "hrp/sys/modstart/modStartMain";

	}
	/**
	*系统启用<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/sys/modstart/modStartAddPage", method = RequestMethod.GET)
	public String modStartAddPage(Model mode) throws Exception {

		return "hrp/sys/modstart/modStartAdd";

	}
	/**
	*系统启用<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/sys/modstart/addModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String modStartJson = modStartService.addModStart(mapVo);

		return JSONObject.parseObject(modStartJson);
		
	}
	/**
	*系统启用<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/sys/modstart/queryModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String modStart = modStartService.queryModStart(getPage(mapVo));

		return JSONObject.parseObject(modStart);
		
	}
	/**
	*系统启用<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/sys/modstart/deleteModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteModStart(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String modStartJson = modStartService.deleteBatchModStart(listVo);
	   return JSONObject.parseObject(modStartJson);
			
	}
	
	/**
	*系统启用<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/sys/modstart/modStartUpdatePage", method = RequestMethod.GET)
	
	public String modStartUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        ModStart modStart = new ModStart();
		modStart = modStartService.queryModStartByCode(mapVo);
		mode.addAttribute("group_id", modStart.getGroup_id());
		mode.addAttribute("hos_id", modStart.getHos_id());
		mode.addAttribute("copy_code", modStart.getCopy_code());
		mode.addAttribute("mod_code", modStart.getMod_code());
		mode.addAttribute("start_year", modStart.getStart_year());
		mode.addAttribute("start_month", modStart.getStart_month());
		mode.addAttribute("create_user", modStart.getCreate_user());
		mode.addAttribute("create_date", modStart.getCreate_date());
		return "hrp/sys/modstart/modStartUpdate";
	}
	/**
	*系统启用<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/sys/modstart/updateModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
	   
	   
		String modStartJson = modStartService.updateModStart(mapVo);
		
		return JSONObject.parseObject(modStartJson);
	}
	/**
	*系统启用<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/sys/modstart/importModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modStartJson = modStartService.importModStart(mapVo);
		
		return JSONObject.parseObject(modStartJson);
	}

}

