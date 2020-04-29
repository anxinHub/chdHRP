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
import com.chd.hrp.htcg.entity.collect.HtcgMaterialAdvice;
import com.chd.hrp.htcg.service.collect.HtcgMaterialAdviceService;

@Controller
public class HtcgMaterialAdviceController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgMaterialAdviceController.class);
	
	
	@Resource(name = "htcgMaterialAdviceService")
	private final HtcgMaterialAdviceService htcgMaterialAdviceService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/htcgMaterialAdviceMainPage", method = RequestMethod.GET)
	public String htcgMaterialAdviceMainPage(Model mode) throws Exception {
		return "hrp/htcg/collect/materialAdvice/htcgMaterialAdviceMain";
	}
	// 添加页面
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/htcgMaterialAdviceAddPage", method = RequestMethod.GET)
	public String htcgMaterialAdviceAddPage(Model mode) throws Exception {
		return "hrp/htcg/collect/materialAdvice/htcgMaterialAdviceAdd";

	}
	
	// 保存
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/addHtcgMaterialAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgMaterialAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String materialAdviceJson;
		try {
			materialAdviceJson = htcgMaterialAdviceService.addHtcgMaterialAdvice(mapVo);
		} catch (Exception e) {
			materialAdviceJson=e.getMessage();
		}

		return JSONObject.parseObject(materialAdviceJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/queryHtcgMaterialAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgMaterialAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String materialAdvice = htcgMaterialAdviceService.queryHtcgMaterialAdvice(getPage(mapVo));
		return JSONObject.parseObject(materialAdvice);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/deleteHtcgMaterialAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgMaterialAdvice(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
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
            mapVo.put("mate_code", ids[10]);
            listVo.add(mapVo);
        }
		String materialAdviceJson = htcgMaterialAdviceService.deleteBatchHtcgMaterialAdvice(listVo);
	   return JSONObject.parseObject(materialAdviceJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/htcgMaterialAdviceUpdatePage", method = RequestMethod.GET)
	public String htcgMaterialAdviceUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcgMaterialAdvice htcgMaterialAdvice = htcgMaterialAdviceService.queryHtcgMaterialAdviceByCode(mapVo);
		mode.addAttribute("group_id",htcgMaterialAdvice.getGroup_id());
		mode.addAttribute("hos_id",htcgMaterialAdvice.getHos_id());
		mode.addAttribute("copy_code",htcgMaterialAdvice.getCopy_code());
		mode.addAttribute("mr_no", htcgMaterialAdvice.getMr_no());
		mode.addAttribute("in_hos_no", htcgMaterialAdvice.getIn_hos_no());
		mode.addAttribute("patient_name", htcgMaterialAdvice.getPatient_name());
		mode.addAttribute("advice_date", DateUtil.dateToString(htcgMaterialAdvice.getAdvice_date(), "yyyy-MM-dd")); 
		mode.addAttribute("order_by_no", htcgMaterialAdvice.getOrder_by_no());
		mode.addAttribute("order_by_id", htcgMaterialAdvice.getOrder_by_id());
		mode.addAttribute("order_by_code", htcgMaterialAdvice.getOrder_by_code());
		mode.addAttribute("order_by_name", htcgMaterialAdvice.getOrder_by_name());
		mode.addAttribute("perform_by_no", htcgMaterialAdvice.getPerform_by_no());
		mode.addAttribute("perform_by_id", htcgMaterialAdvice.getPerform_by_id());
		mode.addAttribute("perform_by_code", htcgMaterialAdvice.getPerform_by_code());
		mode.addAttribute("perform_by_name", htcgMaterialAdvice.getPerform_by_name());
		mode.addAttribute("mate_code", htcgMaterialAdvice.getMate_code());
		mode.addAttribute("mate_name", htcgMaterialAdvice.getMate_name());
		mode.addAttribute("amount",htcgMaterialAdvice.getAmount());
		mode.addAttribute("price", htcgMaterialAdvice.getPrice());
		mode.addAttribute("income_money", htcgMaterialAdvice.getIncome_money());
		mode.addAttribute("recipe_type_code", htcgMaterialAdvice.getRecipe_type_code());
		mode.addAttribute("recipe_type_name", htcgMaterialAdvice.getRecipe_type_name());
		mode.addAttribute("place", htcgMaterialAdvice.getPlace());
		return "hrp/htcg/collect/materialAdvice/htcgMaterialAdviceUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/updateHtcgMaterialAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgMaterialAdvice(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String materialAdviceJson;
		try {
			materialAdviceJson = htcgMaterialAdviceService.updateHtcgMaterialAdvice(mapVo);
		} catch (Exception e) {
			materialAdviceJson=e.getMessage();
		}
		
		return JSONObject.parseObject(materialAdviceJson);
	}
	
	//导入
	@RequestMapping(value = "/hrp/htcg/collect/materialAdvice/importHtcgMaterialAdvice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgMaterialAdvice(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String htcgMaterialAdvice;
		try {

			htcgMaterialAdvice = null;
		}
		catch (Exception e) {

			htcgMaterialAdvice = e.getMessage();

		}
		return JSONObject.parseObject(htcgMaterialAdvice);

	}

}

