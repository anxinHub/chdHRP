package com.chd.hrp.ass.controller.check.house;

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
import com.chd.hrp.ass.entity.check.house.AssChkAHouse;
import com.chd.hrp.ass.entity.check.house.AssChkAdetailHouse;
import com.chd.hrp.ass.service.check.house.AssChkAHouseService;
import com.chd.hrp.ass.service.check.house.AssChkAdetailHouseService;

/**
 * 
 * @Description: 051001资产盘亏申报(土地)
 * @Table: ASS_CHK_A_House
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkAHouseController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkAHouseController.class);

	// 引入Service服务
	@Resource(name = "assChkAHouseService")
	private final AssChkAHouseService assChkAHouseService = null;

	@Resource(name = "assChkAdetailHouseService")
	private final AssChkAdetailHouseService assChkAdetailHouseService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */						
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/assChkApplyHouseMainPage", method = RequestMethod.GET)
	public String assBackHouseMainPage(Model mode) throws Exception {

		return "hrp/ass/asshouse/asscheck/chkapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/assChkApplyHouseAddPage", method = RequestMethod.GET)
	public String assBackHouseAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asscheck/chkapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/saveAssChkApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkAHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkAHouse assChkAHouse = assChkAHouseService.queryByCode(mapVo);
		if(assChkAHouse != null){
			if(assChkAHouse.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkAHouseJson = assChkAHouseService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkAHouseJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/assChkApplyHouseUpdatePage", method = RequestMethod.GET)
	public String assChkAHouseUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkAHouse assChkAHouse = new AssChkAHouse();

		assChkAHouse = assChkAHouseService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAHouse.getGroup_id());
		mode.addAttribute("hos_id", assChkAHouse.getHos_id());
		mode.addAttribute("copy_code", assChkAHouse.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAHouse.getAss_chk_no());
		mode.addAttribute("note", assChkAHouse.getNote());
		mode.addAttribute("create_emp", assChkAHouse.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAHouse.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAHouse.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAHouse.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAHouse.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAHouse.getAudit_emp_name());
		mode.addAttribute("state", assChkAHouse.getState());
		mode.addAttribute("state_name", assChkAHouse.getState_name());
		
		mode.addAttribute("ass_05005", MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asshouse/asscheck/chkapply/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/deleteAssChkApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_chk_no", ids[3]);
			
			AssChkAHouse assChkAHouse = assChkAHouseService.queryByCode(mapVo);
			if(assChkAHouse != null){
				if(assChkAHouse.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChkAHouseJson = assChkAHouseService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkAHouseJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/queryAssChkApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAHouse(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackHouse = assChkAHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackHouse);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/queryAssChkApplyDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailHouse(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String carno = mapVo.get("ass_chk_no").toString();
		String car[] =carno.split(",");
		  final List<String> ids = new ArrayList<String>();  
		for (int i = 0; i < car.length; i++) {
			ids.add(car[i]);
		}
		mapVo.put("ass_chk_no", ids);
		String assBackDetailHouse = assChkAdetailHouseService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailHouse);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/deleteAssChkApplyDetailHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAdetailHouse(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("ass_chk_no", ids[3])   ;
				mapVo.put("ass_card_no", ids[4]);
				AssChkAHouse assChkAHouse = assChkAHouseService.queryByCode(mapVo);
				if(assChkAHouse != null){
					if(assChkAHouse.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkAdetailHouseJson = assChkAdetailHouseService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkAdetailHouseJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asshouse/check/chkapply/updateConfirmChkApplyHouse", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkAHouse(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			mapVo.put("ass_chk_no", ids[3]);
			mapVo.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			mapVo.put("audit_emp", SessionManager.getUserId());
			
			AssChkAHouse assChkAHouse = assChkAHouseService.queryByCode(mapVo);
			if(assChkAHouse == null || assChkAHouse.getState() > 0){
				continue;
			}
			
			List<AssChkAdetailHouse> detailList = assChkAdetailHouseService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkAdetailHouse detail : detailList) {
					Map<String, Object> listCardMap = new HashMap<String, Object>();
					listCardMap.put("group_id", detail.getGroup_id());
					listCardMap.put("hos_id", detail.getHos_id());
					listCardMap.put("copy_code", detail.getCopy_code());
					listCardMap.put("dispose_type", 12);
					listCardMap.put("apply_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					listCardMap.put("ass_card_no", detail.getAss_card_no());
					if(map.containsKey(detail.getAss_card_no())){
						return JSONObject.parseObject("{\"warn\":\"存在已盘亏的卡片,不能进行操作! \"}");
					}
					map.put(detail.getAss_card_no(), detail.getAss_card_no());
					listCardVo.add(listCardMap);
				}
			}else{
				return JSONObject.parseObject("{\"warn\":\"没有明细数据不能操作盘亏确认! \"}");
			}
			listVo.add(mapVo);

		}
		
		if(listVo.size() == 0){
			return JSONObject.parseObject("{\"warn\":\"不能重复确认操作! \"}");
		}

		String assChkAHouseJson = assChkAHouseService.updateConfirmChkAHouse(listVo,listCardVo);

		return JSONObject.parseObject(assChkAHouseJson);

	}
	
}
