/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.require.collectquery;
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
import com.chd.hrp.mat.service.requrie.collectquery.MatRequireCollectQueryService;
/**
 * 
 * @Description:
 * 汇总查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class MatRequireCollectQueryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatRequireCollectQueryController.class);
	
	//引入Service服务
	@Resource(name = "matRequireCollectQueryService")
	private final MatRequireCollectQueryService matRequireCollectQueryService = null;
   
	/*@Resource(name = "matRequireDetailService")
	private final MatRequireDetailService matRequireDetailService = null;*/
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	
	/**
	 * 汇总需求计划查询--主页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collectquery/matDeptRequriedCollectQMainPage", method = RequestMethod.GET)
	public String matDeptRequriedCollectQMainPage(Model mode) throws Exception {
		
		return "hrp/mat/require/collectquery/matDeptCollectQMainPage";

	}
	/**
	 * 汇总明细 计划单--查看
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collectquery/queryMatDeptRequriedCollectQ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptRequriedCollectQ(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String matRequireMainJson = matRequireCollectQueryService.queryCollectQ(getPage(mapVo));

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * 汇总查询  点击汇总单号查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collectquery/matDeptCollectStoreDetail", method = RequestMethod.GET)
	public String matDeptCollectStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("req_id", mapVo.get("req_id"));
		mode.addAttribute("req_code", mapVo.get("req_code"));
		
		mode.addAttribute("maker",mapVo.get("maker"));

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collectquery/matDeptCollectQStoreDetail";
	}
	
	@RequestMapping(value = "/hrp/mat/require/collectquery/queryMatDeptCollectStoreDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptCollectStoreDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matRequireMainJson = matRequireCollectQueryService.queryCollectStore(mapVo);

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * 汇总查询  点击汇总明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collectquery/matDeptCollectDeptDetail", method = RequestMethod.GET)
	public String matDeptCollectDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("req_id", mapVo.get("req_id"));
		mode.addAttribute("req_code", mapVo.get("req_code"));
		mode.addAttribute("maker",mapVo.get("maker"));

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collectquery/matDeptCollectQDeptDetail";
	}
	
	/**
	 * 汇总查询  点击汇总明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collectquery/queryMatDeptCollectDeptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDeptCollectDeptDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		String matRequireMainJson = matRequireCollectQueryService.queryCollectDept(mapVo);

		return JSONObject.parseObject(matRequireMainJson);
		
	}
	
	/**
	 * 汇总查询  点击汇总明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/require/collectquery/matDeptCollectPurDetail", method = RequestMethod.GET)
	public String matDeptCollectPurDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("pur_id", mapVo.get("pur_id"));
		mode.addAttribute("pur_code", mapVo.get("pur_code"));
		//根据pur_id查询计划明细数据，调用方法  

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/require/collectquery/matDeptCollectQPurDetail";
	}
	
	
	
}

