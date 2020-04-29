/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.wage;
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
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.acc.serviceImpl.wage.AccWageEmpKindServiceImpl;

/**
* @Title. @Description.
* 工资套职工分类关系
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

 
@Controller
public class AccWageEmpKindController extends BaseController{
	private static Logger logger = Logger.getLogger(AccWageEmpKindController.class);
	
	
	@Resource(name = "accWageEmpKindService")
	private final AccWageEmpKindServiceImpl accWageEmpKindService = null;
    
	/**
	 * 工资套职工分类关系<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accwageempkind/accWageEmpKindMainPage", method = RequestMethod.GET)
	public String accWageEmpKindMainPage(Model mode) throws Exception {
		return "hrp/acc/accwageempkind/accWageEmpKindMain";
	}
	
	/**
	*工资套职工分类关系<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accwageempkind/accWageEmpKindAddPage", method = RequestMethod.GET)
	public String accWageEmpKindAddPage(Model mode) throws Exception {

		return "hrp/acc/accwageempkind/accWageEmpKindAdd";

	} 
	
	/**
	 * 工资套职工分类关系<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accwageempkind/addAccWageEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccWageEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String AccWageEmpJson = accWageEmpKindService.addAccWageEmpKind(mapVo);
		return JSONObject.parseObject(AccWageEmpJson);
	}
	
	/**
	 * 【工资管理-基础信息-工资套】：保存工资套与人员类别
	 */
	@RequestMapping(value = "/hrp/acc/accwageempkind/saveAccWageEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public String saveAccWageEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = accWageEmpKindService.saveAccWageEmpKind(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	/**
	 * 【工资管理-基础信息-工资套】：工资套操作查看，查询工资套与职工类别关系
	 */
	@RequestMapping(value = "/hrp/acc/accwageempkind/queryAccWageEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccWageEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AccWageEmpKind = accWageEmpKindService.queryAccWageEmpKind(getPage(mapVo));
		return JSONObject.parseObject(AccWageEmpKind);
	}
	
	/**
	*工资套职工分类关系<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accwageempkind/deleteAccWageEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public String deleteAccWageEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			String msg = accWageEmpKindService.deleteBatchAccWageEmpKind(mapVo);
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
			
	}
	
	 
	/**
	*工资套职工分类关系<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accwageempkind/accWageEmpKindUpdatePage", method = RequestMethod.GET)
	
	public String accWageEmpKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccWageEmp accWageEmp = new AccWageEmp();
        
		accWageEmp = accWageEmpKindService.queryAccWageEmpKindByCode(mapVo);
		
		mode.addAttribute("wage_code", accWageEmp.getWage_code());
		
		mode.addAttribute("wage_name", accWageEmp.getWage_name());
		
		mode.addAttribute("group_id", accWageEmp.getGroup_id());
		
		mode.addAttribute("hos_id", accWageEmp.getHos_id());
		
		mode.addAttribute("copy_code", accWageEmp.getCopy_code());
		
		mode.addAttribute("kind_code", accWageEmp.getKind_code()); 
		
		mode.addAttribute("kind_name", accWageEmp.getKind_name()); 
	
		return "hrp/acc/accwageempkind/accWageEmpKindUpdate";
	}
	/**
	*工资套职工分类关系<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accwageempkind/updateAccWageEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccWageEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        
        mapVo.put("hos_id", SessionManager.getHosId());
        
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("old_wage_code", mapVo.get("old_wage_code"));
        
        mapVo.put("old_kind_code", mapVo.get("old_kind_code"));
		
		String AccWageEmpJson = accWageEmpKindService.updateAccWageEmpKind(mapVo);
		
		return JSONObject.parseObject(AccWageEmpJson);
	}
	 
	
}

