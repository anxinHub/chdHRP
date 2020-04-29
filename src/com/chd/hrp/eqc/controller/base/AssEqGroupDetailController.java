
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
import com.chd.hrp.eqc.service.base.AssEqGroupDetailService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 00机组明细 ASS_EQGroupDetail Controller实现类
*/
@Controller
public class AssEqGroupDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqGroupDetailController.class);
	
	//引入Service服务
	@Resource(name = "assEqGroupDetailService")
	private final AssEqGroupDetailService assEqGroupDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqGroupDetailMainPage", method = RequestMethod.GET)
	public String assEqGroupDetailMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqgroupdetailoupdetail/assEqGroupDetailMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqGroupDetailAddPage", method = RequestMethod.GET)
	public String assEqGroupDetailAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqgroupdetail/assEqGroupDetailAdd";

	}

	/**
	 * @Description 
	 * 添加数据 00机组明细 ASS_EQGroupDetail
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/addAssEqGroupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssEqGroupDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
    mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
    mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
       
		String assEqGroupDetailJson = assEqGroupDetailService.add(mapVo);

		return JSONObject.parseObject(assEqGroupDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 00机组明细 ASS_EQGroupDetail
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqGroupDetailUpdatePage", method = RequestMethod.GET)
	public String assEqGroupDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	  if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
    mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
    mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		
    Map<String,Object> assEqGroupDetail = new HashMap<String, Object>();
    
		assEqGroupDetail = assEqGroupDetailService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqGroupDetail.get("group_id"));
		mode.addAttribute("hos_id", assEqGroupDetail.get("hos_id"));
		mode.addAttribute("copy_code", assEqGroupDetail.get("copy_code"));
		mode.addAttribute("eq_unit_code", assEqGroupDetail.get("eq_unit_code"));
		mode.addAttribute("eo_eq_group", assEqGroupDetail.get("eo_eq_group"));
		mode.addAttribute("main_flag", assEqGroupDetail.get("main_flag"));
		mode.addAttribute("income_rate", assEqGroupDetail.get("income_rate"));
		mode.addAttribute("expend_rate", assEqGroupDetail.get("expend_rate"));
		mode.addAttribute("remark", assEqGroupDetail.get("remark"));
		mode.addAttribute("to_date", assEqGroupDetail.get("to_date"));
		mode.addAttribute("update_date", assEqGroupDetail.get("update_date"));
		mode.addAttribute("update_time", assEqGroupDetail.get("update_time"));
		mode.addAttribute("update_userdr", assEqGroupDetail.get("update_userdr"));
		mode.addAttribute("invalid_flag", assEqGroupDetail.get("invalid_flag"));
		mode.addAttribute("sort", assEqGroupDetail.get("sort"));
		
		return "hrp/eqc/base/asseqgroupdetail/assEqGroupDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 00机组明细 ASS_EQGroupDetail
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqGroupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqGroupDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
    mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
    mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String assEqGroupDetailJson = assEqGroupDetailService.update(mapVo);
		
		return JSONObject.parseObject(assEqGroupDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 00机组明细 ASS_EQGroupDetail
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqGroupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqGroupDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("eq_unit_code", ids[3])   ;
				mapVo.put("eo_eq_group", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
			String assEqGroupDetailJson = assEqGroupDetailService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assEqGroupDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 00机组明细 ASS_EQGroupDetail
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqGroupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqGroupDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	  if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
    mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
    mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String assEqGroupDetail = assEqGroupDetailService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqGroupDetail);
		
	}
	
    
}

