
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
import com.chd.hrp.eqc.service.xymanage.AssEqEquipOperatorService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 09设备操作员 ASS_EQEquipOperator Controller实现类
*/
@Controller
public class AssEqEquipOperatorController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqEquipOperatorController.class);
	
	//引入Service服务
	@Resource(name = "assEqEquipOperatorService")
	private final AssEqEquipOperatorService assEqEquipOperatorService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipOperatorMainPage", method = RequestMethod.GET)
	public String assEqEquipOperatorMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqequipoperator/assEqEquipOperatorMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipOperatorAddPage", method = RequestMethod.GET)
	public String assEqEquipOperatorAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqequipoperator/assEqEquipOperatorAdd";

	}

	/**
	 * @Description 
	 * 添加数据 09设备操作员 ASS_EQEquipOperator
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqEquipOperator", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqEquipOperator(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			//mapVo.put("eo_eq_group", ids[1]);
			mapVo.put("user_id", ids[1]);
			mapVo.put("percent", ids[2]);
			mapVo.put("from_date", DateUtil.stringToDate(ids[3], "yyyy-MM-dd"));
			mapVo.put("to_date", DateUtil.stringToDate(ids[4], "yyyy-MM-dd"));
			mapVo.put("remark", ids[5]);
			mapVo.put("rowNo", ids[6]);// 行号
			mapVo.put("flag", ids[7]);// 添加 修改标识
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assEqEquipOperatorService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 09设备操作员 ASS_EQEquipOperator
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipOperatorUpdatePage", method = RequestMethod.GET)
	public String assEqEquipOperatorUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqEquipOperator = new HashMap<String, Object>();
    
		assEqEquipOperator = assEqEquipOperatorService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqEquipOperator.get("group_id"));
		mode.addAttribute("hos_id", assEqEquipOperator.get("hos_id"));
		mode.addAttribute("copy_code", assEqEquipOperator.get("copy_code"));
		mode.addAttribute("analysis_code", assEqEquipOperator.get("analysis_code"));
		//mode.addAttribute("eo_eq_group", assEqEquipOperator.get("eo_eq_group"));
		mode.addAttribute("user_id", assEqEquipOperator.get("user_id"));
		mode.addAttribute("percent", assEqEquipOperator.get("percent"));
		mode.addAttribute("remark", assEqEquipOperator.get("remark"));
		mode.addAttribute("from_date", assEqEquipOperator.get("from_date"));
		mode.addAttribute("to_date", assEqEquipOperator.get("to_date"));
		
		return "hrp/eqc/xymanage/asseqequipoperator/assEqEquipOperatorUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 09设备操作员 ASS_EQEquipOperator
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqEquipOperator", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqEquipOperator(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqEquipOperatorJson = assEqEquipOperatorService.update(mapVo);
		
		return JSONObject.parseObject(assEqEquipOperatorJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 09设备操作员 ASS_EQEquipOperator
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqEquipOperator", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqEquipOperator(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			//mapVo.put("eo_eq_group", ids[1]);
			mapVo.put("user_id", ids[1]);
			
			listVo.add(mapVo);
      
		}
	    
		String assEqEquipOperatorJson = assEqEquipOperatorService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqEquipOperatorJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 09设备操作员 ASS_EQEquipOperator
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqEquipOperator", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqEquipOperator(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqEquipOperator = assEqEquipOperatorService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqEquipOperator);
		
	}
	
    
}

