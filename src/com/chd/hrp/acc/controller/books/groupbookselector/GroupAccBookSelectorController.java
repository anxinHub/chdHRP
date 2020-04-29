package com.chd.hrp.acc.controller.books.groupbookselector;

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
import com.chd.hrp.sys.serviceImpl.CusDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.HosDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.ProjDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.baseData.SysAccSubjServiceImpl;
@Controller
public class GroupAccBookSelectorController extends BaseController{
	private static Logger logger = Logger.getLogger(GroupAccBookSelectorController.class);
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
	
	@Resource(name = "sysAccSubjService")
	private final SysAccSubjServiceImpl sysAccSubjService = null;
	
	@Resource(name = "hosDictService")
	private final HosDictServiceImpl hosDictService = null;
	
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookDeptSelectorPage", method = RequestMethod.GET)
	public String groupAccBookDeptSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData,String flag,String rela_code) throws Exception {
	
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
	    mode.addAttribute("rela_code", rela_code.toString());
		return "hrp/acc/groupaccselector/groupAccBookDeptSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupccBookEmpSelectorPage", method = RequestMethod.GET)
	public String groupccBookEmpSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookEmpSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookProjSelectorPage", method = RequestMethod.GET)
	public String groupAccBookProjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookProjSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookSupSelectorPage", method = RequestMethod.GET)
	public String groupAccBookSupSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookSupSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookStoreSelectorPage", method = RequestMethod.GET)
	public String groupAccBookStoreSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookStoreSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookHosSelectorPage", method = RequestMethod.GET)
	public String groupAccBookHosSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookHosSelector";
	}
	
	
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookCusSelectorPage", method = RequestMethod.GET)
	public String groupAccBookCusSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookCusSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookProjEndOsSelectorPage", method = RequestMethod.GET)
	public String groupAccBookProjEndOsSelectorPage(Model mode,String listBoxData,String flag) throws Exception {
		mode.addAttribute("listBoxData", listBoxData.toString());
		mode.addAttribute("flag", flag.toString());
		return "hrp/acc/groupaccselector/groupAccBookProjEndOsSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccBookSubjSelectorPage", method = RequestMethod.GET)
	public String groupAccBookSubjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData,String flag,String check_nodes,String sign,String rela_code) throws Exception {
		mode.addAttribute("listBoxData", new String((listBoxData.toString()).getBytes("iso-8859-1"),"utf-8"));
		mode.addAttribute("flag", flag.toString());
		mode.addAttribute("sign", sign.toString());
		mode.addAttribute("rela_code", rela_code.toString());
		if(!"".equals(check_nodes)&&null!= check_nodes){
			
			mode.addAttribute("check_nodes", check_nodes.toString());
			
		}
		
		return "hrp/acc/groupaccselector/groupAccBookSubjSelector";
	}
	
	@RequestMapping(value = "/hrp/acc/groupbookselector/groupAccSubjSelectorPage", method = RequestMethod.GET)
	public String groupAccSubjSelectorPage(Model mode,@RequestParam Map<String, Object> listBoxData, String rela_code,String flag ) throws Exception {
			mode.addAttribute("listBoxData", new String((listBoxData.toString()).getBytes("iso-8859-1"),"utf-8")); 
			mode.addAttribute("flag", flag.toString());
			mode.addAttribute("rela_code", rela_code.toString()); 
		return "hrp/acc/groupaccselector/groupAccSubjSelector";
	}
	
	//科目tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupSubjBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupSubjBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		mapVo.put("acc_year", SessionManager.getAcctYear());
		String subj = sysAccSubjService.queryGroupSubjBySelector(mapVo);
		response.getWriter().print(subj);
		return null;
	}
	
	
	//部门tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupDeptBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupDeptBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		String deptDict = deptDictService.queryGroupDeptDictBySelector(mapVo);
		response.getWriter().print(deptDict);
		return null;
	}
	
	//项目tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupProjBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupProjBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		String projDict = projDictService.queryGroupProjDictBySelector(mapVo);
		response.getWriter().print(projDict);
		return null;
	}
	
	
	//供应商tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupSupBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupSupBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		String supDict = supDictService.queryGroupSupDictBySelector(mapVo);
		response.getWriter().print(supDict);
		return null;
	}
	
	//客户tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupCusBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupCusBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		String cusDict = cusDictService.queryGroupCusDictBySelector(mapVo);
		response.getWriter().print(cusDict);
		return null;
	}
	
	//库房tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupStoreBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupStoreBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		String storeDict = storeDictService.queryStoreDictBySelector(mapVo);
		response.getWriter().print(storeDict);
		return null;
	}
	
	//单位tree
	@RequestMapping(value = "/hrp/acc/groupbookselector/queryGroupHosBySelector", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGroupHosBySelector(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse response) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		/*mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());*/
		String hosDict = hosDictService.queryGroupHosDictBySelector(mapVo);
		response.getWriter().print(hosDict);
		return null;
	}
	
	
	
}
