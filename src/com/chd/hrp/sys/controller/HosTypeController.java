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
import com.chd.hrp.sys.entity.HosType;
import com.chd.hrp.sys.serviceImpl.HosTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class HosTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(HosTypeController.class);
	
	
	@Resource(name = "hosTypeService")
	private final HosTypeServiceImpl hosTypeService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/hostype/hosTypeMainPage", method = RequestMethod.GET)
	public String hosTypeMainPage(Model mode) throws Exception {

		return "hrp/sys/hostype/hosTypeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/hostype/hosTypeAddPage", method = RequestMethod.GET)
	public String hosTypeAddPage(Model mode) throws Exception {

		return "hrp/sys/hostype/hosTypeAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/hostype/addHosType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHosType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String hosTypeJson = hosTypeService.addHosType(mapVo);

		return JSONObject.parseObject(hosTypeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/hostype/queryHosType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHosType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String hosType = hosTypeService.queryHosType(getPage(mapVo));

		return JSONObject.parseObject(hosType);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/hostype/deleteHosType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHosType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("type_code", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String hosTypeJson = hosTypeService.deleteBatchHosType(listVo);
	   return JSONObject.parseObject(hosTypeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/hostype/hosTypeUpdatePage", method = RequestMethod.GET)
	
	public String hosTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        HosType hosType = new HosType();
		hosType = hosTypeService.queryHosTypeByCode(mapVo);
		mode.addAttribute("type_code", hosType.getType_code());
		mode.addAttribute("type_name", hosType.getType_name());
		mode.addAttribute("is_stop", hosType.getIs_stop());
		mode.addAttribute("note", hosType.getNote());
		
		return "hrp/sys/hostype/hosTypeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/hostype/updateHosType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHosType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosTypeJson = hosTypeService.updateHosType(mapVo);
		
		return JSONObject.parseObject(hosTypeJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/hostype/importHosType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHosType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String hosTypeJson = hosTypeService.importHosType(mapVo);
		
		return JSONObject.parseObject(hosTypeJson);
	}

}

