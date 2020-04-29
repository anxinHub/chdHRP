package com.chd.hrp.ass.controller.assdisposal;

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
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalApplyDetailSpecial;
import com.chd.hrp.ass.entity.assdisposal.special.AssDisposalApplySpecial;
import com.chd.hrp.ass.service.assdisposal.special.AssDisposalApplyDetailSpecialService;
import com.chd.hrp.ass.service.assdisposal.special.AssDisposalApplySpecialService;

/**
 * 
 * @Description: 051001资产处置申报(专用设备)
 * @Table: ASS_DISPOSAL_A_SPECIAL
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0 
 */
@Controller
public class AssDisposalApplySpecialController extends BaseController {     
	private static Logger logger = Logger.getLogger(AssDisposalApplySpecialController.class);

	// 引入Service服务
	@Resource(name = "assDisposalApplySpecialService")
	private final AssDisposalApplySpecialService assDisposalApplySpecialService = null;

	@Resource(name = "assDisposalApplyDetailSpecialService")
	private final AssDisposalApplyDetailSpecialService assDisposalApplyDetailSpecialService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/assDisposalApplySpecialMainPage", method = RequestMethod.GET)
	public String assBackSpecialMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05310",MyConfig.getSysPara("05310"));
		
		return "hrp/ass/assspecial/assdisposal/disposalapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/assDisposalApplySpecialAddPage", method = RequestMethod.GET)
	public String assBackSpecialAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/assdisposal/disposalapply/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/assImportSpecialPage", method = RequestMethod.GET)
	public String assImportSpecialPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("dispose_type_name", mapVo.get("dispose_type_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assspecial/assdisposal/disposalapply/batchAdd";
	}
	
	/**
	 * @Description 添加数据 051001资产处置申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/saveAssDisposalApplySpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalApplySpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
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
		
		AssDisposalApplySpecial assDisposalApplySpecial = assDisposalApplySpecialService.queryByCode(mapVo);
		if(assDisposalApplySpecial != null){
			if(assDisposalApplySpecial.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalApplySpecialJson = assDisposalApplySpecialService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalApplySpecialJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产处置申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/assDisposalApplySpecialUpdatePage", method = RequestMethod.GET)
	public String assDisposalApplySpecialUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplySpecial assDisposalApplySpecial = new AssDisposalApplySpecial();

		assDisposalApplySpecial = assDisposalApplySpecialService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplySpecial.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplySpecial.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplySpecial.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplySpecial.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplySpecial.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplySpecial.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplySpecial.getNote());
		mode.addAttribute("create_emp", assDisposalApplySpecial.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplySpecial.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplySpecial.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplySpecial.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplySpecial.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplySpecial.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplySpecial.getState());
		mode.addAttribute("state_name", assDisposalApplySpecial.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05310",MyConfig.getSysPara("05310"));
		
		return "hrp/ass/assspecial/assdisposal/disposalapply/update";
	}


	/**
	 * @Description 删除数据 051001资产处置申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/deleteAssDisposalApplySpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplySpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("dis_a_no", ids[3]);
			
			AssDisposalApplySpecial assDisposalApplySpecial = assDisposalApplySpecialService.queryByCode(mapVo);
			if(assDisposalApplySpecial != null){
				if(assDisposalApplySpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDisposalApplySpecialJson = assDisposalApplySpecialService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalApplySpecialJson);

	}

	/**
	 * @Description 查询数据 051001资产处置申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/queryAssDisposalApplySpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalApplySpecial(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackSpecial = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackSpecial = assDisposalApplySpecialService.query(getPage(mapVo));
			 
		}else{

			assBackSpecial = assDisposalApplySpecialService.queryDetails(getPage(mapVo));
			 
		}

		return JSONObject.parseObject(assBackSpecial);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/queryAssDisposalApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("dis_a_no").toString();
		String car[] = carno.split(",");
		final List<String> ids = new ArrayList<String>();
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("dis_a_no", ids);
        
		String assBackDetailSpecial = assDisposalApplyDetailSpecialService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailSpecial);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/deleteAssDisposalApplyDetailSpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyDetailSpecial(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("dis_a_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssDisposalApplySpecial assDisposalApplySpecial = assDisposalApplySpecialService.queryByCode(mapVo);
				if(assDisposalApplySpecial != null){
					if(assDisposalApplySpecial.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalApplyDetailSpecialJson = assDisposalApplyDetailSpecialService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalApplyDetailSpecialJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/updateConfirmDisposalApplySpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalApplySpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
			mapVo.put("dis_a_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssDisposalApplySpecial assDisposalApplySpecial = assDisposalApplySpecialService.queryByCode(mapVo);
			if(assDisposalApplySpecial == null || assDisposalApplySpecial.getState() == 2){
				continue;
			}
			
			List<AssDisposalApplyDetailSpecial> detailList = assDisposalApplyDetailSpecialService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailSpecial detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplySpecial.getDispose_type());
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已处置的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置确认! \"}");
			}
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assDisposalApplySpecialJson = assDisposalApplySpecialService.updateConfirmDisposalApplySpecial(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplySpecialJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/updateUnConfirmDisposalApplySpecial", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnConfirmDisposalApplySpecial(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

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
			mapVo.put("dis_a_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssDisposalApplySpecial assDisposalApplySpecial = assDisposalApplySpecialService.queryByCode(mapVo);
			if(assDisposalApplySpecial == null || assDisposalApplySpecial.getState() == 0){
				continue;
			}
			
			List<AssDisposalApplyDetailSpecial> detailList = assDisposalApplyDetailSpecialService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailSpecial detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplySpecial.getDispose_type());
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作处置取消确认! \"}");
			}
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复取消确认操作! \"}");
		}

		String assDisposalApplySpecialJson = assDisposalApplySpecialService.updateUnConfirmDisposalApplySpecial(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplySpecialJson);

	}
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assspecial/assdisposal/disposalapply/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assDisposalApplySpecialService.queryState(mapVo);
		
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
