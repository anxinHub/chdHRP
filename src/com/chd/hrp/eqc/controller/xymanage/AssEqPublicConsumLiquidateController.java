
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
import com.chd.hrp.eqc.service.xymanage.AssEqPublicConsumLiquidateService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 17服务公共消耗资源清算 ASS_EQPublicConsumLiquidate Controller实现类
*/
@Controller
public class AssEqPublicConsumLiquidateController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqPublicConsumLiquidateController.class);
	
	//引入Service服务
	@Resource(name = "assEqPublicConsumLiquidateService")
	private final AssEqPublicConsumLiquidateService assEqPublicConsumLiquidateService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumLiquidateMainPage", method = RequestMethod.GET)
	public String assEqPublicConsumLiquidateMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsumliquidate/assEqPublicConsumLiquidateMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumLiquidateAddPage", method = RequestMethod.GET)
	public String assEqPublicConsumLiquidateAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqpublicconsumliquidate/assEqPublicConsumLiquidateAdd";

	}

	/**
	 * @Description 
	 * 添加数据 17服务公共消耗资源清算 ASS_EQPublicConsumLiquidate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqPublicConsumLiquidate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqPublicConsumLiquidate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

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
			mapVo.put("liquid_date", DateUtil.stringToDate(ids[9], "yyyy-MM-dd"));
			mapVo.put("remark", ids[10]);
			mapVo.put("rowNo", ids[11]);// 行号
			mapVo.put("flag", ids[12]);// 添加 修改标识
			
			mapVo.put("invalid_flag", 1);
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assEqPublicConsumLiquidateService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 17服务公共消耗资源清算 ASS_EQPublicConsumLiquidate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqPublicConsumLiquidateUpdatePage", method = RequestMethod.GET)
	public String assEqPublicConsumLiquidateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqPublicConsumLiquidate = new HashMap<String, Object>();
    
		assEqPublicConsumLiquidate = assEqPublicConsumLiquidateService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqPublicConsumLiquidate.get("group_id"));
		mode.addAttribute("hos_id", assEqPublicConsumLiquidate.get("hos_id"));
		mode.addAttribute("copy_code", assEqPublicConsumLiquidate.get("copy_code"));
		mode.addAttribute("year", assEqPublicConsumLiquidate.get("year"));
		mode.addAttribute("month", assEqPublicConsumLiquidate.get("month"));
		mode.addAttribute("consum_code", assEqPublicConsumLiquidate.get("consum_code"));
		mode.addAttribute("dept_code", assEqPublicConsumLiquidate.get("dept_code"));
		mode.addAttribute("liquid_date", assEqPublicConsumLiquidate.get("liquid_date"));
		mode.addAttribute("unit_code", assEqPublicConsumLiquidate.get("unit_code"));
		mode.addAttribute("price", assEqPublicConsumLiquidate.get("price"));
		mode.addAttribute("quantity_type", assEqPublicConsumLiquidate.get("quantity_type"));
		mode.addAttribute("quantity", assEqPublicConsumLiquidate.get("quantity"));
		mode.addAttribute("amount", assEqPublicConsumLiquidate.get("amount"));
		mode.addAttribute("invalid_flag", assEqPublicConsumLiquidate.get("invalid_flag"));
		mode.addAttribute("remark", assEqPublicConsumLiquidate.get("remark"));
		
		return "hrp/eqc/xymanage/asseqpublicconsumliquidate/assEqPublicConsumLiquidateUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 17服务公共消耗资源清算 ASS_EQPublicConsumLiquidate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqPublicConsumLiquidate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqPublicConsumLiquidate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumLiquidateJson = assEqPublicConsumLiquidateService.update(mapVo);
		
		return JSONObject.parseObject(assEqPublicConsumLiquidateJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 17服务公共消耗资源清算 ASS_EQPublicConsumLiquidate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqPublicConsumLiquidate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqPublicConsumLiquidate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String assEqPublicConsumLiquidateJson = assEqPublicConsumLiquidateService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqPublicConsumLiquidateJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 17服务公共消耗资源清算 ASS_EQPublicConsumLiquidate
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqPublicConsumLiquidate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqPublicConsumLiquidate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqPublicConsumLiquidate = assEqPublicConsumLiquidateService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqPublicConsumLiquidate);
		
	}
	
    
}

