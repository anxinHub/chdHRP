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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostParaAssSetData;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostParaAssSetDataServiceImpl;

/**
* @Title. @Description.
* 医辅分摊设置采集数据表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostParaAssSetDataController extends BaseController{
	private static Logger logger = Logger.getLogger(CostParaAssSetDataController.class);
	
	
	@Resource(name = "costParaAssSetDataService")
	private final CostParaAssSetDataServiceImpl costParaAssSetDataService = null;
	
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
	/**
	*医辅分摊设置采集数据表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/costParaAssSetDataMainPage", method = RequestMethod.GET)
	public String costParaAssSetDataMainPage(Model mode) throws Exception {

		return "hrp/cost/costparaasssetdata/costParaAssSetDataMain";

	}
	/**
	*医辅分摊设置采集数据表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/costParaAssSetDataAddPage", method = RequestMethod.GET)
	public String costParaAssSetDataAddPage(Model mode) throws Exception {

		return "hrp/cost/costparaasssetdata/costParaAssSetDataAdd";

	}
	/**
	*医辅分摊设置采集数据表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/addCostParaAssSetData", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostParaAssSetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costParaAssSetDataJson = costParaAssSetDataService.addCostParaAssSetData(mapVo);

		return JSONObject.parseObject(costParaAssSetDataJson);
		
	}
	/**
	*医辅分摊设置采集数据表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/queryCostParaAssSetData", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostParaAssSetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
		mapVo.put("is_flag", para_value);
		
		String costParaAssSetData = costParaAssSetDataService.queryCostParaAssSetData(getPage(mapVo));

		return JSONObject.parseObject(costParaAssSetData);
		
	}
	/**
	*医辅分摊设置采集数据表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/deleteCostParaAssSetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaAssSetData(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("year_month",ids[3]);   
			mapVo.put("dept_id",ids[4]);   
			mapVo.put("dept_no",ids[5]);   
			mapVo.put("server_dept_id",ids[6]);   
			mapVo.put("server_dept_no",ids[7]);   
			mapVo.put("cost_item_id",ids[8]);   
			mapVo.put("cost_item_no",ids[9]);   
			mapVo.put("para_code",ids[10]); 
            listVo.add(mapVo);
        }
		String costParaAssSetDataJson = costParaAssSetDataService.deleteBatchCostParaAssSetData(listVo);
	   return JSONObject.parseObject(costParaAssSetDataJson);
			
	}
	
	/**
	*医辅分摊设置采集数据表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/costParaAssSetDataUpdatePage", method = RequestMethod.GET)
	
	public String costParaAssSetDataUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostParaAssSetData costParaAssSetData = new CostParaAssSetData();
        
        String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
		mapVo.put("is_flag", para_value);
        
		costParaAssSetData = costParaAssSetDataService.queryCostParaAssSetDataByCode(mapVo);
		mode.addAttribute("group_id", costParaAssSetData.getGroup_id());
		mode.addAttribute("hos_id", costParaAssSetData.getHos_id());
		mode.addAttribute("copy_code", costParaAssSetData.getCopy_code());
		mode.addAttribute("year_month", costParaAssSetData.getYear_month());
		mode.addAttribute("dept_id", costParaAssSetData.getDept_id());
		mode.addAttribute("dept_no", costParaAssSetData.getDept_no());
		mode.addAttribute("dept_name", costParaAssSetData.getDept_name());
		mode.addAttribute("server_dept_id", costParaAssSetData.getServer_dept_id());
		mode.addAttribute("server_dept_name", costParaAssSetData.getServer_dept_name());
		mode.addAttribute("server_dept_no", costParaAssSetData.getServer_dept_no());
		mode.addAttribute("cost_item_id", costParaAssSetData.getCost_item_id());
		mode.addAttribute("cost_item_no", costParaAssSetData.getCost_item_no());
		mode.addAttribute("cost_item_name", costParaAssSetData.getCost_item_name());
		mode.addAttribute("para_code", costParaAssSetData.getPara_code());
		mode.addAttribute("para_name", costParaAssSetData.getPara_name());
		mode.addAttribute("para_value", costParaAssSetData.getPara_value());
		mode.addAttribute("total_value", costParaAssSetData.getTotal_value());
		return "hrp/cost/costparaasssetdata/costParaAssSetDataUpdate";
	}
	/**
	*医辅分摊设置采集数据表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/updateCostParaAssSetData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaAssSetData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costParaAssSetDataJson = costParaAssSetDataService.updateCostParaAssSetData(mapVo);
		
		return JSONObject.parseObject(costParaAssSetDataJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/costParaAssSetDataImportPage", method = RequestMethod.GET)
	public String costParaAssSetDataImportPage(Model mode) throws Exception {

		return "hrp/cost/costparaasssetdata/costParaAssSetDataImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costparaasssetdata/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\对应目录","对应字典.xls");
	    return null; 
	 }
	 
	/**
	*医辅分摊设置采集数据表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costparaasssetdata/readCostParaAssSetDataFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
    	List<CostParaAssSetData> list2 = new ArrayList<CostParaAssSetData> ();  
    	List<String[]> list = UploadUtil.readFile(plupload, request, response);
    	List<CostParaAssSetData> errorList = new ArrayList<CostParaAssSetData>();
		for (int i = 1; i < list.size(); i++) {
			String temp[] = list.get(i);//行
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//模版生成,根据不同字典处理业务
			if("".equals(temp[0]) || "".equals(temp[1])){
				CostParaAssSetData costParaAssSetData = new CostParaAssSetData();
				
					costParaAssSetData.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					costParaAssSetData.setHos_id(Long.getLong(SessionManager.getHosId()));
					costParaAssSetData.setCopy_code(SessionManager.getCopyCode());
					costParaAssSetData.setYear_month(temp[3]);
					costParaAssSetData.setDept_id(Long.getLong(temp[4]));
					costParaAssSetData.setDept_no(Long.getLong(temp[5]));
					costParaAssSetData.setServer_dept_id(Long.getLong(temp[6]));
					costParaAssSetData.setServer_dept_no(Long.getLong(temp[7]));
					costParaAssSetData.setCost_item_id(Long.getLong(temp[8]));
					costParaAssSetData.setCost_item_no(Long.getLong(temp[9]));
					costParaAssSetData.setPara_code(temp[10]);
					costParaAssSetData.setPara_value(Double.valueOf(temp[11]));
					costParaAssSetData.setTotal_value(Double.valueOf(temp[12]));
				costParaAssSetData.setError_type("数据表列存在空的数据列");
				errorList.add(costParaAssSetData);
			}else{
				//需要转换各别列 通过SessionManager 里面获取
					mapVo.put("group_id", SessionManager.getGroupId());
				
					mapVo.put("hos_id", SessionManager.getHosId());
				
					mapVo.put("copy_code", SessionManager.getCopyCode());
				
					mapVo.put("year_month",temp[3]);
				
					mapVo.put("dept_id",temp[4]);
				
					mapVo.put("dept_no",temp[5]);
				
					mapVo.put("server_dept_id",temp[6]);
				
					mapVo.put("server_dept_no",temp[7]);
				
					mapVo.put("cost_item_id",temp[8]);
				
					mapVo.put("cost_item_no",temp[9]);
				
					mapVo.put("para_code",temp[10]);
				
					mapVo.put("para_value",temp[11]);
				
					mapVo.put("total_value",temp[12]);
				
			}
			try {
				CostParaAssSetData data = new CostParaAssSetData();
				data = costParaAssSetDataService.queryCostParaAssSetDataByCode(mapVo);
					if(data != null){
					data.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					data.setHos_id(Long.getLong(SessionManager.getHosId()));
					data.setCopy_code(SessionManager.getCopyCode());
					data.setYear_month(temp[3]);
					data.setDept_id(Long.getLong(temp[4]));
					data.setDept_no(Long.getLong(temp[5]));
					data.setServer_dept_id(Long.getLong(temp[6]));
					data.setServer_dept_no(Long.getLong(temp[7]));
					data.setCost_item_id(Long.getLong(temp[8]));
					data.setCost_item_no(Long.getLong(temp[9]));
					data.setPara_code(temp[10]);
					data.setPara_value(Double.valueOf(temp[11]));
					data.setTotal_value(Double.valueOf(temp[12]));
					data.setError_type("数据在数据库中已存在");
					errorList.add(data);
					}else{
							if(i==list.size()-1){
							String dataJson = costParaAssSetDataService.addCostParaAssSetData(mapVo);
							JSONObject json = JSONObject.parseObject(dataJson);
							String flag = String.valueOf(json.get("state"));
							if(!flag.equals("true")){
								CostParaAssSetData data_error = new CostParaAssSetData();
								data_error.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_error.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_error.setCopy_code(SessionManager.getCopyCode());
								data_error.setYear_month(temp[3]);
								data_error.setDept_id(Long.getLong(temp[4]));
								data_error.setDept_no(Long.getLong(temp[5]));
								data_error.setServer_dept_id(Long.getLong(temp[6]));
								data_error.setServer_dept_no(Long.getLong(temp[7]));
								data_error.setCost_item_id(Long.getLong(temp[8]));
								data_error.setCost_item_no(Long.getLong(temp[9]));
								data_error.setPara_code(temp[10]);
								data_error.setPara_value(Double.valueOf(temp[11]));
								data_error.setTotal_value(Double.valueOf(temp[12]));
								data_error.setError_type("导入失败");
								errorList.add(data_error);
								}
							}
					}
			} catch (DataAccessException e) {
				CostParaAssSetData data_exc = new CostParaAssSetData();
								data_exc.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_exc.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_exc.setCopy_code(SessionManager.getCopyCode());
								data_exc.setYear_month(temp[3]);
								data_exc.setDept_id(Long.getLong(temp[4]));
								data_exc.setDept_no(Long.getLong(temp[5]));
								data_exc.setServer_dept_id(Long.getLong(temp[6]));
								data_exc.setServer_dept_no(Long.getLong(temp[7]));
								data_exc.setCost_item_id(Long.getLong(temp[8]));
								data_exc.setCost_item_no(Long.getLong(temp[9]));
								data_exc.setPara_code(temp[10]);
								data_exc.setPara_value(Double.valueOf(temp[11]));
								data_exc.setTotal_value(Double.valueOf(temp[12]));
				data_exc.setError_type("导入系统出错");
				errorList.add(data_exc);
			}
		}
		list2.addAll(errorList);
		mode.addAttribute("resultsJson",  ChdJson.toJson(list2, list2.size()));
	    	return "hrp/cost/costparaasssetdata/costparaasssetdataImportMessage";
    }  
	/**
	*医辅分摊设置采集数据表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costparaasssetdata/addBatchCostParaAssSetData", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostParaAssSetData(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("year_month",ids[3]);   
			mapVo.put("dept_id",ids[4]);   
			mapVo.put("dept_no",ids[5]);   
			mapVo.put("server_dept_id",ids[6]);   
			mapVo.put("server_dept_no",ids[7]);   
			mapVo.put("cost_item_id",ids[8]);   
			mapVo.put("cost_item_no",ids[9]);   
			mapVo.put("para_code",ids[10]);   
			mapVo.put("para_value",ids[11]);   
			mapVo.put("total_value",ids[12]); 
            listVo.add(mapVo);
        }
		String costParaAssSetDataJson = costParaAssSetDataService.addBatchCostParaAssSetData(listVo);
	    return JSONObject.parseObject(costParaAssSetDataJson);
    }
}
