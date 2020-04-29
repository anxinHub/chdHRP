package com.chd.hrp.med.controller.affi.query;

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
import com.chd.hrp.med.service.affi.query.MedAffiStockSearchService;

/**
 * @Description:代销-库存查询
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAffiStockSearchController extends BaseController {
	
	private static Logger logger = Logger.getLogger(MedAffiStockSearchController.class);
	
	@Resource(name = "medAffiStockSearchService")
	private final MedAffiStockSearchService medAffiStockSearchService = null;
	/**
	 * @Time 2016/12/20
	 * @author lxj
	 * @Description 库存明细账查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiStockDetailPage", method = RequestMethod.GET)
	public String medAffiStockDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/affi/query/medAffiStockDetail";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiStorageQueryStockDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiStorageQueryStockDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiStockSearchJson = medAffiStockSearchService.queryMedAffiStorageQueryStockDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiStockSearchJson);
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj 
	 * @Description 库存分布查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiStockRoutingPage", method = RequestMethod.GET)
	public String medAffiStockRoutingPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiStockRouting";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiStockRouting", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiStockRouting(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiStockRoutingJson = medAffiStockSearchService.queryMedAffiStockRouting(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiStockRoutingJson);
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj 
	 * @Description 供应商采购汇总查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiSupCountPage", method = RequestMethod.GET)
	public String medAffiSupCountPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiSupCount";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiSupCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiSupCount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiSupCountJson = medAffiStockSearchService.queryMedAffiSupCount(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiSupCountJson);
	}
	
	/**
	 * @Time 2016/12/20
	 * @author lxj 
	 * @Description 供应商采购明细查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiSupDetailPage", method = RequestMethod.GET)
	public String medAffiSupDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiSupDetail";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiSupDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiSupDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiSupDetailJson = medAffiStockSearchService.queryMedAffiSupDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiSupDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj 
	 * @Description 入库明细查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiInDetailPage", method = RequestMethod.GET)
	public String medAffiInDetail(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiInDetail";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiInDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiInDetailJson = medAffiStockSearchService.queryMedAffiInDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiInDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj 
	 * @Description 科室领用明细--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiOutDeptPage", method = RequestMethod.GET)
	public String medAffiOutDeptPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiOutDept";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiOutDept", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiOutDeptInDetailJson = medAffiStockSearchService.queryMedAffiOutDept(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiOutDeptInDetailJson);
	}
	
	/**
	 * @Time 2016/12/21
	 * @author lxj 
	 * @Description 出库明细查询--主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiOutDetailPage", method = RequestMethod.GET)
	public String medAffiOutDetailPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiOutDetail";
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
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiOutDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiOutDetailJson = medAffiStockSearchService.queryMedAffiOutDetail(getPage(mapVo));
		
		return JSONObject.parseObject(medAffiOutDetailJson);
	}
	
	/**
	 * 2016/12/21 lxj
	 * @Description 出库明细-供应商信息页 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiOutSupMessagePage", method = RequestMethod.GET)
	public String medOutSupMessagePage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("inv_id", mapVo.get("inv_id"));
		mode.addAttribute("out_id", mapVo.get("out_id"));
		mode.addAttribute("store_id", mapVo.get("store_id"));
		
		return "hrp/med/affi/query/medAffiOutSupMessage";
	}
	
	/**
	 * 2016/12/21 lxj
	 * @Description 出库明细-供应商信息 查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiOutSupMessage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutSupMessage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiOutSupMessageJson = medAffiStockSearchService.queryMedAffiOutSupMessage(getPage(mapVo));

		return JSONObject.parseObject(medAffiOutSupMessageJson);

	}
	
	/**
	 * 植入介入报表跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/medAffiOutImplantPage", method = RequestMethod.GET)
	public String medAffiOutImplantPage(Model mode) throws Exception {
		
		mode.addAttribute("p08005", MyConfig.getSysPara("08005"));
		
		return "hrp/med/affi/query/medAffiOutImplant";
	}
	/**
	 * 植入介入报表
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/affi/query/queryMedAffiOutImplant", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiOutImplant(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medAffiOutSupMessageJson = medAffiStockSearchService.queryMedAffiOutImplant(getPage(mapVo));
		return JSONObject.parseObject(medAffiOutSupMessageJson);

	}
	
}
