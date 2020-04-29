package com.chd.hrp.ass.controller.apply;

import java.util.ArrayList;
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
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.entity.apply.AssApplyDeptProof;
import com.chd.hrp.ass.service.apply.AssApplyDeptProofService;
/**
 * 采购申请单明细论证页面
 * @author cyw
 *
 */
@Controller
public class AssApplyDeptProofController extends BaseController{

	@Resource(name="assApplyDeptProofService")
	AssApplyDeptProofService assApplyDeptProofService=null;
	
	/**
	 * @Description 添加论证跳转页面
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assapplydeptproof/assApplyDeptProofPage", method = RequestMethod.GET)
	public String assApplyProofPage(@RequestParam("apply_id") String apply_id,@RequestParam("detail_id")String detail_id,Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
    
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("apply_id", apply_id);
		mapVo.put("detail_id", detail_id);
		if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		    //同步查询主表信息
		    AssApplyDeptProof applyDeptProof=assApplyDeptProofService.queryApplyProof(mapVo);
		    if(applyDeptProof!=null){
		   // System.out.println("------"+applyDeptProof.getProof_id());
		    mode.addAttribute("proof_id", applyDeptProof.getProof_id());
		    mode.addAttribute("apply_id", apply_id);
		    mode.addAttribute("detail_id", detail_id);
		    mode.addAttribute("bcost_year", applyDeptProof.getBcost_year());
		    mode.addAttribute("avg_cost", applyDeptProof.getAvg_cost());
		    mode.addAttribute("avg_profit", applyDeptProof.getAvg_profit());
		    mode.addAttribute("benefit_rate", applyDeptProof.getBenefit_rate());
		    mode.addAttribute("create_user", applyDeptProof.getCreate_user());
		    mode.addAttribute("use_place", applyDeptProof.getUse_place());
		    mode.addAttribute("apply_analyze", applyDeptProof.getApply_analyze());
		    mode.addAttribute("investigate_analyze", applyDeptProof.getInvestigate_analyze());
		    mode.addAttribute("describ", applyDeptProof.getDescrib());
		    }else{
		    	 mode.addAttribute("apply_id", apply_id);
				 mode.addAttribute("detail_id", detail_id);
		    }
		    
		return "hrp/ass/assapplydept/assApplyproof";

	}
	

	
	/**
	 * 查询论证明细表信息
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	
	@RequestMapping(value = "/hrp/ass/assapplydeptproof/queryApplyProofDetail", method = RequestMethod.POST)
	@ResponseBody
	public String queryApplyProofDetail(@RequestParam Map<String, Object> mapVo, Model mode){
		
		 if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		 // System.out.println(mapVo);
		    String ApplyProofDetail = assApplyDeptProofService.queryApplyProofDetail(getPage(mapVo));
		    return ApplyProofDetail;
		
	}
	
	/**
	 * 删除论证表
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/ass/assapplydeptproof/deleteApplyProof", method = RequestMethod.POST)
	@ResponseBody
	public String deleteApplyProof(@RequestParam Map<String, Object> mapVo, Model mode){
		
		 if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		 String res=assApplyDeptProofService.deleteApplyProof(mapVo);
		return res;
		
		
	}
	
	/**
	 * 删除论证明细表
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/ass/assapplydeptproof/deleteApplyProofDetail", method = RequestMethod.POST)
	@ResponseBody
	public String deleteApplyProofDetail(@RequestParam ("ParamVo") String proofdetail, Model mode){
		
		
		  List<Map<String, Object>> listVo = new ArrayList();
		    String[] arrayOfString;
		    int j = (arrayOfString = proofdetail.split(",")).length;
		    for (int i = 0; i < j; i++)
		    {
		      String id = arrayOfString[i];
		      Map<String, Object> mapVo = new HashMap();
		      mapVo.put("group_id", id.split("@")[0]);
		      mapVo.put("hos_id", id.split("@")[1]);
		      mapVo.put("copy_code", id.split("@")[2]);
		      mapVo.put("proof_id", id.split("@")[3]);
		      mapVo.put("proof_detail_id", id.split("@")[4]);
		      listVo.add(mapVo);
		    }
		    String resultstr = assApplyDeptProofService.deleteApplyProofDetail(listVo);
		    return resultstr;

		
	}
	
	/**
	 * 新增或者更新论证表信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	  @RequestMapping(value={"/hrp/ass/assapplydeptproof/updateApplyProof"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public Map<String, Object> updateApplyProof(@RequestParam Map<String, Object> mapVo,  Model mode)
	    throws Exception
	  {
		  if (mapVo.get("group_id") == null) {
		      mapVo.put("group_id", SessionManager.getGroupId());
		    }
		    if (mapVo.get("hos_id") == null) {
		      mapVo.put("hos_id", SessionManager.getHosId());
		    }
		    if (mapVo.get("copy_code") == null) {
		      mapVo.put("copy_code", SessionManager.getCopyCode());
		    }
		    
		    String res=assApplyDeptProofService.saveorupdateApplyProof(mapVo);
	    return JSONObject.parseObject(res);
	  }
}
