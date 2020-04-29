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
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalApplyDetailHouse;
import com.chd.hrp.ass.entity.assdisposal.house.AssDisposalApplyHouse;
import com.chd.hrp.ass.service.assdisposal.house.AssDisposalApplyDetailHouseService;
import com.chd.hrp.ass.service.assdisposal.house.AssDisposalApplyHouseService;

/**
 * 
 * @Description: 051001资产处置申报(土地)
 * @Table: ASS_DISPOSAL_A_House
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller 
public class AssDisposalApplyHouseController extends BaseController {
	private static Logger logger = Logger.getLogger(AssDisposalApplyHouseController.class);

	// 引入Service服务
	@Resource(name = "assDisposalApplyHouseService")
	private final AssDisposalApplyHouseService assDisposalApplyHouseService = null;

	@Resource(name = "assDisposalApplyDetailHouseService")
	private final AssDisposalApplyDetailHouseService assDisposalApplyDetailHouseService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/assDisposalApplyHouseMainPage", method = RequestMethod.GET)
	public String assBackHouseMainPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/assdisposal/disposalapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/assDisposalApplyHouseAddPage", method = RequestMethod.GET)
	public String assBackHouseAddPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/assdisposal/disposalapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/saveAssDisposalApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssDisposalApplyHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssDisposalApplyHouse assDisposalApplyHouse = assDisposalApplyHouseService.queryByCode(mapVo);
		if(assDisposalApplyHouse != null){
			if(assDisposalApplyHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assDisposalApplyHouseJson = assDisposalApplyHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assDisposalApplyHouseJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/assDisposalApplyHouseUpdatePage", method = RequestMethod.GET)
	public String assDisposalApplyHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDisposalApplyHouse assDisposalApplyHouse = new AssDisposalApplyHouse();

		assDisposalApplyHouse = assDisposalApplyHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDisposalApplyHouse.getGroup_id());
		mode.addAttribute("hos_id", assDisposalApplyHouse.getHos_id());
		mode.addAttribute("copy_code", assDisposalApplyHouse.getCopy_code());
		mode.addAttribute("dis_a_no", assDisposalApplyHouse.getDis_a_no());
		mode.addAttribute("dispose_type", assDisposalApplyHouse.getDispose_type());
		mode.addAttribute("dispose_type_name", assDisposalApplyHouse.getDispose_type_name());
		mode.addAttribute("note", assDisposalApplyHouse.getNote());
		mode.addAttribute("create_emp", assDisposalApplyHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assDisposalApplyHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assDisposalApplyHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assDisposalApplyHouse.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assDisposalApplyHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assDisposalApplyHouse.getAudit_emp_name());
		mode.addAttribute("state", assDisposalApplyHouse.getState());
		mode.addAttribute("state_name", assDisposalApplyHouse.getState_name());
		return "hrp/ass/asshouse/assdisposal/disposalapply/update";
	}


	/**
	 * @Description 删除数据 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/deleteAssDisposalApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyHouse assDisposalApplyHouse = assDisposalApplyHouseService.queryByCode(mapVo);
			if(assDisposalApplyHouse != null){
				if(assDisposalApplyHouse.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assDisposalApplyHouseJson = assDisposalApplyHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assDisposalApplyHouseJson);

	}

	/**
	 * @Description 查询数据 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/queryAssDisposalApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDisposalApplyHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String assBackHouse = null;
		
		if("0".equals(mapVo.get("show_detail").toString())){
			
			assBackHouse = assDisposalApplyHouseService.query(getPage(mapVo));
			 
		}else{

			assBackHouse = assDisposalApplyHouseService.queryDetails(getPage(mapVo));
			 
		}


		return JSONObject.parseObject(assBackHouse);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/queryAssDisposalApplyDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDisposalApplyDetailHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
        
		String assBackDetailHouse = assDisposalApplyDetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailHouse);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/deleteAssDisposalApplyDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDisposalApplyDetailHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssDisposalApplyHouse assDisposalApplyHouse = assDisposalApplyHouseService.queryByCode(mapVo);
				if(assDisposalApplyHouse != null){
					if(assDisposalApplyHouse.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认处置的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assDisposalApplyDetailHouseJson = assDisposalApplyDetailHouseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assDisposalApplyDetailHouseJson);
			
	}
	
	/**
	 * @Description 确认处置 051001资产处置申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/assdisposal/disposalapply/updateConfirmDisposalApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmDisposalApplyHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssDisposalApplyHouse assDisposalApplyHouse = assDisposalApplyHouseService.queryByCode(mapVo);
			if(assDisposalApplyHouse == null || assDisposalApplyHouse.getState() > 0){
				continue;
			}
			
			List<AssDisposalApplyDetailHouse> detailList = assDisposalApplyDetailHouseService.queryByDisANo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssDisposalApplyDetailHouse detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", assDisposalApplyHouse.getDispose_type());
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

		String assDisposalApplyHouseJson = assDisposalApplyHouseService.updateConfirmDisposalApplyHouse(listVo,listCardVo);

		return JSONObject.parseObject(assDisposalApplyHouseJson);

	}
	
}
