package com.chd.hrp.hr.controller.sc;

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
import com.chd.hrp.hr.service.sc.HrPageTemplateService;



/**
 * 
 * @ClassName: HrPageTemplateController
 * @Description: 页面设计器模板
 * @author zn
 * @date 
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sc/hrpagetemplate")
public class HrPageTemplateController extends BaseController{

	private static Logger logger = Logger.getLogger(HrPageTemplateController.class);
	
	// 引入Service服务
	@Resource(name = "hrPageTemplateService")
	private final HrPageTemplateService hrPageTemplateService = null;
	
  /**
	* 
	* @Title: hrPageTemplateMainPage
	* @Description: 模板页面跳转
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageTemplateMainPage", method = RequestMethod.GET)
	public String hrPageTemplateMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagetemplate/hrPageTemplateMain";
	}
	
	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrPageTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrPageTemplate(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
    
		String hrPageTemplateJson =  hrPageTemplateService.queryHrPageTemplate(mapVo);
		
		return JSONObject.parseObject(hrPageTemplateJson);
	}
	
	/**
	* 
	* @Title: hrPageTemplateImgMainPage
	* @Description: 模板图形查看
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageTemplateImgMainPage", method = RequestMethod.GET)
	public String hrPageTemplateImgMainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("template_path", mapVo.get("template_path"));
		return "hrp/hr/sc/hrpagetemplate/hrPageTemplateImgMain";
	}
	
  /**
	* 
	* @Title: hrPageTemplateStyleTestMainPage
	* @Description: 模板图形截图
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年3月18日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrPageTemplateStyleTestMainPage", method = RequestMethod.GET)
	public String hrPageTemplateStyleTestMainPage(Model mode) throws Exception {
		return "hrp/hr/sc/hrpagetemplate/hrPageTemplateStyleTestMain";
	}
}
