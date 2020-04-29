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
import com.chd.hrp.htcg.entity.collect.HtcgMrInfo;
import com.chd.hrp.htcg.service.collect.HtcgMrInfoService;


@Controller
public class HtcgMrInfoController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgMrInfoController.class);
	
	@Resource(name = "htcgMrInfoService")
	private final HtcgMrInfoService htcgMrInfoService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/htcgMrInfoMainPage", method = RequestMethod.GET)
	public String htcgMrInfoMainPage(Model mode) throws Exception {

		return "hrp/htcg/collect/mrInfo/htcgMrInfoMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/htcgMrInfoAddPage", method = RequestMethod.GET)
	public String htcgMrInfoAddPage(Model mode) throws Exception {

		return "hrp/htcg/collect/mrInfo/htcgMrInfoAdd";

	}
	


	// 保存
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/addHtcgMrInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgMrInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String mrInfoJson;
		try {
			mrInfoJson = htcgMrInfoService.addHtcgMrInfo(mapVo);
		} catch (Exception e) {
			mrInfoJson=e.getMessage();
		}

		return JSONObject.parseObject(mrInfoJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/queryHtcgMrInfo", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgMrInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String mrInfo = htcgMrInfoService.queryHtcgMrInfo(getPage(mapVo));

		return JSONObject.parseObject(mrInfo);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/deleteHtcgMrInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMrInfo(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("mr_no", ids[3]);//实际实体类变量
				mapVo.put("in_hos_no", ids[4]);
            listVo.add(mapVo);
        }
		String mrInfoJson;
		try {
			mrInfoJson = htcgMrInfoService.deleteBatchHtcgMrInfo(listVo);
		} catch (Exception e) {
			mrInfoJson=e.getMessage();
		}
	
	   return JSONObject.parseObject(mrInfoJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/htcgMrInfoUpdatePage", method = RequestMethod.GET)
	public String htcgMrInfoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgMrInfo mrInfo = htcgMrInfoService.queryHtcgMrInfoByCode(mapVo);
		mode.addAttribute("group_id", mrInfo.getGroup_id());
		mode.addAttribute("hos_id", mrInfo.getHos_id());
		mode.addAttribute("copy_code", mrInfo.getCopy_code());
		mode.addAttribute("mr_no", mrInfo.getMr_no());
		mode.addAttribute("in_hos_no", mrInfo.getIn_hos_no());
		mode.addAttribute("in_date", mrInfo.getIn_date());
		mode.addAttribute("in_dept_id", mrInfo.getIn_dept_id());
		mode.addAttribute("in_dept_no", mrInfo.getIn_dept_no());
		mode.addAttribute("in_dept_code", mrInfo.getIn_dept_code());
		mode.addAttribute("in_dept_name", mrInfo.getIn_dept_name());
		mode.addAttribute("out_date", mrInfo.getOut_date());
		mode.addAttribute("out_dept_id", mrInfo.getOut_dept_id());
		mode.addAttribute("out_dept_no", mrInfo.getOut_dept_no());
		mode.addAttribute("out_dept_code", mrInfo.getOut_dept_code());
		mode.addAttribute("out_dept_name", mrInfo.getOut_dept_name());
		mode.addAttribute("patient_name", mrInfo.getPatient_name());
		mode.addAttribute("patient_age", mrInfo.getPatient_age());
		mode.addAttribute("patient_sex", mrInfo.getPatient_sex());
		mode.addAttribute("birth_date", mrInfo.getBirth_date());
		mode.addAttribute("identity_code", mrInfo.getIdentity_code());
		mode.addAttribute("identity_name", mrInfo.getIdentity_name());
		mode.addAttribute("in_days", mrInfo.getIn_days());
		mode.addAttribute("director_name", mrInfo.getDirector_name());
		mode.addAttribute("major_name", mrInfo.getMajor_name());
		mode.addAttribute("in_hos_name", mrInfo.getIn_hos_name());
		mode.addAttribute("outcome_code", mrInfo.getOutcome_code());
		mode.addAttribute("outcome_name", mrInfo.getOutcome_name());
		return "hrp/htcg/collect/mrInfo/htcgMrInfoUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/updateHtcgMrInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMrInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String mrInfoJson;
		try {
			mrInfoJson = htcgMrInfoService.updateHtcgMrInfo(mapVo);
		} catch (Exception e) {
			mrInfoJson=e.getMessage();
		}
		
		return JSONObject.parseObject(mrInfoJson);
	}
	
	@RequestMapping(value = "/hrp/htcg/collect/mrInfo/importHtcgMrInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgMrInfo(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String mrInfoJson;
		try {

			 mrInfoJson = htcgMrInfoService.importHtcgMrInfo(mapVo);
		}
		catch (Exception e) {

			mrInfoJson = e.getMessage();

		}
		return JSONObject.parseObject(mrInfoJson);

	}
}

