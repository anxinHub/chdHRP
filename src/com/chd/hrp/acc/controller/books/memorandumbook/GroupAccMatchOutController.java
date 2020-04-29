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
import com.chd.hrp.acc.entity.AccMatchOut;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccMatchOutService;
@Controller
public class GroupAccMatchOutController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccMatchOutController.class);
	
	@Resource(name = "groupAccMatchOutService")
	private final GroupAccMatchOutService groupAccMatchOutService = null;
	
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/groupAccMatchOutMainPage", method = RequestMethod.GET)
	public String accMatchOutMainPage(Model mode) throws Exception {

		String yearMonth=MyConfig.getCurAccYearMonth();
			if(yearMonth==null || yearMonth.equals("000000")){
				String curDate= DateUtil.dateToString(new Date());
				yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
				
			}
			
			mode.addAttribute("acc_year", yearMonth.substring(0, 4));
			mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accmod/accmatchout/group/groupAccMatchOutMain";
	}
	/**
	*配套资金初始账表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/groupAccMatchOutAddPage", method = RequestMethod.GET)
	public String accMatchOutAddPage(Model mode) throws Exception {

		return "hrp/acc/accmod/accmatchout/group/groupAccMatchOutAdd";

	}
	/**
	*配套资金初始账表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/addGroupAccMatchOut", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccMatchOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String date = mapVo.get("occur_date").toString();
		mapVo.put("vouch_id", 0);
		mapVo.put("vouch_check_id", 0);
		mapVo.put("note", "");
		mapVo.put("is_import", 0);
		mapVo.put("create_user", SessionManager.getUserId());
		mapVo.put("create_date", new Date());
		mapVo.put("occur_date", DateUtil.stringToDate(date,"yyyy-MM-dd"));
		String accMatchOutJson = groupAccMatchOutService.addGroupAccMatchOut(mapVo);
		
		return JSONObject.parseObject(accMatchOutJson);
		
	}
	/**
	*配套资金初始账表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/queryGroupAccMatchOut", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccMatchOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String accMatchOutJson = groupAccMatchOutService.queryGroupAccMatchOut(getPage(mapVo));

		return JSONObject.parseObject(accMatchOutJson);
		
	}
	/**
	*配套资金初始账表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/deletBatchGroupAccMatchOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletBatchAccMatchOut(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("group_id", res[0]);
            
            mapVo.put("hos_id", res[1]);
            
            mapVo.put("copy_code", res[2]); 
            
            mapVo.put("acc_year", res[3]); 
            
            mapVo.put("out_id", res[4]); 
			
            listVo.add(mapVo);
        }
		String accMatchOutJson = groupAccMatchOutService.deleteBatchGroupAccMatchOut(listVo);
	   return JSONObject.parseObject(accMatchOutJson);
			
	}
	
	/**
	*配套资金初始账表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/groupAccMatchOutUpdatePage", method = RequestMethod.GET)
	
	public String accMatchOutUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		AccMatchOut accMatchOut = new AccMatchOut();
		accMatchOut = groupAccMatchOutService.queryGroupAccMatchOutByCode(mapVo);
		mode.addAttribute("group_id", accMatchOut.getGroup_id());
		mode.addAttribute("hos_id", accMatchOut.getHos_id());
		mode.addAttribute("copy_code", accMatchOut.getCopy_code());
		mode.addAttribute("acc_year", accMatchOut.getAcc_year());
		mode.addAttribute("out_id", accMatchOut.getOut_id());
		mode.addAttribute("proj_id", accMatchOut.getProj_id());
		mode.addAttribute("proj_no", accMatchOut.getProj_no());
		mode.addAttribute("proj_code", accMatchOut.getProj_code());
		mode.addAttribute("proj_name", accMatchOut.getProj_name());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mode.addAttribute("occur_date", sdf.format(accMatchOut.getOccur_date()));
		mode.addAttribute("summary", accMatchOut.getSummary());
		mode.addAttribute("subj_id", accMatchOut.getSubj_id());
		mode.addAttribute("debit", accMatchOut.getDebit());
		mode.addAttribute("business_no", accMatchOut.getBusiness_no());
		mode.addAttribute("vouch_id", accMatchOut.getVouch_id());
		mode.addAttribute("vouch_check_id", accMatchOut.getVouch_check_id());
		mode.addAttribute("note", accMatchOut.getNote());
		mode.addAttribute("create_user", accMatchOut.getCreate_user());
		mode.addAttribute("create_date", accMatchOut.getCreate_date());
		mode.addAttribute("vouch_no", accMatchOut.getVouch_no());
		mode.addAttribute("is_import", accMatchOut.getIs_import());
		mode.addAttribute("subj_code", accMatchOut.getSubj_code());
		
		return "hrp/acc/accmod/accmatchout/group/groupAccMatchOutUpdate";
	}
	/**
	*配套资金初始账表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accmod/accmatchout/group/updateGroupAccMatchOut", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateaccMatchOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String occur_date = String.valueOf(mapVo.get("occur_date"));
		mapVo.put("occur_date",DateUtil.stringToDate(occur_date, "yyyy-MM-dd"));
		String accMatchOutJson = groupAccMatchOutService.updateGroupAccMatchOut(mapVo);
		
		return JSONObject.parseObject(accMatchOutJson);
	}
}
