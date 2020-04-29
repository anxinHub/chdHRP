/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.info.basic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.med.service.info.basic.MedPayTypeService;
/**
 * 
 * @Description:
 * MED_PAY_TYPE
 * @Table:
 * MED_PAY_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedPayTypeController extends BaseController{
	private static Logger logger = Logger.getLogger(MedPayTypeController.class);
	
	
	@Resource(name = "medPayTypeService")
	private final MedPayTypeService medPayTypeService = null;
   
    
	/**
	 * 支付方式主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/medPayTypeMainPage", method = RequestMethod.GET)
	public String medPayTypeMainPage(Model mode) throws Exception {

		return "hrp/med/info/basic/paytype/medPayTypeMain";

	}
	/**
	 * 支付方式--添加页面
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/medPayTypeAddPage", method = RequestMethod.GET)
	public String medPayTypeAddPage(Model mode) throws Exception {

		return "hrp/med/info/basic/paytype/medPayTypeAdd";

	}
	
	/**
	 * 支付方式--继承页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/medPayTypeExtendPage", method = RequestMethod.GET)
	public String medPayTypeExtendPage(Model mode) throws Exception {

		return "hrp/med/info/basic/paytype/medPayTypeExtend";

	}
	/**
	 * 支付方式--保存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/addMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_name").toString()));
        mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
		String medPayTypeJson = medPayTypeService.add(mapVo);

		return JSONObject.parseObject(medPayTypeJson);
		
	}
	/**
	 * 支付方式--查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/queryMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		mapVo.put("hos_id", SessionManager.getHosId());		
		mapVo.put("copy_code", SessionManager.getCopyCode());		
		String medPayType = medPayTypeService.query(mapVo);
		return JSONObject.parseObject(medPayType);
		
	}
	
	/**
	 * 支付方式--删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/deleteMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPayType(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("pay_code", id.split("@")[0]);
			mapVo.put("group_id", id.split("@")[1]);
			mapVo.put("hos_id", id.split("@")[2]);
			mapVo.put("copy_code", id.split("@")[3]);
            //mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String medPayTypeJson = medPayTypeService.deleteBatch(listVo);
	   return JSONObject.parseObject(medPayTypeJson);
			
	}
	
	/**
	 * 支付方式--更新页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/medPayTypeUpdatePage", method = RequestMethod.GET)
	
	public String medPayTypeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
      
		Map<String,Object> medPayType = medPayTypeService.queryByCode(mapVo);
		mode.addAttribute("medPayType", medPayType);
		
		return "hrp/med/info/basic/paytype/medPayTypeUpdate";
	}
	
	/**
	 * 支付方式--更新
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/updateMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatemedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());		
		}
		
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("pay_name").toString()));	   
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("pay_name").toString()));
	   
		String medPayTypeJson = medPayTypeService.update(mapVo);
		
		return JSONObject.parseObject(medPayTypeJson);
	}
	
	/**
	 * 支付方式--导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/importMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importmedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medPayTypeJson =null;// medPayTypeService.import(mapVo);
		
		return JSONObject.parseObject(medPayTypeJson);
	}
	
	/**
	 * 支付方式--继承
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/paytype/extendMedPayType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> extendMedPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());		
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());		
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());		
		}
		
		
		String medPayTypeJson = medPayTypeService.extendMedPayType(mapVo);

		return JSONObject.parseObject(medPayTypeJson);
	}

}

