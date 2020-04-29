/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.ass.controller.dict;
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
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.serviceImpl.AccParaServiceImpl;
 
/**
* @Title. @Description.
* 系统参数
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AssParaController extends BaseController{
	private static Logger logger = Logger.getLogger(AssParaController.class);
	
	
	@Resource(name = "accParaService")
	private final AccParaServiceImpl accParaService = null;
   
    
	/**
	*系统参数<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/ass/asspara/assParaMainPage", method = RequestMethod.GET)
	public String assParaMainPage(Model mode) throws Exception {

		return "hrp/ass/asspara/assParaMain";

	}
	
	/**
	*系统参数<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/ass/asspara/queryAssPara", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAssPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("mod_code", "05");
		String accPara = accParaService.queryAccPara(getPage(mapVo));

		return JSONObject.parseObject(accPara);
		
	}
	
	/**
	*系统参数<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/ass/asspara/updateAssPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String modCode=SessionManager.getModCode();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("mod_code", modCode);
		
		String accParaJson = "" ;
		
		try{
		 
			accParaJson = accParaService.updateBatchAccPara(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(accParaJson);
	}
	
}

