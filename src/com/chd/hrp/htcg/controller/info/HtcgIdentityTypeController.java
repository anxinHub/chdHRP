package com.chd.hrp.htcg.controller.info;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.htcg.entity.info.HtcgIdentityType;
import com.chd.hrp.htcg.service.info.HtcgIdentityTypeService;

@Controller
public class HtcgIdentityTypeController extends BaseController {
	
	public static Logger logger = Logger.getLogger(HtcgIdentityTypeController.class);
	
	@Resource(name = "htcgIdentityTypeService")
	private final HtcgIdentityTypeService htcgIdentityTypeService = null;
	
	
	
	@RequestMapping(value ="/hrp/htcg/info/identityType/htcgIdentityTypeMainPage",method=RequestMethod.GET)
	public String htcgIdentityTypeMainPage(Model model)throws Exception{
		return  "hrp/htcg/info/identityType/htcgIdentityTypeMain";
	}
	
	
	// 添加页面
	@RequestMapping(value = "/hrp/htcg/info/identityType/htcgIdentityTypeAddPage", method = RequestMethod.GET)
	public String htcgIdentityTypeAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/identityType/htcgIdentityTypeAdd";

	}
		
	@RequestMapping(value = "/hrp/htcg/info/identityType/addHtcgIdentityType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgIdentityType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String identityTypeJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("identity_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("identity_name").toString()));
		
		try {
			
			 identityTypeJson = htcgIdentityTypeService.addHtcgIdentityType(mapVo);
			 
		} catch (Exception e) {
			// TODO: handle exception
			identityTypeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(identityTypeJson);	
	}
	
	@RequestMapping(value="/hrp/htcg/info/identityType/queryHtcgIdentityType",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryHtcgIdentityType(@RequestParam Map<String,Object> mapVo,Model model) throws Exception{

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
        String result =htcgIdentityTypeService.queryHtcgIdentityType(getPage(mapVo));
        
		return JSONObject.parseObject(result);
	}
	
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/identityType/deleteHtcgIdentityType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgIdentityType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("identity_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String identityTypeJson = "";
		try {
			identityTypeJson = htcgIdentityTypeService.deleteBatchHtcgIdentityType(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			identityTypeJson = e.getMessage();
		}
		
	    return JSONObject.parseObject(identityTypeJson);		
	}
		
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/info/identityType/htcgIdentityTypeUpdatePage", method = RequestMethod.GET)
	public String htcgIdentityTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        HtcgIdentityType htcgIdentityType = htcgIdentityTypeService.queryHtcgIdentityTypeByCode(mapVo);
		mode.addAttribute("group_id", htcgIdentityType.getGroup_id());
		mode.addAttribute("hos_id", htcgIdentityType.getHos_id());
		mode.addAttribute("copy_code", htcgIdentityType.getCopy_code());
		mode.addAttribute("identity_code", htcgIdentityType.getIdentity_code());
		mode.addAttribute("identity_name", htcgIdentityType.getIdentity_name());
		mode.addAttribute("spell_code", htcgIdentityType.getSpell_code());
		mode.addAttribute("wbx_code", htcgIdentityType.getWbx_code());
		mode.addAttribute("is_stop", htcgIdentityType.getIs_stop());
		return "hrp/htcg/info/identityType/htcgIdentityTypeUpdate";
	}
			
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/identityType/updateHtcgIdentityType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIdentityType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("identity_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("identity_name").toString()));
		String identityTypeJson ="";
		try {
			identityTypeJson = htcgIdentityTypeService.updateHtcgIdentityType(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			identityTypeJson = e.getMessage();
		}
		
		
		return JSONObject.parseObject(identityTypeJson);
	}
	 
	@RequestMapping(value = "/hrp/htcg/info/identityType/importHtcgIdentityType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgIdentityType(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String identityTypeJson ="";
		
		try {

			 identityTypeJson = htcgIdentityTypeService.importHtcgIdentityType(mapVo);
		}
		catch (Exception e) {
			identityTypeJson = e.getMessage();
		}
		
		return JSONObject.parseObject(identityTypeJson);
	}
	
}
