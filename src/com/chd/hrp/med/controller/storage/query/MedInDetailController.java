/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.storage.query;

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
import com.chd.hrp.med.service.storage.in.MedStorageInService;
import com.chd.hrp.med.service.storage.out.MedOutMainService;
import com.chd.hrp.med.service.storage.query.MedInDetailService;
import com.chd.hrp.sup.service.SupDeliveryMainService;

/**
 * 
 * @Description: 物流统计查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedInDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(MedInDetailController.class);
	

	//引入Service服务
	@Resource(name = "medInDetailService")
	private final MedInDetailService medInDetailService = null;
	
	@Resource(name = "medOutMainService")
	private final MedOutMainService medOutMainService = null;
	@Resource(name = "medStorageInService")
	private final MedStorageInService medStorageInService = null;
	@Resource(name = "supDeliveryMainService")
	private final SupDeliveryMainService supDeliveryMainService = null;
	
	/**
	 * @Description 库存明细查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medStockDetailPage", method = RequestMethod.GET)
	public String medStockDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/storage/query/medStockDetail";
	}

	
	/**
	 * @Description 库存明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryStockDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryStockDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		
		String queryStockDetail = medInDetailService.queryMedStorageQueryStockDetail(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	
	@RequestMapping(value = "/hrp/med/storage/query/medWorkDetailPage", method = RequestMethod.GET)
	public String medWorkDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/query/medWorkDetail";
	}
	
	
	//业务明细
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryWorkDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryWorkDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		
		String queryStockDetail = medInDetailService.queryMedStorageQueryWorkDetail(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	/**
	 * @Description 库存分布查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medStockRoutingPage", method = RequestMethod.GET)
	public String medStockRoutingPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/storage/query/medStockRouting";
	}
	
	
	/**
	 * @Description 库存分布查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryStockRouting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryStockRouting(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockRouting = medInDetailService.queryMedStorageQueryStockRouting(getPage(mapVo));

		return JSONObject.parseObject(queryStockRouting);

	}
	
	/**
	 * @Description 条码分布查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medInvBarRoutingPage", method = RequestMethod.GET)
	public String medInvBarRoutingPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/storage/query/medInvBarRouting";
	}
	
	/**
	 * @Description 条码分布查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryInvBarRouting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryInvBarRouting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockRouting = medInDetailService.queryMedStorageQueryInvBarRouting(getPage(mapVo));

		return JSONObject.parseObject(queryStockRouting);
	}
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medInDetailPage", method = RequestMethod.GET)
	public String MedCommonInMainPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/storage/query/medInDetail";
	}

	/**
	 * @Description 入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryInDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryInDetail = medInDetailService.queryMedStorageQueryInDetail(getPage(mapVo));

		return JSONObject.parseObject(queryInDetail);

	}
	
	

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medInSupCountPage", method = RequestMethod.GET)
	public String medInSupCountPage(Model mode) throws Exception {

		return "hrp/med/storage/query/medInSupCount";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryMedInSupCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryMedInSupCount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medInSupCount = medInDetailService.queryMedStorageQueryMedInSupCount(mapVo);

		return JSONObject.parseObject(medInSupCount);

	}
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode供应商采购明细查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medInSupDetailPage", method = RequestMethod.GET)
	public String medInSupDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/storage/query/medInSupDetail";
	}

	/**
	 * @Description 供应商采购明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryMedInSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryMedInSupDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryMedInSupDetail = medInDetailService.queryMedStorageQueryMedInSupDetail(getPage(mapVo));

		return JSONObject.parseObject(queryMedInSupDetail);

	}
	
	/**
	 * @Description 主页面跳转
	 * @param mode科室领用明细查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medOutDeptPage", method = RequestMethod.GET)
	public String medApplyDetailPage(Model mode) throws Exception {

		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/storage/query/medOutDept";
	}

	/**
	 * @Description 科室领用明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryMedOutDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryMedOutDept(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		//查询时  登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if(mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null){
			
			mapVo.put("dept_id", "00"); //当 dept_id == 00 时  根据用户 部门权限 限定 申请科室
		}
		
		if(mapVo.get("store_id") == "" || mapVo.get("store_id") == null){
			
			mapVo.put("store_id", "00"); //当 store_id == 00 时  根据用户 仓库权限 限定 响应仓库
		}
		String queryMedApplyDetail = medInDetailService.queryMedStorageQueryMedOutDept(getPage(mapVo));

		return JSONObject.parseObject(queryMedApplyDetail);

	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 主页面跳转
	 * @param mode出库明细查询 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medOutDetailPage", method = RequestMethod.GET)
	public String medOutDetailPage(Model mode) throws Exception {

		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/storage/query/medOutDetail";
	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 出库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageQueryOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageQueryOutDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = medInDetailService.queryMedStorageQueryOutDetail(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 主页面跳转
	 * @param mode出库单供应商信息页
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medOutSupMessagePage", method = RequestMethod.GET)
	public String medOutSupMessagePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("out_id", mapVo.get("out_id"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		return "hrp/med/storage/query/medOutSupMessage";
	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 出库单供应商信息查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedOutSupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedOutSupMessage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = medInDetailService.queryMedOutSupMessage(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	/**
	 * 入库台账报表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medStorageInvCertDetailPage", method = RequestMethod.GET)
	public String medStorageInvCertDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/storage/query/medInvCertDetail";
	}
	/**
	 * 入库台账报表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedStorageInvCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedStorageInvCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = medInDetailService.queryMedStorageInvCertDetail(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	
	/**
	 * @Description 科室申领统计查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/medApplyCountPage", method = RequestMethod.GET)
	public String medApplyCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/storage/query/medApplyCount";
	}

	
	/**
	 * @Description 库存明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedApplyCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedApplyCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockDetail = medInDetailService.queryMedApplyCount(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	/**
	 * @Description 科室申领统计查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedInSupBusTypeSumPage", method = RequestMethod.GET)
	public String queryMedInSupBusTypeSumPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));

		return "hrp/med/storage/query/medInSupBusTypeSum";
	}
	/**
	 * 供应商入库汇总查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/storage/query/queryMedInSupBusTypeSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInSupBusTypeSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockDetail = medInDetailService.queryMedInSupBusTypeSum(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
}
