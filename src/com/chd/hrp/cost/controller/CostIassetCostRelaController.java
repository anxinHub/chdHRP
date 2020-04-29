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
import com.chd.hrp.cost.entity.CostFassetCostRela;
import com.chd.hrp.cost.entity.CostIassetCostRela;
import com.chd.hrp.cost.entity.CostIassetTypeArrt;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.service.CostIassetCostRelaService;
import com.chd.hrp.cost.service.CostIassetTypeArrtService;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostParaService;

/**
 * @Title. @Description. 无形资产分类与成本项目的对应关系
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostIassetCostRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(CostIassetCostRelaController.class);

	@Resource(name = "costIassetCostRelaService")
	private final CostIassetCostRelaService costIassetCostRelaService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "costIassetTypeArrtService")
	private final CostIassetTypeArrtService costIassetTypeArrtService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costiassetcostrela/costIassetCostRelaMainPage", method = RequestMethod.GET)
	public String costIassetCostRelaMainPage(Model mode) throws Exception {

		return "hrp/cost/costiassetcostrela/costIassetCostRelaMain";

	}

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/costIassetCostRelaAddPage", method = RequestMethod.GET)
	public String costIassetCostRelaAddPage(Model mode) throws Exception {

		return "hrp/cost/costiassetcostrela/costIassetCostRelaAdd";

	}

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/addCostIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostIassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIassetCostRelaJson = costIassetCostRelaService.addCostIassetCostRela(mapVo);

		return JSONObject.parseObject(costIassetCostRelaJson);

	}

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/queryCostIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostIassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String para_value =MyConfig.getSysPara("03001");
		mapVo.put("is_flag", para_value);
		
		
		String costIassetCostRela = costIassetCostRelaService.queryCostIassetCostRela(getPage(mapVo));

		return JSONObject.parseObject(costIassetCostRela);

	}

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/deleteCostIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostIassetCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("id", ids[3]);

			listVo.add(mapVo);
		}
		String costIassetCostRelaJson = costIassetCostRelaService.deleteBatchCostIassetCostRela(listVo);
		return JSONObject.parseObject(costIassetCostRelaJson);

	}

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/costIassetCostRelaUpdatePage", method = RequestMethod.GET)
	public String costIassetCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		CostIassetCostRela costIassetCostRela = new CostIassetCostRela();
		costIassetCostRela = costIassetCostRelaService.queryCostIassetCostRelaByCode(mapVo);
		mode.addAttribute("id", costIassetCostRela.getId());

		mode.addAttribute("group_id", costIassetCostRela.getGroup_id());

		mode.addAttribute("hos_id", costIassetCostRela.getHos_id());

		mode.addAttribute("copy_code", costIassetCostRela.getCopy_code());

		mode.addAttribute("cost_item_code", costIassetCostRela.getCost_item_code());

		mode.addAttribute("cost_item_name", costIassetCostRela.getCost_item_name());

		mode.addAttribute("asset_type_name", costIassetCostRela.getAsset_type_name());

		mode.addAttribute("asset_type_code", costIassetCostRela.getAsset_type_code());

		return "hrp/cost/costiassetcostrela/costIassetCostRelaUpdate";
	}

	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costiassetcostrela/updateCostIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostIassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costIassetCostRelaJson = costIassetCostRelaService.updateCostIassetCostRela(mapVo);

		return JSONObject.parseObject(costIassetCostRelaJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/costIassetCostRelaImportPage", method = RequestMethod.GET)
	public String costIassetCostRelaImportPage(Model mode) throws Exception {

		return "hrp/cost/costiassetcostrela/costIassetCostRelaImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costiassetcostrela/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "无形资产分类与成本项目的对应关系.xls");

		return null;
	}

	
	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/readCostIassetCostRelaFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostFassetCostRela> list_err = new ArrayList<CostFassetCostRela>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostFassetCostRela costFassetCostRela = new CostFassetCostRela();

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

					mapVo.put("asset_type_code", temp[0]);
					
				} else {
					
					err_sb.append("资产分类编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costFassetCostRela.setAsset_type_name(temp[1]);
					
				}
				
				
				if (StringUtils.isNotEmpty(temp[2])) {

					mapVo.put("cost_item_code", temp[2]);
					
				} else {
					
					err_sb.append("成本项目编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					costFassetCostRela.setCost_item_name(temp[3]);
					
				}

				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostIassetTypeArrt cfta = costIassetTypeArrtService.queryCostIassetTypeArrtByCode(byCodeMap);

				if(cfta != null ){
					
					mapVo.put("asset_type_code", cfta.getAsset_type_code());
					
					costFassetCostRela.setAsset_type_code((String)mapVo.get("asset_type_code"));
					
				}else{
					
					costFassetCostRela.setAsset_type_code((String)mapVo.get("asset_type_code"));
					
					err_sb.append("无形资产分类不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_code", cidn.getCost_item_code());
					
					costFassetCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
				}else{
					
					costFassetCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostIassetCostRela data_exc_extis = costIassetCostRelaService.queryCostIassetCostRelaByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("对应关系已经存在！ ");
					
				}
				
				if (err_sb.toString().length() > 0) {

					costFassetCostRela.setError_type(err_sb.toString());

					list_err.add(costFassetCostRela);
					
				} else {
					
					mapVo.put("id", "");

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostFassetCostRela data_exc = new CostFassetCostRela();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costIassetCostRelaService.addBatchCostIassetCostRela(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}
	/**
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/addBatchCostIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostIassetCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostIassetCostRela> list_err = new ArrayList<CostIassetCostRela>();

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
				
				mapVo.put("id", "");

				mapVo.put("asset_type_code", jsonObj.get("asset_type_code"));
				
				mapVo.put("asset_type_name", jsonObj.get("asset_type_name"));

				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));

				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));
				
				///////////////////////////////////////////////////////////////////////////////
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostIassetTypeArrt cfta = costIassetTypeArrtService.queryCostIassetTypeArrtByCode(byCodeMap);
				if(cfta != null ){
				
					mapVo.put("asset_type_code", cfta.getAsset_type_code());	
				
				}else{
				
					err_sb.append("无形资产分类编码 不存在 ");
				
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");
				
				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);
				
				if(cidn != null ){
				
					mapVo.put("cost_item_code", cidn.getCost_item_code());
					
				}else{
				
					err_sb.append("成本项目 不存在 ");
				
				}

				byCodeMap.put("asset_type_code", mapVo.get("asset_type_code"));
				
				CostIassetCostRela data_exc_extis = costIassetCostRelaService.queryCostIassetCostRelaByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}

				CostIassetCostRela costIassetCostRela = new CostIassetCostRela();

				if (err_sb.toString().length() > 0) {
					
					costIassetCostRela.setAsset_type_code(mapVo.get("asset_type_code").toString());
					
					costIassetCostRela.setAsset_type_name(mapVo.get("asset_type_name").toString());

					costIassetCostRela.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costIassetCostRela.setAcc_year(mapVo.get("acc_year").toString());
					
					costIassetCostRela.setCost_item_code(mapVo.get("cost_item_code").toString());
					
					costIassetCostRela.setCost_item_name(mapVo.get("cost_item_name").toString());

					costIassetCostRela.setError_type(err_sb.toString());

					list_err.add(costIassetCostRela);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costIassetCostRelaService.addBatchCostIassetCostRela(list_batch);

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
	 * 2016/10/26 lxj
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 继承页面
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/costIassetCostRelaExtendPage", method = RequestMethod.GET)
	public String costIassetCostRelaExtendPage(Model mode) throws Exception {

		return "hrp/cost/costiassetcostrela/costIassetCostRelaExtendPage";

	}
	
	/**
	 * 2016/10/26 lxj
	 * 无形资产分类与成本项目的对应关系<BR>
	 * 继承
	 */
	@RequestMapping(value = "/hrp/cost/costiassetcostrela/extendCostIassetCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostIassetCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costIassetCostRelaJson = costIassetCostRelaService.extendCostIassetCostRela(mapVo);

		return JSONObject.parseObject(costIassetCostRelaJson);

	}

}
