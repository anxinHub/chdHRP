/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould.other;
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
import com.chd.hrp.ass.entity.assremould.other.AssRemouldFOther;
import com.chd.hrp.ass.entity.assremould.other.AssRemouldFdetailOther;
import com.chd.hrp.ass.service.assremould.other.AssRemouldFDetailOtherService;
import com.chd.hrp.ass.service.assremould.other.AssRemouldFOtherService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(其他固定资产)
 * @Table:
 * ASS_REMOULD_F_Other
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldFOtherController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldFOtherController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldFOtherService")
	private final AssRemouldFOtherService assRemouldFOtherService = null;
	@Resource(name="assRemouldFDetailOtherService")
	   private final AssRemouldFDetailOtherService   assRemouldFDetailOtherService=null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfOther/assRemouldFOtherMainPage", method = RequestMethod.GET)
	public String assRemouldFOtherMainPage(Model mode) throws Exception {

		return "hrp/ass/assother/assremouldfother/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/assRemouldFOtherAddPage", method = RequestMethod.GET)
	public String assRemouldFOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldfother/add";

	}

	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/addAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldFOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
       
		String assRemouldFOtherJson = assRemouldFOtherService.add(mapVo);

		return JSONObject.parseObject(assRemouldFOtherJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/saveAssRemouldFSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFSourceOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String assRemouldFOtherJson = assRemouldFOtherService.saveAssRemouldFSourceOther(mapVo);
		
		return JSONObject.parseObject(assRemouldFOtherJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/assRemouldFOtherUpdatePage", method = RequestMethod.GET)
	public String assRemouldFOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRemouldFOther assRemouldFOther = new AssRemouldFOther();
    
		assRemouldFOther = assRemouldFOtherService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldFOther.getGroup_id());
		mode.addAttribute("hos_id", assRemouldFOther.getHos_id());
		mode.addAttribute("copy_code", assRemouldFOther.getCopy_code());
		mode.addAttribute("fcord_no", assRemouldFOther.getFcord_no());
		mode.addAttribute("bus_type_code", assRemouldFOther.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldFOther.getVen_id());
		mode.addAttribute("ven_no", assRemouldFOther.getVen_no());
		mode.addAttribute("sup_code", assRemouldFOther.getSup_code());
		mode.addAttribute("sup_name", assRemouldFOther.getSup_name());
		mode.addAttribute("create_emp", assRemouldFOther.getCreate_emp());
		mode.addAttribute("create_date", assRemouldFOther.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldFOther.getAudit_emp());
		mode.addAttribute("fcord_date", assRemouldFOther.getFcord_date());
		mode.addAttribute("state", assRemouldFOther.getState());
		mode.addAttribute("note", assRemouldFOther.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldfother/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfOther/updateAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldFOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldFOtherJson = assRemouldFOtherService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldFOtherJson);
	}
	/**
	 * @Description 
	 * 资产改造竣工确认(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfOther/updateConfirmAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssRemouldFOther(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
			AssRemouldFOther  assRemouldAspecial=assRemouldFOtherService.queryByCode(mapVo);
		if(assRemouldAspecial == null || assRemouldAspecial.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldFdetailOther> detailList = assRemouldFDetailOtherService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldFdetailOther detail : detailList) {
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

		
		String assRemouldAspecialJson = assRemouldFOtherService.updateConfirmAssRemouldFOther(listVo,listCardVo);
	
		return JSONObject.parseObject(assRemouldAspecialJson);

	}
	
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/saveAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldFOtherJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
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
	  
		assRemouldFOtherJson = assRemouldFOtherService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldFOtherJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfOther/deleteAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fcord_no", ids[3]);
				AssRemouldFOther assRemouldAspecial = assRemouldFOtherService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assRemouldFOtherJson = assRemouldFOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldFOtherJson);
			
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/deleteAssRemouldFDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFDetailOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFOtherJson = assRemouldFOtherService.deleteAssRemouldFDetailOther(listVo);
		
		return JSONObject.parseObject(assRemouldFOtherJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/deleteAssRemouldFSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFSourceOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFOtherJson = assRemouldFOtherService.deleteAssRemouldFSourceOther(listVo);
		
		return JSONObject.parseObject(assRemouldFOtherJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfOther/queryAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFOther = assRemouldFOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldFOther);
		
	}
	
	
	/**
	 * @Description 
	 * 查询资金来源 资产改造竣工(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/queryAssRemouldFSourceOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFSourceOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFOther = assRemouldFOtherService.queryAssRemouldFSourceOther(mapVo);
		
		return JSONObject.parseObject(assRemouldFOther);
		
	}
	/**
	 * @Description 
	 * 修改时查询数据 050805 资产改造竣工明细数据(其他固定资产)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/queryAssRemouldFDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFDetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFOther = assRemouldFOtherService.queryAssRemouldFDetailOther(mapVo);
		
		return JSONObject.parseObject(assRemouldFOther);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050805 资产改造竣工(其他固定资产)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/assRemouldFOtherImportPage", method = RequestMethod.GET)
	public String assRemouldFOtherImportPage(Model mode) throws Exception {

		return "hrp/ass/assother/assremouldfother/assRemouldFOtherImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050805 资产改造竣工(其他固定资产)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assremould/assremouldfother/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","050805 资产改造竣工(其他固定资产).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050805 资产改造竣工(其他固定资产)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assremould/assremouldfother/readAssRemouldFOtherFiles",method = RequestMethod.POST)  
    public String readAssRemouldFOtherFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRemouldFOther> list_err = new ArrayList<AssRemouldFOther>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRemouldFOther assRemouldFOther = new AssRemouldFOther();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRemouldFOther.setFcord_no(temp[3]);
		    		mapVo.put("fcord_no", temp[3]);
					
					} else {
						
						err_sb.append("改造竣工单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRemouldFOther.setBus_type_code(temp[4]);
		    		mapVo.put("bus_type_code", temp[4]);
					
					} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRemouldFOther.setVen_id(Long.valueOf(temp[5]));
		    		mapVo.put("ven_id", temp[5]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRemouldFOther.setVen_no(Long.valueOf(temp[6]));
		    		mapVo.put("ven_no", temp[6]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assRemouldFOther.setCreate_emp(Long.valueOf(temp[7]));
		    		mapVo.put("create_emp", temp[7]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assRemouldFOther.setCreate_date(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("create_date", temp[8]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assRemouldFOther.setAudit_emp(Long.valueOf(temp[9]));
		    		mapVo.put("audit_emp", temp[9]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assRemouldFOther.setFcord_date(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("fcord_date", temp[10]);
					
					} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assRemouldFOther.setState(Integer.valueOf(temp[11]));
		    		mapVo.put("state", temp[11]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assRemouldFOther.setNote(temp[12]);
		    		mapVo.put("note", temp[12]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					
				AssRemouldFOther data_exc_extis = assRemouldFOtherService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldFOther.setError_type(err_sb.toString());
					
					list_err.add(assRemouldFOther);
					
				} else {
			  
					String dataJson = assRemouldFOtherService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldFOther data_exc = new AssRemouldFOther();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050805 资产改造竣工(其他固定资产)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfother/addBatchAssRemouldFOther", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRemouldFOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRemouldFOther> list_err = new ArrayList<AssRemouldFOther>();
		
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
			
			AssRemouldFOther assRemouldFOther = new AssRemouldFOther();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("fcord_no"))) {
						
					assRemouldFOther.setFcord_no((String)jsonObj.get("fcord_no"));
		    		mapVo.put("fcord_no", jsonObj.get("fcord_no"));
		    		} else {
						
						err_sb.append("改造竣工单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {
						
					assRemouldFOther.setBus_type_code((String)jsonObj.get("bus_type_code"));
		    		mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
		    		} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {
						
					assRemouldFOther.setVen_id(Long.valueOf((String)jsonObj.get("ven_id")));
		    		mapVo.put("ven_id", jsonObj.get("ven_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {
						
					assRemouldFOther.setVen_no(Long.valueOf((String)jsonObj.get("ven_no")));
		    		mapVo.put("ven_no", jsonObj.get("ven_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assRemouldFOther.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assRemouldFOther.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assRemouldFOther.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fcord_date"))) {
						
					assRemouldFOther.setFcord_date(DateUtil.stringToDate((String)jsonObj.get("fcord_date")));
		    		mapVo.put("fcord_date", jsonObj.get("fcord_date"));
		    		} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assRemouldFOther.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assRemouldFOther.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					
				AssRemouldFOther data_exc_extis = assRemouldFOtherService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldFOther.setError_type(err_sb.toString());
					
					list_err.add(assRemouldFOther);
					
				} else {
			  
					String dataJson = assRemouldFOtherService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldFOther data_exc = new AssRemouldFOther();
			
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

