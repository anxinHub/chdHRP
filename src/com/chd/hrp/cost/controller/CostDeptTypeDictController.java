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

import org.apache.commons.lang.StringUtils;
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
import com.chd.hrp.cost.entity.CostDeptTypeDict;
import com.chd.hrp.cost.serviceImpl.CostDeptTypeDictServiceImpl;

/**
* @Title. @Description.
* 成本类型字典
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostDeptTypeDictController extends BaseController{
	private static Logger logger = Logger.getLogger(CostDeptTypeDictController.class);
	
	
	@Resource(name = "costDeptTypeDictService")
	private final CostDeptTypeDictServiceImpl costDeptTypeDictService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	*成本类型字典<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costdepttypedict/costDeptTypeDictMainPage", method = RequestMethod.GET)
	public String costDeptTypeDictMainPage(Model mode) throws Exception {

		return "hrp/cost/costdepttypedict/costDeptTypeDictMain";

	}
	/**
	*成本类型字典<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costdepttypedict/costDeptTypeDictAddPage", method = RequestMethod.GET)
	public String costDeptTypeDictAddPage(Model mode) throws Exception {

		return "hrp/cost/costdepttypedict/costDeptTypeDictAdd";

	}
	
	@RequestMapping(value = "/hrp/cost/costdepttypedict/costDeptTypeDictChangePage", method = RequestMethod.GET)
	
	public String costDeptTypeDictChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostDeptTypeDict costDeptTypeDict = new CostDeptTypeDict();
		costDeptTypeDict = costDeptTypeDictService.queryCostDeptTypeDictByCode(mapVo);
		mode.addAttribute("cost_type_id", costDeptTypeDict.getCost_type_id());
		mode.addAttribute("cost_type_code", costDeptTypeDict.getCost_type_code());
		mode.addAttribute("cost_type_name", costDeptTypeDict.getCost_type_name());
		mode.addAttribute("spell_code", costDeptTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", costDeptTypeDict.getWbx_code());
		return "hrp/cost/costdepttypedict/costDeptTypeDictChange";
	}
	
	/**
	*成本类型字典<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costdepttypedict/addCostDeptTypeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostDeptTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称cost_type_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_type_name").toString()));
		//根据名称cost_type_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_type_name").toString()));
		String costDeptTypeDictJson = costDeptTypeDictService.addCostDeptTypeDict(mapVo);

		return JSONObject.parseObject(costDeptTypeDictJson);
		
	}
	/**
	*成本类型字典<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costdepttypedict/queryCostDeptTypeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostDeptTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costDeptTypeDict = costDeptTypeDictService.queryCostDeptTypeDict(getPage(mapVo));

		return JSONObject.parseObject(costDeptTypeDict);
		
	}
	/**
	*成本类型字典<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costdepttypedict/deleteCostDeptTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostDeptTypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			String str=assBaseService.isExistsDataByTable("cost_type_dict", ids[0]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的成本类型被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
  
			mapVo.put("cost_type_id",ids[0]); 
            listVo.add(mapVo);
        }
		String costDeptTypeDictJson = costDeptTypeDictService.deleteBatchCostDeptTypeDict(listVo);
	   return JSONObject.parseObject(costDeptTypeDictJson);
			
	}
	
	/**
	*成本类型字典<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costdepttypedict/costDeptTypeDictUpdatePage", method = RequestMethod.GET)
	
	public String costDeptTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostDeptTypeDict costDeptTypeDict = new CostDeptTypeDict();
		costDeptTypeDict = costDeptTypeDictService.queryCostDeptTypeDictByCode(mapVo);
		mode.addAttribute("cost_type_id", costDeptTypeDict.getCost_type_id());
		mode.addAttribute("cost_type_code", costDeptTypeDict.getCost_type_code());
		mode.addAttribute("cost_type_name", costDeptTypeDict.getCost_type_name());
		mode.addAttribute("spell_code", costDeptTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", costDeptTypeDict.getWbx_code());
		return "hrp/cost/costdepttypedict/costDeptTypeDictUpdate";
	}
	/**
	*成本类型字典<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costdepttypedict/updateCostDeptTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostDeptTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称cost_type_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_type_name").toString()));
		//根据名称cost_type_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_type_name").toString()));
		
		String costDeptTypeDictJson = costDeptTypeDictService.updateCostDeptTypeDict(mapVo);
		
		return JSONObject.parseObject(costDeptTypeDictJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costdepttypedict/costDeptTypeDictImportPage", method = RequestMethod.GET)
	public String costDeptTypeDictImportPage(Model mode) throws Exception {

		return "hrp/cost/costdepttypedict/costDeptTypeDictImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costdepttypedict/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","成本类型.xls");
	    return null; 
	 }
	 
	/**
	*成本分类字典<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costdepttypedict/readCostDeptTypeDictFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostDeptTypeDict> list_err = new ArrayList<CostDeptTypeDict>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostDeptTypeDict costDeptTypeDict = new CostDeptTypeDict();
				
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
					
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costDeptTypeDict.setCost_type_code(temp[0]);
						
						mapVo.put("cost_type_code", temp[0]);
					} else {
						err_sb.append("成本分类编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costDeptTypeDict.setCost_type_name(temp[1]);
						
						mapVo.put("cost_type_name", temp[1]);
					} else {
						err_sb.append("成本分类名称为空  ");
					}
				CostDeptTypeDict data_exc_extis = costDeptTypeDictService.queryCostDeptTypeDictByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costDeptTypeDict.setError_type(err_sb.toString());
					
					list_err.add(costDeptTypeDict);
				} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_type_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_type_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostDeptTypeDict data_exc = new CostDeptTypeDict();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costDeptTypeDictService.addBatchCostDeptTypeDict(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本分类字典<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costdepttypedict/addBatchCostDeptTypeDict", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostDeptTypeDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostDeptTypeDict> list_err = new ArrayList<CostDeptTypeDict>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			mapVo.put("cost_type_code", jsonObj.get("cost_type_code"));
			
			mapVo.put("cost_type_name", jsonObj.get("cost_type_name"));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_type_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_type_name").toString()));
		   
				CostDeptTypeDict data_exc_extis = costDeptTypeDictService.queryCostDeptTypeDictByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostDeptTypeDict costDeptTypeDict = new CostDeptTypeDict();
				
				if (err_sb.toString().length() > 0) {
					
					costDeptTypeDict.setCost_type_code(mapVo.get("cost_type_code").toString());
					
					costDeptTypeDict.setCost_type_name(mapVo.get("cost_type_name").toString());
					
					costDeptTypeDict.setError_type(err_sb.toString());
					
					list_err.add(costDeptTypeDict);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costDeptTypeDictService.addBatchCostDeptTypeDict(list_batch);
				
			}
		} catch (DataAccessException e) {
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
    }
}

