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
import com.chd.base.util.DateUtil;
import com.chd.hrp.htcg.entity.collect.HtcgDrugAdvice;
import com.chd.hrp.htcg.service.collect.HtcgDrugAdviceService;

@Controller
public class HtcgDrugAdviceController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrugAdviceController.class);
	
	@Resource(name = "htcgDrugAdviceService")
	private final HtcgDrugAdviceService htcgDrugAdviceService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/htcgDrugAdviceMainPage", method = RequestMethod.GET)
	public String htcgDrugAdviceMainPage(Model mode) throws Exception {
		return "hrp/htcg/collect/drugAdvice/htcgDrugAdviceMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/htcgDrugAdviceAddPage", method = RequestMethod.GET)
	public String htcgDrugAdviceAddPage(Model mode) throws Exception {
		return "hrp/htcg/collect/drugAdvice/htcgDrugAdviceAdd";
	}
	

	// 保存
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/addHtcgDrugAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgDrugAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drugAdviceJson;
		try {
			drugAdviceJson = htcgDrugAdviceService.addHtcgDrugAdvice(mapVo);
		} catch (Exception e) {
			drugAdviceJson=e.getMessage();
		}

		return JSONObject.parseObject(drugAdviceJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/queryHtcgDrugAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrugAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drugAdvice = htcgDrugAdviceService.queryHtcgDrugAdvice(getPage(mapVo));
		return JSONObject.parseObject(drugAdvice);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/deleteHtcgDrugAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrugAdvice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String [] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("mr_no", ids[3]);
			mapVo.put("in_hos_no", ids[4]);
			mapVo.put("advice_date", DateUtil.stringToDate(ids[5],"yyyy-MM-dd"));
			mapVo.put("order_by_no", ids[6]);
			mapVo.put("order_by_id", ids[7]);
			mapVo.put("perform_by_no", ids[8]);
			mapVo.put("perform_by_id", ids[9]);
			mapVo.put("drug_code", ids[10]);
            listVo.add(mapVo);
        }
		String drugAdviceJson = null;
		try {
			drugAdviceJson = htcgDrugAdviceService.deleteBathcHtcgDrugAdvice(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			drugAdviceJson = e.getMessage();
		}
		
	   return JSONObject.parseObject(drugAdviceJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/htcgDrugAdviceUpdatePage", method = RequestMethod.GET)
	public String htcgDrugAdviceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("advice_date", DateUtil.stringToDate(mapVo.get("advice_date").toString(),"yyyy-MM-dd"));
		HtcgDrugAdvice htcgDrugAdvice = htcgDrugAdviceService.queryHtcgDrugAdviceByCode(mapVo);
		mode.addAttribute("group_id",htcgDrugAdvice.getGroup_id());
		mode.addAttribute("hos_id",htcgDrugAdvice.getHos_id());
		mode.addAttribute("copy_code",htcgDrugAdvice.getCopy_code());
		mode.addAttribute("mr_no", htcgDrugAdvice.getMr_no());
		mode.addAttribute("in_hos_no", htcgDrugAdvice.getIn_hos_no());
		mode.addAttribute("patient_name", htcgDrugAdvice.getPatient_name());
		mode.addAttribute("advice_date", DateUtil.dateToString(htcgDrugAdvice.getAdvice_date(), "yyyy-MM-dd")); 
		mode.addAttribute("order_by_no", htcgDrugAdvice.getOrder_by_no());
		mode.addAttribute("order_by_id", htcgDrugAdvice.getOrder_by_id());
		mode.addAttribute("order_by_code", htcgDrugAdvice.getOrder_by_code());
		mode.addAttribute("order_by_name", htcgDrugAdvice.getOrder_by_name());
		mode.addAttribute("perform_by_no", htcgDrugAdvice.getPerform_by_no());
		mode.addAttribute("perform_by_id", htcgDrugAdvice.getPerform_by_id());
		mode.addAttribute("perform_by_code", htcgDrugAdvice.getPerform_by_code());
		mode.addAttribute("perform_by_name", htcgDrugAdvice.getPerform_by_name());
		mode.addAttribute("drug_code", htcgDrugAdvice.getDrug_code());
		mode.addAttribute("drug_name", htcgDrugAdvice.getDrug_name());
		mode.addAttribute("amount",htcgDrugAdvice.getAmount());
		mode.addAttribute("price", htcgDrugAdvice.getPrice());
		mode.addAttribute("income_money", htcgDrugAdvice.getIncome_money());
		mode.addAttribute("recipe_type_code", htcgDrugAdvice.getRecipe_type_code());
		mode.addAttribute("recipe_type_name", htcgDrugAdvice.getRecipe_type_name());
		mode.addAttribute("place", htcgDrugAdvice.getPlace());
		return "hrp/htcg/collect/drugAdvice/htcgDrugAdviceUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/updateHtcgDrugAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgDrugAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drugAdviceJson;
		try {
			drugAdviceJson = htcgDrugAdviceService.updateHtcgDrugAdvice(mapVo);
		} catch (Exception e) {
			drugAdviceJson=e.getMessage();
		}
		
		return JSONObject.parseObject(drugAdviceJson);
	}
	
	//导入
	@RequestMapping(value = "/hrp/htcg/collect/drugAdvice/importHtcgDrugAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgDrugAdvice(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String htcgDrugAdvice;
		try {

			htcgDrugAdvice = null;
		}
		catch (Exception e) {

			htcgDrugAdvice = e.getMessage();

		}
		return JSONObject.parseObject(htcgDrugAdvice);

	}
	
}

