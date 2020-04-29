package com.chd.hrp.hr.controller.organize;

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
import com.chd.hrp.hr.service.organize.HrDeptViewService;

/**
 * 
 * @author Administrator 部门架构
 */
@Controller
@RequestMapping(value = "/hrp/hr/dept")
public class HrDeptViewController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrDeptViewController.class);

	// 引入Service服务

	@Resource(name = "hrDeptViewService")
	private final HrDeptViewService hrDeptViewService= null;

	 //组织架构右键查看
		@RequestMapping(value = "/deptViewPage", method = RequestMethod.GET)
		public String DeptViewViewPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			
			mode.addAttribute("DeptView_code", mapVo.get("DeptView_code"));
			return "hrp/hr/organize/deptview/deptView";

		}
		//
		@RequestMapping(value = "/deptOrgSetPage", method = RequestMethod.GET)
		public String deptOrgSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			return "hrp/hr/organize/deptview/deptOrgSet";

		}
		//初始化树形
		@RequestMapping(value = "/queryDeptViewOrg", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		@ResponseBody
		public String queryDeptViewOrg(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			
			String reJson=null;
			try{
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				
				reJson=hrDeptViewService.queryDeptViewOrg(mapVo);
			}catch(Exception e){
				reJson="{\"error\":\""+e.getMessage()+"\"}";
			}
			
			return reJson;
			
		}
		//查询部门架构设置
		@RequestMapping(value = "/queryDeptViewOrgSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryDeptViewOrgSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String DeptView = hrDeptViewService.queryDeptViewOrgSet(getPage(mapVo));

			return JSONObject.parseObject(DeptView);

		}
		//
		/**
		 * 添加保存
		 * 
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/addDeptOrgSet", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addDeptOrgSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			try {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}

				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				
				String deptJson = hrDeptViewService.addDeptOrgSet(mapVo);

				return JSONObject.parseObject(deptJson);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
			}

		}
		//查询右键菜单
		@RequestMapping(value = "/queryDeptViewOrgMenu", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryDeptViewOrgMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String DeptView = hrDeptViewService.queryDeptViewOrgMenu(getPage(mapVo));

			return JSONObject.parseObject(DeptView);

		}
		//查询科室主任照片
		@RequestMapping(value = "/queryDeptViewOrgImg", method = RequestMethod.POST)
		@ResponseBody
		public String queryDeptViewOrgImg(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String DeptView = hrDeptViewService.queryDeptViewOrgImg(getPage(mapVo));

			return DeptView;

		}
}
