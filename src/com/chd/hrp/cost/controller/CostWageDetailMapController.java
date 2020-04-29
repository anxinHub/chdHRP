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
import com.chd.hrp.cost.entity.CostWageDetailMap;
import com.chd.hrp.cost.serviceImpl.CostWageDetailMapServiceImpl;

/**
* @Title. @Description.
* 工资明细数据与工资项关系表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostWageDetailMapController extends BaseController{
	private static Logger logger = Logger.getLogger(CostWageDetailMapController.class);
	
	
	@Resource(name = "costWageDetailMapService")
	private final CostWageDetailMapServiceImpl costWageDetailMapService = null;
   
   
	/**
	*工资明细数据与工资项关系表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/costWageDetailMapMainPage", method = RequestMethod.GET)
	public String costWageDetailMapMainPage(Model mode) throws Exception {

		return "hrp/cost/costwagedetailmap/costWageDetailMapMain";

	}
	/**
	*工资明细数据与工资项关系表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/costWageDetailMapAddPage", method = RequestMethod.GET)
	public String costWageDetailMapAddPage(Model mode) throws Exception {

		return "hrp/cost/costwagedetailmap/costWageDetailMapAdd";

	}
	/**
	*工资明细数据与工资项关系表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/addCostWageDetailMap", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostWageDetailMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costWageDetailMapJson = costWageDetailMapService.addCostWageDetailMap(mapVo);

		return JSONObject.parseObject(costWageDetailMapJson);
		
	}
	/**
	*工资明细数据与工资项关系表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/queryCostWageDetailMap", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostWageDetailMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String costWageDetailMap = costWageDetailMapService.queryCostWageDetailMap(getPage(mapVo));

		return JSONObject.parseObject(costWageDetailMap);
		
	}
	/**
	*工资明细数据与工资项关系表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/deleteCostWageDetailMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostWageDetailMap(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("id",ids[0]);   
			
			mapVo.put("wage_code",ids[1]);   
			
			mapVo.put("wage_column",ids[2]); 
			
            listVo.add(mapVo);
        }
		String costWageDetailMapJson = costWageDetailMapService.deleteBatchCostWageDetailMap(listVo);
	   return JSONObject.parseObject(costWageDetailMapJson);
			
	}
	
	/**
	*工资明细数据与工资项关系表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/costWageDetailMapUpdatePage", method = RequestMethod.GET)
	
	public String costWageDetailMapUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        CostWageDetailMap costWageDetailMap = new CostWageDetailMap();
		costWageDetailMap = costWageDetailMapService.queryCostWageDetailMapByCode(mapVo);
		mode.addAttribute("id", costWageDetailMap.getId());
		
		mode.addAttribute("wage_code", costWageDetailMap.getWage_code());
		
		mode.addAttribute("wage_column", costWageDetailMap.getWage_column());
		
		return "hrp/cost/costwagedetailmap/costWageDetailMapUpdate";
	}
	/**
	*工资明细数据与工资项关系表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/updateCostWageDetailMap", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostWageDetailMap(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costWageDetailMapJson = costWageDetailMapService.updateCostWageDetailMap(mapVo);
		
		return JSONObject.parseObject(costWageDetailMapJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/costWageDetailMapImportPage", method = RequestMethod.GET)
	public String costWageDetailMapImportPage(Model mode) throws Exception {

		return "hrp/cost/costwagedetailmap/costWageDetailMapImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costwagedetailmap/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\对应目录","对应字典.xls");
		
	    return null; 
	 }
	 
	/**
	*工资明细数据与工资项关系表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costwagedetailmap/readCostWageDetailMapFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostWageDetailMap> list_err = new ArrayList<CostWageDetailMap>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostWageDetailMap costWageDetailMap = new CostWageDetailMap();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (StringUtils.isNotEmpty(temp[0])) {
						
						costWageDetailMap.setId(Long.valueOf(temp[0]));
						
						mapVo.put("id", temp[0]);
					} else {
						err_sb.append("ID为空  ");
					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costWageDetailMap.setWage_code(temp[1]);
						
						mapVo.put("wage_code", temp[1]);
					} else {
						err_sb.append("工资项编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costWageDetailMap.setWage_column(temp[2]);
						
						mapVo.put("wage_column", temp[2]);
					} else {
						err_sb.append("工资明细列为空  ");
					}
				CostWageDetailMap data_exc_extis = costWageDetailMapService.queryCostWageDetailMapByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costWageDetailMap.setError_type(err_sb.toString());
					
					list_err.add(costWageDetailMap);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostWageDetailMap data_exc = new CostWageDetailMap();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costWageDetailMapService.addBatchCostWageDetailMap(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*工资明细数据与工资项关系表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costwagedetailmap/addBatchCostWageDetailMap", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostWageDetailMap(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostWageDetailMap> list_err = new ArrayList<CostWageDetailMap>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			mapVo.put("id", jsonObj.get("id"));
			
			mapVo.put("wage_code", jsonObj.get("wage_code"));
			
			mapVo.put("wage_column", jsonObj.get("wage_column"));
			
		   
				CostWageDetailMap data_exc_extis = costWageDetailMapService.queryCostWageDetailMapByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostWageDetailMap costWageDetailMap = new CostWageDetailMap();
				
				if (err_sb.toString().length() > 0) {
					costWageDetailMap.setId(Long.valueOf(mapVo.get("id").toString()));
					
					costWageDetailMap.setWage_code(mapVo.get("wage_code").toString());
					
					costWageDetailMap.setWage_column(mapVo.get("wage_column").toString());
					
					
					costWageDetailMap.setError_type(err_sb.toString());
					
					list_err.add(costWageDetailMap);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costWageDetailMapService.addBatchCostWageDetailMap(list_batch);
				
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

