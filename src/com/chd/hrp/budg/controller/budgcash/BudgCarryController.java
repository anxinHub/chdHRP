/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.controller.budgcash;
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
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.service.budgcash.BudgCarryService;
/**
 * 
 * @Description:
 * 业务预算结转
 * @Table:
 * BUDG_WORK_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class BudgCarryController extends BaseController{
	
	private static Logger logger = Logger.getLogger(BudgCarryController.class);
	
	//引入Service服务
	@Resource(name = "budgCarryService")
	private final BudgCarryService budgCarryService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcarry/budgCarryMainPage", method = RequestMethod.GET)
	public String budgWorkCarryMainPage(Model mode) throws Exception {
		
		return "/hrp/budg/budgcash/budgcarry/budgCarryMain";

	}
	
	/**
	 * @Description 
	 * 待结转月份 、已结转月份 赋值
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/budgcash/budgcarry/setValue", method = RequestMethod.POST)
	@ResponseBody
	public String setValue(Model mode) throws Exception {
		
		Map<String,Object> mapVo=new HashMap<String,Object>();
		mapVo.put("group_id",SessionManager.getGroupId());
		mapVo.put("hos_id",SessionManager.getHosId());
		mapVo.put("copy_code",SessionManager.getCopyCode());
		mapVo.put("mod_code","0206");
		
		// 已结转月份查询
		List<Map<String,Object>> listCarried= budgCarryService.queryYearMonthBefore(mapVo) ;
		
		String  year_month = "" ;
		
		String year_month_before = "" ;
		
		if(listCarried.size() > 0){
			
			year_month_before = String.valueOf(listCarried.get(0).get("year")) + listCarried.get(0).get("month");
			
			if(!"12".equals(listCarried.get(0).get("month").toString())){
				int month = Integer.parseInt(listCarried.get(0).get("month").toString())+1 ;
				
				if(month < 10){
					
					year_month = String.valueOf(listCarried.get(0).get("year")) +"0"+month;
					
				}else{
					
					year_month = String.valueOf(listCarried.get(0).get("year")) +""+month;
				}
			}else{
				String year = String.valueOf(Integer.parseInt(listCarried.get(0).get("year").toString())+1) ;
				String month = "01";
				year_month = year + month;
			}
			
		}else{//不存在 结转数据时  待结转月份为 业务预算模块启用年月
			
			year_month  = budgCarryService.queryYearMonthStart(mapVo);
		}
		
		return "{\"year_month\":\""+year_month+"\",\"year_month_before\":\""+year_month_before+"\"}";

	}
	
	/**
	 *  结转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcarry/chargeBudgWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chargeBudgWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		mapVo.put("cash_user", SessionManager.getUserId());
		
		//获取当前日期  Date类型
		Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
		mapVo.put("cash_date", date);
	
		String budgJson = null ;
		
		try {
			
			 budgJson = budgCarryService.chargeBudgWork(mapVo);
			 
		} catch (Exception e) {
			
			 budgJson = e.getMessage();
		}
				
		return JSONObject.parseObject(budgJson);
	}
	
	/**
	 * 反结
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/budgcash/budgcarry/reChargeBudgWork", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reChargeBudgWork(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
	
		mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		mapVo.put("cash_user", SessionManager.getUserId());
		
		//获取当前日期  Date类型
		Date date = DateUtil.getCurrenDate("yyyy-MM-dd");
		mapVo.put("cash_date", date);
	
		
		String budgJson = null ;
		
		try {
			
			budgJson = budgCarryService.reChargeBudgWork(mapVo);
			
		} catch (Exception e) {
			
			budgJson = e.getMessage();
		}
		
		return JSONObject.parseObject(budgJson);
	}
	


}

