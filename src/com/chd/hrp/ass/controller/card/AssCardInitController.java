/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.card;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.entity.card.AssCardInitGeneral;
import com.chd.hrp.ass.entity.card.AssCardInitHouse;
import com.chd.hrp.ass.entity.card.AssCardInitInassets;
import com.chd.hrp.ass.entity.card.AssCardInitLand;
import com.chd.hrp.ass.entity.card.AssCardInitOther;
import com.chd.hrp.ass.entity.card.AssCardInitSpecial;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;
import com.chd.hrp.ass.entity.maintain.AssMaintainRec;
import com.chd.hrp.ass.entity.measure.AssMeasureRec;
import com.chd.hrp.ass.entity.repair.AssRepairApply;
import com.chd.hrp.ass.entity.repair.AssRepairRec;
import com.chd.hrp.ass.service.card.AssCardInitBasicService;
import com.chd.hrp.ass.service.card.AssCardInitGeneralService;
import com.chd.hrp.ass.service.card.AssCardInitHouseService;
import com.chd.hrp.ass.service.card.AssCardInitInassetsService;
import com.chd.hrp.ass.service.card.AssCardInitLandService;
import com.chd.hrp.ass.service.card.AssCardInitOtherService;
import com.chd.hrp.ass.service.card.AssCardInitSpecialService;
import com.chd.hrp.ass.service.dict.AssCardSetQueService;
import com.chd.hrp.ass.service.dict.AssCardSetTabService;
import com.chd.hrp.ass.service.dict.AssCardSetViewService;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.chd.hrp.ass.service.inspection.AssInspectionDetailService;
import com.chd.hrp.ass.service.inspection.AssInspectionMainService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecItemService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecService;
import com.chd.hrp.ass.service.measure.AssMeasureRecDetailService;
import com.chd.hrp.ass.service.measure.AssMeasureRecService;
import com.chd.hrp.ass.service.repair.AssRepairApplyService;
import com.chd.hrp.ass.service.repair.AssRepairRecService;

/**
 * 
 * @Description: 050802 资产卡片维护_一般设备
 * @Table: ASS_CARD_GENERAL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssCardInitController extends BaseController {       

	private static Logger logger = Logger.getLogger(AssCardInitController.class);

	// 引入Service服务
	@Resource(name = "assCardInitGeneralService")
	private final AssCardInitGeneralService assCardInitGeneralService = null;

	@Resource(name = "assCardInitHouseService")
	private final AssCardInitHouseService assCardInitHouseService = null;

	@Resource(name = "assCardInitOtherService")
	private final AssCardInitOtherService assCardInitOtherService = null;

	@Resource(name = "assCardInitSpecialService")
	private final AssCardInitSpecialService assCardInitSpecialService = null;

	@Resource(name = "assCardInitInassetsService")
	private final AssCardInitInassetsService assCardInitInassetsService = null;

	@Resource(name = "assCardInitLandService")
	private final AssCardInitLandService assCardInitLandService = null;

	@Resource(name = "assCardSetViewService")
	private final AssCardSetViewService assCardSetViewService = null;

	@Resource(name = "assCardSetTabService")
	private final AssCardSetTabService assCardSetTabService = null;
	
	@Resource(name = "assCardSetQueService")
	private final AssCardSetQueService assCardSetQueService = null;
	
	@Resource(name = "assCardInitBasicService")
	private final AssCardInitBasicService assCardInitBasicService = null;
	
	@Resource(name = "assNoDictService")
	private final AssNoDictService assNoDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assCardInitMainPage", method = RequestMethod.GET)
	public String assCardInitMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/asscardinit/assCardInitMain";
	}

	@RequestMapping(value = "/hrp/ass/asscardinit/assCardInitAddPage", method = RequestMethod.GET)
	public String assCardInitAddPage(Model mode, String ass_nature) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		return "hrp/ass/asscardinit/assCardInitAdd";
	}

	/**
	 * @Description 资金来源跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assResourceInitMainPage", method = RequestMethod.GET)
	public String assResourceInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/resourceinit/assResourceInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/resourceinit/assResourceInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/resourceinit/assResourceInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/resourceinit/assResourceInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/resourceinit/assResourceInitInassetsMain";
		} else if (ass_nature.equals("06")) {
			return "hrp/ass/asscardinit/resourceinit/assResourceInitLandMain";
		} else {
			return "";
		}
	}

	/**
	 * @Description 使用科室跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assShareDeptInitMainPage", method = RequestMethod.GET)
	public String assShareDeptInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/sharedeptinit/assShareDeptInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/sharedeptinit/assShareDeptInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/sharedeptinit/assShareDeptInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/sharedeptinit/assShareDeptInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/sharedeptinit/assShareDeptInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/sharedeptinit/assShareDeptInitLandMain";
		} else{
			return "";
		}
	}


	/**
	 * @Description 折旧记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assDepreAccInitMainPage", method = RequestMethod.GET)
	public String assDepreAccInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("ass_year", yearMonth.substring(0, 4));
		mode.addAttribute("ass_month", yearMonth.substring(4, 6));
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/depreinit/acc/assDepreAccInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/depreinit/acc/assDepreAccInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/depreinit/acc/assDepreAccInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/depreinit/acc/assDepreAccInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/depreinit/acc/assDepreAccInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/depreinit/acc/assDepreAccInitLandMain";
		} else{
			return "";
		}
	}
	
	
	/**
	 * @Description 分摊记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assDepreManageInitMainPage", method = RequestMethod.GET)
	public String assDepreManageInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("ass_year", yearMonth.substring(0, 4));
		mode.addAttribute("ass_month", yearMonth.substring(4, 6));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/depreinit/manager/assDepreManageInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/depreinit/manager/assDepreManageInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/depreinit/manager/assDepreManageInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/depreinit/manager/assDepreManageInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/depreinit/manager/assDepreManageInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/depreinit/manager/assDepreManageInitLandMain";
		} else{
			return "";
		}
	}
	
	/**
	 * @Description 资产文档跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assFileInitMainPage", method = RequestMethod.GET)
	public String assFileInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/fileinit/assFileInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/fileinit/assFileInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/fileinit/assFileInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/fileinit/assFileInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/fileinit/assFileInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/fileinit/assFileInitLandMain";
		} else{
			return "";
		}
	}
	
	/**
	 * @Description 资产照片跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assPhotoInitMainPage", method = RequestMethod.GET)
	public String assPhotoInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/photoinit/assPhotoInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/photoinit/assPhotoInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/photoinit/assPhotoInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/photoinit/assPhotoInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/photoinit/assPhotoInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/photoinit/assPhotoInitLandMain";
		} else{
			return "";
		}
	}
	
	
	/**
	 * @Description 资产附件跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assAccessoryInitMainPage", method = RequestMethod.GET)
	public String assAccessoryInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/accessoryinit/assAccessoryInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/accessoryinit/assAccessoryInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/accessoryinit/assAccessoryInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/accessoryinit/assAccessoryInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/accessoryinit/assAccessoryInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/accessoryinit/assAccessoryInitLandMain";
		} else{
			return "";
		}
	}
	
	
	/**
	 * @Description 资产分期付款跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assStageInitMainPage", method = RequestMethod.GET)
	public String assStageInitMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/paystageinit/assPayStageInitHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/paystageinit/assPayStageInitSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/paystageinit/assPayStageInitGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/paystageinit/assPayStageInitOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/paystageinit/assPayStageInitInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/paystageinit/assPayStageInitLandMain";
		} else{
			return "";
		}
	}
	
	/**
	 * @Description 原值变动跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assCardChangePage", method = RequestMethod.GET)
	public String assCardChangePage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscardinit/changeinit/assChangeHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscardinit/changeinit/assChangeSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscardinit/changeinit/assChangeGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscardinit/changeinit/assChangeOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscardinit/changeinit/assChangeInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscardinit/changeinit/assChangeLandMain";
		} else{
			return "";
		}
	}

	
	/**
	 * @Description 维修记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assRepairRecMainPage", method = RequestMethod.GET)
	public String assRepairRecMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscardinit/repairrecinit/main";
	}
	//维修记录Servcie
	@Resource(name = "assRepairRecService")
	private final AssRepairRecService assRepairRecService = null;
	@Resource(name = "assRepairApplyService")
	private final AssRepairApplyService assRepairApplyService = null;
	//查询维修记录
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssRepairRecByCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String assRepairRec = assRepairRecService.query(getPage(mapVo));

		return JSONObject.parseObject(assRepairRec);
	}
	
	@RequestMapping(value = "/hrp/ass/asscardinit/assRepairRecUpdatePage", method = RequestMethod.GET)
	public String assRepairRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//维修记录表
		AssRepairRec assRepairRec = new AssRepairRec();
				assRepairRec = assRepairRecService.queryByCode(mapVo);
		//申请
		AssRepairApply assRepairApply = new AssRepairApply();
		mapVo.put("apply_no", assRepairRec.getApply_no());
		assRepairApply = assRepairApplyService.queryByCode(mapVo);
		
		
		mode.addAttribute("group_id", assRepairApply.getGroup_id());
		mode.addAttribute("hos_id", assRepairApply.getHos_id());
		mode.addAttribute("copy_code", assRepairApply.getCopy_code());
		mode.addAttribute("apply_no", assRepairApply.getApply_no());
		mode.addAttribute("ass_year", assRepairApply.getAss_year());
		mode.addAttribute("ass_month", assRepairApply.getAss_month());
		mode.addAttribute("ass_nature", assRepairApply.getAss_nature());
		mode.addAttribute("repair_dept_id", assRepairApply.getRepair_dept_id());
		mode.addAttribute("repair_dept_no", assRepairApply.getRepair_dept_no());
		mode.addAttribute("ass_name", assRepairApply.getAss_name());
		mode.addAttribute("apply_emp", assRepairApply.getApply_emp());
		mode.addAttribute("apply_date", assRepairApply.getApply_date());
		mode.addAttribute("create_emp", assRepairApply.getCreate_emp());
		mode.addAttribute("create_date", DateUtil.dateToString(assRepairApply.getCreate_date(),"yyyy-MM-dd"));
		mode.addAttribute("audit_emp", assRepairApply.getAudit_emp());
		mode.addAttribute("audit_date", DateUtil.dateToString(assRepairApply.getAudit_date(),"yyyy-MM-dd"));
		mode.addAttribute("sharp_degree", assRepairApply.getSharp_degree());
		mode.addAttribute("rep_phone", assRepairApply.getRep_phone());
		mode.addAttribute("rep_team_code", assRepairApply.getRep_team_code());
		mode.addAttribute("note", assRepairApply.getNote());
		mode.addAttribute("ass_card_no", assRepairApply.getAss_card_no());
		
		mode.addAttribute("is_inner",assRepairRec.getIs_inner());
		mode.addAttribute("repair_date",DateUtil.dateToString(assRepairRec.getRepair_date(),"yyyy-MM-dd"));
		mode.addAttribute("repair_engr",assRepairRec.getRepair_engr());
		mode.addAttribute("trouble_level",assRepairRec.getTrouble_level());
		mode.addAttribute("repair_level",assRepairRec.getRepair_level());
		mode.addAttribute("repair_hours",assRepairRec.getRepair_hours());
		mode.addAttribute("is_contract",assRepairRec.getIs_contract());
		mode.addAttribute("contract_no",assRepairRec.getContract_no());
		mode.addAttribute("repair_money",assRepairRec.getRepair_money());
		mode.addAttribute("other_money",assRepairRec.getOther_money());
		mode.addAttribute("repair_desc",assRepairRec.getRepair_desc());
		mode.addAttribute("repair_rec_no", assRepairRec.getRepair_rec_no());
		mode.addAttribute("state", assRepairRec.getState());
		return "hrp/ass/asscardinit/repairrecinit/view";
	}
	/**
	 * @Description 
	 * 查询数据 tabledesc
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssRepairRecDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssRepairRecDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String assRepairRec = assRepairRecService.queryAssRepairRecDetail(mapVo);
		
		return JSONObject.parseObject(assRepairRec);
		
	}
	
	
	/**
	 * @Description 保养记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assMaintainRecMainPage", method = RequestMethod.GET)
	public String assMaintainRecMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		return "hrp/ass/asscardinit/assmaintainrec/assMaintainRecMain";
	}
	  /**
     * 查询保养记录
     */
	// 引入Service服务
		@Resource(name = "assMaintainRecService")
		private final AssMaintainRecService assMaintainRecService = null;

		@Resource(name = "assMaintainRecItemService")
		private final AssMaintainRecItemService assMaintainRecItemService = null;
		
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssMaintainRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assMaintainRec = assMaintainRecService.queryAssMaintainRecCard(getPage(mapVo));

		return JSONObject.parseObject(assMaintainRec);
	}
	/**
	 * hrp/ass/asscard/assMaintainRecUpdatePage
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assMaintainRecUpdatePage", method = RequestMethod.GET)
	public String assMaintainRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMaintainRec assMaintainRec = new AssMaintainRec();

		assMaintainRec = assMaintainRecService.queryAssMaintainRecByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assMaintainRec.getGroup_id());

		mode.addAttribute("hos_id", assMaintainRec.getHos_id());

		mode.addAttribute("copy_code", assMaintainRec.getCopy_code());

		mode.addAttribute("rec_id", assMaintainRec.getRec_id());

		mode.addAttribute("rec_no", assMaintainRec.getRec_no());

		mode.addAttribute("ass_year", assMaintainRec.getAss_year());

		mode.addAttribute("ass_month", assMaintainRec.getAss_month());

		mode.addAttribute("plan_id", assMaintainRec.getPlan_id());

		mode.addAttribute("ass_nature", assMaintainRec.getAss_nature());

		mode.addAttribute("fact_exec_emp", assMaintainRec.getFact_exec_emp());

		mode.addAttribute("fact_exec_date", sdf.format(assMaintainRec.getFact_exec_date()));

		mode.addAttribute("maintain_hours", assMaintainRec.getMaintain_hours());

		mode.addAttribute("maintain_money", assMaintainRec.getMaintain_money());

		mode.addAttribute("state", assMaintainRec.getState());

		mode.addAttribute("maintain_dept_id", assMaintainRec.getMaintain_dept_id());

		mode.addAttribute("maintain_dept_no", assMaintainRec.getMaintain_dept_no());

		mode.addAttribute("create_emp", assMaintainRec.getCreate_emp());

		mode.addAttribute("create_date", sdf.format(assMaintainRec.getCreate_date()));

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		if (assMaintainRec.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assMaintainRec.getAudit_emp());

		}

		if (assMaintainRec.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assMaintainRec.getAudit_date()));

		}

		if (assMaintainRec.getPlan_exec_date() == null) {

			mode.addAttribute("plan_exec_date", "");

		} else {

			mode.addAttribute("plan_exec_date", sdf.format(assMaintainRec.getPlan_exec_date()));

		}

		if (assMaintainRec.getMaintain_desc() == null) {

			mode.addAttribute("maintain_desc", "");

		} else {

			mode.addAttribute("maintain_desc", assMaintainRec.getMaintain_desc());

		}
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/asscardinit/assmaintainrec/assMaintainRecUpdate";
	}
	/**
	 * @Description 计量记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assMeasureRecMainPage", method = RequestMethod.GET)
	public String assMeasureRecMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		return "hrp/ass/asscardinit/assMeasureRec/assMeasureRecMain";
	}
	/**
	 * @Description 查询数据 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	// 引入Service服务
		@Resource(name = "assMeasureRecService")
		private final AssMeasureRecService assMeasureRecService = null;

		@Resource(name = "assMeasureRecDetailService")
		private final AssMeasureRecDetailService assMeasureRecDetailService = null;
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssMeasureRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasureRec(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String assMeasureRec = assMeasureRecService.queryAssMeasureRecByCard(getPage(mapVo));

		return JSONObject.parseObject(assMeasureRec);

	}
	/**
	 * @Description 更新跳转页面 051204 检测计量记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assMeasureRecUpdatePage", method = RequestMethod.GET)
	public String assMeasureRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		AssMeasureRec assMeasureRec = new AssMeasureRec();

		assMeasureRec = assMeasureRecService.queryAssMeasureRecByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assMeasureRec.getGroup_id());

		mode.addAttribute("hos_id", assMeasureRec.getHos_id());

		mode.addAttribute("copy_code", assMeasureRec.getCopy_code());

		mode.addAttribute("rec_id", assMeasureRec.getRec_id());

		mode.addAttribute("seq_no", assMeasureRec.getSeq_no());

		mode.addAttribute("plan_id", assMeasureRec.getPlan_id());

		mode.addAttribute("cert_no", assMeasureRec.getCert_no());

		mode.addAttribute("ass_nature", assMeasureRec.getAss_nature());

		mode.addAttribute("naturs_name", assMeasureRec.getNaturs_name());

		if (assMeasureRec.getOuter_measure_engr() == null) {

			mode.addAttribute("outer_measure_engr", "");

		} else {

			mode.addAttribute("outer_measure_engr", assMeasureRec.getOuter_measure_engr());

		}

		if (assMeasureRec.getInner_measure_emp() == null) {

			mode.addAttribute("inner_measure_emp", "");
		} else {

			mode.addAttribute("inner_measure_emp", assMeasureRec.getInner_measure_emp());

		}

		if (assMeasureRec.getDeal_emp() == null) {

			mode.addAttribute("deal_emp", "");
		} else {

			mode.addAttribute("deal_emp", assMeasureRec.getDeal_emp());

		}

		if (assMeasureRec.getPlan_measure_date() == null) {

			mode.addAttribute("plan_measure_date", "");
		} else {

			mode.addAttribute("plan_measure_date", sdf.format(assMeasureRec.getPlan_measure_date()));

		}

		if (assMeasureRec.getFact_measure_date() == null) {

			mode.addAttribute("fact_measure_date", "");
		} else {

			mode.addAttribute("fact_measure_date", sdf.format(assMeasureRec.getFact_measure_date()));

		}

		if (assMeasureRec.getPre_next_date() == null) {

			mode.addAttribute("pre_next_date", "");
		} else {

			mode.addAttribute("pre_next_date", sdf.format(assMeasureRec.getPre_next_date()));

		}

		if (assMeasureRec.getCreate_emp() == null) {

			mode.addAttribute("create_emp", "");
		} else {

			mode.addAttribute("create_emp", assMeasureRec.getCreate_emp());

		}

		if (assMeasureRec.getCreate_date() == null) {

			mode.addAttribute("create_date", "");
		} else {

			mode.addAttribute("create_date", sdf.format(assMeasureRec.getCreate_date()));

		}

		if (assMeasureRec.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");
		} else {

			mode.addAttribute("audit_emp", assMeasureRec.getAudit_emp());

		}

		if (assMeasureRec.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");
		} else {

			mode.addAttribute("audit_date", sdf.format(assMeasureRec.getAudit_date()));

		}

		mode.addAttribute("measure_hours", assMeasureRec.getMeasure_hours());

		mode.addAttribute("other_money", assMeasureRec.getOther_money());

		mode.addAttribute("measure_money", assMeasureRec.getMeasure_money());

		mode.addAttribute("measure_memo", assMeasureRec.getMeasure_memo());

		mode.addAttribute("measure_kind", assMeasureRec.getMeasure_kind());

		mode.addAttribute("inner_measure_dept_id", assMeasureRec.getInner_measure_dept_id());

		mode.addAttribute("inner_measure_dept_no", assMeasureRec.getInner_measure_dept_no());

		mode.addAttribute("inner_measure_dept_name", assMeasureRec.getInner_measure_dept_name());

		mode.addAttribute("outer_measure_org", assMeasureRec.getOuter_measure_org());

		mode.addAttribute("create_emp_name", assMeasureRec.getCreate_emp_name());

		mode.addAttribute("audit_emp_name", assMeasureRec.getAudit_emp_name());

		mode.addAttribute("deal_emp_name", assMeasureRec.getDeal_emp_name());

		mode.addAttribute("measure_type", assMeasureRec.getMeasure_type());

		mode.addAttribute("state", assMeasureRec.getState());

		mode.addAttribute("measure_result", assMeasureRec.getMeasure_result());

		mode.addAttribute("measure_idea", assMeasureRec.getMeasure_idea());

		return "hrp/ass/asscardinit/assMeasureRec/assMeasureRecUpdate";
	}



	/**
	 * @Description 资产转移跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assCardChargeMainPage", method = RequestMethod.GET)
	public String assCardChargeMainPage(Model mode, String ass_nature) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		return "hrp/ass/asscardinit/assCardChargeMain";
	}

	/**
	 * @Description 巡检信息跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assInspectionMainPage", method = RequestMethod.GET)
	public String assInspectionMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscardinit/assinspection/assInspectionMain";
	}


	// 引入Service服务
		@Resource(name = "assInspectionMainService")
		private final AssInspectionMainService assInspectionMainService = null;

		@Resource(name = "assInspectionDetailService")
		private final AssInspectionDetailService assInspectionDetailService = null;
	/**
	 * @Description 查询数据 巡检信息主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssInspectionMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInspectionMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		String assInspectionMain = assInspectionMainService.queryAssInspectionMainByCard(getPage(mapVo));

		return JSONObject.parseObject(assInspectionMain);

	}
	/**
	 * @Description 更新跳转巡检记录
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assInspectionUpdatePage", method = RequestMethod.GET)
	public String AssInspectionUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		AssInspectionMain assInspectionMain = new AssInspectionMain();

		assInspectionMain = assInspectionMainService.queryAssInspectionMainByCode(mapVo);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		mode.addAttribute("group_id", assInspectionMain.getGroup_id());

		mode.addAttribute("hos_id", assInspectionMain.getHos_id());

		mode.addAttribute("copy_code", assInspectionMain.getCopy_code());

		mode.addAttribute("ins_id", assInspectionMain.getIns_id());

		mode.addAttribute("ins_no", assInspectionMain.getIns_no());

		mode.addAttribute("ins_name", assInspectionMain.getIns_name());

		mode.addAttribute("ass_year", assInspectionMain.getAss_year());

		mode.addAttribute("ass_month", assInspectionMain.getAss_month());

		mode.addAttribute("ass_nature", assInspectionMain.getAss_nature());

		mode.addAttribute("dept_id", assInspectionMain.getDept_id());

		mode.addAttribute("dept_no", assInspectionMain.getDept_no());

		mode.addAttribute("create_emp", assInspectionMain.getCreate_emp());

		mode.addAttribute("create_emp_name", assInspectionMain.getCreate_emp_name());

		mode.addAttribute("create_date", sdf.format(assInspectionMain.getCreate_date()));

		if (assInspectionMain.getAudit_emp() == null) {

			mode.addAttribute("audit_emp", "");

		} else {

			mode.addAttribute("audit_emp", assInspectionMain.getAudit_emp());

		}

		if (assInspectionMain.getAudit_date() == null) {

			mode.addAttribute("audit_date", "");

		} else {

			mode.addAttribute("audit_date", sdf.format(assInspectionMain.getAudit_date()));

		}

		mode.addAttribute("state", assInspectionMain.getState());

		if (assInspectionMain.getNote() == null) {

			mode.addAttribute("note", "");

		} else {

			mode.addAttribute("note", assInspectionMain.getNote());

		}

		return "hrp/ass/asscardinit/assinspection/assInspectionUpdate";
	}
	/**
	 * @Description 添加数据 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/addAssCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCardInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCardGeneralJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String ass_nature = (String) mapVo.get("ass_nature");
		
		if (ass_nature.equals("01")) {
			assCardGeneralJson = assCardInitHouseService.add(mapVo);
		} else if (ass_nature.equals("02")) {
			assCardGeneralJson = assCardInitSpecialService.add(mapVo);
		} else if (ass_nature.equals("03")) {
			assCardGeneralJson = assCardInitGeneralService.add(mapVo);
		} else if (ass_nature.equals("04")) {
			assCardGeneralJson = assCardInitOtherService.add(mapVo);
		} else if (ass_nature.equals("05")) {
			assCardGeneralJson = assCardInitInassetsService.add(mapVo);
		} else if (ass_nature.equals("06")) {
			assCardGeneralJson = assCardInitLandService.add(mapVo);
		}

		try {
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardGeneralJson);

	}

	// 查询所有列
	@RequestMapping(value = "/hrp/ass/asscardinit/getAssCardInitTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardInitTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetViewService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	// 获取动态查询条件
	@RequestMapping(value = "/hrp/ass/asscardinit/getAssCardInitQue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardInitQue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetQueService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	
	
	//获取卡片编码
	@RequestMapping(value = "/hrp/ass/asscardinit/getAssCardInitNo", method = RequestMethod.POST)
	@ResponseBody
	public String getAssCardInitNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		
		String result = assCardInitBasicService.getAssCardNo(mapVo);

		return result;
	}
	
	//通过所选资产获取资产的信息
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssDictByID", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDictByID(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("ass_id") == null || mapVo.get("ass_id").equals("")){
			mapVo.put("ass_id","0");
			mapVo.put("ass_ino","0");
		}

		AssNoDict assNoDict = assNoDictService.queryAssNoDictByCode(mapVo);
		JSONObject json = new JSONObject();	
		
		return json.toJSONString(assNoDict);
	}
	
	
	

	// 动态显示卡片表头
	@RequestMapping(value = "/hrp/ass/asscardinit/getAssCardInitGridTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardInitGridTitle(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_init_tab_view", 1);
		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetViewService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}

	// 动态显示卡片页签
	@RequestMapping(value = "/hrp/ass/asscardinit/getAssCardInitTab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardInitTab(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_view", 1);
		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetTabService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	

	/**
	 * @Description 更新跳转页面 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assCardInitUpdatePage", method = RequestMethod.GET)
	public String assCardInitUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		if (!mapVo.containsKey("ass_nature") || mapVo.get("ass_nature") == null ) {
			mapVo.put("ass_nature", "01");
		}
		JSONObject json = new JSONObject();
		json.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		SerializerFeature serializerFeature = SerializerFeature.WriteDateUseDateFormat;
		 ValueFilter filter = new ValueFilter() {
	            @Override
	            public Object process(Object object, String name, Object value) {
	                if(value instanceof BigDecimal || value instanceof Double || value instanceof Float){
	                    return new BigDecimal(value.toString());
	                }
	                return value;
	            }
	     };
		
		String ass_nature = (String) mapVo.get("ass_nature");
		AssCardInitSpecial assCardSpecial = null;
		AssCardInitGeneral assCardGeneral = null;
		AssCardInitHouse assCardHouse = null;
		AssCardInitOther assCardOther = null;
		AssCardInitInassets assCardInassets = null;
		AssCardInitLand assCardLand = null;
		if (ass_nature.equals("01")) {
			
			assCardHouse = assCardInitHouseService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardHouse.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardHouse,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("02")) {
			
			assCardSpecial = assCardInitSpecialService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardSpecial.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardSpecial,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("03")) {
			
			assCardGeneral = assCardInitGeneralService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardGeneral.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardGeneral,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("04")) {
			
			assCardOther = assCardInitOtherService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardOther.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardOther,filter,new SerializerFeature[0]));
		} else if (ass_nature.equals("05")) {
			
			assCardInassets = assCardInitInassetsService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardInassets.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardInassets,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("06")) {
			
			assCardLand = assCardInitLandService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardLand.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardLand,filter,new SerializerFeature[0]));
			
		}
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		
		return "hrp/ass/asscardinit/assCardInitUpdate";
	}
	

	/**
	 * @Description 更新数据 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/updateAssCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCardInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");

		String assCardGeneralJson = "";
		try {
			if (ass_nature.equals("01")) {
				assCardGeneralJson = assCardInitHouseService.update(mapVo);
			} else if (ass_nature.equals("02")) {
				assCardGeneralJson = assCardInitSpecialService.update(mapVo);
			} else if (ass_nature.equals("03")) {
				assCardGeneralJson = assCardInitGeneralService.update(mapVo);
			} else if (ass_nature.equals("04")) {
				assCardGeneralJson = assCardInitOtherService.update(mapVo);
			} else if (ass_nature.equals("05")){
				assCardGeneralJson = assCardInitInassetsService.update(mapVo);
			} else if (ass_nature.equals("06")){
				assCardGeneralJson = assCardInitLandService.update(mapVo);
			}
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardGeneralJson);
	}

	/**
	 * @Description 删除数据 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAssCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCardInit(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);

			listVo.add(mapVo);

		}

		String assCardGeneralJson = "";
		try {
			if (ass_nature.equals("01")) {
				assCardGeneralJson = assCardInitHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assCardGeneralJson = assCardInitSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assCardGeneralJson = assCardInitGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assCardGeneralJson = assCardInitOtherService.deleteBatch(listVo);
			} else if(ass_nature.equals("05")){
				assCardGeneralJson = assCardInitInassetsService.deleteBatch(listVo);
			}else if(ass_nature.equals("06")){
				assCardGeneralJson = assCardInitLandService.deleteBatch(listVo);
			}
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}
	
	/**
	 * @Description 根据资产性质删除全部数据 050802 资产卡片维护
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/deleteAllAssCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAllAssCardInit(@RequestParam(value = "ass_nature") String ass_nature,
			Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("is_del_rel", 1);//是否删除关联

		String assCardGeneralJson = "";
		try {
			if (ass_nature.equals("01")) {
				assCardGeneralJson = assCardInitHouseService.delete(mapVo);
			} else if (ass_nature.equals("02")) {
				assCardGeneralJson = assCardInitSpecialService.delete(mapVo);
			} else if (ass_nature.equals("03")) {
				assCardGeneralJson = assCardInitGeneralService.delete(mapVo);
			} else if (ass_nature.equals("04")) {
				assCardGeneralJson = assCardInitOtherService.delete(mapVo);
			} else if(ass_nature.equals("05")){
				assCardGeneralJson = assCardInitInassetsService.delete(mapVo);
			}else if(ass_nature.equals("06")){
				assCardGeneralJson = assCardInitLandService.delete(mapVo);
			}
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assCardGeneralJson);

	}

	/**
	 * @Description 查询数据 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/queryAssCardInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCardInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assCard = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assCard = assCardInitHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assCard = assCardInitSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assCard = assCardInitGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assCard = assCardInitOtherService.query(getPage(mapVo));
		} else if(ass_nature.equals("05")){
			assCard = assCardInitInassetsService.query(getPage(mapVo));
		} else if(ass_nature.equals("06")){
			assCard = assCardInitLandService.query(getPage(mapVo));
		}

		return JSONObject.parseObject(assCard);

	}
	
	/**
	 * 导入期初卡片
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assAssCardInitImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assAssCardInitImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			String result = assCardInitBasicService.importAssCardInitData(mapVo);
			
			return result;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	/**
	 * 导入期初资金来源
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assAssResourceInitImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assAssResourceInitImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			String result = assCardInitBasicService.importAssResourceInitData(mapVo);
			
			return result;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	/**
	 * 导入期初折旧记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assAssDepreInitImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assAssDepreInitImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			String result = assCardInitBasicService.importAssDepreInitData(mapVo);
			
			return result;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	/**
	 * 导入期初分摊记录
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assAssManageDepreInitImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assAssManageDepreInitImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			String result = assCardInitBasicService.importAssManageDepreInitData(mapVo);
			
			return result;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	/**
	 * 导入期初分期付款
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscardinit/assAssPayInitImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String assAssPayInitImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			mapVo.put("group_id", SessionManager.getGroupId());

			mapVo.put("hos_id", SessionManager.getHosId());

			mapVo.put("copy_code", SessionManager.getCopyCode());

			mapVo.put("user_id", SessionManager.getUserId());
			
			String result = assCardInitBasicService.importAssPayInitData(mapVo);
			
			return result;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	
	@RequestMapping(value = "/hrp/ass/asscardinit/assAssCardInitAccount", method = RequestMethod.POST)
	@ResponseBody
	public String assAssCardInitAccount(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		try {
			
			String result = assCardInitBasicService.initAccount(mapVo);
			
			return result;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}

}
