/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.maintain;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.maintain.AssMaintainRec;
import com.chd.hrp.ass.service.maintain.AssMaintainRecItemService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecService;

/**
 * 
 * @Description: 051203 保养记录
 * @Table: ASS_MAINTAIN_REC
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainRecController extends BaseController { 

	private static Logger logger = Logger.getLogger(AssMaintainRecController.class);

	// 引入Service服务
	@Resource(name = "assMaintainRecService")
	private final AssMaintainRecService assMaintainRecService = null;

	@Resource(name = "assMaintainRecItemService")
	private final AssMaintainRecItemService assMaintainRecItemService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/assMaintainRecMainPage", method = RequestMethod.GET)
	public String assMaintainRecMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05104",MyConfig.getSysPara("05104"));
		return "hrp/ass/assmaintainrec/assMaintainRecMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/assMaintainRecAddPage", method = RequestMethod.GET)
	public String assMaintainRecAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assmaintainrec/assMaintainRecAdd";

	}

	/**
	 * @Description 添加数据 051203 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/addAssMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
			mapVo.put("create_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));
	
			mapVo.put("create_emp", SessionManager.getUserId());
	
			mapVo.put("state", "0");
	
			mapVo.put("audit_emp", "");
	
			mapVo.put("audit_date", "");
			
			if(mapVo.get("plan_id") != null && mapVo.get("plan_id") != ""){
				mapVo.put("plan_id", mapVo.get("plan_id"));
			}else{
				mapVo.put("plan_id", "");
			}
	
			if (mapVo.get("plan_exec_date").toString() == "") {
				mapVo.put("plan_exec_date", "");
			} else {
				mapVo.put("plan_exec_date", DateUtil.stringToDate(mapVo.get("plan_exec_date").toString(), "yyyy-MM-dd"));
			}
	
			mapVo.put("fact_exec_date", DateUtil.stringToDate(mapVo.get("fact_exec_date").toString(), "yyyy-MM-dd"));
			
			return JSONObject.parseObject(assMaintainRecService.addOrUpdateMainRec(mapVo));
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051203 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/assMaintainRecUpdatePage", method = RequestMethod.GET)
	public String assMaintainRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMaintainRec assMaintainRec = new AssMaintainRec();

		assMaintainRec = assMaintainRecService.queryAssMaintainRecByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assMaintainRec.getGroup_id());

		mode.addAttribute("hos_id", assMaintainRec.getHos_id());

		mode.addAttribute("copy_code", assMaintainRec.getCopy_code());

		mode.addAttribute("rec_id", assMaintainRec.getRec_id());

		mode.addAttribute("rec_no", assMaintainRec.getRec_no());

		mode.addAttribute("ass_year", assMaintainRec.getAss_year());

		mode.addAttribute("ass_month", assMaintainRec.getAss_month());

		mode.addAttribute("plan_id", assMaintainRec.getPlan_id());

		mode.addAttribute("ass_nature", assMaintainRec.getAss_nature());
		
		mode.addAttribute("naturs_name", assMaintainRec.getNaturs_name());

		mode.addAttribute("fact_exec_emp", assMaintainRec.getFact_exec_emp());

		mode.addAttribute("fact_exec_date", sdf.format(assMaintainRec.getFact_exec_date()));

		mode.addAttribute("maintain_hours", assMaintainRec.getMaintain_hours());

		mode.addAttribute("maintain_money", assMaintainRec.getMaintain_money());

		mode.addAttribute("state", assMaintainRec.getState());

		mode.addAttribute("maintain_dept_id", assMaintainRec.getMaintain_dept_id());

		mode.addAttribute("maintain_dept_no", assMaintainRec.getMaintain_dept_no());

		mode.addAttribute("create_emp", assMaintainRec.getCreate_emp());

		mode.addAttribute("create_date", sdf.format(assMaintainRec.getCreate_date()));

		if (assMaintainRec.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assMaintainRec.getAudit_emp());

		}

		if (assMaintainRec.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assMaintainRec.getAudit_date()));

		}

		if (assMaintainRec.getPlan_exec_date() == null) {

			mode.addAttribute("plan_exec_date", "");

		} else {

			mode.addAttribute("plan_exec_date", sdf.format(assMaintainRec.getPlan_exec_date()));

		}

		if (assMaintainRec.getMaintain_desc() == null) {

			mode.addAttribute("maintain_desc", "");

		} else {

			mode.addAttribute("maintain_desc", assMaintainRec.getMaintain_desc());

		}

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/assmaintainrec/assMaintainRecUpdate";
	}

	/**
	 * @Description 更新数据 051203 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/updateAssMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainRec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (mapVo.get("plan_exec_date").toString() == "") {

			mapVo.put("plan_exec_date", "");

		} else {

			mapVo.put("plan_exec_date", DateUtil.stringToDate(mapVo.get("plan_exec_date").toString(), "yyyy-MM-dd"));

		}

		mapVo.put("fact_exec_date", DateUtil.stringToDate(mapVo.get("fact_exec_date").toString(), "yyyy-MM-dd"));
		try {
			String assMaintainRecJson = assMaintainRecService.updateAssMaintainRec(mapVo);

			return JSONObject.parseObject(assMaintainRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051203 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/addOrUpdateAssMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMaintainRec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainRecJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				assMaintainRecJson = assMaintainRecService.addOrUpdateAssMaintainRec(detailVo);

			}
			return JSONObject.parseObject(assMaintainRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 删除数据 051203 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/deleteAssMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		String assMaintainRecJson;

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("rec_id", ids[3]);

			if (Integer.parseInt(ids[4]) != 0) {

				flag = false;

				break;

			}
			listVo.add(mapVo);
		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"删除失败 该数据已被审核或被终止! \"}");

		}
		try {
			assMaintainRecItemService.deleteBatchAssMaintainRecItem(listVo);
			assMaintainRecJson = assMaintainRecService.deleteBatchAssMaintainRec(listVo);

			return JSONObject.parseObject(assMaintainRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051203 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/queryAssMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainRec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());


		String assMaintainRec = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assMaintainRec = assMaintainRecService.queryAssMaintainRec(getPage(mapVo));
			 
		}else{

			assMaintainRec = assMaintainRecService.queryDetails(getPage(mapVo));
			 
		}
		return JSONObject.parseObject(assMaintainRec);

	}

	/**
	 * @Description 审核数据 051201 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/auditMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMaintainPlan(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			if (Integer.parseInt(ids[4]) != 0) {

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

			return JSONObject.parseObject("{\"error\":\"审核失败 状态必须是新建的才能审核! \"}");

		}
		try {
			String assMaintainRecJson = assMaintainRecService.updateBatchAssMaintainRec(listVo);
			return JSONObject.parseObject(assMaintainRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 消审数据 051201 保养记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/backMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> backMaintainRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			if (!ids[4].equals("1")) {

				flag = false;

				break;

			}

			listVo.add(mapVo);

		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"消审失败 状态必须是审核的才能消审! \"}");

		}
		try {
			String assMaintainRecJson = assMaintainRecService.updateBatchAssMaintainRecBack(listVo);

			return JSONObject.parseObject(assMaintainRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 终止计划 051201 保养计划
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/stopMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> stopMaintainRec(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo = new HashMap<String, Object>();

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("rec_id", ids[3]);

			mapVo.put("state", "2");

			listVo.add(mapVo);

		}
		try {
			String assMaintainRecJson = assMaintainRecService.updateBatchAssMaintainRecStop(listVo);

			return JSONObject.parseObject(assMaintainRecJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}
	
	// 保存验收项目
			@RequestMapping(value = "/hrp/ass/assmaintainrec/saveAssRecItem", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> saveAssRecItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

				List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				String assAcceptDetailJson = "";

				List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("rec_item_data").toString());

				for (Map<String, Object> detailVo : detail) {

					detailVo.put("group_id", SessionManager.getGroupId());

					detailVo.put("hos_id", SessionManager.getHosId());

					detailVo.put("copy_code", SessionManager.getCopyCode());

					if (detailVo.get("item_code") == null || "".equals(detailVo.get("item_code"))) {
						continue;
					}

					detailVo.put("is_normal", Integer.parseInt(detailVo.get("is_normal").toString()));

					listVo.add(detailVo);
				}

				try {
					assAcceptDetailJson = assMaintainRecService.saveAssMaintainItem(listVo);
				} catch (Exception e) {

					return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
				}

				return JSONObject.parseObject(assAcceptDetailJson);

			}
			
			// 删除验收项目
			@RequestMapping(value = "/hrp/ass/assmaintainrec/deleteAssRecItem", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> deleteAssRecItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
					throws Exception {
				List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
				String assAcceptMainJson;
				for (String id : paramVo.split(",")) {
					Map<String, Object> mapVo = new HashMap<String, Object>();
					String[] ids = id.split("@");
					// 表的主键
					mapVo.put("group_id", ids[0]);

					mapVo.put("hos_id", ids[1]);

					mapVo.put("copy_code", ids[2]);

					mapVo.put("plan_id", ids[3]);

					mapVo.put("detail_id", ids[4]);

					mapVo.put("item_code", ids[5]);

					listVo.add(mapVo);
				}
				try {
					                 
					assAcceptMainJson = assMaintainRecService.deleteAssMaintainItem(listVo);
				} catch (Exception e) {
					return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
				}

				return JSONObject.parseObject(assAcceptMainJson);
			}	

			// 生成保养计划项目
			@RequestMapping(value = "/hrp/ass/assmaintainrec/buildAssRecItem", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> buildAssRecItem(@RequestParam Map<String, Object> mapVo, Model model)
					throws Exception {
				String assMaintainMainJson = "";
				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				assMaintainMainJson = assMaintainRecService.buildAssRecItem(mapVo);

				return JSONObject.parseObject(assMaintainMainJson);
			}
			
			// 查询保养项目
			@RequestMapping(value = "/hrp/ass/assmaintainrec/queryAssRecItem", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> queryAssRecItem(@RequestParam Map<String, Object> mapVo, Model mode)
					throws Exception {

				String assMaintainMain = "";

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				assMaintainMain = assMaintainRecService.queryAssRecItem(getPage(mapVo));

				return JSONObject.parseObject(assMaintainMain);
			}
			
			/**
			 * 保养计划单状态查询
			 * @param mapVo
			 * @param mode
			 * @return
			 * @throws Exception
			 */
			@RequestMapping(value = "/hrp/ass/assmaintainrec/queryAssMainRecState", method = RequestMethod.POST)
			@ResponseBody
			public Map<String, Object> queryAssMainRecState(@RequestParam Map<String, Object> mapVo, Model mode)
					throws Exception {

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				//入库单状态查询  （打印时校验数据专用）
				List<String> list = assMaintainRecService.queryAssMainRecState(mapVo);
				
				if(list.size() == 0){
					
					return JSONObject.parseObject("{\"state\":\"true\"}");
					
				}else{
					
					String  str = "" ;
					for(String item : list){
						
						str += item +"," ;
					}
					
					return JSONObject.parseObject("{\"error\":\"保养记录单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
				}
				
				
			}	

}
