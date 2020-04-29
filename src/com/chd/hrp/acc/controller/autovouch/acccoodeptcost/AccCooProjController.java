package com.chd.hrp.acc.controller.autovouch.acccoodeptcost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.service.AccFundaAnalysisService;
import com.chd.hrp.acc.service.AccTargetService;
import com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCooProjService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Unit;
import com.intersys.cache.util.Console;

@Controller
public class AccCooProjController extends BaseController {

	
	private static Logger logger = Logger.getLogger(AccCooProjController.class);

	@Resource(name = "accCooProjService")
	private final AccCooProjService accCooProjService = null;
	

	/**
	 * 合作项目<BR>
	 * 页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/accCooProjMainPage", method = RequestMethod.GET)
	public String accCooProjMainPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/acccoodepcost/acccooproj/accCooProjMain";

	}
	
	/**
	 * 添加<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/addAccCooProjPage", method = RequestMethod.GET)
	public String addAccCooProjPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/acccoodepcost/acccooproj/accCooProjAdd";

	}
	
	/**
	 * 修改<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/updateAccCooProjPage", method = RequestMethod.GET)
	public String updateAccCooProjPage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		Map<String, Object> fundaMap = accCooProjService.queryAccProjByCode(mapVo);

		mode.addAttribute("group_id", fundaMap.get("group_id"));
		mode.addAttribute("hos_id", fundaMap.get("hos_id"));
		mode.addAttribute("copy_code", fundaMap.get("copy_code"));
		mode.addAttribute("proj_code", fundaMap.get("proj_code"));
		mode.addAttribute("proj_name", fundaMap.get("proj_name"));
		mode.addAttribute("coop_type", fundaMap.get("coop_type"));
		mode.addAttribute("is_stop", fundaMap.get("is_stop"));
		mode.addAttribute("note", fundaMap.get("note"));

		return "hrp/acc/autovouch/acccoodepcost/acccooproj/accCooProjUpdate";

	}
	
	/**
	 * 导入页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/accProjImportPage", method = RequestMethod.GET)
	public String accProjImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());

		return "hrp/acc/autovouch/acccoodepcost/acccooproj/accCooProjImport";
	}
	
	/**
	 * 添加保存
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/saveAccCooProj", method = RequestMethod.POST)
	public Map<String, Object> saveAccCooProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCooProjService.saveAccCooProj(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	
	/**
	 * 修改保存
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/updateAccCooProj", method = RequestMethod.POST)
	public Map<String, Object> updateAccCooProj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCooProjService.updateAccCooProj(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	
	/**
	 * 查询添加修改页面的科室列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/queryAccCooProjDept", method = RequestMethod.POST)
	public Map<String, Object> queryAccCooProjDept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCooProjService.queryAccCooProjDetail(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}

	/**
	 * 查询添加修改页面的外来单位列表
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/queryAccCooProjObj", method = RequestMethod.POST)
	public Map<String, Object> queryAccCooProjObj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			String reJson = accCooProjService.queryAccCooProjObj(mapVo);
			return JSONObject.parseObject(reJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}");
		}
	}
	
	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/queryAccCooProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCooProj(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accProj = accCooProjService.queryAccCooProj(getPage(mapVo));

		return JSONObject.parseObject(accProj);

	}
	
	
	/**
	*<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/deleteAccCooProj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCooProj(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());
            
            mapVo.put("proj_code", ids[3]);
            
            listVo.add(mapVo);
        }
		
		
		String accProjJson = accCooProjService.deleteAccCooProj(listVo);
	   
		return JSONObject.parseObject(accProjJson);
			
	}
	
	/**
	 * 导入
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/acc/autovouch/acccoodeptcost/acccooproj/importAccCooProj", method = RequestMethod.POST)
	public String importAccCooProj(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return accCooProjService.importAccCooProj(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
	}
	

}