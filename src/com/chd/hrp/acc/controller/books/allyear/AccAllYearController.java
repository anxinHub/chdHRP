package com.chd.hrp.acc.controller.books.allyear;

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
import com.chd.hrp.acc.service.books.allyear.AccAllYearService;
/**
 * 全年账簿
 * @author gaopei
 *
 */
@Controller 
public class AccAllYearController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccAllYearController.class);
	
	@Resource(name = "accAllYearService")
	private final AccAllYearService accAllYearService = null; 
	
	/**
	 * 科目明细账页面
	 * */
	@RequestMapping(value = "hrp/acc/allyear/accSubjDetailPage", method = RequestMethod.GET)
	public String accThreeColumnLedgerDetailMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/allyear/accSubjDetailMain";
	}
	
	/**
	 * 科目明细账查询
	 * */
	@RequestMapping(value = "hrp/acc/allyear/collectAllYearBySubjDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAllYearBySubjDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		 mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result = accAllYearService.collectAllYearBySubjDetail(mapVo);
        
		return JSONObject.parseObject(result) ;
	}
	/**
	 * 辅助明细账页面
	 * */
	@RequestMapping(value = "hrp/acc/allyear/accZcheckDetailPage", method = RequestMethod.GET)
	public String accZcheckDetailPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/allyear/accZcheckDetailMain";
	}
	
	/**
	 * 辅助明细账查询
	 * */
	@RequestMapping(value = "hrp/acc/allyear/collectAccZcheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccZcheckDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result= accAllYearService.collectAccZcheckDetail(mapVo);
        
       // String result= accZcheckAuxiliaryAccountService.collectAccZcheckDetailLedger(mapVo);
		
		return JSONObject.parseObject(result);
		
	}
	
}
