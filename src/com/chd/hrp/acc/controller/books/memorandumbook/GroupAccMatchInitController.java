package com.chd.hrp.acc.controller.books.memorandumbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.hrp.acc.entity.AccMatchInit;
import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccMatchInitService;
@Controller
public class GroupAccMatchInitController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccMatchInitController.class);
	
	@Resource(name = "groupAccMatchInitService")
	private final GroupAccMatchInitService groupAccMatchInitService = null;
	
//	@Resource(name = "groupAccProjAttrService")
//	private final GroupAccProjAttrServiceImpl groupAccProjAttrService = null;
	
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/groupAccMatchInitMainPage", method = RequestMethod.GET)
	public String accMatchInitMainPage(Model mode) throws Exception {

		return "hrp/acc/accmatchinit/group/groupAccMatchInitMain";
	}
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/groupAccMatchInitAddPage", method = RequestMethod.GET)
	public String accMatchInitAddPage(Model mode) throws Exception {

		return "hrp/acc/accmatchinit/group/groupAccMatchInitAdd";

	}
	/**
	*配套资金初始账表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/addGroupAccMatchInit", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccMatchInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String accMatchInitJson = groupAccMatchInitService.addGroupAccMatchInit(mapVo);

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*配套资金初始账表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/queryGroupAccMatchInit", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccMatchInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String accMatchInitJson = groupAccMatchInitService.queryGroupAccMatchInit(getPage(mapVo));

		return JSONObject.parseObject(accMatchInitJson);
		
	}
	/**
	*配套资金初始账表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/deletBatchGroupAccMatchInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletBatchAccMatchInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("proj_id", id.split("@")[0]);
            mapVo.put("group_id", id.split("@")[1]);
            mapVo.put("hos_id", id.split("@")[2]);
            mapVo.put("copy_code", id.split("@")[3]);
            mapVo.put("acc_year", id.split("@")[4]);
            listVo.add(mapVo);
        }
		String accMatchInitJson = groupAccMatchInitService.deleteBatchGroupAccMatchInit(listVo);
	   return JSONObject.parseObject(accMatchInitJson);
			
	}
	
	/**
	*配套资金初始账表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/groupAccMatchInitUpdatePage", method = RequestMethod.GET)
	
	public String accMatchInitUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccMatchInit accMatchInit = new AccMatchInit();
		accMatchInit = groupAccMatchInitService.queryGroupAccMatchInitByCode(mapVo);
		mode.addAttribute("group_id", accMatchInit.getGroup_id());
		mode.addAttribute("hos_id", accMatchInit.getHos_id());
		mode.addAttribute("copy_code", accMatchInit.getCopy_code());
		mode.addAttribute("acc_year", accMatchInit.getAcc_year());
		mode.addAttribute("proj_id", accMatchInit.getProj_id());
		mode.addAttribute("proj_no", accMatchInit.getProj_no());
		mode.addAttribute("proj_code", accMatchInit.getProj_code());
		mode.addAttribute("proj_name", accMatchInit.getProj_name());
		mode.addAttribute("bal_os", accMatchInit.getBal_os());
		mode.addAttribute("sum_od", accMatchInit.getSum_od());
		mode.addAttribute("sum_oc", accMatchInit.getSum_oc());
		mode.addAttribute("end_os", accMatchInit.getEnd_os());
		return "hrp/acc/accmatchinit/group/groupAccMatchInitUpdate";
	}
	/**
	*配套资金初始账表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accmatchinit/group/updateGroupAccMatchInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccMatchInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String accMatchInitJson = groupAccMatchInitService.updateGroupAccMatchInit(mapVo);
		
		return JSONObject.parseObject(accMatchInitJson);
	}
	
	
	/*@RequestMapping(value = "/hrp/acc/accmatchinit/group/queryGroupAccProjAttrByProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccProjAttrByProj(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse res) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		AccProjAttr accMatchInitJson = groupAccProjAttrService.queryGroupAccProjAttrByProj(mapVo);
		
		String json = JSONObject.toJSONString(accMatchInitJson);		
		res.getWriter().print(json);
		return null;
	}*/
}
