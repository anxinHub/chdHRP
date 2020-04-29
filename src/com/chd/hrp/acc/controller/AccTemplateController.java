/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.util.*;
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
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccTemplate;
import com.chd.hrp.acc.serviceImpl.AccTemplateServiceImpl;

/**
* @Title. @Description.
* 凭证模板主表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccTemplateController extends BaseController{
	private static Logger logger = Logger.getLogger(AccTemplateController.class);
	
	
	@Resource(name = "accTemplateService")
	private final AccTemplateServiceImpl accTemplateService = null;
   
    
	/**
	*凭证模板主表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acctemplate/accTemplateMainPage", method = RequestMethod.GET)
	public String accTemplateMainPage(Model mode) throws Exception {

		return "hrp/acc/acctemplate/accTemplateMain";

	}
	/**
	*凭证模板主表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acctemplate/accTemplateAddPage", method = RequestMethod.GET)
	public String accTemplateAddPage(Model mode) throws Exception {

		return "hrp/acc/acctemplate/accTemplateAdd";

	}
	/**
	*凭证模板主表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acctemplate/addAccTemplate", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accTemplateJson = accTemplateService.addAccTemplate(mapVo);

		return JSONObject.parseObject(accTemplateJson);
		
	}
	/**
	*凭证模板主表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acctemplate/queryAccTemplate", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
			mapVo.put("group_id", SessionManager.getGroupId());
			
			mapVo.put("hos_id", SessionManager.getHosId());
			
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("user_name", SessionManager.getUserName());
		
		String accTemplate = accTemplateService.queryAccTemplate(getPage(mapVo));

		return JSONObject.parseObject(accTemplate);
		
	}
	/**
	*凭证模板主表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acctemplate/deleteAccTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccTemplate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accTemplateJson = accTemplateService.deleteBatchAccTemplate(listVo);
	   return JSONObject.parseObject(accTemplateJson);
			
	}
	
	/**
	*凭证模板主表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acctemplate/accTemplateUpdatePage", method = RequestMethod.GET)
	
	public String accTemplateUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccTemplate accTemplate = new AccTemplate();
		accTemplate = accTemplateService.queryAccTemplateByCode(mapVo);
		mode.addAttribute("vouch_id", accTemplate.getVouch_id());
		mode.addAttribute("group_id", accTemplate.getGroup_id());
		mode.addAttribute("hos_id", accTemplate.getHos_id());
		mode.addAttribute("copy_code", accTemplate.getCopy_code());
		mode.addAttribute("acc_year", accTemplate.getAcc_year());
		mode.addAttribute("vouch_type_code", accTemplate.getVouch_type_code());
		mode.addAttribute("vouch_date", accTemplate.getVouch_date());
		mode.addAttribute("vouch_att_num", accTemplate.getVouch_att_num());
		mode.addAttribute("template_sort", accTemplate.getTemplate_sort());
		mode.addAttribute("template_name", accTemplate.getTemplate_name());
		mode.addAttribute("template_id", accTemplate.getTemplate_id());
		mode.addAttribute("note", accTemplate.getNote());
		return "hrp/acc/acctemplate/accTemplateUpdate";
	}
	/**
	*凭证模板主表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acctemplate/updateAccTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accTemplateJson = accTemplateService.updateAccTemplate(mapVo);
		
		return JSONObject.parseObject(accTemplateJson);
	}
	/**
	*凭证模板主表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acctemplate/importAccTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accTemplateJson = accTemplateService.importAccTemplate(mapVo);
		
		return JSONObject.parseObject(accTemplateJson);
	}
	
	@RequestMapping(value = "/hrp/acc/acctemplate/queryAccVouchSubj", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccVouchSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accTemplateJson = accTemplateService.queryAccVouchSubj(getPage(mapVo));
		
		return JSONObject.parseObject(accTemplateJson);
	}
	
	@RequestMapping(value = "/hrp/acc/acctemplate/accTemplateAllMainPage", method = RequestMethod.GET)
	public String accTemplateAllMainPage(Model mode) throws Exception {

		return "hrp/acc/acctemplate/accTemplateAllMain";

	}
	
	@RequestMapping(value = "/hrp/acc/acctemplate/extendAccTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccTemplate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accTemplateJson = accTemplateService.extendAccTemplate(mapVo);
		
		return JSONObject.parseObject(accTemplateJson);
	}

}

