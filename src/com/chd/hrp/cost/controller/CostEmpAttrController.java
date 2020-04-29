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
import com.chd.hrp.cost.entity.CostEmpAttr;
import com.chd.hrp.cost.entity.CostEmpTitleAttr;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.serviceImpl.CostEmpAttrServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpTitleAttrServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpTypeAttrServiceImpl;

/**
* @Title. @Description.
* 成本_职工字典表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostEmpAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(CostEmpAttrController.class);
	
	
	@Resource(name = "costEmpAttrService")
	private final CostEmpAttrServiceImpl costEmpAttrService = null;
	
	@Resource(name = "costEmpTitleAttrService")
	private final CostEmpTitleAttrServiceImpl costEmpTitleAttrService = null;
	
	@Resource(name = "costEmpTypeAttrService")
	private final CostEmpTypeAttrServiceImpl costEmpTypeAttrService = null;
   
   
	/**
	*成本_职工字典表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costempattr/costEmpAttrMainPage", method = RequestMethod.GET)
	public String costEmpAttrMainPage(Model mode) throws Exception {

		return "hrp/cost/costempattr/costEmpAttrMain";

	}
	/**
	*成本_职工字典表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costempattr/costEmpAttrAddPage", method = RequestMethod.GET)
	public String costEmpAttrAddPage(Model mode) throws Exception {

		return "hrp/cost/costempattr/costEmpAttrAdd";

	}
	/**
	*成本_职工字典表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costempattr/addCostEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称emp_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));
		//根据名称emp_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));
		String costEmpAttrJson = costEmpAttrService.addCostEmpAttr(mapVo);

		return JSONObject.parseObject(costEmpAttrJson);
		
	}
	/**
	*成本_职工字典表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costempattr/queryCostEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpAttr = costEmpAttrService.queryCostEmpAttr(getPage(mapVo));

		return JSONObject.parseObject(costEmpAttr);
		
	}
	/**
	*成本_职工字典表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costempattr/deleteCostEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("emp_id",ids[2]);   
			mapVo.put("emp_no",ids[3]); 
			mapVo.put("emp_code",ids[4]); 
			mapVo.put("copy_code",ids[5]); 
            listVo.add(mapVo);
        }
		String costEmpAttrJson = costEmpAttrService.deleteBatchCostEmpAttr(listVo);
	   return JSONObject.parseObject(costEmpAttrJson);
			
	}
	
	/**
	*成本_职工字典表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costempattr/costEmpAttrUpdatePage", method = RequestMethod.GET)
	
	public String costEmpAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostEmpAttr costEmpAttr = new CostEmpAttr();
		costEmpAttr = costEmpAttrService.queryCostEmpAttrByCode(mapVo);
		mode.addAttribute("group_id", costEmpAttr.getGroup_id());
		mode.addAttribute("hos_id", costEmpAttr.getHos_id());
		mode.addAttribute("emp_id", costEmpAttr.getEmp_id());
		mode.addAttribute("emp_no", costEmpAttr.getEmp_no());
		mode.addAttribute("copy_code", costEmpAttr.getCopy_code());
		mode.addAttribute("title_code", costEmpAttr.getTitle_code());
		mode.addAttribute("title_name", costEmpAttr.getTitle_name());
		mode.addAttribute("emp_kind_name", costEmpAttr.getEmp_kind_name());
		mode.addAttribute("emp_kind_code", costEmpAttr.getEmp_kind_code());
		mode.addAttribute("emp_code", costEmpAttr.getEmp_code());
		mode.addAttribute("emp_name", costEmpAttr.getEmp_name());
		mode.addAttribute("note", costEmpAttr.getNote());
		mode.addAttribute("spell_code", costEmpAttr.getSpell_code());
		mode.addAttribute("wbx_code", costEmpAttr.getWbx_code());
		return "hrp/cost/costempattr/costEmpAttrUpdate";
	}
	/**
	*成本_职工字典表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costempattr/updateCostEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称emp_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));
		//根据名称emp_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));
		
		String costEmpAttrJson = costEmpAttrService.updateCostEmpAttr(mapVo);
		
		return JSONObject.parseObject(costEmpAttrJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costempattr/costEmpAttrImportPage", method = RequestMethod.GET)
	public String costEmpAttrImportPage(Model mode) throws Exception {

		return "hrp/cost/costempattr/costEmpAttrImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costempattr/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","职工维护.xls");
	    return null; 
	 }
	 
	/**
	*成本_职工字典表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costempattr/readCostEmpAttrFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostEmpAttr> list_err = new ArrayList<CostEmpAttr>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostEmpAttr costEmpAttr = new CostEmpAttr();
				
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
						
						costEmpAttr.setTitle_code(temp[0]);
						
						mapVo.put("title_code", temp[0]);
					} else {
						err_sb.append("职称编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costEmpAttr.setEmp_kind_code(temp[2]);
						
						mapVo.put("emp_kind_code", temp[2]);
					} else {
						err_sb.append("分类编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costEmpAttr.setEmp_code(temp[4]);
						
						mapVo.put("emp_code", temp[4]);
					} else {
						err_sb.append("职工编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costEmpAttr.setEmp_name(temp[5]);
						
						mapVo.put("emp_name", temp[5]);
					} else {
						err_sb.append("职工姓名为空  ");
					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costEmpAttr.setNote(temp[6]);
						
						mapVo.put("note", temp[6]);
					} else {
						err_sb.append("备注为空  ");
					}
					
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costEmpAttr.setTitle_name(temp[1]);
					}
					
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costEmpAttr.setEmp_kind_name(temp[3]);
					}
					
				CostEmpAttr data_exc_extis = costEmpAttrService.queryCostEmpAttrByCode(mapVo);
				
				CostEmpTitleAttr costEmpTitleAttr = costEmpTitleAttrService.queryCostEmpTitleAttrByCode(mapVo);
				
				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("emp_code", mapVo.get("emp_code"));
				
				CostEmpAttr costEmp = costEmpAttrService.queryEmpByCode(byCodeMap);
				
				if(costEmp != null ){
					
					mapVo.put("emp_id", costEmp.getEmp_id());
					
					mapVo.put("emp_no", costEmp.getEmp_no());
					
					costEmpAttr.setEmp_code((String)mapVo.get("emp_code"));
					
				}else{
					
					costEmpAttr.setEmp_code(mapVo.get("emp_code").toString());
					
					err_sb.append("职工不存在 ");
					
				}
				
				if (costEmpTitleAttr == null) {
					err_sb.append("职工职称不存在！ ");
				}
				
				if (costEmpTypeAttr == null) {
					err_sb.append("职工分类不存在！ ");
				}
				
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costEmpAttr.setError_type(err_sb.toString());
					
					list_err.add(costEmpAttr);
				} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostEmpAttr data_exc = new CostEmpAttr();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costEmpAttrService.addBatchCostEmpAttr(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_职工字典表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costempattr/addBatchCostEmpAttr", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostEmpAttr(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostEmpAttr> list_err = new ArrayList<CostEmpAttr>();
		
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
			
			mapVo.put("title_code", jsonObj.get("title_code"));
			
			mapVo.put("title_name", jsonObj.get("title_name"));
			
			mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
			
			mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
			
			mapVo.put("emp_code", jsonObj.get("emp_code"));
			
			mapVo.put("emp_name", jsonObj.get("emp_name"));
			
			mapVo.put("note", jsonObj.get("note"));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("emp_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("emp_name").toString()));
			
		   
				CostEmpAttr data_exc_extis = costEmpAttrService.queryCostEmpAttrByCode(mapVo);
				
				CostEmpTitleAttr costEmpTitleAttr = costEmpTitleAttrService.queryCostEmpTitleAttrByCode(mapVo);
				
				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService.queryCostEmpTypeAttrByCode(mapVo);
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("emp_code", mapVo.get("emp_code"));
				
				CostEmpAttr costEmp = costEmpAttrService.queryEmpByCode(byCodeMap);
				
				if(costEmp != null ){
					
					mapVo.put("emp_id", costEmp.getEmp_id());
					
					mapVo.put("emp_no", costEmp.getEmp_no());
					
				}else{
					
					err_sb.append("职工不存在 ");
					
				}
				
				if (costEmpTitleAttr == null) {
					err_sb.append("职工职称不存在！ ");
				}
				
				if (costEmpTypeAttr == null) {
					err_sb.append("职工分类不存在！ ");
				}
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostEmpAttr costEmpAttr = new CostEmpAttr();
				
				if (err_sb.toString().length() > 0) {
					
					costEmpAttr.setEmp_code(mapVo.get("emp_code").toString());
					
					costEmpAttr.setTitle_code(mapVo.get("title_code").toString());
					
					costEmpAttr.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					
					costEmpAttr.setEmp_code(mapVo.get("emp_code").toString());
					
					costEmpAttr.setEmp_name(mapVo.get("emp_name").toString());
					
					costEmpAttr.setNote(mapVo.get("note").toString());
					
					costEmpAttr.setError_type(err_sb.toString());
					
					list_err.add(costEmpAttr);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costEmpAttrService.addBatchCostEmpAttr(list_batch);
				
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

