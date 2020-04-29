/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.check.special;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.check.special.AssChkInMainSpecial;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkInDetailSpecialService;
import com.chd.hrp.ass.service.check.special.AssChkInMainSpecialService;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(专用设备)
 * @Table:
 * ASS_CHK_IN_MAIN_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssChkInMainSpecialController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssChkInMainSpecialController.class);
	
	//引入Service服务
	@Resource(name = "assChkInMainSpecialService")
	private final AssChkInMainSpecialService assChkInMainSpecialService = null;
	
	//引入Service服务
	@Resource(name = "assChkInDetailSpecialService")
	private final AssChkInDetailSpecialService assChkInDetailSpecialService = null;
	
	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/assChkInSpecialMainPage", method = RequestMethod.GET)
	public String assChkInMainSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_0502001",MyConfig.getSysPara("0502001"));
		
		return "hrp/ass/assspecial/asscheck/checkin/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/assChkInSpecialAddPage", method = RequestMethod.GET)
	public String assChkInMainSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/checkin/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/assChkInSpecialSourcePage", method = RequestMethod.GET)
	public String assChkInSpecialSourcePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("price", mapVo.get("price"));
		mode.addAttribute("proj_id", mapVo.get("proj_id"));
		
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/asscheck/checkin/source";
	}

	/**
	 * @Description 
	 * 添加数据 050701 资产盘盈入库主表(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/saveAssChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkInMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("create_emp", SessionManager.getUserId());
		
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
		
		AssChkInMainSpecial assChkInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
		if(assChkInMainSpecial != null && assChkInMainSpecial.getState() > 0){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assChkInMainSpecialJson = assChkInMainSpecialService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assChkInMainSpecialJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 050701 资产盘盈入库主表(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/assChkInSpecialUpdatePage", method = RequestMethod.GET)
	public String assChkInMainSpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		AssChkInMainSpecial assChkInMainSpecial = new AssChkInMainSpecial();
    
		assChkInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
		mode.addAttribute("group_id", assChkInMainSpecial.getGroup_id());
		mode.addAttribute("hos_id", assChkInMainSpecial.getHos_id());
		mode.addAttribute("copy_code", assChkInMainSpecial.getCopy_code());
		mode.addAttribute("ass_in_no", assChkInMainSpecial.getAss_in_no());
		mode.addAttribute("store_id", assChkInMainSpecial.getStore_id());
		mode.addAttribute("store_no", assChkInMainSpecial.getStore_no());
		mode.addAttribute("store_code", assChkInMainSpecial.getStore_code());
		mode.addAttribute("store_name", assChkInMainSpecial.getStore_name());
		mode.addAttribute("dept_id", assChkInMainSpecial.getDept_id());
		mode.addAttribute("dept_no", assChkInMainSpecial.getDept_no());
		mode.addAttribute("dept_code", assChkInMainSpecial.getDept_code());
		mode.addAttribute("dept_name", assChkInMainSpecial.getDept_name());
		mode.addAttribute("ass_purpose", assChkInMainSpecial.getAss_purpose());
		mode.addAttribute("ass_purpose_name", assChkInMainSpecial.getAss_purpose_name());
		mode.addAttribute("proj_id", assChkInMainSpecial.getProj_id());
		mode.addAttribute("proj_no", assChkInMainSpecial.getProj_no());
		mode.addAttribute("proj_code", assChkInMainSpecial.getProj_code());
		mode.addAttribute("proj_name", assChkInMainSpecial.getProj_name());
		mode.addAttribute("in_money", assChkInMainSpecial.getIn_money());
		mode.addAttribute("note", assChkInMainSpecial.getNote());
		mode.addAttribute("create_emp", assChkInMainSpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkInMainSpecial.getCreate_emp_name());
		mode.addAttribute("create_date", assChkInMainSpecial.getCreate_date());
		mode.addAttribute("in_date", assChkInMainSpecial.getIn_date());
		mode.addAttribute("confirm_emp", assChkInMainSpecial.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assChkInMainSpecial.getConfirm_emp_name());
		mode.addAttribute("state", assChkInMainSpecial.getState());
		mode.addAttribute("state_name", assChkInMainSpecial.getState_name());
		
		mode.addAttribute("ass_04006",MyConfig.getSysPara("04006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_0502001",MyConfig.getSysPara("0502001"));
		
		return "hrp/ass/assspecial/asscheck/checkin/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050701 资产盘盈入库主表(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/updateAssChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssChkInMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assChkInMainSpecialJson = assChkInMainSpecialService.update(mapVo);
		
		return JSONObject.parseObject(assChkInMainSpecialJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 050701 资产盘盈入库主表(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/addOrUpdateAssChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssChkInMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assChkInMainSpecialJson ="";
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
		
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		assChkInMainSpecialJson = assChkInMainSpecialService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assChkInMainSpecialJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050701 资产盘盈入库主表(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/deleteAssChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkInMainSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_in_no", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assChkInMainSpecialJson = assChkInMainSpecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkInMainSpecialJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 050701 资产盘盈入库主表(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/queryAssChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInMainSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assChkInMainSpecial = assChkInMainSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assChkInMainSpecial);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050701 资产盘盈入库明细(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/queryAssChkInDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assChkInMainSpecial = assChkInDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assChkInMainSpecial);
		
	}
	
	/**
	 * @Description 删除数据 明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/deleteAssChkInDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkInDetailSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			mapVo.put("ass_id", ids[4]);
			mapVo.put("ass_no", ids[5]);
			mapVo.put("ass_spec", ids[6]);
			mapVo.put("ass_model", ids[7]);
			AssChkInMainSpecial assChkInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
			
			if(assChkInMainSpecial !=null && assChkInMainSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			listVo.add(mapVo);
		}
		
		String assChkInDetailSpecialJson = assChkInDetailSpecialService.deleteBatch(listVo);
		return JSONObject.parseObject(assChkInDetailSpecialJson);
	}
	
	/**
	 * 卡片信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/queryAssChkInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = assCardSpecialService.query(getPage(mapVo));
		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				jsonarray.remove(i);
			}
		}
		json.put("Rows", jsonarray);
		return JSONObject.parseObject(json.toString());
	}
	
	/**
	 * 生成卡片
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/initAssChkInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssChkInCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssChkInMainSpecial assChkInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
			
			if(assChkInMainSpecial != null && assChkInMainSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			assCard = assChkInMainSpecialService.initAssChkInCardSpecial(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/initAssChkInBatchCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssChkInBatchCardSpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssChkInMainSpecial assChkInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
			
			if(assChkInMainSpecial != null && assChkInMainSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			assCard = assChkInMainSpecialService.initAssChkInBatchCardSpecial(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	

	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/deleteAssChkInCardSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCard(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("ass_in_no", ids[4]);
			AssChkInMainSpecial assInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
			
			if(assInMainSpecial != null && assInMainSpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
			}

			AssCardSpecial assCardSpecial = assCardSpecialService.queryByCode(mapVo);
			if (assCardSpecial != null && assCardSpecial.getUse_state() > 0) {
				return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
			}

			listVo.add(mapVo);

		}
		
		String assCardGeneralJson = "";
		try {
			assCardGeneralJson = assCardSpecialService.deleteBatch(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	/**
	 * 确认入库操作
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/updateConfirmChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkInSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_in_no", ids[3]);
			mapVo.put("in_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("confirm_emp", SessionManager.getUserId());
			
			AssChkInMainSpecial assChkInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);
			if(assChkInMainSpecial == null || assChkInMainSpecial.getState() == 2){
				continue;
			}
			
			if(DateUtil.compareDate(assChkInMainSpecial.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			
			List<Map<String, Object>> list = assChkInDetailSpecialService.queryByInit(mapVo);//资产明细数据
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
				
				Integer use_state = assCardSpecialService.queryCardExistsByAssInNo(mapExists);//通过资产
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state != Integer.parseInt(temp.get("in_amount").toString())){
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardSpecialService.queryAssCardByAssInNo(mapExists);
				boolean assFlag = true,
						specFlag = true,
						modelFlag = true,
						brandFlag = true,
						facFlag = true,
						unitFlag = true,
						priceFlag = true,
						storeFlag = true,
						venFlag = true,
						//deptFlag = true,
						purposeFlag = true,
						projFlag = true;
				
				for(Map<String, Object> card : cardList){
					if(verification(temp.get("ass_id"),card.get("ass_id"))){
						assFlag = false;
						break;
					}
					if(verification(temp.get("ass_spec"),card.get("ass_spec"))){
						specFlag = false;
						break;
					}
					if(verification(temp.get("ass_model"),card.get("ass_mondl"))){
						modelFlag = false;
						break;
					}
					if(verification(temp.get("ass_brand"),card.get("ass_brand"))){
						brandFlag = false;
						break;
					}
					if(verification(temp.get("fac_id"),card.get("fac_id"))){
						facFlag = false;
						break;
					}
					if(verification(temp.get("unit_code"),card.get("unit_code"))){
						unitFlag = false;
						break;
					}
					//if(verification(temp.get("price"),card.get("price"))){
						//priceFlag = false;
						//break;
					//}
					if(verification(temp.get("store_id"),card.get("store_id"))){
						storeFlag = false;
						break;
					}
					if(verification(temp.get("ven_id"),card.get("ven_id"))){
						venFlag = false;
						break;
					}
					//if(verification(temp.get("dept_id"),card.get("dept_id"))){
						//deptFlag = false;
						//break;
					//}
					if(verification(temp.get("ass_purpose"),card.get("ass_purpose"))){
						purposeFlag = false;
						break;
					}
					if(verification(temp.get("proj_id"),card.get("proj_id"))){
						projFlag = false;
						break;
					}
				}
				if(!assFlag){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片或不匹配的资产,不能入库! \"}");
				}
				if(!specFlag){
					return JSONObject.parseObject("{\"warn\":\"存在规格不匹配的资产,不能入库! \"}");
				}
				if(!modelFlag){
					return JSONObject.parseObject("{\"warn\":\"存在型号不匹配的资产,不能入库! \"}");
				}
				if(!brandFlag){
					return JSONObject.parseObject("{\"warn\":\"存在品牌不匹配的资产,不能入库! \"}");
				}
				if(!facFlag){
					return JSONObject.parseObject("{\"warn\":\"存在生产厂商不匹配的资产,不能入库! \"}");
				}
				if(!unitFlag){
					return JSONObject.parseObject("{\"warn\":\"存在计量单位不匹配的资产,不能入库! \"}");
				}
				if(!priceFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
				if(!storeFlag){
					return JSONObject.parseObject("{\"warn\":\"存在仓库不匹配的资产,不能入库! \"}");
				}
				if(!venFlag){
					return JSONObject.parseObject("{\"warn\":\"存在供应商不匹配的资产,不能入库! \"}");
				}
				//if(!deptFlag){
					//return JSONObject.parseObject("{\"warn\":\"存在领用科室不匹配的资产,不能入库! \"}");
				//}
				if(!purposeFlag){
					return JSONObject.parseObject("{\"warn\":\"存在用途不匹配的资产,不能入库! \"}");
				}
				if(!projFlag){
					return JSONObject.parseObject("{\"warn\":\"存在项目不匹配的资产,不能入库! \"}");
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
			
			AssChkInMainSpecial assInMainSpecial = assChkInMainSpecialService.queryByCode(mapVo);//主表
			if (assInMainSpecial.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}
		
		try {
			String assChkInMainSpecialJson = assChkInMainSpecialService.updateConfirmChkInSpecial(listVo);
			return JSONObject.parseObject(assChkInMainSpecialJson);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}

		

	}
	
	private boolean verification(Object obj1,Object obj2){
		//true为不相等
		if(NumberUtil.isNumeric(String.valueOf(obj1)) || NumberUtil.isNumeric(String.valueOf(obj2))){
			Double number1 = obj1 == null || obj1.equals("") ? 0 : Double.parseDouble(String.valueOf(obj1));
			Double number2 = obj2 == null || obj2.equals("") ? 0 : Double.parseDouble(String.valueOf(obj2));
			if(Math.abs(number1 - number2) > 0){
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
	 * 导入跳转页面 050701 资产盘盈入库主表(专用设备)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/assChkInSpecialImportPage", method = RequestMethod.GET)
	public String assChkInMainSpecialImportPage(Model mode) throws Exception {

		return "hrp/ass/assspecial/asscheck/checkin/assChkInMainSpecialImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050701 资产盘盈入库主表(专用设备)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assspecial/check/checkin/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","050701 资产盘盈入库主表(专用设备).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050701 资产盘盈入库主表(专用设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assspecial/check/checkin/readAssChkInSpecialFiles",method = RequestMethod.POST)  
    public String readAssChkInMainSpecialFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssChkInMainSpecial> list_err = new ArrayList<AssChkInMainSpecial>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssChkInMainSpecial assChkInMainSpecial = new AssChkInMainSpecial();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assChkInMainSpecial.setAss_in_no(temp[3]);
		    		mapVo.put("ass_in_no", temp[3]);
					
					} else {
						
						err_sb.append("业务单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assChkInMainSpecial.setStore_id(Long.valueOf(temp[4]));
		    		mapVo.put("store_id", temp[4]);
					
					} else {
						
						err_sb.append("仓库编码ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assChkInMainSpecial.setStore_no(Long.valueOf(temp[5]));
		    		mapVo.put("store_no", temp[5]);
					
					} else {
						
						err_sb.append("仓库编码NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assChkInMainSpecial.setDept_id(Long.valueOf(temp[6]));
		    		mapVo.put("dept_id", temp[6]);
					
					} else {
						
						err_sb.append("领用科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assChkInMainSpecial.setDept_no(Long.valueOf(temp[7]));
		    		mapVo.put("dept_no", temp[7]);
					
					} else {
						
						err_sb.append("领用科室NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assChkInMainSpecial.setAss_purpose(temp[8]);
		    		mapVo.put("ass_purpose", temp[8]);
					
					} else {
						
						err_sb.append("用途为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assChkInMainSpecial.setProj_id(Long.valueOf(temp[9]));
		    		mapVo.put("proj_id", temp[9]);
					
					} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assChkInMainSpecial.setProj_no(Long.valueOf(temp[10]));
		    		mapVo.put("proj_no", temp[10]);
					
					} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assChkInMainSpecial.setIn_money(Double.valueOf(temp[11]));
		    		mapVo.put("in_money", temp[11]);
					
					} else {
						
						err_sb.append("入库金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assChkInMainSpecial.setNote(temp[12]);
		    		mapVo.put("note", temp[12]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					assChkInMainSpecial.setCreate_emp(Long.valueOf(temp[13]));
		    		mapVo.put("create_emp", temp[13]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					assChkInMainSpecial.setCreate_date(DateUtil.stringToDate(temp[14]));
		    		mapVo.put("create_date", temp[14]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					assChkInMainSpecial.setIn_date(DateUtil.stringToDate(temp[15]));
		    		mapVo.put("in_date", temp[15]);
					
					} else {
						
						err_sb.append("入库日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					assChkInMainSpecial.setConfirm_emp(Long.valueOf(temp[16]));
		    		mapVo.put("confirm_emp", temp[16]);
					
					} else {
						
						err_sb.append("确认人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					assChkInMainSpecial.setState(Integer.valueOf(temp[17]));
		    		mapVo.put("state", temp[17]);
					
					} else {
						
						err_sb.append("0:新建 1:审核 2:确认为空  ");
						
					}
					 
					
				AssChkInMainSpecial data_exc_extis = assChkInMainSpecialService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assChkInMainSpecial.setError_type(err_sb.toString());
					
					list_err.add(assChkInMainSpecial);
					
				} else {
			  
					String dataJson = assChkInMainSpecialService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssChkInMainSpecial data_exc = new AssChkInMainSpecial();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050701 资产盘盈入库主表(专用设备)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/addBatchAssChkInSpecial", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssChkInMainSpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssChkInMainSpecial> list_err = new ArrayList<AssChkInMainSpecial>();
		
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
			
			AssChkInMainSpecial assChkInMainSpecial = new AssChkInMainSpecial();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("ass_in_no"))) {
						
					assChkInMainSpecial.setAss_in_no((String)jsonObj.get("ass_in_no"));
		    		mapVo.put("ass_in_no", jsonObj.get("ass_in_no"));
		    		} else {
						
						err_sb.append("业务单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					assChkInMainSpecial.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库编码ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_no"))) {
						
					assChkInMainSpecial.setStore_no(Long.valueOf((String)jsonObj.get("store_no")));
		    		mapVo.put("store_no", jsonObj.get("store_no"));
		    		} else {
						
						err_sb.append("仓库编码NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					assChkInMainSpecial.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("领用科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					assChkInMainSpecial.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("领用科室NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_purpose"))) {
						
					assChkInMainSpecial.setAss_purpose((String)jsonObj.get("ass_purpose"));
		    		mapVo.put("ass_purpose", jsonObj.get("ass_purpose"));
		    		} else {
						
						err_sb.append("用途为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {
						
					assChkInMainSpecial.setProj_id(Long.valueOf((String)jsonObj.get("proj_id")));
		    		mapVo.put("proj_id", jsonObj.get("proj_id"));
		    		} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_no"))) {
						
					assChkInMainSpecial.setProj_no(Long.valueOf((String)jsonObj.get("proj_no")));
		    		mapVo.put("proj_no", jsonObj.get("proj_no"));
		    		} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_money"))) {
						
					assChkInMainSpecial.setIn_money(Double.valueOf((String)jsonObj.get("in_money")));
		    		mapVo.put("in_money", jsonObj.get("in_money"));
		    		} else {
						
						err_sb.append("入库金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assChkInMainSpecial.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assChkInMainSpecial.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assChkInMainSpecial.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_date"))) {
						
					assChkInMainSpecial.setIn_date(DateUtil.stringToDate((String)jsonObj.get("in_date")));
		    		mapVo.put("in_date", jsonObj.get("in_date"));
		    		} else {
						
						err_sb.append("入库日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {
						
					assChkInMainSpecial.setConfirm_emp(Long.valueOf((String)jsonObj.get("confirm_emp")));
		    		mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
		    		} else {
						
						err_sb.append("确认人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assChkInMainSpecial.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("0:新建 1:审核 2:确认为空  ");
						
					}
					
					
				AssChkInMainSpecial data_exc_extis = assChkInMainSpecialService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assChkInMainSpecial.setError_type(err_sb.toString());
					
					list_err.add(assChkInMainSpecial);
					
				} else {
			  
					String dataJson = assChkInMainSpecialService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssChkInMainSpecial data_exc = new AssChkInMainSpecial();
			
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
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/check/checkin/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assChkInMainSpecialService.queryState(mapVo);
		
		if(list.size()>0){
			for (String tran_no : list) {
				str += tran_no +" ";
			}
			return JSONObject.parseObject("{\"error\":\"单号为"+str+"的数据是未确认,无法打印!\",\"state\":\"true\"}");
			
		}else{
			return JSONObject.parseObject("{\"state\":\"true\"}");
		}
		
	}
	
}

