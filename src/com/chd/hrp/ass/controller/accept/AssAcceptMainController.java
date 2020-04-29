/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.controller.accept;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.ass.entity.accept.AssAcceptMain;
import com.chd.hrp.ass.entity.accept.AssAcceptPhoto;
import com.chd.hrp.ass.entity.contract.AssContractDetail;
import com.chd.hrp.ass.entity.in.AssInMainGeneral;
import com.chd.hrp.ass.entity.in.AssInMainOther;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.ass.entity.ins.AssInsDetail;
import com.chd.hrp.ass.service.accept.AssAcceptDetailService;
import com.chd.hrp.ass.service.accept.AssAcceptMainService;
import com.chd.hrp.ass.service.accept.AssAcceptPhotoService;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.ass.service.contract.AssContractDetailService;
import com.chd.hrp.ass.service.contract.AssContractMainService;
import com.chd.hrp.ass.service.in.AssInMainGeneralService;
import com.chd.hrp.ass.service.in.AssInMainOtherService;
import com.chd.hrp.ass.service.in.AssInMainSpecialService;
import com.chd.hrp.ass.service.ins.AssInsDetailService;
import com.chd.hrp.ass.service.ins.AssInsMainService;

/**
 * 
 * @Description: 050601 资产验收主表
 * @Table: ASS_ACCEPT_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssAcceptMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AssAcceptMainController.class);

	// 引入Service服务
	@Resource(name = "assAcceptMainService")
	private final AssAcceptMainService assAcceptMainService = null;

	@Resource(name = "assAcceptDetailService")
	private final AssAcceptDetailService assAcceptDetailService = null;

	@Resource(name = "assContractMainService")
	private final AssContractMainService assContractMainService = null;

	@Resource(name = "assContractDetailService")
	private final AssContractDetailService assContractDetailService = null;

	@Resource(name = "assInsMainService")
	private final AssInsMainService assInsMainService = null;

	@Resource(name = "assInsDetailService")
	private final AssInsDetailService assInsDetailService = null;
	
	@Resource(name = "assAcceptPhotoService")
	private final AssAcceptPhotoService assAcceptPhotoService = null;
	
	@Resource(name = "assCardBasicService")
	private final AssCardBasicService assCardBasicService = null;
	
	@Resource(name = "assInMainGeneralService")
	private final AssInMainGeneralService assInMainGeneralService = null;
	
	@Resource(name = "assInMainOtherService")
	private final AssInMainOtherService assInMainOtherService = null;
	
	@Resource(name = "assInMainSpecialService")
	private final AssInMainSpecialService assInMainSpecialService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/assAcceptMainMainPage", method = RequestMethod.GET)
	public String assAcceptMainMainPage(Model mode) throws Exception {
		mode.addAttribute("ass_05003",MyConfig.getSysPara("05013"));
		mode.addAttribute("user_id",SessionManager.getUserId());
		mode.addAttribute("hos_name",SessionManager.getUserName());
		return "hrp/ass/assacceptmain/assAcceptMainMain";

	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/updateToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assAcceptMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("accept_id", ids[3]);

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

			AssAcceptMain assAcceptMain = assAcceptMainService.queryAssAcceptMainByCode(mapVo);

			if (assAcceptMain.getState() != 0) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}
		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不能重复审核! \"}");
		}
		try {
			assAcceptMainJson = assAcceptMainService.updateToExamine(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assAcceptMainJson);
	}

	/**
	 * @Description 审核
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/updateNotToExamine", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateNotToExamine(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assAcceptMainJson = "";

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("accept_id", ids[3]);

			mapVo.put("audit_emp", SessionManager.getUserId());

			mapVo.put("audit_date", new Date());

			AssAcceptMain assAcceptMain = assAcceptMainService.queryAssAcceptMainByCode(mapVo);

			if (assAcceptMain.getState() != 1) {
				continue;
			} else {
				listVo.add(mapVo);
			}

		}

		if (listVo.size() == 0) {
			return JSONObject.parseObject("{\"warn\":\"不是审核状态! \"}");
		}
		try {
			assAcceptMainJson = assAcceptMainService.updateNotToExamine(listVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assAcceptMainJson);
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/assAcceptMainAddPage", method = RequestMethod.GET)
	public String assAcceptMainAddPage(Model mode) throws Exception {
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));		
		return "hrp/ass/assacceptmain/assAcceptMainAdd";

	}
	
	/**
	 * @Description 生成入库页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/assAcceptInMainPage", method = RequestMethod.GET)
	public String assAcceptInMainPage(Model mode) throws Exception {
				
		return "hrp/ass/assacceptmain/assAcceptInMain";

	}

	/**
	 * @Description 按合同生成页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/assAcceptMainInitPage", method = RequestMethod.GET)
	public String assAcceptMainInitPage(Model mode) throws Exception {
		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		return "hrp/ass/assacceptmain/assAcceptMainInit";

	}

	/**
	 * @Description 按安装生成页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/assAcceptMainInitInstallPage", method = RequestMethod.GET)
	public String assAcceptMainInitInstallPage(Model mode) throws Exception {

		return "hrp/ass/assacceptmain/assAcceptMainInitInstall";

	}
	
	/**
	 * @Description 图片信息
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoMainPage", method = RequestMethod.GET)
	public String assAcceptPhotoMainPage(Model mode, String accept_no) throws Exception {
		mode.addAttribute("accept_no", accept_no);
		return "hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoMain";
	}

	/**
	 * @Description 添加数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/addAssAcceptMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssAcceptMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
		//String assBuildAcceptMainJson = "";
		//String assAcceptDetail = "";
		
		//添加默认验收
		//List<Map<String, Object>> listVoBuild = new ArrayList<Map<String, Object>>();
		//生成验收
		//Map<String, Object> buildMap = new HashMap<String, Object>();
		//查询数据
		//Map<String, Object> buildMapVo1 = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		
		AssAcceptMain assPlanDept = assAcceptMainService.queryAssAcceptMainByCode(mapVo);
		if (assPlanDept != null && assPlanDept.getState() > 0) {
			return JSONObject.parseObject("{\"warn\":\"当前单据已被审核,不能操作 \"}");
		}

	
		String accept_date = String.valueOf(mapVo.get("accept_date"));
		if (StringUtils.isNotEmpty(accept_date)) {
			mapVo.put("accept_date", DateUtil.stringToDate(accept_date, "yyyy-MM-dd"));
		}

		String create_date = String.valueOf(mapVo.get("create_date"));
		if (StringUtils.isNotEmpty(create_date) && !"null".equals(create_date)) {
			mapVo.put("create_date", DateUtil.stringToDate(create_date, "yyyy-MM-dd"));
		}
		
		String yearmonth=mapVo.get("ass_year").toString()+mapVo.get("ass_month").toString();
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
		
		mapVo.put("create_emp", SessionManager.getUserId());

		mapVo.put("state", 0);
		mapVo.put("in_state", 0);//入库状态

		mapVo.put("create_date", new Date());

		/*String assAcceptMainJson = assAcceptMainService.addOrUpdateAssAcceptMain(mapVo);

		JSONObject jsonObj = JSONArray.parseObject(assAcceptMainJson);

		String accept_id = (String) jsonObj.get("accept_id");

		String accept_no = (String) jsonObj.get("accept_no");
		buildMapVo1.put("group_id", SessionManager.getGroupId());

		buildMapVo1.put("hos_id", SessionManager.getHosId());

		buildMapVo1.put("copy_code", SessionManager.getCopyCode());
		
		buildMapVo1.put("accept_id",accept_id);

		String assAcceptDetailJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
				continue;
			}

			detailVo.put("accept_id", accept_id);

			detailVo.put("accept_no", accept_no);

			if (detailVo.get("accept_detail_id") == null) {

				detailVo.put("accept_detail_id", "0");

			}

			detailVo.put("contract_detail_id", mapVo.get("contract_detail_id"));

			String ass_id_no = detailVo.get("ass_id").toString();

			detailVo.put("ass_id", ass_id_no.split("@")[0]);

			detailVo.put("ass_no", ass_id_no.split("@")[1]);

			String fac_id_no = detailVo.get("fac_id").toString();

			detailVo.put("fac_id", fac_id_no.split("@")[0]);

			detailVo.put("fac_no", fac_id_no.split("@")[1]);

			try {

				assAcceptDetailJson = assAcceptDetailService.addOrUpdateAssAcceptDetail(detailVo);
				assAcceptDetail = assAcceptDetailService.queryAssAcceptDetailByUpdate(buildMapVo1);
				 Map assMap = JSONObject.parseObject(assAcceptDetail);
					List<Map> assList = JSONArray.parseArray(assMap.get("Rows").toString(), Map.class); 
					for (Map map : assList) {
						buildMap.put("accept_detail_id", map.get("accept_detail_id"));
					}
				//生成验收项目列表
				 改begin zn 解决修改页面点击保存报错
				buildMapVo1.put("ass_id", ass_id_no.split("@")[0]);
				 改end 
				assBuildAcceptMainJson = assAcceptMainService.buildAssAcceptItem(buildMapVo1);
			    Map map = JSONObject.parseObject(assBuildAcceptMainJson);
			     改begin zn 在未维护验收项目维护 返回错误信息
			    if(map.get("warn") != null){
			    	return map;
			    }
			     改end 
				List<Map> list = JSONArray.parseArray(map.get("Rows").toString(), Map.class); 
				for (Map map2 : list) {
					map2.put("group_id", SessionManager.getGroupId());

					map2.put("hos_id", SessionManager.getHosId());

					map2.put("copy_code", SessionManager.getCopyCode());
					map2.put("accept_detail_id", buildMap.get("accept_detail_id"));
					map2.put("accept_id", accept_id);
					
					map2.put("is_normal", "2");
					map2.put("item_code", map2.get("accept_item_code"));
					listVoBuild.add(map2);
				}
				assAcceptMainService.saveAssAcceptItem(listVoBuild);
			} catch (Exception e) {
				logger.error(e.getMessage());
				return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
			}
		}

		JSONObject json = JSONArray.parseObject(assAcceptDetailJson);

		json.put("accept_id", accept_id);

		json.put("accept_no", accept_no);*/
   //  String reJson=assAcceptMainService.addOrUpdateAssAcceptMain(mapVo);
		return JSONObject.parseObject(assAcceptMainService.addOrUpdateAssAcceptMain(mapVo));
		}catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");}

	}

	/**
	 * @Description 按合同生成数据 050601 资产验收明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/initAssAcceptByContract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAcceptByContract(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String initAcceptDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<AssContractDetail> contractDetailList = assContractDetailService.queryAssContractDetailHosList(mapVo);
		if (contractDetailList.size() > 0) {
			String accept_date = String.valueOf(mapVo.get("accept_date"));

			if (StringUtils.isNotEmpty(accept_date)) {

				mapVo.put("accept_date", DateUtil.stringToDate(accept_date, "yyyy-MM-dd"));

			}

			String pact_code_no = mapVo.get("pact_code").toString();

			mapVo.put("pact_code", pact_code_no.split("@")[0]);

			String ven_id_no = mapVo.get("ven_id").toString();

			mapVo.put("ven_id", ven_id_no.split("@")[0]);

			mapVo.put("ven_no", ven_id_no.split("@")[1]);

			String dept_id_no = mapVo.get("dept_id").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[0]);

			mapVo.put("dept_no", dept_id_no.split("@")[1]);

			mapVo.put("accept_emp", mapVo.get("accept_emp"));

			mapVo.put("create_emp", SessionManager.getUserId());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("create_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));

			mapVo.put("state", "0");

			initAcceptDetailJson = assAcceptMainService.addOrUpdateAssAcceptMain(mapVo);

			JSONObject jsonObj = JSONArray.parseObject(initAcceptDetailJson);

			String accept_id = (String) jsonObj.get("accept_id");

			for (AssContractDetail detailVo : contractDetailList) {

				Map<String, Object> detailMap = new HashMap<String, Object>();

				detailMap.put("group_id", detailVo.getGroup_id());

				detailMap.put("hos_id", detailVo.getHos_id());

				detailMap.put("copy_code", detailVo.getCopy_code());

				detailMap.put("accept_id", accept_id);

				detailMap.put("contract_detail_id", pact_code_no.split("@")[1]);

				detailMap.put("ass_id", detailVo.getAss_id());

				detailMap.put("ass_no", detailVo.getAss_no());

				detailMap.put("ass_model", detailVo.getAss_model());

				detailMap.put("ass_spec", detailVo.getAss_spec());

				detailMap.put("ass_brand", detailVo.getAss_brand());

				detailMap.put("fac_id", detailVo.getFac_id());

				detailMap.put("fac_no", detailVo.getFac_no());

				detailMap.put("accept_price", "0");

				detailMap.put("accept_amount", detailVo.getContract_amount());

				detailMap.put("accept_money", "0");

				detailMap.put("is_well", "0");

				initAcceptDetailJson = assAcceptDetailService.addOrUpdateAssAcceptDetail(detailMap);

			}

			for (String id : mapVo.get("pact_codes").toString().split(",")) {

				Map<String, Object> conMapvo = new HashMap<String, Object>();

				conMapvo.put("group_id", mapVo.get("group_id"));

				conMapvo.put("hos_id", mapVo.get("hos_id"));

				conMapvo.put("copy_code", mapVo.get("copy_code"));

				conMapvo.put("accept_id", accept_id);

				conMapvo.put("pact_code", id);

				try {

					initAcceptDetailJson = assAcceptDetailService.initAssAcceptDetailBid(conMapvo);

				} catch (Exception e) {

					return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

				}

			}

			initAcceptDetailJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + mapVo.get("group_id")
					+ "|" + mapVo.get("hos_id") + "|" + mapVo.get("copy_code") + "|" + accept_id + "\"}";
		} else {
			initAcceptDetailJson = "{\"error\":\"没有可生成的数据！\"}";
		}

		return JSONObject.parseObject(initAcceptDetailJson);
	}

	/**
	 * @Description 按安装生成数据 050601 资产验收明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/initAssAcceptByIns", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initAssAcceptByIns(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String initAcceptDetailJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		List<AssInsDetail> list = assInsDetailService.queryAssInsDetailByAccept(mapVo);
		if (list.size() > 0) {
			String accept_date = String.valueOf(mapVo.get("accept_date"));

			if (StringUtils.isNotEmpty(accept_date)) {

				mapVo.put("accept_date", DateUtil.stringToDate(accept_date, "yyyy-MM-dd"));

			}

			String pact_code_no = mapVo.get("pact_code").toString();

			mapVo.put("pact_code", pact_code_no);

			String ven_id_no = mapVo.get("ven_id").toString();

			mapVo.put("ven_id", ven_id_no.split("@")[0]);

			mapVo.put("ven_no", ven_id_no.split("@")[1]);

			String dept_id_no = mapVo.get("dept_id").toString();

			mapVo.put("dept_id", dept_id_no.split("@")[0]);

			mapVo.put("dept_no", dept_id_no.split("@")[1]);

			mapVo.put("accept_emp", mapVo.get("accept_emp"));

			mapVo.put("create_emp", SessionManager.getUserId());

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			mapVo.put("create_date", DateUtil.stringToDate(format.format(new Date()), "yyyy-MM-dd"));

			mapVo.put("state", "0");

			initAcceptDetailJson = assAcceptMainService.addOrUpdateAssAcceptMain(mapVo);

			JSONObject jsonObj = JSONArray.parseObject(initAcceptDetailJson);

			String accept_id = (String) jsonObj.get("accept_id");

			for (AssInsDetail detailVo : list) {

				Map<String, Object> detailMap = new HashMap<String, Object>();

				detailMap.put("group_id", detailVo.getGroup_id());

				detailMap.put("hos_id", detailVo.getHos_id());

				detailMap.put("copy_code", detailVo.getCopy_code());

				detailMap.put("accept_id", accept_id);

				detailMap.put("ass_id", detailVo.getAss_id());

				detailMap.put("ass_no", detailVo.getAss_no());

				detailMap.put("ass_model", detailVo.getAss_model());

				detailMap.put("ass_spec", detailVo.getAss_spec());

				detailMap.put("ass_brand", detailVo.getAss_brand());

				detailMap.put("fac_id", detailVo.getFac_id());

				detailMap.put("fac_no", detailVo.getFac_no());

				detailMap.put("accept_price", detailVo.getIns_price());

				detailMap.put("accept_amount", detailVo.getIns_amount());

				detailMap.put("accept_money", detailVo.getIns_price() * detailVo.getIns_amount());

				detailMap.put("is_well", "0");

				initAcceptDetailJson = assAcceptDetailService.addOrUpdateAssAcceptDetail(detailMap);

			}

			for (String id : mapVo.get("ins_ids").toString().split(",")) {

				Map<String, Object> conMapvo = new HashMap<String, Object>();

				conMapvo.put("group_id", mapVo.get("group_id"));

				conMapvo.put("hos_id", mapVo.get("hos_id"));

				conMapvo.put("copy_code", mapVo.get("copy_code"));

				conMapvo.put("accept_id", accept_id);

				conMapvo.put("ins_id", id);

				try {
					initAcceptDetailJson = assAcceptDetailService.initInstallAssAcceptDetailBid(conMapvo);
				} catch (Exception e) {
					return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
				}

			}

			initAcceptDetailJson = "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + mapVo.get("group_id")
					+ "|" + mapVo.get("hos_id") + "|" + mapVo.get("copy_code") + "|" + accept_id + "\"}";
		} else {
			initAcceptDetailJson = "{\"error\":\"没有可生成的数据！\"}";
		}

		return JSONObject.parseObject(initAcceptDetailJson);
	}

	/**
	 * @Description 更新跳转页面 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/assAcceptMainUpdatePage", method = RequestMethod.GET)
	public String assAcceptMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssAcceptMain assAcceptMain = new AssAcceptMain();

		assAcceptMain = assAcceptMainService.queryAssAcceptMainByCode(mapVo);

		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");

		String aeDate = a.format(assAcceptMain.getAccept_date());

		String arDate = a.format(assAcceptMain.getCreate_date());

		mode.addAttribute("group_id", assAcceptMain.getGroup_id());

		mode.addAttribute("hos_id", assAcceptMain.getHos_id());

		mode.addAttribute("copy_code", assAcceptMain.getCopy_code());

		mode.addAttribute("accept_id", assAcceptMain.getAccept_id());

		mode.addAttribute("accept_no", assAcceptMain.getAccept_no());

		mode.addAttribute("ass_year", assAcceptMain.getAss_year());

		mode.addAttribute("ass_month", assAcceptMain.getAss_month());

		mode.addAttribute("summary", assAcceptMain.getSummary());

		mode.addAttribute("pact_code", assAcceptMain.getPact_code());

		mode.addAttribute("contract_name", assAcceptMain.getContract_name());

		mode.addAttribute("ven_id", assAcceptMain.getVen_id());

		mode.addAttribute("ven_no", assAcceptMain.getVen_no());

		mode.addAttribute("ven_code", assAcceptMain.getVen_code());

		mode.addAttribute("ven_name", assAcceptMain.getVen_name());

		mode.addAttribute("dept_id", assAcceptMain.getDept_id());

		mode.addAttribute("dept_no", assAcceptMain.getDept_no());

		mode.addAttribute("dept_code", assAcceptMain.getDept_code());

		mode.addAttribute("dept_name", assAcceptMain.getDept_name());

		mode.addAttribute("accept_emp", assAcceptMain.getAccept_emp());
		mode.addAttribute("accept_emp_name", assAcceptMain.getAccept_emp_name());

		mode.addAttribute("accept_date", aeDate);

		mode.addAttribute("create_emp", assAcceptMain.getCreate_emp());

		mode.addAttribute("create_emp_name", assAcceptMain.getCreate_emp_name());

		mode.addAttribute("audit_emp", assAcceptMain.getAudit_emp());

		mode.addAttribute("audit_emp_name", assAcceptMain.getAudit_emp_name());

		mode.addAttribute("audit_date", assAcceptMain.getAudit_date());

		mode.addAttribute("state", assAcceptMain.getState());

		mode.addAttribute("accept_desc", assAcceptMain.getAccept_desc());
		
		mode.addAttribute("device_code", assAcceptMain.getDevice_code());
		mode.addAttribute("device_name", assAcceptMain.getDevice_name());
		mode.addAttribute("buy_code", assAcceptMain.getBuy_code());
		mode.addAttribute("buy_name", assAcceptMain.getBuy_name());
		mode.addAttribute("source_code", assAcceptMain.getSource_code());
		mode.addAttribute("source_name", assAcceptMain.getSource_name());
		mode.addAttribute("ass_naturs", assAcceptMain.getAss_naturs());
		mode.addAttribute("naturs_name", assAcceptMain.getNaturs_name());
		mode.addAttribute("in_state", assAcceptMain.getIn_state());

		mode.addAttribute("ass_05005",MyConfig.getSysPara("05005"));
		mode.addAttribute("ass_05006",MyConfig.getSysPara("05006"));
		mode.addAttribute("ass_05013",MyConfig.getSysPara("05013"));		
		mode.addAttribute("user_id",SessionManager.getUserId());
		
		return "hrp/ass/assacceptmain/assAcceptMainUpdate";

	}

	// 生成验收项目
	@RequestMapping(value = "/hrp/ass/assacceptmain/buildAssAcceptItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> buildAssAcceptItem(@RequestParam Map<String, Object> mapVo, Model model)
			throws Exception {
		String assAcceptMainJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		assAcceptMainJson = assAcceptMainService.buildAssAcceptItem(mapVo);

		return JSONObject.parseObject(assAcceptMainJson);
	}

	// 保存验收项目
	@RequestMapping(value = "/hrp/ass/assacceptmain/saveAssAcceptItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAcceptItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assAcceptDetailJson = "";

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("accept_item_data").toString());

		for (Map<String, Object> detailVo : detail) {

			detailVo.put("group_id", SessionManager.getGroupId());

			detailVo.put("hos_id", SessionManager.getHosId());

			detailVo.put("copy_code", SessionManager.getCopyCode());

			if (detailVo.get("item_code") == null || "".equals(detailVo.get("item_code"))) {
				continue;
			}

			detailVo.put("is_normal", Integer.parseInt(detailVo.get("is_normal").toString()));

			listVo.add(detailVo);
		}

		try {
			assAcceptDetailJson = assAcceptMainService.saveAssAcceptItem(listVo);
		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assAcceptDetailJson);

	}

	// 查询验收项目
	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAssAcceptItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptItem(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assAcceptMain = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assAcceptMain = assAcceptMainService.queryAssAcceptItem(getPage(mapVo));

		return JSONObject.parseObject(assAcceptMain);
	}

	// 删除验收项目
	@RequestMapping(value = "/hrp/ass/assacceptmain/deleteAssAcceptItem", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAcceptItem(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assAcceptMainJson;
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("accept_id", ids[3]);

			mapVo.put("accept_detail_id", ids[4]);

			mapVo.put("item_code", ids[5]);

			listVo.add(mapVo);
		}
		try {
			                 
			assAcceptMainJson = assAcceptMainService.deleteAssAcceptItem(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assAcceptMainJson);
	}

	/**
	 * @Description 删除数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/deleteAssAcceptMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAcceptMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String assAcceptMainJson;
		boolean flag = true;
		List<Map<String, Object>> listP = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("accept_id", ids[3]);
			mapVo.put("accept_no", ids[4]);
			
			List<AssAcceptPhoto> list = assAcceptPhotoService.queryAssAcceptPhoto(mapVo);
			if (list.size() > 0) {
				for (AssAcceptPhoto detailVo : list) {

					Map<String, Object> detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", detailVo.getGroup_id());
					detailMap.put("hos_id", detailVo.getHos_id());
					detailMap.put("copy_code", detailVo.getCopy_code());
					detailMap.put("accept_no", detailVo.getAccept_no());
					detailMap.put("photo_code", detailVo.getPhoto_code());
					detailMap.put("photo_name", detailVo.getPhoto_name());
					detailMap.put("file_url", detailVo.getFile_url());
					listP.add(detailMap);
				}
			}

			AssAcceptMain assAcceptMain = assAcceptMainService.queryAssAcceptMainByCode(mapVo);

			if (assAcceptMain.getState() != 0) {
				flag = false;
				break;
			}
			listVo.add(mapVo);
		}
		if (flag == false) {
			return JSONObject.parseObject("{\"error\":\"删除失败 状态必须是新建的才能进行删除! \"}");
		}
		try {
			if(listP.size() >0){
				assAcceptMainJson = assAcceptPhotoService.deleteBatch(listP);
				JSONObject json = JSONObject.parseObject(assAcceptMainJson);
				if(!json.get("state").equals("true")){
					return JSONObject.parseObject(assAcceptMainJson);
				}
				
				assAcceptMainJson = assCardBasicService.deletePhoto(listP);
				JSONObject jsons = JSONObject.parseObject(assAcceptMainJson);
				if(!jsons.get("state").equals("true")){
					return JSONObject.parseObject(assAcceptMainJson);
				}
			}
			
			assAcceptMainJson = assAcceptMainService.deleteBatchAssAcceptMain(listVo);
			
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assAcceptMainJson);
	}

	/**
	 * @Description 生成数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/initContract", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initContract(@RequestParam Map<String, Object> mapVo, Model model) throws Exception {

		String assAcceptMainJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("pact_code", mapVo.get("pact_code"));

		try {

			assAcceptMainJson = assAcceptMainService.initContract(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptMainJson);

	}

	/**
	 * @Description 查询数据 050601 资产验收主表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAssAcceptMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assAcceptMain = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assAcceptMain = assAcceptMainService.queryAssAcceptMain(getPage(mapVo));

		return JSONObject.parseObject(assAcceptMain);
	}

	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAssContractByAccept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssContractByAccept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		mapVo.put("is_group", "0");
		mapVo.put("is_init", "0");
		mapVo.put("state", "1");
		String assContractMain = assContractMainService.queryAssContractMain(getPage(mapVo));

		return JSONObject.parseObject(assContractMain);

	}

	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAssInsByAccept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInsByAccept(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("acc_year", SessionManager.getAcctYear());

		mapVo.put("is_group", "0");
		mapVo.put("is_init", "0");
		mapVo.put("state", "1");
		String assContractMain = assInsMainService.queryAssInsMain(getPage(mapVo));
		return JSONObject.parseObject(assContractMain);
	}

	/**
	 * @Description 删除数据 050601 资产验收明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/deleteAssAcceptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAcceptDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assAcceptDetailJson = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("accept_id", ids[3]);

			mapVo.put("accept_detail_id", ids[4]);

			listVo.add(mapVo);

		}

		try {
			            assAcceptMainService.deleteAssAcceptItem(listVo);
			assAcceptDetailJson = assAcceptDetailService.deleteBatchAssAcceptDetail(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assAcceptDetailJson);

	}

	/**
	 * @Description 查询数据 050601 资产验收明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAssAcceptDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assAcceptDetail = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assAcceptDetail = assAcceptDetailService.queryAssAcceptDetailByUpdate(getPage(mapVo));

		return JSONObject.parseObject(assAcceptDetail);

	}
	//
	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAssAcceptDetailIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptDetailIn(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assAcceptDetail = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assAcceptDetail = assAcceptDetailService.queryAssAcceptDetailIn(getPage(mapVo));

		return JSONObject.parseObject(assAcceptDetail);

	}
	//生成入库单
	@RequestMapping(value = "/hrp/ass/assacceptmain/saveAssAcceptInMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveAssAcceptInMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

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
		String ass_naturs=mapVo.get("ass_naturs").toString();
		String assInMainJson="";
		
		if("02".equals(ass_naturs)){//专用资产
			AssInMainSpecial assInMainSpecial = assInMainSpecialService.queryByCode(mapVo);
			if(assInMainSpecial != null){
				if(assInMainSpecial.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
			}
			assInMainJson = assInMainSpecialService.addOrUpdate(mapVo);
			
		}else if("03".equals(ass_naturs)){//通用资产
			AssInMainGeneral assInMainGeneral = assInMainGeneralService.queryByCode(mapVo);
			if(assInMainGeneral != null){
				if(assInMainGeneral.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
			}
			assInMainJson = assInMainGeneralService.addOrUpdate(mapVo);
			
		}else if("04".equals(ass_naturs)){//其他固定资产
			AssInMainOther assInMainOther = assInMainOtherService.queryByCode(mapVo);
			if(assInMainOther != null){
				if(assInMainOther.getState() > 0){
					return JSONObject.parseObject("{\"warn\":\"已入库确认的数据不能保存! \"}");
				}
			}
			assInMainJson = assInMainOtherService.addOrUpdate(mapVo);
		}
		
		mapVo.put("in_state", 1);
		assInMainJson=assAcceptMainService.updateAcceptMainInState(mapVo);

		return JSONObject.parseObject(assInMainJson);
	}

	/***
	 * 打印状态
	 */
	@RequestMapping(value = "/hrp/ass/assacceptmain/queryAcceptMainState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAcceptMainState(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		//打印时校验数据专用
		List<String> list = assAcceptMainService.queryAcceptMainState(mapVo);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"state\":\"true\"}");
			
		}else{
			
			String  str = "" ;
			for(String item : list){
				
				str += item +"," ;
			}
			
			return JSONObject.parseObject("{\"error\":\"资产验收单【"+str.substring(0, str.length()-1)+"】不是确认状态不能打印.\",\"state\":\"false\"}");
		}
		
		
	}
	
	
	
}
