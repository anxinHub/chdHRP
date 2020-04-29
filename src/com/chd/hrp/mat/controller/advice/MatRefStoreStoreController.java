/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.advice;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.advice.MatRefStoreStoreService;

/**
 * 
 * @Description:  041207 医嘱核销二级库与大库对应关系表
 * @Table: MAT_REF_STORE_STORE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatRefStoreStoreController extends BaseController {

	private static Logger logger = Logger.getLogger(MatRefStoreStoreController.class);

	// 引入Service服务
	@Resource(name = "matRefStoreStoreService")
	private final MatRefStoreStoreService matRefStoreStoreService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/matRefStoreStorePage", method = RequestMethod.GET)
	public String MatRefStoreStoreMainPage(Model mode) throws Exception {

		return "hrp/mat/advice/writeoffsetting/matRefStoreStore";
	}

	/**
	 * @Description 查询数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/queryMatRefStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatRefStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String matDura = matRefStoreStoreService.query(getPage(mapVo));
		
		return JSONObject.parseObject(matDura);
	}

	/**
	 * @Description 保存数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/saveMatRefStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatRefStoreStore(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson;
		try {
			
			matJson = matRefStoreStoreService.save(mapVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/deleteMatRefStoreStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatRefStoreStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {

		String matJson;
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
			
			matJson = matRefStoreStoreService.deleteBatch(listVo);
		} catch (Exception e) {
			
			matJson = "{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matJson);
	}

	// 是否
	@RequestMapping(value = "/hrp/mat/advice/writeoffsetting/queryMatRefStoreList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatRefStoreList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String hrpMatSelect = matRefStoreStoreService.queryMatRefStoreList(mapVo);
		return hrpMatSelect;
	}
}
