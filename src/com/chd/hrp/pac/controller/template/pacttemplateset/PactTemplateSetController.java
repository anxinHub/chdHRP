package com.chd.hrp.pac.controller.template.pacttemplateset;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.template.pacttemplateset.PactTemplateSetService;

@Controller
public class PactTemplateSetController extends BaseController{
	
	@Resource(name = "pactTemplateSetService")
	private PactTemplateSetService pactTemplateSetService;
	
	
	/**
	 * 合同模板设置页面
	 * @return
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/PactTemplateSetMainPage")
	public String PactTemplateSetMainPage() {
		return "hrp/pac/template/pacttemplateset/pactTemplateSetMain";
	}
	
	/**
	 * 表单模板项属性配置页面
	 * @return
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/pactTemplateSheetSetPage")
	public String pactTemplateSheetSetPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//表单模块元素配置  信息查询
		Map<String,Object> sheetMap = pactTemplateSetService.queryPactTemplateSheet(mapVo);
		
		if(sheetMap != null){
			mapVo.put("et_rowid", sheetMap.get("et_rowid")) ;
			mode.addAttribute("et_rowid", sheetMap.get("et_rowid"));
			mode.addAttribute("pts_columns", sheetMap.get("pts_columns"));
		}else{
			mode.addAttribute("et_rowid", null);
			mode.addAttribute("pts_columns", null);
		}
		
		mapVo.put("ptm_showmode", 2) ;// 模块样式 1 表格  2 表单
		//根据 方案id,模块id 查询 按钮信息   渲染按钮用 
		List<Map<String,Object>> buttonInfo = 	pactTemplateSetService.queryPactTemplateButtonInfo(mapVo) ;
		
		mode.addAttribute("pt_rowid", mapVo.get("pt_rowid"));
		mode.addAttribute("ptm_rowid", mapVo.get("ptm_rowid"));
		
		String buttonStr = "" ;
		int ptm_position = 0  ;
		int ptm_position_x = 0 ;
		int ptm_position_y = 0 ;
		if(buttonInfo.size()>0){
			for(Map<String,Object> item : buttonInfo){
				buttonStr += item.get("buttonStr").toString() +"," ;
			}
			ptm_position = Integer.parseInt(buttonInfo.get(0).get("bb_position").toString());
			if(ptm_position==7){
				ptm_position_x = Integer.parseInt(buttonInfo.get(0).get("bb_xposition").toString());
				ptm_position_y = Integer.parseInt(buttonInfo.get(0).get("bb_yposition").toString());
			}
			
		}
		// 渲染 按钮用字符串
		if(buttonStr != "" ){
			mode.addAttribute("buttonStr", buttonStr.substring(0, buttonStr.length()-1)) ;
			mode.addAttribute("ptm_position", ptm_position);
		}else{
			mode.addAttribute("buttonStr", null) ;
			mode.addAttribute("ptm_position", null);
		}
		if(ptm_position==7){
			mode.addAttribute("ptm_position_x", ptm_position_x);
			mode.addAttribute("ptm_position_y", ptm_position_y);
			
		}else{
			mode.addAttribute("ptm_position_x", null);
			mode.addAttribute("ptm_position_y", null);
		}
		
		
		return "hrp/pac/template/pacttemplateset/pactTemplateSheetSet";
	}
	/**
	 * 表格模板项属性配置页面
	 * @return
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/pactTemplateGridSetPage")
	public String pactTemplateGridSetPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//表格模块标签页配置  信息查询
		List<Map<String,Object>> girdList = pactTemplateSetService.queryPactTemplateGrid(mapVo);
		
		String tabStr = "" ;
		if(girdList.size()>0){
			for(Map<String,Object> item : girdList){
				 
				tabStr += item.get("et_rowid").toString() +"_"+item.get("gt_tab").toString()+"_"+item.get("gt_istotalrow").toString() +"," ;
			}
			mapVo.put("et_rowid", girdList.get(0).get("et_rowid"));
			mode.addAttribute("gt_istotalrow",  girdList.get(0).get("gt_istotalrow"));
		}
		//渲染 页签用
		if(tabStr!=""){
			mode.addAttribute("tabStr", tabStr.substring(0, tabStr.length()-1)) ;
		}else{
			mode.addAttribute("tabStr",null);
		}
		mapVo.put("ptm_showmode", 1) ;// 模块样式 1 表格  2 表单
		
		//根据 方案id,模块id 查询 按钮信息   渲染按钮用 
		List<Map<String,Object>> buttonInfo = 	pactTemplateSetService.queryPactTemplateButtonInfo(mapVo) ;
		
		mode.addAttribute("pt_rowid", mapVo.get("pt_rowid"));
		mode.addAttribute("ptm_rowid", mapVo.get("ptm_rowid"));
		mode.addAttribute("et_rowid", mapVo.get("et_rowid"));
		
		
		String buttonStr = "" ;
		if(buttonInfo.size()>0){
			for(Map<String,Object> item : buttonInfo){
				buttonStr += item.get("buttonStr").toString() +"," ;
			}
			
		}
		// 渲染 按钮用字符串
		if(buttonStr != "" ){
			mode.addAttribute("buttonStr", buttonStr.substring(0, buttonStr.length()-1)) ;
		}else{
			mode.addAttribute("buttonStr", null) ;
		}
		
		return "hrp/pac/template/pacttemplateset/pactTemplateGridSet";
	}
	
	/**
	 * 表格模板项属性配置页面
	 * @return
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/pactTemplateGridInfoSetPage")
	public String pactTemplateGridInfoSetPage() {
		return "hrp/pac/template/pacttemplateset/pactTemplateGridInfoSet";
	}
	
	/**
	 * 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/queryPactTemplateSet", method = RequestMethod.POST)
	public Map<String, Object> queryPactTemplateSet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String string = pactTemplateSetService.queryPactTemplateSet(getPage(mapVo));
			
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/savePactTemplateSet", method = RequestMethod.POST)
	public Map<String, Object> savePactTemplateSet(@RequestParam(value="ParamVo") String paramVo, Model mode) {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("pt_rowid", id.split("@")[0]);
			mapVo.put("pt_name", id.split("@")[1]);
			mapVo.put("pt_type", id.split("@")[2]);
			mapVo.put("pt_attribute", id.split("@")[3]);
			listVo.add(mapVo);
		}
		try {
			String json  = pactTemplateSetService.savePactTemplateSet(listVo);
				
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * @Description  删除
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/deletePactTemplateSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactTemplateSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		Date date = new Date();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("pt_rowid", ids[0]);
			     
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = pactTemplateSetService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 选择合同模板后  根据 模板id 查询 合同模板表单模块/表格模块数据
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/queryTemplateModular", method = RequestMethod.POST)
	public String queryTemplateModular(@RequestParam Map<String, Object> mapVo, Model mode) {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String string = pactTemplateSetService.queryTemplateModular(getPage(mapVo));
			
			return string;
	}
	
	/**
	 * 合同配置项查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/queryPactTemplateItem", method = RequestMethod.POST)
	public Map<String, Object> queryPactTemplateItem(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String string = pactTemplateSetService.queryPactTemplateItem(getPage(mapVo));
			
			return JSONObject.parseObject(string);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 根据 合同模板配置方案模块id  查询 合同模板表单配置项/表格配置项数据
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/queryPactTemplateItemSet", method = RequestMethod.POST)
	public String queryPactTemplateItemSet(@RequestParam Map<String, Object> mapVo, Model mode) {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			String string = pactTemplateSetService.queryPactTemplateItemSet(getPage(mapVo));
			
			return string;
	}
	
	/**
	 * 合同模板配置方案模块保存
	 * @param paramVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/savePactTemplateModular", method = RequestMethod.POST)
	public Map<String, Object> savePactTemplateModular(@RequestParam(value="ParamVo") String paramVo, Model mode) {
		try {
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("pt_rowid", id.split("@")[0]);
				mapVo.put("ptm_rowid", id.split("@")[1]);
				mapVo.put("ptm_code", id.split("@")[2]);
				mapVo.put("ptm_name", id.split("@")[3]);
				mapVo.put("ptm_showmode", id.split("@")[4]);
				mapVo.put("ptm_no", id.split("@")[5]);
				mapVo.put("ptm_height",id.split("@")[6].substring(0, id.split("@")[6].length()-1));//去除%
				mapVo.put("ptm_width", id.split("@")[7].substring(0, id.split("@")[7].length()-1));//去除%
				mapVo.put("ptm_position",id.split("@")[8]);
				if("7".equals(id.split("@")[8])){
					mapVo.put("ptm_position_x", id.split("@")[9]);
					mapVo.put("ptm_position_y", id.split("@")[10]);
				}else{
					mapVo.put("ptm_position_x", null);
					mapVo.put("ptm_position_y", null);
				}
				
				listVo.add(mapVo);
			}
			String json  = pactTemplateSetService.savePactTemplateModular(listVo);
				
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * @Description  双击 删除合同模板配置方案模块 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/deletePactTemplateModular", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactTemplateModular(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String matJson;
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
			matJson = pactTemplateSetService.deletePactTemplateModular(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	/**
	 * 表单配置  保存
	 * @param paramVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/savePactTemplateSheetItemSet", method = RequestMethod.POST)
	public Map<String, Object> savePactTemplateSheetItemSet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {	
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
			String json  = pactTemplateSetService.savePactTemplateSheetItemSet(mapVo);
				
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	
	/**
	 * 表格配置  保存
	 * @param paramVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/savePactTemplateGridItemSet", method = RequestMethod.POST)
	public Map<String, Object> savePactTemplateGridItemSet(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {	
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
			String json  = pactTemplateSetService.savePactTemplateGridItemSet(mapVo);
				
			return JSONObject.parseObject(json);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
	/**
	 * @Description  双击 删除表格配置页面 页签 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/deletePactTemplateGridtab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePactTemplateGridtab(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String matJson;
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
			matJson = pactTemplateSetService.deletePactTemplateGridtab(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matJson);
	}
	
	
	
	/**
	 * 表格模板项属性配置信息查询
	 * @return
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/queryPactTemplateGridSetInfo")
	@ResponseBody
	public Map<String, Object> queryPactTemplateGridSetInfo(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("ptm_showmode", 1) ;// 模块样式 1 表格  2 表单
		
		//根据 方案id,模块id 查询 按钮信息   渲染按钮用 
		List<Map<String,Object>> buttonInfo = 	pactTemplateSetService.queryPactTemplateButtonInfo(mapVo) ;
		
		Map<String,Object> tabMap = pactTemplateSetService.queryPactTemplateGridTab(mapVo);
		
		String buttonStr = "" ;
		if(buttonInfo.size()>0){
			for(Map<String,Object> item : buttonInfo){
				buttonStr += item.get("buttonStr").toString() +"," ;
			}
			
		}
		
		return JSONObject.parseObject("{\"buttonStr\":\""+buttonStr+"\",\"gt_istotalrow\":\""+tabMap.get("gt_istotalrow")+"\",\"state\":\"true\"}");
	}
	
	/**
	 * 取数函数下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "hrp/pac/template/pacttemplateset/queryCsCode")
	@ResponseBody
	public String queryCsCode(@RequestParam Map<String, Object> mapVo, Model mode){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		return pactTemplateSetService.queryCsCode(mapVo);
	}
}
