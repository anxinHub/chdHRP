package com.chd.hrp.acc.controller.vouch;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.superVouch.SysPrintTemplate;
import com.chd.hrp.acc.service.vouch.SuperPrintService;

@Controller
@RequestMapping(value="/hrp/acc/accvouch/superPrint")
public class SuperPrintController extends BaseController{

	private static Logger logger = Logger.getLogger(SuperPrintController.class);
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	
	/**
	 * 凭证打印-主页面
	*/
	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String mainPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		String yearMonth=MyConfig.getCurAccYearMonth();
		if(yearMonth==null || yearMonth.equals("000000")){
			String curDate= DateUtil.dateToString(new Date());
			yearMonth=curDate.substring(0,4)+curDate.substring(5,7);
			
		}
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		mode.addAttribute("acc_month", yearMonth.substring(4, 6));
		return "hrp/acc/accvouch/superPrint/main";
	}
	
	/**
	 * 打印模板设置入口，所有页面都走此Controller，调用页面根据系统参数判断，0统一打印(默认)，1按用户打印、2按仓库打印...
	*/
	@RequestMapping(value = "/printSetPage", method = RequestMethod.GET)
	public String printSetPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("template_code")==null){
			return "hrp/acc/accvouch/superPrint/printSet";
		}
		
		String useId="0";
		if(mapVo.get("use_id")!=null && !mapVo.get("use_id").toString().equals("")){
			useId=mapVo.get("use_id").toString();
		}
		
//		前台传use_id，所以不需要查询系统参数了
/*		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String printParaVal="0";//统一打印
		if(mapVo.get("para_code")!=null && !mapVo.get("para_code").equals("")){
			printParaVal=superPrintService.querySuperPrintAccPara(mapVo);
			if(printParaVal!=null && !printParaVal.equals("")){
				if(printParaVal.equals("1")){
					//按用户打印
					useId=SessionManager.getUserId();
				}else if(printParaVal.equals("2")){
					//按仓库打印
					if(mapVo.get("store_id")!=null && !mapVo.get("store_id").equals("")){
						useId=mapVo.get("store_id").toString();
					}
				}else if(printParaVal.equals("3")){
					//按供应商打印
					if(mapVo.get("sup_id")!=null && !mapVo.get("sup_id").equals("")){
						useId=mapVo.get("sup_id").toString();
					}
				}
			}
		}
*/		
		mode.addAttribute("template_code", mapVo.get("template_code"));
		mode.addAttribute("use_id", useId);
		
		return "hrp/acc/accvouch/superPrint/printSet";
	}
	
	/*打印机设置，设置纸张大小*/
	@RequestMapping(value = "/printInfoPage", method = RequestMethod.GET)
	public String printInfoPage(@RequestParam Map<String, Object> reMap,Model mode) throws Exception {
		
		
		return "hrp/acc/accvouch/superPrint/printInfo";
	}
	
	/**
	 * 业务调用页面,所有模块打印模板都走此controller，根据系统参数判断，0统一打印(默认)，1按用户打印、2按仓库打印...
	 * 查询打印模板内容
	 * @param mapVo
	 * @param mode
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySuperPrintContentByCode", method = RequestMethod.POST)//produces = "text/html;charset=UTF-8"
	@ResponseBody
	public Map<String, Object> querySuperPrintContentByCode(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
				
		String content=superPrintService.querySuperPrintContentByCode(mapVo);
		return JSONObject.parseObject(content);
		
	}
	
	/**
	 * 业务调用页面,所有模块打印模板都走此controller，根据系统参数判断，0统一打印(默认)，1按用户打印、2按仓库打印...
	 * 打印模板设置页面，查询打印模板参数
	 * @param mapVo
	 * @param mode
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySuperPrintParaByListJosn", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperPrintParaByListJosn(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String content="";
		try {
			content=superPrintService.querySuperPrintParaByListJosn(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			content="{\"error\":\""+e.getMessage()+"\"}";
		}
		return content;
		
	}
	
	/**
	 * 业务调用页面,所有模块打印模板都走此controller，根据系统参数判断，0统一打印，1按用户打印、2按仓库打印...
	 * 保存打印模板
	 */
	@RequestMapping(value = "/saveSuperPrintTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSuperPrintTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("create_date",DateUtil.getCurrenDate());
		
		String reJson=null;
		try {
			reJson=superPrintService.saveSuperPrintTemplate(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reJson="{\"error\":\""+StringTool.string2Json(e.getMessage())+"\"}";
		}
		return JSONObject.parseObject(reJson);
		
	}
	
	
	/**
	 *  下拉框-取科目全称
	 */
	@RequestMapping(value = "/querySubjNameAll", method = RequestMethod.POST)
	@ResponseBody
	public String querySubjNameAll(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String resJson=superPrintService.querySubjNameAllDict(mapVo);
		return resJson;
		
	}
	
	
	@RequestMapping(value = "/querySuperPrintParaByCode", method = RequestMethod.POST)
	@ResponseBody
	public String querySuperPrintParaByCode(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String content="";
		try {
			content=superPrintService.querySuperPrintParaByCode(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			content="{\"error\":\""+e.getMessage()+"\"}";
		}
		return content;
		
	}
	
	//保存打印次数
	@RequestMapping(value = "/savePrintCount", method = RequestMethod.POST)
	@ResponseBody
	public String savePrintCount(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		
		if(mapVo.get("business_no")==null || mapVo.get("business_no").equals("")){
			return "{\"state\":\"false\"}";
		}
		
		try {
			
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			String businessNo=mapVo.get("business_no").toString();
			//102-CK17010154|1@102-CK17010155|2
			String []businessNoA=businessNo.split(",");
			if(businessNoA==null || businessNoA.length==0){
				return "{\"state\":\"false\"}";
			}
			
			Map<String, Object> map=null;
			for(int i=0;i<businessNoA.length;i++){
				map=new HashMap<String,Object>();
				String business=businessNoA[i];
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("template_code", mapVo.get("template_code"));
				if(business!=null && !business.equals("") && business.split("@").length==2){
					map.put("business_no", business.split("@")[0]);
					map.put("print_count",business.split("@")[1]);
					list.add(map);
				}
				
			}
			
			if(list==null || list.size()==0){
				return "{\"state\":\"false\"}";
			}
			
			String reJson=superPrintService.savePrintCount(list);
			return reJson;
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"state\":\"false\"}";
		}
		
	}
}
