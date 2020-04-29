/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.measure;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.measure.AssMeasureRec;
import com.chd.hrp.ass.service.measure.AssMeasureRecDetailService;
import com.chd.hrp.ass.service.measure.AssMeasureRecService;

/**
 * 
 * @Description: 051204 检测计量记录
 * @Table: ASS_MEASURE_REC
 * @Author: bell
 * @email: bell@s-chd.com 
 * @Version: 1.0 
 */

@Controller
public class AssMeasureRecController extends BaseController { 

	private static Logger logger = Logger.getLogger(AssMeasureRecController.class);

	// 引入Service服务
	@Resource(name = "assMeasureRecService")
	private final AssMeasureRecService assMeasureRecService = null;

	@Resource(name = "assMeasureRecDetailService")
	private final AssMeasureRecDetailService assMeasureRecDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/assMeasureRecMainPage", method = RequestMethod.GET)
	public String assMeasureRecMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05106",MyConfig.getSysPara("05106"));
		
		return "hrp/ass/assmeasurerec/assMeasureRecMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/assMeasureRecAddPage", method = RequestMethod.GET)
	public String assMeasureRecAddPage(Model mode) throws Exception {

		return "hrp/ass/assmeasurerec/assMeasureRecAdd";

	}
	
	/**
	 * @Description 添加页面跳转 引入资产卡片
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/assMeasureRecImportPage", method = RequestMethod.GET)
	public String assMeasureRecImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("dept_id", mapVo.get("dept_id"));
		mode.addAttribute("dept_no", mapVo.get("dept_no"));
		mode.addAttribute("ass_nature", mapVo.get("ass_nature"));
		
		return "hrp/ass/assmeasurerec/assMeasureRecImports";

	}

	/**
	 * @Description 添加数据 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/addAssMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMeasureRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {
			mapVo.put("group_id", SessionManager.getGroupId());
	
			mapVo.put("hos_id", SessionManager.getHosId());
	
			mapVo.put("copy_code", SessionManager.getCopyCode());
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
			mapVo.put("create_date", sdf.format(new Date()));
	
			mapVo.put("create_emp", SessionManager.getUserId());
	
			mapVo.put("state", "0");
	
			mapVo.put("audit_emp", "");
	
			mapVo.put("audit_date", "");
	
			if (mapVo.get("outer_measure_engr").toString() == "") {
	
				mapVo.put("outer_measure_engr", "");
			} else {
	
				mapVo.put("outer_measure_engr", mapVo.get("outer_measure_engr"));
	
			}
	
			if (mapVo.get("inner_measure_emp").toString() == "") {
	
				mapVo.put("inner_measure_emp", "");
			} else {
	
				mapVo.put("inner_measure_emp", mapVo.get("inner_measure_emp"));
	
			}
			if (mapVo.get("measure_result").toString() == "") {
				
				mapVo.put("measure_result", "");
			} else {
	
				mapVo.put("measure_result", mapVo.get("measure_result"));
	
			}
		
			//String assMeasureRecJson = assMeasureRecService.addOrUpdateAssMeasureRec(mapVo);
			/*JSONObject jsonObj = JSONArray.parseObject(assMeasureRecJson);
			String rec_id = (String) jsonObj.get("rec_id");
			String seq_no = (String) jsonObj.get("seq_no");
			String assMeasureRecDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());
				
				
				detailVo.put("measure_result", mapVo.get("measure_result"));
				
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("rec_id", rec_id);

				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				}
				
				mapVo.put("plan_measure_date", mapVo.get("plan_measure_date").toString());*/
				
				/*String yearmonth = mapVo.get("plan_measure_date").toString().substring(0, 4) + mapVo.get("plan_measure_date").toString().substring(5, 7);
				//启动系统时间
				Map<String, Object> start = SessionManager.getModStartMonth();
				
				String startyearmonth = (String) start.get("05");
				
				int result = startyearmonth.compareTo(yearmonth);
				
				boolean b =	SessionManager.getAccYearMonthIsCheckOut(yearmonth,"ass_flag");
				
				if(result > 0 ){
					
					return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
					
				} else if(b == true){
					
					return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
					
				}*/
				
				//assMeasureRecDetailJson = assMeasureRecDetailService.addOrUpdateAssMeasureRecDetail(detailVo);

			/*}
			JSONObject json = JSONArray.parseObject(assMeasureRecDetailJson);
			json.put("rec_id", rec_id);
			json.put("seq_no", seq_no);*/
			return JSONObject.parseObject(assMeasureRecService.addOrUpdateMeasureRec(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

	}

	/**
	 * @Description 更新跳转页面 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/assMeasureRecUpdatePage", method = RequestMethod.GET)
	public String assMeasureRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssMeasureRec assMeasureRec = new AssMeasureRec();

		assMeasureRec = assMeasureRecService.queryAssMeasureRecByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assMeasureRec.getGroup_id());

		mode.addAttribute("hos_id", assMeasureRec.getHos_id());

		mode.addAttribute("copy_code", assMeasureRec.getCopy_code());

		mode.addAttribute("rec_id", assMeasureRec.getRec_id());

		mode.addAttribute("seq_no", assMeasureRec.getSeq_no());

		mode.addAttribute("plan_id", assMeasureRec.getPlan_id());

		mode.addAttribute("cert_no", assMeasureRec.getCert_no());

		mode.addAttribute("ass_nature", assMeasureRec.getAss_nature());

		mode.addAttribute("naturs_name", assMeasureRec.getNaturs_name());

		if (assMeasureRec.getOuter_measure_engr() == null) {

			mode.addAttribute("outer_measure_engr", "");

		} else {

			mode.addAttribute("outer_measure_engr", assMeasureRec.getOuter_measure_engr());

		}

		if (assMeasureRec.getInner_measure_emp() == null) {

			mode.addAttribute("inner_measure_emp", "");
		} else {

			mode.addAttribute("inner_measure_emp", assMeasureRec.getInner_measure_emp());
			mode.addAttribute("inner_measure_emp_name", assMeasureRec.getInner_measure_emp_name());

		}

		if (assMeasureRec.getPlan_measure_date() == null) {

			mode.addAttribute("plan_measure_date", "");
		} else {

			mode.addAttribute("plan_measure_date", sdf.format(assMeasureRec.getPlan_measure_date()));

		}

		if (assMeasureRec.getFact_measure_date() == null) {

			mode.addAttribute("fact_measure_date", "");
		} else {

			mode.addAttribute("fact_measure_date", sdf.format(assMeasureRec.getFact_measure_date()));

		}

		if (assMeasureRec.getPre_next_date() == null) {

			mode.addAttribute("pre_next_date", "");
		} else {

			mode.addAttribute("pre_next_date", sdf.format(assMeasureRec.getPre_next_date()));

		}

		if (assMeasureRec.getCreate_emp() == null) {

			mode.addAttribute("create_emp", "");
		} else {

			mode.addAttribute("create_emp", assMeasureRec.getCreate_emp());

		}

		if (assMeasureRec.getCreate_date() == null) {

			mode.addAttribute("create_date", "");
		} else {

			mode.addAttribute("create_date", sdf.format(assMeasureRec.getCreate_date()));

		}

		if (assMeasureRec.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");
		} else {

			mode.addAttribute("audit_emp", assMeasureRec.getAudit_emp());

		}

		if (assMeasureRec.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");
		} else {

			mode.addAttribute("audit_date", sdf.format(assMeasureRec.getAudit_date()));

		}


		mode.addAttribute("measure_kind", assMeasureRec.getMeasure_kind());

		mode.addAttribute("outer_measure_org", assMeasureRec.getOuter_measure_org());
		
		mode.addAttribute("outer_measure_engr_name", assMeasureRec.getOuter_measure_engr_name());
		
		mode.addAttribute("outer_measure_engr", assMeasureRec.getOuter_measure_engr());

		mode.addAttribute("create_emp_name", assMeasureRec.getCreate_emp_name());

		mode.addAttribute("audit_emp_name", assMeasureRec.getAudit_emp_name());

		mode.addAttribute("deal_emp_name", assMeasureRec.getDeal_emp_name());

		mode.addAttribute("measure_type", assMeasureRec.getMeasure_type());

		mode.addAttribute("state", assMeasureRec.getState());

		mode.addAttribute("measure_result", assMeasureRec.getMeasure_result());

		mode.addAttribute("measure_idea", assMeasureRec.getMeasure_idea());
		
		mode.addAttribute("entrust_no", assMeasureRec.getEntrust_no());
		
		mode.addAttribute("firm_basis", assMeasureRec.getFirm_basis());

		return "hrp/ass/assmeasurerec/assMeasureRecUpdate";
	}

	/**
	 * @Description 更新数据 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/updateAssMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMeasureRec(@RequestParam Map<String, Object> mapVo, Model mode)
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

		if (mapVo.get("outer_measure_engr").toString() == "") {

			mapVo.put("outer_measure_engr", "");
		} else {

			mapVo.put("outer_measure_engr", mapVo.get("outer_measure_engr"));

		}

		if (mapVo.get("inner_measure_emp").toString() == "") {

			mapVo.put("inner_measure_emp", "");
		} else {

			mapVo.put("inner_measure_emp", mapVo.get("inner_measure_emp"));

		}

		if (mapVo.get("deal_emp").toString() == "") {

			mapVo.put("deal_emp", "");
		} else {

			mapVo.put("deal_emp", mapVo.get("deal_emp"));

		}

		if (mapVo.get("plan_measure_date").toString() == "") {

			mapVo.put("plan_measure_date", "");
		} else {

			mapVo.put("plan_measure_date",
					DateUtil.stringToDate(mapVo.get("plan_measure_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("fact_measure_date").toString() == "") {

			mapVo.put("fact_measure_date", "");
		} else {

			mapVo.put("fact_measure_date",
					DateUtil.stringToDate(mapVo.get("fact_measure_date").toString(), "yyyy-MM-dd"));

		}

		if (mapVo.get("pre_next_date").toString() == "") {

			mapVo.put("pre_next_date", "");
		} else {

			mapVo.put("pre_next_date", DateUtil.stringToDate(mapVo.get("pre_next_date").toString(), "yyyy-MM-dd"));

		}
		try {
			String assMeasureRecJson = assMeasureRecService.updateAssMeasureRec(mapVo);

			return JSONObject.parseObject(assMeasureRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/addOrUpdateAssMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMeasureRec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMeasureRecJson = "";

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
			try {
				assMeasureRecJson = assMeasureRecService.addOrUpdateAssMeasureRec(detailVo);
			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		return JSONObject.parseObject(assMeasureRecJson);
	}

	/**
	 * @Description 删除数据 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/deleteAssMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMeasureRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		String assMeasureRecJson;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("rec_id", ids[3]);
			AssMeasureRec assMeasureRec = assMeasureRecService.queryAssMeasureRecByCode(mapVo);
			if (assMeasureRec.getState() != 0) {

				flag = false;

				break;

			}

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被审核或被终止! \"}");

		}
		try {
			assMeasureRecDetailService.deleteBatchAssMeasureRecDetail(listVo);
			assMeasureRecJson = assMeasureRecService.deleteBatchAssMeasureRec(listVo);

			return JSONObject.parseObject(assMeasureRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/queryAssMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasureRec(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String assMeasureRec = assMeasureRecService.queryAssMeasureRec(getPage(mapVo));

		return JSONObject.parseObject(assMeasureRec);

	}

	/**
	 * @Description 审核数据 051201 计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/auditMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMeasureRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("rec_id", ids[3]);
			AssMeasureRec assMeasureRec = assMeasureRecService.queryAssMeasureRecByCode(mapVo);
			if (assMeasureRec.getState() != 0) {

				flag = false;

				break;

			}

			mapVo.put("state", "1");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核!  \"}");

		}
		try {
			String assMeasurePlanJson = assMeasureRecService.updateBatchAssMeasureRec(listVo);

			return JSONObject.parseObject(assMeasurePlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 消审数据 051201 计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/backMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMeasureRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("rec_id", ids[3]);

			mapVo.put("state", "0");

			mapVo.put("audit_emp", "");

			mapVo.put("audit_date", "");

			// 判断状态不是审核不能进行消审
			AssMeasureRec assMeasureRec = assMeasureRecService.queryAssMeasureRecByCode(mapVo);
			if (assMeasureRec.getState() != 1) {

				flag = false;

				break;

			}

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");

		}
		try {
			String assMeasurePlanJson = assMeasureRecService.updateBatchAssMeasureRecBack(listVo);

			return JSONObject.parseObject(assMeasurePlanJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * 计量记录单状态查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurerec/queryAssMeasureRecState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasureRecState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//入库单状态查询  （打印时校验数据专用）
		List<String> list = assMeasureRecService.queryAssMeasureRecState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"计量记录单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}	
	
}
