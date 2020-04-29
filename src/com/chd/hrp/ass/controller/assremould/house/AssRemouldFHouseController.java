/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould.house;
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
import com.chd.hrp.ass.entity.assremould.house.AssRemouldFHouse;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldFdetailHouse;
import com.chd.hrp.ass.service.assremould.house.AssRemouldFDetailHouseService;
import com.chd.hrp.ass.service.assremould.house.AssRemouldFHouseService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(专用设备)
 * @Table:
 * ASS_REMOULD_F_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldFHouseController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldFHouseController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldFHouseService")
	private final AssRemouldFHouseService assRemouldFHouseService = null;
	@Resource(name="assRemouldFDetailHouseService")
	   private final AssRemouldFDetailHouseService   assRemouldFDetailHouseService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/assRemouldFHouseMainPage", method = RequestMethod.GET)
	public String assRemouldFHouseMainPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/assremouldfhouse/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/assRemouldFHouseAddPage", method = RequestMethod.GET)
	public String assRemouldFHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assremouldfhouse/add";

	}

	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/addAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldFHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
    	String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
       
		String assRemouldFHouseJson = assRemouldFHouseService.add(mapVo);

		return JSONObject.parseObject(assRemouldFHouseJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */                       
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/saveAssRemouldFSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFSourceHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assRemouldFHouseJson = assRemouldFHouseService.saveAssRemouldFSourceHouse(mapVo);
		
		return JSONObject.parseObject(assRemouldFHouseJson);
		
	}
	
	
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/assRemouldFHouseUpdatePage", method = RequestMethod.GET)
	public String assRemouldFHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRemouldFHouse assRemouldFHouse = new AssRemouldFHouse();
    
		assRemouldFHouse = assRemouldFHouseService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldFHouse.getGroup_id());
		mode.addAttribute("hos_id", assRemouldFHouse.getHos_id());
		mode.addAttribute("copy_code", assRemouldFHouse.getCopy_code());
		mode.addAttribute("fcord_no", assRemouldFHouse.getFcord_no());
		mode.addAttribute("bus_type_code", assRemouldFHouse.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldFHouse.getVen_id());
		mode.addAttribute("ven_no", assRemouldFHouse.getVen_no());
		mode.addAttribute("sup_code", assRemouldFHouse.getSup_code());
		mode.addAttribute("sup_name", assRemouldFHouse.getSup_name());
		mode.addAttribute("create_emp", assRemouldFHouse.getCreate_emp());
		mode.addAttribute("create_date", assRemouldFHouse.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldFHouse.getAudit_emp());
		mode.addAttribute("fcord_date", assRemouldFHouse.getFcord_date());
		mode.addAttribute("state", assRemouldFHouse.getState());
		mode.addAttribute("note", assRemouldFHouse.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/assremouldfhouse/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/updateAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldFHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldFHouseJson = assRemouldFHouseService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldFHouseJson);
	}
	/**
	 * @Description 
	 * 资产改造竣工确认(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/updateConfirmAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAspecialConfirmState(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		AssRemouldFHouse  assRemouldAspecial=assRemouldFHouseService.queryByCode(mapVo);
		if(assRemouldAspecial == null || assRemouldAspecial.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldFdetailHouse> detailList = assRemouldFDetailHouseService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldFdetailHouse detail : detailList) {
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

		
		String assRemouldAspecialJson = assRemouldFHouseService.updateConfirmAssRemouldFHouse(listVo,listCardVo);
	
		return JSONObject.parseObject(assRemouldAspecialJson);

	}
	
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/saveAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldFHouseJson ="";
		

		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		
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
	  
		assRemouldFHouseJson = assRemouldFHouseService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldFHouseJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/deleteAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fcord_no", ids[3]);
				AssRemouldFHouse assRemouldAspecial = assRemouldFHouseService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
	      listVo.add(mapVo);
	      
	    }
	    
		String assRemouldFHouseJson = assRemouldFHouseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldFHouseJson);
			
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/deleteAssRemouldFDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFDetailHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFHouseJson = assRemouldFHouseService.deleteAssRemouldFDetailHouse(listVo);
		
		return JSONObject.parseObject(assRemouldFHouseJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/deleteAssRemouldFSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFSourceHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFHouseJson = assRemouldFHouseService.deleteAssRemouldFSourceHouse(listVo);
		
		return JSONObject.parseObject(assRemouldFHouseJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/queryAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFHouse = assRemouldFHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldFHouse);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工资金来源(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/queryAssRemouldFSourceHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFSourceHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFHouse = assRemouldFHouseService.queryAssRemouldFSourceHouse(mapVo);
		
		return JSONObject.parseObject(assRemouldFHouse);
		
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工明细数据(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */                       
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/queryAssRemouldFDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFHouse = assRemouldFHouseService.queryAssRemouldFDetailHouse(mapVo);
		
		return JSONObject.parseObject(assRemouldFHouse);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 050805 资产改造竣工(专用设备)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfHouse/assRemouldFHouseImportPage", method = RequestMethod.GET)
	public String assRemouldFHouseImportPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/assremouldfHouse/assRemouldFHouseImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050805 资产改造竣工(专用设备)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assremould/assremouldfHouse/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"budg\\downTemplate","050805 资产改造竣工(专用设备).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050805 资产改造竣工(专用设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assremould/assremouldfHouse/readAssRemouldFHouseFiles",method = RequestMethod.POST)  
    public String readAssRemouldFHouseFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssRemouldFHouse> list_err = new ArrayList<AssRemouldFHouse>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssRemouldFHouse assRemouldFHouse = new AssRemouldFHouse();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assRemouldFHouse.setFcord_no(temp[3]);
		    		mapVo.put("fcord_no", temp[3]);
					
					} else {
						
						err_sb.append("改造竣工单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assRemouldFHouse.setBus_type_code(temp[4]);
		    		mapVo.put("bus_type_code", temp[4]);
					
					} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assRemouldFHouse.setVen_id(Long.valueOf(temp[5]));
		    		mapVo.put("ven_id", temp[5]);
					
					} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assRemouldFHouse.setVen_no(Long.valueOf(temp[6]));
		    		mapVo.put("ven_no", temp[6]);
					
					} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assRemouldFHouse.setCreate_emp(Long.valueOf(temp[7]));
		    		mapVo.put("create_emp", temp[7]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assRemouldFHouse.setCreate_date(DateUtil.stringToDate(temp[8]));
		    		mapVo.put("create_date", temp[8]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assRemouldFHouse.setAudit_emp(Long.valueOf(temp[9]));
		    		mapVo.put("audit_emp", temp[9]);
					
					} else {
						
						err_sb.append("审核人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assRemouldFHouse.setFcord_date(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("fcord_date", temp[10]);
					
					} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assRemouldFHouse.setState(Integer.valueOf(temp[11]));
		    		mapVo.put("state", temp[11]);
					
					} else {
						
						err_sb.append("状态为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assRemouldFHouse.setNote(temp[12]);
		    		mapVo.put("note", temp[12]);
					
					} else {
						
						err_sb.append("摘要为空  ");
						
					}
					 
					
				AssRemouldFHouse data_exc_extis = assRemouldFHouseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldFHouse.setError_type(err_sb.toString());
					
					list_err.add(assRemouldFHouse);
					
				} else {
			  
					String dataJson = assRemouldFHouseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldFHouse data_exc = new AssRemouldFHouse();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050805 资产改造竣工(专用设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfhouse/addBatchAssRemouldFHouse", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssRemouldFHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssRemouldFHouse> list_err = new ArrayList<AssRemouldFHouse>();
		
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
			
			AssRemouldFHouse assRemouldFHouse = new AssRemouldFHouse();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("fcord_no"))) {
						
					assRemouldFHouse.setFcord_no((String)jsonObj.get("fcord_no"));
		    		mapVo.put("fcord_no", jsonObj.get("fcord_no"));
		    		} else {
						
						err_sb.append("改造竣工单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {
						
					assRemouldFHouse.setBus_type_code((String)jsonObj.get("bus_type_code"));
		    		mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
		    		} else {
						
						err_sb.append("01：改建 02:扩建 03:大型修缮为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {
						
					assRemouldFHouse.setVen_id(Long.valueOf((String)jsonObj.get("ven_id")));
		    		mapVo.put("ven_id", jsonObj.get("ven_id"));
		    		} else {
						
						err_sb.append("供应商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {
						
					assRemouldFHouse.setVen_no(Long.valueOf((String)jsonObj.get("ven_no")));
		    		mapVo.put("ven_no", jsonObj.get("ven_no"));
		    		} else {
						
						err_sb.append("供应商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assRemouldFHouse.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assRemouldFHouse.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("audit_emp"))) {
						
					assRemouldFHouse.setAudit_emp(Long.valueOf((String)jsonObj.get("audit_emp")));
		    		mapVo.put("audit_emp", jsonObj.get("audit_emp"));
		    		} else {
						
						err_sb.append("审核人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fcord_date"))) {
						
					assRemouldFHouse.setFcord_date(DateUtil.stringToDate((String)jsonObj.get("fcord_date")));
		    		mapVo.put("fcord_date", jsonObj.get("fcord_date"));
		    		} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assRemouldFHouse.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("状态为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assRemouldFHouse.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("摘要为空  ");
						
					}
					
					
				AssRemouldFHouse data_exc_extis = assRemouldFHouseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assRemouldFHouse.setError_type(err_sb.toString());
					
					list_err.add(assRemouldFHouse);
					
				} else {
			  
					String dataJson = assRemouldFHouseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssRemouldFHouse data_exc = new AssRemouldFHouse();
			
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

