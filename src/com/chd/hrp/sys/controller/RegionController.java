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
import com.chd.hrp.sys.entity.Region;
import com.chd.hrp.sys.serviceImpl.RegionServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class RegionController extends BaseController{
	private static Logger logger = Logger.getLogger(RegionController.class);
	
	
	@Resource(name = "regionService")
	private final RegionServiceImpl regionService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/region/regionMainPage", method = RequestMethod.GET)
	public String regionMainPage(Model mode) throws Exception {

		return "hrp/sys/region/regionMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/region/regionAddPage", method = RequestMethod.GET)
	public String regionAddPage(Model mode) throws Exception {

		return "hrp/sys/region/regionAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/region/addRegion", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addRegion(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String regionJson = regionService.addRegion(mapVo);

		return JSONObject.parseObject(regionJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/region/queryRegion", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryRegion(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String region = regionService.queryRegion(getPage(mapVo));

		return JSONObject.parseObject(region);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/region/deleteRegion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRegion(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("cities_code", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String regionJson = regionService.deleteBatchRegion(listVo);
	   return JSONObject.parseObject(regionJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/region/regionUpdatePage", method = RequestMethod.GET)
	
	public String regionUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Region region = new Region();
		region = regionService.queryRegionByCode(mapVo);
		mode.addAttribute("cities_code", region.getCities_code());
		mode.addAttribute("cities_name", region.getCities_name());
		mode.addAttribute("is_stop", region.getIs_stop());
		mode.addAttribute("spell_code", region.getSpell_code());
		mode.addAttribute("wbx_code", region.getWbx_code());
		mode.addAttribute("note", region.getNote());
		
		return "hrp/sys/region/regionUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/region/updateRegion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRegion(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String regionJson = regionService.updateRegion(mapVo);
		
		return JSONObject.parseObject(regionJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/region/importRegion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importRegion(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String regionJson = regionService.importRegion(mapVo);
		
		return JSONObject.parseObject(regionJson);
	}

}

