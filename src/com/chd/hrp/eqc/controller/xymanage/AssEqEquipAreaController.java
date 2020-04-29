
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
import com.chd.hrp.eqc.service.xymanage.AssEqEquipAreaService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 13设备面积占比 ASS_EQEquipArea Controller实现类
*/
@Controller
public class AssEqEquipAreaController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssEqEquipAreaController.class);
	
	//引入Service服务
	@Resource(name = "assEqEquipAreaService")
	private final AssEqEquipAreaService assEqEquipAreaService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipAreaMainPage", method = RequestMethod.GET)
	public String assEqEquipAreaMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqequiparea/assEqEquipAreaMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipAreaAddPage", method = RequestMethod.GET)
	public String assEqEquipAreaAddPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/asseqequiparea/assEqEquipAreaAdd";

	}

	/**
	 * @Description 
	 * 添加数据 13设备面积占比 ASS_EQEquipArea
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/saveAssEqEquipArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssEqEquipArea(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("ass_card_no", ids[1]);
			mapVo.put("analysis_area", ids[2]);
			mapVo.put("area_percent", ids[3]);
			mapVo.put("from_date", DateUtil.stringToDate(ids[4], "yyyy-MM-dd"));
			mapVo.put("to_date", DateUtil.stringToDate(ids[5], "yyyy-MM-dd"));
			mapVo.put("remark", ids[6]);
			mapVo.put("rowNo", ids[7]);// 行号
			mapVo.put("flag", ids[8]);// 添加 修改标识
			
			listVo.add(mapVo);
	      
	    }
	        
		String assEqdevicemapJson = assEqEquipAreaService.save(listVo);

		return JSONObject.parseObject(assEqdevicemapJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 13设备面积占比 ASS_EQEquipArea
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqEquipAreaUpdatePage", method = RequestMethod.GET)
	public String assEqEquipAreaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		
		Map<String,Object> assEqEquipArea = new HashMap<String, Object>();
    
		assEqEquipArea = assEqEquipAreaService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assEqEquipArea.get("group_id"));
		mode.addAttribute("hos_id", assEqEquipArea.get("hos_id"));
		mode.addAttribute("copy_code", assEqEquipArea.get("copy_code"));
		mode.addAttribute("analysis_code", assEqEquipArea.get("analysis_code"));
		mode.addAttribute("ass_card_no", assEqEquipArea.get("ass_card_no"));
		mode.addAttribute("analysis_area", assEqEquipArea.get("analysis_area"));
		mode.addAttribute("area_percent", assEqEquipArea.get("area_percent"));
		mode.addAttribute("remark", assEqEquipArea.get("remark"));
		mode.addAttribute("from_date", assEqEquipArea.get("from_date"));
		mode.addAttribute("to_date", assEqEquipArea.get("to_date"));
		
		return "hrp/eqc/xymanage/asseqequiparea/assEqEquipAreaUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 13设备面积占比 ASS_EQEquipArea
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/updateAssEqEquipArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssEqEquipArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqEquipAreaJson = assEqEquipAreaService.update(mapVo);
		
		return JSONObject.parseObject(assEqEquipAreaJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 13设备面积占比 ASS_EQEquipArea
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/deleteAssEqEquipArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssEqEquipArea(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId())   ;
			mapVo.put("hos_id", SessionManager.getHosId())   ;
			mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			mapVo.put("analysis_code", ids[0])   ;
			mapVo.put("ass_card_no", ids[1]);
			
			listVo.add(mapVo);
      
		}
	    
		String assEqEquipAreaJson = assEqEquipAreaService.deleteBatch(listVo);
			
		return JSONObject.parseObject(assEqEquipAreaJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 13设备面积占比 ASS_EQEquipArea
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqEquipArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqEquipArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assEqEquipArea = assEqEquipAreaService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqEquipArea);
		
	}
	
	/**
	 * @Description 
	 * 根据 卡片号  查询 建筑物 使用面积
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssUseArea", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssUseArea(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String area = assEqEquipAreaService.queryAssUseArea(mapVo);

		return JSONObject.parseObject(area);
		
	}
	
    
}

