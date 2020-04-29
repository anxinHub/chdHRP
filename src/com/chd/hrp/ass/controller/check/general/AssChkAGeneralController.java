package com.chd.hrp.ass.controller.check.general;

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
import com.chd.hrp.ass.entity.check.general.AssChkAdetailGeneral;
import com.chd.hrp.ass.entity.check.general.AssChkAGeneral;
import com.chd.hrp.ass.service.check.general.AssChkAdetailGeneralService;
import com.chd.hrp.ass.service.check.general.AssChkAGeneralService;

/**
 * 
 * @Description: 051001资产盘亏申报(专用设备)
 * @Table: ASS_CHK_A_General
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkAGeneralController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkAGeneralController.class);

	// 引入Service服务
	@Resource(name = "assChkAGeneralService")
	private final AssChkAGeneralService assChkAGeneralService = null;

	@Resource(name = "assChkAdetailGeneralService")
	private final AssChkAdetailGeneralService assChkAdetailGeneralService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */						
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/assChkApplyGeneralMainPage", method = RequestMethod.GET)
	public String assBackGeneralMainPage(Model mode) throws Exception {

		return "hrp/ass/assgeneral/asscheck/chkapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/assChkApplyGeneralAddPage", method = RequestMethod.GET)
	public String assBackGeneralAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asscheck/chkapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产盘亏申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/saveAssChkApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkAGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkAGeneral assChkAGeneral = assChkAGeneralService.queryByCode(mapVo);
		if(assChkAGeneral != null){
			if(assChkAGeneral.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkAGeneralJson = assChkAGeneralService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkAGeneralJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/assChkApplyGeneralUpdatePage", method = RequestMethod.GET)
	public String assChkAGeneralUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkAGeneral assChkAGeneral = new AssChkAGeneral();

		assChkAGeneral = assChkAGeneralService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAGeneral.getGroup_id());
		mode.addAttribute("hos_id", assChkAGeneral.getHos_id());
		mode.addAttribute("copy_code", assChkAGeneral.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAGeneral.getAss_chk_no());
		mode.addAttribute("note", assChkAGeneral.getNote());
		mode.addAttribute("create_emp", assChkAGeneral.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAGeneral.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAGeneral.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAGeneral.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAGeneral.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAGeneral.getAudit_emp_name());
		mode.addAttribute("state", assChkAGeneral.getState());
		mode.addAttribute("state_name", assChkAGeneral.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assgeneral/asscheck/chkapply/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/deleteAssChkApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkAGeneral assChkAGeneral = assChkAGeneralService.queryByCode(mapVo);
			if(assChkAGeneral != null){
				if(assChkAGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChkAGeneralJson = assChkAGeneralService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkAGeneralJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/queryAssChkApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAGeneral(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackGeneral = assChkAGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackGeneral);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/queryAssChkApplyDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailGeneral(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailGeneral = assChkAdetailGeneralService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailGeneral);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/deleteAssChkApplyDetailGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAdetailGeneral(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssChkAGeneral assChkAGeneral = assChkAGeneralService.queryByCode(mapVo);
				if(assChkAGeneral != null){
					if(assChkAGeneral.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkAdetailGeneralJson = assChkAdetailGeneralService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkAdetailGeneralJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(专用设备)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgeneral/check/chkapply/updateConfirmChkApplyGeneral", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkAGeneral(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkAGeneral assChkAGeneral = assChkAGeneralService.queryByCode(mapVo);
			if(assChkAGeneral == null || assChkAGeneral.getState() > 0){
				continue;
			}
			
			List<AssChkAdetailGeneral> detailList = assChkAdetailGeneralService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkAdetailGeneral detail : detailList) {
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

		String assChkAGeneralJson = assChkAGeneralService.updateConfirmChkAGeneral(listVo,listCardVo);

		return JSONObject.parseObject(assChkAGeneralJson);

	}
	
}
