/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.dhc;

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
import com.chd.base.ChdToken;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.dhc.MatDhcDictService;

/**  
 * 
 * @Description:  东华基础字典读取
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com 
 * @Version: 1.0  
 */
 
@Controller
public class MatDhcDictController extends BaseController {      

	private static Logger logger = Logger.getLogger(MatDhcDictController.class);

	// 引入Service服务
	@Resource(name = "matDhcDictService")
	private final MatDhcDictService matDhcDictService = null;

	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dhc/dict/matDhcDictPage", method = RequestMethod.GET)
	public String MatStorageInMainPage(Model mode) throws Exception {
		
		return "hrp/mat/dhc/dict/matDhcDict";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/dhc/dict/matDhcDictImpPage", method = RequestMethod.GET)
	public String matStorageInAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		return "hrp/mat/dhc/dict/choiceImp";
	}

	/**
	 * @Description 查询导入数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dhc/dict/queryMatDhcDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatDhcDict(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson = matDhcDictService.queryMatDhcDict(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 导入东华字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/hrp/mat/dhc/dict/impMatDhcDict", method = RequestMethod.POST)
	@ResponseBody
	@ChdToken
	public Map<String, Object> impMatDhcDict(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
	
		
		Map<String,Object> retMap;
		try {
			retMap = matDhcDictService.impMatDhcDict(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", false);
			retMap.put("error", "操作失败！");
		}

		return retMap;
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/dhc/dict/deleteMatDhcDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatDhcDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("in_id", ids[3]);
			mapVo.put("is_com", "0");
			listVo.add(mapVo);
		}
		
		Map<String,Object> retMap;
		try {
			retMap = matDhcDictService.deleteMatDhcDict(listVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", false);
			retMap.put("error", "操作失败！");
		}

		return retMap;
	}
}
