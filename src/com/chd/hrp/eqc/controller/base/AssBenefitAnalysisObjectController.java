
/*
 *
 */
 package com.chd.hrp.eqc.controller.base;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.eqc.service.base.AssBenefitAnalysisObjectService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 03效益分析对象 ASS_Benefit_Analysis_Object Controller实现类
*/
@Controller
public class AssBenefitAnalysisObjectController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssBenefitAnalysisObjectController.class);
	
	//引入Service服务
	@Resource(name = "assBenefitAnalysisObjectService")
	private final AssBenefitAnalysisObjectService assBenefitAnalysisObjectService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assBenefitAnalysisObjectMainPage", method = RequestMethod.GET)
	public String assBenefitAnalysisObjectMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/assbenefitanalysisobject/assBenefitAnalysisObjectMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assBenefitAnalysisObjectAddPage", method = RequestMethod.GET)
	public String assBenefitAnalysisObjectAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/assbenefitanalysisobject/assBenefitAnalysisObjectAdd";

	}

	/**
	 * @Description 
	 * 添加数据 03效益分析对象 ASS_Benefit_Analysis_Object
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssBenefitAnalysisObject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssBenefitAnalysisObject(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("analysis_name", ids[1]);
			mapVo.put("analysis_attribute", ids[2]);
			mapVo.put("remark", ids[3]);
			mapVo.put("rowNo", ids[4]);// 行号
			mapVo.put("flag", ids[5]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 1);// 添加 修改标识
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assBenefitAnalysisObjectService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 03效益分析对象 ASS_Benefit_Analysis_Object
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assBenefitAnalysisObjectUpdatePage", method = RequestMethod.GET)
	public String assBenefitAnalysisObjectUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assBenefitAnalysisObject = new HashMap<String, Object>();
    
		assBenefitAnalysisObject = assBenefitAnalysisObjectService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assBenefitAnalysisObject.get("group_id"));
		mode.addAttribute("hos_id", assBenefitAnalysisObject.get("hos_id"));
		mode.addAttribute("copy_code", assBenefitAnalysisObject.get("copy_code"));
		mode.addAttribute("analysis_code", assBenefitAnalysisObject.get("analysis_code"));
		mode.addAttribute("analysis_name", assBenefitAnalysisObject.get("analysis_name"));
		mode.addAttribute("analysis_attribute", assBenefitAnalysisObject.get("analysis_attribute"));
		mode.addAttribute("invalid_flag", assBenefitAnalysisObject.get("invalid_flag"));
		mode.addAttribute("remark", assBenefitAnalysisObject.get("remark"));
		
		return "hrp/eqc/base/assbenefitanalysisobject/assBenefitAnalysisObjectUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 03效益分析对象 ASS_Benefit_Analysis_Object
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssBenefitAnalysisObject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssBenefitAnalysisObject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBenefitAnalysisObjectJson = assBenefitAnalysisObjectService.update(mapVo);
		
		return JSONObject.parseObject(assBenefitAnalysisObjectJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 03效益分析对象 ASS_Benefit_Analysis_Object
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssBenefitAnalysisObject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssBenefitAnalysisObject(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			
			listVo.add(mapVo);
      
		}
	    
		String assBenefitAnalysisObjectJson = assBenefitAnalysisObjectService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assBenefitAnalysisObjectJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 03效益分析对象 ASS_Benefit_Analysis_Object
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssBenefitAnalysisObject", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssBenefitAnalysisObject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assBenefitAnalysisObject = assBenefitAnalysisObjectService.query(getPage(mapVo));

		return JSONObject.parseObject(assBenefitAnalysisObject);
		
	}
	
    
}

