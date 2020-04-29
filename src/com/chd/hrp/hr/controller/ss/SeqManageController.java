package com.chd.hrp.hr.controller.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.hr.service.ss.SeqManageService;



/**
 * 职称等级
 * 
 * 
 * @param <seqManageService>
 *
 */
@Controller
public class SeqManageController extends BaseController{



	// 引入Service服务
	@Resource(name = "seqManageService")
	private final SeqManageService seqManageService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/seqManageMainPage", method = RequestMethod.GET)
	public String seqManageMainPage(Model mode) throws Exception {

		return "hrp/hr/ss/hrSeqManage/seqManageMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/seqManageAdd", method = RequestMethod.GET)
	public String seqManageAdd(Model mode) throws Exception {

		return "hrp/hr/ss/hrSeqManage/seqManageAdd";

	}
	/**
	 * @Description 生成页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/seqManageUpdate", method = RequestMethod.GET)
	public String seqManageUpdate(Model mode) throws Exception {

		return "hrp/hr/ss/hrSeqManage/seqManageUpdate";

	}
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/addSeqManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSeqManage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			/*if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}*/

			String seq = seqManageService.addSeqManage(mapVo);

			return JSONObject.parseObject(seq);
		} catch (Exception e) {			
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/querySeqManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySeqManage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		/*if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}*/
		String hosEmpKind = seqManageService.querySeqManage(mapVo);

		return JSONObject.parseObject(hosEmpKind);

	}
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/deleteSeqManage", method = RequestMethod.POST)
	@ResponseBody

	public String deleteSeqManage(@RequestParam String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		for(String id: paramVo.split(",")){
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//session中获取不变的
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			//传过来的表中的值
			mapVo.put("seq_code",id);
			listVo.add(mapVo);
		}
			return seqManageService.deleteSeqManage(listVo);		 
	}
	/**
	 * @Description 生成序列
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/createSeqManage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createSeqManage(@RequestParam(value="param") String param, Model mode) throws Exception {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();						
				//传过来的表中的值
				mapVo.put("seq_code", param.split("@")[0]);  // id.split("@")[0]   //貌似不需要for 来变换id，在分割
				mapVo.put("seq_name", param.split("@")[1]);
				mapVo.put("min_value", param.split("@")[2]);
				mapVo.put("max_value", param.split("@")[3]);
				mapVo.put("buffer_value", param.split("@")[4]);
				mapVo.put("start_value", param.split("@")[5]);
				mapVo.put("increment_value", param.split("@")[6]);

			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}

			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			
			String seq = seqManageService.createSeqManage(mapVo);

			return JSONObject.parseObject(seq);
		} catch (Exception e) {			
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/**
	 * @Description 删除序列
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/hr/ss/hrSeqManage/dropSeqManage", method = RequestMethod.POST)
	@ResponseBody

	public String dropSeqManage(@RequestParam String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
		for(String id: paramVo.split(",")){
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//session中获取不变的
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());

			//传过来的表中的值
			mapVo.put("seq_code",id);
			listVo.add(mapVo);
		}
			return seqManageService.dropSeqManage(listVo);		 
	}
}
