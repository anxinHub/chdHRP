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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.books.subjaccount.GroupAccSubjLedgerService;

@Controller
public class GroupAccSubjLedgerController extends BaseController{
	
	private static Logger logger = Logger.getLogger(GroupAccSubjLedgerController.class);
	
	@Resource(name = "groupAccSubjLedgerService")
	private final GroupAccSubjLedgerService groupAccSubjLedgerService = null;
	
	/**
	 * 科目总账页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccSubjLedgerMainPage", method = RequestMethod.GET)
	public String groupAccSubjLedgerMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		return "hrp/acc/accsubjledger/group/groupAccSubjLedgerMain";
	}
	
	/**
	 * 科目总账查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/collectGroupAccSubjLedger", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccSubjLedger(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));

        String result = groupAccSubjLedgerService.collectGroupAccSubjLedger(mapVo);
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 科目明细账页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccSubjLedgerDetailMainPage", method = RequestMethod.GET)
	public String groupAccSubjLedgerDetailMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accsubjledger/group/groupAccSubjLedgerDetailMain";
	}
	
	/**
	 * 科目明细账查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/collectGroupAccSubjLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccSubjLedgerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        /*if(!"".equals(mapVo.get("subj_code"))){
        	mapVo.put("subj_code", mapVo.get("subj_code").toString().replaceAll(";", ",").replaceAll(" ", ""));
        }*/
        
        String result = groupAccSubjLedgerService.collectGroupAccSubjLedgerDetail(mapVo);
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 三栏明细账页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccThreeColumnLedgerDetailMainPage", method = RequestMethod.GET)
	public String groupAccThreeColumnLedgerDetailMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accsubjledger/group/groupAccThreeColumnLedgerDetailMain";
	}
	
	/**
	 * 三栏明细账查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/collectGroupAccThreeColumnLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccThreeColumnLedgerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());

        /*if(!"".equals(mapVo.get("subj_code"))){
        	mapVo.put("subj_code", mapVo.get("subj_code").toString().replaceAll(";", ",").replaceAll(" ", ""));
        }*/
        
        String result = groupAccSubjLedgerService.collectGroupThreeColumnLedgerDetail(mapVo);
        
		return JSONObject.parseObject(result);
		
	}
	/**
	 * 科目汇总表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccSubjLedgerSumMainPage", method = RequestMethod.GET)
	public String groupAccSubjLedgerSumMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		
		return "hrp/acc/accsubjledger/group/groupAccSubjLedgerSumMain";
	}
	
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/queryGroupSubjLedgerSum", method = RequestMethod.POST)
	@ResponseBody
	public String queryGroupSubjLedgerSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));
       
		return groupAccSubjLedgerService.queryGroupAccVouchCountSum(mapVo);
		
	}
	
	
	/**
	 * 科目汇总表查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/collectGroupAccSubjLedgerSumMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupAccSubjLedgerSumMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));
        
        String result = groupAccSubjLedgerService.queryGroupAccVouchCountSumDetail(mapVo);
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 余额明细账页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccBalanceLedgerDetailMainPage", method = RequestMethod.GET)
	public String groupAccBalanceLedgerDetailMainPage(Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		
		return "hrp/acc/accsubjledger/group/groupAccBalanceLedgerDetailMain";
	}
	
	/**
	 * 余额明细账查询
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/collectGroupBalanceLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectGroupBalanceLedgerDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        mapVo.put("accPara022", MyConfig.getSysPara("022"));
        
        String result = groupAccSubjLedgerService.collectGroupBalanceLedgerDetail(mapVo);
        
		return JSONObject.parseObject(result);
		
	}
	
	/**
	 * 多栏账设置页面
	 * */
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/groupAccSetColumnsLedgerMainPage", method = RequestMethod.GET)
	public String groupAccColumnsSetPage(Model mode) throws Exception {
		
		return "hrp/acc/accsubjledger/group/groupAccSetColumnsLedgerMain";
	}
	/**
	 * 科目余额表明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/group/accSubjLederDetailPage", method = RequestMethod.GET)
	public String accSubjLederDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("acc_year", mapVo.get("acc_year").toString().substring(0, 4));
		
		mode.addAttribute("acc_month", mapVo.get("acc_year").toString().substring(5, 7));
		
		mode.addAttribute("year_month", mapVo.get("year_month").toString().substring(5, 7));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		
		mode.addAttribute("year_month_b", mapVo.get("acc_year"));
		
		mode.addAttribute("year_month_e", mapVo.get("year_month"));
		
		return "hrp/acc/accsubjledger/group/accSubjLedgerDetail";
	}
	/**
	 * 科目余额表明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/group/queryAccSubjLederDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjLederDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
        String result = groupAccSubjLedgerService.queryGroupAccSubjLederDetail(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/acc/group/accSubjLederCheckPage", method = RequestMethod.GET)
	public String accSubjLederCheckPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("acc_year", mapVo.get("acc_year").toString().substring(0, 4));
		
		mode.addAttribute("acc_month", mapVo.get("acc_year").toString().substring(5, 7));
		
		mode.addAttribute("year_month", mapVo.get("year_month").toString().substring(5, 7));
		
		mode.addAttribute("state", mapVo.get("state"));
		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		
		mode.addAttribute("subj_id", mapVo.get("subj_id"));
		
		return "hrp/acc/accsubjledger/group/accSubjLedgerCheck";
	}
	
	
	/**
	 * 科目余额表  辅助核算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/group/queryAccSubjLederCkeck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjLederCkeck(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
       
        String result = groupAccSubjLedgerService.queryGroupAccSubjLederCheck(mapVo);
        
		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/acc/group/queryAccSubjByPlan", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjByPlan(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		if(null==mapVo.get("plan_code")){
        	
			mapVo.put("plan_code", "0");
       
		}
       
        String result = groupAccSubjLedgerService.queryGroupAccSubjByPlan(mapVo);
		
        return JSONObject.parseObject(result);
	}
	
	//查询数据
	@RequestMapping(value = "/hrp/acc/accsubjledger/group/queryGroupAccColumnsLedgerDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccColumnsLedgerDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String result = groupAccSubjLedgerService.queryGroupAccColumnsLedgerDetail(mapVo);
		
		return JSONObject.parseObject(result);
	}
	/**
	 * 科目余额表  辅助核算明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/group/accSubjLederCheckDetailPage", method = RequestMethod.GET)
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
		
		return "hrp/acc/accsubjledger/group/groupAccSubjLedgerCheckDetail";
	}
	
	/**
	 * 科目余额表 查询辅助核算明细
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/group/queryAccSubjLedgerCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccSubjLedgerCheckDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
       
        String result = groupAccSubjLedgerService.queryGroupAccSubjLedgerCheckDetail(mapVo);
		
        return JSONObject.parseObject(result);
	}
	/**
	 * 查询科目  按subj_code查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/group/accsubjledger/group/queryGroupAccSubjTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupAccSubjTree(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String result = groupAccSubjLedgerService.queryGroupAccSubjTree(mapVo);
		
		return JSONObject.parseObject(result);
	}
	
	
}
