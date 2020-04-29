package com.chd.hrp.mat.controller.affi.query;

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
import com.chd.hrp.mat.service.affi.query.MatAffiStockSearchService;

/**
 * @Description:代销-库存查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatAffiStockSearchController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MatAffiStockSearchController.class);
	
	@Resource(name = "matAffiStockSearchService")
	private final MatAffiStockSearchService matAffiStockSearchService = null;
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 库存明细账查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiStockDetailPage", method = RequestMethod.GET)
	public String matAffiStockDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/query/matAffiStockDetail";
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 库存明细账查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiStorageQueryStockDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiStorageQueryStockDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiStockSearchJson = matAffiStockSearchService.queryMatAffiStorageQueryStockDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiStockSearchJson);
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj 
	 * @Description 库存分布查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiStockRoutingPage", method = RequestMethod.GET)
	public String matAffiStockRoutingPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/query/matAffiStockRouting";
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 库存分布查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiStockRouting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiStockRouting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiStockRoutingJson = matAffiStockSearchService.queryMatAffiStockRouting(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiStockRoutingJson);
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj 
	 * @Description 供应商采购汇总查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiSupCountPage", method = RequestMethod.GET)
	public String matAffiSupCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		
		return "hrp/mat/affi/query/matAffiSupCount";
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 供应商采购汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiSupCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiSupCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiSupCountJson = matAffiStockSearchService.queryMatAffiSupCount(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiSupCountJson);
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj 
	 * @Description 供应商采购明细查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiSupDetailPage", method = RequestMethod.GET)
	public String matAffiSupDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/query/matAffiSupDetail";
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 供应商采购明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiSupDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiSupDetailJson = matAffiStockSearchService.queryMatAffiSupDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiSupDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj 
	 * @Description 入库明细查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiInDetailPage", method = RequestMethod.GET)
	public String matAffiInDetail(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/query/matAffiInDetail";
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 入库明细查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiInDetailJson = matAffiStockSearchService.queryMatAffiInDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiInDetailJson);
	}
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 代销汇总查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiInInvCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInInvCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiInDetailJson = matAffiStockSearchService.queryMatAffiInInvCollection(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiInDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj 
	 * @Description 科室领用明细--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiOutDeptPage", method = RequestMethod.GET)
	public String matAffiOutDeptPage(Model mode) throws Exception {

		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		
		return "hrp/mat/affi/query/matAffiOutDept";
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 科室领用明细  查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiOutDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiOutDeptInDetailJson = matAffiStockSearchService.queryMatAffiOutDept(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiOutDeptInDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj 
	 * @Description 出库明细查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiOutDetailPage", method = RequestMethod.GET)
	public String matAffiOutDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/query/matAffiOutDetail";
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 出库明细 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiOutDetailJson = matAffiStockSearchService.queryMatAffiOutDetail(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiOutDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj
	 * @Description 出库材料汇总 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiOutInvCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutInvCollection(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiOutDetailJson = matAffiStockSearchService.queryMatAffiOutInvCollection(getPage(mapVo));
		
		return JSONObject.parseObject(matAffiOutDetailJson);
	}
	
	/**
	 * 2016/12/21 lxj
	 * @Description 出库明细-供应商信息页 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiOutSupMessagePage", method = RequestMethod.GET)
	public String matOutSupMessagePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("out_id", mapVo.get("out_id"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		return "hrp/mat/affi/query/matAffiOutSupMessage";
	}
	
	/**
	 * 2016/12/21 lxj
	 * @Description 出库明细-供应商信息 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiOutSupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutSupMessage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiOutSupMessageJson = matAffiStockSearchService.queryMatAffiOutSupMessage(getPage(mapVo));

		return JSONObject.parseObject(matAffiOutSupMessageJson);

	}
	
	/**
	 * 植入介入报表跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiOutImplantPage", method = RequestMethod.GET)
	public String matAffiOutImplantPage(Model mode) throws Exception {
		
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("hos_name", SessionManager.getHosName());
		
		return "hrp/mat/affi/query/matAffiOutImplant";
	}
	/**
	 * 植入介入报表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiOutImplant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutImplant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matAffiOutSupMessageJson = matAffiStockSearchService.queryMatAffiOutImplant(getPage(mapVo));
		return JSONObject.parseObject(matAffiOutSupMessageJson);

	}
	
	
	/**
	 * @Description 材料库存分布查询(供应商)
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiStockRoutingBySupPage", method = RequestMethod.GET)
	public String matAffiStockRoutingBySupPage(Model mode) throws Exception {
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/affi/query/matAffiStockRoutingBySup";
	}
	/**
	 * 代销入库台账报表
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/matAffiOutCertDetailPage", method = RequestMethod.GET)
	public String matAffiOutCertDetailPage(Model mode) throws Exception {
		return "hrp/mat/storage/query/matOutCertDetail";
	}
	/**
	 * 代销入库台账报表查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiOutCertDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiOutCertDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String queryOutDetail = matAffiStockSearchService.queryMatAffiOutCertDetail(getPage(mapVo));

		return JSONObject.parseObject(queryOutDetail);

	}
	
	/**
	 * 材料库存分布查询(供应商)
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */ 
	@RequestMapping(value = "/hrp/mat/affi/query/queryMatAffiStockRoutingBySup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiStockRoutingBySup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String[] dateBegin = String.valueOf(mapVo.get("begin_confirm_date")).split("-") ;
		
		String[] dateEnd = String.valueOf(mapVo.get("end_confirm_date")).split("-") ;
		
		mapVo.put("beginDate", dateBegin[0]+dateBegin[1]);
		mapVo.put("endDate", dateEnd[0] + dateEnd[1]);
		
		String matAffiOutSupMessageJson = matAffiStockSearchService.queryMatAffiStockRoutingBySup(getPage(mapVo));
		return JSONObject.parseObject(matAffiOutSupMessageJson);

	}
}
