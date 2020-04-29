package com.chd.hrp.acc.controller.autovouch.accpubCost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.hrp.acc.service.autovouch.accpubCost.AccPersonCollectService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Unit;
import com.intersys.cache.util.Console;

@Controller
public class AccPersonCollectController extends BaseController {

	
	private static Logger logger = Logger.getLogger(AccPersonCollectController.class);

	@Resource(name = "accPersonCollectService")
	private final AccPersonCollectService accPersonCollectService = null;
	

	/**
	 * 科室人员采集<BR>
	 * 主页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/accPersonCollectMainPage", method = RequestMethod.GET)
	public String accPersonCollectMainPage(Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));

		return "hrp/acc/autovouch/accpubCost/accsharedata/accpersoncollect/accPersonCollectMain";

	}
	
	/**
	 * 科室人员采集<BR>
	 * 添加页面
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/addAccPersonCollectPage", method = RequestMethod.GET)
	public String addAccPersonCollectPage(Model mode) throws Exception {

		return "hrp/acc/autovouch/accpubCost/accsharedata/accpersoncollect/accPersonCollectAdd";

	}
	
	/**
	 * 更新页面<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/updateAccPersonCollectPage", method = RequestMethod.GET)
	public String updateAccPersonCollectPage(@RequestParam Map<String, Object> mapVo,
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

		Map<String, Object> personMap = accPersonCollectService.queryAccPersonById(mapVo);

		mode.addAttribute("group_id", personMap.get("group_id"));
		mode.addAttribute("hos_id", personMap.get("hos_id"));
		mode.addAttribute("copy_code", personMap.get("copy_code"));
		mode.addAttribute("year_month", personMap.get("year_month"));
		mode.addAttribute("dept_id", personMap.get("dept_id"));
		mode.addAttribute("dept_code_name", personMap.get("dept_code_name"));
		mode.addAttribute("emp_count", personMap.get("emp_count"));
		
		return "hrp/acc/autovouch/accpubCost/accsharedata/accpersoncollect/accPersonCollectUpdate";

	}
	
	/**
	 * 导入页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/accPersonCollectImportPage", method = RequestMethod.GET)
	public String accPersonCollectImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("year_month", mapVo.get("year_month"));
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());

		return "hrp/acc/autovouch/accpubCost/accsharedata/accpersoncollect/accPersonCollectImport";
	}
	
	/**
	 * 导入人员的详细信息
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/importAccPersonCollect", method = RequestMethod.POST)
	public String importAccPersonCollect(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			return accPersonCollectService.importAccPersonCollect(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + StringTool.string2Json(e.getMessage()) + "\"}";
		}
	}

	/**
	 * <BR>
	 * 查询总人数
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/queryTotalNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryTotalNum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String totalJson = accPersonCollectService.queryTotalNum(mapVo);

		return JSONObject.parseObject(totalJson);
	}
	
	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/queryAccPersonCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPersonCollect(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accPerson = accPersonCollectService.queryAccPersonCollect(getPage(mapVo));

		return JSONObject.parseObject(accPerson);

	}
	
	/**
	 * <BR>
	 * 添加保存
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/addAccPersonCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccPersonCollect(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			String addAccPersonCollectJson = accPersonCollectService.addAccPersonCollect(mapVo);

			return JSONObject.parseObject(addAccPersonCollectJson);
			
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}
	
	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/updateAccPersonCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPersonCollect(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		try{
			
			String AccPersonJson = accPersonCollectService.updateAccPersonCollect(mapVo);

			return JSONObject.parseObject(AccPersonJson);
			
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

		
	}
	
	/**
	 * <BR>
	 * 审核
	 */

	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/updateAccPersonState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPersonState(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String AccPersonJson = accPersonCollectService.updateAccPersonState(mapVo);

		return JSONObject.parseObject(AccPersonJson);

	}
	
	/**
	 * <BR>
	 * 继承上月
	 */

	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/updateAccPersonDataFromLastMonth", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPersonDataFromLastMonth(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String AccPersonJson = accPersonCollectService.updateAccPersonDataFromLastMonth(mapVo);

		return JSONObject.parseObject(AccPersonJson);

	}
	
	/**
	 * <BR>
	 * 采集系统数据
	 */
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/insertAccSysPerson", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> insertAccSysPerson(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			String insertAccSysPersonJson = accPersonCollectService.insertAccSysPerson(mapVo);

			return JSONObject.parseObject(insertAccSysPersonJson);
			
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}

	}
	
	/**
	*<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/autovouch/accpubCost/accperson/deletePersonCollect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePersonCollect(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("year_month", ids[3]);
            
            mapVo.put("dept_id", ids[4]);
           
//            Map<String, Object> targetDataMap = accTargetService.queryAccTargetDataByCode(mapVo);
//            
//            if(targetDataMap!=null){
//            	
//            	flag = false;
//				break;
//            }
            
            listVo.add(mapVo);
        }
		
//		if(flag == false){
//			
//			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被引用! \"}");
//			
//		}
		
		try{
			
			String accPersonJson = accPersonCollectService.deletePersonCollect(listVo);
			   
			return JSONObject.parseObject(accPersonJson);
			
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\"}");
		}
	
	}
}