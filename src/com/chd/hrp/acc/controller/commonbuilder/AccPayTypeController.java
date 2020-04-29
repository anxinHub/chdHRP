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
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.acc.serviceImpl.commonbuilder.AccPayTypeServiceImpl;

/**
* @Title. @Description.
* 结算方式
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccPayTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(AccPayTypeController.class);
	
	
	@Resource(name = "accPayTypeService")
	private final AccPayTypeServiceImpl accPayTypeService = null;
   
    
	/**
	*结算方式<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accpaytype/accPayTypeMainPage", method = RequestMethod.GET)
	public String accPayTypeMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaytype/accPayTypeMain";

	}
	/**
	*结算方式<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accpaytype/accPayTypeAddPage", method = RequestMethod.GET)
	public String accPayTypeAddPage(Model mode) throws Exception {

		return "hrp/acc/accpaytype/accPayTypeAdd";

	}
	
	/**
	*结算方式<BR>
	*继承数据页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accpaytype/accPayTypeExtendPage", method = RequestMethod.GET)
	public String accPayTypeExtendPage(Model mode) throws Exception {

		return "hrp/acc/accpaytype/accPayTypeExtend";

	}
	/**
	*结算方式<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accpaytype/addAccPayType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String accPayTypeJson = accPayTypeService.addAccPayType(mapVo);

		return JSONObject.parseObject(accPayTypeJson);
		
	}
	/**
	*结算方式<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accpaytype/queryAccPayType", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String accPayType = accPayTypeService.queryAccPayType(getPage(mapVo));

		return JSONObject.parseObject(accPayType);
		
	}
	/**
	*结算方式<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accpaytype/deleteAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccPayType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("pay_code", id.split("@")[0]);
			mapVo.put("pay_type_code", id.split("@")[0]);
			mapVo.put("group_id", id.split("@")[1]);
			mapVo.put("hos_id", id.split("@")[2]);
			mapVo.put("copy_code", id.split("@")[3]);
			/*mapVo.put("acc_year", id.split("@")[4]);*/
            //mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accPayTypeJson = accPayTypeService.deleteBatchAccPayType(listVo);
	   return JSONObject.parseObject(accPayTypeJson);
			
	}
	
	/**
	*结算方式<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accpaytype/accPayTypeUpdatePage", method = RequestMethod.GET)
	
	public String accPayTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccPayType accPayType = new AccPayType();
		accPayType = accPayTypeService.queryAccPayTypeByCode(mapVo);
		if(accPayType != null){
			
			mode.addAttribute("pay_code", accPayType.getPay_code());
			mode.addAttribute("group_id", accPayType.getGroup_id());
			mode.addAttribute("hos_id", accPayType.getHos_id());
			mode.addAttribute("copy_code", accPayType.getCopy_code());
			/*mode.addAttribute("acc_year", accPayType.getAcc_year());*/
			mode.addAttribute("pay_name", accPayType.getPay_name());
			mode.addAttribute("pay_type", accPayType.getPay_type());
			mode.addAttribute("type_name", accPayType.getType_name());
			/*mode.addAttribute("subj_id", accPayType.getSubj_id());
			mode.addAttribute("subj_name", accPayType.getSubj_name());*/
			mode.addAttribute("spell_code", accPayType.getSpell_code());
			mode.addAttribute("wbx_code", accPayType.getWbx_code());
			mode.addAttribute("is_stop", accPayType.getIs_stop());
			mode.addAttribute("source_code", accPayType.getSource_code());
		}
		return "hrp/acc/accpaytype/accPayTypeUpdate";
	}
	/**
	*结算方式<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accpaytype/updateAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
	   
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_name").toString()));
	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_name").toString()));
	   
		String accPayTypeJson = accPayTypeService.updateAccPayType(mapVo);
		
		return JSONObject.parseObject(accPayTypeJson);
	}
	/**
	*结算方式<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accpaytype/importAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accPayTypeJson = accPayTypeService.importAccPayType(mapVo);
		
		return JSONObject.parseObject(accPayTypeJson);
	}
	
	/**
	*结算方式<BR>
	*继承数据
	*/
	
	@RequestMapping(value = "/hrp/acc/accpaytype/extendAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
		}

		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("acct_year", SessionManager.getAcctYear());
		
		String accPayTypeJson = accPayTypeService.extendAccPayType(mapVo);

		return JSONObject.parseObject(accPayTypeJson);
	}

}

