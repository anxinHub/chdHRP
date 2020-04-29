package com.chd.hrp.acc.controller.termend.yearend;

import java.util.HashMap;
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
import com.chd.hrp.acc.service.termend.yearend.AccBudgTargetService;

/**
 * @Title. 
 * @Description.
* 年度授权/直接支出预算下达数<BR>
 * @Copyright: Copyright (c) 2015年12月10日 下午1:48:45
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @Version: 1.0 
 */
@Controller
public class AccBudgTargetController extends BaseController {
	private static Logger logger = Logger.getLogger(AccBudgTargetController.class);
	
	@Resource(name = "accBudgTargetService")
	private final AccBudgTargetService accBudgTargetService = null;
	
	/**
	 * 主页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/accBudgTargetMainPage", method = RequestMethod.GET)
	public String accBudgTargetMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		return "hrp/acc/termend/yearend/budgtarget/accBudgTargetMain";
	}
	
	/**
	 * 添加页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/accBudgTargetAddPage", method = RequestMethod.GET)
	public String accBudgTargetAddPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
		return "hrp/acc/termend/yearend/budgtarget/accBudgTargetAdd";
	}
	
	/**
	 * 修改页面跳转
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/accBudgTargetUpdatePage", method = RequestMethod.GET)
	public String accBudgTargetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> accBudgTarget =  accBudgTargetService.queryAccBudgTargetByCode(mapVo);
		
		mode.addAttribute("accBudgTarget", accBudgTarget);
		
		return "hrp/acc/termend/yearend/budgtarget/accBudgTargetUpdate";
	}
	
	/**
	 * 主查询
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/queryAccBudgTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBudgTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accTemplateData = accBudgTargetService.queryAccBudgTarget(mapVo);

		return JSONObject.parseObject(accTemplateData);
	}
	
	/**
	 * 主键查询
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/queryAccBudgTargetByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBudgTargetByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return accBudgTargetService.queryAccBudgTargetByCode(mapVo);
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/saveAccBudgTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAccBudgTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		Map<String, Object> retMap = null;
		try {
			if (mapVo.get("target_id") == null || "".equals(mapVo.get("target_id"))) {
				
				retMap = accBudgTargetService.addAccBudgTarget(mapVo);
			}else{
				
				retMap = accBudgTargetService.updateAccBudgTarget(mapVo);
			}
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
	
		return retMap;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/deleteAccBudgTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBudgTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> retMap = null;
		try {
			
			retMap = accBudgTargetService.deleteAccBudgTarget(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * 审核/消审
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/auditAccBudgTarget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditAccBudgTarget(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> retMap = null;
		try {
			
			retMap = accBudgTargetService.auditAccBudgTarget(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
	
	/**
	 * 获取实际支出金额
	 */
	@RequestMapping(value = "hrp/acc/termend/yearend/budgtarget/updateAccBudgTargetForMoney", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBudgTargetForMoney(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Map<String, Object> retMap = null;
		try {
			
			retMap = accBudgTargetService.updateAccBudgTargetForMoney(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}

		return retMap;
	}
}
