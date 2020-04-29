/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.in;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.chd.base.util.NumberUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.in.AssInMainHouse;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.in.AssInDetailHouseService;
import com.chd.hrp.ass.service.in.AssInMainHouseService;
/**
 * 
 * @Description:
 * 资产入库主表(房屋及建筑)
 * @Table:
 * ASS_IN_MAIN_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssInMainHouseController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssInMainHouseController.class);
	
	//引入Service服务
	@Resource(name = "assInMainHouseService")
	private final AssInMainHouseService assInMainHouseService = null;
	@Resource(name = "assInDetailHouseService")
	private final AssInDetailHouseService assInDetailHouseService = null;
	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/assInMainHouseMainPage", method = RequestMethod.GET)
	public String assInMainHouseMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05054",MyConfig.getSysPara("05054"));
		
		return "hrp/ass/asshouse/assin/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/assInMainHouseAddPage", method = RequestMethod.GET)
	public String assInMainHouseAddPage(Model mode) throws Exception {
		mode.addAttribute("ass_nature", "01");
		return "hrp/ass/asscard/assInCardAdd";

	}

	/**
	 * @Description 
	 * 添加数据 资产入库主表(房屋及建筑)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/addAssInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInMainHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String assInMainHouseJson = assInMainHouseService.add(mapVo);

		return JSONObject.parseObject(assInMainHouseJson);
		
	}


	
	
	/**
	 * @Description 
	 * 删除数据 资产入库主表(房屋及建筑)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/deleteAssInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInMainHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssInMainHouse assInMainHouse = assInMainHouseService.queryByCode(mapVo);
			
			if(assInMainHouse.getState() > 0){
				flag = false;
				break;
			}
			
			listVo.add(mapVo);
		}
		
		if(!flag){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
		}

		String assInMainHouseJson = assInMainHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assInMainHouseJson);

	}
	
	/**
	 * @Description 
	 * 查询数据 资产入库主表(房屋及建筑)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/queryAssInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInMainHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		String assInMainHouse = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assInMainHouse = assInMainHouseService.query(getPage(mapVo));
			
		}else{
			
			assInMainHouse = assInMainHouseService.queryDetails(getPage(mapVo));
			
		}
		 

		return JSONObject.parseObject(assInMainHouse);
		
	}
	/**
	 * @Description 入库确认
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assin/updateConfirmInMainHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmInMainHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assInMainJson = "";
		for (String vdata : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = vdata.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			
			AssInMainHouse assInMainHouse = assInMainHouseService.queryByCode(mapVo);
			if (assInMainHouse.getState() == 2) {
				continue;
			}
			
			
			if(DateUtil.compareDate(assInMainHouse.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			
			List<Map<String, Object>> list = assInDetailHouseService.queryByInit(mapVo);//资产明细数据
			
			if(list.size() == 0){
				return JSONObject.parseObject("{\"warn\":\"单据不存在资产,不能入库! \"}");
			}
			for(Map<String, Object> temp : list){
				
				Map<String, Object> mapExists = new HashMap<String, Object>();

				mapExists.put("group_id", temp.get("group_id"));

				mapExists.put("hos_id", temp.get("hos_id"));

				mapExists.put("copy_code", temp.get("copy_code"));

				mapExists.put("ass_in_no", temp.get("ass_in_no"));
				
				mapExists.put("ass_id", temp.get("ass_id"));
				
				mapExists.put("ass_no", temp.get("ass_no"));
				
				Integer use_state = assCardHouseService.queryCardExistsByAssInNo(mapExists);//通过资产
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state != Integer.parseInt(temp.get("in_amount").toString())){
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardHouseService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true,
					
						priceFlag = true,
						
						venFlag = true;
		
				for(Map<String, Object> cardno : cardList){
					Map<String, Object> MapCard= new HashMap<String, Object>();
					
					MapCard.put("ass_card_no", cardno.get("ass_card_no"));
					MapCard.put("group_id", cardno.get("group_id"));
					MapCard.put("hos_id", cardno.get("hos_id"));
					MapCard.put("copy_code", cardno.get("copy_code"));
					Integer dept_house=assInMainHouseService.queryBydept(MapCard);
					Integer dept_r_house=assInMainHouseService.queryByRdept(MapCard);
					if (dept_house == 0 || dept_r_house==0) {
						return JSONObject.parseObject("{\"warn\":\"卡片没有维护使用科室,不能入库! \"}");
					}
				}
				
				for(Map<String, Object> card : cardList){
					if(verification(temp.get("ass_id"),card.get("ass_id"))){
						assFlag = false;
						break;
					}
					//if(verification(temp.get("price"),card.get("price"))){
						//priceFlag = false;
						//break;
					//}
					if(verification(temp.get("VEN_ID"),card.get("ven_id"))){
						venFlag = false;
						break;
					}
				}
				if(!assFlag){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if(!priceFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
				if(!venFlag){
					return JSONObject.parseObject("{\"warn\":\"存在供应商不匹配的资产,不能入库! \"}");
				}
			}
			
		}
		
		
		for (String data : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = data.split("@");

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ass_in_no", ids[3]);
			
			mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			
			mapVo.put("confirm_emp", SessionManager.getUserId());
			
			AssInMainHouse assInMainHouse = assInMainHouseService.queryByCode(mapVo);//主表
			if (assInMainHouse.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}
			
		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复入库! \"}");
		}
		
		try {
			assInMainJson = assInMainHouseService.updateConfirm(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assInMainJson);
	}
	
	private boolean verification(Object obj1,Object obj2){
		//true为不相等
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
			
			BigDecimal bd1 = new BigDecimal(obj1.toString());
			
			BigDecimal bd2 = new BigDecimal(obj2.toString());
			
			
			if(bd1.compareTo(bd2) != 0){
				return true;
			}else{
				return false;
			}
		}
		if(obj1 == null && obj2 == null){
			return false;
		}
		if(obj1 == null && obj2 != null){
			return true;
		}
		if(obj1 != null && obj2 == null){
			return true;
		}
		if(obj1 != null && obj2 != null){
			if(!obj1.equals(obj2)){
				return true;
			}else {
				return false;
			}
		}else{
			return false;
		}
	}	

  /**
	 * @Description 
	 * 导入跳转页面 资产入库主表(房屋及建筑)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/assInMainHouseImportPage", method = RequestMethod.GET)
	public String assInMainHouseImportPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/assin/assInMainHouseImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 资产入库主表(房屋及建筑)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/asshouse/assin/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","资产入库主表(房屋及建筑).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 资产入库主表(房屋及建筑)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/asshouse/assin/readAssInMainHouseFiles",method = RequestMethod.POST)  
    public String readAssInMainHouseFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssInMainHouse> list_err = new ArrayList<AssInMainHouse>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssInMainHouse assInMainHouse = new AssInMainHouse();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assInMainHouse.setAss_in_no(temp[3]);
		    		mapVo.put("ass_in_no", temp[3]);
					
					} else {
						
						err_sb.append("业务单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assInMainHouse.setBus_type_code(temp[4]);
		    		mapVo.put("bus_type_code", temp[4]);
					
					} else {
						
						err_sb.append("业务类型为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assInMainHouse.setVen_id(Long.valueOf(temp[5]));
		    		mapVo.put("ven_id", temp[5]);
					
					} else {
						
						err_sb.append("开发商ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assInMainHouse.setVen_no(Long.valueOf(temp[6]));
		    		mapVo.put("ven_no", temp[6]);
					
					} else {
						
						err_sb.append("开发商变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assInMainHouse.setIn_money(Double.valueOf(temp[7]));
		    		mapVo.put("in_money", temp[7]);
					
					} else {
						
						err_sb.append("入库金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assInMainHouse.setNote(temp[8]);
		    		mapVo.put("note", temp[8]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assInMainHouse.setCreate_emp(Long.valueOf(temp[9]));
		    		mapVo.put("create_emp", temp[9]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assInMainHouse.setCreate_date(DateUtil.stringToDate(temp[10]));
		    		mapVo.put("create_date", temp[10]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assInMainHouse.setIn_date(DateUtil.stringToDate(temp[11]));
		    		mapVo.put("in_date", temp[11]);
					
					} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assInMainHouse.setConfirm_emp(Long.valueOf(temp[12]));
		    		mapVo.put("confirm_emp", temp[12]);
					
					} else {
						
						err_sb.append("确认人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					assInMainHouse.setState(Integer.valueOf(temp[13]));
		    		mapVo.put("state", temp[13]);
					
					} else {
						
						err_sb.append("0:新建 1:审核 2:确认为空  ");
						
					}
					 
					
				AssInMainHouse data_exc_extis = assInMainHouseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assInMainHouse.setError_type(err_sb.toString());
					
					list_err.add(assInMainHouse);
					
				} else {
			  
					String dataJson = assInMainHouseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssInMainHouse data_exc = new AssInMainHouse();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 资产入库主表(房屋及建筑)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/asshouse/assin/addBatchAssInMainHouse", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssInMainHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssInMainHouse> list_err = new ArrayList<AssInMainHouse>();
		
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
			
			AssInMainHouse assInMainHouse = new AssInMainHouse();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("ass_in_no"))) {
						
					assInMainHouse.setAss_in_no((String)jsonObj.get("ass_in_no"));
		    		mapVo.put("ass_in_no", jsonObj.get("ass_in_no"));
		    		} else {
						
						err_sb.append("业务单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bus_type_code"))) {
						
					assInMainHouse.setBus_type_code((String)jsonObj.get("bus_type_code"));
		    		mapVo.put("bus_type_code", jsonObj.get("bus_type_code"));
		    		} else {
						
						err_sb.append("业务类型为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_id"))) {
						
					assInMainHouse.setVen_id(Long.valueOf((String)jsonObj.get("ven_id")));
		    		mapVo.put("ven_id", jsonObj.get("ven_id"));
		    		} else {
						
						err_sb.append("开发商ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ven_no"))) {
						
					assInMainHouse.setVen_no(Long.valueOf((String)jsonObj.get("ven_no")));
		    		mapVo.put("ven_no", jsonObj.get("ven_no"));
		    		} else {
						
						err_sb.append("开发商变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_money"))) {
						
					assInMainHouse.setIn_money(Double.valueOf((String)jsonObj.get("in_money")));
		    		mapVo.put("in_money", jsonObj.get("in_money"));
		    		} else {
						
						err_sb.append("入库金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assInMainHouse.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assInMainHouse.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assInMainHouse.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_date"))) {
						
					assInMainHouse.setIn_date(DateUtil.stringToDate((String)jsonObj.get("in_date")));
		    		mapVo.put("in_date", jsonObj.get("in_date"));
		    		} else {
						
						err_sb.append("入账日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {
						
					assInMainHouse.setConfirm_emp(Long.valueOf((String)jsonObj.get("confirm_emp")));
		    		mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
		    		} else {
						
						err_sb.append("确认人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assInMainHouse.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("0:新建 1:审核 2:确认为空  ");
						
					}
					
					
				AssInMainHouse data_exc_extis = assInMainHouseService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assInMainHouse.setError_type(err_sb.toString());
					
					list_err.add(assInMainHouse);
					
				} else {
			  
					String dataJson = assInMainHouseService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssInMainHouse data_exc = new AssInMainHouse();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }

	/**
* 状态查询
* @param mapVo
* @param mode
* @return
* @throws Exception
*/
@RequestMapping(value = "/hrp/ass/asshouse/assin/queryAssInHouseStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssInHouseStates(@RequestParam Map<String, Object> mapVo, Model mode)
throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assInMainHouseService.queryAssInHouseStates(mapVo);

if(list.size() == 0){

return JSONObject.parseObject("{\"state\":\"true\"}");

}else{

String  str = "" ;
for(String item : list){
	
	str += item +"," ;
}

return JSONObject.parseObject("{\"error\":\"入库单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


} 
}

