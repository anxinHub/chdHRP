/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.ins;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.contract.AssContractDetail;
import com.chd.hrp.ass.entity.ins.AssInsMain;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
import com.chd.hrp.ass.service.contract.AssContractDetailService;
import com.chd.hrp.ass.service.contract.AssContractMainService;
import com.chd.hrp.ass.service.ins.AssInsDetailService;
import com.chd.hrp.ass.service.ins.AssInsMainService;

/**
 * 
 * @Description: 050601 资产安装主表
 * @Table: ASS_INS_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssInsMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssInsMainController.class);

	// 引入Service服务
	@Resource(name = "assInsMainService")
	private final AssInsMainService assInsMainService = null;

	@Resource(name = "assInsDetailService")
	private final AssInsDetailService assInsDetailService = null;

	@Resource(name = "assContractMainService")
	private final AssContractMainService assContractMainService = null;

	@Resource(name = "assContractDetailService")
	private final AssContractDetailService assContractDetailService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/assInsMainMainPage", method = RequestMethod.GET)
	public String assInsMainMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05012",MyConfig.getSysPara("05012"));
		
		return "hrp/ass/assinsmain/assInsMainMain";

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/updateToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInsMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

			AssInsMain assInsMain = assInsMainService.queryAssInsMainByCode(mapVo);

			if (assInsMain.getState() != 0) {
				continue;
			}else{
				listVo.add(mapVo);
			}

		}

		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复审核! \"}");
		}
		try {

			assInsMainJson = assInsMainService.updateToExamine(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInsMainJson);
	}

	/**
	 * @Description 消审
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/updateNotToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInsMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			mapVo.put("audit_emp", "");

			mapVo.put("audit_date", "");

			AssInsMain assInsMain = assInsMainService.queryAssInsMainByCode(mapVo);

			if (assInsMain.getState() != 1) {
				continue;
			}else{
				listVo.add(mapVo);
			}
		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不是审核状态! \"}");
		}
		try {
			assInsMainJson = assInsMainService.updateNotToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assInsMainJson);

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/assInsMainAddPage", method = RequestMethod.GET)
	public String assInsMainAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinsmain/assInsMainAdd";

	}

	/**
	 * @Description 生成页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/assInsMainInitPage", method = RequestMethod.GET)
	public String assInsMainInitPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assinsmain/assInsMainInit";

	}

	/**
	 * @Description 按合同生成数据 050601 资产安装明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/initAssInsByContract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssInsByContract(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assInsDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<AssContractDetail> contractDetailList = assContractDetailService.queryAssContractDetailHosList(mapVo);
		if (contractDetailList.size() > 0) {
			String ins_date = String.valueOf(mapVo.get("ins_date"));

			if (StringUtils.isNotEmpty(ins_date)) {
				mapVo.put("ins_date", DateUtil.stringToDate(ins_date, "yyyy-MM-dd"));
			}

			mapVo.put("create_emp", SessionManager.getUserId());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("create_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));

			mapVo.put("state", "0");
			try {
				assInsDetailJson = assInsMainService.addOrUpdateAssInsMain(mapVo);
				JSONObject jsonObj = JSONArray.parseObject(assInsDetailJson);
				String ins_id = (String) jsonObj.get("ins_id");
				String ins_no = (String) jsonObj.get("ins_no");

				for (AssContractDetail detailVo : contractDetailList) {
					Map<String, Object> detailMap = new HashMap<String, Object>();

					detailMap.put("group_id", detailVo.getGroup_id());

					detailMap.put("hos_id", detailVo.getHos_id());

					detailMap.put("copy_code", detailVo.getCopy_code());

					detailMap.put("ins_id", ins_id);

					detailMap.put("ins_no", ins_no);

					detailMap.put("contract_detail_id", mapVo.get("contract_detail_id"));

					detailMap.put("ass_id", detailVo.getAss_id());

					detailMap.put("ass_no", detailVo.getAss_no());

					detailMap.put("ass_model", detailVo.getAss_model());

					detailMap.put("ass_spec", detailVo.getAss_spec());

					detailMap.put("ass_brand", detailVo.getAss_brand());

					detailMap.put("fac_id", detailVo.getFac_id());

					detailMap.put("fac_no", detailVo.getFac_no());

					detailMap.put("ins_price", "0");

					detailMap.put("ins_amount", detailVo.getContract_amount());

					detailMap.put("ins_money", "0");

					assInsDetailJson = assInsDetailService.addOrUpdateAssInsDetail(detailMap);

				}

				for (String id : mapVo.get("pact_codes").toString().split(",")) {
					Map<String, Object> conMapvo = new HashMap<String, Object>();
					conMapvo.put("group_id", mapVo.get("group_id"));
					conMapvo.put("hos_id", mapVo.get("hos_id"));
					conMapvo.put("copy_code", mapVo.get("copy_code"));
					conMapvo.put("ins_id", ins_id);
					conMapvo.put("pact_code", id);
					assInsDetailJson = assInsDetailService.initAssInsDetailtBid(conMapvo);
				}

				assInsDetailJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + mapVo.get("group_id")
						+ "|" + mapVo.get("hos_id") + "|" + mapVo.get("copy_code") + "|" + ins_id + "|" + ins_no
						+ "\"}";

			} catch (Exception e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		} else {
			assInsDetailJson = "{\"error\":\"没有可生成的数据！\"}";
		}

		return JSONObject.parseObject(assInsDetailJson);
	}

	/**
	 * @Description 添加数据 050601 资产安装主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/addAssInsMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInsMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		AssInsMain assPlanDept = assInsMainService.queryAssInsMainByCode(mapVo);
		if(assPlanDept != null){
			if(assPlanDept.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"当前单据已被审核,不能操作 \"}");
			}
		}
		String yearmonth=mapVo.get("ass_year").toString()+mapVo.get("ass_month").toString();
		//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		}  
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		mapVo.put("ins_date", DateUtil.stringToDate(mapVo.get("ins_date").toString(), "yyyy-MM-dd"));

		mapVo.put("create_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));

		mapVo.put("create_emp", SessionManager.getUserId());

		mapVo.put("state", "0");
		try {
			/*String assInsMainJson = assInsMainService.addOrUpdateAssInsMain(mapVo);
			JSONObject jsonObj = JSONArray.parseObject(assInsMainJson);
			String ins_id = (String) jsonObj.get("ins_id");
			String ins_no = (String) jsonObj.get("ins_no");

			String assInsDetailJson = "";

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());
				detailVo.put("hos_id", SessionManager.getHosId());
				detailVo.put("copy_code", SessionManager.getCopyCode());

				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}

				detailVo.put("ins_id", ins_id);
				detailVo.put("ins_no", ins_no);

				detailVo.put("contract_detail_id", mapVo.get("contract_detail_id"));

				if (detailVo.get("ins_detail_id") == null) {
					detailVo.put("ins_detail_id", "0");
				}

				//String ins_money = detailVo.get("ins_money").toString().replaceAll(",", "");
				if(detailVo.get("ins_money") != null){
					String ins_money = detailVo.get("ins_money").toString();
					detailVo.put("ins_money", Double.parseDouble(ins_money));
				}

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);

				String fid = detailVo.get("fac_id").toString();
				if (fid != null && fid.split("@").length == 2) {
					detailVo.put("fac_id", fid.split("@")[0]);
					detailVo.put("fac_no", fid.split("@")[1]);
				} else {
					detailVo.put("fac_id", null);
					detailVo.put("fac_no", null);
				}

				assInsDetailJson = assInsDetailService.addOrUpdateAssInsDetail(detailVo);

			}
			JSONObject json = JSONArray.parseObject(assInsDetailJson);
			json.put("ins_id", ins_id);
			json.put("ins_no", ins_no);*/
			return JSONObject.parseObject(assInsMainService.addOrUpdateAssInsMain(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 050601 资产安装主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/assInsMainUpdatePage", method = RequestMethod.GET)
	public String assInsMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInsMain assInsMain = new AssInsMain();
		assInsMain = assInsMainService.queryAssInsMainByCode(mapVo);

		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		String inDate = a.format(assInsMain.getIns_date());
		String crDate = a.format(assInsMain.getCreate_date());
		// String auDate=a.format(assInsMain.getAudit_date());

		mode.addAttribute("group_id", assInsMain.getGroup_id());
		mode.addAttribute("hos_id", assInsMain.getHos_id());
		mode.addAttribute("copy_code", assInsMain.getCopy_code());
		mode.addAttribute("ins_id", assInsMain.getIns_id());
		mode.addAttribute("ins_no", assInsMain.getIns_no());
		mode.addAttribute("ass_year", assInsMain.getAss_year());
		mode.addAttribute("ass_month", assInsMain.getAss_month());
		mode.addAttribute("ins_date", inDate);
		mode.addAttribute("pact_code", assInsMain.getPact_code());

		mode.addAttribute("ven_id", assInsMain.getVen_id());
		mode.addAttribute("ven_no", assInsMain.getVen_no());
		mode.addAttribute("sup_name", assInsMain.getSup_name());
		mode.addAttribute("dept_id", assInsMain.getDept_id());
		mode.addAttribute("dept_no", assInsMain.getDept_no());
		mode.addAttribute("ins_money", assInsMain.getIns_money());
		mode.addAttribute("dept_code", assInsMain.getDept_code());
		mode.addAttribute("dept_name", assInsMain.getDept_name());
		mode.addAttribute("accept_desc", assInsMain.getAccept_desc());
		mode.addAttribute("create_emp", assInsMain.getCreate_emp());
		mode.addAttribute("create_emp_name", assInsMain.getCreate_emp_name());
		mode.addAttribute("create_date", crDate);
		mode.addAttribute("audit_emp", assInsMain.getAudit_emp());
		mode.addAttribute("audit_emp_name", assInsMain.getAudit_emp_name());
		// mode.addAttribute("audit_date", auDate);
		mode.addAttribute("state", assInsMain.getState());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05012",MyConfig.getSysPara("05012"));
		
		return "hrp/ass/assinsmain/assInsMainUpdate";
	}

	/**
	 * @Description 删除数据 050601 资产安装主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/deleteAssInsMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInsMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		String assInsMainJson;

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			AssInsMain assInsMain = assInsMainService.queryAssInsMainByCode(mapVo);

			if (assInsMain.getState() != 0) {

				flag = false;

				break;
			}

			listVo.add(mapVo);

		}

		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}

		try {
			assInsDetailService.deleteBatchAssInsContractMap(listVo);
			assInsDetailService.deleteBatchAssInsDetail(listVo);
			assInsMainJson = assInsMainService.deleteBatchAssInsMain(listVo);
			return JSONObject.parseObject(assInsMainJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}
	}

	/**
	 * @Description 查询数据 050601 资产安装主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/queryAssInsMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInsMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("ass_month") != null && !"".equals(mapVo.get("ass_month").toString())){
			mapVo.put("ass_year", mapVo.get("ass_month").toString().substring(0, 4));
			mapVo.put("ass_month", mapVo.get("ass_month").toString().substring(4, 6));
		}
		
		if(mapVo.get("ass_month1") != null && !"".equals(mapVo.get("ass_month1").toString())){
			mapVo.put("ass_year1", mapVo.get("ass_month1").toString().substring(0, 4));
			mapVo.put("ass_month1", mapVo.get("ass_month1").toString().substring(4, 6));
		}

		String assInsMain = assInsMainService.queryAssInsMain(getPage(mapVo));

		return JSONObject.parseObject(assInsMain);

	}

	@RequestMapping(value = "/hrp/ass/assinsmain/queryAssContractByIns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_group", "0");
		mapVo.put("is_init", "0");
		mapVo.put("state", "1");
		String assContractMain = assContractMainService.queryAssContractMain(getPage(mapVo));

		return JSONObject.parseObject(assContractMain);

	}

	/***
	 * 打印状态
	 */
	@RequestMapping(value = "/hrp/ass/assinsmain/queryInsMainState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryInsMainState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//打印时校验数据专用
		List<String> list = assInsMainService.queryInsMainState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"资产安装单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
}
