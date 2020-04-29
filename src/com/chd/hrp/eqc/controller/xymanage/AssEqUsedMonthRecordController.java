/*
 * 
 * 
 * */ 
package com.chd.hrp.eqc.controller.xymanage;
import java.util.ArrayList;
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
import com.chd.base.SessionManager;
import com.chd.hrp.eqc.service.xymanage.AssEqUsedResourceService;


/**
 * 
 * @Title. 
 * @Description.
 *@author lh0225
 *@date 20200417
 */

/**
* 月度资源消耗 ASS_EQUsedResource Controller实现类
*/
@Controller
public class AssEqUsedMonthRecordController extends BaseController{
	//引入Service服务
		@Resource(name = "assEqUsedResourceService")
		private final AssEqUsedResourceService assEqUsedResourceService = null;
	   
	
	/**
	 * 
	 * 主界面跳转
	 * @author lh0225
	 * 
	 * 
	 */
	
	@RequestMapping(value = "/hrp/eqc/xymanage/assEqUsedResourceMonth", method = RequestMethod.GET)
	public String assEqUsedResourceMainPage(Model mode) throws Exception {

		return "hrp/eqc/xymanage/assequsedmonthsource/assEqUsedMonthResourceMain";

	}

	/**
	 * @Description 
	 * 查询数据 15其他设备月度资源消耗 ASS_EQUsedResource
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/eqc/xymanage/queryAssEqUsedMonthResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssEqUsedResource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("startyear")!=null && mapVo.get("startyear")!=""){
			String year = mapVo.get("startyear").toString().substring(0, 4);
			mapVo.put("year", year);
			String month = mapVo.get("startyear").toString().substring(5, 7);
			mapVo.put("month", month);
			String startyear = year+month;
			mapVo.put("startyear", startyear);
			char  falg = '0' ;
			mapVo.put("falg", falg);
		}
	
		if(mapVo.get("endyear")!=null && mapVo.get("endyear")!=""){
			String endsyear = mapVo.get("endyear").toString().substring(0, 4);
			mapVo.put("endsyear", endsyear);
			String endmonth = mapVo.get("endyear").toString().substring(5, 7);
			mapVo.put("endmonth", endmonth);
			String endyear = endsyear+endmonth;
			mapVo.put("endyear", endyear);
			char  falg = '1' ;
			mapVo.put("falg", falg);
			
		}
		String assEqUsedResource = assEqUsedResourceService.query(getPage(mapVo));

		return JSONObject.parseObject(assEqUsedResource);
		
	}
	
}
