package com.chd.hrp.acc.controller;

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
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Unit;
import com.intersys.cache.util.Console;

@Controller
public class AccFundaAnalysisController extends BaseController {

	
	private static Logger logger = Logger.getLogger(AccFundaAnalysisController.class);

	@Resource(name = "accFundaAnalysisService")
	private final AccFundaAnalysisService accFundaAnalysisService = null;
	

	/**
	 * 基本分析指标<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accfunda/accFundaAnalysisMainPage", method = RequestMethod.GET)
	public String accFundaAnalysisMainPage(Model mode) throws Exception {

		return "hrp/acc/accfundamentalanalysis/accFundaAnalysisMain";

	}


	/**
	 * 添加基本分析指标<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/accfunda/addAccFundaAnalysisPage", method = RequestMethod.GET)
	public String addAccFundaAnalysisPage(Model mode) throws Exception {

		return "hrp/acc/accfundamentalanalysis/accFundaAnalysisAdd";

	}
	
	//报表制作主页面-弹出设置单元格页面
	@RequestMapping(value = "/hrp/acc/accfunda/cellSetPage", method = RequestMethod.GET)
	public String cellSetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("mod_code", mapVo.get("mod_code"));

		return "hrp/acc/accfundamentalanalysis/cellSet";
	}
	
	/**
	 * 修改基本分析指标<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/accfunda/updateAccFundaAnalysisPage", method = RequestMethod.GET)
	public String updateAccFundaAnalysisPage(@RequestParam Map<String, Object> mapVo,
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

		Map<String, Object> fundaMap = accFundaAnalysisService.queryAccFundaByCode(mapVo);

		mode.addAttribute("group_id", fundaMap.get("group_id"));
		mode.addAttribute("hos_id", fundaMap.get("hos_id"));
		mode.addAttribute("copy_code", fundaMap.get("copy_code"));
		mode.addAttribute("bas_code", fundaMap.get("bas_code"));
		mode.addAttribute("bas_name", fundaMap.get("bas_name"));
		mode.addAttribute("bas_unit", fundaMap.get("bas_unit"));
		mode.addAttribute("bas_type_code", fundaMap.get("bas_type_code"));
		mode.addAttribute("bas_type_name", fundaMap.get("bas_type_name"));
		mode.addAttribute("wx_type_code", fundaMap.get("wx_type_code"));
		mode.addAttribute("wx_type_name", fundaMap.get("wx_type_name"));
		mode.addAttribute("fma_en", fundaMap.get("fma_en"));
		mode.addAttribute("fma_cn", fundaMap.get("fma_cn"));
		mode.addAttribute("std_val", fundaMap.get("std_val"));
		mode.addAttribute("is_stop", fundaMap.get("is_stop"));
		mode.addAttribute("spell_code", fundaMap.get("spell_code"));
		mode.addAttribute("note", fundaMap.get("note"));

		return "hrp/acc/accfundamentalanalysis/accFundaAnalysisUpdate";

	}

	/**
	 * <BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accfunda/addAccFundaAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccFundaAnalysis(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"bas_name").toString()));

		String addAccFundaAnalysisJson = accFundaAnalysisService.addAccFundaAnalysis(mapVo);

		return JSONObject.parseObject(addAccFundaAnalysisJson);

	}
	
	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accfunda/queryAccFundaAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccFundaAnalysis(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accFunda = accFundaAnalysisService.queryAccFundaAnalysis(getPage(mapVo));

		return JSONObject.parseObject(accFunda);

	}
	
	/**
	*<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accfunda/deleteBatchAccFunda", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccFunda(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		boolean flag = true;
		
		for ( String id: paramVo.split(",")) {
			
			String [] ids = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
            
            mapVo.put("group_id", ids[0]);
            
            mapVo.put("hos_id", ids[1]);
            
            mapVo.put("copy_code", ids[2]);
            
            mapVo.put("bas_code", ids[3]);
           
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
		
		String accFundaJson = accFundaAnalysisService.deleteBatchAccFunda(listVo);
	   
		return JSONObject.parseObject(accFundaJson);
			
	}
	
	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accfunda/updateAccFundaAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccFundaAnalysis(
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

		mapVo.put("spell_code",StringTool.toPinyinShouZiMu(mapVo.get("bas_name").toString()));

		String AccFundaJson = accFundaAnalysisService.updateAccFundaAnalysis(mapVo);

		return JSONObject.parseObject(AccFundaJson);
	}

}