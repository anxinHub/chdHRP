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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostMateArrt;
import com.chd.hrp.cost.entity.CostMaterialDetail;
import com.chd.hrp.cost.service.CostMateArrtService;
import com.chd.hrp.cost.service.CostMateTypeArrtService;
import com.chd.hrp.cost.service.CostMaterialDetailService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.service.DeptDictService;
import com.chd.hrp.sys.service.SourceService;

/**
 * @Title. @Description. 科室材料支出明细表
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostMaterialDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(CostMaterialDetailController.class);

	@Resource(name = "costMaterialDetailService")
	private final CostMaterialDetailService costMaterialDetailService = null;

	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;

	@Resource(name = "costMateArrtService")
	private final CostMateArrtService costMateArrtService = null;
 
	@Resource(name = "costMateTypeArrtService")
	private final CostMateTypeArrtService costMateTypeArrtService = null;

	@Resource(name = "sourceService")
	private final SourceService sourceService = null;
	

	@RequestMapping(value = "/hrp/cost/costmaterialdetail/synPage", method = RequestMethod.GET)
	public String synPage(Model mode) throws Exception {

		return "hrp/cost/costmaterialdetail/syn";
	}
	
	/**
	 * 科室材料支出明细表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costmaterialdetail/costMaterialDetailMainPage", method = RequestMethod.GET)
	public String costMaterialDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costmaterialdetail/costMaterialDetailMain";

	}

	/**
	 * 科室材料支出明细表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/costMaterialDetailAddPage", method = RequestMethod.GET)
	public String costMaterialDetailAddPage(Model mode) throws Exception {

		return "hrp/cost/costmaterialdetail/costMaterialDetailAdd";

	}

	/**
	 * 科室材料支出明细表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/addCostMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostMaterialDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costMaterialDetailJson = costMaterialDetailService.addCostMaterialDetail(mapVo);

		return JSONObject.parseObject(costMaterialDetailJson);

	}

	/**
	 * 科室材料支出明细表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/queryCostMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostMaterialDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costMaterialDetail = costMaterialDetailService.queryCostMaterialDetail(getPage(mapVo));

		return JSONObject.parseObject(costMaterialDetail);

	}

	/**
	 * 科室材料支出明细表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/deleteCostMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostMaterialDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("acc_month", ids[4]);

			mapVo.put("dept_code", ids[5]);

			mapVo.put("mate_type_code", ids[6]);

			mapVo.put("source_id", ids[7]);

			listVo.add(mapVo);
		}
		String costMaterialDetailJson = costMaterialDetailService.deleteBatchCostMaterialDetail(listVo);
		return JSONObject.parseObject(costMaterialDetailJson);

	}

	/**
	 * 科室材料支出明细表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/costMaterialDetailUpdatePage", method = RequestMethod.GET)
	public String costMaterialDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostMaterialDetail costMaterialDetail = new CostMaterialDetail();
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
    	mapVo.put("is_flag", para_value);
		
		costMaterialDetail = costMaterialDetailService.queryCostMaterialDetailByCode(mapVo);
		mode.addAttribute("group_id", costMaterialDetail.getGroup_id());

		mode.addAttribute("hos_id", costMaterialDetail.getHos_id());

		mode.addAttribute("copy_code", costMaterialDetail.getCopy_code());

		mode.addAttribute("year_month", costMaterialDetail.getAcc_year().toString() + costMaterialDetail.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costMaterialDetail.getAcc_year());
		
		mode.addAttribute("acc_month", costMaterialDetail.getAcc_month());

		mode.addAttribute("source_id", costMaterialDetail.getSource_id());

		mode.addAttribute("sum_money", costMaterialDetail.getSum_money());

		mode.addAttribute("dept_no", costMaterialDetail.getDept_no());
		mode.addAttribute("source_code", costMaterialDetail.getSource_code());
		mode.addAttribute("source_name", costMaterialDetail.getSource_name());
		mode.addAttribute("dept_code", costMaterialDetail.getDept_code());
		mode.addAttribute("dept_name", costMaterialDetail.getDept_name());
		mode.addAttribute("mate_type_code", costMaterialDetail.getMate_type_code());
		mode.addAttribute("mate_type_name", costMaterialDetail.getMate_type_name());

		return "hrp/cost/costmaterialdetail/costMaterialDetailUpdate";
	}

	/**
	 * 科室材料支出明细表<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costmaterialdetail/updateCostMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostMaterialDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costMaterialDetailJson = costMaterialDetailService.updateCostMaterialDetail(mapVo);

		return JSONObject.parseObject(costMaterialDetailJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/costMaterialDetailImportPage", method = RequestMethod.GET)
	public String costMaterialDetailImportPage(Model mode) throws Exception {

		return "hrp/cost/costmaterialdetail/costMaterialDetailImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costmaterialdetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "材料支出明细采集.xls");

		return null;
	}

	/**
	 * 科室材料支出明细表<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/readCostMaterialDetailFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostMaterialDetail> list_err = new ArrayList<CostMaterialDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostMaterialDetail costMaterialDetail = new CostMaterialDetail();

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
					
					String year_month = temp[0].toString();
					
					costMaterialDetail.setYear_month(temp[0]);
					
					costMaterialDetail.setAcc_year(year_month.substring(0, 4));
					
					costMaterialDetail.setAcc_month(year_month.substring(4, 6));

					mapVo.put("acc_year", year_month.substring(0, 4));
					
					mapVo.put("acc_month", year_month.substring(4, 6));

				} else {

					err_sb.append("统计年月为空  ");

				}
				
				
				if (StringUtils.isNotEmpty(temp[1])) {

					mapVo.put("dept_code", temp[1]);
					
				} else {
					
					err_sb.append("科室编码为空  ");
	
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					mapVo.put("dept_name", temp[2]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					mapVo.put("mate_code", temp[3]);
					
				} else {
					
					err_sb.append("材料编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[4])) {

					mapVo.put("mate_name", temp[4]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[5])) {

					mapVo.put("mate_type_code", temp[5]);
					
					costMaterialDetail.setMate_type_code(temp[5]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[6])) {

					mapVo.put("mate_type_name", temp[6]);
					
					costMaterialDetail.setMate_type_name(temp[6]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[7])) {

					mapVo.put("source_code", temp[7]);
					
				} else {
					
					err_sb.append("资金来源编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[8])) {

					mapVo.put("source_name", temp[8]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[9])) {

					costMaterialDetail.setSum_money(Long.valueOf(temp[9]));

					mapVo.put("sum_money", temp[9]);
					
				} else {
					
					err_sb.append("数量为空  ");
					
				}
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("is_stop", "0");
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				DeptDict deptDict = deptDictService.queryDeptDictByDeptCode(byCodeMap);
				
				if(deptDict != null ){
					
					mapVo.put("dept_code", deptDict.getDept_code());
					
					costMaterialDetail.setDept_code((String)mapVo.get("dept_code"));
					
					costMaterialDetail.setDept_name((String)mapVo.get("dept_name"));
					
				}else{
					
					costMaterialDetail.setDept_code((String)mapVo.get("dept_code"));
					
					costMaterialDetail.setDept_name((String)mapVo.get("dept_name"));
					
					err_sb.append("科室不存在 ");
					
				}
				
				
				byCodeMap.put("source_code", mapVo.get("source_code"));
				
				Source source = sourceService.querySourceByCode(byCodeMap);
				
				if(source != null ){
					
					mapVo.put("source_id", source.getSource_id());
					
					costMaterialDetail.setSource_code((String)mapVo.get("source_code"));

					costMaterialDetail.setSource_name((String)mapVo.get("source_name"));
					
				}else{
					
					costMaterialDetail.setSource_code((String)mapVo.get("source_code"));
					
					costMaterialDetail.setSource_name((String)mapVo.get("source_name"));
					
					err_sb.append("资金来源编码不存在 ");
					
				}
				
				byCodeMap.put("acc_year", mapVo.get("acc_year"));
				
				byCodeMap.put("acc_month", mapVo.get("acc_month"));
				
				byCodeMap.put("dept_id", mapVo.get("dept_id"));
				
				byCodeMap.put("dept_code", "");
				
				byCodeMap.put("mate_id", mapVo.get("mate_id"));
				
				byCodeMap.put("mate_code", "");
				
				byCodeMap.put("source_id", mapVo.get("source_id"));
				
				byCodeMap.put("source_code", "");
				
				byCodeMap.put("is_flag", 0);
				
				CostMaterialDetail data_exc_extis = costMaterialDetailService.queryCostMaterialDetailByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("当月下存在配置  ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costMaterialDetail.setError_type(err_sb.toString());

					list_err.add(costMaterialDetail);
					
				} else {

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostMaterialDetail data_exc = new CostMaterialDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costMaterialDetailService.addBatchCostMaterialDetail(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 科室材料支出明细表<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/addBatchCostMaterialDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostMaterialDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostMaterialDetail> list_err = new ArrayList<CostMaterialDetail>();

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
				
				mapVo.put("mate_type_code", jsonObj.get("mate_type_code"));

				mapVo.put("mate_type_name", jsonObj.get("mate_type_name"));

				mapVo.put("source_code", jsonObj.get("source_code"));
				
				mapVo.put("source_name", jsonObj.get("source_name"));

				mapVo.put("sum_money", jsonObj.get("sum_money"));

				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("is_stop", "0");
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				DeptDict deptDict = deptDictService.queryDeptDictByDeptCode(byCodeMap);
				
				if(deptDict != null ){
					
					mapVo.put("dept_code", deptDict.getDept_code());
					
				}else{
					
					err_sb.append("科室不存在 ");
					
				}
				
				byCodeMap.put("source_code", mapVo.get("source_code"));
				
				Source source = sourceService.querySourceByCode(byCodeMap);
				
				if(source != null ){
					
					mapVo.put("source_id", source.getSource_id());
					
				}else{

					err_sb.append("资金来源编码不存在 ");
					
				}

				byCodeMap.put("acc_year", mapVo.get("acc_year"));
				
				byCodeMap.put("acc_month", mapVo.get("acc_month"));
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("source_id", mapVo.get("source_id"));
				
				byCodeMap.put("source_code", "");
				
				CostMaterialDetail data_exc_extis = costMaterialDetailService.queryCostMaterialDetailByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {

					err_sb.append("当月下存在配置  ");
					
				}

				CostMaterialDetail costMaterialDetail = new CostMaterialDetail();

				if (err_sb.toString().length() > 0) {
					
					costMaterialDetail.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costMaterialDetail.setAcc_year(mapVo.get("acc_year").toString());
					
					costMaterialDetail.setAcc_month(mapVo.get("acc_month").toString());

					costMaterialDetail.setDept_code(mapVo.get("dept_code").toString());

					costMaterialDetail.setDept_name(mapVo.get("dept_name").toString());

					costMaterialDetail.setMate_code(mapVo.get("mate_code").toString());
					
					costMaterialDetail.setMate_name(mapVo.get("mate_name").toString());
					
					costMaterialDetail.setMate_type_code(mapVo.get("mate_type_code").toString());
					
					costMaterialDetail.setMate_type_name(mapVo.get("mate_type_name").toString());

					costMaterialDetail.setSource_code(mapVo.get("source_code").toString());
					
					costMaterialDetail.setSource_name(mapVo.get("source_name").toString());

					costMaterialDetail.setIs_charge(Integer.valueOf(mapVo.get("is_charge").toString()));

					costMaterialDetail.setSum_money(Long.valueOf(mapVo.get("num").toString()));


					costMaterialDetail.setError_type(err_sb.toString());

					list_err.add(costMaterialDetail);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costMaterialDetailService.addBatchCostMaterialDetail(list_batch);

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
	
	@RequestMapping(value = "/hrp/cost/costmaterialdetail/addSynData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSynData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String data;
		try {
			data = costMaterialDetailService.addSynData(mapVo);
		} catch (Exception e) {
			
			data = e.getMessage();
		}

		return JSONObject.parseObject(data);
	}
}
