/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostItemDict;
import com.chd.hrp.cost.serviceImpl.CostCustomDeptServiceImpl;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.entity.DeptKind;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptKindServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptServiceImpl;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostCustomDeptController extends BaseController {

	private static Logger logger = Logger.getLogger(CostCustomDeptController.class);

	@Resource(name = "costCustomDeptService")
	private final CostCustomDeptServiceImpl costCustomDeptService = null;

	@Resource(name = "deptKindService")
	private final DeptKindServiceImpl deptKindService = null;


	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/cost/costcustomdept/deptMainPage", method = RequestMethod.GET)
	public String deptMainPage(Model mode) throws Exception {

		return "hrp/cost/costcustomdept/deptMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/cost/costcustomdept/deptAddPage", method = RequestMethod.GET)
	public String deptAddPage(Model mode) throws Exception {

		return "hrp/cost/costcustomdept/deptAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/cost/costcustomdept/addCostCustomDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String deptJson = costCustomDeptService.add(mapVo);

		return JSONObject.parseObject(deptJson);

	}


	// 查询
	@RequestMapping(value = "/hrp/cost/costcustomdept/queryCostCustomDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String dept = costCustomDeptService.query(getPage(mapVo));

		return JSONObject.parseObject(dept);

	}

	// 删除
	@RequestMapping(value = "/hrp/cost/costcustomdept/deleteCostCustomDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteDept(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String deptJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("dept_code", id.split("@")[2]);
			mapVo.put("dept_id", id.split("@")[3]);
			listVo.add(mapVo);
		}

		deptJson = costCustomDeptService.deleteBatch(listVo);
		return JSONObject.parseObject(deptJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/cost/costcustomdept/deptUpdatePage", method = RequestMethod.GET)
	public String deptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map = costCustomDeptService.queryByCode(mapVo);
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("dept_id", map.get("dept_id"));
		mode.addAttribute("dept_code", map.get("dept_code"));
		mode.addAttribute("kind_code", map.get("kind_code"));
		mode.addAttribute("kind_name", map.get("kind_name"));
		mode.addAttribute("dept_name", map.get("dept_name"));
		mode.addAttribute("is_stop", map.get("is_stop"));
		mode.addAttribute("spell_code", map.get("spell_code"));
		mode.addAttribute("wbx_code", map.get("wbx_code"));
		mode.addAttribute("note", map.get("note"));

		return "hrp/cost/costcustomdept/deptUpdate";
	}

	@RequestMapping(value = "/hrp/cost/costcustomdept/queryDeptByCode", method = RequestMethod.POST)
	public Map<String, Object> queryDeptByCode(@RequestParam Map<String, Object> mapVo, Model mode, HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map = costCustomDeptService.queryByCode(mapVo);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("dept", map);
		response.getWriter().print(jsonObj.toString());
		return null;
	}

	// 修改保存
	@RequestMapping(value = "/hrp/cost/costcustomdept/updateCostCustomDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String deptJson = costCustomDeptService.update(mapVo);

		return JSONObject.parseObject(deptJson); 
	}

	@RequestMapping(value = "/hrp/cost/costcustomdept/deptImportPage", method = RequestMethod.GET)
	public String deptImportPage(Model mode) throws Exception {

		return "hrp/cost/costcustomdept/deptImport";

	}

	@RequestMapping(value = "hrp/cost/costcustomdept/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "成本自定义科室.xls");

		return null;
	}

	/**
	 *导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/cost/costcustomdept/readDeptFiles", method = RequestMethod.POST)
	public String readDeptFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws Exception {
		

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", SessionManager.getGroupId());

		map.put("hos_id", SessionManager.getHosId());

		map.put("copy_code", SessionManager.getCopyCode());
		
		// 科室类别编码数据  校验科室类别编码
		List<Map<String, Object>> listKindDate = costCustomDeptService.queryDeptKindDate(map) ;

		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				Map<String, Object> errMap = new HashMap<String, Object>();

				String temp[] = list.get(i);// 行
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0])){
						err_sb.append("第"+i+"行数据与第 "+j+"行【部门编码】重复;");
					}
					
					if(temp[1].equals(error[1])){
						err_sb.append("第"+i+"行数据与第 "+j+"行【部门名称】重复;");
					}
				}

				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (ExcelReader.validExceLColunm(temp, 0) ) {
					errMap.put("dept_code", temp[0]);
					mapVo.put("dept_code", temp[0]);

				} else {

					err_sb.append("部门编码为空;");

				}

				if (ExcelReader.validExceLColunm(temp,1)) {
					
					errMap.put("dept_name", temp[1]);
					
					mapVo.put("dept_name", temp[1]);

				} else {
					err_sb.append("部门名称为空;");
				}
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					int flag = 0 ; 
					
					for(int  m= 0 ; m<listKindDate.size() ; m++){
						
						if(listKindDate.get(m).get("kind_code").equals(temp[2])){
							
							mapVo.put("kind_code", temp[2]);
							
							flag =  1 ;
							
							break ;
						}
						
					}
					errMap.put("kind_code", temp[2]);
					
					if(flag == 0){
						err_sb.append("部门分类编码输入不合法！");
					}
					

				} else {
					err_sb.append("部门分类编码为空;");
				}


				if (ExcelReader.validExceLColunm(temp,3)) {
					errMap.put("is_stop", temp[3]);
					mapVo.put("is_stop", temp[3]);
				} else {
					err_sb.append("是否停用为空  ");
				}
				
				if (ExcelReader.validExceLColunm(temp,4)) {
					errMap.put("note", temp[4]);
					mapVo.put("note", temp[4]);
				} else {
					errMap.put("note", "");
					mapVo.put("note", "");
				}
				
				

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("dept_name", mapVo.get("dept_name"));

				List<Map<String,Object>> data_exc_extis = costCustomDeptService.queryDeptById(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis.size() >0 ) {

					err_sb.append("科室编码或科室名称已经存在！");

				}

				if (err_sb.toString().length() > 0) {

					errMap.put("error_type",err_sb.toString());

					list_err.add(errMap);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("dept_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("dept_name").toString()));

					addList.add(mapVo);
					//costCustomdeptService.add(mapVo);
				}
			}
			
			if (addList.size() > 0 && list_err.size() == 0) {

				costCustomDeptService.addBatch(addList);

			}
		}catch (DataAccessException e) {

			Map<String,Object> errMap = new HashMap<String,Object>();

			errMap.put("error_type","导入系统出错");

			list_err.add(errMap);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}
}
