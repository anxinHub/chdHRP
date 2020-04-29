/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.check.land;
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
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.check.land.AssChkInMainLand;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.check.land.AssChkInDetailLandService;
import com.chd.hrp.ass.service.check.land.AssChkInMainLandService;
/**
 * 
 * @Description:
 * 050701 资产盘盈入库主表(土地)
 * @Table:
 * ASS_CHK_IN_MAIN_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssChkInMainLandController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssChkInMainLandController.class);
	
	//引入Service服务
	@Resource(name = "assChkInMainLandService")
	private final AssChkInMainLandService assChkInMainLandService = null;
	
	//引入Service服务
	@Resource(name = "assChkInDetailLandService")
	private final AssChkInDetailLandService assChkInDetailLandService = null;
	
	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/assChkInLandMainPage", method = RequestMethod.GET)
	public String assChkInMainLandMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05070",MyConfig.getSysPara("05070"));
		
		return "hrp/ass/assland/asscheck/checkin/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/assChkInLandAddPage", method = RequestMethod.GET)
	public String assChkInMainLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/checkin/add";

	}

	/**
	 * @Description 
	 * 添加数据 050701 资产盘盈入库主表(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/saveAssChkInLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkInMainLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
		AssChkInMainLand assChkInMainLand = assChkInMainLandService.queryByCode(mapVo);
		if(assChkInMainLand != null && assChkInMainLand.getState() > 0){
			return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
		}
		
		String assChkInMainLandJson = assChkInMainLandService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assChkInMainLandJson);
		
	}
	/**
	 * @Description 
	 * 更新跳转页面 050701 资产盘盈入库主表(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/assChkInLandUpdatePage", method = RequestMethod.GET)
	public String assChkInMainLandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		AssChkInMainLand assChkInMainLand = new AssChkInMainLand();
    
		assChkInMainLand = assChkInMainLandService.queryByCode(mapVo);
		mode.addAttribute("group_id", assChkInMainLand.getGroup_id());
		mode.addAttribute("hos_id", assChkInMainLand.getHos_id());
		mode.addAttribute("copy_code", assChkInMainLand.getCopy_code());
		mode.addAttribute("ass_in_no", assChkInMainLand.getAss_in_no());
		mode.addAttribute("store_id", assChkInMainLand.getStore_id());
		mode.addAttribute("store_no", assChkInMainLand.getStore_no());
		mode.addAttribute("store_code", assChkInMainLand.getStore_code());
		mode.addAttribute("store_name", assChkInMainLand.getStore_name());
		mode.addAttribute("dept_id", assChkInMainLand.getDept_id());
		mode.addAttribute("dept_no", assChkInMainLand.getDept_no());
		mode.addAttribute("dept_code", assChkInMainLand.getDept_code());
		mode.addAttribute("dept_name", assChkInMainLand.getDept_name());
		mode.addAttribute("ass_purpose", assChkInMainLand.getAss_purpose());
		mode.addAttribute("ass_purpose_name", assChkInMainLand.getAss_purpose_name());
		mode.addAttribute("proj_id", assChkInMainLand.getProj_id());
		mode.addAttribute("proj_no", assChkInMainLand.getProj_no());
		mode.addAttribute("proj_code", assChkInMainLand.getProj_code());
		mode.addAttribute("proj_name", assChkInMainLand.getProj_name());
		mode.addAttribute("in_money", assChkInMainLand.getIn_money());
		mode.addAttribute("note", assChkInMainLand.getNote());
		mode.addAttribute("create_emp", assChkInMainLand.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkInMainLand.getCreate_emp_name());
		mode.addAttribute("create_date", assChkInMainLand.getCreate_date());
		mode.addAttribute("in_date", assChkInMainLand.getIn_date());
		mode.addAttribute("confirm_emp", assChkInMainLand.getConfirm_emp());
		mode.addAttribute("confirm_emp_name", assChkInMainLand.getConfirm_emp_name());
		mode.addAttribute("state", assChkInMainLand.getState());
		mode.addAttribute("state_name", assChkInMainLand.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05071",MyConfig.getSysPara("05071"));
		
		return "hrp/ass/assland/asscheck/checkin/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050701 资产盘盈入库主表(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/updateAssChkInLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssChkInMainLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assChkInMainLandJson = assChkInMainLandService.update(mapVo);
		
		return JSONObject.parseObject(assChkInMainLandJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 050701 资产盘盈入库主表(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/addOrUpdateAssChkInLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssChkInMainLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assChkInMainLandJson ="";
		

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
	  
		assChkInMainLandJson = assChkInMainLandService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assChkInMainLandJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050701 资产盘盈入库主表(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/deleteAssChkInLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkInMainLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
	    
		String assChkInMainLandJson = assChkInMainLandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkInMainLandJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 050701 资产盘盈入库主表(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/queryAssChkInLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInMainLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assChkInMainLand = assChkInMainLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assChkInMainLand);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050701 资产盘盈入库明细(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/queryAssChkInDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInDetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String assChkInMainLand = assChkInDetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assChkInMainLand);
		
	}
	
	/**
	 * @Description 删除数据 明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/deleteAssChkInDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkInDetailLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			AssChkInMainLand assChkInMainLand = assChkInMainLandService.queryByCode(mapVo);
			
			if(assChkInMainLand !=null && assChkInMainLand.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
			}
			
			listVo.add(mapVo);
		}
		
		String assChkInDetailLandJson = assChkInDetailLandService.deleteBatch(listVo);
		return JSONObject.parseObject(assChkInDetailLandJson);
	}
	
	/**
	 * 卡片信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/queryAssChkInCardLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkInCardLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = assCardLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assCard);
	}
	
	/**
	 * 生成卡片
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/initAssChkInCardLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssChkInCardLand(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assCard = "";
		try {
			AssChkInMainLand assChkInMainLand = assChkInMainLandService.queryByCode(mapVo);
			
			if(assChkInMainLand != null && assChkInMainLand.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
			}
			
			assCard = assChkInMainLandService.initAssChkInCardLand(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}

	@RequestMapping(value = "/hrp/ass/assland/check/checkin/deleteAssChkInCardLand", method = RequestMethod.POST)
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
			AssChkInMainLand assInMainLand = assChkInMainLandService.queryByCode(mapVo);
			
			if(assInMainLand != null && assInMainLand.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能删除! \"}");
			}

			AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
			if (assCardLand != null && assCardLand.getUse_state() > 0) {
				return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
			}

			listVo.add(mapVo);

		}
		
		String assCardGeneralJson = "";
		try {
			assCardGeneralJson = assCardLandService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/updateConfirmChkInLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkInLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkInMainLand assChkInMainLand = assChkInMainLandService.queryByCode(mapVo);
			if(DateUtil.compareDate(assChkInMainLand.getCreate_date(), new Date())){
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			if(assChkInMainLand == null || assChkInMainLand.getState() == 2){
				continue;
			}
			
			List<Map<String, Object>> list = assChkInDetailLandService.queryByInit(mapVo);//资产明细数据
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
				
				Integer use_state = assCardLandService.queryCardExistsByAssInNo(mapExists);//通过资产
				
				if(use_state == 0){
					return JSONObject.parseObject("{\"warn\":\"存在没有生成卡片的资产,不能入库! \"}");
				}
				
				if(use_state != Integer.parseInt(temp.get("in_amount").toString())){
					return JSONObject.parseObject("{\"warn\":\"卡片和资产数量不匹配,不能入库! \"}");
				}
				
				List<Map<String, Object>> cardList = assCardLandService.queryAssCardByAssInNo(mapExists);
				for(Map<String, Object> cardno : cardList){
					Map<String, Object> MapCard= new HashMap<String, Object>();
					
					MapCard.put("ass_card_no", cardno.get("ass_card_no"));
					MapCard.put("group_id", cardno.get("group_id"));
					MapCard.put("hos_id", cardno.get("hos_id"));
					MapCard.put("copy_code", cardno.get("copy_code"));
					Integer dept_house=assChkInMainLandService.queryBydept(MapCard);
					Integer dept_r_house=assChkInMainLandService.queryByRdept(MapCard);
					if (dept_house == 0 || dept_r_house==0) {
						return JSONObject.parseObject("{\"warn\":\"卡片没有维护使用科室,不能入库! \"}");
					}
				}
				boolean assFlag = true,
						priceFlag = true,
						purposeFlag = true,
						projFlag = true;
				
				for(Map<String, Object> card : cardList){
					if(verification(temp.get("ass_id"),card.get("ass_id"))){
						assFlag = false;
						break;
					}
					//if(verification(temp.get("price"),card.get("price"))){
						//priceFlag = false;
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
				if(!priceFlag){
					return JSONObject.parseObject("{\"warn\":\"存在资产单价和卡片原值不匹配的资产,不能入库! \"}");
				}
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
			
			AssChkInMainLand assInMainLand = assChkInMainLandService.queryByCode(mapVo);//主表
			if (assInMainLand.getState() == 2) {
				continue;
			}else{
				listVo.add(mapVo);
			}

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}
		
		try {
			String assChkInMainLandJson = assChkInMainLandService.updateConfirmChkInLand(listVo);
			return JSONObject.parseObject(assChkInMainLandJson);
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
	 * 导入跳转页面 050701 资产盘盈入库主表(土地)
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/assChkInLandImportPage", method = RequestMethod.GET)
	public String assChkInMainLandImportPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/checkin/assChkInMainLandImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 050701 资产盘盈入库主表(土地)
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/ass/assland/check/checkin/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"sys\\downTemplate","050701 资产盘盈入库主表(土地).xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 050701 资产盘盈入库主表(土地)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/ass/assland/check/checkin/readAssChkInLandFiles",method = RequestMethod.POST)  
    public String readAssChkInMainLandFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<AssChkInMainLand> list_err = new ArrayList<AssChkInMainLand>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				AssChkInMainLand assChkInMainLand = new AssChkInMainLand();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					assChkInMainLand.setAss_in_no(temp[3]);
		    		mapVo.put("ass_in_no", temp[3]);
					
					} else {
						
						err_sb.append("业务单号为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					assChkInMainLand.setStore_id(Long.valueOf(temp[4]));
		    		mapVo.put("store_id", temp[4]);
					
					} else {
						
						err_sb.append("仓库编码ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					assChkInMainLand.setStore_no(Long.valueOf(temp[5]));
		    		mapVo.put("store_no", temp[5]);
					
					} else {
						
						err_sb.append("仓库编码NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					assChkInMainLand.setDept_id(Long.valueOf(temp[6]));
		    		mapVo.put("dept_id", temp[6]);
					
					} else {
						
						err_sb.append("领用科室ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					assChkInMainLand.setDept_no(Long.valueOf(temp[7]));
		    		mapVo.put("dept_no", temp[7]);
					
					} else {
						
						err_sb.append("领用科室NO为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					assChkInMainLand.setAss_purpose(temp[8]);
		    		mapVo.put("ass_purpose", temp[8]);
					
					} else {
						
						err_sb.append("用途为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					assChkInMainLand.setProj_id(Long.valueOf(temp[9]));
		    		mapVo.put("proj_id", temp[9]);
					
					} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[10])) {
						
					assChkInMainLand.setProj_no(Long.valueOf(temp[10]));
		    		mapVo.put("proj_no", temp[10]);
					
					} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[11])) {
						
					assChkInMainLand.setIn_money(Double.valueOf(temp[11]));
		    		mapVo.put("in_money", temp[11]);
					
					} else {
						
						err_sb.append("入库金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[12])) {
						
					assChkInMainLand.setNote(temp[12]);
		    		mapVo.put("note", temp[12]);
					
					} else {
						
						err_sb.append("备注为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[13])) {
						
					assChkInMainLand.setCreate_emp(Long.valueOf(temp[13]));
		    		mapVo.put("create_emp", temp[13]);
					
					} else {
						
						err_sb.append("制单人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[14])) {
						
					assChkInMainLand.setCreate_date(DateUtil.stringToDate(temp[14]));
		    		mapVo.put("create_date", temp[14]);
					
					} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[15])) {
						
					assChkInMainLand.setIn_date(DateUtil.stringToDate(temp[15]));
		    		mapVo.put("in_date", temp[15]);
					
					} else {
						
						err_sb.append("入库日期为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[16])) {
						
					assChkInMainLand.setConfirm_emp(Long.valueOf(temp[16]));
		    		mapVo.put("confirm_emp", temp[16]);
					
					} else {
						
						err_sb.append("确认人为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[17])) {
						
					assChkInMainLand.setState(Integer.valueOf(temp[17]));
		    		mapVo.put("state", temp[17]);
					
					} else {
						
						err_sb.append("0:新建 1:审核 2:确认为空  ");
						
					}
					 
					
				AssChkInMainLand data_exc_extis = assChkInMainLandService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assChkInMainLand.setError_type(err_sb.toString());
					
					list_err.add(assChkInMainLand);
					
				} else {
			  
					String dataJson = assChkInMainLandService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssChkInMainLand data_exc = new AssChkInMainLand();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 050701 资产盘盈入库主表(土地)
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/ass/assland/check/checkin/addBatchAssChkInLand", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchAssChkInMainLand(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<AssChkInMainLand> list_err = new ArrayList<AssChkInMainLand>();
		
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
			
			AssChkInMainLand assChkInMainLand = new AssChkInMainLand();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("ass_in_no"))) {
						
					assChkInMainLand.setAss_in_no((String)jsonObj.get("ass_in_no"));
		    		mapVo.put("ass_in_no", jsonObj.get("ass_in_no"));
		    		} else {
						
						err_sb.append("业务单号为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_id"))) {
						
					assChkInMainLand.setStore_id(Long.valueOf((String)jsonObj.get("store_id")));
		    		mapVo.put("store_id", jsonObj.get("store_id"));
		    		} else {
						
						err_sb.append("仓库编码ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("store_no"))) {
						
					assChkInMainLand.setStore_no(Long.valueOf((String)jsonObj.get("store_no")));
		    		mapVo.put("store_no", jsonObj.get("store_no"));
		    		} else {
						
						err_sb.append("仓库编码NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {
						
					assChkInMainLand.setDept_id(Long.valueOf((String)jsonObj.get("dept_id")));
		    		mapVo.put("dept_id", jsonObj.get("dept_id"));
		    		} else {
						
						err_sb.append("领用科室ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("dept_no"))) {
						
					assChkInMainLand.setDept_no(Long.valueOf((String)jsonObj.get("dept_no")));
		    		mapVo.put("dept_no", jsonObj.get("dept_no"));
		    		} else {
						
						err_sb.append("领用科室NO为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("ass_purpose"))) {
						
					assChkInMainLand.setAss_purpose((String)jsonObj.get("ass_purpose"));
		    		mapVo.put("ass_purpose", jsonObj.get("ass_purpose"));
		    		} else {
						
						err_sb.append("用途为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_id"))) {
						
					assChkInMainLand.setProj_id(Long.valueOf((String)jsonObj.get("proj_id")));
		    		mapVo.put("proj_id", jsonObj.get("proj_id"));
		    		} else {
						
						err_sb.append("项目ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("proj_no"))) {
						
					assChkInMainLand.setProj_no(Long.valueOf((String)jsonObj.get("proj_no")));
		    		mapVo.put("proj_no", jsonObj.get("proj_no"));
		    		} else {
						
						err_sb.append("项目变更ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_money"))) {
						
					assChkInMainLand.setIn_money(Double.valueOf((String)jsonObj.get("in_money")));
		    		mapVo.put("in_money", jsonObj.get("in_money"));
		    		} else {
						
						err_sb.append("入库金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("note"))) {
						
					assChkInMainLand.setNote((String)jsonObj.get("note"));
		    		mapVo.put("note", jsonObj.get("note"));
		    		} else {
						
						err_sb.append("备注为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_emp"))) {
						
					assChkInMainLand.setCreate_emp(Long.valueOf((String)jsonObj.get("create_emp")));
		    		mapVo.put("create_emp", jsonObj.get("create_emp"));
		    		} else {
						
						err_sb.append("制单人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("create_date"))) {
						
					assChkInMainLand.setCreate_date(DateUtil.stringToDate((String)jsonObj.get("create_date")));
		    		mapVo.put("create_date", jsonObj.get("create_date"));
		    		} else {
						
						err_sb.append("制单日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_date"))) {
						
					assChkInMainLand.setIn_date(DateUtil.stringToDate((String)jsonObj.get("in_date")));
		    		mapVo.put("in_date", jsonObj.get("in_date"));
		    		} else {
						
						err_sb.append("入库日期为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("confirm_emp"))) {
						
					assChkInMainLand.setConfirm_emp(Long.valueOf((String)jsonObj.get("confirm_emp")));
		    		mapVo.put("confirm_emp", jsonObj.get("confirm_emp"));
		    		} else {
						
						err_sb.append("确认人为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("state"))) {
						
					assChkInMainLand.setState(Integer.valueOf((String)jsonObj.get("state")));
		    		mapVo.put("state", jsonObj.get("state"));
		    		} else {
						
						err_sb.append("0:新建 1:审核 2:确认为空  ");
						
					}
					
					
				AssChkInMainLand data_exc_extis = assChkInMainLandService.queryByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					assChkInMainLand.setError_type(err_sb.toString());
					
					list_err.add(assChkInMainLand);
					
				} else {
			  
					String dataJson = assChkInMainLandService.add(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			AssChkInMainLand data_exc = new AssChkInMainLand();
			
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
@RequestMapping(value = "/hrp/ass/assland/check/checkin/queryAssChkInLandStates", method = RequestMethod.POST)
@ResponseBody
public Map<String, Object> queryAssChkInLandStates(@RequestParam Map<String, Object> mapVo, Model mode)
	throws Exception {

mapVo.put("group_id", SessionManager.getGroupId());

mapVo.put("hos_id", SessionManager.getHosId());

mapVo.put("copy_code", SessionManager.getCopyCode());

//入库单状态查询  （打印时校验数据专用）
List<String> list = assChkInMainLandService.queryAssChkInLandStates(mapVo);

if(list.size() == 0){
	
	return JSONObject.parseObject("{\"state\":\"true\"}");
	
}else{
	
	String  str = "" ;
	for(String item : list){
		
		str += item +"," ;
	}
	
	return JSONObject.parseObject("{\"error\":\"盘点单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
}


}
}

