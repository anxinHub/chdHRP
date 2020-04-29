package com.chd.hrp.ass.controller.assinterfacejournal;

import java.util.Map;

import javax.annotation.Resource;

import org.nutz.dao.entity.annotation.Readonly;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.ass.service.assinterfacejournal.AssInterfaceJournalService;

@Controller
public class AssInterfaceJournalController extends BaseController{

	@Resource(name="assInterfaceJournalService")
	private final AssInterfaceJournalService assInterfaceJournalService=null;
	/**
	 * 接口日志主页跳转
	 * @param maoVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assinterfacejournal/assInterfaceJournalMain",method=RequestMethod.GET)
	public String AssInterfaceJournalMain(@RequestParam Map<String, Object> maoVo,Model model){
		
		
		return "hrp/ass/assinterfacejournal/assInterfaceJournalMain";
	}
	/**
	 * 主表查询方法
	 * @param mapVo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assinterfacejournal/queryAssInterfaceJournal",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInterfaceJournal(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String res=assInterfaceJournalService.queryAssInterfaceJournal(getPage(mapVo));
		return JSON.parseObject(res);
	}
	
	
	/**
	 * 保存（其他模块调用）
	 * @param mapVo
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value="/hrp/ass/assinterfacejournal/insertAssInterfaceJournal",method=RequestMethod.POST)
	@ResponseBody
	public int insertAssInterfaceJournal(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		int res=assInterfaceJournalService.insertAssInterfaceJournal(mapVo);
		return res;
	}
	*/
	/**
	 * 补录
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assinterfacejournal/addMakeUpInterface",method=RequestMethod.POST)
	@ResponseBody
	public String addMakeUpInterface(@RequestParam Map<String, Object> mapVo,Model model){
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//System.out.println(mapVo);
		return assInterfaceJournalService.addMakeUpInterface(mapVo);
	}
	
	/**
	 * 接口分类
	 * @return
	 */
	@RequestMapping(value="/hrp/ass/assinterfacejournal/queryPItype",method=RequestMethod.POST)
	@ResponseBody
	public String queryPItype(@RequestParam Map<String, Object> mapVo,Model model){
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String res=assInterfaceJournalService.queryPItype(mapVo);
		return res;
	}
}
