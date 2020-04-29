/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.hrp.acc.entity.AccLeder;
import com.chd.hrp.acc.serviceImpl.AccLederCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccLederServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccSubjServiceImpl;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.serviceImpl.ModStartServiceImpl;

/**
 * @Title. @Description. 财务账表
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AccLederController extends BaseController {
	private static Logger logger = Logger.getLogger(AccLederController.class);

	@Resource(name = "accLederService")
	private final AccLederServiceImpl accLederService = null;

	@Resource(name = "accSubjService")
	private final AccSubjServiceImpl accSubjService = null;

	@Resource(name = "accLederCheckService")
	private final AccLederCheckServiceImpl accLederCheckService = null;

	@Resource(name = "modStartService")
	private final ModStartServiceImpl modStartService = null;

	/**
	 * 财务账表<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/acc/accleder/accLederMainPage", method = RequestMethod.GET)
	public String accLederMainPage(Model mode) throws Exception {

		return "hrp/acc/accleder/accLederMain";

	}

	/**
	 * 财务账表<BR>
	 * 试算平衡页面
	 */
	@RequestMapping(value = "/hrp/acc/accleder/accLederIndexPage", method = RequestMethod.GET)
	public String accLederIndexPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getAccYearMonth().getCurYearMonthAss();
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		return "hrp/acc/accleder/accLederIndex";

	}

	/**
	 * 财务账表<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accleder/accLederAddPage", method = RequestMethod.GET)
	public String accLederAddPage(Model mode) throws Exception {

		return "hrp/acc/accleder/accLederAdd";

	}

	@RequestMapping(value = "/hrp/acc/accleder/queryAccLederCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccLederCheck(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String accLederCheck = "";// accLederCheckService.queryAccLederCheck(getPage(mapVo));

		return JSONObject.parseObject(accLederCheck);

	}

	@RequestMapping(value = "/hrp/acc/accleder/queryAccLederBySubjId", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccLederBySubjId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		mapVo.put("year_month", "1");

		return accLederService.queryAccLederBySubjId(mapVo);

	}

	/**
	 * 财务账表<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accleder/queryAccLeder", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		String accLeder = accLederService.queryAccLeder(getPage(mapVo));

		return JSONObject.parseObject(accLeder);

	}

	/**
	 * 财务账表<BR>
	 * 查询试算平衡
	 */
	@RequestMapping(value = "/hrp/acc/accleder/queryAccLederIndex", method = RequestMethod.POST)
	@ResponseBody

	public Map<String, Object> queryAccLederIndex(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		List<AccLeder> accLederList = accLederService.queryAccLederIndex(mapVo);

		ModStart modStart = new ModStart();

		mapVo.remove("sortname");

		mapVo.put("mod_code", "01");

		modStart = modStartService.queryModStartByCode(mapVo);

		if (modStart != null) {

			mode.addAttribute("acc_time", modStart.getStart_year() + "." + modStart.getStart_month());

		}

		mode.addAttribute("acc_time", SessionManager.getAcctYear() + ".00");

		return JSONObject.parseObject(ChdJson.toJson(accLederList));

	}

	@RequestMapping(value = "/hrp/acc/accleder/deleteAccLeder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccLeder(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] res = id.split("@");

			mapVo.put("acc_month", "00");

			mapVo.put("acc_year", res[4]);

			mapVo.put("copy_code", res[3]);

			mapVo.put("group_id", res[1]);

			mapVo.put("hos_id", res[2]);

			mapVo.put("subj_code", res[0]);// 实际实体类变量

			listVo.add(mapVo);
		}

		String accSubjJson = accLederService.deleteAccLederBySubjId(listVo);

		return JSONObject.parseObject(accSubjJson);

	}

	@RequestMapping(value = "/hrp/acc/accleder/addAccLeder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		String accFinaContentJson = "";
		try {
			accFinaContentJson = accLederService.addAccLeder(mapVo);
		} catch (Exception e) {
			accFinaContentJson = e.getMessage();
		}

		return JSONObject.parseObject(accFinaContentJson);

	}

	/**
	 * 财务账表<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accleder/accLederUpdatePage", method = RequestMethod.GET)

	public String accLederUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		AccLeder accLeder = new AccLeder();

		accLeder = accLederService.queryAccLederByCode(mapVo);

		if (accLeder != null) {

			mode.addAttribute("group_id", accLeder.getGroup_id());

			mode.addAttribute("hos_id", accLeder.getHos_id());

			mode.addAttribute("copy_code", accLeder.getCopy_code());

			mode.addAttribute("acc_year", accLeder.getAcc_year());

			mode.addAttribute("acc_month", accLeder.getAcc_month());

			mode.addAttribute("subj_code", accLeder.getSubj_code());

			mode.addAttribute("subj_name", accLeder.getSubj_name());

			mode.addAttribute("subj_dire", accLeder.getSubj_dire());

			mode.addAttribute("bal_os", accLeder.getBal_os());

			mode.addAttribute("this_od", accLeder.getThis_od());

			mode.addAttribute("this_oc", accLeder.getThis_oc());

			mode.addAttribute("sum_od", accLeder.getSum_od());

			mode.addAttribute("sum_oc", accLeder.getSum_oc());

			mode.addAttribute("end_os", accLeder.getEnd_os());

			mode.addAttribute("is_check", accLeder.getIs_check());

			mode.addAttribute("show_sum", mapVo.get("show_sum"));

			return "hrp/acc/accleder/accLederUpdate";

		}

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		mode.addAttribute("acc_month", mapVo.get("acc_month"));

		mode.addAttribute("subj_code", mapVo.get("subj_code"));

		mode.addAttribute("subj_name", mapVo.get("subj_name"));

		mode.addAttribute("dire", mapVo.get("subj_dire"));

		return "hrp/acc/accleder/accLederAdd";
	}

	/**
	 * 财务账表<BR>
	 * 导入
	 */

	@RequestMapping(value = "/hrp/acc/accleder/importAccLeder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccLeder(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String accLederJson = accLederService.importAccLeder(mapVo);

		return JSONObject.parseObject(accLederJson);
	}

	@RequestMapping(value = "/hrp/acc/accleder/queryGridTitle", method = RequestMethod.GET)
	@ResponseBody

	public String queryGridTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String accLederJson = accLederService.getGridTitle(mapVo);

		return accLederJson;

	}

	@RequestMapping(value = "/hrp/acc/accleder/getSubjItemTitle", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String getSubjItemTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		String accSubj = accLederService.queryGridTitle(mapVo);

		return accSubj;

	}

	// 部门下拉框
	@RequestMapping(value = "/hrp/acc/accleder/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.queryDeptDict(mapVo);

		return dept;

	}

	// 职工下拉框
	@RequestMapping(value = "/hrp/acc/accleder/queryEmp", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmp(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.queryEmp(mapVo);

		return dept;

	}

	// 项目下拉框
	@RequestMapping(value = "/hrp/acc/accleder/queryProjDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryProjDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.queryProjDictDict(mapVo);

		return dept;

	}

	// 库房下拉框
	@RequestMapping(value = "/hrp/acc/accleder/queryStoreDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryStoreDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.queryStoreDictDict(mapVo);

		return dept;

	}

	// 供应商下拉框
	@RequestMapping(value = "/hrp/acc/accleder/querySupDictDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySupDictDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.querySupDictDict(mapVo);

		return dept;

	}

	// 客户下拉框
	@RequestMapping(value = "/hrp/acc/accleder/queryCusDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryCusDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.queryCusDict(mapVo);

		return dept;

	}

	// 自定义下拉框
	@RequestMapping(value = "/hrp/acc/accleder/querySourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		String dept = accLederService.querySourceDict(mapVo);

		return dept;

	}

	// 资金来源下拉框
	@RequestMapping(value = "/hrp/acc/accleder/queryCheckItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCheckItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String dept = accLederService.queryCheckItem(mapVo);

		return dept;

	}

	// 单位字典
	@RequestMapping(value = "/hrp/acc/accleder/queryAccHosInfo", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccHosInfo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String rejson = accLederService.queryHosInfo(mapVo);

		return rejson;
	}

	@RequestMapping(value = "/hrp/acc/accleder/queryModByModCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryModByModCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		String dept = accLederService.queryModByModCode(mapVo);

		return dept;

	}

	@RequestMapping(value = "/hrp/acc/accleder/queryAccLederCheckList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccLederCheckList(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acc_year") == null) {

			mapVo.put("acc_year", SessionManager.getAcctYear());

		}

		Map<String, Object> map = accLederService.queryCheckItemTable(mapVo);

		if (map != null) {

			map.put("COLUMN_CODE1", String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());
			map.put("COLUMN_CODE2", String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());
			map.put("COLUMN_CODE3", String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());
			map.put("COLUMN_CODE4", String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());

			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))) {
				if (map.get("TABLE1") != null) {
					if (StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))) {
						map.put("COLUMN_CHECK1", "");
					} else {

						map.put("COLUMN_CHECK1", map.get("COLUMN_CHECK1") + "_ID");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))) {
						map.put("COLUMN_NO1", "");
					} else {
						map.put("COLUMN_CHECK1_NO", map.get("COLUMN_CHECK1").toString().substring(0, 6) + "_NO");

					}
				}

			} else {
				map.put("COLUMN_NO1", "");
			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))) {
				if (map.get("TABLE2") != null) {
					if (map.get("COLUMN_CHECK2") == null) {
						map.put("COLUMN_CHECK2", "");
					} else {
						map.put("COLUMN_CHECK2", map.get("COLUMN_CHECK2") + "_ID");

					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))) {
						map.put("COLUMN_NO2", "");
					} else {
						map.put("COLUMN_CHECK2_NO", map.get("COLUMN_CHECK2").toString().substring(0, 6) + "_NO");
					}
				}

			} else {

				map.put("COLUMN_NO2", "");
			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))) {
				if (map.get("TABLE3") != null) {
					if (map.get("COLUMN_CHECK3") == null) {
						map.put("COLUMN_CHECK3", "");
					} else {
						map.put("COLUMN_CHECK3", map.get("COLUMN_CHECK3") + "_ID");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))) {
						map.put("COLUMN_NO3", "");
					} else {
						map.put("COLUMN_CHECK3_NO", map.get("COLUMN_CHECK3").toString().substring(0, 6) + "_NO");
					}
				}

			} else {

				map.put("COLUMN_NO3", "");
			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))) {
				if (map.get("TABLE4") != null) {
					if (map.get("COLUMN_CHECK4") == null) {
						map.put("COLUMN_CHECK4", "");
					} else {
						map.put("COLUMN_CHECK4", map.get("COLUMN_CHECK4") + "_ID");

					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))) {
						map.put("COLUMN_NO4", "");
					} else {
						map.put("COLUMN_CHECK4_NO", map.get("COLUMN_CHECK4").toString().substring(0, 6) + "_NO");
					}
				}

			} else {

				map.put("COLUMN_NO4", "");
			}
			mapVo.putAll(map);

		}

		String accLederCheck = accLederService.queryAccLederCheckList(mapVo);

		return JSONObject.parseObject(accLederCheck);

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/acc/accleder/downTemplate", method = RequestMethod.GET)
	public String downTemplate(@RequestParam Map<String, Object> mapVo, Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<List> list = new ArrayList();

		List<String> listdata = new ArrayList<String>();

		Map<String, Object> entityMap = new HashMap<String, Object>();

		if (!"".equals(mapVo.get("subj_code")) && mapVo.get("subj_code") != null) {

			entityMap.put("subj_code", mapVo.get("subj_code"));

			Map<String, Object> titleMap = accLederService.queryTitle(entityMap);

			if (!"".equals(titleMap.get("CHECK1_NAME")) && null != titleMap.get("CHECK1_NAME")) {

				listdata.add(titleMap.get("CHECK1_NAME").toString() + "编码");

				listdata.add(titleMap.get("CHECK1_NAME").toString() + "名称");

			}

			if (!"".equals(titleMap.get("CHECK2_NAME")) && null != titleMap.get("CHECK2_NAME")) {

				listdata.add(titleMap.get("CHECK2_NAME").toString() + "编码");

				listdata.add(titleMap.get("CHECK2_NAME").toString() + "名称");

			}

			if (!"".equals(titleMap.get("CHECK3_NAME")) && null != titleMap.get("CHECK3_NAME")) {

				listdata.add(titleMap.get("CHECK3_NAME").toString() + "编码");

				listdata.add(titleMap.get("CHECK3_NAME").toString() + "名称");

			}

			if (!"".equals(titleMap.get("CHECK4_NAME")) && null != titleMap.get("CHECK4_NAME")) {

				listdata.add(titleMap.get("CHECK4_NAME").toString() + "编码");

				listdata.add(titleMap.get("CHECK4_NAME").toString() + "名称");

			}

		}

		listdata.add("年初余额");

		listdata.add("累计借方");

		listdata.add("累计贷方");

		listdata.add("期初余额");

		list.add(listdata);

		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "\\" + "excelTemplate\\"
				+ "\\acc\\downTemplate\\";

		String downLoadPath = ctxPath + "辅助核算初始账.xls";

		ExcelWriter.exportExcel(new File(downLoadPath), list);

		printTemplate(request, response, "acc\\downTemplate", "辅助核算初始账.xls");

		return null;
	}

	@RequestMapping(value = "/hrp/acc/accleder/accLederImportPage", method = RequestMethod.GET)
	public String accLederImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mode.addAttribute("subj_code", mapVo.get("subj_code"));

		mode.addAttribute("group_id", mapVo.get("group_id"));

		mode.addAttribute("hos_id", mapVo.get("hos_id"));

		mode.addAttribute("copy_code", mapVo.get("copy_code"));

		mode.addAttribute("acc_year", mapVo.get("acc_year"));

		return "hrp/acc/accleder/accLederImport";

	}

	@RequestMapping(value = "/hrp/acc/accleder/accLederMainImportPage", method = RequestMethod.GET)
	public String accLederMainImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		return "hrp/acc/accleder/accLederMainImport";

	}

	/**
	 * @Description 导入数据 科目辅助核算
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/acc/accleder/readAccLederRelaFiles", method = RequestMethod.POST)
	@ResponseBody
	public String readAccLederRelaFiles(@RequestParam Map<String, Object> entityMap, Model mode) throws IOException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		String ipmdata = accLederService.readAccLederRelaFiles(entityMap);
		return ipmdata;
	}

	/**
	 * @Description 导入数据 科目初始账
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/acc/accleder/readAccLederFiles", method = RequestMethod.POST)
	@ResponseBody
	public String readAccLederFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		String accLeder = accLederService.readAccLederFiles(mapVo);
		return accLeder;

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/acc/accleder/downLederTemplate", method = RequestMethod.GET)
	public String downLederTemplate(@RequestParam Map<String, Object> mapVo, Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<List> list = new ArrayList();

		List<String> listdata = new ArrayList<String>();

		listdata.add("科目编码");

		listdata.add("科目名称");

		listdata.add("期初余额");

		/*
		 * listdata.add("本期借方金额");
		 * 
		 * listdata.add("本期贷方金额");
		 */

		listdata.add("本年累计借方金额");

		listdata.add("本年累计贷方金额");

		listdata.add("期末余额");

		list.add(listdata);

		String ctxPath = request.getSession().getServletContext().getRealPath("/") + "\\" + "excelTemplate\\"
				+ "\\acc\\downTemplate\\";

		String downLoadPath = ctxPath + "科目初始账.xls";

		ExcelWriter.exportExcel(new File(downLoadPath), list);

		printTemplate(request, response, "acc\\downTemplate", "科目初始账.xls");

		return null;
	}

	@RequestMapping(value = "hrp/acc/accleder/verifyAccLederIndex", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> verifyAccLederIndex(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("copy_nature", SessionManager.getCopyNature());

		if (mapVo.get("acc_year") == null) {
			mapVo.put("acc_year", SessionManager.getAcctYear());
		}

		String accLederJson = accLederService.verifyAccLederIndex(mapVo);

		return JSONObject.parseObject(accLederJson);
	}

}
