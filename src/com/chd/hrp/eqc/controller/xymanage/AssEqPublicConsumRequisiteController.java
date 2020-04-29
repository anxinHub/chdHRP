
/*
 *
 */
 package com.chd.hrp.eqc.controller.xymanage;
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
import com.chd.hrp.eqc.service.xymanage.AssEqPublicConsumRequisiteService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 16服务公共消耗资源请领 ASS_EQPublicConsumRequisite Controller实现类
*/
@Controller
public class AssEqPublicConsumRequisiteController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqPublicConsumRequisiteController.class);
	
	//引入Service服务
	@Resource(name = "assEqPublicConsumRequisiteService")
	private final AssEqPublicConsumRequisiteService assEqPublicConsumRequisiteService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumRequisiteMainPage", method = RequestMethod.GET)
	public String assEqPublicConsumRequisiteMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsumrequisite/assEqPublicConsumRequisiteMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumRequisiteAddPage", method = RequestMethod.GET)
	public String assEqPublicConsumRequisiteAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsumrequisite/assEqPublicConsumRequisiteAdd";

	}

	/**
	 * @Description 
	 * 添加数据 16服务公共消耗资源请领 ASS_EQPublicConsumRequisite
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqPublicConsumRequisite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqPublicConsumRequisite(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("consum_code", ids[2])   ;
			mapVo.put("dept_code", ids[3]);
			mapVo.put("unit_code", ids[4]);
			mapVo.put("quantity_type", ids[5]);
			mapVo.put("quantity", ids[6]);
			mapVo.put("price", ids[7]);
			mapVo.put("amount", ids[8]);
			mapVo.put("req_date", DateUtil.stringToDate(ids[9], "yyyy-MM-dd"));
			mapVo.put("remark", ids[10]);
			mapVo.put("rowNo", ids[11]);// 行号
			mapVo.put("flag", ids[12]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 1);
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assEqPublicConsumRequisiteService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 16服务公共消耗资源请领 ASS_EQPublicConsumRequisite
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumRequisiteUpdatePage", method = RequestMethod.GET)
	public String assEqPublicConsumRequisiteUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqPublicConsumRequisite = new HashMap<String, Object>();
    
		assEqPublicConsumRequisite = assEqPublicConsumRequisiteService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqPublicConsumRequisite.get("group_id"));
		mode.addAttribute("hos_id", assEqPublicConsumRequisite.get("hos_id"));
		mode.addAttribute("copy_code", assEqPublicConsumRequisite.get("copy_code"));
		mode.addAttribute("year", assEqPublicConsumRequisite.get("year"));
		mode.addAttribute("month", assEqPublicConsumRequisite.get("month"));
		mode.addAttribute("consum_code", assEqPublicConsumRequisite.get("consum_code"));
		mode.addAttribute("dept_code", assEqPublicConsumRequisite.get("dept_code"));
		mode.addAttribute("req_date", assEqPublicConsumRequisite.get("req_date"));
		mode.addAttribute("unit_code", assEqPublicConsumRequisite.get("unit_code"));
		mode.addAttribute("price", assEqPublicConsumRequisite.get("price"));
		mode.addAttribute("quantity_type", assEqPublicConsumRequisite.get("quantity_type"));
		mode.addAttribute("quantity", assEqPublicConsumRequisite.get("quantity"));
		mode.addAttribute("amount", assEqPublicConsumRequisite.get("amount"));
		//mode.addAttribute("is_input_flag", assEqPublicConsumRequisite.get("is_input_flag"));
		mode.addAttribute("is_input_flag", assEqPublicConsumRequisite.get("is_input_flag"));
		mode.addAttribute("invalid_flag", assEqPublicConsumRequisite.get("invalid_flag"));
		mode.addAttribute("remark", assEqPublicConsumRequisite.get("remark"));
		
		return "hrp/eqc/xymanage/asseqpublicconsumrequisite/assEqPublicConsumRequisiteUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 16服务公共消耗资源请领 ASS_EQPublicConsumRequisite
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqPublicConsumRequisite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqPublicConsumRequisite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumRequisiteJson = assEqPublicConsumRequisiteService.update(mapVo);
		
		return JSONObject.parseObject(assEqPublicConsumRequisiteJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 16服务公共消耗资源请领 ASS_EQPublicConsumRequisite
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqPublicConsumRequisite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqPublicConsumRequisite(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
			
			mapVo.put("year", ids[0])   ;
			mapVo.put("month", ids[1])   ;
			mapVo.put("consum_code", ids[2])   ;
			mapVo.put("dept_code", ids[3]);
			
			listVo.add(mapVo);
      
		}
	    
		String assEqPublicConsumRequisiteJson = assEqPublicConsumRequisiteService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqPublicConsumRequisiteJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 16服务公共消耗资源请领 ASS_EQPublicConsumRequisite
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqPublicConsumRequisite", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqPublicConsumRequisite(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumRequisite = assEqPublicConsumRequisiteService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqPublicConsumRequisite);
		
	}
	
    
}

