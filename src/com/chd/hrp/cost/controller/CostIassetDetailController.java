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
import com.chd.hrp.cost.entity.CostFassetArrt;
import com.chd.hrp.cost.entity.CostIassetArrt;
import com.chd.hrp.cost.entity.CostIassetDetail;
import com.chd.hrp.cost.service.CostIassetArrtService;
import com.chd.hrp.cost.service.CostIassetDetailService;
import com.chd.hrp.cost.service.CostIassetTypeArrtService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.service.DeptDictService;
import com.chd.hrp.sys.service.SourceService;

/**
 * @Title. @Description. 科室无形资产折旧明细
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostIassetDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(CostIassetDetailController.class);

	@Resource(name = "costIassetDetailService")
	private final CostIassetDetailService costIassetDetailService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;

	@Resource(name = "costIassetArrtService")
	private final CostIassetArrtService costIassetArrtService = null;

	@Resource(name = "costIassetTypeArrtService")
	private final CostIassetTypeArrtService costIassetTypeArrtService = null;

	@Resource(name = "sourceService")
	private final SourceService sourceService = null;
	
	@RequestMapping(value = "/hrp/cost/costiassetdetail/synPage", method = RequestMethod.GET)
	public String synPage(Model mode) throws Exception {

		return "hrp/cost/costiassetdetail/syn";
	}
	/**
	 * 科室无形资产折旧明细<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costiassetdetail/costIassetDetailMainPage", method = RequestMethod.GET)
	public String costIassetDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costiassetdetail/costIassetDetailMain";

	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costiassetdetail/costIassetDetailAddPage", method = RequestMethod.GET)
	public String costIassetDetailAddPage(Model mode) throws Exception {

		return "hrp/cost/costiassetdetail/costIassetDetailAdd";

	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costiassetdetail/addCostIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostIassetDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIassetDetailJson = costIassetDetailService.addCostIassetDetail(mapVo);

		return JSONObject.parseObject(costIassetDetailJson);

	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costiassetdetail/queryCostIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIassetDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costIassetDetail = costIassetDetailService.queryCostIassetDetail(getPage(mapVo));

		return JSONObject.parseObject(costIassetDetail);

	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costiassetdetail/deleteCostIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIassetDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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

			mapVo.put("asset_type_code", ids[6]);

			mapVo.put("source_id", ids[7]);

			listVo.add(mapVo);
		}
		String costIassetDetailJson = costIassetDetailService.deleteBatchCostIassetDetail(listVo);
		return JSONObject.parseObject(costIassetDetailJson);

	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costiassetdetail/costIassetDetailUpdatePage", method = RequestMethod.GET)
	public String costIassetDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostIassetDetail costIassetDetail = new CostIassetDetail();
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());

		mapVo.put("is_flag", para_value);
		
		costIassetDetail = costIassetDetailService.queryCostIassetDetailByCode(mapVo);
		mode.addAttribute("group_id", costIassetDetail.getGroup_id());

		mode.addAttribute("hos_id", costIassetDetail.getHos_id());

		mode.addAttribute("copy_code", costIassetDetail.getCopy_code());

		mode.addAttribute("year_month", costIassetDetail.getAcc_year().toString() + costIassetDetail.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costIassetDetail.getAcc_year());
		
		mode.addAttribute("acc_month", costIassetDetail.getAcc_month());

		mode.addAttribute("source_id", costIassetDetail.getSource_id());

		mode.addAttribute("depre_amount", costIassetDetail.getDepre_amount());

		mode.addAttribute("dept_no", costIassetDetail.getDept_no());
		
		mode.addAttribute("dept_code", costIassetDetail.getDept_code());
		mode.addAttribute("dept_name", costIassetDetail.getDept_name());
		mode.addAttribute("asset_type_code", costIassetDetail.getAsset_type_code());
		mode.addAttribute("asset_type_name", costIassetDetail.getAsset_type_name());
		mode.addAttribute("source_code", costIassetDetail.getSource_code());
		mode.addAttribute("source_name", costIassetDetail.getSource_name());

		return "hrp/cost/costiassetdetail/costIassetDetailUpdate";
	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costiassetdetail/updateCostIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostIassetDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costIassetDetailJson = costIassetDetailService.updateCostIassetDetail(mapVo);

		return JSONObject.parseObject(costIassetDetailJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costiassetdetail/costIassetDetailImportPage", method = RequestMethod.GET)
	public String costIassetDetailImportPage(Model mode) throws Exception {

		return "hrp/cost/costiassetdetail/costIassetDetailImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costiassetdetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "无形资产摊销明细采集.xls");

		return null;
	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costiassetdetail/readCostIassetDetailFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostIassetDetail> list_err = new ArrayList<CostIassetDetail>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostIassetDetail costIassetDetail = new CostIassetDetail();

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
					
					costIassetDetail.setYear_month(temp[0]);

					costIassetDetail.setAcc_year(year_month.substring(0, 4));
					
					costIassetDetail.setAcc_month(year_month.substring(4, 6));
					
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

					mapVo.put("asset_type_code", temp[3]);
					
					costIassetDetail.setAsset_type_code(temp[3]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[4])) {

					mapVo.put("asset_type_name", temp[4]);
					
					costIassetDetail.setAsset_type_name(temp[4]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[5])) {

					mapVo.put("source_code", temp[5]);
					
				} else {
					
					err_sb.append("资金来源编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[6])) {

					mapVo.put("source_name", temp[6]);
					
				}
				
				if (StringUtils.isNotEmpty(temp[7])) {

					costIassetDetail.setDepre_amount(Double.valueOf(temp[7]));

					mapVo.put("depre_amount", temp[7]);
					
				} else {
					
					err_sb.append("折旧额为空  ");
					
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
					
					costIassetDetail.setDept_code((String)mapVo.get("dept_code"));
					
					costIassetDetail.setDept_name((String)mapVo.get("dept_name"));
					
				}else{
					
					costIassetDetail.setDept_code((String)mapVo.get("dept_code"));
					
					costIassetDetail.setDept_name((String)mapVo.get("dept_name"));
					
					err_sb.append("科室不存在 ");
					
				}
				
				byCodeMap.put("source_code", mapVo.get("source_code"));
				
				Source source = sourceService.querySourceByCode(byCodeMap);
				
				if(source != null ){
					
					mapVo.put("source_id", source.getSource_id());
					
					costIassetDetail.setSource_code((String)mapVo.get("source_code"));

					costIassetDetail.setSource_name((String)mapVo.get("source_name"));
					
				}else{
					
					costIassetDetail.setSource_code((String)mapVo.get("source_code"));
					
					costIassetDetail.setSource_name((String)mapVo.get("source_name"));
					
					err_sb.append("资金来源编码不存在 ");
					
				}
				
				byCodeMap.put("acc_year", mapVo.get("acc_year"));
				
				byCodeMap.put("acc_month", mapVo.get("acc_month"));
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("source_id", mapVo.get("source_id"));
				
				byCodeMap.put("source_code", "");
				
				byCodeMap.put("is_flag", 0);
				
				CostIassetDetail data_exc_extis = costIassetDetailService.queryCostIassetDetailByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("当月下存在配置  ");
					
				}
				if (err_sb.toString().length() > 0) {

					costIassetDetail.setError_type(err_sb.toString());

					list_err.add(costIassetDetail);
					
				} else {

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostIassetDetail data_exc = new CostIassetDetail();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costIassetDetailService.addBatchCostIassetDetail(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 科室无形资产折旧明细<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costiassetdetail/addBatchCostIassetDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostIassetDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostIassetDetail> list_err = new ArrayList<CostIassetDetail>();

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
				
				mapVo.put("asset_code", jsonObj.get("asset_code"));
				
				mapVo.put("asset_name", jsonObj.get("asset_name"));

				mapVo.put("asset_type_code", jsonObj.get("asset_type_code"));

				mapVo.put("asset_type_name", jsonObj.get("asset_type_name"));
				
				mapVo.put("source_code", jsonObj.get("source_code"));
				
				mapVo.put("source_name", jsonObj.get("source_name"));

				mapVo.put("depre_amount", jsonObj.get("depre_amount"));

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
				
				CostIassetDetail data_exc_extis = costIassetDetailService.queryCostIassetDetailByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("当月下存在配置  ");
					
				}

				CostIassetDetail costIassetDetail = new CostIassetDetail();

				if (err_sb.toString().length() > 0) {

					costIassetDetail.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());

					costIassetDetail.setAcc_year((mapVo.get("acc_year").toString()));
					
					costIassetDetail.setAcc_month(mapVo.get("acc_month").toString());
					
					costIassetDetail.setDept_code(mapVo.get("dept_code").toString());

					costIassetDetail.setDept_name(mapVo.get("dept_name").toString());

					costIassetDetail.setAsset_code(mapVo.get("asset_code").toString());
					
					costIassetDetail.setAsset_name(mapVo.get("asset_name").toString());
					
					costIassetDetail.setAsset_type_code(mapVo.get("asset_type_code").toString());
					
					costIassetDetail.setAsset_type_name(mapVo.get("asset_type_name").toString());

					costIassetDetail.setSource_code(mapVo.get("source_code").toString());
					
					costIassetDetail.setSource_name(mapVo.get("source_name").toString());

					costIassetDetail.setDepre_amount(Double.valueOf(mapVo.get("depre_amount").toString()));

					costIassetDetail.setError_type(err_sb.toString());

					list_err.add(costIassetDetail);
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costIassetDetailService.addBatchCostIassetDetail(list_batch);

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
	
	@RequestMapping(value = "/hrp/cost/costiassetdetail/addSynData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSynData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String data;
		try {
			data = costIassetDetailService.addSynData(mapVo);
		} catch (Exception e) {
			
			data = e.getMessage();
		}

		return JSONObject.parseObject(data);
	}
}
