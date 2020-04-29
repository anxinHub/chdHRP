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
import com.chd.hrp.sys.entity.Unit;
import com.chd.hrp.sys.serviceImpl.UnitServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class UnitController extends BaseController{
	private static Logger logger = Logger.getLogger(UnitController.class);
	
	
	@Resource(name = "unitService")
	private final UnitServiceImpl unitService = null;
   
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/unit/unitMainPage", method = RequestMethod.GET)
	public String unitMainPage(Model mode) throws Exception {

		return "hrp/sys/unit/unitMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/unit/unitAddPage", method = RequestMethod.GET)
	public String unitAddPage(Model mode) throws Exception {

		return "hrp/sys/unit/unitAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/unit/addUnit", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		String unitJson = unitService.addUnit(mapVo);

		return JSONObject.parseObject(unitJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/unit/queryUnit", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String unit = unitService.queryUnit(getPage(mapVo));

		return JSONObject.parseObject(unit);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/unit/deleteUnit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUnit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("unit_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String unitJson = unitService.deleteBatchUnit(listVo);
	   return JSONObject.parseObject(unitJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/unit/unitUpdatePage", method = RequestMethod.GET)
	
	public String unitUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
        Unit unit = new Unit();
		unit = unitService.queryUnitByCode(mapVo);
		mode.addAttribute("group_id", unit.getGroup_id());
		mode.addAttribute("hos_id", unit.getHos_id());
		mode.addAttribute("unit_code", unit.getUnit_code());
		mode.addAttribute("unit_name", unit.getUnit_name());
		mode.addAttribute("is_stop", unit.getIs_stop());
		mode.addAttribute("spell_code", unit.getSpell_code());
		mode.addAttribute("wbx_code", unit.getWbx_code());
		mode.addAttribute("note", unit.getNote());
		
		return "hrp/sys/unit/unitUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/unit/updateUnit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String unitJson = unitService.updateUnit(mapVo);
		
		return JSONObject.parseObject(unitJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/unit/importUnit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String unitJson = unitService.importUnit(mapVo);
		return JSONObject.parseObject(unitJson);
	}
	
	@RequestMapping(value = "/hrp/sys/unit/unitImportPage", method = RequestMethod.GET)
	public String unitImportPage(Model mode) throws Exception {

		return "hrp/sys/unit/unitImport";
	}

	@RequestMapping(value = "hrp/sys/unit/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "计量单位.xls");
		return null;
	}
	
	// 导入
	@RequestMapping(value="/hrp/sys/unit/readUnitFiles",method = RequestMethod.POST)  
    public String readUnitFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		
		
		List<Unit> list_err = new ArrayList<Unit>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Unit Unit = new Unit();
				
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
						
						Unit.setUnit_code(temp[0]);
						mapVo.put("unit_code", temp[0]);
					
					} else {
						
						err_sb.append("计量编码为空  ");
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
					Unit.setUnit_name(temp[1]);
		    		mapVo.put("unit_name", temp[1]);
					
					} else {
						
						err_sb.append("计量名称为空  ");
					}
					 
					
					 
					if (StringTool.isNotBlank(temp[2])) {
						
					Unit.setIs_stop(Integer.valueOf(temp[2]));
		    		mapVo.put("is_stop", temp[2]);
					
					} else {
						
						err_sb.append("是否停用为空  ");
					}
					
					if(temp.length - 1 >= 3){
						
						if (StringTool.isNotBlank(temp[3])) {
							
							Unit.setNote(temp[3]);
				    		mapVo.put("note", temp[3]);
							
							} 
						
					}else{
						
						Unit.setNote("");
						
			    		mapVo.put("note", "");
						
					}

					
					Map<String, Object> byCodeMap = new HashMap<String, Object>();

					byCodeMap.put("group_id", mapVo.get("group_id"));

					byCodeMap.put("hos_id", mapVo.get("hos_id"));

					byCodeMap.put("copy_code", mapVo.get("copy_code"));

					byCodeMap.put("unit_code", mapVo.get("unit_code"));
					
				Unit data_exc_extis = unitService.queryUnitByCode(byCodeMap);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
				}
			
				if (err_sb.toString().length() > 0) {
					
					Unit.setError_type(err_sb.toString());
					
					list_err.add(Unit);
					
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("unit_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("unit_name").toString()));
					String dataJson = unitService.addUnit(mapVo);
				}
			}
			
		} catch (DataAccessException e) {
			
			Unit data_exc = new Unit();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
	}
	
	//导入保存
			@RequestMapping(value = "/hrp/sys/unit/addImportUnit", method = RequestMethod.POST)
			@ResponseBody
		    public Map<String, Object> addImportUnit(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				List<Unit> list_err = new ArrayList<Unit>();
				
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
					
					Unit Unit = new Unit();
					
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					
							if (StringTool.isNotBlank(jsonObj.get("unit_code"))) {
								
							Unit.setUnit_code((String)jsonObj.get("unit_code"));
				    		mapVo.put("unit_code", jsonObj.get("unit_code"));
				    		} else {
								
								err_sb.append("计量编码为空  ");
							}
							
							if (StringTool.isNotBlank(jsonObj.get("unit_name"))) {
								
							Unit.setUnit_name((String)jsonObj.get("unit_name"));
				    		mapVo.put("unit_name", jsonObj.get("unit_name"));
				    		} else {
								
								err_sb.append("计量名称为空  ");
							}
						
							if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
								
							Unit.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
				    		mapVo.put("is_stop", Integer.parseInt(jsonObj.get("is_stop").toString()));
				    		} else {
								
								err_sb.append("是否停用为空  ");
							}
							
							if (StringTool.isNotBlank(jsonObj.get("note"))) {
								
							Unit.setNote((String)jsonObj.get("note"));
				    		mapVo.put("note", jsonObj.get("note"));
				    		} else {
								
								err_sb.append("备注为空  ");
							}
							
							
						Unit data_exc_extis = unitService.queryUnitByCode(mapVo);
						
						if (data_exc_extis != null) {
							
							err_sb.append("编码已经存在！ ");
						}
						if (err_sb.toString().length() > 0) {
							
							Unit.setError_type(err_sb.toString());
							
							list_err.add(Unit);
							
						} else {
							mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("unit_name").toString()));
							mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("unit_name").toString()));
							String dataJson = unitService.addUnit(mapVo);
						}
					}
					
				} catch (DataAccessException e) {
					
					Unit data_exc = new Unit();
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

