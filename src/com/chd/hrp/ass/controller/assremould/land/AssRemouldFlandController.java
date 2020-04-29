/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould.land;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.assremould.land.AssRemouldFdetailLand;
import com.chd.hrp.ass.entity.assremould.land.AssRemouldFland;
import com.chd.hrp.ass.service.assremould.land.AssRemouldFDetailLandService;
import com.chd.hrp.ass.service.assremould.land.AssRemouldFlandService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(土地)
 * @Table:
 * ASS_REMOULD_F_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldFlandController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldFlandController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldFlandService")
	private final AssRemouldFlandService assRemouldFlandService = null;
	@Resource(name="assRemouldFDetailLandService")
	   private final AssRemouldFDetailLandService   assRemouldFDetailLandService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/assRemouldFlandMainPage", method = RequestMethod.GET)
	public String assRemouldFlandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/assremould/assremouldfland/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/assRemouldFlandAddPage", method = RequestMethod.GET)
	public String assRemouldFlandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assremould/assremouldfland/add";

	}

	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/addAssRemouldFland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldFland(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String yearmonth = mapVo.get("create_date").toString().substring(0, 4) + mapVo.get("create_date").toString().substring(5, 7);
 
		
		String curYearMonth = MyConfig.getCurAccYearMonth();
		if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
			return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
		}
		
       
		String assRemouldFlandJson = assRemouldFlandService.add(mapVo);

		return JSONObject.parseObject(assRemouldFlandJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/saveAssRemouldFSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFSourceLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		String assRemouldFlandJson = assRemouldFlandService.saveAssRemouldFSourceLand(mapVo);
		
		return JSONObject.parseObject(assRemouldFlandJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/assRemouldFlandUpdatePage", method = RequestMethod.GET)
	public String assRemouldFlandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRemouldFland assRemouldFland = new AssRemouldFland();
    
		assRemouldFland = assRemouldFlandService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldFland.getGroup_id());
		mode.addAttribute("hos_id", assRemouldFland.getHos_id());
		mode.addAttribute("copy_code", assRemouldFland.getCopy_code());
		mode.addAttribute("fcord_no", assRemouldFland.getFcord_no());
		mode.addAttribute("bus_type_code", assRemouldFland.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldFland.getVen_id());
		mode.addAttribute("ven_no", assRemouldFland.getVen_no());
		mode.addAttribute("sup_name", assRemouldFland.getSup_name());
		mode.addAttribute("sup_code", assRemouldFland.getSup_code());
		mode.addAttribute("create_emp", assRemouldFland.getCreate_emp());
		mode.addAttribute("create_date", assRemouldFland.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldFland.getAudit_emp());
		mode.addAttribute("fcord_date", assRemouldFland.getFcord_date());
		mode.addAttribute("state", assRemouldFland.getState());
		mode.addAttribute("note", assRemouldFland.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/assremould/assremouldfland/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/updateAssRemouldFland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldFland(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldFlandJson = assRemouldFlandService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldFlandJson);
	}
	/**
	 * @Description 
	 * 资产改造竣工确认(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/updateConfirmAssRemouldFland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmAssRemouldFland(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
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
		AssRemouldFland  assRemouldAspecial=assRemouldFlandService.queryByCode(mapVo);
		if(assRemouldAspecial == null || assRemouldAspecial.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldFdetailLand> detailList = assRemouldFDetailLandService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldFdetailLand detail : detailList) {
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

		
		String assRemouldAspecialJson = assRemouldFlandService.updateConfirmAssRemouldFland(listVo,listCardVo);
	
		return JSONObject.parseObject(assRemouldAspecialJson);

	}
	
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/saveAssRemouldFland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFland(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldFlandJson ="";
		

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
		
		
	  
		assRemouldFlandJson = assRemouldFlandService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldFlandJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/deleteAssRemouldFland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFland(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fcord_no", ids[3]);
				AssRemouldFland assRemouldAspecial = assRemouldFlandService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
	      listVo.add(mapVo);
	      
	    }
	    
		String assRemouldFlandJson = assRemouldFlandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldFlandJson);
			
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/deleteAssRemouldFDetailland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFDetailland(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFlandJson = assRemouldFlandService.deleteAssRemouldFDetailland(listVo);
		
		return JSONObject.parseObject(assRemouldFlandJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/deleteAssRemouldFSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFSourceLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFlandJson = assRemouldFlandService.deleteAssRemouldFSourceLand(listVo);
		
		return JSONObject.parseObject(assRemouldFlandJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/queryAssRemouldFland", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFland(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFland = assRemouldFlandService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldFland);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/queryAssRemouldFSourceLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFSourceLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFland = assRemouldFlandService.queryAssRemouldFSourceLand(mapVo);
		
		return JSONObject.parseObject(assRemouldFland);
		
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工明细数据(土地)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfland/queryAssRemouldFDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFDetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFland = assRemouldFlandService.queryAssRemouldFDetailLand(mapVo);
		
		return JSONObject.parseObject(assRemouldFland);
		
	}
	
}

