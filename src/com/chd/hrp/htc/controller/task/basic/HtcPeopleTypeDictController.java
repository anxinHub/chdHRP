package com.chd.hrp.htc.controller.task.basic;

import java.io.IOException;
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
import com.chd.hrp.htc.entity.task.basic.HtcPeopleTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcPeopleTypeDictService;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcPeopleTypeDictController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcPeopleTypeDictController.class);

	@Resource(name = "htcPeopleTypeDictService")
	private final HtcPeopleTypeDictService htcPeopleTypeDictService = null;

	 /*
	  * 人员类别维护页面跳转
	  * */
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/htcPeopleTypeDictMainPage", method = RequestMethod.GET)
	public String htcPeopleTypeDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/peopletypedict/htcPeopleTypeDictMain";

	}

	// 添加页面 
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/htcPeopleTypeDictAddPage", method = RequestMethod.GET)
	public String htcPeopleTypeDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/peopletypedict/htcPeopleTypeDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/addHtcPeopleTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			String peopleTypeDictJson;
			
				mapVo.put("group_id", SessionManager.getGroupId());
				
			    mapVo.put("hos_id", SessionManager.getHosId());
			
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("peop_type_name").toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("peop_type_name").toString()));

		  	try {
		  		
		  		peopleTypeDictJson = htcPeopleTypeDictService.addHtcPeopleTypeDict(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				peopleTypeDictJson = e.getMessage();
			}

			return JSONObject.parseObject(peopleTypeDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/queryHtcPeopleTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		    mapVo.put("group_id", SessionManager.getGroupId());
		
		    mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String peopleTypeDict = htcPeopleTypeDictService.queryHtcPeopleTypeDict(getPage(mapVo));

		return JSONObject.parseObject(peopleTypeDict);

	}

	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/deleteHtcPeopleTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcPeopleTypeDict(@RequestParam(value="ParamVo") String paramVo,Model mode)
			throws Exception {
		
		   String peopleTypeDictJson = "";
		   
		   List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		   
			for (String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				mapVo.put("group_id", ids[0]);
				
				mapVo.put("hos_id", ids[1]);
				
				mapVo.put("copy_code", ids[2]);
				
				mapVo.put("peop_type_code", ids[3]);
				
				listVo.add(mapVo);
			}
			
		try {
			
			peopleTypeDictJson = htcPeopleTypeDictService.deleteBatchHtcPeopleTypeDict(listVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			peopleTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleTypeDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/htcPeopleTypeDictUpdatePage", method = RequestMethod.GET)
	public String HtcPeopleTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HtcPeopleTypeDict peopleTypeDict = htcPeopleTypeDictService.queryHtcPeopleTypeDictByCode(mapVo);
		
		mode.addAttribute("group_id", peopleTypeDict.getGroup_id());
		
		mode.addAttribute("hos_id", peopleTypeDict.getHos_id());
		
		mode.addAttribute("copy_code", peopleTypeDict.getCopy_code());
		
		mode.addAttribute("peop_type_code", peopleTypeDict.getPeop_type_code());
		
		mode.addAttribute("peop_type_name", peopleTypeDict.getPeop_type_name());
		
		mode.addAttribute("peop_type_desc", peopleTypeDict.getPeop_type_desc());
		
		mode.addAttribute("is_stop", peopleTypeDict.getIs_stop());
		
		mode.addAttribute("spell_code", peopleTypeDict.getSpell_code());
		
		mode.addAttribute("wbx_code", peopleTypeDict.getWbx_code());

		return "hrp/htc/task/basic/peopletypedict/htcPeopleTypeDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/updateHtcPeopleTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		String peopleTypeDictJson = "";
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("peop_type_name").toString()));
		
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("peop_type_name").toString()));

		try {
	  		
			 peopleTypeDictJson = htcPeopleTypeDictService.updateHtcPeopleTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			peopleTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleTypeDictJson);
	}
	
	//同步系统平台人员类别
	@RequestMapping(value = "/hrp/htc/task/basic/peopletypedict/synchroHtcPeopleTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcPeopleTypeDict(Model mode)
			throws Exception {
		
       String peopleTypeDictJson = "";
       
        Map<String, Object> mapVo = new HashMap<String, Object>();
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());


		try {
	  		
			 peopleTypeDictJson = htcPeopleTypeDictService.synchroHtcPeopleTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			peopleTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(peopleTypeDictJson);

	}
	
	/**
	*<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/htc/task/basic/peopletypedict/impHtcPeopleTypeDict",method = RequestMethod.POST)  
	@ResponseBody
    public Map<String, Object> impHtcPeopleTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		 String peopleTypeDictJson = "";
		 
		try {
			 peopleTypeDictJson= htcPeopleTypeDictService.impHtcPeopleTypeDict(mapVo);
		} catch (Exception e) {
			peopleTypeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(peopleTypeDictJson);
    }  
}
