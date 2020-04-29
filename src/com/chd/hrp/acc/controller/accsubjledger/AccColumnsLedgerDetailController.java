package com.chd.hrp.acc.controller.accsubjledger;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;


@Controller
public class AccColumnsLedgerDetailController {
	
	private static Logger logger = Logger.getLogger(AccColumnsLedgerDetailController.class);
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/accColumnsLedgerDetailMainPage", method = RequestMethod.GET)
	public String accBadDebtsPreparaMainPage(Model mode) throws Exception {
		
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		
		return "hrp/acc/accsubjledger/accColumnsLedgerDetailMain";
	}
}
