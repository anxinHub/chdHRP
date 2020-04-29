
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
import com.chd.hrp.eqc.service.base.AssEqcServDetConsService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 07服务细项消耗表 ASS_EQCSERVDETCONS Controller实现类
*/
@Controller
public class AssEqcServDetIConsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqcServDetIConsController.class);
	
	//引入Service服务
	@Resource(name = "assEqcServDetConsService")
	private final AssEqcServDetConsService assEqcServDetConsService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServDetConsMainPage", method = RequestMethod.GET)
	public String assEqcServDetConsMainPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcservdetcons/assEqcServDetConsMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServDetConsAddPage", method = RequestMethod.GET)
	public String assEqcServDetConsAddPage(Model mode) throws Exception {

		return "hrp/eqc/base/asseqcservdetcons/assEqcServDetConsAdd";

	}

	/**
	 * @Description 
	 * 添加数据 06服务项目消耗表 ASS_EQCSERVDETCONS
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/saveAssEqcServDetCons", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqcServDetCons(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("charge_item_id", ids[0])   ;
			mapVo.put("consum_code", ids[1])   ;
			mapVo.put("quantity", ids[2]);
			mapVo.put("quantity_type", ids[3]);
			mapVo.put("month_stat_flag", ids[4]);
			mapVo.put("cycle_num", ids[5]);
			mapVo.put("cycle_nuit", ids[6]);
			mapVo.put("type_name", ids[7]);
			mapVo.put("rowNo", ids[8]);// 行号
			mapVo.put("flag", ids[9]);// 添加 修改标识
			
			listVo.add(mapVo);
	      
	    }
		String assEqcServDetConsJson = assEqcServDetConsService.save(listVo);

		return JSONObject.parseObject(assEqcServDetConsJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 06服务项目消耗表 ASS_EQCSERVDETCONS
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/assEqcServDetConsUpdatePage", method = RequestMethod.GET)
	public String assEqcServDetConsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String,Object> assEqcServDetCons = new HashMap<String, Object>();
    
		assEqcServDetCons = assEqcServDetConsService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqcServDetCons.get("group_id"));
		mode.addAttribute("hos_id", assEqcServDetCons.get("hos_id"));
		mode.addAttribute("copy_code", assEqcServDetCons.get("copy_code"));
		mode.addAttribute("charge_item_id", assEqcServDetCons.get("charge_item_id"));
		mode.addAttribute("consum_code", assEqcServDetCons.get("consum_code"));
		mode.addAttribute("quantity", assEqcServDetCons.get("quantity"));
		mode.addAttribute("quantity_type", assEqcServDetCons.get("quantity_type"));
		mode.addAttribute("month_stat_flag", assEqcServDetCons.get("month_stat_flag"));
		mode.addAttribute("cycle_num", assEqcServDetCons.get("cycle_num"));
		mode.addAttribute("type_name", assEqcServDetCons.get("type_name"));
		mode.addAttribute("cycle_nuit", assEqcServDetCons.get("cycle_nuit"));
		
		return "hrp/eqc/base/asseqcservdetcons/assEqcServDetConsUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 06服务项目消耗表 ASS_EQCSERVDETCONS
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/updateAssEqcServDetCons", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqcServDetCons(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assEqcServDetConsJson = assEqcServDetConsService.update(mapVo);
		
		return JSONObject.parseObject(assEqcServDetConsJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 06服务项目消耗表 ASS_EQCSERVDETCONS
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/deleteAssEqcServDetCons", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqcServDetCons(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				mapVo.put("charge_item_id", ids[0])   ;
				mapVo.put("consum_code", ids[1]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assEqcServDetConsJson = assEqcServDetConsService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqcServDetConsJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 06服务项目消耗表 ASS_EQCSERVDETCONS
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/base/queryAssEqcServDetCons", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqcServDetCons(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assEqcServDetCons = assEqcServDetConsService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqcServDetCons);
		
	}
	
    
}

