/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.card;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.ftp.FtpUtil;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;
import com.chd.hrp.ass.entity.maintain.AssMaintainRec;
import com.chd.hrp.ass.entity.measure.AssMeasureRec;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.ass.service.card.AssCardHouseService;
import com.chd.hrp.ass.service.card.AssCardInassetsService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.ass.service.card.AssCardOtherService;
import com.chd.hrp.ass.service.card.AssCardSpecialService;
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
import com.chd.hrp.ass.service.repair.repreportcentre.AssRepReportCentreService;

/**
 * 
 * @Description: 050802 资产卡片维护_一般设备
 * @Table: ASS_CARD_GENERAL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssCardController extends BaseController {

	private static Logger logger = Logger.getLogger(AssCardController.class);

	// 引入Service服务
	@Resource(name = "assCardGeneralService")
	private final AssCardGeneralService assCardGeneralService = null;

	@Resource(name = "assCardHouseService")
	private final AssCardHouseService assCardHouseService = null;

	@Resource(name = "assCardOtherService")
	private final AssCardOtherService assCardOtherService = null;

	@Resource(name = "assCardSpecialService")
	private final AssCardSpecialService assCardSpecialService = null;

	@Resource(name = "assCardInassetsService")
	private final AssCardInassetsService assCardInassetsService = null;

	@Resource(name = "assCardLandService")
	private final AssCardLandService assCardLandService = null;

	@Resource(name = "assCardSetViewService")
	private final AssCardSetViewService assCardSetViewService = null;

	@Resource(name = "assCardSetTabService")
	private final AssCardSetTabService assCardSetTabService = null;
	
	@Resource(name = "assCardSetQueService")
	private final AssCardSetQueService assCardSetQueService = null;
	
	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;
	
	@Resource(name = "assNoDictService")
	private final AssNoDictService assNoDictService = null;
	
	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assCardMainPage", method = RequestMethod.GET)
	public String assCardMainPage(Model mode) throws Exception {

		mode.addAttribute("ass_05074",MyConfig.getSysPara("05074"));
		mode.addAttribute("ass_05075",MyConfig.getSysPara("05075"));
		mode.addAttribute("ass_05076",MyConfig.getSysPara("05076"));
		mode.addAttribute("ass_05077",MyConfig.getSysPara("05077"));
		mode.addAttribute("ass_05078",MyConfig.getSysPara("05078"));
		mode.addAttribute("ass_05079",MyConfig.getSysPara("05079"));
		mode.addAttribute("ass_05100",MyConfig.getSysPara("05100"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		return "hrp/ass/asscard/assCardMain";
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/assCardUpdateBatchPage", method = RequestMethod.GET)
	public String assCardUpdateBatchPage(@RequestParam Map<String, Object> map,Model mode) throws Exception {
		mode.addAttribute("paramVo", map.get("paramVo"));
		mode.addAttribute("ass_nature", map.get("ass_nature"));
		return "hrp/ass/asscard/assCardBatchUpdate";
	}

	@RequestMapping(value = "/hrp/ass/asscard/assInCardAddPage", method = RequestMethod.GET)
	public String assInCardAddPage(Model mode, String ass_nature,String in_type) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("in_type", in_type);
		return "hrp/ass/asscard/assInCardAdd";
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscard/assCardAddPage", method = RequestMethod.GET)
	public String assCardAddPage(Model mode, String ass_nature) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		return "hrp/ass/asscard/assCardAdd";
	}

	/**
	 * @Description 资金来源跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assResourceMainPage", method = RequestMethod.GET)
	public String assResourceMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/resource/assResourceHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/resource/assResourceSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/resource/assResourceGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/resource/assResourceOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/resource/assResourceInassetsMain";
		} else if (ass_nature.equals("06")) {
			return "hrp/ass/asscard/resource/assResourceLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assShareDeptMainPage", method = RequestMethod.GET)
	public String assShareDeptMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/sharedept/assShareDeptHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/sharedept/assShareDeptSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/sharedept/assShareDeptGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/sharedept/assShareDeptOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/sharedept/assShareDeptInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/sharedept/assShareDeptLandMain";
		} else{
			return "";
		}
	}
	
	/**
	 * @Description 使用科室历史纪录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assShareDeptRMainPage", method = RequestMethod.GET)
	public String assShareDeptRMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("ass_year", yearMonth.substring(0, 4));
		mode.addAttribute("ass_month", yearMonth.substring(4, 6));
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/sharedept/assShareDeptRHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/sharedept/assShareDeptRSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/sharedept/assShareDeptRGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/sharedept/assShareDeptROtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/sharedept/assShareDeptRInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/sharedept/assShareDeptRLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assDepreAccMainPage", method = RequestMethod.GET)
	public String assDepreAccMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("ass_year", yearMonth.substring(0, 4));
		mode.addAttribute("ass_month", yearMonth.substring(4, 6));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/depre/acc/assDepreAccHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/depre/acc/assDepreAccSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/depre/acc/assDepreAccGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/depre/acc/assDepreAccOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/depre/acc/assDepreAccInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/depre/acc/assDepreAccLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assDepreManageMainPage", method = RequestMethod.GET)
	public String assDepreManageMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		String yearMonth = MyConfig.getCurAccYearMonth();
		mode.addAttribute("ass_year", yearMonth.substring(0, 4));
		mode.addAttribute("ass_month", yearMonth.substring(4, 6));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/depre/manager/assDepreManageHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/depre/manager/assDepreManageSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/depre/manager/assDepreManageGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/depre/manager/assDepreManageOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/depre/manager/assDepreManageInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/depre/manager/assDepreManageLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assFileMainPage", method = RequestMethod.GET)
	public String assFileMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/file/assFileHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/file/assFileSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/file/assFileGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/file/assFileOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/file/assFileInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/file/assFileLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assPhotoMainPage", method = RequestMethod.GET)
	public String assPhotoMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/photo/assPhotoHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/photo/assPhotoSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/photo/assPhotoGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/photo/assPhotoOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/photo/assPhotoInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/photo/assPhotoLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assAccessoryMainPage", method = RequestMethod.GET)
	public String assAccessoryMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/accessory/assAccessoryHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/accessory/assAccessorySpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/accessory/assAccessoryGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/accessory/assAccessoryOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/accessory/assAccessoryInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/accessory/assAccessoryLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assStageMainPage", method = RequestMethod.GET)
	public String assStageMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/paystage/assPayStageHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/paystage/assPayStageSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/paystage/assPayStageGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/paystage/assPayStageOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/paystage/assPayStageInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/paystage/assPayStageLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assCardChangePage", method = RequestMethod.GET)
	public String assCardChangePage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		
		if (ass_nature.equals("01")) {
			return "hrp/ass/asscard/change/assChangeHouseMain";
		} else if (ass_nature.equals("02")) {
			return "hrp/ass/asscard/change/assChangeSpecialMain";
		} else if (ass_nature.equals("03")) {
			return "hrp/ass/asscard/change/assChangeGeneralMain";
		} else if (ass_nature.equals("04")) {
			return "hrp/ass/asscard/change/assChangeOtherMain";
		} else if (ass_nature.equals("05")) {
			return "hrp/ass/asscard/change/assChangeInassetsMain";
		} else if (ass_nature.equals("06")){
			return "hrp/ass/asscard/change/assChangeLandMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/assRepairRecMainPage", method = RequestMethod.GET)
	public String assRepairRecMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/repairrec/main";
	}
	
	
	//维修记录Servcie
	@Resource(name="assRepReportCentreService")
	private final AssRepReportCentreService assRepReportCentreService  = null ;
	
	
	//查询维修记录
	@RequestMapping(value = "/hrp/ass/asscard/queryAssRepairRecByCard", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssRepairRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String AssRepReportCentreCenter = assRepReportCentreService.queryAssRepReportCentreCenter(mapVo);
		return AssRepReportCentreCenter;
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscard/assRepairRecUpdatePage", method = RequestMethod.GET)
	public String assRepairRecUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		return "hrp/ass/asscard/repairrec/view";
	}
	
	
	
	
	/**
	 * @Description 保养记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assMaintainRecMainPage", method = RequestMethod.GET)
	public String assMaintainRecMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/assmaintainrec/assMaintainRecMain";
	}
    /**
     * 查询保养记录
     */
	// 引入Service服务
		@Resource(name = "assMaintainRecService")
		private final AssMaintainRecService assMaintainRecService = null;

		@Resource(name = "assMaintainRecItemService")
		private final AssMaintainRecItemService assMaintainRecItemService = null;
		
	@RequestMapping(value = "/hrp/ass/asscard/queryAssMaintainRec", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/ass/asscard/assMaintainRecUpdatePage", method = RequestMethod.GET)
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

		return "hrp/ass/asscard/assmaintainrec/assMaintainRecUpdate";
	}
	/**
	 * @Description 计量记录跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assMeasureRecMainPage", method = RequestMethod.GET)
	public String assMeasureRecMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/assMeasureRec/assMeasureRecMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/queryAssMeasureRec", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/ass/asscard/assMeasureRecUpdatePage", method = RequestMethod.GET)
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

		return "hrp/ass/asscard/assMeasureRec/assMeasureRecUpdate";
	}

	/**
	 * @Description 资产转移跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assCardChangeMainPage", method = RequestMethod.GET)
	public String assCardChangeMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/assCardChargeMain";
	}

	/**
	 * @Description 巡检信息跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/assInspectionMainPage", method = RequestMethod.GET)
	public String assInspectionMainPage(Model mode, String ass_nature, String ass_card_no) throws Exception {
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("ass_card_no", ass_card_no);
		return "hrp/ass/asscard/assinspection/assInspectionMain";
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
	@RequestMapping(value = "/hrp/ass/asscard/queryAssInspectionMain", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/ass/asscard/assInspectionUpdatePage", method = RequestMethod.GET)
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

		return "hrp/ass/asscard/assinspection/assInspectionUpdate";
	}
	/**
	 * @Description 添加数据 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/addAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssCard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assCardGeneralJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String ass_nature = (String) mapVo.get("ass_nature");
		
		if (ass_nature.equals("01")) {
			String yearmonth =  mapVo.get("in_date").toString().substring(0, 4) + mapVo.get("in_date").toString().substring(5, 7);
			//启动系统时间
			Map<String, Object> start = SessionManager.getModStartMonth();
			
			String startyearmonth = (String) start.get(SessionManager.getModCode());
			
			int result = startyearmonth.compareTo(yearmonth);
			
			
			if(result > 0 ){
				
				return JSONObject.parseObject("{\"warn\":\"入账日期"+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
				
			}  
			
			String curYearMonth = MyConfig.getCurAccYearMonth();
			if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
				return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
			}
			
			assCardGeneralJson = assCardHouseService.add(mapVo);
		} else if (ass_nature.equals("02")) {
			assCardGeneralJson = assCardSpecialService.add(mapVo);
		} else if (ass_nature.equals("03")) {
			assCardGeneralJson = assCardGeneralService.add(mapVo);
		} else if (ass_nature.equals("04")) {
			assCardGeneralJson = assCardOtherService.add(mapVo);
		} else if (ass_nature.equals("05")) {
			assCardGeneralJson = assCardInassetsService.add(mapVo);
		} else if (ass_nature.equals("06")) {
			
			String yearmonth =  mapVo.get("in_date").toString().substring(0, 4) + mapVo.get("in_date").toString().substring(5, 7);
			//启动系统时间
			Map<String, Object> start = SessionManager.getModStartMonth();
			
			String startyearmonth = (String) start.get(SessionManager.getModCode());
			
			int result = startyearmonth.compareTo(yearmonth);
			
			
			if(result > 0 ){
				
				return JSONObject.parseObject("{\"warn\":\"入账日期"+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
				
			}  
			
			String curYearMonth = MyConfig.getCurAccYearMonth();
			if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
				return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
			}
			
			assCardGeneralJson = assCardLandService.add(mapVo);
		}

		try {
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assCardGeneralJson);

	}

	// 查询所有列
	@RequestMapping(value = "/hrp/ass/asscard/getAssCardTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardTitle(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetViewService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	// 获取动态查询条件
	@RequestMapping(value = "/hrp/ass/asscard/getAssCardQue", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardQue(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetQueService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	
	
	
	//获取卡片编码
	@RequestMapping(value = "/hrp/ass/asscard/getAssCardNo", method = RequestMethod.POST)
	@ResponseBody
	public String getAssCardNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		
		String result = assCardBasicService.getAssCardNo(mapVo);

		return result;
	}
	
	//通过所选资产获取资产的信息
	@RequestMapping(value = "/hrp/ass/asscard/queryAssDictByID", method = RequestMethod.POST)
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
	@RequestMapping(value = "/hrp/ass/asscard/getAssCardGridTitle", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardGridTitle(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_tab_view", 1);
		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetViewService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}

	// 动态显示卡片页签
	@RequestMapping(value = "/hrp/ass/asscard/getAssCardTab", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAssCardTab(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_view", 1);
		mapVo.put("ass_naturs", mapVo.get("ass_nature"));
		String result = assCardSetTabService.query(getPage(mapVo));

		return JSONObject.parseObject(result);
	}
	

	
	@RequestMapping(value = "/hrp/ass/asscard/assCardUpdatePage", method = RequestMethod.GET)
	public String assCardUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		AssCardSpecial assCardSpecial = null;
		AssCardGeneral assCardGeneral = null;
		AssCardHouse assCardHouse = null;
		AssCardOther assCardOther = null;
		AssCardInassets assCardInassets = null;
		AssCardLand assCardLand = null;
		if (ass_nature.equals("01")) {
			assCardHouse = assCardHouseService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardHouse.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardHouse,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("02")) {
			assCardSpecial = assCardSpecialService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardSpecial.getAss_card_no());
			mode.addAttribute("bar_code",assCardSpecial.getBar_code());
			mode.addAttribute("bar_url",assCardSpecial.getBar_url());
			mode.addAttribute("card_data", json.toJSONString(assCardSpecial,filter,new SerializerFeature[0]));
			
			
		} else if (ass_nature.equals("03")) {
			assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardGeneral.getAss_card_no());
			mode.addAttribute("bar_code",assCardGeneral.getBar_code());
			mode.addAttribute("bar_url",assCardGeneral.getBar_url());
			mode.addAttribute("card_data", json.toJSONString(assCardGeneral,filter,new SerializerFeature[0]));
			
			
		} else if (ass_nature.equals("04")) {
			
			assCardOther = assCardOtherService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardOther.getAss_card_no());
			mode.addAttribute("bar_code",assCardOther.getBar_code());
			mode.addAttribute("bar_url",assCardOther.getBar_url());
			mode.addAttribute("card_data", json.toJSONString(assCardOther,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("05")) {
			
			assCardInassets = assCardInassetsService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardInassets.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardInassets,filter,new SerializerFeature[0]));
			
		} else if (ass_nature.equals("06")) {
			
			assCardLand = assCardLandService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardLand.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardLand,filter,new SerializerFeature[0]));
			
		}
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		
		mode.addAttribute("ass_05078",MyConfig.getSysPara("05078"));
		
		mode.addAttribute("ass_05074",MyConfig.getSysPara("05074"));
		mode.addAttribute("ass_05075",MyConfig.getSysPara("05075"));
		mode.addAttribute("ass_05076",MyConfig.getSysPara("05076"));
		mode.addAttribute("ass_05077",MyConfig.getSysPara("05077"));
		mode.addAttribute("ass_05078",MyConfig.getSysPara("05078"));
		mode.addAttribute("ass_05079",MyConfig.getSysPara("05079"));
		mode.addAttribute("ass_05100",MyConfig.getSysPara("05100"));
		
		return "hrp/ass/asscard/assCardUpdate";
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/assInCardUpdatePage", method = RequestMethod.GET)
	public String assInCardUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		AssCardSpecial assCardSpecial = null;
		AssCardGeneral assCardGeneral = null;
		AssCardHouse assCardHouse = null;
		AssCardOther assCardOther = null;
		AssCardInassets assCardInassets = null;
		AssCardLand assCardLand = null;
		if (ass_nature.equals("01")) {
			
			assCardHouse = assCardHouseService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardHouse.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardHouse,filter,new SerializerFeature[0]));
			mode.addAttribute("is_init",assCardHouse.getIs_init());
			
			
		} else if (ass_nature.equals("02")) {
			
			assCardSpecial = assCardSpecialService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardSpecial.getAss_card_no());
			mode.addAttribute("bar_code",assCardSpecial.getBar_code());
			mode.addAttribute("bar_url",assCardSpecial.getBar_url());
			mode.addAttribute("card_data", json.toJSONString(assCardSpecial,filter,new SerializerFeature[0]));
			mode.addAttribute("is_init",assCardSpecial.getIs_init());
			
		} else if (ass_nature.equals("03")) {
			
			assCardGeneral = assCardGeneralService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardGeneral.getAss_card_no());
			mode.addAttribute("bar_code",assCardGeneral.getBar_code());
			mode.addAttribute("bar_url",assCardGeneral.getBar_url());
			mode.addAttribute("card_data", json.toJSONString(assCardGeneral,filter,new SerializerFeature[0]));
			mode.addAttribute("is_init",assCardGeneral.getIs_init());
			
		} else if (ass_nature.equals("04")) {
			
			assCardOther = assCardOtherService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardOther.getAss_card_no());
			mode.addAttribute("bar_code",assCardOther.getBar_code());
			mode.addAttribute("bar_url",assCardOther.getBar_url());
			mode.addAttribute("card_data", json.toJSONString(assCardOther,filter,new SerializerFeature[0]));
			mode.addAttribute("is_init",assCardOther.getIs_init());
			
		} else if (ass_nature.equals("05")) {
			
			assCardInassets = assCardInassetsService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardInassets.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardInassets,filter,new SerializerFeature[0]));
			mode.addAttribute("is_init",assCardInassets.getIs_init());
			
		} else if (ass_nature.equals("06")) {
			
			assCardLand = assCardLandService.queryByCode(mapVo);
			mode.addAttribute("ass_card_no",assCardLand.getAss_card_no());
			mode.addAttribute("card_data", json.toJSONString(assCardLand,filter,new SerializerFeature[0]));
			mode.addAttribute("is_init",assCardLand.getIs_init());
			
		}
		mode.addAttribute("ass_nature", ass_nature);
		mode.addAttribute("group_id", SessionManager.getGroupId());
		mode.addAttribute("hos_id", SessionManager.getHosId());
		mode.addAttribute("copy_code", SessionManager.getCopyCode());
		mode.addAttribute("in_type",mapVo.get("in_type"));
		
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));		
		mode.addAttribute("ass_05076",MyConfig.getSysPara("05076"));
		mode.addAttribute("ass_05074",MyConfig.getSysPara("05074"));
		mode.addAttribute("ass_05075",MyConfig.getSysPara("05075"));
		mode.addAttribute("ass_05077",MyConfig.getSysPara("05077"));
		mode.addAttribute("ass_05078",MyConfig.getSysPara("05078"));
		mode.addAttribute("ass_05079",MyConfig.getSysPara("05079"));
		mode.addAttribute("ass_05100",MyConfig.getSysPara("05100"));
		
		return "hrp/ass/asscard/assInCardUpdate";
	}
	
	
	

	/**
	 * @Description 更新数据 050802 资产卡片维护_一般设备
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/asscard/updateAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssCard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String ass_nature = (String) mapVo.get("ass_nature");

		String assCardGeneralJson = "";
		try {
			if (ass_nature.equals("01")) {
				String yearmonth =  mapVo.get("in_date").toString().substring(0, 4) + mapVo.get("in_date").toString().substring(5, 7);
				//启动系统时间
				Map<String, Object> start = SessionManager.getModStartMonth();
				
				String startyearmonth = (String) start.get(SessionManager.getModCode());
				
				int result = startyearmonth.compareTo(yearmonth);
				
				
				if(result > 0 ){
					
					return JSONObject.parseObject("{\"warn\":\"入账日期"+ yearmonth + "月份在系统启动时间"+startyearmonth+"之前,不允许添加单据 \"}");
					
				} 
				
				String curYearMonth = MyConfig.getCurAccYearMonth();
				if(Integer.parseInt(yearmonth) < Integer.parseInt(curYearMonth)){
					return JSONObject.parseObject("{\"warn\":\""+ yearmonth + "已经结账 不允许添加单据 \"}");
				}
				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse.getUse_state() == 6 || assCardHouse.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				assCardGeneralJson = assCardHouseService.update(mapVo);
				
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial =assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial.getUse_state() == 6 || assCardSpecial.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				assCardGeneralJson = assCardSpecialService.update(mapVo);
				
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral.getUse_state() == 6 || assCardGeneral.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				assCardGeneralJson = assCardGeneralService.update(mapVo);
				
			} else if (ass_nature.equals("04")) {
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther.getUse_state() == 6 || assCardOther.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
				assCardGeneralJson = assCardOtherService.update(mapVo);
			} else if (ass_nature.equals("05")){
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets.getUse_state() == 6 || assCardInassets.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				
				assCardGeneralJson = assCardInassetsService.update(mapVo);
			} else if (ass_nature.equals("06")){
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand.getUse_state() == 6 || assCardLand.getUse_state() == 7){
					return JSONObject.parseObject("{\"warn\":\"卡片已被处置或待处置,不能保存! \"}");
				}
				assCardGeneralJson = assCardLandService.update(mapVo);
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
	@RequestMapping(value = "/hrp/ass/asscard/deleteAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCard(@RequestParam(value = "ParamVo") String paramVo, String ass_nature,
			Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		boolean flag = true;
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			
			if (ass_nature.equals("01")) {
				String str = assBaseService.isExistsDataByTable("ASS_CARD_HOUSE", ids[4]);

				if (Strings.isNotBlank(str)) {
					retErrot = "{\"error\":\"删除失败，选择的卡片被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}

				AssCardHouse assCardHouse = assCardHouseService.queryByCode(mapVo);
				if(assCardHouse.getUse_state() > 0){
					flag = false;
				}
				
			} else if (ass_nature.equals("02")) {
				AssCardSpecial assCardSpecial = assCardSpecialService.queryByCode(mapVo);
				if(assCardSpecial.getUse_state() > 0){
					flag = false;
				}
				
			} else if (ass_nature.equals("03")) {
				AssCardGeneral assCardGeneral = assCardGeneralService.queryByCode(mapVo);
				if(assCardGeneral.getUse_state() > 0){
					flag = false;
				}
				
			} else if (ass_nature.equals("04")) {
				
				AssCardOther assCardOther = assCardOtherService.queryByCode(mapVo);
				if(assCardOther.getUse_state() > 0){
					flag = false;
				}
			} else if(ass_nature.equals("05")){
				
				AssCardInassets assCardInassets = assCardInassetsService.queryByCode(mapVo);
				if(assCardInassets.getUse_state() > 0){
					flag = false;
				}
			}else if(ass_nature.equals("06")){
				String str = assBaseService.isExistsDataByTable("ASS_CARD_LAND", ids[4]);

				if (Strings.isNotBlank(str)) {
					retErrot = "{\"error\":\"删除失败，选择的卡片被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
					return JSONObject.parseObject(retErrot);
				}
				
				AssCardLand assCardLand = assCardLandService.queryByCode(mapVo);
				if(assCardLand.getUse_state() > 0){
					flag = false;
				}
			}

			listVo.add(mapVo);

		}
		
		if(!flag){
			return JSONObject.parseObject("{\"error\":\"卡片不是新建状态,不能删除! \"}");
		}

		String assCardGeneralJson = "";
		try {
			if (ass_nature.equals("01")) {
				assCardGeneralJson = assCardHouseService.deleteBatch(listVo);
			} else if (ass_nature.equals("02")) {
				assCardGeneralJson = assCardSpecialService.deleteBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assCardGeneralJson = assCardGeneralService.deleteBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assCardGeneralJson = assCardOtherService.deleteBatch(listVo);
			} else if(ass_nature.equals("05")){
				assCardGeneralJson = assCardInassetsService.deleteBatch(listVo);
			}else if(ass_nature.equals("06")){
				assCardGeneralJson = assCardLandService.deleteBatch(listVo);
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
	@RequestMapping(value = "/hrp/ass/asscard/queryAssCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCard(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("user_id", SessionManager.getUserId());

		String assCard = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			assCard = assCardHouseService.query(getPage(mapVo));
		} else if (ass_nature.equals("02")) {
			assCard = assCardSpecialService.query(getPage(mapVo));
		} else if (ass_nature.equals("03")) {
			assCard = assCardGeneralService.query(getPage(mapVo));
		} else if (ass_nature.equals("04")) {
			assCard = assCardOtherService.query(getPage(mapVo));
		} else if(ass_nature.equals("05")){
			assCard = assCardInassetsService.query(getPage(mapVo));
		} else if(ass_nature.equals("06")){
			assCard = assCardLandService.query(getPage(mapVo));
		}
		JSONObject json = JSONObject.parseObject(assCard);
		JSONArray jsonarray = JSONArray.parseArray(json.get("Rows").toString());
		for(int i = 0 ; i < jsonarray.size(); i ++){
			JSONObject job = jsonarray.getJSONObject(i); 
			if(job.get("ass_card_no").equals("合计")){
				job.put("disabledcheckbox", "true");
			}
		}
		json.put("Rows", jsonarray);
		
		

		return JSONObject.parseObject(json.toJSONString());
	}

	
	
	@RequestMapping(value = "/hrp/ass/asscard/resetBarCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetBarCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String result = assCardBasicService.resetBarCode(mapVo);
		return JSONObject.parseObject(result);
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/updateIsBarPrint", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateIsBarPrint(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String paramVo = mapVo.get("paramVo").toString();
		for (String id : paramVo.split(",")) {

			Map<String, Object> map= new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("is_bar_print", "1");
			map.put("ass_card_no", id);
			
			listVo.add(map);
		}
		
		
		String assCard = "";
		if (!mapVo.containsKey("ass_nature")) {
			mapVo.put("ass_nature", "01");
		}
		String ass_nature = (String) mapVo.get("ass_nature");
		if (ass_nature.equals("01")) {
			
		} else if (ass_nature.equals("02")) {
			assCard = assCardSpecialService.updateIsBarPrint(listVo);
		} else if (ass_nature.equals("03")) {
			assCard = assCardGeneralService.updateIsBarPrint(listVo);
		} else if (ass_nature.equals("04")) {
			assCard = assCardOtherService.updateIsBarPrint(listVo);
		} else if(ass_nature.equals("05")){
			
		} else if(ass_nature.equals("06")){
			
		}
		return JSONObject.parseObject(assCard);
	}
	
	
	@RequestMapping(value = "/hrp/ass/asscard/updateBatchCard", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchCard(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		String assCard = ""; 
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : map.get("paramVo").toString().split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_card_no", ids[3]);
			mapVo.put("simple_name", map.get("simple_name"));
			mapVo.put("man_code", map.get("man_code"));
			mapVo.put("contract_code", map.get("contract_code"));
			mapVo.put("file_number", map.get("file_number"));
			mapVo.put("ass_seq_no", map.get("ass_seq_no"));
			
			mapVo.put("fac_id", map.get("fac_id"));
			mapVo.put("fac_no", map.get("fac_no"));
			
			mapVo.put("ven_id", map.get("ven_id"));
			mapVo.put("ven_no", map.get("ven_no"));
			
			mapVo.put("accept_emp", map.get("accept_emp"));
			
			mapVo.put("service_date", map.get("service_date"));
			
			mapVo.put("common_name", map.get("common_name"));
			
			mapVo.put("reg_no", map.get("reg_no"));
			
			listVo.add(mapVo);
		}
		try {
			
			String ass_nature = (String) map.get("ass_nature");
			if (ass_nature.equals("01")) {
				
			} else if (ass_nature.equals("02")) {
				assCard = assCardSpecialService.updateBatch(listVo);
			} else if (ass_nature.equals("03")) {
				assCard = assCardGeneralService.updateBatch(listVo);
			} else if (ass_nature.equals("04")) {
				assCard = assCardOtherService.updateBatch(listVo);
			} else if(ass_nature.equals("05")){
				
			} else if(ass_nature.equals("06")){
				  
			}
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assCard);
	}
	
	@RequestMapping(value = "/hrp/ass/asscard/generalCardCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generalCardCode(@RequestParam Map<String, Object> map, Model mode){
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String basePath = "ass";
		String group_id = mapVo.get("group_id").toString();
		String hos_id = mapVo.get("hos_id").toString();
		String copy_code = mapVo.get("copy_code").toString();
		String assTwoPath = "assbartwo";
		String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assTwoPath + "/"+map.get("ass_nature").toString()+"/";// 资产性质
		File file = new File(twoFilePath+"/"+map.get("ass_card_no").toString() + ".png");
		if(!file.exists()){
			try {
				FtpUtil.getQRWriteFile(map.get("ass_card_no").toString(), "", twoFilePath, map.get("ass_card_no").toString() + ".png");// 二维码
			} catch (IOException e) {
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}
		
		return JSONObject.parseObject("{\"msg\":\"生成成功.\",\"state\":\"true\"}");
	}
}
