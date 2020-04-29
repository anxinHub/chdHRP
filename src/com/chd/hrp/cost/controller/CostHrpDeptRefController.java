/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.service.CostHrpDeptRefService;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostHrpDeptRefController extends BaseController {

	private static Logger logger = Logger.getLogger(CostHrpDeptRefController.class);

	@Resource(name = "costHrpDeptRefService")
	private final CostHrpDeptRefService costHrpDeptRefService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/deptRefMainPage", method = RequestMethod.GET)
	public String deptMainPage(Model mode) throws Exception {

		return "hrp/cost/costhrpdeptref/deptRefMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/addDeptRefPage", method = RequestMethod.GET)
	public String addDeptRefPage(Model mode) throws Exception {

		return "hrp/cost/costhrpdeptref/deptRefAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/addCostHrpDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostHrpDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String deptJson = costHrpDeptRefService.add(mapVo);

		return JSONObject.parseObject(deptJson);

	}


	// 查询
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/queryCostHrpDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostHrpDeptRef(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String dept = costHrpDeptRefService.query(getPage(mapVo));

		return JSONObject.parseObject(dept);

	}

	// 删除
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/deleteCostHrpDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostHrpDeptRef(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String deptJson = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("ref_id", id.split("@")[2]);
			listVo.add(mapVo);
		}

		deptJson = costHrpDeptRefService.deleteBatch(listVo);
		return JSONObject.parseObject(deptJson);

	}
	
	// 停用
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/endCostHrpDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> endCostHrpDeptRef(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String deptJson = "";
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("ref_id", id.split("@")[2]);
			
			mapVo.put("end_date", df.format(new Date()));
			
			mapVo.put("is_stop", "1");
			
			listVo.add(mapVo);
		}

		deptJson = costHrpDeptRefService.endCostHrpDeptRef(listVo);
		return JSONObject.parseObject(deptJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/updateDeptRefPage", method = RequestMethod.GET)
	public String deptUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		DateFormat df  = new SimpleDateFormat("yyyy-MM-dd");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map = costHrpDeptRefService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", map.get("group_id"));
		mode.addAttribute("hos_id", map.get("hos_id"));
		mode.addAttribute("ref_id", map.get("ref_id"));
		mode.addAttribute("cost_custom_dept_id", map.get("cost_custom_dept_id"));
		mode.addAttribute("cost_custom_dept_code", map.get("cost_custom_dept_code"));
		mode.addAttribute("cost_custom_dept_name", map.get("cost_custom_dept_name"));
		mode.addAttribute("dept_id", map.get("dept_id"));
		mode.addAttribute("dept_code", map.get("dept_code"));
		mode.addAttribute("dept_name", map.get("dept_name"));
		mode.addAttribute("is_stop", map.get("is_stop"));
		mode.addAttribute("start_date", df.format(df.parse(String.valueOf(map.get("start_date")))));
		mode.addAttribute("end_date", df.format(df.parse(String.valueOf(map.get("end_date")))));

		return "hrp/cost/costhrpdeptref/deptRefUpdate";
	}


	// 修改保存
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/updateCostHrpDeptRef", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String deptJson = costHrpDeptRefService.update(mapVo);

		return JSONObject.parseObject(deptJson); 
	}

	@RequestMapping(value = "/hrp/cost/costhrpdeptref/importDeptRefPage", method = RequestMethod.GET)
	public String deptImportPage(Model mode) throws Exception {

		return "hrp/cost/costhrpdeptref/deptRefImport";

	}

	@RequestMapping(value = "hrp/cost/costhrpdeptref/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "成本自定义科室对应关系.xls");

		return null;
	}

	/**
	 *导入
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/readDeptRefFiles", method = RequestMethod.POST)
	public String readDeptRefFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws Exception {
		// 获取部门编码规则

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", SessionManager.getGroupId());

		map.put("hos_id", SessionManager.getHosId());

		map.put("copy_code", SessionManager.getCopyCode());
		
		// 科室类别编码数据  校验科室类别编码
		List<Map<String, Object>> hrpDeptDate = costHrpDeptRefService.queryHrpDeptDate(map) ;
		
		// 科室类别编码数据  校验科室类别编码
		List<Map<String, Object>> costDeptDate = costHrpDeptRefService.queryCostDeptDate(map) ;

		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		
		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				Map<String, Object> errMap = new HashMap<String, Object>();

				String temp[] = list.get(i);// 行
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0])){
						err_sb.append("第"+i+"行数据与第 "+j+"行【系统科室编码】重复(系统科室只能对应一个成本自定义科室);");
					}
				}
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (ExcelReader.validExceLColunm(temp, 0) ) {
					
					int flag = 0 ; 
					
					for(int  m= 0 ; m<hrpDeptDate.size() ; m++){
						
						if(hrpDeptDate.get(m).get("dept_code").equals(temp[0])){
							
							mapVo.put("dept_id", hrpDeptDate.get(m).get("dept_id"));
							
							flag =  1 ;
							
							break ;
						}
						
					}
					errMap.put("dept_code", temp[0]);
					
					if(flag == 0){
						err_sb.append("系统科室编码输入不合法！");
					}

				} else {

					err_sb.append("系统科室编码为空;");

				}
				if (ExcelReader.validExceLColunm(temp,1)) {
					errMap.put("dept_name", temp[1]);
					mapVo.put("dept_name", temp[1]);
				} else {
					err_sb.append("系统科室名称为空;");
				}
				
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					int flag = 0 ; 
					
					for(int  m= 0 ; m<costDeptDate.size() ; m++){
						
						if(costDeptDate.get(m).get("dept_code").equals(temp[2])){
							
							mapVo.put("cost_custom_dept_id", costDeptDate.get(m).get("dept_id"));
							
							flag =  1 ;
							
							break ;
						}
						
					}
					errMap.put("cost_custom_dept_code", temp[2]);
					
					if(flag == 0){
						err_sb.append("自定义科室编码输入不合法！");
					}
					
				} else {
					err_sb.append("自定义科室编码为空;");
				}
				
				if (ExcelReader.validExceLColunm(temp,3)) {
					errMap.put("cost_custom_dept_name", temp[3]);
					mapVo.put("cost_custom_dept_name", temp[3]);
				} else {
					err_sb.append("自定义科室名称为空;");
				}
				

				if (ExcelReader.validExceLColunm(temp,4)) {
					errMap.put("is_stop", temp[4]);
					mapVo.put("is_stop", temp[4]);
				} else {
					err_sb.append("是否停用为空  ");
				}
				
				mapVo.put("start_date", df.format(new Date()));
				mapVo.put("end_date", null);
				
				if(mapVo.get("dept_id") != null && !"".equals(mapVo.get("dept_id")) ){
					Map<String, Object> byCodeMap = new HashMap<String, Object>();

					byCodeMap.put("group_id", mapVo.get("group_id"));

					byCodeMap.put("hos_id", mapVo.get("hos_id"));

					byCodeMap.put("copy_code", mapVo.get("copy_code"));
					
					
					byCodeMap.put("dept_id", mapVo.get("dept_id"));
					
					int count = costHrpDeptRefService.queryExist(byCodeMap);

					// 根据不同业务提示相关信息

					if (count >0 ) {

						err_sb.append("系统科室对应关系已经存在！");

					}
				}
				

				if (err_sb.toString().length() > 0) {

					errMap.put("error_type",err_sb.toString());

					list_err.add(errMap);

				} else {

					addList.add(mapVo);
					//costHrpDeptRefService.add(mapVo);
				}
			}
			
			if (addList.size() > 0 && list_err.size() == 0) {

				costHrpDeptRefService.addBatch(addList);

			}
		}catch (DataAccessException e) {

			Map<String,Object> errMap = new HashMap<String,Object>();

			errMap.put("error_type","导入系统出错");

			list_err.add(errMap);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}
	
	/**
	 * 系统科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/queryHrpDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrpDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());


		mapVo.put("hos_id", SessionManager.getHosId());

		String deptJson = costHrpDeptRefService.queryHrpDept(mapVo);

		return deptJson;
	}
	
	/**
	 * 自定义科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costhrpdeptref/queryCostCustomDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostCustomDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());


		mapVo.put("hos_id", SessionManager.getHosId());

		String deptJson = costHrpDeptRefService.queryCostCustomDept(mapVo);

		return deptJson;
	}
}
