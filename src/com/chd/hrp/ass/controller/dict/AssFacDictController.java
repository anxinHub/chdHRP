/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.ass.controller.dict;
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
import com.chd.hrp.sys.entity.FacDict;
import com.chd.hrp.sys.serviceImpl.FacDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AssFacDictController extends BaseController{
	private static Logger logger = Logger.getLogger(AssFacDictController.class);
	
	
	@Resource(name = "facDictService")
	private final FacDictServiceImpl facDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/ass/facdict/assfacDictMainPage", method = RequestMethod.GET)
	public String facDictMainPage(Model mode) throws Exception {

		return "hrp/ass/facdict/assfacDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/ass/facdict/assfacDictAddPage", method = RequestMethod.GET)
	public String facDictAddPage(Model mode) throws Exception {

		return "hrp/ass/facdict/assfacDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/ass/facdict/assaddFacDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String facDictJson = facDictService.addFacDict(mapVo);

		return JSONObject.parseObject(facDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/ass/facdict/assqueryFacDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String facDict = facDictService.queryFacDict(getPage(mapVo));

		return JSONObject.parseObject(facDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/ass/facdict/assdeleteFacDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFacDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String facDictJson = facDictService.deleteBatchFacDict(listVo);
	   return JSONObject.parseObject(facDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/ass/facdict/assfacDictUpdatePage", method = RequestMethod.GET)
	
	public String facDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        FacDict facDict = new FacDict();
		facDict = facDictService.queryFacDictByCode(mapVo);
		mode.addAttribute("fac_no", facDict.getFac_no());
		mode.addAttribute("group_id", facDict.getGroup_id());
		mode.addAttribute("hos_id", facDict.getHos_id());
		mode.addAttribute("fac_id", facDict.getFac_id());
		mode.addAttribute("fac_code", facDict.getFac_code());
		mode.addAttribute("fac_name", facDict.getFac_name());
		mode.addAttribute("user_code", facDict.getUser_code());
		mode.addAttribute("create_date", facDict.getCreate_date());
		mode.addAttribute("note", facDict.getNote());
		mode.addAttribute("is_stop", facDict.getIs_stop());
		
		return "hrp/ass/facdict/assfacDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/ass/facdict/assupdateFacDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String facDictJson = facDictService.updateFacDict(mapVo);
		
		return JSONObject.parseObject(facDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/ass/facdict/assimportFacDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importFacDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String facDictJson = facDictService.importFacDict(mapVo);
		
		return JSONObject.parseObject(facDictJson);
	}

}

