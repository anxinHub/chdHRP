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
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;
import com.chd.hrp.cost.entity.CostEmpTypeAttr;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostWageCostRela;
import com.chd.hrp.cost.entity.CostWageItemArrt;
import com.chd.hrp.cost.service.CostEmpKindWageItemSetService;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.serviceImpl.CostEmpTypeAttrServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostWageCostRelaServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostWageItemArrtServiceImpl;
import com.chd.hrp.sys.entity.DeptDict;

/**
 * @Title. @Description. 工资项与成本项目的对应关系
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostWageCostRelaController extends BaseController {
	private static Logger logger = Logger
			.getLogger(CostWageCostRelaController.class);

	@Resource(name = "costWageCostRelaService")
	private final CostWageCostRelaServiceImpl costWageCostRelaService = null;

	@Resource(name = "costEmpTypeAttrService")
	private final CostEmpTypeAttrServiceImpl costEmpTypeAttrService = null;

	@Resource(name = "costWageItemArrtService")
	private final CostWageItemArrtServiceImpl costWageItemArrtService = null;

	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "costEmpKindWageItemSetService")
	private final CostEmpKindWageItemSetService costEmpKindWageItemSetService = null;

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costwagecostrela/costWageCostRelaMainPage", method = RequestMethod.GET)
	public String costWageCostRelaMainPage(Model mode) throws Exception {

		return "hrp/cost/costwagecostrela/costWageCostRelaMain";

	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costwagecostrela/costWageCostRelaExtendPage", method = RequestMethod.GET)
	public String costWageCostRelaExtendPage(Model mode) throws Exception {

		return "hrp/cost/costwagecostrela/costWageCostRelaExtend";

	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costwagecostrela/costWageCostRelaAddPage", method = RequestMethod.GET)
	public String costWageCostRelaAddPage(Model mode) throws Exception {

		return "hrp/cost/costwagecostrela/costWageCostRelaAdd";

	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/addCostWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostWageCostRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		List list_batch = new ArrayList();
		
		List<String> list_wage_item = new ArrayList<String>();
		
		String emp_kind_code = (String) mapVo.get("emp_kind_code");
		
		String wage_item_code = (String) mapVo.get("wage_item_code");
		
		String cost_item_id = (String) mapVo.get("cost_item_id");
		
		String cost_item_no = (String) mapVo.get("cost_item_no");
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		   if(entityMap.get("group_id") == null){
			   
		   
			   entityMap.put("group_id", SessionManager.getGroupId());
			   
			}
		   
			if(entityMap.get("hos_id") == null){
				
				entityMap.put("hos_id", SessionManager.getHosId());
			
			}
			if(entityMap.get("copy_code") == null){
				
				entityMap.put("copy_code", SessionManager.getCopyCode());
	        
			}
		
		/*
		 *判断工资项是否为空,如过为空选择职工分类下全部工资项 
		 * */
         if(StringUtils.isNotEmpty(wage_item_code)){
			
			String[] dept_split = wage_item_code.split(";");
			
			for(int i =0 ; i<dept_split.length; i++){
				
				list_wage_item.add(dept_split[i]);
				
			}
			
		}else{
			
			entityMap.put("emp_kind_code", emp_kind_code);

			List<CostEmpKindWageItemSet> list= costEmpKindWageItemSetService.queryCostEmpKindWageItemSetByEmpKindCode(entityMap);
			
			if(list.size() == 0 ){
				
				return JSONObject.parseObject("{\"error\":\"当前职工分类下没有工资项 \"}");
				
			}
			
			for(int i =0 ;i<list.size();i++){
				
				CostEmpKindWageItemSet empKindWageItem = list.get(i);
				
				list_wage_item.add(empKindWageItem.getWage_item_code());
				
			}
		}
         
			for (int i = 0; i < list_wage_item.size(); i++) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("group_id", SessionManager.getGroupId());
    			    
				map.put("hos_id", SessionManager.getHosId());
    				
				map.put("copy_code", SessionManager.getCopyCode());
    				
				map.put("acc_year", mapVo.get("acc_year").toString());
				
				map.put("acc_month", mapVo.get("acc_month").toString());
				
				map.put("emp_kind_code", emp_kind_code);
				
				map.put("wage_item_code", list_wage_item.get(i));
    				
				map.put("cost_item_id", cost_item_id);
				
				map.put("cost_item_no", cost_item_no);
				
				list_batch.add(map);
				
			   }
			//costWageCostRelaService.deleteBatchCostWageCostRela(list_batch);
			
		String costWageCostRelaJson = costWageCostRelaService.addBatchCostWageCostRela(list_batch);

		return JSONObject.parseObject(costWageCostRelaJson);
		
	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/queryCostWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostWageCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costWageCostRela = costWageCostRelaService
				.queryCostWageCostRela(getPage(mapVo));

		return JSONObject.parseObject(costWageCostRela);

	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/deleteCostWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostWageCostRela(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("id", ids[0]);
			listVo.add(mapVo);
		}
		String costWageCostRelaJson = costWageCostRelaService
				.deleteBatchCostWageCostRela(listVo);
		return JSONObject.parseObject(costWageCostRelaJson);

	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/costWageCostRelaUpdatePage", method = RequestMethod.GET)
	public String costWageCostRelaUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		CostWageCostRela costWageCostRela = new CostWageCostRela();
		costWageCostRela = costWageCostRelaService
				.queryCostWageCostRelaByCode(mapVo);
		mode.addAttribute("id", costWageCostRela.getId());
		mode.addAttribute("group_id", costWageCostRela.getGroup_id());
		mode.addAttribute("hos_id", costWageCostRela.getHos_id());
		mode.addAttribute("copy_code", costWageCostRela.getCopy_code());
		mode.addAttribute("year_month", costWageCostRela.getAcc_year()
				.toString() + costWageCostRela.getAcc_month().toString());

		mode.addAttribute("acc_year", costWageCostRela.getAcc_year());

		mode.addAttribute("acc_month", costWageCostRela.getAcc_month());
		mode.addAttribute("emp_kind_code", costWageCostRela.getEmp_kind_code());
		mode.addAttribute("wage_item_code",
				costWageCostRela.getWage_item_code());
		mode.addAttribute("emp_kind_name", costWageCostRela.getEmp_kind_name());
		mode.addAttribute("wage_item_name",
				costWageCostRela.getWage_item_name());
		mode.addAttribute("cost_item_id", costWageCostRela.getCost_item_id());
		mode.addAttribute("cost_item_no", costWageCostRela.getCost_item_no());
		mode.addAttribute("cost_item_name",
				costWageCostRela.getCost_item_name());
		return "hrp/cost/costwagecostrela/costWageCostRelaUpdate";
	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costwagecostrela/updateCostWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostWageCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costWageCostRelaJson = costWageCostRelaService
				.updateCostWageCostRela(mapVo);

		return JSONObject.parseObject(costWageCostRelaJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costwagecostrela/costWageCostRelaImportPage", method = RequestMethod.GET)
	public String costWageCostRelaImportPage(Model mode) throws Exception {

		return "hrp/cost/costwagecostrela/costWageCostRelaImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costwagecostrela/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {
		printTemplate(request, response, "cost\\基础设置", "工资项与成本项目的对应关系.xls");
		return null;
	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/readCostWageCostRelaFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostWageCostRela> list_err = new ArrayList<CostWageCostRela>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostWageCostRela costWageCostRela = new CostWageCostRela();

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

					costWageCostRela.setYear_month(temp[0]);

					costWageCostRela.setAcc_year(year_month.substring(0, 4));

					costWageCostRela.setAcc_month(year_month.substring(4, 6));

					mapVo.put("acc_year", year_month.substring(0, 4));

					mapVo.put("acc_month", year_month.substring(4, 6));

				} else {

					err_sb.append("统计年月为空  ");

				}
				if (StringUtils.isNotEmpty(temp[1])) {

					costWageCostRela.setEmp_kind_code(temp[1]);

					mapVo.put("emp_kind_code", temp[1]);
				} else {
					err_sb.append("职工分类为空  ");
				}
				if (StringUtils.isNotEmpty(temp[3])) {

					costWageCostRela.setWage_item_code(temp[3]);

					mapVo.put("wage_item_code", temp[3]);
				} else {
					err_sb.append("工资项编码为空  ");
				}
				if (StringUtils.isNotEmpty(temp[5])) {

					// costWageCostRela.setCost_item_id(Long.valueOf(temp[5]));

					mapVo.put("cost_item_code", temp[5]);
				} else {
					err_sb.append("成本项目ID为空  ");
				}

				if (StringUtils.isNotEmpty(temp[2])) {

					costWageCostRela.setEmp_kind_name(temp[2]);
				}
				if (StringUtils.isNotEmpty(temp[4])) {

					costWageCostRela.setWage_item_name(temp[4]);

				}
				if (StringUtils.isNotEmpty(temp[6])) {

					costWageCostRela.setCost_item_name(temp[6]);

				}

				CostWageCostRela data_exc_extis = costWageCostRelaService
						.queryCostWageCostRelaByCode(mapVo);
				// 根据不同业务提示相关信息
				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService
						.queryCostEmpTypeAttrByCode(mapVo);

				CostWageItemArrt costWageItemArrt = costWageItemArrtService
						.queryCostWageItemArrtByCode(mapVo);

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				byCodeMap.put("is_stop", "0");

				CostItemDictNo cidn = costItemDictNoService
						.queryCostItemDictNoByCode(byCodeMap);

				if (cidn != null) {

					mapVo.put("cost_item_id", cidn.getCost_item_id());

					mapVo.put("cost_item_no", cidn.getCost_item_no());

					costWageCostRela.setCost_item_code((String) mapVo
							.get("cost_item_code"));

				} else {

					costWageCostRela.setCost_item_code((String) mapVo
							.get("cost_item_code"));

					err_sb.append("成本项目不存在 ");

				}

				if (costEmpTypeAttr == null) {

					err_sb.append("职工分类编码不存在！ ");

				}

				if (costWageItemArrt == null) {

					err_sb.append("工资项编码不存在！ ");

				}

				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {

					costWageCostRela.setError_type(err_sb.toString());

					list_err.add(costWageCostRela);
				} else {

					list_batch.add(mapVo);

				}
			}
		} catch (DataAccessException e) {

			CostWageCostRela data_exc = new CostWageCostRela();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costWageCostRelaService.addBatchCostWageCostRela(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 工资项与成本项目的对应关系<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/addBatchCostWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostWageCostRela(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostWageCostRela> list_err = new ArrayList<CostWageCostRela>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());

				// mapVo.put("id", jsonObj.get("id"));

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

				mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));

				mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));

				mapVo.put("wage_item_code", jsonObj.get("wage_item_code"));

				mapVo.put("wage_item_name", jsonObj.get("wage_item_name"));

				mapVo.put("cost_item_id", jsonObj.get("cost_item_id"));

				mapVo.put("cost_item_no", jsonObj.get("cost_item_no"));

				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));

				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));

				CostWageCostRela data_exc_extis = costWageCostRelaService
						.queryCostWageCostRelaByCode(mapVo);

				CostEmpTypeAttr costEmpTypeAttr = costEmpTypeAttrService
						.queryCostEmpTypeAttrByCode(mapVo);

				CostWageItemArrt costWageItemArrt = costWageItemArrtService
						.queryCostWageItemArrtByCode(mapVo);

				Map<String, Object> byCodeMap = new HashMap<String, Object>();

				byCodeMap.put("group_id", mapVo.get("group_id"));

				byCodeMap.put("hos_id", mapVo.get("hos_id"));

				byCodeMap.put("copy_code", mapVo.get("copy_code"));

				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				byCodeMap.put("is_stop", "0");

				CostItemDictNo cidn = costItemDictNoService
						.queryCostItemDictNoByCode(byCodeMap);

				if (cidn != null) {

					mapVo.put("cost_item_id", cidn.getCost_item_id());

					mapVo.put("cost_item_no", cidn.getCost_item_no());

				} else {

					err_sb.append("成本项目 不存在 ");

				}

				if (costEmpTypeAttr == null) {

					err_sb.append("职工分类编码不存在！ ");

				}

				if (costWageItemArrt == null) {

					err_sb.append("工资项编码不存在！ ");

				}

				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}

				CostWageCostRela costWageCostRela = new CostWageCostRela();

				if (err_sb.toString().length() > 0) {

					costWageCostRela.setYear_month(mapVo.get("acc_year")
							.toString() + mapVo.get("acc_month").toString());

					costWageCostRela.setAcc_year(mapVo.get("acc_year")
							.toString());

					costWageCostRela.setAcc_month(mapVo.get("acc_month")
							.toString());

					costWageCostRela.setEmp_kind_code(mapVo
							.get("emp_kind_code").toString());

					costWageCostRela.setEmp_kind_name(mapVo
							.get("emp_kind_name").toString());

					costWageCostRela.setWage_item_code(mapVo.get(
							"wage_item_code").toString());

					costWageCostRela.setWage_item_name(mapVo.get(
							"wage_item_name").toString());

					costWageCostRela.setCost_item_code(mapVo.get(
							"cost_item_code").toString());

					costWageCostRela.setCost_item_name(mapVo.get(
							"cost_item_name").toString());

					/*
					 * costWageCostRela.setCost_item_id(Long.valueOf(mapVo.get(
					 * "cost_item_id").toString()));
					 * 
					 * costWageCostRela.setCost_item_no(Long.valueOf(mapVo.get(
					 * "cost_item_no").toString()));
					 */

					costWageCostRela.setError_type(err_sb.toString());

					list_err.add(costWageCostRela);
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costWageCostRelaService.addBatchCostWageCostRela(list_batch);

			}
		} catch (DataAccessException e) {

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

	/**
	 * 2016/10/25 lxj 工资项与成本项目的对应关系<BR>
	 * 继承
	 */
	@RequestMapping(value = "/hrp/cost/costwagecostrela/extendCostWageCostRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendCostWageCostRela(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costWageCostRelaJson = costWageCostRelaService
				.extendCostWageCostRela(mapVo);

		return JSONObject.parseObject(costWageCostRelaJson);

	}
}
