/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.controller.teachingmanagement;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.chd.hrp.hr.entity.teachingmanagement.HrWithTeach;
import com.chd.hrp.hr.service.teachingmanagement.HrWithTeachService;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_WITH_TEACH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value="/hrp/hr/teachingmanagement")
public class HrWithTeachController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HrWithTeachController.class);
	
	//引入Service服务
	@Resource(name = "hrWithTeachService")
	private final HrWithTeachService hrWithTeachService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrWithTeachMainPage", method = RequestMethod.GET)
	public String hrWithTeachMainPage(Model mode) throws Exception {

		return "hrp/hr/teachingmanagement/hrwithteach/hrWithTeachMain";

	}

	
	/**
	 * @Description 
	 * 保存数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/addOrUpdateHrWithTeach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateHrWithTeach(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String hrWithTeachJson = "";
		try {

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			hrWithTeachJson = hrWithTeachService.addOrUpdate(mapVo);

			return JSONObject.parseObject(hrWithTeachJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * @Description 
	 * 删除数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteHrWithTeach", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrWithTeach(@RequestParam String paramVo, Model mode) throws Exception {
		
		try {
			/*List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();*/
			List<HrWithTeach> listVo = JSONArray.parseArray(paramVo, HrWithTeach.class);
			for (HrWithTeach hrWithTeach : listVo) {
				hrWithTeach.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrWithTeach.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			
			
			String hrWithTeachJson = hrWithTeachService.deleteHrWithTeach(listVo);

			return hrWithTeachJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
			
	}
	
	/**
	 * @Description 
	 * 查询数据 
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryHrWithTeach", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrWithTeach(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		if(mapVo.get("year") == null){
			
		mapVo.put("year", SessionManager.getAcctYear());
        
		}
		String hrWithTeach = hrWithTeachService.query(getPage(mapVo));

		return JSONObject.parseObject(hrWithTeach);
		
	}
	
	/**
	 * 查询轮转科室信息  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDeptComboBoxHrWithTeach", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptComboBoxHrWithTeach(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		
		return hrWithTeachService.queryDeptComboBox(mapVo);
		
	}
	/**
	 * 查询  双击工号出现下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryComboBoxHrWithTeach", method = RequestMethod.POST)
	@ResponseBody
	public String queryComboBoxHrWithTeach(@RequestParam Map<String, Object> mapVo)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (mapVo.get("hos_id") == null) {
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		return hrWithTeachService.queryComboBox(mapVo);
		
	}
	/*
	 * 导入数据
	 */
	@RequestMapping(value = "/importWithTeach", method = RequestMethod.POST)
	@ResponseBody
	public String importWithTeach(@RequestParam Map<String, Object> mapVo,
			Model mode, HttpServletRequest request) throws Exception {
		String reJson = hrWithTeachService.importWithTeach(mapVo);
		return reJson;
	}

}

