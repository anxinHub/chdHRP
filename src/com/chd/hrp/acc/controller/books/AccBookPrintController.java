package com.chd.hrp.acc.controller.books;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.chd.base.startup.LoadChdInfo;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.books.AccBookPrintService;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;

@Controller
@RequestMapping(value="/hrp/acc/books/print")
public class AccBookPrintController extends BaseController{

	private static Logger logger = Logger.getLogger(AccBookPrintController.class);
	@Resource(name = "accBookPrintService")
	private final AccBookPrintService accBookPrintService = null;
	
	/**
	 * 账簿打印-主页面
	*/
	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String mainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
		}
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/books/print/main";
	}
	
	/**
	 * 打印科目查询
	 */
	@RequestMapping(value = "/queryAccBooksPrintSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccBooksPrintSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String msg = accBookPrintService.queryAccBooksPrintSubj(mapVo);

		return JSONObject.parseObject(msg);
	}
}
