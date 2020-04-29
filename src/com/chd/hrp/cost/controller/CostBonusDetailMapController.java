/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.util.*;
import javax.annotation.Resource;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostBonusDetailMap;
import com.chd.hrp.cost.serviceImpl.CostBonusDetailMapServiceImpl;

/**
* @Title. @Description.
* 奖金明细数据与工资项关系表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostBonusDetailMapController extends BaseController{
	private static Logger logger = Logger.getLogger(CostBonusDetailMapController.class);
	
	
	@Resource(name = "costBonusDetailMapService")
	private final CostBonusDetailMapServiceImpl costBonusDetailMapService = null;
   
   
	/**
	*奖金明细数据与工资项关系表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/costBonusDetailMapMainPage", method = RequestMethod.GET)
	public String costBonusDetailMapMainPage(Model mode) throws Exception {

		return "hrp/cost/costbonusdetailmap/costBonusDetailMapMain";

	}
	/**
	*奖金明细数据与工资项关系表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/costBonusDetailMapAddPage", method = RequestMethod.GET)
	public String costBonusDetailMapAddPage(Model mode) throws Exception {

		return "hrp/cost/costbonusdetailmap/costBonusDetailMapAdd";

	}
	/**
	*奖金明细数据与工资项关系表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/addCostBonusDetailMap", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostBonusDetailMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costBonusDetailMapJson = costBonusDetailMapService.addCostBonusDetailMap(mapVo);

		return JSONObject.parseObject(costBonusDetailMapJson);
		
	}
	/**
	*奖金明细数据与工资项关系表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/queryCostBonusDetailMap", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostBonusDetailMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costBonusDetailMap = costBonusDetailMapService.queryCostBonusDetailMap(getPage(mapVo));

		return JSONObject.parseObject(costBonusDetailMap);
		
	}
	/**
	*奖金明细数据与工资项关系表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/deleteCostBonusDetailMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostBonusDetailMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("id",ids[0]);   
			
			mapVo.put("bonus_code",ids[1]);   
			
			mapVo.put("bonus_column",ids[2]); 
			
            listVo.add(mapVo);
        }
		String costBonusDetailMapJson = costBonusDetailMapService.deleteBatchCostBonusDetailMap(listVo);
	   return JSONObject.parseObject(costBonusDetailMapJson);
			
	}
	
	/**
	*奖金明细数据与工资项关系表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/costBonusDetailMapUpdatePage", method = RequestMethod.GET)
	
	public String costBonusDetailMapUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        CostBonusDetailMap costBonusDetailMap = new CostBonusDetailMap();
		costBonusDetailMap = costBonusDetailMapService.queryCostBonusDetailMapByCode(mapVo);
		mode.addAttribute("id", costBonusDetailMap.getId());
		
		mode.addAttribute("bonus_code", costBonusDetailMap.getBonus_code());
		
		mode.addAttribute("bonus_column", costBonusDetailMap.getBonus_column());
		
		return "hrp/cost/costbonusdetailmap/costBonusDetailMapUpdate";
	}
	/**
	*奖金明细数据与工资项关系表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/updateCostBonusDetailMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostBonusDetailMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costBonusDetailMapJson = costBonusDetailMapService.updateCostBonusDetailMap(mapVo);
		
		return JSONObject.parseObject(costBonusDetailMapJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/costBonusDetailMapImportPage", method = RequestMethod.GET)
	public String costBonusDetailMapImportPage(Model mode) throws Exception {

		return "hrp/cost/costbonusdetailmap/costBonusDetailMapImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costbonusdetailmap/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","奖金明细数据与工资项关系.xls");
		
	    return null; 
	 }
	 
	/**
	*奖金明细数据与工资项关系表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costbonusdetailmap/readCostBonusDetailMapFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostBonusDetailMap> list_err = new ArrayList<CostBonusDetailMap>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostBonusDetailMap costBonusDetailMap = new CostBonusDetailMap();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costBonusDetailMap.setId(Long.valueOf(temp[0]));
						
						mapVo.put("id", temp[0]);
					} else {
						err_sb.append("ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costBonusDetailMap.setBonus_code(temp[1]);
						
						mapVo.put("bonus_code", temp[1]);
					} else {
						err_sb.append("奖金项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costBonusDetailMap.setBonus_column(temp[2]);
						
						mapVo.put("bonus_column", temp[2]);
					} else {
						err_sb.append("奖金明细列为空  ");
					}
				CostBonusDetailMap data_exc_extis = costBonusDetailMapService.queryCostBonusDetailMapByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costBonusDetailMap.setError_type(err_sb.toString());
					
					list_err.add(costBonusDetailMap);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostBonusDetailMap data_exc = new CostBonusDetailMap();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costBonusDetailMapService.addBatchCostBonusDetailMap(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*奖金明细数据与工资项关系表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costbonusdetailmap/addBatchCostBonusDetailMap", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostBonusDetailMap(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostBonusDetailMap> list_err = new ArrayList<CostBonusDetailMap>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			mapVo.put("id", jsonObj.get("id"));
			
			mapVo.put("bonus_code", jsonObj.get("bonus_code"));
			
			mapVo.put("bonus_column", jsonObj.get("bonus_column"));
			
		   
				CostBonusDetailMap data_exc_extis = costBonusDetailMapService.queryCostBonusDetailMapByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostBonusDetailMap costBonusDetailMap = new CostBonusDetailMap();
				
				if (err_sb.toString().length() > 0) {
					costBonusDetailMap.setId(Long.valueOf(mapVo.get("id").toString()));
					
					costBonusDetailMap.setBonus_code(mapVo.get("bonus_code").toString());
					
					costBonusDetailMap.setBonus_column(mapVo.get("bonus_column").toString());
					
					
					costBonusDetailMap.setError_type(err_sb.toString());
					
					list_err.add(costBonusDetailMap);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costBonusDetailMapService.addBatchCostBonusDetailMap(list_batch);
				
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

