/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.query;

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
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.mat.service.storage.out.MatOutMainService;
import com.chd.hrp.mat.service.storage.query.MatInDetailService;
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
public class MatInDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(MatInDetailController.class);
	

	//引入Service服务
	@Resource(name = "matInDetailService")
	private final MatInDetailService matInDetailService = null;
	
	@Resource(name = "matOutMainService")
	private final MatOutMainService matOutMainService = null;
	@Resource(name = "matStorageInService")
	private final MatStorageInService matStorageInService = null;
	@Resource(name = "supDeliveryMainService")
	private final SupDeliveryMainService supDeliveryMainService = null;
	
	/**
	 * @Description 库存明细查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matStockDetailPage", method = RequestMethod.GET)
	public String matStockDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matStockDetail";
	}

	
	/**
	 * @Description 库存明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryStockDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryStockDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		
		String queryStockDetail = matInDetailService.queryMatStorageQueryStockDetail(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	
	@RequestMapping(value = "/hrp/mat/storage/query/matWorkDetailPage", method = RequestMethod.GET)
	public String matWorkDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matWorkDetail";
	}
	//业务明细
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryWorkDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryWorkDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		
		String queryStockDetail = matInDetailService.queryMatStorageQueryWorkDetail(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	//入出库查询
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatInOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInOutDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		
		String queryStockDetail = matInDetailService.queryMatInOutDetail(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	/**
	 * @Description 库存分布查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matStockRoutingPage", method = RequestMethod.GET)
	public String matStockRoutingPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matStockRouting";
	}
	
	
	/**
	 * @Description 库存分布查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryStockRouting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryStockRouting(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockRouting = matInDetailService.queryMatStorageQueryStockRouting(getPage(mapVo));

		return JSONObject.parseObject(queryStockRouting);

	}
	
	/**
	 * @Description 条码分布查询 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInvBarRoutingPage", method = RequestMethod.GET)
	public String matInvBarRoutingPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matInvBarRouting";
	}
	
	/**
	 * @Description 条码分布查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryInvBarRouting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryInvBarRouting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockRouting = matInDetailService.queryMatStorageQueryInvBarRouting(getPage(mapVo));

		return JSONObject.parseObject(queryStockRouting);
	}
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInDetailPage", method = RequestMethod.GET)
	public String MatCommonInMainPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matInDetail";
	}

	/**
	 * @Description 入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryInDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryInDetail = matInDetailService.queryMatStorageQueryInDetail(getPage(mapVo));

		return JSONObject.parseObject(queryInDetail);

	}
	
	/**
	 * @Description 材料入库汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageInInv", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInInv(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryInDetail = matInDetailService.queryMatStorageInInv(getPage(mapVo));

		return JSONObject.parseObject(queryInDetail);

	}
	
	

	/**
	 * @Description 供应商汇总主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInSupCountPage", method = RequestMethod.GET)
	public String matInSupCountPage(Model mode) throws Exception {

		return "hrp/mat/storage/query/matInSupCount";
	}
	
	
	/**
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryMatInSupCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryMatInSupCount(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matInSupCount = matInDetailService.queryMatStorageQueryMatInSupCount(mapVo);

		return JSONObject.parseObject(matInSupCount);

	}
	/**
	 * 查询发生过入库业务的物资类别编码,名称
	 * @param mapVo
	 * @param mdl
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/storage/query/queryOccurMatTypeDictForVariableHead",method=RequestMethod.POST)
	@ResponseBody
	public String queryOccurMatTypeDictForVariableHead(@RequestParam Map<String, Object> mapVo,Model mdl){
		if (mapVo.get("group_id")==null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if (mapVo.get("hos_id")==null) {mapVo.put("hos_id", SessionManager.getHosId());}
		if (mapVo.get("copy_code")==null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		if (mapVo.get("user_id")==null) {mapVo.put("user_id", SessionManager.getUserId());}
		
		return matInDetailService.queryOccurMatTypeDictForVariableHead(mapVo);
	}
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode供应商采购明细查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matInSupDetailPage", method = RequestMethod.GET)
	public String matInSupDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matInSupDetail";
	}

	/**
	 * @Description 供应商采购明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryMatInSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryMatInSupDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryMatInSupDetail;
		if("1".equals(mapVo.get("store_type").toString())){
			queryMatInSupDetail = matInDetailService.queryMatStorageQueryMatInSupDetailSet(getPage(mapVo));
		}else{
			queryMatInSupDetail = matInDetailService.queryMatStorageQueryMatInSupDetail(getPage(mapVo));
		}
		
		return JSONObject.parseObject(queryMatInSupDetail);

	}
	
	/**
	 * @Description 主页面跳转
	 * @param mode科室领用明细查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matTranDetailPage", method = RequestMethod.GET)
	public String matTranDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matTranDetail";
	}
	
	/**
	 * @Description 主页面跳转
	 * @param 材料调拨明细查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matOutDeptPage", method = RequestMethod.GET)
	public String matApplyDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matOutDept";
	}
	
	/**
	 * @Description 主页面跳转
	 * @param mode科室领用明细查询
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matOutDeptNewPage", method = RequestMethod.GET)
	public String matOutDeptNewPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matOutDeptNew";
	}
	
	/**
	 * @Description 科室领用明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryMatOutDeptNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryMatOutDeptNew(
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
		String queryMatApplyDetail = matInDetailService.queryMatStorageQueryMatOutDeptNew(getPage(mapVo));

		return JSONObject.parseObject(queryMatApplyDetail);

	}
	

	/**
	 * @Description 科室领用明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryMatOutDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryMatOutDept(
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
		String queryMatApplyDetail = matInDetailService.queryMatStorageQueryMatOutDept(getPage(mapVo));

		return JSONObject.parseObject(queryMatApplyDetail);

	}
	
	
	/**
	 * @Description 调拨明细汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatAccountReportTranCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportTranCollection(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String queryMatApplyDetail = matInDetailService.queryMatAccountReportTranCollection(getPage(mapVo));

		return JSONObject.parseObject(queryMatApplyDetail);

	}
	/**
	 * @Description 调拨明细汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatAccountReportTranDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAccountReportTranDetail(
			@RequestParam Map<String, Object> mapVo, Model mode)
					throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String queryMatApplyDetail = matInDetailService.queryMatAccountReportTranDetail(getPage(mapVo));
		
		return JSONObject.parseObject(queryMatApplyDetail);
		
	}
	
	
	/**
	 * 2016/12/19 lxj
	 * @Description 主页面跳转
	 * @param mode出库明细查询 
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matOutDetailPage", method = RequestMethod.GET)
	public String matOutDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matOutDetail";
	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 出库明细查询 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageQueryOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageQueryOutDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		//查询时  登录用户只能查询其有数据权限（部门权限、仓库权限）的 相关数据
		if(mapVo.get("dept_id") == "" || mapVo.get("dept_id") == null){
			
			mapVo.put("dept_id", "00"); //当 dept_id == 00 时  根据用户 部门权限 限定 申请科室
		}
		String queryOutDetail = matInDetailService.queryMatStorageQueryOutDetail(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	/**
	 * 2016/12/19 lxj
	 * @Description 出库按材料汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageOutInvCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageOutInvCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = matInDetailService.queryMatStorageOutInvCollection(getPage(mapVo));
		
		return JSONObject.parseObject(queryOutDetail);
		
	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 主页面跳转
	 * @param mode出库单供应商信息页
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matOutSupMessagePage", method = RequestMethod.GET)
	public String matOutSupMessagePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("out_id", mapVo.get("out_id"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		return "hrp/mat/storage/query/matOutSupMessage";
	}
	
	/**
	 * 2016/12/19 lxj
	 * @Description 出库单供应商信息查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatOutSupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatOutSupMessage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = matInDetailService.queryMatOutSupMessage(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	/**
	 * 入库台账报表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matStorageInvCertDetailPage", method = RequestMethod.GET)
	public String matStorageInvCertDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matInvCertDetail";
	}
	/**
	 * 入库台账报表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStorageInvCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStorageInvCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = matInDetailService.queryMatStorageInvCertDetail(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	
	/**
	 * @Description 科室申领统计查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/matApplyCountPage", method = RequestMethod.GET)
	public String matApplyCountPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matApplyCount";
	}

	
	/**
	 * @Description 库存明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatApplyCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatApplyCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockDetail = matInDetailService.queryMatApplyCount(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	/**
	 * @Description 科室申领统计查询
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatInSupBusTypeSumPage", method = RequestMethod.GET)
	public String queryMatInSupBusTypeSumPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/storage/query/matInSupBusTypeSum";
	}
	/**
	 * 供应商入库汇总查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatInSupBusTypeSum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInSupBusTypeSum(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryStockDetail = matInDetailService.queryMatInSupBusTypeSum(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	@RequestMapping(value = "/hrp/mat/storage/query/matInOutDetailPage", method = RequestMethod.GET)
	public String matInOutDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matInOutDetail";
	}
	@RequestMapping(value = "/hrp/mat/storage/query/matStoreInvHoldDetailPage", method = RequestMethod.GET)
	public String matStoreInvHoldDetailPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matStoreInvHoldDetail";
	}
	
	/**
	 * 仓库结余查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStoreInvBalanceDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatStoreInvBalanceDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String queryStockDetail = matInDetailService.queryMatStoreInvBalanceDetail(getPage(mapVo));

		return JSONObject.parseObject(queryStockDetail);

	}
	
	/**
	 * @Description 
	 * 仓库结余-材料入出库明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/storage/query/queryMatStoreInvInOutDetail", method = RequestMethod.GET)
	public String queryMatStoreInvInOutDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matStoreInvInOutDetail = matInDetailService.queryMatStoreInvInOutDetail(mapVo);
		
		String storeMsg = matInDetailService.queryStoreMsg(mapVo);
		
		String invMsg = matInDetailService.queryInvMsg(mapVo);
		
		
		
		mode.addAttribute("matStoreInvInOutDetail", JSONObject.parseObject(matStoreInvInOutDetail));
		
		mode.addAttribute("storeMsg", JSONObject.parseObject(storeMsg));
		
		mode.addAttribute("invMsg", JSONObject.parseObject(invMsg));
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/storage/query/matStoreInvInOutDetail";
	}
	
	

	
	
}
