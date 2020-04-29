/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.advice;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.med.service.advice.MedRefStoreStoreService;

/**
 * 
 * @Description:  081207 医嘱核销二级库与大库对应关系表
 * @Table: MED_REF_STORE_STORE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedRefStoreStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MedRefStoreStoreController.class);

	// 引入Service服务
	@Resource(name = "medRefStoreStoreService")
	private final MedRefStoreStoreService medRefStoreStoreService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/medRefStoreStorePage", method = RequestMethod.GET)
	public String MedRefStoreStoreMainPage(Model mode) throws Exception {

		return "hrp/med/advice/writeoffsetting/medRefStoreStore";
	}

	/**
	 * @Description 查询数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/queryMedRefStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedRefStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		String medDura = medRefStoreStoreService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medDura);
	}

	/**
	 * @Description 保存数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/saveMedRefStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedRefStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson;
		try {
			
			medJson = medRefStoreStoreService.save(mapVo);
		} catch (Exception e) {
			
			medJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/deleteMedRefStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedRefStoreStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String medJson;
		try {
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
				mapVo.put("old_id", ids[3]);
				listVo.add(mapVo);
			}
			
			medJson = medRefStoreStoreService.deleteBatch(listVo);
		} catch (Exception e) {
			
			medJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(medJson);
	}

	// 是否
	@RequestMapping(value = "/hrp/med/advice/writeoffsetting/queryMedRefStoreList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedRefStoreList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMedSelect = medRefStoreStoreService.queryMedRefStoreList(mapVo);
		return hrpMedSelect;
	}
}
