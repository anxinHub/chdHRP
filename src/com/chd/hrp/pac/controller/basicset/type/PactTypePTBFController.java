package com.chd.hrp.pac.controller.basicset.type;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;


import com.chd.hrp.pac.service.basicset.type.PactTypePTBFService;
/**
 * 按钮函数设置
 * 
 * @author lh0225
 *
 *
 */
@Controller
@RequestMapping(value="/hrp/pac/basicset/type/PTBF")
public class PactTypePTBFController extends BaseController{
	
	@Resource(name="pactTypePTBFService")
	private final PactTypePTBFService pactTypePTBFService=null;
	/**
	 * 页面跳转
	 * @author lh0225
	 * @return
	 */
	@RequestMapping(value="/PactTypePTBFMainPage",method=RequestMethod.GET)
	public String ToPactTypePTBFMainPage(){
		
		return "hrp/pac/basicset/type/PTBF/pacptbfAction";
	}
 /**
  * @author lh0225
  * 查询功能
  * @param mapVo
  * @param model
  */
	@RequestMapping(value="/queryPactPtbf",method=RequestMethod.POST)
	@ResponseBody
	
	public Map<String , Object >queryPactPtbf(@RequestParam Map<String, Object> mapVo,Model model){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String res=pactTypePTBFService.queryPactPtbf(getPage(mapVo));
	    //System.out.println(res);
		
		//方法
		
		return JSONObject.parseObject(res);
	}
	
	/**
	 * 根据ID查询
	 * @author lh0225
	 * @param mapVo
	 * @param model
	 * 
	 */
	
	
	@RequestMapping(value="/queryPactPtbfId",method=RequestMethod.POST)
	@ResponseBody
	
	public String queryPactPtbfId(@RequestParam Map<String, Object> mapVo,Model model){
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//方法
		//String res=null;
		String res=pactTypePTBFService.queryPactPtbfId(mapVo);
		
		return res;
	}
	
	/**
	 * 保存
	 * @author lh0225
	 * @param mapVo
	 * @param model
	 */
	
	
	@RequestMapping(value="/savePacPtbfAction",method=RequestMethod.POST)
	@ResponseBody
	
	public  String savePacPtbfAction(@RequestParam Map<String ,Object>mapVo,Model model){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//方法
		//System.out.println(mapVo);
		String res=pactTypePTBFService.savePacPtbfAction(mapVo);
		return res;
		
	}
	
	/**
	 * 更新方法
	 * @author lh0225
	 * 
	 * @param mapVo
	 * @param model
	 * 
	 */
	
	@RequestMapping(value="/updatePacPtbfAction",method=RequestMethod.POST)
	@ResponseBody
	
	public String updatePacPtbfAction(@RequestParam Map<String ,Object>mapVo , Model model){
		
		if (mapVo.get("group_id") == null) {
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
		mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String res=pactTypePTBFService.updatePacPtbfAction(mapVo);
		//方法
		return res;
		
	}
	
	
	/**
	 * 
	 * 删除方法
	 * @author lh0225
	 * @param mapVo
	 * @param model
	 * 
	 */
	@RequestMapping(value="/deletePacPtbfAction",method=RequestMethod.POST)
	@ResponseBody
	public String deletePacPtbfAction(@RequestParam Map<String ,Object>mapVo , Model model){
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String res=pactTypePTBFService.deletePacPtbfAction(mapVo);
			//方法
			return res;
		
	}
}
