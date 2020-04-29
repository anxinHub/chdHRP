/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.commonbuilder;
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
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCurServiceImpl;

/**
* @Title. @Description.
* 币种
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCurController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCurController.class);
	
	
	@Resource(name = "accCurService")
	private final AccCurServiceImpl accCurService = null;
   
    
	/**
	*币种<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccur/accCurMainPage", method = RequestMethod.GET)
	public String accCurMainPage(Model mode) throws Exception {

		return "hrp/acc/acccur/accCurMain";

	}
	/**
	*币种<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccur/accCurAddPage", method = RequestMethod.GET)
	public String accCurAddPage(Model mode) throws Exception {

		return "hrp/acc/acccur/accCurAdd";

	}
	
	/**
	*币种<BR>
	*继承数据页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccur/accCurExtendPage", method = RequestMethod.GET)
	public String accCurExtendPage(Model mode) throws Exception {

		return "hrp/acc/acccur/accCurExtend";

	}
	
	/**
	*币种<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acccur/addAccCur", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cur_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cur_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("acc_year", SessionManager.getAcctYear());
		String accCurJson = accCurService.addAccCur(mapVo);

		return JSONObject.parseObject(accCurJson);
		
	}
	/**
	*币种<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acccur/queryAccCur", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
        mapVo.put("acc_year", SessionManager.getAcctYear());
		String accCur = accCurService.queryAccCur(getPage(mapVo));

		return JSONObject.parseObject(accCur);
		
	}
	/**
	*币种<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acccur/deleteAccCur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCur(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("cur_code", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
            mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            mapVo.put("acc_year", id.split("@")[4]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accCurJson = accCurService.deleteBatchAccCur(listVo);
	   return JSONObject.parseObject(accCurJson);
			
	}
	
	/**
	*币种<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccur/accCurUpdatePage", method = RequestMethod.GET)
	
	public String accCurUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCur accCur = new AccCur();
        if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		if(mapVo.get("cur_code") == null || "".equals(mapVo.get("cur_code"))){
			return "hrp/acc/acccur/accCurAdd";
		}
		
		accCur = accCurService.queryAccCurByCode(mapVo);
		mode.addAttribute("cur_code", accCur.getCur_code());
		mode.addAttribute("group_id", accCur.getGroup_id());
		mode.addAttribute("hos_id", accCur.getHos_id());
		mode.addAttribute("copy_code", accCur.getCopy_code());
		mode.addAttribute("acc_year", accCur.getAcc_year());
		mode.addAttribute("cur_name", accCur.getCur_name());
		mode.addAttribute("cur_num", accCur.getCur_num());
		mode.addAttribute("cur_rate", accCur.getCur_rate());
		mode.addAttribute("cur_plan", accCur.getCur_plan());
		mode.addAttribute("is_self", accCur.getIs_self());
		mode.addAttribute("spell_code", accCur.getSpell_code());
		mode.addAttribute("wbx_code", accCur.getWbx_code());
		mode.addAttribute("is_stop", accCur.getIs_stop());
		return "hrp/acc/acccur/accCurUpdate";
	}
	/**
	*币种<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acccur/updateAccCur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cur_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cur_name").toString()));
	   
		String accCurJson = accCurService.updateAccCur(mapVo);
		
		return JSONObject.parseObject(accCurJson);
	}
	/**
	*币种<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acccur/importAccCur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCurJson = accCurService.importAccCur(mapVo);
		
		return JSONObject.parseObject(accCurJson);
	}
	
	/**
	*币种<BR>
	*继承数据
	*/
	
	@RequestMapping(value = "/hrp/acc/acccur/extendAccCur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccCur(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());

		String accCurJson = accCurService.extendAccCur(mapVo);

		return JSONObject.parseObject(accCurJson);
	}

}

