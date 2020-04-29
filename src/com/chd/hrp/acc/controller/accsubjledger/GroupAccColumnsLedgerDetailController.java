package com.chd.hrp.acc.controller.accsubjledger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.MyConfig;


@Controller
public class GroupAccColumnsLedgerDetailController {
	
	private static Logger logger = Logger.getLogger(GroupAccColumnsLedgerDetailController.class);
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccColumnsLedgerDetailMainPage", method = RequestMethod.GET)
	public String groupAccColumnsLedgerDetailMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accsubjledger/group/groupAccColumnsLedgerDetailMain";
	}
}
