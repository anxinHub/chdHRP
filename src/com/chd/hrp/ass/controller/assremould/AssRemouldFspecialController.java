/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.controller.assremould;
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
import com.chd.hrp.ass.entity.assremould.AssRemouldFdetailSpecial;
import com.chd.hrp.ass.entity.assremould.AssRemouldFspecial;
import com.chd.hrp.ass.service.assremould.AssRemouldFDetailSpecialService;
import com.chd.hrp.ass.service.assremould.AssRemouldFspecialService;
/**
 * 
 * @Description:
 * 050805 资产改造竣工(专用设备)
 * @Table:
 * ASS_REMOULD_F_SPECIAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

@Controller
public class AssRemouldFspecialController extends BaseController{
	
	private static Logger logger = Logger.getLogger(AssRemouldFspecialController.class);
	
	//引入Service服务
	@Resource(name = "assRemouldFspecialService")
	private final AssRemouldFspecialService assRemouldFspecialService = null;
	@Resource(name="assRemouldFDetailSpecialService")
	   private final AssRemouldFDetailSpecialService   assRemouldFDetailSpecialService=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/assRemouldFspecialMainPage", method = RequestMethod.GET)
	public String assRemouldFspecialMainPage(Model mode) throws Exception {

		return "hrp/ass/assremould/assremouldfspecial/main";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/assRemouldFspecialAddPage", method = RequestMethod.GET)
	public String assRemouldFspecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assremould/assremouldfspecial/add";

	}

	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/addAssRemouldFspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssRemouldFspecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		mapVo.put("create_emp",SessionManager.getUserId() );
       
		String assRemouldFspecialJson = assRemouldFspecialService.add(mapVo);

		return JSONObject.parseObject(assRemouldFspecialJson);
		
	}
	/**
	 * @Description 
	 * 添加数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/saveAssRemouldFSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String assRemouldFspecialJson = assRemouldFspecialService.saveAssRemouldFSourceSpecial(mapVo);
		
		return JSONObject.parseObject(assRemouldFspecialJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/assRemouldFspecialUpdatePage", method = RequestMethod.GET)
	public String assRemouldFspecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		AssRemouldFspecial assRemouldFspecial = new AssRemouldFspecial();
    
		assRemouldFspecial = assRemouldFspecialService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", assRemouldFspecial.getGroup_id());
		mode.addAttribute("hos_id", assRemouldFspecial.getHos_id());
		mode.addAttribute("copy_code", assRemouldFspecial.getCopy_code());
		mode.addAttribute("fcord_no", assRemouldFspecial.getFcord_no());
		mode.addAttribute("bus_type_code", assRemouldFspecial.getBus_type_code());
		mode.addAttribute("ven_id", assRemouldFspecial.getVen_id());
		mode.addAttribute("ven_no", assRemouldFspecial.getVen_no());
		mode.addAttribute("sup_code", assRemouldFspecial.getSup_code());
		mode.addAttribute("sup_name", assRemouldFspecial.getSup_name());
		mode.addAttribute("create_emp", assRemouldFspecial.getCreate_emp());
		mode.addAttribute("create_date", assRemouldFspecial.getCreate_date());
		mode.addAttribute("audit_emp", assRemouldFspecial.getAudit_emp());
		mode.addAttribute("fcord_date", assRemouldFspecial.getFcord_date());
		mode.addAttribute("state", assRemouldFspecial.getState());
		mode.addAttribute("note", assRemouldFspecial.getNote());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assremould/assremouldfspecial/update";
	}
		
	/**
	 * @Description 
	 * 更新数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/updateAssRemouldFspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssRemouldFspecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String assRemouldFspecialJson = assRemouldFspecialService.update(mapVo);
		
		return JSONObject.parseObject(assRemouldFspecialJson);
	}
	/**
	 * @Description 
	 * 资产改造竣工确认(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/updateConfirmAssRemouldFspecial", method = RequestMethod.POST)
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
		AssRemouldFspecial  assRemouldAspecial=assRemouldFspecialService.queryByCode(mapVo);
		if(assRemouldAspecial == null || assRemouldAspecial.getState() == 2){
			continue;
		}	
		if(DateUtil.compareDate(assRemouldAspecial.getCreate_date(), new Date())){
			return JSONObject.parseObject("{\"warn\":\"确认日期不能小于制单日期,不能入库! \"}");
		}
		//查询明细表
			List<AssRemouldFdetailSpecial> detailList = assRemouldFDetailSpecialService.queryByDisANo(mapVo);
			//明细表存在进行判断卡片
			if(detailList != null && detailList.size() > 0){
				for (AssRemouldFdetailSpecial detail : detailList) {
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

		
		String assRemouldAspecialJson = assRemouldFspecialService.updateConfirmAssRemouldFspecial(listVo,listCardVo);
	
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
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/saveAssRemouldFspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssRemouldFspecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String assRemouldFspecialJson ="";
		

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
	  
		assRemouldFspecialJson = assRemouldFspecialService.addOrUpdate(mapVo);
		
		return JSONObject.parseObject(assRemouldFspecialJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/deleteAssRemouldFspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFspecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("fcord_no", ids[3]);
				AssRemouldFspecial assRemouldAspecial = assRemouldFspecialService.queryByCode(mapVo);
				if (assRemouldAspecial != null ) {
					if (assRemouldAspecial.getState() >0) {
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
					
				}
				
	      listVo.add(mapVo);
	      
	    }
	    
		String assRemouldFspecialJson = assRemouldFspecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assRemouldFspecialJson);
			
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/deleteAssRemouldFDetailspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFDetailspecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFspecialJson = assRemouldFspecialService.deleteAssRemouldFDetailspecial(listVo);
		
		return JSONObject.parseObject(assRemouldFspecialJson);
		
	}
	/**
	 * @Description 
	 * 删除数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/deleteAssRemouldFSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssRemouldFSourceSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		
		String assRemouldFspecialJson = assRemouldFspecialService.deleteAssRemouldFSourceSpecial(listVo);
		
		return JSONObject.parseObject(assRemouldFspecialJson);
		
	}
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/queryAssRemouldFspecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFspecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFspecial = assRemouldFspecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assRemouldFspecial);
		
	}
	
	
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/queryAssRemouldFSourceSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFSourceSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFspecial = assRemouldFspecialService.queryAssRemouldFSourceSpecial(mapVo);
		
		return JSONObject.parseObject(assRemouldFspecial);
		
	}
	/**
	 * @Description 
	 * 查询数据 050805 资产改造竣工明细数据(专用设备)
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assremould/assremouldfspecial/queryAssRemouldFDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRemouldFDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRemouldFspecial = assRemouldFspecialService.queryAssRemouldFDetailSpecial(mapVo);
		
		return JSONObject.parseObject(assRemouldFspecial);
		
	}
	
}

