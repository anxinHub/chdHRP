package com.chd.hrp.acc.controller.verification;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.serviceImpl.AccLederCheckServiceImpl;
import com.chd.hrp.acc.serviceImpl.AccLederServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCheckTypeServiceImpl;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccSubjServiceImpl;
import com.chd.hrp.acc.serviceImpl.verification.AccNostroServiceImpl;
import com.chd.hrp.acc.serviceImpl.verification.AccVerificationServiceImpl;
import com.chd.hrp.acc.serviceImpl.vouch.AccVouchCheckServiceImpl;
@Controller
public class AccNostroController extends BaseController{
	private static Logger logger = Logger.getLogger(AccNostroController.class);
	
	@Resource(name = "accSubjService")
	private final AccSubjServiceImpl accSubjService = null;
	
	@Resource(name = "accLederService")
	private final AccLederServiceImpl accLederService = null;
	
	@Resource(name = "accLederCheckService")
	private final AccLederCheckServiceImpl accLederCheckService = null;
	
	@Resource(name = "accCheckTypeService")
	private final AccCheckTypeServiceImpl accCheckTypeService = null;
	
	@Resource(name = "accVouchCheckService")
	private final AccVouchCheckServiceImpl accVouchCheckService = null;
	
	@Resource(name = "accVerificationService")
	private final AccVerificationServiceImpl accVerificationService = null;
	
	@Resource(name = "accNostroService")
	private final AccNostroServiceImpl accNostroService = null;
	
	/**
	 * 往来初始账页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/accCurrentAccountPage", method = RequestMethod.GET)
	public String accCurrentAccountPage(Model mode) throws Exception {
		return "hrp/acc/accnostro/accCurrentAccountMain";
	}
	
	/**
	 * 往来初始账页面 查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryAccCurrentAccount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCurrentAccount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {	 
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());		
	    mapVo.put("copy_code", SessionManager.getCopyCode());       
	    mapVo.put("acc_year", SessionManager.getAcctYear());
	    String accSubj = accNostroService.queryCurrentAccount(getPage(mapVo));
		return JSONObject.parseObject(accSubj);
	}
	
	
	
	/**
	 * 往来初始账 更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/accCurrentAccountDetailPage", method = RequestMethod.GET)
	public String accCurrentAccountDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());

		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		mode.addAttribute("subj_dire",mapVo.get("subj_dire"));
		
		AccLederCheck accLederCheck = accNostroService.queryAccLederCheckBySubjId(mapVo);
		mode.addAttribute("accLederCheck", accLederCheck);
		return "hrp/acc/accnostro/accCurrentAccountDetail";
	}
	/**
	 * 往来初始账 更新页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryAccCurrentAccountInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCurrentAccountInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
        
		String accVouchCheck = accNostroService.queryAccCurrentAccountInit(getPage(mapVo));
		return JSONObject.parseObject(accVouchCheck);		
	}
	
	/**
	 * 往来初始账 添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/addAccVouchVerCheckInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccVouchVerCheckInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	        
		}
		
		if(mapVo.get("acc_year") == null){
			
	        mapVo.put("acc_year", SessionManager.getAcctYear());
	        
		}
		
		String accVouchCheckJson = accVouchCheckService.addAccVouchCheckByNostro(mapVo);
		
		return JSONObject.parseObject(accVouchCheckJson);
	}
	/**
	*往来初始账 删除
	*/
	@RequestMapping(value = "/hrp/acc/accnostro/deleteAccVouchVerCheckInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchVerCheckInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("vouch_check_id", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
            mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            mapVo.put("is_init", 1);//实际实体类变量
            listVo.add(mapVo);
        }
		String accVouchCheckJson = accNostroService.deleteAccVouchCheckInit(listVo);
	   return JSONObject.parseObject(accVouchCheckJson);
			
	}
	
	
	/**
	*往来初始账校验明细 删除
	*/
	@RequestMapping(value = "/hrp/acc/accnostro/deleteAccVouchCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchCheck(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("vouch_check_id", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
            mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accVouchCheckJson = accVouchCheckService.deleteBatchAccVouchCheck(listVo);
	   return JSONObject.parseObject(accVouchCheckJson);
			
	}
	
	
	/**
	 * 往来初始账校验
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/accCurrentAccountCheckPage", method = RequestMethod.GET)
	public String accCurrentAccountCheckPage(Model mode) throws Exception {
		return "hrp/acc/accnostro/accCurrentAccountCheckMain";
	}
	
	/**
	 * 往来初始校验 页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryAccCurrentAccountCheck", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCurrentAccountCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());       
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String accSubj = accNostroService.queryCurrentAccountCheck(getPage(mapVo));
		return JSONObject.parseObject(accSubj);
		
	}
	
	/**
	 * 往来初始账校验  明细页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/accCurrentAccountCheckDetailPage", method = RequestMethod.GET)
	public String accCurrentAccountCheckDetailPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		mode.addAttribute("subj_name", mapVo.get("subj_name"));
		mode.addAttribute("subj_dire",mapVo.get("subj_dire"));
		return "hrp/acc/accnostro/accCurrentAccountCheckDetail";
	}
	
	/**
	 * 获得动态表头
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/getItemTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getItemTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {	 
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());        
        mapVo.put("acc_year", SessionManager.getAcctYear());		
        AccSubj accSubj = accSubjService.queryAccSubjByCode(mapVo);
        String re = JsonListBeanUtil.beanToJson(accSubj);
		return JSONObject.parseObject(re);
		
	}
	
	
	/**
	 * 往来初始校验 明细页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryAccCurrentAccountCheckDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCurrentAccountCheckDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		mapVo.put("group_id", SessionManager.getGroupId());	
		mapVo.put("hos_id", SessionManager.getHosId());	
        mapVo.put("copy_code", SessionManager.getCopyCode()); 
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        
        String result =  accNostroService.queryCurrentAccountCheckDetail(getPage(mapVo));
		return JSONObject.parseObject(result);
		
	}
	
	
	/**
	 * 往来查询--余额查询 页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/balanceMainPage", method = RequestMethod.GET)
	public String balanceMain(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accnostro/nostroquery/accBalanceMain";
	}
	
	/**
	 * 往来查询 --余额页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryBalance", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryBalance(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {		 
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
	    mapVo.put("copy_code", SessionManager.getCopyCode()); 
	    mapVo.put("acc_year", SessionManager.getAcctYear()); 
	    String result = ""; 
	    result = accNostroService.queryAccLederCheckBalanceO(getPage(mapVo));       
		return JSONObject.parseObject(result);
			
	}	
	
	
	/**
	 * 往来查询--明细账查询跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/detailAccountMainPage", method = RequestMethod.GET)
	public String detailAccountMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accnostro/nostroquery/accDetailAccountMain";
	}
	/**
	 * 往来查询--明细账查询页面  查询
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryAccDetailAccountMain", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccDetailAccountMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode()); 
        mapVo.put("acc_year", SessionManager.getAcctYear());  
        
        String result = "";
        //mapVo.put("objdata", new ArrayList<Map<String, Object>>());
        result = accNostroService.collectQueryAccDetailAccount(getPage(mapVo));  
    	return JSONObject.parseObject(result);
      
	}
	
	/**
	 * 往来查询--核销清册页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/inventoryVerificationMainPage", method = RequestMethod.GET)
	public String inventoryVerificationMainPage(Model mode) throws Exception {
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accnostro/nostroquery/accInventoryVerificationMain";
	}
	
	/**
	 * 往来查询--核销清册页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryInventoryVerification", method = RequestMethod.POST)
	@ResponseBody	
	public Map<String, Object> queryInventoryVerification(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
       
        String result = "";
        
        result = accNostroService.queryAccVerificationDetail(getPage(mapVo));
		return JSONObject.parseObject(result);
		
	}
	
	
	/**
	 * 往来查询--已核销金额页面跳转（借方）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/accInventoryVerificationQetail", method = RequestMethod.GET)
	public String accInventoryVerificationQetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("acc_year", SessionManager.getAcctYear());
	    //查询veri_check_id
	    String veri_check_id=accVerificationService.queryVeriCheckId(mapVo);
		mode.addAttribute("check_id", mapVo.get("check_id"));
		mode.addAttribute("check_type", mapVo.get("check_type"));
		mode.addAttribute("check_type_id", mapVo.get("check_type_id"));
		mode.addAttribute("subj_dire", mapVo.get("subj_dire"));
		mode.addAttribute("check_code", mapVo.get("check_code"));
		mode.addAttribute("veri_check_id",veri_check_id);
		mode.addAttribute("subj_code", mapVo.get("subj_code"));
		
		return "hrp/acc/accnostro/nostroquery/accInventoryVerificationQetail";
	}
		
	/**
	 * 往来查询--已核销金额页面查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/accInventoryVerificationQ", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> accInventoryVerificationQ(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        String result = "";
        
        
		result = accVerificationService.queryAccVerDetail(getPage(mapVo));
		
		return JSONObject.parseObject(result);
	}
	
	
	/**
	 * 往来查询--个人往来催款单
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/pContactsReminderMainPage", method = RequestMethod.GET)
	public String pContactsReminderMainPage(Model mode) throws Exception {	
		String yearMonth=MyConfig.getCurAccYearMonth();
		mode.addAttribute("yearMonth", yearMonth.substring(0, 4)+"."+yearMonth.substring(4, 6));
		return "hrp/acc/accnostro/nostroquery/accPContactsReminderMain";		
	}
	/**
	 * 往来查询--个人往来催款单查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryContactsReminderDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryContactsReminderDetail(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		String result ="";
		mapVo.put("group_id", SessionManager.getGroupId());		
		mapVo.put("hos_id", SessionManager.getHosId());		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
        
        if(mapVo.get("show_history") == null){
        	mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
        
       //是否记账状态
        if(mapVo.get("is_check") !=null){
        	if(mapVo.get("is_check").equals("1")){
            	mapVo.put("state", " and av.state > 0 and av.state < 99");
            }else{
            	mapVo.put("state",  " and av.state = 99");
            }
        }
      //核销状态
       if(mapVo.get("verificationState") !=null){
        	if(mapVo.get("verificationState").equals("3")){ //已核销  bal_amt=debit and  bal_amt>0
            	mapVo.put("balState"," and nvl(a.debit,0) = nvl(a.bal_amt,0)  and nvl(a.bal_amt,0) >0  ");
            }else if(mapVo.get("verificationState").equals("2")){//部分核销  bal_amt>0 && bal_amt<debit
            	mapVo.put("balState"," and nvl(a.debit,0) > nvl(a.bal_amt,0)  and nvl(a.bal_amt,0) > 0 ");
            }else if(mapVo.get("verificationState").equals("1")){//未核销 bal_amt=0 
            	mapVo.put("balState"," and nvl(a.bal_amt,0) = 0 ");
            }else {
            	mapVo.put("balState","");
            }
        }
        
        result = accNostroService.queryContactsReminderDetail(mapVo);   
        return JSONObject.parseObject(result);
		
	}
	
	//下载导入模版
	@RequestMapping(value="hrp/acc/accnostro/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 
		
		List<List> list = new ArrayList();
		List<String> listdata = new ArrayList<String>();		
		Map<String, Object> entityMap = new HashMap<String, Object>();	
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		listdata.add("摘要");
		if(!"".equals(mapVo.get("subj_code")) && mapVo.get("subj_code") != null){		
			entityMap.put("subj_code", mapVo.get("subj_code"));			
			Map<String, Object> titleMap = accLederService.queryTitle(entityMap);			
			if(titleMap != null){			
				if(!"".equals(titleMap.get("CHECK1_NAME"))&& null != titleMap.get("CHECK1_NAME")){					
					listdata.add(titleMap.get("CHECK1_NAME").toString()+"编码");					
					listdata.add(titleMap.get("CHECK1_NAME").toString()+"名称");					
				}
				
				if(!"".equals(titleMap.get("CHECK2_NAME"))&& null != titleMap.get("CHECK2_NAME")){				
					listdata.add(titleMap.get("CHECK2_NAME").toString()+"编码");					
					listdata.add(titleMap.get("CHECK2_NAME").toString()+"名称");					
				}
				
				if(!"".equals(titleMap.get("CHECK3_NAME"))&& null != titleMap.get("CHECK3_NAME")){					
					listdata.add(titleMap.get("CHECK3_NAME").toString()+"编码");					
					listdata.add(titleMap.get("CHECK3_NAME").toString()+"名称");					
				}
				
				if(!"".equals(titleMap.get("CHECK4_NAME"))&& null != titleMap.get("CHECK4_NAME")){					
					listdata.add(titleMap.get("CHECK4_NAME").toString()+"编码");					
					listdata.add(titleMap.get("CHECK4_NAME").toString()+"名称");					
				}				
			}			
		}

		listdata.add("发生日期");		
		listdata.add("到期日期");		
		listdata.add("合同编号");		
		listdata.add("单据号");		
		listdata.add("金额");		
		list.add(listdata);		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "\\" + "excelTemplate\\"+"\\acc\\downTemplate\\";		
		String downLoadPath = ctxPath + "往来辅助核算初始账.xls";		
		ExcelWriter.exportExcel(new File(downLoadPath), list);		
		printTemplate(request, response, "acc\\downTemplate", "往来辅助核算初始账.xls");
		return null; 
	 }
	
	@RequestMapping(value = "/hrp/acc/accnostro/accCurrentAccountImportPage", method = RequestMethod.GET)
	public String accCurrentAccountImportPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {		
		mode.addAttribute("subj_code", mapVo.get("subj_code"));		
		mode.addAttribute("group_id",mapVo.get("group_id"));		
		mode.addAttribute("hos_id", mapVo.get("hos_id"));		
		mode.addAttribute("copy_code", mapVo.get("copy_code"));		
		mode.addAttribute("subj_dire", mapVo.get("subj_dire"));		
		return "hrp/acc/accnostro/accCurrentAccountImport";	
	}
	@RequestMapping(value = "/hrp/acc/accnostro/accCurrentAccountImportLJ",method = RequestMethod.POST)
	@ResponseBody
	public String accCurrentAccountImportLJ(@RequestParam Map<String, Object> mapVo, Model mode) {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		
		String para = mapVo.get("para").toString();
		JSONObject JSONOpara = JSONObject.parseObject(para);
		mapVo.put("subj_code", JSONOpara.get("subj_code"));
		mapVo.put("subj_dire", JSONOpara.get("subj_dire"));
		Map<String, Object> table = accLederService.queryTitle(mapVo);
		String accVouchCheckJson=accVouchCheckService.getIsAccFlag(mapVo);
		JSONObject aa = JSONObject.parseObject(accVouchCheckJson);
		if("false".equals(aa.get("state"))) {
			
			return "{\"error\":\"没有结账\"}";
		}
		try {
			
			String reJson = accNostroService.readFilesImport(mapVo,table);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	
	/**
	 * 查询会计科目  是否挂辅助核算
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/querySubjIsCheck", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjIsCheck(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String subj = accVerificationService.querySubjIsCheck(getPage(mapVo));
		return subj;
		
	}
	/**
	 * //单位字典
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accnostro/queryVHosDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryVHosDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String subj = accLederService.queryHosInfo(mapVo);
		return subj;
		
	}
	/**
	 * @Description 
	 * 导入数据 科目辅助核算
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/acc/accnostro/readAccVouchCheckRelaFiles",method = RequestMethod.POST)  
	 public String readAccVouchCheckRelaFiles(@RequestParam Map<String, Object> entityMap,Plupload plupload,HttpServletRequest request,HttpServletResponse response,Model mode) throws IOException { 			 
			
		 List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();			
		
		 List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		 //根据科目ID查询所挂的辅助核算
		 entityMap.put("group_id", SessionManager.getGroupId());
		 entityMap.put("hos_id", SessionManager.getHosId());
		 entityMap.put("copy_code", SessionManager.getCopyCode());
		 entityMap.put("acc_year", SessionManager.getAcctYear());
		 
		 Map<String, Object> table = accLederService.queryTitle(entityMap);			
		
		 StringBuffer sql = new StringBuffer();//拼接插入数据的列名			
			
		 try {
				
			 for (int i = 1; i < list.size(); i++) {					
				
				 sql.setLength(0);
				 
				 StringBuffer err_sb = new StringBuffer();					
					
				 StringBuffer sqlValue = new StringBuffer();//拼接插入数据的列值					
					
				 Map<String, Object> map = new HashMap<String, Object>();					
					
				 String temp[] = list.get(i);// 行					
					
				 //根据科目ID查询出所挂的辅助核算项
					
				 Map<String, Object> mapVo = new HashMap<String, Object>();					
					
				 if (entityMap.get("group_id") == null) {						
						
					 map.put("group_id", SessionManager.getGroupId());						
					
				 }else{						
						
					 map.put("group_id", entityMap.get("group_id"));						
					
				 }
					
					
				 if (entityMap.get("hos_id") == null) {						
						
					 map.put("hos_id", SessionManager.getHosId());						
					
				 }else{						
						
					 map.put("hos_id", entityMap.get("hos_id"));						
					
				 }
					
					
				 if (entityMap.get("copy_code") == null) {						
						
					 map.put("copy_code", SessionManager.getCopyCode());					
					
				 }else{						
						
					 map.put("copy_code", entityMap.get("copy_code"));						
					
				 }
						
					
				 map.put("acc_year", SessionManager.getAcctYear());						
					
				 map.put("subj_id",entityMap.get("subj_id"));					
					
				 map.put("vouch_check_ids",UUIDLong.absStringUUID());	
					
				 map.put("vouch_detail_id","");
					
				 map.put("vouch_id","");
					//String regexStr = "^\\d+(\\.\\d+)?$";						
					
				 if(StringTool.isNotBlank(temp[temp.length-1])){							
						//if(temp[temp.length-1].matches(regexStr)){							
							
					 if("0".equals(entityMap.get("subj_dire"))){		
								
						 map.put("debit", Double.parseDouble(temp[temp.length-1]));					
								
						 map.put("credit", 0);
								//如果出现错误，将导入信息导入到页面
								
						 mapVo.put("money", Double.parseDouble(temp[temp.length-1]));				
							
					 }else{
								
						 map.put("debit", 0);
								
						 map.put("credit", Double.parseDouble(temp[temp.length-1]));
								
						 mapVo.put("money", Double.parseDouble(temp[temp.length-1]));
							}		
						/*}else{
							err_sb.append("金额不是数字  ");		
						}*/
					} else {		
						
						err_sb.append("金额为空  ");		
					
					}
					
				 if(StringTool.isNotBlank(temp[temp.length-2])){
						
					 mapVo.put("business_no", temp[temp.length-2]);		
						
					 map.put("business_no", temp[temp.length-2]);			
					
				 } else {
						
					mapVo.put("business_no", "");
					
					map.put("business_no", "");
					
				}/* else {		
						
					 err_sb.append("单据号为空  ");	
					
				 }*/
						
					
				 if(StringTool.isNotBlank(temp[temp.length-3])){
							
					mapVo.put("con_no", temp[temp.length-3]);
								
					map.put("con_no", temp[temp.length-3]);
								
				} else {
					
				mapVo.put("con_no", "");
				
				map.put("con_no", "");
				
			}/*else {
								
					err_sb.append("合同编号为空  ");
							
				}*/
						
					//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						
					if(StringTool.isNotBlank(temp[temp.length-4])){
							
						try {
								
							//format.setLenient(false);
								
							mapVo.put("due_date", temp[temp.length-4]);

							map.put("due_date", temp[temp.length-4]);
								
							} catch (Exception e) {
								
							err_sb.append("到期日期格式不正确  ");
								
							}
								
						} else {
							
							mapVo.put("due_date", "");
							
							map.put("due_date", "");
							
						} /*else {
								
							err_sb.append("到期日期为空  ");
							
						}*/
						
						if(StringTool.isNotBlank(temp[temp.length-5])){
							
							try {
								
								//format.setLenient(false);
								
								mapVo.put("occur_date", temp[temp.length-5]);
								
								map.put("occur_date", temp[temp.length-5]);
								
							} catch (Exception e) {
								
								err_sb.append("发生日期格式不正确  "); 
								
							}
								
						} else {
								
							err_sb.append("发生日期为空  ");
							
						}
						
						if(StringTool.isNotBlank(temp[0])){
							
							mapVo.put("summary", temp[0]);
							
							map.put("summary", temp[0]);
								
						} else {
								
							mapVo.put("summary", "");
							
							map.put("summary", "");
							
						}
						
						if (temp.length>7) {
							
							if(StringTool.isNotBlank(temp[1])){
								
								mapVo.put("column_code_value", temp[1].toString().trim());
								
								map.put("check1_code", temp[1]);
								
								if(StringTool.isNotBlank(temp[2])){
									
									map.put("check1_name", temp[2]);
									
								}								
								mapVo.put("column_code", table.get("COLUMN1_CODE"));
								
								mapVo.put("column_name", table.get("COLUMN1_NAME"));
								
								mapVo.put("column_id", table.get("COLUMN1_ID"));
								
								mapVo.put("column_no", table.get("COLUMN1_NO"));
								
								mapVo.put("query_table", table.get("CHECK1_TABLE"));
								
								if(table.get("CHECK1_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
									
									mapVo.put("check_type_id", table.get("CHECK1_TYPE_ID"));
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									//map.put(table.get("COLUMN1_CHECK").toString(), checkItem.get(table.get("COLUMN1_ID")));
									
									sqlValue.append(checkItem.get(table.get("COLUMN1_ID"))+",");
									
									sql.append(table.get("COLUMN1_CHECK").toString()+",");
									
								}else{
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									/*map.put("check1_id", checkItem.get(table.get("COLUMN1_ID")));
									
									map.put("check1_no", checkItem.get(table.get("COLUMN1_NO")));*/
									
									if(checkItem != null){
										
										sqlValue.append(checkItem.get(table.get("COLUMN1_ID"))+",");
										
										sqlValue.append(checkItem.get(table.get("COLUMN1_NO"))+",");
										//用于动态的获取辅助核算
										 String item =(String) table.get("COLUMN1_CHECK") ;
										 
											sql.append(item+"_id,");
											
											sql.append(item+"_no,");

										
										mapVo.put("check_type_id", "");
										
									}else{
										
										err_sb.append(table.get("CHECK1_NAME")+"编码名称不存在  ");
									}
									
								}
								
							} else {
								
									map.put("check1_code", temp[1]);
									
									map.put("check1_name", temp[2]);
									
									err_sb.append(table.get("CHECK1_NAME")+"编码为空  ");
								
							}
								
						}
									
						if (temp.length>9) {
							
							if(StringTool.isNotBlank(temp[3])){
								
								mapVo.put("column_code_value", temp[3]);
								
								map.put("check2_code", temp[3]);
								
								if(StringTool.isNotBlank(temp[4])){
									
									map.put("check2_name", temp[4]);
									
								}
							
								mapVo.put("column_code", table.get("COLUMN2_CODE").toString().trim());
								
								mapVo.put("column_name", table.get("COLUMN2_NAME"));
								
								mapVo.put("column_id", table.get("COLUMN2_ID"));
								
								mapVo.put("column_no", table.get("COLUMN2_NO"));
								
								mapVo.put("query_table", table.get("CHECK2_TABLE"));
								
								if(table.get("CHECK1_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
									
									mapVo.put("check_type_id", table.get("CHECK2_TYPE_ID"));
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									//map.put(table.get("COLUMN2_CHECK").toString(), checkItem.get(table.get("COLUMN2_ID")));
									
									sqlValue.append(checkItem.get(table.get("COLUMN2_ID"))+",");
									
									sql.append(table.get("COLUMN2_CHECK").toString()+",");
									
								}else{
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									/*map.put("check2_id", checkItem.get(table.get("COLUMN2_ID")));
									
									map.put("check2_no", checkItem.get(table.get("COLUMN2_NO")));*/
									
									if(checkItem != null){
										
										sqlValue.append(checkItem.get(table.get("COLUMN2_ID"))+",");
										
										sqlValue.append(checkItem.get(table.get("COLUMN2_NO")) +",");
										
										//用于动态的获取辅助核算
										 String item =(String) table.get("COLUMN2_CHECK") ;
										 
											sql.append(item+"_id,");
											
											sql.append(item+"_no,");

										
										mapVo.put("check_type_id", "");
									
										
									}else{
										
										err_sb.append(table.get("CHECK2_NAME")+"编码名称不存在  ");
									}
									
								}
								
							} else {
								
									map.put("check2_code", temp[3]);
									
									map.put("check2_name", temp[4]);
									
									err_sb.append(table.get("CHECK2_NAME")+"编码为空  ");
								
							}
								
						}
							
						if (temp.length>11) {
							
							if(StringTool.isNotBlank(temp[5])){
								
								mapVo.put("column_code_value", temp[5]);
								
								map.put("check3_code", temp[5]);
								
								if(StringTool.isNotBlank(temp[6])){
									
									map.put("check3_name", temp[6]);
									
								}
							
								mapVo.put("column_code", table.get("COLUMN3_CODE"));
								
								mapVo.put("column_name", table.get("COLUMN3_NAME"));
								
								mapVo.put("column_id", table.get("COLUMN3_ID"));
								
								mapVo.put("column_no", table.get("COLUMN3_NO"));
								
								mapVo.put("query_table", table.get("CHECK3_TABLE"));
								
								if(table.get("CHECK3_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
									
									mapVo.put("check_type_id", table.get("CHECK3_TYPE_ID"));
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									//map.put(table.get("COLUMN3_CHECK").toString(), checkItem.get(table.get("COLUMN3_ID")));
									
									sqlValue.append(checkItem.get(table.get("COLUMN3_ID"))+",");
									
									sql.append(table.get("COLUMN3_CHECK").toString()+",");
									
								}else{
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									/*map.put("check3_id", checkItem.get(table.get("COLUMN3_ID")));
									
									map.put("check3_no", checkItem.get(table.get("COLUMN3_NO")));*/
									
									if(checkItem != null){
										
										sqlValue.append(checkItem.get(table.get("COLUMN3_ID"))+",");
										
										sqlValue.append(checkItem.get(table.get("COLUMN3_NO"))+",");
										
										//用于动态的获取辅助核算
										 String item =(String) table.get("COLUMN3_CHECK") ;
										 
											sql.append(item+"_id,");
											
											sql.append(item+"_no,");

										
										
										mapVo.put("check_type_id", "");
										
									}else{
										
										err_sb.append(table.get("CHECK3_NAME")+"编码名称不存在  ");
									}
									
								}
								
							} else {
									
									map.put("check3_code", temp[4]);
									
									map.put("check3_name", temp[5]);
								
									err_sb.append(table.get("CHECK3_NAME")+"编码为空  ");
								
							}
								
						}
									
						if (temp.length>13) {
							
							if(StringTool.isNotBlank(temp[6])){
								
								mapVo.put("column_code_value", temp[6]);
								
								map.put("check4_code", temp[6]);
								
								if(StringTool.isNotBlank(temp[7])){
									
									map.put("check4_name", temp[7]);
									
								}
								
								mapVo.put("column_code", table.get("COLUMN4_CODE"));
								
								mapVo.put("column_name", table.get("COLUMN4_NAME"));
								
								mapVo.put("column_id", table.get("COLUMN4_ID"));
								
								mapVo.put("column_no", table.get("COLUMN4_NO"));
								
								mapVo.put("query_table", table.get("CHECK4_TABLE"));
								
								if(table.get("CHECK4_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
									
									mapVo.put("check_type_id", table.get("CHECK4_TYPE_ID"));
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									//map.put(table.get("COLUMN4_CHECK").toString(), checkItem.get(table.get("COLUMN4_ID")));
									
									sqlValue.append(checkItem.get(table.get("COLUMN4_ID"))+",");
									
									sql.append(table.get("COLUMN4_CHECK").toString()+",");
									
								}else{
									
									Map<String, Object> checkItem= accLederService.queryAccCheckItem(mapVo);
									
									/*map.put("check4_id", checkItem.get(table.get("COLUMN4_ID")));
									
									map.put("check4_no", checkItem.get(table.get("COLUMN4_NO")));*/
									
									if(checkItem != null){
										
										sqlValue.append(checkItem.get(table.get("COLUMN4_ID"))+",");
										
										sqlValue.append(checkItem.get(table.get("COLUMN4_NO"))+",");
										
										
										//用于动态的获取辅助核算
											
										 String item =(String) table.get("COLUMN4_CHECK") ;
										 
										 sql.append(item+"_id,");
											
										 sql.append(item+"_no,");
										
										mapVo.put("check_type_id", "");
										
									}else{
										
										err_sb.append(table.get("CHECK4_NAME")+"编码名称不存在  ");
									}
									
								}
								
							} else {
								
								map.put("check4_code", temp[6]);
								
								map.put("check4_name", temp[7]);
									
								err_sb.append(table.get("CHECK4_NAME")+"编码为空  ");
								
							}
								
						}

						map.put("error_type",err_sb.toString());

					
					map.put("sqlValue", sqlValue);
					
					list_err.add(map);
					
				}
				
				entityMap.put("itemList", list_err);
				
				entityMap.put("sql", sql);
				
				if("".equals(list_err.get(0).get("error_type").toString())||null==list_err.get(0).get("error_type").toString()){
					
					accVouchCheckService.importAccVouchCheck(entityMap);
					
					list_err.clear();//导入成功，删除list 中的数据
					
				}
				
			} catch (DataAccessException e) {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("error_type","导入系统出错");
				
				list_err.add(map);
				
			}
			
			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
			
			return null;
		
    } 
}
