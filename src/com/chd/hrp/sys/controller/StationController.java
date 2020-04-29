/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.Station;
import com.chd.hrp.sys.serviceImpl.StationServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class StationController extends BaseController{
	private static Logger logger = Logger.getLogger(StationController.class);
	
	
	@Resource(name = "stationService")
	private final StationServiceImpl stationService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/station/stationMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {

		return "hrp/sys/station/stationMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/station/stationAddPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/sys/station/stationAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/station/addStation", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addStation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String stationJson = stationService.addStation(mapVo);

		return JSONObject.parseObject(stationJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/station/queryStation", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryStation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String station = stationService.queryStation(getPage(mapVo));

		return JSONObject.parseObject(station);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/station/deleteStation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStation(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("station_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String stationJson = stationService.deleteBatchStation(listVo);
	   return JSONObject.parseObject(stationJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/station/stationUpdatePage", method = RequestMethod.GET)
	
	public String stationUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Station station = new Station();
		station = stationService.queryStationByCode(mapVo);
		mode.addAttribute("group_id", station.getGroup_id());
		mode.addAttribute("hos_id", station.getHos_id());
		mode.addAttribute("station_code", station.getStation_code());
		mode.addAttribute("station_name", station.getStation_name());
		mode.addAttribute("spell_code", station.getSpell_code());
		mode.addAttribute("wbx_code", station.getWbx_code());
		mode.addAttribute("is_stop", station.getIs_stop());
		mode.addAttribute("note", station.getNote());
		
		return "hrp/sys/station/stationUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/station/updateStation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String stationJson = stationService.updateStation(mapVo);
		
		return JSONObject.parseObject(stationJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/station/importStation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importStation(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String stationJson = stationService.importStation(mapVo);
		
		return JSONObject.parseObject(stationJson);
	}

	
	// 下载导入模版
	@RequestMapping(value = "hrp/sys/station/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "sys\\医院信息", "职工岗位.xls");
		return null;
	}
	
	
	@RequestMapping(value = "/hrp/sys/station/sysStationImportPage", method = RequestMethod.GET)
	public String sysStationImportPage(Model mode) throws Exception {

		return "hrp/sys/station/stationImport";

	}
	
	/**
	 * 导入职工学历<BR>
	 * 
	 */
	@RequestMapping(value = "/hrp/sys/station/readSysStationFiles", method = RequestMethod.POST)
	public String readSysStationFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
		
		
		
		List<Station> list_err = new ArrayList<Station>();
		
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Station station = new Station();
				
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
				
				
				if(ExcelReader.validExceLColunm(temp, 0)){
					
					station.setStation_code(temp[0].toString());
					
					mapVo.put("station_code", temp[0]);
					
				}else {
					
					err_sb.append("岗位编码不能为空");
					
				}
				
				if(ExcelReader.validExceLColunm(temp, 1)){
					
					station.setStation_name(temp[1].toString());
					
					mapVo.put("station_name", temp[1]);
					
				}else {
					
					err_sb.append("岗位名称不能为空");
					
				}
				
                if(ExcelReader.validExceLColunm(temp, 2)){
					
					station.setIs_stop(Integer.parseInt(temp[2]));
					
					mapVo.put("is_stop", temp[2]);
					
				}else {
					
					err_sb.append("是否停用不能为空");
					
				}
                
                if(ExcelReader.validExceLColunm(temp, 3)){
					
					station.setNote(temp[3]);
					
					mapVo.put("note", temp[3]);
					
				}else {
					
                    station.setNote(null);
					
					mapVo.put("note", null);
					
				}
                
                Station station2 =  stationService.queryStationByCode(mapVo);
                
                if(station2 != null){
                	
                	err_sb.append("编码重复");
                }
				
                if (err_sb.toString().length() > 0) {
					
                	station.setError_type(err_sb.toString());
					
					list_err.add(station);
					
				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("station_name").toString()));
					
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("station_name").toString()));
					
					addList.add(mapVo);
					
				}
				
			}
			
			if(addList.size()> 0 ){
				
				stationService.addBatchStation(addList);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Station data_exc = new Station();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
	}
	
	
	@RequestMapping(value = "/hrp/sys/station/addBatchSysStation", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSysStation(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> addList = new ArrayList<Map<String,Object>>();
		
		List<Station> list_err = new ArrayList<Station>();
		
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
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Station station = new Station();
				
				if (StringTool.isNotBlank(jsonObj.get("station_code"))) {

					station.setStation_code(jsonObj.get("station_code").toString());
					
					mapVo.put("station_code", jsonObj.get("station_code"));
					
				} else {

					err_sb.append("岗位编码为空");

				}
				
				if (StringTool.isNotBlank(jsonObj.get("station_name"))) {

					station.setStation_name(jsonObj.get("station_name").toString());
					
					mapVo.put("station_name", jsonObj.get("station_name"));
					
				} else {

					err_sb.append("岗位名称为空");

				}
				
				
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					station.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
					
					mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
					
				} else {

					err_sb.append("是否停用为空");

				}
				
				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					station.setNote(jsonObj.get("note").toString());
					
					mapVo.put("note", jsonObj.get("note").toString());
					
				} else {

	                station.setNote(null);
					
					mapVo.put("note", null);

				}
				
				
			       Station station2 =  stationService.queryStationByCode(mapVo);
	                
	                if(station2 != null){
	                	
	                	err_sb.append("编码重复");
	                }
					
	                if (err_sb.toString().length() > 0) {
						
	                	station.setError_type(err_sb.toString());
						
						list_err.add(station);
						
					} else {
						
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("station_name").toString()));
						
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("station_name").toString()));
						
						addList.add(mapVo);
						
					}
				
			}
			
             	if(addList.size()> 0 ){
				
				stationService.addBatchStation(addList);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			// TODO: handle exception
	      	Station data_exc = new Station();
			
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

