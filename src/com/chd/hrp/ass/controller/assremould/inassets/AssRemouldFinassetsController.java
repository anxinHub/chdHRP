/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould.inassets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldFdetailInassets;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldFinassets;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldFDetailInassetsService;
import com.chd.hrp.ass.service.assremould.inassets.AssRemouldFinassetsService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(无形资产)
 * @Table:
 * ASS_REMOULD_F_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldFinassetsController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldFinassetsController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldFinassetsService")
	private final AssRemouldFinassetsService assRemouldFinassetsService = null;
	@Resource(name="assRemouldFDetailInassetsService")
	   private final AssRemouldFDetailInassetsService   assRemouldFDetailInassetsService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/assRemouldFinassetsMainPage", method = RequestMethod.GET)
	public String assRemouldFinassetsMainPage(Model mode) throws Exception {

		return "hrp/ass/assinassets/assremould/assremouldfinassets/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/assRemouldFinassetsAddPage", method = RequestMethod.GET)
	public String assRemouldFinassetsAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assremould/assremouldfinassets/add";

	}

	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/addAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldFinassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
    	mapVo.put("create_emp", SessionManager.getUserId());
		
    	String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
       
		String assRemouldFinassetsJson = assRemouldFinassetsService.add(mapVo);

		return JSONObject.parseObject(assRemouldFinassetsJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/saveAssRemouldFSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
    	mapVo.put("create_emp", SessionManager.getUserId());
		
    	String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		String assRemouldFinassetsJson = assRemouldFinassetsService.saveAssRemouldFSourceInassets(mapVo);
		
		return JSONObject.parseObject(assRemouldFinassetsJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/assRemouldFinassetsUpdatePage", method = RequestMethod.GET)
	public String assRemouldFinassetsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		AssRemouldFinassets assRemouldFinassets = new AssRemouldFinassets();
    
		assRemouldFinassets = assRemouldFinassetsService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldFinassets.getGroup_id());
		mode.addAttribute("hos_id", assRemouldFinassets.getHos_id());
		mode.addAttribute("copy_code", assRemouldFinassets.getCopy_code());
		mode.addAttribute("fcord_no", assRemouldFinassets.getFcord_no());
		mode.addAttribute("bus_type_code", assRemouldFinassets.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldFinassets.getVen_id());
		mode.addAttribute("ven_no", assRemouldFinassets.getVen_no());
		mode.addAttribute("sup_code", assRemouldFinassets.getSup_code());
		mode.addAttribute("sup_name", assRemouldFinassets.getSup_name());
		mode.addAttribute("create_emp", assRemouldFinassets.getCreate_emp());
		mode.addAttribute("create_date", assRemouldFinassets.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldFinassets.getAudit_emp());
		mode.addAttribute("fcord_date", assRemouldFinassets.getFcord_date());
		mode.addAttribute("state", assRemouldFinassets.getState());
		mode.addAttribute("note", assRemouldFinassets.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assinassets/assremould/assremouldfinassets/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/updateAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldFinassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldFinassetsJson = assRemouldFinassetsService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldFinassetsJson);
	}
	/**
	 * @Description 
	 *  资产改造竣工确认(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/updateConfirmAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssRemouldFinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listCardVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("fcord_no", ids[3]);
			mapVo.put("fcord_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
//			mapVo.put("apply_no", ids[4]);
		//查询主表是否存在
		AssRemouldFinassets  assRemouldAspecial=assRemouldFinassetsService.queryByCode(mapVo);
		if(assRemouldAspecial == null || assRemouldAspecial.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldFdetailInassets> detailList = assRemouldFDetailInassetsService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldFdetailInassets detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAspecial.getBus_type_code());
					listCardMap.put("dispose_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				//如果明细表不存在返回没有数据
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		//判断listVo是否有数据
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		
		String assRemouldAspecialJson = assRemouldFinassetsService.updateConfirmAssRemouldFinassets(listVo,listCardVo);
	
		return JSONObject.parseObject(assRemouldAspecialJson);

	}
	
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/saveAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFinassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldFinassetsJson ="";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
    	
    	mapVo.put("create_emp", SessionManager.getUserId());
		
    	String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(4, 6);
    	//启动系统时间
		Map<String, Object> start = SessionManager.getModStartMonth();
		
		String startyearmonth = (String) start.get(SessionManager.getModCode());
		
		int result = startyearmonth.compareTo(yearmonth);
		
		
		if(result > 0 ){
			
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
			
		}  
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
	  
		assRemouldFinassetsJson = assRemouldFinassetsService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldFinassetsJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/deleteAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFinassets(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fcord_no", ids[3]);
				AssRemouldFinassets assRemouldAspecial = assRemouldFinassetsService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
	      listVo.add(mapVo);
	      
	    }
	    
		String assRemouldFinassetsJson = assRemouldFinassetsService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldFinassetsJson);
			
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/deleteAssRemouldFDetailinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFDetailinassets(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("fcord_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			
			listVo.add(mapVo);
			
		}
		
		String assRemouldFinassetsJson = assRemouldFinassetsService.deleteAssRemouldFDetailinassets(listVo);
		
		return JSONObject.parseObject(assRemouldFinassetsJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/deleteAssRemouldFSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFSourceInassets(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", ids[0])   ;
			mapVo.put("hos_id", ids[1])   ;
			mapVo.put("copy_code", ids[2])   ;
			mapVo.put("fcord_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);
			mapVo.put("source_id", ids[5]);
			
			listVo.add(mapVo);
			
		}
		
		String assRemouldFinassetsJson = assRemouldFinassetsService.deleteAssRemouldFSourceInassets(listVo);
		
		return JSONObject.parseObject(assRemouldFinassetsJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/queryAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFinassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String assRemouldFinassets = assRemouldFinassetsService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldFinassets);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/queryAssRemouldFSourceInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFSourceInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		if(mapVo.get("acct_year") == null){
			
			mapVo.put("acct_year", SessionManager.getAcctYear());
			
		}
		String assRemouldFinassets = assRemouldFinassetsService.queryAssRemouldFSourceInassets(mapVo);
		
		return JSONObject.parseObject(assRemouldFinassets);
		
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工明细数据(无形资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/queryAssRemouldFDetailInassets", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFDetailInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
		}
		
		if(mapVo.get("copy_code") == null){
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
		}
		if(mapVo.get("acct_year") == null){
			
			mapVo.put("acct_year", SessionManager.getAcctYear());
			
		}
		String assRemouldFinassets = assRemouldFinassetsService.queryAssRemouldFDetailInassets(mapVo);
		
		return JSONObject.parseObject(assRemouldFinassets);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050805 资产改造竣工(无形资产)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/assRemouldFinassetsImportPage", method = RequestMethod.GET)
	public String assRemouldFinassetsImportPage(Model mode) throws Exception {

		return "hrp/ass/assremould/assremouldfinassets/assRemouldFinassetsImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050805 资产改造竣工(无形资产)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assremould/assremouldfinassets/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","050805 资产改造竣工(无形资产).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050805 资产改造竣工(无形资产)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assremould/assremouldfinassets/readAssRemouldFinassetsFiles",method = RequestMethod.POST)  
    public String readAssRemouldFinassetsFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRemouldFinassets> list_err = new ArrayList<AssRemouldFinassets>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRemouldFinassets assRemouldFinassets = new AssRemouldFinassets();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRemouldFinassets.setFcord_no(temp[3]);
		    		mapVo.put("fcord_no", temp[3]);
					
					} else {
						
						err_sb.append("改造竣工单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRemouldFinassets.setBus_type_code(temp[4]);
		    		mapVo.put("bus_type_code", temp[4]);
					
					} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRemouldFinassets.setVen_id(Long.valueOf(temp[5]));
		    		mapVo.put("ven_id", temp[5]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRemouldFinassets.setVen_no(Long.valueOf(temp[6]));
		    		mapVo.put("ven_no", temp[6]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assRemouldFinassets.setCreate_emp(Long.valueOf(temp[7]));
		    		mapVo.put("create_emp", temp[7]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assRemouldFinassets.setCreate_date(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("create_date", temp[8]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assRemouldFinassets.setAudit_emp(Long.valueOf(temp[9]));
		    		mapVo.put("audit_emp", temp[9]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assRemouldFinassets.setFcord_date(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("fcord_date", temp[10]);
					
					} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assRemouldFinassets.setState(Integer.valueOf(temp[11]));
		    		mapVo.put("state", temp[11]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assRemouldFinassets.setNote(temp[12]);
		    		mapVo.put("note", temp[12]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					
				AssRemouldFinassets data_exc_extis = assRemouldFinassetsService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldFinassets.setError_type(err_sb.toString());
					
					list_err.add(assRemouldFinassets);
					
				} else {
			  
					String dataJson = assRemouldFinassetsService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldFinassets data_exc = new AssRemouldFinassets();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050805 资产改造竣工(无形资产)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfinassets/addBatchAssRemouldFinassets", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRemouldFinassets(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRemouldFinassets> list_err = new ArrayList<AssRemouldFinassets>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
	
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
				
			}
			
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
				
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
			}
		
			Iterator it = json.iterator();
		
			try {
			
			while (it.hasNext()) {
				
			StringBuffer err_sb = new StringBuffer();
			
			AssRemouldFinassets assRemouldFinassets = new AssRemouldFinassets();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("fcord_no"))) {
						
					assRemouldFinassets.setFcord_no((String)jsonObj.get("fcord_no"));
		    		mapVo.put("fcord_no", jsonObj.get("fcord_no"));
		    		} else {
						
						err_sb.append("改造竣工单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {
						
					assRemouldFinassets.setBus_type_code((String)jsonObj.get("bus_type_code"));
		    		mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
		    		} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {
						
					assRemouldFinassets.setVen_id(Long.valueOf((String)jsonObj.get("ven_id")));
		    		mapVo.put("ven_id", jsonObj.get("ven_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {
						
					assRemouldFinassets.setVen_no(Long.valueOf((String)jsonObj.get("ven_no")));
		    		mapVo.put("ven_no", jsonObj.get("ven_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assRemouldFinassets.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assRemouldFinassets.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assRemouldFinassets.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fcord_date"))) {
						
					assRemouldFinassets.setFcord_date(DateUtil.stringToDate((String)jsonObj.get("fcord_date")));
		    		mapVo.put("fcord_date", jsonObj.get("fcord_date"));
		    		} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assRemouldFinassets.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assRemouldFinassets.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					
				AssRemouldFinassets data_exc_extis = assRemouldFinassetsService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldFinassets.setError_type(err_sb.toString());
					
					list_err.add(assRemouldFinassets);
					
				} else {
			  
					String dataJson = assRemouldFinassetsService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldFinassets data_exc = new AssRemouldFinassets();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

