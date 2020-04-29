package com.chd.hrp.pac.controller.cmitype;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.entity.cmitype.PACTInterfaceType;
import com.chd.hrp.pac.service.cmitype.COSTBUSISOURECDICTService;
import com.chd.hrp.pac.service.cmitype.PACTInterfaceTypeService;

@Controller
@RequestMapping(value = "/hrp/pac/cmitype")
public class PACTInterfaceTypeController  extends BaseController{

	@Resource(name = "PACTInterfaceTypeService")
	private PACTInterfaceTypeService InterfaceTypeService;
	
	@Resource(name = "COSTBUSISOURECDICTService")
	private COSTBUSISOURECDICTService CostService;
	
	
	@RequestMapping(value = "/PACTInterfaceTypeMaintPage", method = RequestMethod.GET)
	public String toPACTInterfaceTypeMaintPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("IFB_GROUPID",SessionManager.getGroupId());
		mode.addAttribute("IFB_PrjName",SessionManager.getHosId());
		mode.addAttribute("COPY_CODE",SessionManager.getCopyCode());
		return "hrp/pac/cmitype/PACTInterfaceTypeMaintPage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPACTInterfaceType", method = RequestMethod.POST)
	public Map<String, Object> queryPACTInterfaceType(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("IFB_GROUPID", SessionManager.getGroupId());
			mapVo.put("IFB_PrjName", SessionManager.getHosId());
			mapVo.put("COPY_CODE", SessionManager.getCopyCode());
			String query = InterfaceTypeService.queryPACTInterfaceType(getPage(mapVo));
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addPACTInterfaceType", method = RequestMethod.POST)
	public Map<String, Object> addPACTInterfaceType(@RequestParam String mapVo, Model mode) {
		try {
			List<PACTInterfaceType> listVo = JSONArray.parseArray(mapVo, PACTInterfaceType.class);
			String query = InterfaceTypeService.addBatchPACTInterfaceType(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/deletePACTInterfaceType",method = RequestMethod.POST)
	public Map<String, Object> deletePACTInterfaceType(@RequestParam String mapVo, Model mode) {
		try {
			List<PACTInterfaceType> listVo = JSONArray.parseArray(mapVo, PACTInterfaceType.class);
			String query = InterfaceTypeService.deletePACTInterfaceTypByStatus(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/updatePACTInterfaceType",method = RequestMethod.POST)
	public Map<String, Object> updatePACTInterfaceType(@RequestParam String mapVo, Model mode) {
		try {
			List<PACTInterfaceType> listVo = JSONArray.parseArray(mapVo, PACTInterfaceType.class);
			String query = InterfaceTypeService.updatePACTInterfaceType(listVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/COSTBUSISOURECDICT", method = RequestMethod.POST)
	public String queryCOSTBUSISOURECDICT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {			
			return CostService.queryCOSTBUSISOURECDICT(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
		
	}
	
}
