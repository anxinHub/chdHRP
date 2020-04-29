/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.cost.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
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
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.entity.CostEmpAttr;
import com.chd.hrp.cost.entity.CostEmpBonusDetail;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostBonusSchemeSetServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpAttrServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpBonusDetailServiceImpl;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
 * @Title. @Description. 人员奖金明细数据表
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostEmpBonusDetailController extends BaseController {
	private static Logger logger = Logger
			.getLogger(CostEmpBonusDetailController.class);

	@Resource(name = "costEmpBonusDetailService")
	private final CostEmpBonusDetailServiceImpl costEmpBonusDetailService = null;

	@Resource(name = "costBonusSchemeSetService")
	private final CostBonusSchemeSetServiceImpl costBonusSchemeSetService = null;

	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;

	@Resource(name = "costEmpAttrService")
	private final CostEmpAttrServiceImpl costEmpAttrService = null;

	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;

	/**
	 * 人员奖金明细数据表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costempbonusdetail/costEmpBonusDetailMainPage", method = RequestMethod.GET)
	public String costEmpBonusDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costempbonusdetail/costEmpBonusDetailMain";

	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/costEmpBonusDetailAddPage", method = RequestMethod.GET)
	public String costEmpBonusDetailAddPage(
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
		List<CostBonusSchemeSet> list = null;
		String scheme_id = mapVo.get("scheme_id").toString();
		if (!"".equals(scheme_id) && scheme_id != null) {
			list = costBonusSchemeSetService
					.queryCostBonusSchemeSetByTitleList(mapVo);
			mode.addAttribute("bonusSchemeSetList", list);
			mode.addAttribute("scheme_id", scheme_id);
		} else {
			mode.addAttribute("bonusSchemeSetList",
					list = new ArrayList<CostBonusSchemeSet>());
			mode.addAttribute("scheme_id", "");
		}
		return "hrp/cost/costempbonusdetail/costEmpBonusDetailAdd";
	}

	@RequestMapping(value = "/hrp/cost/costempbonusdetail/queryBonusSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBonusSchemeSet(
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
		String costEmpBonusDetail = "";

		costEmpBonusDetail = costBonusSchemeSetService
				.queryCostBonusSchemeSetByTitle(mapVo);
		return JSONObject.parseObject(costEmpBonusDetail);

	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/addCostEmpBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostEmpBonusDetail(
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

		String costEmpBonusDetailJson = costEmpBonusDetailService
				.addCostEmpBonusDetail(mapVo);

		return JSONObject.parseObject(costEmpBonusDetailJson);

	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/queryCostEmpBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostEmpBonusDetail(
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

		String para_value = MyConfig.getSysPara("03001");

		mapVo.put("is_flag", para_value);

		String costEmpBonusDetail = costEmpBonusDetailService
				.queryCostEmpBonusDetail(getPage(mapVo));

		return JSONObject.parseObject(costEmpBonusDetail);

	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/deleteCostEmpBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpBonusDetail(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
			mapVo.put("dept_no", ids[6]);
			mapVo.put("emp_id", ids[7]);
			mapVo.put("emp_no", ids[8]);
			mapVo.put("emp_kind_code", ids[9]);
			listVo.add(mapVo);
		}
		String costEmpBonusDetailJson = costEmpBonusDetailService
				.deleteBatchCostEmpBonusDetail(listVo);
		return JSONObject.parseObject(costEmpBonusDetailJson);

	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/costEmpBonusDetailUpdatePage", method = RequestMethod.GET)
	public String costEmpBonusDetailUpdatePage(
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
		List<CostBonusSchemeSet> list = null;
		String scheme_id = mapVo.get("scheme_id").toString();
		if (!"".equals(scheme_id) && scheme_id != null) {
			list = costBonusSchemeSetService
					.queryCostBonusSchemeSetByTitleList(mapVo);
			mode.addAttribute("bonusSchemeSetList", list);
			mode.addAttribute("scheme_id", scheme_id);
		} else {
			mode.addAttribute("bonusSchemeSetList",
					list = new ArrayList<CostBonusSchemeSet>());
			mode.addAttribute("scheme_id", "");
		}
		CostEmpBonusDetail costEmpBonusDetail = new CostEmpBonusDetail();

		String para_value = (String) ((SessionManager.getCostParaMap().get(
				"03001") == null) ? 0 : SessionManager.getCostParaMap()
				.get("03001").toString());

		mapVo.put("is_flag", para_value);

		costEmpBonusDetail = costEmpBonusDetailService
				.queryCostEmpBonusDetailByCode(mapVo);
		mode.addAttribute("group_id", costEmpBonusDetail.getGroup_id());
		mode.addAttribute("hos_id", costEmpBonusDetail.getHos_id());
		mode.addAttribute("copy_code", costEmpBonusDetail.getCopy_code());
		mode.addAttribute("year_month", costEmpBonusDetail.getAcc_year().toString() + costEmpBonusDetail.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costEmpBonusDetail.getAcc_year());
		mode.addAttribute("acc_month", costEmpBonusDetail.getAcc_month());
		mode.addAttribute("dept_id", costEmpBonusDetail.getDept_id());
		mode.addAttribute("dept_no", costEmpBonusDetail.getDept_no());
		mode.addAttribute("dept_name", costEmpBonusDetail.getDept_name());
		mode.addAttribute("emp_id", costEmpBonusDetail.getEmp_id());
		mode.addAttribute("emp_no", costEmpBonusDetail.getEmp_no());
		mode.addAttribute("emp_name", costEmpBonusDetail.getEmp_name());
		mode.addAttribute("emp_kind_code",
				costEmpBonusDetail.getEmp_kind_code());
		mode.addAttribute("emp_kind_name",
				costEmpBonusDetail.getEmp_kind_name());
		mode.addAttribute("bonus1", costEmpBonusDetail.getBonus1());
		mode.addAttribute("bonus2", costEmpBonusDetail.getBonus2());
		mode.addAttribute("bonus3", costEmpBonusDetail.getBonus3());
		mode.addAttribute("bonus4", costEmpBonusDetail.getBonus4());
		mode.addAttribute("bonus5", costEmpBonusDetail.getBonus5());
		mode.addAttribute("bonus6", costEmpBonusDetail.getBonus6());
		mode.addAttribute("bonus7", costEmpBonusDetail.getBonus7());
		mode.addAttribute("bonus8", costEmpBonusDetail.getBonus8());
		mode.addAttribute("bonus9", costEmpBonusDetail.getBonus9());
		mode.addAttribute("bonus10", costEmpBonusDetail.getBonus10());
		mode.addAttribute("bonus11", costEmpBonusDetail.getBonus11());
		mode.addAttribute("bonus12", costEmpBonusDetail.getBonus12());
		mode.addAttribute("bonus13", costEmpBonusDetail.getBonus13());
		mode.addAttribute("bonus14", costEmpBonusDetail.getBonus14());
		mode.addAttribute("bonus15", costEmpBonusDetail.getBonus15());
		mode.addAttribute("bonus16", costEmpBonusDetail.getBonus16());
		mode.addAttribute("bonus17", costEmpBonusDetail.getBonus17());
		mode.addAttribute("bonus18", costEmpBonusDetail.getBonus18());
		mode.addAttribute("bonus19", costEmpBonusDetail.getBonus19());
		mode.addAttribute("bonus20", costEmpBonusDetail.getBonus20());
		mode.addAttribute("bonus21", costEmpBonusDetail.getBonus21());
		mode.addAttribute("bonus22", costEmpBonusDetail.getBonus22());
		mode.addAttribute("bonus23", costEmpBonusDetail.getBonus23());
		mode.addAttribute("bonus24", costEmpBonusDetail.getBonus24());
		mode.addAttribute("bonus25", costEmpBonusDetail.getBonus25());
		mode.addAttribute("bonus26", costEmpBonusDetail.getBonus26());
		mode.addAttribute("bonus27", costEmpBonusDetail.getBonus27());
		mode.addAttribute("bonus28", costEmpBonusDetail.getBonus28());
		mode.addAttribute("bonus29", costEmpBonusDetail.getBonus29());
		mode.addAttribute("bonus30", costEmpBonusDetail.getBonus30());
		return "hrp/cost/costempbonusdetail/costEmpBonusDetailUpdate";
	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costempbonusdetail/updateCostEmpBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpBonusDetail(
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

		String costEmpBonusDetailJson = costEmpBonusDetailService
				.updateCostEmpBonusDetail(mapVo);

		return JSONObject.parseObject(costEmpBonusDetailJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/costEmpBonusDetailImportPage", method = RequestMethod.GET)
	public String costEmpBonusDetailImportPage(
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
		List<CostBonusSchemeSet> list = null;
		String scheme_id = mapVo.get("scheme_id").toString();
		if (!"".equals(scheme_id) && scheme_id != null) {
			list = costBonusSchemeSetService
					.queryCostBonusSchemeSetByTitleList(mapVo);
			mode.addAttribute("bonusSchemeSetList", list);
			mode.addAttribute("scheme_id", scheme_id);
		} else {
			mode.addAttribute("bonusSchemeSetList",
					list = new ArrayList<CostBonusSchemeSet>());
			mode.addAttribute("scheme_id", "");
		}
		return "hrp/cost/costempbonusdetail/costEmpBonusDetailImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costempbonusdetail/downTemplate", method = RequestMethod.GET)
	public String downTemplate(String scheme_id, Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		List<List> list = new ArrayList();
		List<String> listdata = new ArrayList<String>();
		listdata.add("统计年");
		listdata.add("统计月");
		listdata.add("科室编码");
		listdata.add("科室名称");
		listdata.add("职工编码");
		listdata.add("职工名称");
		listdata.add("职工分类编码");
		listdata.add("职工分类名称");
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if (!"".equals(scheme_id) && scheme_id != null) {
			entityMap.put("scheme_id", scheme_id);
			List<CostBonusSchemeSet> bonusSchemeSetList = costBonusSchemeSetService
					.queryCostBonusSchemeSetByTitleList(entityMap);
			for (int i = 0; i < bonusSchemeSetList.size(); i++) {
				listdata.add(bonusSchemeSetList.get(i).getBonus_item_name());
			}
		}
		list.add(listdata);
		String ctxPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "\\" + "excelTemplate\\" + "\\cost\\基础设置\\";
		String downLoadPath = ctxPath + "职工奖金明细数据采集.xls";
		ExcelWriter.exportExcel(new File(downLoadPath), list);
		printTemplate(request, response, "cost\\基础设置", "职工奖金明细数据采集.xls");
		return null;
	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 导入
	 * 
	 * @throws Exception
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/readCostEmpBonusDetailFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(String scheme_id, Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws NoSuchMethodException, SecurityException, Exception {

		List<CostEmpBonusDetail> list_err = new ArrayList<CostEmpBonusDetail>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				CostEmpBonusDetail costEmpBonusDetail = new CostEmpBonusDetail();
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
					
					costEmpBonusDetail.setYear_month(temp[0]);
					
					costEmpBonusDetail.setAcc_year(year_month.substring(0, 4));
					
					costEmpBonusDetail.setAcc_month(year_month.substring(4, 6));

					mapVo.put("acc_year", year_month.substring(0, 4));
					
					mapVo.put("acc_month", year_month.substring(4, 6));

				} else {

					err_sb.append("统计年月为空  ");

				}
				if (StringUtils.isNotEmpty(temp[1])) {
					mapVo.put("dept_code", temp[1]);
					DeptDict deptDict = deptDictService
							.queryDeptDictByDeptNo(mapVo);
					costEmpBonusDetail.setDept_code(temp[1]);
					costEmpBonusDetail.setDept_name(temp[2]);
					if (deptDict != null) {
						mapVo.put("dept_id", deptDict.getDept_id());
						mapVo.put("dept_no", deptDict.getDept_no());
					} else {
						err_sb.append("科室不存在  ");
					}
				} else {
					err_sb.append("科室编码为空  ");
				}

				if (StringUtils.isNotEmpty(temp[3])) {
					mapVo.put("emp_code", temp[3]);
					CostEmpAttr empAttr = costEmpAttrService
							.queryCostEmpAttrByCode(mapVo);
					costEmpBonusDetail.setEmp_code(temp[3]);
					costEmpBonusDetail.setEmp_name(temp[4]);
					if (empAttr != null) {
						mapVo.put("emp_id", empAttr.getEmp_id());
						mapVo.put("emp_no", empAttr.getEmp_no());
					} else {
						err_sb.append("职工不存在  ");
					}
				} else {
					err_sb.append("职工编码为空  ");
				}

				if (StringUtils.isNotEmpty(temp[5])) {
					costEmpBonusDetail.setEmp_kind_code(temp[5]);
					costEmpBonusDetail.setEmp_kind_name(temp[6]);
					mapVo.put("emp_kind_code", temp[6]);
				} else {
					err_sb.append("职工分类编码为空  ");
				}
				mapVo.put("scheme_id", scheme_id);
				if (!"".equals(scheme_id) && scheme_id != null) {
					List<CostBonusSchemeSet> bonusSchemeSetList = costBonusSchemeSetService
							.queryCostBonusSchemeSetByTitleList(mapVo);
					for (int j = 0; j < bonusSchemeSetList.size(); j++) {
						String bonus = bonusSchemeSetList.get(j)
								.getBonus_column();
						if (StringUtils.isNotEmpty(temp[7 + j])) {
							Class cls = Class
									.forName("com.chd.hrp.cost.entity.CostEmpBonusDetail");
							Method m = cls.getDeclaredMethod("set"
									+ convertFieldName(bonus), double.class);
							m.invoke(costEmpBonusDetail,
									Double.valueOf(temp[7 + j]));
							mapVo.put(bonus, Double.valueOf(temp[7 + j]));
						} else {
							err_sb.append(bonusSchemeSetList.get(j)
									.getBonus_item_name() + "为空  ");
						}
					}
				}
				CostEmpBonusDetail data_exc_extis = costEmpBonusDetailService
						.queryCostEmpBonusDetailByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					costEmpBonusDetail.setError_type(err_sb.toString());
					list_err.add(costEmpBonusDetail);
				} else {
					String dataJson = costEmpBonusDetailService
							.addCostEmpBonusDetail(mapVo);
				}
			}
		} catch (DataAccessException e) {
			CostEmpBonusDetail data_exc = new CostEmpBonusDetail();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
			e.printStackTrace();
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
	}

	private static String convertFieldName(String fieldName) throws Exception {
		byte[] items = fieldName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/**
	 * 人员奖金明细数据表<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costempbonusdetail/addBatchCostEmpBonusDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostEmpBonusDetail(String scheme_id,
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostEmpBonusDetail> list_err = new ArrayList<CostEmpBonusDetail>();
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
		String s = null;
		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {
				StringBuffer err_sb = new StringBuffer();
				JSONObject jsonObj = JSONObject.parseObject(it.next()
						.toString());
				String year_month = String.valueOf(jsonObj.get("year_month"));
				
				mapVo.put("acc_year", year_month.substring(0, 4));
				
				mapVo.put("acc_month", year_month.substring(4, 6));
				mapVo.put("dept_code", jsonObj.get("dept_code"));
				mapVo.put("dept_name", jsonObj.get("dept_name"));
				DeptDict deptDict = deptDictService
						.queryDeptDictByDeptNo(mapVo);
				if (deptDict != null) {
					mapVo.put("dept_id", deptDict.getDept_id());
					mapVo.put("dept_no", deptDict.getDept_no());
				} else {
					err_sb.append("科室不存在  ");
				}
				mapVo.put("emp_code", jsonObj.get("emp_code"));
				mapVo.put("emp_name", jsonObj.get("emp_name"));
				CostEmpAttr empAttr = costEmpAttrService
						.queryCostEmpAttrByCode(mapVo);
				if (empAttr != null) {
					mapVo.put("emp_id", empAttr.getEmp_id());
					mapVo.put("emp_no", empAttr.getEmp_no());
				} else {
					err_sb.append("职工不存在  ");
				}
				mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
				mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
				if (!"".equals(scheme_id) && scheme_id != null) {
					mapVo.put("scheme_id", scheme_id);
					List<CostBonusSchemeSet> bonusSchemeSetList = costBonusSchemeSetService
							.queryCostBonusSchemeSetByTitleList(mapVo);
					for (int j = 0; j < bonusSchemeSetList.size(); j++) {
						String bonus = bonusSchemeSetList.get(j)
								.getBonus_column();
						mapVo.put(bonus, jsonObj.get(bonus));
					}
				}
				CostEmpBonusDetail data_exc_extis = costEmpBonusDetailService
						.queryCostEmpBonusDetailByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("已经存在！ ");
				}
				CostEmpBonusDetail costEmpBonusDetail = new CostEmpBonusDetail();
				if (err_sb.toString().length() > 0) {
					costEmpBonusDetail.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costEmpBonusDetail.setAcc_year(mapVo.get("acc_year")
							.toString());
					costEmpBonusDetail.setAcc_month(mapVo.get("acc_month")
							.toString());
					costEmpBonusDetail.setDept_code(mapVo.get(
							"dept_code").toString());
					costEmpBonusDetail.setDept_name(mapVo.get(
							"dept_name").toString());
					costEmpBonusDetail.setEmp_code(mapVo.get(
							"emp_code").toString());
					costEmpBonusDetail.setEmp_name(mapVo.get(
							"emp_name").toString());
					costEmpBonusDetail.setEmp_kind_code(mapVo.get(
							"emp_kind_code").toString());
					costEmpBonusDetail.setEmp_kind_code(mapVo.get(
							"emp_kind_name").toString());
					costEmpBonusDetail.setError_type(err_sb.toString());
					list_err.add(costEmpBonusDetail);
				} else {
					mapVo.put("scheme_id", scheme_id);
					if (!"".equals(scheme_id) && scheme_id != null) {	
						List<CostBonusSchemeSet> bonusSchemeSetList = costBonusSchemeSetService
								.queryCostBonusSchemeSetByTitleList(mapVo);
						for (int j = 0; j < bonusSchemeSetList.size(); j++) {
							String bonus = bonusSchemeSetList.get(j)
									.getBonus_column();
							Class cls = Class
									.forName("com.chd.hrp.cost.entity.CostEmpBonusDetail");
							Method m = cls.getDeclaredMethod("set"
									+ convertFieldName(bonus), double.class);
							m.invoke(costEmpBonusDetail,
									Double.valueOf(mapVo.get(bonus).toString()));
						}
					}
					costEmpBonusDetailService.addCostEmpBonusDetail(mapVo);
				}
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
}
