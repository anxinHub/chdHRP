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
import com.chd.hrp.cost.entity.CostBonusDetailMap;
import com.chd.hrp.cost.entity.CostBonusItemArrt;
import com.chd.hrp.cost.serviceImpl.CostBonusDetailMapServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostBonusItemArrtServiceImpl;

/**
* @Title. @Description.
* 成本_奖金项属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostBonusItemArrtController extends BaseController{
	private static Logger logger = Logger.getLogger(CostBonusItemArrtController.class);
	
	
	@Resource(name = "costBonusItemArrtService")
	private final CostBonusItemArrtServiceImpl costBonusItemArrtService = null;
	
	@Resource(name = "costBonusDetailMapService")
	private final CostBonusDetailMapServiceImpl costBonusDetailMapService = null;
   
   
	/**
	*成本_奖金项属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/costBonusItemArrtMainPage", method = RequestMethod.GET)
	public String costBonusItemArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costbonusitemarrt/costBonusItemArrtMain";

	}
	/**
	*成本_奖金项属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/costBonusItemArrtAddPage", method = RequestMethod.GET)
	public String costBonusItemArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costbonusitemarrt/costBonusItemArrtAdd";

	}
	/**
	*成本_奖金项属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/addCostBonusItemArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostBonusItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称bonus_item_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bonus_item_name").toString()));
		//根据名称bonus_item_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bonus_item_name").toString()));
		String costBonusItemArrtJson = costBonusItemArrtService.addCostBonusItemArrt(mapVo);

		return JSONObject.parseObject(costBonusItemArrtJson);
		
	}
	/**
	*成本_奖金项属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/queryCostBonusItemArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostBonusItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costBonusItemArrt = costBonusItemArrtService.queryCostBonusItemArrt(getPage(mapVo));

		return JSONObject.parseObject(costBonusItemArrt);
		
	}
	/**
	*成本_奖金项属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/deleteCostBonusItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostBonusItemArrt(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("bonus_item_code",ids[3]); 
            listVo.add(mapVo);
        }
		String costBonusItemArrtJson = costBonusItemArrtService.deleteBatchCostBonusItemArrt(listVo);
	   return JSONObject.parseObject(costBonusItemArrtJson);
			
	}
	
	/**
	*成本_奖金项属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/costBonusItemArrtUpdatePage", method = RequestMethod.GET)
	
	public String costBonusItemArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostBonusItemArrt costBonusItemArrt = new CostBonusItemArrt();
		costBonusItemArrt = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);
		mode.addAttribute("group_id", costBonusItemArrt.getGroup_id());
		mode.addAttribute("hos_id", costBonusItemArrt.getHos_id());
		mode.addAttribute("copy_code", costBonusItemArrt.getCopy_code());
		mode.addAttribute("bonus_item_code", costBonusItemArrt.getBonus_item_code());
		mode.addAttribute("bonus_item_name", costBonusItemArrt.getBonus_item_name());
		mode.addAttribute("is_stop", costBonusItemArrt.getIs_stop());
		mode.addAttribute("remark", costBonusItemArrt.getRemark());
		mode.addAttribute("spell_code", costBonusItemArrt.getSpell_code());
		mode.addAttribute("wbx_code", costBonusItemArrt.getWbx_code());
		return "hrp/cost/costbonusitemarrt/costBonusItemArrtUpdate";
	}
	/**
	*成本_奖金项属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/updateCostBonusItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostBonusItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称bonus_item_name拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bonus_item_name").toString()));
		//根据名称bonus_item_name五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bonus_item_name").toString()));
		
		String costBonusItemArrtJson = costBonusItemArrtService.updateCostBonusItemArrt(mapVo);
		
		return JSONObject.parseObject(costBonusItemArrtJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/costBonusItemArrtImportPage", method = RequestMethod.GET)
	public String costBonusItemArrtImportPage(Model mode) throws Exception {

		return "hrp/cost/costbonusitemarrt/costBonusItemArrtImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costbonusitemarrt/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","奖金项维护.xls");
	    return null; 
	 }
	 
	/**
	*成本_奖金项属性表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costbonusitemarrt/readCostBonusItemArrtFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostBonusItemArrt> list_err = new ArrayList<CostBonusItemArrt>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		CostBonusDetailMap costBonusDetailMap = costBonusDetailMapService.querySequenceById();
		
		int result =Integer.valueOf(costBonusDetailMap.getId().toString());
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostBonusItemArrt costBonusItemArrt = new CostBonusItemArrt();
				
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
						
						costBonusItemArrt.setBonus_item_code(temp[0]);
						
						mapVo.put("bonus_item_code", temp[0]);
					} else {
						err_sb.append("奖金项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costBonusItemArrt.setBonus_item_name(temp[1]);
						
						mapVo.put("bonus_item_name", temp[1]);
					} else {
						err_sb.append("奖金项名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costBonusItemArrt.setIs_stop(Integer.valueOf(temp[2]));
						
						mapVo.put("is_stop", temp[2]);
					} else {
						err_sb.append("是否停用为空  ");
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costBonusItemArrt.setRemark(temp[3]);
						
						mapVo.put("remark", temp[3]);
					} else {
						err_sb.append("备注为空  ");
					}
				CostBonusItemArrt data_exc_extis = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costBonusItemArrt.setError_type(err_sb.toString());
					
					list_err.add(costBonusItemArrt);
				} else {
					
						mapVo.put("bonus_column", "bonus"+(result-1+i));
					
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bonus_item_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bonus_item_name").toString()));
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostBonusItemArrt data_exc = new CostBonusItemArrt();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costBonusItemArrtService.addBatchCostBonusItemArrt(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*成本_奖金项属性表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costbonusitemarrt/addBatchCostBonusItemArrt", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostBonusItemArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostBonusItemArrt> list_err = new ArrayList<CostBonusItemArrt>();
		
		CostBonusDetailMap costBonusDetailMap = costBonusDetailMapService.querySequenceById();
		
		int result =Integer.valueOf(costBonusDetailMap.getId().toString());
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		int i=0;
		
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
			
			mapVo.put("bonus_item_code", jsonObj.get("bonus_item_code"));
			
			mapVo.put("bonus_item_name", jsonObj.get("bonus_item_name"));
			
			mapVo.put("is_stop", jsonObj.get("is_stop"));
			
			mapVo.put("remark", jsonObj.get("remark"));
			
			mapVo.put("bonus_column", "bonus"+(result+i));
			
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("bonus_item_name").toString()));
			
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("bonus_item_name").toString()));
			
		   
				CostBonusItemArrt data_exc_extis = costBonusItemArrtService.queryCostBonusItemArrtByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostBonusItemArrt costBonusItemArrt = new CostBonusItemArrt();
				
				if (err_sb.toString().length() > 0) {
					costBonusItemArrt.setBonus_item_code(mapVo.get("bonus_item_code").toString());
					
					costBonusItemArrt.setBonus_item_name(mapVo.get("bonus_item_name").toString());
					
					costBonusItemArrt.setIs_stop(Integer.valueOf(mapVo.get("is_stop").toString()));
					
					costBonusItemArrt.setRemark(mapVo.get("remark").toString());
					
					
					costBonusItemArrt.setError_type(err_sb.toString());
					
					list_err.add(costBonusItemArrt);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costBonusItemArrtService.addBatchCostBonusItemArrt(list_batch);
				
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

