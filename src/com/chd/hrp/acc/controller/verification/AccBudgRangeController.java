/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.verification;
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
import com.chd.base.SessionManager;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccBudgRange;
import com.chd.hrp.acc.serviceImpl.verification.AccBudgRangeServiceImpl;

/**
* @Title. @Description.
* 账龄区间表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccBudgRangeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBudgRangeController.class);
	
	@Resource(name = "accBudgRangeService")
	private final AccBudgRangeServiceImpl accBudgRangeService = null;
    
	/**
	*账龄区间表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accbudgrange/accBudgRangeMainPage", method = RequestMethod.GET)
	public String accBudgRangeMainPage(Model mode) throws Exception {
		return "hrp/acc/accbudgrange/accBudgRangeMain";
	}
	
	
	@RequestMapping(value = "/hrp/acc/accbudgrange/queryAccBudgRangeTitle", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccBudgRangeTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());   
		List<AccBudgRange> list = accBudgRangeService.queryAccBudgRangeList(mapVo);		
		return JSONObject.parseObject(JsonListBeanUtil.listToJson(list));		
	}
	
	
	/**
	*账龄区间表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accbudgrange/accBudgRangeAddPage", method = RequestMethod.GET)
	public String accBudgRangeAddPage(Model mode) throws Exception {
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());        
        AccBudgRange start_day = accBudgRangeService.getMaxDay(mapVo);
        if(start_day == null){
        	mode.addAttribute("start_day", 0);
        }else{
        	mode.addAttribute("start_day", start_day.getEnd_days());
        }		
		return "hrp/acc/accbudgrange/accBudgRangeAdd";

	}
	/**
	*账龄区间表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accbudgrange/addAccBudgRange", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> addAccBudgRange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {		
		mapVo.put("group_id", SessionManager.getGroupId());	
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());       
		String accBudgRangeJson = accBudgRangeService.addAccBudgRange(mapVo);
		return JSONObject.parseObject(accBudgRangeJson);		
	}
	/**
	*账龄区间表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accbudgrange/queryAccBudgRange", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryAccBudgRange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());   
		String accBudgRange = accBudgRangeService.queryAccBudgRange(getPage(mapVo));
		return JSONObject.parseObject(accBudgRange);		
	}
	/**
	*账龄区间表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accbudgrange/deleteAccBudgRange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccBudgRange(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
            mapVo.put("range_id", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", SessionManager.getGroupId());		
    		mapVo.put("hos_id", SessionManager.getHosId());		
            mapVo.put("copy_code", SessionManager.getCopyCode());   
            listVo.add(mapVo);
        }
		String accBudgRangeJson = accBudgRangeService.deleteBatchAccBudgRange(listVo);
	   return JSONObject.parseObject(accBudgRangeJson);			
	}
	
	@RequestMapping(value = "/hrp/acc/accbudgrange/getMaxDays", method = RequestMethod.POST)
	@ResponseBody
	public String getMaxDays(Model mode){		
		Map<String, Object> mapVo=new HashMap<String, Object>();		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());       
        AccBudgRange start_day = accBudgRangeService.getMaxDay(mapVo);        
        String maxDays = "";        
        if(start_day == null){       	
        	maxDays = "ok";       
        }else{       		
        	maxDays = String.valueOf(start_day.getEnd_days());       	
        }       
        return maxDays;
	}
	
	/**
	*账龄区间表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accbudgrange/accBudgRangeUpdatePage", method = RequestMethod.GET)	
	public String accBudgRangeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());   
        
        AccBudgRange accBudgRange = new AccBudgRange();
		accBudgRange = accBudgRangeService.queryAccBudgRangeByCode(mapVo);
		mode.addAttribute("range_id", accBudgRange.getRange_id());
		mode.addAttribute("group_id", accBudgRange.getGroup_id());
		mode.addAttribute("hos_id", accBudgRange.getHos_id());
		mode.addAttribute("copy_code", accBudgRange.getCopy_code());
		mode.addAttribute("begin_days", accBudgRange.getBegin_days());
		mode.addAttribute("end_days", accBudgRange.getEnd_days());
		mode.addAttribute("range_pro", accBudgRange.getRange_pro());
		mode.addAttribute("note", accBudgRange.getNote());
		return "hrp/acc/accbudgrange/accBudgRangeUpdate";
	}
	/**
	*账龄区间表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accbudgrange/updateAccBudgRange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccBudgRange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());   
        
		String accBudgRangeJson = accBudgRangeService.updateAccBudgRange(mapVo);		
		return JSONObject.parseObject(accBudgRangeJson);
	}
	/**
	*账龄区间表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accbudgrange/importAccBudgRange", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccBudgRange(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());   
		String accBudgRangeJson = accBudgRangeService.importAccBudgRange(mapVo);		
		return JSONObject.parseObject(accBudgRangeJson);
	}

}

