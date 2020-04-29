/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.mat.controller.info.basic;
import java.util.Date;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.info.basic.MatSafeChangeService;

/**
* @Title. @Description.
* 安全库存调整
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

@Controller
@RequestMapping(value = "/hrp/mat/info/basic/safechange/")
public class MatSafeChangeController extends BaseController{
	private static Logger logger = Logger.getLogger(MatSafeChangeController.class);
	
	@Resource(name = "matSafeChangeService")
	private final MatSafeChangeService matSafeChangeService = null;
	
	/**
	* 主页面跳转
	*/
	@RequestMapping(value = "/matSafeChangeListPage", method = RequestMethod.GET)
	public String matSafeChangeListPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/safechange/matSafeChangeList";
	}
	
	/**
	* 维护页面跳转
	*/
	@RequestMapping(value = "/matSafeChangeAddPage", method = RequestMethod.GET)
	public String matSafeChangeAddPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/safechange/matSafeChangeAdd";
	}

	/**
	 * @Description 
	 * 修改页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matSafeChangeUpdatePage", method = RequestMethod.GET)
	public String matSafeChangeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//添加主表数据
		Map<String, Object> matSafeChange = matSafeChangeService.queryMatSafeChangeById(mapVo);
		//转换日期
		if(matSafeChange.get("make_date") != null && !"".equals(matSafeChange.get("make_date").toString())){
			matSafeChange.put("make_date", DateUtil.dateToString((Date) matSafeChange.get("make_date"), "yyyy-MM-dd"));
		}
		if(matSafeChange.get("check_date") != null && !"".equals(matSafeChange.get("check_date").toString())){
			matSafeChange.put("check_date", DateUtil.dateToString((Date) matSafeChange.get("check_date"), "yyyy-MM-dd"));
		}
		if(matSafeChange.get("confirm_date") != null && !"".equals(matSafeChange.get("confirm_date").toString())){
			matSafeChange.put("confirm_date", DateUtil.dateToString((Date) matSafeChange.get("confirm_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("matSafeChange", matSafeChange);
		
		return "hrp/mat/info/basic/safechange/matSafeChangeUpdate";
	}
	
	/**
	*查询
	*/
	@RequestMapping(value = "/queryMatSafeChangeList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSafeChangeList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String matSafeChange = matSafeChangeService.queryMatSafeChangeList(getPage(mapVo));

		return JSONObject.parseObject(matSafeChange);
	}
	
	/**
	* 明细精准查询
	*/
	@RequestMapping(value = "/queryMatSafeChangeDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatSafeChangeDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String matSafeChange = matSafeChangeService.queryMatSafeChangeDetailById(mapVo);

		return JSONObject.parseObject(matSafeChange);
	}
	
	/**
	*保存
	*/
	@RequestMapping(value = "/saveMatSafeChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatSafeChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = matSafeChangeService.saveMatSafeChange(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	*删除
	*/
	@RequestMapping(value = "/deleteMatSafeChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatSafeChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		Map<String, Object> retMap = null;
		
		try {
			
			retMap = matSafeChangeService.deleteMatSafeChange(mapVo);
		} catch (Exception e) {
			
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败");
		}
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/auditMatSafeChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatSafeChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			mapVo.put("state", 2);
			mapVo.put("exists_state", 1);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("check_date", new Date());
			retMap = matSafeChangeService.updateMatSafeChangeState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 消审
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/unAuditMatSafeChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatSafeChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			mapVo.put("state", 1);
			mapVo.put("exists_state", 2);
			mapVo.put("checker", null);
			mapVo.put("check_date", null);
			retMap = matSafeChangeService.updateMatSafeChangeState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 确认
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/confirmMatSafeChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> confirmMatSafeChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		try{
			retMap = matSafeChangeService.confirmMatSafeChange(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	* 材料列表查询
	*/
	@RequestMapping(value = "/queryMatInvBySafeChange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBySafeChange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String matInv = matSafeChangeService.queryMatInvBySafeChange(getPage(mapVo));

		return JSONObject.parseObject(matInv);
	}
}

