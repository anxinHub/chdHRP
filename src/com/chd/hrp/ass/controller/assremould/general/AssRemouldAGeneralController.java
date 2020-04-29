/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould.general;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldAdetailGeneral;
import com.chd.hrp.ass.service.assremould.general.AssRemouldADetailGeneralService;
import com.chd.hrp.ass.service.assremould.general.AssRemouldAGeneralService;
/**
 * 
 * @Description:改造申报一般设备
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldAGeneralController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldAGeneralController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldAGeneralService")
	private final AssRemouldAGeneralService assRemouldAGeneralService = null;
	//引入Service服务
		@Resource(name = "assRemouldADetailGeneralService")
		private final AssRemouldADetailGeneralService assRemouldADetailGeneralService= null;
		
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/assRemouldAGeneralMainPage", method = RequestMethod.GET)
	public String assRemouldAGeneralMainPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/assremouldageneral/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/assRemouldAGeneralAddPage", method = RequestMethod.GET)
	public String assRemouldAGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assremouldageneral/add";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/saveAssRemouldAGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldAGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String assRemouldAGeneralJson = assRemouldAGeneralService.add(mapVo);

		return JSONObject.parseObject(assRemouldAGeneralJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/assRemouldAGeneralUpdatePage", method = RequestMethod.GET)
	public String assRemouldAGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    
		Map<String,Object> assRemouldAGeneralMap = assRemouldAGeneralService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldAGeneralMap.get("group_id"));
		mode.addAttribute("hos_id", assRemouldAGeneralMap.get("hos_id"));
		mode.addAttribute("copy_code", assRemouldAGeneralMap.get("copy_code"));
		mode.addAttribute("apply_no", assRemouldAGeneralMap.get("apply_no"));
		mode.addAttribute("bus_type_code", assRemouldAGeneralMap.get("bus_type_code"));
		mode.addAttribute("bus_type_name", assRemouldAGeneralMap.get("bus_type_name"));
		mode.addAttribute("ven_id", assRemouldAGeneralMap.get("ven_id"));
		mode.addAttribute("ven_no", assRemouldAGeneralMap.get("ven_no"));
		mode.addAttribute("sup_code", assRemouldAGeneralMap.get("sup_code"));
		mode.addAttribute("sup_name", assRemouldAGeneralMap.get("sup_name"));
		mode.addAttribute("create_emp", assRemouldAGeneralMap.get("create_emp"));
		mode.addAttribute("create_date", assRemouldAGeneralMap.get("create_date"));
		mode.addAttribute("create_emp_name", assRemouldAGeneralMap.get("create_emp_name"));
		mode.addAttribute("audit_emp_name", assRemouldAGeneralMap.get("audit_emp_name"));
		mode.addAttribute("audit_emp", assRemouldAGeneralMap.get("audit_emp"));
		mode.addAttribute("change_date", assRemouldAGeneralMap.get("change_date"));
		mode.addAttribute("state", assRemouldAGeneralMap.get("state"));
		mode.addAttribute("note", assRemouldAGeneralMap.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assremouldageneral/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/updateAssRemouldAGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldAGeneralJson = assRemouldAGeneralService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldAGeneralJson);
	}
	/**
	 * @Description 确认 tabledesc
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/updateAssRemouldAGeneralConfirmState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAGeneralConfirmState(@RequestParam(value = "ParamVo") String paramVo,
			Model mode) throws Exception {

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
			mapVo.put("apply_no", ids[3]);
			mapVo.put("change_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			// mapVo.put("apply_no", ids[4]);

			// 查询主表数据是否存在
			Map<String,Object> assRemouldAGeneral = assRemouldAGeneralService.queryByCode(mapVo);
			if (assRemouldAGeneral == null || assRemouldAGeneral.get("state") .toString().equals("2")) {
				continue;
			}
			DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
			Date date=fmt.parse(assRemouldAGeneral.get("create_date").toString());
			if (DateUtil.compareDate(date, new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			// 查询明细表
			List<AssRemouldAdetailGeneral> detailList = assRemouldADetailGeneralService.queryByDisANo(mapVo);
			// 明细表有数据进行卡片判断
			if (detailList != null && detailList.size() > 0) {
				for (AssRemouldAdetailGeneral detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("bus_type_code", assRemouldAGeneral.get("bus_type_code"));
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if (map.containsKey(detail.getAss_card_no())) {
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			} else {
				// 明细表没有数据返回错误信息
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		// 判断主表和明细是否有数据
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assRemouldAGeneralJson = assRemouldAGeneralService.updateAssRemouldAGeneralConfirmState(listVo,
				listCardVo);

		return JSONObject.parseObject(assRemouldAGeneralJson);

	}
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/addOrUpdateAssRemouldAGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldAGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldAGeneralJson ="";
		

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
	  
		assRemouldAGeneralJson = assRemouldAGeneralService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assRemouldAGeneralJson);
	}
	
	/**
	 * @Description 删除数据从表数据 tabledesc
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/deleteAssRemouldDetailAGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldDetailAGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("apply_no", ids[3]);
			mapVo.put("ass_card_no", ids[4]);

			Map<String,Object>  assRemouldAHouseMap = assRemouldAGeneralService.queryByCode(mapVo);
			if (assRemouldAHouseMap != null ) {
				if (!assRemouldAHouseMap.get("state") .toString().equals("0")) {
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
				
			}
			listVo.add(mapVo);

		}
		

		String assRemouldAspecialJson = assRemouldADetailGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assRemouldAspecialJson);

	}
	
	
	/**
	 * @Description 
	 * 删除数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/deleteAssRemouldAGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldAGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("apply_no", ids[3]);

				Map<String,Object>  assRemouldAHouseMap = assRemouldAGeneralService.queryByCode(mapVo);
				if (assRemouldAHouseMap != null ) {
					if (!assRemouldAHouseMap.get("state") .toString().equals("0")) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
	      listVo.add(mapVo);
	      
	  
			}
		String assRemouldAGeneralJson = assRemouldAGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldAGeneralJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/queryAssRemouldAGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldAGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldAGeneral = assRemouldAGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAGeneral);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldageneral/queryAssRemouldADict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldADict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldAGeneral = assRemouldAGeneralService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAGeneral);
		
	}
	
	
	
}

