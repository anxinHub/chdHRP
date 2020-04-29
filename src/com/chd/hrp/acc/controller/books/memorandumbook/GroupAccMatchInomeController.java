package com.chd.hrp.acc.controller.books.memorandumbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccMatchIncome;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccMatchIncomeService;
@Controller
public class GroupAccMatchInomeController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccMatchInomeController.class);
	
	@Resource(name = "groupAccMatchIncomeService")
	private final GroupAccMatchIncomeService groupAccMatchIncomeService = null;
	
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/groupAccMatchIncomeMainPage", method = RequestMethod.GET)
	public String accMatchIncomeMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));


		return "hrp/acc/accmatchincome/group/groupAccMatchIncomeMain";
	}
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/groupAccMatchIncomeAddPage", method = RequestMethod.GET)
	public String accMatchIncomeAddPage(Model mode) throws Exception {

		return "hrp/acc/accmatchincome/group/groupAccMatchIncomeAdd";

	}
	/**
	*配套资金初始账表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/addGroupAccMatchIncome", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccMatchIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String accMatchIncomeJson = groupAccMatchIncomeService.addGroupAccMatchIncome(mapVo);

		return JSONObject.parseObject(accMatchIncomeJson);
		
	}
	/**
	*配套资金初始账表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/queryGroupAccMatchIncome", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccMatchIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String accMatchIncomeJson = groupAccMatchIncomeService.queryGroupAccMatchIncome(getPage(mapVo));

		return JSONObject.parseObject(accMatchIncomeJson);
		
	}
	/**
	*配套资金初始账表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/deletBatchGroupAccMatchIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletBatchAccMatchIncome(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("income_id", id.split("@")[0]);
            mapVo.put("group_id", id.split("@")[1]);
            mapVo.put("hos_id", id.split("@")[2]);
            mapVo.put("copy_code", id.split("@")[3]);
            mapVo.put("acc_year", id.split("@")[4]);
            listVo.add(mapVo);
        }
		String accMatchIncomeJson = groupAccMatchIncomeService.deleteBatchGroupAccMatchIncome(listVo);
	   return JSONObject.parseObject(accMatchIncomeJson);
			
	}
	
	/**
	*配套资金初始账表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/groupAccMatchIncomeUpdatePage", method = RequestMethod.GET)
	
	public String accMatchIncomeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccMatchIncome AccMatchIncome = new AccMatchIncome();
		AccMatchIncome = groupAccMatchIncomeService.queryGroupAccMatchIncomeByCode(mapVo);
		mode.addAttribute("group_id", AccMatchIncome.getGroup_id());
		mode.addAttribute("hos_id", AccMatchIncome.getHos_id());
		mode.addAttribute("copy_code", AccMatchIncome.getCopy_code());
		mode.addAttribute("acc_year", AccMatchIncome.getAcc_year());
		mode.addAttribute("proj_id", AccMatchIncome.getProj_id());
		mode.addAttribute("business_no", AccMatchIncome.getBusiness_no());
		mode.addAttribute("proj_no", AccMatchIncome.getProj_no());
		mode.addAttribute("proj_code", AccMatchIncome.getProj_code());
		mode.addAttribute("proj_name", AccMatchIncome.getProj_name());
		mode.addAttribute("reply_no", AccMatchIncome.getReply_no());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("reply_date",sdf.format(AccMatchIncome.getReply_date()));
		mode.addAttribute("reply_money", AccMatchIncome.getReply_money());
		mode.addAttribute("note", AccMatchIncome.getNote());
		
		return "hrp/acc/accmatchincome/group/groupAccMatchIncomeUpdate";
	}
	/**
	*配套资金初始账表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accmatchincome/group/updateGroupAccMatchIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccMatchIncome(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
	   
		String reply_date = String.valueOf(mapVo.get("reply_date"));
		mapVo.put("reply_date",DateUtil.stringToDate(reply_date, "yyyy-MM-dd"));
		String accMatchIncomeJson = groupAccMatchIncomeService.updateGroupAccMatchIncome(mapVo);
		
		return JSONObject.parseObject(accMatchIncomeJson);
	}
}
