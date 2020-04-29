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
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostMateCostRela;
import com.chd.hrp.cost.entity.CostMateTypeArrt;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostMateCostRelaService;
import com.chd.hrp.cost.service.CostMateTypeArrtService;
import com.chd.hrp.cost.service.CostParaService;

/**
 * @Title. @Description. 材料类别与成本项目的对应关系
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class CostMateCostRelaController extends BaseController {

	private static Logger logger = Logger.getLogger(CostMateCostRelaController.class);

	@Resource(name = "costMateCostRelaService")
	private final CostMateCostRelaService costMateCostRelaService = null;
	
	@Resource(name = "costMateTypeArrtService")
	private final CostMateTypeArrtService costMateTypeArrtService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costmatecostrela/costMateCostRelaMainPage", method = RequestMethod.GET)
	public String costMateCostRelaMainPage(Model mode) throws Exception {

		return "hrp/cost/costmatecostrela/costMateCostRelaMain";

	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costmatecostrela/costMateCostRelaAddPage", method = RequestMethod.GET)
	public String costMateCostRelaAddPage(Model mode) throws Exception {

		return "hrp/cost/costmatecostrela/costMateCostRelaAdd";

	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/addCostMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostMateCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costMateCostRelaJson = costMateCostRelaService.addCostMateCostRela(mapVo);

		return JSONObject.parseObject(costMateCostRelaJson);

	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/queryCostMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostMateCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costMateCostRela = costMateCostRelaService.queryCostMateCostRela(getPage(mapVo));

		return JSONObject.parseObject(costMateCostRela);

	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/deleteCostMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostMateCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		String costMateCostRelaJson = costMateCostRelaService.deleteBatchCostMateCostRela(listVo);
		return JSONObject.parseObject(costMateCostRelaJson);

	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/costMateCostRelaUpdatePage", method = RequestMethod.GET)
	public String costMateCostRelaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		CostMateCostRela costMateCostRela = new CostMateCostRela();
		costMateCostRela = costMateCostRelaService.queryCostMateCostRelaByCode(mapVo);
		mode.addAttribute("id", costMateCostRela.getId());

		mode.addAttribute("group_id", costMateCostRela.getGroup_id());

		mode.addAttribute("hos_id", costMateCostRela.getHos_id());

		mode.addAttribute("copy_code", costMateCostRela.getCopy_code());

		mode.addAttribute("cost_item_id", costMateCostRela.getCost_item_id());

		mode.addAttribute("cost_item_no", costMateCostRela.getCost_item_no());

		mode.addAttribute("mate_type_name", costMateCostRela.getMate_type_name());

		mode.addAttribute("cost_item_name", costMateCostRela.getCost_item_name());

		mode.addAttribute("mate_type_code", costMateCostRela.getMate_type_code());

		return "hrp/cost/costmatecostrela/costMateCostRelaUpdate";
	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costmatecostrela/updateCostMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostMateCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costMateCostRelaJson = costMateCostRelaService.updateCostMateCostRela(mapVo);

		return JSONObject.parseObject(costMateCostRelaJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costmatecostrela/costMateCostRelaImportPage", method = RequestMethod.GET)
	public String costMateCostRelaImportPage(Model mode) throws Exception {

		return "hrp/cost/costmatecostrela/costMateCostRelaImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costmatecostrela/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "材料分类与成本项目的对应关系.xls");

		return null;
	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/readCostMateCostRelaFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostMateCostRela> list_err = new ArrayList<CostMateCostRela>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostMateCostRela costMateCostRela = new CostMateCostRela();

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

					mapVo.put("mate_type_code", temp[0]);
					
				} else {
					
					err_sb.append("材料分类编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					costMateCostRela.setMate_type_name(temp[1]);
					
				}

				
				if (StringUtils.isNotEmpty(temp[2])) {
					
					mapVo.put("cost_item_code", temp[2]);
					
				} else {
					
					err_sb.append("成本项目编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					costMateCostRela.setCost_item_name(temp[3]);
					
				}
				
				
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));
				
				CostMateTypeArrt cmta = costMateTypeArrtService.queryCostMateTypeArrtByCode(byCodeMap);

				if(cmta != null ){
					
					mapVo.put("mate_type_code", cmta.getMate_type_code());
					
					costMateCostRela.setMate_type_code((String)mapVo.get("mate_type_code"));
					
				}else{
					
					costMateCostRela.setMate_type_code((String)mapVo.get("mate_type_code"));
					
					err_sb.append("材料分类不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_code", cidn.getCost_item_code());
					
					costMateCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
				}else{
					
					costMateCostRela.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					err_sb.append("成本项目不存在 ");
					
				}

				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));
				
				CostMateCostRela data_exc_extis = costMateCostRelaService.queryCostMateCostRelaByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("已经存在此对应关系 ");
					
				}
				if (err_sb.toString().length() > 0) {

					costMateCostRela.setError_type(err_sb.toString());

					list_err.add(costMateCostRela);
					
				} else {
					
					mapVo.put("id", "");

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostMateCostRela data_exc = new CostMateCostRela();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costMateCostRelaService.addBatchCostMateCostRela(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 材料类别与成本项目的对应关系<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/addBatchCostMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostMateCostRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostMateCostRela> list_err = new ArrayList<CostMateCostRela>();

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

				mapVo.put("mate_type_code", jsonObj.get("mate_type_code"));

				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
				
				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));
				
				mapVo.put("mate_type_name", jsonObj.get("mate_type_name"));
				
				///////////////////////////////////////////////////////////////////////////////

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));
				
				CostMateTypeArrt cmta = costMateTypeArrtService.queryCostMateTypeArrtByCode(byCodeMap);

				if(cmta != null ){
					
					mapVo.put("mate_type_code", cmta.getMate_type_code());	
					
				}else{
					
					err_sb.append("材料分类编码 不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
				
				byCodeMap.put("is_stop", "0");
				
				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_code", cidn.getCost_item_code());
					
				}else{
					
					err_sb.append("成本项目 不存在 ");
					
				}
				
				byCodeMap.put("mate_type_code", mapVo.get("mate_type_code"));
				
				CostMateCostRela data_exc_extis = costMateCostRelaService.queryCostMateCostRelaByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("已经存在此年月对应关系 ");
					
				}

				CostMateCostRela costMateCostRela = new CostMateCostRela();

				if (err_sb.toString().length() > 0) {

					costMateCostRela.setMate_type_code(mapVo.get("mate_type_code").toString());
					
					costMateCostRela.setMate_type_name(mapVo.get("mate_type_name").toString());

					costMateCostRela.setCost_item_code(mapVo.get("cost_item_code").toString());
					
					costMateCostRela.setCost_item_name(mapVo.get("cost_item_name").toString());

					costMateCostRela.setError_type(err_sb.toString());

					list_err.add(costMateCostRela);
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costMateCostRelaService.addBatchCostMateCostRela(list_batch);

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
	 * 2016/10/26  lxj
	 * 材料类别与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 继承页面
	@RequestMapping(value = "/hrp/cost/costmatecostrela/costMateCostRelaExtendPage", method = RequestMethod.GET)
	public String costMateCostRelaExtendPage(Model mode) throws Exception {

		return "hrp/cost/costmatecostrela/costMateCostRelaExtend";

	}
	
	/**
	 * 2016/10/26 lxj
	 * 材料类别与成本项目的对应关系<BR>
	 * 继承
	 */
	@RequestMapping(value = "/hrp/cost/costmatecostrela/extendCostMateCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostMateCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costMateCostRelaJson = costMateCostRelaService.extendCostMateCostRela(mapVo);

		return JSONObject.parseObject(costMateCostRelaJson);

	}
}
