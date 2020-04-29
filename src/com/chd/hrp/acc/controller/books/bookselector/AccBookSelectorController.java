package com.chd.hrp.acc.controller.books.bookselector;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccSubjServiceImpl;
import com.chd.hrp.sys.serviceImpl.CusDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.ProjDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.HosDictServiceImpl;
@Controller
public class AccBookSelectorController extends BaseController{
	private static Logger logger = Logger.getLogger(AccBookSelectorController.class);
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	@Resource(name = "projDictService")
	private final ProjDictServiceImpl projDictService = null;
	
	@Resource(name = "cusDictService")
	private final CusDictServiceImpl cusDictService = null;
	
	@Resource(name = "supDictService")
	private final SupDictServiceImpl supDictService = null;
	
	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;
	
	@Resource(name = "accSubjService")
	private final AccSubjServiceImpl accSubjService = null;
	
	@Resource(name = "hosDictService")
	private final HosDictServiceImpl hosDictService = null;
	
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookDeptSelectorPage", method = RequestMethod.GET)
	public String accBookDeptSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookDeptSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookEmpSelectorPage", method = RequestMethod.GET)
	public String accBookEmpSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookEmpSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookProjSelectorPage", method = RequestMethod.GET)
	public String accBookProjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookProjSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookSupSelectorPage", method = RequestMethod.GET)
	public String accBookSupSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookSupSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookStoreSelectorPage", method = RequestMethod.GET)
	public String accBookStoreSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookStoreSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookHosSelectorPage", method = RequestMethod.GET)
	public String accBookHosSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookHosSelector";
	}
	
	
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookCusSelectorPage", method = RequestMethod.GET)
	public String accBookCusSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookCusSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookProjEndOsSelectorPage", method = RequestMethod.GET)
	public String accBookProjEndOsSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/accselector/accBookProjEndOsSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accBookSubjSelectorPage", method = RequestMethod.GET)
	public String accBookSubjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData,String flag,String check_nodes,String sign) throws Exception {
		mode.addAttribute("listBoxData", new String((listBoxData.toString()).getBytes("iso-8859-1"),"utf-8"));
		mode.addAttribute("flag", flag.toString());
		mode.addAttribute("sign", sign.toString());
		if(!"".equals(check_nodes)&&null!= check_nodes){
			
			mode.addAttribute("check_nodes", check_nodes.toString());
			
		}
		
		return "hrp/acc/accselector/accBookSubjSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/bookselector/accSubjSelectorPage", method = RequestMethod.GET)
	public String accSubjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData ) throws Exception {
	 
			mode.addAttribute("listBoxData", new String((listBoxData.toString()).getBytes("iso-8859-1"),"utf-8"));
		  
		return "hrp/acc/accselector/accSubjSelector";
	}
	
	//科目tree
	@RequestMapping(value = "/hrp/acc/bookselector/querySubjBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySubjBySelector(
			@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String subj = accSubjService.queryAccSubjBySelector(mapVo);
		response.getWriter().print(subj);
		return null;
	}
	
	
	//部门tree
	@RequestMapping(value = "/hrp/acc/bookselector/queryDeptBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptBySelector(
			@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String deptDict = deptDictService.queryDeptDictBySelector(mapVo);
		response.getWriter().print(deptDict);
		return null;
	}
	
	//项目tree
	@RequestMapping(value = "/hrp/acc/bookselector/queryProjBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryProjBySelector(
			@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String projDict = projDictService.queryProjDictBySelector(mapVo);
		response.getWriter().print(projDict);
		return null;
	}
	
	
	//供应商tree
	@RequestMapping(value = "/hrp/acc/bookselector/querySupBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupBySelector(
				@RequestParam Map<String, Object> mapVo, Model mode,
				HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String supDict = supDictService.querySupDictBySelector(mapVo);
		response.getWriter().print(supDict);
		return null;
	}
	
	//客户tree
	@RequestMapping(value = "/hrp/acc/bookselector/queryCusBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCusBySelector(
				@RequestParam Map<String, Object> mapVo, Model mode,
				HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String cusDict = cusDictService.queryCusDictBySelector(mapVo);
		response.getWriter().print(cusDict);
		return null;
	}
	
	//库房tree
	@RequestMapping(value = "/hrp/acc/bookselector/queryStoreBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreBySelector(
				@RequestParam Map<String, Object> mapVo, Model mode,
				HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String storeDict = storeDictService.queryStoreDictBySelector(mapVo);
		response.getWriter().print(storeDict);
		return null;
	}
	
	//库房tree
	@RequestMapping(value = "/hrp/acc/bookselector/queryHosBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHosBySelector(
				@RequestParam Map<String, Object> mapVo, Model mode,
				HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String hosDict = hosDictService.queryHosDictBySelector(mapVo);
		response.getWriter().print(hosDict);
		return null;
	}
	
	
	
}
