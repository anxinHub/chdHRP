/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.storage.checkMobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.mat.service.storage.checkMobile.MatCheckMobileMainService;

/**
 * @Description:
 * @Table: MAT_CHECK_MOBILE 高值材料盘点任务单
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatCheckMobileMainController extends BaseController {

	private static Logger logger = Logger.getLogger(MatCheckMobileMainController.class);

	// 引入Service服务
	@Autowired
	private MatCheckMobileMainService matCheckMobileMainService;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/matCheckMobileMainMainPage", method = RequestMethod.GET)
	public String matCheckMainMainPage(Model mode) throws Exception {

		return "hrp/mat/storage/checkmobile/matChecMobilekMainMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/matCheckMobileMainAddPage", method = RequestMethod.GET)
	public String matCheckMainAddPage(Model mode) throws Exception {

		return "hrp/mat/storage/checkmobile/matCheckMobileMainAdd";

	}

	
	/**
	 * @Description 添加数据前检查是否已经生成过数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/existMatCheckMobileByCreateDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> existMatCheckMobileByCreateDate(@RequestParam Map<String, Object> mapVo) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matJson;
		try {
			matJson = matCheckMobileMainService.existMatCheckMobileByCreateDate(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/addMatCheckMobileMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatCheckMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String matJson;
		try {
			matJson = matCheckMobileMainService.saveMatCheckMobileMain(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);

	}

	/**
	 * @Description 主页查询
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/queryMatCheckMobileMain", method = RequestMethod.POST)
	@ResponseBody
	public Object queryMatCheckMobileMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		Object result;
		try {
			result = matCheckMobileMainService.queryMatCheckMobileMain(getPage(mapVo));
		} catch (Exception e) {
			result = e.getMessage();
		}

		return result;

	}

	/**
	 * @Description 删除数据 (暂时没有删除功能)
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/deleteMatCheckMobileMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatCheckMain(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("hos_id", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_id", ids[3]);
			mapVo.put("is_com", ids[4]);
			mapVo.put("detail_id", ids[5]);
			listVo.add(mapVo);
		}

		String matJson;
		try {
			matJson = matCheckMobileMainService.deleteBatch(listVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * @Description 根据盘点id查询各盘点材料的高值盘点数量
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/checkMobile/queryCheckMmobileCheAmount", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> queryCheckMmobileCheAmount(@RequestParam Map<String, Object> mapVo) throws Exception {
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		try {
			resultList = matCheckMobileMainService.queryCheckMobilesByCheckId(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return resultList;
	}
}
