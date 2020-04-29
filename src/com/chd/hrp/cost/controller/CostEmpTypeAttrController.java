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
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.serviceImpl.CostEmpTypeAttrServiceImpl;

/**
* @Title. @Description.
* 成本_职工分类表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostEmpTypeAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(CostEmpTypeAttrController.class);
	
	
	@Resource(name = "costEmpTypeAttrService")
	private final CostEmpTypeAttrServiceImpl costEmpTypeAttrService = null;
   
   
	/**
	*成本_职工分类表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costemptypeattr/costEmpTypeAttrMainPage", method = RequestMethod.GET)
	public String costEmpTypeAttrMainPage(Model mode) throws Exception {

		return "hrp/cost/costemptypeattr/costEmpTypeAttrMain";

	}
	/**
	*成本_职工分类表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costemptypeattr/costEmpTypeAttrAddPage", method = RequestMethod.GET)
	public String costEmpTypeAttrAddPage(Model mode) throws Exception {

		return "hrp/cost/costemptypeattr/costEmpTypeAttrAdd";

	}
	/**
	*成本_职工分类表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costemptypeattr/addCostEmpTypeAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostEmpTypeAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称emp_kind_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_kind_name").toString()));
		//根据名称emp_kind_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_kind_name").toString()));
		String costEmpTypeAttrJson = costEmpTypeAttrService.addCostEmpTypeAttr(mapVo);

		return JSONObject.parseObject(costEmpTypeAttrJson);
		
	}
	/**
	*成本_职工分类表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costemptypeattr/queryCostEmpTypeAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostEmpTypeAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttr(getPage(mapVo));

		return JSONObject.parseObject(costEmpTypeAttr);
		
	}
	/**
	*成本_职工分类表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costemptypeattr/deleteCostEmpTypeAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpTypeAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("emp_kind_code",ids[3]); 
            listVo.add(mapVo);
        }
		String costEmpTypeAttrJson = costEmpTypeAttrService.deleteBatchCostEmpTypeAttr(listVo);
	   return JSONObject.parseObject(costEmpTypeAttrJson);
			
	}
	
	/**
	*成本_职工分类表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costemptypeattr/costEmpTypeAttrUpdatePage", method = RequestMethod.GET)
	
	public String costEmpTypeAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostEmpTypeAttr costEmpTypeAttr = new CostEmpTypeAttr();
		costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
		mode.addAttribute("group_id", costEmpTypeAttr.getGroup_id());
		mode.addAttribute("hos_id", costEmpTypeAttr.getHos_id());
		mode.addAttribute("copy_code", costEmpTypeAttr.getCopy_code());
		mode.addAttribute("emp_kind_code", costEmpTypeAttr.getEmp_kind_code());
		mode.addAttribute("emp_kind_name", costEmpTypeAttr.getEmp_kind_name());
		mode.addAttribute("note", costEmpTypeAttr.getNote());
		mode.addAttribute("spell_code", costEmpTypeAttr.getSpell_code());
		mode.addAttribute("wbx_code", costEmpTypeAttr.getWbx_code());
		return "hrp/cost/costemptypeattr/costEmpTypeAttrUpdate";
	}
	/**
	*成本_职工分类表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costemptypeattr/updateCostEmpTypeAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpTypeAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称emp_kind_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_kind_name").toString()));
		//根据名称emp_kind_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_kind_name").toString()));
		
		String costEmpTypeAttrJson = costEmpTypeAttrService.updateCostEmpTypeAttr(mapVo);
		
		return JSONObject.parseObject(costEmpTypeAttrJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costemptypeattr/costEmpTypeAttrImportPage", method = RequestMethod.GET)
	public String costEmpTypeAttrImportPage(Model mode) throws Exception {

		return "hrp/cost/costemptypeattr/costEmpTypeAttrImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costemptypeattr/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工分类.xls");
	    return null; 
	 }
	 
	/**
	*成本_职工分类表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costemptypeattr/readCostEmpTypeAttrFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostEmpTypeAttr> list_err = new ArrayList<CostEmpTypeAttr>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostEmpTypeAttr costEmpTypeAttr = new CostEmpTypeAttr();
				
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
						
						costEmpTypeAttr.setEmp_kind_code(temp[0]);
						
						mapVo.put("emp_kind_code", temp[0]);
					} else {
						err_sb.append("分类编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costEmpTypeAttr.setEmp_kind_name(temp[1]);
						
						mapVo.put("emp_kind_name", temp[1]);
					} else {
						err_sb.append("分类名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costEmpTypeAttr.setNote(temp[2]);
						
						mapVo.put("note", temp[2]);
					} else {
						err_sb.append("备注为空  ");
					}
				CostEmpTypeAttr data_exc_extis = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costEmpTypeAttr.setError_type(err_sb.toString());
					
					list_err.add(costEmpTypeAttr);
				} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_kind_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_kind_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostEmpTypeAttr data_exc = new CostEmpTypeAttr();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costEmpTypeAttrService.addBatchCostEmpTypeAttr(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_职工分类表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costemptypeattr/addBatchCostEmpTypeAttr", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostEmpTypeAttr(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostEmpTypeAttr> list_err = new ArrayList<CostEmpTypeAttr>();
		
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
			
			mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
			
			mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
			
			mapVo.put("note", jsonObj.get("note"));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_kind_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_kind_name").toString()));
			
		   
				CostEmpTypeAttr data_exc_extis = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostEmpTypeAttr costEmpTypeAttr = new CostEmpTypeAttr();
				
				if (err_sb.toString().length() > 0) {
					costEmpTypeAttr.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					
					costEmpTypeAttr.setEmp_kind_name(mapVo.get("emp_kind_name").toString());
					
					costEmpTypeAttr.setNote(mapVo.get("note").toString());
					
					
					costEmpTypeAttr.setError_type(err_sb.toString());
					
					list_err.add(costEmpTypeAttr);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costEmpTypeAttrService.addBatchCostEmpTypeAttr(list_batch);
				
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
	
	
	/**
	* 2016/10/26 lxj
	*成本_职工分类表<BR>
	*同步
	*/	
	
	@RequestMapping(value = "/hrp/cost/costemptypeattr/syncCostEmpTypeAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostEmpTypeAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costEmpTypeAttrJson = costEmpTypeAttrService.syncCostEmpTypeAttr(mapVo);
		
		return JSONObject.parseObject(costEmpTypeAttrJson);
	}
	/**
	* 2016/10/26 lxj
	*成本_职工分类表<BR>
	*同步
	*/	
	
	@RequestMapping(value = "/hrp/cost/costemptypeattr/syncCostEmpTypeAttrNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostEmpTypeAttrNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costEmpTypeAttrJson = costEmpTypeAttrService.syncCostEmpTypeAttrNew(mapVo);
		
		return JSONObject.parseObject(costEmpTypeAttrJson);
	}
}

