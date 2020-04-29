/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.require.collectquery;
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
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.requrie.collectquery.MedRequireCollectQueryService;
/**
 * 
 * @Description:
 * 汇总查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MedRequireCollectQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedRequireCollectQueryController.class);
	
	//引入Service服务
	@Resource(name = "medRequireCollectQueryService")
	private final MedRequireCollectQueryService medRequireCollectQueryService = null;
   
	/*@Resource(name = "medRequireDetailService")
	private final MedRequireDetailService medRequireDetailService = null;*/
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	
	/**
	 * 汇总需求计划查询--主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collectquery/medDeptRequriedCollectQMainPage", method = RequestMethod.GET)
	public String medDeptRequriedCollectQMainPage(Model mode) throws Exception {
		
		return "hrp/med/require/collectquery/medDeptCollectQMainPage";

	}
	/**
	 * 汇总明细 计划单--查看
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collectquery/queryMedDeptRequriedCollectQ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptRequriedCollectQ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history",MyConfig.getSysPara("03001"));
		}
		
		String medRequireMainJson = medRequireCollectQueryService.queryCollectQ(getPage(mapVo));

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * 汇总查询  点击汇总单号查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collectquery/medDeptCollectStoreDetail", method = RequestMethod.GET)
	public String medDeptCollectStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("req_id", mapVo.get("req_id"));
		mode.addAttribute("req_code", mapVo.get("req_code"));
		
		mode.addAttribute("maker",mapVo.get("maker"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/collectquery/medDeptCollectQStoreDetail";
	}
	
	@RequestMapping(value = "/hrp/med/require/collectquery/queryMedDeptCollectStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptCollectStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String medRequireMainJson = medRequireCollectQueryService.queryCollectStore(mapVo);

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * 汇总查询  点击汇总明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collectquery/medDeptCollectDeptDetail", method = RequestMethod.GET)
	public String medDeptCollectDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("req_id", mapVo.get("req_id"));
		mode.addAttribute("req_code", mapVo.get("req_code"));
		mode.addAttribute("maker",mapVo.get("maker"));
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/collectquery/medDeptCollectQDeptDetail";
	}
	
	/**
	 * 汇总查询  点击汇总明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collectquery/queryMedDeptCollectDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedDeptCollectDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
	
		if(mapVo.get("user_id") == null){
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		String medRequireMainJson = medRequireCollectQueryService.queryCollectDept(mapVo);

		return JSONObject.parseObject(medRequireMainJson);
		
	}
	
	/**
	 * 汇总查询  点击汇总明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/require/collectquery/medDeptCollectPurDetail", method = RequestMethod.GET)
	public String medDeptCollectPurDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("pur_id", mapVo.get("pur_id"));
		mode.addAttribute("pur_code", mapVo.get("pur_code"));
		//根据pur_id查询计划明细数据，调用方法  
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/require/collectquery/medDeptCollectQPurDetail";
	}
	
	
	
}

