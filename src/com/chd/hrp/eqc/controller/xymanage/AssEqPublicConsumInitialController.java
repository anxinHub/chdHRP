
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
import com.chd.hrp.eqc.service.xymanage.AssEqPublicConsumInitialService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial Controller实现类
*/
@Controller
public class AssEqPublicConsumInitialController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqPublicConsumInitialController.class);
	
	//引入Service服务
	@Resource(name = "assEqPublicConsumInitialService")
	private final AssEqPublicConsumInitialService assEqPublicConsumInitialService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumInitialMainPage", method = RequestMethod.GET)
	public String assEqPublicConsumInitialMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsuminitial/assEqPublicConsumInitialMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumInitialAddPage", method = RequestMethod.GET)
	public String assEqPublicConsumInitialAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsuminitial/assEqPublicConsumInitialAdd";

	}

	/**
	 * @Description 
	 * 添加数据 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqPublicConsumInitial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqPublicConsumInitial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("consum_code", ids[0])   ;
			mapVo.put("dept_code", ids[1]);
			mapVo.put("unit_code", ids[2]);
			mapVo.put("quantity_type", ids[3]);
			mapVo.put("quantity", ids[4]);
			mapVo.put("price", ids[5]);
			mapVo.put("amount", ids[6]);
			mapVo.put("use_date", DateUtil.stringToDate(ids[7], "yyyy-MM-dd"));
			mapVo.put("start_time", DateUtil.stringToDate(ids[8], "yyyy-MM-dd"));
			mapVo.put("remark", ids[9]);
			mapVo.put("rowNo", ids[10]);// 行号
			mapVo.put("flag", ids[11]);// 添加 修改标识
			
			mapVo.put("year", ids[7].substring(0, 4));
			mapVo.put("month",ids[7].substring(5, 7));
			mapVo.put("is_input_flag", 1);
			mapVo.put("invalid_flag", 1);
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assEqPublicConsumInitialService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumInitialUpdatePage", method = RequestMethod.GET)
	public String assEqPublicConsumInitialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqPublicConsumInitial = new HashMap<String, Object>();
    
		assEqPublicConsumInitial = assEqPublicConsumInitialService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqPublicConsumInitial.get("group_id"));
		mode.addAttribute("hos_id", assEqPublicConsumInitial.get("hos_id"));
		mode.addAttribute("copy_code", assEqPublicConsumInitial.get("copy_code"));
		mode.addAttribute("consum_code", assEqPublicConsumInitial.get("consum_code"));
		
		mode.addAttribute("dept_code", assEqPublicConsumInitial.get("dept_code"));
		mode.addAttribute("use_date", assEqPublicConsumInitial.get("use_date"));
		mode.addAttribute("start_time", assEqPublicConsumInitial.get("start_time"));
		mode.addAttribute("unit_code", assEqPublicConsumInitial.get("unit_code"));
		mode.addAttribute("price", assEqPublicConsumInitial.get("price"));
		mode.addAttribute("quantity_type", assEqPublicConsumInitial.get("quantity_type"));
		
		mode.addAttribute("quantity", assEqPublicConsumInitial.get("quantity"));
		mode.addAttribute("amount", assEqPublicConsumInitial.get("amount"));
		mode.addAttribute("year", assEqPublicConsumInitial.get("year"));
		mode.addAttribute("month", assEqPublicConsumInitial.get("month"));
		mode.addAttribute("is_input_flag", assEqPublicConsumInitial.get("is_input_flag"));
		mode.addAttribute("invalid_flag", assEqPublicConsumInitial.get("invalid_flag"));
		mode.addAttribute("remark", assEqPublicConsumInitial.get("remark"));
		
		return "hrp/eqc/xymanage/asseqpublicconsuminitial/assEqPublicConsumInitialUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqPublicConsumInitial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqPublicConsumInitial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumInitialJson = assEqPublicConsumInitialService.update(mapVo);
		
		return JSONObject.parseObject(assEqPublicConsumInitialJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqPublicConsumInitial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqPublicConsumInitial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split("@,")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("consum_code", ids[0])   ;
			mapVo.put("dept_code", ids[1]);
			
			listVo.add(mapVo);
      
		}
	    
		String assEqPublicConsumInitialJson = assEqPublicConsumInitialService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqPublicConsumInitialJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 15服务公共消耗资源期初表 ASS_EQPublicConsumInitial
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqPublicConsumInitial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqPublicConsumInitial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumInitial = assEqPublicConsumInitialService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqPublicConsumInitial);
		
	}
	
    
}

