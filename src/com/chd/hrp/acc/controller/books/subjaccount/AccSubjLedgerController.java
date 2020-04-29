package com.chd.hrp.acc.controller.books.subjaccount;


import java.util.Date;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService;
 
@Controller
public class AccSubjLedgerController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AccSubjLedgerController.class);
	
	@Resource(name = "accSubjLedgerService")
	private final AccSubjLedgerService accSubjLedgerService = null;
	
	/**
	 * 科目总账 按月页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/accSubjLedgerMainPage", method = RequestMethod.GET)
	public String accSubjLedgerMainPage(Model mode) throws Exception {
		
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		return "hrp/acc/accsubjledger/accSubjLedgerMain";
	}
	
	/**
	 * 科目总账 按月查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/collectAccSubjLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccSubjLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));

        String result = accSubjLedgerService.collectAccSubjLedger(getPage(mapVo));
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 科目总账 按天页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/accSubjLedgerDetailMainPage", method = RequestMethod.GET)
	public String accSubjLedgerDetailMainPage(Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		return "hrp/acc/accsubjledger/accSubjLedgerDetailMain";
	}
	
	/**
	 * 科目总账 按天查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/collectAccSubjLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccSubjLedgerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result = accSubjLedgerService.collectAccSubjLedgerDetail(getPage(mapVo));
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 三栏明细账页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/accThreeColumnLedgerDetailMainPage", method = RequestMethod.GET)
	public String accThreeColumnLedgerDetailMainPage(Model mode) throws Exception {
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		mode.addAttribute("accPara043", MyConfig.getSysPara("043"));
		return "hrp/acc/accsubjledger/accThreeColumnLedgerDetailMain";
	}
	
	/**
	 * 三栏明细账查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/collectAccThreeColumnLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccThreeColumnLedgerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());

        /*if(!"".equals(mapVo.get("subj_code"))){
        	mapVo.put("subj_code", mapVo.get("subj_code").toString().replaceAll(";", ",").replaceAll(" ", ""));
        }*/
        
        String result = accSubjLedgerService.collectThreeColumnLedgerDetail(getPage(mapVo));
		return JSONObject.parseObject(result);
		
	}
	/**
	 * 科目汇总表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsubjledger/accSubjLedgerSumMainPage", method = RequestMethod.GET)
	public String accSubjLedgerSumMainPage(Model mode) throws Exception {
		
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		
		return "hrp/acc/accsubjledger/accSubjLedgerSumMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/querySubjLedgerSum", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjLedgerSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));
       
		return accSubjLedgerService.queryAccVouchCountSum(mapVo);
		
	}
	
	/**
	 * 科目汇总表查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/collectAccSubjLedgerSumMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectAccSubjLedgerSumMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", mapVo.get("acc_year_b").toString());
        //mapVo.put("create_date_b", mapVo.get("acc_year_b").toString()+"-"+mapVo.get("acc_month_b").toString());
        //mapVo.put("create_date_e", mapVo.get("acc_year_e").toString()+"-"+mapVo.get("acc_month_e").toString());
        mapVo.put("accPara022", MyConfig.getSysPara("022"));
        String result = accSubjLedgerService.queryAccVouchCountSumDetail(getPage(mapVo));
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 余额明细账页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/accBalanceLedgerDetailMainPage", method = RequestMethod.GET)
	public String accBalanceLedgerDetailMainPage(Model mode) throws Exception {
		
		String yearMonth=SessionManager.getAcctYear();
		int month = new Date().getMonth() + 1;
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+(month < 10 ? "0"+month:month));
		
		return "hrp/acc/accsubjledger/accBalanceLedgerDetailMain";
	}
	
	/**
	 * 余额明细账查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/collectBalanceLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBalanceLedgerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));
        
        String result = accSubjLedgerService.collectBalanceLedgerDetail(getPage1(mapVo));
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 多栏账设置页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/accSetColumnsLedgerMainPage", method = RequestMethod.GET)
	public String accColumnsSetPage(Model mode) throws Exception {
		
		return "hrp/acc/accsubjledger/accSetColumnsLedgerMain";
	}
	/**
	 * 科目余额表明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accSubjLederDetailPage", method = RequestMethod.GET)
	public String accSubjLederDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("acc_year", mapVo.get("acc_year").toString().substring(0, 4));
		
		mode.addAttribute("acc_month", mapVo.get("acc_year").toString().substring(5, 7));
		
		mode.addAttribute("year_month", mapVo.get("year_month").toString().substring(5, 7));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		
		mode.addAttribute("year_month_b", mapVo.get("acc_year"));
		
		mode.addAttribute("year_month_e", mapVo.get("year_month"));
		
		return "hrp/acc/accsubjledger/accSubjLedgerDetail";
	}
	/**
	 * 科目余额表明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/queryAccSubjLederDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjLederDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result = accSubjLedgerService.queryAccSubjLederDetail(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/acc/accSubjLederCheckPage", method = RequestMethod.GET)
	public String accSubjLederCheckPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("acc_year", mapVo.get("acc_year").toString().substring(0, 4));
		
		mode.addAttribute("acc_month", mapVo.get("acc_year").toString().substring(5, 7));
		
		mode.addAttribute("year_month", mapVo.get("year_month").toString().substring(5, 7));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		
		mode.addAttribute("subj_id", mapVo.get("subj_id"));
		
		return "hrp/acc/accsubjledger/accSubjLedgerCheck";
	}
	
	
	/**
	 * 科目余额表  辅助核算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/queryAccSubjLederCkeck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjLederCkeck(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
   //     mapVo.put("acc_year", SessionManager.getAcctYear());
       
        String result = accSubjLedgerService.queryAccSubjLederCheck(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/acc/queryAccSubjByPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjByPlan(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(null==mapVo.get("plan_code")){
        	
			mapVo.put("plan_code", "0");
       
		}
       
        String result = accSubjLedgerService.queryAccSubjByPlan(mapVo);
		
        return JSONObject.parseObject(result);
	}
	
	//多栏明细账查询
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccColumnsLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccColumnsLedgerDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String result = accSubjLedgerService.queryAccColumnsLedgerDetail(getPage(mapVo));
		
		return JSONObject.parseObject(result);
	}
	/**
	 * 科目余额表  辅助核算明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accSubjLederCheckDetailPage", method = RequestMethod.GET)
	public String accSubjLederCheckDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("subj_id", mapVo.get("subj_id"));
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		
		mode.addAttribute("acc_month", mapVo.get("acc_month"));
		
		mode.addAttribute("year_month", mapVo.get("year_month"));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("value1", mapVo.get("value1"));
		
		mode.addAttribute("value2", mapVo.get("value2"));
		
		mode.addAttribute("value3", mapVo.get("value3"));
		
		mode.addAttribute("value4", mapVo.get("value4"));
		
		return "hrp/acc/accsubjledger/accSubjLedgerCheckDetail";
	}
	
	/**
	 * 科目余额表 查询辅助核算明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/queryAccSubjLedgerCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjLedgerCheckDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
       
        String result = accSubjLedgerService.queryAccSubjLedgerCheckDetail(mapVo);
		
        return JSONObject.parseObject(result);
	}
	/**
	 * 查询科目  按subj_code查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryAccSubjTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String result = accSubjLedgerService.queryAccSubjTree(mapVo);
		
		return JSONObject.parseObject(result);
	}
	
	/**
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsubjledger/queryKMZZTVouchNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryKMZZTVouchNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson=null;
		try {
			reJson=accSubjLedgerService.queryKMZZTVouchNo(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
}
