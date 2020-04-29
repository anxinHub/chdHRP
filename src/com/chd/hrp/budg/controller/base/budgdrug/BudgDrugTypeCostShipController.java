/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.base.budgdrug;

import java.io.IOException;
import java.util.ArrayList;
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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgDrugTypeCostShip;
import com.chd.hrp.budg.entity.BudgDrugTypeDict;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeCostShipService;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeDictService;
import com.chd.hrp.budg.service.base.budgwage.BudgWageItemCostShipService;

/**
 * 
 * @Description: 药品分类与预算支出科目对应关系
 * @Table: BUDG_DRUG_TYPE_COST_SHIP
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Controller
public class BudgDrugTypeCostShipController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgDrugTypeCostShipController.class);

	// 引入Service服务
	@Resource(name = "budgDrugTypeCostShipService")
	private final BudgDrugTypeCostShipService budgDrugTypeCostShipService = null;

	@Resource(name = "budgWageItemCostShipService")
	private final BudgWageItemCostShipService budgWageItemCostShipService = null;

	@Resource(name = "budgDrugTypeDictService")
	private final BudgDrugTypeDictService budgDrugTypeDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipMainPage", method = RequestMethod.GET)
	public String budgDrugTypeCostShipMainPage(Model mode) throws Exception {
		return "hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mapVo
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipAddPage", method = RequestMethod.GET)
	public String budgDrugTypeCostShipAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		return "hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipAdd";
	}

	/**
	 * @Description 添加数据 药品分类与预算支出科目对应关系
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/addBudgDrugTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDrugTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String budgDrugTypeCostShipJson  = "";
		
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
			budgDrugTypeCostShipJson = budgDrugTypeCostShipService.add(mapVo);
			
		} catch (Exception e) {
			budgDrugTypeCostShipJson = e.getMessage();
		}
		return JSONObject.parseObject(budgDrugTypeCostShipJson);
		
	}

	/**
	 * @Description 更新跳转页面 药品分类与预算支出科目对应关系
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipUpdatePage", method = RequestMethod.GET)
	public String budgDrugTypeCostShipUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		BudgDrugTypeCostShip budgDrugTypeCostShip = new BudgDrugTypeCostShip();

		budgDrugTypeCostShip = budgDrugTypeCostShipService.queryByCode(mapVo);
		
		if(budgDrugTypeCostShip != null ){
			
			mode.addAttribute("cost_subj_code", budgDrugTypeCostShip.getCost_subj_code());
			
		}
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("budg_year", mapVo.get("budg_year"));
		mode.addAttribute("med_type_id", mapVo.get("med_type_id"));
		mode.addAttribute("med_type_no", mapVo.get("med_type_no"));
		mode.addAttribute("income_subj_code", mapVo.get("income_subj_code"));

		return "hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipUpdate";
	}

	/**
	 * @Description 更新数据 药品分类与预算支出科目对应关系
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/updateBudgDrugTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgDrugTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String budgDrugTypeCostShipJson = "";
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());   
			mapVo.put("hos_id", SessionManager.getHosId());   
			mapVo.put("copy_code", SessionManager.getCopyCode());   
		  
			budgDrugTypeCostShipJson = budgDrugTypeCostShipService.update(mapVo);
			
		} catch (Exception e) {
			budgDrugTypeCostShipJson = e.getMessage();
		}
		return JSONObject.parseObject(budgDrugTypeCostShipJson);
	}

	/**
	 * @Description 更新数据 药品分类与预算支出科目对应关系
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/addOrUpdateBudgDrugTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgDrugTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String budgDrugTypeCostShipJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			budgDrugTypeCostShipJson = budgDrugTypeCostShipService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(budgDrugTypeCostShipJson);
	}

	/**
	 * @Description 删除数据 药品分类与预算支出科目对应关系
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/deleteBudgDrugTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDrugTypeCostShip(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("budg_year", ids[3]);
			mapVo.put("med_type_id", ids[4]);
			mapVo.put("income_subj_code", ids[5]);
			
			listVo.add(mapVo);

		}

		String budgDrugTypeCostShipJson = budgDrugTypeCostShipService.deleteBatch(listVo);

		return JSONObject.parseObject(budgDrugTypeCostShipJson);

	}

	/**
	 * @Description 查询数据 药品分类与预算支出科目对应关系
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/queryBudgDrugTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrugTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		String budgDrugTypeCostShip = budgDrugTypeCostShipService.query(getPage(mapVo));

		return JSONObject.parseObject(budgDrugTypeCostShip);

	}

	/**
	 * @Description 导入跳转页面 药品分类与预算支出科目对应关系
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipImportPage", method = RequestMethod.GET)
	public String budgDrugTypeCostShipImportPage(Model mode) throws Exception {
		return "hrp/budg/base/budgdrug/drugtypecostship/budgDrugTypeCostShipImport";
	}

	/**
	 * @Description 下载导入模版 药品分类与预算支出科目对应关系
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/base/budgdrug/drugtypecostship/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\base", "药品分类与预算支出科目对应关系.xls");

		return null;
	}

	/**
	 * @Description 导入数据 药品分类与预算支出科目对应关系
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/readBudgDrugTypeCostShipFiles", method = RequestMethod.POST)
	public String readBudgDrugTypeCostShipFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<BudgDrugTypeCostShip> list_err = new ArrayList<BudgDrugTypeCostShip>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				BudgDrugTypeCostShip budgDrugTypeCostShip = new BudgDrugTypeCostShip();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				for (int j = i + 1; j < list.size(); j++) {
					String error[] = list.get(j);

					if (temp[0].equals(error[0]) && temp[1].equals(error[1])) {
						err_sb.append("第" + i + "行数据与第 " + j + "行预算年度、药品分类相同 ,多个预算支出科目 不允许对应同一药品分类;");
					}

					if (temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])) {
						err_sb.append("第" + i + "行数据与第 " + j + "行数据重复;");
					}
				}

				if (StringTool.isNotBlank(temp[0])) {
					budgDrugTypeCostShip.setBudg_year(temp[0]);
					mapVo.put("budg_year", temp[0]);
				} else {
					err_sb.append("预算年度为空  ");
				}

				if (StringTool.isNotBlank(temp[1])) {
					budgDrugTypeCostShip.setMed_type_id(Long.parseLong(temp[1]));
					mapVo.put("med_type_id", temp[1]);
					mapVo.put("is_stop", "0");
					BudgDrugTypeCostShip code = budgDrugTypeCostShipService.queryByCode(mapVo);
					if (code != null) {
						err_sb.append("该药品分类与其他预算支出科目已存在对应关系。多个预算支出科目 不允许对应同一药品分类!!");
					}

					BudgDrugTypeDict type = budgDrugTypeDictService.queryByCode(mapVo);

					if (type == null) {
						err_sb.append("该药品分类编码不存在或已停用;");
					} else {
						budgDrugTypeCostShip.setMed_type_id(type.getMed_type_id());
						mapVo.put("drug_type_id", type.getMed_type_id());
					}

				} else {

					err_sb.append("药品分类编码为空;");

				}

				if (StringTool.isNotBlank(temp[2])) {
					//budgDrugTypeCostShip.setSubj_code(temp[2]);
					mapVo.put("subj_code", temp[2]);
					int count = budgWageItemCostShipService.queryCostSubjByCode(mapVo);
					if (count == 0) {
						err_sb.append("该年度支出预算科目不是末级科目或不存在;");
					}

				} else {
					err_sb.append("科目编码为空  ");
				}

				BudgDrugTypeCostShip data_exc_extis = budgDrugTypeCostShipService.queryByCode(mapVo);

				if (data_exc_extis != null) {
					err_sb.append("数据已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					budgDrugTypeCostShip.setError_type(err_sb.toString());
					list_err.add(budgDrugTypeCostShip);
				} else {
					addList.add(mapVo);
				}

			}

			if (list_err.size() == 0) {
				budgDrugTypeCostShipService.addBatch(addList);
			}

		} catch (DataAccessException e) {
			BudgDrugTypeCostShip data_exc = new BudgDrugTypeCostShip();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * 药品类别 下拉框(添加页面用)
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/queryMedTypes", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedTypes(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String item = null;
		if(mapVo.get("is_filter")!= null && "1".equals(mapVo.get("is_filter").toString())){
			item = budgDrugTypeCostShipService.queryMedTypesFilter(mapVo);
		}else{
			item = budgDrugTypeCostShipService.queryMedTypes(mapVo);
		}
		return item;

	}
	
	/**
	 * 添加 页面  预算科目下拉框 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/queryBudgSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("budg_year") == null) {
			mapVo.put("budg_year", SessionManager.getAcctYear());
		}
		
		if(mapVo.get("subj_type").equals("05")){
			mapVo.put("table", "BUDG_COST_SUBJ");
			mapVo.put("column", "cost_subj_code");
		}
		if(mapVo.get("subj_type").equals("04")){
			mapVo.put("table", "BUDG_INCOME_SUBJ");
			mapVo.put("column", "income_subj_code");
		}
		
		String budgSubj = budgDrugTypeCostShipService.queryBudgSubj(mapVo);
		return budgSubj;

	}
	
	/**
	 * @Description 
	 * 继承上一年度药品分类与预算科目对应关系
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/drugtypecostship/extendBudgDrugTypeCostShip", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgDrugTypeCostShip(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId())   ;
		mapVo.put("hos_id", SessionManager.getHosId())   ;
		mapVo.put("copy_code", SessionManager.getCopyCode())   ;
			
		String budgAssetTypyCostShipJson = budgDrugTypeCostShipService.extendBudgDrugTypeCostShip(mapVo);

		return JSONObject.parseObject(budgAssetTypyCostShipJson);
		
	}

}
