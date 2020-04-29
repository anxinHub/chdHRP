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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.entity.EmpKind;
import com.chd.hrp.sys.serviceImpl.EmpKindServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class EmpKindController extends BaseController{
	private static Logger logger = Logger.getLogger(EmpKindController.class);
	
	
	@Resource(name = "empKindService")
	private final EmpKindServiceImpl empKindService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/empkind/empKindMainPage", method = RequestMethod.GET)
	public String empKindMainPage(Model mode) throws Exception {

		return "hrp/sys/empkind/empKindMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/empkind/empKindAddPage", method = RequestMethod.GET)
	public String empKindAddPage(Model mode) throws Exception {

		return "hrp/sys/empkind/empKindAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/empkind/addEmpKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String empKindJson = empKindService.addEmpKind(mapVo);

		return JSONObject.parseObject(empKindJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/empkind/queryEmpKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String empKind = empKindService.queryEmpKind(getPage(mapVo));

		return JSONObject.parseObject(empKind);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/empkind/deleteEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpKind(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
            mapVo.put("kind_code", id.split("@")[2]);
            listVo.add(mapVo);
        }
		String empKindJson = empKindService.deleteBatchEmpKind(listVo);
	   return JSONObject.parseObject(empKindJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/empkind/empKindUpdatePage", method = RequestMethod.GET)
	
	public String empKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        EmpKind empKind = new EmpKind();
		empKind = empKindService.queryEmpKindByCode(mapVo);
		mode.addAttribute("group_id", empKind.getGroup_id());
		mode.addAttribute("hos_id", empKind.getHos_id());
		mode.addAttribute("kind_code", empKind.getKind_code());
		mode.addAttribute("kind_name", empKind.getKind_name());
		mode.addAttribute("spell_code", empKind.getSpell_code());
		mode.addAttribute("wbx_code", empKind.getWbx_code());
		mode.addAttribute("is_stop", empKind.getIs_stop());
		mode.addAttribute("note", empKind.getNote());
		
		return "hrp/sys/empkind/empKindUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/empkind/updateEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String empKindJson = empKindService.updateEmpKind(mapVo);
		
		return JSONObject.parseObject(empKindJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/empkind/importEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String empKindJson = empKindService.importEmpKind(mapVo);
		
		return JSONObject.parseObject(empKindJson);
	}
	
	
	@RequestMapping(value = "/hrp/sys/empkind/empKindImportPage", method = RequestMethod.GET)
	public String empKindImportPage(Model mode) throws Exception {

		return "hrp/sys/empkind/empKindImport";

	}

	@RequestMapping(value = "hrp/sys/empkind/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sys\\医院信息", "职工分类.xls");

		return null;
	}

	/**
	 * 职工分类<BR>
	 * 导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/sys/empkind/readEmpKindFiles", method = RequestMethod.POST)
	public String readEmpKindFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws Exception {

		List<EmpKind> list_err = new ArrayList<EmpKind>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {

			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				EmpKind empKind = new EmpKind();

				String temp[] = list.get(i);// 行
				
				
/*               if(temp[i].length() == 0){
            	   
            	   continue;
               }*/

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}
				

				if (validExceColunm(temp,0)) {

					mapVo.put("kind_code", temp[0]);

					empKind.setKind_code(temp[0]);

				} else {

					err_sb.append("职工分类编码为空");
				}

				if (validExceColunm(temp,1)) {

					mapVo.put("kind_name", temp[1]);

					empKind.setKind_name(temp[1]);

				} else {

					err_sb.append("职工分类名称为空");
				}

				if (validExceColunm(temp,2)) {

					mapVo.put("is_stop", temp[2]);

					empKind.setIs_stop(Integer.parseInt(temp[2].toString()));

				} else {

					err_sb.append("是否停用为空");
				}



					if (validExceColunm(temp,3)) {

						mapVo.put("note", temp[3]);

						empKind.setNote(temp[3]);

					}else{
						
						mapVo.put("note","");

						empKind.setNote("");
					}



				EmpKind eKind = empKindService.queryEmpKindByCode(mapVo);

				if (eKind != null) {

					err_sb.append("职工类别编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					empKind.setError_type(err_sb.toString());

					list_err.add(empKind);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo
							.get("kind_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get(
							"kind_name").toString()));

					empKindService.addEmpKind(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			EmpKind data_exc = new EmpKind();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	@RequestMapping(value = "/hrp/sys/empkind/addImportEmpKindDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addImportEmpKindDict(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<EmpKind> list_err = new ArrayList<EmpKind>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				StringBuffer err_sb = new StringBuffer();

				EmpKind empKind = new EmpKind();

				Map<String, Object> mapVo = new HashMap<String, Object>();

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}

				if (StringTool.isNotBlank(jsonObj.get("kind_code"))) {

					mapVo.put("kind_code", jsonObj.get("kind_code").toString());

					empKind.setKind_code(jsonObj.get("kind_code").toString());

				} else {

					err_sb.append("职工类别编码为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("kind_name"))) {

					mapVo.put("kind_name", jsonObj.get("kind_name").toString());

					empKind.setKind_name(jsonObj.get("kind_name").toString());

				} else {

					err_sb.append("职工类别名称为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					mapVo.put("is_stop",
							Integer.parseInt(jsonObj.get("is_stop").toString()));

					empKind.setIs_stop(Integer.parseInt(jsonObj.get("is_stop")
							.toString()));

				} else {

					err_sb.append("是否停用为空");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					mapVo.put("note", jsonObj.get("note").toString());

					empKind.setNote(jsonObj.get("note").toString());

				} else {

					mapVo.put("note", "");

					empKind.setNote("");
				}

				EmpKind  eEmpKind = empKindService.queryEmpKindByCode(mapVo);

				if (eEmpKind != null) {

					err_sb.append("职工类别编码已经存在");
				}

				if (err_sb.toString().length() > 0) {

					empKind.setError_type(err_sb.toString());

					list_err.add(empKind);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));

					empKindService.addEmpKind(mapVo);
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			return JSONObject
					.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err,
					list_err.size()));

		} else {
			return JSONObject
					.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
	
	public static boolean validExceColunm(String[] arr, int colunmNum) {

		if (arr.length - 1 >= colunmNum) {

			if (StringTool.isNotBlank(arr[colunmNum]) || arr[colunmNum].length()!=0) {

				return true;
				
			} else {
				
				return false;
			}

		} else {

			return false;
		}

	}
}

