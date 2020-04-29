package com.chd.hrp.htcg.controller.collect;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.htcg.entity.collect.HtcgIcd9Info;
import com.chd.hrp.htcg.service.collect.HtcgIcd9InfoService;

@Controller
public class HtcgIcd9InfoController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgIcd9InfoController.class);
	
	@Resource(name = "htcgIcd9InfoService")
	private final HtcgIcd9InfoService htcgIcd9InfoService = null;
    
	
	// 维护页面跳转 collect
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/htcgIcd9InfoMainPage", method = RequestMethod.GET)
	public String htcgIcd9InfoMainPage(Model mode) throws Exception {

		return "hrp/htcg/collect/icd9Info/htcgIcd9InfoMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/htcgIcd9InfoAddPage", method = RequestMethod.GET)
	public String htcgIcd9InfoAddPage(Model mode) throws Exception {
		return "hrp/htcg/collect/icd9Info/htcgIcd9InfoAdd";
	}

	// 保存
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/addIcdHtcg9Info", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addIcdHtcg9Info(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String icd9InfoJson;
		try {
			icd9InfoJson = htcgIcd9InfoService.addIcdHtcg9Info(mapVo);
		} catch (Exception e) {
			icd9InfoJson=e.getMessage();
		}

		return JSONObject.parseObject(icd9InfoJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/queryHtcgIcd9Info", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgIcd9Info(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String icd9Info = htcgIcd9InfoService.queryHtcgIcd9Info(getPage(mapVo));
		return JSONObject.parseObject(icd9Info);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/deleteHtcgIcd9Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgIcd9Info(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String  id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String [] result = id.split("@");
			mapVo.put("group_id", result[0]);
			mapVo.put("hos_id", result[1]);
			mapVo.put("copy_code", result[2]);
            mapVo.put("mr_no", result[3]);//实际实体类变量
            mapVo.put("in_hos_no",result[4]);
            listVo.add(mapVo);
        }
		String icd9InfoJson = "";
		try {
			
			icd9InfoJson = htcgIcd9InfoService.deleteBatchHtcgIcd9Info(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			icd9InfoJson = e.getMessage();
		}
	   return JSONObject.parseObject(icd9InfoJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/htcgIcd9InfoUpdatePage", method = RequestMethod.GET)
	public String htcgIcd9InfoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgIcd9Info icd9Info = htcgIcd9InfoService.queryHtcgIcd9InfoByCode(mapVo);
		mode.addAttribute("group_id",icd9Info.getGroup_id());
		mode.addAttribute("hos_id",icd9Info.getHos_id());
		mode.addAttribute("copy_code",icd9Info.getCopy_code());
		mode.addAttribute("mr_no", icd9Info.getMr_no());
		mode.addAttribute("in_hos_no", icd9Info.getIn_hos_no());
		mode.addAttribute("patient_name", icd9Info.getPatient_name());
		mode.addAttribute("icd9_code", icd9Info.getIcd9_code());
		mode.addAttribute("icd9_name", icd9Info.getIcd9_name());
		mode.addAttribute("icd9_seq", icd9Info.getIcd9_seq());
		mode.addAttribute("icd9_date", DateUtil.dateToString(icd9Info.getIcd9_date(), "yyyy-MM-dd"));
		mode.addAttribute("icd9_time", icd9Info.getIcd9_time());
		mode.addAttribute("icd9_oper", icd9Info.getIcd9_oper());
		mode.addAttribute("anest_type_code", icd9Info.getAnest_type_code());
		mode.addAttribute("anest_type_name", icd9Info.getAnest_type_name());
		mode.addAttribute("anest_oper", icd9Info.getAnest_oper());
		return "hrp/htcg/collect/icd9Info/htcgIcd9InfoUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/updateHtcgIcd9Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgIcd9Info(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String icd9InfoJson;
		try {
			icd9InfoJson = htcgIcd9InfoService.updateHtcgIcd9Info(mapVo);
		} catch (Exception e) {
			icd9InfoJson=e.getMessage();
		}
		
		return JSONObject.parseObject(icd9InfoJson);
	}
	
	//导入
	@RequestMapping(value = "/hrp/htcg/collect/icd9Info/importHtcgIcd9Info", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgIcd9Info(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String htcgIcd9Info;
		try {

			htcgIcd9Info = null;
		}
		catch (Exception e) {

			htcgIcd9Info = e.getMessage();

		}
		return JSONObject.parseObject(htcgIcd9Info);

	}
	
	
}

