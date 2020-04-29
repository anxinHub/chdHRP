/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDeptParaDict;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostParaAssSet;
import com.chd.hrp.cost.entity.CostParaManSet;
import com.chd.hrp.cost.service.CostDeptParaDictService;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostParaAssSetService;
import com.chd.hrp.cost.serviceImpl.CostParaAssSetDataServiceImpl;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.service.DeptDictService;

/**
 * @Title. @Description. 医辅分摊设置
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostParaAssSetController extends BaseController {

	private static Logger logger = Logger.getLogger(CostParaAssSetController.class);

	@Resource(name = "costParaAssSetService")
	private final CostParaAssSetService costParaAssSetService = null;

	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;

	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;

	@Resource(name = "costDeptParaDictService")
	private final CostDeptParaDictService costDeptParaDictService = null;
	
	@Resource(name = "costParaAssSetDataService")
	private final CostParaAssSetDataServiceImpl costParaAssSetDataService = null;

	/**
	 * 医辅分摊设置<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costparaassset/costParaAssSetMainPage", method = RequestMethod.GET)
	public String costParaAssSetMainPage(Model mode) throws Exception {

		return "hrp/cost/costparaassset/costParaAssSetMain";

	}

	/**
	 * 医辅分摊设置<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costparaassset/costParaAssSetAddPage", method = RequestMethod.GET)
	public String costParaAssSetAddPage(Model mode) throws Exception {

		return "hrp/cost/costparaassset/costParaAssSetAdd";

	}

	/**
	 * 医辅分摊设置<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/addCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaAssSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costParaAssSetJson = costParaAssSetService.addCostParaAssSet(mapVo);

		return JSONObject.parseObject(costParaAssSetJson);

	}

	/**
	 * 医辅分摊设置<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/queryCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaAssSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());

		mapVo.put("is_flag", para_value);

		String costParaAssSet = costParaAssSetService.queryCostParaAssSet(getPage(mapVo));

		return JSONObject.parseObject(costParaAssSet);

	}

	/**
	 * 医辅分摊设置<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/deleteCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaAssSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

			mapVo.put("server_dept_id", ids[6]);

			mapVo.put("cost_item_id", ids[7]);

			listVo.add(mapVo);
		}
		String costParaAssSetJson = costParaAssSetService.deleteBatchCostParaAssSet(listVo);
		return JSONObject.parseObject(costParaAssSetJson);

	}

	/**
	 * 医辅分摊设置<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/costParaAssSetUpdatePage", method = RequestMethod.GET)
	public String costParaAssSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());

		mapVo.put("is_flag", para_value);

		CostParaAssSet costParaAssSet = new CostParaAssSet();
		
		costParaAssSet = costParaAssSetService.queryCostParaAssSetByCode(mapVo);
		
		mode.addAttribute("group_id", costParaAssSet.getGroup_id());

		mode.addAttribute("hos_id", costParaAssSet.getHos_id());

		mode.addAttribute("copy_code", costParaAssSet.getCopy_code());

		mode.addAttribute("dept_id", costParaAssSet.getDept_id());

		mode.addAttribute("dept_no", costParaAssSet.getDept_no());

		mode.addAttribute("server_dept_id", costParaAssSet.getServer_dept_id());

		mode.addAttribute("server_dept_no", costParaAssSet.getServer_dept_no());

		mode.addAttribute("cost_item_id", costParaAssSet.getCost_item_id());

		mode.addAttribute("cost_item_no", costParaAssSet.getCost_item_no());

		mode.addAttribute("para_code", costParaAssSet.getPara_code());

		mode.addAttribute("dept_code", costParaAssSet.getDept_name());
		mode.addAttribute("dept_name", costParaAssSet.getDept_name());
		mode.addAttribute("server_dept_code", costParaAssSet.getServer_dept_name());
		mode.addAttribute("server_dept_name", costParaAssSet.getServer_dept_name());
		mode.addAttribute("cost_item_code", costParaAssSet.getCost_item_name());
		mode.addAttribute("cost_item_name", costParaAssSet.getCost_item_name());
		mode.addAttribute("para_name", costParaAssSet.getPara_name());
		mode.addAttribute("year_month", costParaAssSet.getYear_month());
		mode.addAttribute("acc_year", costParaAssSet.getAcc_year());
		mode.addAttribute("acc_month", costParaAssSet.getAcc_month());

		return "hrp/cost/costparaassset/costParaAssSetUpdate";
	}

	/**
	 * 医辅分摊设置<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costparaassset/updateCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaAssSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costParaAssSetJson = costParaAssSetService.updateCostParaAssSet(mapVo);

		return JSONObject.parseObject(costParaAssSetJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costparaassset/costParaAssSetImportPage", method = RequestMethod.GET)
	public String costParaAssSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costparaassset/costParaAssSetImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costparaassset/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "医辅分摊设置.xls");

		return null;
	}

	/**
	 * 医辅分摊设置<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/readCostParaAssSetFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostParaAssSet> list_err = new ArrayList<CostParaAssSet>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostParaAssSet costParaAssSet = new CostParaAssSet();

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
				if (StringUtils.isNotEmpty(temp[0])) {

					mapVo.put("year_month", temp[0]);
					
				} else {
					
					err_sb.append("统计年月为空  ");
					
				}
				if (StringUtils.isNotEmpty(temp[1])) {

					mapVo.put("dept_code", temp[1]);
					
				} else {
					
					err_sb.append("服务科室编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					mapVo.put("dept_name", temp[2]);
					
				} else {
					
					err_sb.append("服务科室名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					mapVo.put("server_dept_code", temp[3]);
					
				} else {
					
					err_sb.append("受益科室编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[4])) {

					mapVo.put("server_dept_name", temp[4]);
					
				} else {
					
					err_sb.append("受益科室名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[5])) {

					mapVo.put("cost_item_code", temp[5]);
					
				} else {
					
					err_sb.append("成本项目编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[6])) {

					mapVo.put("cost_item_name", temp[6]);
					
				} else {
					
					err_sb.append("成本项目名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[7])) {

					mapVo.put("para_code", temp[7]);
					
				} else {
					
					err_sb.append("分摊参数编码为空  ");
					
				}
				if (StringUtils.isNotEmpty(temp[8])) {

					mapVo.put("para_name", temp[8]);
					
				} else {
					
					err_sb.append("分摊参数名称为空  ");
					
				}
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("year_month", mapVo.get("year_month"));
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("is_stop", "0");
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("type_code", "('02')");
				
				List<DeptDict> deptDict_list = deptDictService.queryDeptDictNo(byCodeMap);
				
				if(deptDict_list.size()>0){
					
					DeptDict dept= deptDict_list.get(0);
					
					mapVo.put("dept_id", dept.getDept_id());
					
					mapVo.put("dept_no", dept.getDept_no());
					
					costParaAssSet.setDept_code((String)mapVo.get("dept_code"));
					
					costParaAssSet.setDept_name((String)mapVo.get("dept_name"));
					
				}else{
					
					costParaAssSet.setDept_code((String)mapVo.get("dept_code"));
					
					costParaAssSet.setDept_name((String)mapVo.get("dept_name"));
					
					err_sb.append("服务科室不存在 ");
					
				}
				
				byCodeMap.put("type_code", "('02','03','04')");
				
				byCodeMap.put("dept_code", mapVo.get("server_dept_code"));
				
				List<DeptDict> list_deptDict  = deptDictService.queryDeptDictNo(byCodeMap);
				
				if(list_deptDict.size()>0 ){
					
					DeptDict dept = list_deptDict.get(0);
					
					mapVo.put("server_dept_id", dept.getDept_id());
					
					mapVo.put("server_dept_no", dept.getDept_no());
					
					costParaAssSet.setServer_dept_code((String)mapVo.get("server_dept_code"));
					
					costParaAssSet.setServer_dept_name((String)mapVo.get("server_dept_name"));
					
				}else{
					
					costParaAssSet.setServer_dept_code((String)mapVo.get("server_dept_code"));
					
					costParaAssSet.setServer_dept_name((String)mapVo.get("server_dept_name"));
					
					err_sb.append("受益科室不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
					costParaAssSet.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					costParaAssSet.setCost_item_name((String)mapVo.get("cost_item_name"));
					
				}else{
					
					costParaAssSet.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					costParaAssSet.setCost_item_name((String)mapVo.get("cost_item_name"));
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("para_code", mapVo.get("para_code"));

				CostDeptParaDict cdpd = costDeptParaDictService.queryCostDeptParaDictByCode(byCodeMap);

				if(cdpd != null ){
					
					mapVo.put("para_code", cdpd.getPara_code());
					
					costParaAssSet.setPara_code((String)mapVo.get("para_code"));
					
					costParaAssSet.setPara_name((String)mapVo.get("para_name"));
					
				}else{
					
					costParaAssSet.setPara_code((String)mapVo.get("para_code"));
					
					costParaAssSet.setPara_name((String)mapVo.get("para_name"));
					
					err_sb.append("分摊参数不存在 ");
					
				}
				
				byCodeMap.put("year_month", mapVo.get("year_month"));
				
				byCodeMap.put("dept_id", mapVo.get("dept_id"));
				
				byCodeMap.put("server_dept_id", mapVo.get("server_dept_id"));
				
				byCodeMap.put("cost_item_id", mapVo.get("cost_item_id"));
				
				byCodeMap.put("dept_code", "");
				
				byCodeMap.put("server_dept_code", "");
				
				byCodeMap.put("cost_item_code", "");
				
				byCodeMap.put("para_code", "");
				
				byCodeMap.put("is_flag", 0);

				CostParaAssSet data_exc_extis = costParaAssSetService.queryCostParaAssSetByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("已存在此分摊配置  ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					costParaAssSet.setYear_month((String)mapVo.get("year_month"));

					costParaAssSet.setError_type(err_sb.toString());

					list_err.add(costParaAssSet);
					
				} else {

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostParaAssSet data_exc = new CostParaAssSet();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costParaAssSetService.addBatchCostParaAssSet(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 医辅分摊设置<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/addBatchCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostParaAssSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostParaAssSet> list_err = new ArrayList<CostParaAssSet>();

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

				mapVo.put("year_month", jsonObj.get("year_month"));
				
				mapVo.put("dept_code", jsonObj.get("dept_code"));

				mapVo.put("dept_name", jsonObj.get("dept_name"));

				mapVo.put("server_dept_code", jsonObj.get("server_dept_code"));

				mapVo.put("server_dept_name", jsonObj.get("server_dept_name"));

				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));

				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));

				mapVo.put("para_code", jsonObj.get("para_code"));
				
				mapVo.put("para_name", jsonObj.get("para_name"));
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("year_month", mapVo.get("year_month"));
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("is_stop", "0");
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("type_code", "('02')");
				
				List<DeptDict> list_deptDict = deptDictService.queryDeptDictNo(byCodeMap);
				
				if(list_deptDict.size()>0){
					
					DeptDict deptDict = list_deptDict.get(0);
					
					mapVo.put("dept_id", deptDict.getDept_id());
					
					mapVo.put("dept_no", deptDict.getDept_no());
					
				}else{
					
					err_sb.append("服务科室不存在 ");
					
				}
				
				byCodeMap.put("dept_code", mapVo.get("server_dept_code"));
				
				byCodeMap.put("type_code", "('03','02','04')");
				
				List<DeptDict> deptDict_list = deptDictService.queryDeptDictNo(byCodeMap);
				
				if(deptDict_list.size()>0){
					
					DeptDict deptDict = deptDict_list.get(0);
					
					mapVo.put("server_dept_id", deptDict.getDept_id());
					
					mapVo.put("server_dept_no", deptDict.getDept_no());
					
				}else{
					
					err_sb.append("受益科室不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
				}else{
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("para_code", mapVo.get("para_code"));

				CostDeptParaDict cdpd = costDeptParaDictService.queryCostDeptParaDictByCode(byCodeMap);

				if(cdpd != null ){
					
					mapVo.put("para_code", cdpd.getPara_code());
					
				}else{
					
					err_sb.append("分摊参数不存在 ");
					
				}
				
				byCodeMap.put("dept_id", mapVo.get("dept_id"));
				
				byCodeMap.put("server_dept_id", mapVo.get("server_dept_id"));
				
				byCodeMap.put("cost_item_id", mapVo.get("cost_item_id"));
				
				byCodeMap.put("dept_code", "");
				
				byCodeMap.put("server_dept_code", "");
				
				byCodeMap.put("cost_item_code", "");
				
				byCodeMap.put("para_code", "");
				
				byCodeMap.put("is_flag", 0);

				CostParaAssSet data_exc_extis = costParaAssSetService.queryCostParaAssSetByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("已存在此分摊配置  ");
					
				}

				CostParaAssSet costParaAssSet = new CostParaAssSet();

				if (err_sb.toString().length() > 0) {
					
					costParaAssSet.setYear_month(mapVo.get("year_month").toString());
					
					costParaAssSet.setDept_code(mapVo.get("dept_code").toString());

					costParaAssSet.setDept_name(mapVo.get("dept_name").toString());

					costParaAssSet.setServer_dept_code(mapVo.get("server_dept_code").toString());

					costParaAssSet.setServer_dept_name(mapVo.get("server_dept_name").toString());

					costParaAssSet.setCost_item_code(mapVo.get("cost_item_code").toString());

					costParaAssSet.setCost_item_name(mapVo.get("cost_item_name").toString());

					costParaAssSet.setPara_code(mapVo.get("para_code").toString());
					
					costParaAssSet.setPara_name(mapVo.get("para_name").toString());

					costParaAssSet.setError_type(err_sb.toString());

					list_err.add(costParaAssSet);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costParaAssSetService.addBatchCostParaAssSet(list_batch);

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
	
	/**
	 * 管理分摊设置<BR>
	 * 维护页面跳转
	 */
	// 快速填充
	@RequestMapping(value = "/hrp/cost/costparaassset/costParaAssSetFastPage", method = RequestMethod.GET)
	public String costParaAssSetFastPage(Model mode) throws Exception {

		return "hrp/cost/costparaassset/costParaAssSetFast";

	}
	
	/**
	 * 管理分摊设置<BR>
	 * 快速填充保存
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/fastCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastCostParaAssSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String costParaManSetJson = null;
		
		List list_batch = new ArrayList();
		
		List<String> list_dept = new ArrayList<String>();
		
		List<String> list_server_dept = new ArrayList<String>();
		
		List<String> list_cost_item = new ArrayList<String>();
		
		String dept = (String) mapVo.get("dept");
		
		String server_dept = (String) mapVo.get("server_dept");
		
		String cost_item = (String) mapVo.get("cost_item");
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		
		entityMap.put("is_stop", "0");
		
		entityMap.put("is_last", "1");
		
		if(StringUtils.isNotEmpty(dept)){
			
			String[] dept_split = dept.split(";");
			
			for(int i =0 ; i<dept_split.length; i++){
				
				list_dept.add(dept_split[i]);
				
			}
			
		}else{
			
			entityMap.put("kind_code", "('03','02','01')");

			List<DeptDict> list= deptDictService.queryDeptDictNo(entityMap);
			
			for(int i =0 ;i<list.size();i++){
				
				DeptDict dd = list.get(i);
				
				list_dept.add(dd.getDept_id()+"."+dd.getDept_no());
				
			}
			
			
		}
		
		if(StringUtils.isNotEmpty(server_dept)){
			
			String[] server_dept_split = server_dept.split(";");
			
			for(int i = 0 ;i<server_dept_split.length;i++){
				
				list_server_dept.add(server_dept_split[i]);
				
			}
			
		}else{

			List<DeptDict> list= deptDictService.queryDeptDictNo(entityMap);
			
			for(int i =0 ;i<list.size();i++){
				
				DeptDict dd = list.get(i);
				
				list_server_dept.add(dd.getDept_id()+"."+dd.getDept_no());
				
			}
			
		}
		if(StringUtils.isNotEmpty(cost_item)){
			
			String[] cost_item_split = cost_item.split(";");
			
			for(int i = 0 ;i<cost_item_split.length;i++){
				
				list_cost_item.add(cost_item_split[i]);
				
			}
			
		}else{
			
			List<CostItemDictNo> list = costItemDictNoService.queryItemDictNo(entityMap);
			
			for(int i=0; i<list.size();i++){

				CostItemDictNo cid = list.get(i);
				
				list_cost_item.add(cid.getCost_item_id()+"."+cid.getCost_item_no());
				
			}
			
		}	

        
        for(int i =0 ; i<list_dept.size(); i++){
        	
        	for(int j = 0; j<list_server_dept.size(); j++){
        		
        		for(int k =0 ; k<list_cost_item.size(); k++){
        			
        			Map<String, Object> para_map = new HashMap<String, Object>();
        			
        			if (para_map.get("group_id") == null) {
        				
        				para_map.put("group_id", SessionManager.getGroupId());
        				
        			}
        			
        			if (para_map.get("hos_id") == null) {
        				
        				para_map.put("hos_id", SessionManager.getHosId());
        				
        			}
        			
        			if (para_map.get("copy_code") == null) {
        				
        				para_map.put("copy_code", SessionManager.getCopyCode());
        				
        			}
        			
        			para_map.put("dept_id", list_dept.get(i).split("\\.")[0]);
        			
        			para_map.put("dept_no", list_dept.get(i).split("\\.")[1]);
        			
        			para_map.put("server_dept_id", list_server_dept.get(j).split("\\.")[0]);
        			
        			para_map.put("server_dept_no", list_server_dept.get(j).split("\\.")[1]);
        			
        			para_map.put("cost_item_id", list_cost_item.get(k).split("\\.")[0]);
        			
        			para_map.put("cost_item_no", list_cost_item.get(k).split("\\.")[1]);
        			
        			para_map.put("acc_year", mapVo.get("acc_year"));
        			
        			para_map.put("acc_month", mapVo.get("acc_month"));
        			
        			para_map.put("para_code", mapVo.get("para_code"));
        			
        			list_batch.add(para_map);

        		}

        	}
        }
        
    	costParaAssSetDataService.deleteBatchCostParaAssSetData(list_batch);
    	
    	costParaAssSetService.deleteBatchCostParaAssSet(list_batch);
    	
    	costParaManSetJson=costParaAssSetService.addBatchCostParaAssSet(list_batch);

		return JSONObject.parseObject(costParaManSetJson);

	}
	/**
	 * 管理分摊设置<BR>
	 * 继承
	 */
	@RequestMapping(value = "/hrp/cost/costparaassset/inheritCostParaAssSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inheritCostParaAssSet(@RequestParam(value = "year_month", required = true) String year_month, Model mode) throws Exception {

		Map<String, Object> para_map = new HashMap<String, Object>();
		
		if (para_map.get("group_id") == null) {
			
			para_map.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (para_map.get("hos_id") == null) {
			
			para_map.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (para_map.get("copy_code") == null) {
			
			para_map.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		para_map.put("acc_year", year_month.substring(0,4));
		
		para_map.put("acc_month", year_month.substring(4,6));
		
		para_map.put("is_flag", "0");

		List<CostParaAssSet> list = costParaAssSetService.queryCostParaAssSetNoPage(para_map);
		
        if(list.size() == 0){
			
			return JSONObject.parseObject("{\"msg\":\"统计年月:"+para_map.get("acc_year")+para_map.get("acc_month")+"没有数据，无法继承到下个月\",\"state\":\"false\"}");
		}
		
		List list_batch = new ArrayList();
		
		for(int i =0; i<list.size(); i++){
			
			 para_map = new HashMap<String, Object>();
			
			CostParaAssSet cps = list.get(i);
			
			if (para_map.get("group_id") == null) {
				
				para_map.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (para_map.get("hos_id") == null) {
				
				para_map.put("hos_id", SessionManager.getHosId());
				
			}
			
			if (para_map.get("copy_code") == null) {
				
				para_map.put("copy_code", SessionManager.getCopyCode());
				
			}
			
			para_map.put("dept_id", cps.getDept_id());
			
			para_map.put("dept_no", cps.getDept_no());
			
			para_map.put("server_dept_id", cps.getServer_dept_id());
			
			para_map.put("server_dept_no", cps.getServer_dept_no());
			
			para_map.put("cost_item_id", cps.getCost_item_id());
			
			para_map.put("cost_item_no", cps.getCost_item_no());
			
            para_map.put("acc_year", DateUtil.getNextYear_Month(year_month).substring(0,4));
			
			para_map.put("acc_month", DateUtil.getNextYear_Month(year_month).substring(4,6));
			
			para_map.put("para_code", cps.getPara_code());
			
			list_batch.add(para_map);
			
		}
		
		costParaAssSetDataService.deleteBatchCostParaAssSetData(list_batch);
    	
    	costParaAssSetService.deleteBatchCostParaAssSet(list_batch);
		
		String costParaManSetJson=costParaAssSetService.addBatchCostParaAssSet(list_batch);

		return JSONObject.parseObject(costParaManSetJson);

	}
}
