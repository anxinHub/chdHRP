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
import com.chd.hrp.ass.entity.assdisposal.other.AssDisposalApplyDetailOther;
import com.chd.hrp.ass.entity.assdisposal.other.AssDisposalApplyOther;
import com.chd.hrp.ass.service.assdisposal.other.AssDisposalApplyDetailOtherService;
import com.chd.hrp.ass.service.assdisposal.other.AssDisposalApplyOtherService;

/**
 * 
 * @Description: 051001资产处置申报(其他固定资产)
 * @Table: ASS_DISPOSAL_A_Other
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssDisposalApplyOtherController extends BaseController {     
	private static Logger logger = Logger.getLogger(AssDisposalApplyOtherController.class);
 
	// 引入Service服务
	@Resource(name = "assDisposalApplyOtherService")
	private final AssDisposalApplyOtherService assDisposalApplyOtherService = null;

	@Resource(name = "assDisposalApplyDetailOtherService")
	private final AssDisposalApplyDetailOtherService assDisposalApplyDetailOtherService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/assDisposalApplyOtherMainPage", method = RequestMethod.GET)
	public String assBackOtherMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05042",MyConfig.getSysPara("05042"));
		
		return "hrp/ass/assother/assdisposal/disposalapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/assDisposalApplyOtherAddPage", method = RequestMethod.GET)
	public String assBackOtherAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdisposal/disposalapply/add";

	}
	
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/assImportOtherPage", method = RequestMethod.GET)
	public String assImportOtherPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("dispose_type", mapVo.get("dispose_type"));
		mode.addAttribute("dispose_type_name", mapVo.get("dispose_type_name"));
		mode.addAttribute("create_date", mapVo.get("create_date"));
		mode.addAttribute("note", mapVo.get("note"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assother/assdisposal/disposalapply/batchAdd";
	}
	
	/**
	 * @Description 添加数据 051001资产处置申报(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/saveAssDisposalApplyOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalApplyOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssDisposalApplyOther assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);
		if(assDisposalApplyOther != null){
			if(assDisposalApplyOther.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalApplyOtherJson = assDisposalApplyOtherService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalApplyOtherJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产处置申报(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/assDisposalApplyOtherUpdatePage", method = RequestMethod.GET)
	public String assDisposalApplyOtherUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyOther assDisposalApplyOther = new AssDisposalApplyOther();

		assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplyOther.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplyOther.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplyOther.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplyOther.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplyOther.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplyOther.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplyOther.getNote());
		mode.addAttribute("create_emp", assDisposalApplyOther.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplyOther.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplyOther.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplyOther.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplyOther.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplyOther.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplyOther.getState());
		mode.addAttribute("state_name", assDisposalApplyOther.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05042",MyConfig.getSysPara("05042"));
		
		return "hrp/ass/assother/assdisposal/disposalapply/update";
	}


	/**
	 * @Description 删除数据 051001资产处置申报(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/deleteAssDisposalApplyOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyOther assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);
			if(assDisposalApplyOther != null){
				if(assDisposalApplyOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDisposalApplyOtherJson = assDisposalApplyOtherService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalApplyOtherJson);

	}

	/**
	 * @Description 查询数据 051001资产处置申报(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/queryAssDisposalApplyOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalApplyOther(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackOther = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackOther = assDisposalApplyOtherService.query(getPage(mapVo));
			 
		}else{

			assBackOther = assDisposalApplyOtherService.queryDetails(getPage(mapVo));
			 
		}
		

		return JSONObject.parseObject(assBackOther);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/queryAssDisposalApplyDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailOther(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailOther = assDisposalApplyDetailOtherService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailOther);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/deleteAssDisposalApplyDetailOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyDetailOther(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssDisposalApplyOther assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);
				if(assDisposalApplyOther != null){
					if(assDisposalApplyOther.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalApplyDetailOtherJson = assDisposalApplyDetailOtherService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalApplyDetailOtherJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置申报(其他固定资产)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/updateConfirmDisposalApplyOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalApplyOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyOther assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);
			if(assDisposalApplyOther == null || assDisposalApplyOther.getState() > 0){
				continue;
			}
			
			List<AssDisposalApplyDetailOther> detailList = assDisposalApplyDetailOtherService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailOther detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyOther.getDispose_type());
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

		String assDisposalApplyOtherJson = assDisposalApplyOtherService.updateConfirmDisposalApplyOther(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyOtherJson);

	}
	
	
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/updateUnConfirmDisposalApplyOther", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUnConfirmDisposalApplyOther(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyOther assDisposalApplyOther = assDisposalApplyOtherService.queryByCode(mapVo);
			if(assDisposalApplyOther == null || assDisposalApplyOther.getState() == 0){
				continue;
			}
			
			List<AssDisposalApplyDetailOther> detailList = assDisposalApplyDetailOtherService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailOther detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyOther.getDispose_type());
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

		String assDisposalApplyOtherJson = assDisposalApplyOtherService.updateUnConfirmDisposalApplyOther(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyOtherJson);

	}
	
	
	
	@RequestMapping(value = "/hrp/ass/assother/assdisposal/disposalapply/queryState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String str = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		List<String> list = assDisposalApplyOtherService.queryState(mapVo);
		
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
