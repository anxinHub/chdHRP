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
import com.chd.hrp.cost.entity.CostWageDetailMap;
import com.chd.hrp.cost.entity.CostWageItemArrt;
import com.chd.hrp.cost.serviceImpl.CostWageDetailMapServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostWageItemArrtServiceImpl;

/**
* @Title. @Description.
* 成本_工资项属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostWageItemArrtController extends BaseController{
	private static Logger logger = Logger.getLogger(CostWageItemArrtController.class);
	
	
	@Resource(name = "costWageItemArrtService")
	private final CostWageItemArrtServiceImpl costWageItemArrtService = null;
	
	@Resource(name = "costWageDetailMapService")
	private final CostWageDetailMapServiceImpl costWageDetailMapService = null;
   
   
	/**
	*成本_工资项属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/costWageItemArrtMainPage", method = RequestMethod.GET)
	public String costWageItemArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costwageitemarrt/costWageItemArrtMain";

	}
	/**
	*成本_工资项属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/costWageItemArrtAddPage", method = RequestMethod.GET)
	public String costWageItemArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costwageitemarrt/costWageItemArrtAdd";

	}
	/**
	*成本_工资项属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/addCostWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostWageItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称wage_item_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		//根据名称wage_item_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
		String costWageItemArrtJson = costWageItemArrtService.addCostWageItemArrt(mapVo);

		return JSONObject.parseObject(costWageItemArrtJson);
		
	}
	/**
	*成本_工资项属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/queryCostWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostWageItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costWageItemArrt = costWageItemArrtService.queryCostWageItemArrt(getPage(mapVo));

		return JSONObject.parseObject(costWageItemArrt);
		
	}
	/**
	*成本_工资项属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/deleteCostWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostWageItemArrt(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("wage_item_code",ids[3]); 
            listVo.add(mapVo);
        }
		String costWageItemArrtJson = costWageItemArrtService.deleteBatchCostWageItemArrt(listVo);
	   return JSONObject.parseObject(costWageItemArrtJson);
			
	}
	
	/**
	*成本_工资项属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/costWageItemArrtUpdatePage", method = RequestMethod.GET)
	
	public String costWageItemArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostWageItemArrt costWageItemArrt = new CostWageItemArrt();
		costWageItemArrt = costWageItemArrtService.queryCostWageItemArrtByCode(mapVo);
		mode.addAttribute("group_id", costWageItemArrt.getGroup_id());
		mode.addAttribute("hos_id", costWageItemArrt.getHos_id());
		mode.addAttribute("copy_code", costWageItemArrt.getCopy_code());
		mode.addAttribute("wage_item_code", costWageItemArrt.getWage_item_code());
		mode.addAttribute("wage_item_name", costWageItemArrt.getWage_item_name());
		mode.addAttribute("is_stop", costWageItemArrt.getIs_stop());
		mode.addAttribute("remark", costWageItemArrt.getRemark());
		mode.addAttribute("spell_code", costWageItemArrt.getSpell_code());
		mode.addAttribute("wbx_code", costWageItemArrt.getWbx_code());
		return "hrp/cost/costwageitemarrt/costWageItemArrtUpdate";
	}
	/**
	*成本_工资项属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/updateCostWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostWageItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称wage_item_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
		//根据名称wage_item_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
		
		String costWageItemArrtJson = costWageItemArrtService.updateCostWageItemArrt(mapVo);
		
		return JSONObject.parseObject(costWageItemArrtJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/costWageItemArrtImportPage", method = RequestMethod.GET)
	public String costWageItemArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costwageitemarrt/costWageItemArrtImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costwageitemarrt/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","工资项维护.xls");
	    return null; 
	 }
	 
	/**
	*成本_工资项属性表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costwageitemarrt/readCostWageItemArrtFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostWageItemArrt> list_err = new ArrayList<CostWageItemArrt>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		CostWageDetailMap costWageDetailMap = costWageDetailMapService.querySequence();
		
		int count =Integer.valueOf(costWageDetailMap.getId().toString());
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostWageItemArrt costWageItemArrt = new CostWageItemArrt();
				
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
						
						costWageItemArrt.setWage_item_code(temp[0]);
						
						mapVo.put("wage_item_code", temp[0]);
					} else {
						err_sb.append("工资项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costWageItemArrt.setWage_item_name(temp[1]);
						
						mapVo.put("wage_item_name", temp[1]);
					} else {
						err_sb.append("工资项名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costWageItemArrt.setIs_stop(Integer.valueOf(temp[2]));
						
						mapVo.put("is_stop", temp[2]);
					} else {
						err_sb.append("是否停用为空  ");
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costWageItemArrt.setRemark(temp[3]);
						
						mapVo.put("remark", temp[3]);
					} else {
						err_sb.append("备注为空  ");
					}
				CostWageItemArrt data_exc_extis = costWageItemArrtService.queryCostWageItemArrtByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costWageItemArrt.setError_type(err_sb.toString());
					
					list_err.add(costWageItemArrt);
				} else {
					
						mapVo.put("wage_column", "wage"+(count-1+i));
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostWageItemArrt data_exc = new CostWageItemArrt();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costWageItemArrtService.addBatchCostWageItemArrt(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_工资项属性表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/addBatchCostWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostWageItemArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostWageItemArrt> list_err = new ArrayList<CostWageItemArrt>();
		
		CostWageDetailMap costWageDetailMap = costWageDetailMapService.querySequence();
		
		int count =Integer.valueOf(costWageDetailMap.getId().toString());
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		int i = 0;
		
		try {
			while (it.hasNext()) {
				
				++i;
				
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
			
			mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));
			
			mapVo.put("wage_item_name", jsonObj.get("wage_item_name"));
			
			mapVo.put("is_stop", jsonObj.get("is_stop"));
			
			mapVo.put("remark", jsonObj.get("remark"));
			
			mapVo.put("wage_column", "wage"+(count+i));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("wage_item_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("wage_item_name").toString()));
			
		   
				CostWageItemArrt data_exc_extis = costWageItemArrtService.queryCostWageItemArrtByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostWageItemArrt costWageItemArrt = new CostWageItemArrt();
				
				if (err_sb.toString().length() > 0) {
					costWageItemArrt.setWage_item_code(mapVo.get("wage_item_code").toString());
					
					costWageItemArrt.setWage_item_name(mapVo.get("wage_item_name").toString());
					
					costWageItemArrt.setIs_stop(Integer.valueOf(mapVo.get("is_stop").toString()));
					
					costWageItemArrt.setRemark(mapVo.get("remark").toString());
					
					
					costWageItemArrt.setError_type(err_sb.toString());
					
					list_err.add(costWageItemArrt);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costWageItemArrtService.addBatchCostWageItemArrt(list_batch);
				
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
	*2016/10/28 lxj
	*成本_工资项属性表<BR>
	*同步财务工资项到成本工资项
	*/	
	
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/syncCostWageItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostWageItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costWageItemArrtJson = costWageItemArrtService.syncCostWageItemArrt(mapVo);
		
		return JSONObject.parseObject(costWageItemArrtJson);
	}
	/**
	*2016/10/28 lxj
	*成本_工资项属性表<BR>
	*同步财务工资项到成本工资项
	*/	
	
	@RequestMapping(value = "/hrp/cost/costwageitemarrt/syncCostWageItemArrtNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncCostWageItemArrtNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costWageItemArrtJson = costWageItemArrtService.syncCostWageItemArrtNew(mapVo);
		
		return JSONObject.parseObject(costWageItemArrtJson);
	}
}

