package com.chd.hrp.eqc.controller.xymanage;

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
import com.chd.hrp.acc.service.accDictType.AccDictTypeService;
import com.chd.hrp.eqc.service.xymanage.EqcEQdeviceService;

@Controller
@RequestMapping(value = "/hrp/eqc/xymanage")
public class EqcEQdeviceController extends BaseController {

	private static Logger logger = Logger.getLogger(EqcEQdeviceController.class);
	
	@Resource(name = "EqcEQdeviceService")
	private final EqcEQdeviceService eqcEQdeviceService = null;
	
	/**
	 * @Description 主页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/eqDeviceMapMain", method = RequestMethod.GET) 
	public String eqDeviceMapMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  mode.addAttribute("mod_code", SessionManager.getModCode()); 
	  return "hrp/eqc/xymanage/EQDeviceMap/eqDeviceMapMain"; 
	}
	/**
	 * @Description 新增页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/eqDeviceMapAdd", method = RequestMethod.GET) 
	public String eqDeviceMapAdd(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode()); 
		return "hrp/eqc/xymanage/EQDeviceMap/eqDeviceMapAdd"; 
	}
	/**
	 * @Description 修改页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/eqDeviceMapUpdata", method = RequestMethod.GET) 
	public String eqDeviceMapUpdata(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode()); 
		return "hrp/eqc/xymanage/EQDeviceMap/eqDeviceMapUpdata"; 
	}
	
	
	
	/**
	 * @Description 查询
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/queryEQDeviceMap", method = RequestMethod.POST) 
	public Map<String, Object> queryEQDeviceMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  mode.addAttribute("mod_code", SessionManager.getModCode()); 
	  String result = null ; 
	  result = eqcEQdeviceService.queryEQdevice(mapVo);
	  return JSONObject.parseObject(result);
	}
	
	
	/**
	 * @Description 添加
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/addEQDeviceMap", method = RequestMethod.POST) 
	public Map<String, Object> addEQDeviceMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  mode.addAttribute("mod_code", SessionManager.getModCode()); 
	  String result = null ; 
	  result = eqcEQdeviceService.addEQdevice(mapVo);
	  return JSONObject.parseObject(result);
	}
	
	
	/**
	 * @Description 删除
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteEQDeviceMap", method = RequestMethod.POST) 
	public Map<String, Object> deleteEQDeviceMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode()); 
		String result = null ; 
		result = eqcEQdeviceService.deleteEQdevice(mapVo);
		return JSONObject.parseObject(result);
	}
	
	
	/**
	 * @Description 修改
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEQDeviceMap", method = RequestMethod.POST) 
	public Map<String, Object> updateEQDeviceMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("mod_code", SessionManager.getModCode()); 
		String result = null ; 
		result = eqcEQdeviceService.updateEQdevice(mapVo);
		return JSONObject.parseObject(result);
	}
  
}
