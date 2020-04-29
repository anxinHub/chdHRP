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
import com.chd.hrp.acc.entity.AccVouchType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccVouchTypeServiceImpl;

/**
* @Title. @Description.
* 凭证类型
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccVouchTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccVouchTypeController.class);
	
	
	@Resource(name = "accVouchTypeService")
	private final AccVouchTypeServiceImpl accVouchTypeService = null;
   
    
	/**
	*凭证类型<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accvouchtype/accVouchTypeMainPage", method = RequestMethod.GET)
	public String accVouchTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accvouchtype/accVouchTypeMain";

	}
	/**
	*凭证类型<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouchtype/accVouchTypeAddPage", method = RequestMethod.GET)
	public String accVouchTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accvouchtype/accVouchTypeAdd";

	}
	
	/**
	*凭证类型<BR>
	*继承数据页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accvouchtype/accVouchTypeExtendPage", method = RequestMethod.GET)
	public String accVouchTypeExtendPage(Model mode) throws Exception {

		return "hrp/acc/accvouchtype/accVouchTypeExtend";

	}
	
	/**
	*凭证类型<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accvouchtype/addAccVouchType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccVouchType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("vouch_type_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("vouch_type_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String accVouchTypeJson = accVouchTypeService.addAccVouchType(mapVo);

		return JSONObject.parseObject(accVouchTypeJson);
		
	}
	/**
	*凭证类型<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accvouchtype/queryAccVouchType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccVouchType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());
		String accVouchType = accVouchTypeService.queryAccVouchType(getPage(mapVo));

		return JSONObject.parseObject(accVouchType);
		
	}
	/**
	*凭证类型<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accvouchtype/deleteAccVouchType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccVouchType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("vouch_type_code", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
            mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accVouchTypeJson = accVouchTypeService.deleteBatchAccVouchType(listVo);
	   return JSONObject.parseObject(accVouchTypeJson);
			
	}
	
	/**
	*凭证类型<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accvouchtype/accVouchTypeUpdatePage", method = RequestMethod.GET)
	
	public String accVouchTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccVouchType accVouchType = new AccVouchType();
		accVouchType = accVouchTypeService.queryAccVouchTypeByCode(mapVo);
		mode.addAttribute("vouch_type_code", accVouchType.getVouch_type_code());
		mode.addAttribute("group_id", accVouchType.getGroup_id());
		mode.addAttribute("hos_id", accVouchType.getHos_id());
		mode.addAttribute("copy_code", accVouchType.getCopy_code());
		mode.addAttribute("vouch_type_name", accVouchType.getVouch_type_name());
		mode.addAttribute("vouch_type_short", accVouchType.getVouch_type_short());
		mode.addAttribute("spell_code", accVouchType.getSpell_code());
		mode.addAttribute("wbx_code", accVouchType.getWbx_code());
		mode.addAttribute("is_stop", accVouchType.getIs_stop());
		return "hrp/acc/accvouchtype/accVouchTypeUpdate";
	}
	/**
	*凭证类型<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accvouchtype/updateAccVouchType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccVouchType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("vouch_type_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("vouch_type_name").toString()));
	   
		String accVouchTypeJson = accVouchTypeService.updateAccVouchType(mapVo);
		
		return JSONObject.parseObject(accVouchTypeJson);
	}
	/**
	*凭证类型<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accvouchtype/importAccVouchType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccVouchType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accVouchTypeJson = accVouchTypeService.importAccVouchType(mapVo);
		
		return JSONObject.parseObject(accVouchTypeJson);
	}
	
	/**
	*凭证类型<BR>
	*继承数据
	*/
	
	@RequestMapping(value = "/hrp/acc/accvouchtype/ExtendAccVouchType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> ExtendAccVouchType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());

		String accVouchTypeJson = accVouchTypeService.extendAccVouchType(mapVo);

		return JSONObject.parseObject(accVouchTypeJson);
	}

}

