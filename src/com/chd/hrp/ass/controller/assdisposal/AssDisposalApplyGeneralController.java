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
import com.chd.hrp.ass.entity.assdisposal.general.AssDisposalApplyDetailGeneral;
import com.chd.hrp.ass.entity.assdisposal.general.AssDisposalApplyGeneral;
import com.chd.hrp.ass.service.assdisposal.general.AssDisposalApplyDetailGeneralService;
import com.chd.hrp.ass.service.assdisposal.general.AssDisposalApplyGeneralService;

/**
 * 
 * @Description: 051001资产处置申报(一般设备)
 * @Table: ASS_DISPOSAL_A_General
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller 
public class AssDisposalApplyGeneralController extends BaseController {   
	private static Logger logger = Logger.getLogger(AssDisposalApplyGeneralController.class);
 
	// 引入Service服务
	@Resource(name = "assDisposalApplyGeneralService")
	private final AssDisposalApplyGeneralService assDisposalApplyGeneralService = null;

	@Resource(name = "assDisposalApplyDetailGeneralService")
	private final AssDisposalApplyDetailGeneralService assDisposalApplyDetailGeneralService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/assDisposalApplyGeneralMainPage", method = RequestMethod.GET)
	public String assBackGeneralMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05311",MyConfig.getSysPara("05311"));
		
		return "hrp/ass/assgeneral/assdisposal/disposalapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/assDisposalApplyGeneralAddPage", method = RequestMethod.GET)
	public String assBackGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assdisposal/disposalapply/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/assImportGeneralPage", method = RequestMethod.GET)
	public String assImportGeneralPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("dispose_type_name", mapVo.get("dispose_type_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/assdisposal/disposalapply/batchAdd";
	}
	
	/**
	 * @Description 添加数据 051001资产处置申报(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/saveAssDisposalApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalApplyGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssDisposalApplyGeneral assDisposalApplyGeneral = assDisposalApplyGeneralService.queryByCode(mapVo);
		if(assDisposalApplyGeneral != null){
			if(assDisposalApplyGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalApplyGeneralJson = assDisposalApplyGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalApplyGeneralJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产处置申报(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/assDisposalApplyGeneralUpdatePage", method = RequestMethod.GET)
	public String assDisposalApplyGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyGeneral assDisposalApplyGeneral = new AssDisposalApplyGeneral();

		assDisposalApplyGeneral = assDisposalApplyGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplyGeneral.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplyGeneral.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplyGeneral.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplyGeneral.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplyGeneral.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplyGeneral.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplyGeneral.getNote());
		mode.addAttribute("create_emp", assDisposalApplyGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplyGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplyGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplyGeneral.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplyGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplyGeneral.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplyGeneral.getState());
		mode.addAttribute("state_name", assDisposalApplyGeneral.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05311",MyConfig.getSysPara("05311"));
		
		return "hrp/ass/assgeneral/assdisposal/disposalapply/update";
	}


	/**
	 * @Description 删除数据 051001资产处置申报(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/deleteAssDisposalApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyGeneral assDisposalApplyGeneral = assDisposalApplyGeneralService.queryByCode(mapVo);
			if(assDisposalApplyGeneral != null){
				if(assDisposalApplyGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDisposalApplyGeneralJson = assDisposalApplyGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalApplyGeneralJson);

	}

	/**
	 * @Description 查询数据 051001资产处置申报(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/queryAssDisposalApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalApplyGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackGeneral = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackGeneral = assDisposalApplyGeneralService.query(getPage(mapVo));
			 
		}else{

			assBackGeneral = assDisposalApplyGeneralService.queryDetails(getPage(mapVo));
			 
		}


		return JSONObject.parseObject(assBackGeneral);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/queryAssDisposalApplyDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailGeneral = assDisposalApplyDetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailGeneral);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/deleteAssDisposalApplyDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyDetailGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssDisposalApplyGeneral assDisposalApplyGeneral = assDisposalApplyGeneralService.queryByCode(mapVo);
				if(assDisposalApplyGeneral != null){
					if(assDisposalApplyGeneral.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalApplyDetailGeneralJson = assDisposalApplyDetailGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalApplyDetailGeneralJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置申报(一般设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/updateConfirmDisposalApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalApplyGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyGeneral assDisposalApplyGeneral = assDisposalApplyGeneralService.queryByCode(mapVo);
			/*if (assDisposalApplyGeneral.getState()==2) {
				continue;
			}*/
			
			if(assDisposalApplyGeneral == null || assDisposalApplyGeneral.getState() > 0){
				continue;
			}
			
			List<AssDisposalApplyDetailGeneral> detailList = assDisposalApplyDetailGeneralService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailGeneral detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyGeneral.getDispose_type());
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

		String assDisposalApplyGeneralJson = assDisposalApplyGeneralService.updateConfirmDisposalApplyGeneral(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyGeneralJson);

	}
	
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/updateUnConfirmDisposalApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnConfirmDisposalApplyGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyGeneral assDisposalApplyGeneral = assDisposalApplyGeneralService.queryByCode(mapVo);
			
			if(assDisposalApplyGeneral == null || assDisposalApplyGeneral.getState() == 0){
				continue;
			}
			
			List<AssDisposalApplyDetailGeneral> detailList = assDisposalApplyDetailGeneralService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailGeneral detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyGeneral.getDispose_type());
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

		String assDisposalApplyGeneralJson = assDisposalApplyGeneralService.updateUnConfirmDisposalApplyGeneral(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyGeneralJson);

	}
	
	
	/**
	 * @Description
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/assdisposal/disposalapply/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assDisposalApplyGeneralService.queryState(mapVo);
		
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
