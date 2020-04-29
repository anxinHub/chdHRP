/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostServeItemDict;
import com.chd.hrp.cost.service.CostServeItemDictService;

/**
* @Title. @Description.
* 成本_职工字典表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostServeItemDictController extends BaseController{
	private static Logger logger = Logger.getLogger(CostServeItemDictController.class);
	
	
	@Resource(name = "costServeItemDictService")
	private final CostServeItemDictService costServeItemDictService = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	*服务项目编码<BR>
	*维护页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costserveitemdict/costServeItemDictMainPage", method = RequestMethod.GET)
	public String costEmpAttrMainPage(Model mode) throws Exception {

		return "hrp/cost/costserveitemdict/costServeItemDictMain";

	}
	
	
	/**
	*跳转到添加页面<BR>
	*维护页面跳转
	*/  
	@RequestMapping(value = "/hrp/cost/costserveitemdict/costServeItemDictAddPage", method = RequestMethod.GET)
	public String costAssDetailAddPage(Model mode) throws Exception {

		return "hrp/cost/costserveitemdict/costServeItemDictAdd";
	}

	
	@RequestMapping(value = "/hrp/cost/costserveitemdict/addCostServeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostServeItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("server_item_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("server_item_name").toString()));
		
		
		String costServeItemDictJson = costServeItemDictService.addCostServeItemDict(mapVo);

		return JSONObject.parseObject(costServeItemDictJson);
		
	}
	
	/**
	*<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costserveitemdict/queryCostServeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostServeItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		   mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		   mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
           mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		String costServeItemDict = costServeItemDictService.queryCostServeItemDict(getPage(mapVo));

		return JSONObject.parseObject(costServeItemDict);
		
	}
	
	/**
	*
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costserveitemdict/deleteCostServeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostServeItemDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot="";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
			String str = assBaseService.isExistsDataByTable("cost_serve_item_dict", ids[3]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的服务项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id",ids[0]);   
			
			mapVo.put("hos_id",ids[1]);   
			
			mapVo.put("copy_code",ids[2]);   
			
			mapVo.put("server_item_code",ids[3]);   

			
            listVo.add(mapVo);
        }
		String costServeItemDictJson = costServeItemDictService.deleteBatchCostAssDetail(listVo);
	   return JSONObject.parseObject(costServeItemDictJson);
			
	}
	
	/**
	*科室成本明细数据表_医辅分摊<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costserveitemdict/costServeItemDictUpdatePage", method = RequestMethod.GET)
	public String costAssDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		
		CostServeItemDict costServeItemDict = new CostServeItemDict();
        
		costServeItemDict = costServeItemDictService.queryCostServeItemDictByCode(mapVo);
		
		mode.addAttribute("group_id", costServeItemDict.getGroup_id());
		
		mode.addAttribute("hos_id", costServeItemDict.getHos_id());
		
		mode.addAttribute("copy_code", costServeItemDict.getCopy_code());
		
		mode.addAttribute("server_item_code", costServeItemDict.getServer_item_code());
		
		mode.addAttribute("server_item_name", costServeItemDict.getServer_item_name());
		
		mode.addAttribute("is_stop", costServeItemDict.getIs_stop());
		
		return "hrp/cost/costserveitemdict/costServeItemDictUpdate";
	}
	
	
	@RequestMapping(value = "/hrp/cost/costserveitemdict/updateCostServeItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostServeItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("server_item_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("server_item_name").toString()));
		
		String costServeItemDictJson = costServeItemDictService.updateCostAssDetail(mapVo);
		
		return JSONObject.parseObject(costServeItemDictJson);
	}
	
	@RequestMapping(value = "/hrp/cost/costserveitemdict/costServeItemDictImportPage", method = RequestMethod.GET)
	public String costServeItemDictImportPage(Model mode) throws Exception {

		return "hrp/cost/costserveitemdict/costServeItemDictImport";
	}

	@RequestMapping(value = "hrp/cost/costserveitemdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "服务项目.xls");
		return null;
	}
	
	// 导入
		@RequestMapping(value="/hrp/cost/costserveitemdict/readCostServeItemDictFiles",method = RequestMethod.POST)  
	    public String readCostServeItemDictFiles(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
			
			List<CostServeItemDict> list_err = new ArrayList<CostServeItemDict>();
			List<String[]> list = UploadUtil.readFile(plupload, request, response);
			try {
				for (int i = 1; i < list.size(); i++) {
					
					StringBuffer err_sb = new StringBuffer();
					
					CostServeItemDict CostServeItemDict = new CostServeItemDict();
					
					String temp[] = list.get(i);// 行
					Map<String, Object> mapVo = new HashMap<String, Object>();
					if (mapVo.get("group_id") == null) {

						mapVo.put("group_id", SessionManager.getGroupId());
					}
					if (mapVo.get("hos_id") == null) {

						mapVo.put("hos_id", SessionManager.getHosId());
					}
					if (mapVo.get("copy_code") == null) {

						mapVo.put("copy_code", SessionManager.getCopyCode());
					}
						 
						if (StringTool.isNotBlank(temp[0])) {
							
							CostServeItemDict.setServer_item_code(temp[0]);
							mapVo.put("server_item_code", temp[0]);
						
						} else {
							
							err_sb.append("内部服务项目编码为空  ");
						}
						
						if (StringTool.isNotBlank(temp[1])) {
							
						CostServeItemDict.setServer_item_name(temp[1]);
			    		mapVo.put("server_item_name", temp[1]);
						
						} else {
							
							err_sb.append("内部服务项目名称为空  ");
						}
						 
						if (StringTool.isNotBlank(temp[2])) {
							
						CostServeItemDict.setIs_stop(Integer.valueOf(temp[2]));
			    		mapVo.put("is_stop", temp[2]);
						
						} else {
							
							err_sb.append("是否停用为空  ");
						}
					
						Map<String, Object> byCodeMap = new HashMap<String, Object>();

						byCodeMap.put("group_id", mapVo.get("group_id"));

						byCodeMap.put("hos_id", mapVo.get("hos_id"));

						byCodeMap.put("copy_code", mapVo.get("copy_code"));

						byCodeMap.put("server_item_code", mapVo.get("server_item_code"));
						
					CostServeItemDict data_exc_extis = costServeItemDictService.queryCostServeItemDictByCode(byCodeMap);
					
					if (data_exc_extis != null) {
						
						err_sb.append("数据已经存在！ ");
					}
				
					if (err_sb.toString().length() > 0) {
						
						CostServeItemDict.setError_type(err_sb.toString());
						
						list_err.add(CostServeItemDict);
						
					} else {
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("server_item_name").toString()));
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("server_item_name").toString()));
						String dataJson = costServeItemDictService.addCostServeItemDict(mapVo);
					}
				}
				
			} catch (DataAccessException e) {
				
				CostServeItemDict data_exc = new CostServeItemDict();
				
				data_exc.setError_type("导入系统出错");
				
				list_err.add(data_exc);
			}
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		}
		
		//导入保存
				@RequestMapping(value = "/hrp/cost/costserveitemdict/addImportCostServeItemDict", method = RequestMethod.POST)
				@ResponseBody
			    public Map<String, Object> addImportCostServeItemDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
					List<CostServeItemDict> list_err = new ArrayList<CostServeItemDict>();
					
					JSONArray json = JSONArray.parseArray(paramVo);
				
					Map<String, Object> mapVo = new HashMap<String, Object>();
					
						if (mapVo.get("group_id") == null) {
							
							mapVo.put("group_id", SessionManager.getGroupId());
						}
						
						if (mapVo.get("hos_id") == null) {
							
							mapVo.put("hos_id", SessionManager.getHosId());
						}
						if (mapVo.get("copy_code") == null) {
							
							mapVo.put("copy_code", SessionManager.getCopyCode());
						}
					
						Iterator it = json.iterator();
					
						try {
						
						while (it.hasNext()) {
							
						StringBuffer err_sb = new StringBuffer();
						
						CostServeItemDict CostServeItemDict = new CostServeItemDict();
						
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						
								if (StringTool.isNotBlank(jsonObj.get("server_item_code"))) {
									
								CostServeItemDict.setServer_item_code((String)jsonObj.get("server_item_code"));
					    		mapVo.put("server_item_code", jsonObj.get("server_item_code"));
					    		} else {
									
									err_sb.append("内部服务项目编码为空  ");
								}
								
								if (StringTool.isNotBlank(jsonObj.get("server_item_name"))) {
									
								CostServeItemDict.setServer_item_name((String)jsonObj.get("server_item_name"));
					    		mapVo.put("server_item_name", jsonObj.get("server_item_name"));
					    		} else {
									
									err_sb.append("内部服务项目名称为空  ");
								}
							
								if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
									
								CostServeItemDict.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
					    		mapVo.put("is_stop", jsonObj.get("is_stop"));
					    		} else {
									
									err_sb.append("是否停用为空  ");
								}
							
							CostServeItemDict data_exc_extis = costServeItemDictService.queryCostServeItemDictByCode(mapVo);
							
							if (data_exc_extis != null) {
								
								err_sb.append("编码已经存在！ ");
							}
							if (err_sb.toString().length() > 0) {
								
								CostServeItemDict.setError_type(err_sb.toString());
								
								list_err.add(CostServeItemDict);
								
							} else {
								mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("server_item_name").toString()));
								mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("server_item_name").toString()));
								String dataJson = costServeItemDictService.addCostServeItemDict(mapVo);
							}
						}
						
					} catch (DataAccessException e) {
						
						CostServeItemDict data_exc = new CostServeItemDict();
						list_err.add(data_exc);
						
						return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
					}
						
					if (list_err.size() > 0) {
						
						return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
					} else {
						
						return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
					}
					
				}
	
	
}

