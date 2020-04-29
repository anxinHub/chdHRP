
package com.chd.hrp.acc.controller.commonbuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccCashierType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccCashierTypeServiceImpl;


/**
* 出纳类型
*/


@Controller
public class AccCashierTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCashierTypeController.class);
	
	
	@Resource(name = "accCashierTypeService")
	private final AccCashierTypeServiceImpl accCashierTypeService = null;
   
    
	/**
	*出纳类型<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashiertype/accCashierTypeMainPage", method = RequestMethod.GET)
	public String accVouchTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/acccashiertype/accCashierTypeMain";

	}
	/**
	*出纳类型<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccashiertype/accCashierTypeAddPage", method = RequestMethod.GET)
	public String accCashierTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/acccashiertype/accCashierTypeAdd";

	}
	
	/**
	*出纳类型<BR>
	*继承数据页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acccashiertype/accCashierTypeExtendPage", method = RequestMethod.GET)
	public String accCashierTypeExtendPage(Model mode) throws Exception {

		return "hrp/acc/acccashiertype/accCashierTypeExtend";

	}
	
	/**
	*出纳类型<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acccashiertype/addAccCashierType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCashierType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String accCashierTypeJson = accCashierTypeService.addAccCashierType(mapVo);

		return JSONObject.parseObject(accCashierTypeJson);
		
	}
	/**
	*出纳类型<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acccashiertype/queryAccCashierType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCashierType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());
		String accCashierType = accCashierTypeService.queryAccCashierType(getPage(mapVo));

		return JSONObject.parseObject(accCashierType);
		
	}
	/**
	*出纳类型<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acccashiertype/deleteAccCashierType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCashierType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("tell_type_code", id.split("@")[0]);//实际实体类变量
            mapVo.put("group_id", id.split("@")[1]);//实际实体类变量
            mapVo.put("hos_id", id.split("@")[2]);//实际实体类变量
            mapVo.put("copy_code", id.split("@")[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String accCashierTypeJson = accCashierTypeService.deleteBatchAccCashierType(listVo);
	   return JSONObject.parseObject(accCashierTypeJson);
			
	}
	
	/**
	*出纳类型<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashiertype/accCashierTypeUpdatePage", method = RequestMethod.GET)
	
	public String accCashierTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccCashierType accCashierType = new AccCashierType();
		accCashierType = accCashierTypeService.queryAccCashierTypeByCode(mapVo);
		mode.addAttribute("tell_type_code", accCashierType.getTell_type_code());
		mode.addAttribute("group_id", accCashierType.getGroup_id());
		mode.addAttribute("hos_id", accCashierType.getHos_id());
		mode.addAttribute("copy_code", accCashierType.getCopy_code());
		mode.addAttribute("tell_type_name", accCashierType.getTell_type_name());
		mode.addAttribute("is_stop", accCashierType.getIs_stop());
		mode.addAttribute("kind_code", accCashierType.getKind_code());
		mode.addAttribute("vouch_type_code", accCashierType.getVouch_type_code());
		mode.addAttribute("vouch_type_name", accCashierType.getVouch_type_name());
		return "hrp/acc/acccashiertype/accCashierTypeUpdate";
	}
	/**
	*出纳类型<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acccashiertype/updateAccCashierType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashierType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		String accCashierTypeJson = accCashierTypeService.updateAccCashierType(mapVo);
		
		return JSONObject.parseObject(accCashierTypeJson);
	}
	/**
	*出纳类型<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashiertype/importAccCashierType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccCashierType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accCashierTypeJson = accCashierTypeService.importAccCashierType(mapVo);
		
		return JSONObject.parseObject(accCashierTypeJson);
	}
	
	

}

