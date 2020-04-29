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
import com.chd.hrp.htcg.entity.collect.HtcgMedicalAdvice;
import com.chd.hrp.htcg.service.collect.HtcgMedicalAdviceService;

@Controller
public class HtcgMedicalAdviceController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgMedicalAdviceController.class);
	
	
	@Resource(name = "htcgMedicalAdviceService")
	private final HtcgMedicalAdviceService htcgMedicalAdviceService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/htcgMedicalAdviceMainPage", method = RequestMethod.GET)
	public String htcgMedicalAdviceMainPage(Model mode) throws Exception {
		return "hrp/htcg/collect/medicalAdvice/htcgMedicalAdviceMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/htcgMedicalAdviceAddPage", method = RequestMethod.GET)
	public String htcgMedicalAdviceAddPage(Model mode) throws Exception {
		return "hrp/htcg/collect/medicalAdvice/htcgMedicalAdviceAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/addHtcgMedicalAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgMedicalAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdviceJson;
		try {
			medicalAdviceJson = htcgMedicalAdviceService.addHtcgMedicalAdvice(mapVo);
		} catch (Exception e) {
			medicalAdviceJson=e.getMessage();
		}
		return JSONObject.parseObject(medicalAdviceJson);
		
	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/queryHtcgMedicalAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMedicalAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdvice = htcgMedicalAdviceService.queryHtcgMedicalAdvice(getPage(mapVo));
		return JSONObject.parseObject(medicalAdvice);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/deleteHtcgMedicalAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMedicalAdvice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
			mapVo.put("charge_item_code", ids[10]);
            listVo.add(mapVo);
        }
		String medicalAdviceJson = null;
		
		try {
			
			medicalAdviceJson = htcgMedicalAdviceService.deleteBatchHtcgMedicalAdvice(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			medicalAdviceJson = e.getMessage();
		}
		
	   return JSONObject.parseObject(medicalAdviceJson);
			
	}
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/htcgMedicalAdviceUpdatePage", method = RequestMethod.GET)
	public String htcgMedicalAdviceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgMedicalAdvice htcgMedicalAdvice = htcgMedicalAdviceService.queryHtcgMedicalAdviceByCode(mapVo);
		mode.addAttribute("group_id",htcgMedicalAdvice.getGroup_id());
		mode.addAttribute("hos_id",htcgMedicalAdvice.getHos_id());
		mode.addAttribute("copy_code",htcgMedicalAdvice.getCopy_code());
		mode.addAttribute("mr_no", htcgMedicalAdvice.getMr_no());
		mode.addAttribute("in_hos_no", htcgMedicalAdvice.getIn_hos_no());
		mode.addAttribute("patient_name", htcgMedicalAdvice.getPatient_name());
		mode.addAttribute("advice_date", DateUtil.dateToString(htcgMedicalAdvice.getAdvice_date(), "yyyy-MM-dd")); 
		mode.addAttribute("order_by_no", htcgMedicalAdvice.getOrder_by_no());
		mode.addAttribute("order_by_id", htcgMedicalAdvice.getOrder_by_id());
		mode.addAttribute("order_by_code", htcgMedicalAdvice.getOrder_by_code());
		mode.addAttribute("order_by_name", htcgMedicalAdvice.getOrder_by_name());
		mode.addAttribute("perform_by_no", htcgMedicalAdvice.getPerform_by_no());
		mode.addAttribute("perform_by_id", htcgMedicalAdvice.getPerform_by_id());
		mode.addAttribute("perform_by_code", htcgMedicalAdvice.getPerform_by_code());
		mode.addAttribute("perform_by_name", htcgMedicalAdvice.getPerform_by_name());
		mode.addAttribute("charge_item_code", htcgMedicalAdvice.getCharge_item_code());
		mode.addAttribute("charge_item_code", htcgMedicalAdvice.getCharge_item_code());
		mode.addAttribute("charge_item_name", htcgMedicalAdvice.getCharge_item_name());
		mode.addAttribute("amount",htcgMedicalAdvice.getAmount());
		mode.addAttribute("price", htcgMedicalAdvice.getPrice());
		mode.addAttribute("income_money", htcgMedicalAdvice.getIncome_money());
		mode.addAttribute("recipe_type_code", htcgMedicalAdvice.getRecipe_type_code());
		mode.addAttribute("recipe_type_name", htcgMedicalAdvice.getRecipe_type_name());
		mode.addAttribute("place", htcgMedicalAdvice.getPlace());
		return "hrp/htcg/collect/medicalAdvice/htcgMedicalAdviceUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/updateHtcgMedicalAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgMedicalAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String medicalAdviceJson;
		try {
			medicalAdviceJson = htcgMedicalAdviceService.updateHtcgMedicalAdvice(mapVo);
		} catch (Exception e) {
			medicalAdviceJson=e.getMessage();
		}
		
		return JSONObject.parseObject(medicalAdviceJson);
	}
	
	
	//导入
	@RequestMapping(value = "/hrp/htcg/collect/medicalAdvice/importHtcgMedicalAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgMedicalAdvice(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String htcgMedicalAdvice;
		try {

			htcgMedicalAdvice = null;
		}
		catch (Exception e) {

			htcgMedicalAdvice = e.getMessage();

		}
		return JSONObject.parseObject(htcgMedicalAdvice);

	}
	
}

