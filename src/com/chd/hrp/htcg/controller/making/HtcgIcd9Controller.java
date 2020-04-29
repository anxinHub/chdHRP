package com.chd.hrp.htcg.controller.making;
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
import com.chd.hrp.htcg.entity.making.HtcgIcd9;
import com.chd.hrp.htcg.service.making.HtcgIcd9Service;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgIcd9Controller extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgIcd9Controller.class);
	
	
	@Resource(name = "htcgIcd9Service")
	private final HtcgIcd9Service htcgIcd9Service = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/icd9/htcgIcd9MainPage", method = RequestMethod.GET)
	public String htcgIcd9MainPage(Model mode) throws Exception {
		return "hrp/htcg/making/icd9/htcgIcd9Main";
	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/icd9/htcgIcd9AddPage", method = RequestMethod.GET)
	public String htcgIcd9AddPage(Model mode) throws Exception {

		return "hrp/htcg/making/icd9/htcgIcd9Add";

	}
	 

	// 保存
	@RequestMapping(value = "/hrp/htcg/making/icd9/addHtcgIcd9", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgIcd9(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String icd9Json ;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd9_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd9_name").toString()));
		try{
			icd9Json = htcgIcd9Service.addHtcgIcd9(mapVo);
		}catch(Exception e){
			icd9Json = e.getMessage();
		}
		return JSONObject.parseObject(icd9Json);
	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/making/icd9/queryHtcgIcd9", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgIcd9(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String	icd9Json = htcgIcd9Service.queryHtcgIcd9(getPage(mapVo));
		return JSONObject.parseObject(icd9Json);
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/making/icd9/deleteHtcgIcd9", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgIcd9(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String icd9Json ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("icd9_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		try{
			
			icd9Json = htcgIcd9Service.deleteBatchHtcgIcd9(listVo);
		
		}catch(Exception e){
			
			icd9Json = e.getMessage();
			
		}
		
	   return JSONObject.parseObject(icd9Json);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/icd9/htcgIcd9UpdatePage", method = RequestMethod.GET)
	
	public String htcgIcd9UpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgIcd9 icd9 =  htcgIcd9Service.queryHtcgIcd9ByCode(mapVo);
		mode.addAttribute("group_id", icd9.getGroup_id());
		mode.addAttribute("hos_id", icd9.getHos_id());
		mode.addAttribute("copy_code", icd9.getCopy_code());
		mode.addAttribute("icd9_code", icd9.getIcd9_code());
		mode.addAttribute("icd9_name", icd9.getIcd9_name());
		mode.addAttribute("spell_code", icd9.getSpell_code());
		mode.addAttribute("wbx_code", icd9.getWbx_code());
		mode.addAttribute("icd9_note", icd9.getIcd9_note());
		return "hrp/htcg/making/icd9/htcgIcd9Update";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/icd9/updateHtcgIcd9", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgIcd9(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String icd9Json ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			 
			icd9Json = htcgIcd9Service.updateHtcgIcd9(mapVo);
		
		}catch(Exception e){
			
			icd9Json = e.getMessage();
			
		}
		
		return JSONObject.parseObject(icd9Json);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/htcg/making/icd9/importHtcgIcd9", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> importHtcgIcd9(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String icd9Json ;
		try {
			icd9Json = htcgIcd9Service.importHtcgIcd9(mapVo);
		}
		catch (Exception e) {
			icd9Json = e.getMessage();
		}
		return JSONObject.parseObject(icd9Json);
	}

}

