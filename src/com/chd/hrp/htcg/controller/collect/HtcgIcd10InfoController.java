package com.chd.hrp.htcg.controller.collect;
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
import com.chd.hrp.htcg.entity.collect.HtcgIcd10Info;
import com.chd.hrp.htcg.service.collect.HtcgIcd10InfoService;

@Controller
public class HtcgIcd10InfoController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgIcd10InfoController.class);
	
	
	@Resource(name = "htcgIcd10InfoService")
	private final HtcgIcd10InfoService htcgIcd10InfoService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/htcgIcd10InfoMainPage", method = RequestMethod.GET)
	public String htcgIcd10InfoMainPage(Model mode) throws Exception {

		return "hrp/htcg/collect/icd10Info/htcgIcd10InfoMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/htcgIcd10InfoAddPage", method = RequestMethod.GET)
	public String htcgIcd10InfoAddPage(Model mode) throws Exception {
		return "hrp/htcg/collect/icd10Info/htcgIcd10InfoAdd";
	}
	

	// 保存
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/addHtcgIcd10Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgIcd10Info(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String icd10InfoJson;
		try {
			icd10InfoJson = htcgIcd10InfoService.addHtcgIcd10Info(mapVo);
		} catch (Exception e) {
			icd10InfoJson=e.getMessage();
		}
		return JSONObject.parseObject(icd10InfoJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/queryHtcgIcd10Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryIcd10Info(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String icd10Info = htcgIcd10InfoService.queryHtcgIcd10Info(getPage(mapVo));
		return JSONObject.parseObject(icd10Info);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/deleteHtcgIcd10Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgIcd10Info(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
				String [] ids =id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("mr_no", ids[3]);//实际实体类变量
				mapVo.put("in_hos_no", ids[4]);
            listVo.add(mapVo);
        }
		String icd10InfoJson;
		try {
			icd10InfoJson = htcgIcd10InfoService.deleteBatchHtcgIcd10Info(listVo);
		} catch (Exception e) {
			icd10InfoJson=e.getMessage();
		}
	   return JSONObject.parseObject(icd10InfoJson);
			
	}
	
	
	// 修改页面跳转 
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/htcgIcd10InfoUpdatePage", method = RequestMethod.GET)
	public String htcgIcd10InfoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcgIcd10Info htcgIcd10Info = htcgIcd10InfoService.queryHtcgIcd10InfoByCode(mapVo);
		mode.addAttribute("group_id", htcgIcd10Info.getGroup_id());
		mode.addAttribute("hos_id", htcgIcd10Info.getHos_id());
		mode.addAttribute("copy_code", htcgIcd10Info.getCopy_code());
		mode.addAttribute("mr_no", htcgIcd10Info.getMr_no());
		mode.addAttribute("in_hos_no", htcgIcd10Info.getIn_hos_no());
		mode.addAttribute("icd10_type_code", htcgIcd10Info.getIcd10_type_code());
		mode.addAttribute("icd10_type_name", htcgIcd10Info.getIcd10_type_name());
		mode.addAttribute("icd10_code", htcgIcd10Info.getIcd10_code());
		mode.addAttribute("icd10_name", htcgIcd10Info.getIcd10_name());
		mode.addAttribute("icd10_seq", htcgIcd10Info.getIcd10_seq());
		mode.addAttribute("outcome_name", htcgIcd10Info.getOutcome_name());
		mode.addAttribute("outcome_code", htcgIcd10Info.getOutcome_code());
		return "hrp/htcg/collect/icd10Info/htcgIcd10InfoUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/updateHtcgIcd10Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgIcd10Info(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String icd10InfoJson;
		try {
			icd10InfoJson = htcgIcd10InfoService.updateHtcgIcd10Info(mapVo);
		} catch (Exception e) {
			icd10InfoJson=e.getMessage();
		}
		
		return JSONObject.parseObject(icd10InfoJson);
	}
	
	@RequestMapping(value = "/hrp/htcg/collect/icd10Info/importHtcgIcd10Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgIcd10Info(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String icd10InfoJson = "";
		try {
			icd10InfoJson = null;
		}
		catch (Exception e) {
			icd10InfoJson=e.getMessage();
		}
		return JSONObject.parseObject(icd10InfoJson);
	}
}

