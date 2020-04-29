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
import com.chd.hrp.sys.entity.Countries;
import com.chd.hrp.sys.serviceImpl.CountriesServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CountriesController extends BaseController{
	private static Logger logger = Logger.getLogger(CountriesController.class);
	
	
	@Resource(name = "countriesService")
	private final CountriesServiceImpl countriesService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/countries/countriesMainPage", method = RequestMethod.GET)
	public String countriesMainPage(Model mode) throws Exception {

		return "hrp/sys/countries/countriesMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/countries/countriesAddPage", method = RequestMethod.GET)
	public String countriesAddPage(Model mode) throws Exception {

		return "hrp/sys/countries/countriesAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/countries/addCountries", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCountries(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String countriesJson = countriesService.addCountries(mapVo);

		return JSONObject.parseObject(countriesJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/countries/queryCountries", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCountries(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String countries = countriesService.queryCountries(getPage(mapVo));

		return JSONObject.parseObject(countries);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/countries/deleteCountries", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCountries(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("countries_code", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String countriesJson = countriesService.deleteBatchCountries(listVo);
	   return JSONObject.parseObject(countriesJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/countries/countriesUpdatePage", method = RequestMethod.GET)
	
	public String countriesUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Countries countries = new Countries();
		countries = countriesService.queryCountriesByCode(mapVo);
		mode.addAttribute("countries_code", countries.getCountries_code());
		mode.addAttribute("countries_name", countries.getCountries_name());
		mode.addAttribute("countries_simple", countries.getCountries_simple());
		mode.addAttribute("countries_en", countries.getCountries_en());
		mode.addAttribute("is_stop", countries.getIs_stop());
		mode.addAttribute("spell_code", countries.getSpell_code());
		mode.addAttribute("wbx_code", countries.getWbx_code());
		mode.addAttribute("note", countries.getNote());
		
		return "hrp/sys/countries/countriesUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/countries/updateCountries", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCountries(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String countriesJson = countriesService.updateCountries(mapVo);
		
		return JSONObject.parseObject(countriesJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/countries/importCountries", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importCountries(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String countriesJson = countriesService.importCountries(mapVo);
		
		return JSONObject.parseObject(countriesJson);
	}

}

