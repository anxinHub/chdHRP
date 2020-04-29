package com.chd.hrp.pac.controller.skht.change;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;
import com.chd.hrp.pac.service.basicset.common.PactChangeService;
import com.chd.hrp.pac.service.skht.changeaftersign.PactChangeAfterSignSKHTService;

/**
 * 收款合同签订后合同变更
 * 2020-03-067
 * @author cyw
 *
 */
@Controller
public class PactChangeAfterSignSKHTController {
	
	@Resource(name="pactChangeAfterSignSKHTService")
	private final PactChangeAfterSignSKHTService pactChangeAfterSignSKHTService=null;
	

	@Resource(name = "pactChangeService")
	private PactChangeService pactChangeService;
	
	/**
	 * 主页跳转
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/pac/skht/change/pactAfterChangeSKHTMainPage",method=RequestMethod.GET)
	public String pactChangeAfterSignSKHTMain(@RequestParam Map<String, Object> mapVo,Model model){
		
		return "hrp/pac/skht/changeaftersign/pactChangeAfterSignSKHTMain";
	}
	/**
	 * 添加页面跳转
	 * @return
	 */
	@RequestMapping(value="/hrp/pac/skht/changeaftersign/addPactAfterChangeSKHT",method=RequestMethod.GET)
   public String addPactAfterChangeSKHTMain(@RequestParam Map<String, Object> mapVo,Model model){
	   
	   return "hrp/pac/skht/changeaftersign/pactAddChangeAfterSign";
   }
	/**
	 * 跳转修改页面，
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/pac/skht/changeaftersign/updatePactAfterChangeSKHT",method=RequestMethod.GET)
	public String updatePactAfterChangeSKHT(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		Map<String, Object> change=pactChangeAfterSignSKHTService.querySKHTByChangeCode(mapVo);
		model.addAttribute("mapresult", change);
		model.addAttribute("state", mapVo.get("state"));
		return "hrp/pac/skht/changeaftersign/pactChangeAfterSignSKHTEdit";
	}
	/**
	 * 备份合同修改页面
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/pac/skht/changeaftersign/UpdateChangeSKHTC",method=RequestMethod.GET)
	public String UpdateChangeSKHTC(@RequestParam Map<String, Object> mapVo,Model mode){
		
		mode.addAttribute("change_code", mapVo.get("change_code"));
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		PactMainSKHTEntity entity = pactChangeService.queryPrePactMainSKHTByChangeCode(mapVo);
		if (entity != null) {
			mode.addAttribute("entity", entity);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			mode.addAttribute("sign_date", format.format(entity.getSign_date()));
			mode.addAttribute("start_date", format.format(entity.getStart_date()));
			mode.addAttribute("end_date", format.format(entity.getEnd_date()));
			mode.addAttribute("change_code", mapVo.get("change_code"));
		}
		return "hrp/pac/skht/changeaftersign/pactUpdateChangeSKHTC";
	}
	///根据客户带出相应的收款合同(过滤条件：合同状态未签订后，验证数据权限)
	@RequestMapping(value="/hrp/pac/skht/changeaftersign/querySKHTbyCus",method=RequestMethod.POST)
	@ResponseBody
	public String querySKHTbyCus(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String res=pactChangeAfterSignSKHTService.querySKHTbyCus(mapVo);
		return res;
	}
	/**
	 * 判断是否有未完成的变更单
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/pac/skht/changeaftersign/ifExitsPactOthers",method=RequestMethod.POST)
	@ResponseBody
	public String ifExitsPactOthers(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String count=pactChangeAfterSignSKHTService.queryExitsPactOthers(mapVo);
		return count;
	}
	
	/**
	 * 增加变更单（合同签订后变更）
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/pac/skht/changeaftersign/AddChangeAfterSign",method=RequestMethod.POST)
	@ResponseBody
	public String AddChangeAfterSign(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		String res=pactChangeAfterSignSKHTService.addChangeAfterSign(mapVo);
		return res;
	}
	
	/**
	 * 查询收款合同明细
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/pac/skht/changeaftersign/queryPactDetSKHT", method = RequestMethod.POST)
	public Map<String, Object> queryPactDetSKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("table_name", "pact_det_skht");
			String query = pactChangeAfterSignSKHTService.queryPactDetSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 查询收款合同明细(备份表)
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/queryPactDetSKHTC", method = RequestMethod.POST)
public Map<String, Object> queryPactDetSKHTC(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("table_name", "pact_det_skht_c");
			String query = pactChangeAfterSignSKHTService.queryPactDetSKHT(mapVo);
			return JSONObject.parseObject(query);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * 删除备份合同
	 * @param mapVo
	 * @param mode
	 * @return
	 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/deletePactMainSKHTC", method = RequestMethod.POST)
public String deletePactMainSKHTC(@RequestParam String param, Model mode){
		 
		  List<Map<String, Object>> listvo=new ArrayList();
		  String[] arrayOfString;
		   int j = (arrayOfString = param.split(",")).length;
		   
		    for (int i = 0; i < j; i++)
		    {
		    	  String detail = arrayOfString[i];
			      Map<String, Object> mapVo = new HashMap();
			      mapVo.put("group_id", SessionManager.getGroupId());
					mapVo.put("hos_id", SessionManager.getHosId());
					mapVo.put("copy_code", SessionManager.getCopyCode());
			      mapVo.put("pact_code", detail.split("@")[0]);
			      mapVo.put("change_code", detail.split("@")[1]);
			      
			      listvo.add(mapVo);
		    }
        String res=pactChangeAfterSignSKHTService.deletePactMainSKHTC(listvo);
		return res;
	}
	
	/**
	 * 提交
	 * @param mapVo
	 * @param mode
	 * @return
	 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/submitPactMainSKHTC", method = RequestMethod.POST)
public String submitPactMainSKHTC(@RequestParam String param, Model mode){
		
		  List<Map<String, Object>> listvo=new ArrayList();
		  String[] arrayOfString;
		   int j = (arrayOfString = param.split(",")).length;
		   SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		   Date mkdate=new Date();
		    for (int i = 0; i < j; i++)
		    {
		    	  String detail = arrayOfString[i];
			      Map<String, Object> mapVo = new HashMap();
			      mapVo.put("group_id", SessionManager.getGroupId());
				  mapVo.put("hos_id", SessionManager.getHosId());
				  mapVo.put("copy_code", SessionManager.getCopyCode());
			      mapVo.put("pact_code", detail.split("@")[0]);
			      mapVo.put("change_code", detail.split("@")[1]);
			      mapVo.put("user_id", SessionManager.getUserId());			      
			      mapVo.put("make_Date", format.format(mkdate));
			      listvo.add(mapVo);
		    }
        String res=pactChangeAfterSignSKHTService.updateSubmitPactMainSKHTC(listvo);
		return res;
	}
	
	/**
	 * 撤销
	 * @param mapVo
	 * @param mode
	 * @return
	 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/cancelPactMainSKHTC", method = RequestMethod.POST)
public String cancelPactMainSKHTC(@RequestParam String param, Model mode){
		
		  List<Map<String, Object>> listvo=new ArrayList();
		  String[] arrayOfString;
		   int j = (arrayOfString = param.split(",")).length;
		   SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		   Date mkdate=new Date();
		    for (int i = 0; i < j; i++)
		    {
		    	  String detail = arrayOfString[i];
			      Map<String, Object> mapVo = new HashMap();
			      mapVo.put("group_id", SessionManager.getGroupId());
				  mapVo.put("hos_id", SessionManager.getHosId());
				  mapVo.put("copy_code", SessionManager.getCopyCode());
			      mapVo.put("pact_code", detail.split("@")[0]);
			      mapVo.put("change_code", detail.split("@")[1]);
			      mapVo.put("user_id", SessionManager.getUserId());

			      mapVo.put("make_Date", format.format(mkdate));
			      listvo.add(mapVo);
		    }
        String res=pactChangeAfterSignSKHTService.updateCancelPactMainSKHTC(listvo);
		return res;
	}
	
	/**
	 * 确认
	 * @param mapVo
	 * @param mode
	 * @return
	 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/confirmPactMainSKHTC", method = RequestMethod.POST)
public String confirmPactMainSKHTC(@RequestParam String param, Model mode){
		
		  List<Map<String, Object>> listvo=new ArrayList();
		  String[] arrayOfString;
		   int j = (arrayOfString = param.split(",")).length;
		   SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		   Date mkdate=new Date();
		    for (int i = 0; i < j; i++)
		    {
		    	  String detail = arrayOfString[i];
			      Map<String, Object> mapVo = new HashMap();
			      mapVo.put("group_id", SessionManager.getGroupId());
				  mapVo.put("hos_id", SessionManager.getHosId());
				  mapVo.put("copy_code", SessionManager.getCopyCode());
			      mapVo.put("pact_code", detail.split("@")[0]);
			      mapVo.put("change_code", detail.split("@")[1]);
			      mapVo.put("user_id", SessionManager.getUserId());
			      mapVo.put("make_Date", format.format(mkdate));
			      listvo.add(mapVo);
		    }
        String res=pactChangeAfterSignSKHTService.confirmPactMainSKHTC(listvo);
		return res;
	}
	
/**
 * 更新备份合同
 * @param mapVo
 * @param mode
 * @return
 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/updatePactMainSKHTC", method = RequestMethod.POST)
public String updatePactMainSKHTC(@RequestParam Map<String, Object> mapVo, Model mode){
			 mapVo.put("group_id", SessionManager.getGroupId());
			 mapVo.put("hos_id", SessionManager.getHosId());
			 mapVo.put("copy_code", SessionManager.getCopyCode());	
			String res=pactChangeAfterSignSKHTService.updatePactMainSKHTC(mapVo);
			return res;
		}

/**
 * 更新变更单信息
 * @param mapVo
 * @param model
 * @return
 */
@ResponseBody
@RequestMapping(value = "/hrp/pac/skht/changeaftersign/UpdateChangeAfterSign", method = RequestMethod.POST)
public String UpdateChangeAfterSign(@RequestParam Map<String, Object> mapVo,Model model){
	mapVo.put("group_id", SessionManager.getGroupId());
	mapVo.put("hos_id", SessionManager.getHosId());
	mapVo.put("copy_code", SessionManager.getCopyCode());
	mapVo.put("user_id", SessionManager.getUserId());
	String res=pactChangeAfterSignSKHTService.updateChangeAfterSign(mapVo);
	return res;
}

}
