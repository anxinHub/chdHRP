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
import com.chd.hrp.htcg.entity.making.HtcgSchemeDrgs;
import com.chd.hrp.htcg.service.making.HtcgSchemeDrgsService;

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
public class HtcgSchemeDrgsController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgSchemeDrgsController.class);
	
	
	@Resource(name = "htcgSchemeDrgsService")
	private final HtcgSchemeDrgsService htcgSchemeDrgsService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/htcgSchemeDrgsMainPage", method = RequestMethod.GET)
	public String htcgSchemeDrgsMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemedrgs/htcgSchemeDrgsMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/htcgSchemeDrgsAddPage", method = RequestMethod.GET)
	public String htcgSchemeDrgsAddPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemedrgs/htcgSchemeDrgsAdd";

	}
	 
	
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/htcgSchemeDrgsAddBatchPage", method = RequestMethod.GET)
	public String htcgSchemeDrgsAddBatchPage(Model mode) throws Exception {

		return "hrp/htcg/making/schemedrgs/htcgSchemeDrgsAddBatch";

	}

	// 保存
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/addHtcgSchemeDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgSchemeDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String schemeDrgsJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			schemeDrgsJson = htcgSchemeDrgsService.addHtcgSchemeDrgs(mapVo);
		
		}catch(Exception e){
			
			schemeDrgsJson= e.getMessage();
			
		}
		return JSONObject.parseObject(schemeDrgsJson);
		
	}
	
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/addBatchHtcgSchemeDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchHtcgSchemeDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String schemeDrgsJson = "";
        String [] drgs_codes = mapVo.get("drgs_codes").toString().split(";");
        List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
        for(int i = 0 ; i < drgs_codes.length; i++){
        	Map<String, Object> map =new HashMap<String, Object>();
        	map.put("group_id", SessionManager.getGroupId());
        	map.put("hos_id", SessionManager.getHosId());
        	map.put("copy_code", SessionManager.getCopyCode());
        	map.put("scheme_code", mapVo.get("scheme_code"));
        	map.put("drgs_code", drgs_codes[i]);
        	listVo.add(map);
        }
        try{

        	schemeDrgsJson = htcgSchemeDrgsService.addBatchHtcgSchemeDrgs(listVo);

		}catch(Exception e){
			schemeDrgsJson= e.getMessage();
		}
        
		return JSONObject.parseObject(schemeDrgsJson);
		
	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/queryHtcgSchemeDrgs", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgSchemeDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String schemeDrgs = htcgSchemeDrgsService.queryHtcgSchemeDrgs(getPage(mapVo));
		return JSONObject.parseObject(schemeDrgs);
	}
	//删除
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/deleteHtcgSchemeDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeDrgs(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String schemeDrgsJson ;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String  id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
	            mapVo.put("scheme_code", ids[3]);
	            mapVo.put("drgs_code", ids[4]);
	            listVo.add(mapVo);
        }
		try{
			schemeDrgsJson = htcgSchemeDrgsService.deleteBatchHtcgSchemeDrgs(listVo);
		}catch(Exception e){
			schemeDrgsJson= e.getMessage();
		}
	   return JSONObject.parseObject(schemeDrgsJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/htcgSchemeDrgsUpdatePage", method = RequestMethod.GET)
	public String htcgSchemeDrgsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgSchemeDrgs schemeDrgs =  htcgSchemeDrgsService.queryHtcgSchemeDrgsByCode(mapVo);
		mode.addAttribute("group_id", schemeDrgs.getGroup_id());
		mode.addAttribute("hos_id", schemeDrgs.getHos_id());
		mode.addAttribute("copy_code", schemeDrgs.getCopy_code());
		mode.addAttribute("scheme_code", schemeDrgs.getScheme_code());
		mode.addAttribute("scheme_name", schemeDrgs.getScheme_name());
		mode.addAttribute("drgs_code", schemeDrgs.getDrgs_code());
		mode.addAttribute("drgs_name", schemeDrgs.getDrgs_name());
		mode.addAttribute("drgs_note", schemeDrgs.getDrgs_note());
		return "hrp/htcg/making/schemedrgs/htcgSchemeDrgsUpdate";
	}
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/schemedrgs/updateHtcgSchemeDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgSchemeDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String schemeDrgsJson ;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try{
			schemeDrgsJson = htcgSchemeDrgsService.updateHtcgSchemeDrgs(mapVo);
		}catch(Exception e){
			schemeDrgsJson= e.getMessage();
		}
		return JSONObject.parseObject(schemeDrgsJson);
	}
	
	

}

