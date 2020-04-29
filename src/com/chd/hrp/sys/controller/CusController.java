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
import com.chd.hrp.sys.entity.Cus;
import com.chd.hrp.sys.entity.CusType;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.CusDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.CusServiceImpl;
import com.chd.hrp.sys.serviceImpl.CusTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CusController extends BaseController{
	private static Logger logger = Logger.getLogger(CusController.class);
	
	
	@Resource(name = "cusService")
	private final CusServiceImpl cusService = null;
   
	@Resource(name = "cusDictService")
	private final CusDictServiceImpl cusDictService = null;
    
	@Resource(name = "cusTypeService")
	private final CusTypeServiceImpl cusTypeService=null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/cus/cusMainPage", method = RequestMethod.GET)
	public String cusMainPage(Model mode) throws Exception {

		return "hrp/sys/cus/cusMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/cus/cusAddPage", method = RequestMethod.GET)
	public String cusAddPage(Model mode) throws Exception {

		return "hrp/sys/cus/cusAdd";

	}
	
	@RequestMapping(value = "/hrp/sys/cus/cusChangePage", method = RequestMethod.GET)
	public String cusChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Cus cus = new Cus();
		cus = cusService.queryCusByCode(mapVo);
		mode.addAttribute("group_id", cus.getGroup_id());
		mode.addAttribute("hos_id", cus.getHos_id());
		mode.addAttribute("cus_id", cus.getCus_id());
		mode.addAttribute("cus_code", cus.getCus_code());
		mode.addAttribute("type_code", cus.getType_code());
		mode.addAttribute("type_name", cus.getType_name());
		mode.addAttribute("cus_name", cus.getCus_name());
		mode.addAttribute("sort_code", cus.getSort_code());
		mode.addAttribute("spell_code", cus.getSpell_code());
		mode.addAttribute("wbx_code", cus.getWbx_code());
		mode.addAttribute("is_stop", cus.getIs_stop());
		mode.addAttribute("note", cus.getNote());
		
		return "hrp/sys/cus/cusChange";
	}

	// 保存
	@RequestMapping(value = "/hrp/sys/cus/addCus", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String cusJson = cusService.addCus(mapVo);

		return JSONObject.parseObject(cusJson);
		
	}
	
	@RequestMapping(value = "/hrp/sys/cus/addCusDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String cusJson = cusDictService.addCusDict(mapVo);

		return JSONObject.parseObject(cusJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/cus/queryCus", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String cus = cusService.queryCus(getPage(mapVo));

		return JSONObject.parseObject(cus);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/cus/deleteCus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCus(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String cusJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("cus_id", id.split("@")[2]);
            mapVo.put("cus_code", id.split("@")[3]);
            listVo.add(mapVo);
           
            
        }
		 cusJson = cusService.deleteBatchCus(listVo);
	   return JSONObject.parseObject(cusJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/cus/cusUpdatePage", method = RequestMethod.GET)
	
	public String cusUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Cus cus = new Cus();
		cus = cusService.queryCusByCode(mapVo);
		mode.addAttribute("group_id", cus.getGroup_id());
		mode.addAttribute("hos_id", cus.getHos_id());
		mode.addAttribute("cus_id", cus.getCus_id());
		mode.addAttribute("cus_code", cus.getCus_code());
		mode.addAttribute("type_code", cus.getType_code());
		mode.addAttribute("type_name", cus.getType_name());
		mode.addAttribute("cus_name", cus.getCus_name());
		mode.addAttribute("sort_code", cus.getSort_code());
		mode.addAttribute("spell_code", cus.getSpell_code());
		mode.addAttribute("wbx_code", cus.getWbx_code());
		mode.addAttribute("is_stop", cus.getIs_stop());
		mode.addAttribute("is_disable", cus.getIs_disable());
		mode.addAttribute("note", cus.getNote());
		mode.addAttribute("is_mat", cus.getIs_mat());
		mode.addAttribute("is_ass", cus.getIs_ass());
		mode.addAttribute("is_med", cus.getIs_med());
		mode.addAttribute("is_sup", cus.getIs_sup());
		
		return "hrp/sys/cus/cusUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/cus/updateCus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCus(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String cusJson = cusService.updateCus(mapVo);
		
		return JSONObject.parseObject(cusJson);
	}
	
	@RequestMapping(value = "/hrp/sys/cus/cusImportPage", method = RequestMethod.GET)
	public String cusImportPage(Model mode) throws Exception {

		return "hrp/sys/cus/cusImport";
	}

	@RequestMapping(value = "hrp/sys/cus/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "客户维护.xls");
		return null;
	}

	// 导入
	@RequestMapping(value="/hrp/sys/cus/readCusFiles",method = RequestMethod.POST)  
    public String readCusFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("proj_code", "HOS_CUS");
		entityMap.put("mod_code", "00");

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String s_view = Strings.removeFirst(rules_v); 
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Cus> list_err = new ArrayList<Cus>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				Cus Cus = new Cus();
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
					Object cus_code = temp[0].length();
					if (cus_code !=  rules_level_length.get(1)) {
						err_sb.append("编码不符合要求,请重新添加.编码规则长度："+rules_level_length.get(1));
					}	
					Cus.setCus_code(temp[0]);
					mapVo.put("cus_code", temp[0]);
				} else {
					err_sb.append("客户编码为空  ");
				}
					
				if (StringTool.isNotBlank(temp[1])) {
					Cus.setCus_name(temp[1]);
		    		mapVo.put("cus_name", temp[1]);
				} else {
					err_sb.append("客户名称为空  ");
				}
					 
				if (StringTool.isNotBlank(temp[2])) {
					Cus.setType_code(temp[2]);
		    		mapVo.put("type_code", temp[2]);
				} else {
					err_sb.append("类型编码为空  ");
				}
					
				if (StringTool.isNotBlank(temp[3])) {
					Cus.setType_name(temp[3]);
		    		mapVo.put("type_name", temp[3]);
				} else {
					err_sb.append("类型名称为空  ");
				}
					 
				mapVo.put("sort_code", "系统生成");
				if (StringTool.isNotBlank(temp[4])) {
					Cus.setIs_mat(Integer.valueOf(temp[4]));
		    		mapVo.put("is_mat", temp[4]);
				} else {
					err_sb.append("物流管理为空  ");
				}
				
				if (StringTool.isNotBlank(temp[5])) {
					Cus.setIs_ass(Integer.valueOf(temp[5]));
		    		mapVo.put("is_ass", temp[5]);
				} else {
					err_sb.append("固定资产为空  ");
				}
				
				if (StringTool.isNotBlank(temp[6])) {
					Cus.setIs_med(Integer.valueOf(temp[6]));
		    		mapVo.put("is_med", temp[6]);
				} else {
					err_sb.append("药品管理为空  ");
				}
					
				if (StringTool.isNotBlank(temp[7])) {
					Cus.setIs_sup(Integer.valueOf(temp[7]));
		    		mapVo.put("is_sup", temp[7]);
				} else {
					err_sb.append("供应商平台为空  ");
				}
					
				if (StringTool.isNotBlank(temp[8])) {
					Cus.setIs_stop(Integer.valueOf(temp[8]));
		    		mapVo.put("is_stop", temp[8]);
				} else {
					err_sb.append("是否停用为空  ");
				}
					 
				if (StringTool.isNotBlank(temp[9])) {
					Cus.setNote(temp[9]);
		    		mapVo.put("note", temp[9]);
				} else {
					err_sb.append("备注为空  ");
				}
					
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				byCodeMap.put("group_id", mapVo.get("group_id"));
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				byCodeMap.put("cus_code", mapVo.get("cus_code"));
				
				Cus data_exc_extis = cusService.queryCusByCode(byCodeMap);
				if (data_exc_extis != null) {
					err_sb.append("数据已经存在！ ");
				}
				
				byCodeMap.put("type_code", mapVo.get("type_code"));
				CusType Custype = cusTypeService.queryCusTypeByCode(byCodeMap);
				if (Custype == null) {
					err_sb.append("类别编码不存在  ");
				}
				
				if (err_sb.toString().length() > 0) {
					Cus.setError_type(err_sb.toString());
					list_err.add(Cus);
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cus_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cus_name").toString()));
					String dataJson = cusService.addCus(mapVo);
				}
			}
		} catch (DataAccessException e) {
			Cus data_exc = new Cus();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
	}

		//导入保存
		@RequestMapping(value = "/hrp/sys/cus/addImportCus", method = RequestMethod.POST)
		@ResponseBody
	    public Map<String, Object> addImportCus(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
			List<Cus> list_err = new ArrayList<Cus>();
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
				
				Cus Cus = new Cus();
				
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
						if (StringTool.isNotBlank(jsonObj.get("cus_code"))) {
							
						Cus.setCus_code((String)jsonObj.get("cus_code"));
			    		mapVo.put("cus_code", jsonObj.get("cus_code"));
			    		} else {
							
							err_sb.append("客户编码为空  ");
						}
						
						if (StringTool.isNotBlank(jsonObj.get("type_code"))) {
							
						Cus.setType_code((String)jsonObj.get("type_code"));
			    		mapVo.put("type_code", jsonObj.get("type_code"));
			    		} else {
							
							err_sb.append("类型编码为空  ");
						}
						
						if (StringTool.isNotBlank(jsonObj.get("type_name"))) {
							
						Cus.setType_name((String)jsonObj.get("type_name"));
			    		mapVo.put("type_name", jsonObj.get("type_name"));
			    		} else {
							
							err_sb.append("类型名称为空  ");
						}
						
						if (StringTool.isNotBlank(jsonObj.get("cus_name"))) {
							
						Cus.setCus_name((String)jsonObj.get("cus_name"));
			    		mapVo.put("cus_name", jsonObj.get("cus_name"));
			    		} else {
							
							err_sb.append("客户名称为空  ");
						}
					
						/*if (StringTool.isNotBlank(jsonObj.get("sort_code"))) {
							
						Cus.setSort_code(Long.valueOf((String)jsonObj.get("sort_code")));
			    		mapVo.put("sort_code", jsonObj.get("sort_code"));
			    		} else {
							
							err_sb.append("排序号为空  ");
						}*/
						
					/*	if (StringTool.isNotBlank(jsonObj.get("user_code"))) {
							
							Cus.setUser_code((String)jsonObj.get("user_code"));
				    		mapVo.put("user_code", jsonObj.get("user_code"));
				    		} else {
								
								err_sb.append("变更人用为空  ");
							}
						
						if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
							
							Cus.setCreate_date((Date) jsonObj.get("create_date"));
				    		mapVo.put("create_date", jsonObj.get("create_date"));
				    		} else {
								
								err_sb.append("变更时间为空  ");
							}
						if (StringTool.isNotBlank(jsonObj.get("dlog"))) {
							
							Cus.setDlog((String)jsonObj.get("dlog"));
				    		mapVo.put("dlog", jsonObj.get("dlog"));
				    		} else {
								
								err_sb.append("变更原因为空  ");
							}*/
						if (StringTool.isNotBlank(jsonObj.get("is_mat"))) {
							
							Cus.setIs_mat(Integer.valueOf((String)jsonObj.get("is_mat")));
				    		mapVo.put("is_mat", jsonObj.get("is_mat"));
				    		} else {
								
								err_sb.append("物流管理为空  ");
							}
						if (StringTool.isNotBlank(jsonObj.get("is_ass"))) {
							
							Cus.setIs_ass(Integer.valueOf((String)jsonObj.get("is_ass")));
				    		mapVo.put("is_ass", jsonObj.get("is_ass"));
				    		} else {
								
								err_sb.append("固定资产为空  ");
							}
						if (StringTool.isNotBlank(jsonObj.get("is_med"))) {
							
							Cus.setIs_med(Integer.valueOf((String)jsonObj.get("is_med")));
				    		mapVo.put("is_med", jsonObj.get("is_med"));
				    		} else {
								
								err_sb.append("药品管理为空  ");
							}
						if (StringTool.isNotBlank(jsonObj.get("is_sup"))) {
							
							Cus.setIs_sup(Integer.valueOf((String)jsonObj.get("is_sup")));
				    		mapVo.put("is_sup", jsonObj.get("is_sup"));
				    		} else {
								
								err_sb.append("供应商平台为空  ");
							}
						
						if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {
							
						Cus.setIs_stop(Integer.valueOf((String)jsonObj.get("is_stop")));
			    		mapVo.put("is_stop", jsonObj.get("is_stop"));
			    		} else {
							
							err_sb.append("是否停用为空  ");
						}
						
						if (StringTool.isNotBlank(jsonObj.get("note"))) {
							
						Cus.setNote((String)jsonObj.get("note"));
			    		mapVo.put("note", jsonObj.get("note"));
			    		} else {
							
							err_sb.append("备注为空  ");
						}
						
						
					Cus data_exc_extis = cusService.queryCusByCode(mapVo);
					
					if (data_exc_extis != null) {
						
						err_sb.append("编码已经存在！ ");
					}
					if (err_sb.toString().length() > 0) {
						
						Cus.setError_type(err_sb.toString());
						
						list_err.add(Cus);
						
					} else {
						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cus_name").toString()));
						mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cus_name").toString()));
						String dataJson = cusService.addCus(mapVo);
					}
				}
				
			} catch (DataAccessException e) {
				
				Cus data_exc = new Cus();
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

