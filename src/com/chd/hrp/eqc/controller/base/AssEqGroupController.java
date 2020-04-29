
/*
 *
 */
 package com.chd.hrp.eqc.controller.base;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.eqc.service.base.AssEqGroupService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 00机组维护 ASS_EQGroup Controller实现类
*/
@Controller
public class AssEqGroupController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqGroupController.class);
	
	//引入Service服务
	@Resource(name = "assEqGroupService")
	private final AssEqGroupService assEqGroupService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqGroupMainPage", method = RequestMethod.GET)
	public String assEqGroupMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqgroup/assEqGroupMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqGroupAddPage", method = RequestMethod.GET)
	public String assEqGroupAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqgroup/assEqGroupAdd";

	}

	/**
	 * @Description 
	 * 添加数据 00机组维护 ASS_EQGroup
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqGroup(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("eq_unit_code", ids[0])   ;
			mapVo.put("eq_unit_name", ids[1]);
			mapVo.put("from_date", DateUtil.stringToDate(DateUtil.jsDateToString(ids[2], "yyyy-MM-dd"),"yyyy-MM-dd"));
			mapVo.put("to_date", DateUtil.stringToDate(DateUtil.jsDateToString(ids[3], "yyyy-MM-dd"),"yyyy-MM-dd"));
			mapVo.put("detailData",ids[4] );
			mapVo.put("rowNo", ids[5]);// 机组编码 数据唯一标识
			mapVo.put("flag", ids[6]);// 添加 修改标识
			mapVo.put("update_date", DateUtil.stringToDate(DateUtil.dateFormat1(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
			mapVo.put("update_time",DateUtil.stringToDate(DateUtil.dateFormat1(new Date(),"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
			mapVo.put("invalid_flag", "0");
			mapVo.put("remark", null);
			
			listVo.add(mapVo);
	      
	    }
       
		String assEqGroupJson = assEqGroupService.save(listVo);

		return JSONObject.parseObject(assEqGroupJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 00机组维护 ASS_EQGroup
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqGroupUpdatePage", method = RequestMethod.GET)
	public String assEqGroupUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqGroup = new HashMap<String, Object>();
    
		assEqGroup = assEqGroupService.queryByCode(mapVo);
		
		mode.addAttribute("Group_id", assEqGroup.get("group_id"));
		mode.addAttribute("hos_id", assEqGroup.get("hos_id"));
		mode.addAttribute("copy_code", assEqGroup.get("copy_code"));
		mode.addAttribute("eq_unit_code", assEqGroup.get("eq_unit_code"));
		mode.addAttribute("eq_unit_name", assEqGroup.get("eq_unit_name"));
		mode.addAttribute("update_date", assEqGroup.get("update_date"));
		mode.addAttribute("update_time", assEqGroup.get("update_time"));
		mode.addAttribute("from_date", assEqGroup.get("from_date"));
		mode.addAttribute("to_date", assEqGroup.get("to_date"));
		mode.addAttribute("remark", assEqGroup.get("remark"));
		mode.addAttribute("invalid_flag", assEqGroup.get("invalid_flag"));
		
		return "hrp/eqc/base/asseqgroup/assEqGroupUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 00机组维护 ASS_EQGroup
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqGroupJson = assEqGroupService.update(mapVo);
		
		return JSONObject.parseObject(assEqGroupJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 00机组维护 ASS_EQGroup
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqGroup(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("eq_unit_code", ids[0]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assEqGroupJson = assEqGroupService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqGroupJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 00机组维护 ASS_EQGroup
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqGroup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqGroup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqGroup = assEqGroupService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqGroup);
		
	}
	
    
}

