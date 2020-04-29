package com.chd.hrp.ass.controller.check.land;

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
import com.chd.hrp.ass.entity.check.land.AssChkAdetailLand;
import com.chd.hrp.ass.entity.check.land.AssChkAland;
import com.chd.hrp.ass.service.check.land.AssChkAdetailLandService;
import com.chd.hrp.ass.service.check.land.AssChkAlandService;

/**
 * 
 * @Description: 051001资产盘亏申报(土地)
 * @Table: ASS_CHK_A_LAND
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class AssChkAlandController extends BaseController {
	private static Logger logger = Logger.getLogger(AssChkAlandController.class);

	// 引入Service服务
	@Resource(name = "assChkAlandService")
	private final AssChkAlandService assChkAlandService = null;

	@Resource(name = "assChkAdetailLandService")
	private final AssChkAdetailLandService assChkAdetailLandService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */						
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/assChkApplyLandMainPage", method = RequestMethod.GET)
	public String assBackLandMainPage(Model mode) throws Exception {

		return "hrp/ass/assland/asscheck/chkapply/main";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/assChkApplyLandAddPage", method = RequestMethod.GET)
	public String assBackLandAddPage(Model mode) throws Exception {

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/chkapply/add";

	}
	
	/**
	 * @Description 添加数据 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/saveAssChkApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssChkAland(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
		AssChkAland assChkAland = assChkAlandService.queryByCode(mapVo);
		if(assChkAland != null){
			if(assChkAland.getState() > 0){
				return JSONObject.parseObject("{\"warn\":\"数据不能保存! \"}");
			}
		}

		String assChkAlandJson = assChkAlandService.addOrUpdate(mapVo);

		return JSONObject.parseObject(assChkAlandJson);

	}
	
	/**
	 * @Description 更新跳转页面 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/assChkApplyLandUpdatePage", method = RequestMethod.GET)
	public String assChkAlandUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssChkAland assChkAland = new AssChkAland();

		assChkAland = assChkAlandService.queryByCode(mapVo);

		mode.addAttribute("group_id", assChkAland.getGroup_id());
		mode.addAttribute("hos_id", assChkAland.getHos_id());
		mode.addAttribute("copy_code", assChkAland.getCopy_code());
		mode.addAttribute("ass_chk_no", assChkAland.getAss_chk_no());
		mode.addAttribute("note", assChkAland.getNote());
		mode.addAttribute("create_emp", assChkAland.getCreate_emp());
		mode.addAttribute("create_emp_name", assChkAland.getCreate_emp_name());
		mode.addAttribute("create_date", DateUtil.dateToString(assChkAland.getCreate_date(), "yyyy-MM-dd"));
		mode.addAttribute("apply_date", DateUtil.dateToString(assChkAland.getApply_date(), "yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assChkAland.getAudit_emp());
		mode.addAttribute("audit_emp_name", assChkAland.getAudit_emp_name());
		mode.addAttribute("state", assChkAland.getState());
		mode.addAttribute("state_name", assChkAland.getState_name());
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/assland/asscheck/chkapply/update";
	}


	/**
	 * @Description 删除数据 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/deleteAssChkApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAland(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkAland assChkAland = assChkAlandService.queryByCode(mapVo);
			if(assChkAland != null){
				if(assChkAland.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
				}
			}

			listVo.add(mapVo);

		}

		String assChkAlandJson = assChkAlandService.deleteBatch(listVo);

		return JSONObject.parseObject(assChkAlandJson);

	}

	/**
	 * @Description 查询数据 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */																	   
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/queryAssChkApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChkAland(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assBackLand = assChkAlandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackLand);

	}
	
	//查询明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/queryAssChkApplyDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssChkAdetailLand(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
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
		String assBackDetailLand = assChkAdetailLandService.query(getPage(mapVo));

		return JSONObject.parseObject(assBackDetailLand);
		
	}
	
	//删除明细列表  添加页使用
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/deleteAssChkApplyDetailLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssChkAdetailLand(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
				AssChkAland assChkAland = assChkAlandService.queryByCode(mapVo);
				if(assChkAland != null){
					if(assChkAland.getState() > 0){
						return JSONObject.parseObject("{\"warn\":\"已确认盘亏的数据不能删除! \"}");
					}
				}
				
				listVo.add(mapVo);
	    }
	    
		String assChkAdetailLandJson = assChkAdetailLandService.deleteBatch(listVo);
			
	  return JSONObject.parseObject(assChkAdetailLandJson);
			
	}
	
	/**
	 * @Description 确认盘亏 051001资产盘亏申报(土地)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assland/check/chkapply/updateConfirmChkApplyLand", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateConfirmChkAland(@RequestParam(value = "ParamVo") String paramVo, Model mode)
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
			
			AssChkAland assChkAland = assChkAlandService.queryByCode(mapVo);
			if(assChkAland == null || assChkAland.getState() == 2){
				continue;
			}
			
			List<AssChkAdetailLand> detailList = assChkAdetailLandService.queryByAssChkNo(mapVo);
			if(detailList != null && detailList.size() > 0){
				for (AssChkAdetailLand detail : detailList) {
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

		String assChkAlandJson = assChkAlandService.updateConfirmChkAland(listVo,listCardVo);

		return JSONObject.parseObject(assChkAlandJson);

	}
	
}
