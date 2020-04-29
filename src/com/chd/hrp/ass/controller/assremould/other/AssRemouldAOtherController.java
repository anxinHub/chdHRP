/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould.other;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.chd.hrp.ass.entity.assremould.other.AssRemouldAdetailOther;
import com.chd.hrp.ass.service.assremould.other.AssRemouldADetailOtherService;
import com.chd.hrp.ass.service.assremould.other.AssRemouldAOtherService;
/**
 * 
 * @Description:改造申报其他固定资产
 * tabledesc
 * @Table:
 * ASS_REMOULD_A_Other
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldAOtherController extends BaseController{
	
	
	//引入Service服务
	@Resource(name = "assRemouldAOtherService")
	private final AssRemouldAOtherService assRemouldAOtherService = null;
	//引入Service服务
	@Resource(name = "assRemouldADetailOtherService")
	private final AssRemouldADetailOtherService assRemouldADetailOtherService= null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/assRemouldAOtherMainPage", method = RequestMethod.GET)
	public String assRemouldAOtherMainPage(Model mode) throws Exception {

		return "hrp/ass/assother/assremouldaother/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/assRemouldAOtherAddPage", method = RequestMethod.GET)
	public String assRemouldAOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldaother/add";

	}

	/**
	 * @Description 
	 * 添加数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/saveAssRemouldAOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldAOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
       
		String assRemouldAOtherJson = assRemouldAOtherService.add(mapVo);

		return JSONObject.parseObject(assRemouldAOtherJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/assRemouldAOtherUpdatePage", method = RequestMethod.GET)
	public String assRemouldAOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
    
		Map<String,Object>  assRemouldAOtherMap = assRemouldAOtherService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldAOtherMap.get("group_id"));
		mode.addAttribute("hos_id", assRemouldAOtherMap.get("hos_id"));
		mode.addAttribute("copy_code", assRemouldAOtherMap.get("copy_code"));
		mode.addAttribute("apply_no", assRemouldAOtherMap.get("apply_no"));
		mode.addAttribute("bus_type_code", assRemouldAOtherMap.get("bus_type_code"));
		mode.addAttribute("bus_type_name", assRemouldAOtherMap.get("bus_type_name"));
		mode.addAttribute("ven_id", assRemouldAOtherMap.get("ven_id"));
		mode.addAttribute("ven_no", assRemouldAOtherMap.get("ven_no"));
		mode.addAttribute("sup_code", assRemouldAOtherMap.get("sup_code"));
		mode.addAttribute("sup_name", assRemouldAOtherMap.get("sup_name"));
		mode.addAttribute("create_emp", assRemouldAOtherMap.get("create_emp"));
		mode.addAttribute("create_date", assRemouldAOtherMap.get("create_date"));
		mode.addAttribute("create_emp_name", assRemouldAOtherMap.get("create_emp_name"));
		mode.addAttribute("audit_emp_name", assRemouldAOtherMap.get("audit_emp_name"));
		mode.addAttribute("audit_emp", assRemouldAOtherMap.get("audit_emp"));
		mode.addAttribute("change_date", assRemouldAOtherMap.get("change_date"));
		mode.addAttribute("state", assRemouldAOtherMap.get("state"));
		mode.addAttribute("note", assRemouldAOtherMap.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assremouldaother/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/updateAssRemouldAOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldAOtherJson = assRemouldAOtherService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldAOtherJson);
	}
	/**
	 * @Description 
	 * 申报
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/updateAssRemouldAOtherConfirmState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldAOtherConfirmState(@RequestParam(value = "ParamVo") String paramVo,
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
			Map<String,Object> assRemouldAGeneral = assRemouldAOtherService.queryByCode(mapVo);
			if (assRemouldAGeneral == null || assRemouldAGeneral.get("state") .toString().equals("2")) {
				continue;
			}
			DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
			Date date=fmt.parse(assRemouldAGeneral.get("create_date").toString());
			if (DateUtil.compareDate(date, new Date())) {
				return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
			}
			// 查询明细表
			List<AssRemouldAdetailOther> detailList = assRemouldADetailOtherService.queryByDisANo(mapVo);
			// 明细表有数据进行卡片判断
			if (detailList != null && detailList.size() > 0) {
				for (AssRemouldAdetailOther detail : detailList) {
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

		String assRemouldAGeneralJson = assRemouldAOtherService.updateAssRemouldAOtherConfirmState(listVo,
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
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/addOrUpdateAssRemouldAOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssRemouldAOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldAOtherJson ="";
		

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
	  
		assRemouldAOtherJson = assRemouldAOtherService.addOrUpdate(detailVo);
		
		}
		return JSONObject.parseObject(assRemouldAOtherJson);
	}
	
	/**
	 * @Description 删除数据从表数据 tabledesc
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/deleteAssRemouldDetailAOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldDetailAOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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

			Map<String,Object>  assRemouldAHouseMap = assRemouldAOtherService.queryByCode(mapVo);
			if (assRemouldAHouseMap != null ) {
				if (!assRemouldAHouseMap.get("state") .toString().equals("0")) {
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
				
			}
			listVo.add(mapVo);

		}
		

		String assRemouldAspecialJson = assRemouldADetailOtherService.deleteBatch(listVo);

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
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/deleteAssRemouldAOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldAOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("apply_no", ids[3]);
				Map<String,Object>  assRemouldAHouseMap = assRemouldAOtherService.queryByCode(mapVo);
				if (assRemouldAHouseMap != null ) {
					if (!assRemouldAHouseMap.get("state") .toString().equals("0")) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
	      listVo.add(mapVo);
	      
	    }
	    
		String assRemouldAOtherJson = assRemouldAOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldAOtherJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/queryAssRemouldAOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldAOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldAOther = assRemouldAOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldAOther);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldaOther/queryAssRemouldADict", method = RequestMethod.POST)
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
		String assRemouldAOther = assRemouldAOtherService.queryAssRemouldADict(mapVo);

		return JSONObject.parseObject(assRemouldAOther);
		
	}
	
}

