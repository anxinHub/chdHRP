/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.require.dept;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.requrie.dept.MatRequireConfirmService;
/**
 * 
 * @Description:
 * 科室购置计划审核
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatRequireConfirmController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatRequireConfirmController.class);
	
	//引入Service服务
	@Resource(name = "matRequireConfirmService")
	private final MatRequireConfirmService matRequireConfirmService = null;
   
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	
	/**
	 * 科室购置计划--主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/matDeptRequriedConfirmMainPage", method = RequestMethod.GET)
	public String matDeptRequriedConfirmMainPage(Model mode) throws Exception {

		mode.addAttribute("p04031", MyConfig.getSysPara("04031"));
		
		return "hrp/mat/require/dept/confirm/matDeptRequriedConfirmMainPage";

	}
	
	/**
	 * 科室购置计划--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/queryMatDeptRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequriedConfirm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());	
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){			
			mapVo.put("acc_year", SessionManager.getAcctYear());        
		}
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());        
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		String matRequireMain = matRequireConfirmService.queryConfirm(getPage(mapVo));
		return JSONObject.parseObject(matRequireMain);	
	}
	
	/**
	 * 科室购置计划--审核页面查看主表申请单据
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/matDeptRequriedConfirmDetail", method = RequestMethod.GET)
	public String matDeptRequriedConfirmDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());	
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acc_year") == null){			
			mapVo.put("acc_year", SessionManager.getAcctYear());        
		}
		
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());        
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> matDeptMain = matRequireConfirmService.queryByCode(mapVo);
		
		mode.addAttribute("req_id", matDeptMain.get("req_id"));
		mode.addAttribute("req_code", matDeptMain.get("req_code"));
		
		mode.addAttribute("dept_id", matDeptMain.get("dept_id"));
		mode.addAttribute("dept_no", matDeptMain.get("dept_no"));
		mode.addAttribute("dept_code", matDeptMain.get("dept_code"));
		mode.addAttribute("dept_name", matDeptMain.get("dept_name"));
		
		mode.addAttribute("stock_id", matDeptMain.get("stock_id"));
		mode.addAttribute("stock_no", matDeptMain.get("stock_no"));
		mode.addAttribute("store_code", matDeptMain.get("store_code"));
		mode.addAttribute("store_name", matDeptMain.get("store_name") );
		
		mode.addAttribute("make_date", matDeptMain.get("make_date"));
		mode.addAttribute("rdate", matDeptMain.get("rdate"));
		mode.addAttribute("brif", matDeptMain.get("brif"));
		mode.addAttribute("other_inv", matDeptMain.get("other_inv"));
		mode.addAttribute("state", matDeptMain.get("state"));
		
		mode.addAttribute("thisDateB", mapVo.get("thisDateB"));
		mode.addAttribute("thisDateE", mapVo.get("thisDateE"));
		mode.addAttribute("lastDateB", mapVo.get("lastDateB"));
		mode.addAttribute("lastDateE", mapVo.get("lastDateE"));

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/dept/confirm/matDeptRequriedConfirmDetail";

	}
	
	/**
	 * 科室购置计划--审核页面查看明细数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/queryMatDeptConfirmDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptConfirmDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());	
		}
		
		if(mapVo.get("hos_id") == null){	
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		
		if(mapVo.get("copy_code") == null){			
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){			
			mapVo.put("acc_year", SessionManager.getAcctYear());        
		}
		if(mapVo.get("user_id") == null){			
			mapVo.put("user_id", SessionManager.getUserId());        
		}
	
		String matRequireMain = matRequireConfirmService.queryConfirmDetail(getPage(mapVo));
		return JSONObject.parseObject(matRequireMain);
		
	}
	
	/**
	 * 科室购置计划--退回科室
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/matDeptRequriedConfirmReturnPage", method = RequestMethod.GET)
	public String matDepartRequriedConfirmReturnPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("reqIds", mapVo.get("reqIds"));
		return "hrp/mat/require/dept/confirm/matDeptRequriedConfirmReturnPage";

	}
	
	/**
	 * 科室购置计划--退回科室保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/returnMatDeptRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> returnMatDeptRequriedConfirm(@RequestParam Map<String, Object> mapVo) throws Exception {
		String return_reason= mapVo.get("return_reason").toString();
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String reqIds = mapVo.get("reqIds").toString();
		String[] reqId = reqIds.split(",");
        for (int i = 0; i < reqId.length; i++) {
        	Map<String, Object> mapVo1 = new HashMap<String, Object>();
        	mapVo1.put("hos_id", SessionManager.getHosId());
        	mapVo1.put("group_id", SessionManager.getGroupId());
        	mapVo1.put("copy_code", SessionManager.getCopyCode());
        	mapVo1.put("acc_year", SessionManager.getAcctYear());
        	mapVo1.put("req_id", reqId[i]);
        	mapVo1.put("return_reason", return_reason);
        	listVo.add(mapVo1);
        }
		
		String matRequireMain = matRequireConfirmService.updateReturn(listVo);
		return JSONObject.parseObject(matRequireMain);
		
	}
	
	/**
	 * 科室购置计划--审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/submitMatDeptRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> submitMatDeptRequriedConfirm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);
            mapVo.put("req_code", id.split("@")[1]);
            mapVo.put("copy_code", SessionManager.getCopyCode());
            mapVo.put("hos_id", SessionManager.getHosId());
            mapVo.put("group_id", SessionManager.getGroupId());
            mapVo.put("acc_year", SessionManager.getAcctYear());
            mapVo.put("user_id", SessionManager.getUserId());
            
            mapVo.put("check_date", new Date());
            
            listVo.add(mapVo);
        }
		String matRequireMainJson = matRequireConfirmService.updateAudit(listVo);
		return JSONObject.parseObject(matRequireMainJson);
	}
	
	/**
	 * 科室购置计划--取消审核
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/dept/confirm/unSubmitMatDeptRequriedConfirm", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> unSubmitMatDeptRequriedConfirm(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {	
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("req_id", id.split("@")[0]);//实际实体类变量
            mapVo.put("req_code", id.split("@")[1]);//实际实体类变量
            mapVo.put("copy_code", SessionManager.getCopyCode());//实际实体类变量
            mapVo.put("hos_id", SessionManager.getHosId());//实际实体类变量
            mapVo.put("group_id", SessionManager.getGroupId());//实际实体类变量
            mapVo.put("acc_year", SessionManager.getAcctYear());//实际实体类变量
            listVo.add(mapVo);
        }
		
		String matRequireMainJson = matRequireConfirmService.updateAuditCancle(listVo);	
		return JSONObject.parseObject(matRequireMainJson);
	}	
	
}

