
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.controller.base.budgsubj;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.service.AccSubjNatureService;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgCostSubj;
import com.chd.hrp.budg.service.base.budgsubj.BudgAccSubjShipService;
import com.chd.hrp.budg.service.base.budgsubj.BudgCostSubjService;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.serviceImpl.RulesServiceImpl;

/**
 * 
 * @Description: 支出性质
 * @Table: COST_BUDG_SUBJ
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class BudgCostSubjController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgCostSubjController.class);

	// 引入Service服务
	@Resource(name = "budgCostSubjService")
	private final BudgCostSubjService budgCostSubjService = null;

	@Resource(name = "budgAccSubjShipService")
	private final BudgAccSubjShipService budgAccSubjShipService = null;

	@Resource(name = "rulesService")
	private final RulesServiceImpl rulesService = null;

	@Resource(name = "accSubjNatureService")
	private final AccSubjNatureService accSubjNatureService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjMainPage", method = RequestMethod.GET)
	public String budgCostSubjMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjMain";

	}

	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgAccRelationshipMainPage", method = RequestMethod.GET)
	public String budgAccRelationshipMainPage(Model mode) throws Exception {
		mode.addAttribute("subj_type", "05");
		return "hrp/budg/budgaccrelationship/budgAccRelationshipMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjAddPage", method = RequestMethod.GET)
	public String budgCostSubjAddPage(Model mode, String budg_year) throws Exception {
		mode.addAttribute("budg_year", budg_year);
		return "hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjAdd";

	}

	/**
	 * @Description 添加数据 支出性质
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/addBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgCostSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));

		String costBudgSubjJson = budgCostSubjService.addBudgCostSubj(mapVo);

		return JSONObject.parseObject(costBudgSubjJson);

	}

	/**
	 * @Description 更新跳转页面 支出性质
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjUpdatePage", method = RequestMethod.GET)
	public String budgCostSubjUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		BudgCostSubj costBudgSubj = new BudgCostSubj();

		costBudgSubj = budgCostSubjService.queryBudgCostSubjByCode(mapVo);

		mode.addAttribute("group_id", costBudgSubj.getGroup_id());
		mode.addAttribute("hos_id", costBudgSubj.getHos_id());
		mode.addAttribute("copy_code", costBudgSubj.getCopy_code());
		mode.addAttribute("budg_year", costBudgSubj.getBudg_year());
		mode.addAttribute("subj_code", costBudgSubj.getSubj_code());
		mode.addAttribute("subj_name", costBudgSubj.getSubj_name());
		mode.addAttribute("subj_name_all", costBudgSubj.getSubj_name_all());
		// mode.addAttribute("subj_nature", costBudgSubj.getSubj_nature());
		// mode.addAttribute("subj_nature_name", costBudgSubj.getSubj_nature_name());
		mode.addAttribute("super_code", costBudgSubj.getSuper_code());
		mode.addAttribute("super_name", costBudgSubj.getSuper_name());
		mode.addAttribute("subj_level", costBudgSubj.getSubj_level());
		mode.addAttribute("is_last", costBudgSubj.getIs_last());
		mode.addAttribute("is_caarried", costBudgSubj.getIs_caarried());
		mode.addAttribute("spell_code", costBudgSubj.getSpell_code());
		mode.addAttribute("wbx_code", costBudgSubj.getWbx_code());

		return "hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjUpdate";
	}

	/**
	 * @Description 更新数据 支出性质
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/updateBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgCostSubj(@RequestParam Map<String, Object> mapVo, Model mode)
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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));
		String costBudgSubjJson = budgCostSubjService.updateBudgCostSubj(mapVo);

		return JSONObject.parseObject(costBudgSubjJson);
	}

	/**
	 * @Description 删除数据 支出性质
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/deleteBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgCostSubj(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String retErrot = "";
		String useStr = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("budg_year", ids[3]);
			mapVo.put("subj_code", ids[4]);
			mapVo.put("super_code", ids[5]);

			//判断是否被引用
			String str_sup = budgCostSubjService.isExistsDataByTable("budg_cost_subj", ids[4]);
			
			if (Strings.isNotBlank(str_sup)) {
				retErrot = "{\"error\":\"删除失败，选择的科目被以下业务使用：【" + str_sup.substring(0, str_sup.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			if(ids[6].toString().equals("否")){
				useStr+=mapVo.get("subj_code") +",";
			}
		
			// 查询 预算科目是否
			// int isUse = budgAccRelationshipService.queryDelDate(mapVo);
			// if(isUse >0){
			// useStr += mapVo.get("subj_code") +",";
			listVo.add(mapVo);

		}

		if (useStr != null && !"".equals(useStr)) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的数据：【" + useStr.substring(0, useStr.length() - 1)
					+ "】已被使用,不允许删除。\",\"state\":\"false\"}");
		}

		String costBudgSubjJson = budgCostSubjService.deleteBatchBudgCostSubj(listVo);

		return JSONObject.parseObject(costBudgSubjJson);

	}

	/**
	 * @Description 查询数据 支出性质
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/queryBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgCostSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String costBudgSubj = budgCostSubjService.queryBudgCostSubj(getPage(mapVo));

		return JSONObject.parseObject(costBudgSubj);

	}

	/**
	 * @Description 导入跳转页面 支出性质
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjImportPage", method = RequestMethod.GET)
	public String budgCostSubjImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgsubj/budgcostsubj/budgCostSubjImport";

	}

	/**
	 * @Description 下载导入模版 支出性质
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/base/budgsubj/budgcostsubj/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\base", "支出预算科目模版.xls");

		return null;
	}

	/**
	 * @Description 导入数据 支出性质
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/readBudgCostSubjFiles", method = RequestMethod.POST)
	public String readBudgCostSubjFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<BudgCostSubj> list_err = new ArrayList<BudgCostSubj>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				BudgCostSubj costBudgSubj = new BudgCostSubj();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());

				for (int j = i + 1; j < list.size(); j++) {
					String error[] = list.get(j);
					if (temp[0].equals(error[0]) && temp[1].equals(error[1])) {
						err_sb.append("第" + i + "行数据与第 " + j + "行数据重复;");
					}

				}

				if (StringTool.isNotBlank(temp[0])) {

					costBudgSubj.setBudg_year(String.valueOf(temp[0]));
					mapVo.put("budg_year", temp[0]);

				} else {

					err_sb.append("预算年度为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					costBudgSubj.setSubj_code(String.valueOf(temp[1]));
					mapVo.put("subj_code", temp[1]);

				} else {

					err_sb.append("科目编码为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {

					costBudgSubj.setSubj_name(String.valueOf(temp[2]));
					costBudgSubj.setSubj_name_all(String.valueOf(temp[2]));
					mapVo.put("subj_name", temp[2]);
					mapVo.put("subj_name_all", temp[2]);
					mapVo.put("is_last", 1);
					mapVo.put("type_code", "");

				} else {
					err_sb.append("科目名称为空  ");
				}
				/*原科目全称
				if (StringTool.isNotBlank(temp[3])) {

					costBudgSubj.setSubj_name_all(String.valueOf(temp[3]));
					mapVo.put("subj_name_all", temp[3]);

				}
				*/
				
				/*
				 * if (StringTool.isNotBlank(temp[4])) {
				 * 
				 * costBudgSubj.setSubj_nature(String.valueOf(temp[4]));
				 * 
				 * mapVo.put("subj_nature", temp[4]);
				 * 
				 * mapVo.put("subj_nature_code", temp[4]);
				 * 
				 * AccSubjNature nature = accSubjNatureService.queryAccSubjNatureByCode(mapVo) ;
				 * 
				 * if(nature == null ){ err_sb.append("科目性质不存在;"); }
				 * 
				 * } else {
				 * 
				 * err_sb.append("科目性质为空  ");
				 * 
				 * }
				 */
				/*原是否末级
				if (StringTool.isNotBlank(temp[4])) {
					if ("是".equals(String.valueOf(temp[4]))) {
						costBudgSubj.setIs_last(1);
						mapVo.put("is_last", 1);
					} else if ("否".equals(String.valueOf(temp[4]))) {
						costBudgSubj.setIs_last(0);
						mapVo.put("is_last", 0);
					} else {
						costBudgSubj.setIs_last(Integer.valueOf(temp[4]));
						mapVo.put("is_last", temp[4]);
					}
				} else {
					err_sb.append("是否末级为空  ");
				}
				*/
				if (StringTool.isNotBlank(temp[3])) {
					if ("是".equals(String.valueOf(temp[3]))) {
						costBudgSubj.setIs_last(1);
						mapVo.put("is_caarried", 1);
					} else if ("否".equals(String.valueOf(temp[3]))) {
						costBudgSubj.setIs_last(0);
						mapVo.put("is_caarried", 0);
					} else {
						costBudgSubj.setIs_caarried(Integer.parseInt(String.valueOf(temp[3])));
						mapVo.put("is_caarried", temp[3]);
					}
				} else {
					err_sb.append("是否结转为空  ");
				}
				String rules = new String();
				try {
					rules = getRules(mapVo);

					if (rules == null || rules == "") {
						err_sb.append("预算支出科目编码规则未设置！");

					} else {

						String subj_code = (String) mapVo.get("subj_code");
						String[] ruless = rules.split("-");
						Map<Integer, Integer> maxNumMap = new HashMap<Integer, Integer>();

						Map<Integer, Integer> position = new HashMap<Integer, Integer>();

						int super_num = 0;

						for (int j = 0; j < ruless.length; j++) {
							int num = Integer.parseInt(ruless[j].replace(" ", ""));
							super_num += num;
							maxNumMap.put(super_num, j + 1);
							position.put(j + 1, super_num);
						}

						if (maxNumMap.containsKey(subj_code.length())) {// 编码匹配
							int subj_level = maxNumMap.get(subj_code.length());
							mapVo.put("subj_level", subj_level);
							if (subj_level == 1) {
								mapVo.put("super_code", "0");
							} else {
								int super_level = subj_level - 1;// 上级级次
								int subPosition = position.get(super_level);// 上级级次位置
								String super_code = subj_code.substring(0, subPosition);// 截取上级编码
								mapVo.put("super_code", super_code);
								
					        	 Map<String,Object> supMap=budgCostSubjService.querySup(mapVo);
					        	 int count =0;
					        	 for(int m = 1 ; m < list.size(); m++){					        		 
										String subjcodes[] = list.get(m);
										if(m!=i && super_code.equals(subjcodes[1])){
											count =1;
											break;
										}											
								 }
					        	 if(supMap==null && count== 0 ){
					        		 err_sb.append("上级编码不存在！"); 
					        	 }
							}
						} else {
							err_sb.append("不符合编码规则！");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				BudgCostSubj data_exc_extis = budgCostSubjService.queryBudgCostSubjByCode(mapVo);
				// BudgAccRelationship budgRealtionship =
				// budgAccRelationshipService.queryBudgAccRelationshipByCode(mapVo);
				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				/*
				 * if(budgRealtionship != null){ err_sb.append("对应会计科目 对应关系已存在！ "); }
				 */
				if (err_sb.toString().length() > 0) {

					costBudgSubj.setError_type(err_sb.toString());

					list_err.add(costBudgSubj);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));

					addList.add(mapVo);

				}

			}

			if (list_err.size() == 0) {
				String dataJson = budgCostSubjService.addBatchBudgCostSubj(addList);
			}
		} catch (DataAccessException e) {

			BudgCostSubj data_exc = new BudgCostSubj();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 支出性质
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/addBatchBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchBudgCostSubj(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<BudgCostSubj> list_err = new ArrayList<BudgCostSubj>();

		JSONArray json = JSONArray.parseArray(paramVo);

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

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				BudgCostSubj costBudgSubj = new BudgCostSubj();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("budg_year"))) {

					costBudgSubj.setBudg_year(String.valueOf(jsonObj.get("budg_year")));
					mapVo.put("budg_year", jsonObj.get("budg_year"));

				} else {

					err_sb.append("预算年度为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("subj_code"))) {

					costBudgSubj.setSubj_code(String.valueOf(jsonObj.get("subj_code")));
					mapVo.put("subj_code", jsonObj.get("subj_code"));

				} else {

					err_sb.append("科目编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("subj_name"))) {

					costBudgSubj.setSubj_name(String.valueOf(jsonObj.get("subj_name")));
					mapVo.put("subj_name", jsonObj.get("subj_name"));

				} else {

					err_sb.append("科目名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("subj_name_all"))) {

					costBudgSubj.setSubj_name_all(String.valueOf(jsonObj.get("subj_name_all")));
					mapVo.put("subj_name_all", jsonObj.get("subj_name_all"));

				} else {

					err_sb.append("科目全称为空  ");

				}
				/*
				 * if (StringTool.isNotBlank(jsonObj.get("subj_nature"))) {
				 * 
				 * costBudgSubj.setSubj_nature(String.valueOf(jsonObj.get("subj_nature")));
				 * mapVo.put("subj_nature", jsonObj.get("subj_nature"));
				 * 
				 * } else {
				 * 
				 * err_sb.append("科目性质为空  ");
				 * 
				 * }
				 */
				if (StringTool.isNotBlank(jsonObj.get("is_last"))) {

					costBudgSubj.setIs_last(Integer.valueOf(String.valueOf(jsonObj.get("is_last"))));

					mapVo.put("is_last", jsonObj.get("is_last"));

				} else {

					err_sb.append("是否末级为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("acc_subj_code2"))) {

					costBudgSubj.setIs_caarried(Integer.parseInt(String.valueOf(jsonObj.get("acc_subj_code2"))));
					mapVo.put("is_caarried", jsonObj.get("is_caarried"));

				} else {

					err_sb.append("是否结转为空  ");

				}
				String rules = new String();
				try {
					rules = getRules(mapVo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String subj_code = (String) mapVo.get("subj_code");
				String[] ruless = rules.split("-");
				Map<Integer, Integer> maxNumMap = new HashMap<Integer, Integer>();

				Map<Integer, Integer> position = new HashMap<Integer, Integer>();

				int super_num = 0;

				for (int j = 0; j < ruless.length; j++) {
					int num = Integer.parseInt(ruless[j].replace(" ", ""));
					super_num += num;
					maxNumMap.put(super_num, j + 1);
					position.put(j + 1, super_num);
				}

				if (maxNumMap.containsKey(subj_code.length())) {// 编码匹配
					int subj_level = maxNumMap.get(subj_code.length());
					mapVo.put("subj_level", subj_level);
					if (subj_level == 1) {
						mapVo.put("super_code", "0");
					} else {
						int super_level = subj_level - 1;// 上级级次
						int subPosition = position.get(super_level);// 上级级次位置
						String super_code = subj_code.substring(0, subPosition);// 截取上级编码
						mapVo.put("super_code", super_code);
					}
				} else {
					err_sb.append("添加失败 不符合编码规则 请重新输入！");
				}
				BudgCostSubj data_exc_extis = budgCostSubjService.queryBudgCostSubjByCode(mapVo);

				BudgAccSubjShip budgRealtionship = budgAccSubjShipService.queryBudgAccSubjShipByCode(mapVo);
				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (budgRealtionship != null) {
					err_sb.append("对应会计科目 对应关系已存在！ ");
				}
				if (err_sb.toString().length() > 0) {

					costBudgSubj.setError_type(err_sb.toString());

					list_err.add(costBudgSubj);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("subj_name").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("subj_name").toString()));

					String dataJson = budgCostSubjService.addBudgCostSubj(mapVo);

				}

			}

		} catch (DataAccessException e) {

			BudgCostSubj data_exc = new BudgCostSubj();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

	/**
	 * 获取会计科目编码规则
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("proj_code", "BUDG_COST_SUBJ");

		mapVo.put("mod_code", "02");

		Rules rules = rulesService.queryRulesByCode(mapVo);

		StringBuffer sb = new StringBuffer();

		for (int i = 1; i <= 10; i++) {
			Method m = (Method) rules.getClass().getMethod("get" + ("Level" + i));
			Object obj = m.invoke(rules, new Object[] {});
			if (obj.equals("0")) {
				break;
			}
			if (i == 10) {
				sb.append(obj);
			} else {
				sb.append(obj + "-");
			}

		}
		return sb.toString();
	}

	/**
	 * 获取上级编码 、科目级次
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/getSuperCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSuperCode(@RequestParam Map<String, Object> mapVo, Model mode) {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String deptJson = budgCostSubjService.getSuperCode(mapVo);
		return JSONObject.parseObject(deptJson);
	}

	/**
	 * 继承上一年度的支出预算科目及与会计科目的对应关系
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/extendBudgCostSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendBudgCostSubj(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String costBudgSubj = budgCostSubjService.extendBudgCostSubj(mapVo);

		return JSONObject.parseObject(costBudgSubj);

	}
	/**
	 * 批量修改
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgBathUpdateMainPage", method = RequestMethod.GET)
	public String budgBathUpdateMainPage(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		mode.addAttribute("subj_code", paramVo);
		return "hrp/budg/base/budgsubj/budgcostsubj/budgBathUpdateMainPage";
	}
	/**
	 * @Description 
	 * 更新数据 支出预算科目类别
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgsubj/budgcostsubj/budgBathUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> budgBathUpdate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
				
			mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("budg_year") == null){
			
			mapVo.put("budg_year", SessionManager.getAcctYear());
        
		}
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		String subj_code=mapVo.get("subj_code").toString();
		for ( String id: subj_code.split(",")) {
			Map<String, Object> mapV=new HashMap<String, Object>();
			String[] ids=id.split("@");
			
			//表的主键
			mapV.put("group_id", SessionManager.getGroupId());
			mapV.put("hos_id", SessionManager.getHosId());
			mapV.put("copy_code", SessionManager.getCopyCode());
			mapV.put("budg_year",  ids[0]);
			
			mapV.put("type_code",  mapVo.get("type_code"));
			mapV.put("subj_code", ids[1]);
			
			listVo.add(mapV);
		}
		String costBudgSubjJson = budgCostSubjService.updateBatchCostType(listVo);
		
		return JSONObject.parseObject(costBudgSubjJson);
	}
}
