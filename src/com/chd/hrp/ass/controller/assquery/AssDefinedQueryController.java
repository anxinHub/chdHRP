package com.chd.hrp.ass.controller.assquery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.jdbc.SQL;
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
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.service.assquery.AssDefinedQueryService;

/**
 * 综合查询-自定义查询
 * 
 * @author fhqfm
 *
 */
@Controller
public class AssDefinedQueryController extends BaseController {
	private static Logger logger = Logger.getLogger(AssDefinedQueryController.class);

	@Resource(name = "assDefinedQueryService")
	private final AssDefinedQueryService assDefinedQueryService = null;
	
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/assDefinedQueryMainPage", method = RequestMethod.GET)
	public String assDefinedQueryMainPage(Model mode) throws Exception {

		return "hrp/ass/assquery/defined/assDefinedQueryMain";

	}
	
	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/assDefinedQueryAddPage", method = RequestMethod.GET)
	public String assDefinedQueryAddPage(Model mode) throws Exception {

		return "hrp/ass/assquery/defined/assDefinedQueryAdd";

	}
	
	/**
	 * @Description 
	 * 更新页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/assDefinedQueryUpdatePage", method = RequestMethod.GET)
	public String assDefinedQueryUpdatePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mode.addAllAttributes(assDefinedQueryService.queryByCode(mapVo));
		return "hrp/ass/assquery/defined/assDefinedQueryUpdate";

	}
	
	/**
	 * @Description 
	 * SQL执行页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/assDefinedQueryRunMainPage", method = RequestMethod.GET)
	public String assDefinedQueryRunMainPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		//mode.addAllAttributes(mapVo);
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String,Object>> items = assDefinedQueryService.queryAssReportItem(mapVo);
		
		mode.addAttribute("rhead_id", mapVo.get("rhead_id"));
		mode.addAttribute("items", ChdJson.toJson(items));
		return "hrp/ass/assquery/defined/assDefinedQueryRunMain";

	}
	
	@RequestMapping(value = "/hrp/ass/assquery/getFields", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getFields(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String,Object> reJson = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			List<String> fields = assDefinedQueryService.queryFields(mapVo);
			if(fields.size() > 0){
				for (String string : fields) {
					Map<String,String> field = new HashMap<String, String>();
					field.put("field_name", string);
					field.put("field_text", string);
					field.put("field_type", "text");//字段类型
					field.put("is_show", "2");//是否显示
					field.put("s_mode", "1");//查询方式
					field.put("s_flag", "2");//是否查询
					list.add(field);
				}
				
			}
		} catch (Exception e) {
			reJson.put("warn", e.getMessage());
			return reJson;
		}
		reJson.put("fields", list);

		return reJson;
		
	}
	
	/**
	 * @Description 
	 * 查询数据 综合查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/queryAssDefinedQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDefinedQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("mod_code", SessionManager.getModCode());
		
		String assDefinedQuery = null;
		try {
			assDefinedQuery = assDefinedQueryService.queryAssDefinedQuery(getPage(mapVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return JSONObject.parseObject(assDefinedQuery);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/queryAssReportItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssReportItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("mod_code", SessionManager.getModCode());
		
		List<Map<String, Object>> AssReportItem = null;
		try {
			AssReportItem = assDefinedQueryService.queryAssReportItem(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return ChdJson.toJson(AssReportItem);
		
	}
	
	/**
	 * @Description 
	 * 删除数据 综合查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/deleteAssDefinedQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDefinedQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String,Object> reJson = new HashMap<String,Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assDefinedQuery = null;
		try {
			assDefinedQuery = assDefinedQueryService.deleteAssDefinedQuery(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			reJson.put("warn", e.getMessage());
			return reJson;
		}

		return JSONObject.parseObject(assDefinedQuery);
		
	}
	
	/**
	 * @Description 添加数据 自定义报表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assquery/addAssDefinedQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDefinedQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("mod_code", SessionManager.getModCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String reJson = assDefinedQueryService.addAssDefinedQuery(mapVo);

		return JSONObject.parseObject(reJson);

	}
	
	/**
	 * @Description 更新数据 自定义报表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assquery/updateAssDefinedQuery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssDefinedQuery(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("mod_code", SessionManager.getModCode());
		
		mapVo.put("create_emp", SessionManager.getUserId());
		
		String reJson = assDefinedQueryService.updateAssDefinedQuery(mapVo);

		return JSONObject.parseObject(reJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 执行自定义查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assquery/queryAssDefinedQueryRun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDefinedQueryRun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assDefinedQuery = null;
		try {
			assDefinedQuery = assDefinedQueryService.queryAssDefinedQueryRun(getPage(mapVo));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return JSONObject.parseObject(assDefinedQuery);
		
	}
	
}
