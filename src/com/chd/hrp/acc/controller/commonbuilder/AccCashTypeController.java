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
import com.chd.hrp.acc.entity.AccCashType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCashTypeServiceImpl;

/**
* @Title. @Description.
* 现金流量类别
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCashTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCashTypeController.class);
	
	
	@Resource(name = "accCashTypeService")
	private final AccCashTypeServiceImpl accCashTypeService = null;
   
    
	/**
	*现金流量类别<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashtype/accCashTypeMainPage", method = RequestMethod.GET)
	public String accCashTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/acccashitem/accCashTypeMain";

	}
	/**
	*现金流量类别<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccashtype/accCashTypeAddPage", method = RequestMethod.GET)
	public String accCashTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/acccashitem/accCashTypeAdd";

	}
	/**
	*现金流量类别<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acccashtype/addAccCashType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCashType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cash_type_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cash_type_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String accCashTypeJson = accCashTypeService.addAccCashType(mapVo);

		return JSONObject.parseObject(accCashTypeJson);
		
	}
	/**
	*现金流量类别<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acccashtype/queryAccCashType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCashType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accCashType = accCashTypeService.queryAccCashType(getPage(mapVo));

		return JSONObject.parseObject(accCashType);
		
	}
	/**
	*现金流量类别<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acccashtype/deleteAccCashType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCashType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("cash_type_id", id.split("@")[0]);//实际实体类变量
			mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
			mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accCashTypeJson = accCashTypeService.deleteBatchAccCashType(listVo);
	   return JSONObject.parseObject(accCashTypeJson);
			
	}
	
	/**
	*现金流量类别<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashtype/accCashTypeUpdatePage", method = RequestMethod.GET)
	
	public String accCashTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCashType accCashType = new AccCashType();
		accCashType = accCashTypeService.queryAccCashTypeByCode(mapVo);
		mode.addAttribute("cash_type_id", accCashType.getCash_type_id());
		mode.addAttribute("group_id", accCashType.getGroup_id());
		mode.addAttribute("hos_id", accCashType.getHos_id());
		mode.addAttribute("copy_code", accCashType.getCopy_code());
		mode.addAttribute("cash_type_code", accCashType.getCash_type_code());
		mode.addAttribute("cash_type_name", accCashType.getCash_type_name());
		mode.addAttribute("spell_code", accCashType.getSpell_code());
		mode.addAttribute("wbx_code", accCashType.getWbx_code());
		mode.addAttribute("is_stop", accCashType.getIs_stop());
		return "hrp/acc/acccashitem/accCashTypeUpdate";
	}
	/**
	*现金流量类别<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acccashtype/updateAccCashType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cash_type_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cash_type_name").toString()));
	   
		String accCashTypeJson = accCashTypeService.updateAccCashType(mapVo);
		
		return JSONObject.parseObject(accCashTypeJson);
	}
	/**
	*现金流量类别<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashtype/importAccCashType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCashType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCashTypeJson = accCashTypeService.importAccCashType(mapVo);
		
		return JSONObject.parseObject(accCashTypeJson);
	}

	@RequestMapping(value = "/hrp/acc/acccashtype/queryAccCashTypeByMenu", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccCashTypeByMenu(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());
		
		String group = accCashTypeService.queryAccCashTypeByMenu(mapVo);

		return JSONObject.parseObject(group);
		
	}
}

