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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDeptParaDict;
import com.chd.hrp.cost.entity.CostUserDefinedPara;
import com.chd.hrp.cost.service.CostDeptParaDictService;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.service.CostUserDefinedParaService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.service.DeptDictService;

/**
 * @Title. @Description. 自定义参数数据采集表
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostUserDefinedParaController extends BaseController {

	private static Logger logger = Logger.getLogger(CostUserDefinedParaController.class);

	@Resource(name = "costUserDefinedParaService")
	private final CostUserDefinedParaService costUserDefinedParaService = null;

	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;

	@Resource(name = "costDeptParaDictService")
	private final CostDeptParaDictService costDeptParaDictService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;

	/**
	 * 自定义参数数据采集表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaMainPage", method = RequestMethod.GET)
	public String costUserDefinedParaMainPage(Model mode) throws Exception {

		return "hrp/cost/costuserdefinedpara/costUserDefinedParaMain";

	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaAddPage", method = RequestMethod.GET)
	public String costUserDefinedParaAddPage(Model mode) throws Exception {

		return "hrp/cost/costuserdefinedpara/costUserDefinedParaAdd";

	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/addCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> addCostUserDefinedPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costUserDefinedParaJson = costUserDefinedParaService.addCostUserDefinedPara(mapVo);

		return JSONObject.parseObject(costUserDefinedParaJson);

	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/queryCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryCostUserDefinedPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) { 
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
    	
		String costUserDefinedPara = costUserDefinedParaService.queryCostUserDefinedPara(getPage(mapVo));

		return JSONObject.parseObject(costUserDefinedPara);

	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/deleteCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostUserDefinedPara(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("acc_year", ids[3]);
			mapVo.put("acc_month", ids[4]);

			mapVo.put("dept_id", ids[5]);

			mapVo.put("para_code", ids[6]);

			listVo.add(mapVo);
		}
		String costUserDefinedParaJson = costUserDefinedParaService.deleteBatchCostUserDefinedPara(listVo);
		return JSONObject.parseObject(costUserDefinedParaJson);

	}

	
	/**
	 * 自定义参数数据采集表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/deleteMonthlyCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostUserDefinedPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());

	    mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		
		String costUserDefinedParaJson;
		
	   try {
		
		   costUserDefinedParaJson = costUserDefinedParaService.deleteMonthlyCostUserDefinedPara(mapVo);
		   
		} catch (Exception e) {
			// TODO: handle exception
			costUserDefinedParaJson = e.getMessage();
		}
		return JSONObject.parseObject(costUserDefinedParaJson);

	}
	
	/**
	 * 自定义参数数据采集表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaUpdatePage", method = RequestMethod.GET)

	public String costUserDefinedParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostUserDefinedPara costUserDefinedPara = new CostUserDefinedPara();
		
	    String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
		
		costUserDefinedPara = costUserDefinedParaService.queryCostUserDefinedParaByCode(mapVo);
		
		mode.addAttribute("group_id", costUserDefinedPara.getGroup_id());

		mode.addAttribute("hos_id", costUserDefinedPara.getHos_id());

		mode.addAttribute("copy_code", costUserDefinedPara.getCopy_code());
		
		mode.addAttribute("year_month", costUserDefinedPara.getAcc_year().toString() + costUserDefinedPara.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costUserDefinedPara.getAcc_year());
		mode.addAttribute("acc_month", costUserDefinedPara.getAcc_month());

		mode.addAttribute("dept_id", costUserDefinedPara.getDept_id());

		mode.addAttribute("dept_no", costUserDefinedPara.getDept_no());
		
		mode.addAttribute("dept_code", costUserDefinedPara.getDept_code());

		mode.addAttribute("dept_name", costUserDefinedPara.getDept_name());

		mode.addAttribute("para_code", costUserDefinedPara.getPara_code());
		
		mode.addAttribute("para_name", costUserDefinedPara.getPara_name());

		mode.addAttribute("para_value", costUserDefinedPara.getPara_value());

		return "hrp/cost/costuserdefinedpara/costUserDefinedParaUpdate";
	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/updateCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostUserDefinedPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costUserDefinedParaJson = costUserDefinedParaService.updateCostUserDefinedPara(mapVo);

		return JSONObject.parseObject(costUserDefinedParaJson);
	}
	
	
	/**
	 * 自定义参数数据采集表<BR>
	 * 继承页面跳转
	 */
	// 添加页面  
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaExtendPage", method = RequestMethod.GET)
	public String costUserDefinedParaExtendPage(Model mode) throws Exception {

		return "hrp/cost/costuserdefinedpara/costUserDefinedParaExtend";

	}
	
	
	/**
	 * @Description 自定义参数数据采集表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaExtendInheritance", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> costUserDefinedParaExtendInheritance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

			String extendInheritance = "";
			try {
				
					mapVo.put("group_id", SessionManager.getGroupId());
					mapVo.put("hos_id", SessionManager.getHosId());
					mapVo.put("copy_code", SessionManager.getCopyCode());

					
				   extendInheritance = costUserDefinedParaService.costUserDefinedParaExtendInheritance(mapVo);
				  
			}catch (Exception e) {
				// TODO: handle exception
				extendInheritance = e.getMessage();
			}

		 

		   return JSONObject.parseObject(extendInheritance);
		   
	}

	
	/**
	 * 自定义参数数据采集表<BR>
	 * 同步系统其它数据信息
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaSynchroPage", method = RequestMethod.GET)
	public String costUserDefinedParaSynchroPage(Model mode) throws Exception {

		return "hrp/cost/costuserdefinedpara/costUserDefinedParaSynchro";

	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 同步系统其它数据信息
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/synchroCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroCostUserDefinedPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		    String synchroJson = "";
		
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());
		
			mapVo.put("copy_code", SessionManager.getCopyCode());
		
			try {
				
				synchroJson = costUserDefinedParaService.synchroCostUserDefinedPara(mapVo);
				
			} catch (Exception e) {
				// TODO: handle exception
				synchroJson = e.getMessage();
			}
			 return JSONObject.parseObject(synchroJson);

	}
	
	
	// 导入页面
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaImportPage", method = RequestMethod.GET)
	public String costUserDefinedParaImportPage(Model mode) throws Exception {

		return "hrp/cost/costuserdefinedpara/costUserDefinedParaImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costuserdefinedpara/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "自定义分摊参数采集表.xls");

		return null;
	}

	/**
	 * 自定义参数数据采集表<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/costUserDefinedParaImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String readCostUserDefinedParaFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costUserDefinedParaService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
		
	}
	/**
	 * 自定义参数数据采集表<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costuserdefinedpara/addBatchCostUserDefinedPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostUserDefinedPara(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostUserDefinedPara> list_err = new ArrayList<CostUserDefinedPara>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

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

				String year_month = String.valueOf(jsonObj.get("year_month"));
				
				mapVo.put("acc_year", year_month.substring(0, 4));
				
				mapVo.put("acc_month", year_month.substring(4, 6));

				mapVo.put("dept_code", jsonObj.get("dept_code"));

				mapVo.put("dept_name", jsonObj.get("dept_name"));

				mapVo.put("para_code", jsonObj.get("para_code"));
				
				mapVo.put("para_name", jsonObj.get("para_name"));

				mapVo.put("para_value", jsonObj.get("para_value"));
				
				
				DeptDict deptDict = deptDictService.queryDeptDictByDeptCode(mapVo);

				if (deptDict != null) {

					mapVo.put("dept_id", deptDict.getDept_id());

					mapVo.put("dept_no", deptDict.getDept_no());

				} else {
					err_sb.append("科室不存在 ");
				}

				CostDeptParaDict cdpd = costDeptParaDictService.queryCostDeptParaDictByCode(mapVo);

				if (cdpd != null) {

					mapVo.put("para_code", cdpd.getPara_code());

				} else {

					err_sb.append("分摊参数不存在 ");

				}
				
				CostUserDefinedPara data_exc_extis = costUserDefinedParaService.queryCostUserDefinedParaByCode(mapVo);

				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}

				CostUserDefinedPara costUserDefinedPara = new CostUserDefinedPara();

				if (err_sb.toString().length() > 0) {
					costUserDefinedPara.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costUserDefinedPara.setAcc_year(mapVo.get("acc_year").toString());
					
					costUserDefinedPara.setAcc_month(mapVo.get("acc_month").toString());

					costUserDefinedPara.setDept_code(mapVo.get("dept_code").toString());

					costUserDefinedPara.setDept_name(mapVo.get("dept_name").toString());

					costUserDefinedPara.setPara_code(mapVo.get("para_code").toString());
					
					costUserDefinedPara.setPara_name(mapVo.get("para_name").toString());

					costUserDefinedPara.setPara_value(Double.valueOf(mapVo.get("para_value").toString()));

					costUserDefinedPara.setError_type(err_sb.toString());

					list_err.add(costUserDefinedPara);
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costUserDefinedParaService.addBatchCostUserDefinedPara(list_batch);

			}
		}
		catch (DataAccessException e) {

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
}
